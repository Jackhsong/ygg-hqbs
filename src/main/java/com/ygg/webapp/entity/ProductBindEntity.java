package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProductBindEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int saleWindowId;
    
    private int ProductId;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getSaleWindowId()
    {
        return saleWindowId;
    }
    
    public void setSaleWindowId(int saleWindowId)
    {
        this.saleWindowId = saleWindowId;
    }
    
    public int getProductId()
    {
        return ProductId;
    }
    
    public void setProductId(int productId)
    {
        ProductId = productId;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
