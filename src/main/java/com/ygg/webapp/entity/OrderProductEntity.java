package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderProductEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int productId;
    
    private int orderId;
    
    private int productCount;
    
    private String smallImage;
    
    private String mediumImage;
    
    private String shortName;
    
    private float salesPrice;
    
    private float partnerDistributionPrice;
    
    private float cost;
    
    public float getCost()
    {
        return cost;
    }
    
    public void setCost(float cost)
    {
        this.cost = cost;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
    
    public int getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
    
    public int getProductCount()
    {
        return productCount;
    }
    
    public void setProductCount(int productCount)
    {
        this.productCount = productCount;
    }
    
    public String getSmallImage()
    {
        return smallImage;
    }
    
    public void setSmallImage(String smallImage)
    {
        this.smallImage = smallImage;
    }
    
    public String getShortName()
    {
        return shortName;
    }
    
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    public float getSalesPrice()
    {
        return salesPrice;
    }
    
    public void setSalesPrice(float salesPrice)
    {
        this.salesPrice = salesPrice;
    }
    
    public float getPartnerDistributionPrice()
    {
        return partnerDistributionPrice;
    }
    
    public void setPartnerDistributionPrice(float partnerDistributionPrice)
    {
        this.partnerDistributionPrice = partnerDistributionPrice;
    }
    
    public String getMediumImage()
    {
        return mediumImage;
    }
    
    public void setMediumImage(String mediumImage)
    {
        this.mediumImage = mediumImage;
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
