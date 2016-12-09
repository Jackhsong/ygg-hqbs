package com.ygg.webapp.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.service.OrderPayService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.SpringBeanUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;

public class UserLoginFilter implements Filter
{
    private String encode = "UTF-8";// 默认UTF-8编码
    
    private Logger log = Logger.getLogger(UserLoginFilter.class);
    
    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        String encoding = filterConfig.getInitParameter("encode");
        if (encoding != null)
        {
            encode = encoding;
        }
    }
    
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        
        String isQqbs = "";
        String isYw = "";
        // log.info(requestURL + "?" + request.getQueryString());
        if (request.getParameter(("quanqiubushou")) == null)
        {
            isQqbs = request.getSession().getAttribute("quanqiubushou") != null ? request.getSession().getAttribute("quanqiubushou").toString() : "0";
        }
        else if (request.getParameter(("yanwang")) == null)
        {
            isYw = request.getSession().getAttribute("yanwang") != null ? request.getSession().getAttribute("yanwang").toString() : "0";
        }
        else
        {
            isQqbs = request.getParameter("quanqiubushou");
            isYw = request.getParameter("yanwang");
        }
        
        Cookie[] cookies = request.getCookies();
        AccountView currAv = SessionUtil.getCurrentUser(request.getSession());
        if (currAv == null && cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName() != null && cookie.getName().equals("userinfo"))
                {
                    String s = cookie.getValue();
                    try
                    {
                        JSONObject j = JSON.parseObject(s, JSONObject.class);
                        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
                        // AccountEntity ae = ad.findAccountByPwd(cookie.getValue());
                        AccountEntity ae = ad.findByNameAndType(j.getString("name"), j.getByte("type"));
                        if (ae != null)
                        {
                            AccountView av = new AccountView();
                            BeanUtils.copyProperties(ae, av);
                            SessionUtil.addUserToSession(request.getSession(), av);
                            break;
                        }
                    }
                    catch (Exception e)
                    {
                        
                        // //移除session 和 cookie 中的数据
                        SessionUtil.removeUserToSession(request.getSession());
                        SessionUtil.removeCurrentOrderConfirmId(request.getSession());
                        SessionUtil.removeCurrentSelectedAddres(request.getSession());
                        SessionUtil.removeWeiXinOpenId(request.getSession());
                        SessionUtil.removeWeiXinCode(request.getSession());
                        request.getSession().removeAttribute("userimage");
                        Cookie cook = new Cookie("userinfo", null);
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                        break;
                    }
                    
                }
            }
        }
        
        boolean isMicroMessage = CommonUtil.isWeiXinVersionMoreThan5(request.getHeader("User-Agent"));
        String usercode = request.getParameter("code");
        String openId = SessionUtil.getCurrentWeiXinOpenId(request.getSession());
        boolean toAuthorize = true;
        String requestURL = request.getRequestURL().toString();
        
        // 移除全球代言人和燕网的信息
        if (requestURL.contains("quanqiubushou"))
        {
            request.getSession().removeAttribute("yanwang");
        }
        else if (requestURL.contains("yanwang"))
        {
            request.getSession().removeAttribute("quanqiubushou");
        }
        else if ((requestURL.contains("m.gegejia.com/ygg/index") || requestURL.endsWith("m.gegejia.com") || requestURL.contains("m.gegejia.com/special")
            || requestURL.contains("/product/single") || requestURL.contains("/product/msingle/") || requestURL.contains("/cnty/toac"))
            && !requestURL.contains(".quanqiubushou") && !requestURL.contains(".yanwang"))
        {
            if (isQqbs.equals("1") || isYw.equals("1"))
            {
                SessionUtil.removeUserToSession(request.getSession());
            }
            request.getSession().removeAttribute("quanqiubushou");
            request.getSession().removeAttribute("yanwang");
        }
        
        if ((requestURL.contains("group") || requestURL.contains("test.gegejia.com") || requestURL.contains("/temp/activity/") || requestURL.contains("/pages/") || requestURL.contains("/activity/packet"))
            && !requestURL.contains("test.gegejia.com/ygg/order/orderdetail"))
        {
            toAuthorize = false;
        }
        
        if ((openId == null || openId.equals("")) && isMicroMessage && toAuthorize)
        {
            if (usercode == null || usercode.equals("")) // 如果是weixin浏览器，只跳一次 && isRedirectMicroMessage
            {
                
                String redirectUrl = CommonUtil.getRedirectUrl(request.getRequestURL().toString());
                
                String redirect_url = URLEncoder.encode(redirectUrl + "?" + request.getQueryString(), "UTF-8");
                
                String appid2 = "";
                String url = "";
                if (isQqbs.equals("1"))
                {
                    appid2 = "wx50718ec381121bd5";
                    redirect_url += URLEncoder.encode("&quanqiubushou=1", "UTF-8");
                    url =
                        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid2 + "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8")
                            + "&response_type=code&scope=snsapi_base&quanqiubushou=1&state=abcdefg123#wechat_redirect";
                }
                else if (isYw.equals("1"))
                {
                    appid2 = "wx8d8553968c1508a0";
                    redirect_url += URLEncoder.encode("&yanwang=1", "UTF-8");
                    url =
                        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid2 + "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8")
                            + "&response_type=code&scope=snsapi_base&yanwang=1&state=abcdefg123#wechat_redirect";
                }
                else
                {
                    appid2 = CommonConstant.APPID;
                    redirect_url += URLEncoder.encode("&yanwang=0", "UTF-8");
                    url =
                        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid2 + "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8")
                            + "&response_type=code&scope=snsapi_base&quanqiubushou=0&yanwang=0&state=abcdefg123#wechat_redirect";
                }
                response.sendRedirect(url);
                // isRedirectMicroMessage = false ;
                return;
            }
            if (isQqbs.equals("1"))
            {
                SessionUtil.addWeiXinCodeQQBS(request.getSession(), usercode);
                log.info("1");
            }
            else if (isYw.equals("1"))
            {
                SessionUtil.addWeiXinCodeQQBS(request.getSession(), usercode);
                log.info("3");
            }
            else
            {
                SessionUtil.addWeiXinCode(request.getSession(), usercode);
                log.info("2");
            }
            
            if (usercode != null)
            {
                String resquestParams = "{'code':'" + usercode + "'}";
                OrderPayService orderPayService = SpringBeanUtil.getBean("orderPayService", OrderPayService.class);
                try
                {
                    openId = orderPayService.getOpenId(request, response, resquestParams);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
                if (isQqbs.equals("1"))
                {
                    SessionUtil.addWeiXinOpenIdQQBS(request.getSession(), openId);
                    log.info("3");
                }
                else if (isYw.equals("1"))
                {
                    SessionUtil.addWeiXinOpenIdQQBS(request.getSession(), openId);
                    log.info("6");
                }
                else
                {
                    SessionUtil.addWeiXinOpenId(request.getSession(), openId);
                    log.info("4");
                }
                
            }
        }
        
        String fxvalue = (request.getParameter(CommonConstant.OrderSoucrChannelKey) == null ? "" : request.getParameter(CommonConstant.OrderSoucrChannelKey).trim());
        if (!fxvalue.equals(""))
        {
            if (fxvalue.length() > CommonConstant.OrderSoucrChannelKeyLength)
                fxvalue = fxvalue.substring(0, CommonConstant.OrderSoucrChannelKeyLength);
            response.addCookie(CommonUtil.addCookie(CommonConstant.OrderSoucrChannelKey, fxvalue, 60 * 60 * 24 * 15));
        }
        chain.doFilter(request, response);
        
    }
    
    public void destroy()
    {
        
    }
    
}
