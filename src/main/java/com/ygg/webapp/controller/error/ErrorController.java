
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.error;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.service.account.HqbsAccountService;
import com.ygg.webapp.service.error.ErrorService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.wechat.entity.req.UserInfo;

/**
  * 引导页面控制器
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: ErrorController.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Controller
@RequestMapping("/error")
public class ErrorController
{
    
    private static final Logger logger = Logger.getLogger(ErrorController.class);
    
    @Resource
    private HqbsAccountService hqbsAccountService;
    
     /**    */
    @Resource(name="errorService")
    private ErrorService errorService;
    
    /**
     * 粉丝关系列表临时使用
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/guide")
    public ModelAndView getGuide()  
            throws Exception{
        ModelAndView modelAndView = new ModelAndView("error/808");
        return modelAndView;
    }
    
    /**
     * 有推荐创建用户信息
     * @param accountId
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/createAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createAccount(
            @RequestParam(value = "accountId", defaultValue = "-1", required = false) int accountId,
            HttpServletRequest request, HttpServletResponse response, HttpSession session){
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            if(accountId != -1){
                QqbsAccountEntity tui = hqbsAccountService.getAccountByAccountId(accountId);
                if(tui != null){
                    UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
                    if (userInfo != null)
                    {
                        //先判断用户是否已经创建
                        QqbsAccountEntity dqaccount = hqbsAccountService.getAccounByOpenId(userInfo.getOpenid());
                        if(dqaccount != null){
                            logger.error("引导页面--输入推荐人--进入时，数据库中已经存在ID="+dqaccount.getAccountId()+"，直接跳转首页--Openid="+userInfo.getOpenid());
                        }else{
                            logger.error("引导页面--输入推荐人--进入处理开始--Openid="+userInfo.getOpenid()+"--Unionid="+userInfo.getUnionid());
                            errorService.createAccount(userInfo, accountId, session);
                            logger.error("引导页面--输入推荐人--进入处理结束");
                        }
                    }
                }else{
                    resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    resultMap.put("msg","推荐人ID不存在!");
                }
            }else{
                resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                resultMap.put("msg","推荐人ID不存在!");
            }
        }
        catch (Exception e)
        {
            logger.error("用户手动添加推荐人失败", e);
            resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            resultMap.put("msg","创建关系失败,请重试!");
        }
        return resultMap;
    }
    /**
     * 有推荐创建用户信息
     * @param accountId
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/createNotTAccount", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object createNotTAccount(
            HttpServletRequest request, HttpServletResponse response, HttpSession session){
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            UserInfo userInfo = (UserInfo)session.getAttribute("userInfo");
            if (userInfo != null)
            {
                //先判断用户是否已经创建
                QqbsAccountEntity dqaccount = hqbsAccountService.getAccounByOpenId(userInfo.getOpenid());
                if(dqaccount != null){
                    logger.error("引导页面--直接--进入时，数据库中已经存在ID="+dqaccount.getAccountId()+"，直接跳转首页--Openid="+userInfo.getOpenid());
                }else{
                    logger.error("引导页面--直接--进入处理开始--Openid="+userInfo.getOpenid()+"--Unionid="+userInfo.getUnionid());
                    errorService.createNotTAccount(userInfo, session);
                    logger.error("引导页面--直接--进入处理结束");
                }
            }else{
                resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                resultMap.put("msg","直接进入失败,请重试!");
            }
        }
        catch (Exception e)
        {
            logger.error("直接进入失败", e);
            resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            resultMap.put("msg","直接进入失败,请重试!");
        }
        return resultMap;
    }
    
}
