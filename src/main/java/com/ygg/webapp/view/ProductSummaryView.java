package com.ygg.webapp.view;

public class ProductSummaryView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String brand;
    
    private String specification;
    
    private String placeOfOrigin;
    
    private String storageMethod;
    
    private String durabilityPeriod;
    
    private String peopleFor;
    
    private String foodMethod;
    
    private String useMethod;
    
    private String manufacturerDate;
    
    private String tip;
    private String name;
    public String getName(){
    	return name;
    }
    public void setName(String name){
    	this.name = name;
    }
    
    public String getBrand()
    {
        return brand;
    }
    
    public void setBrand(String brand)
    {
        this.brand = brand;
    }
    
    public String getSpecification()
    {
        return specification;
    }
    
    public void setSpecification(String specification)
    {
        this.specification = specification;
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
    
    public String getManufacturerDate()
    {
        return manufacturerDate;
    }
    
    public void setManufacturerDate(String manufacturerDate)
    {
        this.manufacturerDate = manufacturerDate;
    }
    
    public String getTip()
    {
        return tip;
    }
    
    public void setTip(String tip)
    {
        this.tip = tip;
    }
    
}
