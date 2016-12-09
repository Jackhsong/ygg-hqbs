package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.AccountAvailablePointRecordEntity;
import com.ygg.webapp.exception.DaoException;

public interface AccountAvailablePointRecordDao
{
    
    /**
     * 根据用户id查询对应的可用积分记录
     * 
     * @return
     */
    List<AccountAvailablePointRecordEntity> findAvailablePointRecordsByAid(int accountId)
        throws DaoException;
    
    /**
     * 新增账号可用积分记录
     * 
     * @return
     */
    int addAvailablePointRecords(AccountAvailablePointRecordEntity aapre)
        throws DaoException;
}
