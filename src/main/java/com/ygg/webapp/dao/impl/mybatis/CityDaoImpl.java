package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CityEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("cityDao")
public class CityDaoImpl extends BaseDaoImpl implements CityDao
{
    
    @Override
    public List<CityEntity> findAllCityInfo()
        throws DaoException
    {
        /*
         * String sql = "SELECT city_id,province_id,name FROM city"; return queryAllInfo2T(sql);
         */
        List<CityEntity> list = this.getSqlSession().selectList("AreaMapper.findAllCityInfo");
        if (list == null)
            list = new ArrayList<CityEntity>();
        return list;
    }
    
    @Override
    public List<CityEntity> findAllCityByPID(String provinceId)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("provinceId", provinceId);
        List<CityEntity> list = this.getSqlSession().selectList("AreaMapper.findAllCityByPID", map);
        if (list == null)
            list = new ArrayList<CityEntity>();
        return list;
    }
    
    @Override
    public String findCityNameById(String cityId)
        throws DaoException
    {
        Object cityName = this.getSqlSession().selectOne("AreaMapper.findCityNameById", cityId);
        if (cityName == null)
            return "";
        return cityName.toString();
    }
    
}
