package com.ygg.webapp.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.CustomLayoutTypeEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.ActivitiesCustomDao;
import com.ygg.webapp.dao.BannerWindowDao;
import com.ygg.webapp.dao.BrandDao;
import com.ygg.webapp.dao.CustomLayoutDaoIF;
import com.ygg.webapp.dao.MallWindowDaoIF;
import com.ygg.webapp.dao.PageCustomDao;
import com.ygg.webapp.dao.ProductBindDao;
import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.ProductMobileDetailDao;
import com.ygg.webapp.dao.SaleFlagDaoIF;
import com.ygg.webapp.dao.SaleTagDao;
import com.ygg.webapp.dao.SaleWindowDao;
import com.ygg.webapp.dao.SellerDao;
import com.ygg.webapp.dao.impl.mybatis.SaleFlagDaoImpl;
import com.ygg.webapp.entity.ActivitiesCommonEntity;
import com.ygg.webapp.entity.ActivitiesCustomEntity;
import com.ygg.webapp.entity.BannerWindowEntity;
import com.ygg.webapp.entity.BrandEntity;
import com.ygg.webapp.entity.CustomLayoutEntity;
import com.ygg.webapp.entity.MallWindowEntity;
import com.ygg.webapp.entity.PageCustomEntity;
import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.ProductMobileDetailEntity;
import com.ygg.webapp.entity.SaleWindowEntity;
import com.ygg.webapp.entity.SellerEntity;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.StringUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.ActivitiesProductView;
import com.ygg.webapp.view.BannerView;
import com.ygg.webapp.view.BaseView;
import com.ygg.webapp.view.LaterView;
import com.ygg.webapp.view.NowView;
import com.ygg.webapp.view.ProductDetailView;
import com.ygg.webapp.view.ProductSummaryView;
import com.ygg.webapp.view.TipView;

/**
 * 查询和首页相关的Service
 * 
 * @author lihc
 * 
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService
{
    @Resource
    private AccountDao adi;
    
    @Resource(name = "bannerWindowDao")
    private BannerWindowDao bwdi = null;
    
    @Resource(name = "saleWindowDao")
    private SaleWindowDao swdi = null; // new SaleWindowDaoImpl();
    
    @Resource(name = "saleTagDao")
    private SaleTagDao stdi = null; // new SaleTagDaoImpl();
    
    @Resource(name = "productCountDao")
    private ProductCountDao pcdi = null;
    
    @Resource(name = "productBindDao")
    private ProductBindDao pbdi = null;
    
    @Resource(name = "productDao")
    private ProductDao pdi = null;
    
    @Resource(name = "sellerDao")
    private SellerDao sdi = null;
    
    @Resource(name = "brandDao")
    private BrandDao bdi = null;
    
    @Resource(name = "pageCustomDao")
    private PageCustomDao pagecdi = null;
    
    @Resource(name = "productMobileDetailDao")
    private ProductMobileDetailDao pmddi = null;
    
    @Resource(name = "activitiesCommonDao")
    private ActivitiesCommonDao acdi = null;
    
    @Resource(name = "productCommonDao")
    private ProductCommonDao pcommondi = null;
    
    @Resource(name = "activitiesCustomDao")
    private ActivitiesCustomDao activitiescdi = null;
    
    @Resource(name = "memService")
    private CacheServiceIF cache;
    
    @Resource(name = "saleFlagDaoIF")
    private SaleFlagDaoIF sfdi = new SaleFlagDaoImpl();
    
    @Resource
    private MallWindowDaoIF mwdi;
    
    @Resource
    private CustomLayoutDaoIF cldi;
    
    public String homeList(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String type = param.get("type").getAsString();
        if (type.contains(CommonEnum.RESOURCE_TYPE.BANNER.getValue()))
        {
            List<Integer> bannerIdList = bwdi.findCurrDisplayBannerId();
            result.add("bannerList", parser.parse(bannerIdList.toString()));
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.NOW.getValue()))
        {
            List<Integer> nowList = swdi.findCurrDisplayNowId();
            result.add("nowList", parser.parse(nowList.toString()));
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.LATER.getValue()))
        {
            List<Integer> laterList = swdi.findCurrDisplayLaterId();
            result.add("laterList", parser.parse(laterList.toString()));
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public List<BannerView> getBannerDisplays()
        throws Exception
    {
        
        List<BannerWindowEntity> bwes = bwdi.findDisplayBannersInfo();
        if (null == bwes || bwes.isEmpty())
            return null;
        List<BannerView> bvs = new ArrayList<BannerView>();
        for (BannerWindowEntity bwe : bwes)
        {
            BannerView bv = new BannerView();
            bv.setId(bwe.getDisplayId() + "");
            bv.setImage(bwe.getImage());
            bv.setType(bwe.getType() + "");
            
            setUrl(bv, bwe.getType(), bv.getId());
            
            bvs.add(bv);
        }
        return bvs;
    }
    
    public void setUrl(BaseView bv, byte type, String displayId)
        throws Exception
    {
        if (type > 0 && type == (byte)CommonEnum.SALE_TYPE.ACTIVITIES_CUSTOM.getValue())
        {
            String url = this.customActivitiesDetail("{'customActivitiesId':'" + displayId + "'}");
            bv.setUrl(url);
        }
    }
    
    /**
     * 得到首页信息
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseView> List<T> getIndexDisplays(String params)
        throws Exception
    {
        if (null == params || params.equals(""))
            throw new IllegalArgumentException("params is not null !");
        
        if (params.equals(CommonEnum.RESOURCE_TYPE.BANNER.getValue()))
            return (List<T>)getBannerDisplays();
        else if (params.equals(CommonEnum.RESOURCE_TYPE.NOW.getValue()))
            return (List<T>)getNowDisplays();
        else if (params.equals(CommonEnum.RESOURCE_TYPE.LATER.getValue()))
            return (List<T>)getLaterDisplays();
        return null;
    }
    
    /**
     * 得到所有今天特卖的商品
     * 
     * @return
     * @throws Exception
     */
    public List<NowView> getNowDisplays()
        throws Exception
    {
        int saleTimeType = DateTime.now().getHourOfDay() >= 10 ? (DateTime.now().getHourOfDay() >= 20 ? 20 : 10) : 20;
        List<SaleWindowEntity> nowList = null;
        if (saleTimeType == 20)
        {
            nowList = swdi.findDisplayNowInfos();
        }
        else
        {
            // 10点 至 20点之间，不查询今日开始的晚场特卖
            List<Integer> idList = swdi.findTodayDisplayId(Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_20.getValue()));
            nowList = swdi.findCurrDisplayNowIdWhereIdNotIn(idList);
        }
        
        if (null == nowList || nowList.isEmpty())
            return null;
        List<NowView> nvs = new ArrayList<NowView>();
        Calendar curr = Calendar.getInstance();
        for (SaleWindowEntity swe : nowList)
        {
            NowView nv = new NowView();
            nv.setSellWindowId(swe.getId() + "");
            nv.setId(swe.getDisplayId() + "");
            nv.setImage(swe.getImage());
            nv.setType(swe.getType() + "");
            nv.setLeftDesc(swe.getName());
            nv.setRightDesc(swe.getDesc());
            nv.setLabers(swe.getSaleTagLabels());
            
            Calendar end = Calendar.getInstance();
            Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
            end.setTime(endDate);
            end.add(Calendar.DAY_OF_YEAR, 1);
            if (swe.getSaleTimeType() == Byte.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_10.getValue()))
            {
                end.set(Calendar.HOUR_OF_DAY, 10);
            }
            else
            {
                end.set(Calendar.HOUR_OF_DAY, 20);
            }
            nv.setEndSecond(((end.getTimeInMillis() - curr.getTimeInMillis()) / 1000) + "");
            
            // nv.setEndSecond(CommonUtil.date2String(endDate, "yyyy/MM/dd")+" 10:00:00"); ////// 由前端计算剩余时间
            // 自定义活动获取url
            setUrl(nv, swe.getType(), nv.getId());
            
            if (swe.getType() == CommonEnum.SALE_TYPE.PRODUCT.getValue())
            {
                
                ProductCountEntity pce = pcdi.findCountInfoById(swe.getDisplayId());
                ProductEntity pe = pdi.findProductPartById(swe.getDisplayId());
                
                if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                    Date currTime = new Date();
                    if (currTime.before(startTime) || currTime.after(endTime)) // 未开始或已结束
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    }
                    else
                    {
                        int stock = pce.getStock();
                        if (stock == 0)
                        {
                            nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                        }
                        else if (pce.getLock() >= stock)
                        {
                            nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                        }
                        else
                        {
                            nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                        }
                    }
                }
                else
                {
                    nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                }
                
            }
            else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue())
            {
                
                ActivitiesCommonEntity ace = acdi.findActivitiesInfoById(swe.getDisplayId());
                // nv.setCommonActivitiesName(ace.getName());
                
                List<Integer> availableProductIds = new ArrayList<Integer>();
                Object nvStatusCache = cache.getCache(CacheConstant.RES_HOMEDETAIL_NOW_NVSTATUS_KEY + swe.getDisplayId());
                if (nvStatusCache != null)
                {
                    availableProductIds = (List<Integer>)nvStatusCache;
                }
                else
                {
                    List<Integer> relationProductIds = acdi.findProductIdsById(swe.getDisplayId());
                    for (Integer relationProductId : relationProductIds)
                    {
                        ProductEntity pe = pdi.findProductPartById(relationProductId);
                        Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                        Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                        Date currTime = new Date();
                        if (!(pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()) || currTime.before(startTime) || currTime.after(endTime)))
                        {
                            availableProductIds.add(relationProductId);
                        }
                    }
                    cache.addCache(CacheConstant.RES_HOMEDETAIL_NOW_NVSTATUS_KEY + swe.getDisplayId(), availableProductIds, CacheConstant.CACHE_SECOND_30);
                }
                
                ProductCountEntity pce = pcdi.findProductCountSumByProductIds(availableProductIds);
                int stock = pce.getStock();
                if (stock == 0)
                {
                    nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                }
                else if (pce.getLock() >= stock)
                {
                    nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                }
                else
                {
                    nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                }
                
            }
            else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_CUSTOM.getValue())
            {
                nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                // ProductCountEntity pce = pcdi.findProductCountSumByCustomId(swe.getDisplayId());
                // if (pce == null)
                // {
                // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                // }
                // else
                // {
                // int stock = pce.getStock();
                // if (stock == 0)
                // {
                // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                // }
                // else if (pce.getLock() >= stock)
                // {
                // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                // }
                // else
                // {
                // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                // }
                // }
            }
            
            nvs.add(nv);
        }
        
        return nvs;
        
    }
    
    /**
     * 得到所有即将特卖的商品
     * 
     * @return
     * @throws Exception
     */
    public List<LaterView> getLaterDisplays()
        throws Exception
    {
        int saleTimeType = DateTime.now().getHourOfDay() >= 10 ? (DateTime.now().getHourOfDay() >= 20 ? 20 : 10) : 20;
        List<SaleWindowEntity> laterList = new ArrayList<>();// laterList = swdi.findDisplayLaterInfos();
        if (saleTimeType == 20)
        {
            // 10点场在前20点场在后
            laterList.addAll(swdi.findDisplayLaterInfos(Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_10.getValue())));
            laterList.addAll(swdi.findDisplayLaterInfos(Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_20.getValue())));
        }
        else
        {
            // 今日20点场 + 明日10点场
            laterList.addAll(swdi.findTodayDisplayInfos(Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_20.getValue())));
            laterList.addAll(swdi.findDisplayLaterInfos(Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_10.getValue())));
        }
        if (null == laterList || laterList.isEmpty())
            return null;
        List<LaterView> lvs = new ArrayList<LaterView>();
        Calendar curr = Calendar.getInstance();
        for (SaleWindowEntity swe : laterList)
        {
            LaterView nv = new LaterView();
            nv.setSellWindowId(swe.getId() + "");
            nv.setId(swe.getDisplayId() + "");
            nv.setImage(swe.getImage());
            nv.setLabers(swe.getSaleTagLabels());
            
            Calendar start = Calendar.getInstance();
            Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
            start.setTime(startDate);
            start.set(Calendar.HOUR_OF_DAY, 10);
            nv.setStartSecond(((start.getTimeInMillis() - curr.getTimeInMillis()) / 1000) + "");
            
            // nv.setStartSecond(CommonUtil.date2String(startDate, "yyyy/MM/dd")+" 10:00:00" ); /// 由JS去计算时间
            
            nv.setLeftDesc(swe.getName());
            nv.setRightDesc(swe.getDesc());
            nv.setType(swe.getType() + "");
            
            setUrl(nv, swe.getType(), nv.getId());
            
            lvs.add(nv);
        }
        
        return lvs;
    }
    
    @Override
    public String homeDetail(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String type = param.get("type").getAsString();
        if (type.contains(CommonEnum.RESOURCE_TYPE.BANNER.getValue()))
        {
            List<Integer> bannerIds = new ArrayList<Integer>();
            JsonArray bannerIdJson = (JsonArray)param.get("bannerList");
            Iterator<JsonElement> it = bannerIdJson.iterator();
            while (it.hasNext())
            {
                bannerIds.add(it.next().getAsInt());
            }
            List<BannerWindowEntity> bwes = bwdi.findDisplayBannerInfoByIds(bannerIds);
            
            List<BannerView> bvs = new ArrayList<BannerView>();
            for (BannerWindowEntity bwe : bwes)
            {
                BannerView bv = new BannerView();
                bv.setId(bwe.getDisplayId() + "");
                bv.setImage(bwe.getImage());
                bv.setType(bwe.getType() + "");
                bvs.add(bv);
            }
            result.add("bannerDetailList", parser.parse(JSONUtils.toJson(bvs, false)));
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.NOW.getValue()))
        {
            List<NowView> nvs = new ArrayList<NowView>();
            List<Integer> nowIds = new ArrayList<Integer>();
            JsonArray nowIdJson = (JsonArray)param.get("nowList");
            Iterator<JsonElement> it = nowIdJson.iterator();
            while (it.hasNext())
            {
                nowIds.add(it.next().getAsInt());
            }
            List<SaleWindowEntity> swes = swdi.findDisplayNowInfoByIds(nowIds);
            
            // 也可使用join查询
            List<List<Integer>> tagIds = swdi.findTagIdByIds(nowIds);
            List<List<String>> labelImages = new ArrayList<List<String>>();
            // 也可使用批量查询
            for (List<Integer> tagid : tagIds)
            {
                labelImages.add(stdi.findImagesByIds(tagid));
            }
            Calendar curr = Calendar.getInstance();
            for (int i = 0; i < nowIds.size(); i++)
            {
                SaleWindowEntity swe = swes.get(i);
                List<String> laberImage = labelImages.get(i);
                NowView nv = new NowView();
                nv.setId(swe.getDisplayId() + "");
                nv.setImage(swe.getImage());
                nv.setType(swe.getType() + "");
                nv.setLeftDesc(swe.getName());
                nv.setRightDesc(swe.getDesc());
                nv.setLabers(laberImage);
                
                Calendar end = Calendar.getInstance();
                Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                end.setTime(endDate);
                end.add(Calendar.DAY_OF_YEAR, 1);
                end.set(Calendar.HOUR_OF_DAY, 10);
                nv.setEndSecond(((end.getTimeInMillis() - curr.getTimeInMillis()) / 1000) + "");
                
                if (swe.getType() == CommonEnum.SALE_TYPE.PRODUCT.getValue())
                {
                    ProductCountEntity pce = pcdi.findProductCountInfoById(swe.getDisplayId());
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    }
                    else if (pce.getLock() >= stock)
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                    }
                    else
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    }
                }
                else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue())
                {
                    ProductCountEntity pce = pcdi.findProductCountSumByCommonId(swe.getDisplayId());
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    }
                    else if (pce.getLock() >= stock)
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                    }
                    else
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    }
                }
                else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_CUSTOM.getValue())
                {
                    nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    // ProductCountEntity pce = pcdi.findProductCountSumByCustomId(swe.getDisplayId());
                    // int stock = pce.getStock();
                    // if (stock == 0)
                    // {
                    // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    // }
                    // else if (pce.getLock() >= stock)
                    // {
                    // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                    // }
                    // else
                    // {
                    // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    // }
                }
                nvs.add(nv);
            }
            result.add("nowDetailList", parser.parse(JSONUtils.toJson(nvs, false)));
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.LATER.getValue()))
        {
            List<LaterView> lvs = new ArrayList<LaterView>();
            List<Integer> laterIds = new ArrayList<Integer>();
            JsonArray laterIdJson = (JsonArray)param.get("laterList");
            Iterator<JsonElement> it = laterIdJson.iterator();
            while (it.hasNext())
            {
                laterIds.add(it.next().getAsInt());
            }
            List<SaleWindowEntity> swes = swdi.findDisplayLaterInfoByIds(laterIds);
            
            // 也可分解查询
            List<List<String>> laberImages = stdi.findImagesBySaleIds(laterIds);
            Calendar curr = Calendar.getInstance();
            for (int i = 0; i < laterIds.size(); i++)
            {
                SaleWindowEntity swe = swes.get(i);
                List<String> laberImage = laberImages.get(i);
                LaterView nv = new LaterView();
                nv.setId(swe.getDisplayId() + "");
                nv.setImage(swe.getImage());
                nv.setLabers(laberImage);
                
                Calendar start = Calendar.getInstance();
                Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
                start.setTime(startDate);
                start.set(Calendar.HOUR_OF_DAY, 10);
                nv.setStartSecond(((start.getTimeInMillis() - curr.getTimeInMillis()) / 1000) + "");
                
                nv.setLeftDesc(swe.getName());
                nv.setRightDesc(swe.getDesc());
                nv.setType(swe.getType() + "");
                lvs.add(nv);
            }
            result.add("laterDetailList", parser.parse(JSONUtils.toJson(lvs, false)));
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String productBase(String requestParams)
        throws Exception
    {
        DecimalFormat df = new DecimalFormat("0.00");
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int productId = param.get("productId").getAsInt();
        
        // 设置 productStatus与second
        /*
         * ProductBindEntity pbe = pbdi.findProductBindInfoById(productId); if (pbe != null) // 查询到商品绑定的特卖信息 {
         * SaleWindowEntity swe = swdi.findSaleInfoById(pbe.getSaleWindowId()); int curr = CommonUtil.getNowSaleDate();
         * if (curr < swe.getStartTime()) // 未开始 { result.addProperty("productStatus",
         * CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()); Calendar start = Calendar.getInstance(); Date
         * startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd"); start.setTime(startDate);
         * start.set(Calendar.HOUR_OF_DAY, 10); result.addProperty("second", ((start.getTimeInMillis() -
         * Calendar.getInstance().getTimeInMillis())/1000) + "");
         * 
         * Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd"); result.addProperty("second",
         * CommonUtil.date2String(startDate, "yyyy/MM/dd")+" 10:00:00"); ////// 由前端计算剩余时间
         * 
         * } else if (curr > swe.getEndTime()) // 已结束 { result.addProperty("productStatus",
         * CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()); result.addProperty("second", ""); } else {
         * ProductCountEntity pce = pcdi.findProductCountInfoById(productId); int stock = pce.getStock(); if (stock ==
         * 0) { result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
         * result.addProperty("second", ""); } else if (pce.getLock() >= stock) { result.addProperty("productStatus",
         * CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue()); Calendar end = Calendar.getInstance(); Date endDate
         * = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd"); end.setTime(endDate);
         * end.add(Calendar.DAY_OF_YEAR, 1); end.set(Calendar.HOUR_OF_DAY, 10); result.addProperty("second",
         * ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis())/1000) + "");
         * 
         * Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd"); result.addProperty("second",
         * CommonUtil.date2String(endDate, "yyyy/MM/dd")+" 10:00:00"); ////// 由前端计算剩余时间
         * 
         * } else { result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()); Calendar
         * end = Calendar.getInstance(); Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
         * end.setTime(endDate); end.add(Calendar.DAY_OF_YEAR, 1); end.set(Calendar.HOUR_OF_DAY, 10);
         * result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis())/1000) + "");
         * 
         * 
         * Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd"); result.addProperty("second",
         * CommonUtil.date2String(endDate, "yyyy/MM/dd")+" 10:00:00"); ////// 由前端计算剩余时间
         * 
         * } }
         * 
         * //// 加上开始时间和end时间 Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
         * result.addProperty("startDate", CommonUtil.date2String(startDate, "yyyy/MM/dd")+" 10:00:00"); //////
         * 由前端计算剩余时间
         * 
         * Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd"); result.addProperty("endDate",
         * CommonUtil.date2String(endDate, "yyyy/MM/dd")+" 10:00:00"); ////// 由前端计算剩余时间
         * 
         * } else // 没有查询到商品绑定的特卖信息 { result.addProperty("productStatus",
         * CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()); result.addProperty("second", ""); }
         */
        
        ProductEntity pe = pdi.findProductInfoById(productId);
        if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 查询到商品绑定的特卖信息
        {
            if (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                Date currTime = new Date();
                
                if (currTime.before(startTime)) // 未开始
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    result.addProperty("second", ((startTime.getTime() - currTime.getTime()) / 1000) + "");
                }
                else if (currTime.after(endTime)) // 已结束
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    result.addProperty("second", "");
                }
                else
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(productId);
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        result.addProperty("second", "");
                    }
                    else if (pce.getLock() >= stock)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                        result.addProperty("second", ((endTime.getTime() - currTime.getTime()) / 1000) + "");
                    }
                    else
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        result.addProperty("second", ((endTime.getTime() - currTime.getTime()) / 1000) + "");
                    }
                }
            }
            else if (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                ProductCountEntity pce = pcdi.findCountInfoById(productId);
                int stock = pce.getStock();
                result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                if (stock == 0)
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                }
                else if (pce.getLock() >= stock)
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                }
                result.addProperty("second", "");
            }
        }
        else
        // 已下架
        {
            result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
            result.addProperty("second", "");
        }
        
        // 设置imageList
        // ProductEntity pe = pdi.findProductInfoById(productId);
        List<String> imageList = new ArrayList<String>();
        if (!CommonUtil.isBlank(pe.getImage1()))
        {
            imageList.add(pe.getImage1());
        }
        if (!CommonUtil.isBlank(pe.getImage2()))
        {
            imageList.add(pe.getImage2());
        }
        if (!CommonUtil.isBlank(pe.getImage3()))
        {
            imageList.add(pe.getImage3());
        }
        if (!CommonUtil.isBlank(pe.getImage4()))
        {
            imageList.add(pe.getImage4());
        }
        if (!CommonUtil.isBlank(pe.getImage5()))
        {
            imageList.add(pe.getImage5());
        }
        result.add("imageList", parser.parse(JSONUtils.toJson(imageList, false)));
        
        SellerEntity se = sdi.findBrandInfoById(pe.getSellerId());
        /**替换单引号和去除换行符*/
        result.addProperty("name", StringUtil.replaceQuotAndEnter(pe.getName()));
        result.addProperty("highPrice", df.format(pe.getMarketPrice()));
        result.addProperty("lowPrice", df.format(pe.getSalesPrice()));
        result.addProperty("pcDetail", buildPcDetailToHtml(productId, se.getSellerType() != 1)); // pe.getPcDetail1() +
                                                                                                 // "");
        result.addProperty("pcDetail",  pe.getPcDetail() +  "");        
        ProductCountEntity pce = pcdi.findProductCountInfoById(productId);
        result.addProperty("sellCount", (pce == null ? "0" : pce.getSell() + ""));
        if (result.get("productStatus").getAsString().equals(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()))
        {
            result.addProperty("stockCount", "0");
        }
        else
        {
            result.addProperty("stockCount", (pce == null ? "0" : pce.getStock() + ""));
        }
        
        List<String> notes = new ArrayList<String>();
        
        if (se.getSellerType() == CommonEnum.SELLER_DELIVERY_TYPE.BONDED.getValue())
        {
            result.addProperty("isBonded", CommonEnum.COMMON_IS.YES.getValue());
            notes.add(CommonConstant.PRODUCT_BONDED_IS_TIP);
        }
        else if (se.getSellerType() == CommonEnum.SELLER_DELIVERY_TYPE.HONGKONG.getValue())
        {
            notes.add(CommonConstant.PRODUCT_HONGKONG_IS_TIP);
            result.addProperty("isBonded", CommonEnum.COMMON_IS.NO.getValue());
        }
        
        String sellerName = se.getSellerName().contains("左岸城堡") ? se.getSellerName() : "左岸城堡";
        notes.add("* 本产品由杭州左岸有限公司发货。");
        result.add("notes", parser.parse(JSONUtils.toJson(notes, false)));
        // else
        // {
        // result.addProperty("isBonded", CommonEnum.COMMON_IS.NO.getValue());
        // }
        
        result.addProperty("sellerName", sellerName);
        /**替换单引号和去除换行符*/
        result.addProperty("gegeSay",StringUtil.replaceQuotAndEnter(pe.getDesc()));
        
        // 格格说头像
        String gegeSayImage = pdi.findGegeImageById(pe.getGegeImageId());
        if (StringUtils.isEmpty(gegeSayImage))
        {
            gegeSayImage = "http://yangege.b0.upaiyun.com/gegeImage/product/11de8455f396d.png";
        }
        result.addProperty("gegeSayImage", gegeSayImage);
        
        BrandEntity be = bdi.findBrandInfoById(pe.getBrandId());
        ProductSummaryView psv = new ProductSummaryView();
        psv.setBrand(be.getName());
        psv.setName(pe.getName());
        psv.setSpecification(pe.getNetVolume());
        psv.setPlaceOfOrigin(pe.getPlaceOfOrigin());
        psv.setStorageMethod(pe.getStorageMethod());
        psv.setDurabilityPeriod(pe.getDurabilityPeriod());
        psv.setPeopleFor(pe.getPeopleFor());
        psv.setFoodMethod(pe.getFoodMethod());
        psv.setUseMethod(pe.getUseMethod());
        psv.setManufacturerDate(pe.getManufacturerDate());
        psv.setTip(pe.getTip());
        result.add("summary", parser.parse(JSONUtils.toJson(psv, false)));
        
        List<TipView> tipList = new ArrayList<TipView>();
        List<PageCustomEntity> pces = pagecdi.findPageCustomInfoByProductIds(productId);
        for (PageCustomEntity pageCustomEntity : pces)
        {
            TipView tv = new TipView();
            tv.setName(pageCustomEntity.getName());
            tv.setLink(pageCustomEntity.getUrl());
            tipList.add(tv);
        }
        result.add("tipList", parser.parse(JSONUtils.toJson(tipList, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        
        ProductCommonEntity pcet = this.pcommondi.findProductCommonInfoById(productId);
        if (pcet != null)
            result.addProperty("wexinShareImgUrl", pcet.getSmallImage()); // 作为微信分享的图标r
            
        return result.toString();
    }
    
    private String buildPcDetailToHtml(int productId, boolean isOverseas)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        List<ProductDetailView> productDetailList = findProductMobileDetail(productId);
        sb.append("<div>");
        if (productDetailList != null && productDetailList.size() > 0)
        {
            for (ProductDetailView pdv : productDetailList)
            {
                String contentType = pdv.getContentType();
                String isLink = pdv.getIsLink();
                String content = pdv.getContent();
                String link = pdv.getLink();
                if (contentType != null && contentType.equals("2")) // 文字 暂不写Enum只接用数字表示notime
                {
                    if (isLink != null && isLink.equals("1") && link != null && !link.equals(""))
                        sb.append("<a href=\"" + link + "\">" + content + "</a>");
                    else
                        sb.append(content);
                    
                }
                else if (contentType != null && contentType.equals("1"))
                {
                    if (isLink != null && isLink.equals("1") && link != null && !link.equals(""))
                        sb.append("<a href=\"" + link + "\"><img src=\"" + content + "\" ></a>");
                    else
                        sb.append("<img src=\"" + content + "\" />");
                }
                sb.append("</br>");
            }
        }
        if (isOverseas)
        {
            sb.append("<img src=\"http://img.gegejia.com/baobeigaozhi.jpg\" />");
            sb.append("</br>");
        }
        sb.append("</div>");
        return sb.toString();
    }
    
    @Override
    public String productDetail(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int productId = param.get("productId").getAsInt();
        
        /*
         * List<ProductDetailView> productDetailList = new ArrayList<ProductDetailView>();
         * List<ProductMobileDetailEntity> pmdes = pmddi.findMobileDetailInfoByProductId(productId); for
         * (ProductMobileDetailEntity pmde : pmdes) { ProductDetailView pdv = new ProductDetailView();
         * pdv.setOrder(pmde.getOrder() + ""); pdv.setContentType(pmde.getContentType() + "");
         * pdv.setHeight(pmde.getHeight() + ""); pdv.setWidth(pmde.getWidth() + ""); pdv.setIsLink(pmde.getIsLink() +
         * ""); pdv.setLinkType(pmde.getLinkType() + ""); pdv.setContent(pmde.getContent());
         * pdv.setLink(pmde.getLink()); productDetailList.add(pdv); }
         */
        List<ProductDetailView> productDetailList = findProductMobileDetail(productId);
        if (productDetailList != null && productDetailList.size() > 0)
            result.add("productDetailList", parser.parse(JSONUtils.toJson(productDetailList, false)));
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    private List<ProductDetailView> findProductMobileDetail(int productId)
    {
        List<ProductDetailView> productDetailList = new ArrayList<ProductDetailView>();
        List<ProductMobileDetailEntity> pmdes = pmddi.findMobileDetailInfoByProductId(productId);
        for (ProductMobileDetailEntity pmde : pmdes)
        {
            ProductDetailView pdv = new ProductDetailView();
            pdv.setOrder(pmde.getOrder() + "");
            pdv.setContentType(pmde.getContentType() + "");
            pdv.setHeight(pmde.getHeight() + "");
            pdv.setWidth(pmde.getWidth() + "");
            pdv.setIsLink(pmde.getIsLink() + "");
            pdv.setLinkType(pmde.getLinkType() + "");
            pdv.setContent(pmde.getContent());
            pdv.setLink(pmde.getLink());
            productDetailList.add(pdv);
        }
        return productDetailList;
    }
    
    @Override
    public String commonActivitiesList(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int commonActivitiesId = param.get("commonActivitiesId").getAsInt();
        ActivitiesCommonEntity ace = acdi.findActivitiesInfoById(commonActivitiesId);
        String urlType = "item";
        // 特卖
        if (ace.getType() == 1)
        {
            // 设置 productStatus与second
            SaleWindowEntity swe = swdi.findSaleInfoByDisplayId(commonActivitiesId, (byte)CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue());
            if (swe != null) // 查询到对应的特卖信息
            {
                int curr = CommonUtil.getNowSaleDate();
                if (curr < swe.getStartTime()) // 未开始
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    Calendar start = Calendar.getInstance();
                    Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
                    start.setTime(startDate);
                    start.set(Calendar.HOUR_OF_DAY, 10);
                    result.addProperty("second", ((start.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                    /*
                     * Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
                     * result.addProperty("second", CommonUtil.date2String(startDate, "yyyy/MM/dd")+" 10:00:00"); //////
                     * 由前端计算剩余时间
                     */
                }
                else if (curr > swe.getEndTime()) // 已结束
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    result.addProperty("second", "");
                }
                else
                {
                    ProductCountEntity pce = pcdi.findProductCountSumByCommonId(commonActivitiesId);
                    int stock = (pce == null ? 0 : pce.getStock());
                    if (stock == 0)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        result.addProperty("second", "");
                    }
                    else if (pce.getLock() >= stock)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                        Calendar end = Calendar.getInstance();
                        Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                        end.setTime(endDate);
                        end.add(Calendar.DAY_OF_YEAR, 1);
                        end.set(Calendar.HOUR_OF_DAY, 10);
                        result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                        
                        /*
                         * Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                         * result.addProperty("second", CommonUtil.date2String(endDate, "yyyy/MM/dd")+" 10:00:00");
                         * ////// 由前端计算剩余时间
                         */
                    }
                    else
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        Calendar end = Calendar.getInstance();
                        Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                        end.setTime(endDate);
                        end.add(Calendar.DAY_OF_YEAR, 1);
                        end.set(Calendar.HOUR_OF_DAY, 10);
                        result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                        /*
                         * Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                         * result.addProperty("second", CommonUtil.date2String(endDate, "yyyy/MM/dd")+" 10:00:00");
                         * ////// 由前端计算剩余时间
                         */
                    }
                }
                
                result.addProperty("wexinsharename", swe.getName());
                result.addProperty("wexinsharedesc", swe.getDesc());
            }
            else
            // 没有查询到对应的特卖信息
            {
                result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                result.addProperty("second", "");
            }
        }
        // 商城
        else if (ace.getType() == 2)
        {
            urlType = "mitem";
        }
        result.addProperty("type", ace.getType());
        result.addProperty("urlType", urlType);
        result.addProperty("bannerImage", (ace == null ? "" : ace.getImage()));
        result.addProperty("name", (ace == null ? "" : ace.getName()));
        result.addProperty("desc", (ace == null ? "" : ace.getDesc()));
        result.addProperty("detail", (ace == null ? "" : ace.getDetail()));
        result.addProperty("wxShareTitle", (ace == null ? "" : ace.getWxShareTitle()));
        result.addProperty("gegesay", (ace == null ? "" : ace.getGegesay()));
        
        List<Integer> productIdList = acdi.findProductIdsById(commonActivitiesId);
        result.add("productIdList", (productIdList == null || productIdList.isEmpty() ? null : parser.parse(JSONUtils.toJson(productIdList, false))));
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String commonActivitiesDetail(String requestParams, int type)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        String imgUrl = ""; // WX分享图标"
        JsonObject param = (JsonObject)parser.parse(requestParams);
        List<Integer> productIds = new ArrayList<Integer>();
        JsonArray productIdJson = (JsonArray)param.get("productIdList");
        int cid = Integer.parseInt( param.get("cid").toString() );
        Iterator<JsonElement> it = productIdJson.iterator();
        while (it.hasNext())
        {
            productIds.add(it.next().getAsInt());
        }
        DecimalFormat df = new DecimalFormat("0.00");
        List<ActivitiesProductView> productIdList = new ArrayList<ActivitiesProductView>();

        List<ProductCommonEntity> pdces = pcommondi.findProductCommonInfoById(productIds);
        List<ProductCommonEntity> pces = new ArrayList<ProductCommonEntity>();
        if (productIds != null && !productIds.isEmpty() && pdces != null && !pdces.isEmpty())
        {
            for (Integer pid : productIds)
            {
                int size = pdces.size();
                for (int i = 0; i < size; i++)
                {
                    ProductCommonEntity pceTmp = pdces.get(i);
                    if (pid.intValue() == pceTmp.getProductId())
                    {
                        pces.add(pdces.remove(i));
                        break;
                    }
                }
            }
            imgUrl = (pces.isEmpty() == true ? "" : pces.get(0).getSmallImage());
        }
        
        for (ProductCommonEntity pce : pces)
        {
            ProductEntity product = pdi.findProductInfoById(pce.getProductId());
            ActivitiesProductView apv = new ActivitiesProductView();
            apv.setProductId(pce.getProductId() + "");
            /* apv.setImage(pce.getMediumImage()); */
            apv.setImage(product.getImage1());
            apv.setName(pce.getName());
            apv.setHighPrice(df.format(pce.getMarketPrice()));
            apv.setLowPrice(df.format(pce.getSalesPrice()));
            int sellCount = this.pcommondi.findProductSellCountById(pce.getProductId()); // this.pcdi.findSellCountByProductId(pce.getProductId())
                                                                                         // ;
            apv.setSellCount(sellCount + ""); // pce.getSellCount() + ""); // 可能不准查product_count
            
            ProductEntity pe = pdi.findProductPartById(pce.getProductId());
            /*
             * ProductBindEntity pbe = pbdi.findProductBindInfoById(pce.getProductId()); if (pbe != null) //
             * 查询到商品绑定的特卖信息 { SaleWindowEntity swe = swdi.findSaleInfoById(pbe.getSaleWindowId()); int curr =
             * CommonUtil.getNowSaleDate(); if (curr < swe.getStartTime()) // 未开始 {
             * apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()); } else if (curr > swe.getEndTime())
             * // 已结束 { apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()); } else { ProductCountEntity
             * pcounte = pcdi.findProductCountInfoById(pce.getProductId()); int stock = pcounte.getStock(); if (stock ==
             * 0) { apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()); } else if (pcounte.getLock() >=
             * stock) { apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue()); } else {
             * apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()); } } }
             */
            
            if (type != 2)
            {
                if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 查询到商品绑定的特卖信息
                {
                    Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                    Date currTime = new Date();
                    
                    if (currTime.before(startTime)) // 未开始
                    {
                        apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    }
                    else if (currTime.after(endTime)) // 已结束
                    {
                        apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    }
                    else
                    {
                        ProductCountEntity pcounte = pcdi.findCountInfoById(pce.getProductId());
                        int stock = pcounte.getStock();
                        if (stock == 0)
                        {
                            apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        }
                        else if (pcounte.getLock() >= stock)
                        {
                            apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                        }
                        else
                        {
                            apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        }
                    }
                }
                else
                // 没有查询到商品绑定的特卖信息
                {
                    apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                }
            }
            else
            {
                apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
            }
            
            productIdList.add(apv);
        }
 
        int mainProductId = Collections.min(productIds);
        ProductEntity mainProduct = pdi.findProductInfoById(mainProductId);
        List<String> imageList = new ArrayList<String>();
        if(mainProduct.getImage1()!=null && !mainProduct.getImage1().isEmpty()){
        	imageList.add(mainProduct.getImage1());
        }
        if(mainProduct.getImage2()!=null && !mainProduct.getImage2().isEmpty()){
        	imageList.add(mainProduct.getImage2());
        }
        if(mainProduct.getImage3()!=null && !mainProduct.getImage3().isEmpty()){
        	imageList.add(mainProduct.getImage3());
        }
        if(mainProduct.getImage4()!=null && !mainProduct.getImage4().isEmpty()){
        	imageList.add(mainProduct.getImage4());
        }
        if(mainProduct.getImage5()!=null && !mainProduct.getImage5().isEmpty()){
        	imageList.add(mainProduct.getImage5());
        }

        
        List<ProductMobileDetailEntity> detail = pmddi.findMobileDetailInfoByProductId(mainProductId);
        StringBuffer detailHTML = new StringBuffer();
        for(ProductMobileDetailEntity item:detail){
        	detailHTML.append("<img src=\""+item.getContent()+"\" />");
        }
        List<ProductDetailView> productDetailList = findProductMobileDetail(mainProductId);
        if (productDetailList != null && productDetailList.size() > 0) {
            result.add("detail", parser.parse(JSONUtils.toJson(productDetailList, false)));
        }
        ActivitiesCommonEntity ace = acdi.findActivitiesInfoById(cid);
   
        result.addProperty("gegesay", (ace == null ? "" : ace.getGegesay()));
        
        result.add("productDetailList", parser.parse(JSONUtils.toJson(productIdList, false)));
        result.add("imageList", parser.parse(JSONUtils.toJson(imageList, false)));
        result.addProperty("imgUrl", imgUrl);
        result.addProperty("lowPrice", mainProduct.getSalesPrice());
        result.addProperty("detail", detailHTML.toString());
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String customActivitiesDetail(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int customActivitiesId = param.get("customActivitiesId").getAsInt();
        ActivitiesCustomEntity ace = activitiescdi.findActivitiesInfoById(customActivitiesId);
        return (ace != null ? ace.getShareUrl() : "");
        /*
         * result.addProperty("url", ace.getUrl());
         * 
         * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); return
         * result.toString();
         */
        
    }
    
    @Override
    public List<Map<String, Object>> findStartEndTimeById(List<Integer> saleWindowId)
        throws Exception
    {
        return this.swdi.findStartEndTimeById(saleWindowId);
    }
    
    @Override
    public String getBrandEndSecond(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int commonActivitiesId = param.get("commonActivitiesId").getAsInt();
        
        // 设置 productStatus与second
        SaleWindowEntity swe = swdi.findSaleInfoByDisplayId(commonActivitiesId, (byte)CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue());
        if (swe != null) // 查询到对应的特卖信息
        {
            if (swe.getSaleTimeType() == Byte.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_10.getValue()))
            {
                int curr = CommonUtil.getNowSaleDate();
                if (curr < swe.getStartTime()) // 未开始
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    Calendar start = Calendar.getInstance();
                    Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
                    start.setTime(startDate);
                    start.set(Calendar.HOUR_OF_DAY, 10);
                    result.addProperty("second", ((start.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                }
                else if (curr > swe.getEndTime()) // 已结束
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    result.addProperty("second", "");
                }
                else
                {
                    ProductCountEntity pce = pcdi.findProductCountSumByCommonId(commonActivitiesId);
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        result.addProperty("second", "");
                    }
                    else if (pce.getLock() >= stock)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                        Calendar end = Calendar.getInstance();
                        Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                        end.setTime(endDate);
                        end.add(Calendar.DAY_OF_YEAR, 1);
                        end.set(Calendar.HOUR_OF_DAY, 10);
                        result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                        
                    }
                    else
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        Calendar end = Calendar.getInstance();
                        Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                        end.setTime(endDate);
                        end.add(Calendar.DAY_OF_YEAR, 1);
                        end.set(Calendar.HOUR_OF_DAY, 10);
                        result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                        
                    }
                }
            }
            else
            {
                int curr = CommonUtil.getNowSaleDateNight();
                if (curr < swe.getStartTime()) // 未开始
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    Calendar start = Calendar.getInstance();
                    Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
                    start.setTime(startDate);
                    start.set(Calendar.HOUR_OF_DAY, 20);
                    result.addProperty("second", ((start.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                }
                else if (curr > swe.getEndTime()) // 已结束
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    result.addProperty("second", "");
                }
                else
                {
                    ProductCountEntity pce = pcdi.findProductCountSumByCommonId(commonActivitiesId);
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        result.addProperty("second", "");
                    }
                    else if (pce.getLock() >= stock)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                        Calendar end = Calendar.getInstance();
                        Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                        end.setTime(endDate);
                        end.add(Calendar.DAY_OF_YEAR, 1);
                        end.set(Calendar.HOUR_OF_DAY, 20);
                        result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                        
                    }
                    else
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        Calendar end = Calendar.getInstance();
                        Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                        end.setTime(endDate);
                        end.add(Calendar.DAY_OF_YEAR, 1);
                        end.set(Calendar.HOUR_OF_DAY, 20);
                        result.addProperty("second", ((end.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) + "");
                        
                    }
                }
            }
            
        }
        else
        // 没有查询到对应的特卖信息
        {
            result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
            result.addProperty("second", "");
        }
        return result.toString();
    }
    
    @Override
    public String getProductDynamicInfo(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int productId = param.get("productId").getAsInt();
        
        ProductEntity pe = pdi.findProductInfoById(productId);
        if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 查询到商品绑定的特卖信息
        {
            if (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                Date currTime = new Date();
                
                if (currTime.before(startTime)) // 未开始
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    result.addProperty("second", ((startTime.getTime() - currTime.getTime()) / 1000) + "");
                }
                else if (currTime.after(endTime)) // 已结束
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    result.addProperty("second", "");
                }
                else
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(productId);
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        result.addProperty("second", "");
                    }
                    else if (pce.getLock() >= stock)
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                        result.addProperty("second", ((endTime.getTime() - currTime.getTime()) / 1000) + "");
                    }
                    else
                    {
                        result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        result.addProperty("second", ((endTime.getTime() - currTime.getTime()) / 1000) + "");
                    }
                }
            }
            else if (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                ProductCountEntity pce = pcdi.findCountInfoById(productId);
                int stock = pce.getStock();
                result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                if (stock == 0)
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                }
                else if (pce.getLock() >= stock)
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                }
                result.addProperty("second", "");
            }
            
        }
        else
        // 已下架
        {
            result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
            result.addProperty("second", "");
        }
        
        ProductCountEntity pce = pcdi.findProductCountInfoById(productId);
        result.addProperty("sellCount", (pce == null ? "0" : pce.getSell() + ""));
        if (result.get("productStatus").getAsString().equals(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()))
        {
            result.addProperty("stockCount", "0");
        }
        else
        {
            result.addProperty("stockCount", (pce == null ? "0" : pce.getStock() + ""));
        }
        
        return result.toString();
    }
    
    @Override
    public boolean checkProductCanBeAccessed(Map<String, Object> para)
        throws Exception
    {
        int id = Integer.parseInt(para.get("id") == null ? "0" : para.get("id") + "");
        byte type = Byte.parseByte(para.get("type") == null ? "0" : para.get("type") + "");
        ProductEntity pe = pdi.findProductInfoById(id);
        if (pe == null || type != pe.getType())
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Map<String, Object> homeListNew(Map<String, Object> para)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String type = para.get("type") == null ? "" : para.get("type") + "";
        int saleDate = CommonUtil.getNowSaleDate();
        if (type.contains(CommonEnum.RESOURCE_TYPE.BANNER.getValue()))
        {
            List<Integer> bannerIds = null;
            Object cacheResult = cache.getCache(CacheConstant.RES_HOMELIST_BANNER_KEY + saleDate);
            if (cacheResult != null)
            {
                bannerIds = (List<Integer>)cacheResult;
            }
            else
            {
                bannerIds = bwdi.findCurrDisplayBannerId();
                cache.addCache(CacheConstant.RES_HOMELIST_BANNER_KEY + saleDate, bannerIds, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            
            List<BannerView> bvs = new ArrayList<BannerView>();
            for (Integer bannerId : bannerIds)
            {
                cacheResult = cache.getCache(CacheConstant.RES_HOMEDETAIL_BANNER_KEY + bannerId);
                BannerView bv = new BannerView();
                if (cacheResult != null)
                {
                    bv = (BannerView)cacheResult;
                }
                else
                {
                    BannerWindowEntity bwe = bwdi.findDisplayBannerInfoById(bannerId);
                    bv.setId(bwe.getDisplayId() + "");
                    bv.setImage(bwe.getImage());
                    bv.setType(bwe.getType() + "");
                    bv.setUrl(getSaleWindowUrl(bwe.getType(), bwe.getDisplayId()));
                    cache.addCache(CacheConstant.RES_HOMEDETAIL_BANNER_KEY + bannerId, bv, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                bvs.add(bv);
            }
            result.put("bannerList", bvs);
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.NOW.getValue()))
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_HOMELIST_NOW_KEY + saleDate);
            if (cacheResult != null)
            {
                result.put("nowList", (List<Integer>)cacheResult);
            }
            else
            {
                List<Integer> nowList = swdi.findCurrDisplayNowId();
                cache.addCache(CacheConstant.RES_HOMELIST_NOW_KEY + saleDate, nowList, CacheConstant.CACHE_MINUTE_1 / 2);
                result.put("nowList", nowList);
            }
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.LATER.getValue()))
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_HOMELIST_LATER_KEY + saleDate);
            if (cacheResult != null)
            {
                result.put("laterList", (List<Integer>)cacheResult);
            }
            else
            {
                List<Integer> laterList = swdi.findCurrDisplayLaterId();
                cache.addCache(CacheConstant.RES_HOMELIST_LATER_KEY + saleDate, laterList, CacheConstant.CACHE_MINUTE_1 / 2);
                result.put("laterList", laterList);
                System.out.println("laterList" + laterList);
            }
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.THEME.getValue()))
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_HOMELIST_THEME_KEY + saleDate);
            List<Map<String, String>> themeList = new ArrayList<Map<String, String>>();
            if (cacheResult != null)
            {
                themeList = (List<Map<String, String>>)cacheResult;
            }
            else
            {
                List<MallWindowEntity> mwes = mwdi.findDisplayMallWindow();
                for (MallWindowEntity mwe : mwes)
                {
                    Map<String, String> themeMap = new HashMap<String, String>();
                    themeMap.put("themeId", mwe.getId() + "");
                    themeMap.put("image", mwe.getImage());
                    themeMap.put("title", mwe.getName());
                    themeMap.put("url", "url");
                    themeList.add(themeMap);
                }
                cache.addCache(CacheConstant.RES_HOMELIST_THEME_KEY + saleDate, themeList, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            result.put("themeList", themeList);
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.CUSTOM.getValue()))
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_HOMELIST_ACTIVITY_KEY + saleDate);
            List<Map<String, Object>> activityList = new ArrayList<Map<String, Object>>();
            if (cacheResult != null)
            {
                activityList = (List<Map<String, Object>>)cacheResult;
            }
            else
            {
                List<Map<String, Object>> customRegions = cldi.findDisplayCustomRegion();
                for (Map<String, Object> customRegion : customRegions)
                {
                    List<CustomLayoutEntity> cles = cldi.findCustomLayoutsByLayoutIds(cldi.findLayoutIdByRegionId(((Long)customRegion.get("id")).intValue()));
                    if (cles.isEmpty())
                    {
                        continue;
                    }
                    Map<String, Object> activity = new HashMap<String, Object>();
                    activity.put("title", customRegion.get("title").toString());
                    
                    List<Map<String, Object>> contents = new ArrayList<Map<String, Object>>();
                    for (CustomLayoutEntity cle : cles)
                    {
                        if (cle.getDisplayType() == 1)// 全屏宽
                        {
                            Map<String, Object> content = new HashMap<String, Object>();
                            // content.put("type", cle.getOneType() + "");
                            content.put("displayType", cle.getDisplayType() + "");
                            content.put("image", cle.getOneImage());
                            // content.put("id", cle.getOneDisplayId() + "");
                            content.put("width", cle.getOneWidth() + "");
                            content.put("height", cle.getOneHeight() + "");
                            String url = getCustomLayoutUrl(cle.getOneType(), cle.getOneDisplayId());
                            content.put("url", url);
                            contents.add(content);
                        }
                        else
                        // 半屏宽
                        {
                            Map<String, Object> content = new HashMap<String, Object>();
                            content.put("displayType", cle.getDisplayType() + "");
                            Map<String, String> left = new HashMap<String, String>();
                            // content1.put("type", cle.getOneType() + "");
                            left.put("image", cle.getOneImage());
                            // content1.put("id", cle.getOneDisplayId() + "");
                            left.put("width", cle.getOneWidth() + "");
                            left.put("height", cle.getOneHeight() + "");
                            String url1 = getCustomLayoutUrl(cle.getOneType(), cle.getOneDisplayId());
                            left.put("url", url1);
                            content.put("left", left);
                            Map<String, Object> right = new HashMap<String, Object>();
                            // right.put("displayType", cle.getDisplayType() + "");
                            // content2.put("type", cle.getTwoType() + "");
                            right.put("image", cle.getTwoImage());
                            // content2.put("id", cle.getTwoDisplayId() + "");
                            right.put("width", cle.getTwoWidth() + "");
                            right.put("height", cle.getTwoHeight() + "");
                            String url2 = getCustomLayoutUrl(cle.getTwoType(), cle.getTwoDisplayId());
                            right.put("url", url2);
                            content.put("right", right);
                            contents.add(content);
                        }
                    }
                    activity.put("content", contents);
                    activityList.add(activity);
                }
                cache.addCache(CacheConstant.RES_HOMELIST_ACTIVITY_KEY + saleDate, activityList, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            result.put("activityList", activityList);
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.HOT.getValue()))
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_HOMELIST_HOT_KEY + saleDate);
            List<Map<String, String>> hotList = new ArrayList<Map<String, String>>();
            if (cacheResult != null)
            {
                hotList = (List<Map<String, String>>)cacheResult;
            }
            else
            {
                List<Map<String, Object>> hotMaps = pdi.findDisplayHotProduct();
                for (Map<String, Object> hot : hotMaps)
                {
                    Object peCache = cache.getCache(CacheConstant.RES_PRODUCTBASE_PE_KEY + (Long)hot.get("product_id"));
                    ProductEntity pe = null;
                    if (peCache != null)
                    {
                        pe = (ProductEntity)peCache;
                    }
                    else
                    {
                        pe = pdi.findProductInfoById(((Long)hot.get("product_id")).intValue());
                        cache.addCache(CacheConstant.RES_PRODUCTBASE_PE_KEY + (Long)hot.get("product_id"), pe, CacheConstant.CACHE_MINUTE_1 / 2);
                    }
                    
                    Map<String, String> hotMap = new HashMap<String, String>();
                    hotMap.put("id", pe.getId() + "");
                    hotMap.put("image", pe.getImage1().replace("!v1product", "!todayhot"));
                    hotMap.put("title", pe.getShortName());
                    hotMap.put("price", pe.getSalesPrice() + "");
                    int productType = Integer.parseInt(hot.get("type") == null ? "1" : hot.get("type") + "");
                    String url = "";
                    if (productType == 1)
                    {
                        url = YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + pe.getId() + ".htm";
                    }
                    else
                    {
                        url = YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + pe.getId() + ".htm";
                    }
                    hotMap.put("url", url);
                    hotList.add(hotMap);
                }
                cache.addCache(CacheConstant.RES_HOMELIST_HOT_KEY + saleDate, hotList, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            result.put("hotList", hotList);
        }
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }
    
    @Override
    public Map<String, Object> homeDetailNew(Map<String, Object> para)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String type = para.get("type") == null ? "" : para.get("type") + "";
        String nowList = para.get("nowList") == null ? "" : para.get("nowList") + "";
        String laterList = para.get("laterList") == null ? "" : para.get("laterList") + "";
        AccountView av = para.get("av") == null ? null : (AccountView)para.get("av");
        List<Integer> collectSaleIds = new ArrayList<Integer>();
        if (av != null && av.getId() != 0)
        {
            collectSaleIds = adi.findCollectSaleIdsByAid(av.getId());
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.NOW.getValue()))
        {
            List<NowView> nvs = new ArrayList<NowView>();
            String[] nowArr = nowList.split(",");
            List<String> nowIds = Arrays.asList(nowArr);
            Calendar curr = Calendar.getInstance();
            for (String it : nowIds)
            {
                Integer nowId = Integer.parseInt(it);
                Object sweCache = cache.getCache(CacheConstant.RES_HOMEDETAIL_NOW_SWE_KEY + nowId);
                Object nvCache = cache.getCache(CacheConstant.RES_HOMEDETAIL_NOW_NV_KEY + nowId);
                
                SaleWindowEntity swe = null;
                if (sweCache != null)
                {
                    swe = (SaleWindowEntity)sweCache;
                }
                else
                {
                    swe = swdi.findDisplayNowInfoById(nowId);
                    cache.addCache(CacheConstant.RES_HOMEDETAIL_NOW_SWE_KEY + nowId, swe, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                
                NowView nv = null;
                if (nvCache != null)
                {
                    nv = (NowView)nvCache;
                }
                else
                {
                    nv = new NowView();
                    nv.setId(swe.getId() + "");
                    nv.setImage(swe.getImage());
                    nv.setUrl(getSaleWindowUrl(swe.getType(), swe.getDisplayId()));
                    nv.setType(swe.getType() + "");
                    nv.setLeftDesc(swe.getName());
                    nv.setRightDesc(swe.getDesc());
                    nv.setFlagImage("http://yangege.b0.upaiyun.com/178dcb8d4f6ec.png!nationalflag");
                    if (swe.getSaleFlagId() != 0)
                    {
                        nv.setFlagImage(sfdi.findImageById(swe.getSaleFlagId()));
                    }
                    cache.addCache(CacheConstant.RES_HOMEDETAIL_NOW_NV_KEY + nowId, nv, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                
                Calendar end = Calendar.getInstance();
                Date endDate = CommonUtil.string2Date(swe.getEndTime() + "", "yyyyMMdd");
                end.setTime(endDate);
                end.add(Calendar.DAY_OF_YEAR, 1);
                end.set(Calendar.HOUR_OF_DAY, 10);
                nv.setEndSecond(((end.getTimeInMillis() - curr.getTimeInMillis()) / 1000) + "");
                
                if (swe.getType() == CommonEnum.SALE_TYPE.PRODUCT.getValue())
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(swe.getDisplayId());
                    ProductEntity pe = pdi.findProductPartById(swe.getDisplayId());
                    
                    if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()))
                    {
                        Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                        Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                        Date currTime = new Date();
                        if (currTime.before(startTime) || currTime.after(endTime)) // 未开始或已结束
                        {
                            nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                        }
                        else
                        {
                            int stock = pce.getStock();
                            if (stock == 0)
                            {
                                nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                            }
                            else if (pce.getLock() >= stock)
                            {
                                nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                            }
                            else
                            {
                                nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                            }
                        }
                    }
                    else
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    }
                }
                else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue())
                {
                    ActivitiesCommonEntity ace = acdi.findActivitiesInfoById(swe.getDisplayId());
                    
                    List<Integer> availableProductIds = new ArrayList<Integer>();
                    Object nvStatusCache = cache.getCache(CacheConstant.RES_HOMEDETAIL_NOW_NVSTATUS_KEY + nowId);
                    if (nvStatusCache != null)
                    {
                        availableProductIds = (List<Integer>)nvStatusCache;
                    }
                    else
                    {
                        List<Integer> relationProductIds = acdi.findProductIdsById(swe.getDisplayId());
                        for (Integer relationProductId : relationProductIds)
                        {
                            ProductEntity pe = pdi.findProductPartById(relationProductId);
                            Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                            Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                            Date currTime = new Date();
                            if (!(pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()) || currTime.before(startTime) || currTime.after(endTime)))
                            {
                                availableProductIds.add(relationProductId);
                            }
                        }
                        cache.addCache(CacheConstant.RES_HOMEDETAIL_NOW_NVSTATUS_KEY + nowId, availableProductIds, CacheConstant.CACHE_MINUTE_1 / 2);
                    }
                    
                    ProductCountEntity pce = pcdi.findProductCountSumByProductIds(availableProductIds);
                    int stock = pce.getStock();
                    if (stock == 0)
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    }
                    else if (pce.getLock() >= stock)
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                    }
                    else
                    {
                        nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    }
                }
                else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_CUSTOM.getValue())
                {
                    nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    // ProductCountEntity pce = pcdi.findProductCountSumByCustomId(swe.getDisplayId());
                    // int stock = pce.getStock();
                    // if (stock == 0)
                    // {
                    // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue());
                    // }
                    // else if (pce.getLock() >= stock)
                    // {
                    // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue());
                    // }
                    // else
                    // {
                    // nv.setStatus(CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue());
                    // }
                }
                
                if (av != null)
                {
                    if (av.getId() != 0 && collectSaleIds.contains(nowId))
                    {
                        nv.setIsCollect(CommonEnum.COMMON_IS.YES.getValue());
                    }
                    else
                    {
                        nv.setIsCollect(CommonEnum.COMMON_IS.NO.getValue());
                    }
                }
                else
                {
                    nv.setIsCollect(CommonEnum.COMMON_IS.NO.getValue());
                }
                nvs.add(nv);
            }
            result.put("nowDetailList", nvs);
        }
        if (type.contains(CommonEnum.RESOURCE_TYPE.LATER.getValue()))
        {
            List<LaterView> lvs = new ArrayList<LaterView>();
            String[] laterArr = laterList.split(",");
            List<String> laterIds = Arrays.asList(laterArr);
            Calendar curr = Calendar.getInstance();
            for (String it : laterIds)
            {
                int laterId = Integer.parseInt(it);
                Object sweCache = cache.getCache(CacheConstant.RES_HOMEDETAIL_LATER_SWE_KEY + laterId);
                Object lvCache = cache.getCache(CacheConstant.RES_HOMEDETAIL_LATER_LV_KEY + laterId);
                
                SaleWindowEntity swe = null;
                if (sweCache != null)
                {
                    swe = (SaleWindowEntity)sweCache;
                }
                else
                {
                    swe = swdi.findDisplayLaterInfoById(laterId);
                    cache.addCache(CacheConstant.RES_HOMEDETAIL_LATER_SWE_KEY + laterId, swe, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                
                LaterView lv = null;
                if (lvCache != null)
                {
                    lv = (LaterView)lvCache;
                }
                else
                {
                    lv = new LaterView();
                    lv.setId(swe.getId() + "");
                    lv.setImage(swe.getImage());
                    lv.setUrl(getSaleWindowUrl(swe.getType(), swe.getDisplayId()));
                    lv.setLeftDesc(swe.getName());
                    lv.setRightDesc(swe.getDesc());
                    lv.setType(swe.getType() + "");
                    
                    lv.setFlagImage("http://yangege.b0.upaiyun.com/178dcb8d4f6ec.png!nationalflag");
                    if (swe.getSaleFlagId() != 0)
                    {
                        lv.setFlagImage(sfdi.findImageById(swe.getSaleFlagId()));
                    }
                    cache.addCache(CacheConstant.RES_HOMEDETAIL_LATER_LV_KEY + laterId, lv, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                
                if (av != null)
                {
                    if (av.getId() != 0 && collectSaleIds.contains(laterId))
                    {
                        lv.setIsCollect(CommonEnum.COMMON_IS.YES.getValue());
                    }
                    else
                    {
                        lv.setIsCollect(CommonEnum.COMMON_IS.NO.getValue());
                    }
                }
                else
                {
                    lv.setIsCollect(CommonEnum.COMMON_IS.NO.getValue());
                }
                
                Calendar start = Calendar.getInstance();
                Date startDate = CommonUtil.string2Date(swe.getStartTime() + "", "yyyyMMdd");
                start.setTime(startDate);
                start.set(Calendar.HOUR_OF_DAY, 10);
                lv.setStartSecond(((start.getTimeInMillis() - curr.getTimeInMillis()) / 1000) + "");
                lvs.add(lv);
            }
            result.put("laterDetailList", lvs);
        }
        return result;
    }
    
    public String getCustomLayoutUrl(int type, int id)
    {
        String url = "";
        if (type == CustomLayoutTypeEnum.SALE_PRODUCT.ordinal())
        {
            url = YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + id + ".htm";
        }
        else if (type == CustomLayoutTypeEnum.MALL_PRODUCT.ordinal())
        {
            url = YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + id + ".htm";
        }
        else if (type == CustomLayoutTypeEnum.ACTIVITIES_COMMON.ordinal())
        {
            url = YggWebProperties.getInstance().getProperties("domain_name") + "/sale-" + id + ".htm";
        }
        else if (type == CustomLayoutTypeEnum.ACTIVITIES_CUSTOM.ordinal())
        {
            ActivitiesCustomEntity ace = activitiescdi.findActivitiesInfoById(id);
            url = ace.getShareUrl();
        }
        return url;
    }
    
    public String getSaleWindowUrl(int type, int id)
    {
        String url = "";
        if (type == CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue())
        {
            
            url = YggWebProperties.getInstance().getProperties("domain_name") + "/sale-" + id + ".htm";
        }
        else if (type == CommonEnum.SALE_TYPE.PRODUCT.getValue())
        {
            url = YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + id + ".htm";
        }
        else if (type == CommonEnum.SALE_TYPE.ACTIVITIES_CUSTOM.getValue())
        {
            ActivitiesCustomEntity ace = activitiescdi.findActivitiesInfoById(id);
            url = ace.getShareUrl();
        }
        return url;
    }
    
    @Override
    public Map<String, Object> themeMenuList(Map<String, Object> para)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int themeId = Integer.parseInt(para.get("themeId") == null ? "0" : para.get("themeId") + "");
        Object cacheResult = cache.getCache(CacheConstant.RES_THEMEMENULIST_MWE_KEY + themeId);
        MallWindowEntity mwe = null;
        if (cacheResult != null)
        {
            mwe = (MallWindowEntity)cacheResult;
        }
        else
        {
            mwe = mwdi.findMallWindowById(themeId);
            cache.addCache(CacheConstant.RES_THEMEMENULIST_MWE_KEY + themeId, mwe, CacheConstant.CACHE_MINUTE_1 / 2);
        }
        
        cacheResult = cache.getCache("test:" + CacheConstant.RES_THEMEMENULIST_MALLPAGEMAP_KEY + themeId);
        Map<String, Object> mallPageMap = null;
        if (cacheResult != null)
        {
            mallPageMap = (Map<String, Object>)cacheResult;
        }
        else
        {
            mallPageMap = mwdi.findMallPageById(mwe.getMallPageId());
            cache.addCache(CacheConstant.RES_THEMEMENULIST_MALLPAGEMAP_KEY + themeId, mallPageMap, CacheConstant.CACHE_MINUTE_1 / 2);
        }
        
        cacheResult = cache.getCache(CacheConstant.RES_THEMEMENULIST_MALLPAGEFLOORLIST_KEY + themeId);
        List<Map<String, Object>> mallPageFloorList = null;
        if (cacheResult != null)
        {
            mallPageFloorList = (List<Map<String, Object>>)cacheResult;
        }
        else
        {
            mallPageFloorList = mwdi.findAllMallPageFloorById(mwe.getMallPageId());
            Map<String, Object> allMenu = new HashMap<String, Object>();
            allMenu.put("id", 0l);
            allMenu.put("name", "全部");
            mallPageFloorList.add(0, allMenu);
            cache.addCache(CacheConstant.RES_THEMEMENULIST_MALLPAGEFLOORLIST_KEY + themeId, mallPageFloorList, CacheConstant.CACHE_MINUTE_1 / 2);
        }
        
        result.put("name", mallPageMap.get("name") + "");
        result.put("menuList", mallPageFloorList);
        
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }
    
    @Override
    public Map<String, Object> themeMenuProductList(Map<String, Object> para)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int themeId = Integer.parseInt(para.get("themeId") == null ? "0" : para.get("themeId") + "");
        int menuId = Integer.parseInt(para.get("menuId") == null ? "0" : para.get("menuId") + "");
        Set<Integer> mProductIdSet = new LinkedHashSet<Integer>();
        if (menuId == 0)
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_THEMEMENULIST_MWE_KEY + themeId);
            MallWindowEntity mwe = null;
            if (cacheResult != null)
            {
                mwe = (MallWindowEntity)cacheResult;
            }
            else
            {
                mwe = mwdi.findMallWindowById(themeId);
                cache.addCache(CacheConstant.RES_THEMEMENULIST_MWE_KEY + themeId, mwe, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            
            cacheResult = cache.getCache(CacheConstant.RES_THEMEMENULIST_MALLPAGEFLOORLIST_KEY + themeId);
            List<Map<String, Object>> mallPageFloorList = null;
            if (cacheResult != null)
            {
                mallPageFloorList = (List<Map<String, Object>>)cacheResult;
            }
            else
            {
                mallPageFloorList = mwdi.findAllMallPageFloorById(mwe.getMallPageId());
                cache.addCache(CacheConstant.RES_THEMEMENULIST_MALLPAGEFLOORLIST_KEY + themeId, mallPageFloorList, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            
            for (Map<String, Object> mallPageFloorMap : mallPageFloorList)
            {
                int id = ((Long)mallPageFloorMap.get("id")).intValue();
                cacheResult = cache.getCache(CacheConstant.RES_THEMEMENUPRODUCTLIST_PRODUCTIDS_KEY + id);
                List<Integer> productsList = null;
                if (cacheResult != null)
                {
                    productsList = (List<Integer>)cacheResult;
                }
                else
                {
                    productsList = mwdi.findProductIdsByMallPageFloorId(id);
                    cache.addCache(CacheConstant.RES_THEMEMENUPRODUCTLIST_PRODUCTIDS_KEY + id, productsList, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                mProductIdSet.addAll(productsList);
            }
        }
        else
        {
            Object cacheResult = cache.getCache(CacheConstant.RES_THEMEMENUPRODUCTLIST_PRODUCTIDS_KEY + menuId);
            List<Integer> productsList = null;
            if (cacheResult != null)
            {
                productsList = (List<Integer>)cacheResult;
            }
            else
            {
                productsList = mwdi.findProductIdsByMallPageFloorId(menuId);
                cache.addCache(CacheConstant.RES_THEMEMENUPRODUCTLIST_PRODUCTIDS_KEY + menuId, productsList, CacheConstant.CACHE_MINUTE_1 / 2);
            }
            mProductIdSet.addAll(productsList);
        }
        
        result.put("mProductId", mProductIdSet);
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }
    
    @Override
    public Map<String, Object> themeMenuProductDetail(Map<String, Object> para)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        String mProductId = para.get("mProductId") == null ? "" : para.get("mProductId") + "";
        List<String> productStringIds = Arrays.asList(mProductId.split(","));
        List<Integer> productIds = new ArrayList<Integer>();
        for (String it : productStringIds)
        {
            productIds.add(Integer.parseInt(it));
        }
        List<ActivitiesProductView> productIdList = new ArrayList<ActivitiesProductView>();
        List<ProductCommonEntity> pces = pcommondi.findProductCommonInfoById(productIds);
        for (ProductCommonEntity pce : pces)
        {
            ActivitiesProductView apv = new ActivitiesProductView();
            apv.setProductId(pce.getProductId() + "");
            apv.setImage(pce.getMediumImage());
            apv.setName(pce.getName());
            apv.setHighPrice(pce.getMarketPrice() + "");
            apv.setLowPrice(pce.getSalesPrice() + "");
            apv.setSellCount(pce.getSellCount() + "");
            apv.setSellingPoint(pce.getSellingPoint());
            apv.setProductType(CommonEnum.PRODUCT_TYPE.MALL.getValue());
            
            ProductEntity pe = pdi.findProductPartById(pce.getProductId());
            if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 查询到商品绑定的特卖信息
            {
                ProductCountEntity pcounte = pcdi.findCountInfoById(pce.getProductId());
                int stock = pcounte.getStock();
                if (stock == 0)
                {
                    apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                }
                else if (pcounte.getLock() >= stock)
                {
                    apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                }
                else
                {
                    apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                }
            }
            else
            // 已下架
            {
                apv.setType(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
            }
            productIdList.add(apv);
        }
        
        result.put("mProductDetailList", productIdList);
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }
    
}
