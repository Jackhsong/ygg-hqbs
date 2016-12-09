package com.ygg.webapp.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTimerTest
{
    
    public static void main(String[] args)
    {
        
        // String[] configs = { "spring.xml"};
        long d = System.currentTimeMillis();
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring.xml"); // classpath后一要加个*号
        System.out.println("--------SpringTimerTest--start----success-----time---is:" + (System.currentTimeMillis() - d) + "ms");
        
        try
        {
            Thread.sleep(1000 * 60 * 24 * 365 * 10); // 10年
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
}
