package com.ygg.webapp.dao;

import com.ygg.webapp.entity.ReserveDownloadEntity;
import com.ygg.webapp.exception.DaoException;

public interface ReserveDownloadDao
{
    
    public ReserveDownloadEntity findReserveDownLoad(String phonenum)
        throws DaoException;
    
    public int insertReserveDownload(ReserveDownloadEntity rde)
        throws DaoException;
    
    public boolean isPhoneNumExists(String phonenum)
        throws DaoException;
}
