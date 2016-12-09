package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderConfirmProductEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int orderConfirmId;
    
    private int productId;
    
    private short productCount;
    
    public int getId()
    {
        return id;
    }
    
    public int getOrderConfirmId()
    {
        return orderConfirmId;
    }
    
    public void setOrderConfirmId(int order_confirm_id)
    {
        this.orderConfirmId = order_confirm_id;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int product_id)
    {
        this.productId = product_id;
    }
    
    public short getProductCount()
    {
        return productCount;
    }
    
    public void setProductCount(short product_count)
    {
        this.productCount = product_count;
    }
    
    public void setId(int id)
    {
        this.id = id;
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
