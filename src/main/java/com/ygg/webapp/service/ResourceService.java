package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.view.BannerView;
import com.ygg.webapp.view.BaseView;
import com.ygg.webapp.view.LaterView;
import com.ygg.webapp.view.NowView;

public interface ResourceService
{
    
    /**
     * 首页列表
     *
     * @param request
     * @return
     */
    String homeList(String requestParams)
        throws Exception;
        
    /**
     * 首页详情
     *
     * @param request
     * @return
     */
    String homeDetail(String requestParams)
        throws Exception;
        
    /**
     * 得到Banner所有信息
     * 
     * @return
     * @throws Exception
     */
    List<BannerView> getBannerDisplays()
        throws Exception;
        
    /**
     * 得到所有今天特卖的商品
     * 
     * @return
     * @throws Exception
     */
    List<NowView> getNowDisplays()
        throws Exception;
        
    /**
     * 得到首页信息
     * 
     * @return
     * @throws Exception
     */
    public <T extends BaseView> List<T> getIndexDisplays(String params)
        throws Exception;
        
    /**
     * 得到所有即将特卖的商品
     * 
     * @return
     * @throws Exception
     */
    List<LaterView> getLaterDisplays()
        throws Exception;
        
    /**
     * 商品基本信息
     *
     * @param request
     * @return
     */
    String productBase(String requestParams)
        throws Exception;
        
    /**
     * 商品详细信息
     *
     * @param request
     * @return
     */
    String productDetail(String requestParams)
        throws Exception;
        
    /**
     * 通用专场列表
     *
     * @param request
     * @return
     */
    String commonActivitiesList(String requestParams)
        throws Exception;
        
    /**
     * 通用专场详情
     *
     * @param request
     * @return
     */
    String commonActivitiesDetail(String requestParams, int type)
        throws Exception;
        
    /**
     * 自定义专场详情
     *
     * @param request
     * @return
     */
    String customActivitiesDetail(String requestParams)
        throws Exception;
        
    /**
     * 查询今日和即将特买的时间
     * 
     * @param saleWindowId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findStartEndTimeById(List<Integer> saleWindowId)
        throws Exception;
        
    /**
     * 得到品团的倒计时功能
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String getBrandEndSecond(String requestParams)
        throws Exception;
        
    /**
     * 得到商品详情的动态信息
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String getProductDynamicInfo(String requestParams)
        throws Exception;
        
    /**
     * 检查商品是否能够被访问
     * 
     * @param para
     * @return
     * @throws Exception
     */
    boolean checkProductCanBeAccessed(Map<String, Object> para)
        throws Exception;
        
    /**
     * 新版 首页列表 信息
     * 
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> homeListNew(Map<String, Object> para)
        throws Exception;
        
    /**
     * 新版 首页详情 信息
     * 
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> homeDetailNew(Map<String, Object> para)
        throws Exception;
        
    /**
     * 主题馆菜单列表
     * 
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> themeMenuList(Map<String, Object> para)
        throws Exception;
        
    /**
     * 主题馆菜单商品列表
     * 
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> themeMenuProductList(Map<String, Object> para)
        throws Exception;
        
    /**
     * 主题馆菜单商品详情
     * 
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> themeMenuProductDetail(Map<String, Object> para)
        throws Exception;
        
}
