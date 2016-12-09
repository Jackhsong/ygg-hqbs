package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SmsVerifyDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.SmsVerifyEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("smsVerifyDao")
public class SmsVerifyDaoImpl extends BaseDaoImpl implements SmsVerifyDao
{
    
    @Override
    public SmsVerifyEntity findSmsVerifyByMobile(String mobile, byte type)
        throws DaoException
    {
        // String sql =
        // "SELECT id,mobile_number,code,type,is_used,valid_time,update_time from sms_verify WHERE mobile_number=? AND type=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", mobile);
        params.put("type", type);
        SmsVerifyEntity se = this.getSqlSession().selectOne("SmsVerifyMapper.findSmsVerifyByMobile", params);
        return se;
    }
    
    @Override
    public int addSmsVerify(SmsVerifyEntity sve)
        throws DaoException
    {
        // String sql = "INSERT INTO sms_verify(mobile_number,code,type,valid_time,create_time) VALUES(?,?,?,?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobilenumber", sve.getMobileNumber());
        params.put("code", sve.getCode());
        params.put("type", sve.getType());
        params.put("validtime", new Timestamp(Long.parseLong(sve.getValidTime())));
        params.put("createtime", new Timestamp(Long.parseLong(sve.getCreateTime())));
        return this.getSqlSession().insert("SmsVerifyMapper.addSmsVerify", params);
        
    }
    
    @Override
    public int updateSmsVerifyById(SmsVerifyEntity sve)
        throws DaoException
    {
        // String sql = "UPDATE sms_verify SET code=?,is_used=?,valid_time=? where id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("code", sve.getCode());
        params.put("isused", sve.getIsUsed());
        params.put("validtime", new Timestamp(Long.parseLong(sve.getValidTime())));
        params.put("id", sve.getId());
        return this.getSqlSession().update("SmsVerifyMapper.updateSmsVerifyById", params);
    }
    
}
