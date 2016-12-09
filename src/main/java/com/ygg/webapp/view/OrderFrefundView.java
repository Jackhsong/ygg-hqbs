package com.ygg.webapp.view;

import java.util.List;

import com.ygg.webapp.util.CommonUtil;

public class OrderFrefundView extends BaseView implements Comparable<OrderFrefundView>
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    public String orderId;
    
    public String orderNumber;
    
    public String status; // 2：待发货，3：已发货，4：交易成功
    
    public String orderProductId;
    
    public String operateTime;
    
    public String createTime;
    
    public String totalPrice; // 商品总价，包含邮费
    
    // public String refundStatus ; // (退款的状态)
    
    // public String orderProductRefundId ;
    
    public List<OrderFrefundProductView> orderDetailList;
    
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
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    public String getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    public List<OrderFrefundProductView> getOrderDetailList()
    {
        return orderDetailList;
    }
    
    public void setOrderDetailList(List<OrderFrefundProductView> orderDetailList)
    {
        this.orderDetailList = orderDetailList;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    @Override
    public int compareTo(OrderFrefundView ov)
    {
        if (this.operateTime == null || this.operateTime.equals("") || ov.getOperateTime() == null || ov.getOperateTime().equals(""))
            return 0;
        if (CommonUtil.string2Date(operateTime, "yyyy-MM-dd HH:mm:ss").equals(CommonUtil.string2Date(ov.getOperateTime(), "yyyy-MM-dd HH:mm:ss")))
        {
            return 0;
        }
        return CommonUtil.string2Date(operateTime, "yyyy-MM-dd HH:mm:ss").before(CommonUtil.string2Date(ov.getOperateTime(), "yyyy-MM-dd HH:mm:ss")) ? 1 : -1;
    }
    
}
