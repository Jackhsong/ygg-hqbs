package com.ygg.webapp.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtil
{
    public static String WEB_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    private static DateTimeFormatter FORMAT = DateTimeFormat.forPattern(WEB_FORMAT);
    
    private static SimpleDateFormat SDF = new SimpleDateFormat(WEB_FORMAT);
    
    private static SimpleDateFormat TIMESTAMP_FORMAT_SDF = new SimpleDateFormat(TIMESTAMP_FORMAT);
    
    /**
     * 以字符串(yyyy-MM-dd HH:mm:ss) 格式返回当前时间
     * 
     * @return
     */
    public static String now()
    {
        return DateTime.now().toString(WEB_FORMAT);
    }
    
    /**
     * 将(yyyy-MM-dd HH:mm:ss) 格式 字符串 转换为 DateTime
     * 
     * @param s
     * @return
     */
    public static DateTime string2DateTime(String s)
    {
        if (s == null || "".equals(s))
        {
            return null;
        }
        else
        {
            return DateTime.parse(s, FORMAT);
        }
    }
    
    /**
     * 将fmt 格式 字符串s 转换为 DateTime
     * 
     * @param s
     * @return
     */
    public static DateTime string2DateTime(String s, String fmt)
    {
        if (s != null && !"".equals(s) && fmt != null && !"".equals(fmt))
        {
            DateTimeFormatter cuFmt = DateTimeFormat.forPattern(fmt);
            return DateTime.parse(s, cuFmt);
        }
        return null;
    }
    
    /**
     * 将(yyyy-MM-dd HH:mm:ss) 格式 字符串 转换为 Date
     * 
     * @param s
     * @return
     */
    public static Date string2Date(String s)
    {
        if (s != null && !"".equals(s))
        {
            try
            {
                return SDF.parse(s);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 将 fmt 格式 字符串 转换为 Date
     * 
     * @param s
     * @param fmt
     * @return
     */
    public static Date string2Date(String s, String fmt)
    {
        if (s != null && !"".equals(s) && fmt != null && !"".equals(fmt))
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat(fmt);
                return sdf.parse(s);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 将Date 转换成 "yyyy-MM-dd HH:mm:ss" 格式的字符串
     * 
     * @param date
     * @return
     */
    public static String dateToString(Date date)
    {
        return SDF.format(date);
    }
    
    /**
     * 将实际类型为java.sql.Timestamp
     * @param timestamp
     * @return
     */
    public static String timestampObjectToString(Object timestamp)
    {
        if (timestamp == null)
        {
            return "";
        }
        return ((Timestamp)timestamp).toString();
    }
    
    public static String timestampStringToWebString(String timestamp)
        throws Exception
    {
        if (timestamp == null || "".equals(timestamp))
        {
            return "";
        }
        Date d = TIMESTAMP_FORMAT_SDF.parse(timestamp);
        return SDF.format(d);
    }

    public static String timestampObjectToWebString(Object timestamp)
        throws Exception
    {
        if (timestamp == null)
        {
            return "";
        }
        return timestampStringToWebString(((Timestamp)timestamp).toString());
    }
}

