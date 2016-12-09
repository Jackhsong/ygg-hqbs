package com.ygg.webapp.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.webapp.util.FileOperateUtil;

@Controller("testController")
@RequestMapping("/test")
public class TestController
{
    
    @RequestMapping("/")
    public String testFtl()
    {
        return "test/ajaxupfile";
    }
    
    /**
     * 上传文件
     *
     * @author geloin
     * @date 2012-3-29 下午4:01:41
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ajaxupload")
    // @ResponseBody
    public void upload(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        // 别名
        String[] alaises = ServletRequestUtils.getStringParameters(request, "img");
        
        String[] params = new String[] {"img"};
        Map<String, Object[]> values = new HashMap<String, Object[]>();
        values.put("img", alaises);
        
        List<Map<String, Object>> result = FileOperateUtil.upload(request, params, values);
        
        map.put("result", result);
        map.put("success", "1");
        
        String user_agent = request.getHeader("user-agent");
        
        System.out.println("user_agent is: " + user_agent);
        
        // return "{'success':'1'}" ; // new ModelAndView("test/ajaxupfile", map);
        // response.setContentType("application/json;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write("{\"ttyy\":\"5677\"}");
        pw.flush();
        pw.close();
    }
    
    @RequestMapping("/test2")
    public void test2(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        
        response.setContentType("application/json;charset=UTF-8");
        // response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.write("{\"success\":\"1\"}");
        pw.flush();
        pw.close();
        
    }
    
    @RequestMapping("/q")
    public ModelAndView test404(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        return new ModelAndView("error/404");
    }
    
    @RequestMapping("/test500")
    public ModelAndView test500(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        return new ModelAndView("error/500");
    }
    
    @RequestMapping("/im")
    public ModelAndView testIm()
    {
        return new ModelAndView("webim/index");
    }
}
