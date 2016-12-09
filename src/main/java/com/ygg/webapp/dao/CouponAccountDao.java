package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.exception.DaoException;

public interface CouponAccountDao
{
    /**
     * 新增用户优惠券
     * 
     * @return
     */
    int addCouponAccount(CouponAccountEntity cae)
        throws DaoException;
    
    /**
     * 根据用户id和优惠券id查询优惠券信息
     * 
     * @return
     */
    CouponAccountEntity findCouponsByAidAndId(int id, int accountId)
        throws DaoException;
    
    /**
     * 根据用户id查询未使用的优惠券
     * 
     * @return
     */
    List<CouponAccountEntity> findUnusedCouponsByAid(int accountId)
        throws DaoException;
    
    /**
     * 根据用户id查询已使用的优惠券
     * 
     * @return
     */
    List<CouponAccountEntity> findUsedCouponsByAid(int accountId)
        throws DaoException;
    
    /**
     * 根据用户id查询已过期的优惠券
     * 
     * @return
     */
    List<CouponAccountEntity> findExpiredCouponsByAid(int accountId)
        throws DaoException;
    
    /**
     * 根据id更新为已使用
     * 
     * @return
     */
    int updateCouponAccount2Used(int id)
        throws DaoException;
    
    /**
     * 根据优惠券id，用户Id，类型查找是有优惠券
     * @param accountId
     * @param couponId
     * @param type
     * @return
     * @throws DaoException
     */
    CouponAccountEntity findCouponInfoBycidAndType(int accountId, int couponId, int type)
        throws DaoException;
    
    /**
     * 根据id将优惠券更新为未使用
     *
     * @return
     */
    int updateCouponAccount2UnUsed(int id)
        throws DaoException;
    
    /**
     * 根据用户Id、优惠券Id、优惠券来源查找用户领券记录
     * @param accountId
     * @param couponId
     * @param sourceType
     * @return
     * @throws DaoException
     */
    List<CouponAccountEntity> findAllCouponInfoBycidAndType(int accountId, int couponId, int sourceType)
        throws DaoException;
    
    /**
     * 根据用户优惠券Id查找账户优惠券信息
     * @param id
     * @return
     * @throws DaoException
     */
    CouponAccountEntity findCouponAccountById(int id)
        throws DaoException;
}
