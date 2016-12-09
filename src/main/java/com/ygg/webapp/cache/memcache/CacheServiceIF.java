package com.ygg.webapp.cache.memcache;

import java.util.Set;

import com.ygg.webapp.exception.CacheException;

public interface CacheServiceIF
{
    
    boolean delete(String key)
        throws CacheException;
    
    void flushAll();
    
    Object get(String key);
    
    Integer getInt(String key);
    
    Long getLong(String key);
    
    String getString(String key);
    
    void set(String key, Object value, int expire);
    
    int size();
    
    Set<String> keys();
    
}
