package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class OrderEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String number;

    private String batchNumber = "0";

    private String sameBatchNumber = "0";
    
    private int accountId;
    
    private int receiveAddressId;
    
    private float totalPrice;
    
    private String remark;
    
    private byte status;
    
    private byte payChannel;
    
    private String createTime;
    
    private String payTime;
    
    private String sendTime;
    
    private String receiveTime;
    
    private short freightMoney;
    
    private int sellerId;
    
    private int orderSellerId;
    
    private int appChannel;
    
    private int sourceChannelId = 0;
    
    private int accountPoint;
    
    private int accountCouponId;
    
    private float couponPrice;
    
    private float realPrice;

    private int isMemberOrder;

    private String appVersion;

    private byte checkStatus;
    
     /**左岸城堡佣金 */
    private float hqbsDraw;

    public byte getCheckStatus()
    {
        return checkStatus;
    }

    public void setCheckStatus(byte checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    public String getSameBatchNumber()
    {
        return sameBatchNumber;
    }

    public void setSameBatchNumber(String sameBatchNumber)
    {
        this.sameBatchNumber = sameBatchNumber;
    }

    public String getAppVersion()
    {
        return appVersion;
    }

    public void setAppVersion(String appVersion)
    {
        this.appVersion = appVersion;
    }

    public int getIsMemberOrder()
    {
        return isMemberOrder;
    }

    public void setIsMemberOrder(int isMemberOrder)
    {
        this.isMemberOrder = isMemberOrder;
    }

    public String getBatchNumber()
    {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber)
    {
        this.batchNumber = batchNumber;
    }

    public short getFreightMoney()
    {
        return freightMoney;
    }
    
    public void setFreightMoney(short freightMoney)
    {
        this.freightMoney = freightMoney;
    }
    
    public int getSellerId()
    {
        return sellerId;
    }
    
    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
    }
    
    public int getOrderSellerId()
    {
        return orderSellerId;
    }
    
    public void setOrderSellerId(int orderSellerId)
    {
        this.orderSellerId = orderSellerId;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getNumber()
    {
        return number;
    }
    
    public void setNumber(String number)
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
    
    public int getReceiveAddressId()
    {
        return receiveAddressId;
    }
    
    public void setReceiveAddressId(int receiveAddressId)
    {
        this.receiveAddressId = receiveAddressId;
    }
    
    public float getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setTotalPrice(float totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
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
    
    public String getPayTime()
    {
        return payTime;
    }
    
    public void setPayTime(String payTime)
    {
        this.payTime = payTime;
    }
    
    public String getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }
    
    public String getReceiveTime()
    {
        return receiveTime;
    }
    
    public void setReceiveTime(String receiveTime)
    {
        this.receiveTime = receiveTime;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    public byte getPayChannel()
    {
        return payChannel;
    }
    
    public void setPayChannel(byte payChannel)
    {
        this.payChannel = payChannel;
    }
    
    public int getAppChannel()
    {
        return appChannel;
    }
    
    public void setAppChannel(int appChannel)
    {
        this.appChannel = appChannel;
    }
    
    public int getSourceChannelId()
    {
        return sourceChannelId;
    }
    
    public void setSourceChannelId(int sourceChannelId)
    {
        this.sourceChannelId = sourceChannelId;
    }
    
    public int getAccountPoint()
    {
        return accountPoint;
    }
    
    public void setAccountPoint(int accountPoint)
    {
        this.accountPoint = accountPoint;
    }
    
    public int getAccountCouponId()
    {
        return accountCouponId;
    }
    
    public void setAccountCouponId(int accountCouponId)
    {
        this.accountCouponId = accountCouponId;
    }
    
    public float getCouponPrice()
    {
        return couponPrice;
    }
    
    public void setCouponPrice(float couponPrice)
    {
        this.couponPrice = couponPrice;
    }
    
    public float getRealPrice()
    {
        return realPrice;
    }
    
    public void setRealPrice(float realPrice)
    {
        this.realPrice = realPrice;
    }

    
    /**  
     *@return  the hqbsDraw
     */
    public float getHqbsDraw()
    {
        return hqbsDraw;
    }

    
    /** 
     * @param hqbsDraw the hqbsDraw to set
     */
    public void setHqbsDraw(float hqbsDraw)
    {
        this.hqbsDraw = hqbsDraw;
    }
}
