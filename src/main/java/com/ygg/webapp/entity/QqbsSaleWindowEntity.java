package com.ygg.webapp.entity;

import com.ygg.webapp.entity.base.BaseEntity;

public class QqbsSaleWindowEntity extends BaseEntity
{
    
    private short type;
    
    private int displayId;
    
    private short saleTimeType;
    
    private int saleFlagId;
    
    private String name;
    
    private String desc;
    
    private String image;
    
    private String startTime;
    
    private String endTime;
    
    private short isDisplay;
    
    private short isDisplayFlag;
    
    private short order;
    
    private String flagUrl;
    
    private String status;
    
    
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
    
    public short getSaleTimeType()
    {
        return saleTimeType;
    }
    
    public void setSaleTimeType(short saleTimeType)
    {
        this.saleTimeType = saleTimeType;
    }
    
    public int getSaleFlagId()
    {
        return saleFlagId;
    }
    
    public void setSaleFlagId(int saleFlagId)
    {
        this.saleFlagId = saleFlagId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    public short getIsDisplayFlag()
    {
        return isDisplayFlag;
    }
    
    public void setIsDisplayFlag(short isDisplayFlag)
    {
        this.isDisplayFlag = isDisplayFlag;
    }
    
    public short getOrder()
    {
        return order;
    }
    
    public void setOrder(short order)
    {
        this.order = order;
    }
    
    public String getFlagUrl()
    {
        return flagUrl;
    }
    
    public void setFlagUrl(String flagUrl)
    {
        this.flagUrl = flagUrl;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
}
