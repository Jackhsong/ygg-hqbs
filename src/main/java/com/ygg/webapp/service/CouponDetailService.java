package com.ygg.webapp.service;

import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface CouponDetailService
{

    /**
     * 查询优惠券详情
     * @param couponId
     * @return
     * @throws ServiceException
     */
    CouponDetailEntity findCouponDetailByCouponId(int couponId)
        throws ServiceException;

    /**
     * 查询优惠券
     * @param couponId
     * @return
     * @throws ServiceException
     */
    CouponEntity findCouponById(int couponId)
        throws ServiceException;

    /**
     * 获取用户优惠券信息
     * @param accountId
     * @param type 1[未使用]、2[已使用]、3[已过期]
     * @return
     * @throws ServiceException
     */
    Map<String,Object> findCouponAccountInfo(int accountId,int type)
        throws ServiceException;

    /**
     * 优惠码兑换优惠券
     * @param accountId
     * @param code
     * @return
     * @throws ServiceException
     */
    Map<String,Object> executeCodeChangeCoupon(int accountId,String code)
        throws ServiceException;

    /**
     * 订单使用优惠券信息
     * @param accountId
     * @param couponId
     * @return
     * @throws ServiceException
     */
    Map<String,Object> executeOrderCoupon(int couponId,int accountId)
        throws ServiceException;

}
