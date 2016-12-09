package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CouponDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("couponDao")
public class CouponDaoImpl extends BaseDaoImpl implements CouponDao
{
    
    @Override
    public CouponEntity findCouponById(int couponId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("CouponMapper.findCouponById", couponId);
    }
    
}
