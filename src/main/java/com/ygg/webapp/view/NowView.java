package com.ygg.webapp.view;

import java.util.List;

public class NowView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String type;
    
    private String image;
    
    private List<String> labers;
    
    private String endSecond;
    
    private String leftDesc;
    
    private String rightDesc;
    
    private String status;
    
    private String sellWindowId;
    
    private String url;
    
    private String flagImage = "";
    
    private String isCollect;
    
    public String getIsCollect()
    {
        return isCollect;
    }
    
    public void setIsCollect(String isCollect)
    {
        this.isCollect = isCollect;
    }

    public String getFlagImage()
    {
        return flagImage;
    }
    
    public void setFlagImage(String flagImage)
    {
        this.flagImage = flagImage;
    }

    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
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
    
    public String getEndSecond()
    {
        return endSecond;
    }
    
    public void setEndSecond(String endSecond)
    {
        this.endSecond = endSecond;
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
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
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
