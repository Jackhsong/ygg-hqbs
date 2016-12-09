package com.ygg.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.CheckService;
import com.ygg.webapp.service.ThreadService;
import com.ygg.webapp.util.CommonEnum;

public class OrderThreadServiceImpl extends Thread implements ThreadService
{
    
    private static Logger logger = Logger.getLogger(OrderThreadServiceImpl.class);
    
    private boolean isshutdown = false;
    
    @Resource(name = "checkService")
    private CheckService checkService;
    
    @Resource(name = "productCountDao")
    private ProductCountDao productCountDao;
    
    @Override
    public void init()
    {
        this.setName("OrderThreadServiceImpl");
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
                List<Integer> orderIds = findTempAccountIdsAndNoValid();
                // checkService.checkShoppingCartReferencesInfos(accountIds);
                
                if (orderIds == null || orderIds.size() == 0)
                {
                    // logger.warn("order_lock_time is null ");
                    continue;
                }
                for (Integer orderId : orderIds)
                {
                    // / 2 到order_product_lock_count 表中查询是否有记录
                    List<Integer> productIds = this.productCountDao.findLockCountProductIdsByAccountId(orderId.intValue());
                    if (productIds == null || productIds.size() == 0)
                    {
                        // logger.warn("orderId:"+orderId+",还没有生成定单!");
                        this.productCountDao.updateIsValidByOrderId(orderId, 0); // 对于错误不同步的数据调用一次
                        continue;
                    }
                    checkService.checkOrderCartReferencesInfos(orderId, productIds);
                }
                
            }
            catch (ServiceException se)
            {
                logger.error("------OrderThreadServiceImpl------", se);
            }
            finally
            {
            }
        }
    }
    
    private List<Integer> findTempAccountIdsAndNoValid()
        throws ServiceException
    {
        List<Integer> accountIds = this.productCountDao.findAccountIdsAndNoValid();
        return accountIds;
    }
    
}
