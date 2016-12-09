package com.ygg.webapp.service;

import java.util.Map;

public interface IntegralService
{
    /**
     * 获取用户积分信息
     * @param accountId  用户ID
     * @param getIntegralDetail  是否获取明细信息
     * @return
     * @throws Exception
     */
    Map<String, Object> getIntegralInfo(int accountId, boolean getIntegralDetail)
        throws Exception;
}