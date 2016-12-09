package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderConfirmEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private long number;
    
    private int accountId;
    
    private int tempAccountId;
    
    private int freightTemplateId;
    
    private float totalPrice;
    
    private String createTime;
    
    private byte isValid;
    
    private int sellerId;
    
    public int getSellerId()
    {
        return sellerId;
    }
    
    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public long getNumber()
    {
        return number;
    }
    
    public void setNumber(long number)
    {
        this.number = number;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public int getTempAccountId()
    {
        return tempAccountId;
    }
    
    public void setTempAccountId(int tempAccountId)
    {
        this.tempAccountId = tempAccountId;
    }
    
    public int getFreightTemplateId()
    {
        return freightTemplateId;
    }
    
    public void setFreightTemplateId(int freightTemplateId)
    {
        this.freightTemplateId = freightTemplateId;
    }
    
    public float getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public byte getIsValid()
    {
        return isValid;
    }
    
    public void setIsValid(byte isValid)
    {
        this.isValid = isValid;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString()
    {
        return "id:" + id + "number:" + number + "totalPrice:" + totalPrice;
    }
}
