package com.ygg.webapp.test;

import java.util.Calendar;
import java.util.Date;

import com.ygg.webapp.util.CommonUtil;

public class Test
{
    
    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        String curtimeStr = CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
        Date curtime = CommonUtil.string2Date(curtimeStr, "yyyy-MM-dd HH:mm:ss");
        
        Calendar end = Calendar.getInstance();
        end.setTime(new Date());
        // end.add(Calendar.DAY_OF_YEAR, 1);
        end.set(Calendar.HOUR_OF_DAY, 10);
        end.set(Calendar.SECOND, 0);
        end.set(Calendar.MINUTE, 0);
        String hour10everydayStr = CommonUtil.date2String(end.getTime(), "yyyy-MM-dd HH:mm:ss");
        Date hour10Date = CommonUtil.string2Date(hour10everydayStr, "yyyy-MM-dd HH:mm:ss");
        long time = curtime.getTime() - hour10Date.getTime();
        if (time > 0) // 10点后
        {
            end.add(Calendar.DAY_OF_YEAR, 1); // 取明天的10点
        }
        String hour10everydayStr1 = CommonUtil.date2String(end.getTime(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(hour10everydayStr1);
    }
    
}
