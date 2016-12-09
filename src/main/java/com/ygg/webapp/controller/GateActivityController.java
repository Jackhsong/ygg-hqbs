package com.ygg.webapp.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.entity.GateActivityEntity;
import com.ygg.webapp.entity.GatePrizeEntity;
import com.ygg.webapp.service.CouponDetailService;
import com.ygg.webapp.service.GateActivityService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonUtil;

/**
 * 任意门领取优惠券控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/gate")
public class GateActivityController
{
    private static Logger logger = Logger.getLogger(GateActivityController.class);
    
    @Resource
    private GateActivityService gateActivityService;
    
    @Resource
    private CouponDetailService couponDetailService;
    
    /**
     * web任意门礼品领取  页面
     */
    @RequestMapping("/activity/web")
    public ModelAndView gate(HttpServletRequest request, HttpServletResponse response)
    
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("currentDate", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            GateActivityEntity gate = gateActivityService.findGateActivity(para);
            if (gate == null)
            {
                gate = gateActivityService.findNextOpenGateActivity(para);
                if (gate != null)
                {
                    mv.addObject("nextTime", DateTime.parse(Timestamp.valueOf(gate.getValidTimeStart()).toString(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                        .toString("M月dd日 HH:mm"));
                }
                mv.setViewName("gate/gate_close_wap");
                return mv;
            }
            GatePrizeEntity gatePrize = gateActivityService.findGatePrizeByGateId(gate.getId());
            
            if (gatePrize == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            CouponDetailEntity couponDetail = couponDetailService.findCouponDetailByCouponId(gatePrize.getCouponId());
            if (couponDetail == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            
            Map<String, Object> wxMap = new HashMap<String, Object>();
            
            gate.setReceiveAmount(108 + gate.getReceiveAmount());//设置任意门领取人数默认+108
            String resquestParams = "{'url':'" + gate.getUrl() + "'}";
            CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
            mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
            mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
            mv.addObject("appid", wxMap.get("appid"));
            mv.addObject("timestamp", wxMap.get("timestamp"));
            mv.addObject("nonceStr", wxMap.get("nonceStr"));
            mv.addObject("signature", wxMap.get("signature"));
            mv.addObject("iswx5version", wxMap.get("iswx5version"));
            
            mv.addObject("link", gate.getUrl()); // 分享链接
            //TODO
            mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
            mv.addObject("wxShareDesc", "任意门，一起穿越吧！"); // 分享内容
            mv.addObject("name", "任意门，一起穿越吧！");// 分享标题
            mv.addObject("gateId", gate.getId() + "");
            mv.addObject("gate", gate);
            
            mv.setViewName("gate/gate_open_wap");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    @RequestMapping("/activity/appOpen")
    public ModelAndView gate(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
    
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            //签名验证
            String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
                        System.out.println(verify_sign);
            if (!verify_sign.equals(sign))
            {
                mv.setViewName("error/404");
                return mv;
            }
            
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("currentDate", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            GateActivityEntity gate = gateActivityService.findGateActivity(para);
            if (gate == null)
            {
                // 未开门页面
                gate = gateActivityService.findNextOpenGateActivity(para);
                mv.setViewName("gate/gate_close_app");
                if (gate != null)
                {
                    mv.addObject("nextTime", DateTime.parse(Timestamp.valueOf(gate.getValidTimeStart()).toString(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"))
                        .toString("M月dd日 HH:mm"));
                }
                return mv;
            }
            GatePrizeEntity gatePrize = gateActivityService.findGatePrizeByGateId(gate.getId());
            
            if (gatePrize == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            CouponDetailEntity couponDetail = couponDetailService.findCouponDetailByCouponId(gatePrize.getCouponId());
            if (couponDetail == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            
            String validTime = "";
            int days = 0;
            if (gatePrize.getValidTimeType() == 1)
            {
                CouponEntity coupon = couponDetailService.findCouponById(gatePrize.getCouponId());
                DateTime endTime = DateTime.parse(coupon.getEndTime(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                validTime = endTime.toString("yyyy.MM.dd");
                days = Days.daysBetween(DateTime.now(), endTime).getDays();
            }
            else if (gatePrize.getValidTimeType() == 2)
            {
                // 领券之日N天内有效，可能不准确，有可能用户打开页面没有领券
                validTime = DateTime.now().plusDays(gatePrize.getDays() - 1).toString("yyyy.MM.dd");
                days = gatePrize.getDays();
            }
            
            //accountId == 0，说明app未登录
            mv.addObject("login", accountId > 0);
            
            Map<String, Object> wxMap = new HashMap<String, Object>();
            
            gate.setReceiveAmount(108 + gate.getReceiveAmount());//设置任意门领取人数默认+108
            
            String resquestParams = "{'url':'" + gate.getUrl() + "'}";
            CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
            mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
            mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
            mv.addObject("appid", wxMap.get("appid"));
            mv.addObject("timestamp", wxMap.get("timestamp"));
            mv.addObject("nonceStr", wxMap.get("nonceStr"));
            mv.addObject("signature", wxMap.get("signature"));
            mv.addObject("iswx5version", wxMap.get("iswx5version"));
            
            mv.addObject("link", gate.getUrl()); // 分享链接
            //TODO
            mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
            mv.addObject("wxShareDesc", "任意门，一起穿越吧！"); // 分享内容
            mv.addObject("name", "任意门，一起穿越吧！");// 分享标题
            mv.addObject("accountId", accountId + "");
            mv.addObject("gateId", gate.getId() + "");
            mv.addObject("gate", gate);
            mv.addObject("validTime", validTime);
            mv.addObject("days", days + "");
            
            mv.setViewName("gate/gate_open_app");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /**
     * web输入口令开门
     * @param gateId:任意门Id
     * @param answer:任意门口令
     * @return
     */
    @RequestMapping(value = "/activity/web/openDoor", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String webOpenDoor(@RequestParam(value = "gateId", required = true) int gateId, @RequestParam(value = "answer", required = false, defaultValue = "") String answer)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("id", gateId);
            para.put("answer", answer);
            para.put("currentDate", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            
            resultMap = gateActivityService.webOpenDoor(para);
        }
        catch (Exception e)
        {
            resultMap.put("status", 0);
            logger.error(e.getMessage(), e);
        }
        return JSON.toJSONString(resultMap);
    }
    
    /**
     * app输入口令开门
     * @param accountId：用户账号Id
     * @param gateId：任意门Id
     * @param answer：口令
     * @return
     */
    @RequestMapping(value = "/activity/app/openDoor", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String appOpenDoor(@RequestParam(value = "accountId", required = true) int accountId, @RequestParam(value = "gateId", required = true) int gateId,
        @RequestParam(value = "answer", required = false, defaultValue = "") String answer)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("id", gateId);
            para.put("answer", answer);
            para.put("currentDate", DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            para.put("accountId", accountId);
            resultMap = gateActivityService.appOpenDoor(para);
        }
        catch (Exception e)
        {
            resultMap.put("status", 0);
            logger.error(e.getMessage(), e);
        }
        return JSON.toJSONString(resultMap);
    }
    
    /**
     * 玩任意门领取优惠券
     * @param request
     * @param gateId：任意门Id
     * @param mobileNumber：手机号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/activity/receive", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String receive(@RequestParam(value = "gateId", required = true) int gateId, @RequestParam(value = "phone", required = false, defaultValue = "") String mobileNumber)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            resultMap = gateActivityService.receivePrize(mobileNumber, gateId);
        }
        catch (Exception e)
        {
            logger.error("任意门领奖出错", e);
            resultMap.put("status", 0);
        }
        return JSON.toJSONString(resultMap);
    }
}
