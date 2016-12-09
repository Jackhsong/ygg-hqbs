package com.ygg.webapp.entity;

public class SpecialActivityNewEntity
{
    private int id;
    
    private String title;
    
    private String image;
    
    private int isAvailable;
    
    private String createTime;

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

    public int getIsAvailable()
    {
        return isAvailable;
    }

    public void setIsAvailable(int isAvailable)
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
}
