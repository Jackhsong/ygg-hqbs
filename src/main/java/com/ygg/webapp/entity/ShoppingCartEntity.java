package com.ygg.webapp.entity;

public class ShoppingCartEntity extends BaseEntity implements Comparable<ShoppingCartEntity>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountId;
    
    private int productId;
    
    private short productCount;
    
    private byte status;
    
    private String createTime;
    
    private String updateTime;
    
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
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
    
    public short getProductCount()
    {
        return productCount;
    }
    
    public void setProductCount(short productCount)
    {
        this.productCount = productCount;
    }
    
    public byte getStatus()
    {
        return status;
    }
    
    public void setStatus(byte status)
    {
        this.status = status;
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
    
    @Override
    public int compareTo(ShoppingCartEntity sce)
    {
        return this.productId - sce.getProductId();
    }
    
}
