package com.ygg.webapp.service;

import java.util.Map;

import com.ygg.webapp.exception.ServiceException;

public interface GroupBuyService
{
    /**
     * 查询 团购商品 相关信息
     * 
     * @param groupProductId
     * @return
     * @throws Exception
     */
    Map<String, Object> findGroupProductInfo(int groupProductId)
        throws ServiceException;
}
