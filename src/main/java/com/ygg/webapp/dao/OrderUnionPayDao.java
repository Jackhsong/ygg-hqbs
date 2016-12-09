package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.OrderUnionPayEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderUnionPayDao
{
    
    /**
     * 替换订单银联支付
     *
     * @return
     */
    int replaceIntoOrderUnionPay(OrderUnionPayEntity oupe)
        throws DaoException;
    
    /**
     * 修改订单阿里支付
     *
     * @return
     */
    int updateOrderUnionPay(OrderUnionPayEntity oupe)
        throws DaoException;
    
    /**
     * 根据支付标识获取所有订单阿里支付
     *
     * @return
     */
    List<OrderUnionPayEntity> findOrderUnionPayByMark(String payMark)
        throws DaoException;
    
    List<OrderUnionPayEntity> findOrderUnionPayByOrderId(int orderId)
        throws DaoException;
}