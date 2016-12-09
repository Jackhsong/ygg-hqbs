package com.ygg.webapp.view;

public class BannerView extends BaseView
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    private String type;
    
    private String image;
    
    private String commonActivitiesName;
    
    private String url = "";
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getCommonActivitiesName()
    {
        return commonActivitiesName;
    }
    
    public void setCommonActivitiesName(String commonActivitiesName)
    {
        this.commonActivitiesName = commonActivitiesName;
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
    
}
