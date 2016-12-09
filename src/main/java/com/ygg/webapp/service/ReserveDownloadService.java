package com.ygg.webapp.service;

import com.ygg.webapp.entity.ReserveDownloadEntity;

public interface ReserveDownloadService
{
    
    public ReserveDownloadEntity findReserveDownLoad(String phonenum)
        throws Exception;
    
    public int insertReserveDownload(ReserveDownloadEntity rde)
        throws Exception;
    
    public boolean isPhoneNumExists(String phonenum)
        throws Exception;
    
    /**
     * 预约下载
     * 
     * @param phonenum
     * @throws Exception
     */
    String reserveDownload(String requestParams)
        throws Exception;
    
    public String getStoreName();
    
    public String getRealName();
    
    public String getDownLoadContentType();
    
}
