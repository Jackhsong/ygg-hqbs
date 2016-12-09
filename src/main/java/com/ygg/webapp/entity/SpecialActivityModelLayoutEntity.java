package com.ygg.webapp.entity;

public class SpecialActivityModelLayoutEntity
{
    private int id;
    
    private int specialActivityModelId;
    
    private String title;
    
    private String image;
    
    private String imageWidth;
    
    private String imageHeight;
    
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
    
    public int getSpecialActivityModelId()
    {
        return specialActivityModelId;
    }
    
    public void setSpecialActivityModelId(int specialActivityModelId)
    {
        this.specialActivityModelId = specialActivityModelId;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public String getImageWidth()
    {
        return imageWidth;
    }
    
    public void setImageWidth(String imageWidth)
    {
        this.imageWidth = imageWidth;
    }
    
    public String getImageHeight()
    {
        return imageHeight;
    }
    
    public void setImageHeight(String imageHeight)
    {
        this.imageHeight = imageHeight;
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
