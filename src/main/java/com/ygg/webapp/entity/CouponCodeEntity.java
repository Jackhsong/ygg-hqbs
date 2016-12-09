package com.ygg.webapp.entity;

public class CouponCodeEntity extends BaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int couponDetailId;
    
    private byte type;
    
    private int sameMaxCount;
    
    private int total;
    
    private String code;
    
    private String startTime;
    
    private String endTime;
    
    private String remark;
    
    private String createTime;
    
    private String updateTime;
    
    private byte isAvailable;

    private byte changeType;

    private int changeCount;

    public byte getChangeType()
    {
        return changeType;
    }

    public void setChangeType(byte changeType)
    {
        this.changeType = changeType;
    }

    public int getChangeCount()
    {
        return changeCount;
    }

    public void setChangeCount(int changeCount)
    {
        this.changeCount = changeCount;
    }

    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getCouponDetailId()
    {
        return couponDetailId;
    }
    
    public void setCouponDetailId(int couponDetailId)
    {
        this.couponDetailId = couponDetailId;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public int getSameMaxCount()
    {
        return sameMaxCount;
    }
    
    public void setSameMaxCount(int sameMaxCount)
    {
        this.sameMaxCount = sameMaxCount;
    }
    
    public int getTotal()
    {
        return total;
    }
    
    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
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
    
    public byte getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(byte isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
}
