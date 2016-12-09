
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.withdrawals;

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
import com.ygg.webapp.service.withdrawals.WithdrawalsService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.SessionUtil;

/**
  * 提现控制器
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: WithdrawalsController.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Controller
@RequestMapping("/withdrawals")
public class WithdrawalsController {
	
	 /**    */
	@Resource(name="withdrawalsService")
	private WithdrawalsService withdrawalsService;
	private static Logger logger = Logger.getLogger(WithdrawalsController.class);
	/**
	 * 获取提现日志
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWithdrawalsLogs")
    public ModelAndView getWithdrawalsLogs(HttpServletRequest request, HttpServletResponse response, HttpSession session)
	        throws Exception{
        ModelAndView modelAndView = new ModelAndView("withdrawals/list");
        modelAndView.addObject("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
	
	
	/**
	 * 获取提现日志
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getListByPage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getListByPage(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
    		HttpServletRequest request, HttpServletResponse response, HttpSession session)
	        throws Exception{
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
		QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
		try{
			
			resultMap.put("logList",withdrawalsService.getLogList(account, page));
	        resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
	        
		}catch(Exception e){
			resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
			e.printStackTrace();
		}
        return resultMap;
        
    }
	
	
	/**
	 * 用户点击提现处理
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWithdrawals", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getWithdrawals(@RequestParam(value = "cashNum", required = true) float cashNum,
    		HttpServletRequest request, HttpServletResponse response, HttpSession session){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        try{
        	boolean falg = withdrawalsService.updateDraw(account, cashNum);
        	if(!falg){
        		resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        	}
        }catch(Exception e){
        	logger.error("提现失败"+e);
        	e.printStackTrace();
        	resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        return resultMap;
    }

}
