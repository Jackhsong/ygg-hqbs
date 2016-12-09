package com.ygg.webapp.view;

import java.util.List;

import com.ygg.webapp.util.CommonUtil;

public class OrderProductView extends BaseView implements Comparable<OrderProductView>
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String productId;
    
    private String image;
    
    private String shortName;
    
    private String count;
    
    private String salesPrice;
    
    private String status;
    
    private String stockCount;
    
    private String restriction;
    
    private String updateTime;
    
    /**order_produce表的id*/
    private String orderProductId;
    
    /**退款的状态*/
    private String refundStatus; 
    
    /**order_product_refund表id*/
    private String orderProductRefundId;
    
    /**order_product_refund表type*/
    private String type;
    
    public String getOrderProductId()
    {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }

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
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getStockCount()
    {
        return stockCount;
    }
    
    public void setStockCount(String stockCount)
    {
        this.stockCount = stockCount;
    }
    
    public String getRestriction()
    {
        return restriction;
    }
    
    public void setRestriction(String restriction)
    {
        this.restriction = restriction;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getRefundStatus()
    {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }

    public String getOrderProductRefundId()
    {
        return orderProductRefundId;
    }

    public void setOrderProductRefundId(String orderProductRefundId)
    {
        this.orderProductRefundId = orderProductRefundId;
    }
    
    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public int compareTo(OrderProductView o)
    {
        if (this.updateTime == null || this.updateTime.equals(""))
            return 0;
        if (o.getUpdateTime() == null || o.getUpdateTime().equals(""))
            return 0;
        return CommonUtil.string2Date(this.updateTime, "yyyy-MM-dd HH:mm:ss").before(CommonUtil.string2Date(o.getUpdateTime(), "yyyy-MM-dd HH:mm:ss")) ? 1 : -1;
    }
    
}
