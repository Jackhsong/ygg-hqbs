/**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.wechat;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ygg.webapp.service.wechat.WeChatService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonHttpClient;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.wechat.message.event.EventMessage;

/**
 * 微信回调控制器
 * 
 * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
 * @version $Id: WeChatCallbackController.java 12586 2016-05-23 07:16:23Z qiuyibo $
 * @since 2.0
 */
@Controller
@RequestMapping("/weChatCallback")
public class WeChatCallbackController
{
    
    private static Logger logger = Logger.getLogger(WeChatCallbackController.class);
    
    /** 微信相关服务接口 */
    @Resource(name = "weChatService")
    private WeChatService weChatService;
    
    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public @ResponseBody String WeChatCallback(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response)
    
    {
        
        logger.debug("微信回调验证 signature=" + signature + " timestamp=" + timestamp + " nonce=" + nonce + " echostr=" + echostr);
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WeixinMessageDigestUtil.getInstance().CheckSignature(signature, timestamp, nonce))
        {
            logger.error("微信回调验证通过!");
            return echostr;
        }
        
        return null;
    }
    
    @RequestMapping(value = "/callback", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String WeChatCallback(HttpServletRequest request, HttpServletResponse response)
    {
        
        // logger.error("进入微信微信回调!");
        Map<String, Object> requestMap;
        // xml格式的消息数据
        String respXml = "";
        
        try
        {
            requestMap = CommonUtil.parseXml(request);
            // 发送方帐号 openid
            String fromUserName = requestMap.get("FromUserName") != null ? requestMap.get("FromUserName").toString() : "";
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName") != null ? requestMap.get("ToUserName").toString() : "";
            
            long createTime = requestMap.get("CreateTime") != null ? Long.valueOf(requestMap.get("CreateTime").toString()) : 0;
            // 消息类型
            String msgType = requestMap.get("MsgType") != null ? requestMap.get("MsgType").toString() : "";
            
            String event = requestMap.get("Event") != null ? requestMap.get("Event").toString() : "";
            
            String eventKey = requestMap.get("EventKey") != null ? requestMap.get("EventKey").toString() : "";
            
            // String ticket = requestMap.get("Ticket") != null ? requestMap.get("Ticket").toString() : "";
            
            if (msgType.equals("event"))
            {
                if (event.equals("subscribe") || event.equals("SCAN"))
                {
                    eventKey = eventKey.replace("qrscene_", "");
                    logger.info("关注方式:" + event);
                    logger.info("关注人openId:" + fromUserName);
                    logger.info("关注公众号AppId:" + toUserName);
                    logger.info("推荐人Id:" + eventKey);
                    // 获取平台accessToken
                    String appid = CommonConstant.APPID;
                    String secret = CommonConstant.APPSECRET;
                    String accessToken = WeixinMessageDigestUtil.getAccessToken(appid, secret);
                    
                    String index_url = "http://zuoan.waibao58.com/ygg-hqbs/hqbsHomeInfo/getHomeInfo";
                    //发送图文信息
                    StringBuffer str = new StringBuffer();
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
                    str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
                    str.append("<MsgType><![CDATA[news]]></MsgType>");
                    str.append("<ArticleCount><![CDATA[1]]></ArticleCount>");
                    str.append("<Articles>");
                    str.append("<item>");
                    str.append("<Title><![CDATA[左岸城堡-点击进入>>>]]></Title>");
                    str.append("<PicUrl><![CDATA[http://zuoan.waibao58.com/ygg-hqbs/scripts/images/logo-3.jpg]]></PicUrl>");
                    str.append("<Url><![CDATA[" + index_url + "]]></Url>");
                    str.append("</item>");
                    str.append("</Articles>");
                    str.append("</xml>");
                    // 发送文本信息
                    String url1 = "http://zuoan/cilu/article/ciluintroduce.html";
                    String url2 = "http://b2.waibao.help/cilu/article/spokesman.html";
                    String url3 = "http://b2.waibao.help/cilu/article/superwelfare.html";
                    
                    StringBuffer sb = new StringBuffer();
                    sb.append("首先祝贺您加入代言人，\n");
                    sb.append("点击<a href=\"" + index_url + "\">心动好货</a>购买产品，\n");
                    sb.append("分享自己的专属二维码，").append("\n");
                    sb.append("然后就可以轻松赚钱啦！").append("\n\n");
                    sb.append("1、<a href=\"" + url1 + "\">了解左岸城堡</a>").append("\n\n");
                    sb.append("2、<a href=\"" + url2 + "\">代言人基本法</a>").append("\n\n");
                    sb.append("3、<a href=\"" + url3 + "\">粉丝超级福利</a>").append("\n");
                    //已经关注时，不推送消息
                    if(event.equals("subscribe")){
                        respXml = str.toString();
                        //CommonHttpClient.messageCustomSend(accessToken, fromUserName, sb.toString());
                    }
                    EventMessage eventMessage = new EventMessage();
                    eventMessage.setToUserName(toUserName);
                    eventMessage.setFromUserName(fromUserName);
                    eventMessage.setMsgType(msgType);
                    eventMessage.setEvent(event);
                    eventMessage.setCreateTime(createTime);
                    eventMessage.setEventKey(eventKey);
                    if (StringUtils.isNotBlank(eventKey))
                    {
                        logger.info("--处理扫码关注开始--");
                        weChatService.subscribe(eventMessage);
                        logger.info("--处理扫码关注结束--");
                    }
                    else
                    {
                        logger.error("用户关注时--推荐人为空--不做处理--");
                    }
                    
                }
            }
            else if (msgType.equals("text"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
                logger.info("调用多客服text类型：" + respXml);
            }
            else if (msgType.equals("image"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
                logger.info("调用多客服image类型：" + respXml);
            }
            else if (msgType.equals("voice"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
                logger.info("调用多客服voice类型：" + respXml);
            }
            else if (msgType.equals("video"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
                logger.info("调用多客服video类型：" + respXml);
            }
            else if (msgType.equals("link"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
                logger.info("调用多客服link类型：" + respXml);
            }
            else
            {
                respXml = "success";
                logger.info("回调其他类型通知msgType：" + msgType);
                logger.info("回调其他类型通知event：" + event);
            }
            
        }
        catch (Exception e)
        {
            logger.error("处理回调出错！", e);
        }
        return respXml;
    }
    
    private String transferCustomerService(String fromUserName, String toUserName)
    {
        StringBuffer str = new StringBuffer();
        str.append("<xml>");
        str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
        str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
        str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
        str.append("<MsgType><![CDATA[transfer_customer_service]]></MsgType>");
        str.append("</xml>");
        return str.toString();
    }
    
}
