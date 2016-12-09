package com.ygg.webapp.entity;

public class AccountEntity extends BaseEntity
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String unionId;
    
    private String name;
    
    private String pwd;
    
    private byte type;
    
    private String nickname;
    
    private String mobileNumber;
    
    private String create_time;
    
    private String update_time;
    
    private String image;
    
    private int availablePoint;
    
    private String recommendedCode;
    
    private int recommendedCount;
    
    private int recommendedOrderCount;
    
    private int subRecommendedCount;
    
    private int totalRecommendedPoint;
    
    private byte isRecommended;
    
    private byte partnerStatus;
    
    private byte applyPartnerStatus;
    
    private byte isHasOrder;

    private int level;

    private float totalSuccessPrice;
    
    private String createTime;
    
    private String updateTime;

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public float getTotalSuccessPrice()
    {
        return totalSuccessPrice;
    }

    public void setTotalSuccessPrice(float totalSuccessPrice)
    {
        this.totalSuccessPrice = totalSuccessPrice;
    }

    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public String getNickname()
    {
        return nickname;
    }
    
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
    
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
    
    public String getCreate_time()
    {
        return create_time;
    }
    
    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }
    
    public String getUpdate_time()
    {
        return update_time;
    }
    
    public void setUpdate_time(String update_time)
    {
        this.update_time = update_time;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    public int getAvailablePoint()
    {
        return availablePoint;
    }
    
    public void setAvailablePoint(int availablePoint)
    {
        this.availablePoint = availablePoint;
    }
    
    public String getRecommendedCode()
    {
        return recommendedCode;
    }
    
    public void setRecommendedCode(String recommendedCode)
    {
        this.recommendedCode = recommendedCode;
    }
    
    public int getRecommendedCount()
    {
        return recommendedCount;
    }
    
    public void setRecommendedCount(int recommendedCount)
    {
        this.recommendedCount = recommendedCount;
    }
    
    public int getRecommendedOrderCount()
    {
        return recommendedOrderCount;
    }
    
    public void setRecommendedOrderCount(int recommendedOrderCount)
    {
        this.recommendedOrderCount = recommendedOrderCount;
    }
    
    public int getSubRecommendedCount()
    {
        return subRecommendedCount;
    }
    
    public void setSubRecommendedCount(int subRecommendedCount)
    {
        this.subRecommendedCount = subRecommendedCount;
    }
    
    public int getTotalRecommendedPoint()
    {
        return totalRecommendedPoint;
    }
    
    public void setTotalRecommendedPoint(int totalRecommendedPoint)
    {
        this.totalRecommendedPoint = totalRecommendedPoint;
    }
    
    public byte getIsRecommended()
    {
        return isRecommended;
    }
    
    public void setIsRecommended(byte isRecommended)
    {
        this.isRecommended = isRecommended;
    }
    
    public byte getPartnerStatus()
    {
        return partnerStatus;
    }
    
    public void setPartnerStatus(byte partnerStatus)
    {
        this.partnerStatus = partnerStatus;
    }
    
    public byte getApplyPartnerStatus()
    {
        return applyPartnerStatus;
    }
    
    public void setApplyPartnerStatus(byte applyPartnerStatus)
    {
        this.applyPartnerStatus = applyPartnerStatus;
    }
    
    public byte getIsHasOrder()
    {
        return isHasOrder;
    }
    
    public void setIsHasOrder(byte isHasOrder)
    {
        this.isHasOrder = isHasOrder;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }

	
	/**  
	 *@return  the unionId
	 */
	public String getUnionId() {
		return unionId;
	}

	
	/** 
	 * @param unionId the unionId to set
	 */
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
}
