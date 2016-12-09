package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.ProvinceEntity;
import com.ygg.webapp.exception.DaoException;

public interface ProvinceDao
{
    
    /**
     * 查询所有的省份信息
     *
     * @return
     */
    List<ProvinceEntity> findAllProvinceInfo()
        throws DaoException;
    
    /**
     * 根据provinceId查provinceName
     * 
     * @param provinceId
     * @return
     * @throws DaoException
     */
    String findProvinceNameById(String provinceId)
        throws DaoException;
    
    /**
     * 根据运费模板id查询运费信息
     *
     * @return
     */
    List<Map<String, Object>> findFreightInfoByFreightId(int freightId)
        throws DaoException;
    
    /**
     * 根据运费模板id和省份id查询运费
     *
     * @return
     */
    int findMoneyByFIdAndPId(int freightId, int provinceId)
        throws DaoException;
}