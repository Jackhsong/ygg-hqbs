package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DistrictEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int cityId;
    
    private int districtId;
    
    private String name;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getCityId()
    {
        return cityId;
    }
    
    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }
    
    public int getDistrictId()
    {
        return districtId;
    }
    
    public void setDistrictId(int districtId)
    {
        this.districtId = districtId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
}
