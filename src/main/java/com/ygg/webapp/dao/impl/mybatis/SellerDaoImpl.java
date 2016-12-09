package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SellerDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.SellerEntity;
import com.ygg.webapp.entity.SellerExpandEntity;
import com.ygg.webapp.exception.DaoException;

import java.util.List;
import java.util.Map;

@Repository("sellerDao")
public class SellerDaoImpl extends BaseDaoImpl implements SellerDao
{
    
    @Override
    public SellerEntity findBrandInfoById(int id)
        throws DaoException
    {
        // String sql = "SELECT seller_type,seller_name FROM seller WHERE id=? LIMIT 1";
        SellerEntity se = this.getSqlSession().selectOne("SellerMapper.findBrandInfoById", id);
        return se;
    }
    
    @Override
    public SellerEntity findOrderSellerInfoById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("SellerMapper.findOrderSellerInfoById", id);
    }
    
    @Override
    public int insertOrderSellerInfo(SellerEntity seller)
        throws DaoException
    {
        return getSqlSession().insert("SellerMapper.insertOrderSellerInfo", seller);
    }
    
    @Override
    public SellerEntity findSellerById(int sellerId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("SellerMapper.findSellerById", sellerId);
    }
    
    @Override
    public SellerExpandEntity findSellerExpandBySellerId(int sellerId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("SellerMapper.findSellerExpandBySellerId", sellerId);
    }

    @Override
    public List<Integer> findAllSellerActivitiesBlacklistId()
        throws DaoException
    {
        return this.getSqlSession().selectList("SellerMapper.findAllSellerActivitiesBlacklistId");
    }
    
    @Override
    public List<Map<String, Object>> findAllSellerPosgageBlacklistId()
        throws DaoException
    {
        return this.getSqlSession().selectList("SellerMapper.findAllSellerPosgageBlacklistId");
    }
}
