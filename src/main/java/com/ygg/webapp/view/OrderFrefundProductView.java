package com.ygg.webapp.view;

public class OrderFrefundProductView extends BaseView
{
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    public String productId;
    
    public String image;
    
    public String shortName;
    
    public String count;
    
    public String salesPrice;
    
    public String orderProductRefundId;
    
    public String refundStatus; // (退款的状态)
    
    public String refundMoney; // 在退款进度查询中有值
    
    public String type; // 退款类型
    
    public String orderProductId;
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public String getShortName()
    {
        return shortName;
    }
    
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    public String getCount()
    {
        return count;
    }
    
    public void setCount(String count)
    {
        this.count = count;
    }
    
    public String getSalesPrice()
    {
        return salesPrice;
    }
    
    public void setSalesPrice(String salesPrice)
    {
        this.salesPrice = salesPrice;
    }
    
    public String getOrderProductRefundId()
    {
        return orderProductRefundId;
    }
    
    public void setOrderProductRefundId(String orderProductRefundId)
    {
        this.orderProductRefundId = orderProductRefundId;
    }
    
    public String getRefundStatus()
    {
        return refundStatus;
    }
    
    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    
    public String getRefundMoney()
    {
        return refundMoney;
    }
    
    public void setRefundMoney(String refundMoney)
    {
        this.refundMoney = refundMoney;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
}
