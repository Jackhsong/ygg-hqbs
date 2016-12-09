package com.ygg.webapp.view;

public class OrderReturnProductView extends OrderFrefundProductView
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    public String totalPrice; // (不包含邮费),
    
    public String orderNumber; // (订单编号),
    
    public String status; // (订单状态),
    
    public String operateTime; // (成交时间)
    
    public String orderId;
    
    public String getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    public String getOrderNumber()
    {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
}
