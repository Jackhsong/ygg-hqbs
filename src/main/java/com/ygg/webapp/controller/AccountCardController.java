package com.ygg.webapp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.service.AccountCardService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.OrderProductRefundView;

@Controller("accountCardController")
@RequestMapping("/ac")
public class AccountCardController
{
    
    @Resource(name = "accountCardService")
    private AccountCardService accountCardService;
    
    /**
     * 跳到选择账号的界面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/toSelectAccountCard")
    public ModelAndView toSelectAccountCard(HttpServletRequest request, @ModelAttribute OrderProductRefundView oprf,
        @RequestParam(value = "ordSource", required = false, defaultValue = "1") String source,
        @RequestParam(value = "canReturnPay", required = false, defaultValue = "0") int canReturnPay, HttpSession session)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        
        List<AccountCartEntity> acesList = getAcesList(av.getId());
        mv.addObject("acesList", acesList);
        
        String isApp = SessionUtil.getCurrentRequestResource(session, av.getId() + "");
        if (isApp != null)
        {
            mv.addObject("isApp", "1");
        }
        if (source.equals("2")) // ==2 是从提交申请中来， ==1 是从管理中来
        {
            SessionUtil.addOrderRefundProduct(request.getSession(), oprf);
        }
        
        mv.setViewName("orderrefund/accountsSelect");
        
        int selectedAccountCardId = 0;
        if (source.equals("1")) // ==1 是从管理中来 从session中取
            oprf = SessionUtil.getOrderRefundProduct(request.getSession());
            
        if ("2".equals(source))// 2 是从提交申请中来
        {
            request.getSession().setAttribute("refund_orderProductId" + oprf.getOrderProductId(), canReturnPay);
            selectedAccountCardId = oprf.getAccountCardId();
        }
        else
        {
            canReturnPay = Integer.valueOf(request.getSession().getAttribute("refund_orderProductId" + oprf.getOrderProductId()) == null ? "0"
                : request.getSession().getAttribute("refund_orderProductId" + oprf.getOrderProductId()) + "");
        }
        mv.addObject("canReturnPay", canReturnPay + "");
        mv.addObject("selectedAccountCardId", selectedAccountCardId); //
        mv.addObject("orderId", oprf.getOrderId());
        mv.addObject("orderProductId", oprf.getOrderProductId());
        mv.addObject("productId", oprf.getProductId());
        
        return mv;
    }
    
    /**
     * 选择AccountCard账号,返回到申请页面
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectAccountCard/{accountCardId}")
    public ModelAndView selectAccountCard(HttpServletRequest request, @PathVariable("accountCardId") String accountCardId)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        OrderProductRefundView oprf = SessionUtil.getOrderRefundProduct(request.getSession()); // 保证一定要有值
        
        if (oprf != null)
        {
            // 重新设置accountCardId 重新加入session
            oprf.setAccountCardId(Integer.parseInt(accountCardId));
            if ("0".equals(accountCardId))
            {
                oprf.setAccountCardVal("原路返回");
            }
            else
            {
                oprf.setAccountCardVal(accountCardService.getAccountCardValueById(Integer.parseInt(accountCardId)));
            }
            
            // SessionUtil.removeOrderRefundProduct(request.getSession());
            SessionUtil.addOrderRefundProduct(request.getSession(), oprf);
            
            mv.addObject("orderId", oprf.getOrderId() + "");
            mv.addObject("orderProductId", oprf.getOrderProductId() + "");
            mv.addObject("productId", oprf.getProductId() + "");
        }
        
        mv.setViewName("redirect:/orderrefund/getSubmitApplicationInfo");
        return mv;
    }
    
    /**
     * 得到银行卡号管理
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/getAllAccountCard")
    public ModelAndView getAllAccountCard(HttpServletRequest request, HttpSession session)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        String isApp = SessionUtil.getCurrentRequestResource(session, av.getId() + "");
        if (isApp != null)
        {
            mv.addObject("isApp", "1");
        }
        String isaddBank = "1"; // 1 增加 0 不增加
        String isaddAliPay = "1";
        List<AccountCartEntity> acesList = getAcesList(av.getId());
        if (acesList != null && acesList.size() > 0)
        {
            for (AccountCartEntity ace : acesList)
            {
                if (ace.getType() == (byte)CommonEnum.ACCOUNT_CARD_TYPE.TYPE_BANK_TYPE.getValue())
                {
                    isaddBank = "0";
                }
                if (ace.getType() == (byte)CommonEnum.ACCOUNT_CARD_TYPE.TYPE_ALI_TYPE.getValue())
                {
                    isaddAliPay = "0";
                }
            }
        }
        
        mv.addObject("acesList", acesList);
        mv.addObject("isaddBank", isaddBank);
        mv.addObject("isaddAliPay", isaddAliPay);
        mv.setViewName("orderrefund/accountsManage");
        
        return mv;
    }
    
    private List<AccountCartEntity> getAcesList(int accountId)
    {
        JsonParser parser = new JsonParser();
        String responseParams = accountCardService.getAllAccountCard("{'accountId':'" + accountId + "'}");
        JsonObject result = (JsonObject)parser.parse(responseParams);
        Object acesJson = result.get("aces");
        List<AccountCartEntity> acesList = null;
        if (acesJson != null && !acesJson.toString().equals(""))
        {
            acesList = JSONUtils.fromJson(acesJson.toString(), new TypeToken<List<AccountCartEntity>>()
            {
            });
        }
        
        return acesList;
    }
    
    /**
     * 跳到银行卡的新增页面
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toaddBank")
    public String toAddBank(HttpServletRequest request)
        throws Exception
    {
        return "orderrefund/accountsBank";
    }
    
    /**
     * 跳到银行卡的新增页面
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/toaddAli")
    public String toAddAli(HttpServletRequest request)
        throws Exception
    {
        return "orderrefund/accountsAlipay";
    }
    
    /**
     * 修改
     * 
     * @param request
     * @param type
     * @param acId
     * @return
     * @throws Exception
     */
    @RequestMapping("/tomodify")
    public ModelAndView tomodify(HttpServletRequest request, @RequestParam(value = "type", required = false, defaultValue = "1") String type, // 1：银行，2：支付宝',
        @RequestParam(value = "acId", required = false, defaultValue = "0") String acId, HttpSession session)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        if (type.equals("1"))
            mv.setViewName("orderrefund/accountsBank");
        else
            mv.setViewName("orderrefund/accountsAlipay");
            
        JsonParser parser = new JsonParser();
        // 查出记录显示在页面上
        String requestParams = "{'acId':'" + acId + "'}";
        String responseParams = this.accountCardService.getAccountCardById(requestParams);
        JsonObject result = (JsonObject)parser.parse(responseParams);
        
        Object aceJson = result.get("ace");
        AccountCartEntity aceObj = null;
        if (aceJson != null && !aceJson.toString().equals(""))
        {
            aceObj = JSONUtils.fromJson(aceJson.toString(), new TypeToken<AccountCartEntity>()
            {
            });
            
            String isApp = SessionUtil.getCurrentRequestResource(session, aceObj.getAccountId() + "");
            if (isApp != null)
            {
                mv.addObject("isApp", "1");
            }
        }
        
        mv.addObject("ace", aceObj);
        return mv;
    }
    
    /**
     * 保存银行卡号
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/editAccountCard")
    public ModelAndView editAccountCard(HttpServletRequest request, @RequestParam(value = "type", required = false, defaultValue = "1") String type, // 1：银行，2：支付宝',
        @RequestParam(value = "bankType", required = false, defaultValue = "1") String bankType,
        @RequestParam(value = "cardNumber", required = false, defaultValue = "") String cardNumber,
        @RequestParam(value = "cardName", required = false, defaultValue = "") String cardName, @RequestParam(value = "id", required = false, defaultValue = "0") String acId)
            throws Exception
    {
        ModelAndView mv = new ModelAndView();
        JsonParser parser = new JsonParser();
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        
        String requestParams = "{'accountId':'" + av.getId() + "','bankType':'" + bankType + "','type':'" + type + "','cardNumber':'" + cardNumber + "','cardName':'" + cardName
            + "','accountCardId':'" + acId + "'}";
        String responseParams = accountCardService.editAccountCard(requestParams);
        JsonObject param = (JsonObject)parser.parse(responseParams);
        mv.addObject("status", param.get("status").getAsString());
        String errorCode = param.get("errorCode") == null ? null : param.get("errorCode").getAsString();
        if (errorCode != null)
        {
            mv.addObject("errorCode", errorCode);
            if (type.equals("1"))
                mv.setViewName("orderrefund/accountsBank"); // 失败返回原页面
            else
                mv.setViewName("orderrefund/accountsAlipay"); // 失败返回原页面
            AccountCartEntity aceObj = new AccountCartEntity();
            aceObj.setBankType(Byte.parseByte(bankType));
            aceObj.setCardNumber(cardNumber);
            aceObj.setCardName(cardName);
            mv.addObject("ace", aceObj);
            return mv;
        }
        
        mv.setViewName("redirect:/ac/getAllAccountCard"); // 成功页面
        return mv;
    }
    
}
