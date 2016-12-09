package com.ygg.webapp.dao;

import com.ygg.webapp.entity.AccountSuccessOrderRecordEntity;
import com.ygg.webapp.exception.DaoException;

public interface AccountSuccessOrderRecordDao
{
    /**
     * 新增用户交易成功订单记录
     * 
     * @return
     */
    int addAccountSuccessOrderRecord(AccountSuccessOrderRecordEntity record)
        throws DaoException;
}
