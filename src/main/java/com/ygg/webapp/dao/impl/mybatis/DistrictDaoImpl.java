package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.DistrictDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.DistrictEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("districtDao")
public class DistrictDaoImpl extends BaseDaoImpl implements DistrictDao
{
    
    @Override
    public List<DistrictEntity> findAllDistrictInfo()
        throws DaoException
    {
        List<DistrictEntity> list = this.getSqlSession().selectList("AreaMapper.findAllDistrictInfo");
        if (list == null)
            list = new ArrayList<DistrictEntity>();
        return list;
    }
    
    @Override
    public List<DistrictEntity> findAllDistrictByCId(String cityId)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cityId", cityId);
        List<DistrictEntity> list = this.getSqlSession().selectList("AreaMapper.findAllDistrictByCId", map);
        if (list == null)
            list = new ArrayList<DistrictEntity>();
        return list;
    }
    
    @Override
    public String findDistinctNameById(String districtId)
        throws DaoException
    {
        Object districtName = this.getSqlSession().selectOne("AreaMapper.findDistinctNameById", districtId);
        if (districtName == null)
            return "";
        return districtName.toString();
    }
    
}
