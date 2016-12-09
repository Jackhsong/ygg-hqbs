package com.ygg.webapp.dao;

import com.ygg.webapp.entity.OrderReceiveAddressEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderReceiveAddressDao
{
    
    /**
     * 新增订单地址
     *
     * @return
     */
    int addAddress(OrderReceiveAddressEntity orae)
        throws DaoException;
    
    /**
     * 根据id查找订单地址信息
     *
     * @return
     */
    OrderReceiveAddressEntity findAddressById(int id)
        throws DaoException;
    
}