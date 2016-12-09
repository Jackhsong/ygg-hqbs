package com.ygg.webapp.dao.impl.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.PageCustomDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.PageCustomEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("pageCustomDao")
public class PageCustomDaoImpl extends BaseDaoImpl implements PageCustomDao
{
    
    @Override
    public List<PageCustomEntity> findPageCustomInfoByProductIds(int productId)
        throws DaoException
    {
        // String sql =
        // "SELECT `name`,url FROM page_custom AS p INNER JOIN (SELECT page_custom_id FROM relation_product_and_page_custom WHERE product_id=?) AS r ON p.id=r.page_custom_id;";
        List<PageCustomEntity> list = this.getSqlSession().selectList("PageCustomMapper.findPageCustomInfoByProductIds", productId);
        return list;
        
    }
    
}
