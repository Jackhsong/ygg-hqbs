package com.ygg.webapp.entity;

/**
 * 定单退款实体
 * 
 * @author lihc
 *
 */
public class OrderProductRefundEntity extends BaseEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountCardId;
    
    private int accountId;
    
    private int orderId;
    
    private int productId;
    
    private int orderProductId;
    
    private byte count;
    
    private byte type = 1;
    
    private byte status = 1;
    
    private String explain = "";
    
    private float applyMoney = 0.0f;
    
    private float realMoney = 0.0f;
    
    private String image1 = "";
    
    private String image2 = "";
    
    private String image3 = "";
    
    private String createTime;
    
    private String checkTime;
    
    private String updateTime;
    
    private byte cardType = 2;
    
    private byte bankType = 1;
    
    private String cardNumber;
    
    private String cardName;
    
    private int refundPayType;

    /** 财务打款账户 */
    private int financialAffairsCardId;

    public int getFinancialAffairsCardId()
    {
        return financialAffairsCardId;
    }

    public void setFinancialAffairsCardId(int financialAffairsCardId)
    {
        this.financialAffairsCardId = financialAffairsCardId;
    }

    public int getRefundPayType()
    {
        return refundPayType;
    }
    
    public void setRefundPayType(int refundPayType)
    {
        this.refundPayType = refundPayType;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getAccountCardId()
    {
        return accountCardId;
    }
    
    public void setAccountCardId(int accountCardId)
    {
        this.accountCardId = accountCardId;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public int getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }
    
    public int getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(int orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    public byte getCount()
    {
        return count;
    }
    
    public void setCount(byte count)
    {
        this.count = count;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public byte getStatus()
    {
        return status;
    }
    
    public void setStatus(byte status)
    {
        this.status = status;
    }
    
    public String getExplain()
    {
        return explain;
    }
    
    public void setExplain(String explain)
    {
        this.explain = explain;
    }
    
    public String getImage1()
    {
        return image1;
    }
    
    public void setImage1(String image1)
    {
        this.image1 = image1;
    }
    
    public String getImage2()
    {
        return image2;
    }
    
    public void setImage2(String image2)
    {
        this.image2 = image2;
    }
    
    public String getImage3()
    {
        return image3;
    }
    
    public void setImage3(String image3)
    {
        this.image3 = image3;
    }
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(String checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public void setProductId(int productId)
    {
        this.productId = productId;
    }
    
    public float getApplyMoney()
    {
        return applyMoney;
    }
    
    public void setApplyMoney(float applyMoney)
    {
        this.applyMoney = applyMoney;
    }
    
    public float getRealMoney()
    {
        return realMoney;
    }
    
    public void setRealMoney(float realMoney)
    {
        this.realMoney = realMoney;
    }
    
    public byte getCardType()
    {
        return cardType;
    }
    
    public void setCardType(byte cardType)
    {
        this.cardType = cardType;
    }
    
    public byte getBankType()
    {
        return bankType;
    }
    
    public void setBankType(byte bankType)
    {
        this.bankType = bankType;
    }
    
    public String getCardNumber()
    {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    
    public String getCardName()
    {
        return cardName;
    }
    
    public void setCardName(String cardName)
    {
        this.cardName = cardName;
    }
    
}
