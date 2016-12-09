package com.ygg.webapp.entity;

import com.ygg.webapp.entity.base.BaseEntity;

public class Ticket extends BaseEntity
{
    
    
	 /**    */
	private static final long serialVersionUID = 2566319016599733169L;

	private String appId;
    
    private String ticket;
    
    private int expiresIn;

	
	/**  
	 *@return  the appId
	 */
	public String getAppId() {
		return appId;
	}

	
	/** 
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	
	/**  
	 *@return  the ticket
	 */
	public String getTicket() {
		return ticket;
	}

	
	/** 
	 * @param ticket the ticket to set
	 */
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	
	/**  
	 *@return  the expiresIn
	 */
	public int getExpiresIn() {
		return expiresIn;
	}

	
	/** 
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
