package com.ygg.webapp.util;

import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ygg.webapp.cache.memcache.CacheManager;
import com.ygg.webapp.cache.memcache.CacheServiceIF;
import com.ygg.webapp.entity.Token;
import com.ygg.webapp.wechat.entity.req.UserInfo;



public class WeixinMessageDigestUtil
{
	 private static final Logger logger = Logger.getLogger(WeixinMessageDigestUtil.class);
	 
    private static final WeixinMessageDigestUtil _instance = new WeixinMessageDigestUtil();
    
    private static final String TOKEN = "huanqiubushouq";
    
    private static final String TOKEN1 = "yanwangy";
    
    private MessageDigest alga;
    
    private static CacheServiceIF cache = CacheManager.getClient(CommonProperties.defaultCacheConfig);
    
    
    private WeixinMessageDigestUtil()
    {
        try
        {
            alga = MessageDigest.getInstance("SHA-1");
        }
        catch (Exception e)
        {
            throw new InternalError("init MessageDigest error:" + e.getMessage());
        }
    }
    
    public static WeixinMessageDigestUtil getInstance()
    {
        return _instance;
    }
    
    public boolean CheckSignature(String signature, String timestamp, String nonce)
    {
        String[] ArrTmp = {TOKEN, timestamp, nonce};
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++)
        {
            sb.append(ArrTmp[i]);
        }
        String pwd = encipher(sb.toString());
        
        if (signature.equals(pwd))
        {
            return true;
        }
        return false;
    }
    
    public boolean CheckSignature1(String signature, String timestamp, String nonce)
    {
        String[] ArrTmp = {TOKEN1, timestamp, nonce};
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++)
        {
            sb.append(ArrTmp[i]);
        }
        String pwd = encipher(sb.toString());
        
        if (signature.equals(pwd))
        {
            return true;
        }
        return false;
    }
    
    private String byte2hex(byte[] b)
    {
        String des = "";
        String tmp = null;
        for (int i = 0; i < b.length; i++)
        {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1)
            {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    
    private String encipher(String strSrc)
    {
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        alga.update(bt);
        strDes = byte2hex(alga.digest()); // to HexString
        return strDes;
    }
    
    public static void main(String[] args)
    {
        String signature = "14edab61ccb7b4991db0bd53a7fcc3b69481083e";
        String timestamp = "1444383821";
        String nonce = "655728478";
        
        String[] ArrTmp = {TOKEN, timestamp, nonce};
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++)
        {
            sb.append(ArrTmp[i]);
        }
        String pwd = WeixinMessageDigestUtil.getInstance().encipher(sb.toString());
        
        if (signature.equals(pwd))
        {
        	logger.error("token 验证成功~!");
        }
        else
        {
            logger.error("token 验证失败~!");
        }
    }
    
    /**
     * 获取公众账号accessToken
     * @param host
     * @param params
     * @return
     * @return
     */
    public static String getAccessToken(String appid, String secret){
        Object cdeCache = cache.get(CacheConstant.HQBS_TOKEN_CACHE);
        String accessToken = null;
        if (cdeCache != null){
            accessToken = (String)cdeCache;
        }else{
            JSONObject parameters = new JSONObject();
            parameters.put("grant_type", "client_credential");
            parameters.put("appid",appid);
            parameters.put("secret",secret );
            JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/cgi-bin/token", parameters);
            if (j.getString("errcode") != null){
                logger.error("获取Token失败" + j.toJSONString());
            }else{
                accessToken = j.getString("access_token");
            }
            cache.set(CacheConstant.HQBS_TOKEN_CACHE, accessToken, CacheConstant.CACHE_HOUR_1);
        }
        return accessToken;
    }
    
    /**
     * 获取公众账号Ticket
     * @return
     */
    public static String getTicket(String appId, String secret){
    	String accessToken = getAccessToken(appId,secret);
    	Object cdeCache = cache.get(CacheConstant.HQBS_TICKET_CACHE);
    	String ticket = null;
        if (cdeCache != null){
            ticket = (String)cdeCache;
        }else{
            JSONObject parameters = new JSONObject();
            parameters.put("access_token",accessToken);
            parameters.put("type", "jsapi");
            JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/cgi-bin/ticket/getticket", parameters);
            if (j.getString("errcode") != null){
                if ("ok".equals(j.getString("errmsg"))){
                    ticket = j.getString("ticket");
                }else{
                	logger.error("根据AccessToken="+accessToken+"获取Ticket失败(errmsg不等于ok)" + j.toJSONString());
                }
            }else {
                logger.error("根据AccessToken="+accessToken+"获取Ticket失败(errcode等于空)" + j.toJSONString());
            }
            cache.set(CacheConstant.HQBS_TICKET_CACHE, ticket, CacheConstant.CACHE_MINUTE_1 * 5);
        }
        return ticket;
    }
    
    
    /**
     * 获取用户token
     * @param host
     * @param params
     * @return
     * @return
     */
    public static Token getTokenByCode(String code){
    	Token token = new Token();
    	String appid = CommonConstant.APPID;
        String secret = CommonConstant.APPSECRET;
    	JSONObject parameters = new JSONObject();
        parameters.put("appid", appid);
        parameters.put("secret", secret);
        parameters.put("code", code);
        parameters.put("grant_type", "authorization_code");
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/sns/oauth2/access_token", parameters);
        if(j != null){
            if (j.getString("errcode") != null)
            {
                logger.error("根据code="+code+"获取用户access_token失败" + j.toJSONString());
                return null;
            }else{
                token.setAppId(j.getString("openid"));
                token.setAccessToken(j.getString("access_token"));
                token.setExpiresIn(Integer.valueOf(j.getString("expires_in")));
                return token;
            }
        }else{
            logger.error("根据code="+code+"获取用户access_token失败");
            return null;
        }
        
    }
    
    /**
     * 根据用户的token和oenpid获取用户信息
     * @param access_token
     * @param openid
     * @param
     */
    public static UserInfo getUserInfo(String access_token, String openid)
    {
        UserInfo UserInfo = new UserInfo();
        JSONObject parameters = new JSONObject();
        parameters.put("access_token", access_token);
        parameters.put("openid", openid);
        parameters.put("lang", "zh_CN");
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/sns/userinfo", parameters);
        if(j != null){
            if (j.getString("errcode") != null)
            {
                logger.error("根据用户access_token="+access_token+" openid="+openid+"获取用户基本信息失败" + j.toJSONString());
                return null;
                
            }
            else
            {
                return JSON.parseObject(j.toString(), UserInfo.getClass());
                
            }
        }else{
            logger.error("根据用户access_token="+access_token+" openid="+openid+"获取用户基本信息失败");
            return null;
        }
    }
    
    
    /**
     * 根据公众账号的AccessToken和用户的openid获取用户信息
     * @param access_token
     * @param openid
     * @return
     */
    public static UserInfo getUserInfoByHqbsAccessToken(String access_token, String openid)
    {
        UserInfo UserInfo = new UserInfo();
        JSONObject parameters = new JSONObject();
        parameters.put("access_token", access_token);
        parameters.put("openid", openid);
        parameters.put("lang", "zh_CN");
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/cgi-bin/user/info", parameters);
        if(j != null){
            if (j.getString("errcode") != null)
            {
                logger.error("根据公众账号access_token="+access_token+" openid="+openid+"获取用户基本信息失败" + j.toJSONString());
                return null;
                
            }
            else
            {
                return JSON.parseObject(j.toString(), UserInfo.getClass());
            }
        }else{
            logger.error("根据公众账号access_token="+access_token+" openid="+openid+"获取用户基本信息失败");
            return null;
        }
        
    }
    
    
}
