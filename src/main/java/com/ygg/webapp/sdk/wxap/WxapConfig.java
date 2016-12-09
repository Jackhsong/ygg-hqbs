package com.ygg.webapp.sdk.wxap;

import com.ygg.webapp.util.YggWebProperties;

public class WxapConfig
{
    
    /**
     * 商户号
     */
    public static final String PARTNER = YggWebProperties.getInstance().getProperties("partner");// "1900000109";
    
    /**
     * 密钥
     */
    public static final String PARTNER_KEY = YggWebProperties.getInstance().getProperties("partner_key"); // "8934e7d15453e97507ef794cf7b0519d";
    
    /**
     * AppID
     */
    // public static final String APP_ID = "wxbf91ad08a0e4b81c";
    
    /**
     * AppSecret
     */
    // public static final String APP_SECRET = "23f1b9ce39032f6fc023e7a5f426b135";
    
    /**
     * AppKey
     */
    // public static final String APP_KEY =
    // "s1B1Hb9j3EMF0r83hXrIciXAJQQvuNv0eo6sLTNaaiby8QIT6eniOgEHruLZahb7c2uMriOea2nD7JzzX5MOeyOGqMCv5aOVcbwdfmZuVFfN6F7KSraIDuVnPlXJSUqu";
    
    /**
     * 交易完成后，跳转的页面
     */
    public static final String RETURN_URL = YggWebProperties.getInstance().getProperties("tenpay_return_url"); // "http://121.40.73.86:8080/ygg/order/pay/tenpayreturnback";
    
    /**
     * 支付完成后的回调处理页面
     */
    public static final String NOTIFY_URL = YggWebProperties.getInstance().getProperties("tenpay_notify_url"); // "http://121.40.73.86:8080/ygg/order/pay/tenpaycallback";
    
    public static final String GATE_URL = YggWebProperties.getInstance().getProperties("tenpay_gate_url"); // "https://gw.tenpay.com/gateway/pay.htm"
                                                                                                           // ;
    
}
