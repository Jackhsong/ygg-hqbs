package com.ygg.webapp.controller;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.service.OrderPayService;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.FreeMarkerUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.BannerView;
import com.ygg.webapp.view.LaterView;
import com.ygg.webapp.view.NowView;

/**
 * 主页信息
 * 
 * @author lihc
 *
 */
@Controller("homeController")
// @Scope("prototype")
// @RequestMapping("/main")
public class HomeController
{
    Logger logger = Logger.getLogger(HomeController.class);
    
    @Resource(name = "resourceService")
    private ResourceService resourceService;
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "orderPayService")
    private OrderPayService orderPayService;
    
    /**
     * 主页面 改为返回主页的html内容 主页做缓存
     * 
     * @return
     */
    @RequestMapping("/index")
    // @ResponseBody
    public void tohome(@RequestParam(value = "reload", required = false, defaultValue = "0") String reload, HttpServletRequest request,
        HttpServletResponse response)
        throws Exception
    {
        
        // /////////////////////////////////////处理微信分享///////////////////////////////////////////////
        Map<String, String> params = CommonUtil.getAllRequestParam(request); // 从微信中来
        String wxindexshareFileContent = ""; // javascript代码段
        
        String wxshareurlparams = CommonUtil.getWxShareUrl(params);
        
        wxindexshareFileContent = this.cacheService.getCache(CacheConstant.YGG_WX_SHARE_JAVASCRIPT_CACHE);
        if (wxindexshareFileContent == null || wxindexshareFileContent.equals(""))
        {
            String wxindexshareFileName = HomeController.class.getClassLoader().getResource("wxindexshare.txt").getFile();
            wxindexshareFileContent = CommonUtil.readTxtFile(wxindexshareFileName);
            this.cacheService.addCache(CacheConstant.YGG_WX_SHARE_JAVASCRIPT_CACHE, wxindexshareFileContent, CacheConstant.CACHE_DAY_1);
            // this.memService.set(CacheConstant.YGG_WX_SHARE_JAVASCRIPT_CACHE,wxindexshareFileContent,0);
        }
        String sharehomeurl = YggWebProperties.getInstance().getProperties("sharehomeurl");
        if (wxshareurlparams != null && !wxshareurlparams.equals(""))
        {
            wxshareurlparams = "?" + wxshareurlparams;
            sharehomeurl = sharehomeurl + wxshareurlparams;
        }
        String resquestParams = "{'url':'" + sharehomeurl + "'}";
        Map<String, Object> mvwxshare = new HashMap<String, Object>();
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, mvwxshare);
        mvwxshare.put("link", sharehomeurl); // 分享链接
        mvwxshare.put("imgUrl", ""); // 分享图标
        mvwxshare.put("desc", "desc");
        
        if (mvwxshare != null && !mvwxshare.isEmpty())
        {
            for (Map.Entry<String, Object> entry : mvwxshare.entrySet())
            {
                String key = "wxshare_" + entry.getKey();
                String value = entry.getValue() + "";
                wxindexshareFileContent = wxindexshareFileContent.replaceAll(key, value);
            }
        }
        System.out.println(wxindexshareFileContent);
        
        // ///////////////////////////////////////////////////////////////////////////////////////////////
        
        // ///////////////////////////////不能保存整个页面，因为有倒计时，需要变化，可以在前台发ajax请求再去请求时间的变化，体验不好 isreload
        // 保证只清空一次/////////////以后做成定时器10刷缓存，防止并发请求过多，缓存过多的刷新///////////////////////////////////////
        String pageContentHtmlCache = null;
        
        Writer writer = response.getWriter();
        pageContentHtmlCache = cacheService.getCache(CacheConstant.YGG_PAGE_INDEX_CACHE);
        if (pageContentHtmlCache != null && !pageContentHtmlCache.equals(""))
        {
            writer.write(pageContentHtmlCache + wxindexshareFileContent);
            writer.flush();
            writer.close();
            return;
        }
        
        Map<String, Object> mv = new HashMap<String, Object>();
        List<BannerView> bannerList = null;
        List<NowView> nowList = null;
        List<LaterView> laterList = null;
        List<BannerView> bannerListCache = cacheService.getCache(CacheConstant.INDEX_BANNER_LIST_CACHE);
        List<NowView> nowListCache = cacheService.getCache(CacheConstant.INDEX_NOW_LIST_CACHE);
        List<LaterView> laterListCache = cacheService.getCache(CacheConstant.INDEX_LATER_LIST_CACHE);
        if (bannerListCache == null || bannerListCache.isEmpty())
        {
            bannerList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.BANNER.getValue());
            this.cacheService.addCache(CacheConstant.INDEX_BANNER_LIST_CACHE, bannerList, CacheConstant.CACHE_SECOND_30);
        }
        else
            bannerList = bannerListCache;
        
        if (nowListCache == null || nowListCache.isEmpty())
        {
            nowList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.NOW.getValue());
            this.cacheService.addCache(CacheConstant.INDEX_NOW_LIST_CACHE, nowList, CacheConstant.CACHE_SECOND_30);
        }
        else
        {
            nowList = nowListCache;
        }

        if (laterListCache == null || laterListCache.isEmpty())
        {
            laterList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.LATER.getValue());
            this.cacheService.addCache(CacheConstant.INDEX_LATER_LIST_CACHE, laterList, CacheConstant.CACHE_SECOND_30);
        }
        else
        {
            laterList = laterListCache;
        }
        String nowSellWindowIds = "";
        if (nowList != null && !nowList.isEmpty())
            for (NowView nv : nowList)
                nowSellWindowIds += nv.getSellWindowId() + ",";
        if (nowSellWindowIds != null && !nowSellWindowIds.equals(""))
        {
            nowSellWindowIds = nowSellWindowIds.substring(0, nowSellWindowIds.length() - 1);
            mv.put("nowSwIds", nowSellWindowIds);
        }
        
        String laterSellWindowIds = "";
        if (laterList != null && !laterList.isEmpty())
            for (LaterView lv : laterList)
                laterSellWindowIds += lv.getSellWindowId() + ",";
        
        if (laterSellWindowIds != null && !laterSellWindowIds.equals(""))
        {
            laterSellWindowIds = laterSellWindowIds.substring(0, laterSellWindowIds.length() - 1);
            mv.put("laterSwIds", laterSellWindowIds);
        }
        
        // 判断是否login ，没有login 时，从cookie 中拿出购物车中的内容
        // 如果是login 成功，从session中拿出购物车
        // 只需要拿出一个总数即可
        int shoppingcartCount = 0;
        
        mv.put(CacheConstant.INDEX_BANNER_LIST, bannerList);
        mv.put(CacheConstant.INDEX_NOW_LIST, nowList);
        mv.put(CacheConstant.INDEX_LATER_LIST, laterList);
        mv.put("cartCount", shoppingcartCount);
        mv.put("yggcontextPath", ContextPathUtil.getBasePath(request));
        
        mv.put("yggJsVersion", CommonUtil.getStaticJsVersion()); // 控制js css的版本号
        mv.put("yggCssVersion", CommonUtil.getStaticCssVersion());
        
        // /////////////////////////// end///////////////////////////////////
        
        // // 生成静态的index页面保存在内容中
        String contentHtml = FreeMarkerUtil.createHtml("index.ftl", mv);
        this.cacheService.addCache(CacheConstant.YGG_PAGE_INDEX_CACHE, contentHtml, CacheConstant.CACHE_SECOND_30);
        pageContentHtmlCache = cacheService.getCache(CacheConstant.YGG_PAGE_INDEX_CACHE);
        
        // logger.info("---------------------------HomeController-------reload-----------------");
        writer.write(pageContentHtmlCache + wxindexshareFileContent);
        writer.flush();
        writer.close();
        
    }
    
    @RequestMapping("/getrefreshtime")
    @ResponseBody
    public String getRefreshTimeAjax()
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        String refreshTime = getRefreshTime();
        result.addProperty("refreshtime", refreshTime);
        
        // 查询首页中的商品是否已抢完　
        List<NowView> nowList = resourceService.getIndexDisplays(CommonEnum.RESOURCE_TYPE.NOW.getValue());
        // this.cacheService.addCache(CacheConstant.INDEX_BANNER_LIST_CACHE, bannerList, 0);
        List<NowViewSoldOut> nvses = new ArrayList<NowViewSoldOut>();
        if (nowList != null && nowList.size() > 0)
        {
            for (NowView nv : nowList)
            {
                if (nv.getStatus().equals(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue()))
                {
                    NowViewSoldOut nvs = new NowViewSoldOut();
                    nvs.id = nv.getId();
                    nvs.type = nv.getType();
                    nvses.add(nvs);
                }
            }
        }
        result.add("nvs", parser.parse(JSONUtils.toJson(nvses, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    private class NowViewSoldOut
    {
        public String id;
        
        public String type;
        
        public String status;
        
    }
    
    private String getRefreshTime()
    {
        int saleTimeType = DateTime.now().getHourOfDay() >= 10 ? (DateTime.now().getHourOfDay() >= 20 ? 20 : 10) : 20;

        String refreshTime = "0";
        String curtimeStr = CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        Date curtime = CommonUtil.string2Date(curtimeStr, "yyyy-MM-dd HH:mm:ss");
        
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        if (saleTimeType == 20)
        {
            end.set(Calendar.HOUR_OF_DAY, 10);
        }
        else
        {
            end.set(Calendar.HOUR_OF_DAY, 20);
        }
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MINUTE, 0);
        String hour10everydayStr = CommonUtil.date2String(end.getTime(), "yyyy-MM-dd HH:mm:ss");
        Date hour10Date = CommonUtil.string2Date(hour10everydayStr, "yyyy-MM-dd HH:mm:ss");
        
        long time = (hour10Date.getTime() - curtime.getTime()) / 1000;
        if (time <= 0)
        {
            // / 查第二天的10天
            end.add(Calendar.DAY_OF_YEAR, 1);
            String nextHour10everydayStr = CommonUtil.date2String(end.getTime(), "yyyy-MM-dd HH:mm:ss");
            Date nexthour10Date = CommonUtil.string2Date(nextHour10everydayStr, "yyyy-MM-dd HH:mm:ss");
            time = (nexthour10Date.getTime() - curtime.getTime()) / 1000;
        }
        
        refreshTime = time + "";
        return refreshTime;
    }
    
    @RequestMapping("/getencrypt")
    @ResponseBody
    public String getEncrypt(@RequestParam(value = "shaencrypturl", required = false, defaultValue = "") String shaencrypturl, HttpServletRequest request,
        HttpServletResponse response)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        Map<String, Object> mv = new HashMap<String, Object>();
        String resquestParams = "{'url':'" + shaencrypturl + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, mv);
        mv.put("link", shaencrypturl); // 分享链接
        mv.put("imgUrl", ""); // 分享图标
        result.add("info", parser.parse(JSONUtils.toJson(mv, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        
        return result.toString();
    }
    
    // 新版 首页 ----------------------
    /**
     * 首页列表 ajax
     * 
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/homeList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String homeList(HttpServletRequest request,//
        @RequestParam(value = "type", required = false, defaultValue = "") String type,
        @RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback//
    )
        throws Exception
    {
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("type", type);
            Map<String, Object> result = resourceService.homeListNew(para);
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            return str;
        }
        catch (Exception e)
        {
            logger.error("获取首页列表失败！！！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            return str;
        }
    }
    
    /**
     * 首页详情 ajax
     * 
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/homeDetail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String homeDetail(
        HttpServletRequest request,//
        @RequestParam(value = "type", required = false, defaultValue = "") String type,
        @RequestParam(value = "nowList", required = false, defaultValue = "") String nowList,
        @RequestParam(value = "laterList", required = false, defaultValue = "") String laterList,
        @RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback
        )
        throws Exception
    {
        try
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("type", type);
            para.put("nowList", nowList);
            para.put("laterList", laterList);
            para.put("av", av);
            Map<String, Object> result = resourceService.homeDetailNew(para);
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            return str;
        }
        catch (Exception e)
        {
            logger.error("获取首页详情失败！！！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            return str;
        }
    }

}
