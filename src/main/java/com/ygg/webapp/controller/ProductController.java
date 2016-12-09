package com.ygg.webapp.controller;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.FreeMarkerUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.ProductSummaryView;
import com.ygg.webapp.view.TipView;

/**
 *
 * 商品和商品详情
 * 
 * @author lihc
 *
 */
@Controller("productController")
@RequestMapping("/product")
public class ProductController
{
    
    Logger logger = Logger.getLogger(ProductController.class);
    
    @Resource(name = "resourceService")
    private ResourceService resourceService;
    
    @Resource(name = "shoppingCartService")
    private ShoppingCartService shoppingCartService;
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    private static boolean isreload = false;
    
    /**
     * 得到商品的动态情况ajax请求
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    @RequestMapping("/getproductinfo/{productid}")
    @ResponseBody
    public String getproductdynamic(@PathVariable("productid") String productId)
        throws Exception
    {
        // JsonObject result = new JsonObject();
        return this.resourceService.getProductDynamicInfo("{'productId':'" + productId + "'}");
        // return result.toString() ;
    }
    
    /**
     * 查询单个商品 改成带cache 商城商品
     * 
     * @param productId
     * @param request
     * @param ygguuid
     * @param response
     * @throws Exception
     */
    @RequestMapping("/msingle/{productid}")
    public void mallProduct(@PathVariable("productid") String productId, HttpServletRequest request,
        @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid, HttpServletResponse response)
        throws Exception
    {
        Writer writer = response.getWriter();
        Map<String, Object> checkPara = new HashMap<String, Object>();
        checkPara.put("id", productId);
        checkPara.put("type", CommonEnum.PRODUCT_TYPE.MALL.getValue());
        if (!resourceService.checkProductCanBeAccessed(checkPara))
        {
            // 404 后面前端来了统一加上404页面
            writer.write("您访问的页面不存在");
            writer.flush();
            writer.close();
            return;
        }
        String pageContentHtmlCache = null;
        Map<String, String> params = CommonUtil.getAllRequestParam(request);
        if (params != null && !params.isEmpty())
        {
            pageContentHtmlCache = this.cacheService.getCache(CacheConstant.PAGE_WXSHARE_SINGLE_PRODUCT_CACHE_MALL + productId);
            if (pageContentHtmlCache != null && !pageContentHtmlCache.isEmpty())
            {
                if (pageContentHtmlCache != null && !pageContentHtmlCache.equals(""))
                {
                    writer.write(pageContentHtmlCache);
                    writer.flush();
                    writer.close();
                    return;
                }
            }
            if (params == null)
                params = new HashMap<String, String>();
            params.put("pageCacheKey", CacheConstant.PAGE_WXSHARE_SINGLE_PRODUCT_CACHE_MALL + productId);
            pageContentHtmlCache = processSinglePage(productId, request, response, params);
            writer.write(pageContentHtmlCache);
            writer.flush();
            writer.close();
            return;
        }
        
        pageContentHtmlCache = this.cacheService.getCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE_MALL + productId);
        if (pageContentHtmlCache != null && !pageContentHtmlCache.isEmpty())
        {
            if (pageContentHtmlCache != null && !pageContentHtmlCache.equals(""))
            {
                writer.write(pageContentHtmlCache);
                writer.flush();
                writer.close();
                return;
            }
        }
        
        if (params == null)
            params = new HashMap<String, String>();
        params.put("pageCacheKey", CacheConstant.PAGE_SINGLE_PRODUCT_CACHE_MALL + productId);
        pageContentHtmlCache = processSinglePage(productId, request, response, params);
        
        writer.write(pageContentHtmlCache);
        writer.flush();
        writer.close();
    }
    
    /**
     * 查询单个商品 改成带cache 特卖商品
     * 
     * @param productid
     */
    @RequestMapping("/single/{productid}")
    public void saleProduct(@PathVariable("productid") String productId, HttpServletRequest request,
        @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        HttpServletResponse response,
        HttpSession session)
        throws Exception
    {
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        Writer writer = response.getWriter();
        Map<String, Object> checkPara = new HashMap<String, Object>();
        checkPara.put("id", productId);
        checkPara.put("type", CommonEnum.PRODUCT_TYPE.SALE.getValue());
        if (!resourceService.checkProductCanBeAccessed(checkPara))
        {
            // 404 后面前端来了统一加上404页面
            writer.write("您访问的页面不存在");
            writer.flush();
            writer.close();
            return;
        }
        String pageContentHtmlCache = null;
        Map<String, String> params = CommonUtil.getAllRequestParam(request);
        // logger.info("--------------ProductController------------singleproduct--requestParams-------is:" + params);
        if (params != null && !params.isEmpty())
        {
            /*
             * String fromParams = params.get("from") ; String isappinstalled = params.get("isappinstalled") ;
             * if(fromParams != null && isappinstalled != null && !fromParams.equals("") && !isappinstalled.equals("") )
             * // 从微信分享页面而来,url会改变　 {
             */
        	//TO FIX
            pageContentHtmlCache = null;//this.cacheService.getCache(CacheConstant.PAGE_WXSHARE_SINGLE_PRODUCT_CACHE + productId);
            if (pageContentHtmlCache != null && !pageContentHtmlCache.isEmpty())
            {
                if (pageContentHtmlCache != null && !pageContentHtmlCache.equals(""))
                {
                    pageContentHtmlCache = pageContentHtmlCache.replaceAll("(" + "taccountId" +"=[^\"]*)", "taccountId" + "=" + account.getAccountId());
                    writer.write(pageContentHtmlCache);
                    writer.flush();
                    writer.close();
                    return;
                }
            }
            if (params == null)
                params = new HashMap<String, String>();
            params.put("pageCacheKey", CacheConstant.PAGE_WXSHARE_SINGLE_PRODUCT_CACHE + productId);
            pageContentHtmlCache = processSinglePage(productId, request, response, params);
            pageContentHtmlCache = pageContentHtmlCache.replaceAll("(" + "taccountId" +"=[^\"]*)", "taccountId" + "=" + account.getAccountId());
            writer.write(pageContentHtmlCache);
            writer.flush();
            writer.close();
            return;
            // }
        }
        
        // /// 缓存页面
        pageContentHtmlCache = this.cacheService.getCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE + productId);
        if (pageContentHtmlCache != null && !pageContentHtmlCache.isEmpty())
        {
            if (pageContentHtmlCache != null && !pageContentHtmlCache.equals(""))
            {
                pageContentHtmlCache = pageContentHtmlCache.replaceAll("(" + "taccountId" +"=[^\"]*)", "taccountId" + "=" + account.getAccountId());
                writer.write(pageContentHtmlCache);
                writer.flush();
                writer.close();
                return;
            }
        }
        
        if (params == null)
            params = new HashMap<String, String>();
        params.put("pageCacheKey", CacheConstant.PAGE_SINGLE_PRODUCT_CACHE + productId);
        pageContentHtmlCache = processSinglePage(productId, request, response, params);
        pageContentHtmlCache = pageContentHtmlCache.replaceAll("(" + "taccountId" +"=[^\"]*)", "taccountId" + "=" + account.getAccountId());
        writer.write(pageContentHtmlCache);
        writer.flush();
        writer.close();
    }
    
    @SuppressWarnings("unused")
    private String processSinglePage(String productId, HttpServletRequest request, HttpServletResponse response, Map<String, String> params)
        throws Exception
    {
        String pageCachekey = params.get("pageCacheKey");
        String wxshareurlparams = CommonUtil.getWxShareUrl(params);
        // //单个商品一要关联一个特别商品
        Map<String, Object> mv = new HashMap<String, Object>();
        
        // / 1.查询用户购物车的总数　分login前后　
        String cartCount = "0"; // 记录用户所在购物车中商品的总数量　
        JsonParser parser = new JsonParser();
        JsonObject param = null;
        
        mv.put("cartCount", cartCount);
        
        // 2.查询单个商品的详细信息 先从缓存中去拿数据　
        String resultBase = this.cacheService.getCache(CacheConstant.SINGLE_PRODUCT_CACHE + productId);
        if (resultBase == null || resultBase.isEmpty())
        {
            resultBase = this.resourceService.productBase("{'productId':'" + productId + "'}");
            this.cacheService.addCache(CacheConstant.SINGLE_PRODUCT_CACHE + productId, resultBase, CacheConstant.CACHE_SECOND_30);
        }
        
        String wexinShareImgUrl = "";
        String gegeSay = "";
        if (null != resultBase && !resultBase.equals(""))
        {
            param = (JsonObject)parser.parse(resultBase);
            String status = param.get("status").getAsString();
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
            {
                String productStatus = (param.get("productStatus") == null ? CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue() : param.get("productStatus").getAsString());
                Object imageListJson = param.get("imageList");
                Object tipListJson = param.get("tipList"); // / 自定义的页面
                Object summaryJson = param.get("summary"); // / 商品说明
                Object notesJson = param.get("notes"); // 发货商提示
                
                String name = (param.get("name") == null ? "" : param.get("name").getAsString()); // 商品名称
                String highPrice = (param.get("highPrice") == null ? "" : param.get("highPrice").getAsString());
                String lowPrice = (param.get("lowPrice") == null ? "" : param.get("lowPrice").getAsString());
                String isBonded = (param.get("isBonded") == null ? "" : param.get("isBonded").getAsString()); // 是否保税区
                String sellerName = (param.get("sellerName") == null ? "" : param.get("sellerName").getAsString()); // 商家名称
                String sellCount = (param.get("sellCount") == null ? "" : param.get("sellCount").getAsString()); // 卖出数量
                String stockCount = (param.get("stockCount") == null ? "" : param.get("stockCount").getAsString()); // 库存
                gegeSay = (param.get("gegeSay") == null ? "" : param.get("gegeSay").getAsString()); // 产品描述
                String gegeSayImage = (param.get("gegeSayImage") == null ? "" : param.get("gegeSayImage").getAsString());// 格格说头像
                String second = (param.get("second") == null ? "" : param.get("second").getAsString()); // 倒计时
                String pcDetail = (param.get("pcDetail") == null ? "" : param.get("pcDetail").getAsString()); // 自定义html
                String startDate = (param.get("startDate") == null ? "" : param.get("startDate").getAsString());
                String endDate = (param.get("endDate") == null ? "" : param.get("endDate").getAsString());
                
                wexinShareImgUrl = (param.get("wexinShareImgUrl") == null ? "" : param.get("wexinShareImgUrl").getAsString());
                mv.put("productStatus", productStatus);
                mv.put("name", name);
                mv.put("highPrice", highPrice);
                mv.put("lowPrice", lowPrice);
                mv.put("isBonded", isBonded);
                mv.put("sellerName", sellerName);
                mv.put("sellCount", sellCount);
                mv.put("stockCount", stockCount);
                mv.put("gegeSay", gegeSay);
                mv.put("gegeSayImage", gegeSayImage);
                mv.put("second", second);
                mv.put("pcDetail", pcDetail);
                mv.put("startDate", startDate);
                mv.put("endDate", endDate);
                
                mv.put("yggJsVersion", CommonUtil.getStaticJsVersion()); // 控制js css的版本号
                mv.put("yggCssVersion", CommonUtil.getStaticCssVersion());
                
                if (imageListJson != null)
                {
                    List<String> imageList = JSONUtils.fromJson(imageListJson.toString(), new TypeToken<List<String>>()
                    {
                    });
                    mv.put("imageList", imageList);
                }
                if (tipListJson != null)
                {
                    List<TipView> tipList = JSONUtils.fromJson(tipListJson.toString(), new TypeToken<List<TipView>>()
                    {
                    });
                    mv.put("tipList", tipList);
                }
                if (summaryJson != null)
                {
                    ProductSummaryView summary = JSONUtils.fromJson(summaryJson.toString(), ProductSummaryView.class);
                    mv.put("summary", summary);
                }
                if (notesJson != null)
                {
                    List<String> notesList = JSONUtils.fromJson(notesJson.toString(), new TypeToken<List<String>>()
                    {
                    });
                    mv.put("notesList", notesList);
                }
            }
        }
        
        // boolean flag = CommonUtil.isWeiXinVersionMoreThan5(request.getHeader("User-Agent")) ; ///// 这里是页面缓存，只能都加载上来　
        // if(flag)
        // {
        String sharesingleproducturl = YggWebProperties.getInstance().getProperties("sharesingleproducturl");
        sharesingleproducturl = sharesingleproducturl.replaceFirst("#1", productId);
        
        if (wxshareurlparams != null && !wxshareurlparams.equals(""))
        {
            wxshareurlparams = "?" + wxshareurlparams;
            sharesingleproducturl = sharesingleproducturl + wxshareurlparams;
        }
        // this.logger.debug("sharesingleproducturl---is:"+sharesingleproducturl);
        String resquestParams = "{'url':'" + sharesingleproducturl + "'}";
//        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, mv);
        mv.put("link", sharesingleproducturl); // 分享链接
        mv.put("imgUrl", wexinShareImgUrl); // 分享图标
        
        String wxShareDesc = (gegeSay != null && gegeSay.length() > 50 ? gegeSay.substring(0, 50) : gegeSay);
        mv.put("wxShareDesc", wxShareDesc); // 分享图标
        // }
        
        mv.put("productid", productId);
        mv.put("yggcontextPath", ContextPathUtil.getBasePath(request));
        //分享相关
        mv.put("jsSdk",request.getAttribute("jsSdk"));
        String contentHtml = FreeMarkerUtil.createHtml("saleproduct/product.ftl", mv);
        
        this.cacheService.addCache(pageCachekey, contentHtml, CacheConstant.CACHE_SECOND_30); // 7100
        
        return contentHtml;
    }
    
    /**
     * 查询单个商品
     * 
     * @param productid
     */
    /*
     * @RequestMapping("/single/{productid}") public ModelAndView singleproduct(@PathVariable("productid") String
     * productId , HttpServletRequest request ,@CookieValue(value="ygguuid",required=true,defaultValue="tmpuuid") String
     * ygguuid ) throws Exception { ////单个商品一要关联一个特别商品 ModelAndView mv = new ModelAndView() ;
     * mv.setViewName("saleproduct/product");
     * 
     * /// 1.查询用户购物车的总数　分login前后　 String cartCount = "0" ; // 记录用户所在购物车中商品的总数量　 AccountView av =
     * UserUtil.getCurrentUser(request.getSession()) ; JsonParser parser = new JsonParser(); JsonObject param = null ;
     * String shoppingcartresult = null ; // 返回的都是json串 if(av == null){ // 1.1 从客户端的cookie中去拿一个临时账号 if(ygguuid !=null &&
     * !ygguuid.equals("") && !ygguuid.equals("tmpuuid") ) // cookie 有 { // 1.2 临时购物车中查出数量总和 shoppingcartresult =
     * this.shoppingCartService.findTmpShoppingCartByAccountIdAndProductId(Integer.parseInt(ygguuid),
     * Integer.parseInt(productId) ) ; }
     * 
     * }else{ // 1.2 从库中查出购物车的总量 int accountId = av.getId() ; shoppingcartresult =
     * this.shoppingCartService.findShoppingCartByAccountIdAndProductId(accountId, Integer.parseInt(productId) ) ; }
     * if(shoppingcartresult!=null && !shoppingcartresult.equals("")) { param =
     * (JsonObject)parser.parse(shoppingcartresult); cartCount = (
     * param.get("cartCount")==null?"0":param.get("cartCount").getAsString() ) ; } mv.addObject("cartCount", cartCount)
     * ;
     * 
     * //2.查询单个商品的详细信息 String resultBase = this.resourceService.productBase("{'productId':'"+productId+"'}") ;
     * if(null!=resultBase && !resultBase.equals("") ) { //JsonParser parser = new JsonParser(); param =
     * (JsonObject)parser.parse(resultBase); String status = param.get("status").getAsString() ; if(status.equals(
     * CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue())) { //Object o = param.get("addressList") ; String
     * productStatus = ( param.get("productStatus") ==null ?CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue():
     * param.get("productStatus").getAsString() ) ; Object imageListJson = param.get("imageList") ; Object tipListJson =
     * param.get("tipList") ; /// 自定义的页面 Object summaryJson = param.get("summary") ; /// 商品说明
     * 
     * String name = (param.get("name")==null?"":param.get("name").getAsString()) ; //商品名称 String highPrice =
     * (param.get("highPrice") == null?"":param.get("highPrice").getAsString() ); String lowPrice =
     * (param.get("lowPrice")== null?"" :param.get("lowPrice").getAsString() ) ; String isBonded =
     * (param.get("isBonded") ==null?"": param.get("isBonded").getAsString() ); //是否保税区 String sellerName =
     * (param.get("sellerName") == null ? "" :param.get("sellerName").getAsString() ) ; // 商家名称 String sellCount =
     * (param.get("sellCount") == null?"":param.get("sellCount").getAsString() ); // 卖出数量 String stockCount =
     * (param.get("stockCount") == null?"":param.get("stockCount").getAsString() ); // 库存 String gegeSay =
     * (param.get("gegeSay")==null?"":param.get("gegeSay").getAsString()) ; // 产品描述 String second =
     * (param.get("second")==null?"":param.get("second").getAsString()) ; // 倒计时 String pcDetail =
     * (param.get("pcDetail")==null?"":param.get("pcDetail").getAsString()) ; // 自定义html
     * 
     * 
     * mv.addObject("productStatus", productStatus) ; mv.addObject("name", name) ; mv.addObject("highPrice", highPrice)
     * ; mv.addObject("lowPrice", lowPrice) ; mv.addObject("isBonded", isBonded) ; mv.addObject("sellerName",
     * sellerName) ; mv.addObject("sellCount", sellCount) ; mv.addObject("stockCount", stockCount) ;
     * mv.addObject("gegeSay", gegeSay) ; mv.addObject("second", second) ; mv.addObject("pcDetail", pcDetail) ;
     * 
     * if( imageListJson!= null) { List<String> imageList = JSONUtils.fromJson(imageListJson.toString(), new
     * TypeToken<List<String>>(){} ) ; mv.addObject("imageList", imageList) ; } if(tipListJson !=null) { List<TipView>
     * tipList = JSONUtils.fromJson(tipListJson.toString(), new TypeToken<List<TipView>>(){} ) ; mv.addObject("tipList",
     * tipList) ; } if(summaryJson !=null) { ProductSummaryView summary = JSONUtils.fromJson(summaryJson.toString(),
     * ProductSummaryView.class) ; mv.addObject("summary", summary) ; } } }
     * 
     * mv.addObject("productid", productId);
     * 
     * return mv ; }
     */
    
}
