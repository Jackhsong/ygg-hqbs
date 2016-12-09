package com.ygg.webapp.controller;

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
import com.ygg.webapp.entity.SpreadChannelEntity;
import com.ygg.webapp.entity.SpreadChannelPrizeEntity;
import com.ygg.webapp.service.CouponDetailService;
import com.ygg.webapp.service.SpreadChannelService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonUtil;

@Controller
@RequestMapping("spreadChannel")
public class SpreadChannelController
{
    private static Logger logger = Logger.getLogger(SpreadChannelController.class);
    
    @Resource
    private SpreadChannelService spreadChannelService;
    
    @Resource
    private CouponDetailService couponService;
    
    /**
     * 渠道礼品领取  页面
     */
    @RequestMapping("/web/{channelId}")
    public ModelAndView channel(@PathVariable("channelId") int channelId, HttpServletRequest request, HttpServletResponse response)
    
    {
        ModelAndView mv = new ModelAndView();
        try
        {
            SpreadChannelEntity channel = spreadChannelService.findSpreadChannelById(channelId);
            List<SpreadChannelPrizeEntity> prizeList = spreadChannelService.findSpreadChannelPrizeByChannelId(channelId);
            if (channel == null)
            {
                mv.setViewName("error/404");
                return mv;
            }
            if (prizeList.size() == 0)
            {
                mv.setViewName("error/404");
                return mv;
            }
            
            Map<String, Object> wxMap = new HashMap<String, Object>();
            
            String resquestParams = "{'url':'" + channel.getUrl() + "'}";
            CommonUtil.getWeiXinAccessToken(request, response, resquestParams, wxMap);
            mv.addObject(CacheConstant.WEIXIN_ACCESS_TOKEN, wxMap.get(CacheConstant.WEIXIN_ACCESS_TOKEN));
            mv.addObject(CacheConstant.WEIXIN_JSAPI_TICKET, wxMap.get(CacheConstant.WEIXIN_JSAPI_TICKET));
            mv.addObject("appid", wxMap.get("appid"));
            mv.addObject("timestamp", wxMap.get("timestamp"));
            mv.addObject("nonceStr", wxMap.get("nonceStr"));
            mv.addObject("signature", wxMap.get("signature"));
            mv.addObject("iswx5version", wxMap.get("iswx5version"));
            
            mv.addObject("link", channel.getUrl()); // 分享链接
            //TODO
            mv.addObject("imgUrl", channel.getShareImage()); // 分享图标
            mv.addObject("wxShareDesc", channel.getShareContent()); // 分享内容
            mv.addObject("name", channel.getShareTitle());// 分享标题
            mv.addObject("channelId", channel.getId());
            mv.addObject("title", channel.getShareTitle());
            mv.addObject("channelName", channel.getChannelName());
            mv.setViewName("spreadChannel/channel_wap");
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            mv.setViewName("error/404");
        }
        return mv;
    }
    
    /**
     * 渠道领取优惠券
     * @param request
     * @param channelId：渠道Id
     * @param mobileNumber：手机号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/receive", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String receive(@RequestParam(value = "channelId", required = true) int channelId, @RequestParam(value = "phone", required = false, defaultValue = "") String mobileNumber)
        throws Exception
    {
        try
        {
            Map<String, Object> result = spreadChannelService.receivePrize(mobileNumber, channelId);
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
