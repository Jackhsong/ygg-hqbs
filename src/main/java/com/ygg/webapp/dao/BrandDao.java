package com.ygg.webapp.dao;

import com.ygg.webapp.entity.BrandEntity;
import com.ygg.webapp.exception.DaoException;

public interface BrandDao
{
    
    /**
     * 根据id查询对应的商家信息
     *
     * @return
     */
    BrandEntity findBrandInfoById(int id)
        throws DaoException;
    
}