package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.exception.DaoException;

public interface ProductCommonDao
{
    
    /**
     * 查商品的买出数量
     * 
     * @param productId
     * @return
     * @throws DaoException
     */
    int findProductSellCountById(int productId)
        throws DaoException;
    
    public List<Map<String, Object>> findProductSellCountByIds(List<Integer> productId)
        throws Exception;
    
    /**
     * 根据商品id查询对应的商品常用信息
     *
     * @return
     */
    ProductCommonEntity findProductCommonInfoById(int productId)
        throws DaoException;
    
    /**
     * 根据商品id列表查询对应的商品常用信息列表
     *
     * @return
     */
    List<ProductCommonEntity> findProductCommonInfoById(List<Integer> productIds)
        throws DaoException;
    
    /**
     * 根据商品id更新对应的商品卖出数量信息
     *
     * @return
     */
    int updateSellCountById(int productId, int sellCount)
        throws DaoException;
    
}