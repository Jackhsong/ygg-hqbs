package com.ygg.webapp.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.ygg.webapp.util.CommonUtil;

public class TestXml
{
    
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        
        String xml =
            "<xml><appid><![CDATA[wx7849b287f9c51f82]]></appid>" + "<bank_type><![CDATA[CFT]]></bank_type>" + "<cash_fee><![CDATA[1]]></cash_fee>"
                + "<fee_type><![CDATA[CNY]]></fee_type>" + "<is_subscribe><![CDATA[Y]]></is_subscribe>" + "<mch_id><![CDATA[1231417002]]></mch_id>"
                + "<nonce_str><![CDATA[671f0311e2754fcdd37f70a8550379bc]]></nonce_str>" + "<openid><![CDATA[oZ31Os5CyuOkl7rcdpVSOb-Tso6o]]></openid>"
                + "<out_trade_no><![CDATA[E36E5E0B3AFF4E509A920670B4A780EA]]></out_trade_no>" + "<result_code><![CDATA[SUCCESS]]></result_code>"
                + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<sign><![CDATA[9F88CC47CDE973AC797C4ED7A105D787]]></sign>"
                + "<time_end><![CDATA[20150307184659]]></time_end>" + "<total_fee>1</total_fee>" + "<trade_type><![CDATA[JSAPI]]></trade_type>"
                + "<transaction_id><![CDATA[1007230641201503070028778921]]></transaction_id>" + "</xml>";
        
        // XML解析notify_data数据
        Document doc_notify_data = null;
        try
        {
            doc_notify_data = DocumentHelper.parseText(xml);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        String appid = doc_notify_data.selectSingleNode("//xml/appid").getText();
        String bank_type = doc_notify_data.selectSingleNode("//xml/bank_type").getText();
        String total_fee = doc_notify_data.selectSingleNode("//xml/total_fee").getText();
        String transaction_id = doc_notify_data.selectSingleNode("//xml/transaction_id").getText();
        String result_code = doc_notify_data.selectSingleNode("//xml/result_code").getText();
        String return_code = doc_notify_data.selectSingleNode("//xml/return_code").getText();
        
        String discount = doc_notify_data.selectSingleNode("//xml/out_trade_no").getText();
        System.out.println(appid);
        System.out.println(bank_type);
        System.out.println(total_fee);
        System.out.println(transaction_id);
        System.out.println(result_code);
        System.out.println(return_code);
        System.out.println(discount);
        byte b = 3;
        System.out.println(b == 3);
        
        try
        {
            String password = CommonUtil.strToMD5("123456" + "18971612471");
            System.out.println(password + "-=----------------------");
            
            String curtimeStr = CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss");
            Date curtime = CommonUtil.string2Date(curtimeStr, "yyyy-MM-dd HH:mm:ss");
            System.out.println(curtime);
            Calendar end = Calendar.getInstance();
            // Date endDate = CommonUtil.string2Date(time + "", "yyyyMMdd");
            end.setTime(new Date());
            // end.add(Calendar.DAY_OF_YEAR, 1);
            end.set(Calendar.HOUR_OF_DAY, 10);
            end.set(Calendar.SECOND, 0);
            end.set(Calendar.MINUTE, 0);
            String hour10everydayStr = CommonUtil.date2String(end.getTime(), "yyyy-MM-dd HH:mm:ss");
            System.out.println("-----------hour10everydayStr---------- " + end.getTime());
            Date hour10Date = CommonUtil.string2Date(hour10everydayStr, "yyyy-MM-dd HH:mm:ss");
            System.out.println(hour10Date);
            
            long time = (hour10Date.getTime() - curtime.getTime()) / 1000;
            if (time > 0)
            {
                // / 查第二天的10天
                end.add(Calendar.DAY_OF_YEAR, 1);
                String nextHour10everydayStr = CommonUtil.date2String(end.getTime(), "yyyy-MM-dd HH:mm:ss");
                Date nexthour10Date = CommonUtil.string2Date(nextHour10everydayStr, "yyyy-MM-dd HH:mm:ss");
                System.out.println(nexthour10Date);
                time = (nexthour10Date.getTime() - curtime.getTime()) / 1000;
                System.out.println(time);
            }
            System.out.println(time);
            
            /*
             * String str = "http://www.baidu.com?dd=YY&fff=ddgg"; System.out.println( URLEncoder.encode(str, "UTF-8") )
             * ; List<Integer> it = new ArrayList<Integer>(); it.add(34) ; it.add(55) ; System.out.println(it);
             */
            
            /*
             * for(int i=0;i<1000;i++) { Thread.sleep(1000) ; String refreshtime = CommonUtil.date2String(new Date(),
             * "HH:mm:ss") ;
             * 
             * System.out.println(refreshtime + " sleep-----i---is: "+ i); if(refreshtime.equals("17:29:00")) { break ;
             * } }
             */
            String str10 = "http://m.gegejia.com/sale-#1.htm";
            str10 = str10.replaceFirst("#1", "33");
            System.out.println(str10);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
    }
    
}
