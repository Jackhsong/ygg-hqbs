package com.ygg.webapp.dao;

import com.ygg.webapp.entity.SmsVerifyEntity;
import com.ygg.webapp.exception.DaoException;

public interface SmsVerifyDao
{
    
    /**
     * 根据手机号和类型查询对应的短信校验信息
     *
     * @return
     */
    SmsVerifyEntity findSmsVerifyByMobile(String mobile, byte type)
        throws DaoException;
    
    /**
     * 新增短信校验信息
     *
     * @return
     */
    int addSmsVerify(SmsVerifyEntity sve)
        throws DaoException;
    
    /**
     * 根据id更新短信校验信息
     *
     * @return
     */
    int updateSmsVerifyById(SmsVerifyEntity sve)
        throws DaoException;
    
}