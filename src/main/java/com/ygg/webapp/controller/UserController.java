package com.ygg.webapp.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.sdk.alipaylogin.config.AlipayConfig;
import com.ygg.webapp.sdk.alipaylogin.util.AlipayNotify;
import com.ygg.webapp.sdk.alipaylogin.util.AlipaySubmit;
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.TempAccountService;
import com.ygg.webapp.service.TempShoppingCartService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;

//import weibo4j.Users;
//import weibo4j.model.User;

/**
 *
 * @author lihc
 *         
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController
{
    private static final String[] NOTIFY_MOBILE_NUMBER = {"18664573290"};
    
    Logger logger = Logger.getLogger(UserController.class);
    
    @Resource(name = "accountService")
    private AccountService accountService;
    
    @Resource(name = "shoppingCartService")
    private ShoppingCartService shoppingCartService;
    
    @Resource(name = "tempShoppingCartService")
    private TempShoppingCartService tempShoppingCartService;
    
    @Resource(name = "tempAccountService")
    private TempAccountService tempAccountService;
    
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    /**
     * 登陆 ordertype 是定单确认时需要的一个参数， 当用户没有login时，先login时 要带此参数 1表示正常定单 2是从结算时login时的参数
     * 
     * @return
     */
    @RequestMapping(value = "/tologin", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView tologin(HttpServletRequest request, @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype)
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/login");
        
        mv.addObject("ordertype", ordertype);
        if (CommonUtil.isWeiXinBrower(request))
            mv.addObject("isWeiXinBrower", "1");
        else
            mv.addObject("isWeiXinBrower", "0");
            
        String returnBackUrl = request.getHeader("referer");
        request.getSession().setAttribute("returnBackUrl", returnBackUrl);
        return mv;
    }
    
    /**
     * 使用QQ login
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/toqqlogin/{ordertype}")
    public void toQQConnectLogin(HttpServletRequest request, HttpServletResponse response, @PathVariable("ordertype") String ordertype)
        throws Exception
    {
        response.setContentType("text/html;charset=utf-8");
        try
        {
            request.getSession().setAttribute("qqloginordertype", ordertype);
            response.sendRedirect(new Oauth().getAuthorizeURL(request));
        }
        catch (QQConnectException e)
        {
            logger.error("toQQConnectLogin error", e);
        }
    }
    
    /**
     * QQ login后的回调
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/qqcallback")
    public ModelAndView toQQCallBackLogin(@CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid, HttpServletRequest request,
        HttpServletResponse response)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/mycenter/show"); // 成功 进入到个人中心
        String ordertype = "";
        try
        {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken = null, openID = null;
            long tokenExpireIn = 0L;
            
            if (accessTokenObj.getAccessToken().equals(""))
            {
                this.logger.error("QQ联合login---------------没有获取到响应参数");
            }
            else
            {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                // out.println("<p> start -----------------------------------利用获取到的accessToken,openid 去获取用户在Qzone的昵称等信息
                // ---------------------------- start </p>");
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                String userImageUrl = userInfoBean.getAvatar().getAvatarURL100();
                String loginstr = "{'name':'" + openID + "','type':'2','password':'','nickname':'" + userInfoBean.getNickname() + "','userImageUrl':'" + userImageUrl + "'}";
                ordertype = (request.getSession().getAttribute("qqloginordertype") == null ? "1" : (String)request.getSession().getAttribute("qqloginordertype"));
                processLoginCallBack(loginstr, mv, request, ygguuid, ordertype, response);
                request.getSession().setAttribute("userimage", userImageUrl); // 用户图象
            }
        }
        catch (QQConnectException e)
        {
            this.logger.error("很抱歉，我们没能正确获取到您的信息，原因是： ", e);
        }
        
        if (ordertype != null && ordertype.equals("2")) // 从定单结算中来
        {
            mv.setViewName("redirect:/order/confirm/2");
        }
        else
        {
            String returnBackUrl = (String)request.getSession().getAttribute("returnBackUrl");
            // // 按需加上判断
            // if (returnBackUrl.indexOf("m.gegejia.com") == -1)
            // {
            // returnBackUrl = null;
            // }
            if (returnBackUrl != null)
            {
                request.getSession().setAttribute("returnBackUrl", null);
                mv.setViewName("redirect:" + returnBackUrl);
            }
        }
        
        request.getSession().removeAttribute("qqloginordertype");
        return mv;
    }
    
    /**
     * 使用alipay login
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/toalipaylogin/{ordertype}")
    public void toAlipayConnectLogin(HttpServletRequest request, HttpServletResponse response, @PathVariable("ordertype") String ordertype)
        throws Exception
    {
        response.setContentType("text/html;charset=utf-8");
        try
        {
            request.getSession().setAttribute("alipayloginordertype", ordertype);
            
            // 目标服务地址
            String target_service = "user.auth.quick.login";
            // 必填，页面跳转同步通知页面路径
            String return_url = YggWebProperties.getInstance().getProperties("ali_login_return_url");
            // 需http://格式的完整路径，不允许加?id=123这类自定义参数
            
            // 防钓鱼时间戳
            String anti_phishing_key = AlipaySubmit.query_timestamp();
            // 若要使用请调用类文件submit中的query_timestamp函数
            
            // 客户端的IP地址
            String exter_invoke_ip = "";
            // 非局域网的外网IP地址，如：221.0.0.1
            
            // ////////////////////////////////////////////////////////////////////////////////
            
            // 把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", "alipay.auth.authorize");
            sParaTemp.put("partner", AlipayConfig.partner);
            sParaTemp.put("_input_charset", AlipayConfig.input_charset);
            sParaTemp.put("target_service", target_service);
            sParaTemp.put("return_url", return_url);
            sParaTemp.put("anti_phishing_key", anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", exter_invoke_ip);
            
            // 建立请求
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");
            response.getOutputStream().write(sHtmlText.getBytes("UTF-8"));
            response.getOutputStream().flush();
        }
        catch (Exception e)
        {
            logger.error("toAlipayConnectLogin error", e);
        }
        finally
        {
            response.getOutputStream().close();
        }
    }
    
    /**
     * alipay login后的回调
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/alipaycallback")
    public ModelAndView toAliPayCallBackLogin(@CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid, HttpServletRequest request,
        HttpServletResponse response)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/mycenter/show"); // 成功 进入到个人中心
        String ordertype = "";
        try
        {
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
            {
                String name = (String)iter.next();
                String[] values = (String[])requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++)
                {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            
            // 支付宝用户号
            String userId = new String(request.getParameter("user_id").getBytes("ISO-8859-1"), "UTF-8");
            
            // 授权令牌
            // String token = new String(request.getParameter("token").getBytes("ISO-8859-1"), "UTF-8");
            
            String realName = new String(request.getParameter("real_name").getBytes("ISO-8859-1"), "UTF-8");
            
            // 计算得出通知验证结果
            boolean verify_result = AlipayNotify.verify(params);
            
            if (verify_result)// 验证成功
            {
                String loginstr = "{'name':'" + userId + "','type':'" + CommonEnum.ACCOUNT_LOGIN_TYPE.ALIPAY.getValue() + "','password':'','nickname':'" + realName + "'}";
                ordertype = (request.getSession().getAttribute("alipayloginordertype") == null ? "1" : (String)request.getSession().getAttribute("alipayloginordertype"));
                processLoginCallBack(loginstr, mv, request, ygguuid, ordertype, response);
                // request.getSession().setAttribute("userimage", ""); // 用户图象
            }
            else
            {
                logger.error("alipay联合login---------------验证失败");
            }
        }
        catch (Exception e)
        {
            this.logger.error("alipay---login--失败!", e);
        }
        
        logger.info("ordertype" + ordertype);
        if (ordertype != null && ordertype.equals("2")) // 从定单结算中来
        {
            mv.setViewName("redirect:/order/confirm/2");
        }
        
        request.getSession().removeAttribute("alipayloginordertype");
        return mv;
    }
    
    /**
     * 联合login的回调业务逻辑处理方法
     * 
     * @param loginstr
     * @param mv
     * @param request
     * @param ygguuid
     * @return
     * @throws Exception
     */
    public AccountView processLoginCallBack(String loginstr, ModelAndView mv, HttpServletRequest request, String ygguuid, String ordertype, HttpServletResponse response)
        throws Exception
    {
        AccountView result = this.accountService.login(loginstr);
        // // login 返回前端判断成功去首页，不成功，给出提示错误
        // // login 成功存入session中
        if (result != null && result.getStatus() != null && result.getStatus().equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
        {
            SessionUtil.addUserToSession(request.getSession(), result);
            // writeUserCookie(request, response, result.getPwd(), 60 * 60 * 24 * 30);
            writeUserCookie(request, response, result.getType(), result.getName(), 60 * 60 * 24 * 30);
            /**
             * * 合并临时购物车
             */
            if (ygguuid != null && !ygguuid.equals("tmpuuid") && ordertype != null && ordertype.equals("1")) // ==2时
                                                                                                             // 从购物车结算页面中来的，先不合并
            {
                int tempAccount = getTempAccountId(ygguuid);
                if (tempAccount != CommonConstant.ID_NOT_EXIST)
                {
                    String requestParams = "{'cartToken':'" + tempAccount + "','accountId':'" + result.getId() + "'}";
                    try
                    {
                        this.shoppingCartService.merger(requestParams);
                    }
                    catch (Exception e)
                    {
                        logger.error("----UserController----qqcallbacklogin---购物车合并失败---");
                    }
                }
            }
        }
        else if (result.getErrorCode() != null)
        {
            if (result.getErrorCode().equals(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "手机号不存在!");
            }
            else if (result.getErrorCode().equals(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.PASSWORD_INVALID.getValue()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "密码错误!");
            }
            mv.addObject("name", "qq或weibo联合登陆失败!");
            mv.setViewName("forward:/user/tologin");
        }
        
        if (ordertype != null && ordertype.equals("2")) // 从定单结算中来
        {
            mv.setViewName("redirect:/order/confirm/2");
        }
        return result;
    }
    
    /**
     * 使用weibo login
     * 
     * @param request
     * @param response
     * @throws Exception
     */
//    @RequestMapping(value = "/toweibologin/{ordertype}")
//    public void toWeiBoConnectLogin(HttpServletRequest request, HttpServletResponse response, @PathVariable("ordertype") String ordertype)
//        throws Exception
//    {
//        weibo4j.Oauth oauth = new weibo4j.Oauth();
//        String code = oauth.authorize("code");
//        this.logger.info("WeiBo---login---url---is:" + code);
//        response.setContentType("text/html;charset=utf-8");
//        try
//        {
//            request.getSession().setAttribute("weibologinordertype", ordertype);
//            response.sendRedirect(code);
//        }
//        catch (Exception e)
//        {
//            logger.error("toWeiBoConnectLogin error", e);
//        }
//    }
    
    /**
     * WeiBo的回调业务逻辑处理
     * 
     * @param ygguuid
     * @param request
     * @param response1
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/weibocallback")
//    public ModelAndView toWeiBoCallBackLogin(@CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid, HttpServletRequest request,
//        HttpServletResponse response)
//            throws Exception
//    {
//        ModelAndView mv = new ModelAndView();
//        Map<String, String> params = CommonUtil.getAllRequestParam(request);
//        mv.setViewName("redirect:/mycenter/show"); // 成功 进入到个人中心
//        String ordertype = "";
//        try
//        {
//            String code = params.get("code");
//            weibo4j.Oauth oauth = new weibo4j.Oauth();
//            weibo4j.http.AccessToken at = oauth.getAccessTokenByCode(code);
//            String access_token = at.getAccessToken();
//            Users um = new Users(access_token);
//            User user = um.showUserById(at.getUid());
//            String sname = user != null ? user.getScreenName() : "";
//            String uid = (at != null ? at.getUid() : "00000");
//            String userImageUrl = user == null ? "" : user.getProfileImageUrl();
//            
//            String loginstr = "{'name':'" + uid + "','type':'" + CommonEnum.ACCOUNT_LOGIN_TYPE.SINAWEIBO.getValue() + "','password':'','nickname':'" + sname + "','userImageUrl':'"
//                + userImageUrl + "'}";
//            ordertype = (request.getSession().getAttribute("weibologinordertype") == null ? "1" : (String)request.getSession().getAttribute("weibologinordertype"));
//            processLoginCallBack(loginstr, mv, request, ygguuid, ordertype, response);
//            
//            request.getSession().setAttribute("userimage", userImageUrl); // 用户图象
//        }
//        catch (Exception e)
//        {
//            this.logger.error("WeiBo---login--失败!", e);
//        }
//        
//        if (ordertype != null && ordertype.equals("2")) // 从定单结算中来
//        {
//            // 在此设一个值跳到另一个请求中去时在参数中用@RequestParam中就能得到此参数
//            mv.setViewName("redirect:/order/confirm/2");
//        }
//        else
//        {
//            String returnBackUrl = (String)request.getSession().getAttribute("returnBackUrl");
//            // // 按需加上判断
//            // if (returnBackUrl.indexOf("m.gegejia.com") == -1)
//            // {
//            // returnBackUrl = null;
//            // }
//            if (returnBackUrl != null)
//            {
//                request.getSession().setAttribute("returnBackUrl", null);
//                mv.setViewName("redirect:" + returnBackUrl);
//            }
//        }
//        request.getSession().removeAttribute("weibologinordertype");
//        return mv;
//    }
    
    /**
     * 注册
     * 
     * @return
     */
    @RequestMapping("/toregister/{ordertype}")
    public ModelAndView toregister(@PathVariable("ordertype") String ordertype)
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/register");
        
        mv.addObject("ordertype", ordertype);
        
        return mv;
    }
    
    /**
     * 忘记密码，修改
     * 
     * @return
     */
    @RequestMapping("/toresetpwd")
    public String toresetpwd()
    {
        return "user/resetpwd";
    }
    
    /**
     * 修改密码
     * 
     * @return
     */
    @RequestMapping("/tomodifypwd")
    public String tomodifypwd(HttpServletRequest request)
    {
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
            return "redirect:/user/tologin";
        return "user/modifypwd";
    }
    
    /**
     * 用户登陆页面
     * 
     * @param name
     * @param password
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "name", required = false, defaultValue = "") String name, @RequestParam(value = "password", required = false, defaultValue = "") String password,
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype)
            throws Exception
    {
        if (name.equals(""))
            name = request.getParameter("accountid");
            
        /**
         * 没有login直接用此URL时，要用Filter去处理一下，让它跳到tologin页面上去 用户login成功后，要到cookie中去检查有没有临时账号， 如果有的话，需要合并购物车，没有就不合并
         */
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/mycenter/show"); // 成功 进入到个人中心
        
        AccountView av = new AccountView(name, password);
        String validateResult = av.validate("{'source':'login'}");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.addObject("errorCode", validateResult);
            mv.addObject("name", name);
            mv.setViewName("forward:/user/tologin");
            return mv;
        }
        password = CommonUtil.strToMD5(password + name);
        
        String loginstr = "{'name':'" + name + "','type':'1','password':'" + password + "','nickname':''}";
        
        AccountView result = this.accountService.login(loginstr);
        // // login 返回前端判断成功去首页，不成功，给出提示错误
        // // login 成功存入session中
        if (result != null && result.getStatus() != null && result.getStatus().equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
        {
            SessionUtil.addUserToSession(request.getSession(), result);
            // writeUserCookie(request, response, result.getPwd(), 60 * 60 * 24 * 30);
            writeUserCookie(request, response, result.getType(), result.getName(), 60 * 60 * 24 * 30);
            /**
             * * 合并临时购物车
             */
            if (ygguuid != null && !ygguuid.equals("tmpuuid") && ordertype != null && ordertype.equals("1")) // ==2时
                                                                                                             // 从购物车结算页面中来的，先不合并
            {
                int tempAccount = getTempAccountId(ygguuid);
                if (tempAccount != CommonConstant.ID_NOT_EXIST)
                {
                    String requestParams = "{'cartToken':'" + tempAccount + "','accountId':'" + result.getId() + "'}";
                    try
                    {
                        String responseParams = this.shoppingCartService.merger(requestParams);
                    }
                    catch (Exception e)
                    {
                        logger.error("----UserController----login---购物车合并失败---");
                    }
                }
            }
        }
        else if (result.getErrorCode() != null)
        {
            if (result.getErrorCode().equals(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "手机号不存在!");
            }
            else if (result.getErrorCode().equals(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.PASSWORD_INVALID.getValue()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "密码错误!");
            }
            mv.addObject("name", name);
            mv.setViewName("forward:/user/tologin");
        }
        
        if (ordertype != null && ordertype.equals("2")) // 从定单结算中来
        {
            mv.setViewName("redirect:/order/confirm/2");
        }
        else
        {
            String returnBackUrl = (String)request.getSession().getAttribute("returnBackUrl");
            // // 按需加上判断
            // if (returnBackUrl.indexOf("m.gegejia.com") == -1)
            // {
            // returnBackUrl = null;
            // }
            if (returnBackUrl != null)
            {
                request.getSession().setAttribute("returnBackUrl", null);
                mv.setViewName("redirect:" + returnBackUrl);
            }
        }
        return mv;
    }
    
    /**
     * 修改密码
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifypwd")
    @ResponseBody
    public String modifyPwd(HttpServletRequest request, @RequestParam(value = "oldpwd", required = false, defaultValue = "") String oldpwd,
        @RequestParam(value = "pwd", required = false, defaultValue = "") String pwd)
            throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        String mobileNumber = av.getName();
        String oldPassword = CommonUtil.strToMD5(oldpwd + mobileNumber);
        String newPassword = CommonUtil.strToMD5(pwd + mobileNumber);
        
        String requestParams = "{'mobileNumber':'" + mobileNumber + "','oldPassword':'" + oldPassword + "','newPassword':'" + newPassword + "'}";
        String responseParams = this.accountService.modifyPwd(requestParams);
        JsonObject param = (JsonObject)parser.parse(responseParams);
        String status = param.get("status").getAsString();
        
        if (status != null && status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
        {
            String errorCode = param.get("errorCode").getAsString();
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", errorCode);
            return result.toString();
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    private int getTempAccountId(String tempYgguuid)
        throws Exception
    {
        int tempAccountId = CommonConstant.ID_NOT_EXIST;
        tempAccountId = this.tempAccountService.findTempAccountIdByUUID(tempYgguuid);
        return tempAccountId;
    }
    
    /**
     * 用户注册页面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam("accountid") String name, @RequestParam(value = "password", required = false, defaultValue = "") String password,
        @RequestParam(value = "smsverify", required = false, defaultValue = "") String smsverify,
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype,
        @RequestParam(value = "recommendedCode", required = false, defaultValue = "") String recommendedCode)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        AccountView av = new AccountView(name, password);
        String validateResult = av.validate("{'source':'register'}");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.addObject("errorCode", validateResult);
            mv.addObject("name", name);
            mv.setViewName("forward:/user/toregister/1");
            return mv;
        }
        
        password = CommonUtil.strToMD5(password + name);
        // / 有些输入判断放在前端页面上去判断
        String params =
            "{'mobileNumber':'" + name.trim() + "','verificationCode':'" + smsverify.trim() + "','password':'" + password.trim() + "','recommendedCode':'" + recommendedCode + "'}";
            
        AccountView result = this.accountService.register(params); // 注册服务
        if (result != null && result.getStatus() != null && result.getStatus().equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()) && result.getErrorCode() != null)
        {
            mv.addObject("accountid", name);
            if (result.getErrorCode().equals(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_INVALID.getErrorCode()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "手机号不合法!");
            }
            else if (result.getErrorCode().equals(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_REPEAT.getErrorCode()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "重复手机号!");
            }
            else if (result.getErrorCode().equals(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "验证码错误(包括：已使用、不正确、已过期【10分钟】)!");
            }
            else if (result.getErrorCode().equals(CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "服务器未知错误!");
            }
            mv.setViewName("forward:/user/toregister/1"); // 失败还是在此页面
            return mv;
        }
        
        // if(ordertype != null && ordertype.equals("2")) // 从定单结算页面跳到此页面来的 先自动login再去定单结算页面
        // {
        
        mv.addObject("name", name);
        mv.addObject("accountid", name);
        mv.addObject("password", password);
        mv.addObject("ordertype", ordertype);
        mv.setViewName("forward:/user/login");
        // }
        
        return mv;
    }
    
    /**
     * 发送注册短信，包括60s后重发 accountid 表示电话号码 type为类型,1表示注册用户 2 表示找回密码 verifyCode:图形验证码
     * 
     * @throws Exception
     */
//    @RequestMapping("/sendsms")
//    @ResponseBody
//    public String getSmsVerify(HttpServletRequest request, @RequestParam(value = "accountid", required = false, defaultValue = "") String accountid,
//        @RequestParam(value = "type", required = false, defaultValue = "1") String type, @RequestParam(value = "verifyCode", required = false, defaultValue = "") String verifyCode)
//            throws Exception
//    {
//        Object sessionCode = request.getSession().getAttribute("verifyCode");
//        JsonObject jsonObj = new JsonObject();
//        String ip = CommonUtil.getRemoteIpAddr(request);
//        
//        // 统计同一天内，同一个手机号发送短信的请求次数，如果大于20次，则发送提醒
//        Integer mobileNumberCount = cacheService.getCache(CacheConstant.NUMBER_SENDMSG_TIMES_COUNT + accountid);
//        if (mobileNumberCount == null)
//        {
//            cacheService.addCache(CacheConstant.NUMBER_SENDMSG_TIMES_COUNT + accountid, 1, CacheConstant.CACHE_DAY_1);
//        }
//        else
//        {
//            mobileNumberCount++;
//            logger.info("***********************手机号" + accountid + "今日发送短信请求次数：" + mobileNumberCount + "***********************");
//            cacheService.addCache(CacheConstant.NUMBER_SENDMSG_TIMES_COUNT + accountid, mobileNumberCount, CacheConstant.CACHE_DAY_1);
//            if (mobileNumberCount > CommonConstant.SAME_NUMBER_SENDMSG_MAX_TIMES_PER_DAY)
//            {
//                jsonObj.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
//                jsonObj.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.OUTOF_SENDMSG_TIMES.getValue());
//                Integer isSend = cacheService.getCache(CacheConstant.IS_SEND_TIPS + accountid);
//                // 同一天内，同一个手机号发送短信请求次数达到上限时只发送一次
//                if (isSend == null)
//                {
//                    CommonUtil.sendSMS(NOTIFY_MOBILE_NUMBER, "【左岸城堡】手机号：" + accountid + "今日发送短信请求次数达到" + mobileNumberCount + "次，疑似恶意攻击，请处理", 5);
//                    logger.info("************有手机号同一天内发送短信请求次数超过20，给" + NOTIFY_MOBILE_NUMBER[0] + "发送预警***********");
//                }
//                else
//                {
//                    cacheService.addCache(CacheConstant.IS_SEND_TIPS + accountid, 1, CacheConstant.CACHE_DAY_1);
//                }
//                return jsonObj.toString();
//            }
//            
//        }
        
//        // 统计同一天内，同一个IP发送短信的请求次数，如果大于500次，则发送提醒
//        Integer IPCount = cacheService.getCache(CacheConstant.IP_SENDMSG_TIMES_COUNT + ip);
//        if (IPCount == null)
//        {
//            cacheService.addCache(CacheConstant.IP_SENDMSG_TIMES_COUNT + ip, 1, CacheConstant.CACHE_DAY_1);
//        }
//        else
//        {
//            IPCount++;
//            logger.info("***********************IP地址" + ip + "今日发送短信请求次数：" + IPCount + "***********************");
//            cacheService.addCache(CacheConstant.IP_SENDMSG_TIMES_COUNT + ip, IPCount, CacheConstant.CACHE_DAY_1);
//            if (IPCount > CommonConstant.SAME_IP_SENDMSG_MAX_TIMES_PER_DAY)
//            {
//                jsonObj.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
//                jsonObj.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.OUTOF_SENDMSG_TIMES.getValue());
//                Integer isSend = cacheService.getCache(CacheConstant.IS_SEND_TIPS + ip);
//                // 同一天内，同一个IP发送短信请求次数达到上限时只发送一次
//                if (isSend == null)
//                {
//                    CommonUtil.sendSMS(NOTIFY_MOBILE_NUMBER, "【左岸城堡】IP：" + ip + "今日发送短信请求次数达到" + IPCount + "次，疑似恶意攻击，请处理", 5);
//                    logger.info("************有IP地址同一天内发送短信请求次数超过500，给" + NOTIFY_MOBILE_NUMBER[0] + "发送预警***********");
//                }
//                else
//                {
//                    cacheService.addCache(CacheConstant.IS_SEND_TIPS + ip, 1, CacheConstant.CACHE_DAY_1);
//                }
//                return jsonObj.toString();
//            }
//        }
//        // 图形验证码错误
//        if (sessionCode == null || !(sessionCode + "").equalsIgnoreCase(verifyCode))
//        {
//            jsonObj.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
//            jsonObj.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.IMAGE_VERIFYCODE_ERROR.getValue());
//            return jsonObj.toString();
//        }
//        String params = "{'mobileNumber':'" + accountid + "','type':'" + type + "'}"; // CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue()
//        String result = this.accountService.verificationCode(params);
//        
//        return result;
//    }
//    
    /**
     * 修改密码页面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
    public ModelAndView resetpwd(@RequestParam("accountid") String accountid, @RequestParam(value = "password", required = false, defaultValue = "") String password,
        @RequestParam(value = "smsverify", required = false, defaultValue = "") String smsverify)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forward:/user/toresetpwd"); // 失败还是在此页面
        
        AccountView av = new AccountView(accountid, password);
        String validateResult = av.validate("{'source':'resetpwd'}");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.addObject("errorCode", validateResult);
            mv.addObject("accountid", accountid);
            // mv.setViewName("forward:/user/toresetpwd");
            return mv;
        }
        password = CommonUtil.strToMD5(password + accountid);
        
        JsonParser parser = new JsonParser();
        String requestParams = "{'mobileNumber':'" + accountid + "','verificationCode':'" + smsverify + "','password':'" + password + "'}";
        String response = this.accountService.resetPwd(requestParams);
        JsonObject param = (JsonObject)parser.parse(response);
        String status = param.get("status").getAsString();
        if (status != null && status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
        {
            String errorCode = param.get("errorCode").getAsString();
            mv.addObject("accountid", accountid);
            if (errorCode != null && errorCode.equals(CommonEnum.ACCOUNT_RESET_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "手机号未注册");
            }
            else if (errorCode.equals(CommonEnum.ACCOUNT_RESET_ERRORCODE.VERIFICATION_INVALID.getValue())
                || errorCode.equals(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode()))
            {
                mv.addObject(CommonConstant.ERROR_CODE, "短信验证码错误");
            }
            return mv;
        }
        mv.setViewName("redirect:/user/tologin"); // / 修改成功后进入login页面，
        return mv;
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        
        // //移除session 和 cookie 中的数据
        SessionUtil.removeUserToSession(request.getSession());
        SessionUtil.removeCurrentOrderConfirmId(request.getSession());
        SessionUtil.removeCurrentSelectedAddres(request.getSession());
        SessionUtil.removeWeiXinOpenId(request.getSession());
        SessionUtil.removeWeiXinCode(request.getSession());
        request.getSession().removeAttribute("userimage");
        writeUserCookie(request, response, new Byte("-1"), "", 0);
        return "redirect:/user/tologin";
    }
    
    private void writeUserCookie(HttpServletRequest request, HttpServletResponse response, String pwd, int second)
        throws Exception
    {
        // 写入用户cookie
        Cookie cookie = new Cookie("userinfo", pwd);
        cookie.setMaxAge(second); // 保留一个月
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    private void writeUserCookie(HttpServletRequest request, HttpServletResponse response, byte type, String name, int second)
        throws Exception
    {
        JSONObject j = new JSONObject();
        j.put("type", type);
        j.put("name", name);
        // 写入用户cookie
        Cookie cookie = new Cookie("userinfo", j.toJSONString());
        cookie.setMaxAge(second); // 保留一个月
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    @RequestMapping(value = "/invite", method = RequestMethod.POST)
    @ResponseBody
    public String invite(HttpServletRequest request, //
        @RequestParam(value = "code", required = true) String code, //
        @RequestParam(value = "mobileNumber", required = true) String mobileNumber)
            throws Exception
    {
        try
        {
            Map<String, Object> result = accountService.recommend(mobileNumber, code);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            logger.error("领取错误", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", "0");
            map.put("msg", "领取失败啦~ 请稍后再试");
            return JSON.toJSONString(map);
        }
    }
    
}
