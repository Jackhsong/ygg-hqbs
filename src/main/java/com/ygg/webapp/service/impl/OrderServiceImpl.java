package com.ygg.webapp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.BrandReturnDistributionProportionTypeEnum;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.code.ErrorCodeEnum;
import com.ygg.webapp.code.KuaiDiCompanyAndPhoneTypeEnum;
import com.ygg.webapp.code.OrderRelationEnum;
import com.ygg.webapp.code.ProductEnum;
import com.ygg.webapp.dao.AccountAvailablePointRecordDao;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.AccountSuccessOrderRecordDao;
import com.ygg.webapp.dao.CityDao;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.DistrictDao;
import com.ygg.webapp.dao.LogisticsDetailDao;
import com.ygg.webapp.dao.OrderAliPayDao;
import com.ygg.webapp.dao.OrderConfirmDao;
import com.ygg.webapp.dao.OrderConfirmProductDao;
import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.OrderLogisticsDao;
import com.ygg.webapp.dao.OrderProductRefundDao;
import com.ygg.webapp.dao.OrderReceiveAddressDao;
import com.ygg.webapp.dao.OrderUnionPayDao;
import com.ygg.webapp.dao.OrderWeixinPayDao;
import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.ProvinceDao;
import com.ygg.webapp.dao.ReceiveAddressDao;
import com.ygg.webapp.dao.SellerDao;
import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.dao.TempAccountDao;
import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.dao.account.HqbsAccountDao;
import com.ygg.webapp.dao.account.QqbsSpokespersonDao;
import com.ygg.webapp.dao.fans.HqbsFansDao;
import com.ygg.webapp.dao.fans.HqbsFansOrderDao;
import com.ygg.webapp.entity.AccountAvailablePointRecordEntity;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.AccountRecommendedReturnPointEntity;
import com.ygg.webapp.entity.AccountSuccessOrderRecordEntity;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponCodeEntity;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.LogisticsDetailEntity;
import com.ygg.webapp.entity.OrderAliPayEntity;
import com.ygg.webapp.entity.OrderConfirmEntity;
import com.ygg.webapp.entity.OrderConfirmProductEntity;
import com.ygg.webapp.entity.OrderEntity;
import com.ygg.webapp.entity.OrderLogisticsEntity;
import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderReceiveAddressEntity;
import com.ygg.webapp.entity.OrderSourceChannel;
import com.ygg.webapp.entity.OrderUnionPayEntity;
import com.ygg.webapp.entity.OrderWeixinPayEntity;
import com.ygg.webapp.entity.ProductBaseEntity;
import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.ProvinceEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.ReceiveAddressEntity;
import com.ygg.webapp.entity.SellerEntity;
import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.entity.account.QqbsSpokesperson;
import com.ygg.webapp.entity.fans.QqbsFansEntity;
import com.ygg.webapp.entity.fans.QqbsFansOrderEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.sdk.aliap.config.AlipayConfig;
import com.ygg.webapp.sdk.aliap.util.AlipayNotify;
import com.ygg.webapp.sdk.aliap.util.AlipaySubmit;
import com.ygg.webapp.sdk.aliap.util.UtilDate;
/*import com.unionpay.acp.sdk.SDKConfig;
 import com.unionpay.acp.sdk.SDKConstants;
 import com.unionpay.acp.sdk.SDKUtil;*/
import com.ygg.webapp.sdk.unionpay.DemoBase;
import com.ygg.webapp.sdk.unionpay.SDKConfig;
import com.ygg.webapp.sdk.unionpay.SDKConstants;
import com.ygg.webapp.sdk.unionpay.SDKUtil;
import com.ygg.webapp.sdk.weixin.client.WeiXinHttpClient;
import com.ygg.webapp.sdk.weixin2.GetWxOrderno;
import com.ygg.webapp.sdk.wxap.RequestHandler;
import com.ygg.webapp.sdk.wxap.ResponseHandler;
import com.ygg.webapp.sdk.wxap.util.Sha1Util;
import com.ygg.webapp.service.DoInNewTranscationService;
import com.ygg.webapp.service.OrderService;
import com.ygg.webapp.service.ReceiveAddressService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonHttpClient;
import com.ygg.webapp.util.CommonProperties;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.IdCardUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.MathUtil;
import com.ygg.webapp.util.NetworkUtil;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.util.WeixinMessageDigestUtil;
import com.ygg.webapp.util.YggWebProperties;
import com.ygg.webapp.view.ConfirmOrderView;
import com.ygg.webapp.view.CouponView;
import com.ygg.webapp.view.FreightView;
import com.ygg.webapp.view.OrderLogisticsView;
import com.ygg.webapp.view.OrderProductView;
import com.ygg.webapp.view.OrderView;
import com.ygg.webapp.view.ReceiveAddressView;
import com.ygg.webapp.view.WeiXinPayReqView;

// @Service("orderService") init方法
public class OrderServiceImpl implements OrderService
{
    
    DecimalFormat decimalFormat = new DecimalFormat("0.00");
    
    private int orderValidMillis = 30 * 60 * 1000;
    
    private int adminAccountIdOrderValidMillis = 5 * 24 * 60 * 60 * 1000;
    
    private static Logger log = Logger.getLogger(OrderServiceImpl.class);
    
    @Resource(name = "tempAccountDao")
    private TempAccountDao tadi = null;
    
    @Resource(name = "accountDao")
    private AccountDao adi = null;
    
    @Resource(name = "shoppingCartDao")
    private ShoppingCartDao scdi = null;
    
    @Resource(name = "tempShoppingCartDao")
    private TempShoppingCartDao tscdi = null;
    
    @Resource(name = "receiveAddressDao")
    private ReceiveAddressDao radi = null;
    
    @Resource(name = "productDao")
    private ProductDao pdi = null;
    
    @Resource(name = "productCommonDao")
    private ProductCommonDao pcommondi = null;
    
    @Resource(name = "sellerDao")
    private SellerDao sdi = null;
    
    @Resource(name = "provinceDao")
    private ProvinceDao provincedi = null;
    
    @Resource(name = "cityDao")
    private CityDao citydao = null;
    
    @Resource(name = "districtDao")
    private DistrictDao districtDao = null;
    
    @Resource(name = "orderDao")
    private OrderDao odi = null;
    
    @Resource(name = "orderConfirmDao")
    private OrderConfirmDao oci = null;
    
    @Resource(name = "orderConfirmProductDao")
    private OrderConfirmProductDao ocpi = null;
    
    @Resource(name = "productCountDao")
    private ProductCountDao pcdi = null;
    
    // @Resource(name="cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "orderWeixinPayDao")
    private OrderWeixinPayDao orderWeixinPayDao;
    
    @Resource(name = "receiveAddressService")
    private ReceiveAddressService receiveAddressService;
    
    @Resource(name = "orderUnionPayDao")
    private OrderUnionPayDao orderUnionPayDao;
    
    @Resource(name = "orderAliPayDao")
    private OrderAliPayDao orderAliPayDao;
    
    @Resource(name = "logisticsDetailDao")
    private LogisticsDetailDao logisticsDetailDao;
    
    @Resource(name = "orderLogisticsDao")
    private OrderLogisticsDao orderLogisticsDao;
    
    @Resource(name = "orderReceiveAddressDao")
    private OrderReceiveAddressDao orderReceiveAddressDao;
    
    @Resource(name = "accountAvailablePointRecordDao")
    private AccountAvailablePointRecordDao aaprdi;
    
    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource(name = "accountSuccessOrderRecordDao")
    private AccountSuccessOrderRecordDao accountSuccessOrderRecordDao;
    
    @Resource
    private CouponCodeDao couponCodeDao;
    
    @Resource(name = "orderProductRefundDao")
    private OrderProductRefundDao orderProductRefundDao;
    
    @Resource(name = "doInNewTranscationService")
    private DoInNewTranscationService doInNewTranscationService;
    
    /** 粉丝关系表Dao */
    @Resource(name = "hqbsFansDao")
    private HqbsFansDao hqbsFansDao;
    
    /** 左岸城堡用户表Dao */
    @Resource(name = "hqbsAccountDao")
    private HqbsAccountDao hqbsAccountDao;
    
    /** 左岸城堡粉丝订单表Dao */
    @Resource(name = "hqbsFansOrderDao")
    private HqbsFansOrderDao hqbsFansOrderDao;
    /**    */
    @Resource(name="qqbsSpokespersonDao")
    private QqbsSpokespersonDao qqbsSpokespersonDao;
    /**
     * 保存到缓存中去
     */
    private List<FreightView> freeShippingFreight = null; // new ArrayList<FreightView>();
    
    private String sellerId555 = "45";
    
    private String sellerId556 = "45";
    
    private String sellerId583 = "45";
    
    private String sellerId591 = "0";
    
    public OrderServiceImpl()
    {
        
    }
    
    /**
     * 加载运费0模版,以后可以先加载所有的运费模版
     * 
     * @throws ServiceException
     */
    public void init()
        throws ServiceException
    {
        getFreeShippingFreight();
    }
    
    private List<FreightView> getFreeShippingFreight()
    {
//        freeShippingFreight = this.cacheService.getCache(CacheConstant.FREE_SHIPPING_FREIGHT_CACHE);
        if (freeShippingFreight == null || freeShippingFreight.isEmpty())
        {
            freeShippingFreight = new ArrayList<FreightView>();
            FreightView fv = new FreightView();
            fv.setLogisticsMoney("0");
            List<ProvinceEntity> pes;
            try
            {
                pes = provincedi.findAllProvinceInfo();
                List<String> provinces = new ArrayList<String>();
                for (ProvinceEntity pe : pes)
                {
                    provinces.add(pe.getProvinceId() + "");
                }
                fv.setProvinceIds(provinces);
                freeShippingFreight.add(fv);
            }
            catch (DaoException e)
            {
                log.error(e);
            }
            this.cacheService.addCache(CacheConstant.FREE_SHIPPING_FREIGHT_CACHE, freeShippingFreight, CacheConstant.CACHE_DAY_1);
        }
        return freeShippingFreight;
    }
    
    @Override
    public String generateOrder(OrderView orderView)
        throws ServiceException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String removeOrder(String requestParams)
        throws ServiceException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<OrderView> listAllOrder(String requestParams)
        throws ServiceException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String listOrderStatusCountByUserId(String requestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountid = param.get("id").getAsInt();
        
        int nopayCount = 0; // this.odi.findOrderStatusCount(1, accountid) ; // 在过期范围内
        List<OrderEntity> noPayOrders = this.odi.findNotPayOrderByAIdAndStatus(accountid, 1);
        for (OrderEntity oe : noPayOrders)
        {
            if (oe.getStatus() == CommonEnum.ORDER_STATUS.NOT_PAY.getValue())
            {
                String validTimeStr = odi.findValidTimeByOid(oe.getId());
                if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
                {
                    Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                    if (new Date().before(validTime)) // 订单锁定未过期
                    {
                        nopayCount++;
                    }
                    else
                        continue; // 过期先不显示出来
                }
                else
                    continue; // 过期先不显示出来
            }
        }
        
        int NOTDELIVERYCount = this.odi.findOrderStatusCount(2, accountid);
        int YESDELIVERYCount = this.odi.findOrderStatusCount(3, accountid);
        int SUCCESSCount = this.odi.findOrderStatusCount(4, accountid);
        int USERCANCELCount = this.odi.findOrderStatusCount(5, accountid);
        
        result.addProperty(CommonConstant.ORDER_STATUS_1, nopayCount);
        result.addProperty(CommonConstant.ORDER_STATUS_2, NOTDELIVERYCount);
        result.addProperty(CommonConstant.ORDER_STATUS_3, YESDELIVERYCount);
        result.addProperty(CommonConstant.ORDER_STATUS_4, SUCCESSCount);
        result.addProperty(CommonConstant.ORDER_STATUS_5, USERCANCELCount);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String confirm(String requestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int tempAccountId = param.get("cartToken").getAsInt();
        String type = param.get("type").getAsString();
        String endSecond = "-1";
        List<ConfirmOrderView> covs = new ArrayList<>();
        ReceiveAddressEntity rae = radi.findDefaultAddressByAccountId(accountId);
        Map<String, String> executeResult = new HashMap<>();
        Set<String> tips = new LinkedHashSet<>();
        
        // 用户不存在
        AccountEntity ae = adi.findAccountById(accountId);
        if (ae == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        // 正常确认订单
        if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.NORMAL.getValue()))
        {
            String validTimeStr = scdi.findValidTimeByAid(accountId);
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().after(validTime)) // 购物车锁定已过期
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue());
                    return result.toString();
                }
                else
                // 购物车锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            else
            // 不存在购物车锁定时间 信息
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue());
                return result.toString();
            }
            
            List<ShoppingCartEntity> sces = scdi.findAllNormalCartByAId(accountId);
            executeResult = executeLoginConfirm(covs, rae, sces, accountId, tips);
            
        }
        // 结算时登录确认订单
        else if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.TEMP_MERGER.getValue()))
        {
            // 临时用户不存在
            if (!tadi.idIsExist(tempAccountId))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue());
                return result.toString();
            }
            
            String tempValidTimeStr = tscdi.findValidTimeByAid(tempAccountId);
            if (tempValidTimeStr != null && !tempValidTimeStr.equals("")) // 存在临时购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().after(validTime)) // 临时购物车锁定已过期
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue());
                    return result.toString();
                }
                else
                // 临时购物车锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            else
            // 不存在临时购物车锁定时间 信息
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue());
                return result.toString();
            }
            
            List<TempShoppingCartEntity> tsces = tscdi.findAllNormalCartByAId(tempAccountId);
            executeResult = executeNotLoginConfirm(covs, rae, tsces, accountId, tempAccountId, tips);
            
        }
        
        // 化妆品临时邮费方案
        if (Float.parseFloat(executeResult.get("allTotalPrice")) >= CommonProperties.postageThreshold)
        {
            for (ConfirmOrderView cov : covs)
            {
                if (cov.getTempFlag().equals("0"))
                {
                    cov.setFreights(freeShippingFreight);
                }
            }
            List<OrderConfirmEntity> oces = null;
            if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.NORMAL.getValue()))
            {
                oces = oci.findConfirmByNumberAndAId(Long.parseLong(executeResult.get("confirmId")), accountId);
            }
            else
            {
                oces = oci.findConfirmByNumberAndTId(Long.parseLong(executeResult.get("confirmId")), tempAccountId);
            }
            
            for (OrderConfirmEntity oce : oces)
            {
                oce.setFreightTemplateId(0);
                oci.updateOrderConfirm(oce);
            }
        }
        String defaultAddress = null;
        if (rae != null)
        {
            ReceiveAddressView rav = new ReceiveAddressView();
            
            // BeanUtils.copyProperties(rav,rae);
            rav.setAccountId(rae.getAccountId());
            rav.setId(rae.getId());
            rav.setFullName(rae.getFullName());
            rav.setMobileNumber(rae.getMobileNumber());
            
            rav.setProvince(rae.getProvince());
            rav.setCity(rae.getCity());
            rav.setDistrict(rae.getDistrict());
            
            /*
             * rav.setProvince(this.provincedi.findProvinceNameById(rae.getProvince()));
             * rav.setCity(this.citydao.findCityNameById(rae.getCity()) ); rav.setDistrict(
             * this.districtDao.findDistinctNameById( rae.getDistrict()) ) ; String detailAddress
             * =rav.getProvince()+rav.getCity()+rav.getDistrict()+ rae.getDetailAddress() ;
             */
            
            String provinceName = CommonUtil.getProvinceNameByProvinceId(rae.getProvince());
            String cityName = CommonUtil.getCityNameByCityId(rae.getProvince(), rae.getCity());
            String districtName = CommonUtil.getDistrictNameByDistrictId(rae.getCity(), rae.getDistrict());
            String detailAddress = provinceName + cityName + districtName + rae.getDetailAddress();
            
            /*
             * rav.setProvince(provinceName); rav.setCity(cityName); rav.setDistrict(districtName);
             */
            
            rav.setDetailAddress(detailAddress);
            
            rav.setIdCard(rae.getIdCard());
            rav.setIsDefault(rae.getIsDefault());
            
            defaultAddress = JSONUtils.toJson(rav, false);
        }
        
        result.add("tips", parser.parse(JSONUtils.toJson(tips, false)));
        result.add("confirmOrderList", parser.parse(JSONUtils.toJson(covs, false)));
        result.addProperty("addressId", rae != null ? rae.getId() + "" : "-1");
        result.add("defaultAddress", (defaultAddress != null ? parser.parse(defaultAddress) : null));
        result.addProperty("isBonded", executeResult.get("isBonded"));
        result.addProperty("confirmId", executeResult.get("confirmId"));
        result.addProperty("endSecond", endSecond);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.addProperty("availablePoint", ae.getAvailablePoint() + "");// 用户可用积分
        return result.toString();
    }
    
    @Override
    public String add(String requestParams)
        throws ServiceException
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int tempAccountId = param.get("cartToken").getAsInt();
        String type = param.get("type").getAsString();
        int addressId = param.get("addressId").getAsInt();
        long confirmId = param.get("confirmId").getAsLong();
        float totalPrice = param.get("totalPrice").getAsFloat();
        float couponPrice = param.get("couponPrice").getAsFloat();
        int usedPoint = param.get("usedPoint").getAsInt();
        int couponId = param.get("couponId").getAsInt();
        String paytype = param.get("paytype").getAsString();
        String osc = param.get("osc").getAsString();
        
        float currTotalPrice = 0;
        int freightId = 0;
        int allFreightMoney = 0;
        List<Integer> orderIds = new ArrayList<>();
        ReceiveAddressEntity rae = radi.findAddressByAccountIdAndId(addressId, accountId);
        
        int usedPointTotal = 0;
        float couponTotalPrice = 0;
        float realTotalPrice = 0;
        
        // 用户不存在
        AccountEntity ae = adi.findAccountById(accountId);
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.ACCOUNTID_NOT_EXIST.getErrorCode());
            result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.ACCOUNTID_NOT_EXIST.getErrorMessage());
            return result.toString();
        }
        
        // 收货地址不存在
        if (rae == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.ADDRESSID_NOT_EXIST.getErrorCode());
            result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.ADDRESSID_NOT_EXIST.getErrorMessage());
            return result.toString();
        }
        
        // 身份证信息不合法,不判断保税区,默认身份证入库必须合法
        if (!CommonUtil.isBlank(rae.getIdCard()) && !IdCardUtil.verify(rae.getIdCard()))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.ID_CARD_INVALID.getErrorCode());
            result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.ID_CARD_INVALID.getErrorMessage());
            return result.toString();
        }
        
        // 超过个人最大积分
        if (usedPoint > ae.getAvailablePoint())
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorCode());
            result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorMessage());
            return result.toString();
        }
        
        CouponAccountEntity cae = null;
        CouponDetailEntity cde = null;
        float reducePrice = 0;
        List<Integer> confirmIds = new ArrayList<>();
        List<Integer> orderConfirmProductIds = new ArrayList<>();
        for (OrderConfirmEntity oce : oci.findConfirmByNumber(confirmId))
        {
            confirmIds.add(oce.getId());
            List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
            for (OrderConfirmProductEntity ocpe : ocpes)
            {
                if (!orderConfirmProductIds.contains(ocpe.getProductId()))
                {
                    orderConfirmProductIds.add(ocpe.getProductId());
                }
            }
        }
        
        // 商品不支持的配送区域
        String unSupport = generateUnSupportProductInfos(orderConfirmProductIds, rae);
        if (unSupport != null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.UNSUPPORT_DELIVER_AREA.getErrorCode());
            result.addProperty("errorMessage", unSupport);
            return result.toString();
        }
        
        // 优惠券不匹配
        if (couponId != 0)
        {
            cae = couponAccountDao.findCouponsByAidAndId(couponId, accountId);
            if (cae != null)
            {
                Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId());
                if (cdeCache != null)
                {
                    cde = (CouponDetailEntity)cdeCache;
                }
                else
                {
                    cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
                    cacheService.addCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
                }
            }
            
            Date currDate = CommonUtil.string2Date(CommonUtil.date2String(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
            Date startDate = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd");
            Date endDate = CommonUtil.string2Date(cae.getEndTime(), "yyyy-MM-dd");
            if (cae == null || cde == null || cae.getIsUsed() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()) || startDate.after(currDate) || endDate.before(currDate)
                || (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.PRODUCT.getValue() && !orderConfirmProductIds.contains(cde.getScopeId()))) // 优惠券使用类型为单品
            {
                log.error("处理业务出错：" + ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorCode());
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorCode());
                result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorMessage());
                return result.toString();
            }
            // 判断优惠券类型是否是随机优惠券，若是随机优惠券，优惠金额要读取CouponAccountEntity.reducePrice
            if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            {
                reducePrice = cae.getReducePrice();
            }
            else
            {
                reducePrice = cde.getReduce();
            }
        }
        
        // 特定邮费商家列表
        Map<String, Integer> specialPostageSellerMap = new HashMap<>();
        List<Map<String, Object>> specialPostageSellerIdList = sdi.findAllSellerPosgageBlacklistId();
        for (Map<String, Object> map : specialPostageSellerIdList)
        {
            specialPostageSellerMap.put(map.get("seller_id") + "", Integer.valueOf(map.get("freight_money") + ""));
        }
        
        // 正常新增订单
        if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.NORMAL.getValue()))
        {
            String validTimeStr = scdi.findValidTimeByAid(accountId);
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().after(validTime)) // 购物车锁定已过期
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorCode());
                    result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorMessage());
                    return result.toString();
                }
            }
            else
            // 不存在购物车锁定时间 信息
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorCode());
                result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorMessage());
                return result.toString();
            }
            
            List<OrderConfirmEntity> oces = oci.findConfirmByNumberAndAId(confirmId, accountId);
            if (oces.size() == 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CONFIRMID_NOT_EXIST.getErrorCode());
                result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CONFIRMID_NOT_EXIST.getErrorMessage());
                return result.toString();
            }
            
            // 根据订单数量平均分配用户使用积分和优惠券优惠
            float[] usedPointPrices = new float[oces.size()];
            if (usedPoint != 0)
            {
                float average = usedPoint / oces.size() / 100f;
                for (int i = 0; i < usedPointPrices.length; i++)
                {
                    usedPointPrices[i] = average;
                }
                if (usedPoint % oces.size() != 0)
                {
                    usedPointPrices[oces.size() - 1] += (usedPoint % oces.size()) / 100f;
                }
            }
            
            float[] couponPrices = new float[oces.size()];
            if (cae != null)
            {
                if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.COMMON.getValue())
                {
                    float average = CommonUtil.transformFloat(reducePrice / oces.size());
                    for (int i = 0; i < couponPrices.length; i++)
                    {
                        couponPrices[i] = average;
                    }
                    if (((reducePrice * 100) % oces.size()) / 100 != 0)
                    {
                        couponPrices[oces.size() - 1] = CommonUtil.transformFloat(reducePrice - CommonUtil.transformFloat(average * (oces.size() - 1)));
                    }
                }
                else if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.PRODUCT.getValue())
                {
                    int productTotalCount = ocpi.findProductSumByConfirmIdsAndProductId(confirmIds, cde.getScopeId());
                    float average = CommonUtil.transformFloat(reducePrice / productTotalCount);
                    boolean isHasBalance = false;
                    if (((reducePrice * 100) % productTotalCount) / 100 != 0)
                    {
                        isHasBalance = true;
                    }
                    int index = 0;
                    for (OrderConfirmEntity oce : oces)
                    {
                        List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
                        for (OrderConfirmProductEntity ocpe : ocpes)
                        {
                            if (ocpe.getProductId() == cde.getScopeId())
                            {
                                couponPrices[index] = ocpe.getProductCount() * average;
                                if (isHasBalance && oces.size() != 1)
                                {
                                    couponPrices[index] =
                                        CommonUtil.transformFloat(reducePrice - CommonUtil.transformFloat(average * (productTotalCount - ocpe.getProductCount())));
                                    isHasBalance = false;
                                }
                            }
                        }
                        index++;
                    }
                }
            }
            
            // List<Integer> productIds = new ArrayList<>();
            // for (OrderConfirmEntity oce : oces)
            // {
            // List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
            // for (OrderConfirmProductEntity ocpe : ocpes)
            // {
            // productIds.add(ocpe.getProductId());
            // }
            // }
            // Collections.sort(productIds);
            
            List<Integer> handleProductId = new ArrayList<>();
            // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
            // @SuppressWarnings("unused")
            // List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds);
            OrderSourceChannel oschannel = this.odi.findOrderSourceChannelByMark(osc);
            /*
             * if(oschannel == null) { oschannel = new OrderSourceChannel(); oschannel.setMark(osc);
             * if(odi.addOrderSourceChannel(oschannel) == 0 ) { throw new
             * ServiceException("addOrderSourceChannel失败,osc:" + osc ); } }
             */
            
            String batchNumber = CommonUtil.generateOrderNumber();
            String sameBatchNumber = CommonUtil.generateOrderNumber(); // 真正 订单批次号
            int index = 0;
            // 新增订单，新增订单锁定时间，修改订单确认状态，修改购物车状态，删除购物车锁定数量，新增订单锁定数量，新增订单商品，更新购物车锁定时间至当前时间
            // 化妆品临时邮费方案
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;
            boolean flag4 = true;
            boolean flag5 = true;
            for (OrderConfirmEntity oce : oces)
            {
                // 正常确认订单 modify by zhangld start 2016/3/17
                SellerEntity orderSeller = sdi.findBrandInfoById(oce.getSellerId());
                sdi.insertOrderSellerInfo(orderSeller);
                int orderSellerId = orderSeller.getId();
                if (orderSellerId == 0)
                {
                    throw new ServiceException("插入订单商家失败," + "orderConfirmId:" + oce.getId());
                }
                OrderReceiveAddressEntity orae = new OrderReceiveAddressEntity();
                if (orderSeller != null && orderSeller.getSellerType() == (byte)1)
                {
                    orae.setIdCard("");
                }
                else
                {
                    orae.setIdCard(rae.getIdCard());
                }
                // modify by zhangld end 2016/3/17
                orae.setCity(rae.getCity());
                orae.setDetailAddress(rae.getDetailAddress());
                orae.setDistrict(rae.getDistrict());
                orae.setFullName(rae.getFullName());
                orae.setMobileNumber(rae.getMobileNumber());
                orae.setProvince(rae.getProvince());
                int orderReceiveAddId = orderReceiveAddressDao.addAddress(orae);
                if (orderReceiveAddId == 0)
                {
                    log.error("新增订单收货地址失败");
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    return result.toString();
                }
                orderReceiveAddId = orae.getId();
                freightId = oce.getFreightTemplateId();
                int freightMoney = freightId == 0 ? 0 : provincedi.findMoneyByFIdAndPId(freightId, Integer.parseInt(rae.getProvince()));
                
                // 化妆品临时邮费方案
                if (specialPostageSellerMap.keySet().contains(oce.getSellerId() + ""))
                {
                    freightMoney = Integer.valueOf(specialPostageSellerMap.get(oce.getSellerId() + ""));
                }
                
                allFreightMoney += freightMoney;
                OrderEntity oe = new OrderEntity();
                oe.setOrderSellerId(orderSellerId);
                oe.setNumber(CommonUtil.generateOrderNumber());
                oe.setSameBatchNumber(sameBatchNumber);
                oe.setBatchNumber(batchNumber);
                oe.setAccountId(accountId);
                oe.setReceiveAddressId(orderReceiveAddId);
                try
                {
                    oe.setTotalPrice(decimalFormat.parse(decimalFormat.format(oce.getTotalPrice() + freightMoney)).floatValue());
                }
                catch (ParseException e)
                {
                    throw new ServiceException("ParseException error ", e);
                }
                oe.setCreateTime(System.currentTimeMillis() + "");
                oe.setFreightMoney((short)freightMoney);
                oe.setSellerId(oce.getSellerId());
                oe.setPayChannel(Byte.parseByte(paytype));
                // zhangld 去掉判断 isQqbs
                oe.setAppChannel(CommonEnum.ORDER_CHANNEL_STATUS.QQBS.getValue());
                oe.setAccountCouponId(couponId);
                if (oschannel == null)
                    oe.setSourceChannelId(0);
                else
                    oe.setSourceChannelId(oschannel.getId());
                
                if (oe.getTotalPrice() < couponPrices[index]) // 优惠券优惠大于订单金额
                {
                    couponTotalPrice += oe.getTotalPrice();
                    oe.setCouponPrice(oe.getTotalPrice());
                    oe.setAccountPoint(0);
                    oe.setRealPrice(0);
                    if (couponPrices.length - 1 >= index + 1)
                    {
                        couponPrices[index + 1] += couponPrices[index] - oe.getTotalPrice();
                        usedPointPrices[index + 1] += usedPointPrices[index];
                    }
                }
                else if (oe.getTotalPrice() - couponPrices[index] < usedPointPrices[index]) // 用户使用积分大于订单金额减去优惠券优惠
                {
                    couponTotalPrice += couponPrices[index];
                    usedPointTotal += Math.round((oe.getTotalPrice() - couponPrices[index]) * 100);
                    oe.setCouponPrice(couponPrices[index]);
                    oe.setAccountPoint(Math.round((oe.getTotalPrice() - couponPrices[index]) * 100));
                    oe.setRealPrice(0);
                    if (couponPrices.length - 1 >= index + 1)
                    {
                        usedPointPrices[index + 1] += usedPointPrices[index] + couponPrices[index] - oe.getTotalPrice();
                    }
                }
                else
                // 订单金额大于用户使用积分和优惠券优惠
                {
                    couponTotalPrice += couponPrices[index];
                    usedPointTotal += Math.round(usedPointPrices[index] * 100);
                    oe.setCouponPrice(couponPrices[index]);
                    oe.setAccountPoint(Math.round(usedPointPrices[index] * 100));
                    oe.setRealPrice(oe.getTotalPrice() - couponPrices[index] - usedPointPrices[index]);
                    realTotalPrice += oe.getRealPrice();
                }
                if (oe.getRealPrice() > CommonProperties.orderLowestCheckMoney)
                {
                    oe.setCheckStatus(Byte.valueOf(OrderRelationEnum.CHECK_STATUS_ENUM.CHECK_PASS.getCode()));
                }
                else
                {
                    oe.setCheckStatus(Byte.valueOf(OrderRelationEnum.CHECK_STATUS_ENUM.WAIT_FOR_CHECK.getCode()));
                }
                int orderId = odi.addOrder(oe);
                if (orderId == 0)
                {
                    throw new ServiceException("addOrder失败,accountId:" + accountId + ",orderConfirmId:" + oce.getId());
                }
                orderIds.add(orderId);
                
                int _orderValidMillis = orderValidMillis;
                if (accountId == Integer.parseInt(YggWebProperties.getInstance().getProperties("admin_account_id")))
                {
                    // 系统订单维持5天
                    _orderValidMillis = adminAccountIdOrderValidMillis;
                }
                if (odi.addLockTime(orderId, System.currentTimeMillis() + _orderValidMillis) == 0)
                {
                    throw new ServiceException("addLockTime失败,orderId:" + orderId);
                }
                
                oce.setIsValid(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()));
                if (oci.updateOrderConfirm(oce) == 0)
                {
                    throw new ServiceException("updateOrderConfirm失败,orderId:" + orderId + ",orderConfirmId:" + oce.getId());
                }
                
                List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
                for (OrderConfirmProductEntity ocpe : ocpes)
                {
                    ProductCommonEntity pce = pcommondi.findProductCommonInfoById(ocpe.getProductId());
                    currTotalPrice += pce.getSalesPrice() * ocpe.getProductCount();
                    
                    if (!handleProductId.contains(ocpe.getProductId()))
                    {
                        ShoppingCartEntity sce = scdi.findNormalCartByPIdAndAId(ocpe.getProductId(), accountId);
                        sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.GENERATE_ORDER.getValue());
                        if (scdi.updateShoppingCart(sce) == 0)
                        {
                            throw new ServiceException("updateShoppingCart无匹配,productId:" + ocpe.getProductId() + ",accountId:" + accountId);
                        }
                        
                        if (scdi.deleteLockCountByPIdAndAId(ocpe.getProductId(), accountId) == 0)
                        {
                            throw new ServiceException("deleteLockCountByPIdAndAId无匹配,productId:" + ocpe.getProductId() + ",accountId:" + accountId);
                        }
                        
                        handleProductId.add(ocpe.getProductId());
                    }
                    
                    if (odi.addLockCount(ocpe.getProductId(), orderId, ocpe.getProductCount()) == 0)
                    {
                        throw new ServiceException("addLockCount失败,productId:" + ocpe.getProductId() + ",orderId:" + orderId);
                    }
                    
                    OrderProductEntity ope = new OrderProductEntity();
                    ope.setOrderId(orderId);
                    ope.setProductId(ocpe.getProductId());
                    ope.setProductCount(ocpe.getProductCount());
                    ope.setSmallImage(pce.getSmallImage());
                    ope.setMediumImage(pce.getMediumImage());
                    ope.setShortName(pce.getShortName());
                    ope.setSalesPrice(pce.getSalesPrice());
                    ope.setPartnerDistributionPrice(pce.getPartnerDistributionPrice());
                    ope.setCost(odi.findProductCostByProductId(ocpe.getProductId()));
                    if (odi.addOrderProduct(ope) == 0) // ocpe.getProductId(), orderId, ocpe.getProductCount()) == 0)
                    {
                        throw new ServiceException("addOrderProduct失败,productId:" + ocpe.getProductId() + ",orderId:" + orderId);
                    }
                }
                index++;
                // allFreightMoney += freightId == 0 ? 0 : provincedi.findMoneyByFIdAndPId(freightId,
                // Integer.parseInt(rae.getProvince()));
            }
            
            List<ShoppingCartEntity> normalSces = scdi.findAllNormalCartByAId(accountId);
            List<Integer> tempProductIds = new ArrayList<>();
            for (ShoppingCartEntity sce : normalSces)
            {
                tempProductIds.add(sce.getProductId());
            }
            
            Collections.sort(orderConfirmProductIds);
            Collections.sort(tempProductIds);
            
            if (orderConfirmProductIds.equals(tempProductIds) && scdi.updateLockTime(accountId, System.currentTimeMillis()) == 0)
            {
                throw new ServiceException("updateLockTime无匹配,accountId:" + accountId);
            }
        }
        // 结算时登录确认订单
        else if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.TEMP_MERGER.getValue()))
        {
            // 临时用户不存在
            if (!tadi.idIsExist(tempAccountId))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CARTTOEKN_NOT_EXIST.getErrorCode());
                return result.toString();
            }
            
            String tempValidTimeStr = tscdi.findValidTimeByAid(tempAccountId);
            if (tempValidTimeStr != null && !tempValidTimeStr.equals("")) // 存在临时购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().after(validTime)) // 临时购物车锁定已过期
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorCode());
                    result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorMessage());
                    return result.toString();
                }
            }
            else
            // 不存在临时购物车锁定时间 信息
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorCode());
                result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CART_IS_EXPIRED.getErrorMessage());
                return result.toString();
            }
            
            List<OrderConfirmEntity> oces = oci.findConfirmByNumberAndTId(confirmId, tempAccountId);
            if (oces.size() == 0)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CONFIRMID_NOT_EXIST.getErrorCode());
                result.addProperty("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.CONFIRMID_NOT_EXIST.getErrorMessage());
                return result.toString();
            }
            
            // List<Integer> productIds = new ArrayList<Integer>();
            // for (OrderConfirmEntity oce : oces)
            // {
            // List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
            // for (OrderConfirmProductEntity ocpe : ocpes)
            // {
            // productIds.add(ocpe.getProductId());
            // }
            // }
            // Collections.sort(productIds);
            
            // 根据订单数量平均分配用户使用积分和优惠券优惠
            float[] usedPointPrices = new float[oces.size()];
            if (usedPoint != 0)
            {
                float average = usedPoint / oces.size() / 100f;
                for (int i = 0; i < usedPointPrices.length; i++)
                {
                    usedPointPrices[i] = average;
                }
                if (usedPoint % oces.size() != 0)
                {
                    usedPointPrices[oces.size() - 1] += (usedPoint % oces.size()) / 100f;
                }
            }
            
            float[] couponPrices = new float[oces.size()];
            if (cae != null)
            {
                if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.COMMON.getValue())
                {
                    float average = CommonUtil.transformFloat(reducePrice / oces.size());
                    for (int i = 0; i < couponPrices.length; i++)
                    {
                        couponPrices[i] = average;
                    }
                    if (((reducePrice * 100) % oces.size()) / 100 != 0)
                    {
                        couponPrices[oces.size() - 1] = CommonUtil.transformFloat(reducePrice - CommonUtil.transformFloat(average * (oces.size() - 1)));
                    }
                }
                else if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.PRODUCT.getValue())
                {
                    int productTotalCount = ocpi.findProductSumByConfirmIdsAndProductId(confirmIds, cde.getScopeId());
                    float average = CommonUtil.transformFloat(reducePrice / productTotalCount);
                    boolean isHasBalance = false;
                    if (((reducePrice * 100) % productTotalCount) / 100 != 0)
                    {
                        isHasBalance = true;
                    }
                    int index = 0;
                    for (OrderConfirmEntity oce : oces)
                    {
                        List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
                        for (OrderConfirmProductEntity ocpe : ocpes)
                        {
                            if (ocpe.getProductId() == cde.getScopeId())
                            {
                                couponPrices[index] = ocpe.getProductCount() * average;
                                if (isHasBalance && oces.size() != 1)
                                {
                                    couponPrices[index] =
                                        CommonUtil.transformFloat(reducePrice - CommonUtil.transformFloat(average * (productTotalCount - ocpe.getProductCount())));
                                    isHasBalance = false;
                                }
                            }
                        }
                        index++;
                    }
                }
            }
            
            int index = 0;
            String batchNumber = CommonUtil.generateOrderNumber();
            String sameBatchNumber = CommonUtil.generateOrderNumber(); // 真正 订单批次号
            List<Integer> handleProductId = new ArrayList<Integer>();
            // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
            // @SuppressWarnings("unused")
            // List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds);
            OrderSourceChannel oschannel = this.odi.findOrderSourceChannelByMark(osc);
            /*
             * if(oschannel == null) { oschannel = new OrderSourceChannel(); oschannel.setMark(osc);
             * if(odi.addOrderSourceChannel(oschannel) == 0 ) { throw new
             * ServiceException("addOrderSourceChannel失败,osc:" + osc ); } }
             */
            // 新增订单，新增订单锁定时间，修改订单确认状态，修改临时购物车状态，删除临时购物车锁定数量，新增订单锁定数量，新增订单商品，更新临时购物车锁定时间至当前时间
            // 化妆品临时邮费方案
            for (OrderConfirmEntity oce : oces)
            {
                // 结算时登录确认订单 modify by zhangld start 2016/3/16
                SellerEntity orderSeller = sdi.findBrandInfoById(oce.getSellerId());
                sdi.insertOrderSellerInfo(orderSeller);
                int orderSellerId = orderSeller.getId();
                if (orderSellerId == 0)
                {
                    throw new ServiceException("插入订单商家失败," + "orderConfirmId:" + oce.getId());
                }
                OrderReceiveAddressEntity orae = new OrderReceiveAddressEntity();
                if (orderSeller != null && orderSeller.getSellerType() == (byte)1)
                {
                    orae.setIdCard("");
                }
                else
                {
                    orae.setIdCard(rae.getIdCard());
                }
                // modify by zhangld end 2016/3/16
                orae.setCity(rae.getCity());
                orae.setDetailAddress(rae.getDetailAddress());
                orae.setDistrict(rae.getDistrict());
                orae.setFullName(rae.getFullName());
                orae.setMobileNumber(rae.getMobileNumber());
                orae.setProvince(rae.getProvince());
                int orderReceiveAddId = orderReceiveAddressDao.addAddress(orae);
                if (orderReceiveAddId == 0)
                {
                    log.error("新增订单收货地址失败");
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    return result.toString();
                }
                orderReceiveAddId = orae.getId();
                freightId = oce.getFreightTemplateId();
                int freightMoney = freightId == 0 ? 0 : provincedi.findMoneyByFIdAndPId(freightId, Integer.parseInt(rae.getProvince()));
                
                // 化妆品临时邮费方案
                if (specialPostageSellerMap.keySet().contains(oce.getSellerId() + ""))
                {
                    freightMoney = Integer.valueOf(specialPostageSellerMap.get(oce.getSellerId() + ""));
                }
                
                allFreightMoney += freightMoney;
                OrderEntity oe = new OrderEntity();
                oe.setOrderSellerId(orderSellerId);
                oe.setNumber(CommonUtil.generateOrderNumber());
                oe.setSameBatchNumber(sameBatchNumber);
                oe.setBatchNumber(batchNumber);
                oe.setAccountId(accountId);
                oe.setReceiveAddressId(orderReceiveAddId);
                // oe.setTotalPrice(oce.getTotalPrice() + (float)freightMoney);
                try
                {
                    oe.setTotalPrice(decimalFormat.parse(decimalFormat.format(oce.getTotalPrice() + freightMoney)).floatValue());
                }
                catch (ParseException e)
                {
                    throw new ServiceException("ParseException error ", e);
                }
                oe.setCreateTime(System.currentTimeMillis() + "");
                oe.setFreightMoney((short)freightMoney);
                oe.setSellerId(oce.getSellerId());
                oe.setPayChannel(Byte.parseByte(paytype));
                // zhangld 去掉判断 isQqbs
                oe.setAppChannel(CommonEnum.ORDER_CHANNEL_STATUS.QQBS.getValue());
                oe.setAccountCouponId(couponId);
                
                if (oschannel == null)
                    oe.setSourceChannelId(0);
                else
                    oe.setSourceChannelId(oschannel.getId());
                
                if (oe.getTotalPrice() < couponPrices[index]) // 优惠券优惠大于订单金额
                {
                    couponTotalPrice += oe.getTotalPrice();
                    oe.setCouponPrice(oe.getTotalPrice());
                    oe.setAccountPoint(0);
                    oe.setRealPrice(0);
                    if (couponPrices.length - 1 >= index + 1)
                    {
                        couponPrices[index + 1] += couponPrices[index] - oe.getTotalPrice();
                        usedPointPrices[index + 1] += usedPointPrices[index];
                    }
                }
                else if (oe.getTotalPrice() - couponPrices[index] < usedPointPrices[index]) // 用户使用积分大于订单金额减去优惠券优惠
                {
                    couponTotalPrice += couponPrices[index];
                    usedPointTotal += Math.round((oe.getTotalPrice() - couponPrices[index]) * 100);
                    oe.setCouponPrice(couponPrices[index]);
                    oe.setAccountPoint(Math.round((oe.getTotalPrice() - couponPrices[index]) * 100));
                    oe.setRealPrice(0);
                    if (couponPrices.length - 1 >= index + 1)
                    {
                        usedPointPrices[index + 1] += usedPointPrices[index] + couponPrices[index] - oe.getTotalPrice();
                    }
                }
                else
                // 订单金额大于用户使用积分和优惠券优惠
                {
                    couponTotalPrice += couponPrices[index];
                    usedPointTotal += Math.round(usedPointPrices[index] * 100);
                    oe.setCouponPrice(couponPrices[index]);
                    oe.setAccountPoint(Math.round(usedPointPrices[index] * 100));
                    oe.setRealPrice(oe.getTotalPrice() - couponPrices[index] - usedPointPrices[index]);
                    realTotalPrice += oe.getRealPrice();
                }
                if (oe.getRealPrice() > CommonProperties.orderLowestCheckMoney)
                {
                    oe.setCheckStatus(Byte.valueOf(OrderRelationEnum.CHECK_STATUS_ENUM.CHECK_PASS.getCode()));
                }
                else
                {
                    oe.setCheckStatus(Byte.valueOf(OrderRelationEnum.CHECK_STATUS_ENUM.WAIT_FOR_CHECK.getCode()));
                }
                int orderId = odi.addOrder(oe);
                if (orderId == 0)
                {
                    throw new ServiceException("addOrder失败,accountId:" + accountId + ",orderConfirmId:" + oce.getId());
                }
                orderIds.add(orderId);
                // 系统订单维持5天
                int _orderValidMillis = orderValidMillis;
                if (accountId == Integer.parseInt(YggWebProperties.getInstance().getProperties("admin_account_id")))
                {
                    _orderValidMillis = adminAccountIdOrderValidMillis;
                }
                if (odi.addLockTime(orderId, System.currentTimeMillis() + _orderValidMillis) == 0)
                {
                    throw new ServiceException("addLockTime失败,orderId:" + orderId);
                }
                
                oce.setIsValid(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()));
                if (oci.updateOrderConfirm(oce) == 0)
                {
                    throw new ServiceException("updateOrderConfirm失败,orderId:" + orderId + ",orderConfirmId:" + oce.getId());
                }
                
                List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
                for (OrderConfirmProductEntity ocpe : ocpes)
                {
                    ProductCommonEntity pce = pcommondi.findProductCommonInfoById(ocpe.getProductId());
                    currTotalPrice += pce.getSalesPrice() * ocpe.getProductCount();
                    
                    if (!handleProductId.contains(ocpe.getProductId()))
                    {
                        TempShoppingCartEntity tsce = tscdi.findNormalCartByPIdAndAId(ocpe.getProductId(), tempAccountId);
                        tsce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.GENERATE_ORDER.getValue());
                        if (tscdi.updateShoppingCart(tsce) == 0)
                        {
                            throw new ServiceException("updateShoppingCart无匹配,productId:" + ocpe.getProductId() + ",tempAccountId:" + tempAccountId);
                        }
                        
                        if (tscdi.deleteLockCountByPIdAndAId(ocpe.getProductId(), tempAccountId) == 0)
                        {
                            throw new ServiceException("deleteLockCountByPIdAndAId无匹配,productId:" + ocpe.getProductId() + ",tempAccountId:" + tempAccountId);
                        }
                        
                        handleProductId.add(ocpe.getProductId());
                    }
                    
                    if (odi.addLockCount(ocpe.getProductId(), orderId, ocpe.getProductCount()) == 0)
                    {
                        throw new ServiceException("addLockCount失败,productId:" + ocpe.getProductId() + ",orderId:" + orderId);
                    }
                    
                    OrderProductEntity ope = new OrderProductEntity();
                    ope.setOrderId(orderId);
                    ope.setProductId(ocpe.getProductId());
                    ope.setProductCount(ocpe.getProductCount());
                    ope.setSmallImage(pce.getSmallImage());
                    ope.setMediumImage(pce.getMediumImage());
                    ope.setShortName(pce.getShortName());
                    ope.setSalesPrice(pce.getSalesPrice());
                    ope.setCost(odi.findProductCostByProductId(ocpe.getProductId()));
                    if (odi.addOrderProduct(ope) == 0) // ocpe.getProductId(), orderId, ocpe.getProductCount()) == 0)
                    {
                        throw new ServiceException("addOrderProduct失败,productId:" + ocpe.getProductId() + ",orderId:" + orderId);
                    }
                }
                index++;
            }
            
            List<TempShoppingCartEntity> normalSces = tscdi.findAllNormalCartByAId(accountId);
            List<Integer> tempProductIds = new ArrayList<>();
            for (TempShoppingCartEntity tsce : normalSces)
            {
                tempProductIds.add(tsce.getProductId());
            }
            
            Collections.sort(orderConfirmProductIds);
            Collections.sort(tempProductIds);
            
            if (orderConfirmProductIds.equals(tempProductIds) && tscdi.updateLockTime(tempAccountId, System.currentTimeMillis()) == 0)
            {
                throw new ServiceException("updateLockTime无匹配,tempAccountId:" + tempAccountId);
            }
            
        }
        
        // 处理订单使用的用户积分
        if (usedPoint > 0)
        {
            int availablePoint = ae.getAvailablePoint();
            AccountAvailablePointRecordEntity aapre = new AccountAvailablePointRecordEntity();
            aapre.setAccountId(accountId);
            availablePoint -= usedPoint;
            availablePoint = availablePoint > 0 ? availablePoint : 0;
            aapre.setOperatePoint(usedPoint);
            aapre.setTotalPoint(availablePoint);
            aapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.SHOPPING_USED.getValue());
            aapre.setArithmeticType((byte)2);
            if (aaprdi.addAvailablePointRecords(aapre) == 0)
            {
                log.error("addAvailablePointRecords失败,usedPoint：" + usedPoint + ",aapre:" + aapre);
                ServiceException se = new ServiceException("积分使用异常");
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorCode());
                se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorMessage());
                throw se;
            }
            
            ae.setAvailablePoint(availablePoint);
            if (adi.updateAccountById(ae) == 0)
            {
                log.error("购物抵现，updateAccountById更新失败,accountId：" + ae.getId());
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorCode());
                se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorMessage());
                throw se;
            }
        }
        
        // 处理订单使用的优惠券
        if (cae != null)
        {
            if (couponAccountDao.updateCouponAccount2Used(cae.getId()) == 0)
            {
                log.error("updateCouponAccount2Used更新失败,accountCouponId:" + cae.getId());
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorCode());
                se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorMessage());
                throw se;
            }
        }
        
        // TODO app2.6上线之后以下两行代码务必去掉！！！！
        radi.updateUnDefaultAddress(accountId);//将之前的所有收货地址设为非默认收货地址
        radi.updateDefaultAddress(addressId, accountId, 1);
        
        float allTotalPrice = currTotalPrice + allFreightMoney;
        try
        {
            allTotalPrice = decimalFormat.parse(decimalFormat.format(allTotalPrice)).floatValue();
        }
        catch (ParseException e)
        {
            throw new ServiceException("ParseException error ", e);
        }
        if (allTotalPrice != totalPrice)
        {
            ServiceException se = new ServiceException("订单总价不正确");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.TOTAL_PRICE_INVALID.getErrorCode());
            se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.TOTAL_PRICE_INVALID.getErrorMessage());
            throw se;
        }
        if (cde != null
            && ((cde.getType() == 1 && CommonUtil.transformFloat(currTotalPrice) < cde.getThreshold()) || CommonUtil.transformFloat(couponTotalPrice) > reducePrice + 0.5f))
        {
            ServiceException se = new ServiceException("优惠券处理出错");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorCode());
            se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_COUPON_INVALID.getErrorMessage());
            throw se;
        }
        if (CommonUtil.transformFloat(realTotalPrice) != couponPrice)
        {
            ServiceException se = new ServiceException("优惠后订单总价异常");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.REAL_PRICE_INVALID.getErrorCode());
            se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.REAL_PRICE_INVALID.getErrorMessage());
            throw se;
        }
        // 积分只能抵扣50%
        long canUsePoint = Math.round(Double.valueOf(totalPrice * 100 * 0.5));
        log.warn(usedPointTotal + "!=" + usedPoint + " or " + usedPointTotal + ">" + canUsePoint);
        if ((usedPointTotal != usedPoint) || (usedPointTotal > canUsePoint))
        {
            log.warn(usedPointTotal + "!=" + usedPoint + " or " + usedPointTotal + ">" + canUsePoint);
            ServiceException se = new ServiceException("积分使用异常");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            se.putMap("errorCode", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorCode());
            se.putMap("errorMessage", ErrorCodeEnum.ORDER_ADD_ERRORCODE.USED_POINT_INVALID.getErrorMessage());
            throw se;
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.add("orderIds", parser.parse(JSONUtils.toJson(orderIds, false)));
        return result.toString();
        
    }
    
    private Map<String, String> executeLoginConfirm(List<ConfirmOrderView> covs, ReceiveAddressEntity rae, List<ShoppingCartEntity> sces, int accountId, Set<String> tips)
        throws ServiceException
    {
        Map<String, String> result = new HashMap<>();
        int freightId = 0;
        int isBonded = 0;
        float allTotalPrice = 0.00f;
        Long orderConfirmNumber = CommonUtil.generateTransactionId();
        Map<Integer, List<ProductEntity>> relationSellerProduct = new HashMap<>();
        for (ShoppingCartEntity sce : sces)
        {
            int productId = sce.getProductId();
            ProductEntity pe = pdi.findProductPartById(productId);
            int sellerId = pe.getSellerId();
            pe.setBrandId(sce.getProductCount()); // 特殊场景暂用该字段存储购物车商品数量
            if (relationSellerProduct.containsKey(sellerId))
            {
                relationSellerProduct.get(sellerId).add(pe);
            }
            else
            {
                List<ProductEntity> pes = new ArrayList<>();
                pes.add(pe);
                relationSellerProduct.put(sellerId, pes);
            }
        }
        
        // 特定邮费商家列表
        Map<String, Integer> specialPostageSellerMap = new HashMap<>();
        List<Map<String, Object>> specialPostageSellerIdList = sdi.findAllSellerPosgageBlacklistId();
        for (Map<String, Object> map : specialPostageSellerIdList)
        {
            specialPostageSellerMap.put(map.get("seller_id") + "", Integer.valueOf(map.get("freight_money") + ""));
        }
        
        for (Integer sellerId : relationSellerProduct.keySet())
        {
            List<OrderProductView> orderDetailList = new ArrayList<>();
            List<FreightView> fvs = new ArrayList<>();
            List<ProductEntity> pes = relationSellerProduct.get(sellerId);
            float totalPrice = 0.00f;
            List<Map<String, Object>> selectFreights = new ArrayList<>();
            List<ProductCommonEntity> pces = new ArrayList<>();
            int freight_money = 99;
            SellerEntity se = sdi.findBrandInfoById(sellerId);
            for (ProductEntity pe : pes)
            {
                OrderProductView opv = new OrderProductView();
                ProductCommonEntity pce = pcommondi.findProductCommonInfoById(pe.getId());
                if (se.getSellerType() != CommonEnum.SELLER_DELIVERY_TYPE.DOMESTIC.getValue()) // 保税区可能会拆分订单
                {
                    isBonded = 1;
                    for (int i = 0; i < pe.getBrandId(); i++)
                    {
                        pces.add(pce);
                    }
                }
                
                totalPrice += pce.getSalesPrice() * pe.getBrandId(); // 特殊场景暂用该字段存储购物车商品数量
                allTotalPrice += pce.getSalesPrice() * pe.getBrandId();
                int freightTemplateId = pe.getFreightTemplateId();
                
                // 提高用户体验，暂时使用折中方案，切换地址时可能选择的模板不是最优
                int addressId = rae != null ? Integer.parseInt(rae.getProvince()) : CommonConstant.DEFAULT_RECEIVE_PROVINCE_ID;
                List<Map<String, Object>> freights = provincedi.findFreightInfoByFreightId(freightTemplateId);
                for (Map<String, Object> freight : freights)
                {
                    if (Integer.parseInt(freight.get("province_id") + "") == addressId && Integer.parseInt(freight.get("freight_money") + "") < freight_money)
                    {
                        freight_money = Integer.parseInt(freight.get("freight_money") + "");
                        selectFreights = freights;
                        freightId = freightTemplateId;
                    }
                }
                
                opv.setProductId(pe.getId() + "");
                opv.setImage(pce.getSmallImage());
                opv.setShortName(pce.getShortName());
                opv.setSalesPrice(decimalFormat.format(pce.getSalesPrice())); // pce.getSalesPrice() + "");
                opv.setCount(pe.getBrandId() + ""); // 特殊场景暂用该字段存储购物车商品数量
                opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                opv.setStockCount("");
                opv.setRestriction("");
                orderDetailList.add(opv);
            }
            
            Map<Integer, List<String>> temp = new HashMap<>();
            for (Map<String, Object> freight : selectFreights)
            {
                if (temp.containsKey((Integer)freight.get("freight_money")))
                {
                    temp.get((Integer)freight.get("freight_money")).add(((Long)freight.get("province_id")).toString());
                }
                else
                {
                    List<String> provinceIds = new ArrayList<String>();
                    provinceIds.add(((Long)freight.get("province_id")).toString());
                    temp.put((Integer)freight.get("freight_money"), provinceIds);
                }
            }
            if (temp.keySet().size() == 0) // 没有可用的运费模板则默认包邮
            {
                fvs = getFreeShippingFreight();
                freightId = 0;
            }
            else
            {
                for (Integer freightMoney : temp.keySet())
                {
                    FreightView fv = new FreightView();
                    fv.setLogisticsMoney(freightMoney + "");
                    fv.setProvinceIds(temp.get(freightMoney));
                    fvs.add(fv);
                }
            }
            
            if (se.getSellerType() != CommonEnum.SELLER_DELIVERY_TYPE.DOMESTIC.getValue())
            {
                tips.add(CommonConstant.BONDED_IS_TIP);
            }
            
            // 是保税区发货时金额大于500需要拆分订单标记为隔天发货，避免交税
            if (!specialPostageSellerMap.keySet().contains(sellerId + "") && se.getSellerType() != CommonEnum.SELLER_DELIVERY_TYPE.DOMESTIC.getValue()
                && totalPrice > CommonConstant.BONDED_MONEY_SPLIT_THRESHOLD)
            {
                if (pces.size() > 1)
                {
                    tips.add(CommonConstant.BONDED_SPLIT_TIP);
                }
                float onePrice = 0.00f;
                int currentProductCount = 0;
                int lastProductId = 0;
                int orderConfirmCount = 0;
                Collections.sort(pces);
                // 按价格从小到大组装订单 [1,1,1,2,2,3,4,5] 1,3 2,1
                List<OrderProductView> bondedOrderDetailList = new ArrayList<>();
                // 化妆品临时邮费方案
                // boolean flag = true;
                for (int i = 0; i < pces.size(); i++)
                {
                    ProductCommonEntity pce = pces.get(i);
                    lastProductId = pce.getProductId();
                    currentProductCount++;
                    onePrice += pce.getSalesPrice();
                    // 添加商品
                    if (i == pces.size() - 1 || lastProductId != pces.get(i + 1).getProductId())
                    {
                        OrderProductView opv = new OrderProductView();
                        opv.setProductId(pce.getProductId() + "");
                        opv.setImage(pce.getSmallImage());
                        opv.setShortName(pce.getShortName());
                        opv.setSalesPrice(decimalFormat.format(pce.getSalesPrice())); // pce.getSalesPrice() + "");
                        opv.setCount(currentProductCount + "");
                        opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                        opv.setStockCount("");
                        opv.setRestriction("");
                        bondedOrderDetailList.add(opv);
                        currentProductCount = 0;
                    }
                    
                    // 生成一个订单确认
                    if (i == pces.size() - 1 || onePrice + pces.get(i + 1).getSalesPrice() > CommonConstant.BONDED_MONEY_SPLIT_THRESHOLD)
                    {
                        if (currentProductCount != 0)
                        {
                            OrderProductView opv = new OrderProductView();
                            opv.setProductId(pce.getProductId() + "");
                            opv.setImage(pce.getSmallImage());
                            opv.setShortName(pce.getShortName());
                            opv.setSalesPrice(decimalFormat.format(pce.getSalesPrice())); // pce.getSalesPrice() + "");
                            opv.setCount(currentProductCount + "");
                            opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                            opv.setStockCount("");
                            opv.setRestriction("");
                            bondedOrderDetailList.add(opv);
                            currentProductCount = 0;
                        }
                        
                        ConfirmOrderView cov = new ConfirmOrderView();
                        cov.setOrderDetailList(bondedOrderDetailList);
                        cov.setSellerName(se.getSellerName());
                        cov.setSendAddress(se.getSendAddress());
                        if (orderConfirmCount >= 1)
                        {
                            cov.setSendAddress(se.getSendAddress() + "隔天");
                        }
                        cov.setTotalPrice(decimalFormat.format(onePrice)); // onePrice + "");
                        covs.add(cov);
                        
                        // 添加到订单确认表
                        OrderConfirmEntity oce = new OrderConfirmEntity();
                        oce.setNumber(orderConfirmNumber);
                        oce.setAccountId(accountId);
                        oce.setTempAccountId(0);
                        oce.setFreightTemplateId(0);
                        oce.setTotalPrice(onePrice);
                        oce.setSellerId(sellerId);
                        int orderConfirmId = oci.addOrderConfirm(oce);
                        if (orderConfirmId > 0)
                        {
                            orderConfirmId = oce.getId();
                            for (OrderProductView opv : bondedOrderDetailList)
                            {
                                if (ocpi.addOrderConfirmProduct(orderConfirmId, Integer.parseInt(opv.getProductId()), Short.parseShort(opv.getCount())) == 0)
                                {
                                    throw new ServiceException("addOrderConfirmProduct失败,orderConfirmId:" + orderConfirmId + ",productId:" + opv.getProductId());
                                }
                            }
                        }
                        else
                        {
                            throw new ServiceException("addOrderConfirm失败,orderConfirmNumber:" + orderConfirmNumber + ",accountId:" + accountId);
                        }
                        bondedOrderDetailList = new ArrayList<>();
                        orderConfirmCount++;
                        onePrice = 0;
                    }
                }
            }
            else
            // 不需拆分订单
            {
                ConfirmOrderView cov = new ConfirmOrderView();
                cov.setFreights(fvs);
                
                // 化妆品临时邮费方案
                if (specialPostageSellerMap.keySet().contains(sellerId + ""))
                {
                    Integer postageMoney = specialPostageSellerMap.get(sellerId + "");
                    if (postageMoney <= 0)
                    {
                        cov.setTempFlag("1");
                        cov.setFreights(freeShippingFreight);
                    }
                    else
                    {
                        List<FreightView> fvs1 = new ArrayList<>();
                        FreightView fv = new FreightView();
                        fv.setLogisticsMoney(postageMoney + "");
                        List<ProvinceEntity> pes1;
                        try
                        {
                            pes1 = provincedi.findAllProvinceInfo();
                            List<String> provinces = new ArrayList<String>();
                            for (ProvinceEntity pe : pes1)
                            {
                                provinces.add(pe.getProvinceId() + "");
                            }
                            fv.setProvinceIds(provinces);
                            fvs1.add(fv);
                        }
                        catch (DaoException e)
                        {
                            log.error(e);
                        }
                        cov.setFreights(fvs1);
                        cov.setTempFlag("1");
                    }
                }
                
                cov.setOrderDetailList(orderDetailList);
                cov.setSellerName(se.getSellerName());
                cov.setSendAddress(se.getSendAddress());
                cov.setTotalPrice(decimalFormat.format(totalPrice)); // totalPrice + "");
                covs.add(cov);
                
                // 添加到订单确认表
                OrderConfirmEntity oce = new OrderConfirmEntity();
                oce.setNumber(orderConfirmNumber);
                oce.setAccountId(accountId);
                oce.setTempAccountId(0);
                oce.setFreightTemplateId(freightId);
                oce.setTotalPrice(totalPrice);
                oce.setSellerId(sellerId);
                int orderConfirmId = oci.addOrderConfirm(oce);
                if (orderConfirmId > 0)
                {
                    orderConfirmId = oce.getId();
                    for (OrderProductView opv : orderDetailList)
                    {
                        if (ocpi.addOrderConfirmProduct(orderConfirmId, Integer.parseInt(opv.getProductId()), Short.parseShort(opv.getCount())) == 0)
                        {
                            throw new ServiceException("addOrderConfirmProduct失败,orderConfirmId:" + orderConfirmId + ",productId:" + opv.getProductId());
                        }
                    }
                }
                else
                {
                    throw new ServiceException("addOrderConfirm失败,orderConfirmNumber:" + orderConfirmNumber + ",accountId:" + accountId);
                }
            }
        }
        result.put("isBonded", isBonded + "");
        result.put("allTotalPrice", decimalFormat.format(allTotalPrice)); // allTotalPrice + "");
        result.put("confirmId", orderConfirmNumber + "");
        return result;
    }
    
    private String generateUnSupportProductInfos(List<Integer> productIds, ReceiveAddressEntity rae)
        throws DaoException
    {
        String tips = null;
        if (rae != null)
        {
            for (Integer productId : productIds)
            {
                Object peCache = cacheService.getCache(CacheConstant.RES_PRODUCTBASE_PE_KEY + productId);
                ProductEntity pe = null;
                if (peCache != null)
                {
                    pe = (ProductEntity)peCache;
                }
                else
                {
                    pe = pdi.findProductInfoById(productId);
                    cacheService.addCache(CacheConstant.RES_PRODUCTBASE_PE_KEY + productId, pe, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                Object basePeCache = cacheService.getCache(CacheConstant.RES_PRODUCTBASE_BASEPE_KEY + pe.getProductBaseId());
                ProductBaseEntity basePe = null;
                if (basePeCache != null)
                {
                    basePe = (ProductBaseEntity)basePeCache;
                }
                else
                {
                    basePe = pdi.findProductBaseById(pe.getProductBaseId());
                    cacheService.addCache(CacheConstant.RES_PRODUCTBASE_BASEPE_KEY + pe.getProductBaseId(), basePe, CacheConstant.CACHE_MINUTE_1 / 2);
                }
                
                boolean isSupportArea = false;
                List<Map<String, Object>> deliverAreaInfos = pdi.findDeliverAreaInfosByBpId(pe.getProductBaseId());
                if (basePe.getDeliverAreaType() == ProductEnum.DELIVER_AREA_TYPE.SUPPORT.getCode()) // 支持地区
                {
                    if (deliverAreaInfos.size() == 0) // 历史遗留商品没有关联支持配送的省市区
                    {
                        isSupportArea = true;
                    }
                    else
                    {
                        isSupportArea = false;
                        for (Map<String, Object> deliverAreaInfo : deliverAreaInfos)
                        {
                            // 1表示全部
                            String provinceCode = deliverAreaInfo.get("province_code").toString();
                            String cityCode = deliverAreaInfo.get("city_code").toString();
                            String districtCode = deliverAreaInfo.get("district_code").toString();
                            if (provinceCode.equals("1")) // 支持全部省市区
                            {
                                isSupportArea = true;
                                break;
                            }
                            else if (provinceCode.equals(rae.getProvince())) // 省匹配
                            {
                                if (cityCode.equals("1")) // 支持全部市区
                                {
                                    isSupportArea = true;
                                    break;
                                }
                                else if (cityCode.equals(rae.getCity())) // 市匹配
                                {
                                    if (districtCode.equals("1")) // 支持全部区
                                    {
                                        isSupportArea = true;
                                        break;
                                    }
                                    else if (districtCode.equals(rae.getDistrict())) // 区匹配
                                    {
                                        isSupportArea = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (basePe.getDeliverAreaType() == ProductEnum.DELIVER_AREA_TYPE.UNSUPPORT.getCode()) // 不支持地区
                {
                    isSupportArea = true;
                    for (Map<String, Object> deliverAreaInfo : deliverAreaInfos)
                    {
                        // 1表示全部
                        String provinceCode = deliverAreaInfo.get("province_code").toString();
                        String cityCode = deliverAreaInfo.get("city_code").toString();
                        String districtCode = deliverAreaInfo.get("district_code").toString();
                        if (provinceCode.equals(rae.getProvince())) // 省匹配
                        {
                            if (cityCode.equals("1")) // 不支持全部市区
                            {
                                isSupportArea = false;
                                break;
                            }
                            else if (cityCode.equals(rae.getCity())) // 市匹配
                            {
                                if (districtCode.equals("1")) // 不支持全部区
                                {
                                    isSupportArea = false;
                                    break;
                                }
                                else if (districtCode.equals(rae.getDistrict())) // 区匹配
                                {
                                    isSupportArea = false;
                                    break;
                                }
                            }
                        }
                    }
                }
                
                if (!isSupportArea)
                {
                    tips = pe.getShortName() + " 暂不支持配送至该收货地址，" + basePe.getDeliverAreaDesc();
                    break;
                }
            }
        }
        return tips;
    }
    
    private Map<String, String> executeNotLoginConfirm(List<ConfirmOrderView> covs, ReceiveAddressEntity rae, List<TempShoppingCartEntity> tsces, int accountId, int tempAccountId,
        Set<String> tips)
        throws ServiceException
    {
        Map<String, String> result = new HashMap<>();
        int freightId = 0;
        int isBonded = 0;
        float allTotalPrice = 0.00f;
        Long orderConfirmNumber = CommonUtil.generateTransactionId();
        Map<Integer, List<ProductEntity>> relationSellerProduct = new HashMap<>();
        for (TempShoppingCartEntity tsce : tsces)
        {
            int productId = tsce.getProductId();
            ProductEntity pe = pdi.findProductPartById(productId);
            int sellerId = pe.getSellerId();
            pe.setBrandId(tsce.getProductCount()); // 特殊场景暂用该字段存储购物车商品数量
            if (relationSellerProduct.containsKey(sellerId))
            {
                relationSellerProduct.get(sellerId).add(pe);
            }
            else
            {
                List<ProductEntity> pes = new ArrayList<ProductEntity>();
                pes.add(pe);
                relationSellerProduct.put(sellerId, pes);
            }
        }
        
        // 特定邮费商家列表
        Map<String, Integer> specialPostageSellerMap = new HashMap<>();
        List<Map<String, Object>> specialPostageSellerIdList = sdi.findAllSellerPosgageBlacklistId();
        for (Map<String, Object> map : specialPostageSellerIdList)
        {
            specialPostageSellerMap.put(map.get("seller_id") + "", Integer.valueOf(map.get("freight_money") + ""));
        }
        
        for (Integer sellerId : relationSellerProduct.keySet())
        {
            List<OrderProductView> orderDetailList = new ArrayList<OrderProductView>();
            List<FreightView> fvs = new ArrayList<FreightView>();
            List<ProductEntity> pes = relationSellerProduct.get(sellerId);
            float totalPrice = 0.00f;
            List<Map<String, Object>> selectFreights = new ArrayList<Map<String, Object>>();
            List<ProductCommonEntity> pces = new ArrayList<ProductCommonEntity>();
            int freight_money = 99;
            SellerEntity se = sdi.findBrandInfoById(sellerId);
            for (ProductEntity pe : pes)
            {
                OrderProductView opv = new OrderProductView();
                ProductCommonEntity pce = pcommondi.findProductCommonInfoById(pe.getId());
                if (se.getSellerType() != CommonEnum.SELLER_DELIVERY_TYPE.DOMESTIC.getValue()) // 保税区可能会拆分订单
                {
                    isBonded = 1;
                    for (int i = 0; i < pe.getBrandId(); i++)
                    {
                        pces.add(pce);
                    }
                }
                
                totalPrice += pce.getSalesPrice() * pe.getBrandId(); // 特殊场景暂用该字段存储购物车商品数量
                allTotalPrice += pce.getSalesPrice() * pe.getBrandId();
                int freightTemplateId = pe.getFreightTemplateId();
                
                // 提高用户体验，暂时使用折中方案，切换地址时可能选择的模板不是最优
                int addressId = rae != null ? Integer.parseInt(rae.getProvince()) : CommonConstant.DEFAULT_RECEIVE_PROVINCE_ID;
                List<Map<String, Object>> freights = provincedi.findFreightInfoByFreightId(freightTemplateId);
                for (Map<String, Object> freight : freights)
                {
                    if (Integer.parseInt(freight.get("province_id") + "") == addressId && Integer.parseInt(freight.get("freight_money") + "") < freight_money)
                    {
                        freight_money = Integer.parseInt(freight.get("freight_money") + "");
                        selectFreights = freights;
                        freightId = freightTemplateId;
                    }
                }
                
                opv.setProductId(pe.getId() + "");
                opv.setImage(pce.getSmallImage());
                opv.setShortName(pce.getShortName());
                opv.setSalesPrice(decimalFormat.format(pce.getSalesPrice())); // pce.getSalesPrice() + "");
                opv.setCount(pe.getBrandId() + ""); // 特殊场景暂用该字段存储购物车商品数量
                opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                opv.setStockCount("");
                opv.setRestriction("");
                orderDetailList.add(opv);
            }
            
            Map<Integer, List<String>> temp = new HashMap<Integer, List<String>>();
            for (Map<String, Object> freight : selectFreights)
            {
                if (temp.containsKey(Integer.parseInt(freight.get("freight_money") + "")))
                {
                    temp.get(Integer.parseInt(freight.get("freight_money") + "")).add(((Long)freight.get("province_id")).toString());
                }
                else
                {
                    List<String> provinceIds = new ArrayList<String>();
                    provinceIds.add(((Long)freight.get("province_id")).toString());
                    temp.put((Integer)freight.get("freight_money"), provinceIds);
                }
            }
            if (temp.keySet().size() == 0) // 没有可用的运费模板则默认包邮
            {
                fvs = getFreeShippingFreight();
                freightId = 0;
            }
            else
            {
                for (Integer freightMoney : temp.keySet())
                {
                    FreightView fv = new FreightView();
                    fv.setLogisticsMoney(freightMoney + "");
                    fv.setProvinceIds(temp.get(freightMoney));
                    fvs.add(fv);
                }
            }
            
            if (se.getSellerType() != CommonEnum.SELLER_DELIVERY_TYPE.DOMESTIC.getValue())
            {
                tips.add(CommonConstant.BONDED_IS_TIP);
            }
            
            // 是保税区发货时金额大于500需要拆分订单标记为隔天发货，避免交税
            if (!specialPostageSellerMap.keySet().contains(sellerId + "") && se.getSellerType() != CommonEnum.SELLER_DELIVERY_TYPE.DOMESTIC.getValue()
                && totalPrice > CommonConstant.BONDED_MONEY_SPLIT_THRESHOLD)
            {
                if (pces.size() > 1)
                {
                    tips.add(CommonConstant.BONDED_SPLIT_TIP);
                }
                float onePrice = 0.00f;
                int currentProductCount = 0;
                int lastProductId = 0;
                int orderConfirmCount = 0;
                Collections.sort(pces);
                // 按价格从小到大组装订单
                List<OrderProductView> bondedOrderDetailList = new ArrayList<OrderProductView>();
                // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
                // 化妆品临时邮费方案
                // boolean flag = true;
                for (int i = 0; i < pces.size(); i++)
                {
                    ProductCommonEntity pce = pces.get(i);
                    lastProductId = pce.getProductId();
                    currentProductCount++;
                    onePrice += pce.getSalesPrice();
                    // 添加商品
                    if (i == pces.size() - 1 || lastProductId != pces.get(i + 1).getProductId())
                    {
                        OrderProductView opv = new OrderProductView();
                        opv.setProductId(pce.getProductId() + "");
                        opv.setImage(pce.getSmallImage());
                        opv.setShortName(pce.getShortName());
                        opv.setSalesPrice(decimalFormat.format(pce.getSalesPrice())); // pce.getSalesPrice() + "");
                        opv.setCount(currentProductCount + "");
                        opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                        opv.setStockCount("");
                        opv.setRestriction("");
                        bondedOrderDetailList.add(opv);
                        currentProductCount = 0;
                    }
                    
                    // 生成一个订单确认
                    if (i == pces.size() - 1 || onePrice + pces.get(i + 1).getSalesPrice() > CommonConstant.BONDED_MONEY_SPLIT_THRESHOLD)
                    {
                        if (currentProductCount != 0)
                        {
                            OrderProductView opv = new OrderProductView();
                            opv.setProductId(pce.getProductId() + "");
                            opv.setImage(pce.getSmallImage());
                            opv.setShortName(pce.getShortName());
                            opv.setSalesPrice(decimalFormat.format(pce.getSalesPrice())); // pce.getSalesPrice() + "");
                            opv.setCount(currentProductCount + "");
                            opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                            opv.setStockCount("");
                            opv.setRestriction("");
                            bondedOrderDetailList.add(opv);
                            currentProductCount = 0;
                        }
                        
                        ConfirmOrderView cov = new ConfirmOrderView();
                        cov.setOrderDetailList(bondedOrderDetailList);
                        cov.setSellerName(se.getSellerName());
                        cov.setSendAddress(se.getSendAddress());
                        if (orderConfirmCount >= 1)
                        {
                            cov.setSendAddress(se.getSendAddress() + "隔天");
                        }
                        cov.setTotalPrice(decimalFormat.format(onePrice)); // onePrice + "");
                        covs.add(cov);
                        
                        // 添加到订单确认表
                        OrderConfirmEntity oce = new OrderConfirmEntity();
                        oce.setNumber(orderConfirmNumber);
                        oce.setAccountId(accountId);
                        oce.setTempAccountId(tempAccountId);
                        oce.setFreightTemplateId(0);
                        oce.setTotalPrice(onePrice);
                        oce.setSellerId(sellerId);
                        int orderConfirmId = oci.addOrderConfirm(oce);
                        if (orderConfirmId > 0)
                        {
                            orderConfirmId = oce.getId();
                            for (OrderProductView opv : bondedOrderDetailList)
                            {
                                if (ocpi.addOrderConfirmProduct(orderConfirmId, Integer.parseInt(opv.getProductId()), Short.parseShort(opv.getCount())) == 0)
                                {
                                    throw new ServiceException("addOrderConfirmProduct失败,orderConfirmId:" + orderConfirmId + ",productId:" + opv.getProductId());
                                }
                            }
                        }
                        else
                        {
                            throw new ServiceException("addOrderConfirm失败,orderConfirmNumber:" + orderConfirmNumber + ",accountId:" + accountId);
                        }
                        bondedOrderDetailList = new ArrayList<OrderProductView>();
                        orderConfirmCount++;
                        onePrice = 0;
                    }
                }
                // ConnectionManager.rollbackTransaction();
            }
            else
            // 不需拆分订单
            {
                ConfirmOrderView cov = new ConfirmOrderView();
                cov.setFreights(fvs);
                
                // 化妆品临时邮费方案
                if (specialPostageSellerMap.keySet().contains(sellerId + ""))
                {
                    Integer postageMoney = specialPostageSellerMap.get(sellerId + "");
                    if (postageMoney <= 0)
                    {
                        cov.setTempFlag("1");
                        cov.setFreights(freeShippingFreight);
                    }
                    else
                    {
                        List<FreightView> fvs1 = new ArrayList<>();
                        FreightView fv = new FreightView();
                        fv.setLogisticsMoney(postageMoney + "");
                        List<ProvinceEntity> pes1;
                        try
                        {
                            pes1 = provincedi.findAllProvinceInfo();
                            List<String> provinces = new ArrayList<String>();
                            for (ProvinceEntity pe : pes1)
                            {
                                provinces.add(pe.getProvinceId() + "");
                            }
                            fv.setProvinceIds(provinces);
                            fvs1.add(fv);
                        }
                        catch (DaoException e)
                        {
                            log.error(e);
                        }
                        cov.setFreights(fvs1);
                        cov.setTempFlag("1");
                    }
                }
                
                cov.setOrderDetailList(orderDetailList);
                cov.setSellerName(se.getSellerName());
                cov.setSendAddress(se.getSendAddress());
                cov.setTotalPrice(decimalFormat.format(totalPrice)); // totalPrice + "");
                covs.add(cov);
                
                // 添加到订单确认表
                OrderConfirmEntity oce = new OrderConfirmEntity();
                oce.setNumber(orderConfirmNumber);
                oce.setAccountId(accountId);
                oce.setTempAccountId(tempAccountId);
                oce.setFreightTemplateId(freightId);
                oce.setTotalPrice(totalPrice);
                oce.setSellerId(sellerId);
                // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
                int orderConfirmId = oci.addOrderConfirm(oce);
                if (orderConfirmId > 0)
                {
                    orderConfirmId = oce.getId();
                    for (OrderProductView opv : orderDetailList)
                    {
                        if (ocpi.addOrderConfirmProduct(orderConfirmId, Integer.parseInt(opv.getProductId()), Short.parseShort(opv.getCount())) == 0)
                        {
                            throw new ServiceException("addOrderConfirmProduct失败,orderConfirmId:" + orderConfirmId + ",productId:" + opv.getProductId());
                        }
                    }
                }
                else
                {
                    throw new ServiceException("addOrderConfirm失败,orderConfirmNumber:" + orderConfirmNumber + ",accountId:" + accountId);
                }
                // ConnectionManager.commitTransaction();
            }
        }
        result.put("isBonded", isBonded + "");
        result.put("allTotalPrice", decimalFormat.format(allTotalPrice)); // allTotalPrice + "");
        result.put("confirmId", orderConfirmNumber + "");
        return result;
    }
    
    @Override
    public String findValidTimeByAIDORTAID(String reqeustParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(reqeustParams);
        int accountId = param.get("accountId").getAsInt();
        int tempAccountId = param.get("tempAccountId").getAsInt();
        String type = param.get("type").getAsString();
        String endSecond = "-1";
        
        // 正常确认订单
        if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.NORMAL.getValue()))
        {
            String validTimeStr = scdi.findValidTimeByAid(accountId);
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().after(validTime)) // 购物车锁定已过期
                {
                    endSecond = "-1";
                }
                else
                // 购物车锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            else
            // 不存在购物车锁定时间 信息
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue());
                return result.toString();
            }
            
        }
        else if (type.equals(CommonEnum.ORDER_CONFIRM_TYPE.TEMP_MERGER.getValue()))
        {
            String tempValidTimeStr = tscdi.findValidTimeByAid(tempAccountId);
            if (tempValidTimeStr != null && !tempValidTimeStr.equals("")) // 存在临时购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().after(validTime)) // 临时购物车锁定已过期
                {
                    endSecond = "-1";
                }
                else
                // 临时购物车锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            else
            // 不存在临时购物车锁定时间 信息
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_CONFIRM_ERRORCODE.CART_IS_EXPIRED.getValue());
                return result.toString();
            }
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.addProperty("endSecond", endSecond);
        return result.toString();
    }
    
    @Override
    public String modify(String requestParams)
        throws Exception
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        byte status = param.get("status").getAsByte();
        int orderId = param.get("orderId").getAsInt();
        AccountEntity ae = adi.findAccountById(accountId);
        // 用户不存在
        if (ae == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_MODIFY_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        OrderEntity oe = odi.findOrderById(orderId);
        // 订单不存在
        if (oe == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_MODIFY_ERRORCODE.ORDERID_NOT_EXIST.getValue());
            return result.toString();
        }
        byte oldStatus = oe.getStatus();
        
        if (status == 1 && oldStatus == (byte)CommonEnum.ORDER_STATUS.NOT_PAY.getValue())
        {
            if (odi.updateOrderUserCancel(orderId, accountId) == 0)
            {
                log.error("updateOrderUserCancel失败,orderId：" + orderId + ",accountId:" + accountId);
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                throw se;
            }
            
            List<Integer> productIds = odi.findLockProductIdsByOId(orderId);
            List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds); // 一次锁定多个商品id
            Map<Integer, ProductCountEntity> pceMap = new HashMap<Integer, ProductCountEntity>();
            for (ProductCountEntity pce : pces)
            {
                pceMap.put(pce.getProductId(), pce);
            }
            List<Map<String, Object>> lockProductInfos = odi.findLockCountInfosByOId(orderId);
            for (Map<String, Object> map : lockProductInfos)
            {
                int productId = ((Long)map.get("product_id")).intValue();
                int productCount = (Integer)map.get("product_count");
                if (odi.deleteLockCount(productId, orderId) == 0)
                {
                    log.error("deleteLockCount没有匹配,orderId：" + orderId + ",productId：" + productId);
                    ServiceException se = new ServiceException();
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    throw se;
                }
                
                ProductCountEntity pce = pceMap.get(productId);
                pce.setLock(pce.getLock() - productCount);
                if (pcdi.updateCountInfo(pce) == 0)
                {
                    log.error("updateCountInfo没有匹配,orderId：" + orderId + ",productId：" + productId);
                    ServiceException se = new ServiceException();
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    throw se;
                }
            }
            if (odi.updateLockTime(orderId, System.currentTimeMillis()) == 0)
            {
                log.error("updateLockTime没有匹配,orderId：" + orderId);
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                throw se;
            }
            
            // 返还用户积分和优惠券
            long batchNumber = Long.valueOf(oe.getBatchNumber());
            long number = Long.valueOf(oe.getNumber());
            if (batchNumber != 0 && batchNumber != number)
            {
                int accountPoint = oe.getAccountPoint();
                int accountCouponId = oe.getAccountCouponId();
                
                // 返还积分
                if (accountPoint > 0)
                {
                    ae.setAvailablePoint(ae.getAvailablePoint() + accountPoint);
                    
                    AccountAvailablePointRecordEntity aapre = new AccountAvailablePointRecordEntity();
                    aapre.setAccountId(accountId);
                    aapre.setOperatePoint(accountPoint);
                    aapre.setTotalPoint(ae.getAvailablePoint());
                    aapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.SHOPPING_USED_CANCEL.getValue());
                    aapre.setArithmeticType((byte)1);
                    if (aaprdi.addAvailablePointRecords(aapre) == 0)
                    {
                        log.error("addAvailablePointRecords失败");
                        ServiceException se = new ServiceException("积分记录插入异常");
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                    
                    if (adi.updateAccountById(ae) == 0)
                    {
                        log.error("订单取消返还积分updateAccountById失败！accountId：" + ae.getId() + "，orderId: " + orderId);
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                    
                }
                
                // 返还优惠券
                if (accountCouponId > 0)
                {
                    if (odi.findOrderIdByStatusAndAccountCouponId(accountCouponId) == -1)
                    {
                        // 该优惠券没有被未取消的订单关联 可以返还给用户
                        couponAccountDao.updateCouponAccount2UnUsed(accountCouponId);
                    }
                }
            }
        }
        else if (status == 2 && oldStatus == (byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue())
        {
            if (odi.updateOrderSuccess(orderId, accountId) == 0)
            {
                log.error("updateOrderSuccess失败,orderId：" + orderId + ",accountId:" + accountId);
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                return result.toString();
            }
        }
        else if (status == 3) // 已发货中确认收货
        {
            if (odi.updateOrderSuccess(orderId, accountId) == 0)
            {
                log.error("updateOrderSuccess失败,orderId：" + orderId + ",accountId:" + accountId);
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                return result.toString();
            }
            
            // 计算订单下满足条件的金额 主要针对退款
            float availableRealPrice = oe.getRealPrice();
            List<Map<String, Object>> refunds = orderProductRefundDao.findRefundsByOrderId(oe.getId());
            if (refunds.size() > 0)
            {
                Map<Integer, Integer> refundMap = new HashMap<>();
                for (Map<String, Object> refund : refunds)
                {
                    int orderProductId = Integer.valueOf(refund.get("orderProductId") + "");
                    int count = Integer.valueOf(refund.get("count") + "");
                    refundMap.put(orderProductId, count);
                }
                
                float totalPrice = 0; // 订单商品总价
                float refundTotalPrice = 0; // 发生退款商品总价
                List<Map<String, Object>> ops = odi.findOrderProductInfosByOId(oe.getId());
                for (Map<String, Object> op : ops)
                {
                    int id = Integer.valueOf(op.get("id") + "");
                    int productCount = Integer.valueOf(op.get("product_count") + "");
                    float salePrice = Float.valueOf(op.get("salesPrice") + "");
                    if (refundMap.keySet().contains(id))
                    {
                        // 存在退款
                        int refundCount = refundMap.get(id);
                        refundTotalPrice += salePrice * refundCount;
                    }
                    totalPrice += productCount * salePrice;
                }
                availableRealPrice = (availableRealPrice * ((totalPrice - refundTotalPrice) / totalPrice));
            }
            
            // 计算会员等级信息
            if (availableRealPrice > 0)
            {
                // 计算等级并更新用户总交易成功金额 插入成功交易金额记录
                float totalSuccessPriceAndNowOrderPrice = ae.getTotalSuccessPrice() + availableRealPrice;
                int level = 0;
                if (totalSuccessPriceAndNowOrderPrice >= CommonEnum.LEVEL.V3.getLimitMoney())
                {
                    level = CommonEnum.LEVEL.V3.getCode();
                }
                else if (totalSuccessPriceAndNowOrderPrice >= CommonEnum.LEVEL.V2.getLimitMoney())
                {
                    level = CommonEnum.LEVEL.V2.getCode();
                }
                else if (totalSuccessPriceAndNowOrderPrice >= CommonEnum.LEVEL.V1.getLimitMoney())
                {
                    level = CommonEnum.LEVEL.V1.getCode();
                }
                else
                {
                    level = CommonEnum.LEVEL.V0.getCode();
                }
                
                Map<String, Object> para = new HashMap<>();
                para.put("id", ae.getId());
                para.put("level", level);
                para.put("totalSuccessPrice", totalSuccessPriceAndNowOrderPrice);
                if (adi.updateAccountInfoById(para) == 0)
                {
                    log.error("updateAccountInfoById失败");
                    ServiceException se = new ServiceException("更新用户会员等级信息异常");
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    throw se;
                }
                
                AccountSuccessOrderRecordEntity record = new AccountSuccessOrderRecordEntity();
                record.setAccountId(ae.getId());
                record.setOperateType(1); // 用户确认
                record.setOrderId(oe.getId());
                record.setRealPrice(availableRealPrice);
                record.setTotalRealPrice(totalSuccessPriceAndNowOrderPrice);
                if (accountSuccessOrderRecordDao.addAccountSuccessOrderRecord(record) == 0)
                {
                    log.error("addAccountSuccessOrderRecord失败");
                    ServiceException se = new ServiceException("交易成功订单记录插入异常");
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    throw se;
                }
            }
        }
        else
        // 订单状态不正确
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_MODIFY_ERRORCODE.ORDERID_STATUS_INVALID.getValue());
            return result.toString();
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    @Override
    public String list(String requestParams)
        throws Exception
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        byte type = param.get("type").getAsByte();
        int page = param.get("page").getAsInt();
        
        List<OrderView> ovs = new ArrayList<OrderView>();
        
        // 用户不存在
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_LIST_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        byte[] types;
        if (type == 0)
        {
            types =
                new byte[] {(byte)CommonEnum.ORDER_STATUS.NOT_PAY.getValue(), (byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue(),
                    (byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue(), (byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue(), (byte)CommonEnum.ORDER_STATUS.USER_CANCEL.getValue(),
                    (byte)CommonEnum.ORDER_STATUS.TIME_OUT_CANCEL.getValue()};
        }
        else if (type == 1)
        {
            types = new byte[] {(byte)CommonEnum.ORDER_STATUS.NOT_PAY.getValue()};
        }
        else if (type == 2)
        {
            types = new byte[] {(byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue()};
        }
        else if (type == 3)
        {
            types = new byte[] {(byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue()};
        }
        else if (type == 4)
        {
            types = new byte[] {(byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue()};
        }
        else
        {
            types = new byte[] {(byte)CommonEnum.ORDER_STATUS.USER_CANCEL.getValue(), (byte)CommonEnum.ORDER_STATUS.TIME_OUT_CANCEL.getValue()};
        }
        
        int orderCount = odi.findCountByStatus(accountId, types);
        result.addProperty("orderCount", orderCount + "");
        // page不合法
        if ((page <= 0) || (page > 1 && orderCount + CommonConstant.ORDER_PAGE_COUNT <= page * CommonConstant.ORDER_PAGE_COUNT))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_LIST_ERRORCODE.PAGE_INVALID.getValue());
            return result.toString();
        }
        
        List<OrderEntity> oes = odi.findOrderByStatusAndPage(accountId, types, page);
        for (OrderEntity oe : oes)
        {
            OrderView ov = new OrderView();
            if (oe.getStatus() == CommonEnum.ORDER_STATUS.NOT_PAY.getValue())
            {
                String endSecond = "-1";
                String validTimeStr = odi.findValidTimeByOid(oe.getId());
                if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
                {
                    Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                    if (new Date().before(validTime)) // 订单锁定未过期
                    {
                        endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                    }
                    else
                        continue; // 过期先不显示出来
                }
                else
                    continue; // 过期先不显示出来
                ov.setEndSecond(endSecond);
            }
            if (oe.getPayTime() != null && !oe.getPayTime().equals("0000-00-00 00:00:00"))
            {
                ov.setOperateTime(oe.getPayTime());
            }
            else
            {
                ov.setOperateTime(oe.getCreateTime());
            }
            ov.setOrderId(oe.getId() + "");
            ov.setOrderNumber(oe.getNumber());
            ov.setStatus(oe.getStatus() + "");
            List<OrderProductView> opvs = new ArrayList<OrderProductView>();
            List<Map<String, Object>> lockProductInfos = odi.findOrderProductInfosByOId(oe.getId());
            for (Map<String, Object> map : lockProductInfos)
            {
                int orderProductId = (map.get("id") != null ? ((Long)map.get("id")).intValue() : 0);
                int productId = (map.get("product_id") != null ? ((Long)map.get("product_id")).intValue() : 0);
                int productCount = (map.get("product_count") != null ? (Integer)map.get("product_count") : 0);
                String smallImage = (map.get("smallImage") == null ? "" : map.get("smallImage").toString());
                String shortName = (map.get("shortName") == null ? "" : map.get("shortName").toString());
                String salesPrice = (map.get("salesPrice") == null ? "" : map.get("salesPrice") + "");
                // ProductCommonEntity pce = pcommondi.findProductCommonInfoById(productId);
                OrderProductView opv = new OrderProductView();
                opv.setOrderProductId(String.valueOf(orderProductId));
                opv.setProductId(productId + "");
                opv.setImage(smallImage); // pce.getSmallImage());
                opv.setShortName(shortName); // pce.getShortName());
                opv.setSalesPrice(salesPrice); // pce.getSalesPrice() + "");
                opv.setCount(productCount + "");
                opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                opv.setStockCount("");
                opv.setRestriction("");
                
                //退款逻辑状态显示开始
                //'状态；1：申请中，2：待退货，3：待退款，4：退款成功，5：退款关闭，6：退款取消',
                String refundStatus = "-1",
                //'类型；1：仅退款，2：退款并退货',
                refundType = "-1";
                
                List<Integer> statusList = Arrays.asList(1,2,3,4,5,6);
                Map<String, Object> mapParams = new HashMap<String, Object>();
                mapParams.put("orderProductId", orderProductId);
                mapParams.put("status", statusList);
                Map<String, Object> productRefundMap = orderProductRefundDao.queryOPRInfosByOrderProductId(mapParams);
                refundStatus = (productRefundMap.get("status") == null ? "-1" : productRefundMap.get("status") + ""); 
                refundType = (productRefundMap.get("type") == null ? "1" : productRefundMap.get("type") + "");
                
                opv.setType(refundType);
                opv.setRefundStatus(refundStatus);
                //退款逻辑结束
                
                opvs.add(opv);
            }
            ov.setOrderDetailList(opvs);
            ov.setAllTotalPrice(oe.getRealPrice() + "");
            ov.setPayChannel(oe.getPayChannel() + "");
            ovs.add(ov);
        }
        Collections.sort(ovs);
        result.add("orderList", parser.parse(JSONUtils.toJson(ovs, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String detail(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int orderId = param.get("orderId").getAsInt();
        
        // 用户不存在
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_DETAIL_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        OrderEntity oe = odi.findOrderById(orderId);
        // 订单不存在
        if (oe == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_DETAIL_ERRORCODE.ORDERID_NOT_EXIST.getValue());
            return result.toString();
        }
        SellerEntity se = sdi.findOrderSellerInfoById(oe.getOrderSellerId());
        if (se == null)
        {
            se = new SellerEntity();
        }
        byte orderStatus = oe.getStatus();
        if (orderStatus == (byte)CommonEnum.ORDER_STATUS.NOT_PAY.getValue())
        {
            String endSecond = "-1";
            String validTimeStr = odi.findValidTimeByOid(oe.getId());
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 订单锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            result.addProperty("endSecond", endSecond);
        }
        else
        {
            result.addProperty("endSecond", "");
        }
        
        SellerEntity curSeller = sdi.findSellerById(oe.getSellerId());
        
        if (orderStatus == (byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue() || orderStatus == (byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue())
        {
            String logisticsUrl = ""; // / 先放着
            
            List<Map<String, String>> ldeList = new ArrayList<Map<String, String>>();
            OrderLogisticsEntity ole = orderLogisticsDao.findOrderLogisticsByOId(orderId);
            if (ole != null)
            {
                List<LogisticsDetailEntity> ldes = logisticsDetailDao.findDetailByChannelAndNumber(ole.getChannel(), ole.getNumber());
                result.addProperty("logisticsChannel", ole.getChannel());
                result.addProperty("logisticsNumber", ole.getNumber());
                // for (LogisticsDetailEntity lde : ldes)
                // {
                if (ldes != null && ldes.size() > 0) // 取最新的2条
                {
                    for (int i = 0; i < ldes.size(); i++)
                    {
                        // 只取最新的两条
                        if (i > 1)
                        {
                            break;
                        }
                        LogisticsDetailEntity lde = ldes.get(i);
                        Map<String, String> ldeMap = new HashMap<String, String>();
                        ldeMap.put("operateTime", lde.getOperateTime().substring(0, lde.getOperateTime().length() - 3));
                        ldeMap.put("content", lde.getContent());
                        ldeList.add(ldeMap);
                    }
                }
                else
                {
                    // 判断 是否是 笨鸟发货
                    if (curSeller != null && curSeller.getIsBirdex() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
                    {
                        // 是笨鸟发货，查询 overseas_order_logistics_record 有没有记录
                        List<Map<String, Object>> birdexLogisticsList = odi.findBirdexOrderLogisticsByOrderId(oe.getId());
                        if (birdexLogisticsList != null && birdexLogisticsList.size() > 0)
                        {
                            for (int i = 0; i < birdexLogisticsList.size(); i++)
                            {
                                // 只取最新的两条
                                if (i > 1)
                                {
                                    break;
                                }
                                Map<String, Object> it = birdexLogisticsList.get(i);
                                String operate_time = it.get("operate_time") == null ? "" : it.get("operate_time") + "";
                                String content = it.get("content") == null ? "" : it.get("content") + "";
                                if (!"".equals(operate_time) && !"".equals(content))
                                {
                                    Map<String, String> ldeMap = new HashMap<String, String>();
                                    ldeMap.put("operateTime", operate_time.substring(0, operate_time.length() - 3));
                                    ldeMap.put("content", content);
                                    ldeList.add(ldeMap);
                                }
                            }
                        }
                    }
                }
                
                // }
            }
            else
            {
                // 判断 是否是 笨鸟发货
                if (curSeller != null && curSeller.getIsBirdex() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
                {
                    // 是笨鸟发货，查询 overseas_order_logistics_record 有没有记录
                    List<Map<String, Object>> birdexLogisticsList = odi.findBirdexOrderLogisticsByOrderId(oe.getId());
                    if (birdexLogisticsList != null && birdexLogisticsList.size() > 0)
                    {
                        for (int i = 0; i < birdexLogisticsList.size(); i++)
                        {
                            // 只取最新的两条
                            if (i > 1)
                            {
                                break;
                            }
                            Map<String, Object> it = birdexLogisticsList.get(i);
                            String operate_time = it.get("operate_time") == null ? "" : it.get("operate_time") + "";
                            String content = it.get("content") == null ? "" : it.get("content") + "";
                            if (!"".equals(operate_time) && !"".equals(content))
                            {
                                Map<String, String> ldeMap = new HashMap<String, String>();
                                ldeMap.put("operateTime", operate_time.substring(0, operate_time.length() - 3));
                                ldeMap.put("content", content);
                                ldeList.add(ldeMap);
                            }
                        }
                    }
                    result.addProperty("logisticsNumber", "物流信息稍后显示");
                }
                else
                {
                    result.addProperty("logisticsNumber", "");
                }
                result.addProperty("logisticsChannel", "");
            }
            result.add("logisticsDetail", parser.parse(JSONUtils.toJson(ldeList, false)));
        }
        
        if (oe.getPayTime() != null && !oe.getPayTime().equals("0000-00-00 00:00:00"))
        {
            result.addProperty("operateTime", oe.getPayTime());
        }
        else
        {
            result.addProperty("operateTime", oe.getCreateTime());
        }
        
        List<OrderProductView> opvs = new ArrayList<OrderProductView>();
        List<Map<String, Object>> lockProductInfos = odi.findOrderProductInfosByOId(oe.getId());
        for (Map<String, Object> map : lockProductInfos)
        {
            // int productId = ((Long)map.get("product_id")).intValue();
            // int productCount = (Integer)map.get("product_count");
            // ProductCommonEntity pce = pcommondi.findProductCommonInfoById(productId);
            int orderProductId = (map.get("id") != null ? ((Long)map.get("id")).intValue() : 0);
            int productId = (map.get("product_id") != null ? ((Long)map.get("product_id")).intValue() : 0);
            int productCount = (map.get("product_count") != null ? (Integer)map.get("product_count") : 0);
            String smallImage = (map.get("smallImage") == null ? "" : map.get("smallImage").toString());
            String shortName = (map.get("shortName") == null ? "" : map.get("shortName").toString());
            String salesPrice = (map.get("salesPrice") == null ? "" : map.get("salesPrice") + "");
            
            OrderProductView opv = new OrderProductView();
            opv.setOrderProductId(String.valueOf(orderProductId));
            opv.setProductId(productId + "");
            opv.setImage(smallImage); // pce.getSmallImage());
            opv.setShortName(shortName); // pce.getShortName());
            opv.setSalesPrice(salesPrice); // pce.getSalesPrice() + "");
            opv.setCount(productCount + "");
            opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
            opv.setStockCount("");
            opv.setRestriction("");
            
            //退款逻辑开始
            String refundStatus = "-1",type="-1";
            List<Integer> statusList = Arrays.asList(1,2,3,4,5,6);
            Map<String, Object> mapParams = new HashMap<String, Object>();
            mapParams.put("orderProductId", orderProductId);
            mapParams.put("status", statusList);
            Map<String, Object> productRefundMap = orderProductRefundDao.queryOPRInfosByOrderProductId(mapParams);
            
            Object orderProductRefundId = productRefundMap.get("id");
            boolean isIn7Day = false;
            
            if((byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue() == orderStatus){//2、交易成功7天以后不支持退款
                String receiveTime = oe.getReceiveTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                long receiveLong = sdf.parse(receiveTime).getTime();  
                long nowLong = new Date().getTime();
                long between = nowLong-receiveLong;
                long sevenDay = 1000*3600*24*7;
                if(between<=sevenDay){
                    isIn7Day = true;
                }
            }
            if(((byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue() == orderStatus)||
                    ((byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue() == orderStatus)||
                           ((byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue() == orderStatus&&isIn7Day)||
                                ((byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue() == orderStatus&&orderProductRefundId != null)||
                                     ((byte)CommonEnum.ORDER_STATUS.USER_CANCEL.getValue() == orderStatus&&orderProductRefundId != null)){
                refundStatus = (productRefundMap.get("status") == null ? "0" : productRefundMap.get("status") + ""); // 0表示在order_product_refund表中没有记录
                type = (productRefundMap.get("type") == null ? "0" : productRefundMap.get("type") + "");
            }
            
            opv.setType(type);
            opv.setOrderProductRefundId(String.valueOf(orderProductRefundId));
            opv.setRefundStatus(refundStatus);
            //退款逻辑结束
            
            opvs.add(opv);

        }
        // ReceiveAddressView rav = null ;
        // rav = this.receiveAddressService.findReceiveAddressViewById(oe.getReceiveAddressId() ) ;
        
        result.add("orderProductList", parser.parse(JSONUtils.toJson(opvs, false)));
        result.addProperty("orderStatus", oe.getStatus() + "");
        result.addProperty("orderId", oe.getId() + "");
        result.addProperty("paytype", oe.getPayChannel() + "");
        result.addProperty("orderNumber", oe.getNumber());
        result.addProperty("freightPrice", oe.getFreightMoney() + "");
        result.addProperty("totalPrice", oe.getTotalPrice() + ""); // 总价包邮费
        result.addProperty("realPrice", oe.getRealPrice() + ""); // 实付金额
        result.addProperty("accountPointPrice", oe.getAccountPoint() / 100.0 + ""); // 积分优惠
        result.addProperty("couponPrice", oe.getCouponPrice() + ""); // 优惠券优惠
        result.addProperty("onetotalPrice", this.decimalFormat.format(oe.getTotalPrice() - oe.getFreightMoney())); // 定单的价格，不包括邮费
        result.addProperty("sellerName", se.getSellerName());
        result.addProperty("sendAddress", se.getSendAddress());
        result.addProperty("sellerType", se.getSellerType());//添加订单类型
        result.addProperty("bondedNumType", curSeller.getBondedNumType());//保税区物流单号类型
        result.addProperty("addressId", oe.getReceiveAddressId() + "");
        result.addProperty("createTime", oe.getCreateTime() + "");
        
        // 使用了优惠券，则要判断订单总价是否大于优惠券金额（防止用户订单分开付款小额订单使用大额优惠券的漏洞）
        if (oe.getCouponPrice() > 0)
        {
            if (canUseCoupon(oe.getAccountCouponId(), oe.getTotalPrice()))
            {
                result.addProperty("canUseCoupon", "1");
            }
            else
            {
                result.addProperty("canUseCoupon", "0");
            }
        }
        else
        {
            result.addProperty("canUseCoupon", "1");
        }
        
        byte sellType = se.getSellerType(); // 一定要有值
        if (sellType == Byte.parseByte("2") || sellType == Byte.parseByte("3"))
        {
            result.addProperty("isBonded", "1");
        }
        
        if (oe != null && oe.getReceiveAddressId() > 0) // 定单详情时的定单收货地址的展示
        {
            OrderReceiveAddressEntity orae = this.orderReceiveAddressDao.findAddressById(oe.getReceiveAddressId());
            ReceiveAddressView rav = new ReceiveAddressView();
            rav.setId(orae.getId());
            rav.setFullName(orae.getFullName());
            rav.setMobileNumber(orae.getMobileNumber());
            rav.setProvince(orae.getProvince());
            rav.setCity(orae.getCity());
            rav.setDistrict(orae.getDistrict());
            
            /*
             * rav.setProvince(this.provincedi.findProvinceNameById(orae.getProvince()));
             * rav.setCity(this.citydao.findCityNameById(orae.getCity()) ); rav.setDistrict(
             * this.districtDao.findDistinctNameById( orae.getDistrict()) ) ; String detailAddress
             * =rav.getProvince()+rav.getCity()+rav.getDistrict()+ orae.getDetailAddress() ;
             */
            
            String provinceName = CommonUtil.getProvinceNameByProvinceId(orae.getProvince());
            String cityName = CommonUtil.getCityNameByCityId(orae.getProvince(), orae.getCity());
            String districtName = CommonUtil.getDistrictNameByDistrictId(orae.getCity(), orae.getDistrict());
            String detailAddress = provinceName + cityName + districtName + orae.getDetailAddress();
            rav.setProvince(provinceName);
            rav.setCity(cityName);
            rav.setDistrict(districtName);
            
            rav.setDetailAddress(detailAddress);
            
            result.add("orderAddress", parser.parse(JSONUtils.toJson(rav, false)));
            /*
             * result.addProperty("receiveName", orae.getFullName()); result.addProperty("receivePhone",
             * orae.getMobileNumber()); result.addProperty("receiveAddress", orae.getDetailAddress());
             * result.addProperty("province", orae.getProvince()); result.addProperty("city", orae.getCity());
             * result.addProperty("district", orae.getDistrict());
             */
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    private boolean canUseCoupon(int accountCouponId, float totalPrice)
    {
        CouponAccountEntity cae = couponAccountDao.findCouponAccountById(accountCouponId);
        CouponDetailEntity cde = null;
        if (cae == null)
        {
            return true;
        }
        else
        {
            if (cae.getSourceType() == Byte.valueOf(CouponAccountSourceTypeEnum.COUPON_CODE_CHANGE.getCode() + ""))
            {
                // 优惠码兑换
                CouponCodeEntity cce = couponCodeDao.findInfoById(cae.getCouponCodeId());
                if (cce == null)
                {
                    return true;
                }
                else
                {
                    cde = couponDetailDao.findCouponDetailById(cce.getCouponDetailId());
                }
            }
            else
            {
                // 领取优惠券
                cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
            }
        }
        if (cde == null)
        {
            return true;
        }
        else
        {
            if (totalPrice >= cde.getThreshold())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    @Override
    public String payDetail(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int orderId = param.get("orderId").getAsInt();
        
        // 用户不存在
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_PAYDETAIL_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        OrderEntity oe = odi.findOrderById(orderId);
        // 订单不存在
        if (oe == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ORDER_PAYDETAIL_ERRORCODE.ORDERID_NOT_EXIST.getValue());
            return result.toString();
        }
        byte orderStatus = oe.getStatus();
        if (orderStatus == (byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue() || orderStatus == (byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue()
            || orderStatus == (byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue())
        {
            result.addProperty("isSuccees", "1");
            result.addProperty("operateTime", oe.getPayTime());
            result.addProperty("endSecond", "");
        }
        else
        {
            String endSecond = "-1";
            String validTimeStr = odi.findValidTimeByOid(oe.getId());
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 订单锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            result.addProperty("isSuccees", "0");
            result.addProperty("operateTime", oe.getCreateTime());
            result.addProperty("endSecond", endSecond);
        }
        result.addProperty("payChannel", oe.getPayChannel() + "");
        result.addProperty("orderNumber", oe.getNumber());
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String pay(String requestParams, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        // reqeustParams : {'accountId':'70','channel':'2','ipAddress':'0:0:0:0:0:0:0:1','orderIdList':[215928]}
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        JsonArray orderIdList = param.get("orderIdList").getAsJsonArray();
        byte channel = param.get("channel").getAsByte();
        String ipAddress = param.get("ipAddress").getAsString();
        byte sourceType = param.get("sourceType").getAsByte();
        // String orderSource = param.get("orderSource").getAsString();
        
        float payPrice = 0f;
        String needPay = "1";
        List<Integer> orderIds = new ArrayList<>();
        if (sourceType == CommonEnum.ORDER_PAY_SOURCE_TYPE.CONFIRM.getValue())
        {
            Iterator<JsonElement> it = orderIdList.iterator();
            while (it.hasNext())
            {
                orderIds.add(it.next().getAsInt());
            }
            List<OrderEntity> oes = odi.findNotPayOrderByIds(orderIds);
            if (orderIds.size() == 0 || oes.size() != orderIds.size()) // 订单id不存在
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_PAY_ERRORCODE.ORDERID_NOT_EXIST.getValue());
                return result.toString();
            }
            for (OrderEntity oe : oes)
            {
                payPrice += oe.getRealPrice();
            }
        }
        else if (sourceType == CommonEnum.ORDER_PAY_SOURCE_TYPE.DETAIL.getValue())
        {
            OrderEntity oe = odi.findOrderById(orderIdList.iterator().next().getAsInt());
            orderIds.add(oe.getId());
            payPrice += oe.getRealPrice();
        }
        
        else if (sourceType == CommonEnum.ORDER_PAY_SOURCE_TYPE.MERGER.getValue())
        {
            // 暂不支持
        }
        
        //payPrice = CommonUtil.transformFloat(payPrice);
        int totalFee = Math.round(payPrice * 100);
        
        String payMark = CommonUtil.generateUUID();
        
        if (totalFee == 0)
        {
            OrderEntity oe = odi.findOrderById(orderIdList.iterator().next().getAsInt());
            result.addProperty("orderNumber", oe.getNumber());
            result.addProperty("totalPrice", oe.getTotalPrice());
            result.addProperty("transactionTime", CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
            needPay = "0";
            
            if (channel == CommonEnum.ORDER_PAY_CHANNEL.ALIPAY.getValue())
            {
                for (Integer orderId : orderIds)
                {
                    OrderAliPayEntity owpe = new OrderAliPayEntity();
                    owpe.setOrderId(orderId);
                    owpe.setPayMark(payMark);
                    if (orderAliPayDao.replaceIntoOrderAliPay(owpe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("替换Ali订单支付失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderPayChannel(orderId, accountId, channel) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("更新订单付款渠道失败");
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                    if (odi.updateOrderNotDelivery(orderId) == 0)
                    {
                        log.error("0元订单，更新订单状态失败");
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                }
                
            }
            else if (channel == CommonEnum.ORDER_PAY_CHANNEL.WEIXIN.getValue())
            {
                for (Integer orderId : orderIds)
                {
                    OrderWeixinPayEntity owpe = new OrderWeixinPayEntity();
                    owpe.setOrderId(orderId);
                    owpe.setPayMark(payMark);
                    if (orderWeixinPayDao.replaceIntoOrderWeixinPay(owpe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("替换微信订单支付失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderPayChannel(orderId, accountId, channel) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("更新订单付款渠道失败");
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                    if (odi.updateOrderNotDelivery(orderId) == 0)
                    {
                        log.error("0元订单，更新订单状态失败");
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                }
            }
            else if (channel == CommonEnum.ORDER_PAY_CHANNEL.UNIONPAY.getValue())
            {
                for (Integer orderId : orderIds)
                {
                    OrderUnionPayEntity oupe = new OrderUnionPayEntity();
                    oupe.setOrderId(orderId);
                    oupe.setPayMark(payMark);
                    if (orderUnionPayDao.replaceIntoOrderUnionPay(oupe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("替换银联订单支付失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderPayChannel(orderId, accountId, channel) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("更新订单付款渠道失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderNotDelivery(orderId) == 0)
                    {
                        log.error("0元订单，更新订单状态失败");
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                }
            }
            else
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_PAY_ERRORCODE.CHANNEL_NOT_EXIST.getValue());
                return result.toString();
            }
            
            if (orderIds.size() > 0)
            {
                // 更新订单状态
                
                if (!processPointRecord(orderIds, payMark)) // 处理积分方法 & 处理推荐用户首次下单及合伙人返积分
                {
                    ServiceException se = new ServiceException();
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    throw se;
                }
                Set<Integer> productIdset = new HashSet<Integer>();
                for (Integer orderId : orderIds)
                {
                    productIdset.addAll(odi.findLockProductIdsByOId(orderId));
                }
                List<Integer> productIds = new ArrayList<Integer>();
                productIds.addAll(productIdset);
                List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds); // 一次锁定多个商品id
                Map<Integer, ProductCountEntity> pceMap = new HashMap<Integer, ProductCountEntity>();
                for (ProductCountEntity pce : pces)
                {
                    pceMap.put(pce.getProductId(), pce);
                }
                
                for (Integer orderId : orderIds) // 删除订单锁定数量,更新商品数量,更新订单锁定时间
                {
                    String errorMsg = "";
                    List<Map<String, Object>> lockProductInfos = odi.findLockCountInfosByOId(orderId);
                    for (Map<String, Object> map : lockProductInfos)
                    {
                        int productId = ((Long)map.get("product_id")).intValue();
                        int productCount = (Integer)map.get("product_count");
                        if (odi.deleteLockCount(productId, orderId) == 0)
                        {
                            // ConnectionManager.rollbackTransaction();
                            errorMsg = "deleteLockCount没有匹配,payMark：" + payMark + ",orderId：" + orderId + ",productId：" + productId;
                            log.error(errorMsg);
                            ServiceException se = new ServiceException(errorMsg);
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            throw se;
                        }
                        // 多个相同商品不会出错
                        ProductCountEntity pce = pceMap.get(productId);
                        pce.setLock(pce.getLock() - productCount);
                        pce.setStock(pce.getStock() - productCount);
                        pce.setSell(pce.getSell() + productCount);
                        if (pcdi.updateCountInfo(pce) == 0)
                        {
                            // ConnectionManager.rollbackTransaction();
                            errorMsg = "updateCountInfo没有匹配,payMark：" + payMark + ",orderId：" + orderId + ",productId：" + productId;
                            log.error(errorMsg);
                            ServiceException se = new ServiceException(errorMsg);
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            throw se;
                            
                        }
                        if (pcommondi.updateSellCountById(productId, pce.getSell()) == 0) // 更新通用商品的数量
                        {
                            errorMsg = "updateSellCountById没有匹配,payMark：" + payMark + ",orderId：" + orderId + ",productId：" + productId;
                            log.error(errorMsg);
                            ServiceException se = new ServiceException(errorMsg);
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            throw se;
                        }
                    }
                    if (odi.updateLockTime(orderId, System.currentTimeMillis()) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        errorMsg = "updateLockTime没有匹配,payMark：" + payMark + ",orderId：" + orderId;
                        log.error(errorMsg);
                        ServiceException se = new ServiceException(errorMsg);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        throw se;
                    }
                }
            }
            
            // 0元订单： 付款成功，设置订单发货超时时间
            try
            {
                updateOrderExpireTime(orderIds);
            }
            catch (Exception e)
            {
                log.error("更新订单发货超时时间发生异常", e);
            }
        }
        else
        {
            if (channel == CommonEnum.ORDER_PAY_CHANNEL.ALIPAY.getValue())
            {
                
                if (payPrice <= 0.1f) // 使用国内支付宝
                {
                    String ALIPAY_GATEWAY_NEW = YggWebProperties.getInstance().getProperties("ali_gate_url");// 支付宝网关地址
                    String format = "xml";// 返回格式
                    String v = "2.0";
                    String req_id = UtilDate.getOrderNum();
                    String notify_url = YggWebProperties.getInstance().getProperties("ali_notify_url");
                    String call_back_url = YggWebProperties.getInstance().getProperties("ali_callback_url");
                    String merchant_url = YggWebProperties.getInstance().getProperties("ali_merchant_url");
                    String seller_email = YggWebProperties.getInstance().getProperties("ali_seller_email");// 卖家支付宝帐户
                    String out_trade_no = payMark; // 商户订单号
                    String subject = "左岸城堡";
                    String total_fee = decimalFormat.format(payPrice);
                    // 请求业务参数详细
                    String req_dataToken =
                        "<direct_trade_create_req><notify_url>" + notify_url + "</notify_url><call_back_url>" + call_back_url + "</call_back_url><seller_account_name>"
                            + seller_email + "</seller_account_name><out_trade_no>" + out_trade_no + "</out_trade_no><subject>" + subject + "</subject><total_fee>" + total_fee
                            + "</total_fee><merchant_url>" + merchant_url + "</merchant_url></direct_trade_create_req>";
                    // 必填
                    log.info("ali--requestdata--req_dataToken---is:" + req_dataToken);
                    // 把请求参数打包成数组
                    Map<String, String> sParaTempToken = new HashMap<String, String>();
                    sParaTempToken.put("service", "alipay.wap.trade.create.direct");
                    sParaTempToken.put("partner", AlipayConfig.partner);
                    sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
                    sParaTempToken.put("sec_id", AlipayConfig.sign_type);
                    sParaTempToken.put("format", format);
                    sParaTempToken.put("v", v);
                    sParaTempToken.put("req_id", req_id);
                    sParaTempToken.put("req_data", req_dataToken);
                    
                    String sHtmlTextToken = "";
                    
                    sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, "", "", sParaTempToken); // AlipaySubmit.post(ALIPAY_GATEWAY_NEW,
                    log.info("--ali---sHtmlTextToken-before---is:" + sHtmlTextToken);
                    // URLDECODE返回的信息
                    sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, AlipayConfig.input_charset);
                    log.info("--ali---sHtmlTextToken-after---is:" + sHtmlTextToken);
                    // 获取token
                    String request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
                    log.info("ali----getToken-request_token---is:" + request_token);
                    
                    // 业务详细
                    String req_data = "<auth_and_execute_req><request_token>" + request_token + "</request_token></auth_and_execute_req>";
                    // 必填
                    // 把请求参数打包成数组
                    Map<String, String> sParaTemp = new HashMap<String, String>();
                    sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
                    sParaTemp.put("partner", AlipayConfig.partner);
                    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
                    sParaTemp.put("sec_id", AlipayConfig.sign_type);
                    sParaTemp.put("format", format);
                    sParaTemp.put("v", v);
                    sParaTemp.put("req_data", req_data);
                    
                    // 建立请求
                    String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");
                    
                    if (!CommonUtil.isWeiXinBrower(request.getHeader("User-Agent"))) // 不是微信的话
                        sHtmlText += "<script>document.forms['alipaysubmit'].submit();</script>";
                    
                    log.info("ali----sHtmlText---is:" + sHtmlText);
                    result.addProperty("requestUrl", sHtmlText);
                }
                else
                {
                    // 使用国际支付宝
                    String notify_url = YggWebProperties.getInstance().getProperties("ali_global_notify_url");
                    String call_back_url = YggWebProperties.getInstance().getProperties("ali_global_callback_url");
                    Map<String, String> sParaTemp = new HashMap<>();
                    sParaTemp.put("subject", "左岸城堡全球美食");
                    sParaTemp.put("out_trade_no", payMark);
                    sParaTemp.put("currency", "USD");
                    sParaTemp.put("rmb_fee", decimalFormat.format(payPrice));
                    sParaTemp.put("partner", AlipayConfig.global_partner);
                    sParaTemp.put("notify_url", notify_url);
                    sParaTemp.put("return_url", call_back_url);
                    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
                    sParaTemp.put("product_code", "NEW_WAP_OVERSEAS_SELLER");
                    sParaTemp.put("service", "create_forex_trade_wap");
                    String amount = payPrice >= 0.2 ? payPrice - 0.1 + "" : "0.01";
                    amount = MathUtil.round(amount, 2);
                    sParaTemp.put("split_fund_info", "[{'transIn':'" + AlipayConfig.global_split_partner + "','amount':'" + amount + "','currency':'CNY','desc':'国际支付宝订单'}]");
                    sParaTemp.put("currency", "USD");
                    String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认付款");
                    
                    if (!CommonUtil.isWeiXinBrower(request.getHeader("User-Agent"))) // 不是微信的话
                        sHtmlText += "<script>document.forms['alipaysubmit'].submit();</script>";
                    
                    log.info("ali----sHtmlText---is:" + sHtmlText);
                    result.addProperty("requestUrl", sHtmlText);
                }
                
                for (Integer orderId : orderIds)
                {
                    OrderAliPayEntity owpe = new OrderAliPayEntity();
                    owpe.setOrderId(orderId);
                    owpe.setPayMark(payMark);
                    if (orderAliPayDao.replaceIntoOrderAliPay(owpe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("替换Ali订单支付失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderPayChannel(orderId, accountId, channel) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("更新订单付款渠道失败");
                        /*
                         * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                         * result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                         * return result.toString();
                         */
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                }
                
            }
            else if (channel == CommonEnum.ORDER_PAY_CHANNEL.WEIXIN.getValue())
            {
                // String payMark = CommonUtil.generateUUID();
                
                // // 发起统一支付请求
                com.ygg.webapp.sdk.weixin.RequestHandler reqHandler = new com.ygg.webapp.sdk.weixin.RequestHandler(null, null);
                String nonce_str = Sha1Util.getNonceStr();
                // SortedMap<String, String> packageParams = new TreeMap<String, String>();
                reqHandler.setParameter("appid", CommonConstant.APPID); // 公众账号ID
                reqHandler.setParameter("mch_id", YggWebProperties.getInstance().getProperties("partner")); // 商户号
                reqHandler.setParameter("body", "左岸城堡"); // "gegejiagoodproduct"); //商品描述 左岸城堡优选商品
                reqHandler.setParameter("openid", SessionUtil.getCurrentWeiXinOpenId(request.getSession()));
                
                reqHandler.setParameter("nonce_str", nonce_str); // 随机字符串
                reqHandler.setParameter("out_trade_no", payMark); // 商家订单号
                reqHandler.setParameter("total_fee", Math.round(payPrice * 100) + ""); // 商品金额,以分为单位
                reqHandler.setKey(YggWebProperties.getInstance().getProperties("weixin_key"));
                reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); // 用户的公网ip
                // IpAddressUtil.getIpAddr(request)
                reqHandler.setParameter("notify_url", YggWebProperties.getInstance().getProperties("tenpay_notify_url")); // 通知地址
                reqHandler.setParameter("trade_type", "JSAPI");
                String sign = reqHandler.createSign(reqHandler.getParameters()); // 生成签名
                reqHandler.setParameter("sign", sign);
                String requestUrl = reqHandler.getRequestURL(); // xml格式
                log.info("-----requestBeforePay--------requestUrl-----is:" + requestUrl);
                
                // 通信对象
                WeiXinHttpClient httpClient = new WeiXinHttpClient();
                // 通信对象
                // httpClient.setTimeOut(30);
                // 设置请求内容
                // httpClient.setReqContent(YggWebProperties.getInstance().getProperties("weixin_unifiedorder_url")+"?"+requestUrl);
                if (true) // if(httpClient.call())
                {
                    WeiXinPayReqView wxprv = new WeiXinPayReqView();
                    String prepay_id = "";
                    String responseParams = httpClient.sendPostMsg(YggWebProperties.getInstance().getProperties("weixin_unifiedorder_url"), requestUrl);
                    log.info("----responseParams-----------is:" + responseParams);
                    if (responseParams != null && !responseParams.equals(""))
                    {
                        Map<String, Object> map = GetWxOrderno.doXMLParse(responseParams);
                        prepay_id = map.get("prepay_id") + "";
                    }
                    log.info("----weixin---prepay_id-----------is:" + prepay_id);
                    SortedMap<String, String> finalpackage = new TreeMap<String, String>();
                    String appid2 = CommonConstant.APPID;
                    
                    String timestamp = Sha1Util.getTimeStamp();
                    String nonceStr2 = nonce_str; // Sha1Util.getNonceStr() ; //
                    String prepay_id2 = "prepay_id=" + prepay_id;
                    String packages = prepay_id2;
                    finalpackage.put("appId", appid2);
                    finalpackage.put("timeStamp", timestamp);
                    finalpackage.put("nonceStr", nonceStr2);
                    finalpackage.put("package", packages);
                    finalpackage.put("signType", "MD5");
                    // reqHandler.setKey(YggWebProperties.getInstance().getProperties("weixin_key"));
                    String finalsign = reqHandler.createSign(finalpackage);
                    
                    wxprv.setAppid(appid2);
                    wxprv.setTimestamp(timestamp);
                    wxprv.setSignType("MD5");
                    wxprv.setSign(finalsign);
                    wxprv.setNonce_str(nonceStr2);
                    wxprv.setPackageValue(packages);
                    result.add("wxprv", new JsonParser().parse(JSONUtils.toJson(wxprv, false)));
                    
                }
                
                for (Integer orderId : orderIds)
                {
                    OrderWeixinPayEntity owpe = new OrderWeixinPayEntity();
                    owpe.setOrderId(orderId);
                    owpe.setPayMark(payMark);
                    if (orderWeixinPayDao.replaceIntoOrderWeixinPay(owpe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("替换微信订单支付失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderPayChannel(orderId, accountId, channel) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("更新订单付款渠道失败");
                        /*
                         * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                         * result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                         * return result.toString();
                         */
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        throw se;
                    }
                }
                
                // ConnectionManager.commitTransaction();
            }
            else if (channel == CommonEnum.ORDER_PAY_CHANNEL.UNIONPAY.getValue())
            {
                
                // SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件 在LunchServlet中加载
                // 交易请求url 从配置文件读取
                String requestAppUrl = SDKConfig.getConfig().getFrontRequestUrl();
                // String payMark = CommonUtil.generateUUID();
                
                String call_back_url = YggWebProperties.getInstance().getProperties("unionfronturl");
                // if(orderSource.equals("2")) //订单详情中来
                // call_back_url = YggWebProperties.getInstance().getProperties("unionfronturl_orderDetail");
                // call_back_url +="?orderSource=2" ;
                // call_back_url ="http://m.gegejia.com/ygg/order/orderdetail/"+orderIds.get(0) ;
                
                // 组装请求报文
                Map<String, String> data = new HashMap<String, String>();
                data.put("version", "5.0.0");
                // 字符集编码 默认"UTF-8"
                data.put("encoding", "UTF-8");
                // 签名方法 01 RSA
                data.put("signMethod", "01");
                // 交易类型 01-消费
                data.put("txnType", "01");
                // 交易子类型 01:自助消费 02:订购 03:分期付款
                data.put("txnSubType", "01");
                // 业务类型
                data.put("bizType", "000201");
                // 渠道类型，07-PC，08-手机
                data.put("channelType", "08");
                // 前台通知地址 ，控件接入方式无作用
                data.put("frontUrl", call_back_url);
                // 商户/收单后台接收地址 必送
                data.put("backUrl", YggWebProperties.getInstance().getProperties("unionbackurl"));
                // 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
                data.put("accessType", "0");
                // 商户号码
                data.put("merId", YggWebProperties.getInstance().getProperties("unionmerid"));
                // 订单号 商户根据自己规则定义生成，每订单日期内不重复 商户订单号，8-40位数字字母
                data.put("orderId", payMark);
                // 订单发送时间 格式： YYYYMMDDhhmmss 商户发送交易时间，根据自己系统或平台生成
                data.put("txnTime", CommonUtil.date2String(new Date(), "yyyyMMddHHmmss"));
                
                // 交易金额 分
                data.put("txnAmt", Math.round(payPrice * 100) + "");
                // 交易币种
                data.put("currencyCode", "156");
                
                Map<String, String> submitFromData = DemoBase.signData(data);
                
                String sHtmlText = DemoBase.createHtml(requestAppUrl, submitFromData);
                log.info("-----unionpay---request---requestUrl---is:" + requestAppUrl);
                log.info("-----unionpay---request---form-html---is:" + sHtmlText);
                
                // 银联订单号 tn 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
                // String tn = resmap.get("tn");
                // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
                for (Integer orderId : orderIds)
                {
                    OrderUnionPayEntity oupe = new OrderUnionPayEntity();
                    oupe.setOrderId(orderId);
                    oupe.setPayMark(payMark);
                    if (orderUnionPayDao.replaceIntoOrderUnionPay(oupe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("替换银联订单支付失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                    if (odi.updateOrderPayChannel(orderId, accountId, channel) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        log.error("更新订单付款渠道失败");
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        return result.toString();
                    }
                }
                // String payStr = tn;
                // ConnectionManager.commitTransaction();
                result.addProperty("requestUrl", sHtmlText);
            }
            else
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ORDER_PAY_ERRORCODE.CHANNEL_NOT_EXIST.getValue());
                return result.toString();
            }
        }
        
        // result.add("payJson", parser.parse(JSONUtils.toJson(payMap, false)));
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.addProperty("needPay", needPay);
        return result.toString();
        
    }
    
    @Override
    public String weixinNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        String errorMsg = "";
        ResponseHandler resHandler = null;
        try
        {
            resHandler = new ResponseHandler(request, response);
            resHandler.setKey(YggWebProperties.getInstance().getProperties("weixin_key"));
            // Map<String,String> reqParams = getAllRequestParam(request) ;
            
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1)
            {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String reqParams = new String(outSteam.toByteArray(), "UTF-8");
            log.info("----------weixinNotifyCallBack------------" + reqParams);
            
            // 创建请求对象
            RequestHandler queryReq = new RequestHandler();
            queryReq.init();
            if (true) // resHandler.isTenpaySign() ==
            {
                Document doc_notify_data = null;
                try
                {
                    doc_notify_data = DocumentHelper.parseText(reqParams);
                }
                catch (DocumentException e)
                {
                    log.error("weixinNotifyCallBack---DocumentException--is:", e);
                    throw e;
                }
                String result_code = doc_notify_data.selectSingleNode("//xml/result_code").getText();
                String out_trade_no = "";
                String transaction_id = "";
                if (result_code != null && result_code.equals("SUCCESS"))
                {
                    String return_code = doc_notify_data.selectSingleNode("//xml/return_code").getText();
                    if (return_code != null && return_code.equals("SUCCESS"))
                    {
                        
                        String appid = doc_notify_data.selectSingleNode("//xml/appid").getText();
                        String total_fee = doc_notify_data.selectSingleNode("//xml/total_fee").getText();
                        transaction_id = doc_notify_data.selectSingleNode("//xml/transaction_id").getText();
                        out_trade_no = doc_notify_data.selectSingleNode("//xml/out_trade_no").getText();
                        
                        Map<String, Object> pparams = new HashMap<String, Object>();
                        pparams.put("payMark", out_trade_no);
                        pparams.put("payId", transaction_id);
                        pparams.put("paychannel", "3");
                        pparams.put("payPrice", total_fee);
                        pparams.put("discount", "0");
                        try
                        {
                            processPayNotifyBack(pparams);
                        }
                        catch (ServiceException se)
                        {
                            log.error("--processPayNotifyBack---", se);
                            resHandler.sendToCFT("fail");
                            throw se;
                        }
                        log.info("处理微信即时到账支付回调成功,payMark：" + out_trade_no);
                        // ConnectionManager.commitTransaction();
                        // 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
                        resHandler.sendToCFT("success");
                        
                    }
                    else
                    {
                        errorMsg = "即时到账支付失败,payMark：" + out_trade_no + ",payTid：" + transaction_id;
                        log.error(errorMsg);
                        resHandler.sendToCFT("fail");
                        ServiceException se = new ServiceException(errorMsg);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        throw se;
                    }
                }
                else
                {
                    errorMsg = "即时到账支付失败,payMark：" + out_trade_no + ",payTid：" + transaction_id;
                    log.error(errorMsg);
                    resHandler.sendToCFT("fail");
                    ServiceException se = new ServiceException(errorMsg);
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    throw se;
                }
                
            }
            
        }
        catch (Exception e)
        {
            // ConnectionManager.rollbackTransaction();
            errorMsg = "PayWeiXinCallback失败";
            log.error(errorMsg, e);
            try
            {
                resHandler.sendToCFT("fail");
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            ServiceException se = new ServiceException(errorMsg);
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            throw se;
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String weixinReturnCallBack(HttpServletRequest request, HttpServletResponse response, String requestParams)
        throws ServiceException
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject obj = (JsonObject)parser.parse(requestParams);
        String orderArrayIds = obj.get("orderIds").getAsString();
        
        // 创建支付应答对象
        // ResponseHandler resHandler = new ResponseHandler(request, response);
        // resHandler.setKey(WxapConfig.PARTNER_KEY);
        boolean flag = true;
        if (flag)
        { // if(resHandler.isTenpaySign()) {
            /*
             * //通知id String notify_id = resHandler.getParameter("notify_id"); // 支付结果通知id，对于某些特定商户，只返回通知id，要求商户据此查询交易结果
             * 所以下面要根据此ID再次发起一个请求查询一次结果
             * 
             * //获取返回参数 String trade_state = resHandler.getParameter("trade_state"); String trade_mode =
             * resHandler.getParameter("trade_mode");
             */
            
            if (flag)
            { // if("0".equals(trade_state) && "1".equals(trade_mode)) {
                /*
                 * String endTime = resHandler.getParameter("time_end") ; // 支付完成时间 Date d =
                 * CommonUtil.string2Date(endTime, "yyyyMMddhhmmss") ; endTime = CommonUtil.date2String(d,
                 * "yyyy-MM-dd HH:mm:ss") ;
                 * 
                 * String total_fee = resHandler.getParameter("total_fee") ; // 总金额 单位为分，如果discount有值，通知的total_fee +
                 * discount = 请求的total_fee //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee int discount =
                 * CommonUtil.isBlank(resHandler.getParameter("discount")) ? 0 :
                 * Integer.parseInt(resHandler.getParameter("discount"));
                 * 
                 * //商户订单号 String out_trade_no = resHandler.getParameter("out_trade_no"); //财付通订单号 String transaction_id
                 * = resHandler.getParameter("transaction_id");
                 * 
                 * int totalfree = Integer.parseInt(total_fee) ; float tp = totalfree/100.00f ; DecimalFormat df = new
                 * DecimalFormat("0.00"); String totalPrice = df.format(tp) ; result.addProperty("totalPrice",totalPrice
                 * );
                 * 
                 * log.info("订单支付财付通return调用成功"); //取结果参数做业务处理 log.info("out_trade_no:" +
                 * resHandler.getParameter("out_trade_no")+ " transaction_id:" +
                 * resHandler.getParameter("transaction_id")); log.info("trade_state:" +
                 * resHandler.getParameter("trade_state")+ " total_fee:" + totalPrice );
                 * //如果有使用折扣券，discount有值，total_fee+discount=原请求的total_fee log.info("discount:" +
                 * resHandler.getParameter("discount")+ " time_end:" + endTime );
                 * 
                 * //金额,以分为单位 int payPrice = Integer.parseInt(resHandler.getParameter("total_fee"));
                 */
                
                log.info("处理微信财付通即时到账支付return调用开始,orderArrayIds：" + orderArrayIds);
                String[] orderArrays = orderArrayIds.split(",");
                List<Integer> orderIds = new ArrayList<Integer>();
                for (String oId : orderArrays)
                    orderIds.add(new Integer(oId));
                
                /*
                 * List<OrderWeixinPayEntity> owpes = orderWeixinPayDao.findOrderWeixinPayByMark(out_trade_no); for
                 * (OrderWeixinPayEntity owpe : owpes) { orderIds.add(owpe.getOrderId()); }
                 */
                
                String endSecond = "-1";
                String orderNumber = "";
                String paytype = "";
                String orderId = "";
                String totalPrice = "";
                if (orderIds.size() > 0)
                {
                    float oldPayPrice = odi.findSumPriceByOrderIds(orderIds);
                    log.info("weixin---returncallback-------oldPayPrice :" + oldPayPrice);
                    totalPrice = String.valueOf(oldPayPrice);
                    /*
                     * if (Math.round(oldPayPrice * 100) != (payPrice + discount) ) { log.error("金额不匹配,oldPayPrice：" +
                     * oldPayPrice + ",payPrice：" + payPrice + ",discount：" + discount + ",payMark：" + out_trade_no +
                     * ",payTid：" + transaction_id); }
                     */
                    // / 如果多个定单一起去支付的话，支付方式都是一样的,返回第一个定单编号
                    OrderEntity oe = odi.findOrderById(orderIds.get(0));
                    String validTimeStr = odi.findValidTimeByOid(orderIds.get(0));
                    if (oe != null)
                    {
                        orderNumber = oe.getNumber();
                        paytype = oe.getPayChannel() + "";
                        orderId = oe.getId() + "";
                    }
                    if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
                    {
                        Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                        if (new Date().before(validTime)) // 订单锁定未过期
                        {
                            endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                        }
                    }
                }
                result.addProperty("orderId", orderId);
                result.addProperty("orderNumber", orderNumber);
                result.addProperty("paytype", paytype);
                result.addProperty("endSecond", endSecond); // 支付失败后，显示
                result.addProperty("totalPrice", totalPrice);
                result.addProperty("transactionTime", CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss")); // 交易时间
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); // 是否支付成功
                if (orderIds != null && orderIds.size() > 1)
                {
                    result.addProperty("hasMoreThanOneOrder", "1"); // 是否有多个定单
                    result.addProperty("failOrderCount", (orderIds.size() - 1) + "");
                    // 加入orderIds 用于百度订单统计
                    result.addProperty("pushOrderIds", JSON.toJSONString(orderIds));
                }
                else
                    result.addProperty("hasMoreThanOneOrder", "0");
                log.info("-----------weixinReturnCallBack----------success-------------");
            }
            else
            {
                log.error("查询验证签名失败或业务错误");
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            }
        }
        else
        {
            log.error("认证签名失败");
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        
        return result.toString();
    }
    
    @Override
    public String payrequestweixin(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException
    {
        
        return null;
    }
    
    public String aliPayFailInWeXin(String requestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String reqOrderIds = param.get("orderIds").getAsString();
        String[] oIds = reqOrderIds.split(",");
        List<Integer> orderIds = new ArrayList<Integer>();
        for (String oId : oIds)
            orderIds.add(new Integer(oId));
        
        processAliReturnCallFun(result, orderIds, "");
        
        return result.toString();
    }
    
    @Override
    public String aliReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception
    {
        JsonObject result = new JsonObject();
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
        {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
            {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String transStatus = new String(request.getParameter("result").getBytes("ISO-8859-1"), "UTF-8");
        
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        // 计算得出通知验证结果
        boolean verify_result = AlipayNotify.verifyReturn(params);
        
        if (verify_result)
        {// 验证成功
         // ////////////////////////////////////////////////////////////////////////////////////////
         // 请在这里加上商户的业务逻辑程序代码
            log.info("--ali---returncallback-验证成功");
            log.info("--ali---out_trade_no--is:" + out_trade_no);
            List<Integer> orderIds = new ArrayList<Integer>();
            List<OrderAliPayEntity> owpes = orderAliPayDao.findOrderAliPayByMark(out_trade_no);
            for (OrderAliPayEntity owpe : owpes)
            {
                orderIds.add(owpe.getOrderId());
            }
            
            /*
             * 
             * String endSecond = "-1" ; String orderNumber ="" ; String paytype = "" ; String orderId = ""; float
             * oldPayPrice =0f; if(orderIds.size() > 0 ) { oldPayPrice = odi.findSumPriceByOrderIds(orderIds);
             * log.info("oldPayPrice :"+ oldPayPrice);
             * 
             * /// 如果多个定单一起去支付的话，支付方式都是一样的,返回第一个定单编号 OrderEntity oe = odi.findOrderById(orderIds.get(0)) ; String
             * validTimeStr = odi.findValidTimeByOid(orderIds.get(0)) ; if(oe!=null) { orderNumber = oe.getNumber() ;
             * paytype = oe.getPayChannel()+"" ; orderId = oe.getId()+"" ; }
             * 
             * if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息 { Date validTime =
             * CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss"); if (new Date().before(validTime)) // 订单锁定未过期
             * { endSecond = (validTime.getTime() - new Date().getTime())/1000 + ""; } } } result.addProperty("orderId",
             * orderId); result.addProperty("orderNumber", orderNumber); result.addProperty("paytype", paytype);
             * result.addProperty("endSecond", endSecond); // 支付失败后，显示 result.addProperty("totalPrice",
             * decimalFormat.format(oldPayPrice) ) ; result.addProperty("transactionTime", CommonUtil.date2String(new
             * Date(), "yyyy-MM-dd HH:mm:ss")); // 交易时间 result.addProperty("status",
             * CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); // 是否支付成功 result.addProperty("trade_no",
             * trade_no);
             * 
             * if(orderIds!=null && orderIds.size() >1) result.addProperty("hasMoreThanOneOrder", "1"); // 是否有多个定单 else
             * result.addProperty("hasMoreThanOneOrder", "0");
             */
            processAliReturnCallFun(result, orderIds, trade_no);
            // ////////////////////////////////////////////////////////////////////////////////////////
        }
        else
        {
            // 该页面可做页面美工编辑
            log.error("--ali---returncallback---验证失败--");
        }
        return result.toString();
    }
    
    @Override
    public String aliGlobalReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception
    {
        JsonObject result = new JsonObject();
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
        {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
            {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        // 清算币种
        String currency = new String(request.getParameter("currency").getBytes("ISO-8859-1"), "UTF-8");
        // 外币金额
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        // 计算得出通知验证结果
        boolean verify_result = AlipayNotify.verifyGlobal(params);
        if (verify_result)
        {
            log.info("--ali---aliGlobalReturnCallBack-验证成功");
            log.info("--ali---out_trade_no--is:" + out_trade_no);
            List<Integer> orderIds = new ArrayList<Integer>();
            List<OrderAliPayEntity> owpes = orderAliPayDao.findOrderAliPayByMark(out_trade_no);
            for (OrderAliPayEntity owpe : owpes)
            {
                orderIds.add(owpe.getOrderId());
            }
            processAliReturnCallFun(result, orderIds, trade_no);
        }
        else
        {
            // 该页面可做页面美工编辑
            log.error("--ali---aliGlobalReturnCallBack---验证失败--");
        }
        return result.toString();
    }
    
    private void processAliReturnCallFun(JsonObject result, List<Integer> orderIds, String trade_no)
    {
        String endSecond = "-1";
        String orderNumber = "";
        String paytype = "";
        String orderId = "";
        float oldPayPrice = 0f;
        
        List<OrderEntity> orders = odi.findAllOrderStatusByIds(orderIds);
        boolean status = true;
        if (orders != null)
        {
            for (OrderEntity oe : orders)
            {
                if (oe.getStatus() == Byte.parseByte("1")) // 未付
                {
                    status = false;
                    break;
                }
            }
        }
        if (orderIds.size() > 0)
        {
            oldPayPrice = odi.findSumPriceByOrderIds(orderIds);
            log.info("oldPayPrice :" + oldPayPrice);
            
            // / 如果多个定单一起去支付的话，支付方式都是一样的,返回第一个定单编号
            OrderEntity oe = odi.findOrderById(orderIds.get(0));
            String validTimeStr = odi.findValidTimeByOid(orderIds.get(0));
            if (oe != null)
            {
                orderNumber = oe.getNumber();
                paytype = oe.getPayChannel() + "";
                orderId = oe.getId() + "";
            }
            
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 订单锁定未过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
        }
        log.info("processAliReturnCallFun ---orderId is: " + orderId);
        result.addProperty("orderId", orderId);
        result.addProperty("orderNumber", orderNumber);
        result.addProperty("paytype", paytype);
        result.addProperty("endSecond", endSecond); // 支付失败后，显示
        result.addProperty("totalPrice", decimalFormat.format(oldPayPrice));
        result.addProperty("transactionTime", CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss")); // 交易时间
        
        if (status) // if(trade_no==null || trade_no.equals(""))
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); // 是否支付成功
        else
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        
        result.addProperty("trade_no", trade_no);
        
        if (orderIds != null && orderIds.size() > 1)
        {
            result.addProperty("hasMoreThanOneOrder", "1"); // 是否有多个定单
            // 加入orderIds 用于百度订单统计
            result.addProperty("failOrderCount", (orderIds.size() - 1) + "");
            result.addProperty("pushOrderIds", JSON.toJSONString(orderIds));
        }
        else
            result.addProperty("hasMoreThanOneOrder", "0");
    }
    
    @Override
    public String aliNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception
    {
        
        log.info("----------aliNotifyCallBack---start");
        JsonObject result = new JsonObject();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
        {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
            {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        
        // RSA签名解密
        if (AlipayConfig.sign_type.equals("0001"))
        {
            try
            {
                params = AlipayNotify.decrypt(params);
            }
            catch (Exception e)
            {
                log.error("aliNotifyCallBack---RSA签名解密", e);
                throw e;
            }
        }
        // XML解析notify_data数据
        Document doc_notify_data = null;
        try
        {
            doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
        }
        catch (DocumentException e)
        {
            log.error("aliNotifyCallBack---XML解析notify_data数据", e);
            throw e;
        }
        
        // 商户订单号
        String out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
        this.log.info("aliNotifyCallBack----payMark--is: " + out_trade_no);
        // 支付宝交易号
        String trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
        
        // 交易状态
        String trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
        log.info("aliNotifyCallBack,trade_status:" + trade_status);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        if ("TRADE_SUCCESS".equals(trade_status))
        {
            if (AlipayNotify.verifyNotify(params))
            {// 验证成功
             // ////////////////////////////////////////////////////////////////////////////////////////
                
                Map<String, Object> pparams = new HashMap<String, Object>();
                pparams.put("payMark", out_trade_no);
                pparams.put("payId", trade_no);
                pparams.put("paychannel", "2");
                try
                {
                    processPayNotifyBack(pparams);
                }
                catch (ServiceException se)
                {
                    log.error("--processPayNotifyBack---", se);
                    throw se;
                }
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            }
            else
            {// 验证失败
                log.error("ali----Notifycallback--fail");
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            }
        }
        else if ("TRADE_FINISHED".equals(trade_status))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        else
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        return result.toString();
    }
    
    @Override
    public String aliGlobalNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception
    {
        log.info("----------aliGlobalNotifyCallBack---start");
        JsonObject result = new JsonObject();
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();)
        {
            String name = (String)iter.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++)
            {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        // 清算币种
        String currency = new String(request.getParameter("currency").getBytes("ISO-8859-1"), "UTF-8");
        // 外币金额
        String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        
        log.info("aliGlobalNotifyCallBack,trade_status:" + trade_status);
        if ("TRADE_FINISHED".equals(trade_status)) // "TRADE_SUCCESS".equals(trade_status)
        {
            if (AlipayNotify.verifyGlobal(params))
            {// 验证成功
             // ////////////////////////////////////////////////////////////////////////////////////////
                
                Map<String, Object> pparams = new HashMap<>();
                pparams.put("payMark", out_trade_no);
                pparams.put("payId", trade_no);
                pparams.put("paychannel", "2");
                try
                {
                    processPayNotifyBack(pparams);
                }
                catch (ServiceException se)
                {
                    log.error("--aliGlobalNotifyCallBack---", se);
                    throw se;
                }
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            }
            else
            {// 验证失败
                log.error("ali----aliGlobalNotifyCallBack--fail");
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            }
        }
        // else if ("TRADE_FINISHED".equals(trade_status))
        // {
        // result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        // }
        else
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        
        return result.toString();
    }
    
    private void processPayNotifyBack(Map<String, Object> params)
        throws ServiceException
    {
        
        if (params == null || params.isEmpty())
            return;
        String payMark = params.get("payMark") + "";
        String payId = params.get("payId") + "";
        String paychannel = (params.get("paychannel") == null ? "" : params.get("paychannel") + "");
        String errorMsg = "";
        List<Integer> orderIds = new ArrayList<Integer>();
        log.info("processPayNotifyBack异步回调接口-------payMark---is:" + payMark);
        if (paychannel != null && paychannel.equals("3")) // 微信
        {
            List<OrderWeixinPayEntity> owpes = orderWeixinPayDao.findOrderWeixinPayByMark(payMark);
            for (OrderWeixinPayEntity owpe : owpes)
            {
                orderIds.add(owpe.getOrderId());
            }
            for (OrderWeixinPayEntity owpe : owpes) // 更新订单微信支付,更新订单
            {
                if (owpe.getIsPay() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 未付款
                {
                    owpe.setPayTid(payId);
                    owpe.setIsPay(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
                    if (orderWeixinPayDao.updateOrderWeixinPay(owpe) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        errorMsg = "updateOrderWeixinPay没有匹配,payMark：" + payMark + ",payTid：" + payId + ",orderId：" + owpe.getOrderId();
                        log.error(errorMsg);
                        ServiceException se = new ServiceException(errorMsg);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        throw se;
                        
                    }
                    if (odi.updateOrderNotDelivery(owpe.getOrderId()) == 0)
                    {
                        // ConnectionManager.rollbackTransaction();
                        errorMsg = "updateOrderNotDelivery没有匹配,payMark：" + payMark + ",payTid：" + payId + ",orderId：" + owpe.getOrderId();
                        log.error(errorMsg);
                        ServiceException se = new ServiceException(errorMsg);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        throw se;
                    }
                }
                else
                // 已付款的不在处理
                {
                    orderIds.remove(new Integer(owpe.getOrderId()));
                }
            }
        }
        else if (paychannel != null && paychannel.equals("1")) // 银联支付
        {
            String payPrice = params.get("txnAmt") + "";
            List<OrderUnionPayEntity> oupes = orderUnionPayDao.findOrderUnionPayByMark(payMark);
            for (OrderUnionPayEntity oupe : oupes)
            {
                orderIds.add(oupe.getOrderId());
            }
            
            float oldPayPrice = odi.findSumPriceByOrderIds(orderIds);
            if ((Math.round(oldPayPrice * 100) - Float.parseFloat(payPrice)) > 5f) // 误差5分之内
            {
                log.error("金额不匹配,oldPayPrice：" + oldPayPrice + ",payPrice：" + payPrice + ",payMark：" + payMark);
                return;
            }
            
            String payTid = payId;
            for (OrderUnionPayEntity oupe : oupes) // 更新订单微信支付,更新订单
            {
                if (oupe.getIsPay() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 未付款
                {
                    oupe.setPayTid(payTid);
                    oupe.setIsPay(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
                    if (orderUnionPayDao.updateOrderUnionPay(oupe) == 0)
                    {
                        
                        log.error("updateOrderWeixinPay没有匹配,payMark：" + payMark + ",payTid：" + payTid + ",orderId：" + oupe.getOrderId());
                        return;
                    }
                    if (odi.updateOrderNotDelivery(oupe.getOrderId()) == 0)
                    {
                        log.error("updateOrderNotDelivery没有匹配,payMark：" + payMark + ",payTid：" + payTid + ",orderId：" + oupe.getOrderId());
                        return;
                    }
                }
                else
                // 已付款的不在处理
                {
                    orderIds.remove(new Integer(oupe.getOrderId()));
                }
            }
        }
        
        if (orderIds.size() > 0)
        {
            int accountId = 0;
            
            StringBuffer numbers = new StringBuffer();
            
            if (!processPointRecord(orderIds, payMark)) // 处理积分方法 & 处理推荐用户首次下单及合伙人返积分
            {
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                throw se;
            }
            
            Set<Integer> productIdset = new HashSet<>();
            List<OrderEntity> orderLists = new ArrayList<OrderEntity>();
            //订单总金额
            float totalPrice = 0f;
            //总奖励
            float totalDraw = 0f;
            for (Integer orderId : orderIds)
            {
                productIdset.addAll(odi.findLockProductIdsByOId(orderId));
                OrderEntity oe = odi.findOrderById(orderId);
                accountId = oe.getAccountId();
                numbers.append(oe.getNumber() + ",");
                //订单总金额
                totalPrice += oe.getRealPrice();
                ;
                // 佣金变更 2016年4月14日 zhangld
                float orderDraw = 0f;
                List<Map<String, Object>> orderProducts = odi.findOrderProductInfosByOId(orderId);
                for (Map<String, Object> mp : orderProducts)
                {
                    int productId = Integer.parseInt(mp.get("product_id") + "");
                    int count = Integer.parseInt(mp.get("product_count") + "");
                    float pruductDraw = odi.getHqbsDraw(productId) * count;
                    //订单奖励
                    orderDraw += pruductDraw;
                    //总奖励
                    totalDraw += pruductDraw;
                    
                }
                oe.setHqbsDraw(orderDraw);
                orderLists.add(oe);
            }
            
            List<Integer> productIds = new ArrayList<>();
            productIds.addAll(productIdset);
            List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds); // 一次锁定多个商品id
            Map<Integer, ProductCountEntity> pceMap = new HashMap<>();
            for (ProductCountEntity pce : pces)
            {
                pceMap.put(pce.getProductId(), pce);
            }
            
            for (Integer orderId : orderIds) // 删除订单锁定数量,更新商品数量,更新订单锁定时间
            {
                List<Map<String, Object>> lockProductInfos = odi.findLockCountInfosByOId(orderId);
                for (Map<String, Object> map : lockProductInfos)
                {
                    int productId = ((Long)map.get("product_id")).intValue();
                    int productCount = (Integer)map.get("product_count");
                    if (odi.deleteLockCount(productId, orderId) == 0)
                    {
                        errorMsg = "deleteLockCount没有匹配,payMark：" + payMark + ",payTid：" + payId + ",orderId：" + orderId + ",productId：" + productId;
                        log.error(errorMsg);
                        ServiceException se = new ServiceException(errorMsg);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        throw se;
                        
                    }
                    // 多个相同商品不会出错
                    ProductCountEntity pce = pceMap.get(productId);
                    pce.setLock(pce.getLock() - productCount);
                    pce.setStock(pce.getStock() - productCount);
                    pce.setSell(pce.getSell() + productCount);
                    if (false &&pcdi.updateCountInfo(pce) == 0 && false)
                    {
                        errorMsg = "updateCountInfo没有匹配,payMark：" + payMark + ",payTid：" + payId + ",orderId：" + orderId + ",productId：" + productId;
                        log.error(errorMsg);
                        ServiceException se = new ServiceException(errorMsg);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        throw se;
                        
                    }
                    if (pcommondi.updateSellCountById(productId, pce.getSell()) == 0&&false) // 更新通用商品的数量
                    {
                        log.error("updateSellCountById没有匹配,payMark：" + payMark + ",payTid：" + payId + ",orderId：" + orderId + ",productId：" + productId);
                        return;
                    }
                }
                if (odi.updateLockTime(orderId, System.currentTimeMillis()) == 0&&false)
                {
                    errorMsg = "updateLockTime没有匹配,payMark：" + payMark + ",payTid：" + payId + ",orderId：" + orderId;
                    log.error(errorMsg);
                    ServiceException se = new ServiceException(errorMsg);
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    throw se;
                    
                }
            }
            
            // 付款成功，设置订单发货超时时间
            try
            {
                updateOrderExpireTime(orderIds);
            }
            catch (Exception e)
            {
                log.error("更新订单发货超时时间发生异常", e);
            }
            
            // 左岸城堡订单需要推送消息
            try
            {
                insertFansOrder(orderLists, accountId, numbers.toString(), totalPrice, totalDraw);
            }
            catch (Exception e)
            {
                log.error("左岸城堡插入粉丝订单处理出错", e);
            }
            try
            {
                insertSp(accountId, totalPrice);
            }
            catch (Exception e)
            {
                log.error("左岸城堡订单大于等于299,插入代言人表处理出错", e);
            }
        }
        log.info("处理即时到账支付回调成功,payMark：" + payMark);
    }
    
    private void updateOrderExpireTime(List<Integer> orderIds)
        throws Exception
    {
        for (Integer orderId : orderIds)
        {
            OrderEntity order = odi.findOrderById(orderId);
            if (StringUtils.isNotEmpty(order.getPayTime()))
            {
                DateTime beginTime = new DateTime(CommonUtil.string2Date(order.getPayTime(), "yyyy-MM-dd HH:mm:ss").getTime());
                DateTime endTime = null;
                SellerEntity seller = sdi.findSellerById(order.getSellerId());
                if (seller != null)
                {
                    
                    // 当天15点前订单当天打包并提供物流单号，24小时内有物流信息，超时时间为当天24点
                    if (seller.getSendTimeType() == CommonEnum.SELLER_SENDTIMETYPE.IN_15_HOUR.getValue())
                    {
                        int plusDay = 0;
                        int dayOfHour = beginTime.getHourOfDay();
                        int dayOfWeek = beginTime.getDayOfWeek();
                        
                        if (seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.SEND_ON_SUNDAY.getValue())
                        {
                            // 周六不发货
                            if (dayOfWeek == 5)
                            {
                                if (dayOfHour >= 15)
                                {
                                    plusDay += 2;
                                }
                            }
                            else if (dayOfWeek == 6)
                            {
                                plusDay += 1;
                            }
                            else
                            {
                                if (dayOfHour >= 15)
                                {
                                    plusDay += 1;
                                }
                            }
                        }
                        else if (seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.SEND_ON_SATURDAY.getValue())
                        {
                            // 周天不发货
                            if (dayOfWeek == 6)
                            {
                                if (dayOfHour >= 15)
                                {
                                    plusDay += 2;
                                }
                            }
                            else if (dayOfWeek == 7)
                            {
                                plusDay += 1;
                            }
                            else
                            {
                                if (dayOfHour >= 15)
                                {
                                    plusDay += 1;
                                }
                            }
                        }
                        else if (seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.NOT_SEND_WEEKEND.getValue())
                        {
                            // 周末不发货
                            if (dayOfWeek == 5)
                            {
                                if (dayOfHour >= 15)
                                {
                                    plusDay += 3;
                                }
                            }
                            else if (dayOfWeek == 6)
                            {
                                plusDay += 2;
                            }
                            else if (dayOfWeek == 7)
                            {
                                plusDay += 1;
                            }
                            else
                            {
                                if (dayOfHour >= 15)
                                {
                                    plusDay += 1;
                                }
                            }
                        }
                        else
                        {
                            if (dayOfHour >= 15)
                            {
                                plusDay += 1;
                            }
                        }
                        endTime = beginTime.withTimeAtStartOfDay().plusDays(plusDay).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
                    }
                    else
                    {
                        int plusHour = 0;
                        if (seller.getSendTimeType() == CommonEnum.SELLER_SENDTIMETYPE.IN_24_HOUR.getValue())
                        {
                            plusHour = 24;
                        }
                        else if (seller.getSendTimeType() == CommonEnum.SELLER_SENDTIMETYPE.IN_48_HOUR.getValue())
                        {
                            plusHour = 48;
                        }
                        else if (seller.getSendTimeType() == CommonEnum.SELLER_SENDTIMETYPE.IN_72_HOUR.getValue())
                        {
                            plusHour = 72;
                        }
                        endTime = beginTime.plusHours(plusHour);
                        while (!beginTime.isAfter(endTime))
                        {
                            if (beginTime.getDayOfWeek() == 6)
                            {
                                if (seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.SEND_ON_SUNDAY.getValue()
                                    || seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.NOT_SEND_WEEKEND.getValue())
                                {
                                    endTime = endTime.plusHours(24);
                                }
                            }
                            if (beginTime.getDayOfWeek() == 7)
                            {
                                if (seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.SEND_ON_SATURDAY.getValue()
                                    || seller.getIsSendWeekend() == CommonEnum.SELLER_WEEKENDSENDTYPE.NOT_SEND_WEEKEND.getValue())
                                {
                                    endTime = endTime.plusHours(24);
                                }
                            }
                            beginTime = beginTime.plusDays(1);
                        }
                    }
                    
                    if (endTime != null)
                    {
                        odi.updateOrderExpireTime(orderId, endTime.toString("yyyy-MM-dd HH:mm:ss"));
                    }
                }
            }
        }
    }
    
    private boolean processPointRecord(List<Integer> orderIds, String payMark)
    {
        // log.info("*******原始orderIds：" + orderIds);
        // 订单不包括 积分商品黑名单 中的商品才能加积分。
        List<Integer> canAddIntegralList = new ArrayList<Integer>();
        for (Integer it : orderIds)
        {
            boolean exists = odi.existsProductBlacklist(it, 1);
            if (!exists)
            {
                canAddIntegralList.add(it);
            }
        }
        
        // log.info("*******过来后的orderIds：" + canAddIntegralList);
        if (canAddIntegralList.size() > 0)
        {
            Map<String, Object> payCouponMap = odi.findPayCouponByOrderIds(canAddIntegralList);
            int accountId = ((Long)payCouponMap.get("account_id")).intValue();
            int accountPoint = ((BigDecimal)payCouponMap.get("account_point")).intValue();
            // int accountCouponId = ((Long)payCouponMap.get("account_coupon_id")).intValue();
            float oldPayPrice = ((Double)payCouponMap.get("real_price")).floatValue();
            String appVersion = (String)payCouponMap.get("app_version");
            AccountEntity ae = adi.findAccountById(accountId);
            int availablePoint = ae.getAvailablePoint();
            AccountAvailablePointRecordEntity aapre = new AccountAvailablePointRecordEntity();
            aapre.setAccountId(accountId);
            
            accountPoint = Math.round(oldPayPrice);
            if (accountPoint > 0)
            {
                availablePoint += accountPoint;
                aapre.setOperatePoint(accountPoint);
                aapre.setTotalPoint(availablePoint);
                aapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.SHOPPING_BACK_POINT.getValue());
                aapre.setArithmeticType((byte)1);
                if (aaprdi.addAvailablePointRecords(aapre) == 0)
                {
                    log.error("addAvailablePointRecords失败,accountPoint：" + accountPoint + ",accountPoint：" + accountPoint + ",aapre:" + aapre);
                    return false;
                }
            }
            
            if (availablePoint != ae.getAvailablePoint())
            {
                ae.setAvailablePoint(availablePoint);
                if (adi.updateAccountById(ae) == 0)
                {
                    log.error("updateAccountById更新失败,accountId：" + ae.getId());
                    return false;
                }
            }
            
            // 处理推荐用户首次下单及合伙人返积分
            // AccountEntity ae =
            // adi.findAccountById(((Long)odi.findPayCouponByOrderIds(orderIds).get("account_id")).intValue());
            if (!processRecommended(ae, canAddIntegralList, payMark, appVersion, oldPayPrice, accountId))
                return false;
        }
        
        return true;
    }
    
    /**
     * 查询 合伙人（fatherPartnerAccountId） 的上级（培养合伙人总数大于10个）的所有合伙人
     * 
     * @param fatherPartnerAccountId
     * @return 按顺序依次是上级 、上上级 、上上上级
     */
    private List<Integer> findPartnerTrainRelation(int fatherPartnerAccountId)
    {
        List<Integer> pts = new ArrayList<>();
        try
        {
            Map<String, Object> superiorPartnerTrainInfo = adi.findPartnerTrainQuantityInfo(fatherPartnerAccountId);
            int superiorPartnerTrainQuantity = Integer.valueOf(superiorPartnerTrainInfo.get("total") == null ? "0" : superiorPartnerTrainInfo.get("total") + "");
            int fatherAccountId = Integer.valueOf(superiorPartnerTrainInfo.get("fatherAccountId") == null ? "-1" : superiorPartnerTrainInfo.get("fatherAccountId") + "");
            // 上级培养人
            AccountEntity superiorFatherPa = null;
            if (fatherAccountId != -1)
            {
                if (superiorPartnerTrainQuantity >= 10)
                {
                    // 满足合伙人永久收益条件
                    superiorFatherPa = adi.findAccountById(fatherAccountId);
                    if (superiorFatherPa.getPartnerStatus() != CommonEnum.PARTNER_STATUS.DISABLED_PARTNER.getValue())
                    {
                        pts.add(fatherAccountId);
                    }
                }
                // 继续找上级培养合伙人
                pts.addAll(findPartnerTrainRelation(fatherAccountId));
            }
        }
        catch (Exception e)
        {
            log.error("查询(培养合伙人总数大于10个）的所有合伙人失败！", e);
        }
        return pts;
    }
    
    private boolean processRecommended(AccountEntity ae, List<Integer> orderIds, String payMark, String appVersion, float oldPayPrice, int accountId)
    {
        // 处理推荐用户首次下单及合伙人返积分
        if (ae.getIsRecommended() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
        {
            // 上级合伙人 和 上级推荐人 信息
            int fatherPartnerAccountId = adi.findFatherPartnerAccountIdById(ae.getId());
            int fatherRecommendedAccountId = adi.findFatherRecommendedAccountIdById(ae.getId());
            if (fatherPartnerAccountId == CommonConstant.ID_NOT_EXIST)
            {
                // 没有上级合伙人 判断 自己是不是合伙人 ，是的话，返积分就返给自己
                if (ae.getPartnerStatus() == CommonEnum.PARTNER_STATUS.IS_PARTNER.getValue())
                {
                    fatherPartnerAccountId = ae.getId();
                }
            }
            
            // 当前合伙人的上级培养人列表 且 培养人培养了大于等于10位的合伙人
            // 按顺序依次是上级 、上上级 、上上上级
            List<Integer> partnerTrainAccountIds = new ArrayList<>();
            if (fatherPartnerAccountId != CommonConstant.ID_NOT_EXIST)
            {
                partnerTrainAccountIds = findPartnerTrainRelation(fatherPartnerAccountId);
            }
            
            if (ae.getIsHasOrder() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 首次购买
            {
                float historyRealPrice = odi.findAllRealPriceByAid(accountId);
                if (appVersion.equals("1.3") || (historyRealPrice + oldPayPrice) > 30f) // 1.3版本或者实付金额累计大于30元
                {
                    int subFirstOrderReturnPoint = appVersion.equals("1.3") ? CommonConstant.V13_SUB_FIRST_ORDER_RETURN_POINT : CommonConstant.V14_SUB_FIRST_ORDER_RETURN_POINT;
                    // int fatherRecommendedAccountId = adi.findFatherRecommendedAccountIdById(ae.getId());
                    if (fatherRecommendedAccountId != CommonConstant.ID_NOT_EXIST && !adi.isAccountBlack(fatherRecommendedAccountId))
                    {
                        AccountEntity fatherRecommendedAe = adi.findAccountById(fatherRecommendedAccountId);
                        AccountAvailablePointRecordEntity aapre = new AccountAvailablePointRecordEntity();
                        aapre.setAccountId(fatherRecommendedAccountId);
                        aapre.setOperatePoint(subFirstOrderReturnPoint);
                        aapre.setTotalPoint(fatherRecommendedAe.getAvailablePoint() + subFirstOrderReturnPoint);
                        aapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.BUDDY_FIRST_ORDER.getValue());
                        aapre.setArithmeticType((byte)1);
                        if (aaprdi.addAvailablePointRecords(aapre) == 0)
                        {
                            log.error("addAvailablePointRecords失败,payMark：" + payMark + ",aapre:" + aapre);
                            // /ServiceException se = new ServiceException();
                            // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            // throw se;
                            return false;
                        }
                        
                        fatherRecommendedAe.setAvailablePoint(fatherRecommendedAe.getAvailablePoint() + subFirstOrderReturnPoint);
                        fatherRecommendedAe.setRecommendedOrderCount(fatherRecommendedAe.getRecommendedOrderCount() + 1);
                        fatherRecommendedAe.setTotalRecommendedPoint(fatherRecommendedAe.getTotalRecommendedPoint() + subFirstOrderReturnPoint);
                        if (adi.updateAccountById(fatherRecommendedAe) == 0)
                        {
                            log.error("updateAccountById更新失败,payMark：" + payMark + ",fatherRecommendedAe:" + fatherRecommendedAe);
                            // ServiceException se = new ServiceException();
                            // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            // throw se;
                            return false;
                        }
                        
                        // 首次下单，若邀请人是合伙人，判断合伙人永久收益
                        if (partnerTrainAccountIds.size() > 0 && fatherPartnerAccountId == fatherRecommendedAccountId)
                        {
                            int currSubFirstOrderReturnPoint = subFirstOrderReturnPoint;
                            for (int i = 0; i < partnerTrainAccountIds.size(); i++)
                            {
                                currSubFirstOrderReturnPoint = Long.valueOf(Math.round(currSubFirstOrderReturnPoint / 10.0)).intValue();
                                if (currSubFirstOrderReturnPoint == 0)
                                {
                                    break;
                                }
                                int currSuperiorFatherId = partnerTrainAccountIds.get(i);
                                AccountEntity currSuperiorFatherAe = adi.findAccountById(currSuperiorFatherId);
                                AccountAvailablePointRecordEntity currAapre = new AccountAvailablePointRecordEntity();
                                currAapre.setAccountId(currSuperiorFatherId);
                                currAapre.setOperatePoint(currSubFirstOrderReturnPoint);
                                currAapre.setTotalPoint(currSuperiorFatherAe.getAvailablePoint() + currSubFirstOrderReturnPoint);
                                currAapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.BUDDY_PERPETUAL_ORDER.getValue());
                                currAapre.setArithmeticType((byte)1);
                                if (aaprdi.addAvailablePointRecords(currAapre) == 0)
                                {
                                    log.error("addAvailablePointRecords失败,payMark：" + payMark + ",currAapre:" + currAapre);
                                    // /ServiceException se = new ServiceException();
                                    // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                                    // throw se;
                                    return false;
                                }
                                
                                currSuperiorFatherAe.setAvailablePoint(currSuperiorFatherAe.getAvailablePoint() + currSubFirstOrderReturnPoint);
                                currSuperiorFatherAe.setTotalRecommendedPoint(currSuperiorFatherAe.getTotalRecommendedPoint() + currSubFirstOrderReturnPoint);
                                if (adi.updateAccountById(currSuperiorFatherAe) == 0)
                                {
                                    log.error("updateAccountById更新失败,payMark：" + payMark + ",currSuperiorFatherAe:" + currSuperiorFatherAe);
                                    // ServiceException se = new ServiceException();
                                    // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                                    // throw se;
                                    return false;
                                }
                            }
                        }
                        
                        int index = 1;
                        for (Integer orderId : orderIds)
                        {
                            AccountRecommendedReturnPointEntity arrpe = new AccountRecommendedReturnPointEntity();
                            arrpe.setAccountId(fatherRecommendedAccountId);
                            arrpe.setIsFirstRecommended((byte)1);
                            arrpe.setOrderId(orderId);
                            arrpe.setPoint(0);
                            if (index == 1)
                            {
                                arrpe.setPoint(subFirstOrderReturnPoint);
                            }
                            arrpe.setRecommendedAccountId(ae.getId());
                            arrpe.setType((byte)1); // 首次下单
                            if (adi.addAccountRecommendedReturnPoint(arrpe) == 0)
                            {
                                log.warn("addAccountRecommendedReturnPoint失败,payMark：" + payMark + ",arrpe:" + arrpe);
                            }
                            
                            // 首次下单，若邀请人是合伙人，判断合伙人永久收益
                            if (partnerTrainAccountIds.size() > 0 && fatherPartnerAccountId == fatherRecommendedAccountId)
                            {
                                int currSubFirstOrderReturnPoint = subFirstOrderReturnPoint;
                                for (int i = 0; i < partnerTrainAccountIds.size(); i++)
                                {
                                    currSubFirstOrderReturnPoint = Long.valueOf(Math.round(currSubFirstOrderReturnPoint / 10.0)).intValue();
                                    if (currSubFirstOrderReturnPoint == 0)
                                    {
                                        break;
                                    }
                                    int currSuperiorFatherId = partnerTrainAccountIds.get(i);
                                    AccountRecommendedReturnPointEntity currArrpe = new AccountRecommendedReturnPointEntity();
                                    currArrpe.setAccountId(currSuperiorFatherId);
                                    currArrpe.setIsFirstRecommended((byte)0);
                                    currArrpe.setOrderId(orderId);
                                    currArrpe.setPoint(0);
                                    if (index == 1)
                                    {
                                        currArrpe.setPoint(currSubFirstOrderReturnPoint);
                                    }
                                    currArrpe.setRecommendedAccountId(ae.getId());
                                    currArrpe.setType((byte)3); // 合伙人永久收益
                                    if (adi.addAccountRecommendedReturnPoint(currArrpe) == 0)
                                    {
                                        log.warn("addAccountRecommendedReturnPoint失败,payMark：" + payMark + ",currArrpe:" + currArrpe);
                                    }
                                }
                            }
                            index++;
                        }
                        ae.setIsHasOrder(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
                    }
                }
            }
            
            if (fatherPartnerAccountId != CommonConstant.ID_NOT_EXIST)
            {
                byte isFirstRecommended = 0;
                if (fatherRecommendedAccountId == fatherPartnerAccountId)
                {
                    isFirstRecommended = 1;
                }
                AccountEntity fatherAe = adi.findAccountById(fatherPartnerAccountId);
                if (fatherAe.getPartnerStatus() != CommonEnum.PARTNER_STATUS.DISABLED_PARTNER.getValue())
                {
                    int totalReturnPoint = 0;
                    Map<String, Integer> superiorTotalReturnPointMap = new HashMap<>();
                    for (Integer orderId : orderIds)
                    {
                        int returnPoint = 0;
                        List<Map<String, Object>> orderProductInfos = odi.findOrderProductInfosByOId(orderId);
                        for (Map<String, Object> map : orderProductInfos)
                        {
                            int productId = Integer.valueOf(map.get("product_id") + "");
                            int productCount = (Integer)map.get("product_count");
                            float salesPrice = (Float)map.get("salesPrice");
                            float partnerDistributionPrice = (Float)map.get("partner_distribution_price");
                            ProductEntity pe = pdi.findProductInfoById(productId);
                            if (pe.getReturnDistributionProportionType() == BrandReturnDistributionProportionTypeEnum.PROPORTION_100.ordinal())
                            {
                                returnPoint += (Math.round(salesPrice - partnerDistributionPrice) * 100) * productCount;
                                totalReturnPoint += (Math.round(salesPrice - partnerDistributionPrice) * 100) * productCount;
                            }
                            else
                            {
                                returnPoint += (Math.round((salesPrice - partnerDistributionPrice) * 100) / 4) * productCount;
                                totalReturnPoint += (Math.round((salesPrice - partnerDistributionPrice) * 100) / 4) * productCount;
                            }
                        }
                        if (returnPoint > 0)
                        {
                            AccountRecommendedReturnPointEntity arrpe = new AccountRecommendedReturnPointEntity();
                            arrpe.setAccountId(fatherPartnerAccountId);
                            arrpe.setIsFirstRecommended(isFirstRecommended);
                            arrpe.setOrderId(orderId);
                            arrpe.setPoint(returnPoint);
                            arrpe.setRecommendedAccountId(ae.getId());
                            arrpe.setType((byte)2); // 重复下单
                            if (adi.addAccountRecommendedReturnPoint(arrpe) == 0)
                            {
                                log.warn("addAccountRecommendedReturnPoint失败,payMark：" + payMark + ",arrpe:" + arrpe);
                            }
                            
                            // 合伙人永久收益
                            if (partnerTrainAccountIds.size() > 0)
                            {
                                int currSubFirstOrderReturnPoint = returnPoint;
                                for (int i = 0; i < partnerTrainAccountIds.size(); i++)
                                {
                                    currSubFirstOrderReturnPoint = Long.valueOf(Math.round(currSubFirstOrderReturnPoint / 10.0)).intValue();
                                    if (currSubFirstOrderReturnPoint == 0)
                                    {
                                        break;
                                    }
                                    int currSuperiorFatherId = partnerTrainAccountIds.get(i);
                                    int currTotalSubFirstOrderReturnPoint =
                                        Integer.valueOf(superiorTotalReturnPointMap.get(currSuperiorFatherId + "") == null ? "0"
                                            : superiorTotalReturnPointMap.get(currSuperiorFatherId + "") + "");
                                    superiorTotalReturnPointMap.put(currSuperiorFatherId + "", currTotalSubFirstOrderReturnPoint + currSubFirstOrderReturnPoint);
                                    
                                    byte currIsFirstRecommended = 0;
                                    if (fatherRecommendedAccountId == currSuperiorFatherId)
                                    {
                                        currIsFirstRecommended = 1;
                                    }
                                    AccountRecommendedReturnPointEntity currAapre = new AccountRecommendedReturnPointEntity();
                                    currAapre.setAccountId(currSuperiorFatherId);
                                    currAapre.setIsFirstRecommended(currIsFirstRecommended);
                                    currAapre.setOrderId(orderId);
                                    currAapre.setPoint(currSubFirstOrderReturnPoint);
                                    currAapre.setRecommendedAccountId(ae.getId());
                                    currAapre.setType((byte)3); // 合伙人永久收益
                                    if (adi.addAccountRecommendedReturnPoint(currAapre) == 0)
                                    {
                                        log.warn("addAccountRecommendedReturnPoint失败,payMark：" + payMark + ",currAapre:" + currAapre);
                                    }
                                }
                            }
                        }
                    }
                    if (totalReturnPoint > 0)
                    {
                        fatherAe.setAvailablePoint(fatherAe.getAvailablePoint() + totalReturnPoint);
                        fatherAe.setTotalRecommendedPoint(fatherAe.getTotalRecommendedPoint() + totalReturnPoint);
                        if (adi.updateAccountById(fatherAe) == 0)
                        {
                            log.error("updateAccountById更新失败,payMark：" + payMark + ",fatherAe:" + fatherAe);
                            // ServiceException se = new ServiceException();
                            // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            // throw se;
                            return false;
                        }
                        AccountAvailablePointRecordEntity aapre = new AccountAvailablePointRecordEntity();
                        aapre.setAccountId(fatherPartnerAccountId);
                        aapre.setOperatePoint(totalReturnPoint);
                        aapre.setTotalPoint(fatherAe.getAvailablePoint());
                        aapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.BUDDY_AGAIN_ORDER.getValue());
                        aapre.setArithmeticType((byte)1);
                        if (aaprdi.addAvailablePointRecords(aapre) == 0)
                        {
                            log.error("addAvailablePointRecords失败,payMark：" + payMark + ",aapre:" + aapre);
                            // ServiceException se = new ServiceException();
                            // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            // throw se;
                            return false;
                        }
                        
                        // 合伙人永久收益
                        if (partnerTrainAccountIds.size() > 0)
                        {
                            for (int i = 0; i < partnerTrainAccountIds.size(); i++)
                            {
                                int currSuperiorFatherId = partnerTrainAccountIds.get(i);
                                int currTotalSubFirstOrderReturnPoint =
                                    Integer.valueOf(superiorTotalReturnPointMap.get(currSuperiorFatherId + "") == null ? "0" : superiorTotalReturnPointMap.get(currSuperiorFatherId
                                        + "")
                                        + "");
                                if (currTotalSubFirstOrderReturnPoint > 0)
                                {
                                    AccountEntity currSuperiorFatherAe = adi.findAccountById(currSuperiorFatherId);
                                    currSuperiorFatherAe.setAvailablePoint(currSuperiorFatherAe.getAvailablePoint() + currTotalSubFirstOrderReturnPoint);
                                    currSuperiorFatherAe.setTotalRecommendedPoint(currSuperiorFatherAe.getTotalRecommendedPoint() + currTotalSubFirstOrderReturnPoint);
                                    if (adi.updateAccountById(currSuperiorFatherAe) == 0)
                                    {
                                        log.error("updateAccountById更新失败,payMark：" + payMark + ",currSuperiorFatherAe:" + currSuperiorFatherAe);
                                        // ServiceException se = new ServiceException();
                                        // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                                        // throw se;
                                        return false;
                                    }
                                    
                                    AccountAvailablePointRecordEntity currSuperAapre = new AccountAvailablePointRecordEntity();
                                    currSuperAapre.setAccountId(currSuperiorFatherAe.getId());
                                    currSuperAapre.setOperatePoint(currTotalSubFirstOrderReturnPoint);
                                    currSuperAapre.setTotalPoint(currSuperiorFatherAe.getAvailablePoint());
                                    currSuperAapre.setOperateType((byte)CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.BUDDY_PERPETUAL_ORDER.getValue());
                                    currSuperAapre.setArithmeticType((byte)1);
                                    if (aaprdi.addAvailablePointRecords(currSuperAapre) == 0)
                                    {
                                        log.error("addAvailablePointRecords失败,payMark：" + payMark + ",currSuperAapre:" + currSuperAapre);
                                        // ServiceException se = new ServiceException();
                                        // se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                                        // throw se;
                                        return false;
                                    }
                                }
                            }
                            
                        }
                    }
                }
            }
            
            if (adi.updateAccountById(ae) == 0) // 更新是否有下单
            {
                log.error("updateAccountById更新失败,payMark：" + payMark + ",ae:" + ae);
                return false;
            }
            
        }
        return true;
    }
    
    /**
     * 修改定单的状态，把未付改为待发货
     * 
     * @param params
     * @return
     * @throws ServiceException
     */
    public String modifyOrderStatus(Map<String, Object> params)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        List<Integer> orderIds = new ArrayList<>();
        String orderNum = params.get("orderIds") + ""; // 定单号
        String paychannel = "2"; // ( params.get("paychannel") == null ? "": params.get("paychannel")+"" ) ; // 支付类型
        if (orderNum == null || orderNum.equals(""))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", "0");
            return result.toString();
        }
        List<Integer> orderNums = new ArrayList<Integer>();
        String[] orderNumArray = orderNum.split(",");
        for (String oNum : orderNumArray)
        {
            orderNums.add(Integer.parseInt(oNum));
            
        }
        
        List<OrderEntity> orderEntities = this.odi.findNotPayOrderByOIdAndStatus(orderNums); // Dao方法
        if (orderEntities == null || orderEntities.isEmpty())
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", "1"); // 定单查不到
            return result.toString();
        }
        
        // List<Integer> orderIds = new ArrayList<Integer>();
        for (OrderEntity oe : orderEntities)
        {
            paychannel = oe.getPayChannel() + "";
            if (paychannel != null && paychannel.equals("3")) // 微信
            {
                List<OrderWeixinPayEntity> owpe = orderWeixinPayDao.findOrderWeixinPayByOrderId(oe.getId()); // Dao方法
                if (owpe != null && !owpe.isEmpty())
                {
                    for (OrderWeixinPayEntity owe : owpe)
                    {
                        owe.setIsPay(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
                        if (owe.getPayTid() == null)
                            owe.setPayTid("");
                        if (orderWeixinPayDao.updateOrderWeixinPay(owe) == 0)
                        {
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", "2"); // 支付接口更新报错
                            return result.toString();
                        }
                    }
                }
            }
            else if (paychannel != null && paychannel.equals("2")) // 支付宝
            {
                List<OrderAliPayEntity> owpe = orderAliPayDao.findOrderAliPayByOrderId(oe.getId()); // Dao方法
                if (owpe != null && !owpe.isEmpty())
                {
                    for (OrderAliPayEntity oap : owpe)
                    {
                        oap.setIsPay(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
                        if (oap.getPayTid() == null)
                            oap.setPayTid("");
                        if (orderAliPayDao.updateOrderAliPay(oap) == 0)
                        {
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", "2"); // 支付接口更新报错
                            return result.toString();
                        }
                    }
                }
            }
            else if (paychannel != null && paychannel.equals("1"))
            {
                List<OrderUnionPayEntity> owpe = orderUnionPayDao.findOrderUnionPayByOrderId(oe.getId()); // Dao方法
                if (owpe != null && !owpe.isEmpty())
                {
                    for (OrderUnionPayEntity oue : owpe)
                    {
                        oue.setIsPay(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
                        if (oue.getPayTid() == null)
                            oue.setPayTid("");
                        if (orderUnionPayDao.updateOrderUnionPay(oue) == 0)
                        {
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", "2"); // 支付接口更新报错
                            return result.toString();
                        }
                    }
                }
            }
            int updateOrderStatus = 0;
            if (oe.getPayTime() == null || "".equals(oe.getPayTime()) || "0000-00-00 00:00:00".equals(oe.getPayTime()))
            {
                updateOrderStatus = odi.updateOrderNotDelivery(oe.getId());
            }
            else
            {
                updateOrderStatus = odi.updateOrderStatus(oe.getId(), oe.getPayTime());
            }
            if (updateOrderStatus == 0) // 更新定单的状态为待发货
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", "3"); // 更新定单的状态为待发货 报错
                return result.toString();
            }
            
            if (updateOrderStatus > 0)
            {
                orderIds.add(oe.getId());
            }
            // orderIds.add(oe.getId()) ;
            byte status = oe.getStatus();
            if (status == Byte.parseByte("5") || status == Byte.parseByte("6"))
            {
                
                List<Map<String, Object>> orderProducts = this.odi.findOrderProductInfosByOId(oe.getId());
                if (orderProducts != null && !orderProducts.isEmpty())
                {
                    Map<String, Object> orderProduct = orderProducts.get(0);
                    int productId = Integer.parseInt(orderProduct.get("product_id") + "");
                    int porductCount = Integer.parseInt(orderProduct.get("product_count") + "");
                    ProductCountEntity pce = this.pcdi.findCountInfoById(productId);
                    if (pce.getStock() > 0)
                    {
                        pce.setStock(pce.getStock() - porductCount);
                    }
                    pce.setSell(pce.getSell() + porductCount);
                    if (pcdi.updateCountInfo(pce) == 0)
                    {
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", "5"); //
                        return result.toString();
                        
                    }
                    if (pcommondi.updateSellCountById(productId, pce.getSell()) == 0) // 更新通用商品的数量
                    {
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", "6"); //
                        return result.toString();
                    }
                    
                }
                
            }
            else if (status == Byte.parseByte("1"))
            {
                
                List<Integer> productIds = odi.findLockProductIdsByOId(oe.getId());
                List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds); // 一次锁定多个商品id
                Map<Integer, ProductCountEntity> pceMap = new HashMap<Integer, ProductCountEntity>();
                for (ProductCountEntity pce : pces)
                {
                    pceMap.put(pce.getProductId(), pce);
                }
                
                // for (Integer orderId : orderIds) // 删除订单锁定数量,更新商品数量,更新订单锁定时间
                // {
                List<Map<String, Object>> lockProductInfos = odi.findLockCountInfosByOId(oe.getId());
                for (Map<String, Object> map : lockProductInfos)
                {
                    int productId = ((Long)map.get("product_id")).intValue();
                    int productCount = (Integer)map.get("product_count");
                    if (odi.deleteLockCount(productId, oe.getId()) == 0)
                    {
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", "4"); //
                        return result.toString();
                    }
                    // 多个相同商品不会出错
                    ProductCountEntity pce = pceMap.get(productId);
                    pce.setLock(pce.getLock() - productCount);
                    pce.setStock(pce.getStock() - productCount);
                    pce.setSell(pce.getSell() + productCount);
                    if (pcdi.updateCountInfo(pce) == 0)
                    {
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", "5"); //
                        return result.toString();
                        
                    }
                    if (pcommondi.updateSellCountById(productId, pce.getSell()) == 0) // 更新通用商品的数量
                    {
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", "6"); //
                        return result.toString();
                    }
                }
                if (odi.updateLockTime(oe.getId(), System.currentTimeMillis()) == 0)
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", "7"); //
                    return result.toString();
                }
                // }
            }
            
        }
        
        // 付款成功，设置订单发货超时时间
        try
        {
            updateOrderExpireTime(orderIds);
        }
        catch (Exception e)
        {
            log.error("更新订单发货超时时间发生异常", e);
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String unionReturnCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception
    {
        JsonObject result = new JsonObject();
        request.setCharacterEncoding("ISO-8859-1");
        String encoding = request.getParameter(SDKConstants.param_encoding);
        log.info("银联返回报文中encoding=[" + encoding + "]");
        Map<String, String> respParam = getAllRequestParam(request);
        
        // 打印请求报文
        log.info("----unionReturnCallBack----respParam---" + respParam);
        
        Map<String, String> valideData = null;
        // StringBuffer page = new StringBuffer();
        if (null != respParam && !respParam.isEmpty())
        {
            Iterator<java.util.Map.Entry<String, String>> it = respParam.entrySet().iterator();
            valideData = new HashMap<String, String>(respParam.size());
            while (it.hasNext())
            {
                java.util.Map.Entry<String, String> e = it.next();
                String key = (String)e.getKey();
                String value = (String)e.getValue();
                value = new String(value.getBytes("ISO-8859-1"), encoding);
                // page.append("<tr><td width=\"30%\" align=\"right\">" + key+ "(" + key + ")</td><td>" + value +
                // "</td></tr>");
                valideData.put(key, value);
            }
        }
        log.info("-----unionReturnCallBack---valideData---" + valideData);
        String payMark = valideData.get("orderId");
        if (!SDKUtil.validate(valideData, encoding))
        {
            // page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
            log.error("银联验证签名结果[失败].");
        }
        else
        {
            // page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
            log.info("银联验证签名结果[成功].");
            
            List<Integer> orderIds = new ArrayList<Integer>();
            List<OrderUnionPayEntity> owpes = orderUnionPayDao.findOrderUnionPayByMark(payMark);
            for (OrderUnionPayEntity owpe : owpes)
            {
                orderIds.add(owpe.getOrderId());
            }
            String endSecond = "-1";
            String orderNumber = "";
            String paytype = "";
            String orderId = "";
            float oldPayPrice = 0f;
            if (orderIds.size() > 0)
            {
                oldPayPrice = odi.findSumPriceByOrderIds(orderIds);
                log.info("--unionReturnCallBack---oldPayPrice :" + oldPayPrice);
                
                // / 如果多个定单一起去支付的话，支付方式都是一样的,返回第一个定单编号
                OrderEntity oe = odi.findOrderById(orderIds.get(0));
                String validTimeStr = odi.findValidTimeByOid(orderIds.get(0));
                if (oe != null)
                {
                    orderNumber = oe.getNumber();
                    paytype = oe.getPayChannel() + "";
                    orderId = oe.getId() + "";
                }
                
                if (validTimeStr != null && !validTimeStr.equals("")) // 存在订单锁定时间 信息
                {
                    Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                    if (new Date().before(validTime)) // 订单锁定未过期
                    {
                        endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                    }
                }
            }
            result.addProperty("orderId", orderId);
            result.addProperty("orderNumber", orderNumber);
            result.addProperty("paytype", paytype);
            result.addProperty("endSecond", endSecond); // 支付失败后，显示
            result.addProperty("totalPrice", decimalFormat.format(oldPayPrice));
            result.addProperty("transactionTime", CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss")); // 交易时间
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); // 是否支付成功
            result.addProperty("trade_no", payMark);
            
            if (orderIds != null && orderIds.size() > 1)
            {
                result.addProperty("hasMoreThanOneOrder", "1"); // 是否有多个定单
                result.addProperty("failOrderCount", (orderIds.size() - 1) + "");
                // 加入orderIds 用于百度订单统计
                result.addProperty("pushOrderIds", JSON.toJSONString(orderIds));
            }
            else
                result.addProperty("hasMoreThanOneOrder", "0");
        }
        // req.setAttribute("result", page.toString());
        // req.getRequestDispatcher(pageResult).forward(req, resp);
        
        return result.toString();
    }
    
    @Override
    public String unionNotifyCallBack(HttpServletRequest request, HttpServletResponse response)
        throws ServiceException, Exception
    {
        
        log.info("银联unionNotifyCallBack接收后台通知开始");
        JsonObject result = new JsonObject();
        // 获取银联POST过来反馈信息
        // Map<String,String> params = new HashMap<String,String>();
        request.setCharacterEncoding("ISO-8859-1");
        String encoding = request.getParameter(SDKConstants.param_encoding);
        // 获取请求参数中所有的信息
        Map<String, String> reqParam = getAllRequestParam(request);
        // 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        log.info("银联unionNotifyCallBack－－－－reqParam is: " + reqParam);
        
        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty())
        {
            Iterator<java.util.Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());
            while (it.hasNext())
            {
                java.util.Map.Entry<String, String> e = it.next();
                String key = (String)e.getKey();
                String value = (String)e.getValue();
                value = new String(value.getBytes("ISO-8859-1"), encoding);
                valideData.put(key, value);
            }
        }
        
        // 验证签名
        if (!SDKUtil.validate(valideData, encoding))
        {
            log.error("--unionNotifyCallBack----验证签名结果[失败].");
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        }
        else
        {
            // System.out.println(valideData.get("orderId")); //其他字段也可用类似方式获取
            log.info("--unionNotifyCallBack--valideData--is:" + valideData);
            log.info("--unionNotifyCallBack----验证签名结果[成功].");
            String payMark = valideData.get("orderId");
            String txnAmt = valideData.get("txnAmt");
            String payId = valideData.get("queryId");
            Map<String, Object> pparams = new HashMap<String, Object>();
            pparams.put("payMark", payMark);
            pparams.put("payId", payId);
            pparams.put("paychannel", 1);
            pparams.put("txnAmt", txnAmt);
            try
            {
                processPayNotifyBack(pparams);
            }
            catch (ServiceException se)
            {
                log.error("--processPayNotifyBack---", se);
                throw se;
            }
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        }
        
        return result.toString();
    }
    
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request)
    {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp)
        {
            while (temp.hasMoreElements())
            {
                String en = (String)temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                // System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
                if (null == res.get(en) || "".equals(res.get(en)))
                {
                    res.remove(en);
                }
            }
        }
        return res;
    }
    
    @Override
    public String logisticsDetail(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String orderId = param.get("orderId").getAsString();
        String source = param.get("source").getAsString(); // 来源
        
        List<Map<String, String>> ldeList = new ArrayList<Map<String, String>>();
        
        boolean flag = true;
        String channel = "";
        String number = "";
        // 从退款详情中来
        if (source != null && source.equals(CommonEnum.ORDER_LOGISTIC_SOURCE.ORDER_REFUND_PRODUCT.getValue()))
        {
            Map<String, Object> orderRefundLogistics = orderLogisticsDao.findOrderLogisticsByOrderProductRefundId(Integer.parseInt(orderId)); // 这里的orderId是orderProductRefundId
            if (orderRefundLogistics != null)
            {
                channel = orderRefundLogistics.get("channel") + "";
                number = orderRefundLogistics.get("number") + "";
                
                List<LogisticsDetailEntity> ldes = logisticsDetailDao.findDetailByChannelAndNumber(channel, number);
                result.addProperty("logisticsChannel", channel);
                result.addProperty("logisticsNumber", number);
                for (LogisticsDetailEntity lde : ldes)
                {
                    Map<String, String> ldeMap = new HashMap<String, String>();
                    ldeMap.put("operateTime", lde.getOperateTime().substring(0, lde.getOperateTime().length() - 3));
                    ldeMap.put("content", lde.getContent());
                    ldeList.add(ldeMap);
                }
                
                OrderLogisticsView olv = new OrderLogisticsView();
                olv.setChannel(channel);
                olv.setNumber(number);
                olv.setStatus(orderRefundLogistics.get("status") + "");
                olv.setTelePhone(KuaiDiCompanyAndPhoneTypeEnum.getPhone(channel));
                
                result.add("logisticsinfo", parser.parse(JSONUtils.toJson(olv, false)));
                
            }
            else
            {
                flag = false;
            }
        }
        else
        {
            OrderLogisticsEntity ole = orderLogisticsDao.findOrderLogisticsByOId(new Integer(orderId));
            if (ole != null)
            {
                List<LogisticsDetailEntity> ldes = logisticsDetailDao.findDetailByChannelAndNumber(ole.getChannel(), ole.getNumber());
                result.addProperty("logisticsChannel", ole.getChannel());
                result.addProperty("logisticsNumber", ole.getNumber());
                for (LogisticsDetailEntity lde : ldes)
                {
                    Map<String, String> ldeMap = new HashMap<String, String>();
                    String operateTime =
                        StringUtils.isEmpty(lde.getOperateTime()) ? ""
                            : CommonUtil.date2String(CommonUtil.string2Date(lde.getOperateTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
                    ldeMap.put("operateTime", operateTime);
                    ldeMap.put("content", lde.getContent());
                    ldeList.add(ldeMap);
                }
                OrderLogisticsView olv = new OrderLogisticsView();
                BeanUtils.copyProperties(ole, olv);
                olv.setTelePhone(KuaiDiCompanyAndPhoneTypeEnum.getPhone(ole.getChannel()));
                result.add("logisticsinfo", parser.parse(JSONUtils.toJson(olv, false)));
            }
            else
            {
                flag = false;
            }
            
            OrderEntity oe = odi.findOrderById(Integer.valueOf(orderId));
            SellerEntity curSeller = sdi.findSellerById(oe.getSellerId());
            if (curSeller != null && curSeller.getIsBirdex() == Byte.valueOf(CommonEnum.COMMON_IS.YES.getValue()).byteValue())
            {
                // 是笨鸟发货，查询 overseas_order_logistics_record 有没有记录
                List<Map<String, Object>> birdexLogisticsList = odi.findBirdexOrderLogisticsByOrderId(oe.getId());
                if (birdexLogisticsList != null && birdexLogisticsList.size() > 0)
                {
                    for (int i = 0; i < birdexLogisticsList.size(); i++)
                    {
                        Map<String, Object> it = birdexLogisticsList.get(i);
                        String operate_time = it.get("operate_time") == null ? "" : it.get("operate_time") + "";
                        String content = it.get("content") == null ? "" : it.get("content") + "";
                        if (!"".equals(operate_time) && !"".equals(content))
                        {
                            Map<String, String> ldeMap = new HashMap<String, String>();
                            String operateTime = CommonUtil.date2String(CommonUtil.string2Date(operate_time, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
                            ldeMap.put("operateTime", operateTime);
                            ldeMap.put("content", content);
                            ldeList.add(ldeMap);
                        }
                    }
                }
            }
        }
        
        if (flag)
        {
            
        }
        else
        {
            result.addProperty("logisticsChannel", "");
            result.addProperty("logisticsNumber", "");
        }
        
        result.add("logisticsDetail", parser.parse(JSONUtils.toJson(ldeList, false)));
        
        return result.toString();
    }
    
    @Override
    public Map<String, Object> findOrderProductInfosByOId(int orderId)
        throws Exception
    {
        Map<String, Object> reMap = new HashMap<String, Object>();
        OrderEntity o = odi.findOrderById(orderId);
        reMap.put("orderId", o.getId() + "");
        reMap.put("orderTotal", o.getTotalPrice());
        List<Map<String, Object>> item = odi.findOrderProductInfosByOId(orderId);
        List<Map<String, Object>> newItem = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> ii : item)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("skuId", ii.get("product_id") + "");
            map.put("skuName", ii.get("shortName"));
            map.put("category", "食品");
            map.put("Price", ii.get("salesPrice"));
            map.put("Quantity", ii.get("product_count"));
            newItem.add(map);
        }
        reMap.put("item", JSON.toJSONString(newItem));
        return reMap;
    }
    
    public void autoConfirmReceipt()
        throws ServiceException
    {
        // 改定時任務只在10.168.218.188這部機器上執行
        // 10.168.218.188内网地址，对应外网IP：121.41.85.171
        if (!StringUtils.equals("10.168.218.188", NetworkUtil.getLocalIp()))
        {
            log.info("当前机器ip：" + NetworkUtil.getLocalIp() + " 与约定的ip：10.168.218.188 不一致，不处理autoConfirmReceipt()定时任务，直接返回。");
            return;
        }
        
        List<Map<String, Object>> orders = this.odi.findYesDeliveryOrders(); // 查询 海外购30天
        log.info("orders30 size: " + orders.size());
        List<Map<String, Object>> order15s = this.odi.findYesDeliveryMoreThan15Orders(); // 查询 国内商品 15天以上
        log.info("orders15 size: " + order15s.size());
        if (order15s != null && !order15s.isEmpty())
            orders.addAll(order15s);
        
        log.info("total size: " + orders.size());
        
        if (orders != null && !orders.isEmpty())
        {
            for (Map<String, Object> map : orders)
            {
                try
                {
                    doInNewTranscationService.autoConfirmReceiptInNewTranscation(map);
                }
                catch (Exception e)
                {
                    int orderId = Integer.parseInt(map.get("Id") + "");
                    int accountId = Integer.parseInt(map.get("accountId") + "");
                    log.error("处理订单信息为：  orderId：" + orderId + ",accountId：" + accountId + ",status：" + map.get("status") + ",sendTime:" + map.get("sendTime"));
                    log.error("出错了，信息如下： " + e.getMessage());
                }
            }
            
        }
    }
    
    @Override
    public Map<String, Object> getCoupon(Map<String, Object> param)
        throws Exception
    {
        Map<String, Object> result = new HashMap<>();
        int sourceType = Integer.valueOf(param.get("type") + "");
        int accountId = Integer.valueOf(param.get("accountId") + "");
        int selectedCouponId = Integer.valueOf(param.get("selectedCouponId") == null ? "-1" : param.get("selectedCouponId") + "");
        
        Date currDate = CommonUtil.string2Date(CommonUtil.date2String(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
        List<CouponView> availableCouponDetails = new ArrayList<>();
        List<CouponView> unavailableCouponDetails = new ArrayList<>();
        // 化妆品临时邮费方案
        boolean flag = true;
        // 不参加活动的商家列表
        List<Integer> blackSellerIdList = sdi.findAllSellerActivitiesBlacklistId();
        
        if (sourceType == CommonEnum.ORDER_PAY_SOURCE_TYPE.CONFIRM.getValue())
        {
            float confirmTotalPrice = 0;
            long confirmId = Long.valueOf(param.get("confirmId") == null ? "0" : param.get("confirmId") + "");
            List<OrderConfirmEntity> oces = oci.findConfirmByNumber(confirmId);
            if (oces.size() == 0)
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ORDER_GETCOUPON_ERRORCODE.CONFIRMID_NOT_EXIST.getDescription());
                return result;
            }
            
            List<Integer> orderConfirmProductIds = new ArrayList<>();
            for (OrderConfirmEntity oce : oces)
            {
                confirmTotalPrice += oce.getTotalPrice();
                List<OrderConfirmProductEntity> ocpes = ocpi.findConfirmProductByConfirmId(oce.getId());
                for (OrderConfirmProductEntity ocpe : ocpes)
                {
                    orderConfirmProductIds.add(ocpe.getProductId());
                }
                // 化妆品临时邮费方案
                // if (oce.getSellerId() == 555 || oce.getSellerId() == 556 || oce.getSellerId() == 583 ||
                // oce.getSellerId() == 584 || oce.getSellerId() == 591
                // || oce.getSellerId() == 654)
                // {
                // flag = false;
                // }
                if (blackSellerIdList.contains(oce.getSellerId()))
                {
                    flag = false;
                }
            }
            
            List<CouponAccountEntity> caes = couponAccountDao.findUnusedCouponsByAid(accountId);
            for (CouponAccountEntity cae : caes)
            {
//                Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId());
                CouponDetailEntity cde = null;
//                if (cdeCache != null)
//                {
//                    cde = (CouponDetailEntity)cdeCache;
//                }
//                else
//                {
//                    cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
////                    cacheService.addCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
//                }
                
                boolean isAvailableCoupon = true;
                if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.PRODUCT.getValue())
                {
                    if (!orderConfirmProductIds.contains(cde.getScopeId()))
                    {
                        isAvailableCoupon = false;
                    }
                }
                
                CouponView cv = new CouponView();
                cv.setId(cae.getId() + "");
                Date startTime = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                Date endTime = CommonUtil.string2Date(cae.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                cv.setStartTime(CommonUtil.date2String(startTime, "yy-MM-dd"));
                cv.setEndTime(CommonUtil.date2String(endTime, "yy-MM-dd"));
                cv.setScope(cae.getRemark());
                Date startDate = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd");
                String reduce = cde.getReduce() + "";
                // 如果是随机优惠码
                if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                {
                    reduce = cae.getReducePrice() + "";
                }
                cv.setReducePrice(reduce);
                cv.setThresholdPrice(cde.getThreshold() + "");
                cv.setType(cde.getType() + "");
                if (currDate.before(startDate))
                {
                    isAvailableCoupon = false;
                    cv.setIsAvailable(CommonEnum.COMMON_IS.NO.getValue());
                }
                else
                {
                    cv.setIsAvailable(CommonEnum.COMMON_IS.YES.getValue());
                }
                
                if (isAvailableCoupon && confirmTotalPrice < cde.getThreshold())
                {
                    isAvailableCoupon = false;
                }
                
                if (isAvailableCoupon && flag)
                {
                    if (selectedCouponId == cae.getId())
                    {
                        cv.setSelected("1");
                    }
                    availableCouponDetails.add(cv);
                }
                else
                {
                    unavailableCouponDetails.add(cv);
                }
            }
        }
        else if (sourceType == CommonEnum.ORDER_PAY_SOURCE_TYPE.DETAIL.getValue())
        {
            int orderId = Integer.valueOf(param.get("orderId") + "");
            OrderEntity oe = odi.findOrderById(orderId);
            if (oe == null)
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", ErrorCodeEnum.ORDER_GETCOUPON_ERRORCODE.ORDERID_NOT_EXIST.getDescription());
                return result;
            }
            List<CouponAccountEntity> caes = couponAccountDao.findUnusedCouponsByAid(accountId);
            for (CouponAccountEntity cae : caes)
            {
//                Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId());
                CouponDetailEntity cde = null;
//                if (cdeCache != null)
//                {
//                    cde = (CouponDetailEntity)cdeCache;
//                }
//                else
//                {
//                    cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
////                    cacheService.addCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
//                }
                
                boolean isAvailableCoupon = true;
                if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.PRODUCT.getValue())
                {
                    List<Integer> orderConfirmProductIds = new ArrayList<>();
                    List<Map<String, Object>> orderProductInfos = odi.findOrderProductInfosByOId(orderId);
                    for (Map<String, Object> map : orderProductInfos)
                    {
                        orderConfirmProductIds.add(((Long)map.get("product_id")).intValue());
                    }
                    if (!orderConfirmProductIds.contains(cde.getScopeId()))
                    {
                        isAvailableCoupon = false;
                    }
                }
                
                CouponView cv = new CouponView();
                cv.setId(cae.getId() + "");
                Date startTime = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                Date endTime = CommonUtil.string2Date(cae.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                cv.setStartTime(CommonUtil.date2String(startTime, "yy-MM-dd"));
                cv.setEndTime(CommonUtil.date2String(endTime, "yy-MM-dd"));
                cv.setScope(cae.getRemark());
                Date startDate = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd");
                
                String reduce = cde.getReduce() + "";
                // 如果是随机优惠码
                if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                {
                    reduce = cae.getReducePrice() + "";
                }
                cv.setReducePrice(reduce);
                cv.setThresholdPrice(cde.getThreshold() + "");
                cv.setType(cde.getType() + "");
                if (currDate.before(startDate))
                {
                    isAvailableCoupon = false;
                    cv.setIsAvailable(CommonEnum.COMMON_IS.NO.getValue());
                }
                else
                {
                    cv.setIsAvailable(CommonEnum.COMMON_IS.YES.getValue());
                }
                
                if (isAvailableCoupon && oe.getTotalPrice() < cde.getThreshold())
                {
                    isAvailableCoupon = false;
                }
                
                if (isAvailableCoupon)
                {
                    if (selectedCouponId == cae.getId())
                    {
                        cv.setSelected("1");
                    }
                    availableCouponDetails.add(cv);
                }
                else
                {
                    unavailableCouponDetails.add(cv);
                }
            }
        }
        else if (sourceType == CommonEnum.ORDER_PAY_SOURCE_TYPE.MERGER.getValue())
        {
            List<Integer> orderIds = param.get("orderIdList") == null ? new ArrayList<Integer>() : (List<Integer>)param.get("orderIds");
            List<OrderEntity> oes = odi.findNotPayOrderByIds(orderIds);
            if (orderIds.size() == 0 || oes.size() != orderIds.size()) // 订单id不存在
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("errorCode", ErrorCodeEnum.ORDER_GETCOUPON_ERRORCODE.ORDERID_NOT_EXIST.getDescription());
                return result;
            }
            
            float totalPrice = 0;
            for (OrderEntity oe : oes)
            {
                totalPrice += oe.getTotalPrice();
            }
            
            List<CouponAccountEntity> caes = couponAccountDao.findUnusedCouponsByAid(accountId);
            for (CouponAccountEntity cae : caes)
            {
//                Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId());
                CouponDetailEntity cde = null;
//                if (cdeCache != null)
//                {
//                    cde = (CouponDetailEntity)cdeCache;
//                }
//                else
//                {
//                    cde = couponDetailDao.findCouponDetailById(cae.getCouponDetailId());
//                    cacheService.addCache(CacheConstant.COUPON_DETAIL + cae.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
//                }
//                
                boolean isAvailableCoupon = true;
                if (cde.getScopeType() == CommonEnum.COUPON_SCOPE_TYPE.PRODUCT.getValue())
                {
                    List<Integer> orderConfirmProductIds = new ArrayList<>();
                    for (int orderId : orderIds)
                    {
                        List<Map<String, Object>> orderProductInfos = odi.findOrderProductInfosByOId(orderId);
                        for (Map<String, Object> map : orderProductInfos)
                        {
                            orderConfirmProductIds.add(((Long)map.get("product_id")).intValue());
                        }
                    }
                    if (!orderConfirmProductIds.contains(cde.getScopeId()))
                    {
                        isAvailableCoupon = false;
                    }
                }
                
                CouponView cv = new CouponView();
                cv.setId(cae.getId() + "");
                Date startTime = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                Date endTime = CommonUtil.string2Date(cae.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                cv.setStartTime(CommonUtil.date2String(startTime, "yy-MM-dd"));
                cv.setEndTime(CommonUtil.date2String(endTime, "yy-MM-dd"));
                cv.setScope(cae.getRemark());
                Date startDate = CommonUtil.string2Date(cae.getStartTime(), "yyyy-MM-dd");
                
                String reduce = cde.getReduce() + "";
                // 如果是随机优惠码
                if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                {
                    reduce = cae.getReducePrice() + "";
                }
                cv.setReducePrice(reduce);
                cv.setThresholdPrice(cde.getThreshold() + "");
                cv.setType(cde.getType() + "");
                if (currDate.before(startDate))
                {
                    isAvailableCoupon = false;
                    cv.setIsAvailable(CommonEnum.COMMON_IS.NO.getValue());
                }
                else
                {
                    cv.setIsAvailable(CommonEnum.COMMON_IS.YES.getValue());
                }
                
                if (isAvailableCoupon && totalPrice < cde.getThreshold())
                {
                    isAvailableCoupon = false;
                }
                
                if (isAvailableCoupon)
                {
                    if (selectedCouponId == cae.getId())
                    {
                        cv.setSelected("1");
                    }
                    availableCouponDetails.add(cv);
                }
                else
                {
                    unavailableCouponDetails.add(cv);
                }
            }
        }
        
        Collections.sort(availableCouponDetails, new Comparator<CouponView>()
        {
            @Override
            public int compare(CouponView o1, CouponView o2)
            {
                return (Double.valueOf(o2.getReducePrice()) > Double.valueOf(o1.getReducePrice())) ? 1 : -1;
            }
            
        });
        
        if (selectedCouponId == -1 && availableCouponDetails.size() > 0)
        {
            availableCouponDetails.get(0).setSelected("1");// 默认选中最大的优惠券
        }
        
        result.put("availableCouponDetails", availableCouponDetails);
        result.put("unavailableCouponDetails", unavailableCouponDetails);
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }
    
    /**
     * 支付完成后处理左岸城堡粉丝订单
     * 
     * @param orderLists 订单
     * @param accountId 当前用户id
     * @param number 订单编号已逗号隔开
     * @param totalRealPrice 总价
     */
    private void insertFansOrder(List<OrderEntity> orderLists, int accountId, String number, float totalPrice, float totalDraw)
    {
        
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("fansAccountId", accountId);
        // 当前用户的上级
        List<QqbsFansEntity> qqbsFansList = hqbsFansDao.getFansList(para);
        // 当前用户
        QqbsAccountEntity qqbsAccount = hqbsAccountDao.findAccountByAccountId(accountId);
        
        String appid = CommonConstant.APPID;
        String secret = CommonConstant.APPSECRET;
        
        String token = WeixinMessageDigestUtil.getAccessToken(appid, secret);
        number = number.substring(0, number.length() - 1);
        if (qqbsFansList != null && qqbsFansList.size() > 0)
        {
            for (QqbsFansEntity qfe : qqbsFansList)
            {
                
                QqbsAccountEntity fatherAe = hqbsAccountDao.findAccountByAccountId(qfe.getAccountId());
                StringBuffer sb = new StringBuffer();
                if (qfe.getLevel() == 1)
                {
                    // 直接粉丝
                    sb.append("您的粉丝" + qqbsAccount.getNickName() + "在" + CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") + "购物\n订单号：" + number + "\n订单金额："
                        + MathUtil.format_2(totalPrice) + "元" + "\n获得分享奖励：" + MathUtil.format_2(totalPrice * 0.15f) + "元");
                }
                else if (qfe.getLevel() == 2)
                {
                    // 间接粉丝
                    sb.append("您管理的代言人/粉丝" + qqbsAccount.getNickName() + "在" + CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") + "购物\n订单号：" + number + "\n订单金额："
                        + MathUtil.format_2(totalPrice) + "元" + "\n获得管理补贴：" + MathUtil.format_2(totalPrice * 0.05f) + "元");
                    
                }
                else if (qfe.getLevel() == 3)
                {
                    // 间接粉丝
                    sb.append("您管理的代言人/粉丝" + qqbsAccount.getNickName() + "在" + CommonUtil.date2String(new Date(), "yyyy-MM-dd HH:mm:ss") + "购物\n订单号：" + number + "\n订单金额："
                        + MathUtil.format_2(totalPrice) + "元" + "\n获得管理补贴：" + MathUtil.format_2(totalPrice * 0.07f) + "元");
                	return ;

                }
                try{
                    CommonHttpClient.messageCustomSend(token, fatherAe.getOpenId(), sb.toString());
                }catch(Exception e){
                    log.error("发送用户奖励消息失败",e);
                }
                
                
                for (OrderEntity oe : orderLists)
                {
                    QqbsFansOrderEntity qqbsFansOrder = new QqbsFansOrderEntity();
                    qqbsFansOrder.setFansOrderId(oe.getId());
                    qqbsFansOrder.setAccountId(fatherAe.getAccountId());
                    qqbsFansOrder.setFansAccountId(qqbsAccount.getAccountId());
                    qqbsFansOrder.setFansImage(qqbsAccount.getImage());
                    qqbsFansOrder.setFansNickname(qqbsAccount.getNickName());
                    qqbsFansOrder.setNumber(oe.getNumber());
                    qqbsFansOrder.setRealPrice(oe.getRealPrice());
                    qqbsFansOrder.setStatus(1);
                    qqbsFansOrder.setExStatus(0);
                    if (qfe.getLevel() == 1)
                    {
                        // 直接粉丝
                        qqbsFansOrder.setLevel(1);
                        qqbsFansOrder.setWithdrawCash(totalPrice * 0.15f);
                    }
                    else if (qfe.getLevel() == 2)
                    {
                        // 间接粉丝
                        qqbsFansOrder.setLevel(2);
                        qqbsFansOrder.setWithdrawCash(totalPrice * 0.05f);
                    }
                    else if (qfe.getLevel() == 3)
                    {
                        // 间接粉丝
                        qqbsFansOrder.setLevel(3);
                        qqbsFansOrder.setWithdrawCash(totalPrice * 0.07f);
                        return;
                    }
                    hqbsFansOrderDao.insertFans(qqbsFansOrder);
                }
            }
        }
    }
    /**
     * 插入代言人
     * @param accountId
     * @param totalPrice
     */
    private void insertSp(int accountId,float totalPrice){
         if(totalPrice>=299){
             int count = qqbsSpokespersonDao.getCount(accountId);
             if(count == 0){
                 QqbsSpokesperson qs = new QqbsSpokesperson();
                 qs.setAccountId(accountId);
                 String spTime = CommonUtil.date2String(new Date(), "yyyyMMdd");
                 qs.setSpTime(Integer.valueOf(spTime));
                 qs.setExStatus(0);
                 qqbsSpokespersonDao.insert(qs);
             }
         }
     }
}
