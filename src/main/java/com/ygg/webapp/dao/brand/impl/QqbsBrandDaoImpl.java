package com.ygg.webapp.dao.brand.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.brand.QqbsBrandDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.brand.QqbsBrandEntity;

@Repository
public class QqbsBrandDaoImpl extends BaseDaoImpl implements QqbsBrandDao
{

    @Override
    public List<QqbsBrandEntity> getQqbsBrands() throws Exception
    {
        return this.getSqlSession().selectList("QqbsBrandCtgMapper.getQqbsBrands");
    }
    
}
