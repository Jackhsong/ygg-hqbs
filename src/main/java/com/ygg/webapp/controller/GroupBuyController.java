package com.ygg.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.code.BusinessResponseMessage;
import com.ygg.webapp.service.GroupBuyService;
import com.ygg.webapp.util.CommonEnum;

@Controller
@RequestMapping("/group")
public class GroupBuyController
{
    Logger log = Logger.getLogger(GroupBuyController.class);
    @Resource
    private GroupBuyService groupBuyService;
    
    @RequestMapping("/web/{groupProductId}")
    public ModelAndView groupWeb(@PathVariable("groupProductId") int groupProductId,//团购商品口令Id
        HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("groupBuy/groupProduct");
        return mv;
    }
    
    /**
     * 获取组团商品信息
     * 
     * @param request
     * @param groupProductId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getGroupProductInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getGroupProductInfo(HttpServletRequest request, @RequestParam(value = "groupProductId", required = true) int groupProductId)
        throws Exception
    {
        try
        {
            Map<String, Object> result = groupBuyService.findGroupProductInfo(groupProductId);
            // String str = "callback(" + JSON.toJSONString(result) + ")";
            String str = JSON.toJSONString(result);
            // System.out.println(str);
            return str;
        }
        catch (Exception e)
        {
            log.error("获取组团商品信息失败！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status",CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
            result.put("msg", BusinessResponseMessage.UN_KNOW.getMessage());
            return JSON.toJSONString(result);
        }
    }
}
