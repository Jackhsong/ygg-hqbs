package com.ygg.webapp.entity;

public class CouponAccountEntity extends BaseEntity
{
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountId;
    
    private byte sourceType;
    
    private int couponId;
    
    private int couponCodeId;
    
    private int couponDetailId;
    
    private String remark;
    
    private byte isUsed;
    
    private String startTime;
    
    private String endTime;
    
    private String createTime;
    
    /** 新增，当优惠券类型为随机时，随机减免金额为该值 */
    private int reducePrice;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public int getCouponId()
    {
        return couponId;
    }
    
    public void setCouponId(int couponId)
    {
        this.couponId = couponId;
    }
    
    public int getCouponDetailId()
    {
        return couponDetailId;
    }
    
    public void setCouponDetailId(int couponDetailId)
    {
        this.couponDetailId = couponDetailId;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public byte getIsUsed()
    {
        return isUsed;
    }
    
    public void setIsUsed(byte isUsed)
    {
        this.isUsed = isUsed;
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
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public byte getSourceType()
    {
        return sourceType;
    }
    
    public void setSourceType(byte sourceType)
    {
        this.sourceType = sourceType;
    }
    
    public int getCouponCodeId()
    {
        return couponCodeId;
    }
    
    public void setCouponCodeId(int couponCodeId)
    {
        this.couponCodeId = couponCodeId;
    }
    
    public int getReducePrice()
    {
        return reducePrice;
    }
    
    public void setReducePrice(int reducePrice)
    {
        this.reducePrice = reducePrice;
    }
    
    @Override
    public String toString()
    {
        return "CouponAccountEntity [id=" + id + ", accountId=" + accountId + ", sourceType=" + sourceType + ", couponId=" + couponId + ", couponCodeId=" + couponCodeId
            + ", couponDetailId=" + couponDetailId + ", remark=" + remark + ", isUsed=" + isUsed + ", startTime=" + startTime + ", endTime=" + endTime + ", createTime="
            + createTime + "]";
    }
    
}
