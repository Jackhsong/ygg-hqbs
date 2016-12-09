package com.ygg.webapp.entity;

public class AccountAvailablePointRecordEntity extends BaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountId;
    
    private int operatePoint;
    
    private int totalPoint;
    
    private byte operateType;
    
    private byte arithmeticType;
    
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
    
    public int getOperatePoint()
    {
        return operatePoint;
    }
    
    public void setOperatePoint(int operatePoint)
    {
        this.operatePoint = operatePoint;
    }
    
    public int getTotalPoint()
    {
        return totalPoint;
    }
    
    public void setTotalPoint(int totalPoint)
    {
        this.totalPoint = totalPoint;
    }
    
    public byte getOperateType()
    {
        return operateType;
    }
    
    public void setOperateType(byte operateType)
    {
        this.operateType = operateType;
    }
    
    public byte getArithmeticType()
    {
        return arithmeticType;
    }
    
    public void setArithmeticType(byte arithmeticType)
    {
        this.arithmeticType = arithmeticType;
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
