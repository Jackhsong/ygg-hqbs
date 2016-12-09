package com.ygg.webapp.dao.impl.mybatis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.PlatformConfigDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.exception.DaoException;

@Repository("platformConfigDao")
public class PlatformConfigDaoImpl extends BaseDaoImpl implements PlatformConfigDao
{
    
    @Override
    public List<Map<String, Object>> findAllPlateformConfig()
        throws DaoException
    {
        List<Map<String, Object>> list = null;
        list = this.getSqlSession().selectList("PlateformConfigMapper.findAllPlateformConfig");
        return list;
    }
    
    @Override
    public List<Map<String, Object>> findVariablePlateformConfig()
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        List<String> keyList = Arrays.asList("is_register_coupon", "static_css_version", "static_js_version");
        para.put("keyList", keyList);
        return this.getSqlSession().selectList("PlateformConfigMapper.findPlateformConfigByPara", para);
    }
    
}
