
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.entity.account;

import com.ygg.webapp.entity.base.BaseEntity;

/**
  * 代言人表
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: QqbsSpokesperson.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public class QqbsSpokesperson extends BaseEntity
{
    
     /**    */
    private static final long serialVersionUID = 6722218024548060564L;
    /**帐号ID    */
    private int accountId;
     /**成为直属代言人时间 20160101*/
    private int spTime;
     /**处理状态：0，待处理，1已处理    */
    private int exStatus;
    
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
     *@return  the spTime
     */
    public int getSpTime()
    {
        return spTime;
    }
    
    /** 
     * @param spTime the spTime to set
     */
    public void setSpTime(int spTime)
    {
        this.spTime = spTime;
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
