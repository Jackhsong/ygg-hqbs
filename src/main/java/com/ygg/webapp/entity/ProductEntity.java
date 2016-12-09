package com.ygg.webapp.entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProductEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int productBaseId;
    
    private int brandId;
    
    private int freightTemplateId;
    
    private int sellerId;
    
    private String name;
    
    private String shortName;
    
    private String desc;
    
    private float marketPrice;
    
    private float salesPrice;
    
    private String image1;
    
    private String image2;
    
    private String image3;
    
    private String image4;
    
    private String image5;
    
    private String netVolume;
    
    private String placeOfOrigin;
    
    private String storageMethod;
    
    private String manufacturerDate;
    
    private String durabilityPeriod;
    
    private String peopleFor;
    
    private String foodMethod;
    
    private String useMethod;
    
    private String tip;
    
    private String pcDetail;
    
    private byte isAvailable;
    
    private String createTime;
    
    private String updateTime;
    
    private String startTime;
    
    private String endTime;
    
    private byte isOffShelves;
    
    private byte type;
    
    private int gegeImageId;
    
    private byte activitiesStatus;
    
    /** 返分销毛利百分比类型 */
    private int returnDistributionProportionType;
    
    public int getReturnDistributionProportionType()
    {
        return returnDistributionProportionType;
    }
    
    public void setReturnDistributionProportionType(int returnDistributionProportionType)
    {
        this.returnDistributionProportionType = returnDistributionProportionType;
    }
    
    public byte getActivitiesStatus()
    {
        return activitiesStatus;
    }
    
    public void setActivitiesStatus(byte activitiesStatus)
    {
        this.activitiesStatus = activitiesStatus;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getBrandId()
    {
        return brandId;
    }
    
    public void setBrandId(int brandId)
    {
        this.brandId = brandId;
    }
    
    public int getFreightTemplateId()
    {
        return freightTemplateId;
    }
    
    public void setFreightTemplateId(int freightTemplateId)
    {
        this.freightTemplateId = freightTemplateId;
    }
    
    public int getSellerId()
    {
        return sellerId;
    }
    
    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getShortName()
    {
        return shortName;
    }
    
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public float getMarketPrice()
    {
        return marketPrice;
    }
    
    public void setMarketPrice(float marketPrice)
    {
        this.marketPrice = marketPrice;
    }
    
    public float getSalesPrice()
    {
        return salesPrice;
    }
    
    public void setSalesPrice(float salesPrice)
    {
        this.salesPrice = salesPrice;
    }
    
    public String getImage1()
    {
        return image1;
    }
    
    public void setImage1(String image1)
    {
        if (image1 != null && !image1.equals("") && !image1.startsWith("http://"))
            image1 = "http://" + image1;
        this.image1 = image1;
    }
    
    public String getImage2()
    {
        return image2;
    }
    
    public void setImage2(String image2)
    {
        if (image2 != null && !image2.equals("") && !image2.startsWith("http://"))
            image2 = "http://" + image2;
        this.image2 = image2;
    }
    
    public String getImage3()
    {
        return image3;
    }
    
    public void setImage3(String image3)
    {
        if (image3 != null && !image3.equals("") && !image3.startsWith("http://"))
            image3 = "http://" + image3;
        this.image3 = image3;
    }
    
    public String getImage4()
    {
        return image4;
    }
    
    public void setImage4(String image4)
    {
        if (image4 != null && !image4.equals("") && !image4.startsWith("http://"))
            image4 = "http://" + image4;
        this.image4 = image4;
    }
    
    public String getImage5()
    {
        return image5;
    }
    
    public void setImage5(String image5)
    {
        if (image5 != null && !image5.equals("") && !image5.startsWith("http://"))
            image5 = "http://" + image5;
        this.image5 = image5;
    }
    
    public String getNetVolume()
    {
        return netVolume;
    }
    
    public void setNetVolume(String netVolume)
    {
        this.netVolume = netVolume;
    }
    
    public String getPlaceOfOrigin()
    {
        return placeOfOrigin;
    }
    
    public void setPlaceOfOrigin(String placeOfOrigin)
    {
        this.placeOfOrigin = placeOfOrigin;
    }
    
    public String getStorageMethod()
    {
        return storageMethod;
    }
    
    public void setStorageMethod(String storageMethod)
    {
        this.storageMethod = storageMethod;
    }
    
    public String getManufacturerDate()
    {
        return manufacturerDate;
    }
    
    public void setManufacturerDate(String manufacturerDate)
    {
        this.manufacturerDate = manufacturerDate;
    }
    
    public String getDurabilityPeriod()
    {
        return durabilityPeriod;
    }
    
    public void setDurabilityPeriod(String durabilityPeriod)
    {
        this.durabilityPeriod = durabilityPeriod;
    }
    
    public String getPeopleFor()
    {
        return peopleFor;
    }
    
    public void setPeopleFor(String peopleFor)
    {
        this.peopleFor = peopleFor;
    }
    
    public String getFoodMethod()
    {
        return foodMethod;
    }
    
    public void setFoodMethod(String foodMethod)
    {
        this.foodMethod = foodMethod;
    }
    
    public String getUseMethod()
    {
        return useMethod;
    }
    
    public void setUseMethod(String useMethod)
    {
        this.useMethod = useMethod;
    }
    
    public String getTip()
    {
        return tip;
    }
    
    public void setTip(String tip)
    {
        this.tip = tip;
    }
    
    public String getPcDetail()
    {
        return pcDetail;
    }
    
    public void setPcDetail(String pcDetail)
    {
        this.pcDetail = pcDetail;
    }
    
    public byte getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(byte isAvailable)
    {
        this.isAvailable = isAvailable;
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
    
    public byte getIsOffShelves()
    {
        return isOffShelves;
    }
    
    public void setIsOffShelves(byte isOffShelves)
    {
        this.isOffShelves = isOffShelves;
    }
    
    public int getGegeImageId()
    {
        return gegeImageId;
    }
    
    public void setGegeImageId(int gegeImageId)
    {
        this.gegeImageId = gegeImageId;
    }
    
    public int getProductBaseId()
    {
        return productBaseId;
    }
    
    public void setProductBaseId(int productBaseId)
    {
        this.productBaseId = productBaseId;
    }
    
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
