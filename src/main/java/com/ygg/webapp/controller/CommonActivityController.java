package com.ygg.webapp.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.service.ProductService;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.TempAccountService;
import com.ygg.webapp.service.TempShoppingCartService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.FreeMarkerUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.ActivitiesProductView;
import com.ygg.webapp.view.ShoppingCartView;
import com.ygg.webapp.view.TempShoppingCartView;

@Controller("commonActivityController")
@RequestMapping("/cnty")
public class CommonActivityController
{
    
    Logger logger = Logger.getLogger(CommonActivityController.class);
    
    @Resource(name = "resourceService")
    private ResourceService resourceService;
    
    @Resource(name = "shoppingCartService")
    private ShoppingCartService shoppingCartService;
    
    @Resource(name = "tempShoppingCartService")
    private TempShoppingCartService tempShoppingCartService;
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "tempAccountService")
    private TempAccountService tempAccountService;
    
    @Resource(name = "productService")
    private ProductService productService;
    
    private static boolean isreload = false;
    
    /**
     * 通用专场 加cache
     */
    @SuppressWarnings("unused")
    @RequestMapping("/toac/{caId}")
    public void activitycommon(@PathVariable("caId") String caId, @CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "reload", required = false, defaultValue = "0") String reload, 
        HttpServletRequest request, HttpServletResponse response
        ,HttpSession session)
        throws Exception
    {
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        Map<String, Object> mappage = null;
        String pageContentHtmlCache = null;
        Writer writer = response.getWriter();
        Map<String, String> params = CommonUtil.getAllRequestParam(request);
        // ///////////////////// 要重新加载,注意下面看能否微信分享 /// 后面改成定时器， isreload 控制也只清空一次数据缓存 //////////////////
        // logger.info("--------------CommonActivityController------------activitycommon--requestParams-------is:" +
        // params);
        if (params != null && !params.isEmpty())
        {
            mappage = this.cacheService.getCache(CacheConstant.PAGE_WXSHARE_COMMON_ACTIVITY_PRODUCT_CACHE);
            if (mappage != null && !mappage.isEmpty() && mappage.containsKey(CacheConstant.PAGE_WXSHARE_COMMON_ACTIVITY_PRODUCT_CACHE + caId))
            {
                pageContentHtmlCache = (String)mappage.get(CacheConstant.PAGE_WXSHARE_COMMON_ACTIVITY_PRODUCT_CACHE + caId);
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
            params.put("pageCacheKey", CacheConstant.PAGE_WXSHARE_COMMON_ACTIVITY_PRODUCT_CACHE);
            params.put("ygguuid", ygguuid);
            pageContentHtmlCache = processBrandPage(caId, request, response, mappage, params);
            pageContentHtmlCache = pageContentHtmlCache.replaceAll("(" + "taccountId" +"=[^\"]*)", "taccountId" + "=" + account.getAccountId());
            writer.write(pageContentHtmlCache);
            writer.flush();
            writer.close();
            return;
            
        }
        
        // /// 缓存页面
        mappage = null;// this.cacheService.getCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE);
        // ///////////////////////////////////////////////////////////////////
        if (mappage != null && !mappage.isEmpty() && mappage.containsKey(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE + caId))
        {
            pageContentHtmlCache = (String)mappage.get(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE + caId);
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
        params.put("pageCacheKey", CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE);
        params.put("ygguuid", ygguuid);
        pageContentHtmlCache = processBrandPage(caId, request, response, mappage, params);
        pageContentHtmlCache = pageContentHtmlCache.replaceAll("(" + "taccountId" +"=[^\"]*)", "taccountId" + "=" + account.getAccountId());
        writer.write(pageContentHtmlCache);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping("/getbrandendsecond/{caId}")
    @ResponseBody
    public String getBrandEndSecond(@PathVariable("caId") String caId)
        throws Exception
    {
        String requestParams = "{'commonActivitiesId':'" + caId + "'}";
        String resParams = this.resourceService.getBrandEndSecond(requestParams);
        
        return resParams;
    }
    
    /**
     * 因brand页面都是静态化， 通过ajax请求到每个商品的productcount数量
     * 
     * @param ygguuid
     * @param productid
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/showproductcount/{productid}")
    @ResponseBody
    public String showProductCount(@CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid, @PathVariable("productid") String productid,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        int productcount = 0;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av != null)
        {
            int accountId = av.getId();
            if (accountId != CommonConstant.ID_NOT_EXIST)
            {
                ShoppingCartView scv = this.shoppingCartService.findNormalCartByPIdAndAId(Integer.parseInt(productid), accountId);
                if (scv != null)
                    productcount = scv.getProductCount();
            }
        }
        else if (ygguuid != null && !ygguuid.equals("") && !ygguuid.equals("tmpuuid"))
        {
            int tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
            if (tempAccountId != CommonConstant.ID_NOT_EXIST) // 生成一个UUID再加入表临时用户表中
            {
                int productId = Integer.parseInt(productid);
                TempShoppingCartView tsce = this.tempShoppingCartService.findNormalCartByPIdAndAId(productId, tempAccountId);
                if (tsce != null)
                    productcount = tsce.getProductCount();
            }
        }
        int productsellcount = this.productService.findProductSellCountById(Integer.parseInt(productid));
        
        JsonObject object = new JsonObject();
        object.addProperty("productcount", productcount);
        object.addProperty("productsellcount", productsellcount);
        return object.toString();
    }
    
    /**
     * 批量查询品牌团的中每个商品的信息 ajax请求
     * 
     * @param productids
     * @return
     */
    @RequestMapping("/showproductcounts")
    @ResponseBody
    public String showProductCounts(@CookieValue(value = "ygguuid", required = true, defaultValue = "tmpuuid") String ygguuid,
        @RequestParam(value = "productids", required = false, defaultValue = "0") String productids, HttpServletRequest request)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        if (productids == null || productids.equals("") || productids.equals("0"))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        
        List<Integer> productIds = new ArrayList<Integer>();
        String[] productArrays = productids.split(",");
        for (String pas : productArrays)
            productIds.add(new Integer(pas));
        
        List<Map<String, Object>> products = this.productService.findProductSellCountByIds(productIds);
        if (products == null || products.isEmpty())
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        for (Map<String, Object> record : products)
        {
            String productid = (record.get("productid") == null ? "0" : record.get("productid").toString());
            // String productsellcount = (
            // record.get("productsellcount")==null?"0":record.get("productsellcount").toString() ) ;
            
            int productcount = 0;
            if (av != null)
            {
                int accountId = av.getId();
                if (accountId != CommonConstant.ID_NOT_EXIST)
                {
                    ShoppingCartView scv = this.shoppingCartService.findNormalCartByPIdAndAId(Integer.parseInt(productid), accountId);
                    if (scv != null)
                        productcount = scv.getProductCount();
                }
            }
            else if (ygguuid != null && !ygguuid.equals("") && !ygguuid.equals("tmpuuid"))
            {
                int tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
                if (tempAccountId != CommonConstant.ID_NOT_EXIST) // 生成一个UUID再加入表临时用户表中
                {
                    int productId = Integer.parseInt(productid);
                    TempShoppingCartView tsce = this.tempShoppingCartService.findNormalCartByPIdAndAId(productId, tempAccountId);
                    if (tsce != null)
                        productcount = tsce.getProductCount();
                }
            }
            record.put("productcount", productcount);
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.add("products", parser.parse(JSONUtils.toJson(products, false)));
        return result.toString();
    }
    
    /**
     * 返回productIds
     * 
     * @param request
     * @param productDetailList
     * @param ygguuid
     * @return
     * @throws Exception
     */
    private String querySingleProductCount(HttpServletRequest request, List<ActivitiesProductView> productDetailList, String ygguuid)
        throws Exception
    {
        String productIds = "";
        if (productDetailList == null || productDetailList.isEmpty())
            return productIds;
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av != null) // 已login ,但uuid不会空时，表示已合并购物车
        {
            int accountId = av.getId();
            if (accountId == CommonConstant.ID_NOT_EXIST)
                return productIds;
            for (ActivitiesProductView apv : productDetailList)
            {
                int productId = Integer.parseInt(apv.getProductId());
                productIds += productId + ",";
                ShoppingCartView scv = this.shoppingCartService.findNormalCartByPIdAndAId(productId, accountId);
                if (scv != null)
                    apv.setProductCount(scv.getProductCount() + "");
            }
        }
        else if (ygguuid != null && !ygguuid.equals("") && !ygguuid.equals("tmpuuid")) // 已购物商品，但没有login
        {
            int tempAccountId = this.tempAccountService.findTempAccountIdByUUID(ygguuid);
            if (tempAccountId == CommonConstant.ID_NOT_EXIST) // 生成一个UUID再加入表临时用户表中
            {
                return productIds;
            }
            for (ActivitiesProductView apv : productDetailList)
            {
                int productId = Integer.parseInt(apv.getProductId());
                productIds += productId + ",";
                TempShoppingCartView tsce = this.tempShoppingCartService.findNormalCartByPIdAndAId(productId, tempAccountId);
                if (tsce != null)
                {
                    apv.setProductCount(tsce.getProductCount() + "");
                }
            }
        }
        else
        // 第一次进来
        {
            for (ActivitiesProductView apv : productDetailList)
            {
                int productId = Integer.parseInt(apv.getProductId());
                productIds += productId + ",";
            }
        }
        if (productIds != null && !productIds.equals(""))
            productIds = productIds.substring(0, productIds.length() - 1);
        
        return productIds;
    }
    
    /**
     * 自定义专场 专场的ID
     */
    @RequestMapping("/toaccm/{caId}")
    public void activitycustom(@PathVariable("caId") String caId)
    {
        // / 自定义专场的ID
        
    }
    
    @SuppressWarnings("unused")
    private String processBrandPage(String caId, HttpServletRequest request, HttpServletResponse response, Map<String, Object> mappage, Map<String, String> params)
        throws Exception
    {
        String pageCachekey = params.get("pageCacheKey");
        String ygguuid = params.get("ygguuid");
        
        String wxshareurlparams = CommonUtil.getWxShareUrl(params);
        
        Map<String, Object> mv = new HashMap<String, Object>();
        // / 通用专场的ID
        String requestParams = "{'commonActivitiesId':'" + caId + "'}";
        String result = null;
        
        Map<String, Object> mapdata = this.cacheService.getCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE);
        if (mapdata == null || mapdata.isEmpty() || !mapdata.containsKey(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE + caId))
        {
            if (mapdata == null)
                mapdata = new HashMap<String, Object>();
            
            result = this.resourceService.commonActivitiesList(requestParams);
            mapdata.put(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE + caId, result);
            this.cacheService.addCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE, mapdata, CacheConstant.CACHE_SECOND_30);
        }
        else
        {
            result = (String)mapdata.get(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE + caId);
        }
        
        String imgUrl = "";
        String wxShareTitle = "";
        String gegesay = "";
        if (null != result && !result.equals(""))
        {
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(result);
            String status = param.get("status").getAsString();
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()))
            {
                String second = (param.get("second") == null ? "" : param.get("second").getAsString()); // 倒计时
                String productStatus = (param.get("productStatus") == null ? CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue() : param.get("productStatus").getAsString());
                String name = (param.get("name") == null ? "" : param.get("name").getAsString());
                String desc = (param.get("desc") == null ? "" : param.get("desc").getAsString());
                String detail = (param.get("detail") == null ? "" : param.get("detail").getAsString());
                String wexinsharename = (param.get("wexinsharename") == null ? "" : param.get("wexinsharename").getAsString());
                String wexinsharedesc = (param.get("wexinsharedesc") == null ? "" : param.get("wexinsharedesc").getAsString());
                wxShareTitle = (param.get("wxShareTitle") == null ? "" : param.get("wxShareTitle").getAsString());
                gegesay = (param.get("gegesay") == null ? "" : param.get("gegesay").getAsString());
                String urlType = (param.get("urlType") == null ? "item" : param.get("urlType").getAsString());
                String bannerImage = (param.get("bannerImage") == null ? "" : param.get("bannerImage").getAsString());
                Object productIdList = param.get("productIdList");
                int type = param.get("type").getAsInt();
                mv.put("urlType", urlType);
                mv.put("second", second);
                mv.put("productStatus", productStatus);
                mv.put("name", name);
                mv.put("detail", detail);
                mv.put("desc", desc);
                //组合商品头图，微信分享时也使用这张图片 张礼德
                mv.put("bannerImage", bannerImage);
                mv.put("wexinsharename", wexinsharename);
                mv.put("wexinsharedesc", wexinsharedesc);
                // mv.put("wxShareTitle" , wxShareTitle) ;
                mv.put("gegesay", gegesay);
                
                mv.put("yggJsVersion", CommonUtil.getStaticJsVersion()); // 控制js css的版本号
                mv.put("yggCssVersion", CommonUtil.getStaticCssVersion());
                
                if (productIdList != null) // / 通过ID去查商品详情
                {
                    String productListJson = productIdList.toString();
                    String resultDetail = this.resourceService.commonActivitiesDetail("{'productIdList':" + productListJson + ", \"cid\":"+caId+"}", type); // {'productIdList':[1,2]}
                    if (resultDetail != null && !resultDetail.equals(""))
                    {
                        JsonObject paramDetail = (JsonObject)parser.parse(resultDetail);
                        Object productDetailListJson = paramDetail.get("productDetailList");
                        imgUrl = (paramDetail.get("imgUrl") == null ? "" : paramDetail.get("imgUrl").getAsString());
                        if (productDetailListJson != null)
                        {
                            List<ActivitiesProductView> productDetailList = JSONUtils.fromJson(productDetailListJson.toString(), new TypeToken<List<ActivitiesProductView>>()
                            {
                            });
                            String productIds = querySingleProductCount(request, productDetailList, ygguuid); // 查询单个商品已加入到购物车中的数量
                            
                            mv.put("detail", paramDetail.get("detail").getAsString());
                            mv.put("lowPrice", paramDetail.get("lowPrice").getAsString());
                            Collection<String> imageList = JSONUtils.fromJson(paramDetail.get("imageList").toString()
                            					, new TypeToken<Collection<String>>()         {  }
                            				);
                            mv.put("imageList", imageList);
                            mv.put("image", imgUrl);
                            mv.put("productDetailList", productDetailList);
                            
                            mv.put("productIds", productIds);
                        }
                    }
                }
            }
        }
        
        String sharebrandurl = YggWebProperties.getInstance().getProperties("sharebrandurl");
        sharebrandurl = sharebrandurl.replaceFirst("#1", caId);
        if (wxshareurlparams != null && !wxshareurlparams.equals(""))
        {
            wxshareurlparams = "?" + wxshareurlparams;
            sharebrandurl = sharebrandurl + wxshareurlparams;
        }
        String resquestParams = "{'url':'" + sharebrandurl + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, mv);
        mv.put("link", sharebrandurl); // 分享链接
        mv.put("imgUrl", imgUrl); // 分享图标
        mv.put("wxsharetitle", wxShareTitle);
        mv.put("wxsharedesc", gegesay);
        mv.put("gegesay", gegesay);
        
        mv.put("yggcontextPath", ContextPathUtil.getBasePath(request));
        mv.put("caId", caId);
        //分享相关
        mv.put("jsSdk",request.getAttribute("jsSdk"));
        String contentHtml = FreeMarkerUtil.createHtml("saleproduct/newBrand.ftl", mv);
        
        if (mappage == null)
        {
            mappage = new HashMap<String, Object>();
        }
        mappage.put(pageCachekey + caId, contentHtml);
        this.cacheService.addCache(pageCachekey, mappage, CacheConstant.CACHE_SECOND_30); // 7100
        return contentHtml;
    }
    
    
}
