package com.ygg.webapp.code;

/**
 * 公共错误提示
 * 
 * @author zyb
 *         
 */
public enum BusinessResponseMessage
{
    /** 未知错误 */
    UN_KNOW(0, "未知错误"),
    
    /** 参数错误 */
    PARAMETER_ERROR(0, "参数错误");
    
    
    private int status;
    
    private String message;
    
    private BusinessResponseMessage(int status, String msg)
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
