package com.ygg.webapp.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CollectDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.exception.DaoException;

@Repository("collectDao")
public class CollectDaoImpl extends BaseDaoImpl implements CollectDao
{
    
    @Override
    public int addAccountCollectProduct(int accountId, int productId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", accountId);
        para.put("productId", productId);
        int status = getSqlSession().insert("CollectMapper.addAccountCollectProduct", para);
        return status;
    }
    
    @Override
    public int addAccountCollectActivitiesCommon(int accountId, int activitiesCommonId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", accountId);
        para.put("activitiesCommonId", activitiesCommonId);
        return getSqlSession().insert("CollectMapper.addAccountCollectActivitiesCommon", para);
    }
    
    @Override
    public int addAccountCollectSale(int accountId, int saleId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", accountId);
        para.put("saleId", saleId);
        return getSqlSession().insert("CollectMapper.addAccountCollectSale", para);
    }
    
}
