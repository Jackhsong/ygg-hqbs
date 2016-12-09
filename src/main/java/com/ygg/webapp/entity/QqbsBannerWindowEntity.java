package com.ygg.webapp.entity;

import com.ygg.webapp.entity.base.BaseEntity;

public class QqbsBannerWindowEntity extends BaseEntity
{
    
    
    private short saleTimeType;
    
    private short type;
    
    private int displayId;
    
    private String desc;
    
    private String image;
    
    private String startTime;
    
    private String endTime;
    
    private short isDisplay;
    
    private short order;
    
    /**添加网页链接*/
    private String url;
    
    
    public short getSaleTimeType()
    {
        return saleTimeType;
    }
    
    public void setSaleTimeType(short saleTimeType)
    {
        this.saleTimeType = saleTimeType;
    }
    
    public short getType()
    {
        return type;
    }
    
    public void setType(short type)
    {
        this.type = type;
    }
    
    public int getDisplayId()
    {
        return displayId;
    }
    
    public void setDisplayId(int displayId)
    {
        this.displayId = displayId;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public short getIsDisplay()
    {
        return isDisplay;
    }
    
    public void setIsDisplay(short isDisplay)
    {
        this.isDisplay = isDisplay;
    }
    
    public short getOrder()
    {
        return order;
    }
    
    public void setOrder(short order)
    {
        this.order = order;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
    
    
}
