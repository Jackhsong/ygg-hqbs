package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.CustomLayoutEntity;
import com.ygg.webapp.exception.DaoException;

public interface CustomLayoutDaoIF
{
    /**
     * 查询自定义布局信息根据布局id
     * 
     * @return
     */
    List<CustomLayoutEntity> findCustomLayoutsByLayoutIds(List<Integer> customLayoutIds)
        throws DaoException;
    
    /**
     * 查询当前展示的自定义版块信息
     * 
     * @return
     */
    List<Map<String, Object>> findDisplayCustomRegion()
        throws DaoException;
    
    /**
     * 查询自定义布局id根据版块id
     * 
     * @return
     */
    List<Integer> findLayoutIdByRegionId(int customRegionId)
        throws DaoException;
}
