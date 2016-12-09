package com.ygg.webapp.entity;

public class OrderUnionPayEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int orderId;
    
    private String payMark;
    
    private String payTid;
    
    private byte isPay;
    
    private String createTime;
    
    private String updateTime;
    
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
    
    public String getPayMark()
    {
        return payMark;
    }
    
    public void setPayMark(String payMark)
    {
        this.payMark = payMark;
    }
    
    public String getPayTid()
    {
        return payTid;
    }
    
    public void setPayTid(String payTid)
    {
        this.payTid = payTid;
    }
    
    public byte getIsPay()
    {
        return isPay;
    }
    
    public void setIsPay(byte isPay)
    {
        this.isPay = isPay;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
}
