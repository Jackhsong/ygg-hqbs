package com.ygg.webapp.service;

import com.ygg.webapp.exception.ServiceException;

public interface AccountCardService
{
    
    /**
     * 管理银行卡账号
     * 
     * @param responseParams
     * @return
     * @throws ServiceException
     */
    public String getAllAccountCard(String requestParams)
        throws ServiceException;
    
    /**
     * 编辑银行卡账号
     * 
     * @param responseParams
     * @return
     * @throws ServiceException
     */
    public String editAccountCard(String requestParams)
        throws ServiceException;
    
    public String getAccountCardById(String requestParams)
        throws ServiceException;
    
    public String getAccountCardValueById(int accountCardId)
        throws ServiceException;
}
