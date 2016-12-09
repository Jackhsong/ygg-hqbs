package com.ygg.webapp.cache.memcache;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;

import com.ygg.webapp.exception.CacheException;




public class MemcacheServiceImpl implements CacheServiceIF
{
    
    private static Logger log = Logger.getLogger(MemcacheServiceImpl.class);
    
    private MemcachedClient mc = null;
    
    
    public MemcacheServiceImpl(String host, String port)
        throws IOException
    {
        log.info("缓存单例初始化-----------------------------");
        mc = new MemcachedClient( 
        		new BinaryConnectionFactory()
        		, AddrUtil.getAddresses("127.0.0.1:11211"));
    }
    
    @Override
    public boolean delete(String key)
        throws CacheException
    {
        try
        {
            return mc.delete(key).get();
        }
        catch (Exception e)
        {
            log.error("delete出错！");
            throw new CacheException(e);
        }
    }
    
    @Override
    public void flushAll()
    {
        mc.flush();
    }
    
    @Override
    public Object get(String key)
    {
        return mc.get(key);
    }
    
    @Override
    public Integer getInt(String key)
    {
        return mc.get(key) != null ? (Integer)mc.get(key) : null;
    }
    
    @Override
    public Long getLong(String key)
    {
        return mc.get(key) != null ? (Long)mc.get(key) : null;
    }
    
    @Override
    public String getString(String key)
    {
        return mc.get(key) != null ? (String)mc.get(key) : null;
    }
    
    @Override
    public void set(String key, Object value, int expire)
    {
        if (value != null)
        {
            try
            {
                mc.set(key, expire, value).get();
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public int size()
    {
        throw new UnsupportedOperationException("MemcacheServiceImpl不支持该方法!");
    }
    
    @Override
    public Set<String> keys()
    {
        throw new UnsupportedOperationException("MemcacheServiceImpl不支持该方法!");
    }
    
}
