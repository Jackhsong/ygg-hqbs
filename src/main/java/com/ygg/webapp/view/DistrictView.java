package com.ygg.webapp.view;

import java.io.Serializable;

public class DistrictView implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private String districtId;
    
    private String cityId;
    
    private String name;
    
    public String getDistrictId()
    {
        return districtId;
    }
    
    public void setDistrictId(String districtId)
    {
        this.districtId = districtId;
    }
    
    public String getCityId()
    {
        return cityId;
    }
    
    public void setCityId(String cityId)
    {
        this.cityId = cityId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
