package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ActivitiesCustomDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ActivitiesCustomEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("activitiesCustomDao")
public class ActivitiesCustomDaoImpl extends BaseDaoImpl implements ActivitiesCustomDao
{
    
    @Override
    public ActivitiesCustomEntity findActivitiesInfoById(int id)
        throws DaoException
    {
        // String sql = "SELECT url FROM activities_custom WHERE id=? LIMIT 1";
        ActivitiesCustomEntity ace = this.getSqlSession().selectOne("ActivitiesCustomMapper.findActivitiesInfoById", id);
        return ace;
    }
    
}
