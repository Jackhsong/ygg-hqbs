package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.exception.DaoException;

public interface PlatformConfigDao
{
    
    public List<Map<String, Object>> findAllPlateformConfig()
        throws DaoException;
    
    public List<Map<String, Object>> findVariablePlateformConfig()
        throws DaoException;
    
}
