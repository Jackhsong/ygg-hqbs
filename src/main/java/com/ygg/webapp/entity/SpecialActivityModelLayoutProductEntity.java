package com.ygg.webapp.entity;

public class SpecialActivityModelLayoutProductEntity
{
    private int id;
    
    private int specialActivityModelLayoutId;
    
    private int productId;
    
    private String desc;
    
    private int isDisplay;
    
    private int sequence;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getSpecialActivityModelLayoutId()
    {
        return specialActivityModelLayoutId;
    }
    
    public void setSpecialActivityModelLayoutId(int specialActivityModelLayoutId)
    {
        this.specialActivityModelLayoutId = specialActivityModelLayoutId;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public int getIsDisplay()
    {
        return isDisplay;
    }
    
    public void setIsDisplay(int isDisplay)
    {
        this.isDisplay = isDisplay;
    }
    
    public int getSequence()
    {
        return sequence;
    }
    
    public void setSequence(int sequence)
    {
        this.sequence = sequence;
    }
    
}
