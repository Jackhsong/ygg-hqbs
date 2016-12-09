package com.ygg.webapp.controller;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.ErrorCodeEnum;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.exception.BusException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.sdk.weixin.util.Sha1Util;
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.service.CouponDetailService;
import com.ygg.webapp.service.OrderPayService;
import com.ygg.webapp.service.OrderService;
import com.ygg.webapp.service.ReceiveAddressService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.TempAccountService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.StringUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.ConfirmOrderView;
import com.ygg.webapp.view.FreightView;
import com.ygg.webapp.view.OrderLogisticsView;
import com.ygg.webapp.view.OrderProductView;
import com.ygg.webapp.view.OrderView;
import com.ygg.webapp.view.ReceiveAddressView;
import com.ygg.webapp.view.WeiXinPayReqView;

/**
 * 根据购物车生成一个定单
 * 
 * @author lihc
 *
 */
@Controller("orderController")
@RequestMapping("/order")
public class OrderController
{
    
    private Logger logger = Logger.getLogger(OrderController.class);
    
    @Resource(name = "orderService")
    private OrderService orderService;
    
    @Resource(name = "tempAccountService")
    private TempAccountService tempAccountService;
    
    // @Resource(name="cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "receiveAddressService")
    private ReceiveAddressService receiveAddressService;
    
    @Resource(name = "orderPayService")
    private OrderPayService orderPayService;
    
    @Resource
    private CouponDetailService couponDetailService;
    
    @Resource(name = "accountService")
    private AccountService accountService;
    /**    */
    @Resource(name="shoppingCartService")
    private ShoppingCartService shoppingCartService;
    /**
     * 定单确认 到此请求时用户一定是login的,但购物车有可能没有做合并
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/confirm/{ordertype}")
    public ModelAndView confirm(HttpServletRequest request, HttpServletResponse response,
        @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid, //
        @PathVariable("ordertype") String ordertype // 1：正常登陆购买，2：未登录下单登陆跳转；
    )
        throws Exception
    {
        ModelAndView mv = new ModelAndView("order/order");
        int accountId = CommonConstant.ID_NOT_EXIST;
        int tempAccountId = CommonConstant.ID_NOT_EXIST;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.setViewName("redirect:/user/tologin");
            mv.addObject("ordertype", ordertype);
            SessionUtil.removeCurrentOrderConfirmId(request.getSession());
            return mv;
        }
        else
        {
            accountId = av.getId();
            if (ordertype != null && ordertype.equals("2"))
            {
                if (ygguuid != null && !ygguuid.equals("") && !ygguuid.equals("tmpuuid"))
                {
                    tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
                }
                else
                {
                    BusException be = new BusException("临时账号不存在");
                    be.setViewName("redirect:/mycenter/show");
                    SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
                    SessionUtil.removeCurrentOrderConfirmId(request.getSession());
                    throw be;
                }
            }
        }
        String requestparams = "{'accountId':'" + accountId + "','cartToken':'" + tempAccountId + "','type':'" + ordertype + "'}";
        String responseparams = "";
        String addressId = "-1"; // －１没有收货地址，需要新增
        String isBonded = "0"; // 1表示保税区
        String endSecond = "-1";
        String errorCode = "";
        String confirmId = "";
        String status = CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
        List<ConfirmOrderView> covs = null;
        Object confirmOrderList = null;
        Object defaultAddress = null;
        ReceiveAddressView rav = null;
        Object tips = null;
        Set<String> tipsset = null;
        String isbondtips = "";
        String availablePoint = "0";
        String couponStr = "使用优惠券";
        String couponId = "0";
        
        // 标记返回内容是查询数据库得到还是从缓存中得到。
        boolean endSecondFlag = true;
        try
        {
            confirmId = SessionUtil.getCurrentOrderConfirmId(request.getSession());
            responseparams = this.cacheService.getCache(CacheConstant.ORDER_COMFIRM_ID_CACHE + confirmId);
            if (!(confirmId != null && !confirmId.equals("") && !confirmId.equals("0") && responseparams != null && !responseparams.equals("")))
            {
                responseparams = this.orderService.confirm(requestparams); // 此方法要加事务处理
                JsonParser parser = new JsonParser();
                JsonObject param = (JsonObject)parser.parse(responseparams);
                confirmId = (param.get("confirmId") == null ? "0" : param.get("confirmId").getAsString());
                if (confirmId != null && !confirmId.equals("") && !confirmId.equals("0"))
                {
                    this.cacheService.addCache(CacheConstant.ORDER_COMFIRM_ID_CACHE + confirmId, responseparams, 20 * 60); // 20分钟
                    SessionUtil.addOrderConfirmId(request.getSession(), confirmId);
                }
                endSecondFlag = false;
            }
            
            // 返回response中的信息给前端展示
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseparams);
            
            status = param.get("status") != null ? param.get("status").getAsString() : CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
            addressId = param.get("addressId") != null ? param.get("addressId").getAsString() : "";
            isBonded = param.get("isBonded") != null ? param.get("isBonded").getAsString() : "0";
            errorCode = param.get("errorCode") != null ? param.get("errorCode").getAsString() : "";
            endSecond = param.get("endSecond") != null ? param.get("endSecond").getAsString() : "-1";
            availablePoint = param.get("availablePoint") != null ? param.get("availablePoint").getAsString() : "0";
            confirmOrderList = param.get("confirmOrderList");
            defaultAddress = param.get("defaultAddress");
            tips = param.get("tips");
            if (confirmOrderList != null && !confirmOrderList.toString().equals(""))
            {
                covs = JSONUtils.fromJson(confirmOrderList.toString(), new TypeToken<List<ConfirmOrderView>>()
                {
                });
            }
            
            if (defaultAddress != null && !defaultAddress.toString().equals(""))
            {
                rav = JSONUtils.fromJson(defaultAddress.toString(), ReceiveAddressView.class);
            }
            if (tips != null && !tips.toString().equals(""))
            {
                tipsset = JSONUtils.fromJson(tips.toString(), new TypeToken<LinkedHashSet<String>>()
                {
                });
                for (String tip : tipsset)
                {
                    isbondtips = tip; // 取第一个
                    break;
                }
            }
            
        }
        catch (Exception e)
        {
            logger.error("order---confirm---error:", e);
            BusException be = new BusException(e.getMessage());
            be.setViewName("redirect:/spcart/listsc");
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession());
            throw be;
        }
        
        confirmId = SessionUtil.getCurrentOrderConfirmId(request.getSession());
        if (confirmId == null || confirmId.equals("0"))
        {
            // mv.setViewName("redirect:/order/list/1");
            // mv.setViewName("redirect:/order/pay/aliwxpayfail/"+);
            return mv;
        }
        
        // / 购物车过期
        if (errorCode != null && errorCode.equals(CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue()))
        {
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession());
            mv.setViewName("redirect:/spcart/listsc");
            mv.addObject("ordertype", ordertype);
            return mv;
        }
        
        /**
         * 时间要重新查询一次　endSecond 因为在提交订单时都会去选收货地址，所有运费每次要计算出来, 把选择的收货地址先保存在session中在支付时把它去掉同时也把confirmId也去掉
         */
        if (endSecondFlag)
        {
            // 1.1 从session拿出来后，要重新计算endSecond
            requestparams = "{'accountId':'" + accountId + "','tempAccountId':'" + tempAccountId + "','type':'" + ordertype + "'}";
            responseparams = this.orderService.findValidTimeByAIDORTAID(requestparams);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseparams);
            endSecond = param.get("endSecond") != null ? param.get("endSecond").getAsString() : "-1";
            if (endSecond == null || endSecond.equals("") || endSecond.equals("0") || endSecond.equals("-1"))
            {
                SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
                SessionUtil.removeCurrentOrderConfirmId(request.getSession());
                // 购物车过期 go to 列表信息
                mv.setViewName("redirect:/spcart/listsc");
                mv.addObject("ordertype", ordertype);
                return mv;
            }
            
            // 1.2从session拿出来后，要重新计算地址　
            String sessionAddressId = SessionUtil.getCurrentSelectedAddressId(request.getSession());
            if (sessionAddressId != null && !sessionAddressId.equals("") && !sessionAddressId.equals("-1"))
            {
                rav = this.receiveAddressService.findReceiveAddressViewById(Integer.parseInt(sessionAddressId));
                if (rav == null || rav.getId() <= 0)
                {
                    rav = this.receiveAddressService.findDefaultAddressByAccountId(accountId);
                    SessionUtil.addSelectedAddressId(request.getSession(), rav.getId() + "");
                }
            }
            else
            {
                rav = this.receiveAddressService.findDefaultAddressByAccountId(accountId);
                if (rav != null)
                    addressId = rav.getId() + "";
                SessionUtil.addSelectedAddressId(request.getSession(), addressId);
            }
            
            addressId = SessionUtil.getCurrentSelectedAddressId(request.getSession());
        }
        else
        {
            SessionUtil.addSelectedAddressId(request.getSession(), addressId);
        }
        DecimalFormat df = new DecimalFormat("0.00");
        // 1.3计算运费
        String ismailbag = "0"; // 运费为　0包邮 , 1 不包邮
        float allTotalPrice = 0.00f; // 所有总的费用，包括所有的邮费　
        float couponPrice = 0.00f; // 　allTotalPrice - coupon - integral
        
        if (covs != null && !covs.isEmpty())
        {
            for (ConfirmOrderView cov : covs)
            {
                int logisticsMoney = 0; // 单个订单的邮费 为整数
                float currTotalPrice = 0.00f; // 单个定单的所有总价，包括邮费
                
                List<FreightView> freights = cov.getFreights();
                if (freights != null && freights.size() > 0)
                {
                    for (FreightView fv : freights)
                    {
                        List<String> provinceIds = fv.getProvinceIds();
                        if (rav != null && provinceIds != null && !provinceIds.isEmpty())
                        {
                            String selectedAddressProvince = rav.getProvince();
                            if (selectedAddressProvince != null && !selectedAddressProvince.equals("") && provinceIds.contains(selectedAddressProvince))
                            {
                                logisticsMoney = Integer.parseInt(fv.getLogisticsMoney());
                                if (logisticsMoney > 0)
                                    ismailbag = "1"; // 有运费的话，不包邮
                                break;
                            }
                            else
                                ismailbag = "0"; // 有运费的话，不包邮
                        }
                    }
                }
                currTotalPrice = Float.parseFloat(cov.getTotalPrice()) + logisticsMoney; // 先把总价加上去，再加上邮费
                cov.setLogisticsMoney(logisticsMoney + ""); // 设置邮费
                cov.setAllTotalPrice(df.format(currTotalPrice));
                cov.setIsmailbag(ismailbag); // 设置是否包邮
                allTotalPrice += currTotalPrice;
            }
            
        }
        
        couponPrice = allTotalPrice;
        
        // 1.4计算优惠券金额 & 积分
        if (covs != null && !covs.isEmpty())
        {
            int selectCouponId = Integer.valueOf(SessionUtil.getCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession())));
            if (selectCouponId != -1)
            {
                Map<String, Object> couponInfo = couponDetailService.executeOrderCoupon(selectCouponId, accountId);
                if (couponInfo != null)
                {
                    couponId = selectCouponId + "";
                    int reducePirce = couponInfo.get("reducePirce") == null ? 0 : Integer.valueOf(couponInfo.get("reducePirce") + "");
                    int thresholdPrice = couponInfo.get("thresholdPrice") == null ? 0 : Integer.valueOf(couponInfo.get("thresholdPrice") + "");
                    int type = couponInfo.get("type") == null ? 1 : Integer.valueOf(couponInfo.get("type") + "");
                    if (type == 1)
                    {
                        couponStr = "满" + thresholdPrice + "减" + reducePirce;
                    }
                    else
                    {
                        couponStr = "立减￥" + reducePirce;
                    }
                    couponPrice -= reducePirce;
                    couponPrice = couponPrice < 0 ? 0.00f : couponPrice;
                }
            }
            
            // 获取积分
            AccountEntity ae = accountService.findAccountById(accountId);
            if (ae != null)
            {
                availablePoint = ae.getAvailablePoint() + "";
            }
            else
            {
                availablePoint = "0";
            }
        }
        else
        {
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
        }
        
        long canUsePoint = Math.round(Double.valueOf(couponPrice * 100 * 0.5));
        // /////////////////////////////////////1.3 end /////////////////////////
        JsonParser parser = new JsonParser();
        mv.addObject("ismailbag", ismailbag);
        mv.addObject("status", status);
        mv.addObject("addressId", addressId);
        mv.addObject("defaultAddress", rav);
        mv.addObject("isBonded", isBonded);
        mv.addObject("availablePoint", Long.valueOf(availablePoint) > canUsePoint ? canUsePoint + "" : availablePoint);
        mv.addObject("errorCode", errorCode);
        mv.addObject("endSecond", endSecond);
        mv.addObject("confirmOrderList", covs);
        mv.addObject("confirmId", confirmId);
        mv.addObject("ordertype", ordertype);
        mv.addObject("allTotalPrice", df.format(allTotalPrice)); // 总共定单的总价和
        mv.addObject("couponPrice", df.format(couponPrice)); // 优惠后总价格
        mv.addObject("isbondtips", isbondtips);
        mv.addObject("tips", tipsset);
        mv.addObject("couponStr", couponStr);
        mv.addObject("couponId", couponId);
        
        // //////////////////配置微信公众账号H5支付的配置config ////////////////////////////
        String weixin_pay_url_one = YggWebProperties.getInstance().getProperties("weixin_pay_url_one");
        weixin_pay_url_one = weixin_pay_url_one + "/" + ordertype;
        String resquestParams = "{'url':'" + weixin_pay_url_one + "','totalPrice':'" + allTotalPrice + "'}";
        // String responseParams = orderPayService.configPay(request, response, resquestParams) ;
        getWeiXinAccessToken(request, response, resquestParams, mv);
        
        // /////////////////////////// end///////////////////////////////////
        return mv;
    }
    
    /**
     * 定单新增
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/add")
    public ModelAndView orderAdd(
        HttpServletRequest request,
        @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "totalPrice", required = false, defaultValue = "0") String totalPrice, // 全部订单总价，包含邮费
        @RequestParam(value = "couponPrice", required = false, defaultValue = "0") String couponPrice, // 优惠后的订单总价，值为totalPrice减去积分和优惠的价格
        @RequestParam(value = "usedPoint", required = false, defaultValue = "0") String usedPoint, // 使用的积分数
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype,
        @RequestParam(value = "confirmId", required = false, defaultValue = "0") String confirmId,
        @RequestParam(value = "couponId", required = false, defaultValue = "0") String couponId, // 使用的优惠券ID
        @RequestParam(value = "addressId", required = false, defaultValue = "-1") String addressId,
        @RequestParam(value = "paytype", required = false, defaultValue = "2") String paytype // 1[银联]、2[支付宝]、3[微信])
    )
        throws Exception
    {
        
        ModelAndView mv = new ModelAndView();
        
        int accountId = CommonConstant.ID_NOT_EXIST;
        int tempAccountId = CommonConstant.ID_NOT_EXIST;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            // go to login
            mv.setViewName("redirect:/user/tologin");
            mv.addObject("ordertype", ordertype);
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession());
            return mv;
        }
        else
        {
            accountId = av.getId();
            tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
            
        }
        
        confirmId = SessionUtil.getCurrentOrderConfirmId(request.getSession()); // / confirm 定单过期处理
        if (confirmId == null || confirmId.equals("0"))
        {
            
            String orderIds = SessionUtil.getCurrentOrderId(request.getSession()); // this.cacheService.getCache(orderCacheKey)
                                                                                   // ;
            if (orderIds == null || orderIds.equals(""))
                mv.setViewName("redirect:/order/list/1");
            else
            {
                mv.setViewName("redirect:/order/pay/aliwxpayfail/" + orderIds);
                SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
                SessionUtil.removeCurrentOrderId(request.getSession());
            }
            return mv;
        }
        
        String oscValue = CommonUtil.getCookieValue(request, CommonConstant.OrderSoucrChannelKey);
        //zhangld 去掉判断 isQqbs
        String requestParams =
            "{'accountId':'" + accountId + "','cartToken':'" + tempAccountId + "','type':'" + ordertype + "','confirmId':'" + confirmId + "','addressId':'" + addressId
                + "','couponPrice':'" + couponPrice + "','usedPoint':'" + usedPoint + "','couponId':'" + couponId + "','totalPrice':'" + totalPrice + "','paytype':'" + paytype
                + "','osc':'" + oscValue + "'}";
        
        Object orderIdsJson = "";
        List<Integer> orderIds = null;
        try
        {
            String responseParams = this.orderService.add(requestParams);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            String status = CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
            String errorCode = "";
            String errorMessage = "";
            status = param.get("status") != null ? param.get("status").getAsString() : CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
            errorCode = param.get("errorCode") != null ? param.get("errorCode").getAsString() : "";
            errorMessage = param.get("errorMessage") != null ? param.get("errorMessage").getAsString() : "";
            orderIdsJson = param.get("orderIds");
            if (errorCode != null && !errorCode.equals(""))
            {
                if (errorCode.equals("0"))
                {
                    errorCode = "亲，新增订单错误,请刷新页面!";
                }
                else if (errorCode.equals("1") || errorCode.equals("2")) // 账号不存在
                {
                    mv.setViewName("redirect:/user/tologin");
                    return mv;
                }
                else if (errorCode.equals("4")) // 购物车过期
                {
                    mv.setViewName("redirect:/spcart/listsc");
                    return mv;
                }
                else if (errorCode.equals("3") || errorCode.equals("5")) // 地址不存在 身份证不合法
                {
                    mv.addObject("errorCode", errorMessage); // 身份证不合法,
                    mv.setViewName("forward:/order/confirm/" + ordertype);
                    return mv;
                }
                else if (errorCode.equals("10"))
                {
                    // 优惠券处理异常
                    SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
                    mv.addObject("errorCode", errorMessage);
                    mv.setViewName("forward:/order/confirm/" + ordertype);
                    return mv;
                }
                else if (errorCode.equals(ErrorCodeEnum.ORDER_ADD_ERRORCODE.UNSUPPORT_DELIVER_AREA.getErrorCode()))
                {
                    //不支持的配送地区
                    mv.addObject("errorCode", errorMessage);
                    mv.setViewName("forward:/order/confirm/" + ordertype);
                    return mv;
                }
                else
                {
                    mv.addObject("errorCode", errorMessage);
                    mv.setViewName("forward:/order/confirm/" + ordertype);
                    return mv;
                }
            }
            
            if (orderIdsJson != null)
            {
                orderIds = JSONUtils.fromJson(orderIdsJson.toString(), new TypeToken<List<Integer>>()
                {
                });
            }
        }
        catch (Exception e)
        {
            this.logger.error("OrderController-----add---err:", e);
            if (e instanceof ServiceException)
            {
                BusException se = new BusException("支付接口报错");
                se.setViewName("forward:/order/confirm/" + ordertype);
                se.setErrorCode("支付出错，请刷新页面!");
                throw se;
            }
            throw e;
        }
        
        mv.addObject("orderIdList", orderIds);
        mv.addObject("accountId", accountId);
        mv.setViewName("redirect:/order/topay/" + paytype);
        
        if (paytype != null && paytype.equals("3")) // 微信支付
            mv.setViewName("redirect:/order/topaywx/" + paytype);
        
        String orderIdsStr = "";
        if (orderIds != null)
        {
            for (Integer oId : orderIds)
                orderIdsStr += oId + ",";
        }
        
        if (orderIdsStr != null && !orderIdsStr.equals(""))
        {
            orderIdsStr = orderIdsStr.substring(0, orderIdsStr.length() - 1);
            
            SessionUtil.addCurrentOrderId(request.getSession(), orderIdsStr);
        }
        
        return mv;
    }
    
    /**
     * 微信定单新增,分两步完成 addoderstatus表示成功或失败
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/addajax")
    @ResponseBody
    public String orderAddAjax(
        HttpServletRequest request,
        @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "totalPrice", required = false, defaultValue = "0") String totalPrice,
        @RequestParam(value = "couponPrice", required = false, defaultValue = "0") String couponPrice, // 优惠后的订单总价，值为totalPrice减去积分和优惠的价格
        @RequestParam(value = "usedPoint", required = false, defaultValue = "0") String usedPoint, // 使用的积分数
        @RequestParam(value = "couponId", required = false, defaultValue = "0") String couponId, // 使用的优惠券ID
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype,
        @RequestParam(value = "confirmId", required = false, defaultValue = "0") String confirmId,
        @RequestParam(value = "addressId", required = false, defaultValue = "-1") String addressId,
        @RequestParam(value = "paytype", required = false, defaultValue = "2") String paytype // / //
                                                                                              // 1[银联]、2[支付宝]、3[微信])
    )
        throws Exception
    {
        JsonObject result = new JsonObject();
        
        int accountId = CommonConstant.ID_NOT_EXIST;
        int tempAccountId = CommonConstant.ID_NOT_EXIST;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession());
            result.addProperty("redirecturl", "/user/tologin");
            result.addProperty("ordertype", ordertype);
            result.addProperty("errorCode", "7"); //
            result.addProperty("addoderstatus", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        else
        {
            accountId = av.getId();
            tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
            
        }
        
        confirmId = SessionUtil.getCurrentOrderConfirmId(request.getSession());
        if (confirmId == null || confirmId.equals("0")) // 过期
        {
            // mv.setViewName("redirect:/order/list/1");
            result.addProperty("errorCode", "6"); // 定单过期
            result.addProperty("redirecturl", "/order/list/1");
            
            String orderIds = SessionUtil.getCurrentOrderId(request.getSession()); // this.cacheService.getCache(orderCacheKey)
                                                                                   // ;
            if (orderIds == null || orderIds.equals(""))
                result.addProperty("redirecturl", "/order/list/1");
            else
            {
                result.addProperty("redirecturl", "/order/pay/aliwxpayfail/" + orderIds);
                SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
                SessionUtil.removeCurrentOrderId(request.getSession());
            }
            
            result.addProperty("addoderstatus", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        String oscValue = CommonUtil.getCookieValue(request, CommonConstant.OrderSoucrChannelKey);
        //zhangld 去掉判断 isQqbs
        String requestParams =
            "{'accountId':'" + accountId + "','cartToken':'" + tempAccountId + "','type':'" + ordertype + "','confirmId':'" + confirmId + "','addressId':'" + addressId
                + "','couponPrice':'" + couponPrice + "','usedPoint':'" + usedPoint + "','couponId':'" + couponId + "','totalPrice':'" + totalPrice + "','paytype':'" + paytype
                + "','osc':'" + oscValue + "'}";
        Object orderIdsJson = "";
        List<Integer> orderIds = null;
        try
        {
            String responseParams = this.orderService.add(requestParams);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            String status = CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
            String errorCode = "";
            String errorMessage = "";
            status = param.get("status") != null ? param.get("status").getAsString() : CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
            errorCode = param.get("errorCode") != null ? param.get("errorCode").getAsString() : "";
            errorMessage = param.get("errorMessage") != null ? param.get("errorMessage").getAsString() : "";
            orderIdsJson = param.get("orderIds");
            if (errorCode != null && !errorCode.equals(""))
            {
                if (errorCode.equals("0"))
                {
                    // errorCode ="亲，新增订单错误,请刷新页面!" ;
                }
                else if (errorCode.equals("1") || errorCode.equals("2")) // 账号不存在
                {
                    result.addProperty("redirecturl", "/user/tologin");
                }
                else if (errorCode.equals("4")) // 购物车过期
                {
                    result.addProperty("redirecturl", "/spcart/listsc");
                }
                else if (errorCode.equals("3") || errorCode.equals("5")) // 3 地址不存在 5 身份证不合法
                {
                    result.addProperty("redirecturl", "/order/confirm/" + ordertype);
                }
                else if (errorCode.equals(ErrorCodeEnum.ORDER_ADD_ERRORCODE.UNSUPPORT_DELIVER_AREA.getErrorCode()))
                {
                    result.addProperty("redirecturl", "/order/confirm/" + ordertype);
                    result.addProperty("errorMessage", errorMessage);
                }
                result.addProperty("errorCode", errorCode);
                result.addProperty("addoderstatus", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                return result.toString();
            }
            
            if (orderIdsJson != null)
                orderIds = JSONUtils.fromJson(orderIdsJson.toString(), new TypeToken<List<Integer>>()
                {
                });
            
        }
        catch (Exception e)
        {
            this.logger.error("OrderController-----add---err:", e);
            if (e instanceof ServiceException)
            {
                BusException se = new BusException("支付接口报错");
                se.setViewName("forward:/order/confirm/" + ordertype);
                se.setErrorCode("支付出错，请刷新页面!");
                throw se;
            }
            throw e;
        }
        
        result.addProperty("accountId", accountId);
        
        String orderIdArray = "";
        if (orderIds != null && !orderIds.isEmpty())
        {
            for (Integer oId : orderIds)
                orderIdArray += oId + ",";
        }
        
        if (orderIdArray != null && !orderIdArray.equals(""))
        {
            orderIdArray = orderIdArray.substring(0, orderIdArray.length() - 1);
            
            SessionUtil.addCurrentOrderId(request.getSession(), orderIdArray);
            result.addProperty("orderIdList", orderIdArray);
        }
        
        // result.addProperty("redirecturl", "/order/topay/"+paytype);
        // if(paytype!=null && paytype.equals("3")) //微信支付
        result.addProperty("redirecturl", "/order/topaywx/3");
        
        result.addProperty("addoderstatus", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    /**
     * 显示我的定单
     * 
     * @param request
     * @param type
     * @throws Exception
     */
    @RequestMapping("/list/{type}")
    // type是定单的状态
    public ModelAndView listMyOrder(HttpServletRequest request, @PathVariable("type") String type)
        throws Exception
    {
        
        ModelAndView mv = new ModelAndView();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            // go to login
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        
//        String cartCount = SessionUtil.getCartCount(request.getSession());
//        if(StringUtils.isBlank(cartCount)){
//            String responseparams = shoppingCartService.findShoppingCartCountByAccountId(av.getId());
//            JsonParser parser = new JsonParser();
//            JsonObject param = (JsonObject)parser.parse(responseparams);
//            String cartc = param.get("cartCount").getAsString();
//            SessionUtil.addCartCount(request.getSession(), cartc);
//        }
        String requestParams = "{'accountId':'" + av.getId() + "','type':'" + type + "','page':'1'}";
        String responseParams = this.orderService.list(requestParams);
        
        JsonParser parser = new JsonParser();
        List<OrderView> ovs = null;
        JsonObject param = (JsonObject)parser.parse(responseParams);
        
        String status = "1";
        String errorCode = "";
        String orderCount = "0";
        Object orderList = null;
        status = param.get("status") == null ? "0" : param.get("status").getAsString();
        errorCode = param.get("errorCode") == null ? "" : param.get("errorCode").getAsString();
        orderCount = param.get("orderCount") == null ? "0" : param.get("orderCount").getAsString();
        orderList = param.get("orderList");
        if (orderList != null)
            ovs = JSONUtils.fromJson(orderList.toString(), new TypeToken<List<OrderView>>()
            {
            });
        
        mv.addObject("orderCount", orderCount);
        mv.addObject("orderList", ovs);
        mv.addObject("type", type); // 定单的状态
        mv.setViewName("order/myorder");
        return mv;
    }
    
    /**
     * 确认收货
     * 
     * @param request
     * @param orderid
     * @return
     * @throws Exception
     */
    @RequestMapping("/modifyokorder/{orderid}")
    public ModelAndView okOrder(HttpServletRequest request, @PathVariable("orderid") String orderid)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/order/list/3");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.setViewName("/user/tologin");
            return mv;
        }
        String requestParams = "{'accountId':'" + av.getId() + "','orderId':'" + orderid + "','status':'3'}"; // status
                                                                                                              // =3 已发货
        String responseParams = this.orderService.modify(requestParams);
        
        return mv;
    }
    
    /**
     * 定单修改状态
     * 
     * @param request
     * @param orderid
     * @return
     * @throws Exception
     */
    @RequestMapping("/modify/{orderid}/{status}")
    @ResponseBody
    public String orderModify(HttpServletRequest request, @PathVariable("orderid") String orderid, @PathVariable("status") String orderstatus)
        throws Exception
    {
        JsonObject result = new JsonObject();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            result.addProperty("errorCode", 100);
            return result.toString();
        }
        
        String requestParams = "{'accountId':'" + av.getId() + "','orderId':'" + orderid + "','status':'" + orderstatus + "'}";
        String responseParams = this.orderService.modify(requestParams);
        String status = "1";
        String errorCode = "-1";
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        status = param.get("status") == null ? "1" : param.get("status").getAsString();
        errorCode = param.get("errorCode") == null ? "-1" : param.get("errorCode").getAsString();
        result.addProperty("status", status);
        result.addProperty("errorCode", errorCode);
        return result.toString();
    }
    
    /**
     * 查看商品的物流信息 1 显示此页面中的title， 0不显示title
     * 
     * @param request
     * @param orderid
     * @param isshowtitle：0，1，10（退款退货中查看物流）
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/logistic/{orderId}/{isshowtitle}")
    public ModelAndView orderLogistic(HttpServletRequest request, @PathVariable("orderId") String orderid, @PathVariable("isshowtitle") String isshowtitle,
        HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (isshowtitle != null && isshowtitle.equals("1")) // 是本应用调用，传0是Native app调用时，不需要验证login
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            if (av == null)
            {
                // go to login
                mv.setViewName("redirect:/user/tologin");
                return mv;
            }
        }
        
        String responseParams = this.orderService.logisticsDetail("{'orderId':'" + orderid + "','source':'" + isshowtitle + "'}");
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        Object logisticsDetailJson = param.get("logisticsDetail") == null ? null : param.get("logisticsDetail");
        Object logisticsinfoJson = param.get("logisticsinfo") == null ? null : param.get("logisticsinfo");
        
        List<Map<String, String>> logisticsDetailList = null;
        OrderLogisticsView olv = null;
        if (logisticsDetailJson != null)
            logisticsDetailList = JSONUtils.fromJson(logisticsDetailJson.toString(), new TypeToken<List<Map<String, String>>>()
            {
            });
        
        if (logisticsinfoJson != null)
            olv = JSONUtils.fromJson(logisticsinfoJson.toString(), OrderLogisticsView.class);
        
        mv.addObject("logisticsDetailList", logisticsDetailList);
        mv.addObject("logistics", olv);
        if (isshowtitle.equals("10"))
        {
            isshowtitle = "1";
        }
        mv.addObject("isshowtitle", isshowtitle);
        mv.setViewName("order/logistic");
        return mv;
    }
    
    /**
     * 定单详情url
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/orderdetail/{orderid}")
    public ModelAndView orderDetail(HttpServletRequest request, @PathVariable("orderid") String orderid,
        @RequestParam(value = "type", required = false, defaultValue = "0") String listOrderType,
        @RequestParam(value = "status", required = false, defaultValue = "100") String orderReturnStatus, // 这个是在订单详情页面时支付回调时的参数
        HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            // go to login
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        
        String requestParams = "{'accountId':'" + av.getId() + "','orderId':'" + orderid + "'}";
        String responseParams = this.orderService.detail(requestParams);
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        String status = "0";
        String errorCode = "";
        status = param.get("status") == null ? "0" : param.get("status").getAsString();
        errorCode = param.get("errorCode") == null ? "" : param.get("errorCode").getAsString();
        if (errorCode != null && !errorCode.equals(""))
        {
            mv.setViewName("redirect:/mycenter/show");
            return mv;
        }
        String orderStatus = "", orderNumber = "",freightPrice = "",totalPrice = "",realPrice = "",
        accountPointPrice = "", couponPrice = "",sellerName = "",sendAddress = "",sellerType = "",bondedNumType = "",addressId = "", createTime = "",
        endSecond = "", logisticsUrl = "",paytype = "2", orderId = "",onetotalPrice = "",logisticsChannel = "", logisticsNumber = "",isBonded = "0";
        Object logisticsDetailJson = null;
        List<Map<String, String>> logisticsDetailList = null;
        String canUseCoupon = "1";
        
        Object orderAddress = null;
        ReceiveAddressView rav = null;
        Object orderProductListJson = null;
        List<OrderProductView> orderProductList = null;
        orderStatus = param.get("orderStatus") == null ? "1" : param.get("orderStatus").getAsString();
        orderNumber = param.get("orderNumber") == null ? "" : param.get("orderNumber").getAsString();
        freightPrice = param.get("freightPrice") == null ? "" : param.get("freightPrice").getAsString();
        realPrice = param.get("realPrice") == null ? "" : param.get("realPrice").getAsString();
        accountPointPrice = param.get("accountPointPrice") == null ? "" : param.get("accountPointPrice").getAsString();
        couponPrice = param.get("couponPrice") == null ? "" : param.get("couponPrice").getAsString();
        totalPrice = param.get("totalPrice") == null ? "" : param.get("totalPrice").getAsString();
        sellerName = param.get("sellerName") == null ? "" : param.get("sellerName").getAsString();
        sendAddress = param.get("sendAddress") == null ? "" : param.get("sendAddress").getAsString();
        sellerType = param.get("sellerType") == null ? "" : param.get("sellerType").getAsString();
        bondedNumType = param.get("bondedNumType") == null ? "" : param.get("bondedNumType").getAsString();
        addressId = param.get("addressId") == null ? "" : param.get("addressId").getAsString();
        createTime = param.get("createTime") == null ? "" : param.get("createTime").getAsString();
        endSecond = param.get("endSecond") == null ? "" : param.get("endSecond").getAsString();
        logisticsUrl = param.get("logisticsUrl") == null ? "" : param.get("logisticsUrl").getAsString();
        paytype = param.get("paytype") == null ? "" : param.get("paytype").getAsString();
        orderId = param.get("orderId") == null ? "" : param.get("orderId").getAsString();
        onetotalPrice = param.get("onetotalPrice") == null ? "" : param.get("onetotalPrice").getAsString();
        logisticsChannel = param.get("logisticsChannel") == null ? "" : param.get("logisticsChannel").getAsString();
        logisticsNumber = param.get("logisticsNumber") == null ? "" : param.get("logisticsNumber").getAsString();
        isBonded = param.get("isBonded") == null ? "" : param.get("isBonded").getAsString();
        orderAddress = param.get("orderAddress") == null ? null : param.get("orderAddress");
        orderProductListJson = param.get("orderProductList") == null ? null : param.get("orderProductList");
        logisticsDetailJson = param.get("logisticsDetail") == null ? null : param.get("logisticsDetail");
        canUseCoupon = param.get("canUseCoupon") == null ? "1" : param.get("canUseCoupon").getAsString();
        
        if (orderAddress != null)
            rav = JSONUtils.fromJson(orderAddress.toString(), ReceiveAddressView.class);
        if (orderProductListJson != null)
            orderProductList = JSONUtils.fromJson(orderProductListJson.toString(), new TypeToken<List<OrderProductView>>()
            {
            });
        if (logisticsDetailJson != null)
            logisticsDetailList = JSONUtils.fromJson(logisticsDetailJson.toString(), new TypeToken<List<Map<String, String>>>()
            {
            });
        mv.addObject("logisticsChannel", logisticsChannel);
        mv.addObject("logisticsNumber", logisticsNumber);
        mv.addObject("logisticsDetailList", logisticsDetailList);
        
        mv.addObject("orderStatus", orderStatus);
        mv.addObject("orderNumber", orderNumber);
        mv.addObject("freightPrice", freightPrice);
        mv.addObject("totalPrice", totalPrice);
        mv.addObject("realPrice", realPrice);
        mv.addObject("accountPointPrice", accountPointPrice);
        mv.addObject("couponPrice", couponPrice);
        mv.addObject("orderNumber", orderNumber);
        mv.addObject("sellerName", sellerName);
        mv.addObject("sendAddress", sendAddress);
        mv.addObject("sellerType", sellerType);
        mv.addObject("bondedNumType", bondedNumType);
        mv.addObject("addressId", addressId);
        mv.addObject("orderAddress", rav);
        mv.addObject("createTime", createTime);
        mv.addObject("endSecond", endSecond);
        mv.addObject("logisticsUrl", logisticsUrl);
        mv.addObject("paytype", paytype);
        mv.addObject("orderId", orderId);
        mv.addObject("onetotalPrice", onetotalPrice);
        mv.addObject("isBonded", isBonded);
        mv.addObject("orderReturnStatus", orderReturnStatus);
        mv.addObject("canUseCoupon", canUseCoupon);
        
        mv.addObject("orderProductList", orderProductList);
        // ////////////////////
        String weixin_pay_url_two = YggWebProperties.getInstance().getProperties("weixin_pay_url_two");
        weixin_pay_url_two = weixin_pay_url_two + "/" + orderid;
        String resquestParams = "{'url':'" + weixin_pay_url_two + "','totalPrice':'" + onetotalPrice + "'}";
        getWeiXinAccessToken(request, response, resquestParams, mv);
        // ////////////////////end///////////////////
        
        //返回详情页面移除OrderRefundProduct的session
        SessionUtil.removeOrderRefundProduct(request.getSession());

        mv.addObject("listOrderType", listOrderType);
        mv.setViewName("order/orderdetail");
        return mv;
    }
    
    /**
     * 微信支付请求
     * 
     * @param request
     * @param paytype
     * @param orderIds
     * @param orderId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/topaywx/{paytype}")
    @ResponseBody
    public String orderPaymentWeiXin(HttpServletRequest request, @PathVariable("paytype") String paytype,
        @RequestParam(value = "orderIdList", required = false, defaultValue = "") List<Integer> orderIds,
        @RequestParam(value = "orderId", required = false, defaultValue = "0") int orderId,
        // @RequestParam(value="orderIdWXs",required=false,defaultValue="") String orderIdWXs , // 从微信请求而来
        HttpServletResponse response)
        throws Exception
    {
        JsonObject result = new JsonObject();
        int accountId = CommonConstant.ID_NOT_EXIST;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            // go to login
            result.addProperty("errorCode", "1");
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession());
            return result.toString();
            
        }
        else
        {
            accountId = av.getId();
        }
        
        // 付款来源
        int sourceType = CommonEnum.ORDER_PAY_SOURCE_TYPE.CONFIRM.getValue();
        if (orderId > 0)
        {
            sourceType = CommonEnum.ORDER_PAY_SOURCE_TYPE.DETAIL.getValue();
        }
        
        if (orderIds != null && orderId > 0)
        {
            orderIds.add(orderId);
        }
        
        // / 从微信而来
        
        String orderArrayIds = "";
        for (Integer oId : orderIds)
        {
            orderArrayIds += oId.intValue() + ",";
        }
        
        try
        {
            String remoteIp = request.getRemoteAddr(); // 有的是ipv6 CommonUtil.getRemoteIpAddr(request) ; //
                                                       // request.getRemoteHost() ; // request.getRemoteAddr();
            String reqeustParams =
                "{'accountId':'" + accountId + "','channel':'" + paytype + "','sourceType':'" + sourceType + "','ipAddress':'" + remoteIp + "','orderIdList':" + orderIds + "}";
            String responseParams = this.orderService.pay(reqeustParams, request, response);
            // this.logger.info("----OrderServiceController----------topaywx------responseParams----is: " +
            // responseParams);
            String status = "";
            String needPay = "1";
            Object wxprvJson = null;
            String errorCode = "";
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            status = param.get("status") == null ? "0" : param.get("status").getAsString();
            needPay = param.get("needPay") == null ? "1" : param.get("needPay").getAsString();
            errorCode = param.get("errorCode") == null ? "0" : param.get("errorCode").getAsString();
            wxprvJson = param.get("wxprv") == null ? null : param.get("wxprv");
            
            if ("1".equals(needPay))
            {
                if (wxprvJson != null)
                {
                    WeiXinPayReqView wxprv = JSONUtils.fromJson(wxprvJson.toString(), WeiXinPayReqView.class);
                    
                    if (orderArrayIds != null && !orderArrayIds.equals(""))
                    {
                        orderArrayIds = orderArrayIds.substring(0, orderArrayIds.length() - 1);
                        // result.addProperty("orderIds", orderArrayIds);
                        wxprv.setOrderIds(orderArrayIds);
                    }
                    
                    result.add("wxprv", new JsonParser().parse(JSONUtils.toJson(wxprv, false)));
                }
            }
            else
            {
                status = "2";
            }
            
            result.addProperty("status", status);
            result.addProperty("errorCode", errorCode);
            
        }
        catch (Exception e)
        {
            String msg = "支付请求出错!";
            this.logger.error(msg, e);
            BusException be = new BusException();
            be.putModelObject("errorCode", msg);
            throw be;
        }
        finally
        {
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession()); // 支付成功后清除
            SessionUtil.removeCurrentSelectedAddres(request.getSession());
        }
        
        return result.toString();
    }
    
    /**
     * 支付宝和银联接口一样，微信单独写 支付请求接口，生成支付url，跳到支付URL地址上去 判断定单是否超时，如果超时了要回退
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/topay/{paytype}")
    public ModelAndView orderPayment(HttpServletRequest request, @PathVariable("paytype") String paytype,
        @RequestParam(value = "orderIdList", required = false, defaultValue = "") List<Integer> orderIdList,// 新增订单重定向而来
        @RequestParam(value = "orderId", required = false, defaultValue = "0") int orderId, // 详情页而来
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,//
        // @RequestParam(value = "orderSource", required = false, defaultValue = "1") int orderSource,
        // //orderSource=1是从订单确认页面来,2是从订单详情页面中来
        HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        String viewName = "order/topay";
        // ///////为解决从微信中发起支付宝支付时要login的问题//////////
        
        // 付款来源
        int sourceType = CommonEnum.ORDER_PAY_SOURCE_TYPE.CONFIRM.getValue();
        if (orderId > 0)
        {
            sourceType = CommonEnum.ORDER_PAY_SOURCE_TYPE.DETAIL.getValue();
        }
        
        if (orderIdList != null && orderId > 0)
        {
            orderIdList.add(orderId);
        }
        try
        {
            String remoteIp = request.getRemoteAddr(); // 有的是ipv6 CommonUtil.getRemoteIpAddr(request) ; //
                                                       // request.getRemoteHost() ; // request.getRemoteAddr();
            String reqeustParams =
                "{'accountId':'" + accountId + "','channel':'" + paytype + "','sourceType':'" + sourceType + "','ipAddress':'" + remoteIp + "','orderIdList':" + orderIdList + "}";
            String responseParams = this.orderService.pay(reqeustParams, request, response);
            String requestUrl = "";
            String status = "0";
            String needPay = "1";
            String errorCode = "";
            String errorMsg = "";
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            status = param.get("status") == null ? "0" : param.get("status").getAsString();
            needPay = param.get("needPay") == null ? "1" : param.get("needPay").getAsString();
            errorCode = param.get("errorCode") == null ? "0" : param.get("errorCode").getAsString();
            requestUrl = param.get("requestUrl") == null ? "" : param.get("requestUrl").getAsString();
            
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
            {
                if ("0".equals(needPay))
                {
                    viewName = "order/trade";
                    String orderNumber = param.get("orderNumber") == null ? "0" : param.get("orderNumber").getAsString();
                    String totalPrice = param.get("totalPrice") == null ? "0" : param.get("totalPrice").getAsString();
                    String transactionTime = param.get("transactionTime") == null ? "0" : param.get("transactionTime").getAsString();
                    // 不需要支付 直接跳转到成功页面
                    mv.addObject("hasMoreThanOneOrder", orderIdList.size() > 1 ? "1" : "0");
                    mv.addObject("failOrderCount", orderIdList.size() > 1 ? (orderIdList.size() - 1) + "" : "1");
                    mv.addObject("status", status);
                    mv.addObject("orderNumber", orderNumber);
                    // mv.addObject("paytype", paytype);
                    mv.addObject("endSecond", "0");
                    mv.addObject("totalPrice", totalPrice);
                    mv.addObject("transactionTime", transactionTime);
                    mv.addObject("orderId", orderId);
                }
                else
                {
                    mv.addObject("requestUrl", requestUrl);
                }
            }
            else
            {
                if (errorCode != null && !errorCode.equals(""))
                {
                    if (errorCode.equals(CommonEnum.ORDER_PAY_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
                        errorMsg = "亲，账号不存在";
                    else if (errorCode.equals(CommonEnum.ORDER_PAY_ERRORCODE.ORDERID_NOT_EXIST.getValue()))
                        errorMsg = "亲，定单已过期";
                    else if (errorCode.equals(CommonEnum.ORDER_PAY_ERRORCODE.CHANNEL_NOT_EXIST.getValue()))
                        errorMsg = "亲，支付方式不存在";
                    else if (errorCode.equals(CommonEnum.ORDER_PAY_ERRORCODE.ORDER_STATUS_INVALID.getValue()))
                        errorMsg = "亲，订单状态错误";
                    mv.addObject("errorCode", errorCode);
                    mv.addObject("errorMsg", errorMsg);
                }
            }
        }
        catch (Exception e)
        {
            String msg = "支付请求出错!";
            this.logger.error(msg, e);
            BusException be = new BusException();
            be.putModelObject("errorCode", msg);
            be.setViewName(viewName);
            throw be;
        }
        finally
        {
            SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
            SessionUtil.removeCurrentOrderConfirmId(request.getSession()); // 支付成功后清除
            SessionUtil.removeCurrentSelectedAddres(request.getSession());
        }
        
        boolean flag = CommonUtil.isWeiXinBrower(request);
        if (flag && paytype.equals(CommonEnum.ORDER_PAY_CHANNEL.ALIPAY.getValue() + "")) // 在微信中，又选的是支付宝支付的话
        {
            mv.setViewName("order/aliwxpay");
            String orderIdsInAliPage = "";
            for (Integer oid : orderIdList)
            {
                orderIdsInAliPage += oid + ",";
            }
            orderIdsInAliPage = orderIdsInAliPage.substring(0, orderIdsInAliPage.length() - 1);
            mv.addObject("orderId", orderIdsInAliPage);
            this.logger.info("---OrderController----orderPayment---orderIds---is: " + orderIdsInAliPage);
        }
        else
            mv.setViewName(viewName);
        return mv;
    }
    
    /**
     * 支付详情url
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/paydetail")
    public ModelAndView orderPayDetail(HttpServletRequest request)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        int accountId = CommonConstant.ID_NOT_EXIST;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            // go to login
            mv.setViewName("redirect:/user/tologin");
            mv.addObject("ordertype", 1);
            return mv;
        }
        else
        {
            accountId = av.getId();
        }
        
        return mv;
    }
    
    /**
     * 定单支付请求的后台回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/tenpaycallback")
    public void tenpayNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        try
        {
            String responseParams = this.orderService.weixinNotifyCallBack(request, response);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            String status = "1";
            status = param.get("status") == null ? "0" : param.get("status").getAsString();
            if (status.equals("0"))
                this.logger.error("------tenpayNotifyCallBack----失败");
        }
        catch (Exception e)
        {
            this.logger.error("------tenpayNotifyCallBack----e ", e);
        }
        finally
        {
        }
        this.logger.info("---OrderController---tenpayNotifyCallBack----success");
    }
    
    @RequestMapping("/pay/weixinreturnback/{orderIds}")
    public ModelAndView weixinReturnCallBack(HttpServletRequest request, HttpServletResponse response, @PathVariable("orderIds") String orderIds)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("order/trade");
        
        try
        {
            String requestParams = "{'orderIds':'" + orderIds + "'}";
            String responseParams = this.orderService.weixinReturnCallBack(request, response, requestParams);
            getPayCallBackInfomation(mv, responseParams);
            
        }
        catch (Exception e)
        {
            logger.error("---OrderController--------tenpayReturnCallBack-----", e);
        }
        return mv;
    }
    
    /**
     * 财付通完成后跳转到ygg的url
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/tenpayreturnback")
    public ModelAndView tenpayReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("order/trade");
        
        try
        {
            String responseParams = this.orderService.weixinReturnCallBack(request, response, "{'orderIds':'0'}");
            getPayCallBackInfomation(mv, responseParams);
            
        }
        catch (Exception e)
        {
            logger.error("---OrderController--------tenpayReturnCallBack-----", e);
        }
        return mv;
    }
    
    /**
     * 支付宝 的同步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/alicallback")
    public ModelAndView syncPayCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("order/trade");
        
        try
        {
            
            String responseParams = this.orderService.aliReturnCallBack(request, response);
            getPayCallBackInfomation(mv, responseParams);
        }
        catch (Exception e)
        {
            logger.error("---OrderController--------tenpayReturnCallBack-----", e);
        }
        
        return mv;
    }
    
    /**
     * 国际 支付宝 的同步回调接口
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/aliGlobalcallback")
    public ModelAndView aliGlobalcallback(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("order/trade");
        
        try
        {
            
            String responseParams = this.orderService.aliGlobalReturnCallBack(request, response);
            getPayCallBackInfomation(mv, responseParams);
        }
        catch (Exception e)
        {
            logger.error("---OrderController--------tenpayReturnCallBack-----", e);
        }
        
        return mv;
    }
    
    /**
     * 订单详情页面中的 支付宝 的同步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    // @RequestMapping("/pay/alicallbackod")
    /*
     * public ModelAndView syncOrderDetilPayCallBack(HttpServletRequest request, HttpServletResponse response ) throws
     * Exception { ModelAndView mv = new ModelAndView(); // mv.setViewName("order/trade"); String orderId =""; String
     * status = ""; try { String responseParams = this.orderService.aliReturnCallBack(request, response); JsonParser
     * parser = new JsonParser(); JsonObject param = (JsonObject)parser.parse(responseParams); orderId =
     * param.get("orderId") == null ? "" : param.get("orderId").getAsString(); status = param.get("status") == null ?
     * "0" : param.get("status").getAsString(); logger.info("syncOrderDetilPayCallBack ---orderId is: " + orderId);
     * mv.setViewName("redirect:/order/orderdetail/"+orderId+"?status="+status); // getPayCallBackInfomation(mv,
     * responseParams); } catch (Exception e) { logger.error("---OrderController--------tenpayReturnCallBack-----", e);
     * }
     * 
     * return mv; }
     */
    
    /**
     * 支付宝 在微信中支付时,弹出一个order/aliwxpay 时中调用
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/aliwxpayfail/{orderId}")
    public ModelAndView aliPayFailInWeXin(@PathVariable("orderId") String orderId, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (orderId == null || orderId.equals("") || orderId.equals("0"))
        {
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        
        mv.setViewName("order/trade");
        try
        {
            String requestParams = "{'orderIds':'" + orderId + "'}";
            String responseParams = this.orderService.aliPayFailInWeXin(requestParams);
            getPayCallBackInfomation(mv, responseParams);
        }
        catch (Exception e)
        {
            logger.error("---OrderController--------aliPayFailInWeXin-----", e);
        }
        
        return mv;
    }
    
    /**
     * 银联的同步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pay/unioncallback")
    public ModelAndView syncUnionPayCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("order/trade");
        
        try
        {
            String responseParams = this.orderService.unionReturnCallBack(request, response);
            getPayCallBackInfomation(mv, responseParams);
        }
        catch (Exception e)
        {
            logger.error("---OrderController--------syncUnionPayCallBack-----", e);
        }
        
        return mv;
    }
    
    /**
     * 订单详情页面中的 银联的同步回调接口
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    /*
     * @RequestMapping("/pay/unioncallbackod") public ModelAndView syncUnionPayCallBackOrderDetail(HttpServletRequest
     * request, HttpServletResponse response ) throws Exception { ModelAndView mv = new ModelAndView();
     * //mv.setViewName("order/trade"); String orderId =""; String status = ""; try { String responseParams =
     * this.orderService.unionReturnCallBack(request, response); JsonParser parser = new JsonParser(); JsonObject param
     * = (JsonObject)parser.parse(responseParams); orderId = param.get("orderId") == null ? "" :
     * param.get("orderId").getAsString(); status = param.get("status") == null ? "0" :
     * param.get("status").getAsString(); mv.setViewName("redirect:/order/orderdetail/"+orderId+"?status="+status); //
     * getPayCallBackInfomation(mv, responseParams); } catch (Exception e) {
     * logger.error("---OrderController--------syncUnionPayCallBack-----", e); } return mv; }
     */
    
    /**
     * 支付宝 的异步回调接口
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/pay/alinotifycallback")
    public void syncPayNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        PrintWriter pw = response.getWriter();
        try
        {
            String responseParams = this.orderService.aliNotifyCallBack(request, response);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            String status = "1";
            status = param.get("status") == null ? CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue() : param.get("status").getAsString();
            
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
            {
                this.logger.error("------syncPayNotifyCallBack----失败");
                pw.println("fail");
            }
            else
            {
                pw.println("success");
            }
            
        }
        catch (Exception e)
        {
            this.logger.error("------syncPayNotifyCallBack----e ", e);
            pw.println("fail");
        }
        finally
        {
            pw.close();
        }
        this.logger.info("---OrderController---syncPayNotifyCallBack----success");
    }
    
    /**
     * 支付宝 的异步回调接口
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/pay/aliGlobalNotifycallback")
    public void syncPayGlobalNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        PrintWriter pw = response.getWriter();
        try
        {
            String responseParams = this.orderService.aliGlobalNotifyCallBack(request, response);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            String status = "1";
            status = param.get("status") == null ? CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue() : param.get("status").getAsString();
            
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
            {
                this.logger.error("------syncPayGlobalNotifyCallBack----失败");
                pw.println("fail");
            }
            else
            {
                pw.println("success");
            }
            
        }
        catch (Exception e)
        {
            this.logger.error("------syncPayGlobalNotifyCallBack----e ", e);
            pw.println("fail");
        }
        finally
        {
            pw.close();
        }
        this.logger.info("---OrderController---syncPayGlobalNotifyCallBack----success");
    }
    
    /**
     * 银联的异步回调接口
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/pay/unionnotifycallback")
    public void syncUnionPayNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        try
        {
            String responseParams = this.orderService.unionNotifyCallBack(request, response);
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(responseParams);
            String status = "1";
            status = param.get("status") == null ? "0" : param.get("status").getAsString();
            if (status.equals("0"))
                this.logger.error("------syncUnionPayNotifyCallBack----失败");
        }
        catch (Exception e)
        {
            this.logger.error("------syncUnionPayNotifyCallBack----e ", e);
        }
        finally
        {
        }
        this.logger.info("---OrderController---syncUnionPayNotifyCallBack----success");
    }
    
    private void getPayCallBackInfomation(ModelAndView mv, String responseParams)
    {
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        String status = "1";
        String orderNumber = "";
        String paytype = "";
        String endSecond = ""; // 支付失败后，显示
        String totalPrice = "";
        String transactionTime = ""; // 交易时间
        String orderId = "";
        String trade_no = "";
        String hasMoreThanOneOrder = "1"; // 有多个定单
        String failOrderCount = "0";
        // 供百度订单统计使用
        String pushOrderIds = "";
        List<Map<String, Object>> pushOrderList = new ArrayList<Map<String, Object>>();
        
        status = param.get("status") == null ? "0" : param.get("status").getAsString();
        orderNumber = param.get("orderNumber") == null ? "" : param.get("orderNumber").getAsString();
        paytype = param.get("paytype") == null ? "" : param.get("paytype").getAsString();
        endSecond = param.get("endSecond") == null ? "" : param.get("endSecond").getAsString();
        totalPrice = param.get("totalPrice") == null ? "" : param.get("totalPrice").getAsString();
        transactionTime = param.get("transactionTime") == null ? "" : param.get("transactionTime").getAsString();
        orderId = param.get("orderId") == null ? "" : param.get("orderId").getAsString();
        trade_no = param.get("trade_no") == null ? "" : param.get("trade_no").getAsString();
        hasMoreThanOneOrder = param.get("hasMoreThanOneOrder") == null ? "" : param.get("hasMoreThanOneOrder").getAsString();
        
        try
        {
            if ("1".equals(hasMoreThanOneOrder))
            {
                failOrderCount = param.get("failOrderCount") == null ? "0" : param.get("failOrderCount").getAsString();
                // 包含多个订单
                pushOrderIds = param.get("pushOrderIds").getAsString();
                List<Integer> backIds = (List<Integer>)JSON.parse(pushOrderIds);
                for (Integer id : backIds)
                {
                    Map<String, Object> map = orderService.findOrderProductInfosByOId(id);
                    pushOrderList.add(map);
                }
            }
            else if (!"".equals(orderId))
            {
                // 只包含一个订单，将订单orderId封装进pushOrderList
                Map<String, Object> map = orderService.findOrderProductInfosByOId(Integer.valueOf(orderId));
                pushOrderList.add(map);
            }
        }
        catch (Exception e)
        {
            logger.error("生成'百度订单统计'所需订单信息失败!", e);
        }
        
        mv.addObject("pushOrderList", pushOrderList);
        mv.addObject("hasMoreThanOneOrder", hasMoreThanOneOrder);
        mv.addObject("failOrderCount", failOrderCount);
        mv.addObject("status", status);
        mv.addObject("orderNumber", orderNumber);
        mv.addObject("paytype", paytype);
        mv.addObject("endSecond", endSecond);
        mv.addObject("totalPrice", totalPrice);
        mv.addObject("transactionTime", transactionTime);
        mv.addObject("orderId", orderId);
        mv.addObject("trade_no", trade_no);
    }
    
    private void getWeiXinAccessToken(HttpServletRequest request, HttpServletResponse response, String resquestParams, ModelAndView mv)
        throws Exception
    {
        String responseParams = orderPayService.configPay(request, response, resquestParams);
        JsonParser parser = new JsonParser();
        JsonParser requestparser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        String timestamp = "";
        String nonceStr = "";
        String signature = "";
        String accessToken = "";
        String jsApiTicket = "";
        float totalPrice = 0f;
        timestamp = param.get("timestamp") == null ? Sha1Util.getTimeStamp() : param.get("timestamp").getAsString();
        nonceStr = param.get("nonceStr") == null ? "" : param.get("nonceStr").getAsString();
        signature = param.get("signature") == null ? "" : param.get("signature").getAsString();
        accessToken = JSONUtils.getValue(param, CacheConstant.WEIXIN_ACCESS_TOKEN, "");
        jsApiTicket = JSONUtils.getValue(param, CacheConstant.WEIXIN_JSAPI_TICKET, "");
        JsonObject paramrequest = (JsonObject)requestparser.parse(resquestParams);
        totalPrice = paramrequest.get("totalPrice") == null ? 0f : paramrequest.get("totalPrice").getAsFloat();
        
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, accessToken);
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, jsApiTicket);
        //zhangld 去掉判断 isQqbs
        mv.addObject("appid", CommonConstant.APPID);
        mv.addObject("timestamp", timestamp); // 必填，生成签名的时间戳
        mv.addObject("nonceStr", nonceStr); // 必填，生成签名的随机串
        mv.addObject("signature", signature); // 必填，签名
        // mv.addObject("jsApiList", "[]"); // 必填，需要使用的JS接口列表，
        // this.logger.info("---------getWeiXinAccessToken-----responseParams---is: "+ responseParams);
        
        // 1.判断是否是微信浏览器，而已要是5.0以上版本才能支付，否则给出提示
        boolean flag = CommonUtil.isWeiXinVersionMoreThan5(request.getHeader("User-Agent"));
        mv.addObject("iswx5version", (flag ? "1" : "0")); // 1表示 >=5.0以上的版本
        
        // 发起统一支付请求得到prepar_id
        // resquestParams =
        // "{'totalPrice':'"+totalPrice+"','openId':'"+SessionUtil.getCurrentWeiXinOpenId(request.getSession())+"'}";
        // responseParams = this.orderPayService.requestBeforePay(request, response, resquestParams) ;
        // System.out.println("-----getWeiXinAccessToken---beforeResponseParams----is:" + responseParams);
    }
    
    @RequestMapping("/modifynopayorderstatus")
    @ResponseBody
    public String modifyNoPayOrderStatus(HttpServletRequest request, HttpServletResponse response,
        @RequestParam(value = "orderIds", required = false, defaultValue = "") String orderIds,
        @RequestParam(value = "paychannel", required = false, defaultValue = "") String paychannel)
        throws Exception
    {
        // JsonObject result = new JsonObject();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderIds", orderIds);
        String result = this.orderService.modifyOrderStatus(params);
        
        return result;
    }
    
    /**
     * 获取订单可使用的优惠券
     * 
     * @param type 来源 1[正常确认订单]、2[订单详情页单个订单]、3[订单详情页合并订单]
     * @param confirmId 为1时传该值
     * @param orderId 为2时传该值
     * @param orderIds 为3时传该值
     * @return
     * @throws Exception
     */
    @RequestMapping("/getCoupon/{type}/{orderType}/{confirmId}/{orderId}/{orderIds}")
    public ModelAndView getCoupon(HttpServletRequest request, @PathVariable("type") int type, @PathVariable("orderType") String orderType,
        @PathVariable("confirmId") String confirmId, @PathVariable("orderId") String orderId, @PathVariable("orderIds") String orderIds)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("coupons/getCoupon");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", av.getId());
        para.put("type", type);
        para.put("confirmId", confirmId);
        para.put("orderId", orderId);
        if (type == 3)
        {
            List<Integer> orderIdList = new ArrayList<>();
            if (!"".equals(orderIds))
            {
                if (orderIds.indexOf(",") > -1)
                {
                    String[] ods = orderIds.split(",");
                    for (String it : ods)
                    {
                        orderIdList.add(Integer.valueOf(it));
                    }
                }
                else
                {
                    orderIdList.add(Integer.valueOf(orderIds));
                }
            }
            para.put("orderIdList", orderIdList);
        }
        String selectedCouponId = SessionUtil.getCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
        para.put("selectedCouponId", selectedCouponId);
        Map<String, Object> result = orderService.getCoupon(para);
        // logger.info(JSON.toJSON(result));
        mv.addObject("cancelCouponId", selectedCouponId);
        mv.addObject("status", result.get("status") + "");
        mv.addObject("orderType", orderType);
        mv.addObject("availableCouponDetails", result.get("availableCouponDetails"));
        mv.addObject("unAvailableCouponDetails", result.get("unavailableCouponDetails"));
        return mv;
    }
    
    /**
     * 下单选择优惠券
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectCoupon/{ordertype}/{couponId}")
    public ModelAndView selectedReceiveAddress(HttpServletRequest request, @PathVariable String couponId, @PathVariable String ordertype)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        SessionUtil.removeCurrentSelectedCouponId(request.getSession(), SessionUtil.getCurrentOrderConfirmId(request.getSession()));
        
        if (!"0".equals(couponId) && StringUtil.isNumeric(couponId))
        {
            SessionUtil.addSelectedCouponId(request.getSession(), couponId, SessionUtil.getCurrentOrderConfirmId(request.getSession()));
        }
        
        mv.setViewName("redirect:/order/confirm/" + ordertype);
        return mv;
    }
    
}
