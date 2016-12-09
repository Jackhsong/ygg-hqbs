package com.ygg.webapp.controller;

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

import com.ygg.webapp.code.AccessTypeEnum;
import com.ygg.webapp.service.SpecialActivityService;

@Controller
@RequestMapping("/special")
public class SpecialActivityController
{
    private static Logger logger = Logger.getLogger(SpecialActivityController.class);
    
    @Resource
    private SpecialActivityService specialActivityService;
    
    @RequestMapping("/activity/web/{activityId}")
    public ModelAndView accessSpecialActivityByWap(@PathVariable("activityId") int activityId)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("specialActivity/specialActivity_wap");
            
            Map<String, Object> resultMap = specialActivityService.findSpecialActivityDetailById(activityId, AccessTypeEnum.TYPE_OF_WAP.getValue());
            if (resultMap == null || resultMap.size() == 0)
            {
                mv.setViewName("error/404");
            }
            mv.addObject("activity", resultMap);
            //logger.info(JSON.toJSON(resultMap));
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    @RequestMapping("/activity/appOpen")
    public ModelAndView accessSpecialActivityByApp(@RequestParam(value = "activityId", required = true) int activityId,//
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,//
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign,//
        HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("specialActivity/specialActivity_app");
            
            Map<String, Object> resultMap = specialActivityService.findSpecialActivityDetailById(activityId, AccessTypeEnum.TYPE_OF_APP.getValue());
            if (resultMap == null || resultMap.size() == 0)
            {
                mv.setViewName("error/404");
            }
            mv.addObject("activity", resultMap);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /*
     * @RequestMapping(value = "/jsonSpecialActivityInfo", produces = "application/json;charset=UTF-8")
     * 
     * @ResponseBody public String jsonSpecialActivityInfo(@RequestParam(value = "activityId", required = false,
     * defaultValue = "1") int activityId) { Map<String, Object> resultMap = new HashMap<String, Object>(); try {
     * resultMap.put("activity", specialActivityService.findSpecialActivityDetailById(activityId)); } catch (Exception
     * e) { logger.error(e.getMessage(), e); resultMap.put("rows", new ArrayList<Map<String, Object>>());
     * resultMap.put("total", 0); } return JSON.toJSONString(resultMap); }
     */
    
    /**
     * 新情景特卖页面
     * 
     * @param specialActivityId
     * @return
     * @throws Exception
     */
    @RequestMapping("/sceneWeb/{specialActivityId}")
    public ModelAndView sceneWeb(@PathVariable("specialActivityId") int specialActivityId)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("specialActivity/scene_wap");
        Map<String, Object> info = specialActivityService.findSpecialActivityNewById(specialActivityId, 1, 1);
        // logger.info("info: " + JSON.toJSONString(info));
        mv.addObject("specialActivityId", info.get("specialActivityId") + "");
        mv.addObject("url", info.get("url") + "");
        mv.addObject("productList", info.get("productList"));
        mv.addObject("productMoreList", info.get("productMoreList"));
        return mv;
    }
    
    /**
     * 新情景特卖页面
     * 
     * @param specialActivityId
     * @return
     * @throws Exception
     */
    @RequestMapping("/sceneApp/{specialActivityId}")
    public ModelAndView sceneApp(@PathVariable("specialActivityId") int specialActivityId)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("specialActivity/scene_app");
        Map<String, Object> info = specialActivityService.findSpecialActivityNewById(specialActivityId, 1, 2);
        // logger.info("info: " + JSON.toJSONString(info));
        mv.addObject("specialActivityId", info.get("specialActivityId") + "");
        mv.addObject("url", info.get("url") + "");
        mv.addObject("productList", info.get("productList"));
        mv.addObject("productMoreList", info.get("productMoreList"));
        return mv;
    }
    
    /**
     * 组合情景页面
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/groupSceneWeb/{id}")
    public ModelAndView groupSceneWeb(@PathVariable("id") int id)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("specialActivity/groupScene_wap");
        Map<String, Object> info = specialActivityService.findSpecialActivityGroupById(id, 1);
        mv.addObject("activityId", info.get("activityId") + "");
        mv.addObject("title", info.get("title") + "");
        mv.addObject("productList", info.get("productList"));
        mv.addObject("productMoreList", info.get("productMoreList"));
        //logger.info(JSON.toJSONString(info));
        return mv;
    }
    
    /**
     * 新情景特卖页面
     * 
     * @param specialActivityId
     * @return
     * @throws Exception
     */
    @RequestMapping("/groupSceneApp/{id}")
    public ModelAndView groupSceneApp(@PathVariable("id") int id)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("specialActivity/groupScene_app");
        Map<String, Object> info = specialActivityService.findSpecialActivityGroupById(id, 2);
        mv.addObject("activityId", info.get("activityId") + "");
        mv.addObject("title", info.get("title") + "");
        mv.addObject("productList", info.get("productList"));
        mv.addObject("productMoreList", info.get("productMoreList"));
        //logger.info(JSON.toJSONString(info));
        return mv;
    }
    
    /**
     * 情景模版
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/newSceneWeb/{id}")
    public ModelAndView newSceneWeb(@PathVariable("id") int id)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("specialActivity/activityModel_web");
        Map<String, Object> info = specialActivityService.findSpecialActivityModelById(id);
        mv.addObject("title", info.get("title") + "");
        mv.addObject("imageUrl", info.get("imageUrl") + "");
        mv.addObject("height", info.get("height") + "");
        mv.addObject("width", info.get("width") + "");
        mv.addObject("categoryList", info.get("categoryList"));
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_WAP.getValue());
        mv.addObject("activityId", id + "");
        //        logger.info(JSON.toJSON(info));
        return mv;
    }
    
    /**
     * 情景模版
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/newSceneApp/{id}")
    public ModelAndView newSceneApp(@PathVariable("id") int id)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("specialActivity/activityModel_app");
        Map<String, Object> info = specialActivityService.findSpecialActivityModelById(id);
        mv.addObject("title", info.get("title") + "");
        mv.addObject("imageUrl", info.get("imageUrl") + "");
        mv.addObject("height", info.get("height") + "");
        mv.addObject("width", info.get("width") + "");
        mv.addObject("categoryList", info.get("categoryList"));
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_APP.getValue());
        mv.addObject("activityId", id + "");
        //logger.info(JSON.toJSON(info));
        return mv;
    }
    
    @RequestMapping(value = "/newScene/getData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String newSceneGetData(@RequestParam(value = "activityId", required = false, defaultValue = "0") int activityId,
        @RequestParam(value = "clientType", required = false, defaultValue = "1") int clientType)
        throws Exception
    {
        return specialActivityService.findSpecialActivityModelLayoutProductByIdAndType(activityId, clientType);
    }
}
