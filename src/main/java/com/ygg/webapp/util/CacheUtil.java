
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ygg.common.utils.CommonUtil;
import com.ygg.webapp.cache.memcache.CacheManager;
import com.ygg.webapp.cache.memcache.CacheServiceIF;
import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.SaleFlagDaoIF;
import com.ygg.webapp.dao.banner.QqbsBannerDao;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.QqbsBannerWindowEntity;
import com.ygg.webapp.entity.QqbsSaleWindowEntity;
import com.ygg.webapp.exception.DaoException;


/**
  * 缓存工具类
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: CacheUtil.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public class CacheUtil {
	
	 /**缓存服务*/
	private static CacheServiceIF cache = CacheManager.getClient(CommonProperties.defaultCacheConfig);
	
	private static ProductDao productDao;
	
	private static QqbsBannerDao qqbsBannerDao;
	
	private static SaleFlagDaoIF saleFlagDao;
	
    private static ProductCountDao productCountDao;
    
    private static ActivitiesCommonDao activitiesCommonDao;
	/**
     * 根据商品id获取商品信息
     * 
     * @return
	 * @throws Exception 
     * @throws DaoException
     */
    public static ProductEntity getPE(int porductId) throws Exception{
        Object peCache = cache.get(CacheConstant.RES_PRODUCTBASE_PE_KEY + porductId);
        ProductEntity pe = null;
        if (peCache != null)
        {
            pe = (ProductEntity)peCache;
        }
        else
        {
        	if (productDao == null)
            {
        		productDao =  SpringBeanUtil.getBean(ProductDao.class);
            }
            pe = productDao.findProductInfoById(porductId);
            cache.set(CacheConstant.RES_PRODUCTBASE_PE_KEY + porductId, pe, CacheConstant.CACHE_MINUTE_1 * 2);
        }
        return pe;
    }
    
    /**
     * 根据特卖ID缓存特卖状态
     * @return
     * @throws Exception 
     * @throws DaoException
     */
    public static String getStatus(QqbsSaleWindowEntity swe) throws Exception{
        
        Object peCache = cache.get(CacheConstant.RES_SALE_WINDOW_STATUS_KEY + swe.getId());
        String status = null;
        if (peCache != null)
        {
            status = (String)peCache;
        }else{
            if (swe.getType() == CommonEnum.SALE_TYPE.PRODUCT.getValue())
            {
                if (productCountDao == null)
                {
                    productCountDao =  SpringBeanUtil.getBean(ProductCountDao.class);
                }
                ProductCountEntity pce = productCountDao.findCountInfoById(swe.getDisplayId());
                ProductEntity pe = CacheUtil.getPE(swe.getDisplayId());
                
                if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
                    Date currTime = new Date();
                    if (currTime.after(endTime)) // 已结束
                    {
                        status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue();
                    }
                    else if (currTime.before(startTime)) //即将开抢
                    {
                        status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.BRFORE.getValue();
                    }
                    else
                    {
                        int stock = pce.getStock();
                        if (stock == 0)
                        {
                            status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue();
                        }
                        else if (pce.getLock() >= stock)
                        {
                            status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue();
                        }
                        else
                        {
                            status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue();
                        }
                    }
                }
                else
                {
                    status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue();
                }
            }
            else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue())
            {
                List<Integer> availableProductIds = new ArrayList<Integer>();
                if (activitiesCommonDao == null)
                {
                    activitiesCommonDao =  SpringBeanUtil.getBean(ActivitiesCommonDao.class);
                }
                List<Integer> relationProductIds = activitiesCommonDao.findProductIdsById(swe.getDisplayId());
                for (Integer relationProductId : relationProductIds)
                {
                    ProductEntity pe = CacheUtil.getPE(relationProductId);
//                    Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
//                    Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
//                    Date currTime = new Date();
                    if (!(pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue())))
                    {
                        availableProductIds.add(relationProductId);
                    }
                }
                if (productCountDao == null)
                {
                    productCountDao =  SpringBeanUtil.getBean(ProductCountDao.class);
                }
                ProductCountEntity pce = productCountDao.findProductCountSumByProductIds(availableProductIds);
                int stock = pce.getStock();
                if (stock == 0)
                {
                    status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.NO.getValue();
                }
                else if (pce.getLock() >= stock)
                {
                    status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.CHANCE.getValue();
                }
                else
                {
                    status = CommonEnum.PRODUCT_NOW_STOCK_STATUS.HAVE.getValue();
                }
            }
            cache.set(CacheConstant.RES_SALE_WINDOW_STATUS_KEY + swe.getId(), status, CacheConstant.CACHE_MINUTE_1 + getRandom(1,60));
        }
        return status;
    }
    
    /**
     * 获取随机数
     * @param min
     * @param max
     * @return
     * @throws Exception
     */
    public static int getRandom(int min,int max) throws Exception{
        Random random = new Random();
       return random.nextInt(max)%(max-min+1) + min;
    }
    
    /**
     * 根据商品id获取商品信息
     * 
     * @return
     * @throws Exception 
     * @throws DaoException
     */
    public static ProductEntity getPCE(int porductId) throws Exception{
        Object peCache = cache.get(CacheConstant.RES_PRODUCTBASE_PE_KEY + porductId);
        ProductEntity pe = null;
        if (peCache != null)
        {
            pe = (ProductEntity)peCache;
        }
        else
        {
            if (productDao == null)
            {
                productDao =  SpringBeanUtil.getBean(ProductDao.class);
            }
            pe = productDao.findProductInfoById(porductId);
            cache.set(CacheConstant.RES_PRODUCTBASE_PE_KEY + porductId, pe, CacheConstant.CACHE_MINUTE_1 * 2);
        }
        return pe;
    }
	
    /**
     * 获取banner
     * @return
     * @throws Exception 
     * @throws DaoException
     */
    @SuppressWarnings({ "unchecked" })
	public static List<Map<String, String>> getBanner() throws Exception{
    	Object peCache = cache.get(CacheConstant.RES_BANNER_PE_KEY);
    	List<Map<String, String>> bannerList = new ArrayList<Map<String, String>>();
    	if(peCache != null){
    		bannerList = (List<Map<String, String>>) peCache;
    	}else{
    		String baseDefaultUrl = YggWebProperties.getInstance().getProperties("base_default_url");
    		if (qqbsBannerDao == null)
            {
    			qqbsBannerDao =  SpringBeanUtil.getBean(QqbsBannerDao.class);
            }
    		List<QqbsBannerWindowEntity> findBannerList = qqbsBannerDao.findAllBrandByPara(null);
            if (findBannerList != null && findBannerList.size() > 0){
                for (QqbsBannerWindowEntity qqbsBannerWindowEntity : findBannerList){
                	
                    Map<String, String> item = new HashMap<String, String>();
                    
                    item.put("image", qqbsBannerWindowEntity.getImage().replace("!v1banner", ""));
                    if (qqbsBannerWindowEntity.getType() == 1)
                    {
                        ProductEntity product = getPE(qqbsBannerWindowEntity.getDisplayId());
                        if (product.getType() == 1){
                            // 特卖
                            item.put("url", baseDefaultUrl+"/product/single/"+ qqbsBannerWindowEntity.getDisplayId());
                        }
                        else
                        {
                            // 商城
                            item.put("url", baseDefaultUrl+"/product/msingle/"
                                + qqbsBannerWindowEntity.getDisplayId());
                        }
                    }else if(qqbsBannerWindowEntity.getType() == 3){
                        //添加网页类型
                        item.put("url", qqbsBannerWindowEntity.getUrl());
                    }
                    else
                    {
                        // 组合
                        item.put("url", baseDefaultUrl+"/cnty/toac/"
                            + qqbsBannerWindowEntity.getDisplayId());
                    }
                    bannerList.add(item);
                }
                cache.set(CacheConstant.RES_BANNER_PE_KEY, bannerList, CacheConstant.CACHE_MINUTE_1 * 2);
            }
    	}
        return bannerList;
    }
    /**
     * 缓存国籍
     * @return
	 * @throws Exception 
     * @throws DaoException
     */
    public static String getImageById(int id) throws Exception{
        Object peCache = cache.get(CacheConstant.RES_SALE_FLAG_PE_KEY + id);
        String image = null;
        if (peCache != null)
        {
        	image = (String)peCache;
        }
        else
        {
        	if (saleFlagDao == null)
            {
        		saleFlagDao =  SpringBeanUtil.getBean(SaleFlagDaoIF.class);
            }
        	image = saleFlagDao.findImageById(id);
            cache.set(CacheConstant.RES_SALE_FLAG_PE_KEY + id, image, CacheConstant.CACHE_DAY_1);
        }
        return image;
    }
}
