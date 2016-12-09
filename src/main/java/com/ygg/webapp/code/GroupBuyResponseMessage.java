package com.ygg.webapp.code;

public enum GroupBuyResponseMessage
{
    /** 团购code已过期 */
    CODE_EXPIRE(0, "团购code已过期");
    
    private int status;
    
    private String message;
    
    private GroupBuyResponseMessage(int status, String msg)
    {
        this.status = status;
        this.message = msg;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public String getMessage()
    {
        return message;
    }
}
