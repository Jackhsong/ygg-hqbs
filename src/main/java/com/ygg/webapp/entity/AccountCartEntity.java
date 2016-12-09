package com.ygg.webapp.entity;

/**
 * 用户的银行账号
 * 
 * @author lihc
 *
 */
public class AccountCartEntity extends BaseEntity
{
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private int accountId;
    
    private byte type;
    
    private byte bankType;
    
    private String cardNumber;
    
    private String cardName;
    
    // private String createTime ;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
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
