package com.ygg.webapp.code;

public enum ActivityCrazyFoodPrizeTypeEnum
{
    PRIZE_1("谢谢参与", 1),
    
    PRIZE_2("台湾海龙王花生", 2),
    
    PRIZE_3("泰山花生脆片饼干", 3),
    
    PRIZE_4("印尼啪啪通虾片", 4),
    
    PRIZE_5("韩国蜂蜜黄油腰果", 5),
    
    PRIZE_6("波特小姐蛋卷", 6);
    
    private String name;
    
    private int value;
    
    private ActivityCrazyFoodPrizeTypeEnum(String name, int value)
    {
        this.name = name;
        this.value = value;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public String getName()
    {
        return name;
    }
    
    public static String getName(int value)
    {
        for (ActivityCrazyFoodPrizeTypeEnum e : ActivityCrazyFoodPrizeTypeEnum.values())
        {
            if (e.getValue() == value)
            {
                return e.name;
            }
        }
        return "";
    }
}
