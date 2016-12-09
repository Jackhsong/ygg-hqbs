package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SpecialActivityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.SpecialActivityEntity;
import com.ygg.webapp.entity.SpecialActivityGroupEntity;
import com.ygg.webapp.entity.SpecialActivityModelEntity;
import com.ygg.webapp.entity.SpecialActivityModelLayoutEntity;
import com.ygg.webapp.entity.SpecialActivityModelLayoutProductEntity;
import com.ygg.webapp.entity.SpecialActivityNewEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("specialActivityDao")
public class SpecialActivityDaoImpl extends BaseDaoImpl implements SpecialActivityDao
{
    
    @Override
    public SpecialActivityEntity findSpecialActivityById(int activityId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("SpecialActivityMapper.findSpecialActivityById", activityId);
    }
    
    @Override
    public List<Map<String, Object>> findSpecialActivityLayout(int activityId)
        throws DaoException
    {
        List<Map<String, Object>> reList = this.getSqlSession().selectList("SpecialActivityMapper.findSpecialActivityLayout", activityId);
        return reList == null ? new ArrayList<Map<String, Object>>() : reList;
    }
    
    @Override
    public List<Map<String, Object>> findSpecialActivityLayouProduct(int layouId)
        throws DaoException
    {
        List<Map<String, Object>> reList = this.getSqlSession().selectList("SpecialActivityMapper.findSpecialActivityLayouProduct", layouId);
        return reList == null ? new ArrayList<Map<String, Object>>() : reList;
    }
    
    @Override
    public SpecialActivityNewEntity findSpecialActivityNewById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("SpecialActivityMapper.findSpecialActivityNewById", id);
    }
    
    @Override
    public List<Map<String, Object>> findSpecialActivityNewProductByActIdAndType(int actId, int type)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("type", type);
        para.put("specialActivityNewId", actId);
        return getSqlSession().selectList("SpecialActivityMapper.findSpecialActivityNewProductByActIdAndType", para);
    }
    
    @Override
    public int countSpecialActivityNewProductByActIdAndType(int actId, int type)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("type", type);
        para.put("specialActivityNewId", actId);
        return getSqlSession().selectOne("SpecialActivityMapper.countSpecialActivityNewProductByActIdAndType", para);
    }
    
    @Override
    public SpecialActivityGroupEntity findSpecialActivityGroupById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("SpecialActivityMapper.findSpecialActivityGroupById", id);
    }
    
    @Override
    public List<Map<String, Object>> findSpecialActivityGroupProductByActIdAndType(int id, int type)
        throws DaoException
    {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("activityId", id);
        parameter.put("type", type);
        return getSqlSession().selectList("SpecialActivityMapper.findSpecialActivityGroupProductByActIdAndType", parameter);
    }
    
    @Override
    public SpecialActivityModelEntity findSpecialActivityModelById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("SpecialActivityMapper.findSpecialActivityModelById", id);
    }
    
    @Override
    public List<SpecialActivityModelLayoutEntity> findSpecialActivityModelLayoutsBysamId(int id)
        throws DaoException
    {
        return getSqlSession().selectList("SpecialActivityMapper.findSpecialActivityModelLayoutsBysamId", id);
    }
    
    @Override
    public List<SpecialActivityModelLayoutProductEntity> findSpecialActivityModelLayoutProductsByLayoutId(int id)
        throws DaoException
    {
        return getSqlSession().selectList("SpecialActivityMapper.findSpecialActivityModelLayoutProductsByLayoutId", id);
    }
}
