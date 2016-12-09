package com.ygg.webapp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class YggServletContextListener implements ServletContextListener
{
    
    Logger logger = Logger.getLogger(YggServletContextListener.class);
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
        logger.info("--------------YggServletContextListener------------contextDestroyed---------------------");
    }
    
    @Override
    public void contextInitialized(ServletContextEvent arg0)
    {
        logger.info("--------------YggServletContextListener------------contextInitialized--------------------");
        // System.out.println("------------------------contextInitialized--------------------------");
    }
    
}
