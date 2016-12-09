package com.ygg.webapp.entity;

public class CouponDetailEntity extends BaseEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int threshold;
    
    private int scopeId;
    
    private int reduce;
    
    private byte type;
    
    private byte scopeType;
    
    /** 是否使用随机优惠金额 */
    private byte isRandomReduce;
    
    /** 随机优惠金额最低值 */
    private int lowestReduce;
    
    /** 随机优惠金额最高值 */
    private int maximalReduce;

    private String desc;

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getThreshold()
    {
        return threshold;
    }
    
    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }
    
    public int getScopeId()
    {
        return scopeId;
    }
    
    public void setScopeId(int scopeId)
    {
        this.scopeId = scopeId;
    }
    
    public int getReduce()
    {
        return reduce;
    }
    
    public void setReduce(int reduce)
    {
        this.reduce = reduce;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public byte getScopeType()
    {
        return scopeType;
    }
    
    public void setScopeType(byte scopeType)
    {
        this.scopeType = scopeType;
    }
    
    public byte getIsRandomReduce()
    {
        return isRandomReduce;
    }
    
    public void setIsRandomReduce(byte isRandomReduce)
    {
        this.isRandomReduce = isRandomReduce;
    }
    
    public int getLowestReduce()
    {
        return lowestReduce;
    }
    
    public void setLowestReduce(int lowestReduce)
    {
        this.lowestReduce = lowestReduce;
    }
    
    public int getMaximalReduce()
    {
        return maximalReduce;
    }
    
    public void setMaximalReduce(int maximalReduce)
    {
        this.maximalReduce = maximalReduce;
    }
    
}
