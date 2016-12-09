package com.ygg.webapp.test;

import com.ygg.webapp.sdk.weixin2.GetWxOrderno;
import com.ygg.webapp.util.CommonUtil;

public class TestWeiXinCreateSign
{
    
    public static void main(String[] args)
        throws Exception
    {
        
        for (int i = 0; i < 10; i++)
        {
            String payMark = CommonUtil.generateUUID();
            System.out.println("payMark---is:" + payMark);
            com.ygg.webapp.sdk.weixin.RequestHandler reqHandler = new com.ygg.webapp.sdk.weixin.RequestHandler(null, null);
            reqHandler.setParameter("appid", "wx7849b287f9c51f82"); // 公众账号ID
            reqHandler.setParameter("mch_id", "1231417002"); // 商户号
            reqHandler.setParameter("nonce_str", "36d7534290610d9b7e9abed244dd2f28"); // "db209d71df52e8a3595972ef488b636a");
                                                                                      // // //随机字符串
            // reqHandler.setParameter("nonce_str", Sha1Util.getNonceStr()); // "db209d71df52e8a3595972ef488b636a"); //
            // //随机字符串
            reqHandler.setParameter("body", "左岸城堡"); // 商品描述
            // reqHandler.setParameter("body", "gegejiagoodproduct"); //商品描述
            // reqHandler.setParameter("out_trade_no", payMark); // "E30F43DD721C45B1A1F86FA3357CE3FC".toLowerCase())
            // ;// //商家订单号
            reqHandler.setParameter("out_trade_no", "EA3C59EB869E4FABBB1D2AA44756E835"); // "E30F43DD721C45B1A1F86FA3357CE3FC".toLowerCase())
                                                                                         // ;// //商家订单号
            // reqHandler.setParameter("device_info", "013467007045764");
            // reqHandler.setParameter("fee_type", "CNY");
            
            // reqHandler.setParameter("total_fee", "1"); //商品金额,以分为单位
            reqHandler.setParameter("total_fee", "8900"); // 商品金额,以分为单位
            reqHandler.setParameter("spbill_create_ip", "10.252.146.211"); // 用户的公网ip IpAddressUtil.getIpAddr(request)
            // reqHandler.setParameter("spbill_create_ip", "121.40.73.86"); //用户的公网ip IpAddressUtil.getIpAddr(request)
            
            reqHandler.setParameter("notify_url", "http://m.gegejia.com/ygg/order/pay/tenpaycallback"); // 通知地址
            // reqHandler.setParameter("notify_url", "http://m.gegejia.com/ygg/order/pay/tenpaycallback"); //通知地址
            reqHandler.setParameter("trade_type", "JSAPI");
            // reqHandler.setParameter("openid", "oZ31Os5CyuOkl7rcdpVSOb-Tso6o");
            reqHandler.setParameter("openid", "oZ31Os9XnuMByN_ApNZAWPKBl4yA");
            
            String str = reqHandler.getParams(reqHandler.getParameters());
            // System.out.println("str ----- is: " + str);
            String sign = ""; // MD5Sign.getMD5(str.getBytes("UTF-8")) ;
            // reqHandler.setKey("bd83009a221d1e5c41f26700d7d381e7");
            reqHandler.setKey("8934e7d15453e97507ef794cf7b0519d"); //
            sign = reqHandler.createSign(reqHandler.getParameters()); // 生成签名
            System.out.println("sign----is:" + sign);
            
            /*
             * String key ="bd83009a221d1e5c41f26700d7d381e7" ; //String str2 =
             * "appid=wx7849b287f9c51f82&body=gegejiagoodproduct&mch_id=1231417002&nonce_str=d30d0f522a86b3665d8e3a9a91472e28&notify_url=http://m.gegejia.com/ygg/order/pay/tenpaycallback&openid=oZ31Os5CyuOkl7rcdpVSOb-Tso6o&out_trade_no=D4D414D684854629B0220ADCD32C4675&spbill_create_ip=121.40.73.86&total_fee=1&trade_type=JSAPI&key=bd83009a221d1e5c41f26700d7d381e7"
             * ; String str2 =
             * "appid=wx7849b287f9c51f82&body=gegejiagoodproduct&mch_id=1231417002&nonce_str=d30d0f522a86b3665d8e3a9a91472e28&notify_url=http://m.gegejia.com/ygg/order/pay/tenpaycallback&openid=oZ31Os5CyuOkl7rcdpVSOb-Tso6o&out_trade_no=D4D414D684854629B0220ADCD32C4675&spbill_create_ip=121.40.73.86&total_fee=1&trade_type=JSAPI"
             * ; String sign2 = MD5.sign(str2, key, "UTF-8") ; System.out.println("sign2 is:  "+sign2); boolean b =
             * MD5.verify(str2, sign2, key, "UTF-8") ; System.out.println(b);
             * 
             * b = MD5.verify(str, sign, key, "UTF-8") ; System.out.println(b);
             */
            
            sign = "1420673195623CCC97C125F2A4B40586";
            reqHandler.setParameter("sign", sign);
            
            // /String package1 = reqHandler.genPackage(reqHandler.getParameters()) ;
            // System.out.println("package----is:"+package1);
            String requestUrl = reqHandler.getRequestURL();
            System.out.println("----requestUrl---is:" + requestUrl);
            String prepay_id = GetWxOrderno.getPayNo("https://api.mch.weixin.qq.com/pay/unifiedorder", requestUrl);
            System.out.println("---prepay_id---is:" + prepay_id);
            
            // WeiXinHttpClient httpClient = new WeiXinHttpClient();
            // String resContent = httpClient.sendPostMsg("https://api.mch.weixin.qq.com/pay/unifiedorder",requestUrl) ;
            // System.out.println("resContent -----is: " + resContent);
        }
        
    }
    
}
