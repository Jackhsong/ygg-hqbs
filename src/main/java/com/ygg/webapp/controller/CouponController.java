package com.ygg.webapp.controller;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.code.ErrorCodeEnum;
import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.service.CouponDetailService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/coupon")
public class CouponController
{

    private Logger log = Logger.getLogger(CouponController.class);

    @Resource
    private CouponDetailService couponDetailService;


    /**
     * 我的优惠券
     * @return
     */
    @RequestMapping("/myCoupon")
    public ModelAndView myCoupon(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("coupons/coupons");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        return mv;
    }

    /**
     * 异步获取优惠券列表
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/couponList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String couponList(HttpServletRequest request,@RequestParam(value = "type", required = true, defaultValue = "1") int type)
        throws Exception
    {
        Map<String,Object> result = new HashMap<>();
        try
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            if (av == null)
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ACCOUNT_GETCOUPON_ERRORCODE.ACCOUNTID_NOT_LOGIN.getDescription());
                return JSON.toJSONString(result);
            }
            result = couponDetailService.findCouponAccountInfo(av.getId(),type);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error("异步获取优惠券信息失败！", e);
            result.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.put("msg","服务器忙");
            return JSON.toJSONString(result);
        }
    }

    /**
     * 使用优惠券
     * @return
     */
    @RequestMapping("/useCoupon")
    public ModelAndView useCoupon()
    {
        ModelAndView mv = new ModelAndView("order/useCoupon");
        return mv;
    }

    /**
     * 兑换优惠券
     * @param request
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/codeChangeCoupon", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String codeChangeCoupon(HttpServletRequest request,
        @RequestParam(value = "code", required = true) String  code //优惠码
    )
        throws Exception
    {
        Map<String,Object> result = new HashMap<>();
        try
        {
            AccountView av = SessionUtil.getCurrentUser(request.getSession());
            if (av == null)
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ACCOUNT_GETCOUPON_ERRORCODE.ACCOUNTID_NOT_LOGIN.getDescription());
                return JSON.toJSONString(result);
            }
            result = couponDetailService.executeCodeChangeCoupon(av.getId(),code);
            System.out.println(request);
        }
        catch (Exception e)
        {
            log.error("兑换优惠券失败！", e);
            result.put("status",CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.put("msg","服务器忙");
        }
        return JSON.toJSONString(result);
    }

}
