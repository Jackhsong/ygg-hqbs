/**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.controller.spokesperson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.fans.QqbsLiShiShuJu;
import com.ygg.webapp.service.ActivityService;
import com.ygg.webapp.service.ProductService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.account.HqbsAccountService;
import com.ygg.webapp.service.banner.HqbsBannerService;
import com.ygg.webapp.service.sale.HqbsSaleService;
import com.ygg.webapp.service.saleflag.SaleFlagService;
import com.ygg.webapp.service.spokesperson.SpokesPersonService;
import com.ygg.webapp.service.spokesperson.impl.SpokesPersonServiceImpl;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.SessionUtil;

/**
 * TODO 请在此处添加注释
 * 
 * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
 * @version $Id: SpokesPersonController.java 12586 2016-05-23 07:16:23Z qiuyibo $
 * @since 2.0
 */
@Controller
@RequestMapping("/spokesPerson")
public class SpokesPersonController
{
    
    private static Logger log = Logger.getLogger(SpokesPersonServiceImpl.class);
    
    /**    */
    @Resource(name = "hqbsBannerService")
    private HqbsBannerService hqbsBannerService;
    
    /**    */
    @Resource(name = "productService")
    private ProductService productService;
    
    /**    */
    @Resource(name = "hqbsSaleService")
    private HqbsSaleService HqbsSaleService;
    
    /**    */
    @Resource(name = "saleFlagService")
    private SaleFlagService saleFlagService;
    
    /**    */
    @Resource(name = "activityService")
    private ActivityService activityService;
    
    /**    */
    @Resource(name = "hqbsAccountService")
    private HqbsAccountService hqbsAccountService;
    
    /**    */
    @Resource(name = "spokesPersonService")
    private SpokesPersonService spokesPersonService;
    
    /**    */
    @Resource(name = "shoppingCartService")
    private ShoppingCartService shoppingCartService;
    
    /**
     * 我是代言人页面
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getList")
    public ModelAndView getList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        
        ModelAndView modelAndView = new ModelAndView("spokesperson/list");
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        modelAndView.addObject("image", account.getImage());
        modelAndView.addObject("nickName", account.getNickName());
        modelAndView.addObject("id", account.getAccountId());
        String responseparams = this.spokesPersonService.getListInfo(account);
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseparams);
        // 可提现金额
        modelAndView.addObject("withdrawCash", param.get("withdrawCash"));
        // 历史累计奖励
        modelAndView.addObject("cumulativeReward", param.get("cumulativeReward"));
        // 已提现
        modelAndView.addObject("alreadyCash", param.get("alreadyCash"));
        
        modelAndView.addObject("recommendedPerson", param.get("recommendedPerson"));
        modelAndView.addObject("fans", param.get("fans"));
        // 粉丝订单
        modelAndView.addObject("fansOrderPrice", param.get("fansOrderPrice"));
        modelAndView.addObject("spokesperson", param.get("spokesperson"));
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
    
    /**
     * 二维码页面
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getQrCode")
    public ModelAndView getQrCode(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        String redirectLink = spokesPersonService.getQRCode(SessionUtil.getQqbsAccountFromSession(session), getAppPath(session));
        if (redirectLink == null)
        {
            
            ModelAndView modelAndView = new ModelAndView("spokesperson/qrcode");
            try
            {
                QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
                modelAndView.addObject("image", account.getImage());
                modelAndView.addObject("spokesperson", "0");
                modelAndView.addObject("status", "1");
            }
            catch (Exception e)
            {
                log.error("获取二维码失败", e);
                modelAndView.addObject("status", "2");
            }
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("spokesperson/showQrCode");
        modelAndView.addObject("img", redirectLink);
        return modelAndView;
        
    }
    
    private String getAppPath(HttpSession session)
    {
        return session.getServletContext().getRealPath("/");
    }
    
    /**
     * 我的奖励页面
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getReward")
    public ModelAndView getReward(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/reward");
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        String responseparams = spokesPersonService.getRewardInfo(account);
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseparams);
        // 可提现金额
        modelAndView.addObject("withdrawCash", param.get("withdrawCash"));
        // 是否是代言人
        modelAndView.addObject("spokesperson", param.get("spokesperson"));
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
    
    /**
     * 粉丝订单列表
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansOrderList")
    public ModelAndView getFansOrderList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/fansOrder");
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        String s = spokesPersonService.getFansOrderList(account);
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(s);
        // 总奖励
        modelAndView.addObject("totalReward", param.get("totalReward"));
        // 粉丝销量
        modelAndView.addObject("fansOrderPrice", param.get("fansOrderPrice"));
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
    
    /**
     * 根据昵称、粉丝ID、粉丝等级获取粉丝订单信息
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansOrderListByCondition", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getFansOrderListByCondition(@RequestParam(value = "page", required = false, defaultValue = "1") int page, HttpServletRequest request,
        HttpServletResponse response, HttpSession session)
    {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        try
        {
            // 粉丝订单列表
            resultMap.put("fansOrderList", spokesPersonService.getFansOrderByPage(account, page));
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        catch (Exception e)
        {
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            e.printStackTrace();
        }
        return resultMap;
    }
    
    /**
     * 根据条件查询粉丝订单列表(粉丝订单编号和粉丝ID)
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansOrderByNumOrId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getFansOrderByNum(@RequestParam(value = "num", required = false, defaultValue = "") String num, HttpServletRequest request, HttpServletResponse response,
        HttpSession session)
    {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        try
        {
            // 粉丝订单列表
            resultMap.put("fansOrderList", spokesPersonService.getFansOrderByNumOrId(account, num));
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        catch (Exception e)
        {
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        return resultMap;
    }
    
    /**
     * 粉丝订单列表
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansOrderDetail")
    public ModelAndView getFansOrderDetail(@RequestParam(value = "id", required = true) int id, HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/orderDetail");
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        modelAndView.addObject("orderDetail", spokesPersonService.getFansOrder(account, id));
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
    
    /**
     * 粉丝关系列表临时使用
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFans")
    public ModelAndView getFans()
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/fans");
        spokesPersonService.updateFans();
        return modelAndView;
    }
    
    /**
     * 粉丝订单列表临时使用 (输入订单ID即可，处理)
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansListlinshi")
    public ModelAndView getFansListlinshi()
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/fansListlinshi");
        spokesPersonService.updateFansListlinshi();
        return modelAndView;
    }
    
    /**
     * 粉丝列表
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansList")
    public ModelAndView getFansList(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/fansList");
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        /** 粉丝等级:1 直接粉丝 2 间接粉丝 3 间接粉丝 */
        String s = spokesPersonService.getFansCountByLevel(account);
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(s);
        modelAndView.addObject("count", param.get("count"));
        modelAndView.addObject("oneCount", param.get("oneCount"));
        modelAndView.addObject("twoCount", param.get("twoCount"));
        modelAndView.addObject("thCount", param.get("thCount"));
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
    
    /**
     * 点击我的粉丝变更
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansLists")
    public ModelAndView getFansLists(HttpServletRequest request, HttpServletResponse response, HttpSession session)
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/fansList");
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        String s = spokesPersonService.getFansCountByLevels(account);
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(s);
        modelAndView.addObject("count", param.get("count"));
        modelAndView.addObject("oneCount", param.get("oneCount"));
        modelAndView.addObject("twoThCount", param.get("twoThCount"));
        modelAndView.addObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return modelAndView;
    }
    
    /**
     * 根据昵称、粉丝ID、粉丝等级获取粉丝信息
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansListByCondition", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getFansListByCondition(@RequestParam(value = "nameOrId", required = false, defaultValue = "") String nameOrId,
        @RequestParam(value = "level", required = false, defaultValue = "0") int level, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        try
        {
            // 粉丝订单列表
            resultMap.put("fansList", spokesPersonService.getFansList(account, nameOrId, level, page));
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        catch (Exception e)
        {
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            e.printStackTrace();
        }
        return resultMap;
    }
    
    /**
     * 根据昵称、粉丝ID、粉丝等级获取粉丝信息
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getFansListByConditions", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getFansListByConditions(@RequestParam(value = "nameOrId", required = false, defaultValue = "") String nameOrId,
        @RequestParam(value = "level", required = false, defaultValue = "") String level, @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        QqbsAccountEntity account = SessionUtil.getQqbsAccountFromSession(session);
        try
        {
            // 粉丝订单列表
            resultMap.put("fansList", spokesPersonService.getFansLists(account, nameOrId, level, page));
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        catch (Exception e)
        {
            resultMap.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            e.printStackTrace();
        }
        return resultMap;
    }
    
    /**
     * 粉丝订单列表临时使用
     * 
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getlishishuju")
    public ModelAndView getlishishuju()
        throws Exception
    {
        ModelAndView modelAndView = new ModelAndView("spokesperson/fansListlinshi");
        log.error("------------------开始获取数据------------------");
        List<QqbsLiShiShuJu> list = spokesPersonService.getlishishuju();
        if (list != null && list.size() > 0)
        {
            log.error("------------------获取数据条数=" + list.size());
            for (QqbsLiShiShuJu ls : list)
            {
                try
                {
                    // log.error("--------正在处理 accountId="+ls.getAccountId()+"----------");
                    
                    spokesPersonService.updatelishi(ls);
                    
                    // log.error("--------处理成功 accountId="+ls.getAccountId()+"----------");
                }
                catch (Exception e)
                {
                    log.error("--------处理失败accountId=" + ls.getAccountId() + "----------", e);
                    e.printStackTrace();
                }
            }
        }
        log.error("------------------数据处理完成-----------------");
        return modelAndView;
    }
    
}
