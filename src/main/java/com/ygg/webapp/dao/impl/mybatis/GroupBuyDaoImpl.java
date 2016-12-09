package com.ygg.webapp.dao.impl.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.GroupBuyDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.GroupAccountInfoEntity;
import com.ygg.webapp.entity.GroupProductCodeEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("groupBuyDao")
public class GroupBuyDaoImpl extends BaseDaoImpl implements GroupBuyDao
{
    
    @Override
    public GroupProductCodeEntity findGroupProductCodeById(int id)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("id", id);
        List<GroupProductCodeEntity> reList = findGroupProdutCodeByPara(para);
        if (reList.size() > 0)
        {
            return reList.get(0);
        }
        return null;
    }
    
    @Override
    public List<GroupProductCodeEntity> findGroupProdutCodeByPara(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().selectList("GroupBuyMapper.findGroupProdutByPara", para);
    }
    
    @Override
    public List<GroupAccountInfoEntity> findGroupAccountInfoByPara(Map<String, Object> para)
        throws DaoException
    {
        return null;
    }
    
    @Override
    public List<Map<String, Object>> findGroupAccountInfoByGroupProductCodeId(int groupProductCodeId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("groupProductCodeId", groupProductCodeId);
        return getSqlSession().selectList("GroupBuyMapper.findGroupAccountInfoByPara", para);
    }
    
}
