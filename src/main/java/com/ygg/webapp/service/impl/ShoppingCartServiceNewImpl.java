package com.ygg.webapp.service.impl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.code.ProductActivitiesStatusEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.BrandDao;
import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.dao.TempAccountDao;
import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.entity.BrandEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.ShoppingCartServiceNew;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.MathUtil;

@Service("shoppingCartServiceNew")
public class ShoppingCartServiceNewImpl implements ShoppingCartServiceNew
{
    
    private int cartValidMillis = 24 * 60 * 60 * 1000 * 30;
    
    private Logger log = Logger.getLogger(ShoppingCartServiceNewImpl.class);
    
    @Resource(name = "shoppingCartDao")
    private ShoppingCartDao shoppingCartDao;
    
    @Resource(name = "tempShoppingCartDao")
    private TempShoppingCartDao tempShoppingDao;
    
    @Resource(name = "tempAccountDao")
    private TempAccountDao tadi;
    
    @Resource(name = "accountDao")
    private AccountDao adi;
    
    @Resource(name = "productDao")
    private ProductDao pdi;
    
    @Resource(name = "productCountDao")
    private ProductCountDao pcdi;
    
    @Resource(name = "productCommonDao")
    private ProductCommonDao pcommondi;
    
    @Resource
    private OrderDao odi;
    
    @Resource(name = "brandDao")
    private BrandDao bdi;
    
    /**
     * REQUIRE_NEW
     */
    @Override
    public String editCrudsc(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int id = param.get("id").getAsInt();
        String type = param.get("type").getAsString();
        int productId = param.get("productId").getAsInt();
        short modifyCount = param.get("count").getAsShort();
        
        result.addProperty("id", id);
        result.addProperty("type", type);
        result.addProperty("productId", productId);
        
        // 商品不存在
        ProductEntity pe = pdi.findProductInfoById(productId);
        if (pe == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.PRODUCTID_NOT_EXIST.getValue());
            return result.toString();
        }
        String errorCode = "";
        try
        {
            // 活动判断 后面要抽出来
            if (pe.getActivitiesStatus() == ProductActivitiesStatusEnum.GEGE_WELFARE.ordinal())
            {
                if ("2".equals(type))
                {
                    ServiceException se = new ServiceException("必须登录才能购买格格优惠商品");
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", CommonEnum.CART_EDIT_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
                    throw se; // 回滚
                }
                // 判断 是否符合购买条件
                Map<String, Object> productWelfare = pdi.findProductWelfareByProductId(productId);
                double limitPrice = Double.parseDouble(productWelfare.get("limitPrice") == null ? "0" : productWelfare.get("limitPrice") + "");
                int limitNum = Integer.parseInt(productWelfare.get("limitNum") == null ? "0" : productWelfare.get("limitNum") + "");
                // Map<String, Object> totalPriceMap = adi.countAccountTotalOrderRealPrice(id);
                // double totalPrice = Double.parseDouble(totalPriceMap.get("totalPrice") == null ? "0" :
                // totalPriceMap.get("totalPrice") + "");
                String brandIds = productWelfare.get("brandIds") == null ? "" : productWelfare.get("brandIds").toString();
                String payTimeBegin = productWelfare.get("payTimeBegin") == null ? null : ((Timestamp)productWelfare.get("payTimeBegin")).toString();
                String payTimeEnd = productWelfare.get("payTimeEnd") == null ? null : ((Timestamp)productWelfare.get("payTimeEnd")).toString();
                double totalPrice = 0d;
                
                if (StringUtils.isEmpty(brandIds))
                {
                    if (StringUtils.isNotEmpty(payTimeBegin) && StringUtils.isNotEmpty(payTimeEnd))
                    {
                        totalPrice = odi.findAllRealPriceByAidAndPayTime(id, payTimeBegin, payTimeEnd);
                    }
                    else
                    {
                        totalPrice = odi.findAllRealPriceByAid(id);
                    }
                }
                else
                {
                    List<String> brandIdList = Arrays.asList(brandIds.split(","));
                    Collections.sort(brandIdList);
                    if (StringUtils.isNotEmpty(payTimeBegin) && StringUtils.isNotEmpty(payTimeEnd))
                    {
                        totalPrice = odi.findAllRealPriceByAidAndBidAndPayTime(id, brandIdList, payTimeBegin, payTimeEnd);
                    }
                    else
                    {
                        totalPrice = odi.findAllRealPriceByAidAndBid(id, brandIdList);
                    }
                    
                }
                if (limitPrice > totalPrice)
                {
                    ServiceException se = new ServiceException("无购买格格优惠商品资格，商品ID:" + productId + "用户ID:" + id);
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", CommonEnum.CART_EDIT_ERRORCODE.NO_COMPETENCE_PRODUCT_GEGE_WELFARE.getValue());
                    // se.putMap("showMsg", "亲，此特价商品需要在左岸城堡消费满" + limitPrice + "元才能购买，您已购买" + totalPrice + "元，继续加油哦～");
                    se.putMap("showMsg", getErrorMessage(brandIds, payTimeBegin, payTimeEnd, limitPrice, totalPrice));
                    throw se; // 回滚
                }
                // 限购limitNum件，从订单状态为1、2、3、4中去查找
                if (modifyCount > 0)
                {
                    int count = odi.countHistoryOrderByAccountIdAndProductId(id, productId);
                    count += modifyCount;
                    if (count > limitNum)
                    {
                        ServiceException se = new ServiceException("超过格格优惠商品购买限购数，商品ID:" + productId + "用户ID:" + id);
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", CommonEnum.CART_EDIT_ERRORCODE.NO_COMPETENCE_PRODUCT_GEGE_WELFARE.getValue());
                        se.putMap("showMsg", "亲，您已超过此福利商品的限购数量~");
                        throw se; // 回滚
                    }
                }
            }
            
            // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
            // 超过限购数量
            ProductCountEntity pce = pcdi.findCountInfoByIdForUpdate(productId);
            if (modifyCount > pce.getRestriction()&& false)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.RESTRICTION_EXCEED.getValue());
                result.addProperty("restrictionCount", pce.getRestriction() + "");
                // ConnectionManager.rollbackTransaction();
                // return result.toString();
                errorCode = CommonEnum.CART_EDIT_ERRORCODE.RESTRICTION_EXCEED.getValue();
                
                ServiceException se = new ServiceException("超过商品限购数量");
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", errorCode);
                se.putMap("restrictionCount", (pce.getRestriction() + ""));
                throw se; // 回滚
            }
            
            Set<String> againOperateSet = new HashSet<String>();
            boolean successFlag = true;
            
            // 用户已登录
            if (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue()))
            {
                // 用户不存在
                if (adi.findAccountById(id) == null)
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
                    // ConnectionManager.rollbackTransaction();
                    // return result.toString();
                    errorCode = CommonEnum.CART_EDIT_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue();
                    ServiceException se = new ServiceException("accountId不存在");
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", errorCode);
                    throw se; // 回滚
                }
                
                ShoppingCartEntity sce = shoppingCartDao.findNormalCartByPIdAndAId(productId, id);
                int currCartCount = 0;
                if (sce != null)
                {
                    currCartCount = sce.getProductCount();
                }
                // 删除商品
                if (modifyCount <= 0)
                {
                    successFlag = loginDelete(id, productId, pce, sce, modifyCount);
                }
                // 增加商品
                else if (modifyCount > currCartCount)
                {
                    if (!isValidProduct(productId)) // 验证商品是否正常可售
                    {
                        errorCode = CommonEnum.CART_EDIT_ERRORCODE.PRODUCTID_NOT_EXIST.getValue();
                        throw new ServiceException(errorCode); // 回滚
                    }
                    if (currCartCount == 0) // 新增记录
                    {
                        int availableCount = pce.getStock() - pce.getLock();
                        if (availableCount >= modifyCount || true)
                        {
                            successFlag = loginUpAdd(id, productId, modifyCount, pce, againOperateSet);
                        }
                        else
                        {
                            setValidTime(result, id, type);
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                            result.addProperty("stockCount", availableCount);
                            // ConnectionManager.rollbackTransaction();
                            // return result.toString();
                            
                            errorCode = CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue();
                            ServiceException se = new ServiceException("库存不足");
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            se.putMap("errorCode", errorCode);
                            se.putMap("stockCount", availableCount);
                            throw se; // 回滚
                            // System.out.println("--------------stockCount-------------");
                        }
                    }
                    else
                    // 修改记录
                    {
                        // 需要考虑购物车锁定商品超时,但购物车还有记录的情况,故这里用锁定数量作为已有的数量
                        int currLockCount = this.shoppingCartDao.findLockCountByPIdAndAId(productId, id);
                        if (currLockCount <= 0)
                        {
                            currLockCount = 0;
                        }
                        int availableCount = pce.getStock() - pce.getLock() + currLockCount;
                        
                        if (availableCount >= modifyCount||true) // 库存数量充足
                        {
                            successFlag = loginUpModify(id, productId, modifyCount, pce, sce, currLockCount, againOperateSet);
                        }
                        else
                        // 库存数量不足
                        {
                            // ConnectionManager.rollbackTransaction();
                            // /按正常状态处理
                            setValidTime(result, id, type);
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                            result.addProperty("stockCount", availableCount);
                            result.addProperty("noFlag", "noFlag");
                            if (availableCount == 0) // 没货
                            {
                                sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                                if (this.shoppingCartDao.updateShoppingCart(sce) == 0)
                                {
                                    log.warn("设为商品无货失败,productId:" + productId + ",accountId:" + id);
                                }
                            }
                            return result.toString();
                            
                            /*
                             * List<ShoppingCartEntity> sces = new ArrayList<ShoppingCartEntity>(); sces.add(sce) ;
                             * //this.shoppingCartService.updateShoppingCart(sces,null) ; // 发起另一个事务　REQUIRE_NEW
                             * 上层异常方法中调用 errorCode = CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue() ;
                             * ServiceException se = new ServiceException("库存不足") ; se.putMap("status",
                             * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); se.putMap("errorCode",
                             * errorCode); se.putMap("stockCount", availableCount); se.putMap("sce", sce); throw se ; //
                             * 回滚
                             */
                        }
                    }
                }
                // 减少商品
                else if (modifyCount < currCartCount)
                {
                    successFlag = loginDown(id, productId, modifyCount, pce, sce, currCartCount);
                }
                //
                else
                {
                    setValidTime(result, id, type);
                }
                
            }
            // 用户未登录
            else
            {
                // 临时用户不存在
                if (!tadi.idIsExist(id))
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue());
                    // ConnectionManager.rollbackTransaction();
                    // return result.toString();
                    errorCode = CommonEnum.CART_EDIT_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue();
                    ServiceException se = new ServiceException("临时账号不存在");
                    se.putMap("errorCode", errorCode);
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    throw se; // 回滚
                }
                
                TempShoppingCartEntity tsce = this.tempShoppingDao.findNormalCartByPIdAndAId(productId, id);
                int currCartCount = 0;
                if (tsce != null)
                {
                    currCartCount = tsce.getProductCount();
                }
                // 删除商品
                if (modifyCount <= 0)
                {
                    successFlag = notLoginDelete(id, productId, pce, tsce, modifyCount);
                }
                // 增加商品
                else if (modifyCount > currCartCount)
                {
                    if (!isValidProduct(productId)) // 验证商品是否正常可售
                    {
                        errorCode = CommonEnum.CART_EDIT_ERRORCODE.PRODUCTID_NOT_EXIST.getValue();
                        throw new ServiceException(errorCode); // 回滚
                    }
                    if (currCartCount == 0) // 新增记录
                    {
                        int availableCount = pce.getStock() - pce.getLock();
                        if (availableCount >= modifyCount)
                        {
                            successFlag = notLoginUpAdd(id, productId, modifyCount, pce, againOperateSet);
                        }
                        else
                        {
                            setValidTime(result, id, type);
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                            result.addProperty("stockCount", availableCount);
                            // ConnectionManager.rollbackTransaction();
                            // return result.toString();
                            errorCode = CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue();
                            ServiceException se = new ServiceException("库存不足");
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            se.putMap("stockCount", availableCount);
                            se.putMap("errorCode", errorCode);
                            throw se; // 回滚
                        }
                    }
                    else
                    // 修改记录
                    {
                        // 需要考虑购物车锁定商品超时,但购物车还有记录的情况,故这里用锁定数量作为已有的数量
                        int currLockCount = this.tempShoppingDao.findLockCountByPIdAndAId(productId, id);
                        if (currLockCount == -1)
                        {
                            currLockCount = 0;
                        }
                        int availableCount = pce.getStock() - pce.getLock() + currLockCount;
                        
                        if (availableCount >= modifyCount) // 库存数量充足
                        {
                            successFlag = notLoginUpModify(id, productId, modifyCount, pce, tsce, currLockCount, againOperateSet);
                        }
                        else
                        // 库存数量不足
                        {
                            // ConnectionManager.rollbackTransaction();
                            setValidTime(result, id, type);
                            // /按正常状态处理
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                            result.addProperty("stockCount", availableCount);
                            if (availableCount == 0) // 没货
                            {
                                tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                                if (this.tempShoppingDao.updateShoppingCart(tsce) == 0)
                                {
                                    log.warn("设为商品无货失败,productId:" + productId + ",accountId:" + id);
                                }
                            }
                            result.addProperty("noFlag", "noFlag");
                            return result.toString();
                            
                            /*
                             * List<TempShoppingCartEntity> tsces = new ArrayList<TempShoppingCartEntity>();
                             * tsces.add(tsce) ; // this.shoppingCartService.updateShoppingCart(null,tsces) ; //
                             * 发起另一个事务　REQUIRE_NEW　　上层异常方法中调用 errorCode =
                             * CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue() +""; ServiceException se = new
                             * ServiceException(errorCode) ; se.putMap("errorCode", errorCode); se.putMap("stockCount",
                             * availableCount); se.putMap("tsce", tsce); throw se ; // 回滚
                             */
                        }
                    }
                }
                // 减少商品
                else if (modifyCount < currCartCount)
                {
                    successFlag = notLoginDown(id, productId, modifyCount, pce, tsce, currCartCount);
                }
                //
                else
                {
                    setValidTime(result, id, type);
                }
            }
            
            result.addProperty("successFlag", successFlag);
            result.add("againOperateSet", parser.parse(JSONUtils.toJson(againOperateSet, false)));
        }
        catch (Exception e) // 回滚事务
        {
            // ConnectionManager.rollbackTransaction();
            // errorCode ="edit购物车数据库出错!" ;
            log.error("edit购物车数据库出错!", e);
            if (e instanceof ServiceException)
                throw e;
            
            // ServiceException se = new ServiceException(errorCode) ;
            throw e;
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    private boolean loginDelete(int id, int productId, ProductCountEntity pce, ShoppingCartEntity sce, int modifyCount)
        throws ServiceException
    {
        if (sce != null) // 正常购物车
        {
            sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.USER_DELETE.getValue());
            if (this.shoppingCartDao.updateShoppingCart(sce) == 0) // 修改为用户删除状态
            {
                log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
                return false;
            }
            int lockCount = shoppingCartDao.findLockCountByPIdAndAId(productId, id);
            if (lockCount != -1)// 删除购物车锁定记录及修改商品锁定数量
            {
                if (shoppingCartDao.deleteLockCountByPIdAndAId(productId, id) == 0)
                {
                    log.warn("deleteLockCountByPIdAndAId无匹配,productId:" + productId + ",accountId:" + id);
                    return false;
                }
                pce.setLock(pce.getLock() - lockCount);
                if (pcdi.updateCountInfo(pce) == 0)
                {
                    log.warn("updateCountInfo无匹配,ProductCountEntity:" + pce);
                    return false;
                }
            }
        }
        else
        // 缺货购物车
        {
            sce = shoppingCartDao.findLackCartByPIdAndAId(productId, id);
            if (sce != null)
            {
                sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (shoppingCartDao.updateShoppingCart(sce) == 0) // 修改为没货删除状态
                {
                    log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean loginUpAdd(int id, int productId, short modifyCount, ProductCountEntity pce, Set<String> againOperateSet)
        throws ServiceException
    {
        pce.setLock(pce.getLock() + modifyCount);
        if (pcdi.updateCountInfo(pce) == 0)
        {
            log.warn("updateCountInfo无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        String validTimeStr = this.shoppingCartDao.findValidTimeByAid(id);
        if (validTimeStr == null || validTimeStr.equals("")) // 不存在购物车锁定时间 信息
        {
            if (shoppingCartDao.addLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("addLockTime失败,accountId:" + id);
                return false;
            }
        }
        else
        // 存在购物车锁定时间 信息
        {
            Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().after(validTime) && false) // 购物车锁定已过期,需要把购物车存在的商品重新锁定
            {
                againOperateSet.add("loginAgainAddLockTime");
            }
            if (shoppingCartDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + id);
                return false;
            }
        }
        log.warn("loginUpAdd---addLockCount---,productId:" + productId + ",accountId:" + id);
        // scdi.deleteLockCountByPIdAndAId(productId, id); // 防止多条锁定记录的异常
        if (shoppingCartDao.addLockCount(productId, id, modifyCount) == 0)
        {
            log.warn("addLockCount失败,productId:" + productId + ",accountId:" + id);
            return false;
        }
        
        ShoppingCartEntity sce = new ShoppingCartEntity();
        sce.setAccountId(id);
        sce.setProductId(productId);
        sce.setProductCount(modifyCount);
        if (this.shoppingCartDao.addShoppingCart(sce) == 0)
        {
            log.warn("addShoppingCart失败,productId:" + productId + ",accountId:" + id);
            return false;
        }
        return true;
    }
    
    private boolean loginUpModify(int id, int productId, short modifyCount, ProductCountEntity pce, ShoppingCartEntity sce, int currLockCount, Set<String> againOperateSet)
        throws ServiceException
    {
        pce.setLock(pce.getLock() + modifyCount - currLockCount);
        if (pcdi.updateCountInfo(pce) == 0)
        {
            log.warn("updateCountInfo无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        if (currLockCount == 0) // 购物车锁定已过期
        {
            if (this.shoppingCartDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("----loginUpModify----updateLockTime无匹配,accountId:" + id);
                if (this.shoppingCartDao.addLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
                {
                    log.warn("updateLockTime无匹配,accountId:" + id);
                    return false;
                }
                // return false;
            }
            log.warn("loginUpModify---addLockCount---,productId:" + productId + ",accountId:" + id);
            if (shoppingCartDao.addLockCount(productId, id, modifyCount) == 0)
            {
                log.warn("addLockCount失败,productId:" + productId + ",accountId:" + id);
                return false;
            }
            againOperateSet.add("loginAgainAddLockTime");
        }
        else
        // 购物车锁定未过期
        {
            if (shoppingCartDao.updateLockCount(productId, id, modifyCount) == 0)
            {
                log.warn("updateLockCount无匹配,productId:" + productId + ",accountId:" + id);
                return false;
            }
        }
        sce.setProductCount(modifyCount);
        if (shoppingCartDao.updateShoppingCart(sce) == 0)
        {
            log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        return true;
    }
    
    private boolean loginDown(int id, int productId, short modifyCount, ProductCountEntity pce, ShoppingCartEntity sce, int currCartCount)
        throws ServiceException
    {
        sce.setProductCount(modifyCount);
        if (this.shoppingCartDao.updateShoppingCart(sce) == 0)
        {
            log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        if (shoppingCartDao.updateLockCount(productId, id, modifyCount) == 0)
        {
            log.warn("updateLockCount无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        pce.setLock(pce.getLock() - currCartCount + modifyCount);
        if (pcdi.updateCountInfo(pce) == 0)
        {
            return false;
        }
        return true;
    }
    
    private boolean notLoginDelete(int id, int productId, ProductCountEntity pce, TempShoppingCartEntity tsce, int modifyCount)
        throws ServiceException
    {
        if (tsce != null) // 正常购物车
        {
            tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.USER_DELETE.getValue());
            if (this.tempShoppingDao.updateShoppingCart(tsce) == 0) // 修改为用户删除状态
            {
                log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
                return false;
            }
            int lockCount = tempShoppingDao.findLockCountByPIdAndAId(productId, id);
            if (lockCount != -1)// 删除购物车锁定记录及修改商品锁定数量
            {
                if (tempShoppingDao.deleteLockCountByPIdAndAId(productId, id) == 0)
                {
                    log.warn("deleteLockCountByPIdAndAId无匹配,productId:" + productId + ",accountId:" + id);
                    return false;
                }
                pce.setLock(pce.getLock() - lockCount);
                if (pcdi.updateCountInfo(pce) == 0)
                {
                    log.warn("updateCountInfo无匹配,ProductCountEntity:" + pce);
                    return false;
                }
            }
        }
        else
        // 缺货购物车
        {
            tsce = tempShoppingDao.findLackCartByPIdAndAId(productId, id);
            if (tsce != null)
            {
                tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (tempShoppingDao.updateShoppingCart(tsce) == 0) // 修改为没货删除状态
                {
                    log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private boolean notLoginUpAdd(int id, int productId, short modifyCount, ProductCountEntity pce, Set<String> againOperateSet)
        throws ServiceException
    {
        pce.setLock(pce.getLock() + modifyCount);
        if (pcdi.updateCountInfo(pce) == 0)
        {
            log.warn("updateCountInfo无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        String validTimeStr = tempShoppingDao.findValidTimeByAid(id);
        if (validTimeStr == null || validTimeStr.equals("")) // 不存在购物车锁定时间 信息
        {
            if (tempShoppingDao.addLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("addLockTime失败,accountId:" + id);
                return false;
            }
        }
        else
        // 存在购物车锁定时间 信息
        {
            Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().after(validTime)) // 购物车锁定已过期,需要把购物车存在的商品重新锁定
            {
                againOperateSet.add("notLoginAgainAddLockTime");
            }
            if (tempShoppingDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + id);
                return false;
            }
        }
        // scdi.deleteLockCountByPIdAndAId(productId, id); // 防止多条锁定记录的异常
        if (tempShoppingDao.addLockCount(productId, id, modifyCount) == 0)
        {
            log.warn("addLockCount失败,productId:" + productId + ",accountId:" + id);
            return false;
        }
        
        TempShoppingCartEntity tsce = new TempShoppingCartEntity();
        tsce.setTempAccountId(id);
        tsce.setProductId(productId);
        tsce.setProductCount(modifyCount);
        if (tempShoppingDao.addShoppingCart(tsce) == 0)
        {
            log.warn("addShoppingCart失败,productId:" + productId + ",accountId:" + id);
            return false;
        }
        return true;
    }
    
    private boolean notLoginUpModify(int id, int productId, short modifyCount, ProductCountEntity pce, TempShoppingCartEntity tsce, int currLockCount, Set<String> againOperateSet)
        throws ServiceException
    {
        pce.setLock(pce.getLock() + modifyCount - currLockCount);
        if (pcdi.updateCountInfo(pce) == 0)
        {
            log.warn("updateCountInfo无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        if (currLockCount == 0) // 购物车锁定已过期
        {
            if (tempShoppingDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("---notLoginUpModify---updateLockTime无匹配,accountId:" + id);
                if (tempShoppingDao.addLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
                {
                    log.warn("addLockTime失败,accountId:" + id);
                    return false;
                }
                // return false;
            }
            if (tempShoppingDao.addLockCount(productId, id, modifyCount) == 0)
            {
                log.warn("addLockCount失败,productId:" + productId + ",accountId:" + id);
                return false;
            }
            againOperateSet.add("notLoginAgainAddLockTime");
        }
        else
        // 购物车锁定未过期
        {
            if (tempShoppingDao.updateLockCount(productId, id, modifyCount) == 0)
            {
                log.warn("updateLockCount无匹配,productId:" + productId + ",accountId:" + id);
                return false;
            }
        }
        tsce.setProductCount(modifyCount);
        if (tempShoppingDao.updateShoppingCart(tsce) == 0)
        {
            log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        return true;
    }
    
    private boolean notLoginDown(int id, int productId, short modifyCount, ProductCountEntity pce, TempShoppingCartEntity tsce, int currCartCount)
        throws ServiceException
    {
        tsce.setProductCount(modifyCount);
        if (tempShoppingDao.updateShoppingCart(tsce) == 0)
        {
            log.warn("updateShoppingCart无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        if (tempShoppingDao.updateLockCount(productId, id, modifyCount) == 0)
        {
            log.warn("updateLockCount无匹配,productId:" + productId + ",accountId:" + id);
            return false;
        }
        pce.setLock(pce.getLock() - currCartCount + modifyCount);
        if (pcdi.updateCountInfo(pce) == 0)
        {
            return false;
        }
        return true;
    }
    
    public boolean updateShoppingCart(List<ShoppingCartEntity> sces, List<TempShoppingCartEntity> tsces)
        throws ServiceException
    {
        if (sces != null && sces.size() > 0)
        {
            for (ShoppingCartEntity sce : sces)
            {
                sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (shoppingCartDao.updateShoppingCart(sce) == 0)
                {
                    log.error("设为商品无货失败1,productId:" + sce.getProductId() + ",accountId:" + sce.getAccountId());
                }
            }
        }
        
        if (tsces != null && tsces.size() > 0)
        {
            for (TempShoppingCartEntity tsce : tsces)
            {
                tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (this.tempShoppingDao.updateShoppingCart(tsce) == 0)
                {
                    log.error("设为商品无货失败2,productId:" + tsce.getProductId() + ",tempAccountId:" + tsce.getTempAccountId());
                }
            }
        }
        
        return true;
    }
    
    private void setValidTime(JsonObject result, int id, String type)
    {
        String validTimeStr = "";
        String endSecond = "-1";
        if (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue()))
        {
            validTimeStr = shoppingCartDao.findValidTimeByAid(id);
        }
        else
        {
            validTimeStr = tempShoppingDao.findValidTimeByAid(id);
        }
        if (validTimeStr != null) // 存在购物车锁定时间 信息
        {
            Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().before(validTime)) // 购物车锁定没过期
            {
                endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
            }
        }
        result.addProperty("endSecond", endSecond);
    }
    
    private boolean isValidProduct(int productId)
        throws DaoException
    {
        ProductEntity pe = pdi.findProductPartById(productId);
        Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
        Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
        Date currTime = new Date();
        if ((pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
            || (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.SALE.getValue()) && (currTime.before(startTime) || currTime.after(endTime)))
            || (pe.getType() > Byte.parseByte(CommonEnum.PRODUCT_TYPE.MALL.getValue())))
        {
        	return true;
//            return false;
        }
        return true;
    }
    
    private String getErrorMessage(String brandIds, String payTimeBegin, String payTimeEnd, double limitPrice, double historyRealPrice)
    {
        StringBuffer errorMessage = new StringBuffer("亲，此福利商品需要在左岸城堡");
        if (StringUtils.isEmpty(brandIds))
        {
            if (StringUtils.isNotEmpty(payTimeBegin) && StringUtils.isNotEmpty(payTimeEnd))
            {
                errorMessage.append(CommonUtil.date2String(CommonUtil.string2Date(payTimeBegin, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"))
                    .append("至")
                    .append(CommonUtil.date2String(CommonUtil.string2Date(payTimeEnd, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"))
                    .append("期间");
            }
        }
        else
        {
            if (StringUtils.isNotEmpty(payTimeBegin) && StringUtils.isNotEmpty(payTimeEnd))
            {
                errorMessage.append(CommonUtil.date2String(CommonUtil.string2Date(payTimeBegin, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"))
                    .append("至")
                    .append(CommonUtil.date2String(CommonUtil.string2Date(payTimeEnd, "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd"))
                    .append("期间");
            }
            
            List<String> brandIdList = Arrays.asList(brandIds.split(","));
            Collections.sort(brandIdList);
            StringBuffer brandNames = new StringBuffer();
            for (int i = 0; i < brandIdList.size(); i++)
            {
                BrandEntity be = bdi.findBrandInfoById(Integer.parseInt(brandIdList.get(i)));
                if (StringUtils.isEmpty(brandNames.toString()))
                {
                    brandNames.append(be.getName());
                }
                else
                {
                    brandNames.append("、").append(be.getName());
                }
                if (i >= 2)
                {
                    brandNames.append("等");
                    break;
                }
            }
            errorMessage.append("购买").append(brandNames).append("品牌的商品");
        }
        errorMessage.append("消费满")
            .append(MathUtil.round(limitPrice, 2))
            .append("元才能购买，您当前已消费")
            .append(MathUtil.round(historyRealPrice, 2))
            .append("元，还差")
            .append(MathUtil.round(limitPrice - historyRealPrice, 2))
            .append("元，继续加油哦～");
        return errorMessage.toString();
    }
}
