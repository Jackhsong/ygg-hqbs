package com.ygg.webapp.service.impl;

import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.dao.*;
import com.ygg.webapp.entity.ActivitiesOrderGiftEntity;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.OrderGiftShareService;
import com.ygg.webapp.util.*;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 订单红包
 */
@Service("orderGiftShareService")
public class OrderGiftShareServiceImpl implements OrderGiftShareService
{
    private Logger log = Logger.getLogger(OrderGiftShareServiceImpl.class);
    
    @Resource(name = "orderGiftShareDao")
    private OrderGiftShareDao orderGiftShareDao;

    @Resource
    private AccountDao accountDao;

    @Resource
    private CouponDao couponDao;

    @Resource(name = "memService")
    private CacheServiceIF cacheService;

    @Resource
    private CouponDetailDao couponDetailDao;

    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Override
    public Map<String, Object> findOrderGiftReceiveInfo(int giftId, String openid)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<>();
        int isReceive = 0; // 是否已经领取红包
        int isBindingMobile = 0; // 是否已绑定手机
        String receiveMobile = ""; // 领取手机号
        String reducePrice = ""; // 领取金额
        
        Map<String, Object> record = orderGiftShareDao.findRecordByWXIdAndGiftId(openid, giftId);
        if (record == null)
        {
            // 未曾领取
            receiveMobile = orderGiftShareDao.findMobileByWeixinOpenId(openid); // 微信已绑定手机号
            receiveMobile = receiveMobile == null ? "" : receiveMobile;
            isBindingMobile = "".equals(receiveMobile) ? 0 : 1;
        }
        else
        {
            // 已领取
            isReceive = 1;
            isBindingMobile = 1;
            receiveMobile = record.get("mobileNumber") + "";
            reducePrice = record.get("reducePrice") + "";
        }
        
        result.put("isReceive", isReceive);
        result.put("reducePrice", reducePrice);
        result.put("isBindingMobile", isBindingMobile);
        result.put("receiveMobile", receiveMobile);
        return result;
    }
    
    @Override
    public List<Map<String, Object>> findRecordByGiftId(int giftId)
        throws ServiceException
    {
        List<Map<String, Object>> recordList = orderGiftShareDao.findRecordByGiftId(giftId);
        for (Map<String, Object> it : recordList)
        {
            try
            {
                String createTime = DateTimeUtil.timestampObjectToWebString(it.get("createTime"));
                it.put("createTime", createTime);
            }
            catch (Exception e)
            {
                log.error("时间类型转换出错！", e);
            }
        }
        return recordList;
    }

    @Override
    public Map<String, Object> drawOrderGift(String nickname, String headimgurl, String openid, int giftId, String mobileNumber)
        throws ServiceException
    {
        Map<String,Object> result = new HashMap<>();
        int status = 1;
        int errorCode = 0;
        String errorMsg = "领取失败啦，请稍后再试~";
        int reducePrice = 0;

        if(!"".equals(mobileNumber) && !CommonUtil.isMobile(mobileNumber))
        {
            status = 0;
            errorCode = 1;
            errorMsg = "亲，您的手机号有误~";
            result.put("status", status);
            result.put("errorCode", errorCode);
            result.put("errorMsg", errorMsg);
            return result;
        }

        // 判断微信号 是否已经领取过
        if (orderGiftShareDao.findRecordByWXIdAndGiftId(openid, giftId) != null)
        {
            status = 0;
            errorCode = 2;
            errorMsg = "亲，您已经领取过了~";
            result.put("status", status);
            result.put("errorCode", errorCode);
            result.put("errorMsg", errorMsg);
            return result;
        }

        if ("".equals(mobileNumber))
        {
            mobileNumber = orderGiftShareDao.findMobileByWeixinOpenId(openid);
        }

        // 判断手机号 是否已经领取过
        if (orderGiftShareDao.findRecordByMobileAndGiftId(mobileNumber, giftId) != null)
        {
            status = 0;
            errorCode = 2;
            errorMsg = "亲，您已经领取过了~";
            result.put("status", status);
            result.put("errorCode", errorCode);
            result.put("errorMsg", errorMsg);
            return result;
        }

        // 订单红包信息
        ActivitiesOrderGiftEntity aoge = orderGiftShareDao.findActivitiesOrderGiftById(giftId);
        if (aoge == null)
        {
            status = 0;
            errorCode = 3;
            errorMsg = "亲，红包已经被领完啦~";
            result.put("status", status);
            result.put("errorCode", errorCode);
            result.put("errorMsg", errorMsg);
            return result;
        }
        Date validityTime = new Date(aoge.getValidityTime().getTime());
        if (aoge.getLeftNum() <= 0 || validityTime.before(new Date()))
        {
            status = 0;
            errorCode = 3;
            errorMsg = "亲，红包已经被领完啦~";
            result.put("status", status);
            result.put("errorCode", errorCode);
            result.put("errorMsg", errorMsg);
            return result;
        }

        CouponEntity shareCoupon = couponDao.findCouponById(aoge.getShareCouponId());
        Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + shareCoupon.getCouponDetailId());
        CouponDetailEntity cde = null;
        if (cdeCache != null)
        {
            cde = (CouponDetailEntity)cdeCache;
        }
        else
        {
            cde = couponDetailDao.findCouponDetailById(shareCoupon.getCouponDetailId());
            cacheService.addCache(CacheConstant.COUPON_DETAIL + shareCoupon.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
        }

        int mobileNumberAccountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        log.info("mobileNumberAccountId: " + mobileNumberAccountId);
        if (mobileNumberAccountId == CommonConstant.ID_NOT_EXIST)
        {
            // 插入注册就送预优惠券表 100%
            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            {
                // 是随机优惠码
                int lowestReduce = cde.getLowestReduce();
                int maximalReduce = cde.getMaximalReduce();
                reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
            }
            if (accountDao.addRegisterMobileCoupon(mobileNumber, shareCoupon.getId(), 2, 7, CouponAccountSourceTypeEnum.RED_PACKET.getCode(), reducePrice) == 0)
            {
                throw new ServiceException("插入注册就送预优惠券失败！手机号：" + mobileNumber);
            }
        }
        else
        {
            // 判断是否是自己分享
            if (mobileNumberAccountId == aoge.getShareAccountId())
            {
                status = 0;
                errorCode = 4;
                errorMsg = "亲，您不可以领取自己分享的红包哦~";
                result.put("status", status);
                result.put("errorCode", errorCode);
                result.put("errorMsg", errorMsg);
                return result;
            }

            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(mobileNumberAccountId);
            cae.setCouponDetailId(shareCoupon.getCouponDetailId());
            cae.setCouponId(shareCoupon.getId());
            cae.setRemark(cde.getDesc());
            cae.setStartTime(DateTime.now().withTimeAtStartOfDay().toString(DateTimeUtil.WEB_FORMAT));
            cae.setEndTime(DateTime.now().plusDays(8).withTimeAtStartOfDay().plusMillis(-1).toString(DateTimeUtil.WEB_FORMAT));
            cae.setSourceType((byte)CouponAccountSourceTypeEnum.RED_PACKET.getCode());
            cae.setCouponCodeId(0);

            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            {
                // 是随机优惠码
                int lowestReduce = cde.getLowestReduce();
                int maximalReduce = cde.getMaximalReduce();
                reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
            }
            cae.setReducePrice(reducePrice);// 随机优惠码该值才有效，普通优惠券该值为0
            if (couponAccountDao.addCouponAccount(cae) == 0)
            {
                throw new ServiceException("插入用户订单红包失败！用户ID：" + mobileNumberAccountId);
            }
        }

        // 领取成功
        if (status == 1)
        {
            // 1 插入领取记录； 2 更新红包个数； 3 判断是否需要发送优惠券给分享者；4 插入领取手机号关系

            // 插入领取记录
            try
            {
                Map<String, Object> record = new HashMap<>();
                record.put("activitiesOrderGiftId", giftId);
                record.put("weixinOpenid", openid);
                record.put("mobileNumber", mobileNumber);
                record.put("username", nickname);
                record.put("headImage", headimgurl);
                record.put("reducePrice", reducePrice);
                if (orderGiftShareDao.insertActivitiesOrderGiftRecord(record) == 0)
                {
                    throw new ServiceException("插入订单红包领取记录失败！手机号：" + mobileNumber);
                }
            }
            catch (Exception e)
            {
                log.info("插入领取记录失败！,重新插入。。", e);
                // 插入领取记录
                Map<String, Object> record = new HashMap<>();
                record.put("activitiesOrderGiftId", giftId);
                record.put("weixinOpenid", openid);
                record.put("mobileNumber", mobileNumber);
                record.put("username", mobileNumber);
                record.put("headImage", headimgurl);
                record.put("reducePrice", reducePrice);
                if (orderGiftShareDao.insertActivitiesOrderGiftRecord(record) == 0)
                {
                    throw new ServiceException("插入红包领取记录失败！手机号：" + mobileNumber);
                }
            }

            // 更新红包个数
            int leftNum = aoge.getLeftNum() - 1;
            boolean isSuccess = leftNum == 0;
            Map<String,Object> upPara = new HashMap<>();
            upPara.put("id", aoge.getId());
            upPara.put("leftNum", leftNum > 0 ? leftNum : 0);
            upPara.put("isSuccess", isSuccess ? 1 : 0);
            if (orderGiftShareDao.updateActivitiesOrderGiftById(upPara) == 0)
            {
                throw new ServiceException("更新订单红包信息失败！红包ID：" + aoge.getId());
            }

            orderGiftShareDao.deleteMobile(mobileNumber);
            if (orderGiftShareDao.findMobileByWeixinOpenId(openid) == null)
            {
                if (orderGiftShareDao.addMobileByWeixinOpenId(openid, mobileNumber) == 0)
                {
                    throw new ServiceException("插入 订单红包领取  -  微信和手机号对应关系！手机号：" + mobileNumber);
                }
            }
            else
            {
                if (orderGiftShareDao.updateMobilePhone(openid, mobileNumber) == 0)
                {
                    throw new ServiceException("更新 订单红包领取  -  微信和手机号对应关系！手机号：" + mobileNumber);
                }
            }


            // 发送优惠券
            if (isSuccess && aoge.getIsSuccess() == 0)
            {
                CouponEntity successCoupon = couponDao.findCouponById(aoge.getSuccessCouponId());
                Object _cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + successCoupon.getCouponDetailId());
                CouponDetailEntity _cde = null;
                if (_cdeCache != null)
                {
                    _cde = (CouponDetailEntity)_cdeCache;
                }
                else
                {
                    _cde = couponDetailDao.findCouponDetailById(successCoupon.getCouponDetailId());
                    cacheService.addCache(CacheConstant.COUPON_DETAIL + successCoupon.getCouponDetailId(), _cde, CacheConstant.CACHE_DAY_1);
                }

                CouponAccountEntity cae = new CouponAccountEntity();
                cae.setAccountId(aoge.getShareAccountId());
                cae.setCouponDetailId(successCoupon.getCouponDetailId());
                cae.setCouponId(successCoupon.getId());
                cae.setRemark(_cde.getDesc());
                cae.setStartTime(DateTime.now().withTimeAtStartOfDay().toString(DateTimeUtil.WEB_FORMAT));
                cae.setEndTime(DateTime.now().plusDays(8).withTimeAtStartOfDay().plusMillis(-1).toString(DateTimeUtil.WEB_FORMAT));
                cae.setSourceType((byte)CouponAccountSourceTypeEnum.RED_PACKET.getCode());
                cae.setCouponCodeId(0);

                if (_cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                {
                    // 是随机优惠码
                    int lowestReduce = _cde.getLowestReduce();
                    int maximalReduce = _cde.getMaximalReduce();
                    reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
                }
                cae.setReducePrice(reducePrice);// 随机优惠码该值才有效，普通优惠券该值为0
                status = couponAccountDao.addCouponAccount(cae);
                if (status == 0)
                {
                    throw new ServiceException("订单红包领取人数达到要求，赠送红包！orderSameBatchNumber：" + aoge.getOrderSameBatchNumber());
                }
            }
        }
        result.put("status", 1);
        result.put("reducePrice", reducePrice);
        return result;
    }

    @Override
    public ActivitiesOrderGiftEntity findActivitiesOrderGiftById(int id)
        throws DaoException
    {
        return orderGiftShareDao.findActivitiesOrderGiftById(id);
    }

    @Override
    public Map<String, Object> updateMobilePhone(String wxOpenId, String mobileNumber)
        throws ServiceException
    {
        Map<String,Object> result = new HashMap<>();
        result.put("status", orderGiftShareDao.updateMobilePhone(wxOpenId, mobileNumber));
        return result;
    }
}
