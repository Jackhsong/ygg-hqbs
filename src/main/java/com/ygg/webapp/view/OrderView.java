package com.ygg.webapp.view;

import java.util.List;

import com.ygg.webapp.util.CommonUtil;

public class OrderView extends BaseView implements Comparable<OrderView>
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String orderNumber;
    
    private List<OrderProductView> orderDetailList;
    
    private String status;
    
    private String operateTime;
    
    private String endSecond;
    
    private String allTotalPrice;
    
    private String payChannel;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getOrderNumber()
    {
        return orderNumber;
    }
    
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }
    
    public List<OrderProductView> getOrderDetailList()
    {
        return orderDetailList;
    }
    
    public void setOrderDetailList(List<OrderProductView> orderDetailList)
    {
        this.orderDetailList = orderDetailList;
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
    
    public String getEndSecond()
    {
        return endSecond;
    }
    
    public void setEndSecond(String endSecond)
    {
        this.endSecond = endSecond;
    }
    
    public String getAllTotalPrice()
    {
        return allTotalPrice;
    }
    
    public void setAllTotalPrice(String allTotalPrice)
    {
        this.allTotalPrice = allTotalPrice;
    }
    
    public String getPayChannel()
    {
        return payChannel;
    }
    
    public void setPayChannel(String payChannel)
    {
        this.payChannel = payChannel;
    }
    
    public int compareTo(OrderView ov)
    {
        if (CommonUtil.string2Date(operateTime, "yyyy-MM-dd HH:mm:ss").equals(CommonUtil.string2Date(ov.getOperateTime(), "yyyy-MM-dd HH:mm:ss")))
        {
            return 0;
        }
        return CommonUtil.string2Date(operateTime, "yyyy-MM-dd HH:mm:ss").before(CommonUtil.string2Date(ov.getOperateTime(), "yyyy-MM-dd HH:mm:ss")) ? 1 : -1;
    }
    
}
