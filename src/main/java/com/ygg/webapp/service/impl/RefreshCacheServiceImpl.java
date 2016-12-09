package com.ygg.webapp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.service.ThreadService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonUtil;

public class RefreshCacheServiceImpl implements ThreadService
{
    
    private static Logger logger = Logger.getLogger(RefreshCacheServiceImpl.class);
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Override
    public void init()
    {
        String curtimeStr = CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        Date curtime = CommonUtil.string2Date(curtimeStr, "yyyy-MM-dd HH:mm:ss");
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        end.set(Calendar.HOUR_OF_DAY, 10);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MINUTE, 0);
        long time = curtime.getTime() - end.getTimeInMillis();
        if (time > 0) // 10点后
        {
            end.add(Calendar.DAY_OF_YEAR, 1); // 取明天的10点
        }
        
        java.util.Timer timer = new java.util.Timer();
        long period = 1000 * 60 * 60 * 24;
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                executeJob();
            }
        }, end.getTime(), period);
    }
    
    @Override
    public void destory()
    {
        
    }
    
    private void executeJob()
    {
        logger.info("--------------RefreshCacheServiceImpl----job----runing-------------------");
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
            logger.error("清空首页缓存出错", ex);
        }
        
        try
        {
            this.cacheService.clearCache(CacheConstant.COMMON_ACTIVITY_PRODUCT_CACHE);
            // /因商品页面所需数据较多，页面缓存就不在此刷新，第一次请求时加入缓存中　
            this.cacheService.clearCache(CacheConstant.PAGE_COMMON_ACTIVITY_PRODUCT_CACHE);
            this.cacheService.clearCache(CacheConstant.PAGE_WXSHARE_COMMON_ACTIVITY_PRODUCT_CACHE);
        }
        catch (Exception ex)
        {
            logger.error("清空专场缓存出错", ex);
        }
        
        try
        {
            this.cacheService.clearCache(CacheConstant.PAGE_WXSHARE_SINGLE_PRODUCT_CACHE);
            this.cacheService.clearCache(CacheConstant.PAGE_SINGLE_PRODUCT_CACHE);
            
            this.cacheService.clearCache(CacheConstant.SINGLE_PRODUCT_CACHE);
        }
        catch (Exception ex)
        {
            logger.error("清空单个商品缓存出错", ex);
        }
        logger.info("--------------RefreshCacheServiceImpl---job---end-------------------");
    }
    
}
