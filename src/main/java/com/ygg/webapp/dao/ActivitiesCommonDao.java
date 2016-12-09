package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.ActivitiesCommonEntity;
import com.ygg.webapp.exception.DaoException;

public interface ActivitiesCommonDao
{
    
    /**
     * 根据id查询对应的通用专场信息
     *
     * @return
     */
    ActivitiesCommonEntity findActivitiesInfoById(int id)
        throws DaoException;
    
    /**
     * 根据id查询关联的产品展示顺序id
     *
     * @return
     */
    List<Integer> findProductIdsById(int id)
        throws DaoException;
    
    public float findPriceById(int id)
        	throws DaoException;
}