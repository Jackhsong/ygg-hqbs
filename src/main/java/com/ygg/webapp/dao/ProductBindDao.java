package com.ygg.webapp.dao;

import com.ygg.webapp.entity.ProductBindEntity;
import com.ygg.webapp.exception.DaoException;

public interface ProductBindDao
{
    /**
     * 根据商品id查询对应的商品绑定信息
     *
     * @return
     */
    ProductBindEntity findProductBindInfoById(int id)
        throws DaoException;
}