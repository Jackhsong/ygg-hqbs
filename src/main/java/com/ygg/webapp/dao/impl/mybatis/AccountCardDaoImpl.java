package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.AccountCardDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("accountCardDao")
public class AccountCardDaoImpl extends BaseDaoImpl implements AccountCardDao
{
    
    @Override
    public int insertAccountCard(AccountCartEntity ace)
        throws DaoException
    {
        return this.getSqlSession().insert("AccountCardMapper.insertAccountCard", ace);
    }
    
    @Override
    public int updateAccountCard(AccountCartEntity ace)
        throws DaoException
    {
        return this.getSqlSession().update("AccountCardMapper.updateAccountCard", ace);
    }
    
    @Override
    public List<AccountCartEntity> queryAccountCard(int accountId)
        throws DaoException
    {
        List<AccountCartEntity> list = this.getSqlSession().selectList("AccountCardMapper.queryAccountCard", accountId);
        if (list == null)
            list = new ArrayList<AccountCartEntity>();
        return list;
    }
    
    public AccountCartEntity queryAccountCardById(int accountCardId)
        throws DaoException
    {
        AccountCartEntity ac = this.getSqlSession().selectOne("AccountCardMapper.queryAccountCardById", accountCardId);
        return ac;
    }
    
    @Override
    public int deleteAccountCard(int accountCardId)
        throws DaoException
    {
        
        return this.getSqlSession().delete("AccountCardMapper.deleteAccountCard", accountCardId);
    }
    
    @Override
    public boolean isExistAccountCard(int accountCardId)
        throws DaoException
    {
        
        // String sql = "select count(1) count from account_card c where c.id ="+accountCardId ;
        int count = this.getSqlSession().selectOne("AccountCardMapper.isExistAccountCard", accountCardId);
        if (count > 0)
            return true;
        return false;
    }
    
    public boolean isExistAccountType(int accountId, int type)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", accountId);
        map.put("type", type);
        int count = this.getSqlSession().selectOne("AccountCardMapper.isExistAccountType", map);
        if (count > 0)
            return true;
        return false;
    }
    
    @Override
    public AccountCartEntity queryAccountCardByOrderRefundProductId(int orderProductRefundId)
        throws DaoException
    {
        AccountCartEntity ac = this.getSqlSession().selectOne("AccountCardMapper.queryAccountCardByOrderRefundProductId", orderProductRefundId);
        return ac;
    }
    
}
