package com.ygg.webapp.dao;

import com.ygg.webapp.entity.OrderProductRefundLogisticsEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderProductRefundLogisticDao
{
    
    public int insertOrderProductRefundLogistic(OrderProductRefundLogisticsEntity oprfe)
        throws DaoException;
    
    OrderProductRefundLogisticsEntity queryOrderProductRefundLogisticByOId(int orderProductFrefundId)
        throws DaoException;
    
}
