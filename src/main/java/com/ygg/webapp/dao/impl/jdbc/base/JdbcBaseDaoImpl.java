package com.ygg.webapp.dao.impl.jdbc.base;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ygg.webapp.dao.BaseDao;

public class JdbcBaseDaoImpl implements BaseDao
{
    
    @Resource
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate()
    {
        return jdbcTemplate;
    }
    
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }
    
}
