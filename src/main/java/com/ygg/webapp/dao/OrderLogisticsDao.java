package com.ygg.webapp.dao;

import java.util.Map;

import com.ygg.webapp.entity.OrderLogisticsEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderLogisticsDao
{
    
    /**
     * 新增订单物流
     *
     * @return
     */
    int addOrderLogistics(OrderLogisticsEntity ole)
        throws DaoException;
    
    /**
     *
     * @return
     */
    OrderLogisticsEntity findOrderLogisticsByOId(int orderId)
        throws DaoException;
    
    Map<String, Object> findOrderLogisticsByOrderProductRefundId(int orderProfuctRefundId)
        throws DaoException;
}