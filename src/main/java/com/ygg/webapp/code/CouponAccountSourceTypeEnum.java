package com.ygg.webapp.code;

/**
 * 优惠券来源类型
 * 
 * @author zhangyb
 *
 */
public enum CouponAccountSourceTypeEnum
{
    /** 1  优惠券发放 */
    COUPON_SEND(1, "优惠券发放"),
    
    /** 2  优惠码兑换 */
    COUPON_CODE_CHANGE(2, "优惠码兑换"),
    
    /** 3  抽奖所得 */
    LOTTERY(3, "抽奖所得"),
    
    /** 4  签到奖励 */
    LOGIN(4, "签到奖励"),
    
    /** 5  小伙伴推荐奖励 */
    RECOMMEND(5, "小伙伴推荐奖励"),
    
    /** 6  分享礼包 */
    GIFT(6, "分享礼包"),
    
    /** 7  玩游戏领取 */
    GAME(7, "玩游戏领取"),
    
    /** 8   通过推广渠道领取*/
    SPREAD_CHANNEL(8, "推广渠道领取"),
    
    /** 9   通过任意门领取*/
    ANY_DOOR(9, "任意门领取"),
    
    /**10 订单取消返还*/
    ORDER_CANCEL_RETURN(10, "订单取消返还"),
    
    /**11 红包领取 */
    RED_PACKET(11, "红包领取"),
    
    /**12 美食迎新会*/
    DELICIOUS_FOOD_PARTY(12, "美食迎新会"),
    
    /**13 */
    EXCHANGE_PRODUCT_USED(13, ""),
    
    /** 14 圣诞砸金蛋 */
    CHRISTMAS(14, "圣诞砸金蛋"),
    
    /** 15 跨年领券 */
    NEW_YEAR(15, "跨年领券"),
    
    /** 16 2016新年红包领券 */
    NEW_YEAR_2016(16, "2016新年红包领券"),
    
    /** 17 2016新年红包雨领券 */
    RED_PACKET_RAIN(17, "2016新年红包雨领券"),
    
    /** 18 2016新年100元红包*/
    NEW_YEAR_2016_RED_PACKET(18, "2016新年100元红包"),
    
    /** 19 新年幸运签*/
    NEW_YEAR_LUCKY_LOT(19, "新年幸运签"),
    
    /** 20 情人节*/
    VALENTINES_DAY(20, "情人节活动"),
    
    /** 21 元宵猜灯谜*/
    LANTERN_FESTIVAL(21, "元宵猜灯谜"),
    
    /** 22 元宵挑花灯*/
    HUA_DENG(22, "元宵挑花灯"),
    
    /** 2016女神节*/
    GODDESS_FESTIVAL(23, "2016女神节"),
    
    LUCKY_DRAW(24, "幸运大转盘");
    
    private String desc;
    
    private int code;
    
    private CouponAccountSourceTypeEnum(int code, String desc)
    {
        this.code = code;
        this.desc = desc;
    }
    
    public String getDesc()
    {
        return desc;
    }
    
    public int getCode()
    {
        return code;
    }
    
    /**
     * 根据序值得到枚举值
     * 
     * @param code
     * @return
     */
    public static CouponAccountSourceTypeEnum getEnumByCode(int code)
    {
        for (CouponAccountSourceTypeEnum e : CouponAccountSourceTypeEnum.values())
        {
            if (e.code == code)
                return e;
        }
        return null;
    }
}
