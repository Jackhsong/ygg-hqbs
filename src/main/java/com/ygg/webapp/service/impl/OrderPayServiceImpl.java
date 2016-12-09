package com.ygg.webapp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.sdk.weixin.RequestHandler;
import com.ygg.webapp.sdk.weixin.client.WeiXinHttpClient;
import com.ygg.webapp.sdk.weixin.util.Sha1Util;
import com.ygg.webapp.sdk.weixin.util.XMLUtil;
import com.ygg.webapp.service.OrderPayService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.util.YggWebProperties;

@Service("orderPayService")
public class OrderPayServiceImpl implements OrderPayService
{
    
    // @Resource(name = "weiXinCacheService")
    @Resource(name = "memService")
    private CacheServiceIF weiXinCacheService;
    
    static Logger logger = Logger.getLogger(OrderPayServiceImpl.class);
    
    public String configPay(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(resquestParams);
        String url = param.get("url").getAsString();
        // 创建支付请求对象
        RequestHandler reqHandler = new RequestHandler(request, response);
        // 初始化
        reqHandler.init();
        
        // / 1.第一次调用得到accessToken 和 jsApiTicket
//        Map<String, String> maparams = requestPreParamsToWeiXin(request);
//      String accessToken = maparams.get(CacheConstant.WEIXIN_ACCESS_TOKEN);
//      String jsapiTicket = maparams.get(CacheConstant.WEIXIN_JSAPI_TICKET);
        //改成统一缓存
        String appid = CommonConstant.APPID;
        String secret = CommonConstant.APPSECRET;
        String accessToken = WeixinMessageDigestUtil.getAccessToken(appid, secret);
        String jsapiTicket = WeixinMessageDigestUtil.getTicket(appid, secret);
        if (accessToken == null || accessToken.equals(""))
        {
            // logger.error("微信公众号获取token为空");
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
            return result.toString();
        }
        // JS-SDK权限验证的签名
        Map<String, String> sings = com.ygg.webapp.sdk.weixin.util.Sign.sign(jsapiTicket, url);
        for (Map.Entry<String, String> entry : sings.entrySet())
        {
            result.addProperty(entry.getKey(), entry.getValue());
        }
        result.addProperty(CacheConstant.WEIXIN_ACCESS_TOKEN, accessToken);
        result.addProperty(CacheConstant.WEIXIN_JSAPI_TICKET, jsapiTicket);
        
        // logger.info("------OrderPayServiceImpl---configPay----Access_Token---is:" + accessToken);
        // logger.info("------OrderPayServiceImpl---configPay----JsApi_Ticket---is:" + jsapiTicket);
        return result.toString();
        
    }
    
    public String getOpenId(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception
    {
        String openId = null;
        /*
         * String code = "" ; String url =
         * "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+YggWebProperties.
         * getInstance().getProperties("appid")+"&redirect_uri= " +
         * YggWebProperties.getInstance().getProperties("redirect_uri"
         * )+"&response_type=code&scope=snsapi_base&state=abcdefg#wechat_redirect"; logger.info(url);
         * response.sendRedirect(url);
         */
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(resquestParams);
        String code = param.get("code") == null ? null : param.get("code").getAsString();
        if (code == null || code.equals(""))
            return null;
        String appid2 = "";
        String appsecret2 = "";
        //zhangld 去掉判断 isQqbs
        appid2 = CommonConstant.APPID;
        appsecret2 = CommonConstant.APPSECRET;
        
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid2 + "&secret=" + appsecret2 + "&code=" + code + "&grant_type=authorization_code";
        PostMethod post = new PostMethod(url);
        HttpClient client = new HttpClient();
        client.executeMethod(post);
        String result = this.convertInputStream2Str(post.getResponseBodyAsStream());
        if (!result.contains(":40029"))
        {
            parser = new JsonParser();
            param = (JsonObject)parser.parse(result);
            openId = param.get("openid").getAsString();
        }
        return openId;
    }
    
    @Override
    public String requestBeforePay(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(resquestParams);
        float totalPrice = 0;
        String openId = "";
        totalPrice = param.get("totalPrice") == null ? 0f : param.get("totalPrice").getAsFloat();
        openId = param.get("openId") == null ? "" : param.get("openId").getAsString();
        
        // //1.得到用户的openid
        
        // //2.调用统一支付接口 使用统一支付接口，获取prepay_id=
        
        String order_price = (int)(totalPrice * 100) + ""; // 分为单位
        
        String out_trade_no = CommonUtil.generateUUID();
        RequestHandler reqHandler = new RequestHandler(request, response);
        // wx50718ec381121bd5
        reqHandler.setParameter("appid", CommonConstant.APPID); // 公众账号ID
        // 1304400701
        reqHandler.setParameter("mch_id", YggWebProperties.getInstance().getProperties("partner")); // 商户号
        reqHandler.setParameter("nonce_str", Sha1Util.getNonceStr()); // 随机字符串
        reqHandler.setParameter("body", "左岸城堡"); // 商品描述
        reqHandler.setParameter("out_trade_no", out_trade_no); // 商家订单号
        reqHandler.setParameter("total_fee", order_price); // 商品金额,以分为单位
        reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); // 用户的公网ip
                                                                              // IpAddressUtil.getIpAddr(request)
        reqHandler.setParameter("notify_url", YggWebProperties.getInstance().getProperties("tenpay_notify_url")); // 通知地址
        reqHandler.setParameter("trade_type", "JSAPI");
        reqHandler.setParameter("openid", openId);
        reqHandler.setKey(CommonConstant.APPSECRET);
        String sign = reqHandler.createSign(reqHandler.getParameters()); // 生成签名
        reqHandler.setParameter("sign", sign);
        String requestUrl = reqHandler.getRequestURL(); // xml格式
        
        System.out.println("-----requestBeforePay11--------requestUrl-----is:" + requestUrl);
        
        // 通信对象
        WeiXinHttpClient httpClient = new WeiXinHttpClient();
        // 通信对象
        httpClient.setTimeOut(30);
        // 设置请求内容
        httpClient.setReqContent(YggWebProperties.getInstance().getProperties("weixin_unifiedorder_url") + "?" + requestUrl);
        httpClient.setMethod("POST");
        if (httpClient.call())
        {
            String resContent = httpClient.getResContent();
            logger.info("OrderPayServiceImpl-----requestBeforePay11---resContent is:" + resContent);
            Map<String, String> map = XMLUtil.doXMLParse(resContent);
            String return_code = map.get("return_code");
            if (return_code != null && return_code.equals("SUCCESS"))
            {
                result.addProperty("mch_id", YggWebProperties.getInstance().getProperties("partner"));
                result.addProperty("nonce_str", map.get("nonce_str")); // 微信返回的随机字符串
                result.addProperty("sign", map.get("sign")); // 微信返回的签名
                
                String result_code = map.get("result_code");
                if (result_code != null && result_code.equals("SUCCESS"))
                {
                    result.addProperty("trade_type", map.get("trade_type")); // 交易类型 JSAPI
                    result.addProperty("prepay_id", map.get("prepay_id")); // 预支付交易会话标识
                    result.addProperty("code_url", map.get("code_url")); // 二维码链接
                    result.addProperty("package", "prepay_id=" + map.get("prepay_id"));
                    
                }
            }
            
        }
        
        result.addProperty("signType", "MD5");
        result.addProperty("appid",CommonConstant.APPID);
        result.addProperty("timestamp", Sha1Util.getTimeStamp());
        return result.toString();
    }
    
    
    @Override
    public String requestPay(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception
    {
        return null;
    }
    
    @Override
    public String responseReturnUrl(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception
    {
        return null;
    }
    
    @Override
    public String responseNotifyUrl(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws Exception
    {
        return null;
    }
    
    public static String convertInputStream2Str(InputStream in)
    {
        String result = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int length = 0;
        byte[] buffer = new byte[1024];
        
        try
        {
            while ((length = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, length);
            }
            result = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            logger.error("read from inputstream error.", e);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                logger.error("close the outputstream error.", e);
            }
        }
        return result;
    }
    
}
