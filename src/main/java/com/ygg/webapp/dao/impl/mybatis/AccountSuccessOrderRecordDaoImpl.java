package com.ygg.webapp.dao.impl.mybatis;

import com.ygg.webapp.dao.AccountSuccessOrderRecordDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.AccountSuccessOrderRecordEntity;
import com.ygg.webapp.exception.DaoException;
import org.springframework.stereotype.Repository;

@Repository("accountSuccessOrderRecordDao")
public class AccountSuccessOrderRecordDaoImpl extends BaseDaoImpl implements AccountSuccessOrderRecordDao
{
    
    @Override
    public int addAccountSuccessOrderRecord(AccountSuccessOrderRecordEntity record)
        throws DaoException
    {
        return this.getSqlSession().insert("AccountSuccessOrderRecordMapper.addAccountSuccessOrderRecord", record);
    }
}
