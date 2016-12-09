package com.ygg.webapp.code;

public enum AccessTypeEnum
{
    /**1：手机网页*/
    TYPE_OF_WAP("wap", 1),
    
    /**2：app*/
    TYPE_OF_APP("app", 2);
    
    private String name;
    
    private int value;
    
    private AccessTypeEnum(String name, int value)
    {
        this.name = name;
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public String getName(int value)
    {
        for (AccessTypeEnum e : AccessTypeEnum.values())
        {
            if (e.value == value)
            {
                return e.name;
            }
        }
        return null;
    }
}
