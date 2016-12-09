package com.ygg.webapp.dao;

import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.exception.DaoException;

public interface CouponDao
{
    
    CouponEntity findCouponById(int couponId)
        throws DaoException;
    
}
