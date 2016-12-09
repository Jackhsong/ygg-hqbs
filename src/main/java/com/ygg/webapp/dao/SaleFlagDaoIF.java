package com.ygg.webapp.dao;

import com.ygg.webapp.exception.DaoException;

public interface SaleFlagDaoIF
{
    
    /**
     * 根据id查询对应的国旗image
     * 
     * @return
     */
    String findImageById(int id)
        throws DaoException;
}
