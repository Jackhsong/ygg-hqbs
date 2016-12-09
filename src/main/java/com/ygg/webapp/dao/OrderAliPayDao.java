package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.OrderAliPayEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderAliPayDao
{
    
    /**
     * 替换订单阿里支付
     *
     * @return
     */
    int replaceIntoOrderAliPay(OrderAliPayEntity owpe)
        throws DaoException;
    
    /**
     * 修改订单阿里支付
     *
     * @return
     */
    int updateOrderAliPay(OrderAliPayEntity owpe)
        throws DaoException;
    
    /**
     * 根据支付标识获取所有订单阿里支付
     *
     * @return
     */
    List<OrderAliPayEntity> findOrderAliPayByMark(String payMark)
        throws DaoException;
    
    List<OrderAliPayEntity> findOrderAliPayByOrderId(int orderId)
        throws DaoException;
    
}