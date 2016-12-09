package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ActivitiesCommonEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
    private String image;
    
    private String desc;
    private String detail;
    
    private boolean isAvailable;
    
    private String wxShareTitle;
    
    private String gegesay;
    
    private int type;
    
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    public String getDetail()
    {
        return detail;
    }
    
    public void setDetail(String detail)
    {
        this.detail = detail;
    }    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public boolean isAvailable()
    {
        return isAvailable;
    }
    
    public void setAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public String getWxShareTitle()
    {
        return wxShareTitle;
    }
    
    public void setWxShareTitle(String wxShareTitle)
    {
        this.wxShareTitle = wxShareTitle;
    }
    
    public String getGegesay()
    {
        return gegesay;
    }
    
    public void setGegesay(String gegesay)
    {
        this.gegesay = gegesay;
    }
    
}
