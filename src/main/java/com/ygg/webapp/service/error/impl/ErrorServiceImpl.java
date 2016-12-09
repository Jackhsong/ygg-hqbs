
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.error.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

 import com.ygg.common.services.image.ImgYunManager;
 import com.ygg.common.services.image.ImgYunServiceIF;
import com.ygg.common.utils.CommonConst;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.service.account.HqbsAccountService;
import com.ygg.webapp.service.error.ErrorService;
import com.ygg.webapp.service.wechat.WeChatService;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.wechat.entity.req.UserInfo;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: ErrorServiceImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Service("errorService")
public class ErrorServiceImpl implements ErrorService
{
    private static final Logger logger = Logger.getLogger(ErrorServiceImpl.class);
    
    @Resource
    private WeChatService weChatService;
    @Resource
    private HqbsAccountService hqbsAccountService;
    /** 照片上次方式 */
    private ImgYunServiceIF upyunImg = ImgYunManager.getClient(CommonConst.IMG_UPYUN);
    /**
     * 用户手动输入推荐人
     * @param tui 推荐人信息
     * @return
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public void createAccount(UserInfo userInfo,int accountId,HttpSession session) throws Exception{
        
        String nickname = userInfo.getNickname();
        nickname = CommonUtil.replaceIllegalEmoji(nickname);
        nickname = (nickname == null || "".equals(nickname)) ? "左岸城堡用户" : nickname;
        userInfo.setNickname(nickname);
        
        QqbsAccountEntity qqbsAccount = new QqbsAccountEntity();
        
        qqbsAccount.setFatherAccountId(Integer.valueOf(accountId));
        qqbsAccount.setOpenId(userInfo.getOpenid());
        //用户unionid
        if(StringUtils.isNotBlank(userInfo.getUnionid())){
            qqbsAccount.setUnionId(userInfo.getUnionid());
        }else{
            logger.error("引导页面进入----获取用户信息中无unionid: "+ userInfo);
            qqbsAccount.setUnionId("");
        }
        qqbsAccount.setSubscribe("3");
        qqbsAccount.setNickName(userInfo.getNickname());
        if(StringUtils.isNotBlank(userInfo.getHeadimgurl())){
            Map<String, String> writeResult =
                upyunImg.uploadFile(CommonUtil.convertHttpFile2Bytes(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg"
                    : userInfo.getHeadimgurl()), "qqbsAccount/wechatImage/" + CommonUtil.strToMD5(userInfo.getNickname() + userInfo.getOpenid()) + ".jpg");
            if (writeResult.get("status").equals("success"))
            {
                qqbsAccount.setImage(writeResult.get("url"));
            }else{
                qqbsAccount.setImage("http://imgdata.hoop8.com/1606/5672086323995.jpg");
            }
        }else{
            qqbsAccount.setImage("http://imgdata.hoop8.com/1606/5672086323995.jpg");
        }
        AccountEntity accountEntity = hqbsAccountService.addAccoun(qqbsAccount);
        //处理粉丝关系
        logger.info("引导页面进入,推荐人ID:"+qqbsAccount.getFatherAccountId()+"当前ID:"+qqbsAccount.getAccountId());
        if(qqbsAccount.getFatherAccountId() != 0){
            logger.info("引导页面进入,处理粉丝关系处理开始");
            weChatService.insertFansInfo(accountEntity, qqbsAccount.getFatherAccountId());
            logger.info("引导页面进入,处理粉丝关系处理结束");
        }
        AccountView av = new AccountView();
        BeanUtils.copyProperties(accountEntity, av);
        // 保存用户
        SessionUtil.addUserToSession(session, av);
        SessionUtil.addQqbsAccountToSession(session, qqbsAccount);
        // 保存openid
        SessionUtil.addWeiXinOpenId(session, userInfo.getOpenid());
    }
    
    
    /**
     * 直接进入，没有推荐人 
     * @param tui 推荐人信息
     * @return
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public void createNotTAccount(UserInfo userInfo,HttpSession session) throws Exception{
        String nickname = userInfo.getNickname();
        nickname = CommonUtil.replaceIllegalEmoji(nickname);
        nickname = (nickname == null || "".equals(nickname)) ? "左岸城堡用户" : nickname;
        userInfo.setNickname(nickname);
        
        QqbsAccountEntity qqbsAccount = new QqbsAccountEntity();
        qqbsAccount.setFatherAccountId(0);
        
        qqbsAccount.setOpenId(userInfo.getOpenid());
        //用户unionid
        if(StringUtils.isNotBlank(userInfo.getUnionid())){
            qqbsAccount.setUnionId(userInfo.getUnionid());
        }else{
            logger.error("引导页面进入----获取用户信息中无unionid: "+ userInfo);
            qqbsAccount.setUnionId("");
        }
        qqbsAccount.setSubscribe("4");
        qqbsAccount.setNickName(userInfo.getNickname());
//        if(StringUtils.isNotBlank(userInfo.getHeadimgurl())){
//            Map<String, String> writeResult =
//                upyunImg.uploadFile(CommonUtil.convertHttpFile2Bytes(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg"
//                    : userInfo.getHeadimgurl()), "qqbsAccount/wechatImage/" + CommonUtil.strToMD5(userInfo.getNickname() + userInfo.getOpenid()) + ".jpg");
//            if (writeResult.get("status").equals("success"))
//            {
//                qqbsAccount.setImage(writeResult.get("url"));
//            }else{
//                qqbsAccount.setImage("http://imgdata.hoop8.com/1606/5672086323995.jpg");
//            }
//        }else{
//            qqbsAccount.setImage("http://imgdata.hoop8.com/1606/5672086323995.jpg");
//        }// TO FIXED
        String image = userInfo.getHeadimgurl();
        
        String image2 = image;
        
        qqbsAccount.setImage(image2);
        
        AccountEntity accountEntity = hqbsAccountService.addAccoun(qqbsAccount);
        AccountView av = new AccountView();
        BeanUtils.copyProperties(accountEntity, av);
        // 保存用户
        SessionUtil.addUserToSession(session, av);
        SessionUtil.addQqbsAccountToSession(session, qqbsAccount);
        // 保存openid
        SessionUtil.addWeiXinOpenId(session, userInfo.getOpenid());
    }
}
