package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.OrderConfirmProductEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderConfirmProductDao
{
    
    /**
     * 新增订单确认关联商品
     *
     * @return
     */
    int addOrderConfirmProduct(int orderConfirmId, int productId, short productCount)
        throws DaoException;
    
    /**
     * 查询订单确认商品根据订单确认id
     *
     * @return
     */
    List<OrderConfirmProductEntity> findConfirmProductByConfirmId(int confirmId)
        throws DaoException;

    /**
     * 查询订单确认商品数量根据订单确认id列表和商品id
     *
     * @return
     */
    int findProductSumByConfirmIdsAndProductId(List<Integer> confirmIds, int productId)
        throws DaoException;
}
