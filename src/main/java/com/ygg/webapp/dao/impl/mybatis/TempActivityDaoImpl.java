package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.TempActivityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.exception.DaoException;

@Repository("tempActivityDao")
public class TempActivityDaoImpl extends BaseDaoImpl implements TempActivityDao
{
    
    @Override
    public List<Map<String, Object>> findAllTempActivityDetails()
        throws DaoException
    {
        List<Map<String, Object>> reList = this.getSqlSession().selectList("TempActivityMapper.findAllTempActivityDetails");
        return reList == null ? new ArrayList<Map<String, Object>>() : reList;
    }
    
    @Override
    public List<String> findAllActivityNames()
        throws DaoException
    {
        List<String> reList = this.getSqlSession().selectList("TempActivityMapper.findAllActivityNames");
        return reList == null ? new ArrayList<String>() : reList;
    }
    
    @Override
    public List<Integer> findAnniversaryProductIds(int timeType)
        throws DaoException
    {
        return getSqlSession().selectList("TempActivityMapper.findAnniversaryProductIds", timeType);
    }
    
    @Override
    public List<Integer> findAnniversaryGroupRecommendProductIds()
        throws DaoException
    {
        return getSqlSession().selectList("TempActivityMapper.findAnniversaryGroupRecommendProductIds");
    }
    
    @Override
    public List<Map<String, Object>> findAnniversaryGroup()
        throws DaoException
    {
        return getSqlSession().selectList("TempActivityMapper.findAnniversaryGroup");
    }
    
    @Override
    public List<Integer> findAnniversaryCurrentProductIds(int timeType)
        throws DaoException
    {
        return getSqlSession().selectList("TempActivityMapper.findAnniversaryCurrentProductIds", timeType);
    }
    
    @Override
    public List<Map<String, Object>> findAnniversaryCurrentGroup()
        throws DaoException
    {
        return getSqlSession().selectList("TempActivityMapper.findAnniversaryCurrentGroup");
    }
    
    @Override
    public List<Map<String, Object>> findAnniversaryCurrentHotTopProductIds()
        throws DaoException
    {
        return getSqlSession().selectList("TempActivityMapper.findAnniversaryCurrentHotTopProductIds");
    }
}
