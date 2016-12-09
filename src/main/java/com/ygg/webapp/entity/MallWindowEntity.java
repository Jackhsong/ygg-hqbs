package com.ygg.webapp.entity;

import java.io.Serializable;

public class MallWindowEntity implements Serializable
{
private static final long serialVersionUID = -1958149865094374365L;
    
    private int id;
    
    private int mallPageId;
    
    private String name;
    
    private String image;
    
    private byte isDisplay;
    
    private String remark;
    
    private short sequence;
    
    private String createTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getMallPageId()
    {
        return mallPageId;
    }
    
    public void setMallPageId(int mallPageId)
    {
        this.mallPageId = mallPageId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public byte getIsDisplay()
    {
        return isDisplay;
    }
    
    public void setIsDisplay(byte isDisplay)
    {
        this.isDisplay = isDisplay;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public short getSequence()
    {
        return sequence;
    }
    
    public void setSequence(short sequence)
    {
        this.sequence = sequence;
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
