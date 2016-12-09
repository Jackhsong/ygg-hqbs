package com.ygg.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.entity.CityEntity;
import com.ygg.webapp.exception.BusException;
import com.ygg.webapp.service.AddressService;
import com.ygg.webapp.service.OrderService;
import com.ygg.webapp.service.ProductService;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.service.SaleWindowService;
import com.ygg.webapp.service.TestService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.FreeMarkerUtil;
import com.ygg.webapp.view.BannerView;
import com.ygg.webapp.view.LaterView;
import com.ygg.webapp.view.NowView;
import com.ygg.webapp.view.PageTime;

/**
 * 刷新缓存
 * 
 * @author lihc
 *
 */
@Controller("cacheController")
@RequestMapping("/cache")
public class CacheController
{
    
    Logger logger = Logger.getLogger(CacheController.class);
    
    String toCacheViewName = "redirect:/cache/tocache";
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "resourceService")
    private ResourceService resourceService;
    
    @Resource(name = "productService")
    private ProductService productService;
    
    @Resource(name = "saleWindowService")
    private SaleWindowService saleWindowService;
    
    @Resource(name = "addressService")
    private AddressService addressService;
    
    @Resource(name = "orderService")
    private OrderService orderService;
    
    @Resource(name = "testService")
    private TestService testService;
    
    /**
     * 跳到Cache页面
     * 
     * @return
     */
    @RequestMapping("/tocache")
    public String tocache()
    {
        return "cache/refreshcache";
    }
    
    @RequestMapping("/testst1")
    public String testSpringTransaction1(HttpServletRequest request)
        throws Exception
    {
        System.out.println(request.getRequestURL().toString());
        System.out.println(request.getRequestURI());
        
        return "redirect:/cache/tocache";
    }
    
    /**
     * 清空所有缓存
     * 
     * @return
     */
    @RequestMapping("/clearAllCache")
    public String clearAllCache()
    {
        // cacheService.clear();
        this.clearIndexCache();
        this.clearBrandCache();
        this.clearBrandPageCache();
        this.clearHomeIndexCache();
        this.clearProductCache();
        
        Set<String> keys = this.cacheService.getCache(CacheConstant.REQUEST_COST_TIME_CACHE);
        if (keys != null && !keys.isEmpty())
            for (String key : keys)
            {
                this.cacheService.clearCache(CacheConstant.REQUEST_COST_TIME_CACHE + key);
            }
        this.cacheService.clearCache(CacheConstant.REQUEST_COST_TIME_CACHE);
        return toCacheViewName;
    }
    
    /**
     * 清空首页cache
     * 
     * @return
     */
    @RequestMapping("/cic")
    public String clearIndexCache()
    {
        try
        {
            this.cacheService.clearCache(CacheConstant.INDEX_BANNER_LIST_CACHE);
            
            this.cacheService.clearCache(CacheConstant.INDEX_NOW_LIST_CACHE);
            
            this.cacheService.clearCache(CacheConstant.INDEX_LATER_LIST_CACHE);
            
            // // 以下是更新页面cache
            cacheService.clearCache(CacheConstant.YGG_PAGE_INDEX_CACHE);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BusException be = new BusException("清空首页缓存出错", toCacheViewName);
            throw be;
        }
        return toCacheViewName;
    }
    
    /**
     * 刷新首页缓存
     * 
     * @return
     */
    @RequestMapping("/ric")
    public String refreshIndexCache(HttpServletRequest request)
    {
        try
        {
            
            List<BannerView> bannerList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.BANNER.getValue());
            List<NowView> nowList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.NOW.getValue());
            List<LaterView> laterList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.LATER.getValue());
            
            this.cacheService.clearCache(CacheConstant.INDEX_BANNER_LIST_CACHE);
            this.cacheService.addCache(CacheConstant.INDEX_BANNER_LIST_CACHE, bannerList, 0);
            
            this.cacheService.clearCache(CacheConstant.INDEX_NOW_LIST_CACHE);
            this.cacheService.addCache(CacheConstant.INDEX_NOW_LIST_CACHE, nowList, 0);
            
            this.cacheService.clearCache(CacheConstant.INDEX_LATER_LIST_CACHE);
            this.cacheService.addCache(CacheConstant.INDEX_LATER_LIST_CACHE, laterList, 0);
            
            // // 以下是更新页面cache
            cacheService.clearCache(CacheConstant.YGG_PAGE_INDEX_CACHE);
            Map<String, Object> mv = new HashMap<String, Object>();
            mv.put(CacheConstant.INDEX_BANNER_LIST_CACHE, bannerList);
            mv.put(CacheConstant.INDEX_NOW_LIST_CACHE, nowList);
            mv.put(CacheConstant.INDEX_LATER_LIST_CACHE, laterList);
            mv.put("yggcontextPath", ContextPathUtil.getBasePath(request));
            mv.put("cartCount", "0");
            
            this.cacheService.addCache(CacheConstant.YGG_PAGE_INDEX_CACHE, FreeMarkerUtil.createHtml("indexhtml.ftl", mv), 0);
            ;
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BusException be = new BusException("刷新首页缓存出错", toCacheViewName);
            throw be;
        }
        return toCacheViewName;
    }
    
    /**
     * 只清空所有商品的缓存，不加载
     * 
     * @return
     */
    @RequestMapping("/cpc")
    public String clearProductCache()
    {
        
        try
        {
            
            this.cacheService.clearCache(CacheConstant.SINGLE_PRODUCT_CACHE);
            this.cacheService.clearCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE);
            
            // / Key用　ProductKey+"productId" 组合表示
            // 1.先查出所有商品的Id
            /*
             * List<Integer> productIds = this.productService.findAllProductIds() ; if(productIds!=null &&
             * !productIds.isEmpty() ) { for(Integer productId:productIds) {
             * this.cacheService.clearCache(CacheConstant.SINGLE_PRODUCT_CACHE+productId.intValue());
             * 
             * this.cacheService.clearCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE+productId.intValue()); } }
             */
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BusException be = new BusException("清空缓存出错", toCacheViewName);
            throw be;
        }
        return toCacheViewName;
        
    }
    
    /**
     * 刷新单个产品的Cache
     * 
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping("/rpc")
    public String refreshProductCache()
    {
        try
        {
            
            this.cacheService.clearCache(CacheConstant.SINGLE_PRODUCT_CACHE);
            // /因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
            this.cacheService.clearCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE);
            
            // / Key用　ProductKey+"productId" 组合表示
            // 1.先查出所有商品的Id
            List<Integer> productIds = this.productService.findAllProductIds();
            String result = null;
            if (productIds != null && !productIds.isEmpty())
            {
                Map<String, Object> mapdata = new HashMap<String, Object>();
                for (Integer productId : productIds)
                {
                    /*
                     * this.cacheService.clearCache(CacheConstant.SINGLE_PRODUCT_CACHE+productId.intValue()); result =
                     * this.resourceService.productBase("{'productId':'"+productId+"'}") ;
                     * this.cacheService.addCache(CacheConstant.SINGLE_PRODUCT_CACHE+productId.intValue(), result);
                     * 
                     * ///因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
                     * this.cacheService.clearCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE+productId.intValue());
                     */
                    
                    result = this.resourceService.productBase("{'productId':'" + productId + "'}");
                    mapdata.put(CacheConstant.SINGLE_PRODUCT_CACHE + productId.intValue(), result);
                }
                this.cacheService.addCache(CacheConstant.SINGLE_PRODUCT_CACHE, mapdata, 0);
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BusException be = new BusException("刷新商品缓存出错", toCacheViewName);
            throw be;
        }
        
        return toCacheViewName;
    }
    
    /**
     * 只清空所有专场的缓存，不加载
     * 
     * @return
     */
    @RequestMapping("/cbc")
    public String clearBrandCache()
    {
        
        try
        {
            
            this.cacheService.clearCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE);
            // /因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
            this.cacheService.clearCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE);
            
            // / Key用　ProductKey+"productId" 组合表示
            // 1.先查出所有商品的Id
            /*
             * List<Integer> salewindowIds = this.saleWindowService.findAllSaleWindowIds() ; if(salewindowIds!=null &&
             * !salewindowIds.isEmpty() ) { for(Integer salewindowId:salewindowIds) {
             * this.cacheService.clearCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE+salewindowId.intValue());
             * 
             * this.cacheService.clearCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE+salewindowId.intValue()); }
             * }
             */
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BusException be = new BusException("清空缓存出错", toCacheViewName);
            throw be;
        }
        return toCacheViewName;
        
    }
    
    /**
     * 刷新 通用专场　的Cache
     * 
     * @return
     */
    @RequestMapping("/rbc")
    public String refreshBrandCache()
    {
        try
        {
            
            this.cacheService.clearCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE);
            // /因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
            this.cacheService.clearCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE);
            
            // / Key用　特卖Key+"特卖ID" 组合表示
            // 1.先查出所有商品的Id
            List<Integer> salewindowIds = this.saleWindowService.findAllSaleWindowIds();
            String result = null;
            if (salewindowIds != null && !salewindowIds.isEmpty())
            {
                Map<String, Object> mapdata = new HashMap<String, Object>();
                for (Integer salewindowId : salewindowIds)
                {
                    /*
                     * this.cacheService.clearCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE+salewindowId.intValue());
                     * result = this.resourceService.commonActivitiesList("{'commonActivitiesId':'"+salewindowId+"'}") ;
                     * this.cacheService.addCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE+salewindowId.intValue(),
                     * result,0); ///因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
                     * this.cacheService.clearCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE
                     * +salewindowId.intValue());
                     */
                    
                    result = this.resourceService.commonActivitiesList("{'commonActivitiesId':'" + salewindowId + "'}");
                    mapdata.put(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE + salewindowId.intValue(), result);
                }
                this.cacheService.addCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE, mapdata, 0);
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            BusException be = new BusException("刷新专场缓存出错", toCacheViewName);
            throw be;
        }
        
        return toCacheViewName;
    }
    
    /**
     * 刷新所有的省市区到缓存中
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshAddress")
    public String refreshAddress()
        throws Exception
    {
        
        addressService.init();
        return toCacheViewName;
    }
    
    /**
     * 刷新所有的免邮费的邮费
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/refreshfreefreightmoney")
    public String refreshFreighFreetMoney()
        throws Exception
    {
        this.orderService.init();
        return toCacheViewName;
    }
    
    @RequestMapping("/getPageLogTime")
    public ModelAndView getPageLogTimes()
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("cache/showpagetime");
        Set<String> keys = this.cacheService.getCache(CacheConstant.REQUEST_COST_TIME_CACHE);
        if (keys == null)
            return mv;
        List<PageTime> pageTimes = new ArrayList<PageTime>();
        for (String key : keys)
        {
            PageTime pt = this.cacheService.getCache(CacheConstant.REQUEST_COST_TIME_CACHE + key);
            if (pt != null)
                pageTimes.add(pt);
            
        }
        
        mv.addObject("pageTimes", pageTimes);
        return mv;
    }
    
    /**
     * 远程清空首页缓存
     * 
     * @return
     */
    @RequestMapping("/clearhomepagecache")
    @ResponseBody
    public String clearHomeIndexCache()
    {
        JsonObject result = new JsonObject();
        try
        {
            this.cacheService.clearCache(CacheConstant.INDEX_BANNER_LIST_CACHE);
            this.cacheService.clearCache(CacheConstant.INDEX_NOW_LIST_CACHE);
            this.cacheService.clearCache(CacheConstant.INDEX_LATER_LIST_CACHE);
            
            // // 以下是更新页面cache
            cacheService.clearCache(CacheConstant.YGG_PAGE_INDEX_CACHE);
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        catch (Exception ex)
        {
            this.logger.error("清空首页缓存出错", ex);
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        
        return result.toString();
    }
    
    /**
     * 远程清空专场缓存
     * 
     * @return
     */
    @RequestMapping("/clearbrandpagecache")
    @ResponseBody
    public String clearBrandPageCache()
    {
        JsonObject result = new JsonObject();
        try
        {
            this.cacheService.clearCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE);
            // /因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
            this.cacheService.clearCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE);
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            
        }
        catch (Exception ex)
        {
            this.logger.error("清空专场缓存出错", ex);
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        return result.toString();
        
    }
    
    @RequestMapping("/testmodelattribute")
    // @ResponseBody
    public String testModelAttribute(@ModelAttribute("cityEntity") CityEntity cityEntity)
        throws Exception
    {
        System.out.println(cityEntity);
        return toCacheViewName; // return "{'success':'1'}";
    }
}
