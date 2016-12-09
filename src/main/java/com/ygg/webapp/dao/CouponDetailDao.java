package com.ygg.webapp.dao;

import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.exception.DaoException;

public interface CouponDetailDao
{
    /**
     * 根据优惠详情id查询优惠详情信息
     * 
     * @return
     */
    CouponDetailEntity findCouponDetailById(int couponDetailId)
        throws DaoException;
    
    /**
     * 根据优惠券Id查询优惠券详情信息
     * @param couponId
     * @return
     * @throws DaoException
     */
    CouponDetailEntity findCouponDetailByCouponId(int couponId)
        throws DaoException;
}
