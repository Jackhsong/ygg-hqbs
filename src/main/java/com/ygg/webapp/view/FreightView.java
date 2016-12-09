package com.ygg.webapp.view;

import java.util.List;

public class FreightView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String logisticsMoney;
    
    private List<String> provinceIds;
    
    public String getLogisticsMoney()
    {
        return logisticsMoney;
    }
    
    public void setLogisticsMoney(String logisticsMoney)
    {
        this.logisticsMoney = logisticsMoney;
    }
    
    public List<String> getProvinceIds()
    {
        return provinceIds;
    }
    
    public void setProvinceIds(List<String> provinceIds)
    {
        this.provinceIds = provinceIds;
    }
    
}
