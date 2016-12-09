package com.ygg.webapp.entity;

public class GateActivityEntity
{
    /**Id*/
    private int id;
    
    /**任意门主题*/
    private String theme;
    
    /**任意门口令答案 */
    private String answer;
    
    /**任意门口令描述 */
    private String desc;
    
    /**有效期起始时间 */
    private String validTimeStart;
    
    /**有效期结束时间*/
    private String validTimeEnd;
    
    /** 链接URL（分享链接）*/
    private String url = "";
    
    /**领券提示文案*/
    private String receiveTip;
    
    /** 是否展现，1展现，0不展现*/
    private int isDisplay = 1;
    
    /**创建时间 */
    private String createTime;
    
    /**领取人数*/
    private int receiveAmount;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getTheme()
    {
        return theme;
    }
    
    public void setTheme(String theme)
    {
        this.theme = theme;
    }
    
    public String getAnswer()
    {
        return answer;
    }
    
    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public void setDesc(String desc)
    {
        this.desc = desc;
    }
    
    public String getValidTimeStart()
    {
        return validTimeStart;
    }
    
    public void setValidTimeStart(String validTimeStart)
    {
        this.validTimeStart = validTimeStart;
    }
    
    public String getValidTimeEnd()
    {
        return validTimeEnd;
    }
    
    public void setValidTimeEnd(String validTimeEnd)
    {
        this.validTimeEnd = validTimeEnd;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public int getIsDisplay()
    {
        return isDisplay;
    }
    
    public void setIsDisplay(int isDisplay)
    {
        this.isDisplay = isDisplay;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getReceiveTip()
    {
        return receiveTip;
    }
    
    public void setReceiveTip(String receiveTip)
    {
        this.receiveTip = receiveTip;
    }
    
    public int getReceiveAmount()
    {
        return receiveAmount;
    }
    
    public void setReceiveAmount(int receiveAmount)
    {
        this.receiveAmount = receiveAmount;
    }
    
}
