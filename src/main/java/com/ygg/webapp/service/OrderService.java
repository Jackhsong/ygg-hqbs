package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.view.OrderView;

public interface OrderService
{
    
    /**
     * 加载运费0模版,以后可以先加载所有的运费模版
     * 
     * @throws ServiceException
     */
    public void init()
        throws ServiceException;
    
    /**
     * 订单确认
     * 
     * @param request
     * @return
     */
    String confirm(String requestParams)
        throws ServiceException;
    
    /**
     * 根据accountId or TempAccountId and orderType 查询购物车的过期时间
     * 
     * @param reqeustParams
     * @return
     * @throws ServiceException
     */
    String findValidTimeByAIDORTAID(String reqeustParams)
        throws ServiceException;
    
    /**
     * 订单新增
     * 
     * @param request
     * @return
     */
    String add(String requestParams)
        throws ServiceException;
    
    /**
     * 订单付款
     * 
     * @param request
     * @return
     */
    String pay(String requestParams, HttpServletRequest request, HttpServletResponse response)
        throws Exception;
    
    /**
     * 根据购物车中的对象组装成一个Order对象生成一个定单
     * 
     * @param orderView
     * @return
     * @throws Exception
     */
    public String generateOrder(OrderView orderView)
        throws ServiceException;
    
    /**
     * 对已生成的定单，可以删除
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String removeOrder(String requestParams)
        throws ServiceException;
    
    /**
     * 列出用户的所有定单，根据传递的参数， 列出几种状态下的定单，未传款的定单，已付款的定单
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public List<OrderView> listAllOrder(String requestParams)
        throws ServiceException;
    
    /**
     * 根据用户的ID，查询定单的几个状态下的值
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String listOrderStatusCountByUserId(String requestParams)
        throws ServiceException;
    
    /**
     * 修改订单
     * 
     * @param request
     * @return
     */
    String modify(String requestParams)
        throws Exception;
    
    /**
     * 订单列表
     * 
     * @param request
     * @return
     */
    String list(String requestParams)
        throws Exception;
    
    /**
     * 订单详情
     * 
     * @param request
     * @return
     */
    String detail(String requestParams)
        throws Exception;
    
    /**
     * 查询商品的物流信息
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String logisticsDetail(String requestParams)
        throws Exception;
    
    /**
     * 付款详情
     * 
     * @param request
     * @return
     */
    String payDetail(String requestParams)
        throws Exception;
    
    /**
     * /财付通支付通知（后台通知）
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    String weixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException;
    
    /**
     * 财付通支付应答（处理回调）
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    String weixinReturnCallBack(HttpServletRequest request, HttpServletResponse response, String requestParams)
        throws ServiceException;
    
    /**
     * 在微信公众账号中请求支付接口
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    String payrequestweixin(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException;
    
    /**
     * ali同步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    public String aliReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception;

    /**
     * ali国际支付宝同步回调接口
     *
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    public String aliGlobalReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception;
    
    public String aliPayFailInWeXin(String requestParams)
        throws ServiceException;
    
    /**
     * 银联 同步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    public String unionReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception;
    
    /**
     * ali异步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    String aliNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception;

    /**
     * 国际支付宝   ali异步回调接口
     *
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    String aliGlobalNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception;
    
    /**
     * 银联异步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    String unionNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception;
    
    /**
     * 返回百度推送所需要的订单信息
     * 
     * @param orderId
     * @return
     * @throws Exception
     */
    Map<String, Object> findOrderProductInfosByOId(int orderId)
        throws Exception;
    
    /**
     * 修改定单的状态，把未付改为待发货
     * 
     * @param params
     * @return
     * @throws ServiceException
     */
    public String modifyOrderStatus(Map<String, Object> params)
        throws ServiceException;
    
    /**
     * 超过30 天自动收货
     * 
     * @throws ServiceException
     */
    public void autoConfirmReceipt()
        throws ServiceException;

    /**
     * 获取订单可用优惠券列表
     * @param params
     * @return
     * @throws Exception
     */
    Map<String,Object> getCoupon(Map<String,Object> param)
        throws Exception;
    
    
    
}
