package com.ygg.webapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SimulationUtils
{
    private static final char[] eng_char =
        {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        
    private static final String[] first_name = new String[] {"zhao", "qian", "sun", "li", "zhou", "wang", "wu", "zheng", "feng", "chen", "chu", "wei", "jiang",
        "shen", "yang", "zhu", "qin", "you", "xu", "he", "shi", "zhan", "kong", "cao", "xie", "jin", "shu", "fang", "yuan"};
        
    private static final String[] tel_head = new String[] {"139", "138", "137", "159", "152", "151", "150", "188", "187", "130", "132"};
    
    private static final String[] email_suffix = new String[] {"@lizi.com", "@qq.com", "@163.com", "@sina.com", "@qihoo.com"};
    
    private static int max = Integer.MAX_VALUE;
    
    private static Random random = new Random();
    
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 随机根据phone生成用户名
     * 
     * @return
     */
    public static String generateNameByPhone()
    {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(tel_head[random.nextInt(max) % tel_head.length]);
        while (sBuilder.length() < 11)
        {
            sBuilder.append(random.nextInt(10));
        }
        return sBuilder.toString();
    }
    
    /**
     * 随机根据email生成用户名
     * 
     * @return
     */
    public static String generateNameByEmail()
    {
        StringBuilder sBuilder = new StringBuilder();
        if (random.nextInt(max) % 2 == 0)
        {
            sBuilder.append(first_name[random.nextInt(max) % first_name.length]);
            sBuilder.append(eng_char[random.nextInt(max) % eng_char.length]);
        }
        else
        {
            sBuilder.append(tel_head[random.nextInt(max) % tel_head.length]);
            while (sBuilder.length() < 11)
            {
                sBuilder.append(random.nextInt(10));
            }
        }
        sBuilder.append(email_suffix[random.nextInt(max) % email_suffix.length]);
        return sBuilder.toString();
    }
    
    /**
     * 随机根据email和phone生成用户名
     * 
     * @return
     */
    public static String generateName()
    {
        String name = "";
        int randomType = random.nextInt(max) % 2;
        switch (randomType)
        {
            case 0:// email
                name = generateNameByEmail();
                break;
            case 1:// phone
                name = generateNameByPhone();
                break;
        }
        return name;
    }
    
    public static String generateNameWithHide()
    {
        return hidingUsername(generateName(), true);
    }

    /**
     * 隐藏用户名中间几位
     * 
     * @param str 源用户名
     * @param b 当字符串长度比较小时是否强制替换
     * @return
     */
    public static String hidingUsername(String str, boolean b)
    {
        if ((str == null) || (str.length() < 2))
        {
            return str != null ? str + "*" : "*";
        }
        int length = str.length();
        int index = str.lastIndexOf("@");
        if (CommonUtil.isMobile(str))
        {
            return str.substring(0, 3) + "****" + str.substring(length - 4, length);
        }
        if (str.length() < 7)
        {
            str = str.substring(0, length / 2) + "**" + str.substring((length % 2 == 0) ? length / 2 + 1 : length / 2 + 1, length);
        }
        else if (index > -1)
        {
            str = str.substring(0, (index > 3) ? 3 : index) + "***" + str.substring(index, length);
        }
        else
        {
            str = str.substring(0, 3) + "***" + str.substring(length - 3, length);
        }
        return str;
    }

    /**
     * 随机获取在 longDate与（date.getTime - minusTime）之间的 时间字符串 格式："yyyy-MM-dd HH:mm:ss"
     * 
     * @param longDate
     * @param minusTime
     * @return
     */
    public static String generateTime(long longDate, long minusTime)
    {
        int up = (minusTime < max) ? (int)minusTime : max;
        int long2 = random.nextInt(up);
        Date resultDate = new Date(longDate - long2);
        return fmt.format(resultDate);
    }
}