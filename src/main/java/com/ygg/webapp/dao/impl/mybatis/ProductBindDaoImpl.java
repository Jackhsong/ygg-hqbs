package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ProductBindDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ProductBindEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("productBindDao")
public class ProductBindDaoImpl extends BaseDaoImpl implements ProductBindDao
{
    
    @Override
    public ProductBindEntity findProductBindInfoById(int id)
        throws DaoException
    {
        // String sql = "SELECT sale_window_id FROM product_bind WHERE product_id=? LIMIT 1";
        ProductBindEntity pe = this.getSqlSession().selectOne("ProductBindMapper.findProductBindInfoById", id);
        return pe;
    }
    
}
