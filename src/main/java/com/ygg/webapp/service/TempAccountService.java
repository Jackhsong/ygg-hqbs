package com.ygg.webapp.service;

public interface TempAccountService
{
    
    /**
     * 从用户cookie中拿出一个uuid查出tempaccountId
     * 
     * @param imei
     * @return
     * @throws Exception
     */
    int findTempAccountIdByUUID(String imei)
        throws Exception;
    
    /**
     * 从用户cookie中拿出一个uuid　 在临时账号表中增加一条，先判断是否已存在
     * 
     * @param imei
     * @return
     * @throws Exception
     */
    int addTempAccount(String imei)
        throws Exception;
    
}
