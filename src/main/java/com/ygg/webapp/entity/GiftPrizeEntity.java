package com.ygg.webapp.entity;

public class GiftPrizeEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int giftActivityId;
    
    private int num;
    
    private int couponId;
    
    private int drawWay;
    
    private String createTime = "";
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getGiftActivityId()
    {
        return giftActivityId;
    }
    
    public void setGiftActivityId(int giftActivityId)
    {
        this.giftActivityId = giftActivityId;
    }
    
    public int getNum()
    {
        return num;
    }
    
    public void setNum(int num)
    {
        this.num = num;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
    }
    
    public int getDrawWay()
    {
        return drawWay;
    }
    
    public void setDrawWay(int drawWay)
    {
        this.drawWay = drawWay;
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
