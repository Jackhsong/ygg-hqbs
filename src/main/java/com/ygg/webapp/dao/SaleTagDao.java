package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.exception.DaoException;

public interface SaleTagDao
{
    
    /**
     * 根据id列表查询对应的标签image列表
     *
     * @return
     */
    List<String> findImagesByIds(List<Integer> ids)
        throws DaoException;
    
    /**
     * 根据特卖id列表查询对应的标签image列表
     *
     * @return
     */
    List<List<String>> findImagesBySaleIds(List<Integer> saleIds)
        throws DaoException;
}