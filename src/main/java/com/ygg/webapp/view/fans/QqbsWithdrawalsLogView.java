
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
  * @version $Id: QqbsWithdrawalsLogView.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public class QqbsWithdrawalsLogView extends BaseView
{
    
     /**    */
    private static final long serialVersionUID = -6463088108252413169L;

    /**id*/
    private int id;   
    
    /**创建时间*/
    private String createTime;
    
    /**更新时间*/
    private String updateTime;
    /**帐号ID*/
    private int accountId;
     /**提现金额*/
    private String withdrawals;
     /**提现状态：1提现中 2提现成功 3提现失败 */
    private int status;
    
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
     *@return  the withdrawals
     */
    public String getWithdrawals()
    {
        return withdrawals;
    }
    
    /** 
     * @param withdrawals the withdrawals to set
     */
    public void setWithdrawals(String withdrawals)
    {
        this.withdrawals = withdrawals;
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
}
