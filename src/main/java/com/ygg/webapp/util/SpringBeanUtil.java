package com.ygg.webapp.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringBeanUtil
{
    
    private static WebApplicationContext ctx = null; //
    
    public static void initServletContext(ServletContext sc)
    {
        ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
        
    }
    
    /**
     * 在其它类中可通过 如来得到spring中的bean CacheService cs = SpringBeanUtil.getBean(CacheService.class) ;
     * 
     * @param claz
     * @return
     */
    public static <T> T getBean(Class<T> claz)
    {
        if (ctx == null)
            return null;
        return ctx.getBean(claz);
    }
    
    public static Object getBean(String beanName)
    {
        if (ctx == null)
            return null;
        return ctx.getBean(beanName);
    }
    
    public static <T> T getBean(String beanName, Class<T> claz)
    {
        if (ctx == null)
            return null;
        return ctx.getBean(beanName, claz);
    }
    
}
