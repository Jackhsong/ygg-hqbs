package com.ygg.webapp.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.service.ThreadService;

public class CacheThreadServiceImpl extends Thread implements ThreadService
{
    
    private static Logger logger = Logger.getLogger(CacheThreadServiceImpl.class);
    
    private boolean isshutdown = false;
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Override
    public void init()
    {
        this.setName("CacheThreadServiceImpl");
        this.start();
    }
    
    @Override
    public void destory()
    {
        
        isshutdown = true;
    }
    
    public void run()
    {
        while (!isshutdown)
        {
            try
            {
                Thread.sleep(1000 * 10); // / 10分钟检查一次
            }
            catch (InterruptedException e)
            {
                logger.error("------CacheThreadServiceImpl------", e);
            }
            try
            {
                cacheService.timerClearCache();
            }
            catch (Exception e)
            {
                logger.error("------CacheThreadServiceImpl2------", e);
            }
            finally
            {
            }
        }
    }
}
