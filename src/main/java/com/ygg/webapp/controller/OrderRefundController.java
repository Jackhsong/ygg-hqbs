package com.ygg.webapp.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
// import com.ygg.common.services.image.ImgYunManager;
// import com.ygg.common.services.image.OSSImgYunServiceIF;
// import com.ygg.common.services.image.UpImgYunServiceIF;
import com.ygg.common.utils.CommonConst;
import com.ygg.webapp.annotation.ControllerLog;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.entity.OrderProductRefundEntity;
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.service.OrderRefundService;
import com.ygg.webapp.service.TempAccountService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonProperties;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.PicBucket;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.UUIDGenerator;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.OrderFrefundView;
import com.ygg.webapp.view.OrderProductRefundView;
import com.ygg.webapp.view.OrderReturnProductView;

@Controller("orderRefundController")
@RequestMapping("/orderrefund")
public class OrderRefundController
{
    
//    OSSImgYunServiceIF ossYunIfc = null;//(OSSImgYunServiceIF)ImgYunManager.getClient(CommonConst.IMG_ALIYUN);
//    
//    UpImgYunServiceIF upYunIfc = null;//(UpImgYunServiceIF)ImgYunManager.getClient(CommonConst.IMG_UPYUN);
    
    Logger logger = Logger.getLogger(OrderRefundController.class);
    
    @Resource(name = "orderRefundService")
    private OrderRefundService orderRefundService;
    
    @Resource
    private AccountService accountService;
    
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "tempAccountService")
    private TempAccountService tempAccountService;
    
    Random random = new Random();
    
    // private String sign_key = CommonUtil.SIGN_KEY;
    
    /**
     * 左岸城堡客服首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/orderRefundIndex"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView orderRefundIndex(HttpServletRequest request, HttpServletResponse response, HttpSession session,
        @RequestParam(value = "reqSource", required = false, defaultValue = "1") String reqSource,
        @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId, @RequestParam(value = "sign", required = false, defaultValue = "") String sign,
        @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid)
        throws Exception
    {
        String username = "";
        String password = "";
        
        JsonParser parser = new JsonParser();
        ModelAndView mv = new ModelAndView();
        AccountView av = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName() != null && cookie.getName().equals("appinfo"))
                {
                    mv.addObject("isApp", IS_APP); // 要保存到session中去在app中回退时要找到这个isApp
                    break;
                }
            }
        }
        
        if (!reqSource.equals(CommonEnum.ORDER_REFUND_REQ_SOURCE.REQ_SOURCE_WEB.getValue())) // app 请求
        {
            if ("0".equals(accountId)) // 用户未登录
            {
                mv.addObject("isLogin", "0");
            }
            else
            {
                String requestParams = "{'accountId':'" + accountId + "','sign':'" + sign + "'}";
                String responseParams = this.orderRefundService.getOrderRefundIndex(request, requestParams, response);
                JsonObject param = (JsonObject)parser.parse(responseParams);
                mv.addObject("status", param.get("status").getAsString());
                String errorCode = param.get("errorCode") == null ? null : param.get("errorCode").getAsString();
                if (errorCode != null)
                {
                    mv.setViewName("user/login"); // 失败返回原页面
                    return mv;
                }
            }
            mv.addObject("isApp", IS_APP);
            SessionUtil.addCurrentRequestResource(session, accountId, IS_APP);
            writeAppCookie(request, response, IS_APP, 60 * 60 * 24 * 30);
            av = SessionUtil.getCurrentUser(request.getSession());
        }
        else
        // web请求
        {
            av = SessionUtil.getCurrentUser(request.getSession());
            if (av != null)
            {
                // mv.setViewName("user/login"); // 失败返回原页面
                // return mv;
                /**以下注视代码获取用户信息，美洽调用*/
                // String userInfo = accountService.getUserInfo(av.getId());
                // mv.addObject("userInfo", userInfo);
                // mv.addObject("appUserId", av.getId());
                
                accountId = av.getId() + "";
                username = av.getId() + "";
            }
            else
            {
                /**以下注视代码获取用户信息，美洽调用*/
                // String userInfo = "{\"用户名\":\"未登陆用户\"}";
                // String appUserId = CommonUtil.generateUUID();
                // mv.addObject("userInfo", userInfo);
                // mv.addObject("appUserId", appUserId);
                
                if (ygguuid == null || ygguuid.equals("") || ygguuid.equals("tmpuuid"))
                {
                    ygguuid = UUIDGenerator.getUUID();
                    // 写入cookie
                    writeCartCookie(request, response, ygguuid);
                }
                int tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
                if (tempAccountId == CommonConstant.ID_NOT_EXIST) // 生成一个UUID再加入表临时用户表中
                {
                    tempAccountService.addTempAccount(ygguuid);
                    tempAccountId = tempAccountService.findTempAccountIdByUUID(ygguuid);
                }
                username = "temp" + tempAccountId;
            }
            
            password = CommonUtil.strToMD5(username + CommonConstant.SIGN_KEY + "huanxin");
            if (accountService.findHuanXinInfoByName(username) == CommonConstant.ID_NOT_EXIST)
            {
                Object tokenCache = cacheService.getCache(CacheConstant.COMMON_HUANXIN_CACHE_KEY);
                String token = "";
                if (tokenCache != null)
                {
                    token = (String)tokenCache;
                }
                else
                {
                    Map<String, String> headers = new HashMap<String, String>();
                    String resultToken =
                        CommonUtil.sendRESTFulPost("https://a1.easemob.com/0001234/gegejia/token", "{\"grant_type\": \"client_credentials\",\"client_id\": \"YXA64y4aoLkGEeW9aQNG1iPfCw\",\"client_secret\": \"YXA618qMtxcxHWmTKJOa7dCu4S9mb04\"}", headers);
                    JsonObject param = (JsonObject)parser.parse(resultToken);
                    token = param.get("access_token").getAsString();
                    cacheService.addCache(CacheConstant.COMMON_HUANXIN_CACHE_KEY, token, CacheConstant.CACHE_DAY_1);
                }
                
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + token);
                CommonUtil.sendRESTFulPost("https://a1.easemob.com/0001234/gegejia/users", "{\"username\": \"" + username + "\",\"password\": \"" + password + "\"}", headers);
                accountService.addHuanXinInfo(username);
            }
            mv.addObject("username", username);
            mv.addObject("password", password);
            
        }
        // 查询退款退货的总数量
        if ("0".equals(accountId)) // 用户未登录
        {
            mv.addObject("count", 0);
        }
        else
        {
            String responseParams = this.orderRefundService.getOrderRefundCount("{'accountId':'" + av.getId() + "'}");
            JsonObject param = (JsonObject)parser.parse(responseParams);
            int count = param.get("count").getAsInt();
            if (count > 0)
                mv.addObject("count", param.get("count").getAsInt());
        }
        
        mv.addObject("weixinGege", CommonProperties.weixinGege);
        mv.setViewName("orderrefund/index"); // 成功时
        return mv;
    }
    
    /**
     * 自助退款首页
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/getOrderInfo")
    public ModelAndView getOrderInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, //
        @RequestParam(value = "reqSource", required = false, defaultValue = "1") String reqSource, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        JsonParser parser = new JsonParser();
        
        String requestParams = null;
        if (!reqSource.equals(CommonEnum.ORDER_REFUND_REQ_SOURCE.REQ_SOURCE_WEB.getValue()))// app请求
        {
            mv.addObject("isApp", IS_APP);
            requestParams = "{'accountId':'" + accountId + "','sign':'" + sign + "','isApp':'1'}";
            SessionUtil.addCurrentRequestResource(session, accountId, IS_APP);
        }
        else
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            // 判断是否已经登陆
            if (av == null)
            {
                mv.setViewName("redirect:/user/tologin");
                return mv;
            }
            
            requestParams = "{'accountId':'" + av.getId() + "'}";
        }
        
        // 有可能是从app中进入从其他页面跳转到本页面
        if ("0".equals(accountId))
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            accountId = av.getId() + "";
            String isApp = SessionUtil.getCurrentRequestResource(session, accountId);
            if (isApp != null)
            {
                mv.addObject("isApp", IS_APP);
            }
        }
        
        String responseParams = this.orderRefundService.getOrderInfo(request, response, requestParams);
        JsonObject param = (JsonObject)parser.parse(responseParams);
        mv.addObject("status", param.get("status").getAsString());
        String errorCode = param.get("errorCode") == null ? null : param.get("errorCode").getAsString();
        if (errorCode != null)
        {
            mv.addObject("errorCode", errorCode);
            mv.setViewName("orderrefund/refund"); // 失败返回原页面
            return mv;
        }
        
        Object orderList = param.get("orderList");
        List<OrderFrefundView> ofvs = new ArrayList<OrderFrefundView>();
        if (orderList != null && !orderList.toString().equals(""))
        {
            ofvs = JSONUtils.fromJson(orderList.toString(), new TypeToken<List<OrderFrefundView>>()
            {
            });
        }
        
        SessionUtil.removeOrderRefundProduct(request.getSession());
        
        mv.addObject("orderList", ofvs);
        mv.setViewName("orderrefund/refund"); // 成功页面
        return mv;
    }
    
    /**
     * 退款申请
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/getSubmitApplicationInfo")
    public ModelAndView getSubmitApplicationInfo(
        HttpServletRequest request,
        HttpServletResponse response, //
        HttpSession session, //
        @RequestParam(value = "productId", required = false, defaultValue = "0") String productId,
        @RequestParam(value = "orderProductId", required = false, defaultValue = "0") String orderProductId,
        @RequestParam(value = "orderId", required = false, defaultValue = "0") String orderId,
        @RequestParam(value = "orderProductRefundId", required = false, defaultValue = "0") String orderProductRefundId,
        @RequestParam(value = "reqSource", required = false, defaultValue = "1") String reqSource, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        JsonParser parser = new JsonParser();
        if ("0".equals(accountId))
        {
            mv.addObject("isApp_isAccountIdEmpty", isApp_isAccountIdEmpty);
        }
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        
        if (av != null)
        {
            accountId = av.getId() + "";
        }
        
        OrderProductRefundView oprf = SessionUtil.getOrderRefundProduct(request.getSession());
        if (oprf != null)
        {
            if (productId.equals(ID_IS_ZERO))
                productId = oprf.getProductId() + "";
            if (orderProductId.equals(ID_IS_ZERO))
                orderProductId = oprf.getOrderProductId() + "";
            if (orderId.equals(ID_IS_ZERO))
                orderId = oprf.getOrderId() + "";
            // 控制，图片必须保证，eg：有两张图片：则必须是image1和image2，不能出现是image2和image3的情况 。这个涉及到页面展示
            oprf.refreshImageSequence();
            try
            {
                oprf.setExplain(URLDecoder.decode(oprf.getExplain(), "UTF-8"));
            }
            catch (Exception e)
            {
                oprf.setExplain(oprf.getExplain());
            }
        }
        
        String isApp = SessionUtil.getCurrentRequestResource(session, accountId);
        
        String requestParams = null;
        if (!reqSource.equals(CommonEnum.ORDER_REFUND_REQ_SOURCE.REQ_SOURCE_WEB.getValue())) // app
                                                                                             // 请求
        {
            mv.addObject("isApp", IS_APP);
            orderProductId = this.orderRefundService.findOrderProductIdByPidAndOid(productId, orderId);
            requestParams =
                "{'productId':'" + productId + "','orderProductId':'" + orderProductId + "','orderId':'" + orderId + "','accountId':'" + accountId + "','orderProductRefundId':'"
                    + orderProductRefundId + "','sign':'" + sign + "','isApp':'1'}";
            SessionUtil.addCurrentRequestResource(session, accountId, IS_APP);
        }
        else
        {
            requestParams =
                "{'productId':'" + productId + "','orderProductId':'" + orderProductId + "','orderId':'" + orderId + "','accountId':'" + av.getId() + "','orderProductRefundId':'"
                    + orderProductRefundId + "'}";
            if (isApp != null)
            {
                mv.addObject("isApp", IS_APP);
            }
            else
            {
                mv.addObject("isApp_isAccountIdEmpty", "");
            }
            
        }
        String responseParams = this.orderRefundService.getSubmitApplicationInfo(request, response, requestParams);
        JsonObject result = (JsonObject)parser.parse(responseParams);
        String status = result.get("status").getAsString();
        mv.addObject("status", status);
        if (result.get("errorCode") != null)
        {
            String errorCode = result.get("errorCode").getAsString();
            mv.addObject("errorCode", errorCode);
            mv.setViewName("orderrefund/apply"); // 失败返回原页面
            return mv;
        }
        
        OrderReturnProductView productList = null;
        Object productJson = result.get("productList");
        if (productJson != null && !productJson.toString().equals(""))
        {
            productList = JSONUtils.fromJson(productJson.toString(), new TypeToken<OrderReturnProductView>()
            {
            });
        }
        
        Object selectedAceJson = result.get("selectedAce");
        AccountCartEntity selectedAce = null;
        if (selectedAceJson != null && !selectedAceJson.toString().equals(""))
        {
            selectedAce = JSONUtils.fromJson(selectedAceJson.toString(), new TypeToken<AccountCartEntity>()
            {
            });
            
            boolean flag = false;
            if (oprf != null && oprf.getAccountCardId() <= 0) // 保证第一次进入此方法后，accountCardid没有值
            {
                flag = true;
            }
            if (oprf == null)
            {
                oprf = new OrderProductRefundView();
                flag = true;
            }
            if (flag)
            {
                oprf.setAccountCardId(selectedAce.getId());
                oprf.setAccountCardVal(CommonUtil.getAccountCardValue(selectedAce));
                SessionUtil.addOrderRefundProduct(request.getSession(), oprf);
            }
        }
        
        if (oprf != null)
        {
            mv.addObject("oprf", oprf);
        }
        
        mv.addObject("productList", productList);
        mv.addObject("orderProductId", orderProductId);
        mv.addObject("productId", productId);
        mv.addObject("orderId", orderId);
        mv.addObject("canReturnPay", result.get("canReturnPay"));
        mv.setViewName("orderrefund/apply"); // 成功的页面
        int count = Integer.parseInt(productList.getCount());
        if (count > 0)
        {
            List<Integer> countList = new ArrayList<Integer>();
            for (int i = 1; i <= count; i++)
            {
                countList.add(i);
            }
            mv.addObject("countList", countList); // 生成退款数量的列表
        }
        
        // 对于取消退款的情况 ID 不为空,表示修改, 查出退款详情,放在oprf 中
        /*
         * if(!orderProductRefundId.equals(ID_IS_ZERO)) { oprf =
         * this.orderRefundService.getOrderProductRefundViewById(Integer.parseInt(orderProductRefundId)) ;
         * oprf.setAccountCardVal( CommonUtil.getAccountCardValue(selectedAce) ) ;
         * oprf.setProductId(Integer.parseInt(productId)); SessionUtil.addOrderRefundProduct(request.getSession(),
         * oprf);
         * 
         * mv.addObject("oprf", oprf); }
         */
        mv.addObject("fileUploadUrl", YggWebProperties.getInstance().getProperties(CommonConstant.FILEUPLOAD_URL));
        return mv;
    }
    
    /**
     * 提交退款申请
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/submitApplication")
    @ControllerLog(description = "退款-提交退款申请")
    public ModelAndView submitApplication(HttpServletRequest request, HttpSession session, @ModelAttribute OrderProductRefundView oprf)
        throws Exception
    {
        
        logger.info("退款-提交退款申请Controller:"+"AccountId:"+oprf.getAccountId()+"~"+"OrderId:"+oprf.getOrderId()+"~"+"OrderProductId:"+oprf.getOrderProductId());
        
        ModelAndView mv = new ModelAndView();
        
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        
        oprf.setAccountId(av.getId());
        // 控制，图片必须保证，eg：有两张图片：则必须是image1和image2，不能出现是image2和image3的情况 。这个涉及到页面展示
        oprf.refreshImageSequence();
        // 防止退款说明中有特殊字符，先转码存入数据库
        oprf.setExplain(URLEncoder.encode(oprf.getExplain(), "UTF-8"));
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        result.add("orderRefundProductInfo", parser.parse(JSONUtils.toJson(oprf, false)));
        String resquestParams = result.toString();
        String responseParams = this.orderRefundService.submitApplication(resquestParams);
        
        result = (JsonObject)parser.parse(responseParams);
        String status = result.get("status").getAsString();
        //        int orderProductRefundId = result.get("orderProductRefundId") == null ? 0 : result.get("orderProductRefundId").getAsInt();
        mv.addObject("status", status);
        if (result.get("errorCode") != null)
        {
            String errorCode = result.get("errorCode").getAsString();
            mv.addObject("errorCode", errorCode);
            mv.setViewName("forward:/orderrefund/getSubmitApplicationInfo"); // 失败返回原页面
            SessionUtil.removeOrderRefundProduct(request.getSession());
            SessionUtil.addOrderRefundProduct(request.getSession(), oprf);
            return mv;
        }
        
        SessionUtil.removeOrderRefundProduct(request.getSession());
        /*
         * if (orderProductRefundId != 0) { oprf.setId(orderProductRefundId);
         * SessionUtil.addOrderRefundProduct(request.getSession(), oprf); }
         */
//        mv.setViewName("redirect:/orderrefund/orderRefundIndex");
        
        //跳转到订单详情页面
        mv.setViewName("forward:/order/orderdetail/"+oprf.getOrderId());
//        String isApp = SessionUtil.getCurrentRequestResource(session, av.getId() + "");
//        if (isApp != null)
//        {
//            // mv.setViewName("redirect:ggj://redirect");
//            mv.setViewName("redirect:/orderrefund/getReturnProcessInfo"); // 成功的页面
//        }
        return mv;
    }
    
    /**
     * 退款详情 查询
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/refundInfo")
    public ModelAndView refundInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, //
        @RequestParam(value = "orderProductRefundId", required = false, defaultValue = "0") String orderProductRefundId, //
        @RequestParam(value = "reqSource", required = false, defaultValue = "1") String reqSource, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId, //
        @RequestParam(value = "orderId", required = false, defaultValue = "0") String orderId, //
        @RequestParam(value = "productId", required = false, defaultValue = "0") String productId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        JsonParser parser = new JsonParser();
        String reqParams = null;
        
        if ("0".equals(accountId))
        {
            mv.addObject("isApp_isAccountIdEmpty", isApp_isAccountIdEmpty);
        }
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        // 判断是否已经登陆
        if (av != null)
        {
            accountId = av.getId() + "";
        }
        String isApp = SessionUtil.getCurrentRequestResource(session, accountId);
        /*
         * OrderProductRefundView oprf = SessionUtil.getOrderRefundProduct(request.getSession()); if (oprf != null &&
         * oprf.getId() != 0) { orderProductRefundId = oprf.getId() + ""; }
         */
        
        if (!reqSource.equals(CommonEnum.ORDER_REFUND_REQ_SOURCE.REQ_SOURCE_WEB.getValue())) // app 请求
        {
            
            mv.addObject("isApp", IS_APP);
            orderProductRefundId = this.orderRefundService.findOrderProductRefundIdByPidAndOidAndAid(productId, orderId, accountId);
            reqParams = "{'orderProductRefundId':'" + orderProductRefundId + "','accountId':'" + accountId + "','sign':'" + sign + "','isApp':'1'}";
            SessionUtil.addCurrentRequestResource(session, accountId, IS_APP);
        }
        else
        {
            reqParams = "{'orderProductRefundId':'" + orderProductRefundId + "'}";
            if (isApp != null)
            {
                mv.addObject("isApp", IS_APP);
            }
            else
            {
                mv.addObject("isApp_isAccountIdEmpty", "");
            }
        }
        
        // 有可能是从app中进入从其他页面跳转到本页面
        if ("0".equals(accountId))
        {
            
            if (isApp != null)
            {
                mv.addObject("isApp", IS_APP);
            }
        }
        
        String responseParams = this.orderRefundService.refundInfo(request, response, reqParams);
        JsonObject result = (JsonObject)parser.parse(responseParams);
        String status = result.get("status").getAsString();
        mv.addObject("status", status);
        
        Object refundProductInfoJson = result.get("refundProductInfo");
        Object productListJson = result.get("productList");
        Object accountCartListJson = result.get("accountCartList");
        Object refundPayType = result.get("refundPayType");
        OrderProductRefundEntity ofvs = null;
        OrderReturnProductView orpv = null;
        AccountCartEntity ace = null;
        if (refundProductInfoJson != null && !refundProductInfoJson.toString().equals(""))
        {
            ofvs = JSONUtils.fromJson(refundProductInfoJson.toString(), new TypeToken<OrderProductRefundEntity>()
            {
            });
            
            // 退款说明存入数据库之前进行了URLEncoder.encode(),取出时进行解码
            try
            {
                ofvs.setExplain(URLDecoder.decode(ofvs.getExplain(), "UTF-8"));
            }
            catch (Exception e)
            {
                ofvs.setExplain(ofvs.getExplain());
            }
            String endSecond = "0";
            String checkTime = "";
            Date date = null;
            if (ofvs.getStatus() == Byte.parseByte("1")) // 申请状态中
            {
                checkTime = ofvs.getCreateTime();
                
            }
            else
                checkTime = ofvs.getCheckTime();
            
            date = CommonUtil.string2Date(checkTime, "yyyy-MM-dd HH:mm:ss");
            Calendar curr = Calendar.getInstance();
            curr.setTime(date);
            curr.add(Calendar.DATE, 2);
            date = curr.getTime();
            
            if (checkTime != null && !checkTime.equals("") && date != null)
            {
                if (date.after(new Date()))
                {
                    endSecond = (date.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            mv.addObject("endSecond", endSecond);
        }
        
        if (productListJson != null && !productListJson.toString().equals(""))
        {
            orpv = JSONUtils.fromJson(productListJson.toString(), new TypeToken<OrderReturnProductView>()
            {
            });
        }
        
        if (accountCartListJson != null && !accountCartListJson.toString().equals(""))
        {
            ace = JSONUtils.fromJson(accountCartListJson.toString(), new TypeToken<AccountCartEntity>()
            {
            });
        }
        
        mv.addObject("refundPayType", refundPayType + "");
        mv.addObject("refundProductInfo", ofvs);
        mv.addObject("productList", orpv);
        mv.addObject("accountCartList", ace);
        mv.setViewName("orderrefund/refundDetail");
        return mv;
    }
    
    /**
     * 退货详情(查询)
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/returnGoodInfo")
    public ModelAndView returnGoodInfo(@RequestParam(value = "orderProductRefundId", required = false, defaultValue = "0") String orderProductRefundId,
        @RequestParam(value = "type", required = false, defaultValue = "2") String type)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        String resquestParams = "{'orderProductRefundId':'" + orderProductRefundId + "','type':'" + type + "'}"; // type==2时，表示待退货时，要用户填写物流单号，==3：待退款，4：退款成功
        // ，查物流信息
        String responseParams = this.orderRefundService.returnGoodInfo(resquestParams);
        
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        mv.addObject("status", param.get("status").getAsString());
        String errorCode = param.get("errorCode") == null ? null : param.get("errorCode").getAsString();
        if (errorCode != null)
        {
            mv.addObject("errorCode", errorCode);
            mv.setViewName("orderrefund/returnDetail"); // 原页面返回
            return mv;
        }
        Object productList = param.get("productList");
        OrderReturnProductView orpv = null;
        if (productList != null && !productList.toString().equals(""))
        {
            orpv = JSONUtils.fromJson(productList.toString(), new TypeToken<OrderReturnProductView>()
            {
            });
            mv.addObject("productList", orpv);
        }
        
        String count = param.get("count") == null ? "" : param.get("count").getAsString();
        String money = param.get("money") == null ? "" : param.get("money").getAsString();
        String explain = param.get("explain") == null ? "" : param.get("explain").getAsString();
        String status = param.get("status") == null ? "" : param.get("status").getAsString();
        // String orderProductRefundId = param.get("orderProductRefundId")
        // ==null?"":param.get("orderProductRefundId").getAsString() ;
        String channel = param.get("channel") == null ? "" : param.get("channel").getAsString();
        String number = param.get("number") == null ? "" : param.get("number").getAsString();
        String accountCardValue = param.get("accountCardValue") == null ? "" : param.get("accountCardValue").getAsString();
        String receiveAddress = param.get("receiveAddress") == null ? "浙江杭州西湖区浙商财富中心4号楼607室 左岸城堡（收）0571-86888702" : param.get("receiveAddress").getAsString();
        
        mv.addObject("count", count);
        mv.addObject("money", money);
        try
        {
            mv.addObject("explain", URLDecoder.decode(explain, "UTF-8"));
        }
        catch (Exception e)
        {
            mv.addObject("explain", explain);
        }
        mv.addObject("status", status);
        mv.addObject("channel", channel);
        mv.addObject("number", number);
        mv.addObject("refundStatus", type);
        mv.addObject("accountCardValue", accountCardValue);
        mv.addObject("receiveAddress", receiveAddress);
        mv.addObject("orderProductRefundId", orderProductRefundId);
        mv.setViewName("orderrefund/returnDetail"); // 成功跳转的页面
        return mv;
    }
    
    /**
     * 确认退货
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/submitReturnGood")
    public ModelAndView submitReturnGood(HttpServletRequest request,
        @RequestParam(value = "orderProductRefundId", required = false, defaultValue = "0") String orderProductRefundId,
        @RequestParam(value = "channel", required = false, defaultValue = "") String channel, //
        @RequestParam(value = "number", required = false, defaultValue = "") String number)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        JsonParser parser = new JsonParser();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        
        String requestParams = "{'orderProductRefundId':'" + orderProductRefundId + "','channel':'" + channel + "','number':'" + number + "'}";
        String responseParams = this.orderRefundService.submitReturnGood(requestParams); // //.getOrderInfo(requestParams)
        
        JsonObject param = (JsonObject)parser.parse(responseParams);
        mv.addObject("status", param.get("status").getAsString());
        String errorCode = param.get("errorCode") == null ? null : param.get("errorCode").getAsString();
        if (errorCode != null)
        {
            mv.addObject("errorCode", errorCode);
            mv.addObject("orderProductRefundId", orderProductRefundId);
            mv.setViewName("forward:/orderrefund/returnGoodInfo"); // 原页面返回
            return mv;
        }
        
        Object orderList = param.get("orderList");
        List<OrderFrefundView> ofvs = new ArrayList<OrderFrefundView>();
        if (orderList != null && !orderList.toString().equals(""))
        {
            ofvs = JSONUtils.fromJson(orderList.toString(), new TypeToken<List<OrderFrefundView>>()
            {
            });
        }
        
        mv.addObject("orderList", ofvs);
        String orderId = param.get("orderId").getAsString();
//      mv.setViewName("redirect:/orderrefund/getReturnProcessInfo");
        mv.setViewName("forward:/order/orderdetail/"+orderId);
        return mv;
    }
    
    /**
     * 退款进度
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/getReturnProcessInfo")
    public ModelAndView getReturnProcessInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, //
        @RequestParam(value = "reqSource", required = false, defaultValue = "1") String reqSource, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") String accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        JsonParser parser = new JsonParser();
        
        String requestParams = null;
        
        if (!reqSource.equals(CommonEnum.ORDER_REFUND_REQ_SOURCE.REQ_SOURCE_WEB.getValue()))// app请求
        {
            mv.addObject("isApp", IS_APP);
            requestParams = "{'page':'1','accountId':'" + accountId + "','sign':'" + sign + "','isApp':'1'}";
            SessionUtil.addCurrentRequestResource(session, accountId, IS_APP);
        }
        else
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            // 判断是否已经登陆
            if (av == null)
            {
                mv.setViewName("redirect:/user/tologin");
                return mv;
            }
            requestParams = "{'page':'1','accountId':'" + av.getId() + "'}";
            accountId = av.getId() + "";
        }
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av != null)
        {
            accountId = av.getId() + "";
        }
        String isApp = SessionUtil.getCurrentRequestResource(session, accountId);
        if (isApp != null)
        {
            mv.addObject("isApp", IS_APP);
        }
        
        String responseParams = this.orderRefundService.getReturnProcessInfo(request, response, requestParams);
        
        JsonObject param = (JsonObject)parser.parse(responseParams);
        if (!reqSource.equals(CommonEnum.ORDER_REFUND_REQ_SOURCE.REQ_SOURCE_WEB.getValue()))
        {
            String status = param.get("status").getAsString();
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
            {
                /*
                 * AccountView av = accountService.findAccountById(Integer.parseInt(accountId));
                 * SessionUtil.addUserToSession(request.getSession(), result);
                 */
            }
        }
        mv.addObject("status", param.get("status").getAsString());
        String errorCode = param.get("errorCode") == null ? null : param.get("errorCode").getAsString();
        if (errorCode != null)
        {
            mv.addObject("errorCode", errorCode);
            mv.setViewName("orderrefund/refundProgress"); // 失败返回原页面
            return mv;
        }
        
        Object orderList = param.get("orderList");
        List<OrderFrefundView> ofvs = new ArrayList<OrderFrefundView>();
        if (orderList != null && !orderList.toString().equals(""))
        {
            ofvs = JSONUtils.fromJson(orderList.toString(), new TypeToken<List<OrderFrefundView>>()
            {
            });
        }
        
        mv.addObject("orderList", ofvs);
        mv.setViewName("orderrefund/refundProgress"); // 成功页面
        
        return mv;
    }

    /**
     * 取消退款
     * @param refundId
     * @return
     * @throws Exception
     */
    @RequestMapping("/cancelRefund")
    @ResponseBody
    public String cancelRefund(@RequestParam(value = "refundId", required = true) int refundId)
        throws Exception
    {
        try
        {
            return orderRefundService.cancelRefund(refundId);
        }
        catch (Exception e)
        {
            logger.error("取消退款失败", e);
            Map<String, Object> result = new HashMap<>();
            result.put("status", "0");
            return JSON.toJSONString(result);
        }
    }
    
    /**
     * 图片上传
     * 
     * @param ysimg
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileUpLoad", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String fileUpLoad( // @RequestParam("imgFile") MultipartFile file,
        HttpServletRequest request, @RequestBody String ysimg)
    {
        JsonObject result = new JsonObject();
        String ysimgStr = request.getParameter("ysimg"); // data:image/jpeg;base64,/9j/4AAQ
        
        if (ysimgStr == null || ysimgStr.equals(""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        
        String[] arrays = ysimgStr.split(",");
        if (arrays == null || arrays.length != 2)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        
        Map<String, String> writeResult = null;
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        try
        {
            String fileName = "orderrefund/applyImages/" + System.currentTimeMillis() + ".jpg";
            // writeResult = UpYunUtil.uploadFile(CommonUtil.getBytesFromBASE64(arrays[1]), fileName);
//            writeResult = upYunIfc.uploadFile(CommonUtil.getBytesFromBASE64(arrays[1]), fileName);
//            // System.out.println("upYun result : " + writeResult);
//            
//            // Map<String, String> ossResult = null ;
//            ossYunIfc.uploadFile(CommonUtil.getBytesFromBASE64(arrays[1]), fileName); // 备份一个
            // System.out.println("ossResult : " + ossResult);
            
            if (writeResult != null && writeResult.get("status").equals("success"))
            {
                result.addProperty("imageUrl", writeResult.get("url") + "!v1orderProduct");
            }
            else
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            }
        }
        catch (Exception e)
        {
            this.logger.error("upYunUtil upload error!", e);
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        
        return result.toString();
    }
    
    /**
     * 批量上传图片
     * 
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/batchFileUpLoad", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String batchFileUpLoad(@RequestParam("imgFileData") MultipartFile file, HttpServletRequest request)
    {
        JsonObject result = new JsonObject();
        try
        {
            if (file.isEmpty())
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", "1"); // 请选择文件后上传
            }
            else
            {
                String fileName = file.getOriginalFilename();
                logger.debug("上传图片原fileName：" + fileName);
                int pointIndex = fileName.lastIndexOf(".");
                String fileExt = fileName.substring(pointIndex);
                String id = Long.toHexString(Long.valueOf(random.nextInt(10) + "" + System.currentTimeMillis() + "" + random.nextInt(10)));
                fileName = id + fileExt.toLowerCase();
                logger.debug("上传图片新fileName：" + fileName);
                
                Map<String, String> resultMap = PicBucket.uploadFile(file, fileName);
                logger.debug("上传图片返回状态：" + resultMap.get("status"));
                if (resultMap.get("status").equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
                    String newUrlString = resultMap.get("url") + "";
                    result.addProperty("url", newUrlString);
                    result.addProperty("msg", "成功");
                    result.addProperty("originalName", "oldName");
                }
                else
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", "2"); // 文件上传失败
                }
                
            }
        }
        catch (Exception e)
        {
            logger.error("上传图片失败", e);
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", "2"); // 文件上传失败
        }
        return result.toString();
    }
    
    private final String ID_IS_ZERO = "0";
    
    private final String IS_APP = "1";
    
    private final String isApp_isAccountIdEmpty = "1";
    
    private void writeAppCookie(HttpServletRequest request, HttpServletResponse response, String info, int second)
    {
        // 写入用户cookie
        Cookie cookie = new Cookie("appinfo", info);
        cookie.setMaxAge(second); // 保留一个月
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    private void writeCartCookie(HttpServletRequest request, HttpServletResponse response, String ygguuid)
        throws Exception
    {
        String contextPath = ContextPathUtil.getCookiePath(request);
        // 写入用户cookie
        Cookie cookie = new Cookie("ygguuid", ygguuid);
        cookie.setMaxAge(60 * 60 * 24 * 30 * 6); // 保留半年
        // cookie.setPath(contextPath);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
