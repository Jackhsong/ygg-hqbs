package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.ProductBaseEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.exception.DaoException;

public interface ProductDao
{
    
    /**
     * 根据id查询对应的商品信息
     *
     * @return
     */
    ProductEntity findProductInfoById(int id)
        throws DaoException;
    
    /**
     * 查所有商品的ID
     * 
     * @return
     * @throws DaoException
     */
    List<Integer> findAllProductIds()
        throws DaoException;
    
    /**
     * 根据id查询对应的商品部分信息
     *
     * @return
     */
    ProductEntity findProductPartById(int id)
        throws DaoException;
    
    /**
     * 查询当前展示的今日最热商品信息
     * 
     * @return
     */
    List<Map<String, Object>> findDisplayHotProduct()
        throws DaoException;
    
    /**
     * 查找商品格格说头像
     * @param gegeImageId
     * @return
     * @throws DaoException
     */
    String findGegeImageById(int gegeImageId)
        throws DaoException;
    
    /**
     * 根据商品ID查询格格福利商品
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    Map<String, Object> findProductWelfareByProductId(int productId)
        throws Exception;
    
    ProductBaseEntity findProductBaseById(int productBaseId)
        throws DaoException;
    
    /**
     * 根据基本商品Id查询配送地区信息
     * @param productBaseId
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findDeliverAreaInfosByBpId(int productBaseId)
        throws DaoException;
    
}