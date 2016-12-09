package com.ygg.webapp.service.impl;

import java.util.HashMap;
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
import com.ygg.webapp.dao.GameActivityDao;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.entity.GameActivityEntity;
import com.ygg.webapp.entity.GamePrizeEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.GameActivityService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;

@Service("gameActivityService")
public class GameActivityServiceImpl implements GameActivityService
{
    private static Logger logger = Logger.getLogger(GameActivityServiceImpl.class);
    
    @Resource
    private GameActivityDao gameActivityDao;
    
    @Resource
    private AccountDao accountDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource
    private CouponDao couponDao;
    
    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Override
    public boolean existsGameActivity(int gameId)
        throws ServiceException
    {
        GameActivityEntity game = gameActivityDao.findGameActivityById(gameId);
        return game != null;
    }
    
    @Override
    public GameActivityEntity findGameActivityById(int gameId)
        throws ServiceException
    {
        return gameActivityDao.findGameActivityById(gameId);
    }
    
    @Override
    public GamePrizeEntity findGamePrizeByGameId(int gameId)
        throws ServiceException
    {
        return gameActivityDao.findGamePrizeByGameId(gameId);
    }
    
    @Override
    public Map<String, Object> receivePrize(String mobileNumber, int gameId)
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
        
        GameActivityEntity game = gameActivityDao.findGameActivityById(gameId);
        if (game == null)
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
        para.put("activityId", gameId);
        para.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_GAME.getCode());
        boolean isReceived = gameActivityDao.isReceived(para);
        if (isReceived)
        {
            msg = GameReceivePrizeMessageEnum.NO_CHANGES.getDescription();
            status = GameReceivePrizeMessageEnum.NO_CHANGES.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        //游戏关联奖励
        GamePrizeEntity gamePrize = gameActivityDao.findGamePrizeByGameId(gameId);
        if (gamePrize == null)
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
            status =
                accountDao.addRegisterMobileCoupon(mobileNumber, gamePrize.getCouponId(), gamePrize.getValidityDateType(), gamePrize.getDays(), CouponAccountSourceTypeEnum.GAME.getCode(), 0);
            
            // 未注册用户领取优惠券时，若游戏开启短信提醒，则发送短信
            try
            {
                if (game.getIsSendMsg() == 1)
                {
                    String[] mobiles = new String[] {mobileNumber};
//                    CommonUtil.sendSMS(mobiles, "【左岸城堡】" + game.getMsgContent(), 5);
                }
            }
            catch (Exception e)
            {
                logger.error("未注册用户领取游戏Id=" + game.getId() + "的优惠券时发送短信失败", e);
            }
            
        }
        else
        {
            //插入账户优惠券表
            CouponEntity coupon = couponDao.findCouponById(gamePrize.getCouponId());
            if (coupon == null)
            {
                msg = GameReceivePrizeMessageEnum.ERROR.getDescription();
                status = GameReceivePrizeMessageEnum.ERROR.ordinal();
                result.put("msg", msg);
                result.put("status", status);
                return result;
            }
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(accountId);
            cae.setCouponDetailId(coupon.getCouponDetailId());
            cae.setCouponId(coupon.getId());
            cae.setRemark(coupon.getRemark());
            if (gamePrize.getValidityDateType() == 1)
            {
                cae.setStartTime(coupon.getStartTime());
                cae.setEndTime(coupon.getEndTime());
            }
            else
            {
                cae.setStartTime(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toString("yyyy-MM-dd HH:mm:ss"));
                cae.setEndTime(DateTime.now().plusDays(gamePrize.getDays() - 1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toString("yyyy-MM-dd HH:mm:ss"));
            }
            cae.setSourceType((byte)(CouponAccountSourceTypeEnum.GAME.getCode()));
            cae.setCouponCodeId(0);
            status = couponAccountDao.addCouponAccount(cae);
        }
        
        //插入游戏与手机号关联表关联，记录已经领取该游戏奖励的手机号
        para.put("couponId", gamePrize.getCouponId());
        gameActivityDao.addRelationActivityAndReceivedMobile(para);
        
        //领取成功，更新游戏领取优惠券人数
        gameActivityDao.updateGameReceiveAmount(gameId);
        
        status = GameReceivePrizeMessageEnum.SUCCESS.ordinal();
        msg = GameReceivePrizeMessageEnum.getEnumByOrdinal(status).getDescription();
        result.put("status", status);
        result.put("msg", msg);
        
        return result;
    }
}
