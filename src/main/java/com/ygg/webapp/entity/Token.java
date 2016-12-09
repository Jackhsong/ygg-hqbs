package com.ygg.webapp.entity;

import com.ygg.webapp.entity.base.BaseEntity;

public class Token extends BaseEntity
{

    
	 /**    */
	private static final long serialVersionUID = 1946074349720855249L;

	private String appId;
    
    private String accessToken;
    
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
	 *@return  the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	
	/** 
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
