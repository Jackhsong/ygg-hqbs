package com.ygg.webapp.entity;

/**
 * 推广渠道Entity
 * @author xiongl
 *
 */
public class SpreadChannelEntity
{
    /** 推广渠道Id*/
    private int id;
    
    /**推广渠道名称 */
    private String channelName;
    
    /**分享标题*/
    private String shareTitle;
    
    /**分享图标 */
    private String shareImage;
    
    /**分享分享内容 */
    private String shareContent;
    
    /**推广渠道链接URL（分享链接） */
    private String url = "";
    
    /**领取人数 */
    private int receiveAmount = 0;
    
    /** 推广渠道带来新注册人数*/
    private int newRegister = 0;
    
    /** 推广渠道带来交易金额*/
    private float totalMoney = 0.0f;
    
    /** 推广渠道带来新用户交易金额*/
    private float newBuyerMoney = 0.0f;
    
    /** 领取奖励时是否发送短信，1是，0否*/
    private int isSendMsg;
    
    /** 发送短信内容*/
    private String msgContent = "";
    
    public int getIsSendMsg()
    {
        return isSendMsg;
    }
    
    public void setIsSendMsg(int isSendMsg)
    {
        this.isSendMsg = isSendMsg;
    }
    
    public String getMsgContent()
    {
        return msgContent;
    }
    
    public void setMsgContent(String msgContent)
    {
        this.msgContent = msgContent;
    }
    
    /** 是否可用，1可用，0不可用*/
    private int isAvailable;
    
    /**创建时间 */
    private String createTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getChannelName()
    {
        return channelName;
    }
    
    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }
    
    public String getShareTitle()
    {
        return shareTitle;
    }
    
    public void setShareTitle(String shareTitle)
    {
        this.shareTitle = shareTitle;
    }
    
    public String getShareImage()
    {
        return shareImage;
    }
    
    public void setShareImage(String shareImage)
    {
        this.shareImage = shareImage;
    }
    
    public String getShareContent()
    {
        return shareContent;
    }
    
    public void setShareContent(String shareContent)
    {
        this.shareContent = shareContent;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public int getReceiveAmount()
    {
        return receiveAmount;
    }
    
    public void setReceiveAmount(int receiveAmount)
    {
        this.receiveAmount = receiveAmount;
    }
    
    public int getNewRegister()
    {
        return newRegister;
    }
    
    public void setNewRegister(int newRegister)
    {
        this.newRegister = newRegister;
    }
    
    public float getTotalMoney()
    {
        return totalMoney;
    }
    
    public void setTotalMoney(float totalMoney)
    {
        this.totalMoney = totalMoney;
    }
    
    public float getNewBuyerMoney()
    {
        return newBuyerMoney;
    }
    
    public void setNewBuyerMoney(float newBuyerMoney)
    {
        this.newBuyerMoney = newBuyerMoney;
    }
    
    public int getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(int isAvailable)
    {
        this.isAvailable = isAvailable;
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
