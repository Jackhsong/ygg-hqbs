package com.ygg.webapp.cache.impl;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.cache.memcache.CacheManager;
import com.ygg.webapp.exception.CacheException;
import com.ygg.webapp.util.CacheConstant;

@Service("memService")
public class MemcacheProxyServiceImpl implements CacheServiceIF
{
    
    Logger logger = Logger.getLogger(MemcacheProxyServiceImpl.class);
    
    private com.ygg.webapp.cache.memcache.CacheServiceIF memService = CacheManager.getClient();
    
    @Override
    public <T> void addCache(String k, T v, int pExpire)
    {
        if (k != null && v != null)
            this.memService.set(k, v, pExpire);
    }
    
    @Override
    public <T> void updateCache(String k, T v, int pExpire)
    {
        if (k != null && v != null)
            this.memService.set(k, v, pExpire);
        
    }
    
    @Override
    public void clearCache(String k)
    {
        try
        {
            this.memService.delete(k);
        }
        catch (CacheException e)
        {
            logger.error("memService clearCache error ", e);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCache(String k)
    {
         if (this.memService.get(k) == null)
            return null;
        return (T)this.memService.get(k);
    }
    
    @Override
    public boolean isInCache(String k)
    {
        if (this.memService.get(k) != null)
            return true;
        return false;
    }
    
    @Override
    public void clear()
    {
        Set<String> webappKeys = this.memService.keys();
        for (String key : webappKeys)
        {
            if (key.contains(CacheConstant.HQBSWEB_CACHE_KEY))
            {
                try
                {
                    this.memService.delete(key);
                }
                catch (CacheException e)
                {
                    logger.error("memService clear error ", e);
                }
            }
        }
    }
    
    @Override
    public int size()
    {
        
        return 0;
    }
    
    @Override
    public void timerClearCache()
    {
        
    }
    
}
