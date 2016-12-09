package com.ygg.webapp.entity;

public class ActivityCrazyFoodRecordEntity
{
    private int id;
    
    private int activityId;
    
    private String username;
    
    private int leftTimes;
    
    private int usedTimes;
    
    private String createTime;
    
    private int isWine;
    
    private int isShared;
    
    private String updateTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getActivityId()
    {
        return activityId;
    }
    
    public void setActivityId(int activityId)
    {
        this.activityId = activityId;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public int getLeftTimes()
    {
        return leftTimes;
    }
    
    public void setLeftTimes(int leftTimes)
    {
        this.leftTimes = leftTimes;
    }
    
    public int getUsedTimes()
    {
        return usedTimes;
    }
    
    public void setUsedTimes(int usedTimes)
    {
        this.usedTimes = usedTimes;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public int getIsWine()
    {
        return isWine;
    }
    
    public void setIsWine(int isWine)
    {
        this.isWine = isWine;
    }
    
    public int getIsShared()
    {
        return isShared;
    }
    
    public void setIsShared(int isShared)
    {
        this.isShared = isShared;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString()
    {
        return "[id=" + id + ",activityId=" + activityId + ",username=" + username + ",leftTimes=" + leftTimes + ",usedTimes=" + usedTimes + ",isWine=" + isWine + ",isShared="
            + isShared + ",createTime=" + createTime + ",updateTime=" + updateTime + "]";
    }
}
