
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.extension;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
  * 推广控制器
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: QqbsExtensionController.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Controller
@RequestMapping("/extension")
public class QqbsExtensionController
{
    /**
     * 粉丝订单列表临时使用
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getExtension")
    public ModelAndView getExtension()
            throws Exception{
        ModelAndView modelAndView = new ModelAndView("activity/activity_fanye");
        modelAndView.addObject("shareTitle", "林依轮 饭爷定制礼包全网首发！");
        modelAndView.addObject("shareContent", "林依轮放言抢完3万份，就直播穿裙子跳舞。");
        modelAndView.addObject("shareImage", "http://yangege.b0.upaiyun.com/product/d4f3f559311.jpg");
        return modelAndView;
    }
}
