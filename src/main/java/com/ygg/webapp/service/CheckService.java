package com.ygg.webapp.service;

import java.util.List;

import com.ygg.webapp.exception.ServiceException;

public interface CheckService
{
    
    /**
     * 系统启动时检查购物车中锁定时间
     */
    public void checkShoppingCartReferencesInfos(List<Integer> accountIds)
        throws ServiceException;
    
    public void checkShoppingCartReferencesInfos(Integer accountId, List<Integer> productIds)
        throws ServiceException;
    
    /**
     * 系统启动时检查Temp购物车中锁定时间
     * 
     * @param tempAccountId
     * @param productIds
     * @throws ServiceException
     */
    public void checkTempShoppingCartReferencesInfos(Integer tempAccountId, List<Integer> productIds)
        throws ServiceException;
    
    /**
     * 系统启动时检查 定单　中锁定时间
     * 
     * @param tempAccountId
     * @param productIds
     * @throws ServiceException
     */
    public void checkOrderCartReferencesInfos(Integer accountId, List<Integer> productIds)
        throws ServiceException;
}
