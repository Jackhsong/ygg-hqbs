package com.ygg.webapp.controller;

import java.util.HashMap;
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
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.service.LotteryActivityService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonUtil;

@Controller
@RequestMapping("/activity")
public class LotteryActivityController
{
    Logger log = Logger.getLogger(SpreadController.class);
    
    @Resource(name = "accountService")
    private AccountService accountService;
    
    @Resource
    private LotteryActivityService lotteryActivityService;
    
    /**
     * 平常节日抽奖  页面
     */
    @RequestMapping("/lottery/web/{lotteryId}")
    public ModelAndView gegeinvite(@PathVariable("lotteryId") int lotteryId,//抽奖活动ID
        HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (!lotteryActivityService.existsLotteryActivity(lotteryId))
        {
            mv.setViewName("error/404");
            return mv;
        }
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/lottery/web/" + lotteryId + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/activity/lottery/web/" + lotteryId); // 分享链接
        //TODO
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
        mv.addObject("wxShareDesc", "给你2次成为土壕的机会,快来试试~"); // 分享内容
        mv.addObject("name", "给你2次成为土壕的机会,快来试试~");// 分享标题
        
        mv.addObject("lotteryId", lotteryId);
        mv.setViewName("lottery/activity_wap");
        return mv;
    }
    
    /**
     * 执行抽奖
     */
    @RequestMapping(value = "/lottery/draw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String draw(HttpServletRequest request,//
        @RequestParam(value = "lotteryId", required = true) int lotteryId,//抽奖活动ID
        @RequestParam(value = "phone", required = false, defaultValue = "") String mobileNumber,//
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId)
        throws Exception
    {
        try
        {
            Map<String, Object> result = lotteryActivityService.draw(accountId, mobileNumber, lotteryId);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error("抽奖出错", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 0);
            map.put("msg", "服务器忙~ 请稍后再试~");
            return JSON.toJSONString(map);
        }
    }
    
    /**
     * app 平常节日抽奖  页面
     * @param lotteryId
     * @param accountId
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/lottery/appOpen")
    public ModelAndView gegeinviteApp(@RequestParam(value = "lotteryId", required = true) int lotteryId,//
        @RequestParam(value = "accountId", required = false, defaultValue = "0") int accountId,// 
        @RequestParam(value = "sign", required = false, defaultValue = "") String sign,//
        HttpServletRequest request, HttpServletResponse response//
    )
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (!lotteryActivityService.existsLotteryActivity(lotteryId))
        {
            mv.setViewName("error/404");
            return mv;
        }
        try
        {
            //签名验证
            String verify_sign = CommonUtil.strToMD5(accountId + CommonUtil.SIGN_KEY);
            //            System.out.println(verify_sign);
            if (!verify_sign.equals(sign))
            {
                mv.setViewName("error/404");
                return mv;
            }
        }
        catch (Exception e)
        {
            mv.setViewName("error/404");
            return mv;
        }
        //accountId == 0，说明app未登录
        mv.addObject("login", accountId > 0);
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/lottery/web/" + lotteryId + "'}";//分享出去的页面不要app用户信息
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/activity/lottery/web/" + lotteryId); // 分享链接
        //TODO
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
        mv.addObject("wxShareDesc", "给你2次成为土壕的机会,快来试试~"); // 分享内容
        mv.addObject("name", "给你2次成为土壕的机会,快来试试~");// 分享标题
        mv.addObject("lotteryId", lotteryId);
        mv.addObject("accountId", accountId);
        mv.setViewName("lottery/activity_app");
        return mv;
    }
    
    /**
     * 获取用户剩余抽奖机会
     * @param request
     * @param accountId
     * @param lotteryId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/lottery/getChance", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String getChance(HttpServletRequest request,//
        @RequestParam(value = "accountId", required = true) int accountId,//用户ID
        @RequestParam(value = "lotteryId", required = true) int lotteryId//抽奖活动ID
    )
        throws Exception
    {
        try
        {
            int nums = 0;
            if (accountId > 0)
            {
                nums = lotteryActivityService.chance(lotteryId, accountId);
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("status", "success");
            result.put("nums", nums);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error("获取剩余抽奖次数失败", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", "error");
            map.put("msg", "出错啦~");
            map.put("nums", 0);
            return JSON.toJSONString(map);
        }
    }
    
    /**
     * 分享抽奖活动 增加次数
     * @param request
     * @param accountId
     * @param lotteryId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/lottery/appShare", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String shareActivity(HttpServletRequest request,//
        @RequestParam(value = "accountId", required = true) int accountId,//用户ID
        @RequestParam(value = "lotteryId", required = true) int lotteryId//抽奖活动ID
    )
        throws Exception
    {
        try
        {
            Map<String, Object> result = lotteryActivityService.shareActivity(accountId, lotteryId);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error("分享抽奖活动 增加次数失败！", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 0);
            map.put("msg", "出错啦~");
            return JSON.toJSONString(map);
        }
    }
    
    /**
     * 游戏礼品领取  页面
     */
    @RequestMapping("/lottery/gameGift/{lotteryId}")
    public ModelAndView gameGift(@PathVariable("lotteryId") int lotteryId, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (!lotteryActivityService.existsLotteryActivity(lotteryId))
        {
            mv.setViewName("error/404");
            return mv;
        }
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/lottery/gameGift/" + lotteryId + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/activity/lottery/gameGift/" + lotteryId); // 分享链接
        //TODO
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
        mv.addObject("wxShareDesc", "左岸城堡12元游戏专属现金券~"); // 分享内容
        mv.addObject("name", "左岸城堡12元游戏专属现金券~");// 分享标题
        mv.addObject("lotteryId", lotteryId);
        mv.setViewName("lottery/gameGift");
        return mv;
    }
    
    /**
     * app 调用  宴请好友分享页面
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/gift/appOpen")
    public ModelAndView giftShare(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/gift/appOpen'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/activity/gift/appOpen"); // 分享链接
        //TODO
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
        mv.addObject("wxShareDesc", "送你777元大礼包，这一刻请叫我土壕"); // 分享内容
        mv.addObject("name", "送你777元大礼包，这一刻请叫我土壕");// 分享标题
        mv.setViewName("lottery/shareGift");
        return mv;
    }
    
    /**
     * /gift/share  分享后    调用这个礼包分享  action
     * @param request
     * @param accountId
     * @param giftActivityId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gift/appShare", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String giftAppShare(HttpServletRequest request,//
        @RequestParam(value = "accountId", required = true) int accountId,//用户ID
        @RequestParam(value = "giftId", required = true) int giftActivityId//礼包活动ID
    )
        throws Exception
    {
        try
        {
            Map<String, Object> result = lotteryActivityService.giftAppShare(accountId, giftActivityId);
            return JSON.toJSONString(result);
            
        }
        catch (Exception e)
        {
            log.error("礼包分享，发送礼物失败!", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 0);
            map.put("msg", "出错啦~");
            return JSON.toJSONString(map);
        }
    }
    
    /**
     *    gift/share  分享后    wap看到的 页面
     */
    @RequestMapping("/gift/web/{giftId}")
    public ModelAndView giftWeb(@PathVariable("giftId") int giftId,//分享活动ID
        HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (!lotteryActivityService.existsGiftActivity(giftId))
        {
            mv.setViewName("error/404");
            return mv;
        }
        Map<String, Object> wxMap = new HashMap<String, Object>();
        
        String resquestParams = "{'url':'" + "http://m.gegejia.com/ygg/activity/gift/web/" + giftId + "'}";
        CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
        mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
        mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
        mv.addObject("appid", wxMap.get("appid"));
        mv.addObject("timestamp", wxMap.get("timestamp"));
        mv.addObject("nonceStr", wxMap.get("nonceStr"));
        mv.addObject("signature", wxMap.get("signature"));
        mv.addObject("iswx5version", wxMap.get("iswx5version"));
        
        mv.addObject("link", "http://m.gegejia.com/ygg/activity/gift/web/" + giftId); // 分享链接
        //TODO
        mv.addObject("imgUrl", "http://yangege.b0.upaiyun.com/product/c2f8bd67bd81.png"); // 分享图标
        mv.addObject("wxShareDesc", "送你777元大礼包，这一刻请叫我土壕"); // 分享内容
        mv.addObject("name", "送你777元大礼包，这一刻请叫我土壕");// 分享标题
        
        mv.addObject("giftId", giftId);
        mv.setViewName("lottery/gift_wap");
        return mv;
    }
    
    /**
     * 领取礼包
     */
    @RequestMapping(value = "/gift/draw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String giftDraw(HttpServletRequest request,//
        @RequestParam(value = "giftId", required = true) int giftId,//奖品活动ID
        @RequestParam(value = "phone", required = false, defaultValue = "") String mobileNumber)
        throws Exception
    {
        try
        {
            Map<String, Object> result = lotteryActivityService.giftDrew(mobileNumber, giftId);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            log.error("抽奖出错", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 0);
            map.put("msg", "服务器忙~ 请稍后再试~");
            return JSON.toJSONString(map);
        }
    }
}
