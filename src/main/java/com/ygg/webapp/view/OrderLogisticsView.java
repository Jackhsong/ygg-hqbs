package com.ygg.webapp.view;

public class OrderLogisticsView extends BaseView
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int orderId;
    
    private String channel;
    
    private String number;
    
    private short money;
    
    private String status;
    
    /**快递电话*/
    private String telePhone;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
    
    public String getNumber()
    {
        return number;
    }
    
    public void setNumber(String number)
    {
        this.number = number;
    }
    
    public short getMoney()
    {
        return money;
    }
    
    public void setMoney(short money)
    {
        this.money = money;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getChannel()
    {
        return channel;
    }
    
    public void setChannel(String channel)
    {
        this.channel = channel;
    }
    
    public String getTelePhone()
    {
        return telePhone;
    }
    
    public void setTelePhone(String telePhone)
    {
        this.telePhone = telePhone;
    }
    
}
