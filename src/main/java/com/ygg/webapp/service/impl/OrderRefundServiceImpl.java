package com.ygg.webapp.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.code.RefundPayTypeEnum;
import com.ygg.webapp.dao.AccountCardDao;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.CityDao;
import com.ygg.webapp.dao.DistrictDao;
import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.OrderProductRefundDao;
import com.ygg.webapp.dao.OrderProductRefundLogisticDao;
import com.ygg.webapp.dao.ProvinceDao;
import com.ygg.webapp.dao.SellerDao;
import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.OrderEntity;
import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderProductRefundEntity;
import com.ygg.webapp.entity.OrderProductRefundLogisticsEntity;
import com.ygg.webapp.entity.SellerExpandEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.OrderRefundService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.OrderFrefundProductView;
import com.ygg.webapp.view.OrderFrefundView;
import com.ygg.webapp.view.OrderProductRefundView;
import com.ygg.webapp.view.OrderReturnProductView;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderRefundService")
public class OrderRefundServiceImpl implements OrderRefundService
{
    Logger log = Logger.getLogger(OrderRefundServiceImpl.class);
    
    @Resource(name = "accountCardDao")
    private AccountCardDao accountCardDao;
    
    @Resource(name = "accountDao")
    private AccountDao accountDao;
    
    @Resource(name = "orderProductRefundDao")
    private OrderProductRefundDao orderProductRefundDao;
    
    @Resource(name = "orderProductRefundLogisticDao")
    private OrderProductRefundLogisticDao orderProductRefundLogisticDao;
    
    @Resource(name = "orderDao")
    private OrderDao orderDao;
    
    @Resource(name = "sellerDao")
    private SellerDao sellerDao;
    
    @Resource(name = "provinceDao")
    private ProvinceDao pdi = null;
    
    @Resource(name = "cityDao")
    private CityDao cdi = null;
    
    @Resource(name = "districtDao")
    private DistrictDao ddi = null;
    
    @Resource
    private RestTemplate restTemplate;
    
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    
    @Override
    public String getOrderInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        
        int accountId = Integer.parseInt(params.get("accountId").getAsString()); // 从session中取出
        
        String sign = params.get("sign") == null ? "" : params.get("sign").getAsString().trim();
        String isApp = params.get("isApp") == null ? "0" : params.get("isApp").getAsString().trim();
        
        if (CommonEnum.COMMON_IS.YES.getValue().equals(isApp))
        {
            if (sign.equals(""))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
                return result.toString();
            }
            AccountEntity ac = this.accountDao.findAccountById(accountId);
            if (ac == null || ac.getId() <= 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.ID_NOT_EXISTS.getValue()); // 用户不存在
                return result.toString();
            }
            
            String verify_sign = "";
            try
            {
                verify_sign = CommonUtil.strToMD5(ac.getId() + CommonUtil.SIGN_KEY);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            if (!verify_sign.equals(sign))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.SING_KEY_ERROR.getValue()); //
                return result.toString();
            }
            
            AccountView av = new AccountView();
            av.setId(ac.getId());
            av.setName(ac.getName());
            av.setNickname(ac.getNickname());
            av.setPwd(ac.getPwd());
            av.setType(ac.getType());
            
            SessionUtil.addUserToSession(request.getSession(), av);
            writeUserCookie(request, response, av.getPwd(), 60 * 60 * 24 * 30);
        }
        if (accountDao.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonConstant.ID_NOT_EXIST + "");
            return result.toString();
        }
        byte[] types =
            new byte[] {(byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue(), (byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue(),
                (byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue()};
        
        List<OrderEntity> orderEntities = orderDao.findOrderRefund(accountId, types, 1);
        List<OrderFrefundView> ovs = new ArrayList<OrderFrefundView>();
        for (OrderEntity oe : orderEntities)
        {
            // 未付款并且过期的订单不显示
            if (oe.getStatus() == CommonEnum.ORDER_STATUS.NOT_PAY.getValue())
            {
                String validTimeStr = orderDao.findValidTimeByOid(oe.getId());
                if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
                {
                    Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                    if (new Date().after(validTime))// 订单 过期
                    {
                        continue; // 过期先不显示出来
                    }
                }
                else
                {
                    continue; // 过期先不显示出来
                }
            }
            
            // 会员订单不支持退款
            if (oe.getIsMemberOrder() == Integer.valueOf(CommonEnum.COMMON_IS.YES.getValue()))
            {
                continue;
            }
            
            putOrderFrefundView(ovs, oe, 0, 1); // 此处的 0 自助退款　中来　暂不用
        }
        
        Collections.sort(ovs);
        result.add("orderList", parser.parse(JSONUtils.toJson(ovs, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String getSubmitApplicationInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        
        String productId = params.get("productId") == null ? "" : params.get("productId").getAsString();
        String orderProductId = params.get("orderProductId") == null ? "" : params.get("orderProductId").getAsString();
        String orderId = params.get("orderId") == null ? "" : params.get("orderId").getAsString();
        String accountId = params.get("accountId") == null ? "" : params.get("accountId").getAsString();
        int orderProductRefundId = params.get("orderProductRefundId") == null ? 0 : Integer.parseInt(params.get("orderProductRefundId").getAsString());
        
        String sign = params.get("sign") == null ? "" : params.get("sign").getAsString().trim();
        String isApp = params.get("isApp") == null ? "0" : params.get("isApp").getAsString().trim();
        
        if (CommonEnum.COMMON_IS.YES.getValue().equals(isApp))
        {
            if (sign.equals(""))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
                return result.toString();
            }
            AccountEntity ac = this.accountDao.findAccountById(Integer.parseInt(accountId));
            if (ac == null || ac.getId() <= 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.ID_NOT_EXISTS.getValue()); // 用户不存在
                return result.toString();
            }
            
            String verify_sign = "";
            try
            {
                verify_sign = CommonUtil.strToMD5(ac.getId() + CommonUtil.SIGN_KEY);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            if (!verify_sign.equals(sign))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.SING_KEY_ERROR.getValue()); //
                return result.toString();
            }
            AccountView av = new AccountView();
            av.setId(ac.getId());
            av.setName(ac.getName());
            av.setNickname(ac.getNickname());
            av.setPwd(ac.getPwd());
            av.setType(ac.getType());
            
            SessionUtil.addUserToSession(request.getSession(), av);
            writeUserCookie(request, response, av.getPwd(), 60 * 60 * 24 * 30);
        }
        
        if (productId.equals("") || orderProductId.equals("") || orderId.equals(""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.SUBMIT_APPLICATION_INFO_STATUS.MISSING_PARAMS.getValue());
            return result.toString();
        }
        if (!CommonUtil.isNumeric(productId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.SUBMIT_APPLICATION_INFO_STATUS.PRODUCT_ID_ERROR.getValue());
            return result.toString();
        }
        if (!CommonUtil.isNumeric(orderProductId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.SUBMIT_APPLICATION_INFO_STATUS.ORDER_PRODUCT_ID_ERROR.getValue());
            return result.toString();
        }
        if (!CommonUtil.isNumeric(orderId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.SUBMIT_APPLICATION_INFO_STATUS.ORDER_ID_ERROR.getValue());
            return result.toString();
        }
        
        OrderProductEntity ope = this.orderProductRefundDao.queryOrderProductById(Integer.parseInt(orderProductId));
        if (ope == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.SUBMIT_APPLICATION_INFO_STATUS.ORDER_PRODUCT_ID_ERROR.getValue());
            return result.toString();
        }
        if (ope.getProductId() != Integer.parseInt(productId) || ope.getOrderId() != Integer.parseInt(orderId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.SUBMIT_APPLICATION_INFO_STATUS.PRODUCT_ORDER_NOT_MATCH.getValue());
            return result.toString();
        }
        
        OrderReturnProductView productList = new OrderReturnProductView();
        productList.productId = ope.getProductId() + "";
        // productList.
        
        OrderEntity oe = this.orderDao.findOrderById(ope.getOrderId());
        productList.orderNumber = oe.getNumber();
        productList.status = oe.getStatus() + "";
        if (oe.getPayTime() != null && !oe.getPayTime().equals("0000-00-00 00:00:00"))
        {
            productList.operateTime = oe.getPayTime();
        }
        else
        {
            productList.operateTime = oe.getCreateTime();
        }
        
//        productList.totalPrice = decimalFormat.format(oe.getTotalPrice() - oe.getFreightMoney()); // 单个商品购买数量的总价 -邮费
        productList.productId = ope.getProductId() + "";
        productList.image = ope.getSmallImage();
        productList.shortName = ope.getShortName();
        productList.count = ope.getProductCount() + "";
        productList.salesPrice = decimalFormat.format(ope.getSalesPrice());
        
        productList.totalPrice = decimalFormat.format(ope.getSalesPrice()*ope.getProductCount());
        result.add("productList", parser.parse(JSONUtils.toJson(productList, false)));
        result.addProperty("orderId", orderId);
        result.addProperty("canReturnPay", oe.getPayChannel() > 1 ? 1 : 0);
//        result.addProperty("canReturnPay", oe.getPayChannel());
        result.addProperty("orderProductId", orderProductId);
        
        AccountCartEntity selectedAce = null;
        // 选择一个退款账号，对于修改,已有则用accountCardId ; 没有的话,有两个选支付宝，如果只有一个就先一个，没有就为空
        if (orderProductRefundId > 0)
        {
            selectedAce = this.accountCardDao.queryAccountCardByOrderRefundProductId(orderProductRefundId);
        }
        else
        {
//            List<AccountCartEntity> aces = this.accountCardDao.queryAccountCard(Integer.parseInt(accountId));
//            if (aces.size() == 2)
//            {
//                for (AccountCartEntity ace : aces)
//                {
//                    if (ace.getType() == (byte)CommonEnum.ACCOUNT_CARD_TYPE.TYPE_ALI_TYPE.getValue())
//                    {
//                        selectedAce = ace;
//                        break;
//                    }
//                }
//            }
//            else if (aces.size() == 1)
//            {
//                selectedAce = aces.get(0);
//            }
        }
        
        if (selectedAce != null)
            result.add("selectedAce", parser.parse(JSONUtils.toJson(selectedAce, false)));
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String submitApplication(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        
        Object orderRefundProductInfo = params.get("orderRefundProductInfo");
        OrderProductRefundEntity oprfe = new OrderProductRefundEntity();
        OrderProductRefundView oprfv = null;
        if (orderRefundProductInfo != null)
        {
            oprfv = JSONUtils.fromJson(orderRefundProductInfo.toString(), new TypeToken<OrderProductRefundView>()
            {
            });
        }
        else
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.MISSING_PARAMS.getValue());
            return result.toString();
        }
        
        org.springframework.beans.BeanUtils.copyProperties(oprfv, oprfe);
        
        log.info("退款-提交退款申请Service:"+"AccountId:"+oprfe.getAccountId()+"~"+"OrderId:"+oprfe.getOrderId()+"~"+"OrderProductId:"+oprfe.getOrderProductId());
        
        int accountCardId = oprfe.getAccountCardId();
        if (accountCardId < 0 || !CommonUtil.isNumeric(accountCardId + ""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.ACCOUNT_CARD_ERROR.getValue());
            return result.toString();
        }
        if (accountCardId > 0 && !this.accountCardDao.isExistAccountCard(accountCardId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.ACCOUNT_CARD_ERROR.getValue());
            return result.toString();
        }
        
        if (!CommonUtil.isNumeric(oprfe.getOrderProductId() + ""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.ACCOUNTCARDID_ERROR.getValue());
            return result.toString();
        }
        
        if (!CommonUtil.isNumeric(oprfe.getOrderId() + ""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.ORDERID_ERROR.getValue());
            return result.toString();
        }
        
        OrderProductEntity ope = orderProductRefundDao.queryOrderProductById(oprfe.getOrderProductId());
        if (ope == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.PRODUCTID_ERROR.getValue());
            return result.toString();
        }
        if (!CommonUtil.isNumeric(oprfe.getCount() + "") || oprfe.getCount() > ope.getProductCount())
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.REFUND_COUNT_ERROR.getValue());
            return result.toString();
        }
        if (!CommonUtil.isNumeric(oprfe.getApplyMoney() + ""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.REFUND_MONEY_IS_NOT_NUMERIC.getValue());
            return result.toString();
        }
        OrderEntity oe = orderDao.findOrderById(oprfe.getOrderId());
        if ((oe.getStatus() == CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue()) && oprfe.getType() == 2)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.ORDER_STATUS_ERROR.getValue());
            return result.toString();
        }
        float opeMoney = ope.getSalesPrice() * ope.getProductCount();
        float money = oprfe.getApplyMoney();
        if (money > opeMoney)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.REFUND_MONEY_ERROR.getValue());
            return result.toString();
        }
        
        // 加一条判断,如果accountId,orderId,order_product_id,status!=6 存在的话,不能新增
        if (this.orderProductRefundDao.queryOrderProductExists(oprfe))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_SUBMIT_PRODUNT_STATUS.REFUND_RECORD_EXISTS.getValue());
            return result.toString();
        }
        
        oprfe.setRealMoney(oprfe.getApplyMoney());
        
        if (accountCardId > 0)
        {
            //用户银行账户   
            AccountCartEntity ace = this.accountCardDao.queryAccountCardById(accountCardId);
            if (ace != null)
            {
                oprfe.setCardType(ace.getType());
                oprfe.setBankType(ace.getBankType());
                oprfe.setCardNumber(ace.getCardNumber());
                oprfe.setCardName(ace.getCardName());
                oprfe.setRefundPayType(1);
            }
        }
        else
        {
            //退款 原路返回
            oprfe.setCardType((byte)0);
            oprfe.setBankType((byte)0);
            oprfe.setCardNumber("");
            oprfe.setCardName("");
            oprfe.setRefundPayType(2);
        }

        // 财务打款账户选择
        int targetFinancialAffairsCardId = 4;
        if (oe.getPayChannel() == CommonEnum.PayChannelEnum.ALIPAY.ordinal())
        {
            double appVersion = Double.valueOf((oe.getAppVersion() == null || "".equals(oe.getAppVersion())) ? "0" : oe.getAppVersion()).doubleValue();

//            if ("19".equals(oe.getAppChannel()) || "20".equals(oe.getAppChannel()) || "25".equals(oe.getAppChannel()))
//            {
//                targetFinancialAffairsCardId = 13;
//            }
            if (appVersion >= 1.8)
            {
                if (oe.getRealPrice() <= 0.1f) // 使用国内支付宝
                {
                    targetFinancialAffairsCardId = 13;
                }
                else
                {
                    targetFinancialAffairsCardId = 14;
                }
            }
            else if(11 == oe.getAppChannel())
            {
                // 移动网站
                if (oe.getRealPrice() <= 0.1f)
                {
                    targetFinancialAffairsCardId = 5;
                }
                else
                {
                    targetFinancialAffairsCardId = 14;
                }
            }
            else
            {
                targetFinancialAffairsCardId = 5;
            }
        }
        else if (oe.getPayChannel() == CommonEnum.PayChannelEnum.WEIXIN.ordinal())
        {
            // 判断退款登陆账号
            double version = 0;
            try
            {
                version = Double.parseDouble((oe.getAppVersion() == null || "".equals(oe.getAppVersion())) ? "0" : oe.getAppVersion());
            }
            catch (Exception e)
            {
            }
            if (28 == oe.getAppChannel())
            {
                // 格格团app 订单
                targetFinancialAffairsCardId = 18;
            }
            else if (24 == oe.getAppChannel() || 26 == oe.getAppChannel() || 27 ==oe.getAppChannel())
            {
                // 格格团app 订单
                targetFinancialAffairsCardId = 16;
            }
            else if (22 == oe.getAppChannel())
            {
                targetFinancialAffairsCardId = 15;
            }
            else if ((19 == oe.getAppChannel() || 25 == oe.getAppChannel()) && version > 1.5)
            {
                targetFinancialAffairsCardId = 11;
            }
            else if (20 == oe.getAppChannel() && version > 1.5)
            {
                targetFinancialAffairsCardId = 12;
            }
            else if (version > 1.5)
            {
                if (oe.getNumber().endsWith("1"))
                {
                    targetFinancialAffairsCardId = 9;
                }
                else
                {
                    targetFinancialAffairsCardId = 10;
                }
            }
            else
            {
                if (oe.getNumber().endsWith("1"))
                {
                    // app订单
                    targetFinancialAffairsCardId = 7;
                }
                else
                {
                    // 网页
                    targetFinancialAffairsCardId = 6;
                }
            }
        }
        else if (oe.getPayChannel() == CommonEnum.PayChannelEnum.BANK.ordinal())
        {
            targetFinancialAffairsCardId = 8;
        }

        oprfe.setFinancialAffairsCardId(targetFinancialAffairsCardId);
        
        if (this.orderProductRefundDao.insertOrderProductRefund(oprfe) <= 0)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
            return result.toString();
        }
        
        result.addProperty("orderProductRefundId", oprfe.getId());
        
        // 新增订单退款退货后要冻结相应订单，防止后台发货
        if (orderDao.updateOrderFreeze(ope.getOrderId(), 1) == 1)
        {
            Map<String, Object> freezeMap = orderDao.findOrderFreezeByOrderId(ope.getOrderId());
            if (freezeMap == null)
            {
                orderDao.insertOrderFreeze(ope.getOrderId());
            }
            else
            {
                // 更新订单冻结记录
                orderDao.updateOrderFreezeRecord(Integer.parseInt(freezeMap.get("id") + ""));
            }
        }
        else
        {
            log.warn("新增退款退货商品，冻结对应订单状态失败！");
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String refundInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        String orderProductRefundId = (params.get("orderProductRefundId") == null ? "" : params.get("orderProductRefundId").getAsString());
        
        String accountId = params.get("accountId") == null ? "0" : params.get("accountId").getAsString();
        String sign = params.get("sign") == null ? "" : params.get("sign").getAsString().trim();
        String isApp = params.get("isApp") == null ? "0" : params.get("isApp").getAsString().trim();
        
        if (CommonEnum.COMMON_IS.YES.getValue().equals(isApp))
        {
            if (sign.equals(""))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
                return result.toString();
            }
            AccountEntity ac = this.accountDao.findAccountById(Integer.parseInt(accountId));
            if (ac == null || ac.getId() <= 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.ID_NOT_EXISTS.getValue()); // 用户不存在
                return result.toString();
            }
            
            String verify_sign = "";
            try
            {
                verify_sign = CommonUtil.strToMD5(ac.getId() + CommonUtil.SIGN_KEY);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            if (!verify_sign.equals(sign))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.SING_KEY_ERROR.getValue()); //
                return result.toString();
            }
            AccountView av = new AccountView();
            av.setId(ac.getId());
            av.setName(ac.getName());
            av.setNickname(ac.getNickname());
            av.setPwd(ac.getPwd());
            av.setType(ac.getType());
            
            SessionUtil.addUserToSession(request.getSession(), av);
            writeUserCookie(request, response, av.getPwd(), 60 * 60 * 24 * 30);
        }
        
        if (orderProductRefundId.equals("") || !CommonUtil.isNumeric(orderProductRefundId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.REFUND_INFO_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
            return result.toString();
        }
        
        OrderProductRefundEntity oprfe = this.orderProductRefundDao.queryOrderProductRefundById(Integer.parseInt(orderProductRefundId));
        if (oprfe == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.REFUND_INFO_STATUS.ORDER_PRODUCT_REFUND_ID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        OrderReturnProductView orpv = getOrderReturnProductView(oprfe);
        result.add("productList", parser.parse(JSONUtils.toJson(orpv, false)));
        
        result.addProperty("refundPayType", oprfe.getRefundPayType());//退款接收账户类型
        if (oprfe.getRefundPayType() == RefundPayTypeEnum.CREATE_NEW_ACCOUNT_CARD.ordinal())
        {
            // int accountCardId = oprfe.getAccountCardId();
            AccountCartEntity ace = new AccountCartEntity(); // accountCardDao.queryAccountCardById(accountCardId);
            
            if (ace != null)
            {
                String cardNumber = oprfe.getCardNumber();
                byte cardType = oprfe.getCardType();
                
                if (cardType == (byte)CommonEnum.ACCOUNT_CARD_TYPE.TYPE_BANK_TYPE.getValue()) // 是银行卡　取卡号后四位　
                {
                    if (cardNumber != null && !cardNumber.equals("") && cardNumber.length() > 4)
                    {
                        cardNumber = cardNumber.substring(cardNumber.length() - 4);
                    }
                }
                else
                {
                    if (cardNumber.length() > 13)
                    {
                        cardNumber = CommonUtil.getAccountCardValue(cardNumber, 11, "ali");
                        cardNumber += "...";
                    }
                }
                ace.setCardNumber(cardNumber);
                ace.setType(cardType);
                ace.setBankType(oprfe.getBankType());
                ace.setCardName(oprfe.getCardName());
                result.add("accountCartList", parser.parse(JSONUtils.toJson(ace, false)));
            }
        }
        else
        {
            //原退款账户
        }
        
        result.add("refundProductInfo", parser.parse(JSONUtils.toJson(oprfe, false)));
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String returnGoodInfo(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        String orderProductRefundId = (params.get("orderProductRefundId") == null ? "" : params.get("orderProductRefundId").getAsString());
        String type = (params.get("type") == null ? "" : params.get("type").getAsString()); // type==2时，表示待退货时，要用户填写物流单号，==3：待退款，4：退款成功
                                                                                            // ，查物流信息
        
        if (orderProductRefundId.equals("") || !CommonUtil.isNumeric(orderProductRefundId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.REFUND_INFO_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
            return result.toString();
        }
        
        OrderProductRefundEntity oprfe = this.orderProductRefundDao.queryOrderProductRefundById(Integer.parseInt(orderProductRefundId));
        if (oprfe == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.REFUND_INFO_STATUS.ORDER_PRODUCT_REFUND_ID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        OrderReturnProductView orpv = getOrderReturnProductView(oprfe);
        result.add("productList", parser.parse(JSONUtils.toJson(orpv, false)));
        // result.add("refundProductInfo", parser.parse(JSONUtils.toJson(oprfe, false)) );
        result.addProperty("count", oprfe.getCount() + "");
        result.addProperty("money", this.decimalFormat.format(oprfe.getRealMoney()));
        result.addProperty("explain", oprfe.getExplain());
        result.addProperty("status", oprfe.getStatus() + "");
        result.addProperty("orderProductRefundId", oprfe.getId() + "");
        if (oprfe.getRefundPayType() == RefundPayTypeEnum.CREATE_NEW_ACCOUNT_CARD.ordinal())
        {
            int accountCardId = oprfe.getAccountCardId();
            AccountCartEntity ace = this.accountCardDao.queryAccountCardById(accountCardId);
            if (ace != null)
            {
                String accountCardValue = CommonUtil.getAccountCardValue(ace);
                result.addProperty("accountCardValue", accountCardValue);
            }
        }
        else
        {
            result.addProperty("accountCardValue", "原支付账户");
        }
        
        if (type.equals(CommonEnum.ORDER_RRODUCT_REFUND_STATUS.FOR_RETURN.getValue()) || type.equals(CommonEnum.ORDER_RRODUCT_REFUND_STATUS.SUCCESS_REFUND.getValue())
            || type.equals(CommonEnum.ORDER_RRODUCT_REFUND_STATUS.BACK_OFF.getValue()))
        {
            OrderProductRefundLogisticsEntity opre = orderProductRefundLogisticDao.queryOrderProductRefundLogisticByOId(Integer.parseInt(orderProductRefundId));
            if (opre != null)
            {
                result.addProperty("channel", opre.getChannel());
                result.addProperty("number", opre.getNumber());
            }
        }
        
        String receiveAddress = "浙江杭州西湖区浙商财富中心4号楼607室 左岸城堡（收）0571-86888702";
        
        OrderEntity oe = orderDao.findOrderById(oprfe.getOrderId());
        if (oe != null)
        {
            SellerExpandEntity see = sellerDao.findSellerExpandBySellerId(oe.getSellerId());
            if (see != null)
            {
                String province = pdi.findProvinceNameById(see.getReceiveProvinceCode());
                String city = cdi.findCityNameById(see.getReceiveCityCode());
                String district = ddi.findDistinctNameById(see.getReceiveDistrictCode());
                if (StringUtils.isNotEmpty(province) && StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(district))
                {
                    receiveAddress = province + city + district + see.getReceiveDetailAddress() + " " + see.getReceivePerson() + "（收）" + see.getReceiveTelephone();
                }
                else
                {
                    log.info(String.format("商家ID=%d的收货地址为空", see.getSellerId()));
                }
            }
            else
            {
                log.info(String.format("商家ID=%d的收货地址为空", oe.getSellerId()));
            }
        }
        
        result.addProperty("receiveAddress", receiveAddress);
        return result.toString();
    }
    
    private OrderReturnProductView getOrderReturnProductView(OrderProductRefundEntity oprfe)
    {
        int orderProductId = oprfe.getOrderProductId();
        Map<String, Object> map = orderDao.findOrderProductInfosById(orderProductId);
        
        OrderReturnProductView orpv = new OrderReturnProductView();
        if (map != null && !map.isEmpty())
        {
            int orderId = (map.get("orderId") != null ? ((Long)map.get("orderId")).intValue() : 0);
            int productId = (map.get("product_id") != null ? ((Long)map.get("product_id")).intValue() : 0);
            int productCount = (map.get("product_count") != null ? (Integer)map.get("product_count") : 0);
            String smallImage = (map.get("smallImage") == null ? "" : map.get("smallImage").toString());
            String shortName = (map.get("shortName") == null ? "" : map.get("shortName").toString());
            String salesPrice = (map.get("salesPrice") == null ? "" : map.get("salesPrice") + "");
            
            orpv.productId = productId + "";
            orpv.image = smallImage;
            orpv.shortName = shortName;
            orpv.count = productCount + "";
            orpv.salesPrice = salesPrice;
            orpv.orderId = orderId + "";
            
            OrderEntity oe = this.orderDao.findOrderById(orderId);
            orpv.orderNumber = oe.getNumber();
            orpv.status = oe.getStatus() + "";
//            orpv.totalPrice = this.decimalFormat.format(oe.getTotalPrice() - oe.getFreightMoney());
            
            orpv.totalPrice = decimalFormat.format(Double.valueOf(salesPrice)*productCount);
            
            if (oe.getPayTime() != null && !oe.getPayTime().equals("0000-00-00 00:00:00"))
            {
                orpv.operateTime = oe.getPayTime();
            }
            else
            {
                orpv.operateTime = oe.getCreateTime();
            }
        }
        
        return orpv;
    }
    
    @Override
    public String submitReturnGood(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        String orderProductRefundId = params.get("orderProductRefundId").getAsString();
        String channel = params.get("channel").getAsString();
        String number = params.get("number").getAsString();
        
        if (orderProductRefundId == null || orderProductRefundId.equals("") || channel == null || channel.equals("") || number == null || number.equals(""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_PRODUNT_REFUND_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
            return result.toString();
        }
        
        if (!CommonUtil.isNumeric(orderProductRefundId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_PRODUNT_REFUND_STATUS.PARAMS_FORMAT_ERROR.getValue()); // 缺少参数
            return result.toString();
        }
        
        if (!this.orderProductRefundDao.isOrderProductRefundExist(Integer.parseInt(orderProductRefundId)))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_PRODUNT_REFUND_STATUS.ORDER_PRODUCT_REFUND_ID_NOT_EXISTS.getValue()); //
            return result.toString();
        }
        
        // 向admin发起物流订阅请求
        String subUrl = YggWebProperties.getInstance().getProperties("admin_refund_sub_url") + "?refundId=" + orderProductRefundId + "&channel=" + channel + "&number=" + number;
        try
        {
            String resultJson = restTemplate.getForObject(subUrl, String.class);
            if (resultJson != null)
            {
                @SuppressWarnings("rawtypes")
                Map reMap = (Map)JSON.parse(resultJson);
                String reStatus = reMap.get("status") + "";
                if ("1".equals(reStatus))
                {
                    // 成功 将退款状态从 待退货 -> 待退款
                    Map<String, Object> refundPara = new HashMap<String, Object>();
                    refundPara.put("id", orderProductRefundId);
                    refundPara.put("status", CommonEnum.RefundStatusEnum.WAIT_RETURN_OF_MONEY.ordinal());
                    orderProductRefundDao.updateOrderProductRefund(refundPara);
                    String orderId = orderProductRefundDao.findOrderIdByOrderProductRefundId(orderProductRefundId);
                    result.addProperty("orderId", orderId);
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
                    return result.toString();
                }
                else
                {
                    log.info("向admin发起物流订阅请求失败，返回结果：" + reMap);
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue()); //
                    return result.toString();
                }
            }
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue()); //
            return result.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue()); //
            return result.toString();
        }
    }
    
    @Override
    public String getReturnProcessInfo(HttpServletRequest request, HttpServletResponse response, String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        String page = (params.get("page") == null ? "1" : params.get("page").getAsString());
        String accountId = (params.get("accountId") == null ? "0" : params.get("accountId").getAsString());
        String sign = params.get("sign") == null ? "" : params.get("sign").getAsString().trim();
        String isApp = params.get("isApp") == null ? "0" : params.get("isApp").getAsString().trim();
        
        if (CommonEnum.COMMON_IS.YES.getValue().equals(isApp))
        {
            if (sign.equals(""))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
                return result.toString();
            }
            AccountEntity ac = this.accountDao.findAccountById(Integer.parseInt(accountId));
            if (ac == null || ac.getId() <= 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.ID_NOT_EXISTS.getValue()); // 用户不存在
                return result.toString();
            }
            
            String verify_sign = "";
            try
            {
                verify_sign = CommonUtil.strToMD5(ac.getId() + CommonUtil.SIGN_KEY);
            }
            catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
            }
            if (!verify_sign.equals(sign))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.SING_KEY_ERROR.getValue()); //
                return result.toString();
            }
            
            AccountView av = new AccountView();
            av.setId(ac.getId());
            av.setName(ac.getName());
            av.setNickname(ac.getNickname());
            av.setPwd(ac.getPwd());
            av.setType(ac.getType());
            
            SessionUtil.addUserToSession(request.getSession(), av);
            writeUserCookie(request, response, av.getPwd(), 60 * 60 * 24 * 30);
        }
        
        List<OrderProductRefundEntity> opres = this.orderProductRefundDao.queryAllOrderProductRefund(Integer.parseInt(accountId), Integer.parseInt(page));
        List<OrderFrefundView> ovs = new ArrayList<OrderFrefundView>();
        if (opres != null && !opres.isEmpty())
        {
            for (OrderProductRefundEntity opre : opres)
            {
                
                OrderEntity oe = this.orderDao.findOrderById(opre.getOrderId());
                
                putOrderFrefundView(ovs, oe, opre.getOrderProductId(), 2);
            }
        }
        
        result.add("orderList", parser.parse(JSONUtils.toJson(ovs, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    public void putOrderFrefundView(List<OrderFrefundView> ovs, OrderEntity oe, int orderProductIdFromReturnProcess, int source)
    {
        if (oe == null)
            return;
        
        OrderFrefundView offv = new OrderFrefundView();
        
        offv.orderId = oe.getId() + "";
        offv.orderNumber = oe.getNumber();
        offv.status = oe.getStatus() + "";
        if (oe.getPayTime() != null && !oe.getPayTime().equals("0000-00-00 00:00:00"))
        {
            offv.operateTime = oe.getPayTime();
        }
        else
        {
            offv.operateTime = oe.getCreateTime();
        }
        offv.totalPrice = decimalFormat.format(oe.getTotalPrice());
        
        List<Map<String, Object>> lockProductInfos = orderDao.findOrderProductInfosByOId(oe.getId());
        List<OrderFrefundProductView> ofpvs = new ArrayList<OrderFrefundProductView>();
        
        for (Map<String, Object> map : lockProductInfos)
        {
            int orderProductId = (map.get("id") != null ? ((Long)map.get("id")).intValue() : 0);
            int productId = (map.get("product_id") != null ? ((Long)map.get("product_id")).intValue() : 0);
            int productCount = (map.get("product_count") != null ? (Integer)map.get("product_count") : 0);
            String smallImage = (map.get("smallImage") == null ? "" : map.get("smallImage").toString());
            String shortName = (map.get("shortName") == null ? "" : map.get("shortName").toString());
            String salesPrice = (map.get("salesPrice") == null ? "" : map.get("salesPrice") + "");
            
            OrderFrefundProductView ofpv = new OrderFrefundProductView();
            
            ofpv.productId = productId + "";
            ofpv.image = smallImage;
            ofpv.shortName = shortName;
            ofpv.salesPrice = salesPrice;
            ofpv.count = productCount + "";
            
            ofpv.orderProductId = orderProductId + "";
            
            offv.orderProductId = orderProductId + "";
            
            Map<String, Object> mapParams = new HashMap<String, Object>();
            mapParams.put("orderProductId", orderProductId);
            List<Integer> statusList = new ArrayList<Integer>();
            statusList.add(1);
            statusList.add(2);
            statusList.add(3);
            statusList.add(4);
            statusList.add(5);
            if (source == orderSourceInfo) // 从自助退款　中来　要把　退款取消　状态的查出来，改为可以申请退款　
            {
                statusList.add(6);
                // mapParams.put("status", statusList) ; // 表示查出全部的状态
            }
            /*
             * else if(source == 2) // 从退款进度中来　，不查　退款取消　状态的 { mapParams.put("status", statusList) ; // 表示查出全部的状态 }
             */
            mapParams.put("status", statusList);
            
            Map<String, Object> productRefundMap = orderProductRefundDao.queryOPRInfosByOrderProductId(mapParams);
            ofpv.refundStatus = (productRefundMap.get("status") == null ? "0" : productRefundMap.get("status") + ""); // 0表示在order_product_refund表中没有记录
            
            //自助退款时，只显示可申请退款的订单（申请中(1)、待退货(2)、待退款(3)、退款成功(4)、退款关闭(5)不显示）
            if (source == orderSourceInfo
                && ("1".equals(ofpv.refundStatus) || "2".equals(ofpv.refundStatus) || "3".equals(ofpv.refundStatus) || "4".equals(ofpv.refundStatus) || "5".equals(ofpv.refundStatus)))
            {
                // 不加入
                continue;
            }
            ofpv.refundMoney = (productRefundMap.get("realMoney") == null ? "" : productRefundMap.get("realMoney") + "");
            if (!ofpv.refundMoney.equals(""))
                ofpv.refundMoney = this.decimalFormat.format(Float.valueOf(ofpv.refundMoney));
            
            ofpv.orderProductRefundId = (productRefundMap.get("id") == null ? "0" : productRefundMap.get("id") + "");
            ofpv.type = (productRefundMap.get("type") == null ? "1" : productRefundMap.get("type") + "");
            
            if (source == orderReturnProcess && orderProductId == orderProductIdFromReturnProcess) // 退款进度中来
                                                                                                   // 对于一个定单下有多个商品而言，
            {
                ofpvs.add(ofpv);
                break;
            }
            else if (source == orderSourceInfo)
            {
                ofpvs.add(ofpv);
            }
            
        }
        if (ofpvs.size() > 0)
        {
            offv.orderDetailList = ofpvs;
            ovs.add(offv);
        }
        
    }
    
    public String getOrderRefundCount(String requestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(requestParams);
        String accountId = params.get("accountId") == null ? "" : params.get("accountId").getAsString().trim();
        
        int count = orderProductRefundDao.queryOrderRefundCountByAccountId(Integer.parseInt(accountId));
        
        result.addProperty("count", count);
        
        return result.toString();
    }
    
    @Override
    public String getOrderRefundIndex(HttpServletRequest request, String requestParams, HttpServletResponse response)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(requestParams);
        String accountId = params.get("accountId") == null ? "" : params.get("accountId").getAsString().trim();
        String sign = params.get("sign") == null ? "" : params.get("sign").getAsString().trim();
        
        if (accountId.equals("") || sign.equals(""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.MISSING_PARAMS.getValue()); // 缺少参数
            return result.toString();
        }
        
        AccountEntity ac = this.accountDao.findAccountById(Integer.parseInt(accountId));
        if (ac == null || ac.getId() <= 0)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.ID_NOT_EXISTS.getValue()); // 用户不存在
            return result.toString();
        }
        
        String verify_sign = "";
        try
        {
            verify_sign = CommonUtil.strToMD5(ac.getId() + CommonUtil.SIGN_KEY);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        if (!verify_sign.equals(sign))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_REFUND_INDEX_STATUS.SING_KEY_ERROR.getValue()); //
            return result.toString();
        }
        
        AccountView av = new AccountView();
        av.setId(ac.getId());
        av.setName(ac.getName());
        av.setNickname(ac.getNickname());
        av.setPwd(ac.getPwd());
        av.setType(ac.getType());
        
        SessionUtil.addUserToSession(request.getSession(), av);
        writeUserCookie(request, response, av.getPwd(), 60 * 60 * 24 * 30);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public OrderProductRefundView getOrderProductRefundViewById(int orderProductRefundId)
        throws ServiceException
    {
        OrderProductRefundEntity oprfe = this.orderProductRefundDao.queryOrderProductRefundById(orderProductRefundId);
        OrderProductRefundView oprf = new OrderProductRefundView();
        
        if (oprfe != null)
        {
            BeanUtils.copyProperties(oprfe, oprf);
        }
        
        return oprf;
    }
    
    @Override
    public String findOrderProductRefundIdByPidAndOidAndAid(String productId, String orderId, String accountId)
        throws ServiceException
    {
        return orderProductRefundDao.findOrderProductRefundIdByPidAndOidAndAid(productId, orderId, accountId);
    }
    
    @Override
    public String findOrderProductIdByPidAndOid(String productId, String orderId)
        throws ServiceException
    {
        return orderProductRefundDao.findOrderProductIdByPidAndOid(productId, orderId);
    }
    
    private void writeUserCookie(HttpServletRequest request, HttpServletResponse response, String pwd, int second)
    {
        // 写入用户cookie
        Cookie cookie = new Cookie("userinfo", pwd);
        cookie.setMaxAge(second); // 保留一个月
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    
    public final int orderSourceInfo = 1;
    
    public final int orderReturnProcess = 2;

    /**
     * 取消退款
     * @param refundId
     * @return
     * @throws ServiceException
     */
    @Override
    public String cancelRefund(int refundId)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        String cancelUrl = YggWebProperties.getInstance().getProperties("admin_refund_cancel_url") + "?refundId=" + refundId;
        String resultJson = restTemplate.getForObject(cancelUrl, String.class);
        log.info("向admin发起 取消退款请求，返回结果：" + resultJson);
        if (resultJson != null)
        {
            Map reMap = (Map)JSON.parse(resultJson);
            String reStatus = reMap.get("status") + "";
            if ("1".equals(reStatus))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
                return result.toString();
            }
            else
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                return result.toString();
            }
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        return result.toString();
    }
}
