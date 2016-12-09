package com.ygg.webapp.controller;

import com.ygg.webapp.service.IntegralService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/integral")
public class IntegralController
{
    @Resource
    private IntegralService integralService;
    
    // @Resource
    // private AccountService accountService;
    
    /**
     * 我的积分
     * 
     * @return
     */
    @RequestMapping("/myIntegral")
    public ModelAndView myIntegral(HttpServletRequest request)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("integral/integral");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        Map<String, Object> info = integralService.getIntegralInfo(av.getId(), false);
        mv.addObject("totalIntegral", info.get("totalIntegral") + "");
        return mv;
    }
    
    /**
     * 积分明细
     * 
     * @return
     */
    @RequestMapping("/integralDetail")
    public ModelAndView integralDetail(HttpServletRequest request)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("integral/integralDetail");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        Map<String, Object> info = integralService.getIntegralInfo(av.getId(), true);
        mv.addObject("pointDetails", (List<Map<String, Object>>)info.get("pointDetails"));
        return mv;
    }
    
}
