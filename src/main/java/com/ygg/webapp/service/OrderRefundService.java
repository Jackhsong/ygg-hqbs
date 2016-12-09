package com.ygg.webapp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.view.OrderProductRefundView;

public interface OrderRefundService
{
    
    public OrderProductRefundView getOrderProductRefundViewById(int orderProductRefundId)
        throws ServiceException;
    
    /**
     * 查询退款定单的总数量
     * 
     * @param request
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    public String getOrderRefundCount(String requestParams)
        throws ServiceException;
    
    /**
     * 退款退货的首页
     * 
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    public String getOrderRefundIndex(HttpServletRequest request, String requestParams, HttpServletResponse response)
        throws ServiceException;
    
    /**
     * 获取退款订单信息
     * @param response 
     * @param request 
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String getOrderInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException;
    
    /**
     * 获取退款订单信息
     * @param response 
     * @param request 
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String getSubmitApplicationInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException;
    
    /**
     * 提交退款申请
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String submitApplication(String resquestParams)
        throws ServiceException;
    
    /**
     * 退款详情
     * @param response 
     * @param request 
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String refundInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException;
    
    /**
     * 退货详情
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String returnGoodInfo(String resquestParams)
        throws ServiceException;
    
    /**
     * 确认退货
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String submitReturnGood(String resquestParams)
        throws ServiceException;
    
    /**
     * 退货退款进度查询
     * @param request 
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String getReturnProcessInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException;
    
    /**
     * 更具订单id和accountId查询退款退货Id
     * @param productId
     * @param orderId
     * @param accountId
     * @return
     * @throws ServiceException
     */
    public String findOrderProductRefundIdByPidAndOidAndAid(String productId, String orderId, String accountId)
        throws ServiceException;
    
    /**
     * 根据订单id和商品Id查询订单商品Id
     * @param productId
     * @param orderId
     * @return
     * @throws ServiceException
     */
    public String findOrderProductIdByPidAndOid(String productId, String orderId)
        throws ServiceException;

    /**
     * 取消退款
     * @param refundId
     * @return
     * @throws ServiceException
     */
    public String cancelRefund(int refundId)
            throws ServiceException;
}
