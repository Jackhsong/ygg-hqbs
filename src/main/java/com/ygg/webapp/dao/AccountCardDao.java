package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.exception.DaoException;

public interface AccountCardDao
{
    
    public int insertAccountCard(AccountCartEntity ace)
        throws DaoException;
    
    public int updateAccountCard(AccountCartEntity ace)
        throws DaoException;
    
    /**
     * 根据accountId 查出所有的银行卡记录
     * 
     * @param accountId
     * @return
     * @throws DaoException
     */
    public List<AccountCartEntity> queryAccountCard(int accountId)
        throws DaoException;
    
    public AccountCartEntity queryAccountCardById(int accountCardId)
        throws DaoException;
    
    public AccountCartEntity queryAccountCardByOrderRefundProductId(int orderProductRefundId)
        throws DaoException;
    
    public int deleteAccountCard(int accountCardId)
        throws DaoException;
    
    public boolean isExistAccountCard(int accountCardId)
        throws DaoException;
    
    public boolean isExistAccountType(int accountId, int type)
        throws DaoException;
}
