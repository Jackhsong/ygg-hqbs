package com.ygg.webapp.entity;

public class AccountRecommendedReturnPointEntity extends BaseEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountId;
    
    private int recommendedAccountId;
    
    private int orderId;
    
    private int point;
    
    private byte type;
    
    private byte isFirstRecommended;
    
    private String createTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public int getRecommendedAccountId()
    {
        return recommendedAccountId;
    }
    
    public void setRecommendedAccountId(int recommendedAccountId)
    {
        this.recommendedAccountId = recommendedAccountId;
    }
    
    public int getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
    
    public int getPoint()
    {
        return point;
    }
    
    public void setPoint(int point)
    {
        this.point = point;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public byte getIsFirstRecommended()
    {
        return isFirstRecommended;
    }
    
    public void setIsFirstRecommended(byte isFirstRecommended)
    {
        this.isFirstRecommended = isFirstRecommended;
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
