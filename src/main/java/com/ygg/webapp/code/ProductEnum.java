package com.ygg.webapp.code;

public class ProductEnum
{
    public enum PRODUCT_ACTIVITY_STATUS
    {
        /** 正常 */
        NORMAL(1, "正常"),
        
        /** 团购 */
        GROUP_BUY(2, "团购"),
        
        /** 格格福利 */
        GEGE_WELFARE(3, "格格福利"),
        
        NEWBIE(4, "新人专享");
        
        private final int code;
        
        private final String desc;
        
        private PRODUCT_ACTIVITY_STATUS(int code, String desc)
        {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public String getDesc()
        {
            return desc;
        }
        
        public static PRODUCT_ACTIVITY_STATUS getEnumByOrdinal(int code)
        {
            for (PRODUCT_ACTIVITY_STATUS e : PRODUCT_ACTIVITY_STATUS.values())
            {
                if (e.code == code)
                    return e;
            }
            return null;
        }
    }
    
    public static enum DELIVER_AREA_TYPE
    {
        
        SUPPORT(1),
        
        UNSUPPORT(2);
        
        private final int code;
        
        private DELIVER_AREA_TYPE(int code)
        {
            this.code = code;
        }
        
        public int getCode()
        {
            return code;
        }
        
    }
    
    public static enum PRODUCT_TYPE
    {
        
        SALE(1),
        
        MALL(2),
        
        GEGETUAN(3);
        
        private final int code;
        
        private PRODUCT_TYPE(int code)
        {
            this.code = code;
        }
        
        public int getCode()
        {
            return code;
        }
    }
}
