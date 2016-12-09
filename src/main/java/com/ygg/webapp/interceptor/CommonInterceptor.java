package com.ygg.webapp.interceptor;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.service.wechat.WeChatService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;

public class CommonInterceptor extends HandlerInterceptorAdapter
{
    private static final Logger logger = Logger.getLogger(CommonInterceptor.class);
    
    private List<String> excludeUrls;
    
    private String basePath;
    
    @Resource
    private WeChatService weChatService;
    
    public List<String> getExcludeUrls()
    {
        return excludeUrls;
    }
    
    public void setExcludeUrls(List<String> excludeUrls)
    {
        this.excludeUrls = excludeUrls;
    }
    
    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller 然后进入拦截器链, 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        try
        {
            String fxvalue = (request.getParameter(CommonConstant.OrderSoucrChannelKey) == null ? "" : request.getParameter(CommonConstant.OrderSoucrChannelKey).trim());
            if (!fxvalue.equals(""))
            {
                response.addCookie(CommonUtil.addCookie(CommonConstant.OrderSoucrChannelKey, fxvalue, 60 * 60 * 24 * 15));
            }
            
            // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            // log.info("==============执行顺序: 1、preHandle================");
            String requestUri = request.getRequestURI();
            String contextPath = request.getContextPath();
            String url = requestUri.substring(contextPath.length());
            
            // String completeUrl = request.getScheme() + "://" + request.getServerName() + ":" +
            // request.getServerPort() + requestUri + "/";
            int port = request.getServerPort();
            basePath = null;
            // 微信redirect_url参数中端口号不能带，目前也只支持80端口的回调
            if (port == 80)
            {
                basePath = request.getScheme() + "://" + request.getServerName() + contextPath;
            }
            else
            {
                // 目前不支持非80端口的回调
                basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
            }
            // 过滤非拦截url，在web.xml中配置
            if (StringUtils.isNotEmpty(url) && excludeUrls.contains(url))
            {
                // logger.info("不需要拦截url="+url);
                return true;
            }
            // 如果要访问的资源是不需要验证的
            else if (url.indexOf("/resources") > -1)
            {
                return true;
            }
      
            // 不拦截区域相关的接口
            else if (url.indexOf("/common/area/") == 0)
            {
                return true;
            }
            else
            {
                // logger.error("正式拦截开始");
                HttpSession session = request.getSession();
                
                // AccountView av = SessionUtil.getCurrentUser(session);
                
                QqbsAccountEntity qqbsAccount = SessionUtil.getQqbsAccountFromSession(session);
                
                String test_default_user = YggWebProperties.getInstance().getProperties("test_default_user");
                String appid = CommonConstant.APPID;
                String secret = CommonConstant.APPSECRET;
                // 本地测试用户配置
                if ("true".equals(test_default_user))
                {
                    JSONObject jsSdk = new JSONObject();
                    jsSdk.put("appId", appid);
                    jsSdk.put("timestamp", "1");
                    jsSdk.put("noncestr", "1");
                    jsSdk.put("signature", "1");
                    // 分享出去的链接
                    String shareUrl = basePath + url;
                    String indexUrl = basePath + "/hqbsHomeInfo/getHomeInfo";
                    if (qqbsAccount != null)
                    {
                        shareUrl += "?taccountId=" + qqbsAccount.getAccountId();
                        indexUrl += "?taccountId=" + qqbsAccount.getAccountId();
                    }
                    logger.error("indexUrl=" + indexUrl);
                    jsSdk.put("sharePath", shareUrl);
                    jsSdk.put("indexUrl", indexUrl);
                    jsSdk.put("shareTitle", CommonConstant.DEFAULT_SHARETITLE);
                    jsSdk.put("shareContent", CommonConstant.DEFAULT_SHARECONTENT);
                    
                    jsSdk.put("shareImage", "http://zuoan.waibao58.com/ygg-hqbs/scripts/images/logo-4.png");
                    request.setAttribute("jsSdk", jsSdk);
                    if (qqbsAccount == null)
                    {
                        // AccountView av = new AccountView();
                        // av.setId(554721);
                        // av.setName("oZ31Osw1cX2zt8Co_RRpwZIFnZug");
                        // av.setNickname("游客");
                        // av.setImage("http://yangege.b0.upaiyun.com/brand/11e22fb69fdc7.jpg");
                        //
                        // qqbsAccount = new QqbsAccountEntity();
                        // qqbsAccount.setOpenId("oZ31Osw1cX2zt8Co_RRpwZIFnZug");
                        // qqbsAccount.setAccountId(554721);
                        // qqbsAccount.setId(111548);
                        // qqbsAccount.setNickName("游客");
                        // qqbsAccount.setImage("http://yangege.b0.upaiyun.com/brand/11e22fb69fdc7.jpg");
                        // //保存基本用户信息
                        // SessionUtil.addUserToSession(session, av);
                        // //左岸城堡用户信息
                        // SessionUtil.addQqbsAccountToSession(session, qqbsAccount);
                        // //保存openid
                        // SessionUtil.addWeiXinOpenId(session, appid);
                        // return true;
                        AccountView av = new AccountView();
                        av.setId(2354309);
                        av.setName("oDwiswRDWCoL_th9Bh41CPqqe3JE");
                        av.setNickname("游客");
                        av.setImage("http://www.cilu.com.cn/climg/LOGO/logo_01.jpg");
                        
                        qqbsAccount = new QqbsAccountEntity();
                        qqbsAccount.setOpenId("oDwiswRDWCoL_th9Bh41CPqqe3JE");
                        qqbsAccount.setAccountId(2354309);
                        qqbsAccount.setId(1538433);
                        qqbsAccount.setNickName("游客");
                        qqbsAccount.setHasPersistentQRCode(1);
                        qqbsAccount.setImage("http://www.cilu.com.cn/climg/LOGO/logo_01.jpg");
                        // 保存基本用户信息http://www.cilu.com.cn/climg/LOGO/logo_01.jpg
                        SessionUtil.addUserToSession(session, av);
                        // 左岸城堡用户信息
                        SessionUtil.addQqbsAccountToSession(session, qqbsAccount);
                        // 保存openid
                        SessionUtil.addWeiXinOpenId(session, appid);
                        return true;
                    }
                    else
                    {
                        return true;
                    }
                }
                else if ("false".equals(test_default_user))
                {
                    // 当前页面链接,用于签名使用:签名使用的链接必须是当前页面,否则签名失效
                    String path = basePath + url;
                    if (StringUtils.isNotEmpty(request.getQueryString()))
                    {
                        path += "?" + request.getQueryString();
                    }
                    // logger.error("当前页面链接path="+path);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("timestamp", String.valueOf((new Date().getTime() / 1000)));
                    map.put("noncestr", CommonUtil.getUUID());
                    map.put("jsapi_ticket", WeixinMessageDigestUtil.getTicket(appid, secret));
                    map.put("url", path);
                    map.put("signature", CommonUtil.getJsApiTicketSign_V2(map));
                    
                    JSONObject jsSdk = new JSONObject();
                    jsSdk.put("appId", appid);
                    jsSdk.put("timestamp", map.get("timestamp"));
                    jsSdk.put("noncestr", map.get("noncestr"));
                    jsSdk.put("signature", map.get("signature"));
                    // 分享出去的链接
                    String shareUrl = basePath + url;
                    String indexUrl = basePath + "/hqbsHomeInfo/getHomeInfo";
                    if (qqbsAccount != null)
                    {
                        shareUrl += "?taccountId=" + qqbsAccount.getAccountId();
                        indexUrl += "?taccountId=" + qqbsAccount.getAccountId();
                    }
                    jsSdk.put("sharePath", shareUrl);
                    jsSdk.put("indexUrl", indexUrl);
                    jsSdk.put("shareTitle", CommonConstant.DEFAULT_SHARETITLE);
                    jsSdk.put("shareContent", CommonConstant.DEFAULT_SHARECONTENT);
                    
                    jsSdk.put("shareImage", "http://zuoan.waibao58.com/ygg-hqbs/scripts/images/logo-4.png");
                    request.setAttribute("jsSdk", jsSdk);
                    // logger.error("分享出去的链接shareUrl="+shareUrl);
                    // 正式环境配置
                    if (qqbsAccount == null)
                    {
                        if (StringUtils.isNotEmpty(request.getQueryString()))
                        {
                            url += "?" + request.getQueryString();
                        }
                        // 以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）
                        // String scope = "snsapi_base";
                        // 以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息
                        String scope = "snsapi_userinfo";
                        String state = URLEncoder.encode(url, "UTF-8");
                        // logger.error("state="+state);
                        String redirect_url = basePath + "/wechatOauth";
                        String wechatUrl =
                            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + redirect_url + "&response_type=code&scope=" + scope
                                + "&state=" + state + "#wechat_redirect";
                        // logger.error("wechatUrl="+wechatUrl);
                        // logger.error("拦截器：获取用户code state=" + URLDecoder.decode(state) + " redirect_url=" +
                        // redirect_url);
                        response.sendRedirect(wechatUrl);
//                        response.sendRedirect("homepage/load");
                        
                    }
                    else
                    {
                        // logger.error("拦截器：用户session存在 account=" + JSON.toJSONString(qqbsAccount) + " url=" + url);
                        return true;
                    }
                }
                
            }
        }
        catch (Exception e)
        {
            
            logger.error(e.getMessage(), e);
        }
        // logger.error("进入拦截器结束");
        ModelAndView modelAndView = new ModelAndView("homepage/load");
        return false;
    }
    
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
        throws Exception
    {
        
        request.setAttribute("basePath", basePath);
        super.postHandle(request, response, handler, modelAndView);
        // log.info("==============执行顺序: 2、postHandle================");
        
    }
    
    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     *
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception
    {
        super.afterCompletion(request, response, handler, ex);
        // log.info("==============执行顺序: 3、afterCompletion================");
        
    }
    
}
