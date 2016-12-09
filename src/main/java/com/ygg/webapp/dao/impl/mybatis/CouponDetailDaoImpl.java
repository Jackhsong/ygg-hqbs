package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("couponDetailDao")
public class CouponDetailDaoImpl extends BaseDaoImpl implements CouponDetailDao
{
    
    @Override
    public CouponDetailEntity findCouponDetailById(int couponDetailId)
        throws DaoException
    {
        // String sql = "SELECT type,scope_type,threshold,reduce FROM coupon_detail WHERE id=?";
        CouponDetailEntity cde = this.getSqlSession().selectOne("CouponDetailMapper.findCouponDetailById", couponDetailId);
        return cde;
    }
    
    @Override
    public CouponDetailEntity findCouponDetailByCouponId(int couponId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("CouponDetailMapper.findCouponDetailByCouponId", couponId);
    }
    
}
