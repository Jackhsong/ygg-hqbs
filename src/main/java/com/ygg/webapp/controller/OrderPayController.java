package com.ygg.webapp.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.service.OrderPayService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;

/**
 * 定单支付
 * 
 * @author lihc
 *
 */
@Controller("orderPayController")
@RequestMapping("/op")
public class OrderPayController
{
    
    @Resource(name = "orderPayService")
    private OrderPayService orderPayService;
    
    // @Resource(name = "weiXinCacheService")
    @Resource(name = "memService")
    private CacheServiceIF weiXinCacheService;
    
    /**
     * 手机微信H5支付接口
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/topay")
    @ResponseBody
    public String topay(@ModelAttribute Map<String, String> map, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        JsonObject result = new JsonObject();
        
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            result.addProperty("errorCode", CommonEnum.WEIXIN_ORDER_PAY_STATUS.USER_NOT_LOGIN.getValue()); // 跳到login页面
            return result.toString();
        }
        String paytype = map.get("paytype");
        if (paytype != null && paytype.equals("3")) // 微信支付
        {
            // 1.判断是否是微信浏览器，而已要是5.0以上版本才能支付，否则给出提示
            boolean flag = CommonUtil.isWeiXinVersionMoreThan5(request.getHeader("user_agent"));
            if (!flag)
            {
                result.addProperty("errorCode", CommonEnum.WEIXIN_ORDER_PAY_STATUS.IS_NOT_WEIXIN_5.getValue()); // 请使用微信5.0版本以上
                return result.toString();
            }
        }
        else if (paytype != null && paytype.equals("2")) // 支付宝支付
        {
            
        }
        else if (paytype != null && paytype.equals("1")) // 银联支付
        {
            
        }
        
        return result.toString();
    }
    
}
