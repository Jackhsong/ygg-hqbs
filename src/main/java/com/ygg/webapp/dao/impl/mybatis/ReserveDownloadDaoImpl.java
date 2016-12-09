package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ReserveDownloadDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ReserveDownloadEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("reserveDownloadDao")
public class ReserveDownloadDaoImpl extends BaseDaoImpl implements ReserveDownloadDao
{
    
    @Override
    public ReserveDownloadEntity findReserveDownLoad(String phonenum)
        throws DaoException
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phonenum", phonenum);
        ReserveDownloadEntity rde = this.getSqlSession().selectOne("ReserveDownloadMapper.findReserveDownLoad", map);
        return rde;
    }
    
    @Override
    public int insertReserveDownload(ReserveDownloadEntity rde)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phonenum", rde.getPhonenum());
        map.put("createtime", new Timestamp(System.currentTimeMillis()));
        return this.getSqlSession().insert("ReserveDownloadMapper.insertReserveDownload", map);
    }
    
    @Override
    public boolean isPhoneNumExists(String phonenum)
        throws DaoException
    {
        ReserveDownloadEntity rde = this.findReserveDownLoad(phonenum);
        if (rde != null)
            return true;
        return false;
    }
    
}
