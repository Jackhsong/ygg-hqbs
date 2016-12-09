package com.ygg.webapp.code;

/**
 * 活动奖品类型
 * 
 * @author zhangyb
 *
 */
public enum AppUrlTypeEnum
{
    /** 打开特卖商品 */
    SALE_PRODUCT("gegejia://resource/saleProduct/{0}/{1}"),
    
    /** 打开商城h商品 */
    MALL_PRODUCT("gegejia://resource/mallProduct/{0}/{1}");
    
    private String description;
    
    private AppUrlTypeEnum(String description)
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
    public static AppUrlTypeEnum getEnumByOrdinal(int ordinal)
    {
        for (AppUrlTypeEnum e : AppUrlTypeEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
