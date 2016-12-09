package com.ygg.webapp.view;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PageTime implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    /**
     * 请求的url
     */
    private String requestUrl;
    
    /**
     * 调用的次数
     */
    private long times = 0;
    
    /**
     * 总共的调用时间 ms
     */
    private long allCostTimes = 0;
    
    /**
     * 平均调用时间
     */
    private float averCostTime = 0.0f;
    
    /**
     * 每次请求的开始时间
     */
    private long beginTime = 0;
    
    public PageTime(String requestUrl, long beginTime)
    {
        this.requestUrl = requestUrl;
        // this.allCostTimes += allCostTimes;
        ++this.times;
        this.beginTime = beginTime;
    }
    
    public PageTime merge(PageTime pt)
    {
        if (pt == null)
            return this;
        this.times += pt.getTimes();
        this.allCostTimes += pt.getAllCostTimes();
        
        return this;
    }
    
    public long getBeginTime()
    {
        return this.beginTime;
    }
    
    public void setBeginTime(long beginTime)
    {
        this.beginTime = beginTime;
    }
    
    public String getRequestUrl()
    {
        return requestUrl;
    }
    
    public void setRequestUrl(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }
    
    public long getTimes()
    {
        return times;
    }
    
    public void setTimes(long times)
    {
        this.times += times;
    }
    
    public long getAllCostTimes()
    {
        return allCostTimes;
    }
    
    public void setAllCostTimes(long allCostTimes)
    {
        this.allCostTimes += allCostTimes;
    }
    
    public float getAverCostTime()
    {
        averCostTime = (float)this.allCostTimes / this.times;
        return this.averCostTime;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public String toString()
    {
        return "requestUrl is:" + this.requestUrl + " times is:" + times + " allCostTimes is:" + this.allCostTimes + " averCostTime is:"
            + this.getAverCostTime();
    }
}
