package com.ygg.webapp.entity;

public class GamePrizeEntity
{
    /**游戏奖品id*/
    private int id;
    
    /**游戏id*/
    private int gameId;
    
    /**优惠券id*/
    private int couponId;
    
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
    
    public int getGameId()
    {
        return gameId;
    }
    
    public void setGameId(int gameId)
    {
        this.gameId = gameId;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
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
