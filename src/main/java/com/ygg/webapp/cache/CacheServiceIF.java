package com.ygg.webapp.cache;

/**
 * 缓存总接口
 * 
 * @author lihc
 *
 */
public interface CacheServiceIF
{
    
    /**
     * 像Cache中加一个对象
     * 
     * @param k
     * @param v
     */
    public <T> void addCache(String k, T v, int pExpire);
    
    /**
     * 更新一个Cache中的对象
     * 
     * @param k
     * @param v
     */
    public <T> void updateCache(String k, T v, int pExpire);
    
    /**
     * 移除一个Cache中的 Key
     * 
     * @param k
     */
    public void clearCache(String k);
    
    /**
     * 从Cache中key值中查询一个Value
     * 
     * @param k
     * @return
     */
    public <T> T getCache(String k);
    
    /**
     * 判断一个Key中否在Cache 中
     * 
     * @param k
     * @return
     */
    public boolean isInCache(String k);
    
    /**
     * 清空所有的缓存
     */
    public void clear();
    
    /**
     * 查询所有cache中的size
     * 
     * @return
     */
    public int size();
    
    /**
     * 定时清空超时的cache
     */
    public void timerClearCache();
    
}
