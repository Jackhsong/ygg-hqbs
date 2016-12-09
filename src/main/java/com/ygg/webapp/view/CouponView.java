package com.ygg.webapp.view;

public class CouponView extends BaseView
{

    private String id;

    private String type;

    private String startTime;

    private String endTime;

    private String thresholdPrice;

    private String reducePrice;

    private String isAvailable; // 是否到使用开始时间

    private String scope;

    private String selected = "0";

    public String getSelected()
    {
        return selected;
    }

    public void setSelected(String selected)
    {
        this.selected = selected;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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

    public String getThresholdPrice()
    {
        return thresholdPrice;
    }

    public void setThresholdPrice(String thresholdPrice)
    {
        this.thresholdPrice = thresholdPrice;
    }

    public String getReducePrice()
    {
        return reducePrice;
    }

    public void setReducePrice(String reducePrice)
    {
        this.reducePrice = reducePrice;
    }

    public String getIsAvailable()
    {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable)
    {
        this.isAvailable = isAvailable;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof CouponView)
        {
            CouponView other = (CouponView)obj;
            return this.id == other.id;
        }
        return false;
    }
}
