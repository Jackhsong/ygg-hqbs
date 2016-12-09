package com.ygg.webapp.util;

import java.text.MessageFormat;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

public class StringUtil
{
    
    private static char[] letterNoIO = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    private static Random random = new Random();
    
    /**大写字符，长度0.75*/
    private static final float UPPER_CASE_LEN = 0.75f;
    
    /**半角字符（数字、小写字母、英文字符），长度0.5*/
    private static final float HALF_WIDTH_CHAR_LEN = 0.5f;
    
    /**全角字符（包括汉字、中文字符），长度1*/
    private static final float FULL_WIDTH_CHAR_LEN = 1.0f;
    
    /**
     * 去掉浮点数最后的零
     * 
     * @param floatingNumber
     * @return
     */
    public static String removeLastZero(Object floatingNumberObj)
    {
        String floatingNumber = String.valueOf(floatingNumberObj);
        if (floatingNumber.matches("\\d+\\.?\\d+"))
        {
            if (floatingNumber.indexOf(".") > 0)
            {
                // 正则表达
                floatingNumber = floatingNumber.replaceAll("0+$", "");// 去掉后面无用的零
                floatingNumber = floatingNumber.replaceAll("\\.$", "");// 如小数点后面全是零则去掉小数点
            }
        }
        return floatingNumber;
    }
    
    /**
     * 替换占位符
     * 
     * @param str
     * @return
     */
    public static String replacePlaceholder(String oldStr, String[] placeholders)
    {
        return MessageFormat.format(oldStr, (Object[])placeholders);
    }
    
    /**
     * 判断字符串是否全是数字类型的字符串
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        if (str == null || "".equals(str))
        {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++)
        {
            char nChar = str.charAt(i);
            if (!Character.isDigit(nChar))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 生成num长度的大写字母字符串 4 如AAAA
     * 
     * @param num
     * @return
     */
    public static String getGenerateLetterWithNum(int num)
    {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < num; i++)
        {
            str.append(letterNoIO[random.nextInt(24)]);
        }
        return str.toString();
    }
    
    /**
     * 统计字符长度，中文字符长度为1，大写英文字符长度为0.75，半角字符为0.5，全角字符为1
     * @param str
     * @return
     */
    public static int countStrLen(String str)
    {
        float len = 0.0f;
        if (StringUtils.isEmpty(str))
        {
            return 0;
        }
        char[] chars = str.toCharArray();
        for (char ch : chars)
        {
            if (ch >= 0 && ch <= 255)
            {
                if (ch >= 65 && ch <= 90)
                {
                    len += UPPER_CASE_LEN;
                }
                else
                {
                    len += HALF_WIDTH_CHAR_LEN;
                }
            }
            else
            {
                len += FULL_WIDTH_CHAR_LEN;
            }
        }
        return Double.valueOf(Math.ceil(len)).intValue();
    }
    
    /**
     * 获取指定长度字符中的下标
     * @param str
     * @param len
     * @return
     */
    public static int getIndexOfStr(String str, int len)
    {
        double count = 0;
        for (int i = 0; i < str.length(); i++)
        {
            int ascii = Character.codePointAt(str, i);
            if (ascii >= 0 && ascii <= 255)
            {
                if (ascii >= 65 && ascii <= 90)
                {
                    count += UPPER_CASE_LEN;
                }
                else
                {
                    count += HALF_WIDTH_CHAR_LEN;
                }
            }
            else
            {
                count += FULL_WIDTH_CHAR_LEN;
            }
            if (Double.valueOf(Math.ceil(count)).intValue() == len)
            {
                return i;
            }
        }
        return str.length() - 1;
    }
    
    /**
     * 格式化字符串为指定长度，不足部分用空格补齐
     * @param str
     * @param length
     * @return
     */
    public static String formatterStrLen(String str, int length, String replace)
    {
        StringBuffer sb = new StringBuffer(str);
        if (countStrLen(str) < length)
        {
            int len = length - countStrLen(str);
            for (int i = 0; i < len * 2; i++)
            {
                sb.append(replace);
            }
            return sb.toString();
        }
        else
        {
            return sb.toString();
        }
    }
    /***
     * 替换单引号和换行符
     * @param str
     * @return
     */
    public static String replaceQuotAndEnter(String str){
        if(StringUtils.contains(str, "'")){
            str = StringUtils.replace(str, "'", "\\'");
        }
        if(StringUtils.contains(str, "\r\n")){
            str = StringUtils.replace(str, "\r\n", " ");
        }
        return str;
    }
    
    public static void main(String[] args)
    {
       String str = "杰'克";
       System.out.println(replaceQuotAndEnter(str));
    }
}
