package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderProductRefundEntity;
import com.ygg.webapp.exception.DaoException;

public interface OrderProductRefundDao
{
    
    public int queryOrderRefundCountByAccountId(int accountId)
        throws DaoException;
    
    public int insertOrderProductRefund(OrderProductRefundEntity ope)
        throws DaoException;
    
    public int updateOrderProductRefundInfo(OrderProductRefundEntity ope)
        throws DaoException;
    
    public OrderProductRefundEntity queryOrderProductRefundById(int orderProductRefundId)
        throws DaoException;
    
    public boolean queryOrderProductExists(OrderProductRefundEntity oprfe)
        throws DaoException;
    
    /**
     * 根据orderProductId 查退款的状态 & ID
     * 
     * @param orderProductId
     * @return
     * @throws DaoException
     */
    public Map<String, Object> queryOPRInfosByOrderProductId(Map<String, Object> mapParams)
        throws DaoException;
    
    public OrderProductEntity queryOrderProductById(int orderProductId)
        throws DaoException;
    
    public List<OrderProductRefundEntity> queryAllOrderProductRefund(int accountId, int page)
        throws DaoException;
    
    public boolean isOrderProductRefundExist(int id)
        throws DaoException;
    
    public int updateOrderProductRefund(Map<String, Object> para)
        throws Exception;
    
    public String findOrderProductRefundIdByPidAndOidAndAid(String productId, String orderId, String accountId)
        throws DaoException;
    
    public String findOrderProductIdByPidAndOid(String productId, String orderId)
        throws DaoException;

    List<Map<String, Object>> findRefundsByOrderId(int orderId)
        throws DaoException;
    
    public String findOrderIdByOrderProductRefundId(String orderProductRefundId)
            throws DaoException;
}
