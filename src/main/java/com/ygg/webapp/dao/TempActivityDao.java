package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.exception.DaoException;

public interface TempActivityDao
{
    
    List<Map<String, Object>> findAllTempActivityDetails()
        throws DaoException;
    
    List<String> findAllActivityNames()
        throws DaoException;
    
    List<Integer> findAnniversaryProductIds(int timeType)
        throws DaoException;
    
    List<Integer> findAnniversaryGroupRecommendProductIds()
        throws DaoException;
    
    List<Map<String, Object>> findAnniversaryGroup()
        throws DaoException;
    
    List<Integer> findAnniversaryCurrentProductIds(int timeType)
        throws DaoException;
    
    List<Map<String, Object>> findAnniversaryCurrentGroup()
        throws DaoException;
    
    List<Map<String, Object>> findAnniversaryCurrentHotTopProductIds()
        throws DaoException;
}
