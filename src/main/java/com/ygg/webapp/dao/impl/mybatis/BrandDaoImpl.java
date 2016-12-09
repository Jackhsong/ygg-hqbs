package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.BrandDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.BrandEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("brandDao")
public class BrandDaoImpl extends BaseDaoImpl implements BrandDao
{
    
    @Override
    public BrandEntity findBrandInfoById(int id)
        throws DaoException
    {
        String sql = "SELECT name FROM brand WHERE id=? LIMIT 1";
        BrandEntity be = this.getSqlSession().selectOne("BrandMapper.findBrandInfoById", id);
        return be;
    }
    
}
