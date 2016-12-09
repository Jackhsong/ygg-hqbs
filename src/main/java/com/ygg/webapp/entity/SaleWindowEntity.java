package com.ygg.webapp.entity;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SaleWindowEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int displayId;
    
    private byte type;
    
    private String name;
    
    private String desc;
    
    private String image;
    
    private int startTime;
    
    private int endTime;
    
    private boolean isDisplay;
    
    private short nowOrder;
    
    private short laterOrder;
    
    private String createTime;
    
    private String updateTime;
    
    private int saleFlagId;
    
    private byte isDisplayFlag;

    private List<String> saleTagLabels;

    private byte saleTimeType;

    public byte getSaleTimeType()
    {
        return saleTimeType;
    }

    public void setSaleTimeType(byte saleTimeType)
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
    
    public byte getIsDisplayFlag()
    {
        return isDisplayFlag;
    }
    
    public void setIsDisplayFlag(byte isDisplayFlag)
    {
        this.isDisplayFlag = isDisplayFlag;
    }

    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getDisplayId()
    {
        return displayId;
    }
    
    public void setDisplayId(int displayId)
    {
        this.displayId = displayId;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
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
    
    public int getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }
    
    public int getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(int endTime)
    {
        this.endTime = endTime;
    }
    
    public boolean isDisplay()
    {
        return isDisplay;
    }
    
    public void setDisplay(boolean isDisplay)
    {
        this.isDisplay = isDisplay;
    }
    
    public short getNowOrder()
    {
        return nowOrder;
    }
    
    public void setNowOrder(short nowOrder)
    {
        this.nowOrder = nowOrder;
    }
    
    public short getLaterOrder()
    {
        return laterOrder;
    }
    
    public void setLaterOrder(short laterOrder)
    {
        this.laterOrder = laterOrder;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public List<String> getSaleTagLabels()
    {
        return saleTagLabels;
    }
    
    public void setSaleTagLabels(List<String> saleTagLabels)
    {
        this.saleTagLabels = saleTagLabels;
    }
    
}
