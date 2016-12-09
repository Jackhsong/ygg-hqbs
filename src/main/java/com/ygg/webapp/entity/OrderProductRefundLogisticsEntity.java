package com.ygg.webapp.entity;

/**
 * 
 * @author lihc
 *
 */
public class OrderProductRefundLogisticsEntity extends BaseEntity
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int orderProductRefundId;
    
    private String channel;
    
    private String number;
    
    private int money;
    
    private byte is_receive;
    
    private byte status;
    
    private String createTime;
    
    private byte isSubscribed;
    
    private byte subscribeCount;
    
    private byte isFake;
    
    private String trackInfo;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getOrderProductRefundId()
    {
        return orderProductRefundId;
    }
    
    public void setOrderProductRefundId(int orderProductRefundId)
    {
        this.orderProductRefundId = orderProductRefundId;
    }
    
    public String getChannel()
    {
        return channel;
    }
    
    public void setChannel(String channel)
    {
        this.channel = channel;
    }
    
    public String getNumber()
    {
        return number;
    }
    
    public void setNumber(String number)
    {
        this.number = number;
    }
    
    public int getMoney()
    {
        return money;
    }
    
    public void setMoney(int money)
    {
        this.money = money;
    }
    
    public byte getIs_receive()
    {
        return is_receive;
    }
    
    public void setIs_receive(byte is_receive)
    {
        this.is_receive = is_receive;
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
    
    public byte getIsSubscribed()
    {
        return isSubscribed;
    }
    
    public void setIsSubscribed(byte isSubscribed)
    {
        this.isSubscribed = isSubscribed;
    }
    
    public byte getSubscribeCount()
    {
        return subscribeCount;
    }
    
    public void setSubscribeCount(byte subscribeCount)
    {
        this.subscribeCount = subscribeCount;
    }
    
    public byte getIsFake()
    {
        return isFake;
    }
    
    public void setIsFake(byte isFake)
    {
        this.isFake = isFake;
    }
    
    public String getTrackInfo()
    {
        return trackInfo;
    }
    
    public void setTrackInfo(String trackInfo)
    {
        this.trackInfo = trackInfo;
    }
    
}
