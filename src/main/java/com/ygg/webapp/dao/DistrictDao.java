package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.DistrictEntity;
import com.ygg.webapp.exception.DaoException;

public interface DistrictDao
{
    
    /**
     * 查询所有的区信息
     *
     * @return
     */
    List<DistrictEntity> findAllDistrictInfo()
        throws DaoException;
    
    /**
     * 根据市Id查出所有的区
     * 
     * @param cId
     * @return
     * @throws DaoException
     */
    List<DistrictEntity> findAllDistrictByCId(String cityId)
        throws DaoException;
    
    /**
     *
     * @param districtId
     * @return
     * @throws DaoException
     */
    String findDistinctNameById(String districtId)
        throws DaoException;
}