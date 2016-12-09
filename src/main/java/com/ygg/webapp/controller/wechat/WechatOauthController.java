package com.ygg.webapp.controller.wechat;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ygg.common.services.image.ImgYunManager;
import com.ygg.common.services.image.ImgYunServiceIF;
import com.ygg.common.services.image.UpImgYunServiceIF;
import com.ygg.common.utils.CommonConst;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.Token;
import com.ygg.webapp.service.account.HqbsAccountService;
import com.ygg.webapp.service.wechat.WeChatService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.wechat.entity.req.UserInfo;

/**
 * 拦截器寂寞授权后获取微信的相关信息 1.用户为新用户时，添加账号信息包括：基本信息表：account 左岸城堡用户表：qqbs_account 左岸城堡粉丝关系表：qqbs_fans 2.为老用户是，更新微信头像
 * 
 * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
 * @version $Id: WechatOauthController.java 12583 2016-05-23 06:56:23Z wuhuyun $
 * @since 2.0
 */
@Controller
public class WechatOauthController
{
    
    private static final Logger logger = Logger.getLogger(WechatOauthController.class);
    
    @Resource
    private WeChatService weChatService;
    
    @Resource
    private HqbsAccountService hqbsAccountService;
    
    /** 照片上次方式 */
    private ImgYunServiceIF upyunImg = ImgYunManager.getClient(CommonConst.IMG_UPYUN);
    
    @RequestMapping(value = "/wechatToken")
    @ResponseBody
    public Map<String, String> wechatToken(HttpServletRequest request, HttpServletResponse response)
    {
        Map<String, String> result = new HashMap<String, String>();
        result.put("accessToken", WeixinMessageDigestUtil.getAccessToken(CommonConstant.APPID, CommonConstant.APPSECRET));
        return result;
    }
    
    /**
     * (用户授权后的处理Controller)
     * 
     * @param code
     * @param state
     * @param request
     * @param response
     * @param session
     * @return
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/wechatOauth")
    public String wechatOauth(String code, String state, HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        try
        {
            // 1.用户点击登录后，能够获取code
            // logger.info("用户点击链接state="+state);
            if (StringUtils.isEmpty(code))
            {
                logger.error("code为空");
                return "error";
            }
            // 2.根据code，去获取网页授权access_token
            
            // 正确：access_token expires_in refresh_token openid scope
            // 失败：{"errcode":40029,"errmsg":"invalid code"}
            // JSONObject j = ApiCallService.getTokenByCode(code);
            // 获取用户信息，必须是用户token
            Token token = WeixinMessageDigestUtil.getTokenByCode(code);
            if (token != null)
            {
                String openId = token.getAppId();
                
                // 拉取用户信息(需scope为 snsapi_userinfo)
                UserInfo userInfo = WeixinMessageDigestUtil.getUserInfo(token.getAccessToken(), token.getAppId());
                if (userInfo != null)
                {
                    // logger.info("用户信息有效期外第一次登陆：" + userInfo);
                    String nickname = userInfo.getNickname();
                    nickname = CommonUtil.replaceIllegalEmoji(nickname);
                    nickname = (nickname == null || "".equals(nickname)) ? "左岸城堡用户" : nickname;
                    userInfo.setNickname(nickname);
                    QqbsAccountEntity qqbsAccount = hqbsAccountService.getAccounByUnionId(openId);
                    AccountEntity accountEntity = null;
                    if (qqbsAccount == null)
                    {
                        // 账户不存在的情况下，点击链接进入一定要有推荐人，否则跳转制定页面
                        qqbsAccount = new QqbsAccountEntity();
                        try
                        {
                            int a1 = state.indexOf("taccountId=");
                            if (a1 != -1)
                            {
                                int a2 = state.indexOf("&");
                                String taccountId = "";
                                if (a2 != -1)
                                {
                                    taccountId = state.substring(a1 + 11, a2);
                                }
                                else
                                {
                                    taccountId = state.substring(a1 + 11);
                                }
                                qqbsAccount.setFatherAccountId(Integer.valueOf(taccountId));
                            }
                            else
                            {
                                logger.error("用户点击分享或者链接时--推荐人为空--不做处理--");
                                session.setAttribute("userInfo", userInfo);
                                return "redirect:" + "/error/guide";
                            }
                        }
                        catch (Exception e)
                        {
                            logger.error("用户点击分享,转换推荐人ID出错", e);
                            session.setAttribute("userInfo", userInfo);
                            return "redirect:" + "/error/guide";
                        }
                        qqbsAccount.setOpenId(openId);
                        // 用户unionid
                        if (StringUtils.isNotBlank(userInfo.getUnionid()))
                        {
                            qqbsAccount.setUnionId(userInfo.getUnionid());
                        }
                        else
                        {
                            logger.error("用户点击分享链接----获取用户信息中无unionid: " + userInfo);
                            qqbsAccount.setUnionId("");
                        }
                        qqbsAccount.setSubscribe("2");
                        qqbsAccount.setNickName(userInfo.getNickname());
                        if (StringUtils.isNotBlank(userInfo.getHeadimgurl()))
                        {
                            Map<String, String> writeResult =
                                upyunImg.uploadFile(CommonUtil.convertHttpFile2Bytes(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg"
                                    : userInfo.getHeadimgurl()), "qqbsAccount/wechatImage/" + CommonUtil.strToMD5(userInfo.getNickname() + userInfo.getOpenid()) + ".jpg");
                            if (writeResult.get("status").equals("success"))
                            {
                                qqbsAccount.setImage(writeResult.get("url"));
                            }
                            else
                            {
                                qqbsAccount.setImage("http://imgdata.hoop8.com/1606/5672086323995.jpg");
                            }
                        }
                        else
                        {
                            qqbsAccount.setImage("http://imgdata.hoop8.com/1606/5672086323995.jpg");
                        }
                        accountEntity = hqbsAccountService.addAccoun(qqbsAccount);
                        // 处理粉丝关系
                        logger.info("分享人ID:" + qqbsAccount.getFatherAccountId() + "点击分享链接人ID:" + qqbsAccount.getAccountId());
                        if (qqbsAccount.getFatherAccountId() != 0)
                        {
                            logger.info("分享处理粉丝关系处理开始");
                            weChatService.insertFansInfo(accountEntity, qqbsAccount.getFatherAccountId());
                            logger.info("分享处理粉丝关系处理结束");
                        }
                    }
                    else
                    {
                        boolean flag = false;
                        accountEntity = hqbsAccountService.findAccountById(qqbsAccount.getAccountId());
                        String str = qqbsAccount.getImage();
                        if ((StringUtils.isBlank(str)) || !(str.startsWith("http://yangege.b0.upaiyun.com/qqbsAccount/wechatImage")))
                        {
                            if (userInfo != null && StringUtils.isNotBlank(userInfo.getHeadimgurl()))
                            {
                                Map<String, String> writeResult =
                                    upyunImg.uploadFile(CommonUtil.convertHttpFile2Bytes(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg"
                                        : userInfo.getHeadimgurl()), "qqbsAccount/wechatImage/" + CommonUtil.strToMD5(userInfo.getNickname() + userInfo.getOpenid()) + ".jpg");
                                if (writeResult.get("status").equals("success"))
                                {
                                    // 更新用户头像和昵称
                                    qqbsAccount.setImage(writeResult.get("url"));
                                    qqbsAccount.setNickName(userInfo.getNickname());
                                    accountEntity.setImage(writeResult.get("url"));
                                    flag = true;
                                }
                            }
                        }
                        // 用户unionid
                        if (StringUtils.isBlank(qqbsAccount.getUnionId()))
                        {
                            if (StringUtils.isNotBlank(userInfo.getUnionid()))
                            {
                                qqbsAccount.setUnionId(userInfo.getUnionid());
                                flag = true;
                            }
                            else
                            {
                                logger.error("用户点击链接--获取用户信息中无unionid: " + userInfo);
                            }
                        }
                        if (flag)
                        {
                            hqbsAccountService.updateQqbsAccount(qqbsAccount.getId(), qqbsAccount.getImage(), qqbsAccount.getNickName(), qqbsAccount.getUnionId());
                        }
                    }
                    AccountView av = new AccountView();
                    BeanUtils.copyProperties(accountEntity, av);
                    // 保存用户
                    SessionUtil.addUserToSession(session, av);
                    SessionUtil.addQqbsAccountToSession(session, qqbsAccount);
                    // 保存openid
                    SessionUtil.addWeiXinOpenId(session, openId);
                    state = URLDecoder.decode(state);
                    return "redirect:" + state;
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return "error";
        }
        return "error";
    }
}
