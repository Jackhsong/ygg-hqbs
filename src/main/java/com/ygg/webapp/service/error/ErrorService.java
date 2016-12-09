
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.error;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpSession;

import com.ygg.webapp.wechat.entity.req.UserInfo;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: ErrorService.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public interface ErrorService
{
    
    /**
     * 用户手动输入推荐人
     * @param tui 推荐人信息
     * @return
     */
    public void createAccount(UserInfo userInfo,int accountId,HttpSession session) throws Exception;
    /**
     * 直接进入，没有推荐人 
     * @param tui 推荐人信息
     * @return
     * @throws IOException 
     * @throws UnsupportedEncodingException 
     */
    public void createNotTAccount(UserInfo userInfo,HttpSession session) throws Exception;
}
