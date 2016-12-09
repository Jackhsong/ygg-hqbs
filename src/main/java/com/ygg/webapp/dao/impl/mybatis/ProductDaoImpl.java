package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ProductBaseEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("productDao")
public class ProductDaoImpl extends BaseDaoImpl implements ProductDao
{
    
    @Override
    public ProductEntity findProductInfoById(int id)
        throws DaoException
    {
        /*
         * String sql = "SELECT * FROM product WHERE id=? LIMIT 1"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, id)); return queryOneInfo(sql,
         * params);
         */
        ProductEntity pe = this.getSqlSession().selectOne("ProductMapper.findProductInfoById", id);
        return pe;
    }
    
    @Override
    public List<Integer> findAllProductIds()
        throws DaoException
    {
        
        List<Integer> list = this.getSqlSession().selectList("ProductMapper.findAllProductIds");
        if (list == null)
            list = new ArrayList<Integer>();
        return list;
    }
    
    @Override
    public ProductEntity findProductPartById(int id)
        throws DaoException
    {
        //String sql = "SELECT freight_template_id,seller_id,id FROM product WHERE id=? LIMIT 1";
        ProductEntity pe = this.getSqlSession().selectOne("ProductMapper.findProductPartById", id);
        return pe;
    }
    
    @Override
    public List<Map<String, Object>> findDisplayHotProduct()
        throws DaoException
    {
        return getSqlSession().selectList("ProductMapper.findDisplayHotProduct");
    }
    
    @Override
    public String findGegeImageById(int gegeImageId)
        throws DaoException
    {
        return getSqlSession().selectOne("ProductMapper.findGegeImageById", gegeImageId);
    }
    
    @Override
    public Map<String, Object> findProductWelfareByProductId(int productId)
        throws Exception
    {
        return getSqlSession().selectOne("ProductMapper.findProductWelfareByProductId", productId);
    }
    
    @Override
    public ProductBaseEntity findProductBaseById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("ProductMapper.findProductBaseById", id);
    }
    
    @Override
    public List<Map<String, Object>> findDeliverAreaInfosByBpId(int productBaseId)
        throws DaoException
    {
        return getSqlSession().selectList("ProductMapper.findDeliverAreaInfosByBpId", productBaseId);
    }
}
