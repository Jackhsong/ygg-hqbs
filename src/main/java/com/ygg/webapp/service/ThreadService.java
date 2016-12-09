package com.ygg.webapp.service;

import java.util.List;

import com.ygg.webapp.exception.ServiceException;

public interface ThreadService
{
    
    public void init();
    
    public void destory();
    
    /**
     * 系统启动时检查购物车中锁定时间
     */
    // public void checkShoppingCartReferencesInfos() ; // throws ServiceException;
    
}
