
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.entity.base;

import java.io.Serializable;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: BaseEntity.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public abstract class BaseEntity implements Serializable{
	
	 /**    */
	private static final long serialVersionUID = -3260172075602610165L;

	/**id*/
    protected int id;   
    
    /**创建时间*/
    protected String createTime;
    
    /**更新时间*/
    protected String updateTime;
    
     /**删除标示*/
    protected int delete;

	
	/**  
	 *@return  the id
	 */
	public int getId() {
		return id;
	}

	
	/** 
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	
	/**  
	 *@return  the createTime
	 */
	public String getCreateTime() {
		return createTime;
	}

	
	/** 
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
	/**  
	 *@return  the updateTime
	 */
	public String getUpdateTime() {
		return updateTime;
	}

	
	/** 
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


    
    /**  
     *@return  the delete
     */
    public int getDelete()
    {
        return delete;
    }


    
    /** 
     * @param delete the delete to set
     */
    public void setDelete(int delete)
    {
        this.delete = delete;
    }
}
