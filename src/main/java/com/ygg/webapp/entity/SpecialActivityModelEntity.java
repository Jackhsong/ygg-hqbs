package com.ygg.webapp.entity;

public class SpecialActivityModelEntity
{
    private int id;
    
    private String title;
    
    private String image;
    
    private int imageWidth;
    
    private int imageHeight;
    
    private int isAvailable;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
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
    
    public int getImageWidth()
    {
        return imageWidth;
    }
    
    public void setImageWidth(int imageWidth)
    {
        this.imageWidth = imageWidth;
    }
    
    public int getImageHeight()
    {
        return imageHeight;
    }
    
    public void setImageHeight(int imageHeight)
    {
        this.imageHeight = imageHeight;
    }
    
    public int getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(int isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
}
