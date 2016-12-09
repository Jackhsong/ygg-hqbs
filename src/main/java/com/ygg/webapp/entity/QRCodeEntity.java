package com.ygg.webapp.entity;

import com.ygg.webapp.entity.base.BaseEntity;

/**
 * @author wuhy
 * @date 创建时间：2016年5月18日 上午11:34:42
 */
public class QRCodeEntity extends BaseEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 2720069619194586776L;
    
    private int accountId;
    
    private String qrCodeUrl;
    
    private String creator;
    
    private AccountEntity account;
    
    public String getCreator()
    {
        return creator;
    }
    
    public void setCreator(String creator)
    {
        this.creator = creator;
    }
    
    public AccountEntity getAccount()
    {
        return account;
    }
    
    public void setAccount(AccountEntity account)
    {
        this.account = account;
    }
    
    public int getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }
    
    public String getQrCodeUrl()
    {
        return qrCodeUrl;
    }
    
    public void setQrCodeUrl(String qrCodeUrl)
    {
        this.qrCodeUrl = qrCodeUrl;
    }
    
}
