package com.ygg.webapp.code;

public enum GameReceivePrizeMessageEnum
{
    /** 0 领取失败啦~ */
    ERROR("领取失败啦~"),
    
    /** 1 恭喜您，中奖啦~ */
    SUCCESS("恭喜您，中奖啦~"),
    
    /** 2  没有机会啦~ */
    NO_CHANGES("您已经领取过了，每个手机号只能领取一次哦~"),
    
    /** 3 活动结束*/
    ACTIVITY_FINISHED("活动已经结束");
    
    private String description;
    
    private GameReceivePrizeMessageEnum(String description)
    {
        this.description = description;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * 根据序值得到枚举值
     * 
     * @param ordinal
     * @return
     */
    public static GameReceivePrizeMessageEnum getEnumByOrdinal(int ordinal)
    {
        for (GameReceivePrizeMessageEnum e : GameReceivePrizeMessageEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
