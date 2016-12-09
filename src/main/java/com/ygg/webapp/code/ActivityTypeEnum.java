package com.ygg.webapp.code;

public enum ActivityTypeEnum
{
    /**1：游戏活动*/
    ACTIVITY_TYPE_OF_GAME(1, "游戏活动"),
    
    /**2：推广渠道活动*/
    ACTIVITY_TYPE_OF_SPREADCHANNEL(2, "渠道推广活动"),
    
    /**3：任意门活动*/
    ACTIVITY_TYPE_OF_ANYDOOR(3, "任意门活动");
    
    private int code;
    
    private String name;
    
    private ActivityTypeEnum(int code, String name)
    {
        this.code = code;
        this.name = name;
    }
    
    public int getCode()
    {
        return this.code;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getName(int code)
    {
        for (ActivityTypeEnum e : ActivityTypeEnum.values())
        {
            if (e.code == code)
            {
                return e.name;
            }
        }
        return null;
    }
}
