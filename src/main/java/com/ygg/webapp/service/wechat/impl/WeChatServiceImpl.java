/**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.wechat.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

 import com.ygg.common.services.image.ImgYunManager;
 import com.ygg.common.services.image.ImgYunServiceIF;
import com.ygg.common.utils.CommonConst;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.account.HqbsAccountDao;
import com.ygg.webapp.dao.fans.HqbsFansDao;
import com.ygg.webapp.dao.fans.QqbsBsFansDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.fans.QqbsBsFansEntity;
import com.ygg.webapp.entity.fans.QqbsFansEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.wechat.WeChatService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonHttpClient;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.wechat.entity.req.UserInfo;
import com.ygg.webapp.wechat.message.event.EventMessage;

/**
 * 微信服务接口(关注后处理)
 * 
 * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
 * @version $Id: WeChatServiceImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $
 * @since 2.0
 */
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService
{
    
    /** 照片上次方式 */
    private ImgYunServiceIF upyunImg = ImgYunManager.getClient("upyun");
    
    private static final Logger logger = Logger.getLogger(WeChatServiceImpl.class);
    
    /** 左岸城堡用户表Dao */
    @Resource(name = "hqbsAccountDao")
    private HqbsAccountDao hqbsAccountDao;
    
    /** 基本表Dao */
    @Resource(name = "accountDao")
    private AccountDao accountDao;
    
    /** 粉丝关系表Dao */
    @Resource(name = "hqbsFansDao")
    private HqbsFansDao hqbsFansDao;
    
     /**    */
    @Resource(name="qqbsBsFansDao")
    private QqbsBsFansDao qqbsBsFansDao;
    
    /**
     * @param eventMessage
     * @return
     * @throws Exception
     * @see com.ygg.webapp.service.wechat.WeChatService#subscribe(com.ygg.webapp.wechat.message.event.EventMessage)
     */
    @Override
    public UserInfo subscribe(EventMessage eventMessage)
        throws Exception
    {
        String appid = CommonConstant.APPID;
        String secret = CommonConstant.APPSECRET;
        // 获取微信用户基本信息
        UserInfo userInfo = WeixinMessageDigestUtil.getUserInfoByHqbsAccessToken(WeixinMessageDigestUtil.getAccessToken(appid, secret), eventMessage.getFromUserName());
        if (userInfo != null)
        {
//            logger.info("扫码关注人用户信息：" + userInfo);
            String nickname = userInfo.getNickname();
            nickname = CommonUtil.replaceIllegalEmoji(nickname);
            nickname = (nickname == null || "".equals(nickname)) ? "左岸城堡用户" : nickname;
            userInfo.setNickname(nickname);
            QqbsAccountEntity account = hqbsAccountDao.findAccountByOpenId(eventMessage.getFromUserName());
            // 用户存在，更新操作
            if (account != null)
            {
                logger.info("推荐人Id:" + eventMessage.getEventKey() + "," + "扫码关注人数据库账号已存在,openId:" + eventMessage.getFromUserName());
                account.setSubscribe(userInfo.getSubscribe());
                account.setNickName(userInfo.getNickname());
                Map<String, String> writeResult =
                    upyunImg.uploadFile(CommonUtil.convertHttpFile2Bytes(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg"
                        : userInfo.getHeadimgurl()), "qqbsAccount/wechatImage/" + CommonUtil.strToMD5(userInfo.getNickname() + userInfo.getOpenid()) + ".jpg");
                if (writeResult.get("status").equals("success"))
                {
                    account.setImage(writeResult.get("url"));
                }
                else
                {
                    account.setImage(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg" : userInfo.getHeadimgurl());
                }
                //用户unionid
                if(StringUtils.isBlank(account.getUnionId())){
                    if(StringUtils.isNotBlank(userInfo.getUnionid())){
                        account.setUnionId(userInfo.getUnionid());
                    }else{
                        logger.error("用户关注----获取用户信息中无unionid: "+ userInfo);
                    }
                }
                try
                {
                    hqbsAccountDao.updateAccoun(account);
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage(), e);
                    account.setNickName("微信用户");
                    if (hqbsAccountDao.updateAccoun(account) == 0)
                    {
                        throw new ServiceException("用户关注微信更新信息失败！accountId:" + account.getId());
                    }
                    else
                    {
                        logger.info(account.getOpenId() + "用户关注微信更新信息成功");
                    }
                }
            }
            else
            {
                logger.info("推荐人Id:" + eventMessage.getEventKey() + "," + "扫码关注人数据库账号不存在,openId:" + eventMessage.getFromUserName());
                // 用户不存在,新用户关注
                // 1. 插入基本用户表
                AccountEntity accountEntity = new AccountEntity();
                // 2.插入左岸城堡用户表
                QqbsAccountEntity qae = new QqbsAccountEntity();
                
                accountEntity.setName(userInfo.getOpenid());
                accountEntity.setNickname(userInfo.getNickname());
                //用户unionid
                if(StringUtils.isNotBlank(userInfo.getUnionid())){
                    accountEntity.setUnionId(userInfo.getUnionid());
                    qae.setUnionId(userInfo.getUnionid());
                }else{
                    logger.error("用户关注----获取用户信息中无unionid: "+ userInfo);
                    accountEntity.setUnionId("");
                    qae.setUnionId("");
                }
                accountEntity.setType(CommonConstant.ACCOUNTTYPE);
                accountEntity.setPwd("");
                accountEntity.setMobileNumber("");
                accountEntity.setRecommendedCode("");
                accountEntity.setIsRecommended((byte)0);
                String imageUrl = userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg" : userInfo.getHeadimgurl();
                Map<String, String> writeResult =
                    upyunImg.uploadFile(CommonUtil.convertHttpFile2Bytes(userInfo.getHeadimgurl().equals("") ? "http://imgdata.hoop8.com/1606/5672086323995.jpg"
                        : userInfo.getHeadimgurl()), "qqbsAccount/wechatImage/" + CommonUtil.strToMD5(userInfo.getNickname() + userInfo.getOpenid()) + ".jpg");
                if (writeResult.get("status").equals("success"))
                {
                    imageUrl = writeResult.get("url");
                }
                accountEntity.setImage(imageUrl);
                accountDao.addAccount(accountEntity);
                
                
                qae.setAccountId(accountEntity.getId());
                qae.setOpenId(userInfo.getOpenid());
                
                if (StringUtils.isNotBlank(eventMessage.getEventKey()))
                {
                    qae.setFatherAccountId(Integer.valueOf(eventMessage.getEventKey()));
                    // 处理粉丝关系
                    this.insertFansInfo(accountEntity, Integer.valueOf(eventMessage.getEventKey()));
                }
                else
                {
                    qae.setFatherAccountId(0);
                }
                qae.setNickName(userInfo.getNickname());
                qae.setImage(imageUrl);
                qae.setSubscribe("1");
//                qae.setExStatus(1);
                hqbsAccountDao.insertQqbsAccount(qae);
            }
        }
        return userInfo;
    }
    
    /**
     * 处理粉丝关系
     * 
     * @param accountId 当前用户基本Id
     * @param userInfo 当前用户微信信息
     * @param eventMessage 包含推荐人Id
     * @throws Exception
     */
    public void insertFansInfo(AccountEntity accountEntity, int fatherAccountId)
        throws Exception
    {
        
        // 1.当前用户成为推荐人的1级粉丝
        QqbsAccountEntity oneFans = hqbsAccountDao.findAccountByAccountId(fatherAccountId);
        if (oneFans != null)
        {
            String appid = CommonConstant.APPID;
            String secret = CommonConstant.APPSECRET;
            QqbsFansEntity qfe = new QqbsFansEntity();
            qfe.setAccountId(oneFans.getAccountId());
            qfe.setFansAccountId(accountEntity.getId());
            qfe.setLevel(1);
            qfe.setFansImage(accountEntity.getImage());
            qfe.setFansNickname(accountEntity.getNickname());
            hqbsFansDao.insertFans(qfe);
            
            // 发送消息
//            String one = accountEntity.getNickname() + "通过您的邀请关注了本公众号，成为您的左岸城堡直接粉丝!";
            String one = accountEntity.getNickname() + "通过您的邀请成为您的粉丝!";
            // Token openId
            CommonHttpClient.messageCustomSend(WeixinMessageDigestUtil.getAccessToken(appid, secret), oneFans.getOpenId(), one);
            // 2.当前用户成为推荐人上级的2级粉丝
            // 取推荐人的用户信息信息
            if (oneFans.getFatherAccountId() != 0)
            {
                QqbsAccountEntity secondFans = hqbsAccountDao.findAccountByAccountId(oneFans.getFatherAccountId());
                if (secondFans != null)
                {
                    QqbsFansEntity efe = new QqbsFansEntity();
                    efe.setAccountId(secondFans.getAccountId());
                    efe.setFansAccountId(accountEntity.getId());
                    efe.setLevel(2);
                    efe.setFansImage(accountEntity.getImage());
                    efe.setFansNickname(accountEntity.getNickname());
                    hqbsFansDao.insertFans(efe);
                    // 发送消息
//                    String two = accountEntity.getNickname() + "通过关注您的直接粉丝：" + oneFans.getNickName() + "，成为您的左岸城堡间接粉丝";
                    String two = "您管理的代言人/粉丝: "+ oneFans.getNickName() + " 新增粉丝: " +accountEntity.getNickname();
                    // Token openId
                    CommonHttpClient.messageCustomSend(WeixinMessageDigestUtil.getAccessToken(appid, secret), secondFans.getOpenId(), two);
                    // 3.当前用户成为推荐人上级 上级的3级粉丝
                    // 取推荐人上级的用户信息信息
                    if (secondFans.getFatherAccountId() != 0)
                    {
                        QqbsAccountEntity thirdFans = hqbsAccountDao.findAccountByAccountId(secondFans.getFatherAccountId());
                        if (thirdFans != null)
                        {
                            QqbsFansEntity tfe = new QqbsFansEntity();
                            tfe.setAccountId(thirdFans.getAccountId());
                            tfe.setFansAccountId(accountEntity.getId());
                            tfe.setLevel(3);
                            tfe.setFansImage(accountEntity.getImage());
                            tfe.setFansNickname(accountEntity.getNickname());
                            hqbsFansDao.insertFans(tfe);
                            // 发送消息
//                            String third = accountEntity.getNickname() + "通过关注您的间接粉丝：" + oneFans.getNickName() + "，成为您的左岸城堡间接粉丝";
                            String third = "您管理的代言人/粉丝: "+ oneFans.getNickName() + " 新增粉丝: " +accountEntity.getNickname();
                            // Token openId
                            CommonHttpClient.messageCustomSend(WeixinMessageDigestUtil.getAccessToken(appid, secret), thirdFans.getOpenId(), third);
                        }
                    }
                }
            }
            //处理商家后台粉丝关系无限级
//            logger.info("处理商家后台粉丝关系无限级--开始");
//            insertBsFansInfo(accountEntity, oneFans);
//            logger.info("处理商家后台粉丝关系无限级--结束");
        }
    }
    
    /**
     * 处理商家后台粉丝关系无限级
     * @param accountEntity 当前用户
     * @param one 当前用户上级
     */
    public void insertBsFansInfo(AccountEntity accountEntity, QqbsAccountEntity one){
        QqbsBsFansEntity fans = new QqbsBsFansEntity();
        fans.setAccountId(one.getAccountId());
        fans.setFansAccountId(accountEntity.getId());
        fans.setLevel(1);
        fans.setFansImage(accountEntity.getImage());
        fans.setFansNickname(accountEntity.getNickname());
        qqbsBsFansDao.insertBsFans(fans);
        int i = 2;
        while(one.getFatherAccountId() != 0){
            logger.info("处理商家后台粉丝关系无限级--ID="+accountEntity.getId()+"--等级="+i);
            one = hqbsAccountDao.findAccountByAccountId(one.getFatherAccountId());
            if(one != null){
                QqbsBsFansEntity qfe = new QqbsBsFansEntity();
                qfe.setAccountId(one.getAccountId());
                qfe.setFansAccountId(accountEntity.getId());
                qfe.setLevel(i);
                qfe.setFansImage(accountEntity.getImage());
                qfe.setFansNickname(accountEntity.getNickname());
                qqbsBsFansDao.insertBsFans(qfe);
                i++;
            }else{
                break;
            }
        }
    }
}
