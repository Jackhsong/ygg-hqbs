package com.ygg.webapp.entity.brand;

public class QqbsBrandEntity
{
    
    /**品牌栏目ID*/
    private int ctgId;
    
    /**品牌ID 来自brand表*/
    private int brandId;    
    
    /**品牌栏目名称*/
    private String ctgName;
    
    /**品牌名称*/
    private String brandName;
    
    /**品牌栏目排序*/
    private int ctgOrder;
    
    /**品牌排序*/
    private int brandOrder;
    
    private String image;
    
    /**组合ID*/
    private int brandActivitiesCommonId;
    

    public String getCtgName()
    {
        return ctgName;
    }

    public void setCtgName(String ctgName)
    {
        this.ctgName = ctgName;
    }

    public String getBrandName()
    {
        return brandName;
    }

    public void setBrandName(String brandName)
    {
        this.brandName = brandName;
    }

    public int getCtgOrder()
    {
        return ctgOrder;
    }

    public void setCtgOrder(int ctgOrder)
    {
        this.ctgOrder = ctgOrder;
    }

    public int getBrandOrder()
    {
        return brandOrder;
    }

    public void setBrandOrder(int brandOrder)
    {
        this.brandOrder = brandOrder;
    }

    public int getBrandActivitiesCommonId()
    {
        return brandActivitiesCommonId;
    }

    public void setBrandActivitiesCommonId(int brandActivitiesCommonId)
    {
        this.brandActivitiesCommonId = brandActivitiesCommonId;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public int getCtgId()
    {
        return ctgId;
    }

    public void setCtgId(int ctgId)
    {
        this.ctgId = ctgId;
    }

    public int getBrandId()
    {
        return brandId;
    }

    public void setBrandId(int brandId)
    {
        this.brandId = brandId;
    }
    
    
}
