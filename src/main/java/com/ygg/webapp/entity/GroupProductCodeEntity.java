package com.ygg.webapp.entity;

public class GroupProductCodeEntity
{
    /** 团购商品口令Id */
    private int id;
    
    /** 商品ID */
    private int productId;
    
    /** 商品类型 */
    private int productType;

    /** 团购口令 */
    private String code = "";
    
    /** 是否成团：0：否，1：是 */
    private int isSucceed;
    
    /** 是否可用；0：否，1：是 */
    private int isAvailable;
    
    /** 创建时间 */
    private String createTime = "";
    
    public int getProductType()
    {
        return productType;
    }
    
    public void setProductType(int productType)
    {
        this.productType = productType;
    }

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
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public int getIsSucceed()
    {
        return isSucceed;
    }
    
    public void setIsSucceed(int isSucceed)
    {
        this.isSucceed = isSucceed;
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
