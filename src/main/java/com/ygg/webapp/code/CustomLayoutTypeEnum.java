package com.ygg.webapp.code;

/**
 * 自定义布局 访问类型
 * 
 * @author zhangyb
 *
 */
public enum CustomLayoutTypeEnum
{
    NORMAL(""),

    /** 特卖商品 */
    SALE_PRODUCT("特卖商品"),
    
    /** 通用专场 */
    ACTIVITIES_COMMON("通用专场"),
    
    /** 自定义专场 */
    ACTIVITIES_CUSTOM("自定义专场"),
    
    /** 商城商品 */
    MALL_PRODUCT("商城商品");
    
    private String description;
    
    private CustomLayoutTypeEnum(String description)
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
    public static CustomLayoutTypeEnum getEnumByOrdinal(int ordinal)
    {
        for (CustomLayoutTypeEnum e : CustomLayoutTypeEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
