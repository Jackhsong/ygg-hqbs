package com.ygg.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.CheckService;
import com.ygg.webapp.service.ThreadService;

public class TempShoppingCartThreadServiceImpl extends Thread implements ThreadService
{
    
    private static Logger logger = Logger.getLogger(TempShoppingCartThreadServiceImpl.class);
    
    private boolean isshutdown = false;
    
    @Resource(name = "checkService")
    private CheckService checkService;
    
    @Resource(name = "tempShoppingCartDao")
    private TempShoppingCartDao tempShoppingCartDao;
    
    @Override
    public void init()
    {
        this.setName("TempShoppingCartThreadServiceImpl");
        this.start();
    }
    
    @Override
    public void destory()
    {
        isshutdown = true;
    }
    
    @Override
    public void run()
    {
        while (!isshutdown)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                logger.error(e);
            }
            try
            {
                /**
                 * 本方法中只查询不带事务处理，尽可能减少事处的处理力度
                 */
                List<Integer> tempAccountIds = findTempAccountIdsAndNoValid();
                // checkService.checkShoppingCartReferencesInfos(accountIds);
                
                if (tempAccountIds == null || tempAccountIds.size() == 0)
                {
                    
                    // logger.warn("temp_cart_lock_time is null ");
                    continue;
                }
                for (Integer tempAccountId : tempAccountIds)
                {
                    // / 2 到cart_lock_count 表中查询是否有记录
                    List<Integer> productIds = this.tempShoppingCartDao.findTempLockCountProductIdsByAccountId(tempAccountId.intValue());
                    if (productIds == null || productIds.size() == 0)
                    {
                        this.tempShoppingCartDao.updateIsValidByTempAccountId(tempAccountId, 0); // 对于错误不同步的数据调用一次
                        // logger.warn("tempAccountId:"+tempAccountId+",还没有购物!");
                        continue;
                    }
                    checkService.checkTempShoppingCartReferencesInfos(tempAccountId, productIds);
                }
                
            }
            catch (ServiceException se)
            {
                logger.error("--------TempShoppingCartThreadServiceImpl-----------", se);
            }
            finally
            {
            }
        }
    }
    
    private List<Integer> findTempAccountIdsAndNoValid()
        throws ServiceException
    {
        List<Integer> tempaccountIds = this.tempShoppingCartDao.findTempAccountIdsAndNoValid();
        return tempaccountIds;
    }
    
}
