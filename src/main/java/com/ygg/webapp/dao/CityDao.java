package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.CityEntity;
import com.ygg.webapp.exception.DaoException;

public interface CityDao
{
    
    /**
     * 查询所有的市信息
     *
     * @return
     */
    List<CityEntity> findAllCityInfo()
        throws DaoException;
    
    /**
     * 查cityName
     * 
     * @param cityId
     * @return
     * @throws DaoException
     */
    String findCityNameById(String cityId)
        throws DaoException;
    
    /**
     * 根据省ID查出下面所有的市
     * 
     * @param pId
     * @return
     * @throws DaoException
     */
    List<CityEntity> findAllCityByPID(String provinceId)
        throws DaoException;
}