package com.ygg.webapp.code;

public class ActivityEnum
{
    public static enum SPECIAL_ACTIVITY_GROUP_TYPE
    {
        PRODUCT(1, "商品"),
        
        GROUP(2, "组合"),
        
        CUSTOM_ACTIVITY(3, "自定义活动"),
        
        NON_HREF(4, "点击不跳转");
        
        private int value;
        
        private String name;
        
        private SPECIAL_ACTIVITY_GROUP_TYPE(int value, String name)
        {
            this.value = value;
            this.name = name;
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
            for (SPECIAL_ACTIVITY_GROUP_TYPE e : SPECIAL_ACTIVITY_GROUP_TYPE.values())
            {
                if (value == e.value)
                {
                    return e.name;
                }
            }
            return "";
        }
    }
    
    public static enum ActivityCrazyFoodStatusEnum
    {
        SERVER_ERROR(0, "服务器忙，请稍后"),
        
        NOT_LOGIN(1, "未登录"),
        
        NOT_START(2, "活动还未开始"),
        
        FINISHED(3, "活动已结束"),
        
        WINE(4, "中奖"),
        
        OUT_OF_TIMES(5, "次数用完"),
        
        OUT_OF_LIMIT(6, "次数超过限制");
        
        private int value;
        
        private String message;
        
        ActivityCrazyFoodStatusEnum(int value, String message)
        {
            this.value = value;
            this.message = message;
        }
        
        public int getValue()
        {
            return value;
        }
        
        public String getMessage()
        {
            return message;
        }
    }
    
    public static enum ActivityCrazyFoodPrizeEnum
    {
        PRIZE_1(1, "10元无门槛现金券"),
        
        PRIZE_2(2, "321积分"),
        
        PRIZE_3(3, "1-50随机现金券"),
        
        PRIZE_4(4, "左岸城堡购物袋"),
        
        PRIZE_5(5, "满299-20优惠券"),
        
        PRIZE_6(6, "海蓝之谜传奇面霜");
        
        private int value;
        
        private String desc;
        
        ActivityCrazyFoodPrizeEnum(int value, String desc)
        {
            this.value = value;
            this.desc = desc;
        }
        
        public int getValue()
        {
            return value;
        }
        
        public String getDesc()
        {
            return desc;
        }
    }
}
