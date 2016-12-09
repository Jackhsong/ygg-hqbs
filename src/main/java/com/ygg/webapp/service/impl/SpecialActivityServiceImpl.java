package com.ygg.webapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ygg.common.utils.CommonUtil;
import com.ygg.webapp.code.AccessTypeEnum;
import com.ygg.webapp.code.ActivityEnum;
import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.ActivitiesCustomDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.SpecialActivityDao;
import com.ygg.webapp.entity.ActivitiesCommonEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.SpecialActivityEntity;
import com.ygg.webapp.entity.SpecialActivityGroupEntity;
import com.ygg.webapp.entity.SpecialActivityModelEntity;
import com.ygg.webapp.entity.SpecialActivityModelLayoutEntity;
import com.ygg.webapp.entity.SpecialActivityModelLayoutProductEntity;
import com.ygg.webapp.entity.SpecialActivityNewEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.SpecialActivityService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.YggWebProperties;

@Service("specialActivityService")
public class SpecialActivityServiceImpl implements SpecialActivityService
{
    private static Logger logger = Logger.getLogger(SpecialActivityServiceImpl.class);
    
    @Resource
    private SpecialActivityDao specialActivityDao;
    
    @Resource
    private ActivitiesCommonDao activitiesCommonDao;
    
    @Resource
    private ProductDao productDao;
    
    @Resource(name = "productCountDao")
    private ProductCountDao productCountDao;
    
    @Resource
    private ActivitiesCustomDao activitiesCustomDao;
    
    @Override
    public Map<String, Object> findSpecialActivityDetailById(int activityId, int accessType)
        throws ServiceException
    {
        Map<String, Object> resultMap = null;
        SpecialActivityEntity sae = specialActivityDao.findSpecialActivityById(activityId);
        if (sae == null)
        {
            return resultMap;
        }
        resultMap = new HashMap<String, Object>();
        resultMap.put("title", sae.getTitle());
        resultMap.put("image", sae.getImage());
        resultMap.put("desc", sae.getDesc());
        resultMap.put("width", sae.getImageWidth());
        resultMap.put("height", sae.getImageHeight());
        List<String> categoryList = new ArrayList<String>();
        List<Map<String, Object>> layoutList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> reLayouts = specialActivityDao.findSpecialActivityLayout(activityId);
        for (Map<String, Object> layout : reLayouts)
        {
            int layouId = Integer.valueOf(layout.get("id") + "").intValue();
            categoryList.add(layout.get("shortTitle") + "");
            
            Map<String, Object> layoutMap = new HashMap<String, Object>();
            layoutMap.put("title", layout.get("longTitle") + "");
            layoutMap.put("desc", layout.get("desc") + "");
            layoutMap.put("sequence", layout.get("sequence") + "");
            
            List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> reProducts = specialActivityDao.findSpecialActivityLayouProduct(layouId);
            for (Map<String, Object> product : reProducts)
            {
                Map<String, Object> productMap = new HashMap<String, Object>();
                int displayType = Integer.valueOf(product.get("displayType") + "").intValue();
                productMap.put("displayType", displayType);
                productMap.put("sequence", product.get("sequence"));
                
                if (displayType == 1) // 单张
                {
                    productMap.put("image", product.get("oneImage"));
                    productMap.put("width", product.get("oneWidth"));
                    productMap.put("height", product.get("oneHeight"));
                    productMap.put("desc", product.get("oneDesc"));
                    int type = Integer.valueOf(product.get("oneType") + "").intValue();
                    int oneDisplyId = Integer.valueOf(product.get("oneDisplayId") + "").intValue();
                    
                    String url = "";
                    if (type == 1) // 单品
                    {
                        ProductEntity pe = productDao.findProductInfoById(oneDisplyId);
                        productMap.put("name", pe.getName());
                        productMap.put("salesPrice", pe.getSalesPrice());
                        productMap.put("marketPrice", pe.getMarketPrice());
                        
                        if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                        {
                            if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                            {
                                url += "ggj://redirect/resource/saleProduct/" + pe.getId();
                            }
                            else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                            {
                                url += "/item-" + pe.getId();
                            }
                            if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                            {
                                Date nowTime = new Date();
                                Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                                Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                                if (nowTime.before(startTime))
                                {
                                    productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                                }
                                else if (nowTime.after(endTime))
                                {
                                    productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                }
                                else
                                {
                                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                    if (pce.getStock() == 0)
                                    {
                                        productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                    }
                                    else
                                    {
                                        productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                                    }
                                }
                            }
                            else
                            {
                                productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                            }
                        }
                        else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                        {
                            if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                            {
                                url += "ggj://redirect/resource/mallProduct/" + pe.getId();
                            }
                            else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                            {
                                url += "/mitem-" + pe.getId();
                            }
                            if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                            {
                                ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                if (pce.getStock() == 0)
                                {
                                    productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                }
                                else
                                {
                                    productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                                }
                            }
                            else
                            {
                                productMap.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                            }
                        }
                    }
                    else if (type == 2) // 专场
                    {
                        ActivitiesCommonEntity ace = activitiesCommonDao.findActivitiesInfoById(oneDisplyId);
                        productMap.put("name", ace.getName());
                        if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                        {
                            url += "ggj://redirect/resource/commonActivitiesList/" + ace.getId();
                        }
                        else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                        {
                            url += "/sale-" + ace.getId();
                        }
                    }
                    productMap.put("url", url);
                    productMap.put("type", type);
                }
                else if (displayType == 2) // 两张
                {
                    Map<String, Object> left = new HashMap<String, Object>();
                    left.put("image", product.get("oneImage"));
                    left.put("width", product.get("oneWidth"));
                    left.put("height", product.get("oneHeight"));
                    left.put("desc", product.get("oneDesc"));
                    
                    int oneType = Integer.valueOf(product.get("oneType") + "").intValue();
                    int oneDisplyId = Integer.valueOf(product.get("oneDisplayId") + "").intValue();
                    
                    String leftUrl = "";
                    if (oneType == 1) // 单品
                    {
                        ProductEntity pe = productDao.findProductInfoById(oneDisplyId);
                        left.put("name", pe.getName());
                        left.put("salesPrice", pe.getSalesPrice());
                        left.put("marketPrice", pe.getMarketPrice());
                        if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                        {
                            if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                            {
                                leftUrl += "ggj://redirect/resource/saleProduct/" + pe.getId();
                            }
                            else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                            {
                                leftUrl += "/item-" + pe.getId();
                            }
                            
                            if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                            {
                                Date nowTime = new Date();
                                Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                                Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                                if (nowTime.before(startTime))
                                {
                                    left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                                }
                                else if (nowTime.after(endTime))
                                {
                                    left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                }
                                else
                                {
                                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                    if (pce.getStock() == 0)
                                    {
                                        left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                    }
                                    else
                                    {
                                        left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                                    }
                                }
                            }
                            else
                            {
                                left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                            }
                        }
                        else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                        {
                            if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                            {
                                leftUrl += "ggj://redirect/resource/mallProduct/" + pe.getId();
                            }
                            else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                            {
                                leftUrl += "/mitem-" + pe.getId();
                            }
                            if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                            {
                                ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                if (pce.getStock() == 0)
                                {
                                    left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                }
                                else
                                {
                                    left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                                }
                            }
                            else
                            {
                                left.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                            }
                        }
                    }
                    else if (oneType == 2) // 专场
                    {
                        ActivitiesCommonEntity ace = activitiesCommonDao.findActivitiesInfoById(oneDisplyId);
                        left.put("name", ace.getName());
                        if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                        {
                            leftUrl += "ggj://redirect/resource/commonActivitiesList/" + ace.getId();
                        }
                        else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                        {
                            leftUrl += "/sale-" + ace.getId();
                        }
                    }
                    left.put("type", oneType);
                    left.put("url", leftUrl);
                    productMap.put("left", left);
                    
                    Map<String, Object> right = new HashMap<String, Object>();
                    right.put("image", product.get("twoImage"));
                    right.put("width", product.get("twoWidth"));
                    right.put("height", product.get("twoHeight"));
                    right.put("desc", product.get("twoDesc"));
                    
                    int twoType = Integer.valueOf(product.get("twoType") + "").intValue();
                    int twoDisplyId = Integer.valueOf(product.get("twoDisplayId") + "").intValue();
                    String rightUrl = "";
                    if (twoType == 1) // 单品
                    {
                        ProductEntity pe = productDao.findProductInfoById(twoDisplyId);
                        right.put("name", pe.getName());
                        right.put("salesPrice", pe.getSalesPrice());
                        right.put("marketPrice", pe.getMarketPrice());
                        if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                        {
                            if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                            {
                                rightUrl += "ggj://redirect/resource/saleProduct/" + pe.getId();
                            }
                            else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                            {
                                rightUrl += "/item-" + pe.getId();
                            }
                            if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                            {
                                Date nowTime = new Date();
                                Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                                Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                                if (nowTime.before(startTime))
                                {
                                    right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                                }
                                else if (nowTime.after(endTime))
                                {
                                    right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                }
                                else
                                {
                                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                    if (pce.getStock() == 0)
                                    {
                                        right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                    }
                                    else
                                    {
                                        right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                                    }
                                }
                            }
                            else
                            {
                                right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                            }
                        }
                        else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                        {
                            if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                            {
                                rightUrl += "ggj://redirect/resource/mallProduct/" + pe.getId();
                            }
                            else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                            {
                                rightUrl += "/mitem-" + pe.getId();
                            }
                            if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                            {
                                ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                if (pce.getStock() == 0)
                                {
                                    right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                                }
                                else
                                {
                                    right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                                }
                            }
                            else
                            {
                                right.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                            }
                            
                        }
                    }
                    else if (twoType == 2) // 专场
                    {
                        ActivitiesCommonEntity ace = activitiesCommonDao.findActivitiesInfoById(twoDisplyId);
                        right.put("name", ace.getName());
                        if (accessType == AccessTypeEnum.TYPE_OF_APP.getValue())
                        {
                            rightUrl += "ggj://redirect/resource/commonActivitiesList/" + ace.getId();
                        }
                        else if (accessType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                        {
                            rightUrl += "/sale-" + ace.getId();
                        }
                    }
                    right.put("type", twoType);
                    right.put("url", rightUrl);
                    productMap.put("right", right);
                }
                productList.add(productMap);
            }
            
            layoutMap.put("productList", productList);
            layoutList.add(layoutMap);
        }
        resultMap.put("categoryList", categoryList);
        resultMap.put("layoutList", layoutList);
        return resultMap;
    }
    
    @Override
    public Map<String, Object> findSpecialActivityNewById(int specialActivityNewId, int type, int client)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<>();
        SpecialActivityNewEntity sane = specialActivityDao.findSpecialActivityNewById(specialActivityNewId);
        if (sane == null)
        {
            return result;
        }
        result.put("specialActivityId", sane.getId());
        result.put("url", sane.getImage());
        List<Map<String, Object>> productList = new ArrayList<>();
        
        for (Map<String, Object> p : specialActivityDao.findSpecialActivityNewProductByActIdAndType(specialActivityNewId, 1))
        {
            Map<String, Object> product = new HashMap<>();
            product.put("name", p.get("title") + "");
            product.put("desc", p.get("desc") + "");
            product.put("keyword", p.get("keyword") + "");
            int productId = Integer.valueOf(p.get("productId") + "");
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("id", productId);
            product.put("url", pe.getImage1());
            product.put("gegePrice", pe.getMarketPrice() + "");
            product.put("salesPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (client == 1)
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else
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
                if (client == 1)
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else
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
        result.put("productList", productList);
        // 是否还有更多商品
        List<Map<String, Object>> productMoreList = new ArrayList<>();
        if (specialActivityDao.countSpecialActivityNewProductByActIdAndType(specialActivityNewId, 2) > 0)
        {
            for (Map<String, Object> p : specialActivityDao.findSpecialActivityNewProductByActIdAndType(specialActivityNewId, 2))
            {
                Map<String, Object> product = new HashMap<>();
                product.put("desc", p.get("desc") + "");
                int productId = Integer.valueOf(p.get("productId") + "");
                ProductEntity pe = productDao.findProductInfoById(productId);
                product.put("id", productId);
                // product.put("name", StringUtil.formatterStrLen(pe.getName(), 36, "&nbsp;"));
                product.put("name", pe.getName());
                product.put("url", pe.getImage1());
                product.put("gegePrice", pe.getMarketPrice() + "");
                product.put("salesPrice", pe.getSalesPrice() + "");
                
                if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                {
                    if (client == 1)
                    {
                        product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                    }
                    else
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
                    if (client == 1)
                    {
                        product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                    }
                    else
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
                productMoreList.add(product);
            }
        }
        result.put("productMoreList", productMoreList);
        return result;
    }
    
    @Override
    public List<Map<String, Object>> findSpecialActivityNewMoreProductById(int specialActivityNewId, int type, int client)
        throws ServiceException
    {
        List<Map<String, Object>> productList = new ArrayList<>();
        List<Map<String, Object>> moreProductList = specialActivityDao.findSpecialActivityNewProductByActIdAndType(specialActivityNewId, 2);
        for (Map<String, Object> p : moreProductList)
        {
            Map<String, Object> product = new HashMap<>();
            product.put("name", p.get("title") + "");
            product.put("desc", p.get("desc") + "");
            int productId = Integer.valueOf(p.get("productId") + "");
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("id", productId);
            product.put("url", pe.getImage1());
            product.put("gegePrice", pe.getMarketPrice() + "");
            product.put("salesPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (client == 1)
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else
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
                        product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                    }
                    else if (nowTime.after(endTime))
                    {
                        product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    }
                    else
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                        }
                        else
                        {
                            product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                        }
                    }
                }
                else
                {
                    product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (client == 1)
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else
                {
                    product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + productId);
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                    if (pce.getStock() == 0)
                    {
                        product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    }
                    else
                    {
                        product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                    }
                }
                else
                {
                    product.put("status", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                }
            }
            productList.add(product);
        }
        return productList;
    }
    
    @Override
    public Map<String, Object> findSpecialActivityGroupById(int id, int clientType)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<>();
        SpecialActivityGroupEntity group = specialActivityDao.findSpecialActivityGroupById(id);
        if (group == null)
        {
            return resultMap;
        }
        resultMap.put("activityId", id);
        resultMap.put("title", group.getTitle());
        
        // 楼层商品
        List<Map<String, Object>> productList = new ArrayList<>();
        List<Map<String, Object>> products = specialActivityDao.findSpecialActivityGroupProductByActIdAndType(id, 1);
        for (Map<String, Object> mp : products)
        {
            int layoutType = Integer.parseInt(mp.get("layout_type").toString());
            Map<String, Object> item = new HashMap<>();
            item.put("layoutType", mp.get("layout_type"));
            
            item.put("oneType", mp.get("one_type"));
            item.put("oneRemark", mp.get("one_remark"));
            item.put("oneImageUrl", mp.get("one_image_url"));
            item.put("oneImageWidth", mp.get("one_image_width"));
            item.put("oneImageHeight", mp.get("one_image_height"));
            
            int oneType = Integer.parseInt(mp.get("one_type").toString());
            int oneDisplayId = Integer.parseInt(mp.get("one_display_id").toString());
            if (oneType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.PRODUCT.getValue())
            {
                ProductEntity pe = productDao.findProductInfoById(oneDisplayId);
                if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                {
                    if (clientType == 1)
                    {
                        item.put("oneUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + oneDisplayId + ".htm");
                    }
                    else
                    {
                        item.put("oneUrl", "ggj://redirect/resource/saleProduct/" + oneDisplayId);
                    }
                    if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                    {
                        Date nowTime = new Date();
                        Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                        Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                        if (nowTime.before(startTime))
                        {
                            item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()));
                        }
                        else if (nowTime.after(endTime))
                        {
                            item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                        else
                        {
                            ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                            if (pce.getStock() == 0)
                            {
                                item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                            }
                            else
                            {
                                item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                            }
                        }
                    }
                    else
                    {
                        item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                }
                else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                {
                    if (clientType == 1)
                    {
                        item.put("oneUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + oneDisplayId + ".htm");
                    }
                    else
                    {
                        item.put("oneUrl", "ggj://redirect/resource/mallProduct/" + oneDisplayId);
                    }
                    if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                        else
                        {
                            item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                        }
                    }
                    else
                    {
                        item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                    }
                }
            }
            else if (oneType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.GROUP.getValue())
            {
                if (clientType == 1)
                {
                    item.put("oneUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/sale-" + oneDisplayId + ".htm");
                }
                else
                {
                    item.put("oneUrl", "ggj://redirect/resource/commonActivitiesList/" + oneDisplayId);
                }
            }
            else if (oneType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.CUSTOM_ACTIVITY.getValue())
            {
                if (clientType == 1)
                {
                    item.put("oneUrl", activitiesCustomDao.findActivitiesInfoById(oneDisplayId).getShareUrl());
                }
                else
                {
                    item.put("oneUrl", "ggj://redirect/resource/customActivitiesDetail/" + oneDisplayId);
                }
            }
            else if (oneType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.NON_HREF.getValue())
            {
                item.put("oneUrl", "");
            }
            
            if (layoutType == 2)
            {
                item.put("twoType", mp.get("two_type"));
                item.put("twoRemark", mp.get("two_remark"));
                item.put("twoImageUrl", mp.get("two_image_url"));
                item.put("twoImageWidth", mp.get("two_image_width"));
                item.put("twoImageHeight", mp.get("two_image_height"));
                
                int twoType = Integer.parseInt(mp.get("two_type").toString());
                int twoDisplayId = Integer.parseInt(mp.get("two_display_id").toString());
                if (twoType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.PRODUCT.getValue())
                {
                    ProductEntity pe = productDao.findProductInfoById(twoDisplayId);
                    if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                    {
                        if (clientType == 1)
                        {
                            item.put("twoUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + twoDisplayId + ".htm");
                        }
                        else
                        {
                            item.put("twoUrl", "ggj://redirect/resource/saleProduct/" + twoDisplayId);
                        }
                        if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                        {
                            Date nowTime = new Date();
                            Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                            Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                            if (nowTime.before(startTime))
                            {
                                item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue()));
                            }
                            else if (nowTime.after(endTime))
                            {
                                item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                            }
                            else
                            {
                                ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                                if (pce.getStock() == 0)
                                {
                                    item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                                }
                                else
                                {
                                    item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                                }
                            }
                        }
                        else
                        {
                            item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                    }
                    else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                    {
                        if (clientType == 1)
                        {
                            item.put("twoUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + twoDisplayId + ".htm");
                        }
                        else
                        {
                            item.put("twoUrl", "ggj://redirect/resource/mallProduct/" + twoDisplayId);
                        }
                        if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                        {
                            ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                            if (pce.getStock() == 0)
                            {
                                item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                            }
                            else
                            {
                                item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue()));
                            }
                        }
                        else
                        {
                            item.put("status", Integer.valueOf(CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue()));
                        }
                    }
                }
                else if (twoType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.GROUP.getValue())
                {
                    if (clientType == 1)
                    {
                        item.put("twoUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/sale-" + twoDisplayId + ".htm");
                    }
                    else
                    {
                        item.put("twoUrl", "ggj://redirect/resource/commonActivitiesList/" + twoDisplayId);
                    }
                }
                else if (twoType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.CUSTOM_ACTIVITY.getValue())
                {
                    if (clientType == 1)
                    {
                        item.put("twoUrl", activitiesCustomDao.findActivitiesInfoById(twoDisplayId).getShareUrl());
                    }
                    else
                    {
                        item.put("twoUrl", "gegejia://resource/appCustomActivitiesDetail/" + twoDisplayId);
                    }
                }
                else if (twoType == ActivityEnum.SPECIAL_ACTIVITY_GROUP_TYPE.NON_HREF.getValue())
                {
                    item.put("twoUrl", "");
                }
            }
            productList.add(item);
        }
        resultMap.put("productList", productList);
        
        // 更多商品
        List<Map<String, Object>> productMoreList = new ArrayList<>();
        List<Map<String, Object>> productMores = specialActivityDao.findSpecialActivityGroupProductByActIdAndType(id, 2);
        for (Map<String, Object> p : productMores)
        {
            Map<String, Object> product = new HashMap<>();
            int productId = Integer.valueOf(p.get("product_id") + "");
            ProductEntity pe = productDao.findProductInfoById(productId);
            product.put("id", productId);
            product.put("name", pe.getName());
            product.put("imageUrl", pe.getImage1());
            product.put("gegePrice", pe.getMarketPrice() + "");
            product.put("salesPrice", pe.getSalesPrice() + "");
            
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (clientType == 1)
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + productId + ".htm");
                }
                else
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
                if (clientType == 1)
                {
                    product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + productId + ".htm");
                }
                else
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
            productMoreList.add(product);
        }
        resultMap.put("productMoreList", productMoreList);
        return resultMap;
    }
    
    @Override
    public Map<String, Object> findSpecialActivityModelById(int id)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<>();
        SpecialActivityModelEntity same = specialActivityDao.findSpecialActivityModelById(id);
        if (same == null)
        {
            return resultMap;
        }
        
        resultMap.put("title", same.getTitle());
        resultMap.put("imageUrl", same.getImage());
        resultMap.put("height", same.getImageHeight() + "");
        resultMap.put("width", same.getImageWidth() + "");
        
        List<String> categoryList = new ArrayList<>();
        List<SpecialActivityModelLayoutEntity> layouts = specialActivityDao.findSpecialActivityModelLayoutsBysamId(id);
        for (SpecialActivityModelLayoutEntity samle : layouts)
        {
            categoryList.add(samle.getTitle());
        }
        resultMap.put("categoryList", categoryList);
        return resultMap;
    }
    
    @Override
    public String findSpecialActivityModelLayoutProductByIdAndType(int activityId, int clientType)
        throws ServiceException
    {
        List<Map<String, Object>> layoutList = new ArrayList<>();
        List<SpecialActivityModelLayoutEntity> layouts = specialActivityDao.findSpecialActivityModelLayoutsBysamId(activityId);
        for (SpecialActivityModelLayoutEntity layout : layouts)
        {
            Map<String, Object> item = new HashMap<>();
            item.put("imageUrl", layout.getImage());
            item.put("height", layout.getImageHeight() + "");
            item.put("width", layout.getImageWidth() + "");
            
            List<Map<String, Object>> productList = new ArrayList<>();
            List<SpecialActivityModelLayoutProductEntity> products = specialActivityDao.findSpecialActivityModelLayoutProductsByLayoutId(layout.getId());
            for (SpecialActivityModelLayoutProductEntity lp : products)
            {
                ProductEntity pe = productDao.findProductInfoById(lp.getProductId());
                if (pe != null)
                {
                    Map<String, Object> product = new HashMap<>();
                    product.put("name", pe.getName());
                    product.put("desc", lp.getDesc());
                    product.put("highPrice", format(pe.getMarketPrice() + ""));
                    product.put("lowPrice", format(pe.getSalesPrice() + ""));
                    product.put("imageUrl", pe.getImage1());
                    if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                    {
                        if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                        {
                            product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/item-" + pe.getId() + ".htm");
                        }
                        else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                        {
                            product.put("buyUrl", "ggj://redirect/resource/saleProduct/" + pe.getId());
                        }
                    }
                    else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                    {
                        if (clientType == AccessTypeEnum.TYPE_OF_WAP.getValue())
                        {
                            product.put("buyUrl", YggWebProperties.getInstance().getProperties("domain_name") + "/mitem-" + pe.getId() + ".htm");
                        }
                        else if (clientType == AccessTypeEnum.TYPE_OF_APP.getValue())
                        {
                            product.put("buyUrl", "ggj://redirect/resource/mallProduct/" + pe.getId());
                        }
                    }
                    productList.add(product);
                }
            }
            item.put("productList", productList);
            layoutList.add(item);
        }
        //        logger.info(JSON.toJSONString(layoutList));
        return JSON.toJSONString(layoutList);
    }
    
    private String format(String str)
    {
        if (str.indexOf(".") > 0)
        {
            if (str.endsWith("0"))
            {
                return str.substring(0, str.lastIndexOf("."));
            }
            return str;
        }
        else
        {
            return str;
        }
    }
}
