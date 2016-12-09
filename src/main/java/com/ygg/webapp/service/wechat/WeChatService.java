
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.wechat;

import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.wechat.entity.req.UserInfo;
import com.ygg.webapp.wechat.message.event.EventMessage;

/**
  * 微信相关服务
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: WeChatService.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public interface WeChatService {
    
    /**
     * 微信用户关注公众号推送事件处理
     * @param eventMessage
     * @return
     * @throws Exception
     */
    public UserInfo subscribe(EventMessage eventMessage)
        throws Exception;
        
    /**
     * 处理粉丝关系
     * 
     * @param accountId 当前用户基本Id
     * @param userInfo 当前用户微信信息
     * @param eventMessage 包含推荐人Id
     * @throws Exception
     */
    public void insertFansInfo(AccountEntity accountEntity, int fatherAccountId)throws Exception;
    
    /**
     * 处理商家后台粉丝关系无限级
     * @param accountEntity 当前用户
     * @param one 当前用户上级
     */
    public void insertBsFansInfo(AccountEntity accountEntity, QqbsAccountEntity one);
}
