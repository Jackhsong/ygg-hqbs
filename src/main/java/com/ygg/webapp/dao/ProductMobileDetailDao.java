package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.ProductMobileDetailEntity;
import com.ygg.webapp.exception.DaoException;

public interface ProductMobileDetailDao
{
    
    /**
     * 根据商品id查询对应的多个商品移动详情信息
     *
     * @return
     */
    List<ProductMobileDetailEntity> findMobileDetailInfoByProductId(int productId)
        throws DaoException;
    
}