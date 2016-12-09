package com.ygg.webapp.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonHttpClient;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.SpringBeanUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.OrderProductRefundView;

@Controller("quanqiubushouController")
@RequestMapping("/quanqiubushou")
public class QuanqiubushouController
{
    private static Logger logger = Logger.getLogger(QuanqiubushouController.class);
    
    @Resource(name = "accountDao")
    private AccountDao adi = null;
    
    private final String SIGN_KEY = "quanqiubushouw";
    
    @RequestMapping(value = "/acceptPush", method = RequestMethod.GET)
    public @ResponseBody String WeChatCallback(String signature, String timestamp, String nonce, String echostr, HttpServletRequest request, HttpServletResponse response)
    
    {
        
        logger.debug("微信回调验证 signature=" + signature + " timestamp=" + timestamp + " nonce=" + nonce + " echostr=" + echostr);
        
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WeixinMessageDigestUtil.getInstance().CheckSignature(signature, timestamp, nonce))
        {
            logger.debug("微信回调验证通过!");
            return echostr;
        }
        
        return null;
    }
    
    @RequestMapping(value = "/acceptPush", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String WeChatCallback(HttpServletRequest request, HttpServletResponse response)
    {
        
        Map<String, Object> requestMap;
        // xml格式的消息数据
        String respXml = "";
        
        try
        {
            requestMap = CommonUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName") != null ? requestMap.get("FromUserName").toString() : "";
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName") != null ? requestMap.get("ToUserName").toString() : "";
            
            long createTime = requestMap.get("CreateTime") != null ? Long.valueOf(requestMap.get("CreateTime").toString()) : 0;
            // 消息类型
            String msgType = requestMap.get("MsgType") != null ? requestMap.get("MsgType").toString() : "";
            
            String event = requestMap.get("Event") != null ? requestMap.get("Event").toString() : "";
            
            String eventKey = requestMap.get("EventKey") != null ? requestMap.get("EventKey").toString() : "";
            
            String ticket = requestMap.get("Ticket") != null ? requestMap.get("Ticket").toString() : "";
            
            logger.info(fromUserName);
            logger.info(toUserName);
            logger.info(createTime);
            logger.info(msgType);
            logger.info(event);
            logger.info(eventKey);
            logger.info(ticket);
            
            if (msgType.equals("event"))
            {
                if (event.equals("subscribe"))
                {
                	String appid = CommonConstant.APPID;
                    String secret = CommonConstant.APPSECRET;
                    String token = WeixinMessageDigestUtil.getAccessToken(appid, secret);
                    String index_url =
                        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx50718ec381121bd5&redirect_uri=http%3a%2f%2fwww.waibao58.com%2fhunting%2f&response_type=code&scope=snsapi_userinfo&state=0#wechat_redirect";
                    String index_url2 =
                        "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx50718ec381121bd5&redirect_uri=http%3a%2f%2fwww.waibao58.com%2fhunting%2fcelebrity.html&response_type=code&scope=snsapi_userinfo&state=0#wechat_redirect";
                    // 发送图文信息
                    StringBuffer str = new StringBuffer();
                    str.append("<xml>");
                    str.append("<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>");
                    str.append("<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>");
                    str.append("<CreateTime>" + new Date().getTime() + "</CreateTime>");
                    str.append("<MsgType><![CDATA[news]]></MsgType>");
                    str.append("<ArticleCount><![CDATA[1]]></ArticleCount>");
                    str.append("<Articles>");
                    str.append("<item>");
                    str.append("<Title><![CDATA[左岸城堡-带你吃遍全球！点击进入>>>]]></Title>");
                    // str.append("<Description><![CDATA[Hi！
                    // 欢迎加入左岸城堡，同时恭喜您成为左岸城堡的预备代言人，您只需点击环球美食购买任意美食，即可成为代言人，分享专属二维码，积累粉丝，赢丰厚奖金。详细模式点击查看。]]></Description>");
                    str.append("<PicUrl><![CDATA[https://mmbiz.qlogo.cn/mmbiz/ylJkcBTCM22LYIYbGSQbbnRm3mCrXsXXB2sDoA5FYpBv1IN2Qd9tu2SC5bcX8oHz4YnlibayFhYZg0jRibdpnMHQ/0?wx_fmt=jpeg]]></PicUrl>");
                    str.append("<Url><![CDATA[" + index_url + "]]></Url>");
                    str.append("</item>");
                    str.append("</Articles>");
                    str.append("</xml>");
                    respXml = str.toString();
                    // 发送文本信息
                    String url1 = "http://mp.weixin.qq.com/s?__biz=MzI5MzEyNjUwNA==&mid=401441125&idx=1&sn=d92af2cb36b695cd135e0f9bf706ade5#rd";
                    String url2 = "http://mp.weixin.qq.com/s?__biz=MzI5MzEyNjUwNA==&mid=401267067&idx=1&sn=d316fd6e1411f720243a9877e975f261#rd";
                    String url3 = "http://mp.weixin.qq.com/s?__biz=MzI5MzEyNjUwNA==&mid=401494278&idx=1&sn=199540bc49a12587370131ac39c33afd#rd";
                    String url4 = "http://mp.weixin.qq.com/s?__biz=MzI5MzEyNjUwNA==&mid=401493938&idx=1&sn=f6642533e2fd66705d1579349fde3f40#rd";
                    String url5 = "http://mp.weixin.qq.com/s?__biz=MzI5MzEyNjUwNA==&mid=401493494&idx=1&sn=4a828842e652fcee0505fd506cd9da94#rd";
                    String url6 = "http://mp.weixin.qq.com/s?__biz=MzI5MzEyNjUwNA==&mid=401268077&idx=1&sn=4b515b37e75eee826ef4657321076949#rd";
                    
                    StringBuffer sb = new StringBuffer();
                    sb.append("Hi！ 欢迎加入左岸城堡，同时恭喜您成为左岸城堡的预备代言人，您只需点击");
                    sb.append("<a href=\"" + index_url + "\">环球美食</a>");
                    sb.append("购买任意美食，即可成为代言人，分享专属二维码，积累粉丝，赢丰厚奖金。").append("\n\n");
                    // sb.append("<a href=\"" + index_url2 + "\">点击查看</a>");
                    // sb.append("。");
                    sb.append("<a href=\"" + url1 + "\">1、了解左岸城堡。</a>").append("\n\n");
                    sb.append("<a href=\"" + url2 + "\">2、了解代言人模式。</a>").append("\n\n");
                    sb.append("<a href=\"" + url3 + "\">3、如何购物和推广。</a>").append("\n\n");
                    sb.append("<a href=\"" + url4 + "\">4、关于奖励和提现。</a>").append("\n\n");
                    sb.append("<a href=\"" + url5 + "\">5、如何查看物流信息。</a>").append("\n\n");
                    sb.append("<a href=\"" + url6 + "\">6、联系客服，售后服务。</a>").append("\n");
                    JSONObject j = CommonHttpClient.messageCustomSend(token, fromUserName, sb.toString());
                    logger.info("发送文本信息结果1" + j.toJSONString());
                    
                    eventKey = eventKey.replace("qrscene_", "");
                    int id = adi.findIdByNameAndType(fromUserName, Byte.parseByte("8"));
                    logger.info("id" + id);
                    if (id == CommonConstant.ID_NOT_EXIST)
                    {
                        JSONObject userInfo = CommonHttpClient.getUserInfo(token, fromUserName);
                        if (userInfo != null)
                        {
                            String nickname = userInfo.getString("nickname");
                            String headimgurl = userInfo.getString("headimgurl");
                            nickname = CommonUtil.replaceIllegalEmoji(nickname);
                            nickname = (nickname == null || "".equals(nickname)) ? "微信用户" : nickname;
                            logger.info("nickname=" + nickname + " headimgurl" + headimgurl);
                            JSONObject parameters = new JSONObject();
                            parameters.put("fatherAccountId", eventKey);
                            parameters.put("openId", fromUserName);
                            parameters.put("unionId", fromUserName);
                            parameters.put("nickname", nickname);
                            parameters.put("headimgurl", headimgurl);
                            parameters.put("sign", CommonUtil.strToMD5(fromUserName + SIGN_KEY));
                            JSONObject result = (JSONObject)CommonHttpClient.commonHTTP("post", "http://115.29.211.17:10110/quanqiubushou/account/getInfo", parameters);
                            String params = result.getString("params");
                            logger.info("params=" + params);
                            String accountId = params.substring(params.indexOf("tId") + 6, params.indexOf("st") - 3);
                            logger.info("accountId=" + accountId);
                            String url = "http://www.waibao58.com/hunting/index.php/spread/bindAccount/" + accountId + "/" + eventKey;
                            logger.info(url);
                            logger.info(CommonUtil.sendHttpGet(url));
                            
                            AccountEntity ae = adi.findAccountById(Integer.parseInt(eventKey));
                            sb.delete(0, sb.length());
                            sb.append(nickname + "通过二维码关注了本公众号，成为您的左岸城堡直接粉丝！").append("\n");
                            j = CommonHttpClient.messageCustomSend(token, ae.getName(), sb.toString());
                            logger.info("发送文本信息结果2" + j.toJSONString());
                            
                            String refeeResult = CommonUtil.sendHttpGet("http://www.waibao58.com/hunting/index.php/celebrity/getRefee/" + eventKey);
                            JsonParser parser = new JsonParser();
                            JsonObject param = (JsonObject)parser.parse(refeeResult);
                            param = (JsonObject)parser.parse(param.get("body").toString());
                            logger.info("param:" + param);
                            
                            String fatherAccountId = param.get("father_account_id").toString().substring(1, param.get("father_account_id").toString().length() - 1);
                            String grandAccountId = param.get("grand_account_id").toString().substring(1, param.get("grand_account_id").toString().length() - 1);
                            logger.info("fatherAccountId" + fatherAccountId);
                            logger.info("grandAccountId" + grandAccountId);
                            
                            if (!fatherAccountId.equals("0"))
                            {
                                AccountEntity ae1 = adi.findAccountById(Integer.parseInt(fatherAccountId));
                                sb.delete(0, sb.length());
                                sb.append(nickname + "通过关注您的直接粉丝：" + ae.getNickname() + "，成为您的左岸城堡间接粉丝！").append("\n");
                                j = CommonHttpClient.messageCustomSend(token, ae1.getName(), sb.toString());
                                logger.info("发送文本信息结果3" + j.toJSONString());
                            }
                            
                            if (!grandAccountId.equals("0"))
                            {
                                AccountEntity ae1 = adi.findAccountById(Integer.parseInt(grandAccountId));
                                sb.delete(0, sb.length());
                                sb.append(nickname + "通过关注您的间接粉丝：" + ae.getNickname() + "，成为您的左岸城堡间接粉丝！").append("\n");
                                j = CommonHttpClient.messageCustomSend(token, ae1.getName(), sb.toString());
                                logger.info("发送文本信息结果4" + j.toJSONString());
                            }
                            
                            /*
                             * JsonParser parser = new JsonParser(); String url =
                             * "http://115.29.211.17:10110/quanqiubushou/account/getInfo?fatherAccountId=" + eventKey +
                             * "&openId=" + fromUserName + "&unionId=" + fromUserName + "&sign=" +
                             * CommonUtil.strToMD5(fromUserName + SIGN_KEY); String result =
                             * CommonUtil.sendHttpGet(url); result = result.substring(result.indexOf("tId") + 8,
                             * result.indexOf("st") - 5); logger.info("result" + result); String newId = result;
                             * logger.info("newId" + newId); url =
                             * "http://www.waibao58.com/hunting/index.php/spread/bindAccount/" + newId + "/" + eventKey;
                             * logger.info(url); logger.info(CommonUtil.sendHttpGet(url));
                             */
                        }
                        else
                        {
                            logger.error("acceptPush出错,获取用户信息出错");
                        }
                        
                    }
                }
                
            }
            else if (msgType.equals("text"))
            {
                
                respXml = transferCustomerService(fromUserName, toUserName);
                logger.error("调用多客服" + respXml);
            }
            else if (msgType.equals("image"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
            }
            else if (msgType.equals("voice"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
            }
            else if (msgType.equals("video"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
            }
            else if (msgType.equals("link"))
            {
                respXml = transferCustomerService(fromUserName, toUserName);
            }
            
        }
        catch (Exception e)
        {
            logger.error("acceptPush出错！", e);
        }
        // String respMessage = processRequest(request);
        return respXml;
    }
    
    /**
     * 
     * @创建人: zero
     * @创建时间: 2016年1月26日 上午10:42:55
     * @描述: <p>
     *      (转接客服)
     *      </p>
     * @修改人: zero
     * @修改时间: 2016年1月26日 上午10:42:55
     * @修改备注: <p>
     *        (修改备注)
     *        </p>
     * @param fromUserName
     * @param toUserName
     * @return
     * @returnType String
     * @version V1.0
     */
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
    
    /**
     * 跳到购物车列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/cart/list")
    public ModelAndView toCartList(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "accountId", required = true, defaultValue = "0") String accountId, @RequestParam(value = "sign", required = true, defaultValue = "0") String sign,
        HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        if (!sign.equals(CommonUtil.strToMD5(accountId + SIGN_KEY)))
        {
            mv.setViewName("error/404");
            logger.info("签名错误：accountId" + accountId + ",sign:" + sign);
            return mv;
        }
        
        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
        AccountEntity ae = ad.findAccountById(Integer.parseInt(accountId));
        AccountView av = new AccountView();
        BeanUtils.copyProperties(ae, av);
        SessionUtil.addUserToSession(request.getSession(), av);
        session.setAttribute("quanqiubushou", "1");
        
        mv.setViewName("forward:/spcart/listsc");
        return mv;
    }
    
    /**
     * 跳到订单列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/order/list")
    public ModelAndView toOrderList(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "accountId", required = true, defaultValue = "0") String accountId, @RequestParam(value = "type", required = true, defaultValue = "0") String type,
        @RequestParam(value = "sign", required = true, defaultValue = "0") String sign, HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        if (!sign.equals(CommonUtil.strToMD5(accountId + SIGN_KEY)))
        {
            mv.setViewName("error/404");
            logger.info("签名错误：accountId" + accountId + ",sign:" + sign);
            return mv;
        }
        
        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
        AccountEntity ae = ad.findAccountById(Integer.parseInt(accountId));
        AccountView av = new AccountView();
        BeanUtils.copyProperties(ae, av);
        SessionUtil.addUserToSession(request.getSession(), av);
        session.setAttribute("quanqiubushou", "1");
        
        mv.setViewName("forward:/order/list/" + type);
        return mv;
    }
    
    /**
     * 跳到收货地址列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/address/list")
    public ModelAndView toAddressList(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "accountId", required = true, defaultValue = "0") String accountId, @RequestParam(value = "sign", required = true, defaultValue = "0") String sign,
        HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        if (!sign.equals(CommonUtil.strToMD5(accountId + SIGN_KEY)))
        {
            mv.setViewName("error/404");
            logger.info("签名错误：accountId" + accountId + ",sign:" + sign);
            return mv;
        }
        
        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
        AccountEntity ae = ad.findAccountById(Integer.parseInt(accountId));
        AccountView av = new AccountView();
        BeanUtils.copyProperties(ae, av);
        SessionUtil.addUserToSession(request.getSession(), av);
        session.setAttribute("quanqiubushou", "1");
        
        mv.setViewName("forward:/ads/listads");
        return mv;
    }
    
    /**
     * 跳到商城商品
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/resource/product/msingle")
    public ModelAndView toMallProduct(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "productId", required = true, defaultValue = "0") String productId,
        HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
        AccountEntity ae = ad.findAccountById(account.getAccountId());
        AccountView av = new AccountView();
        BeanUtils.copyProperties(ae, av);
        SessionUtil.addUserToSession(request.getSession(), av);
//        session.setAttribute("quanqiubushou", "1");
        
        mv.setViewName("forward:/product/msingle/" + productId);
        return mv;
    }
    
    /**
     * 跳到特卖商品
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/resource/product/single")
    public ModelAndView toSaleProduct(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "productId", required = true, defaultValue = "0") String productId, 
        HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
        AccountEntity ae = ad.findAccountById(account.getAccountId());
        AccountView av = new AccountView();
        BeanUtils.copyProperties(ae, av);
        SessionUtil.addUserToSession(request.getSession(), av);
//        session.setAttribute("quanqiubushou", "1");
        
        mv.setViewName("forward:/product/single/" + productId);
        return mv;
    }
    
    /**
     * 跳到专场列表
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/resource/sale")
    public ModelAndView toSale(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "saleId", required = true, defaultValue = "0") String saleId,
        HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        AccountDao ad = SpringBeanUtil.getBean("accountDao", AccountDao.class);
        AccountEntity ae = ad.findAccountById(account.getAccountId());
        AccountView av = new AccountView();
        BeanUtils.copyProperties(ae, av);
        SessionUtil.addUserToSession(request.getSession(), av);
//        session.setAttribute("quanqiubushou", "1");
        
        mv.setViewName("forward:/cnty/toac/" + saleId  );
        return mv;
    }
    
}
