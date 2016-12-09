package com.ygg.webapp.dao.impl.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ProductMobileDetailDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ProductMobileDetailEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("productMobileDetailDao")
public class ProductMobileDetailDaoImpl extends BaseDaoImpl implements ProductMobileDetailDao
{
    
    @Override
    public List<ProductMobileDetailEntity> findMobileDetailInfoByProductId(int productId)
        throws DaoException
    {
        // String sql = "SELECT * FROM product_mobile_detail WHERE product_id=? ORDER BY `order` DESC";
        List<ProductMobileDetailEntity> list = this.getSqlSession().selectList("ProductMapper.findMobileDetailInfoByProductId", productId);
        return list;
    }
    
}
