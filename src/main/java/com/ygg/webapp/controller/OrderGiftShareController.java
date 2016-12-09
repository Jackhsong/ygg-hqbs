package com.ygg.webapp.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.YggWebProperties;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.ActivitiesOrderGiftEntity;
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.service.ActivityService;
import com.ygg.webapp.service.OrderGiftShareService;

@Controller
@RequestMapping("/orderGiftShare")
public class OrderGiftShareController
{
    private static Logger log = Logger.getLogger(OrderGiftShareController.class);
    
    @Resource
    private ActivityService activityService;
    
    @Resource
    private OrderGiftShareService orderGiftShareService;
    
    @Resource
    private AccountService accountService;
    
    /**
     * 订单红包领取 wap 页面
     * @param request
     * @param response
     * @param code
     * @param state
     * @return
     * @throws Exception
     */
    @RequestMapping("/wap/{giftId}")
    public ModelAndView wap(HttpServletRequest request, HttpServletResponse response, //
        @PathVariable("giftId") int giftId, //
        @RequestParam(value = "code", required = false, defaultValue = "") String code, // 微信用户同意授权code
        @RequestParam(value = "state", required = false, defaultValue = "") String state,
     @RequestParam(value = "wxId", required = false, defaultValue = "123456789") String wxId // 测试所用
    )
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            ActivitiesOrderGiftEntity aoge = orderGiftShareService.findActivitiesOrderGiftById(giftId);
            if (aoge == null)
            {
                mv.setViewName("error/404");
                log.info("订单红包分享 - 不存在的链接!");
                return mv;
            }
            
            String nickname = request.getSession().getAttribute("weixin_nickname") == null ? null : request.getSession().getAttribute("weixin_nickname") + "";
            String headimgurl = request.getSession().getAttribute("weixin_headimgurl") == null ? null : request.getSession().getAttribute("weixin_headimgurl") + "";
            String openid = request.getSession().getAttribute("weixin_openid") == null ? null : request.getSession().getAttribute("weixin_openid") + "";
            if (nickname == null || headimgurl == null || openid == null)
            {
                String markKey = "order_gift_shareweixin_wap_redirect_mark";
                String mark = request.getSession().getAttribute(markKey) == null ? null : request.getSession().getAttribute(markKey) + "";
                if (!"".equals(code) && !"".equals(state))
                {
                    if (mark == null || (!mark.equals(state)))
                    {
                        code = ""; // code & state 无效
                    }
                }
                
                if ("".equals(code) && !"".equals(state) && (mark != null))
                {
                    // 用户不同意授权
                    request.getSession().removeAttribute(markKey);
                }
                
                // 获取微信code
                if ("".equals(code))
                {
                    // 重定向 获取微信授权
                    String appid = CommonConstant.APPID;
                    String redirect_uri = YggWebProperties.getInstance().getProperties("domain_name") + "/ygg/orderGiftShare/wap/" + giftId;
                    mark = "mark_" + System.currentTimeMillis();
                    request.getSession().setAttribute(markKey, mark);
                    String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + URLEncoder.encode(redirect_uri, "utf-8")
                        + "&response_type=code&scope=snsapi_userinfo&state=" + mark + "#wechat_redirect";
                    response.sendRedirect(getCodeUrl);
                }
                
                log.info("订单红包分享 - 重定向获得code: " + code);
                if ("".equals(code))
                {
                    mv.setViewName("error/404");
                    log.info("订单红包分享 - 微信CODE为空!");
                    return mv;
                }
                Map<String, String> userInfo = activityService.getUserWinxinInfo(code);
                log.info("订单红包分享 - 得到微信用户信息：" + userInfo);
                nickname = userInfo.get("nickname");
                headimgurl = userInfo.get("headimgurl");
                openid = userInfo.get("openid");
                
                if ("".equals(headimgurl))
                {
                    headimgurl = "http://img.gegejia.com/platform/appImage.png";
                }
                
                if ("".equals(nickname) || "".equals(openid))
                {
                    // 获取用户信息失败
                    mv.setViewName("error/404");
                    log.info("订单红包分享 - 用户信息为空!");
                    return mv;
                }
                
                request.getSession().removeAttribute(markKey);
                request.getSession().setAttribute("weixin_nickname", nickname);
                request.getSession().setAttribute("weixin_headimgurl", headimgurl);
                request.getSession().setAttribute("weixin_openid", openid);
            }
            // // 临时调试
            // String nickname = wxId;
            // String headimgurl = "http://yangege.b0.upaiyun.com/activity/activitiesCommon/c31b998c67c9.jpg!v1banner";
            // String openid = wxId;
            request.getSession().setAttribute("weixin_nickname", nickname);
            request.getSession().setAttribute("weixin_headimgurl", headimgurl);
            request.getSession().setAttribute("weixin_openid", openid);
            //
            
            // 红包相关信息
            mv.addObject("totalNum", aoge.getTotalNum()); // 红包总数量
            mv.addObject("leftNum", aoge.getLeftNum()); // 红包剩余数量
            AccountEntity ae = accountService.findAccountById(aoge.getShareAccountId());
            mv.addObject("shareAccountImage", ae.getImage() + ""); // 红包分享人头像
            
            // 微信用户领取信息
            Map<String, Object> receiveInfo = orderGiftShareService.findOrderGiftReceiveInfo(giftId, openid);
            mv.addObject("isReceive", receiveInfo.get("isReceive")); // 是否已经领取过
            mv.addObject("isBindingMobile", receiveInfo.get("isBindingMobile")); // 是否已经绑定过手机号
            mv.addObject("receiveMobile", receiveInfo.get("receiveMobile")); // 绑定手机号
            mv.addObject("reducePrice", receiveInfo.get("reducePrice")); // 领取金额
            
            List<Map<String, Object>> recordList = orderGiftShareService.findRecordByGiftId(giftId);
            mv.addObject("recordList", recordList);
            mv.addObject("giftId", giftId + "");
            // log.info("modelMap: " + mv.getModelMap());
            mv.setViewName("orderGift/wap");
            return mv;
        }
        catch (Exception e)
        {
            log.error("订单红包分享 - 出错！", e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /**
     * 领取订单红包
     *
     * @param mobileNumber
     * @param giftId  订单红包ID
     * @return
     */
    @RequestMapping(value = "/draw", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String draw(HttpServletRequest request, @RequestParam(value = "mobileNumber", required = false, defaultValue = "") String mobileNumber, //
        @RequestParam(value = "giftId", required = false, defaultValue = "0") int giftId)
    {
        Map<String, Object> resultMap = new HashMap<>();
        try
        {
            Map<String, Object> result = new HashMap<>();
            String nickname = request.getSession().getAttribute("weixin_nickname") == null ? null : request.getSession().getAttribute("weixin_nickname") + "";
            String headimgurl = request.getSession().getAttribute("weixin_headimgurl") == null ? null : request.getSession().getAttribute("weixin_headimgurl") + "";
            String openid = request.getSession().getAttribute("weixin_openid") == null ? null : request.getSession().getAttribute("weixin_openid") + "";
            if (nickname == null || headimgurl == null || openid == null)
            {
                log.warn("领取订单红包出错！");
                result.put("status", 0);
                result.put("errorCode", 0);
            }
            else
            {
                result = orderGiftShareService.drawOrderGift(nickname, headimgurl, openid, giftId, mobileNumber);
            }
            resultMap.put("status", 1);
            resultMap.put("result", result);
        }
        catch (Exception e)
        {
            log.error("领取红包抽奖出错！", e);
            resultMap.put("status", 0);
            //            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        System.out.println(resultMap);
        return JSON.toJSONString(resultMap);
    }
    
    /**
     * 修改手机号
     *
     * @param mobileNumber
     * @return
     */
    @RequestMapping(value = "/modifyMobilePhone", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String modifyMobilePhone(HttpServletRequest request, @RequestParam(value = "mobileNumber", required = true) String mobileNumber)
    {
        Map<String, Object> resultMap = new HashMap<>();
        try
        {
            Map<String, Object> result = new HashMap<>();
            String nickname = request.getSession().getAttribute("weixin_nickname") == null ? null : request.getSession().getAttribute("weixin_nickname") + "";
            String headimgurl = request.getSession().getAttribute("weixin_headimgurl") == null ? null : request.getSession().getAttribute("weixin_headimgurl") + "";
            String openid = request.getSession().getAttribute("weixin_openid") == null ? null : request.getSession().getAttribute("weixin_openid") + "";
            if (nickname == null || headimgurl == null || openid == null)
            {
                log.warn("修改手机号出错！");
                result.put("status", 0);
                //                result.put("errorCode", 0);
            }
            else
            {
                result = orderGiftShareService.updateMobilePhone(openid, mobileNumber);
            }
            resultMap.put("status", 1);
            resultMap.put("result", result);
        }
        catch (Exception e)
        {
            log.error("修改手机号出错！", e);
            resultMap.put("status", 0);
            //            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        return JSON.toJSONString(resultMap);
    }
    
}
