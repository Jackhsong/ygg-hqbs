package com.ygg.webapp.entity;

public class ActivitiesCustomEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int type;
    
    private int typeId;
    
    private String url;
    
    private String shareTitle;
    
    private String shareContentTencent;
    
    private String shareContentSina;
    
    private String shareUrl;
    
    private String shareImage;
    
    private String shareType;
    
    private String name;
    
    private String desc;
    
    private String pcDetail;
    
    private String mobileDetail;
    
    private boolean isAvailable;
    
    public String getShareUrl()
    {
        return shareUrl;
    }
    
    public void setShareUrl(String shareUrl)
    {
        this.shareUrl = shareUrl;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public String getPcDetail()
    {
        return pcDetail;
    }
    
    public void setPcDetail(String pcDetail)
    {
        this.pcDetail = pcDetail;
    }
    
    public String getMobileDetail()
    {
        return mobileDetail;
    }
    
    public void setMobileDetail(String mobileDetail)
    {
        this.mobileDetail = mobileDetail;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public boolean isAvailable()
    {
        return isAvailable;
    }
    
    public void setAvailable(boolean isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }
    
    public int getTypeId()
    {
        return typeId;
    }
    
    public void setTypeId(int typeId)
    {
        this.typeId = typeId;
    }
    
    public String getShareTitle()
    {
        return shareTitle;
    }
    
    public void setShareTitle(String shareTitle)
    {
        this.shareTitle = shareTitle;
    }
    
    public String getShareContentTencent()
    {
        return shareContentTencent;
    }
    
    public void setShareContentTencent(String shareContentTencent)
    {
        this.shareContentTencent = shareContentTencent;
    }
    
    public String getShareContentSina()
    {
        return shareContentSina;
    }
    
    public void setShareContentSina(String shareContentSina)
    {
        this.shareContentSina = shareContentSina;
    }
    
    public String getShareImage()
    {
        return shareImage;
    }
    
    public void setShareImage(String shareImage)
    {
        this.shareImage = shareImage;
    }
    
    public String getShareType()
    {
        return shareType;
    }
    
    public void setShareType(String shareType)
    {
        this.shareType = shareType;
    }
    
}
