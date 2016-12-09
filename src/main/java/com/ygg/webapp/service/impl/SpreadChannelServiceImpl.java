package com.ygg.webapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.ygg.webapp.code.ActivityTypeEnum;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.code.GameReceivePrizeMessageEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.SpreadChannelDao;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.entity.SpreadChannelEntity;
import com.ygg.webapp.entity.SpreadChannelPrizeEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.SpreadChannelService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;

@Service("spreadChannelService")
public class SpreadChannelServiceImpl implements SpreadChannelService
{
    private static Logger logger = Logger.getLogger(SpreadChannelServiceImpl.class);
    
    @Resource
    private SpreadChannelDao spreadChannelDao;
    
    @Resource
    private AccountDao accountDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource
    private CouponDao couponDao;
    
    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Override
    public SpreadChannelEntity findSpreadChannelById(int channelId)
        throws ServiceException
    {
        return spreadChannelDao.findSpreadChannelById(channelId);
    }
    
    @Override
    public List<SpreadChannelPrizeEntity> findSpreadChannelPrizeByChannelId(int channelId)
        throws ServiceException
    {
        return spreadChannelDao.findSpreadChannelPrizeByChannelId(channelId);
    }
    
    @Override
    public Map<String, Object> receivePrize(String mobileNumber, int channelId)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        String msg = "";
        
        if ("".equals(mobileNumber))
        {
            msg = GameReceivePrizeMessageEnum.ERROR.getDescription();
            status = GameReceivePrizeMessageEnum.ERROR.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        SpreadChannelEntity channel = spreadChannelDao.findSpreadChannelById(channelId);
        if (channel == null)
        {
            msg = GameReceivePrizeMessageEnum.ACTIVITY_FINISHED.getDescription();
            status = GameReceivePrizeMessageEnum.ACTIVITY_FINISHED.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        
        //判断是否已经领取过奖励
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("mobileNumber", mobileNumber);
        para.put("activityId", channelId);
        para.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_SPREADCHANNEL.getCode());
        boolean isReceived = spreadChannelDao.isReceived(para);
        if (isReceived)
        {
            msg = GameReceivePrizeMessageEnum.NO_CHANGES.getDescription();
            status = GameReceivePrizeMessageEnum.NO_CHANGES.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        //游戏关联奖励
        List<SpreadChannelPrizeEntity> prizeList = spreadChannelDao.findSpreadChannelPrizeByChannelId(channelId);
        if (prizeList == null || prizeList.size() == 0)
        {
            msg = GameReceivePrizeMessageEnum.ERROR.getDescription();
            status = GameReceivePrizeMessageEnum.ERROR.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        
        int accountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (accountId == CommonConstant.ID_NOT_EXIST)
        {
            //插入注册就送预优惠券表
            for (SpreadChannelPrizeEntity prize : prizeList)
            {
                CouponEntity coupon = couponDao.findCouponById(prize.getCouponId());
                if (coupon != null)
                {
                    for (int i = 0; i < prize.getCouponAmount(); i++)
                    {
                        status =
                            accountDao.addRegisterMobileCoupon(mobileNumber, prize.getCouponId(), prize.getValidityDateType(), prize.getDays(), CouponAccountSourceTypeEnum.SPREAD_CHANNEL.getCode(), 0);
                    }
                    //插入渠道与手机号关联表关联，记录已经领取该游戏奖励的手机号
                    para.put("couponId", prize.getCouponId());
                    spreadChannelDao.addRelationActivityAndReceivedMobile(para);
                }
            }
            
            //如果渠道开启短信提醒，则发送短信
            try
            {
                if (channel.getIsSendMsg() == 1)
                {
                    String[] mobiles = new String[] {mobileNumber};
                    CommonUtil.sendSMS(mobiles, "【左岸城堡】" + channel.getMsgContent(), 5);
                }
            }
            catch (Exception e)
            {
                logger.error("未注册用户领取渠道Id=" + channel.getId() + "的优惠券时发送短信失败", e);
            }
            
        }
        else
        {
            //插入账户优惠券表
            for (SpreadChannelPrizeEntity prize : prizeList)
            {
                CouponEntity coupon = couponDao.findCouponById(prize.getCouponId());
                if (coupon != null)
                {
                    for (int i = 0; i < prize.getCouponAmount(); i++)
                    {
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(coupon.getCouponDetailId());
                        cae.setCouponId(coupon.getId());
                        cae.setRemark(coupon.getRemark());
                        if (prize.getValidityDateType() == 1)
                        {
                            cae.setStartTime(coupon.getStartTime());
                            cae.setEndTime(coupon.getEndTime());
                        }
                        else
                        {
                            cae.setStartTime(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toString("yyyy-MM-dd HH:mm:ss"));
                            cae.setEndTime(DateTime.now()
                                .plusDays(prize.getDays() - 1)
                                .withHourOfDay(23)
                                .withMinuteOfHour(59)
                                .withSecondOfMinute(59)
                                .toString("yyyy-MM-dd HH:mm:ss"));
                        }
                        cae.setSourceType((byte)(CouponAccountSourceTypeEnum.SPREAD_CHANNEL.getCode()));
                        cae.setCouponCodeId(0);
                        status = couponAccountDao.addCouponAccount(cae);
                    }
                    //插入渠道与手机号关联表关联，记录已经领取该游戏奖励的手机号
                    para.put("couponId", prize.getCouponId());
                    spreadChannelDao.addRelationActivityAndReceivedMobile(para);
                }
            }
            
        }
        
        //领取成功，更新渠道领取优惠券人数
        spreadChannelDao.updateSpreadChannelReceiveAmount(channelId);
        
        status = GameReceivePrizeMessageEnum.SUCCESS.ordinal();
        msg = GameReceivePrizeMessageEnum.getEnumByOrdinal(status).getDescription();
        result.put("status", status);
        result.put("msg", msg);
        
        return result;
    }
}
