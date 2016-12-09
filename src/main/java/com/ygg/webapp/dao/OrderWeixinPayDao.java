package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.OrderWeixinPayEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderWeixinPayDao
{
    
    /**
     * 替换订单微信支付
     *
     * @return
     */
    int replaceIntoOrderWeixinPay(OrderWeixinPayEntity owpe)
        throws DaoException;
    
    /**
     * 修改订单微信支付
     *
     * @return
     */
    int updateOrderWeixinPay(OrderWeixinPayEntity owpe)
        throws DaoException;
    
    /**
     * 根据支付标识获取所有订单微信支付
     *
     * @return
     */
    List<OrderWeixinPayEntity> findOrderWeixinPayByMark(String payMark)
        throws DaoException;
    
    List<OrderWeixinPayEntity> findOrderWeixinPayByOrderId(int orderId)
        throws DaoException;
}