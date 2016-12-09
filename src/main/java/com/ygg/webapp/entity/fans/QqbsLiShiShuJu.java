
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.entity.fans;

import com.ygg.webapp.entity.base.BaseEntity;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: QqbsLiShiShuJu.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public class QqbsLiShiShuJu extends BaseEntity {
	
	 /**    */
	private static final long serialVersionUID = -8053454714479664338L;

	/**帐号ID    */
	private int accountId;
	  
	 /**父Id*/
	private int fatherAccountId;
	
	 /**    */
	private int status;

	
	/**  
	 *@return  the accountId
	 */
	public int getAccountId() {
		return accountId;
	}

	
	/** 
	 * @param accountId the accountId to set
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	
	/**  
	 *@return  the fatherAccountId
	 */
	public int getFatherAccountId() {
		return fatherAccountId;
	}

	
	/** 
	 * @param fatherAccountId the fatherAccountId to set
	 */
	public void setFatherAccountId(int fatherAccountId) {
		this.fatherAccountId = fatherAccountId;
	}


	
	/**  
	 *@return  the status
	 */
	public int getStatus() {
		return status;
	}


	
	/** 
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}
