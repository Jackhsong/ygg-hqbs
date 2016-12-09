package com.ygg.webapp.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.ygg.webapp.code.ActivityTypeEnum;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.code.GateOpenDoorStatusTypeEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.GateActivityDao;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.entity.GateActivityEntity;
import com.ygg.webapp.entity.GatePrizeEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.GateActivityService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;

@Service("gateActivityService")
public class GateActivityServiceImpl implements GateActivityService
{
    @Resource
    private GateActivityDao gateActivityDao;
    
    @Resource
    private AccountDao accountDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource
    private CouponDao couponDao;
    
    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Override
    public GateActivityEntity findGateActivity(Map<String, Object> para)
        throws ServiceException
    {
        return gateActivityDao.findGateActivity(para);
    }
    
    @Override
    public GatePrizeEntity findGatePrizeByGateId(int id)
        throws ServiceException
    {
        return gateActivityDao.findGatePrizeByGateId(id);
    }
    
    @Override
    public Map<String, Object> receivePrize(String mobileNumber, int gateId)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if ("".equals(mobileNumber))
        {
            //手机号不存在
            resultMap.put("status", GateOpenDoorStatusTypeEnum.USER_NOT_EXISTS.getCode());
            return resultMap;
        }
        
        GateActivityEntity gate = gateActivityDao.findGateActivityById(gateId);
        if (gate == null)
        {
            resultMap.put("status", GateOpenDoorStatusTypeEnum.SERVER_ERROR.getCode());
            return resultMap;
        }
        
        //判断是否已经领取过奖励
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("mobileNumber", mobileNumber);
        para.put("activityId", gateId);
        para.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_ANYDOOR.getCode());
        boolean isReceived = gateActivityDao.isReceived(para);
        if (isReceived)
        {
            resultMap.put("status", GateOpenDoorStatusTypeEnum.RECEIVED_YET.getCode());
            return resultMap;
        }
        //游戏关联奖励
        GatePrizeEntity gatePrize = gateActivityDao.findGatePrizeByGateId(gateId);
        if (gatePrize == null)
        {
            resultMap.put("status", GateOpenDoorStatusTypeEnum.PRIZE_NOT_EXISTS.getCode());
            return resultMap;
        }
        
        CouponEntity coupon = couponDao.findCouponById(gatePrize.getCouponId());
        CouponDetailEntity cde = couponDetailDao.findCouponDetailByCouponId(gatePrize.getCouponId());
        if (coupon == null || cde == null)
        {
            resultMap.put("status", GateOpenDoorStatusTypeEnum.PRIZE_NOT_EXISTS.getCode());
            return resultMap;
        }
        StringBuffer couponInfo = new StringBuffer("");
        StringBuffer validTime = new StringBuffer("有效期至：");
        StringBuffer receiveTip = new StringBuffer("任意门打开了，哇~是");
        
        int accountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (accountId == CommonConstant.ID_NOT_EXIST)
        {
            //插入注册就送预优惠券表
            accountDao.addRegisterMobileCoupon(mobileNumber, gatePrize.getCouponId(), gatePrize.getValidTimeType(), gatePrize.getDays(), CouponAccountSourceTypeEnum.ANY_DOOR.getCode(), 0);
            couponInfo.append("1到500元礼包");
            //            if (cde.getType() == 1)
            //            {
            //                couponInfo.append("满").append(cde.getThreshold()).append("减");
            //                if (cde.getIsRandomReduce() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
            //                {
            //                    couponInfo.append(cde.getLowestReduce()).append("到").append(cde.getMaximalReduce()).append("随机优惠券");
            //                }
            //                else
            //                {
            //                    couponInfo.append(cde.getReduce()).append("优惠券");
            //                }
            //            }
            //            else if (cde.getType() == 2)
            //            {
            //                if (cde.getIsRandomReduce() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
            //                {
            //                    couponInfo.append(cde.getLowestReduce()).append("到").append(cde.getMaximalReduce()).append("元随机现金券");
            //                }
            //                else
            //                {
            //                    couponInfo.append(cde.getReduce()).append("元现金券");
            //                }
            //            }
        }
        else
        {
            //插入账户优惠券表
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(accountId);
            cae.setCouponDetailId(coupon.getCouponDetailId());
            cae.setCouponId(coupon.getId());
            cae.setRemark(coupon.getRemark());
            if (gatePrize.getValidTimeType() == 1)
            {
                cae.setStartTime(coupon.getStartTime());
                cae.setEndTime(coupon.getEndTime());
            }
            else
            {
                cae.setStartTime(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toString("yyyy-MM-dd HH:mm:ss"));
                cae.setEndTime(DateTime.now().plusDays(gatePrize.getDays() - 1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toString("yyyy-MM-dd HH:mm:ss"));
            }
            cae.setSourceType((byte)(CouponAccountSourceTypeEnum.ANY_DOOR.getCode()));
            cae.setCouponCodeId(0);
            
            int reducePrice = 0;
            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            {
                // 是随机优惠券
                int lowestReduce = cde.getLowestReduce();
                int maximalReduce = cde.getMaximalReduce();
                reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
            }
            else
            {
                reducePrice = cde.getReduce();
            }
            cae.setReducePrice(reducePrice);// 随机优惠券该值才有效，普通优惠券该值为0
            couponAccountDao.addCouponAccount(cae);
            
            if (cde.getType() == 1)
            {
                couponInfo.append("满").append(cde.getThreshold()).append("减").append(reducePrice).append("优惠券");
            }
            else if (cde.getType() == 2)
            {
                couponInfo.append(reducePrice).append("元现金券");
            }
        }
        
        receiveTip.append(couponInfo).append("耶！");
        
        if (gatePrize.getValidTimeType() == 1)
        {
            DateTime endTime = new DateTime(CommonUtil.string2Date(coupon.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            validTime.append(endTime.toString("yyyy.MM.dd"));
        }
        else
        {
            validTime.append(DateTime.now().plusDays(gatePrize.getDays() - 1).toString("yyyy.MM.dd"));
        }
        
        //插入活动与手机号关联表关联，记录已经领取该活动的手机号
        para.put("couponId", gatePrize.getCouponId());
        gateActivityDao.addRelationActivityAndReceivedMobile(para);
        
        //领取成功，更新领取优惠券人数
        gateActivityDao.updateGateReceiveAmount(gateId);
        resultMap.put("status", GateOpenDoorStatusTypeEnum.RECEIVE_SUCCESS.getCode());
        resultMap.put("couponInfo", couponInfo);
        resultMap.put("validTime", validTime);
        resultMap.put("receiveTip", receiveTip);
        return resultMap;
    }
    
    @Override
    public Map<String, Object> webOpenDoor(Map<String, Object> para)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        GateActivityEntity gae = gateActivityDao.findGateActivity(para);
        if (gae == null)
        {
            resultMap.put("status", GateOpenDoorStatusTypeEnum.PASSWD_ERROR.getCode());
        }
        else
        {
            GatePrizeEntity gatePrize = gateActivityDao.findGatePrizeByGateId(gae.getId());
            if (gatePrize == null)
            {
                resultMap.put("status", GateOpenDoorStatusTypeEnum.SERVER_ERROR.getCode());
                return resultMap;
            }
            CouponDetailEntity couponDetail = couponDetailDao.findCouponDetailByCouponId(gatePrize.getCouponId());
            if (couponDetail == null)
            {
                resultMap.put("status", GateOpenDoorStatusTypeEnum.SERVER_ERROR.getCode());
                return resultMap;
            }
            
            StringBuffer couponInfo = new StringBuffer("1到500元礼包");
            StringBuffer validTime = new StringBuffer("有效期至：");
            StringBuffer receiveTip = new StringBuffer("任意门打开了，哇~是");
            //            if (couponDetail.getType() == 1)
            //            {
            //                couponInfo.append("满").append(couponDetail.getThreshold()).append("减");
            //                if (couponDetail.getIsRandomReduce() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
            //                {
            //                    couponInfo.append(couponDetail.getLowestReduce()).append("到").append(couponDetail.getMaximalReduce()).append("随机优惠券");
            //                }
            //                else
            //                {
            //                    couponInfo.append(couponDetail.getReduce()).append("优惠券");
            //                }
            //            }
            //            else if (couponDetail.getType() == 2)
            //            {
            //                if (couponDetail.getIsRandomReduce() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
            //                {
            //                    couponInfo.append(couponDetail.getLowestReduce()).append("到").append(couponDetail.getMaximalReduce()).append("元随机现金券");
            //                }
            //                else
            //                {
            //                    couponInfo.append(couponDetail.getReduce()).append("元现金券");
            //                }
            //            }
            //            
            receiveTip.append(couponInfo).append("耶！");
            
            if (gatePrize.getValidTimeType() == 1)
            {
                CouponEntity coupon = couponDao.findCouponById(gatePrize.getCouponId());
                DateTime endTime = new DateTime(CommonUtil.string2Date(coupon.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
                validTime.append(endTime.toString("yyyy.MM.dd"));
            }
            else if (gatePrize.getValidTimeType() == 2)
            {
                // 领券之日N天内有效，可能不准确，有可能用户打开页面没有领券
                validTime.append(DateTime.now().plusDays(gatePrize.getDays() - 1).toString("yyyy.MM.dd"));
            }
            resultMap.put("status", GateOpenDoorStatusTypeEnum.RECEIVE_SUCCESS.getCode());
            resultMap.put("couponInfo", couponInfo);
            resultMap.put("validTime", validTime);
            resultMap.put("receiveTip", receiveTip);
            
        }
        return resultMap;
    }
    
    @Override
    public Map<String, Object> appOpenDoor(Map<String, Object> para)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int accountId = Integer.valueOf(para.get("accountId") + "").intValue();
        int gateId = Integer.valueOf(para.get("id") + "").intValue();
        if (accountId == 0 || accountId == -1)
        {
            //用户不存在
            resultMap.put("status", GateOpenDoorStatusTypeEnum.USER_NOT_EXISTS.getCode());
            return resultMap;
        }
        
        GateActivityEntity gate = gateActivityDao.findGateActivity(para);
        if (gate == null)
        {
            //口令错误
            resultMap.put("status", GateOpenDoorStatusTypeEnum.PASSWD_ERROR.getCode());
            return resultMap;
        }
        
        String mobileNumber = accountDao.findAccountNameById(accountId);
        if (StringUtils.isEmpty(mobileNumber))
        {
            //用户不存在
            resultMap.put("status", GateOpenDoorStatusTypeEnum.USER_NOT_EXISTS.getCode());
            return resultMap;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("mobileNumber", mobileNumber);
        param.put("activityId", gateId);
        param.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_ANYDOOR.getCode());
        boolean isReceived = gateActivityDao.isReceived(param);
        if (isReceived)
        {
            //已经领取
            resultMap.put("status", GateOpenDoorStatusTypeEnum.RECEIVED_YET.getCode());
            return resultMap;
        }
        //关联奖励
        GatePrizeEntity gatePrize = gateActivityDao.findGatePrizeByGateId(gateId);
        if (gatePrize == null)
        {
            //奖品不存在
            resultMap.put("status", GateOpenDoorStatusTypeEnum.PRIZE_NOT_EXISTS.getCode());
            return resultMap;
        }
        
        CouponEntity coupon = couponDao.findCouponById(gatePrize.getCouponId());
        CouponDetailEntity cde = couponDetailDao.findCouponDetailByCouponId(gatePrize.getCouponId());
        if (coupon == null || cde == null)
        {
            //奖品不存在
            resultMap.put("status", GateOpenDoorStatusTypeEnum.PRIZE_NOT_EXISTS.getCode());
            return resultMap;
        }
        
        StringBuffer couponInfo = new StringBuffer("");
        StringBuffer validTime = new StringBuffer("有效期至：");
        StringBuffer receiveTip = new StringBuffer("任意门打开了，哇~是");
        
        //插入账户优惠券表
        CouponAccountEntity cae = new CouponAccountEntity();
        cae.setAccountId(accountId);
        cae.setCouponDetailId(coupon.getCouponDetailId());
        cae.setCouponId(coupon.getId());
        cae.setRemark(coupon.getRemark());
        if (gatePrize.getValidTimeType() == 1)
        {
            cae.setStartTime(coupon.getStartTime());
            cae.setEndTime(coupon.getEndTime());
            DateTime endTime = new DateTime(CommonUtil.string2Date(coupon.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            validTime.append(endTime.toString("yyyy.MM.dd"));
        }
        else
        {
            cae.setStartTime(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toString("yyyy-MM-dd HH:mm:ss"));
            cae.setEndTime(DateTime.now().plusDays(gatePrize.getDays() - 1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toString("yyyy-MM-dd HH:mm:ss"));
            validTime.append(DateTime.now().plusDays(gatePrize.getDays() - 1).toString("yyyy.MM.dd"));
        }
        cae.setSourceType((byte)(CouponAccountSourceTypeEnum.ANY_DOOR.getCode()));
        cae.setCouponCodeId(0);
        
        int reducePrice = 0;
        if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
        {
            // 是随机优惠券
            int lowestReduce = cde.getLowestReduce();
            int maximalReduce = cde.getMaximalReduce();
            reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
        }
        else
        {
            reducePrice = cde.getReduce();
            
        }
        cae.setReducePrice(reducePrice);
        couponAccountDao.addCouponAccount(cae);
        if (cde.getType() == 1)
        {
            couponInfo.append("满").append(cde.getThreshold()).append("减").append(reducePrice).append("优惠券");
        }
        else if (cde.getType() == 2)
        {
            couponInfo.append(reducePrice).append("元现金券");
        }
        receiveTip.append(couponInfo).append("耶！");
        
        //插入活动与手机号关联表关联，记录已经领取该活动的手机号
        param.put("couponId", gatePrize.getCouponId());
        gateActivityDao.addRelationActivityAndReceivedMobile(param);
        
        //领取成功，更新领取优惠券人数
        gateActivityDao.updateGateReceiveAmount(gateId);
        
        //领取成功
        resultMap.put("couponInfo", couponInfo);
        resultMap.put("validTime", validTime);
        resultMap.put("receiveTip", receiveTip);
        resultMap.put("status", GateOpenDoorStatusTypeEnum.RECEIVE_SUCCESS.getCode());
        return resultMap;
    }
    
    @Override
    public GateActivityEntity findNextOpenGateActivity(Map<String, Object> para)
        throws ServiceException
    {
        return gateActivityDao.findNextOpenGateActivity(para);
    }
}
