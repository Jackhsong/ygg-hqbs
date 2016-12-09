package com.ygg.webapp.entity;

public class LogisticsDetailEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String logisticsChannel;
    
    private String logisticsNumber;
    
    private String operateTime;
    
    private String content;
    
    private String createTime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getLogisticsChannel()
    {
        return logisticsChannel;
    }
    
    public void setLogisticsChannel(String logisticsChannel)
    {
        this.logisticsChannel = logisticsChannel;
    }
    
    public String getLogisticsNumber()
    {
        return logisticsNumber;
    }
    
    public void setLogisticsNumber(String logisticsNumber)
    {
        this.logisticsNumber = logisticsNumber;
    }
    
    public String getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(String operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
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
