package com.ygg.webapp.service.impl;

import javax.annotation.Resource;

import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.ErrorCodeEnum;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponCodeEntity;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.view.CouponView;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.CouponDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.CouponDetailService;

import java.util.*;

@Service("couponDetailService")
public class CouponDetailServiceImpl implements CouponDetailService
{
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource
    private CouponDao couponDao;

    @Resource
    private CouponAccountDao couponAccountDao;

    @Resource
    private CouponCodeDao couponCodeDao;

    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Override
    public CouponDetailEntity findCouponDetailByCouponId(int couponId)
        throws ServiceException
    {
        return couponDetailDao.findCouponDetailByCouponId(couponId);
    }
    
    @Override
    public CouponEntity findCouponById(int couponId)
        throws ServiceException
    {
        return couponDao.findCouponById(couponId);
    }

    @Override
    public Map<String, Object> findCouponAccountInfo(int accountId, int type)
        throws ServiceException
    {
        List<CouponAccountEntity> caes = new ArrayList<>();
        if (type == CommonEnum.ACCOUNT_COUPON_TYPE.UNUSED.getValue())
        {
            caes = couponAccountDao.findUnusedCouponsByAid(accountId);
        }
        else if (type == CommonEnum.ACCOUNT_COUPON_TYPE.USED.getValue())
        {
            caes = couponAccountDao.findUsedCouponsByAid(accountId);
        }
        else if (type == CommonEnum.ACCOUNT_COUPON_TYPE.EXPIRED.getValue())
        {
            caes = couponAccountDao.findExpiredCouponsByAid(accountId);
        }

        List<CouponView> cvs = new ArrayList<>();
        List<CouponView> uncvs = new ArrayList<>();
        Date currDate = CommonUtil.string2Date(CommonUtil.date2String(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");

        for (CouponAccountEntity cae : caes)
        {
            CouponView cv = new CouponView();
            cv.setId(cae.getId() + "");
            Date startTime = CommonUtil.string2Date(cae.getStartTime(),"yyyy-MM-dd HH:mm:ss");
            Date endTime = CommonUtil.string2Date(cae.getEndTime(),"yyyy-MM-dd HH:mm:ss");
            cv.setStartTime(CommonUtil.date2String(startTime,"yy-MM-dd"));
            cv.setEndTime(CommonUtil.date2String(endTime, "yy-MM-dd"));
            cv.setScope(cae.getRemark());
            Date startDate = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd");
            
            Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId());
            CouponDetailEntity cde = null;
            if (cdeCache != null)
            {
                cde = (CouponDetailEntity)cdeCache;
            }
            else
            {
                cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
                cacheService.addCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
            }
            
            String reduce = cde.getReduce() + "";
            // 如果是随机优惠码
            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            {
                reduce = cae.getReducePrice() + "";
            }
            cv.setReducePrice(reduce);
            cv.setThresholdPrice(cde.getThreshold() + "");
            cv.setType(cde.getType() + "");
            if (currDate.before(startDate))
            {
                cv.setIsAvailable(CommonEnum.COMMON_IS.NO.getValue());
                uncvs.add(cv);
            }
            else
            {
                cv.setIsAvailable(CommonEnum.COMMON_IS.YES.getValue());
                cvs.add(cv);
            }
        }
        cvs.addAll(uncvs);

        Map<String,Object> result = new HashMap<>();
        result.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.put("coupons",cvs);
        return result;
    }

    @Override
    public Map<String, Object> executeCodeChangeCoupon(int accountId, String code)
        throws ServiceException
    {
        Map<String,Object> result = new HashMap<>();
        Date currDate = CommonUtil.string2Date(CommonUtil.date2String(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
        List<CouponView> cvs = new ArrayList<>();
        CouponCodeEntity cce = couponCodeDao.findInfoByCommonCode(code);
        if (cce != null) // 是一个通用优惠码
        {
            if (CommonEnum.COMMON_IS.NO.getValue().equals(cce.getIsAvailable() + ""))
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.COUPONCODE_NOT_EXIST.getErrorMessage());
                return result;
            }

            Date endDate = CommonUtil.string2Date(cce.getEndTime(), "yyyy-MM-dd");
            if (currDate.after(endDate))
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.COUPONCODE_NOT_EXIST.getErrorMessage());
                return result;
            }

            if (couponCodeDao.findCommonCodeCountByAidAndId(accountId, cce.getId()) >= cce.getSameMaxCount())
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.EXCEED_USED_COUNT.getErrorMessage());
                return result;
            }
            else
            {
                List<Map<String, Object>> cdeMapList = null;
                if (CommonEnum.COUPON_CODE_CHANGE_TPYE.LIBAO.getValue().equals(cce.getChangeType() + ""))
                {
                    cdeMapList = couponCodeDao.findCouponCodeGiftBag(cce.getId());
                }
                else if (CommonEnum.COUPON_CODE_CHANGE_TPYE.SINGLE.getValue().equals(cce.getChangeType() + ""))
                {
                    cdeMapList = new ArrayList<>();
                    Map<String, Object> cm = new HashMap<>();
                    cm.put("couponDetailId", cce.getCouponDetailId());
                    cm.put("changeCount", cce.getChangeCount());
                    cdeMapList.add(cm);
                }
                for (Map<String, Object> currMap : cdeMapList)
                {
                    Integer currId = Integer.parseInt(currMap.get("couponDetailId") + "");
                    Integer changeCount = Integer.parseInt(currMap.get("changeCount") + "");
                    Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + currId);
                    CouponDetailEntity cde = null;
                    if (cdeCache != null)
                    {
                        cde = (CouponDetailEntity)cdeCache;
                    }
                    else
                    {
                        cde = couponDetailDao.findCouponDetailById(currId);
                        cacheService.addCache(CacheConstant.COUPON_DETAIL + currId, cde, CacheConstant.CACHE_DAY_1);
                    }
                    for (int j = 0; j < changeCount; j++)
                    {
                        CouponView cv = new CouponView();
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(currId);
                        cae.setCouponId(0);
                        cae.setEndTime(cce.getEndTime());
                        cae.setRemark(cde.getDesc());
                        cae.setStartTime(cce.getStartTime());
                        cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_CODE_EXCHANGE.getValue());
                        cae.setCouponCodeId((cce.getId()));

                        int reducePrice = 0;

                        if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                        {
                            // 是随机优惠码
                            int lowestReduce = cde.getLowestReduce();
                            int maximalReduce = cde.getMaximalReduce();
                            reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
                            cv.setReducePrice(reducePrice + "");
                        }
                        else
                        {
                            cv.setReducePrice(cde.getReduce() + "");
                        }
                        cae.setReducePrice(reducePrice);// 随机优惠码该值才有效，普通优惠券该值为0

                        if (couponAccountDao.addCouponAccount(cae) == 0){
                            throw new ServiceException("生成优惠券失败！");
                        }
                        cv.setId(cae.getId() + "");

                        cv.setStartTime(cce.getStartTime());
                        cv.setEndTime(cce.getEndTime());
                        cv.setScope(cce.getRemark());
                        cv.setThresholdPrice(cde.getThreshold() + "");
                        cv.setType(cde.getType() + "");
                        Date startDate = CommonUtil.string2Date(cce.getStartTime(), "yyyy-MM-dd");
                        if (currDate.before(startDate))
                        {
                            cv.setIsAvailable(CommonEnum.COMMON_IS.NO.getValue());
                        }
                        else
                        {
                            cv.setIsAvailable(CommonEnum.COMMON_IS.YES.getValue());
                        }
                        cv.setScope(cae.getRemark());
                        cvs.add(cv);
                    }
                }
            }
            couponCodeDao.addCouponCodeCommon(accountId, cce.getId(), Integer.parseInt(cvs.get(0).getId()));
        }
        else
        {
            Map<String, Object> ccdMap = couponCodeDao.findCouponCodeDetailByCode(code);
            if (ccdMap == null)
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.COUPONCODE_NOT_EXIST.getErrorMessage());
                return result;
            }
            else
            {
                Integer is_used = (Integer)ccdMap.get("is_used");
                if (CommonEnum.COMMON_IS.YES.getValue().equals(is_used + ""))
                {
                    result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.COUPONCODE_IS_USED.getErrorMessage());
                    return result;
                }
                int couponCodeId = ((Long)ccdMap.get("coupon_code_id")).intValue();
                cce = couponCodeDao.findInfoById(couponCodeId);

                if (CommonEnum.COMMON_IS.NO.getValue().equals(cce.getIsAvailable() + ""))
                {
                    result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.COUPONCODE_NOT_EXIST.getErrorMessage());
                    return result;
                }

                Date endDate = CommonUtil.string2Date(cce.getEndTime(), "yyyy-MM-dd");
                if (currDate.after(endDate))
                {
                    result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.COUPONCODE_IS_EXPIRED.getErrorMessage());
                    return result;
                }
                if (couponCodeDao.findCodeCountByAidAndId(accountId, cce.getId()) >= cce.getSameMaxCount())
                {
                    result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.put("msg", ErrorCodeEnum.ACCOUNT_CODECHANGECOUPON_ERRORCODE.EXCEED_USED_COUNT.getErrorMessage());
                    return result;
                }
                else
                {
                    List<Map<String, Object>> cdeMapList = null;
                    if (CommonEnum.COUPON_CODE_CHANGE_TPYE.LIBAO.getValue().equals(cce.getChangeType() + ""))
                    {
                        cdeMapList = couponCodeDao.findCouponCodeGiftBag(cce.getId());
                    }
                    else if (CommonEnum.COUPON_CODE_CHANGE_TPYE.SINGLE.getValue().equals(cce.getChangeType() + ""))
                    {
                        cdeMapList = new ArrayList<>();
                        Map<String, Object> cm = new HashMap<>();
                        cm.put("couponDetailId", cce.getCouponDetailId());
                        cm.put("changeCount", cce.getChangeCount());
                        cdeMapList.add(cm);
                    }
                    for (Map<String, Object> currMap : cdeMapList)
                    {
                        Integer currId = Integer.parseInt(currMap.get("couponDetailId") + "");
                        Integer changeCount = Integer.parseInt(currMap.get("changeCount") + "");
                        Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + currId);
                        CouponDetailEntity cde = null;
                        if (cdeCache != null)
                        {
                            cde = (CouponDetailEntity)cdeCache;
                        }
                        else
                        {
                            cde = couponDetailDao.findCouponDetailById(currId);
                            cacheService.addCache(CacheConstant.COUPON_DETAIL + currId, cde, CacheConstant.CACHE_DAY_1);
                        }

                        for (int j = 0; j < changeCount; j++)
                        {
                            CouponView cv = new CouponView();
                            CouponAccountEntity cae = new CouponAccountEntity();
                            cae.setAccountId(accountId);
                            cae.setCouponDetailId(currId);
                            cae.setCouponId(0);
                            cae.setEndTime(cce.getEndTime());
                            cae.setRemark(cde.getDesc());
                            cae.setStartTime(cce.getStartTime());
                            cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_CODE_EXCHANGE.getValue());
                            cae.setCouponCodeId((cce.getId()));

                            int reducePrice = 0;
                            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                            {
                                // 是随机优惠码
                                int lowestReduce = cde.getLowestReduce();
                                int maximalReduce = cde.getMaximalReduce();
                                reducePrice = new Random().nextInt(maximalReduce - lowestReduce) + lowestReduce;
                                cv.setReducePrice(reducePrice + "");
                            }
                            else
                            {
                                cv.setReducePrice(cde.getReduce() + "");
                            }

                            cae.setReducePrice(reducePrice);// 随机优惠码该值才有效，普通优惠券该值为0

                            if (couponAccountDao.addCouponAccount(cae) == 0){
                                throw new ServiceException("生成优惠券失败！");
                            }
                            cv.setId(cae.getId() + "");

                            cv.setStartTime(cce.getStartTime());
                            cv.setEndTime(cce.getEndTime());
                            cv.setScope(cce.getRemark());
                            cv.setThresholdPrice(cde.getThreshold() + "");
                            cv.setType(cde.getType() + "");
                            Date startDate = CommonUtil.string2Date(cce.getStartTime(), "yyyy-MM-dd");
                            if (currDate.before(startDate))
                            {
                                cv.setIsAvailable(CommonEnum.COMMON_IS.NO.getValue());
                            }
                            else
                            {
                                cv.setIsAvailable(CommonEnum.COMMON_IS.YES.getValue());
                            }
                            cv.setScope(cae.getRemark());
                            cvs.add(cv);
                        }
                    }
                    if (couponCodeDao.updateCouponCodeDetail2Used(accountId, Integer.parseInt(cvs.get(0).getId()), ((Long)ccdMap.get("id")).intValue()) == 0)
                    {
                        throw new ServiceException("updateCouponCodeDetail2Used出错！");
                    }
                }
            }
        }
        result.put("couponDetails",cvs);
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }

    @Override
    public Map<String, Object> executeOrderCoupon(int couponAccountId, int accountId)
        throws ServiceException
    {
        Map<String,Object> result = null;
        CouponAccountEntity  cae = couponAccountDao.findCouponsByAidAndId(couponAccountId, accountId);
        if (cae != null)
        {
            result = new HashMap<>();
            Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId());
            CouponDetailEntity cde = null;
            if (cdeCache != null)
            {
                cde = (CouponDetailEntity)cdeCache;
            }
            else
            {
                cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
                cacheService.addCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
            }

            int reducePirce = cde.getReduce();
            int thresholdPrice = cde.getThreshold();
            int type = cde.getType();
            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            {
                reducePirce = cae.getReducePrice();
            }
            result.put("thresholdPrice",thresholdPrice);
            result.put("reducePirce",reducePirce);
            result.put("type",type);
        }
        return result;
    }
}
