 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.brand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.webapp.service.brand.QqbsBrandService;
import com.ygg.webapp.util.CommonEnum;

/**
 * @author Qiu,Yibo
 *
 * 2016年4月28日
 */

@Controller
@RequestMapping("/qqbsBrand")
public class QqbsBrandController
{
    private static final Logger logger = Logger.getLogger(QqbsBrandController.class);
    
    @Resource
    private QqbsBrandService qqbsBrandService;
    
     /**
      * 获取品牌栏目和品牌馆信息
      * @param request
      * @param response
      * @param session
      * @return
      */
    @RequestMapping(value = "/getQqbsBrands", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelAndView getQqbsBrands(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ModelAndView modelAndView = new ModelAndView("brand/index");
        modelAndView.addObject("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        try
        {
            List<Map<String,Object>> brandList =  qqbsBrandService.getQqbsBrands();
            modelAndView.addObject("brandList", brandList);
            modelAndView.addObject("status",CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        catch (Exception e)
        {
            logger.error("直接进入失败", e);
            resultMap.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        return modelAndView;
    }

}
