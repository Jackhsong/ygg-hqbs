package com.ygg.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.util.CommonEnum;

@Controller
@RequestMapping("/theme")
public class ThemeController
{
    private Logger log = Logger.getLogger(ThemeController.class);
    
    @Resource(name = "resourceService")
    private ResourceService resourceService;
    
    /**
     * 主题馆菜单列表 ajax
     * 
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/themeMenuList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String themeMenuList(
        HttpServletRequest request,//
        @RequestParam(value = "themeId", required = false, defaultValue = "0") String themeId,
        @RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback)
        throws Exception
    {
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("themeId", themeId);
            Map<String, Object> result = resourceService.themeMenuList(para);
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
        catch (Exception e)
        {
            log.error("获取首页详情失败！！！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
    }

    /**
     * 主题馆菜单商品列表
     * 
     * @param request
     * @param themeId
     * @param menuId
     * @param jsonpCallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/themeMenuProductList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String themeMenuProductList(
        HttpServletRequest request,//
        @RequestParam(value = "themeId", required = false, defaultValue = "0") String themeId,
        @RequestParam(value = "menuId", required = false, defaultValue = "0") String menuId,
        @RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback)
        throws Exception
    {
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("themeId", themeId);
            para.put("menuId", menuId);
            Map<String, Object> result = resourceService.themeMenuProductList(para);
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
        catch (Exception e)
        {
            log.error("获取主题馆菜单商品列表失败！！！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
    }
    
    /**
     * 主题馆菜单商品详情
     * 
     * @param request
     * @param mProductId
     * @param jsonpCallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/themeMenuProductDetail", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String themeMenuProductDetail(
        HttpServletRequest request,//
        @RequestParam(value = "mProductId", required = false, defaultValue = "") String mProductId,
        @RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback)
        throws Exception
    {
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("mProductId", mProductId);
            Map<String, Object> result = resourceService.themeMenuProductDetail(para);
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
        catch (Exception e)
        {
            log.error("获取主题馆菜单商品详情失败！！！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
    }

}
