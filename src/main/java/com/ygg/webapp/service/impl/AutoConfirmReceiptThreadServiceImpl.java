package com.ygg.webapp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.OrderService;
import com.ygg.webapp.service.ThreadService;
import com.ygg.webapp.util.CommonUtil;

public class AutoConfirmReceiptThreadServiceImpl implements ThreadService
{
    
    private static Logger logger = Logger.getLogger(AutoConfirmReceiptThreadServiceImpl.class);
    
    private ScheduledExecutorService es = null;
    
    private int corePoolSize = 1;
    
    @Resource(name = "orderService")
    private OrderService orderService;
    
    @Override
    public void init()
    {
        es = Executors.newScheduledThreadPool(corePoolSize, new ThreadFactory()
        {
            @Override
            public Thread newThread(Runnable r)
            {
                return new Thread(r, "AutoConfirmReceiptThreadServiceImpl");
            }
            
        }); // .newScheduledThreadPool(corePoolSize);
        executeJob();
    }
    
    private void executeJob()
    {
        logger.info("AutoConfirmReceiptThreadServiceImpl-----start----");
        
        String curtimeStr = CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        Date curtime = CommonUtil.string2Date(curtimeStr, "yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(); // 得到
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long initialDelay = cal.getTimeInMillis() - curtime.getTime();
        
        /*
         * if(initialDelay < 0) // 12点后 { cal.add(Calendar.DAY_OF_YEAR, 1); // 取明天的10点 initialDelay =
         * cal.getTimeInMillis() - curtime.getTime() ; }
         */
        
        long delay = 1000 * 60 * 60 * 24;
        es.scheduleWithFixedDelay(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    logger.info("AutoConfirmReceiptThreadServiceImpl start,time is:" + new Date());
                    orderService.autoConfirmReceipt();
                }
                catch (ServiceException se)
                {
                    logger.error("AutoConfirmReceiptThreadServiceImpl error", se);
                }
            }
        }, initialDelay, delay, TimeUnit.MILLISECONDS); // 每天24点执行一次
        
    }
    
    @Override
    public void destory()
    {
        
    }
    
}
