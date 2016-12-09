package com.ygg.webapp.entity;

public class SmsVerifyEntity
{
    private int id;
    
    private String mobileNumber;
    
    private String code;
    
    private byte type;
    
    private byte isUsed;
    
    private String validTime;
    
    private String createTime;
    
    private String updateTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public byte getIsUsed()
    {
        return isUsed;
    }
    
    public void setIsUsed(byte isUsed)
    {
        this.isUsed = isUsed;
    }
    
    public String getValidTime()
    {
        return validTime;
    }
    
    public void setValidTime(String validTime)
    {
        this.validTime = validTime;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
}
