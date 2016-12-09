package com.ygg.webapp.code;

/**
 * 商品活动状态
 * 
 * @author zhangyb
 *
 */
public enum ProductActivitiesStatusEnum
{
    /** 占位符 */
    INGORE("占位符"),

    /** 正常 */
    NORMAL("正常"),
    
    /** 团购 */
    GROUP_BUY("团购"),
    
    /** 格格福利 */
    GEGE_WELFARE("格格福利");
    
    private String description;
    
    private ProductActivitiesStatusEnum(String description)
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
    public static ProductActivitiesStatusEnum getEnumByOrdinal(int ordinal)
    {
        for (ProductActivitiesStatusEnum e : ProductActivitiesStatusEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
