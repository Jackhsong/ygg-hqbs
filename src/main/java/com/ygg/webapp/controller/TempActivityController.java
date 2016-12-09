package com.ygg.webapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.code.AccessTypeEnum;
import com.ygg.webapp.service.TempActivityService;
import com.ygg.webapp.util.CommonEnum;

/**
 * 临时活动
 * @author xiongl
 *
 */
@Controller
@RequestMapping("/temp")
public class TempActivityController
{
    private static Logger logger = Logger.getLogger(TempActivityController.class);
    
    @Resource
    private TempActivityService tempActivityService;
    
    @RequestMapping("/activity/index")
    public ModelAndView index()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("tempActivity/index");
        return mv;
    }
    
    @RequestMapping(value = "/activity/web", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String wapActivity(@RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback)
    {
        List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
        try
        {
            List<Map<String, Object>> result = tempActivityService.findAllTempActivity(AccessTypeEnum.TYPE_OF_WAP.getValue());
            reList.addAll(result);
            
            //            Map<String, Object> map = new HashMap<String, Object>();
            //            map.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            //            reList.add(0, map);
            System.out.println(JSON.toJSON(result));
        }
        catch (Exception e)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            reList.add(map);
            logger.error(e.getMessage(), e);
        }
        //        return jsonpCallback + "(" + JSON.toJSONString(reList) + ")";
        return JSON.toJSONString(reList);
    }
    
    @RequestMapping(value = "/activity/app", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String appActivity(@RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback)
    {
        List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
        try
        {
            List<Map<String, Object>> result = tempActivityService.findAllTempActivity(AccessTypeEnum.TYPE_OF_APP.getValue());
            reList.addAll(result);
            
            //            Map<String, Object> map = new HashMap<String, Object>();
            //            map.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            //            reList.add(0, map);
            
        }
        catch (Exception e)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            reList.add(map);
            logger.error(e.getMessage(), e);
        }
        //        return jsonpCallback + "(" + JSON.toJSONString(reList) + ")";
        return "callback" + "(" + JSON.toJSONString(reList) + ")";
    }
    
    @RequestMapping(value = "test", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String test()
    {
        List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
        try
        {
            List<Map<String, Object>> result = tempActivityService.findAllTempActivity(AccessTypeEnum.TYPE_OF_APP.getValue());
            reList.addAll(result);
            
            //            Map<String, Object> map = new HashMap<String, Object>();
            //            map.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            //            reList.add(0, map);
            
        }
        catch (Exception e)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            reList.add(map);
            logger.error(e.getMessage(), e);
        }
        //        return jsonpCallback + "(" + JSON.toJSONString(reList) + ")";
        return JSON.toJSONString(reList);
    }
}
