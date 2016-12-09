package com.ygg.webapp.code;

/**
 * 退款接收账户类型
 * 
 * @author zhangyb
 *
 */
public enum RefundPayTypeEnum
{
    /** 0 占位符 */
    NORMAL(""),
    
    /** 1  新建账户 */
    CREATE_NEW_ACCOUNT_CARD("新建账户"),
    
    /** 2  原路返回 */
    RETURN_BACK("原路返回");
    
    private String description;
    
    private RefundPayTypeEnum(String description)
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
    public static RefundPayTypeEnum getEnumByOrdinal(int ordinal)
    {
        for (RefundPayTypeEnum e : RefundPayTypeEnum.values())
        {
            if (e.ordinal() == ordinal)
                return e;
        }
        return null;
    }
}
