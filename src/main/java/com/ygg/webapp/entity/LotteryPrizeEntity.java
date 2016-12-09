package com.ygg.webapp.entity;

public class LotteryPrizeEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int lotteryActivityId;

    private int num;
    
    private int probability;
    
    private int type;
    
    private int couponId;
    
    private int point;
    
    private String createTime = "";
    
    private String updateTime = "";
    
    private int validityDaysType;
    
    private int days;

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
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
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getLotteryActivityId()
    {
        return lotteryActivityId;
    }
    
    public void setLotteryActivityId(int lotteryActivityId)
    {
        this.lotteryActivityId = lotteryActivityId;
    }
    
    public int getProbability()
    {
        return probability;
    }
    
    public void setProbability(int probability)
    {
        this.probability = probability;
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
    
    public int getPoint()
    {
        return point;
    }
    
    public void setPoint(int point)
    {
        this.point = point;
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
