package com.ygg.webapp.dao;

import com.ygg.webapp.entity.ActivitiesCustomEntity;
import com.ygg.webapp.exception.DaoException;

public interface ActivitiesCustomDao
{
    
    /**
     * 根据id查询对应的自定义专场信息
     *
     * @return
     */
    ActivitiesCustomEntity findActivitiesInfoById(int id)
        throws DaoException;
    
}