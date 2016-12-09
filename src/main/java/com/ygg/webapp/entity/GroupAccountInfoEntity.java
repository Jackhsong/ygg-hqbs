package com.ygg.webapp.entity;

public class GroupAccountInfoEntity
{
    /** 团购商品账号记录Id */
    private int id;
    
    /** 商品ID */
    private int productId;
    
    /** 账户ID */
    private int accountId;
    
    /** 团购口令 */
    private String code = "";
    
    /** 团购商品口令Id */
    private int groupProductCodeId;
    
    /** 是否发起人；0：否，1：是 */
    private int isHead;
    
    /** 成团时间 */
    private String succeedTime = "";
    
    /** 是否购买；0：否，1：是 */
    private int isBuy;
    
    /** 是否可用；0：否，1：是 */
    private int isAvailable;
    
    /** 创建时间 */
    private String createTime = "";
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public int getGroupProductCodeId()
    {
        return groupProductCodeId;
    }
    
    public void setGroupProductCodeId(int groupProductCodeId)
    {
        this.groupProductCodeId = groupProductCodeId;
    }
    
    public int getIsHead()
    {
        return isHead;
    }
    
    public void setIsHead(int isHead)
    {
        this.isHead = isHead;
    }
    
    public String getSucceedTime()
    {
        return succeedTime;
    }
    
    public void setSucceedTime(String succeedTime)
    {
        this.succeedTime = succeedTime;
    }
    
    public int getIsBuy()
    {
        return isBuy;
    }
    
    public void setIsBuy(int isBuy)
    {
        this.isBuy = isBuy;
    }
    
    public int getIsAvailable()
    {
        return isAvailable;
    }
    
    public void setIsAvailable(int isAvailable)
    {
        this.isAvailable = isAvailable;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    

}
