package com.ygg.webapp.view;

import java.util.List;

public class LaterView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String type;
    
    private String image;
    
    private List<String> labers;
    
    private String startSecond;
    
    private String leftDesc;
    
    private String rightDesc;
    
    private String sellWindowId;
    
    private String flagImage;
    
    private String isCollect;
    
    public String getFlagImage()
    {
        return flagImage;
    }
    
    public void setFlagImage(String flagImage)
    {
        this.flagImage = flagImage;
    }
    
    public String getIsCollect()
    {
        return isCollect;
    }
    
    public void setIsCollect(String isCollect)
    {
        this.isCollect = isCollect;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public List<String> getLabers()
    {
        return labers;
    }
    
    public void setLabers(List<String> labers)
    {
        this.labers = labers;
    }
    
    public String getStartSecond()
    {
        return startSecond;
    }
    
    public void setStartSecond(String startSecond)
    {
        this.startSecond = startSecond;
    }
    
    public String getLeftDesc()
    {
        return leftDesc;
    }
    
    public void setLeftDesc(String leftDesc)
    {
        this.leftDesc = leftDesc;
    }
    
    public String getRightDesc()
    {
        return rightDesc;
    }
    
    public void setRightDesc(String rightDesc)
    {
        this.rightDesc = rightDesc;
    }
    
    public String getSellWindowId()
    {
        return sellWindowId;
    }
    
    public void setSellWindowId(String sellWindowId)
    {
        this.sellWindowId = sellWindowId;
    }

    @Override
    public String toString()
    {
        return "sellWindowId: " + sellWindowId;
    }
}
