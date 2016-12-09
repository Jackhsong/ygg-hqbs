package com.ygg.webapp.sdk.weixin.util;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;

/**
 * @author wuhy
 * @date 创建时间：2016年5月17日 下午2:00:36
 */
public class QRCodeUtil
{
    private static final JsonParser parser = new JsonParser();
    
    private static final String WEIXIN_API_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    
    private static final String WEIXIN_API_SHOWQRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
    
    public static String buildTemporaryQRCode(int accountId)
    {
        return buildTemporaryQRCode(accountId, CommonConstant.APPID, CommonConstant.APPSECRET);
        
    }
    
    public static String buildTemporaryQRCode(int accountId, String appId, String appSecret)
    {
        String accessToken = WeixinMessageDigestUtil.getAccessToken(appId, appSecret);
        String resultTicket =
            ((JsonObject)parser.parse(CommonUtil.sendRESTFulPost(WEIXIN_API_URL + accessToken, "{\"expire_seconds\": 2592000, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "
                + accountId + "}}}", new HashMap<String, String>()))).get("ticket").toString();
        return WEIXIN_API_SHOWQRCODE_URL + resultTicket.substring(1, resultTicket.length() - 1);
        
    }
}
