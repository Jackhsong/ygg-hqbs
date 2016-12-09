package com.ygg.webapp.dao;

import com.ygg.webapp.exception.DaoException;

public interface TempAccountDao
{
    /**
     * 新增临时账号
     *
     * @return
     */
    int addTempAccount(String imei)
        throws DaoException;
    
    /**
     * 根据imei查询对应的临时账号id
     *
     * @return
     */
    int findIdByImei(String imei)
        throws DaoException;
    
    /**
     * id是否存在
     *
     * @return
     */
    boolean idIsExist(int id)
        throws DaoException;
    
}