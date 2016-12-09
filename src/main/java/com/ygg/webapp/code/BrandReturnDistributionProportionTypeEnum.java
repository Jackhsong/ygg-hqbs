package com.ygg.webapp.code;

/**
 * 品牌返分销毛利百分比类型
 * 
 * @author zhangyb
 *
 */
public enum BrandReturnDistributionProportionTypeEnum
{
    /** 0 占位符 */
    NORMAL("占位符"),

    /** 1 返25% */
    PROPORTION_25("返25%"),

    /** 2 返100% */
    PROPORTION_100("返100%");

    private String description;

    private BrandReturnDistributionProportionTypeEnum(String description)
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
    public static BrandReturnDistributionProportionTypeEnum getEnumByOrdinal(int ordinal)
    {
        for (BrandReturnDistributionProportionTypeEnum e : BrandReturnDistributionProportionTypeEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
