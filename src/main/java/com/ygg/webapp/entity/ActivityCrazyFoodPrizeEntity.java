package com.ygg.webapp.entity;

public class ActivityCrazyFoodPrizeEntity
{
    private int id;
    
    /**抽奖活动id*/
    private int activityId;
    
    /**奖品类型：1，谢谢参与；2,优惠券*/
    private int type;
    
    /**当type=2时，优惠券id*/
    private int couponId;
    
    /**奖品数量,type=1时数量为0(无限制)*/
    private int prizeAmount;
    
    /**中奖概率*/
    private int probability;
    
    /**1：使用原优惠券时间，2：发放日顺延N天*/
    private int validDaysType;
    
    /**当valid_days_type=2时，使用该字段*/
    private int days;
    
    /**是否可用*/
    private int isAvailable;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getActivityId()
    {
        return activityId;
    }
    
    public void setActivityId(int activityId)
    {
        this.activityId = activityId;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
    }
    
    public int getPrizeAmount()
    {
        return prizeAmount;
    }
    
    public void setPrizeAmount(int prizeAmount)
    {
        this.prizeAmount = prizeAmount;
    }
    
    public int getProbability()
    {
        return probability;
    }
    
    public void setProbability(int probability)
    {
        this.probability = probability;
    }
    
    public int getValidDaysType()
    {
        return validDaysType;
    }
    
    public void setValidDaysType(int validDaysType)
    {
        this.validDaysType = validDaysType;
    }
    
    public int getDays()
    {
        return days;
    }
    
    public void setDays(int days)
    {
        this.days = days;
    }
    
    public int getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(int isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
}
