package com.ygg.webapp.oauth;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonHttpClient;

/**
 * api调用的服务类
 * 
 */
public class ApiCallService{
    
    private static final Logger logger = Logger.getLogger(ApiCallService.class);
    
    
    
    public static JSONObject refreshToken(String refreshToken)
    {
        
        JSONObject parameters = new JSONObject();
        parameters.put("appid", CommonConstant.APPID);
        parameters.put("grant_type", "refresh_token");
        parameters.put("refresh_token", refreshToken);
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/sns/oauth2/refresh_token", parameters);
        if (j.getString("errcode") != null)
        {
            logger.info("刷新用户access_token失败" + j.toJSONString());
            return null;
            
        }
        else
        {
            return j;
            
        }
        
    }
    
    public static boolean verificationRefreshToken(String openid, String accessToken)
    {
        
        JSONObject parameters = new JSONObject();
        parameters.put("openid", openid);
        parameters.put("access_token", accessToken);
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/sns/auth", parameters);
        if ("0".equals(j.getString("errcode")) && "ok".equals(j.getString("errmsg")))
        {
            
            return true;
            
        }
        else
        {
            logger.info("验证用户access_token失败" + j.toJSONString());
            return false;
            
        }
        
    }
    
    /**
     * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     * 
     * @param redirect_uri
     */
    public static void getCodeo(String redirect_uri)
    {
        JSONObject parameters = new JSONObject();
        parameters.put("appid", CommonConstant.APPID);
        parameters.put("scope", "snsapi_base");
        parameters.put("state", "group");
        CommonHttpClient.commonHTTP("get", "https://open.weixin.qq.com/connect/oauth2/authorize", parameters);
        
    }
    
    public static void main(String[] args)
        throws Exception
        
    {
        // boolean b = verificationRefreshToken("oOOq-s0TRRsK_uBdCR7r2d_2yIQw",
        // "OezXcEiiBSKSxW0eoylIeF4VEOo2aSZv-p-E3uLJ41_sDcJDma3xObCVFMHAQu7K6a4wubZxj-wQd3bucUYiFSacHlVD7wcxkOwuuRrNmIShlSHV97jHqIeZVAEAl9VMnZ9wvt9LtUNNe7XRQJ3UmA");
        // System.out.println(b);
        // JSONObject j = weChatRefund("7280914c5eed45d2889b1dae4c85efdb", "1001040953201512302394527861", "579575",
        // 990, 990);
        // System.out.println(j.toJSONString());
        
        
//          UserInfo UserInfo = getUserInfoByHqbsAccessToken(
//         "zCeFSR-OpeLU4efjd880-t85JE3qDPSfS6KyhMNvgbHz0OdauJuNYCjWfBteF_Saibr0Kl17UmSXxKKv_g2NQympdeH4WPaC_FMaC-3Mzkmx66FZeKjYYbMDFDT9FoTsXWLaAJAMLA",
//         "oOOq-szKnDsRR0AxLOjoIEerK2M8"); if (UserInfo != null) { System.out.println(UserInfo.getNickname()); }
         
    }
    
}
