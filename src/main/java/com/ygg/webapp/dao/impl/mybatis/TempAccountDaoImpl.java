package com.ygg.webapp.dao.impl.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.TempAccountDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("tempAccountDao")
public class TempAccountDaoImpl extends BaseDaoImpl implements TempAccountDao
{
    
    @Override
    public int addTempAccount(String imei)
        throws DaoException
    {
        // String sql = "INSERT INTO temp_account(imei) VALUES(?)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imei", imei);
        return this.getSqlSession().insert("TempAccountMapper.addTempAccount", map);
    }
    
    @Override
    public int findIdByImei(String imei)
        throws DaoException
    {
        // String sql = "SELECT id FROM temp_account WHERE imei=? LIMIT 1";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imei", imei);
        Integer id = this.getSqlSession().selectOne("TempAccountMapper.findIdByImei", map);
        if (id == null)
            id = CommonConstant.ID_NOT_EXIST;
        return id;
    }
    
    @Override
    public boolean idIsExist(int tid_)
        throws DaoException
    {
        // String sql = "SELECT id FROM temp_account WHERE id=? LIMIT 1";
        Integer id = this.getSqlSession().selectOne("TempAccountMapper.idIsExist", tid_);
        if (id == null || id.intValue() <= 0)
            return false;
        return true;
    }
    
}
