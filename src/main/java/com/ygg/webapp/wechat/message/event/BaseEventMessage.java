
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.wechat.message.event;


 /**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: BaseEventMessage.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public abstract class BaseEventMessage {
	// 开发者微信号
    private String toUserName;
    
    // 发送方帐号（一个OpenID）
    private String fromUserName;
    
    // 消息创建时间 （整型）
    private long createTime;
    
    // 消息类型（event）
    private String msgType;
    
    // 事件源
    private String event;
    
     /**推荐人Id*/
    private String eventKey;

	
	/**  
	 *@return  the toUserName
	 */
	public String getToUserName() {
		return toUserName;
	}

	
	/** 
	 * @param toUserName the toUserName to set
	 */
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	
	/**  
	 *@return  the fromUserName
	 */
	public String getFromUserName() {
		return fromUserName;
	}

	
	/** 
	 * @param fromUserName the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	
	/**  
	 *@return  the createTime
	 */
	public long getCreateTime() {
		return createTime;
	}

	
	/** 
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	
	/**  
	 *@return  the msgType
	 */
	public String getMsgType() {
		return msgType;
	}

	
	/** 
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	
	/**  
	 *@return  the event
	 */
	public String getEvent() {
		return event;
	}

	
	/** 
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}


	
	/**  
	 *@return  the eventKey
	 */
	public String getEventKey() {
		return eventKey;
	}


	
	/** 
	 * @param eventKey the eventKey to set
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
