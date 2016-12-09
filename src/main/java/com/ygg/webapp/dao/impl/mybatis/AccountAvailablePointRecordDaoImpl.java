package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.AccountAvailablePointRecordDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.AccountAvailablePointRecordEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("accountAvailablePointRecordDao")
public class AccountAvailablePointRecordDaoImpl extends BaseDaoImpl implements AccountAvailablePointRecordDao
{
    
    @Override
    public List<AccountAvailablePointRecordEntity> findAvailablePointRecordsByAid(int accountId)
        throws DaoException
    {
        // String sql = "SELECT * FROM account_available_point_record WHERE account_id=? ORDER BY create_time DESC";

        Map<String,Object> para = new HashMap<>();
        para.put("accountId",accountId);
        List<AccountAvailablePointRecordEntity> list = this.getSqlSession().selectList("PointRecordMapper.findAvailablePointRecordsByAid", para);
        if (list == null)
            list = new ArrayList<>();
        return list;
    }
    
    @Override
    public int addAvailablePointRecords(AccountAvailablePointRecordEntity aapre)
        throws DaoException
    {
        // String sql =
        // "INSERT INTO account_available_point_record(account_id,operate_point,total_point,operate_type,arithmetic_type) VALUES(?,?,?,?,?)";
        
        return this.getSqlSession().insert("PointRecordMapper.addAvailablePointRecords", aapre);
    }
    
}
