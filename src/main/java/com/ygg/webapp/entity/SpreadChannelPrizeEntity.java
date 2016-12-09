package com.ygg.webapp.entity;

public class SpreadChannelPrizeEntity
{
    
    /**渠道奖品id*/
    private int id;
    
    /**推广渠道id*/
    private int spreadChannelId;
    
    /**优惠券id*/
    private int couponId;
    
    /**优惠券数量*/
    private int couponAmount;
    
    /**优惠券有效时间,1：使用原优惠券时间，2：发放日顺延N天*/
    private int validityDateType;
    
    /**领券之日起，有效天数。当validity_days_type=2时使用，使用该字段*/
    private int days;
    
    /**创建时间*/
    private String createTime;
    
    /**更新时间*/
    private String updateTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getSpreadChannelId()
    {
        return spreadChannelId;
    }
    
    public void setSpreadChannelId(int spreadChannelId)
    {
        this.spreadChannelId = spreadChannelId;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
    }
    
    public int getCouponAmount()
    {
        return couponAmount;
    }
    
    public void setCouponAmount(int couponAmount)
    {
        this.couponAmount = couponAmount;
    }
    
    public int getValidityDateType()
    {
        return validityDateType;
    }
    
    public void setValidityDateType(int validityDateType)
    {
        this.validityDateType = validityDateType;
    }
    
    public int getDays()
    {
        return days;
    }
    
    public void setDays(int days)
    {
        this.days = days;
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
    
}
