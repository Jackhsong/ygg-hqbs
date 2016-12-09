package com.ygg.webapp.entity;

public class GatePrizeEntity
{
    /**任意门奖品id*/
    private int id;
    
    /**任意门id*/
    private int gateId;
    
    /**优惠券id*/
    private int couponId;
    
    /**优惠券有效时间,1：使用原优惠券时间，2：发放日顺延N天*/
    private int validTimeType;
    
    /**领券之日起，有效天数。当valid_time_type=2时使用，使用该字段*/
    private int days;
    
    /**创建时间*/
    private String createTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getGateId()
    {
        return gateId;
    }
    
    public void setGateId(int gateId)
    {
        this.gateId = gateId;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
    }
    
    public int getValidTimeType()
    {
        return validTimeType;
    }
    
    public void setValidTimeType(int validTimeType)
    {
        this.validTimeType = validTimeType;
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
}
