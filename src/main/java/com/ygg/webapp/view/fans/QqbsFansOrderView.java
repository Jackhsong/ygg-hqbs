
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.view.fans;

import com.ygg.webapp.view.BaseView;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: QqbsFansOrderView.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public class QqbsFansOrderView extends BaseView
{
    
     /**    */
    private static final long serialVersionUID = 289745768163853066L;

    /**id*/
    private int id;   
    
    /**创建时间*/
    private String createTime;
    
    /**更新时间*/
    private String updateTime;
    /**粉丝订单Id */
    private int fansOrderId;
      /**帐号ID    */
    private int accountId;
     /**粉丝账号Id*/
    private int fansAccountId;
     /**粉丝用户头像*/
    private String fansImage;
     /** 粉丝昵称 */
    private String fansNickname;
     /**粉丝等级:1 直接粉丝 2 间接粉丝 3 间接粉丝   */
    private int level;
     /**订单编号，前端展示*/
    private String number;
    /** 可提现   */
    private String withdrawCash;
     /**订单实际金额    */
    private String realPrice;
    /**状态：1已付款 2可提现*/
    private int status;
    /**数据处理状态：0 总金额 1改为可提现 2 可提现*/
    private int exStatus;
    
    /**  
     *@return  the id
     */
    public int getId()
    {
        return id;
    }
    
    /** 
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }
    
    /**  
     *@return  the createTime
     */
    public String getCreateTime()
    {
        return createTime;
    }
    
    /** 
     * @param createTime the createTime to set
     */
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    /**  
     *@return  the updateTime
     */
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    /** 
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    /**  
     *@return  the fansOrderId
     */
    public int getFansOrderId()
    {
        return fansOrderId;
    }
    
    /** 
     * @param fansOrderId the fansOrderId to set
     */
    public void setFansOrderId(int fansOrderId)
    {
        this.fansOrderId = fansOrderId;
    }
    
    /**  
     *@return  the accountId
     */
    public int getAccountId()
    {
        return accountId;
    }
    
    /** 
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    /**  
     *@return  the fansAccountId
     */
    public int getFansAccountId()
    {
        return fansAccountId;
    }
    
    /** 
     * @param fansAccountId the fansAccountId to set
     */
    public void setFansAccountId(int fansAccountId)
    {
        this.fansAccountId = fansAccountId;
    }
    
    /**  
     *@return  the fansImage
     */
    public String getFansImage()
    {
        return fansImage;
    }
    
    /** 
     * @param fansImage the fansImage to set
     */
    public void setFansImage(String fansImage)
    {
        this.fansImage = fansImage;
    }
    
    /**  
     *@return  the fansNickname
     */
    public String getFansNickname()
    {
        return fansNickname;
    }
    
    /** 
     * @param fansNickname the fansNickname to set
     */
    public void setFansNickname(String fansNickname)
    {
        this.fansNickname = fansNickname;
    }
    
    /**  
     *@return  the level
     */
    public int getLevel()
    {
        return level;
    }
    
    /** 
     * @param level the level to set
     */
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    /**  
     *@return  the number
     */
    public String getNumber()
    {
        return number;
    }
    
    /** 
     * @param number the number to set
     */
    public void setNumber(String number)
    {
        this.number = number;
    }
    
    
    
    /**  
     *@return  the withdrawCash
     */
    public String getWithdrawCash()
    {
        return withdrawCash;
    }

    
    /** 
     * @param withdrawCash the withdrawCash to set
     */
    public void setWithdrawCash(String withdrawCash)
    {
        this.withdrawCash = withdrawCash;
    }

    
    /**  
     *@return  the realPrice
     */
    public String getRealPrice()
    {
        return realPrice;
    }

    
    /** 
     * @param realPrice the realPrice to set
     */
    public void setRealPrice(String realPrice)
    {
        this.realPrice = realPrice;
    }

    /**  
     *@return  the status
     */
    public int getStatus()
    {
        return status;
    }
    
    /** 
     * @param status the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    /**  
     *@return  the exStatus
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
