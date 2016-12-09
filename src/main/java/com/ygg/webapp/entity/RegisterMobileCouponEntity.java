package com.ygg.webapp.entity;

public class RegisterMobileCouponEntity
{
    private int id;
    
    private String mobileNumber;
    
    private int couponId;
    
    private int validityDaysType;
    
    private int days;
    
    private int reducePrice;
    
    private int sourceType;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
    }
    
    public int getValidityDaysType()
    {
        return validityDaysType;
    }
    
    public void setValidityDaysType(int validityDaysType)
    {
        this.validityDaysType = validityDaysType;
    }
    
    public int getDays()
    {
        return days;
    }
    
    public void setDays(int days)
    {
        this.days = days;
    }
    
    public int getReducePrice()
    {
        return reducePrice;
    }
    
    public void setReducePrice(int reducePrice)
    {
        this.reducePrice = reducePrice;
    }
    
    public int getSourceType()
    {
        return sourceType;
    }
    
    public void setSourceType(int sourceType)
    {
        this.sourceType = sourceType;
    }
    
}
