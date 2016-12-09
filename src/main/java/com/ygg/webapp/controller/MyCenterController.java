package com.ygg.webapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.service.OrderService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;

/**
 * 个人中心管理
 * 
 * @author lihc
 *
 */
@Controller("myCenterController")
@RequestMapping("/mycenter")
public class MyCenterController
{
    
    @Resource(name = "orderService")
    private OrderService orderService;
    
    /**
     * 用户 login完成进入个人中心 用户注册，找回密码后回到登录页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/show")
    public ModelAndView showMyCenterInfo(HttpServletRequest request)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        /*
         * String userimage = request.getSession().getAttribute("userimage") == null ? null :
         * request.getSession().getAttribute("userimage").toString(); if (userimage != null && !userimage.equals(""))
         * mv.addObject("userimage", userimage);
         */
        
        // /判断是否登录
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        mv.setViewName("mycenter");
        if (av == null)
        {
            mv.addObject("islogin", 0);
            return mv;
        }
        else
        {
            mv.addObject("islogin", 1);
            // / 调用定单服务去查定单的几种状态并获得总数量
            
            mv.addObject("accountid", (av.getNickname() != null ? av.getNickname() : av.getName()));
            String requestParams = "{'accountid':'" + av.getName() + "','id':'" + av.getId() + "'}";
            String responseParams = orderService.listOrderStatusCountByUserId(requestParams);
            if (null != responseParams && !responseParams.equals(""))
            {
                JsonParser parser = new JsonParser();
                JsonObject param = (JsonObject)parser.parse(responseParams);
                int order_status_1 = param.get(CommonConstant.ORDER_STATUS_1).getAsInt();
                int order_status_2 = param.get(CommonConstant.ORDER_STATUS_2).getAsInt();
                int order_status_3 = param.get(CommonConstant.ORDER_STATUS_3).getAsInt();
                int order_status_4 = param.get(CommonConstant.ORDER_STATUS_4).getAsInt();
                int order_status_5 = param.get(CommonConstant.ORDER_STATUS_5).getAsInt();
                
                if (order_status_1 > 0)
                    mv.addObject(CommonConstant.ORDER_STATUS_1, order_status_1);
                if (order_status_2 > 0)
                    mv.addObject(CommonConstant.ORDER_STATUS_2, order_status_2);
                if (order_status_3 > 0)
                    mv.addObject(CommonConstant.ORDER_STATUS_3, order_status_3);
                if (order_status_4 > 0)
                    mv.addObject(CommonConstant.ORDER_STATUS_4, order_status_4);
                if (order_status_5 > 0)
                    mv.addObject(CommonConstant.ORDER_STATUS_5, order_status_5);
            }
            
            String image = av.getImage();
            if (image != null && !image.equals(""))
                mv.addObject("userimage", image);
        }
        
        return mv;
    }
}
