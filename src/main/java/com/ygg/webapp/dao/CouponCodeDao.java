package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.CouponCodeEntity;
import com.ygg.webapp.exception.DaoException;

public interface CouponCodeDao
{
    
    /**
     * 根据id查询优惠券详情
     * 
     * @return
     */
    Map<String, Object> findCouponById(int id)
        throws DaoException;
    
    /**
     * 根据优惠码查询优惠码详情
     * 
     * @return
     */
    Map<String, Object> findCouponCodeDetailByCode(String code)
        throws DaoException;
    
    /**
     * 根据用户id和优惠码详情id更新为已使用
     * 
     * @return
     */
    int updateCouponCodeDetail2Used(int accountId, int couponAccountId, int couponCodeDetailId)
        throws DaoException;
    
    /**
     * 根据优惠码id查询优惠码信息
     * 
     * @return
     */
    CouponCodeEntity findInfoById(int couponCodeId)
        throws DaoException;
    
    /**
     * 根据通用优惠码查询优惠码信息
     * 
     * @return
     */
    CouponCodeEntity findInfoByCommonCode(String commonCode)
        throws DaoException;
    
    /**
     * 根据账号id和优惠码id查询优惠码使用条数
     * 
     * @return
     */
    int findCodeCountByAidAndId(int accountId, int couponCodeId)
        throws DaoException;
    
    /**
     * 根据账号id和优惠码id查询通用优惠码使用条数
     * 
     * @return
     */
    int findCommonCodeCountByAidAndId(int accountId, int couponCodeId)
        throws DaoException;
    
    /**
     * 新增用户优惠券通用记录
     * 
     * @return
     */
    int addCouponCodeCommon(int accountId, int couponCodeId, int couponAccountId)
        throws DaoException;

    /**
     * 根据优惠码ID查询关联的优惠券列表
     * @param couponCodeId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findCouponCodeGiftBag(int couponCodeId)
        throws DaoException;
}
