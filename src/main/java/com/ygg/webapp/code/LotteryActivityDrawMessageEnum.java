package com.ygg.webapp.code;

/**
 * 活动抽奖结果
 * 
 * @author zhangyb
 *
 */
public enum LotteryActivityDrawMessageEnum
{
    /** 0 抽奖失败啦~ */
    ERROR("抽奖失败啦~"),
    
    /** 1 恭喜您，中奖啦~ */
    SUCCESS("恭喜您，中奖啦~"),
    
    /** 2  活动未开始哦~ */
    NOT_STARTED("活动未开始哦~"),
    
    /** 3  活动已经结束啦~ */
    COMPLETE("活动已经结束啦~"),
    
    /** 4  没有机会啦~ */
    NO_CHANGES("没有机会啦~"),
    
    /** 5  没有机会啦~ */
    ACCOUNT_NOT_EXIST("用户不存在"),
    
    /** 6 奖品不存在*/
    PRIZE_NOT_EXIST("奖品不存在");
    
    private String description;
    
    private LotteryActivityDrawMessageEnum(String description)
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
    public static LotteryActivityDrawMessageEnum getEnumByOrdinal(int ordinal)
    {
        for (LotteryActivityDrawMessageEnum e : LotteryActivityDrawMessageEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
