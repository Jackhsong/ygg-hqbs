package com.ygg.webapp.view;

public class ActivitiesProductView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String productId;
    
    private String image;
    
    private String name;
    
    private String highPrice;
    
    private String lowPrice;
    
    private String sellCount; // 已卖多少件从product_count中取
    
    private String productCount = "0"; // 加入到购物车中的单个商品的数量,从购物车和临时购物车中去查询,默认是没有加入到购物车中
    
    private String type;
    
    private String productType;
    
    private String sellingPoint;
    
    public String getProductType()
    {
        return productType;
    }
    
    public void setProductType(String productType)
    {
        this.productType = productType;
    }
    
    public String getSellingPoint()
    {
        return sellingPoint;
    }
    
    public void setSellingPoint(String sellingPoint)
    {
        this.sellingPoint = sellingPoint;
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getHighPrice()
    {
        return highPrice;
    }
    
    public void setHighPrice(String highPrice)
    {
        this.highPrice = highPrice;
    }
    
    public String getLowPrice()
    {
        return lowPrice;
    }
    
    public void setLowPrice(String lowPrice)
    {
        this.lowPrice = lowPrice;
    }
    
    public String getSellCount()
    {
        return sellCount;
    }
    
    public void setSellCount(String sellCount)
    {
        this.sellCount = sellCount;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getProductCount()
    {
        return productCount;
    }
    
    public void setProductCount(String productCount)
    {
        this.productCount = productCount;
    }
    
}
