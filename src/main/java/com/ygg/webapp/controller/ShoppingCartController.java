package com.ygg.webapp.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.exception.BusException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.ProductService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.ShoppingCartServiceNew;
import com.ygg.webapp.service.TempAccountService;
import com.ygg.webapp.service.TempShoppingCartService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.UUIDGenerator;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.OrderProductView;
import com.ygg.webapp.view.ProductCountView;

/**
 * 购物车包括 login 前后 , login 前用cookie 来实现，login后合并到session中
 * 
 * @author lihc
 *
 */
@Controller("shoppingCartController")
@RequestMapping("/spcart")
public class ShoppingCartController
{
    
    Logger logger = Logger.getLogger(ShoppingCartController.class);
    
    @Resource(name = "shoppingCartService")
    private ShoppingCartService shoppingCartService;
    
    @Resource(name = "shoppingCartServiceNew")
    private ShoppingCartServiceNew shoppingCartServiceNew;
    
    @Resource(name = "tempShoppingCartService")
    private TempShoppingCartService tempShoppingCartService;
    
    @Resource(name = "tempAccountService")
    private TempAccountService tempAccountService;
    
    @Resource(name = "productService")
    private ProductService productService;
    
    /**
     * 跳到购物车 列出购物车内的详细列表
     * 
     * @return
     */
    @RequestMapping("/listsc")
    public ModelAndView toShoppingCart(HttpServletRequest request, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cart/cart");
        // 判断是否login ，没有login 时，从cookie 中拿出购物车中的内容
        // 如果是login 成功，从session中拿出购物车　
        if (ygguuid == null || ygguuid.equals("") || ygguuid.equals("tmpuuid"))
        {
            ygguuid = UUIDGenerator.getUUID();
            // 写入用户cookie
            writeCartCookie(request, response, ygguuid);
        }
        
        String errorCode = "";
        String status = CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue();
        String endSecond = "-1";// -1表示已过期
        try
        {
            
            if (ordertype != null && ordertype.equals("2")) // 从提交订单页面而来，如果是２的话需要合并 用户一定是login后的
            {
                shoppingCartMerge(ygguuid, request);
                //SessionUtil.removeCurrentOrderConfirmId(request.getSession());
                //SessionUtil.removeCurrentSelectedAddres(request.getSession());
            }
            
            Map<String, String> map = getAccountId(request, ygguuid);
            //zhangld 去掉判断 isQqbs
            String requestParams = "{'id':'" + map.get("accountId") + "','type':'" + map.get("accountType") + "'}";
            
            String responseParams = this.shoppingCartService.list(requestParams);
            JsonParser param = new JsonParser();
            JsonObject object = (JsonObject)param.parse(responseParams);
            status = object.get("status").getAsString();
            if (status != null && status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
            {
                errorCode = object.get("errorCode").getAsString();
            }
            else
            // 成功
            {
                Object productListJson = object.get("productList");
                Object tipsJson = object.get("tips");
                endSecond = (object.get("endSecond") == null ? "-1" : object.get("endSecond").getAsString());
                if (productListJson != null)
                {
                    List<OrderProductView> productList = JSONUtils.fromJson(productListJson.toString(), new TypeToken<List<OrderProductView>>()
                    {
                    });
                    mv.addObject("productList", productList);
                    float totalPrice = 0.00f;
                    DecimalFormat df = new DecimalFormat("0.00");
                    for (OrderProductView pv : productList)
                    {
                        int count = Integer.parseInt(pv.getCount());
                        float salePrice = Float.parseFloat(pv.getSalesPrice());
                        totalPrice = totalPrice + count * salePrice;
                    }
                    mv.addObject("totalPrice", df.format(totalPrice));
                }
                if (tipsJson != null)
                {
                    List<String> tipsList = JSONUtils.fromJson(tipsJson.toString(), new TypeToken<List<String>>()
                    {
                    });
                    mv.addObject("tipsList", tipsList);
                }
                
            }
            String cartCount = SessionUtil.getCartCount(request.getSession());
            if(StringUtils.isBlank(cartCount)){
                String responseparams = shoppingCartService.findShoppingCartCountByAccountId(Integer.valueOf(map.get("accountId")));
                JsonParser parser = new JsonParser();
                JsonObject paramc = (JsonObject)parser.parse(responseparams);
                String cartc = paramc.get("cartCount").getAsString();
                SessionUtil.addCartCount(request.getSession(), cartc);
            }
        }
        catch (Exception e)
        {
            if (e instanceof ServiceException)
            {
                ServiceException se = (ServiceException)e;
                errorCode = (se.getMap("errorCode") == null ? "" : (String)se.getMap("errorCode"));
                status = (se.getMap("status") == null ? "" : (String)se.getMap("status"));
            }
            logger.error("-----------toShoppingCart-----------", e);
        }
        mv.addObject("endSecond", endSecond);
        mv.addObject("errorCode", errorCode);
        mv.addObject("status", status);
        return mv;
    }
    
    private void shoppingCartMerge(String ygguuid, HttpServletRequest request)
        throws Exception
    {
        int tempAccount = getTempAccountId(ygguuid);
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (tempAccount != CommonConstant.ID_NOT_EXIST)
        {
            String requestParams = "{'cartToken':'" + tempAccount + "','accountId':'" + av.getId() + "'}";
            try
            {
                this.shoppingCartService.merger(requestParams);
            }
            catch (Exception e)
            {
                logger.error("----UserController----login---购物车合并失败---");
            }
        }
    }
    
    private int getTempAccountId(String tempYgguuid)
        throws Exception
    {
        int tempAccountId = CommonConstant.ID_NOT_EXIST;
        tempAccountId = this.tempAccountService.findTempAccountIdByUUID(tempYgguuid);
        return tempAccountId;
    }
    
    @RequestMapping("/getproductstatus/{productid}")
    @ResponseBody
    public String getproductdynamic(@PathVariable("productid") String productId)
        throws Exception
    {
        return this.productService.getProductStatusById("{'productId':'" + productId + "'}");
    }
    
    /**
     * 对于品牌和单品的addToShoppingCart productcount不能以客户端为准，要以服务端为准　
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editscbsp", method = RequestMethod.POST)
    @ResponseBody
    public String editShoppingCartForBrandAndSingleProduct(HttpServletRequest request, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "productid", required = true, defaultValue = "0") String productId,
        @RequestParam(value = "productcount", required = true, defaultValue = "0") String productcount, HttpServletResponse response)
        throws Exception
    {
        
        String result = processEditShoppingCart(request, ygguuid, productId, productcount, response, true);
        return result;
    }
    
    /**
     * 在购物车页面中的操作 编辑购物车 包括 增加商品到购物车，从购物车中移除一个商品 要判断用户　是否　　已登录的情况　 如果用户没有login，写一个UUID串到Cookie中去保存半年 通过ajax去请求 返加一个json串
     * 
     * @param request
     * @param ygguuid
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editsc", method = RequestMethod.POST)
    @ResponseBody
    public String editShoppingCart(HttpServletRequest request, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "productid", required = true, defaultValue = "0") String productId,
        @RequestParam(value = "productcount", required = true, defaultValue = "0") String productcount, HttpServletResponse response)
        throws Exception
    {
        
        String result = processEditShoppingCart(request, ygguuid, productId, productcount, response, false);
        return result;
    }
    
    private String getErrorCodeMsg(String errorCode)
    {
        if (errorCode != null && !errorCode.equals(""))
        {
            if (errorCode.equals(CommonEnum.CART_EDIT_ERRORCODE.PRODUCTID_NOT_EXIST.getValue()))
            {
                errorCode = "此商品已被删除了!";
            }
            else if (errorCode.equals(CommonEnum.CART_EDIT_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
            {
                errorCode = "账号不存在!";
            }
            else if (errorCode.equals(CommonEnum.CART_EDIT_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue()))
            {
                errorCode = "临时账号不存在!";
            }
            else if (errorCode.equals(CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue()))
            {
                errorCode = "库存不足!";
            }
            else if (errorCode.equals(CommonEnum.CART_EDIT_ERRORCODE.RESTRICTION_EXCEED.getValue()))
            {
                errorCode = "超过商品限购数量!";
            }
        }
        return errorCode;
    }
    
    /**
     * 购物车结算的操作
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/submitsc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String submitShoppingCart(HttpServletRequest request, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "iscarttimeout", required = false, defaultValue = "0") String iscarttimeout, HttpServletResponse response)
        throws Exception
    {
        JsonObject result = new JsonObject();
        // 判断是否login ，没有login 时，从cookie 中拿出购物车中的内容
        // 如果是login 成功，从session中拿出购物车　
        if (ygguuid == null || ygguuid.equals("") || ygguuid.equals("tmpuuid"))
        {
            ygguuid = UUIDGenerator.getUUID();
            // 写入用户cookie
            writeCartCookie(request, response, ygguuid);
        }
        Map<String, String> map = getAccountId(request, ygguuid);
        String accountType = map.get("accountType");
        String requestParams = "{'id':'" + map.get("accountId") + "','type':'" + map.get("accountType") + "'}";
        
        String errorCode = "";
        String status = "";
        String endSecond = "";
        List<ProductCountView> lockList = null;
        List<ProductCountView> unlockList = null;
        List<ProductCountView> lackList = null;
        try
        {
            String responseParams = this.shoppingCartService.submit(requestParams);
            JsonParser param = new JsonParser();
            JsonObject object = (JsonObject)param.parse(responseParams);
            status = object.get("status").getAsString();
            if (status != null && status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
            {
                errorCode = object.get("errorCode").getAsString();
                errorCode = getErrorCodeMsg(errorCode);
            }
            else
            // 成功
            {
                Object lockJson = object.get("lock");
                Object unlockJson = object.get("unlock");
                Object lackJson = object.get("lack");
                endSecond = (object.get("endSecond") == null ? "" : object.get("endSecond").getAsString());
                if (lockJson != null)
                {
                    lockList = JSONUtils.fromJson(lockJson.toString(), new TypeToken<List<ProductCountView>>()
                    {
                    });
                    result.addProperty("lock", JSONUtils.toJson(lockList, false));
                }
                if (unlockJson != null)
                {
                    unlockList = JSONUtils.fromJson(unlockJson.toString(), new TypeToken<List<ProductCountView>>()
                    {
                    });
                    result.addProperty("unlock", JSONUtils.toJson(unlockList, false));
                }
                if (lackJson != null)
                {
                    lackList = JSONUtils.fromJson(lackJson.toString(), new TypeToken<List<ProductCountView>>()
                    {
                    });
                    result.addProperty("lack", JSONUtils.toJson(lackList, false));
                }
                
                result.addProperty("endSecond", endSecond);
            }
        }
        catch (Exception e)
        {
            logger.error("---------submitShoppingCart-----", e);
            if (e instanceof ServiceException)
            {
                ServiceException se = (ServiceException)e;
                errorCode = (se.getMap("errorCode") == null ? "" : (String)se.getMap("errorCode"));
                status = (se.getMap("status") == null ? "" : (String)se.getMap("status"));
            }
            logger.error(errorCode);
        }
        
        result.addProperty("status", status);
        result.addProperty("errorCode", errorCode);
        
        String orderType = "1"; // 正常订单 已login
        if (accountType != null && accountType.equals("2"))
        {
            orderType = "2"; // 结算时登录订单 带一个状态 no login
        }
        
        result.addProperty("iscarttimeout", iscarttimeout);
        result.addProperty("orderType", orderType);
        return result.toString();
    }
    
    /**
     * 显示购物车中的cartCount 数量显示在页面上，和这个商品在购物车中的正常状态下的数量productcount 通过ajax去请求 返加一个json串
     * 
     * @return
     */
    @RequestMapping("/showcartcount")
    @ResponseBody
    public String showShoppingCartCount(HttpServletRequest request, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "productid", required = false, defaultValue = "0") String productId)
        throws Exception
    {
        String json = "";
        JsonObject result = new JsonObject();
        String cartCount = "0"; // 记录用户所在购物车中商品的总数量　
        String productcount = "0"; // 该账号和ProductId在购物车内单个商品的数量，不是总数量
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        JsonParser parser = new JsonParser();
        JsonObject param = null;
        String shoppingcartresult = null; // 返回的都是json串
        String endSecond = "";
        if (av == null)
        {
            // 1.1 从客户端的cookie中去拿一个临时账号
            if (ygguuid != null && !ygguuid.equals("") && !ygguuid.equals("tmpuuid")) // cookie 有
            {
                // 1.2 临时购物车中查出数量总和
                shoppingcartresult = this.tempShoppingCartService.findTempShoppingCartByAccountIdAndProductId(ygguuid, Integer.parseInt(productId));
            }
        }
        else
        {
            // 1.2 从库中查出购物车的总量
            int accountId = av.getId();
            shoppingcartresult = this.shoppingCartService.findShoppingCartByAccountIdAndProductId(accountId, Integer.parseInt(productId));
        }
        if (shoppingcartresult != null && !shoppingcartresult.equals(""))
        {
            param = (JsonObject)parser.parse(shoppingcartresult);
            cartCount = (param.get("cartCount") == null ? "0" : param.get("cartCount").getAsString());
            productcount = (param.get("productcount") == null ? "0" : param.get("productcount").getAsString());
            endSecond = (param.get("endSecond") == null ? "" : param.get("endSecond").getAsString());
        }
        result.addProperty("cartCount", cartCount);
        result.addProperty("endSecond", endSecond); // 购物车的锁定时间
        result.addProperty("productcount", productcount);
        json = result.toString();
        return json;
    }
    
    /**
     * 得到账号Id和type 已login后 type=1 没有login type =2
     * 
     * @param tempYgguuid
     * @return
     */
    private Map<String, String> getAccountId(HttpServletRequest request, String tempYgguuid)
        throws Exception
    {
        Map<String, String> map = new HashMap<String, String>();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            int tempAccountId = this.tempAccountService.findTempAccountIdByUUID(tempYgguuid);
            if (tempAccountId == CommonConstant.ID_NOT_EXIST) // 生成一个UUID再加入表临时用户表中
            {
                this.tempAccountService.addTempAccount(tempYgguuid);
                tempAccountId = this.tempAccountService.findTempAccountIdByUUID(tempYgguuid);
            }
            map.put("accountId", (tempAccountId == CommonConstant.ID_NOT_EXIST ? "-1" : tempAccountId) + "");
            map.put("accountType", "2");
            return map;
        }
        map.put("accountId", av.getId() + "");
        map.put("accountType", "1");
        return map;
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
    
    public String processEditShoppingCart(HttpServletRequest request, String ygguuid, String productId, String productcount, HttpServletResponse response,
        boolean isQueryProductCount)
        throws Exception
    {
        JsonObject result = new JsonObject();
        if (ygguuid == null || ygguuid.equals("") || ygguuid.equals("tmpuuid"))
        {
            ygguuid = UUIDGenerator.getUUID();
            // 写入cookie
            writeCartCookie(request, response, ygguuid);
        }
        
        String errorCode = "";
        String status = CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue();
        String endSecond = "";
        String restrictionCount = "";
        String stockCount = "";
        try
        {
            Map<String, String> map = getAccountId(request, ygguuid);
            String accountId = map.get("accountId");
            String accountType = map.get("accountType");
            if (isQueryProductCount) // 要去查prodcutCount
            {
                if (accountType != null && accountType.equals("1"))
                {
                    productcount = "" + (this.shoppingCartService.findProductCountByAIdAndPId(Integer.parseInt(accountId), Integer.parseInt(productId)) + 1);
                }
                else if (accountType != null && accountType.equals("2"))
                {
                    productcount = "" + (this.tempShoppingCartService.findProductCountByAIdAndPId(Integer.parseInt(accountId), Integer.parseInt(productId)) + 1);
                }
            }
            String reqeustParams = "{'id':'" + accountId + "','type':'" + accountType + "','productId':'" + productId + "','count':'" + productcount + "'}";
            
            String responseParams = "";
            try
            {
                responseParams = this.shoppingCartServiceNew.editCrudsc(reqeustParams); // 新起一个事务 REQUIRE_NEW
            }
            catch (Exception e)
            {
                logger.error("ShoppingCart--edit-异常!", e);
                if (e instanceof ServiceException)
                {
                    ServiceException e1 = (ServiceException)e;
                    status = (e1.getMap("status") == null ? "0" : (String)e1.getMap("status"));
                    errorCode = (e1.getMap("errorCode") == null ? "" : e1.getMap("errorCode") + "");
                    int stockCountTmp = (e1.getMap("stockCount") == null ? 0 : (Integer)e1.getMap("stockCount"));
                    restrictionCount = (e1.getMap("restrictionCount") == null ? "0" : e1.getMap("restrictionCount") + "");
                    ShoppingCartEntity sce = (e1.getMap("sce") == null ? null : (ShoppingCartEntity)e1.getMap("sce"));
                    TempShoppingCartEntity tsce = (e1.getMap("tsce") == null ? null : (TempShoppingCartEntity)e1.getMap("tsce"));
                    String showMsg = e1.getMap("showMsg") == null ? "" : e1.getMap("showMsg") + "";
                    if (sce != null)
                    {
                        List<ShoppingCartEntity> list = new ArrayList<ShoppingCartEntity>();
                        if (stockCountTmp == 0) // 没货
                        {
                            list.add(sce);
                            this.shoppingCartServiceNew.updateShoppingCart(list, null);
                        }
                    }
                    if (tsce != null)
                    {
                        List<TempShoppingCartEntity> list = new ArrayList<TempShoppingCartEntity>();
                        if (stockCountTmp == 0) // 没货
                        {
                            list.add(tsce);
                            this.shoppingCartServiceNew.updateShoppingCart(null, list);
                        }
                    }
                    result.addProperty("status", status); // CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", errorCode);
                    result.addProperty("stockCount", stockCount + "");
                    result.addProperty("restrictionCount", restrictionCount); // / errorCode =5时才取得此值
                    result.addProperty("showMsg", showMsg); // / errorCode =6时才取得此值
                    return result.toString();
                }
                result.addProperty("errorCode", errorCode);
                result.addProperty("status", status);
                return result.toString();
            }
            // ///////////////////////////////////////////////end///////////////////////////////////////////////////////
            JsonObject responseObj = new JsonObject();
            responseParams = this.shoppingCartService.editShoppingCart(responseParams); // (reqeustParams) ;
            JsonParser param = new JsonParser();
            responseObj = (JsonObject)param.parse(responseParams);
            
            status = (responseObj.get("status") == null ? "0" : responseObj.get("status").getAsString());
            endSecond = (responseObj.get("endSecond") == null ? "" : responseObj.get("endSecond").getAsString());
            errorCode = (responseObj.get("errorCode") == null ? "" : responseObj.get("errorCode").getAsString());
            restrictionCount = (responseObj.get("restrictionCount") == null ? "" : responseObj.get("restrictionCount").getAsString());
            stockCount = (responseObj.get("stockCount") == null ? "" : responseObj.get("stockCount").getAsString());
            // 成功的话返回 productcount
            result.addProperty("productcount", productcount);
        }
        catch (Exception e)
        {
            /**
             * 出错后，不能给出错误页面，给出返回的错误信息，如库存不足
             */
            logger.error("----ShoppingCartController-------editShoppingCart------errorCodeMsg:", e);
            // 返回json串给页面,因为是ajax请求
            if (e instanceof ServiceException)
            {
                ServiceException se = (ServiceException)e;
                status = (se.getMap("status") == null ? "0" : se.getMap("status") + "");
                errorCode = (se.getMap("errorCode") == null ? "-1" : se.getMap("errorCode") + "");
                stockCount = (se.getMap("stockCount") == null ? "0" : se.getMap("stockCount") + "");
                restrictionCount = (se.getMap("restrictionCount") == null ? "0" : se.getMap("restrictionCount") + "");
                result.addProperty("stockCount", stockCount);
                result.addProperty("restrictionCount", restrictionCount);
            }
            else if (e instanceof BusException)
            {
                BusException be = (BusException)e;
                errorCode = (be.getModelObject("errorCode") != null ? (String)be.getModelObject("errorCode") : "");
                status = (be.getModelObject("status") != null ? ((String)be.getModelObject("status")) : "0");
            }
            result.addProperty("errorCode", errorCode);
            result.addProperty("status", status);
            result.addProperty("productcount", (Integer.parseInt(productcount) - 1) + ""); // 没加成功的话，减回去
            return result.toString();
        }
        result.addProperty("errorCode", errorCode); // 成功的话,errorCode为空
        result.addProperty("endSecond", endSecond);
        result.addProperty("status", status);
        result.addProperty("stockCount", stockCount);
        result.addProperty("restrictionCount", restrictionCount);
        SessionUtil.removeCurrentOrderConfirmId(request.getSession()); // / 从定单再回到购物车操作crud时，又在过期范围之内时，再次刷新
        return result.toString();
    }
}
