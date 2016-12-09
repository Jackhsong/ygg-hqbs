package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SaleFlagDaoIF;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.exception.DaoException;

@Repository("saleFlagDaoIF")
public class SaleFlagDaoImpl extends BaseDaoImpl implements SaleFlagDaoIF
{
    
    @Override
    public String findImageById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("SaleFlagMapper.findImageById", id);
    }
    

    
}
