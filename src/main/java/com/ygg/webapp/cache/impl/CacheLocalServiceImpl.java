package com.ygg.webapp.cache.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ygg.webapp.cache.CacheServiceIF;

/**
 * 本地缓存 spring　默认为单例，可以全局共享使用一个实例 不需要事务处理
 * 
 * @author lihc
 *
 */
@Service("cacheService")
public class CacheLocalServiceImpl implements CacheServiceIF
{
    
    /**
     * 还得考虑cache的容量大小 ConcurrentHashMap 对于value为null时会报空指针　
     *
     */
    private Map<String, Object> caches = Collections.synchronizedMap(new HashMap<String, Object>()); // new
                                                                                                     // ConcurrentHashMap<String,Object>();
    
    /**
     * 存入,更新key时的当前时间　
     */
    private Map<String, Long> cacheDate = new ConcurrentHashMap<String, Long>();
    
    /**
     * pExpire　过期时间，现以秒来计算，<=0时暂定为永久，需要手动刷新
     */
    private Map<String, Long> cacheIntervalTime = new ConcurrentHashMap<String, Long>();
    
    @Override
    public void timerClearCache()
    {
        
        for (Map.Entry<String, Long> entry : cacheIntervalTime.entrySet())
        {
            String key = entry.getKey();
            long intervalTime = entry.getValue();
            
            if (intervalTime > 0 && caches != null && !caches.isEmpty() && caches.containsKey(key))
            {
                long cacheDate = (this.cacheDate.containsKey(key) ? this.cacheDate.get(key) : 0); // 存入时的时间
                if (cacheDate > 0 && (System.currentTimeMillis() - cacheDate) / 1000 > intervalTime)
                {
                    // 过期
                    this.caches.remove(key);
                    this.cacheDate.remove(key);
                    this.cacheIntervalTime.remove(key);
                }
            }
        }
        
    }
    
    @Override
    public <T> void addCache(String k, T v, int pExpire)
    {
        // synchronized (k) {
        if (this.isInCache(k))
        {
            deleteCache(k);
        }
        putCache(k, v, pExpire);
        // }
    }
    
    @Override
    public <T> void updateCache(String k, T v, int pExpire)
    {
        // synchronized (k) {
        if (isInCache(k))
        {
            deleteCache(k);
            
            putCache(k, v, pExpire);
            
        }
        // }
    }
    
    private <T> void putCache(String k, T v, int pExpire)
    {
        this.caches.put(k, v);
        this.cacheDate.put(k, System.currentTimeMillis());
        this.cacheIntervalTime.put(k, new Long(pExpire));
    }
    
    private void deleteCache(String k)
    {
        this.caches.remove(k);
        this.cacheDate.remove(k);
        this.cacheIntervalTime.remove(k);
    }
    
    @Override
    public void clearCache(String k)
    {
        // synchronized (k) {
        if (isInCache(k))
        {
            // this.caches.remove(k) ;
            deleteCache(k);
        }
        // }
        
    }
    
    /**
     * 利用延时删除的策略，取数据时，查看是否在过期时间内
     *
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCache(String k)
    {
        // 1.判断时间是否过期
        long intervalTime = (cacheIntervalTime.containsKey(k) ? this.cacheIntervalTime.get(k) : 0); // 0 不过期
        long cacheDate = (this.cacheDate.containsKey(k) ? this.cacheDate.get(k) : 0); // 存入时的时间
        
        if (intervalTime > 0)
        {
            if ((System.currentTimeMillis() - cacheDate) / 1000 > intervalTime)
            {
                // 过期
                this.clearCache(k);
                return null;
            }
            else
            {
                if (isInCache(k))
                {
                    Object obj = this.caches.get(k);
                    return (obj != null ? (T)this.caches.get(k) : null);
                }
            }
        }
        
        if (isInCache(k))
        {
            Object obj = this.caches.get(k);
            return (obj != null ? (T)this.caches.get(k) : null);
        }
        return null;
    }
    
    @Override
    public boolean isInCache(String k)
    {
        if (this.caches.containsKey(k))
            return true;
        return false;
    }
    
    @Override
    public void clear()
    {
        this.caches.clear();
        this.cacheDate.clear();
        this.cacheIntervalTime.clear();
    }
    
    @Override
    public int size()
    {
        if (caches != null && caches.size() > 0)
            return this.caches.size();
        return 0;
    }
    
}
