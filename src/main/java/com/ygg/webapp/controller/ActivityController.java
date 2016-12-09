package com.ygg.webapp.controller;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ygg.webapp.code.AccessTypeEnum;
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.service.ActivityService;
import com.ygg.webapp.service.TempActivityService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.YggWebProperties;

/**
 * 活动相关
 * 
 * @author xiongl
 *         
 */
@Controller
@RequestMapping("/activity")
public class ActivityController
{
    private static Logger logger = Logger.getLogger(ActivityController.class);
    
    @Resource
    private ActivityService activityService;
    
    @Resource
    private AccountService accountService;
    
    @Resource
    private TempActivityService tempActivityService;
    
    /**
     * 网页访问精品特卖活动
     * 
     * @param activityId：活动Id
     * @return
     */
    @RequestMapping("/simplify/web/{activityId}")
    public ModelAndView accessSimplifyActivityByWap(@PathVariable("activityId") int activityId)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("activity/activity_simplify_wap");
            
            Map<String, Object> resultMap = activityService.findActivitySimplifyDetailById(activityId, AccessTypeEnum.TYPE_OF_WAP.getValue());
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
    
    /**
     * app访问精品活动
     * 
     * @param activityId：活动Id
     * @param accountId：用户Id
     * @param sign：用户标识
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/simplify/appOpen")
    public ModelAndView accessSimplifyActivityByApp(@RequestParam(value = "activityId", required = true) int activityId, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign, //
        HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("activity/activity_simplify_app");
            
            Map<String, Object> resultMap = activityService.findActivitySimplifyDetailById(activityId, AccessTypeEnum.TYPE_OF_APP.getValue());
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
    
    /**
     * app访问疯狂美食活动
     * 
     * @param activityId
     * @param accountId
     * @param sign
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping("/crazyFood/appOpen")
    public ModelAndView accessCrazyFoodActivityByApp(@RequestParam(value = "activityId", required = true) int activityId, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign, //
        HttpServletRequest request, HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            if (!activityService.existsActivityCrazyFood(activityId))
            {
                mv.setViewName("error/404");
                logger.info("疯狂抽美食--活动Id=" + activityId + "不存在！！！");
                return mv;
            }
            
            // 签名验证
            String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
            System.out.println(verify_sign);
            if (!verify_sign.equals(sign))
            {
                mv.setViewName("error/404");
                logger.info(String.format("app访问砸金蛋活动用户签名不匹配，实际签名为%s，收到签名为%s", verify_sign, sign));
                return mv;
            }
            int leftTimes = 2;
            // accountId == 0，说明app未登录
            mv.addObject("login", accountId > 0);
            
            if (accountId > 0)
            {
                leftTimes = activityService.getCrazyFoodActivityLeftTimes(accountId, activityId);
            }
            mv.addObject("leftTimes", leftTimes + "");
            Map<String, Object> wxMap = new HashMap<String, Object>();
            
            String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/crazyFood/web/" + activityId + "'}";// 分享出去的页面不要app用户信息
            CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
            mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
            mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
            mv.addObject("appid", wxMap.get("appid"));
            mv.addObject("timestamp", wxMap.get("timestamp"));
            mv.addObject("nonceStr", wxMap.get("nonceStr"));
            mv.addObject("signature", wxMap.get("signature"));
            mv.addObject("iswx5version", wxMap.get("iswx5version"));
            
            mv.addObject("link", "http://m.gegejia.com/ygg/activity/crazyFood/web/" + activityId); // 分享链接
            mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
            mv.addObject("wxShareDesc", "给你2次成为土壕的机会,快来试试~"); // 分享内容
            mv.addObject("name", "给你2次成为土壕的机会,快来试试~");// 分享标题
            mv.addObject("activityId", activityId + "");
            mv.addObject("accountId", accountId + "");
            mv.setViewName("activity/activity_crazyFood_app");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    *//**
      * wap访问疯狂美食活动
      *
      * @param activityId
      * @param request
      * @param response
      * @return
      */
    /*
    @RequestMapping("/crazyFood/web/{activityId}")
    public ModelAndView accessCrazyFoodActivityByWap(@PathVariable("activityId") int activityId, HttpServletRequest request, HttpServletResponse response)
    {
     ModelAndView mv = new ModelAndView();
     try
     {
         if (!activityService.existsActivityCrazyFood(activityId))
         {
             mv.setViewName("error/404");
             logger.info("疯狂抽美食--活动Id=" + activityId + "不存在！！！");
             return mv;
         }
         Map<String, Object> wxMap = new HashMap<String, Object>();
         
         String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/crazyFood/web/" + activityId + "'}";// 分享出去的页面不要app用户信息
         CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
         mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
         mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
         mv.addObject("appid", wxMap.get("appid"));
         mv.addObject("timestamp", wxMap.get("timestamp"));
         mv.addObject("nonceStr", wxMap.get("nonceStr"));
         mv.addObject("signature", wxMap.get("signature"));
         mv.addObject("iswx5version", wxMap.get("iswx5version"));
         
         mv.addObject("link", "http://m.gegejia.com/ygg/activity/crazyFood/web/" + activityId); // 分享链接
         mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
         mv.addObject("wxShareDesc", "给你2次成为土壕的机会,快来试试~"); // 分享内容
         mv.addObject("name", "给你2次成为土壕的机会,快来试试~");// 分享标题
         mv.addObject("activityId", activityId + "");
         mv.setViewName("activity/activity_crazyFood_wap");
     }
     catch (Exception e)
     {
         logger.error(e.getMessage(), e);
         mv.setViewName("error/404");
     }
     return mv;
    }
    
    *//**
      * 疯狂美食抽奖
      *
      * @param activityId
      * @param mobileNumber
      * @param accountId
      * @return
      */
    /*
    @RequestMapping(value = "/crazyFood/draw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String draw(@RequestParam(value = "activityId", required = true) int activityId,
     @RequestParam(value = "phoneNo", required = false, defaultValue = "") String mobileNumber,
     @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId)
    {
     Map<String, Object> resultMap = new HashMap<String, Object>();
     try
     {
         resultMap = activityService.draw(accountId, mobileNumber, activityId);
     }
     catch (Exception e)
     {
         logger.error("抽奖出错", e);
         resultMap.put("status", 0);
         resultMap.put("msg", "服务器忙~ 请稍后再试~");
     }
     return JSON.toJSONString(resultMap);
    }*/
    
    //    /**
    //     * wap访问疯狂美食活动
    //     *
    //     * @param activityId
    //     * @param request
    //     * @param response
    //     * @return
    //     */
    //    @RequestMapping("/crazyFood/web/{activityId}")
    //    public ModelAndView accessCrazyFoodActivityByWap(@PathVariable("activityId") int activityId, HttpServletRequest request, HttpServletResponse response)
    //    {
    //        ModelAndView mv = new ModelAndView();
    //        try
    //        {
    //            //            if (!activityService.existsActivityCrazyFood(activityId))
    //            //            {
    //            //                mv.setViewName("error/404");
    //            //                logger.info("疯狂抽美食--活动Id=" + activityId + "不存在！！！");
    //            //                return mv;
    //            //            }
    //            mv.addObject("activityId", "6");
    //            mv.setViewName("activity/activity_crazyFood_wap2");
    //        }
    //        catch (Exception e)
    //        {
    //            logger.error(e.getMessage(), e);
    //            mv.setViewName("error/404");
    //        }
    //        return mv;
    //    }
    //    
    //    /**
    //     * 疯狂美食抽奖
    //     *
    //     * @param activityId
    //     * @param mobileNumber
    //     * @return
    //     */
    //    @RequestMapping(value = "/crazyFood/draw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    //    @ResponseBody
    //    public String draw(@RequestParam(value = "activityId", required = true) int activityId,
    //        @RequestParam(value = "phoneNo", required = false, defaultValue = "") String mobileNumber)
    //    {
    //        Map<String, Object> resultMap = new HashMap<>();
    //        try
    //        {
    //            resultMap = activityService.drawTemp(mobileNumber, activityId);
    //        }
    //        catch (Exception e)
    //        {
    //            logger.error("抽奖出错", e);
    //            resultMap.put("status", 0);
    //            resultMap.put("msg", "服务器忙~ 请稍后再试~");
    //        }
    //        return JSON.toJSONString(resultMap);
    //    }
    
    /**
     * 疯狂抽美食分享增加次数
     * 
     * @param accountId
     * @param activityId
     * @return
     */
    /*@RequestMapping(value = "/crazyFood/appShare", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String shareActivity(@RequestParam(value = "accountId", required = true) int accountId, @RequestParam(value = "activityId", required = true) int activityId)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            resultMap = activityService.shareActivity(accountId, activityId);
        }
        catch (Exception e)
        {
            resultMap.put("status", 0);
            resultMap.put("msg", "出错啦~");
        }
        return JSON.toJSONString(resultMap);
    }*/
    
    /**
     * 领取红包 wap页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/packet/web/{accountId}")
    public ModelAndView packet(HttpServletRequest request, HttpServletResponse response, //
        @PathVariable("accountId") int accountId, @RequestParam(value = "code", required = false, defaultValue = "") String code, // 微信用户同意授权code
        @RequestParam(value = "state", required = false, defaultValue = "") String state)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            if (!accountService.existsAccount(accountId))
            {
                mv.setViewName("error/404");
                logger.info("用户ID不存在!");
                return mv;
            }
            
            String mark = request.getSession().getAttribute("weixin_redirect_mark") == null ? null : request.getSession().getAttribute("weixin_redirect_mark") + "";
            if (!"".equals(code) && !"".equals(state))
            {
                if (mark == null || (!mark.equals(state)))
                {
                    // code & state 无效
                    code = "";
                }
            }
            
            if ("".equals(code) && !"".equals(state) && (mark != null))
            {
                // 用户不同意授权
                request.getSession().removeAttribute("weixin_redirect_mark");
                //                mv.setViewName("error/404");
                //                logger.info("用户不同意授权!");
                //                return mv;
            }
            
            // 获取微信code
            if ("".equals(code))
            {
                // 重定向 获取微信授权
                String appid = CommonConstant.APPID;
                String redirect_uri = YggWebProperties.getInstance().getProperties("domain_name") + "/ygg/activity/packet/web/" + accountId;
                mark = "mark_" + System.currentTimeMillis();
                request.getSession().setAttribute("weixin_redirect_mark", mark);
                String getCodeUrl =
                    "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appid + "&redirect_uri=" + URLEncoder.encode(redirect_uri, "utf-8")
                        + "&response_type=code&scope=snsapi_userinfo&state=" + mark + "#wechat_redirect";
                response.sendRedirect(getCodeUrl);
            }
            
            logger.info("重定向获得code: " + code);
            if ("".equals(code))
            {
                mv.setViewName("error/404");
                logger.info("微信CODE为空!");
                return mv;
            }
            Map<String, String> userInfo = activityService.getUserWinxinInfo(code);
            logger.info("得到微信用户信息：" + userInfo);
            String nickname = userInfo.get("nickname");
            String headimgurl = userInfo.get("headimgurl");
            String openid = userInfo.get("openid");
            
            //             String nickname = System.currentTimeMillis() + "";
            //             String headimgurl = "";
            //             String openid = System.currentTimeMillis() + "";;
            
            if ("".equals(headimgurl))
            {
                headimgurl = "http://img.gegejia.com/platform/appImage.png";
            }
            
            if ("".equals(nickname) || "".equals(openid))
            {
                // 获取用户信息失败
                mv.setViewName("error/404");
                logger.info("用户信息为空!");
                return mv;
            }
            request.getSession().removeAttribute("weixin_redirect_mark");
            request.getSession().setAttribute("weixin_nickname", nickname);
            request.getSession().setAttribute("weixin_headimgurl", headimgurl);
            request.getSession().setAttribute("weixin_openid", openid);
            // 获取 红包领取 信息
            Map<String, Object> drawInfo = activityService.getDrawRedPacketInfo(openid);
            mv.addObject("isDraw", drawInfo.get("isDraw") + "");
            mv.addObject("reduce", drawInfo.get("reduce") + "");
            
            Map<String, Object> result = activityService.findRedPacketDrawInfo(accountId, 1);
            List<Map<String, Object>> drawRecord = (List<Map<String, Object>>)result.get("drawRecord");
            mv.addObject("drawRecord", drawRecord);
            mv.addObject("accountId", accountId + "");
            mv.setViewName("activity/wap_red_packet");
            logger.info("isDraw : " + drawInfo.get("isDraw"));
            logger.info("reduce : " + drawInfo.get("reduce"));
            logger.info("accountId : " + accountId);
            logger.info("drawRecord : " + drawRecord);
        }
        catch (Exception e)
        {
            logger.error("疯狂派红包出错！", e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /**
     * 领取红包 抽奖
     * 
     * @param mobileNumber
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/packet/draw", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String packetDraw(HttpServletRequest request, @RequestParam(value = "mobileNumber", required = false, defaultValue = "") String mobileNumber,
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId)
    {
        Map<String, Object> resultMap = new HashMap<>();
        try
        {
            String nickname = request.getSession().getAttribute("weixin_nickname") == null ? null : request.getSession().getAttribute("weixin_nickname") + "";
            String headimgurl = request.getSession().getAttribute("weixin_headimgurl") == null ? null : request.getSession().getAttribute("weixin_headimgurl") + "";
            String openid = request.getSession().getAttribute("weixin_openid") == null ? null : request.getSession().getAttribute("weixin_openid") + "";
            if (nickname == null || headimgurl == null || openid == null)
            {
                logger.warn("领取红包抽奖出错！");
                resultMap.put("status", 0);
                resultMap.put("msg", "页面已过期，请刷新后再试~");
            }
            else
            {
                resultMap = activityService.drawPacket(nickname, headimgurl, openid, accountId, mobileNumber);
            }
        }
        catch (Exception e)
        {
            logger.error("领取红包抽奖出错！", e);
            resultMap.put("status", 0);
            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        return JSON.toJSONString(resultMap);
    }
    
    /**
     * 领取红包 app主页
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/packet/app/{accountId}")
    public ModelAndView packet(HttpServletRequest request, HttpServletResponse response, //
        @PathVariable("accountId") int accountId)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.addObject("isLogin", accountId > 0 ? "1" : "0");
            Map<String, Object> result = activityService.findRedPacketDrawInfo(accountId, 0);
            mv.addObject("isShare", result.get("isShare") + "");
            mv.addObject("isSuccess", result.get("isSuccess") + "");
            mv.addObject("maxReduce", result.get("maxReduce") + "");
            mv.addObject("drawNums", result.get("drawNums") + "");
            List<Map<String, Object>> drawRecord = (List<Map<String, Object>>)result.get("drawRecord");
            mv.addObject("drawRecord", drawRecord);
            mv.setViewName("activity/app_red_packet");
            logger.info("isShare : " + result.get("isShare"));
            logger.info("isSuccess : " + result.get("isSuccess"));
            logger.info("maxReduce : " + result.get("maxReduce"));
            logger.info("drawNums : " + result.get("drawNums"));
            logger.info("drawRecord : " + drawRecord);
        }
        catch (Exception e)
        {
            logger.error("领取红包 app主页！", e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /**
     * app分享红包
     *
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/packet/share/{accountId}", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String packetShare(@PathVariable("accountId") int accountId)
    {
        Map<String, Object> resultMap = new HashMap<>();
        try
        {
            resultMap = activityService.packetShare(accountId);
        }
        catch (Exception e)
        {
            logger.error("app分享红包处理出错！", e);
            resultMap.put("status", 0);
            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        return JSON.toJSONString(resultMap);
    }
    
    /**
     * 美食迎新会：wap访问
     * @return
     */
    @RequestMapping("/foodParty/web")
    public ModelAndView accessFoodPatryActivityByWap()
    {
        ModelAndView mv = new ModelAndView("activity/activity_ganenjie_web");
        mv.addObject("requestFrom", "1");//来自wap
        return mv;
    }
    
    /**
     * 美食迎新会：app访问
     * @param accountId
     * @param sign
     * @return
     */
    @RequestMapping("/foodParty/appOpen")
    public ModelAndView accessFoodPartyActivityByApp(@RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("activity/activity_ganenjie_app");
            
            String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
            System.out.println(verify_sign);
            if (!verify_sign.equals(sign))
            {
                mv.setViewName("error/404");
                logger.info("疯狂抽美食--用户签名sign=" + sign + "不匹配！！！");
                return mv;
            }
            // accountId == 0，说明app未登录
            mv.addObject("login", accountId > 0);
        }
        catch (Exception e)
        {
            logger.error("app访问美食迎新活动出错", e);
            mv.setViewName("error/404");
        }
        mv.addObject("accountId", accountId + "");
        mv.addObject("requestFrom", "2");//来自app
        return mv;
    }
    
    /**
     * 美食迎新会领取奖励
     * @param accountId：帐号Id
     * @param phoneNumber：手机号
     * @param requestFrom：请求来源，1：网页；2：app
     * @return
     */
    @RequestMapping(value = "/foodParty/receive", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String foodPartyReceive(@RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,
        @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
        @RequestParam(value = "requestFrom", required = false, defaultValue = "1") String requestFrom)
    {
        Map<String, Object> resultMap = new HashMap<>();
        try
        {
            resultMap = activityService.receiveFoodPartyPrize(accountId, phoneNumber, requestFrom);
        }
        catch (Exception e)
        {
            logger.error("美食迎新会领取奖励出错！", e);
            resultMap.put("status", 0);
            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        return JSON.toJSONString(resultMap);
    }
    
    // 线下疯狂抽美食
    //    /**
    //     * wap访问疯狂美食活动
    //     *
    //     * @param activityId
    //     * @param request
    //     * @param response
    //     * @return
    //     */
    //    @RequestMapping("/crazyFood/web/{activityId}")
    //    public ModelAndView accessCrazyFoodActivityByWap(@PathVariable("activityId") int activityId, HttpServletRequest request, HttpServletResponse response)
    //    {
    //        ModelAndView mv = new ModelAndView();
    //        try
    //        {
    //            if (!activityService.existsActivityCrazyFood(activityId))
    //            {
    //                mv.setViewName("error/404");
    //                logger.info("疯狂抽美食--活动Id=" + activityId + "不存在！！！");
    //                return mv;
    //            }
    //            Map<String, Object> wxMap = new HashMap<String, Object>();
    //
    //            String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/crazyFood/web/" + activityId + "'}";// 分享出去的页面不要app用户信息
    //            CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
    //            mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
    //            mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
    //            mv.addObject("appid", wxMap.get("appid"));
    //            mv.addObject("timestamp", wxMap.get("timestamp"));
    //            mv.addObject("nonceStr", wxMap.get("nonceStr"));
    //            mv.addObject("signature", wxMap.get("signature"));
    //            mv.addObject("iswx5version", wxMap.get("iswx5version"));
    //
    //            mv.addObject("link", "http://m.gegejia.com/ygg/activity/crazyFood/web/" + activityId); // 分享链接
    //            mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
    //            mv.addObject("wxShareDesc", "给你2次成为土壕的机会,快来试试~"); // 分享内容
    //            mv.addObject("name", "给你2次成为土壕的机会,快来试试~");// 分享标题
    //            mv.addObject("activityId", activityId + "");
    //            mv.setViewName("activity/activity_crazyFood_wap");
    //        }
    //        catch (Exception e)
    //        {
    //            logger.error(e.getMessage(), e);
    //            mv.setViewName("error/404");
    //        }
    //        return mv;
    //    }
    //
    //    /**
    //     * 疯狂美食抽奖
    //     *
    //     * @param activityId
    //     * @param mobileNumber
    //     * @param accountId
    //     * @return
    //     */
    //    @RequestMapping(value = "/crazyFood/draw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    //    @ResponseBody
    //    public String draw(@RequestParam(value = "activityId", required = true) int activityId,
    //        @RequestParam(value = "phoneNo", required = false, defaultValue = "") String mobileNumber,
    //        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId)
    //    {
    //        Map<String, Object> resultMap = new HashMap<String, Object>();
    //        try
    //        {
    //            resultMap = activityService.draw(accountId, mobileNumber, activityId);
    //        }
    //        catch (Exception e)
    //        {
    //            logger.error("抽奖出错", e);
    //            resultMap.put("status", 0);
    //            resultMap.put("msg", "服务器忙~ 请稍后再试~");
    //        }
    //        return JSON.toJSONString(resultMap);
    //    }
    
    /**
     * 红色星期五：wap访问
     * @return
     */
    @RequestMapping("/redFriday/web")
    public ModelAndView accessRedFridayActivityByWap()
    {
        ModelAndView mv = new ModelAndView("activity/activity_100yuan_web");
        mv.addObject("requestFrom", "1");//来自wap
        return mv;
    }
    
    /**
     * 红色星期五：app访问
     * @param accountId
     * @param sign
     * @return
     */
    @RequestMapping("/redFriday/appOpen")
    public ModelAndView accessRedFridayActivityByApp(@RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            mv.setViewName("activity/activity_100yuan_app");
            
            String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
            if (!verify_sign.equals(sign))
            {
                mv.setViewName("error/404");
                logger.info(String.format("app访问红色星期五活动用户签名不匹配，实际签名为%s，收到签名为%s", verify_sign, sign));
                return mv;
            }
            // accountId == 0，说明app未登录
            mv.addObject("login", accountId > 0);
        }
        catch (Exception e)
        {
            logger.error("app访问红色星期五活动出错", e);
            mv.setViewName("error/404");
        }
        mv.addObject("accountId", accountId + "");
        mv.addObject("requestFrom", "2");//来自app
        return mv;
    }
    
    /**
     * 红色星期五领取奖励
     * @param accountId：帐号Id
     * @param phoneNumber：手机号
     * @param requestFrom：请求来源，1：网页；2：app
     * @return
     */
    @RequestMapping(value = "/redFriday/receive", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String redFridayReceive(@RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,
        @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
        @RequestParam(value = "requestFrom", required = false, defaultValue = "1") String requestFrom)
    {
        Map<String, Object> resultMap = new HashMap<>();
        try
        {
            resultMap = activityService.receiveRedFridayPrize(accountId, phoneNumber, requestFrom);
        }
        catch (Exception e)
        {
            logger.error("红色星期五领取奖励出错！", e);
            resultMap.put("status", 0);
            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        return JSON.toJSONString(resultMap);
    }
    
    /**
     * 3.7女神节wap
     * @return
     * @throws Exception
     */
    @RequestMapping("/goddess/wap")
    public ModelAndView goddessWeb()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("activity/goddess_web");
        mv.addObject("requestFrom", AccessTypeEnum.TYPE_OF_WAP.getValue());
        return mv;
    }
    
    /**
     * 3.7女神节app
     * @param accountId
     * @param sign
     * @return
     * @throws Exception
     */
    @RequestMapping("/goddess/app")
    public ModelAndView goddessApp(@RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
        throws Exception
    {
        ModelAndView mv = new ModelAndView("activity/goddess_app");
        String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
        if (!verify_sign.equals(sign))
        {
            mv.setViewName("error/404");
            logger.info(String.format("app访问3.7女神节活动用户签名不匹配，实际签名为%s，收到签名为%s", verify_sign, sign));
            return mv;
        }
        mv.addObject("requestFrom", AccessTypeEnum.TYPE_OF_APP.getValue());
        mv.addObject("login", accountId > 0 ? 1 : 0);
        mv.addObject("received", activityService.findGoddessPrizeRecordByAccountId(accountId) ? 1 : 0);
        mv.addObject("accountId", accountId + "");
        return mv;
    }
    
    @RequestMapping(value = "/goddess/getData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getData(@RequestParam(value = "requestFrom", required = false, defaultValue = "1") int type)
        throws Exception
    {
        return JSON.toJSONString(tempActivityService.findAllTempActivity(type));
    }
    
    @RequestMapping(value = "/goddess/receive", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String goddessReceive(@RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,
        @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
        @RequestParam(value = "requestFrom", required = false, defaultValue = "1") int requestFrom)
    {
        try
        {
            return activityService.receiveGoddessPrize(accountId, phoneNumber, requestFrom);
        }
        catch (Exception e)
        {
            logger.error("3.7女神节领取奖励出错！", e);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("status", 0);
            resultMap.put("msg", "服务器忙~ 请稍后再试~");
            return JSON.toJSONString(resultMap);
        }
    }
    
    @RequestMapping("/anniversary/wap")
    public ModelAndView anniversaryWap()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("anniversary/anniversary_wap");
        Map<String, Object> info = tempActivityService.findAnniversaryProduct(AccessTypeEnum.TYPE_OF_WAP.getValue());
        mv.addObject("timeType", info.get("timeType").toString());
        mv.addObject("productList", info.get("productList"));
        mv.addObject("groupList", info.get("groupList"));
        mv.addObject("recommendList", info.get("recommendList"));
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_WAP.getValue());
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-21 10:00:00", "yyyy-MM-dd HH:mm:ss");
        mv.addObject("seconds", (end.getTime() - start.getTime()) > 0 ? (end.getTime() - start.getTime()) / 1000 + "" : "0");
        return mv;
    }
    
    @RequestMapping("/anniversary/app")
    public ModelAndView anniversaryApp()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("anniversary/anniversary_app");
        Map<String, Object> info = tempActivityService.findAnniversaryProduct(AccessTypeEnum.TYPE_OF_APP.getValue());
        mv.addObject("timeType", info.get("timeType").toString());
        mv.addObject("productList", info.get("productList"));
        mv.addObject("groupList", info.get("groupList"));
        mv.addObject("recommendList", info.get("recommendList"));
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_APP.getValue());
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-21 10:00:00", "yyyy-MM-dd HH:mm:ss");
        mv.addObject("seconds", (end.getTime() - start.getTime()) > 0 ? (end.getTime() - start.getTime()) / 1000 + "" : "0");
        return mv;
    }
    
    @RequestMapping(value = "/anniversary/getProductDate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getProductDate(@RequestParam(value = "timeType", required = false, defaultValue = "0") int timeType,
        @RequestParam(value = "clientType", required = false, defaultValue = "1") int clientType)
    {
        return tempActivityService.getAnniversaryProductDate(timeType, clientType);
    }
    
    @RequestMapping(value = "/anniversary/refreshTime", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String anniversaryRefreshTime()
    {
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-21 10:00:00", "yyyy-MM-dd HH:mm:ss");
        long time = (end.getTime() - start.getTime()) / 1000;
        return JSON.toJSONString(time > 0 ? time + "" : "0");
    }
    
    /**
     * app访问幸运大转盘活动
     * 
     * @param activityId
     * @param accountId
     * @param sign
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/crazyFood/appOpen")
    public ModelAndView accessCrazyFoodActivityByApp(@RequestParam(value = "activityId", required = true, defaultValue = "2") int activityId, //
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId, //
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign)
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            if (!activityService.existsActivityCrazyFood(activityId))
            {
                mv.setViewName("error/404");
                logger.info("app访问幸运大转盘活动Id=" + activityId + "不存在！！！");
                return mv;
            }
            
            String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
            if (!verify_sign.equals(sign))
            {
                mv.setViewName("error/404");
                logger.info(String.format("app访问幸运大转盘活动用户签名不匹配，实际签名为%s，收到签名为%s", verify_sign, sign));
                return mv;
            }
            mv.addObject("login", accountId > 0);
            
            int leftTimes = activityService.updateCrazyFoodActivityLeftTimes(accountId, activityId);
            System.out.println(leftTimes);
            mv.addObject("accountId", accountId + "");
            mv.addObject("leftTimes", leftTimes);
            mv.setViewName("activity/activity_crazyFood_app");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    @RequestMapping("/crazyFood/web")
    public ModelAndView accessCrazyFoodActivityByWap()
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("activity/activity_crazyFood_wap");
        return mv;
    }
    
    /**
     * 疯狂美食抽奖
     *
     * @param activityId
     * @param mobileNumber
     * @param accountId
     * @return
     */
    @RequestMapping(value = "/crazyFood/draw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String draw(@RequestParam(value = "activityId", required = true, defaultValue = "2") int activityId,
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId)
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try
        {
            resultMap = activityService.drawCrazyFood(accountId, activityId);
        }
        catch (Exception e)
        {
            logger.error("幸运大转盘活动抽奖出错", e);
            resultMap.put("status", 0);
            resultMap.put("msg", "服务器忙~ 请稍后再试~");
        }
        return JSON.toJSONString(resultMap);
    }
    
    @RequestMapping("/anniversaryCurrent/wap")
    public ModelAndView anniversaryCurrentWap()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("endAnniversary/endAnniversary_wap");
        Map<String, Object> info = tempActivityService.findAnniversaryCurrentProduct(AccessTypeEnum.TYPE_OF_WAP.getValue());
        mv.addObject("timeType", info.get("timeType").toString());
        mv.addObject("productList", info.get("productList"));
        mv.addObject("saleTopList", info.get("saleTopList"));//热销榜
        mv.addObject("commentTopList", info.get("commentTopList"));//口碑榜
        mv.addObject("repeatTopList", info.get("repeatTopList"));//复购榜
        mv.addObject("popularTopList", info.get("popularTopList"));//人气榜
        mv.addObject("groupList", info.get("groupList"));//品牌团
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_WAP.getValue());
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-22 10:00:00", "yyyy-MM-dd HH:mm:ss");
        mv.addObject("seconds", (end.getTime() - start.getTime()) > 0 ? (end.getTime() - start.getTime()) / 1000 + "" : "0");
        return mv;
    }
    
    @RequestMapping("/anniversaryCurrent/app")
    public ModelAndView anniversaryCurrentApp()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("endAnniversary/endAnniversary_app");
        Map<String, Object> info = tempActivityService.findAnniversaryCurrentProduct(AccessTypeEnum.TYPE_OF_APP.getValue());
        mv.addObject("timeType", info.get("timeType").toString());
        mv.addObject("productList", info.get("productList"));
        mv.addObject("saleTopList", info.get("saleTopList"));//热销榜
        mv.addObject("commentTopList", info.get("commentTopList"));//口碑榜
        mv.addObject("repeatTopList", info.get("repeatTopList"));//复购榜
        mv.addObject("popularTopList", info.get("popularTopList"));//人气榜
        mv.addObject("groupList", info.get("groupList"));//品牌团
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_APP.getValue());
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-22 10:00:00", "yyyy-MM-dd HH:mm:ss");
        mv.addObject("seconds", (end.getTime() - start.getTime()) > 0 ? (end.getTime() - start.getTime()) / 1000 + "" : "0");
        return mv;
    }
    
    @RequestMapping(value = "/anniversaryCurrent/getProductDate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String anniversaryCurrentGetProductDate(@RequestParam(value = "timeType", required = false, defaultValue = "0") int timeType,
        @RequestParam(value = "clientType", required = false, defaultValue = "1") int clientType)
    {
        return tempActivityService.getAnniversaryCurrentProductData(timeType, clientType);
    }
    
    @RequestMapping(value = "/anniversaryCurrent/refreshTime", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String anniversaryCurrentRefreshTime()
    {
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-22 10:00:00", "yyyy-MM-dd HH:mm:ss");
        long time = (end.getTime() - start.getTime()) / 1000;
        return JSON.toJSONString(time > 0 ? time + "" : "0");
    }
    
    @RequestMapping("/anniversaryNew/wap")
    public ModelAndView anniversaryNewWap()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("endAnniversary/newEndAnniversary_wap");
        Map<String, Object> info = tempActivityService.findAnniversaryCurrentProduct(AccessTypeEnum.TYPE_OF_WAP.getValue());
        mv.addObject("timeType", info.get("timeType").toString());
        mv.addObject("productList", info.get("productList"));
        mv.addObject("saleTopList", info.get("saleTopList"));//热销榜
        mv.addObject("commentTopList", info.get("commentTopList"));//口碑榜
        mv.addObject("repeatTopList", info.get("repeatTopList"));//复购榜
        mv.addObject("popularTopList", info.get("popularTopList"));//人气榜
        mv.addObject("groupList", info.get("groupList"));//品牌团
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_WAP.getValue());
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-24 10:00:00", "yyyy-MM-dd HH:mm:ss");
        mv.addObject("seconds", (end.getTime() - start.getTime()) > 0 ? (end.getTime() - start.getTime()) / 1000 + "" : "0");
        return mv;
    }
    
    @RequestMapping("/anniversaryNew/app")
    public ModelAndView anniversaryNewApp()
        throws Exception
    {
        ModelAndView mv = new ModelAndView("endAnniversary/newEndAnniversary_app");
        Map<String, Object> info = tempActivityService.findAnniversaryCurrentProduct(AccessTypeEnum.TYPE_OF_APP.getValue());
        mv.addObject("timeType", info.get("timeType").toString());
        mv.addObject("productList", info.get("productList"));
        mv.addObject("saleTopList", info.get("saleTopList"));//热销榜
        mv.addObject("commentTopList", info.get("commentTopList"));//口碑榜
        mv.addObject("repeatTopList", info.get("repeatTopList"));//复购榜
        mv.addObject("popularTopList", info.get("popularTopList"));//人气榜
        mv.addObject("groupList", info.get("groupList"));//品牌团
        mv.addObject("clientType", AccessTypeEnum.TYPE_OF_APP.getValue());
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-24 10:00:00", "yyyy-MM-dd HH:mm:ss");
        mv.addObject("seconds", (end.getTime() - start.getTime()) > 0 ? (end.getTime() - start.getTime()) / 1000 + "" : "0");
        return mv;
    }
    
    @RequestMapping(value = "/anniversaryNew/getProductDate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String anniversaryNewGetProductDate(@RequestParam(value = "timeType", required = false, defaultValue = "0") int timeType,
        @RequestParam(value = "clientType", required = false, defaultValue = "1") int clientType)
    {
        return tempActivityService.getAnniversaryCurrentProductData(timeType, clientType);
    }
    
    @RequestMapping(value = "/anniversaryNew/refreshTime", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String anniversaryNewRefreshTime()
    {
        Date start = new Date();
        Date end = CommonUtil.string2Date("2016-03-24 10:00:00", "yyyy-MM-dd HH:mm:ss");
        long time = (end.getTime() - start.getTime()) / 1000;
        return JSON.toJSONString(time > 0 ? time + "" : "0");
    }
}
