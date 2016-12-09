
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.common.utils.CommonUtil;
import com.ygg.webapp.cache.memcache.CacheManager;
import com.ygg.webapp.cache.memcache.CacheServiceIF;
import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.SaleWindowDao;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.QqbsSaleWindowEntity;
import com.ygg.webapp.service.ActivityService;
import com.ygg.webapp.service.ProductService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.banner.HqbsBannerService;
import com.ygg.webapp.service.sale.HqbsSaleService;
import com.ygg.webapp.service.saleflag.SaleFlagService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CacheUtil;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonProperties;
import com.ygg.webapp.util.YggWebProperties;

/**
  * 左岸城堡首页
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HomeInfoController.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Controller
@RequestMapping("/hqbsHomeInfo")
public class HomeInfoController {
	/**    */
	@Resource(name="hqbsBannerService")
	private HqbsBannerService hqbsBannerService;
	 /**    */
	@Resource(name="productService")
	private ProductService productService;
	 /**    */
	@Resource(name="hqbsSaleService")
	private HqbsSaleService HqbsSaleService;
	 /**    */
	@Resource(name="saleFlagService")
	private SaleFlagService saleFlagService;
	 /**    */
	@Resource(name="activityService")
	private ActivityService activityService;
	 /**    */
	@Resource(name="shoppingCartService")
	private ShoppingCartService shoppingCartService;
	
    @Resource(name = "activitiesCommonDao")
    private ActivitiesCommonDao acd;
	
	 /**缓存服务*/
	private static CacheServiceIF cache = CacheManager.getClient(CommonProperties.defaultCacheConfig);
	
	
	@SuppressWarnings("unchecked")
    @RequestMapping(value = "/getHomeInfo")
    public ModelAndView getHomeInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	        throws Exception{
		
        ModelAndView modelAndView = new ModelAndView("homepage/index");
        modelAndView.addObject("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        String baseDefaultUrl = YggWebProperties.getInstance().getProperties("base_default_url");
        //banner
        List<Map<String, String>> bannerList = CacheUtil.getBanner();
        
    	Object saleCache = cache.get(CacheConstant.RES_SALE_WINDOW_KEY);
    	List<Map<String, String>> saleList = new ArrayList<Map<String, String>>();
    	if(saleCache != null){
    		saleList = (List<Map<String, String>>) saleCache;
    	}else{
    		List<QqbsSaleWindowEntity> findSaleList = HqbsSaleService.findSaleList(null);
          if (findSaleList != null && findSaleList.size() > 0)
          {
              for (QqbsSaleWindowEntity swe : findSaleList)
              {
                  Date endTime = CommonUtil.string2Date(swe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                  String second = (endTime.getTime() - new Date().getTime()) / 1000 + "";
                  
                  Map<String, String> item = new HashMap<String, String>();
                  item.put("image", swe.getImage().replace("!v1sell", ""));
                  
                  if (swe.getType() == 1)
                  {
                      ProductEntity product = CacheUtil.getPE(swe.getDisplayId());
                      
                      if (product.getType() == 1)
                      {
                          // 特卖
                          item.put("url", baseDefaultUrl+"/product/single/"
                              + swe.getDisplayId());
                      }
                      else
                      {
                          // 商城
                          item.put("url", baseDefaultUrl+"/product/msingle/"
                              + swe.getDisplayId());
                      }
                  }
                  else
                  {
                      // 组合
                      item.put("url", baseDefaultUrl+"/cnty/toac/"
                          + swe.getDisplayId());
                      float price = acd.findPriceById(swe.getDisplayId());
                      item.put("price", price+"");
                  }
                  
                  //计算时间
                  second = processSecond(swe, second);
                  //获取特卖状态
                  String status = CacheUtil.getStatus(swe);
                  status = (status !=null)?status:"0";
                  item.put("status", status);
                  item.put("endSecond", second);
                  item.put("leftDesc", swe.getName());
                  item.put("rightDesc", swe.getDesc());
                  String flagImage = CacheUtil.getImageById((swe.getSaleFlagId()));
                  flagImage = (flagImage != null)?flagImage:"";
                  item.put("flagImage", flagImage);
                  saleList.add(item);
              }
              cache.set(CacheConstant.RES_SALE_WINDOW_KEY, saleList, CacheConstant.CACHE_MINUTE_1 / 2);
          }
    	}
    	
        
        modelAndView.addObject("bannerList", bannerList);
        modelAndView.addObject("saleList", saleList);
        modelAndView.addObject("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
	
    @RequestMapping(value = "/load")
    public ModelAndView load(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	        throws Exception{
        ModelAndView modelAndView = new ModelAndView("homepage/load");
        return modelAndView;

    }
	
	
	/**
	 * 处理时间
	 * @param swe 特卖
	 * @param second 时间
	 * @return  String 
	 * @throws Exception
	 */
	private String processSecond(QqbsSaleWindowEntity swe,String second)
        throws Exception{
	    
	    if (swe.getType() == CommonEnum.SALE_TYPE.PRODUCT.getValue())
        {
            ProductEntity pe = CacheUtil.getPE(swe.getDisplayId());
            
            if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()))
            {
                Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
                Date currTime = new Date();
                //即将开抢
                if (currTime.before(startTime)) 
                {
                    //显示还差多久开始
                    second = (startTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
        }
	    return second;
	    
	}
}
