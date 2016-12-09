package com.ygg.webapp.service;

import com.ygg.webapp.exception.ServiceException;

public interface OrderRefundLogisticService
{
    
    /**
     * 增加物流单号
     * 
     * @param resquestParams
     * @return
     * @throws ServiceException
     */
    public String addLogisticInfo(String resquestParams)
        throws ServiceException;
    
}
