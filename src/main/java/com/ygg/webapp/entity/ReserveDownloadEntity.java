package com.ygg.webapp.entity;

/**
 * 预约下载Entity
 * 
 * @author lihc
 *
 */
public class ReserveDownloadEntity extends BaseEntity
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String phonenum;
    
    private String createtime;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getPhonenum()
    {
        return phonenum;
    }
    
    public void setPhonenum(String phonenum)
    {
        this.phonenum = phonenum;
    }
    
    public String getCreatetime()
    {
        return createtime;
    }
    
    public void setCreatetime(String createtime)
    {
        this.createtime = createtime;
    }
    
}
