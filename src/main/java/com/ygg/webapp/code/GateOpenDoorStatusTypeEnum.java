package com.ygg.webapp.code;

/**
 * 0：服务器发生异常；1：领取成功；2：已经领取；3：用户不存在；4：奖品不存在；5：口令错误；
 * @author Administrator
 *
 */
public enum GateOpenDoorStatusTypeEnum
{
    /**0 服务器错误*/
    SERVER_ERROR(0),
    
    /**1 领取成功*/
    RECEIVE_SUCCESS(1),
    
    /**2 口令错误*/
    PASSWD_ERROR(2),
    
    /**3 已经领取*/
    RECEIVED_YET(3),
    
    /**4 用户不存*/
    USER_NOT_EXISTS(4),
    
    /**5 奖品不存在*/
    PRIZE_NOT_EXISTS(5);
    
    private int code;
    
    private GateOpenDoorStatusTypeEnum(int code)
    {
        this.code = code;
    }
    
    public int getCode()
    {
        return code;
    }
}
