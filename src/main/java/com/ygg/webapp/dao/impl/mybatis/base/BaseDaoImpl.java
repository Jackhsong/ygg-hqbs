package com.ygg.webapp.dao.impl.mybatis.base;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.ygg.webapp.dao.BaseDao;

public class BaseDaoImpl /* extends SqlSessionDaoSupport */implements BaseDao
{
    
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate)
    {
        this.sqlSessionTemplate = sqlSessionTemplate;
        
    }
    
    protected SqlSessionTemplate getSqlSession()
    {
        return sqlSessionTemplate;
    }
}
