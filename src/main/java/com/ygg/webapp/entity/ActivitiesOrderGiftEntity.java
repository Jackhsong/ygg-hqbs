package com.ygg.webapp.entity;

import java.sql.Timestamp;

public class ActivitiesOrderGiftEntity extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private long orderSameBatchNumber;
    
    private int shareAccountId;

    private int successCouponId;

    private int shareCouponId;

    private Timestamp validityTime;

    private int totalNum;

    private int leftNum;

    private int isSuccess;

    private Timestamp createTime;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public long getOrderSameBatchNumber()
    {
        return orderSameBatchNumber;
    }

    public void setOrderSameBatchNumber(long orderSameBatchNumber)
    {
        this.orderSameBatchNumber = orderSameBatchNumber;
    }

    public int getShareAccountId()
    {
        return shareAccountId;
    }

    public void setShareAccountId(int shareAccountId)
    {
        this.shareAccountId = shareAccountId;
    }

    public int getSuccessCouponId()
    {
        return successCouponId;
    }

    public void setSuccessCouponId(int successCouponId)
    {
        this.successCouponId = successCouponId;
    }

    public int getShareCouponId()
    {
        return shareCouponId;
    }

    public void setShareCouponId(int shareCouponId)
    {
        this.shareCouponId = shareCouponId;
    }

    public Timestamp getValidityTime()
    {
        return validityTime;
    }

    public void setValidityTime(Timestamp validityTime)
    {
        this.validityTime = validityTime;
    }

    public int getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(int totalNum)
    {
        this.totalNum = totalNum;
    }

    public int getLeftNum()
    {
        return leftNum;
    }

    public void setLeftNum(int leftNum)
    {
        this.leftNum = leftNum;
    }

    public int getIsSuccess()
    {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess)
    {
        this.isSuccess = isSuccess;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }
}
