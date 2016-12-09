package com.ygg.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.CheckService;
import com.ygg.webapp.service.ThreadService;

public class ShoppingCartThreadServiceImpl extends Thread implements ThreadService
{
    
    private static Logger logger = Logger.getLogger(ShoppingCartThreadServiceImpl.class);
    
    @Resource(name = "shoppingCartDao")
    private ShoppingCartDao shoppingCartDao;
    
    @Resource(name = "checkService")
    private CheckService checkService;
    
    private boolean isshutdown = false;
    
    @Override
    public void init()
    {
        this.setName("ShoppingCartThreadServiceImpl");
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
                List<Integer> accountIds = findAccountIdsAndNoValid();
                // checkService.checkShoppingCartReferencesInfos(accountIds);
                
                if (accountIds == null || accountIds.size() == 0)
                {
                    // logger.warn("cart_lock_time is null ");
                    continue;
                }
                for (Integer accountId : accountIds)
                {
                    // / 2 到cart_lock_count 表中查询是否有记录
                    List<Integer> productIds = this.shoppingCartDao.findLockCountProductIdsByAccountId(accountId.intValue());
                    if (productIds == null || productIds.size() == 0)
                    {
                        // logger.warn("accountId:"+accountId+",还没有购物!");
//                        this.shoppingCartDao.updateIsValidByAccountId(accountId, 0); // 对于错误不同步的数据调用一次
                        continue;
                    }
//                    checkService.checkShoppingCartReferencesInfos(accountId, productIds);
                }
                
            }
            catch (ServiceException se)
            {
                logger.error("---ShoppingCartThreadServiceImpl--", se);
            }
            finally
            {
            }
        }
        
    }
    
    private List<Integer> findAccountIdsAndNoValid()
        throws ServiceException
    {
        List<Integer> accountIds = this.shoppingCartDao.findAccountIdsAndNoValid();
        return accountIds;
    }
    
}
