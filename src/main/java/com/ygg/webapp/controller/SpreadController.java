package com.ygg.webapp.controller;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.FreeMarkerUtil;

@Controller("spreadController")
public class SpreadController
{
    
    Logger log = Logger.getLogger(SpreadController.class);
    
    @Resource(name = "accountService")
    private AccountService accountService;
    
    @RequestMapping("/spread/{code}")
    public void spread(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        Writer writer = response.getWriter();
        Map<String, Object> mv = new HashMap<String, Object>();
        
        mv.put("code", code); // 是否要对code作判断
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/spread/" + code + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, mv);
        mv.put("link", "http://m.gegejia.com/ygg/spread/" + code); // 分享链接
        mv.put("imgUrl", "http://yangege.b0.upaiyun.com/178db730ca2a6.png"); // 分享图标
        mv.put("wxShareDesc", "左岸城堡，进口食品免税店，精选全球美食，实现吃货终极梦想。"); // 分享内容
        mv.put("name", "快来领取左岸城堡进口食品APP 20元现金券");// 分享标题
        String contentHtml = FreeMarkerUtil.createHtml("spread/index.ftl", mv);
        
        writer.write(contentHtml);
        writer.flush();
        writer.close();
        return;
    }
    
    @RequestMapping("/spreadGift/{code}")
    public ModelAndView spreadGift(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (!accountService.checkIfExistsRecommendedCode(code))
        {
            mv.setViewName("error/404");
            return mv;
        }
        String begin = DateTime.now().withTimeAtStartOfDay().toString("yyyy-MM-dd HH:mm:ss");
        String end = DateTime.now().withTimeAtStartOfDay().plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
        if (accountService.countPrepRecommended(code, begin, end) > 1000)
        {
            mv.setViewName("error/404");
            return mv;
        }
        
        mv.addObject("code", code);
        
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/spreadGift/" + code + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/spreadGift/" + code); // 分享链接
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/1d3cff76816f8.png"); // 分享图标
        mv.addObject("wxShareDesc", "左岸城堡，进口食品免税店，精选全球美食，实现吃货终极梦想。"); // 分享内容
        mv.addObject("name", "快来领取左岸城堡进口食品APP 10元现金券");// 分享标题
        mv.setViewName("spread/invite");
        return mv;
    }
    
    @RequestMapping("/appSpreadGift/{code}")
    public ModelAndView appSpreadGift(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (!accountService.checkIfExistsRecommendedCode(code))
        {
            mv.setViewName("error/404");
            return mv;
        }
        String begin = DateTime.now().withTimeAtStartOfDay().toString("yyyy-MM-dd HH:mm:ss");
        String end = DateTime.now().withTimeAtStartOfDay().plusDays(1).toString("yyyy-MM-dd HH:mm:ss");
        if (accountService.countPrepRecommended(code, begin, end) > 1000)
        {
            mv.setViewName("error/404");
            return mv;
        }
        
        mv.addObject("code", code);
        
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/spreadGift/" + code + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/spreadGift/" + code); // 分享链接
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/1d3cff76816f8.png"); // 分享图标
        mv.addObject("wxShareDesc", "左岸城堡，进口食品免税店，精选全球美食，实现吃货终极梦想。"); // 分享内容
        mv.addObject("name", "快来领取左岸城堡进口食品APP 10元现金券");// 分享标题
        mv.setViewName("spread/app_invite");
        return mv;
    }
}
