package com.ygg.webapp.dao.impl.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ActivitiesCommonEntity;
import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("activitiesCommonDao")
public class ActivitiesCommonDaoImpl extends BaseDaoImpl implements ActivitiesCommonDao
{
    
    @Override
    public ActivitiesCommonEntity findActivitiesInfoById(int id)
        throws DaoException
    {
        // String sql = "SELECT name,image FROM activities_common WHERE id=? LIMIT 1";
        ActivitiesCommonEntity ace = (ActivitiesCommonEntity)this.getSqlSession().selectOne("ActivitiesCommonMapper.findActivitiesInfoById", id);
        return ace;
    }
    
    @Override
    public List<Integer> findProductIdsById(int id)
        throws DaoException
    {
        // String sql =
        // "SELECT product_id FROM relation_activities_common_and_product WHERE activities_common_id=? ORDER BY `order` DESC";
        List<Integer> list = this.getSqlSession().selectList("ActivitiesCommonMapper.findProductIdsById", id);
        return list;
    }
    
    
    public float findPriceById(int id)
    	throws DaoException
    {
        List<Integer> list = this.getSqlSession().selectList("ActivitiesCommonMapper.findProductIdsById", id);
        int productId = list.get(0);
        ProductCommonEntity pe = this.getSqlSession().selectOne("ProductCommonMapper.findProductCommonInfoById", productId);
        return pe.getSalesPrice();
    }
    
}
