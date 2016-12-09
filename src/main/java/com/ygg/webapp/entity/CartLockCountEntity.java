package com.ygg.webapp.entity;

public class CartLockCountEntity extends BaseEntity
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountId;
    
    private int productId;
    
    private int productCount;
    
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
    
    public int getProductCount()
    {
        return productCount;
    }
    
    public void setProductCount(int productCount)
    {
        this.productCount = productCount;
    }
    
}
