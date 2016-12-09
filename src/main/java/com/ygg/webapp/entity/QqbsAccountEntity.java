package com.ygg.webapp.entity;

import com.ygg.webapp.entity.base.BaseEntity;

public class QqbsAccountEntity extends BaseEntity
{
    
    /**    */
    private static final long serialVersionUID = -1646780429416683468L;
    
    private int accountId;
    
    private String openId;
    
    private String unionId;
    
    private int fatherAccountId;
    
    private String nickName;
    
    private String image;
    
    // 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
    private String subscribe;
    
    /** 代言人标示 0：不是 1是 */
    private int spokesPerson;
    
    /** 处理状态 */
    private int exStatus;
    
    private int hasPersistentQRCode;
    
    public int getHasPersistentQRCode()
    {
        return hasPersistentQRCode;
    }
    
    public void setHasPersistentQRCode(int hasPersistentQRCode)
    {
        this.hasPersistentQRCode = hasPersistentQRCode;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public String getOpenId()
    {
        return openId;
    }
    
    public void setOpenId(String openId)
    {
        this.openId = openId;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public int getFatherAccountId()
    {
        return fatherAccountId;
    }
    
    public void setFatherAccountId(int fatherAccountId)
    {
        this.fatherAccountId = fatherAccountId;
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
    
    public String getNickName()
    {
        return nickName;
    }
    
    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    /**
     * @return the subscribe
     */
    public String getSubscribe()
    {
        return subscribe;
    }
    
    /**
     * @param subscribe the subscribe to set
     */
    public void setSubscribe(String subscribe)
    {
        this.subscribe = subscribe;
    }
    
    /**
     * @return the spokesPerson
     */
    public int getSpokesPerson()
    {
        return spokesPerson;
    }
    
    /**
     * @param spokesPerson the spokesPerson to set
     */
    public void setSpokesPerson(int spokesPerson)
    {
        this.spokesPerson = spokesPerson;
    }
    
    /**
     * @return the exStatus
     */
    public int getExStatus()
    {
        return exStatus;
    }
    
    /**
     * @param exStatus the exStatus to set
     */
    public void setExStatus(int exStatus)
    {
        this.exStatus = exStatus;
    }
}
