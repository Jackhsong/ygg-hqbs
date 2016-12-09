package com.ygg.webapp.code;

/**
 * 活动奖品类型
 * 
 * @author zhangyb
 *
 */
public enum LotteryPrizeTypeEnum
{
    /** 0 占位符 */
    NORMAL(""),
    
    /** 1  谢谢参与 */
    THANK("谢谢参与"),
    
    /** 2  优惠券 */
    COUPON("优惠券"),
    
    /** 3  积分 */
    INTEGRAL("积分"),

    /** 4 商品 */
    ITEM("商品");
    
    private String description;
    
    private LotteryPrizeTypeEnum(String description)
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
    public static LotteryPrizeTypeEnum getEnumByOrdinal(int ordinal)
    {
        for (LotteryPrizeTypeEnum e : LotteryPrizeTypeEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
