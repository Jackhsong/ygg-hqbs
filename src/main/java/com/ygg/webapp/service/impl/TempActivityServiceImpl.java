package com.ygg.webapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ygg.common.utils.CommonUtil;
import com.ygg.webapp.code.AccessTypeEnum;
import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.TempActivityDao;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.TempActivityService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.YggWebProperties;

@Service("tempActivityService")
public class TempActivityServiceImpl implements TempActivityService
{
    private static Logger logger = Logger.getLogger(TempActivityServiceImpl.class);
    
    @Resource
    private TempActivityDao tempActivityDao;
    
    @Resource
    private ActivitiesCommonDao activitiesCommonDao;
    
    @Resource
    private ProductDao productDao;
    
    @Resource(name = "productCountDao")
    private ProductCountDao productCountDao;
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findAllTempActivity(int type)
        throws ServiceException
    {
        Map<String, Object> activityMap = new HashMap<String, Object>();
        List<Map<String, Object>> activityList = tempActivityDao.findAllTempActivityDetails();
        for (Map<String, Object> it : activityList)
        {
            String desc = it.get("name") + "";
            String productId = it.get("productId") + "";
            String productType = it.get("productType") + "";
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", it.get("image"));
            map.put("name", it.get("productName"));
            map.put("highPrice", it.get("highPrice"));
            map.put("lowPrice", it.get("lowPrice"));
            StringBuilder sb = new StringBuilder();
            if (type == AccessTypeEnum.TYPE_OF_WAP.getValue())
            {
                if (CommonEnum.PRODUCT_TYPE.SALE.getValue().equals(productType))
                {
                    sb.append("http://m.gegejia.com/item-").append(productId).append(".htm");
                }
                else if (CommonEnum.PRODUCT_TYPE.MALL.getValue().equals(productType))
                {
                    sb.append("http://m.gegejia.com/mitem-").append(productId).append(".htm");
                }
            }
            else if (type == AccessTypeEnum.TYPE_OF_APP.getValue())
            {
                if (CommonEnum.PRODUCT_TYPE.SALE.getValue().equals(productType))
                {
                    sb.append("ggj://redirect/resource/saleProduct/").append(productId);
                }
                else if (CommonEnum.PRODUCT_TYPE.MALL.getValue().equals(productType))
                {
                    sb.append("ggj://redirect/resource/mallProduct/").append(productId);
                }
            }
            map.put("url", sb.toString());
            List<Map<String, Object>> details = (List<Map<String, Object>>)activityMap.get(desc);
            if (details == null)
            {
                details = new ArrayList<Map<String, Object>>();
                details.add(map);
                activityMap.put(desc, details);
            }
            else
            {
                details.add(map);
            }
        }
        List<String> activityNames = tempActivityDao.findAllActivityNames();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (String name : activityNames)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("desc", name);
            map.put("detail", activityMap.get(name));
            resultList.add(map);
        }
        return resultList;
    }
    
    @Override
    public Map<String, Object> findAnniversaryProduct(int clientType)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<>();
        int timeType = 1;
        int hour = DateTime.now().getHourOfDay();
        if (hour < 12)
        {
            timeType = 1;
        }
        else if (hour < 14)
        {
            timeType = 2;
        }
        else if (hour < 16)
        {
            timeType = 3;
        }
        else if (hour < 20)
        {
            timeType = 4;
        }
        else if (hour < 22)
        {
            timeType = 5;
        }
        else if (hour <= 23)
        {
            timeType = 6;
        }
        else
        {
            timeType = 1;
        }
        
        List<Integer> productIds = tempActivityDao.findAnniversaryProductIds(timeType);
        List<Map<String, Object>> productList = new ArrayList<>();
        for (Integer productId : productIds)
        {
            Map<String, Object> product = new HashMap<>();
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("productName", pe.getShortName());
            product.put("imageUrl", pe.getImage1());
            product.put("highPrice", pe.getMarketPrice() + "");
            product.put("lowPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date nowTime = new Date();
                    Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    if (nowTime.before(startTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()));
                    }
                    else if (nowTime.after(endTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                        else
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                        }
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                    if (pce.getStock() == 0)
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            productList.add(product);
        }
        
        List<Map<String, Object>> groupList = tempActivityDao.findAnniversaryGroup();
        for (Map<String, Object> group : groupList)
        {
            int acId = Integer.parseInt(group.get("activities_common_id") == null ? "0" : group.get("activities_common_id").toString());
            if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
            {
                group.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/sale-" + acId + ".htm");
            }
            else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
            {
                group.put("buyUrl", "ggj://redirect/resource/commonActivitiesList/" + acId);
            }
            group.remove("activities_common_id");
        }
        
        List<Integer> recommendProductIds = tempActivityDao.findAnniversaryGroupRecommendProductIds();
        List<Map<String, Object>> recommendList = new ArrayList<>();
        for (Integer productId : recommendProductIds)
        {
            Map<String, Object> product = new HashMap<>();
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("productName", pe.getShortName());
            product.put("imageUrl", pe.getImage1());
            product.put("highPrice", pe.getMarketPrice() + "");
            product.put("lowPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + productId);
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
            }
            recommendList.add(product);
        }
        resultMap.put("timeType", timeType);
        resultMap.put("productList", productList);
        resultMap.put("groupList", groupList);
        resultMap.put("recommendList", recommendList);
        //logger.info(JSON.toJSONString(resultMap));
        return resultMap;
    }
    
    @Override
    public String getAnniversaryProductDate(int timeType, int clientType)
        throws ServiceException
    {
        List<Map<String, Object>> productList = new ArrayList<>();
        List<Integer> productIds = tempActivityDao.findAnniversaryProductIds(timeType);
        for (Integer productId : productIds)
        {
            Map<String, Object> product = new HashMap<>();
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("productName", pe.getShortName());
            product.put("imageUrl", pe.getImage1());
            product.put("highPrice", pe.getMarketPrice() + "");
            product.put("lowPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date nowTime = new Date();
                    Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    if (nowTime.before(startTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()));
                    }
                    else if (nowTime.after(endTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                        else
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                        }
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                    if (pce.getStock() == 0)
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            productList.add(product);
        }
        //logger.info(JSON.toJSONString(productList));
        return JSON.toJSONString(productList);
    }
    
    @Override
    public Map<String, Object> findAnniversaryCurrentProduct(int clientType)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<>();
        int timeType = 1;
        int hour = DateTime.now().getHourOfDay();
        if (hour < 12)
        {
            timeType = 1;
        }
        else if (hour < 14)
        {
            timeType = 2;
        }
        else if (hour < 16)
        {
            timeType = 3;
        }
        else if (hour < 20)
        {
            timeType = 4;
        }
        else if (hour < 22)
        {
            timeType = 5;
        }
        else if (hour <= 23)
        {
            timeType = 6;
        }
        else
        {
            timeType = 1;
        }
        
        List<Integer> productIds = tempActivityDao.findAnniversaryCurrentProductIds(timeType);
        List<Map<String, Object>> productList = new ArrayList<>();
        for (Integer productId : productIds)
        {
            Map<String, Object> product = new HashMap<>();
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("productName", pe.getShortName());
            product.put("imageUrl", pe.getImage1());
            product.put("highPrice", pe.getMarketPrice() + "");
            product.put("lowPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date nowTime = new Date();
                    Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    if (nowTime.before(startTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()));
                    }
                    else if (nowTime.after(endTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                        else
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                        }
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                    if (pce.getStock() == 0)
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            productList.add(product);
        }
        
        List<Map<String, Object>> groupList = tempActivityDao.findAnniversaryCurrentGroup();
        for (Map<String, Object> group : groupList)
        {
            int acId = Integer.parseInt(group.get("activities_common_id") == null ? "0" : group.get("activities_common_id").toString());
            if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
            {
                group.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/sale-" + acId + ".htm");
            }
            else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
            {
                group.put("buyUrl", "ggj://redirect/resource/commonActivitiesList/" + acId);
            }
            group.remove("activities_common_id");
        }
        
        List<Map<String, Object>> topProductIds = tempActivityDao.findAnniversaryCurrentHotTopProductIds();
        
        //销量榜
        List<Map<String, Object>> saleTopList = new ArrayList<>();
        //口碑榜
        List<Map<String, Object>> commentTopList = new ArrayList<>();
        //复购榜
        List<Map<String, Object>> repeatTopList = new ArrayList<>();
        //人气榜
        List<Map<String, Object>> popularTopList = new ArrayList<>();
        for (Map<String, Object> mp : topProductIds)
        {
            int productId = Integer.parseInt(mp.get("product_id").toString());
            int type = Integer.parseInt(mp.get("type").toString());
            
            Map<String, Object> product = new HashMap<>();
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("productName", pe.getShortName());
            product.put("imageUrl", pe.getImage1());
            product.put("highPrice", pe.getMarketPrice() + "");
            product.put("lowPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + productId);
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
            }
            
            if (type == 1)
            {
                saleTopList.add(product);
            }
            else if (type == 2)
            {
                commentTopList.add(product);
            }
            else if (type == 3)
            {
                repeatTopList.add(product);
            }
            else if (type == 4)
            {
                popularTopList.add(product);
            }
        }
        resultMap.put("timeType", timeType);
        resultMap.put("productList", productList);
        resultMap.put("groupList", groupList);
        resultMap.put("saleTopList", saleTopList);
        resultMap.put("commentTopList", commentTopList);
        resultMap.put("repeatTopList", repeatTopList);
        resultMap.put("popularTopList", popularTopList);
        //        logger.info(JSON.toJSONString(resultMap));
        return resultMap;
        
    }
    
    @Override
    public String getAnniversaryCurrentProductData(int timeType, int clientType)
        throws ServiceException
    {
        List<Map<String, Object>> productList = new ArrayList<>();
        List<Integer> productIds = tempActivityDao.findAnniversaryCurrentProductIds(timeType);
        for (Integer productId : productIds)
        {
            Map<String, Object> product = new HashMap<>();
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("productName", pe.getShortName());
            product.put("imageUrl", pe.getImage1());
            product.put("highPrice", pe.getMarketPrice() + "");
            product.put("lowPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date nowTime = new Date();
                    Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    if (nowTime.before(startTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()));
                    }
                    else if (nowTime.after(endTime))
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                        else
                        {
                            product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                        }
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                    if (pce.getStock() == 0)
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                    else
                    {
                        product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                    }
                }
                else
                {
                    product.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                }
            }
            productList.add(product);
        }
        //        logger.info(JSON.toJSONString(productList));
        return JSON.toJSONString(productList);
    }
}
