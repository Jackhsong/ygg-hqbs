package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SellerEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String sellerName = "";
    
    private byte sellerType;
    
    private String companyName;
    
    private String companyAddress;
    
    private String contactPerson;
    
    private String contactPhone;
    
    private String qq;
    
    private String email;
    
    private String sendAddress = "";
    
    private byte isAvailable;
    
    private String createTime;
    
    /**是否是笨鸟海淘发货*/
    private byte isBirdex;
    
    /**周末发货类型*/
    private byte isSendWeekend;
    
    /**商家发货时效*/
    private byte sendTimeType;
    
    /**保税区物流单号类型*/
    private int bondedNumType;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getSellerName()
    {
        return sellerName;
    }
    
    public void setSellerName(String sellerName)
    {
        this.sellerName = sellerName;
    }
    
    public byte getSellerType()
    {
        return sellerType;
    }
    
    public void setSellerType(byte sellerType)
    {
        this.sellerType = sellerType;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    public String getCompanyAddress()
    {
        return companyAddress;
    }
    
    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
    }
    
    public String getContactPerson()
    {
        return contactPerson;
    }
    
    public void setContactPerson(String contactPerson)
    {
        this.contactPerson = contactPerson;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getQq()
    {
        return qq;
    }
    
    public void setQq(String qq)
    {
        this.qq = qq;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getSendAddress()
    {
        if (sendAddress == null)
            sendAddress = "";
        return sendAddress;
    }
    
    public void setSendAddress(String sendAddress)
    {
        this.sendAddress = sendAddress;
    }
    
    public byte getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(byte isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public byte getIsBirdex()
    {
        return isBirdex;
    }
    
    public void setIsBirdex(byte isBirdex)
    {
        this.isBirdex = isBirdex;
    }
    
    public byte getIsSendWeekend()
    {
        return isSendWeekend;
    }
    
    public void setIsSendWeekend(byte isSendWeekend)
    {
        this.isSendWeekend = isSendWeekend;
    }
    
    public byte getSendTimeType()
    {
        return sendTimeType;
    }
    
    public void setSendTimeType(byte sendTimeType)
    {
        this.sendTimeType = sendTimeType;
    }

    public int getBondedNumType()
    {
        return bondedNumType;
    }

    public void setBondedNumType(int bondedNumType)
    {
        this.bondedNumType = bondedNumType;
    }
    
}
