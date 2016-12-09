package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ProvinceDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ProvinceEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("provinceDao")
public class ProvinceDaoImpl extends BaseDaoImpl implements ProvinceDao
{
    
    @Override
    public List<ProvinceEntity> findAllProvinceInfo()
        throws DaoException
    {
        /*
         * String sql = "SELECT province_id,name FROM province"; return queryAllInfo2T(sql);
         */
        List<ProvinceEntity> list = this.getSqlSession().selectList("AreaMapper.findAllProvinceInfo");
        if (list == null)
            list = new ArrayList<ProvinceEntity>();
        return list;
    }
    
    @Override
    public List<Map<String, Object>> findFreightInfoByFreightId(int freightId)
        throws DaoException
    {
        String sql = "SELECT province_id,freight_money FROM relation_freight_template_and_province WHERE freight_template_id=?";
        
        List<Map<String, Object>> list = this.getSqlSession().selectList("AreaMapper.findFreightInfoByFreightId", freightId);
        if (list == null || list.isEmpty())
            list = new ArrayList<Map<String, Object>>();
        return list;
    }
    
    @Override
    public int findMoneyByFIdAndPId(int freightId, int provinceId)
        throws DaoException
    {
        String sql = "SELECT freight_money FROM relation_freight_template_and_province WHERE freight_template_id=? AND province_id=? LIMIT 1";
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("freightId", freightId);
        map.put("provinceId", provinceId);
        Integer fm = this.getSqlSession().selectOne("AreaMapper.findMoneyByFIdAndPId", map);
        if (fm == null)
            fm = 0;
        return fm;
    }
    
    @Override
    public String findProvinceNameById(String provinceId)
        throws DaoException
    {
        
        Object provinceName = this.getSqlSession().selectOne("AreaMapper.findProvinceNameById", provinceId);
        if (provinceName == null)
            return "";
        return provinceName.toString();
    }
    
}
