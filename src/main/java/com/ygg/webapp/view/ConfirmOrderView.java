package com.ygg.webapp.view;

import java.util.List;

public class ConfirmOrderView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private List<OrderProductView> orderDetailList;
    
    private List<FreightView> freights;
    
    private String sellerName;
    
    private String sendAddress;
    
    private String totalPrice; // 不包括运费
    
    private String allTotalPrice; // 包括运费
    
    private String logisticsMoney; // 这个订单的邮费
    
    private String ismailbag = "1"; // 是否包邮 , 1 不包邮 0 包邮
    
    private String tempFlag = "0";
    
    public List<OrderProductView> getOrderDetailList()
    {
        return orderDetailList;
    }
    
    public void setOrderDetailList(List<OrderProductView> orderDetailList)
    {
        this.orderDetailList = orderDetailList;
    }
    
    public List<FreightView> getFreights()
    {
        return freights;
    }
    
    public void setFreights(List<FreightView> freights)
    {
        this.freights = freights;
    }
    
    public String getSellerName()
    {
        return sellerName;
    }
    
    public void setSellerName(String sellerName)
    {
        this.sellerName = sellerName;
    }
    
    public String getSendAddress()
    {
        return sendAddress;
    }
    
    public void setSendAddress(String sendAddress)
    {
        this.sendAddress = sendAddress;
    }
    
    public String getTotalPrice()
    {
        return totalPrice;
    }
    
    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }
    
    public String getAllTotalPrice()
    {
        return allTotalPrice;
    }
    
    public void setAllTotalPrice(String allTotalPrice)
    {
        this.allTotalPrice = allTotalPrice;
    }
    
    public String getLogisticsMoney()
    {
        return logisticsMoney;
    }
    
    public void setLogisticsMoney(String logisticsMoney)
    {
        this.logisticsMoney = logisticsMoney;
    }
    
    public String getIsmailbag()
    {
        return ismailbag;
    }
    
    public void setIsmailbag(String ismailbag)
    {
        this.ismailbag = ismailbag;
    }
    
    public String getTempFlag()
    {
        return tempFlag;
    }
    
    public void setTempFlag(String tempFlag)
    {
        this.tempFlag = tempFlag;
    }
    
}
