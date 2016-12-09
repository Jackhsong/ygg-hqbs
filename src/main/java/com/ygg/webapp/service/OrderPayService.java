package com.ygg.webapp.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OrderPayService
{
    
    /**
     * 支付页面的config 配置
     * 
     * @param request
     * @param response
     * @param resquestParams
     * @return
     * @throws Exception
     */
    public String configPay(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception;
    
    /**
     * 支付前的请求参数 resquestParams 可有可无
     * 
     * @param request
     * @param response
     * @param resquestParams
     * @return
     * @throws Exception
     */
    public String requestBeforePay(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception;
    
    /**
     * 发起支付请请 resquestParams 可有可无
     * 
     * @param request
     * @param response
     * @param resquestParams
     * @return
     * @throws Exception
     */
    public String requestPay(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception;
    
    /**
     * 调用支付接口后， 前端回调接口，跳到成功或失败的页面
     * 
     * @param request
     * @param response
     * @param resquestParams 可有可无
     * @return
     * @throws Exception
     */
    public String responseReturnUrl(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception;
    
    /**
     * 调用支付接口后，后端回调接口,验证业务是否正确
     * 
     * @param request
     * @param response
     * @param resquestParams 可有可无
     * @return
     * @throws Exception
     */
    public String responseNotifyUrl(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception;
    
    /**
     * 先得到用户的openId
     * 
     * @param request
     * @param response
     * @param resquestParams
     * @return
     * @throws Exception
     */
    public String getOpenId(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception;
    
}
