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
import com.ygg.webapp.service.CollectService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;

@Controller
@RequestMapping("/collect")
public class CollectController
{
    private Logger log = Logger.getLogger(CollectController.class);

    @Resource
    private CollectService collectService;

    /**
     * 加入收藏
     * 
     * @param request
     * @param id
     * @param jsonpCallback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String add(
        HttpServletRequest request,//
        @RequestParam(value = "id", required = false, defaultValue = "") String id,
        @RequestParam(value = "jsonpCallback", required = false, defaultValue = "callback") String jsonpCallback,
        @RequestParam(value = "type", required = false, defaultValue = "1") int type)
        throws Exception
    {
        try
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            if (av == null || av.getId() == 0)
            {
                Map<String, Object> result = new HashMap<String, Object>();
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", "亲~ 请先登录再收藏哦");
                String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
                return str;
            }
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("id", id);
            para.put("av", av);
            Map<String, Object> result = collectService.add(para);
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            System.out.println(str);
            return str;
        }
        catch (Exception e)
        {
            log.error("加入收藏失败！！！", e);
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.put("msg", "收藏失败啦~");
            String str = jsonpCallback + "(" + JSON.toJSONString(result) + ")";
            return str;
        }
    }

}
