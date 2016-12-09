package com.ygg.webapp.view;

import java.io.Serializable;

public class ProvinceView implements Serializable
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private String provinceId;
    
    private String name;
    
    public String getProvinceId()
    {
        return provinceId;
    }
    
    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
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
