package com.ygg.webapp.view;

public class TempShoppingCartView extends BaseView
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int tempAccountId;
    
    private int productId;
    
    private int productCount;
    
    private byte status;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getTempAccountId()
    {
        return tempAccountId;
    }
    
    public void setTempAccountId(int tempAccountId)
    {
        this.tempAccountId = tempAccountId;
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
    
    public byte getStatus()
    {
        return status;
    }
    
    public void setStatus(byte status)
    {
        this.status = status;
    }
    
}
