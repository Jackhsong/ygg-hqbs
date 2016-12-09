package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.OrderConfirmEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderConfirmDao
{
    
    /**
     * 新增订单确认
     *
     * @return
     */
    int addOrderConfirm(OrderConfirmEntity oce)
        throws DaoException;
    
    /**
     * 修改订单确认
     *
     * @return
     */
    int updateOrderConfirm(OrderConfirmEntity oce)
        throws DaoException;
    
    /**
     * 查询订单确认信息根据订单确认编号和账号id
     *
     * @return
     */
    List<OrderConfirmEntity> findConfirmByNumberAndAId(long number, int accountId)
        throws DaoException;
    
    /**
     * 查询订单确认信息根据订单确认编号和临时账号id
     *
     * @return
     */
    List<OrderConfirmEntity> findConfirmByNumberAndTId(long number, int tempAccountId)
        throws DaoException;

    /**
     * 查询订单确认信息根据订单确认编号
     *
     * @return
     */
    List<OrderConfirmEntity> findConfirmByNumber(long number)
        throws DaoException;
    
}
