package com.ygg.webapp.test;

import java.util.Date;

import com.ygg.webapp.util.CommonUtil;

public class TestThread extends Thread
{
    
    @Override
    public void run()
    {
        long i = 0;
        while (true)
        {
            i++;
        }
        
    }
    
    public static void main(String[] args)
    {
        
        TestThread tt1 = new TestThread();
        tt1.start();
        TestThread tt2 = new TestThread();
        tt2.start();
        TestThread tt3 = new TestThread();
        tt3.start();
        TestThread tt4 = new TestThread();
        tt4.start();
        TestThread5 tt5 = new TestThread5();
        tt5.start();
        
        TestThread tt6 = new TestThread();
        tt6.start();
        TestThread tt7 = new TestThread();
        tt7.start();
        TestThread tt8 = new TestThread();
        tt8.start();
        TestThread tt9 = new TestThread();
        tt9.start();
        
    }
    
    static class TestThread5 extends Thread
    {
        public void run()
        {
            while (true)
            {
                String refreshtime = CommonUtil.date2String(new Date(), "HH:mm:ss SS");
                
                System.out.println(refreshtime + " sleep-----i---is: ");
                if (refreshtime.equals("17:56:40"))
                {
                    break;
                }
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
        }
    }
}
