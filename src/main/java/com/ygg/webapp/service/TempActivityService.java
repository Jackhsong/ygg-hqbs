package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.exception.ServiceException;

public interface TempActivityService
{
    
    /**
     * 访问活动
     * @param type：来源，1：手机网页；2：app
     * @return
     */
    List<Map<String, Object>> findAllTempActivity(int type)
        throws ServiceException;
    
    Map<String, Object> findAnniversaryProduct(int clientType)
        throws ServiceException;
    
    String getAnniversaryProductDate(int timeType, int clientType)
        throws ServiceException;
    
    Map<String, Object> findAnniversaryCurrentProduct(int clientType)
        throws ServiceException;
    
    String getAnniversaryCurrentProductData(int timeType, int clientType)
        throws ServiceException;
    
}
