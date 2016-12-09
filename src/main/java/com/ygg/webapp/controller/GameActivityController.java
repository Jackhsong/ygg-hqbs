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
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.GameActivityEntity;
import com.ygg.webapp.entity.GamePrizeEntity;
import com.ygg.webapp.service.CouponDetailService;
import com.ygg.webapp.service.GameActivityService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonUtil;

/**
 * 游戏领取优惠券控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/game")
public class GameActivityController
{
    private static Logger logger = Logger.getLogger(GameActivityController.class);
    
    @Resource
    private GameActivityService gameActivityService;
    
    @Resource
    private CouponDetailService couponService;
    
    /**
     * 游戏礼品领取  页面
     */
    @RequestMapping("/web/{gameId}")
    public ModelAndView game(@PathVariable("gameId") int gameId, HttpServletRequest request, HttpServletResponse response)
    
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            GameActivityEntity game = gameActivityService.findGameActivityById(gameId);
            GamePrizeEntity gamePrize = gameActivityService.findGamePrizeByGameId(gameId);
            if (game == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            if (gamePrize == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            CouponDetailEntity couponDetail = couponService.findCouponDetailByCouponId(gamePrize.getCouponId());
            if (couponDetail == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            
            StringBuffer info = new StringBuffer("<font color='red'>");
            if (couponDetail.getType() == 1)
            {
                info.append("满").append(couponDetail.getThreshold()).append("减").append(couponDetail.getReduce()).append("</font>吃货专属优惠券");
            }
            else if (couponDetail.getType() == 2)
            {
                info.append(couponDetail.getReduce()).append("元").append("</font>吃货专属现金券");
            }
            
            Map<String, Object> wxMap = new HashMap<String, Object>();
            
            String resquestParams = "{'url':'" + game.getGameURL() + "'}";
            CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
            mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
            mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
            mv.addObject("appid", wxMap.get("appid"));
            mv.addObject("timestamp", wxMap.get("timestamp"));
            mv.addObject("nonceStr", wxMap.get("nonceStr"));
            mv.addObject("signature", wxMap.get("signature"));
            mv.addObject("iswx5version", wxMap.get("iswx5version"));
            
            mv.addObject("link", game.getGameURL()); // 分享链接
            //TODO
            mv.addObject("imgUrl", game.getGameLogo()); // 分享图标
            mv.addObject("wxShareDesc", game.getIntroduce()); // 分享内容
            mv.addObject("name", game.getGameName());// 分享标题
            mv.addObject("gameId", gameId);
            mv.addObject("title", info.toString().replaceAll("<font color='red'>", "").replaceAll("</font>", ""));
            mv.addObject("info", info.toString());
            //mv.setViewName(game.getViewURL());
            mv.setViewName("game/game_wap");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /**
     * 玩游戏领取优惠券
     * @param request
     * @param gameId：游戏Id
     * @param mobileNumber：手机号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/receive", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String receive(@RequestParam(value = "gameId", required = true) int gameId, @RequestParam(value = "phone", required = false, defaultValue = "") String mobileNumber)
        throws Exception
    {
        try
        {
            Map<String, Object> result = gameActivityService.receivePrize(mobileNumber, gameId);
            return JSON.toJSONString(result);
        }
        catch (Exception e)
        {
            logger.error("领奖出错", e);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 0);
            map.put("msg", "服务器忙~ 请稍后再试~");
            return JSON.toJSONString(map);
        }
    }
}
