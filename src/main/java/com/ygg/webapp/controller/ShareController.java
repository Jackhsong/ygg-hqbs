package com.ygg.webapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ygg.webapp.sdk.weixin.util.Sign;
import com.ygg.webapp.util.CommonHttpClient;
import com.ygg.webapp.util.WeixinMessageDigestUtil;

@Controller
@RequestMapping("/share")
public class ShareController
{
    @RequestMapping("/getShareParameter")
    public @ResponseBody String getShareParameter(HttpServletRequest request, @RequestParam(value = "appId", required = true) String appId,
        @RequestParam(value = "secret", required = false, defaultValue = "") String secret, @RequestParam(value = "token", required = false, defaultValue = "") String token,
        @RequestParam(value = "ticket", required = false, defaultValue = "") String ticket, @RequestParam(value = "url", required = true) String url, HttpSession session)
            throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 0);
        JSONObject jsSdk = new JSONObject();
        
        if (StringUtils.isEmpty(token) && StringUtils.isEmpty(ticket) && StringUtils.isNotEmpty(secret))
        {
        	//统一使用缓存
            token = WeixinMessageDigestUtil.getAccessToken(appId, secret);
            if (StringUtils.isNotEmpty(token))
            {
            	//统一使用缓存
                ticket = WeixinMessageDigestUtil.getTicket(appId,secret);
            }
        }
        else if (StringUtils.isEmpty(ticket) && StringUtils.isNotEmpty(token))
        {
        	//统一使用缓存
            ticket = WeixinMessageDigestUtil.getTicket(appId,secret);
        }
        
        if (StringUtils.isEmpty(ticket))
        {
            
            return jsonObject.toJSONString();
        }
        
        Map<String, String> ret = Sign.sign(ticket, url);
        if (ret != null)
        {
            jsSdk.putAll(ret);
            jsSdk.put("appId", appId);
            jsonObject.put("jsSdk", jsSdk);
            jsonObject.put("status", 1);
            
        }
        
        return jsonObject.toJSONString();
        
    }
    
    @RequestMapping("/shareTest")
    public ModelAndView shareTest(HttpServletRequest request, HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("shareTest");
        
        JSONObject parameters = new JSONObject();
        parameters.put("appId", "wx1fc4a80bb0ebd5b6");
        parameters.put("url", "http://test.gegejia.com/ygg/share/shareTest");
        parameters.put("secret", "0d085f3a011c344bf59f86da56ff023e");
        JSONObject jsonObject = (JSONObject)CommonHttpClient.commonHTTP("get", "http://test.gegejia.com/ygg/share/getShareParameter", parameters);
        mv.addAllObjects(jsonObject);
        
        return mv;
    }
}
