package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.PageCustomEntity;
import com.ygg.webapp.exception.DaoException;

public interface PageCustomDao
{
    
    /**
     * 根据商品id查询对应的自定义页面信息
     *
     * @return
     */
    List<PageCustomEntity> findPageCustomInfoByProductIds(int productId)
        throws DaoException;
    
}