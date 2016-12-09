package com.ygg.webapp.dao;

import com.ygg.webapp.entity.SellerEntity;
import com.ygg.webapp.entity.SellerExpandEntity;
import com.ygg.webapp.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface SellerDao
{
    
    /**
     * 根据id查询对应的商家信息
     *
     * @return
     */
    SellerEntity findBrandInfoById(int id)
        throws DaoException;
    
    /**
     * 根据id查询对应的商家信息
     *
     * @return
     */
    SellerEntity findOrderSellerInfoById(int id)
        throws DaoException;
    
    /**
     * 插入订单商家
     * 
     * @throws Exception
     */
    int insertOrderSellerInfo(SellerEntity seller)
        throws DaoException;
    
    /**
     * 根据商家Id查询商家信息
     * @param sellerId
     * @return
     */
    SellerEntity findSellerById(int sellerId)
        throws DaoException;
    
    /**
     * 根据商家ID查找商家扩展信息
     * @param sellerId
     * @return
     * @throws DaoException
     */
    SellerExpandEntity findSellerExpandBySellerId(int sellerId)
        throws DaoException;

    /**
     * 查询不参与活动的商家
     * @return
     * @throws Exception
     */
    List<Integer> findAllSellerActivitiesBlacklistId()
            throws DaoException;

    /**
     * 查询特定的邮费的商家
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findAllSellerPosgageBlacklistId()
            throws DaoException;
}