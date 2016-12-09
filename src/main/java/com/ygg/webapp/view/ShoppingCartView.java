package com.ygg.webapp.view;

public class ShoppingCartView extends BaseView
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String accountId;
    
    private String productId;
    
    private int productCount;
    
    private byte status;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
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
    
    public byte getStatus()
    {
        return status;
    }
    
    public void setStatus(byte status)
    {
        this.status = status;
    }
    
    @Override
    public String toString()
    {
        return this.id + "_";
    }
}
