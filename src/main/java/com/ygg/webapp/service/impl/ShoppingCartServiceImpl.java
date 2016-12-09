package com.ygg.webapp.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.dao.TempAccountDao;
import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.exception.BusException;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.service.ShoppingCartServiceNew;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonProperties;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.view.OrderProductView;
import com.ygg.webapp.view.ProductCountView;
import com.ygg.webapp.view.ShoppingCartView;

@Service("shoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService
{
    
    private int cartValidMillis = 24 * 60 * 60 * 1000 * 30;
    
    private int againCartValidMillis = 5 * 60 * 1000;
    
    private Logger log = Logger.getLogger(ShoppingCartServiceImpl.class);
    
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
    
    @Resource(name = "shoppingCartServiceNew")
    private ShoppingCartServiceNew shoppingCartServiceNew;
    
    @Override
    public List<ShoppingCartView> listShoppingCartInfo(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String addProductToShoppingCart(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String removeProductToShoppingCart(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String findShoppingCartByAccountIdAndProductId(int accountId, int productId)
        throws Exception
    {
        int productcount = 0;
        JsonObject result = new JsonObject();
        int cartCount = this.shoppingCartDao.findShoppingCartCountByAccountId(accountId);
        ShoppingCartEntity sce = this.shoppingCartDao.findNormalCartByPIdAndAId(productId, accountId);
        if (sce != null)
            productcount = sce.getProductCount();
        
        String endSecond = "";
        String validTimeStr = this.shoppingCartDao.findValidTimeByAid(accountId);
        if (validTimeStr != null && !validTimeStr.equals(""))
        {
            Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().before(validTime)) // 购物车锁定没过期
            {
                endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
            }
        }
        result.addProperty("cartCount", cartCount);
        result.addProperty("endSecond", endSecond);
        result.addProperty("productcount", productcount); // / 这个已login的账号在购物车内此productId的数量
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    @Override
    public String findShoppingCartCountByAccountId(int accountId)
        throws Exception
    {
        JsonObject result = new JsonObject();
        int cartCount = this.shoppingCartDao.findShoppingCartCountByAccountId(accountId);
        result.addProperty("cartCount", cartCount);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String getCartToken(String requestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String imei = param.get("imei").getAsString();
        
        int id = tadi.findIdByImei(imei);
        if (id == CommonConstant.ID_NOT_EXIST)
        {
            tadi.addTempAccount(imei);
            id = tadi.findIdByImei(imei);
        }
        
        result.addProperty("cartToekn", id);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    /**
     * REQUIRE
     * 
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    public String editShoppingCart(String requestParams)
        throws ServiceException
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        int productId = 0;
        int id = -1; // accountId
        String status = CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue();
        String endSecond = "-1";
        String responseParams = "";
        try
        {
            // responseParams = this.shoppingCartServiceNew.editCrudsc(requestParams) ; // 新起一个事务 REQUIRE_NEW
            // 转到controller中去执行　
            responseParams = requestParams;
            JsonObject param = (JsonObject)parser.parse(responseParams);
            
            String noFlag = (param.get("noFlag") == null ? "" : param.get("noFlag").getAsString()); // 从　库存不足中来的
            if (noFlag != null && noFlag.toString().equals("noFlag"))
            {
                return responseParams;
            }
            
            boolean successFlag = (param.get("successFlag") == null ? false : param.get("successFlag").getAsBoolean());
            status = (param.get("status") == null ? "0" : param.get("status").getAsString());
            Object againOperateSetObj = param.get("againOperateSet");
            id = (param.get("id") == null ? CommonConstant.ID_NOT_EXIST : param.get("id").getAsInt());
            String type = param.get("type").getAsString();
            productId = (param.get("productId") == null ? 0 : param.get("productId").getAsInt());
            int stockCount = (param.get("stockCount") == null ? 0 : param.get("stockCount").getAsInt());
            // int restrictionCount = (param.get("restrictionCount") == null ?
            // 0:param.get("restrictionCount").getAsInt() ) ;
            
            Set<String> againOperateSet = null; // //==================
            String errorCode = null;
            if (againOperateSetObj != null)
            {
                againOperateSet = JSONUtils.fromJson(againOperateSetObj.toString(), new TypeToken<Set<String>>()
                {
                });
            }
            
            if (successFlag)
            {
                setValidTime(result, id, type);
                // ConnectionManager.commitTransaction();
                // 防止死锁，提交之前事务，再次开启新的事务
                // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
                if (againOperateSet.contains("loginAgainAddLockTime")) // 购物车锁定已过期,需要把购物车存在的商品重新锁定
                {
                    successFlag = loginAgainAddOldProduct(id, productId);
                    if (successFlag)
                    {
                        // ConnectionManager.commitTransaction();
                    }
                    else
                    {
                        
                        List<ShoppingCartEntity> sces = this.shoppingCartDao.findAllNormalCartByAIdExceptPid(id, productId);
                        // ConnectionManager.rollbackTransaction();
                        // 失败则把购物车商品设为无货
                        /*
                         * 拿到另一个REQUIRE_NEW的方法中去实现
                         * 
                         * List<ShoppingCartEntity> sces = this.shoppingCartDao.findAllNormalCartByAIdExceptPid(id,
                         * productId); for (ShoppingCartEntity sce : sces) {
                         * sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
                         * (shoppingCartDao.updateShoppingCart(sce) == 0) { log.error("设为商品无货失败1,productId:" +
                         * sce.getProductId() + ",accountId:" + id); } }
                         */
                        this.shoppingCartServiceNew.updateShoppingCart(sces, null); // 发起另一个事务　REQUIRE_NEW
                        // errorCode = CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue()+"" ;
                        /*
                         * ServiceException se = new ServiceException(errorCode) ; se.putMap("status",
                         * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); se.putMap("errorCode", errorCode);
                         * //se.putMap("sceList", sces); throw se ; // 回滚
                         */
                        BusException be = new BusException();
                        be.putModelObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
                        be.putModelObject("endSecond", endSecond);
                        throw be;// 回滚当前事务
                    }
                }
                else if (againOperateSet.contains("notLoginAgainAddLockTime"))
                {
                    successFlag = notLoginAgainAddOldProduct(id, productId);
                    if (successFlag)
                    {
                        // ConnectionManager.commitTransaction();
                    }
                    else
                    {
                        
                        List<TempShoppingCartEntity> tsces = tempShoppingDao.findAllNormalCartByAIdExceptPid(id, productId);
                        
                        // ConnectionManager.rollbackTransaction();
                        // 失败则把购物车商品设为无货
                        /*
                         * 拿到另一个REQUIRE_NEW的方法中去实现
                         * 
                         * List<TempShoppingCartEntity> tsces = tempShoppingDao.findAllNormalCartByAIdExceptPid(id,
                         * productId); for (TempShoppingCartEntity tsce : tsces) {
                         * tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
                         * (this.tempShoppingDao.updateShoppingCart(tsce) == 0) { log.error("设为商品无货失败2,productId:" +
                         * tsce.getProductId() + ",accountId:" + id); } }
                         */
                        this.shoppingCartServiceNew.updateShoppingCart(null, tsces); // 发起另一个事务　REQUIRE_NEW
                        // errorCode = CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue()+"" ;
                        /*
                         * ServiceException se = new ServiceException(errorCode) ; se.putMap("status",
                         * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); se.putMap("errorCode", errorCode);
                         * throw se ; // 回滚
                         */BusException be = new BusException();
                        be.putModelObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
                        be.putModelObject("endSecond", endSecond);
                        throw be;// 回滚当前事务
                        
                    }
                }
            }
            else
            {
                
                // ConnectionManager.rollbackTransaction();
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                // return result.toString();
                
                errorCode = CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue();
                BusException be = new BusException("未知错误");
                be.putModelObject("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue()); // "未知错误");
                be.putModelObject("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                throw be;// 回滚
                // throw new BusException(errorCode); // 回滚
            }
            // //////////////////////////////加入过期时间　
            String validTimeStr = "";
            
            if (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue()))
            {
                validTimeStr = this.shoppingCartDao.findValidTimeByAid(id);
            }
            else
            {
                validTimeStr = this.tempShoppingDao.findValidTimeByAid(id);
            }
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 购物车锁定没过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            
            result.addProperty("endSecond", endSecond);
            result.addProperty("stockCount", stockCount);
            
        }
        catch (Exception e)
        {
            // String errorCode = "ShoppingCart--edit-异常!" ;
            
            this.log.error("ShoppingCart--edit-异常!", e);
            if (e instanceof ServiceException)
            {
                ServiceException e1 = (ServiceException)e;
                status = (e1.getMap("status") == null ? "0" : (String)e1.getMap("status"));
                String errorCode = (e1.getMap("errorCode") == null ? "" : e1.getMap("errorCode") + "");
                int stockCount = (e1.getMap("stockCount") == null ? 0 : (Integer)e1.getMap("stockCount"));
                String restrictionCount = (e1.getMap("restrictionCount") == null ? "0" : e1.getMap("restrictionCount") + "");
                ShoppingCartEntity sce = (e1.getMap("sce") == null ? null : (ShoppingCartEntity)e1.getMap("sce"));
                TempShoppingCartEntity tsce = (e1.getMap("tsce") == null ? null : (TempShoppingCartEntity)e1.getMap("tsce"));
                if (sce != null)
                {
                    List<ShoppingCartEntity> list = new ArrayList<ShoppingCartEntity>();
                    if (stockCount == 0) // 没货
                    {
                        list.add(sce);
                        this.shoppingCartServiceNew.updateShoppingCart(list, null);
                        /*
                         * sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
                         * (this.shoppingCartDao.updateShoppingCart(sce) == 0) { log.warn("设为商品无货失败,productId:" +
                         * productId + ",accountId:" + id); }
                         */
                    }
                }
                if (tsce != null)
                {
                    List<TempShoppingCartEntity> list = new ArrayList<TempShoppingCartEntity>();
                    if (stockCount == 0) // 没货
                    {
                        list.add(tsce);
                        this.shoppingCartServiceNew.updateShoppingCart(null, list);
                        /*
                         * tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
                         * (this.tempShoppingDao.updateShoppingCart(tsce) == 0) { log.warn("设为商品无货失败,productId:" +
                         * productId + ",accountId:" + id); }
                         */
                    }
                }
                result.addProperty("status", status); // CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", errorCode);
                result.addProperty("stockCount", stockCount + "");
                result.addProperty("restrictionCount", restrictionCount); // / errorCode =5时才取得此值
                return result.toString();
            }
            // else if( e instanceof BusException)
            // throw e ;
            throw e;
        }
        result.addProperty("status", status); // CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    /**
     * 此方法要带有事务 REQUIRE_NEW
     */
    @Override
    public String editCrudsc(String requestParams)
        throws ServiceException
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int id = param.get("id").getAsInt();
        String type = param.get("type").getAsString();
        int productId = param.get("productId").getAsInt();
        short modifyCount = param.get("count").getAsShort();
        
        result.addProperty("id", id);
        ;
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
            // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
            // 超过限购数量
            ProductCountEntity pce = pcdi.findCountInfoByIdForUpdate(productId);
            if (modifyCount > pce.getRestriction())
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.RESTRICTION_EXCEED.getValue());
                result.addProperty("restrictionCount", pce.getRestriction() + "");
                // ConnectionManager.rollbackTransaction();
                // return result.toString();
                errorCode = CommonEnum.CART_EDIT_ERRORCODE.RESTRICTION_EXCEED.getValue();
                throw new ServiceException(errorCode); // 回滚
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
                    throw new ServiceException(errorCode); // 回滚
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
                        if (availableCount >= modifyCount)
                        {
                            successFlag = loginUpAdd(id, productId, modifyCount, pce, againOperateSet);
                        }
                        else
                        {
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                            result.addProperty("stockCount", availableCount);
                            // ConnectionManager.rollbackTransaction();
                            // return result.toString();
                            
                            errorCode = CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue();
                            ServiceException se = new ServiceException(errorCode);
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
                        
                        if (availableCount >= modifyCount) // 库存数量充足
                        {
                            successFlag = loginUpModify(id, productId, modifyCount, pce, sce, currLockCount, againOperateSet);
                        }
                        else
                        // 库存数量不足
                        {
                            // ConnectionManager.rollbackTransaction();
                            /*
                             * 拿到另一个REQUIRE_NEW的方法中去实现
                             * 
                             * 
                             * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                             * result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                             * result.addProperty("stockCount", availableCount); if (availableCount == 0) // 没货 {
                             * sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
                             * (this.shoppingCartDao.updateShoppingCart(sce) == 0) { log.warn("设为商品无货失败,productId:" +
                             * productId + ",accountId:" + id); } }
                             */
                            // return result.toString();
                            List<ShoppingCartEntity> sces = new ArrayList<ShoppingCartEntity>();
                            sces.add(sce);
                            this.shoppingCartServiceNew.updateShoppingCart(sces, null); // 发起另一个事务　REQUIRE_NEW
                            errorCode = CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue();
                            ServiceException se = new ServiceException(errorCode);
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            se.putMap("errorCode", errorCode);
                            se.putMap("stockCount", availableCount);
                            // se.putMap("sce", sce);
                            throw se; // 回滚
                            // System.out.println("-------sce-------------");
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
                    ServiceException se = new ServiceException(errorCode);
                    se.putMap("errorCode", "临时账号不存在");
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
                            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                            result.addProperty("stockCount", availableCount);
                            // ConnectionManager.rollbackTransaction();
                            // return result.toString();
                            errorCode = CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue();
                            ServiceException se = new ServiceException(errorCode);
                            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                            se.putMap("stockCount", availableCount);
                            se.putMap("errorCode", "库存不足");
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
                            /**
                             * 拿到另一个REQUIRE_NEW的方法中去实现 以下更新要放到此方法外部去　
                             */
                            /*
                             * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                             * result.addProperty("errorCode", CommonEnum.CART_EDIT_ERRORCODE.STOCK_LACK.getValue());
                             * result.addProperty("stockCount", availableCount); if (availableCount == 0) // 没货 {
                             * tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
                             * (this.tempShoppingDao.updateShoppingCart(tsce) == 0) { log.warn("设为商品无货失败,productId:" +
                             * productId + ",accountId:" + id); } }
                             */
                            // return result.toString();
                            List<TempShoppingCartEntity> tsces = new ArrayList<TempShoppingCartEntity>();
                            tsces.add(tsce);
                            this.shoppingCartServiceNew.updateShoppingCart(null, tsces); // 发起另一个事务　REQUIRE_NEW
                            errorCode = CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue() + "";
                            ServiceException se = new ServiceException(errorCode);
                            se.putMap("errorCode", errorCode);
                            se.putMap("stockCount", availableCount);
                            se.putMap("tsce", tsce);
                            throw se; // 回滚
                            // this.shoppingCartDao.updateShoppingCart(sce) ;
                            // System.out.println("-------tsce-------------");
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
                    
                }
            }
            
            /*
             * if(successFlag) { ConnectionManager.commitTransaction(); // 防止死锁，提交之前事务，再次开启新的事务
             * ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig); if
             * (againOperateSet.contains("loginAgainAddLockTime")) // 购物车锁定已过期,需要把购物车存在的商品重新锁定 { successFlag =
             * loginAgainAddOldProduct(id ,productId); if(successFlag) { ConnectionManager.commitTransaction(); } else {
             * ConnectionManager.rollbackTransaction(); // 失败则把购物车商品设为无货 List<ShoppingCartEntity> sces =
             * this.shoppingCartDao.findAllNormalCartByAIdExceptPid(id, productId); for (ShoppingCartEntity sce : sces)
             * { sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
             * (shoppingCartDao.updateShoppingCart(sce) == 0) { log.error("设为商品无货失败1,productId:" + sce.getProductId() +
             * ",accountId:" + id); } } } } else if (againOperateSet.contains("notLoginAgainAddLockTime")) { successFlag
             * = notLoginAgainAddOldProduct(id ,productId); if(successFlag) { ConnectionManager.commitTransaction(); }
             * else { ConnectionManager.rollbackTransaction(); // 失败则把购物车商品设为无货 List<TempShoppingCartEntity> tsces =
             * tempShoppingDao.findAllNormalCartByAIdExceptPid(id, productId); for (TempShoppingCartEntity tsce : tsces)
             * { tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue()); if
             * (this.tempShoppingDao.updateShoppingCart(tsce) == 0) { log.error("设为商品无货失败2,productId:" +
             * tsce.getProductId() + ",accountId:" + id); } } } } } else { //ConnectionManager.rollbackTransaction();
             * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
             * result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue()); //return
             * result.toString();
             * 
             * errorCode = CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue() ; throw new
             * ServiceException(errorCode); // 回滚 }
             */
            
            result.addProperty("successFlag", successFlag);
            result.add("againOperateSet", parser.parse(JSONUtils.toJson(againOperateSet, false)));
        }
        catch (Exception e) // 回滚事务
        {
            // ConnectionManager.rollbackTransaction();
            errorCode = "edit购物车数据库出错!";
            log.error(errorCode + e);
            if (e instanceof ServiceException)
                throw e;
            
            ServiceException se = new ServiceException(errorCode);
            throw se;
        }
        
        /*
         * String validTimeStr = ""; String endSecond = "-1"; if
         * (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue())) { validTimeStr =
         * this.shoppingCartDao.findValidTimeByAid(id); } else { validTimeStr =
         * this.tempShoppingDao.findValidTimeByAid(id); } if (validTimeStr != null) // 存在购物车锁定时间 信息 { Date validTime =
         * CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss"); if (new Date().before(validTime)) // 购物车锁定没过期 {
         * endSecond = (validTime.getTime() - new Date().getTime())/1000 + ""; } } result.addProperty("endSecond",
         * endSecond);
         */
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    private boolean loginDelete(int id, int productId, ProductCountEntity pce, ShoppingCartEntity sce, int modifyCount)
        throws ServiceException
    {
        if (modifyCount == 0) // 正常购物车
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
            if (new Date().after(validTime)) // 购物车锁定已过期,需要把购物车存在的商品重新锁定
            {
                againOperateSet.add("loginAgainAddLockTime");
            }
            if (shoppingCartDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + id);
                return false;
            }
        }
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
            return false;
        }
        if (currLockCount == 0) // 购物车锁定已过期
        {
            if (this.shoppingCartDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + id);
                return false;
            }
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
        if (modifyCount == 0) // 正常购物车
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
            return false;
        }
        if (currLockCount == 0) // 购物车锁定已过期
        {
            if (tempShoppingDao.updateLockTime(id, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + id);
                return false;
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
    
    private boolean loginAgainAddOldProduct(int id, int productId)
        throws ServiceException
    {
        List<ShoppingCartEntity> sces = this.shoppingCartDao.findAllNormalCartByAIdExceptPid(id, productId);
        List<Integer> productIds = new ArrayList<Integer>();
        for (ShoppingCartEntity shoppingCartEntity : sces)
        {
            productIds.add(shoppingCartEntity.getProductId());
        }
        
        List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds);
        int index = 0;
        for (ProductCountEntity pce : pces)
        {
            ShoppingCartEntity sce = sces.get(index);
            if (!loginAgainAddLockCount(id, sce, pce))
            {
                return false;
            }
            index++;
        }
        return true;
    }
    
    private boolean loginAgainAddLockCount(int accountId, ShoppingCartEntity sce, ProductCountEntity pce)
        throws ServiceException
    {
        if (!isValidProduct(sce.getProductId()) || pce.getStock() - pce.getLock() <= 0)
        {
            sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
            if (shoppingCartDao.updateShoppingCart(sce) == 0)
            {
                log.warn("设为商品无货失败,productId:" + sce.getProductId() + ",accountId:" + accountId);
                return false;
            }
        }
        else
        // 商品还可以被锁定
        {
            int offset = pce.getStock() - pce.getLock() > sce.getProductCount() ? sce.getProductCount() : pce.getStock() - pce.getLock();
            if (this.shoppingCartDao.addLockCount(sce.getProductId(), accountId, offset) == 0)
            {
                log.warn("addLockCount失败,productId:" + sce.getProductId() + ",accountId:" + accountId);
                return false;
            }
            
            pce.setLock(pce.getLock() + offset);
            if (pcdi.updateCountInfo(pce) == 0)
            {
                log.warn("updateCountInfo无匹配,productId:" + sce.getProductId() + ",accountId:" + accountId);
                return false;
            }
            
            if (sce.getProductCount() > offset) // 修改购物车数量为当前锁定数量
            {
                sce.setProductCount((short)offset);
                if (shoppingCartDao.updateShoppingCart(sce) == 0)
                {
                    log.warn("修改商品数量失败,productId:" + sce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean notLoginAgainAddOldProduct(int id, int productId)
        throws ServiceException
    {
        List<TempShoppingCartEntity> tsces = this.tempShoppingDao.findAllNormalCartByAIdExceptPid(id, productId);
        List<Integer> productIds = new ArrayList<Integer>();
        for (TempShoppingCartEntity tempShoppingCartEntity : tsces)
        {
            productIds.add(tempShoppingCartEntity.getProductId());
        }
        
        List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds);
        int index = 0;
        for (ProductCountEntity pce : pces)
        {
            TempShoppingCartEntity tsce = tsces.get(index);
            if (!isValidProduct(tsce.getProductId()) || pce.getStock() - pce.getLock() <= 0)
            {
                tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (tempShoppingDao.updateShoppingCart(tsce) == 0)
                {
                    log.warn("设为商品无货失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                    return false;
                }
            }
            else
            // 商品还可以被锁定
            {
                int offset = pce.getStock() - pce.getLock() > tsce.getProductCount() ? tsce.getProductCount() : pce.getStock() - pce.getLock();
                if (tempShoppingDao.addLockCount(tsce.getProductId(), id, offset) == 0)
                {
                    log.warn("addLockCount失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                    return false;
                }
                
                pce.setLock(pce.getLock() + offset);
                if (pcdi.updateCountInfo(pce) == 0)
                {
                    log.warn("updateCountInfo无匹配,productId:" + tsce.getProductId() + ",accountId:" + id);
                    return false;
                }
                
                if (tsce.getProductCount() > offset) // 修改购物车数量为当前锁定数量
                {
                    tsce.setProductCount((short)offset);
                    if (tempShoppingDao.updateShoppingCart(tsce) == 0)
                    {
                        log.warn("修改商品数量失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                        return false;
                    }
                }
            }
            index++;
        }
        return true;
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
            return false;
        }
        return true;
    }
    
    @Override
    public String merger(String requestParams)
        throws ServiceException
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int tempAccountId = param.get("cartToken").getAsInt();
        String errorCode = "";
        // 用户不存在
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.CART_MERGER_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            // ConnectionManager.rollbackTransaction();
            // return result.toString();
            errorCode = CommonEnum.CART_MERGER_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue();
            ServiceException se = new ServiceException(errorCode);
            se.putMap("errorCode", "accountId不存在");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            throw se; // 回滚
        }
        // 临时用户不存在
        if (!tadi.idIsExist(tempAccountId))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.CART_MERGER_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue());
            // ConnectionManager.rollbackTransaction();
            // return result.toString();
            ServiceException se = new ServiceException(errorCode);
            se.putMap("errorCode", "cartToekn不存在");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            throw se; // 回滚
        }
        
        boolean cartIsExpire = true;
        boolean tempCartIsExpire = true;
        
        String validTimeStr = this.shoppingCartDao.findValidTimeByAid(accountId);
        if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
        {
            Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().before(validTime)) // 购物车锁定没过期
            {
                cartIsExpire = false;
            }
        }
        
        String tempValidTimeStr = this.tempShoppingDao.findValidTimeByAid(tempAccountId);
        if (tempValidTimeStr != null && !tempValidTimeStr.equals("")) // 存在临时购物车锁定时间 信息
        {
            Date tempValidTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().before(tempValidTime)) // 临时购物车锁定没过期
            {
                tempCartIsExpire = false;
            }
        }
        
        List<Integer> allProductIds = new ArrayList<Integer>();
        List<ShoppingCartEntity> sces = this.shoppingCartDao.findAllNormalCartByAId(accountId);
        Map<Integer, ShoppingCartEntity> sceMap = new HashMap<Integer, ShoppingCartEntity>();
        for (ShoppingCartEntity sce : sces)
        {
            sceMap.put(sce.getProductId(), sce);
            if (!allProductIds.contains(sce.getProductId()))
            {
                allProductIds.add(sce.getProductId());
            }
        }
        List<TempShoppingCartEntity> tsces = this.tempShoppingDao.findAllNormalCartByAId(tempAccountId);
        for (TempShoppingCartEntity tsce : tsces)
        {
            if (!allProductIds.contains(tsce.getProductId()))
            {
                allProductIds.add(tsce.getProductId());
            }
        }
        Collections.sort(allProductIds);
        try
        {
            // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
            List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(allProductIds); // 加锁
            Map<Integer, ProductCountEntity> pceMap = new HashMap<Integer, ProductCountEntity>();
            int index = 0;
            for (ProductCountEntity pce : pces)
            {
                pceMap.put(allProductIds.get(index), pce);
                index++;
            }
            
            boolean successFlag = true;
            // 购物车与临时购物车均以过期-合并商品到购物车,不抢回过期商品,不修改锁定数量、锁定时间、商品数量表
            if (cartIsExpire && tempCartIsExpire)
            {
                successFlag = expireCartAndTempMerger(accountId, sceMap, tsces, pceMap);
            }
            // 购物车与临时购物车均未过期-合并商品到购物车,无过期商品,修改锁定数量、锁定时间、商品数量表
            else if (!cartIsExpire && !tempCartIsExpire)
            {
                successFlag = notExpireCartAndTempMerge(accountId, tempAccountId, validTimeStr, tempValidTimeStr, sceMap, tsces, pceMap);
            }
            // 购物车过期、临时购物车未过期
            else if (cartIsExpire && !tempCartIsExpire)
            {
                successFlag = expireCartNotExpireTempMerger(accountId, tempAccountId, tempValidTimeStr, sceMap, tsces, pceMap);
            }
            // 购物车未过期、临时购物车过期
            else if (!cartIsExpire && tempCartIsExpire)
            {
                
            }
            
            if (successFlag)
            {
                // ConnectionManager.commitTransaction();
            }
            else
            {
                // ConnectionManager.rollbackTransaction();
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                return result.toString();
            }
        }
        catch (Exception e)
        {
            // ConnectionManager.rollbackTransaction();
            log.error("merger出错！");
            // throw e;
            ServiceException se = new ServiceException(errorCode);
            se.putMap("errorCode", "merger操作数据库错误!");
            se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            throw se; // 回滚
            
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    private boolean expireCartAndTempMerger(int accountId, Map<Integer, ShoppingCartEntity> sceMap, List<TempShoppingCartEntity> tsces, Map<Integer, ProductCountEntity> pceMap)
        throws ServiceException
    {
        for (TempShoppingCartEntity tsce : tsces)// 将临时购物车放回到购物车
        {
            if (sceMap.containsKey((tsce.getProductId()))) // 商品相同
            {
                ShoppingCartEntity sce = sceMap.get(tsce.getProductId());
                ProductCountEntity pce = pceMap.get(tsce.getProductId());
                int productCount =
                    (sce.getProductCount() + tsce.getProductCount()) > pce.getRestriction() ? pce.getRestriction() : (sce.getProductCount() + tsce.getProductCount());
                sce.setProductCount((short)productCount);
                if (this.shoppingCartDao.updateShoppingCart(sce) == 0)
                {
                    log.warn("updateShoppingCart1无匹配,productId:" + tsce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
            }
            else
            // 商品不相同
            {
                ShoppingCartEntity sce = new ShoppingCartEntity();
                sce.setAccountId(accountId);
                sce.setProductId(tsce.getProductId());
                sce.setProductCount(tsce.getProductCount());
                if (shoppingCartDao.addShoppingCart(sce) == 0)
                {
                    log.warn("addShoppingCart1失败,productId:" + tsce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
            }
            tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.MERGE_CART.getValue());
            if (this.tempShoppingDao.updateShoppingCart(tsce) == 0)
            {
                log.warn("updateShoppingCart1无匹配,productId:" + tsce.getProductId() + ",accountId:" + accountId);
                return false;
            }
        }
        return true;
    }
    
    private boolean notExpireCartAndTempMerge(int accountId, int tempAccountId, String validTimeStr, String tempValidTimeStr, Map<Integer, ShoppingCartEntity> sceMap,
        List<TempShoppingCartEntity> tsces, Map<Integer, ProductCountEntity> pceMap)
        throws ServiceException
    {
        for (TempShoppingCartEntity tsce : tsces)// 将临时购物车放回到购物车
        {
            if (sceMap.containsKey((tsce.getProductId()))) // 商品相同
            {
                ShoppingCartEntity sce = sceMap.get(tsce.getProductId());
                ProductCountEntity pce = pceMap.get(tsce.getProductId());
                int lockCount =
                    (sce.getProductCount() + tsce.getProductCount()) >= pce.getRestriction() ? pce.getLock()
                        - (sce.getProductCount() + tsce.getProductCount() - pce.getRestriction()) : pce.getLock();
                pce.setLock(lockCount);
                if (pcdi.updateCountInfo(pce) == 0)
                {
                    log.warn("updateCountInfo无匹配,productId:" + sce.getProductId());
                    return false;
                }
                int productCount =
                    (sce.getProductCount() + tsce.getProductCount()) >= pce.getRestriction() ? pce.getRestriction() : (sce.getProductCount() + tsce.getProductCount());
                sce.setProductCount((short)productCount);
                if (this.shoppingCartDao.updateShoppingCart(sce) == 0)
                {
                    log.warn("updateShoppingCart没有匹配记录,accountId:" + accountId);
                    return false;
                }
                if (shoppingCartDao.updateLockCount(sce.getProductId(), accountId, productCount) == 0)
                {
                    log.warn("updateLockCount无匹配,productId:" + sce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
                
            }
            else
            // 商品不相同
            {
                ShoppingCartEntity sce = new ShoppingCartEntity();
                sce.setAccountId(accountId);
                sce.setProductId(tsce.getProductId());
                sce.setProductCount(tsce.getProductCount());
                if (shoppingCartDao.addShoppingCart(sce) == 0)
                {
                    log.warn("addShoppingCart失败,productId:" + tsce.getProductId() + ",accountId:" + tempAccountId);
                    return false;
                }
                if (shoppingCartDao.addLockCount(tsce.getProductId(), accountId, tsce.getProductCount()) == 0)
                {
                    log.warn("addLockCount失败,productId:" + tsce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
            }
            
            if (this.tempShoppingDao.deleteLockCountByPIdAndAId(tsce.getProductId(), tempAccountId) == 0)
            {
                log.warn("deleteLockCountByPIdAndAId无匹配,productId:" + tsce.getProductId() + ",accountId:" + tempAccountId);
                return false;
            }
            
            tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.MERGE_CART.getValue());
            if (tempShoppingDao.updateShoppingCart(tsce) == 0)
            {
                log.warn("updateShoppingCart无匹配,productId:" + tsce.getProductId() + ",tempAccountId:" + tempAccountId);
                return false;
            }
        }
        
        Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
        Date tempValidTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
        if (validTime.before(tempValidTime))
        {
            if (this.shoppingCartDao.updateLockTime(accountId, tempValidTime.getTime()) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + accountId);
                return false;
            }
        }
        return true;
    }
    
    private boolean expireCartNotExpireTempMerger(int accountId, int tempAccountId, String tempValidTimeStr, Map<Integer, ShoppingCartEntity> sceMap,
        List<TempShoppingCartEntity> tsces, Map<Integer, ProductCountEntity> pceMap)
        throws ServiceException
    {
        List<Integer> repeatProductIds = new ArrayList<Integer>();
        for (TempShoppingCartEntity tsce : tsces) // 将临时购物车放回到购物车
        {
            if (sceMap.containsKey((tsce.getProductId()))) // 商品相同
            {
                repeatProductIds.add(tsce.getProductId());
                ShoppingCartEntity sce = sceMap.get(tsce.getProductId());
                ProductCountEntity pce = pceMap.get(tsce.getProductId());
                int availableCount = pce.getStock() - pce.getLock();
                int lockCount = 0;
                int productCount = 0;
                if (availableCount >= sce.getProductCount()) // 库存数量充足
                {
                    lockCount = (sce.getProductCount() + tsce.getProductCount()) >= pce.getRestriction() ? (pce.getRestriction() - tsce.getProductCount()) : sce.getProductCount();
                    productCount =
                        (sce.getProductCount() + tsce.getProductCount()) >= pce.getRestriction() ? pce.getRestriction() : (sce.getProductCount() + tsce.getProductCount());
                }
                else
                // 库存数量不足
                {
                    lockCount = availableCount + tsce.getProductCount() >= pce.getRestriction() ? pce.getRestriction() - tsce.getProductCount() : availableCount;
                    productCount = sce.getProductCount() + lockCount >= pce.getRestriction() ? pce.getRestriction() : lockCount + tsce.getProductCount();
                }
                pce.setLock(pce.getLock() + lockCount);
                if (pcdi.updateCountInfo(pce) == 0)
                {
                    log.warn("updateCountInfo无匹配,productId:" + sce.getProductId());
                    return false;
                }
                if (this.shoppingCartDao.addLockCount(sce.getProductId(), accountId, productCount) == 0)
                {
                    log.warn("addLockCount失败,productId:" + sce.getProductCount() + ",accountId:" + accountId);
                    return false;
                }
                sce.setProductCount((short)productCount);
                if (shoppingCartDao.updateShoppingCart(sce) == 0)
                {
                    log.warn("updateShoppingCart无匹配,productId:" + sce.getProductCount() + ",accountId:" + accountId);
                    return false;
                }
            }
            else
            // 商品不相同
            {
                ShoppingCartEntity sce = new ShoppingCartEntity();
                sce.setAccountId(accountId);
                sce.setProductId(tsce.getProductId());
                sce.setProductCount(tsce.getProductCount());
                if (shoppingCartDao.addShoppingCart(sce) == 0)
                {
                    log.warn("addShoppingCart失败,productId:" + tsce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
                if (shoppingCartDao.addLockCount(tsce.getProductId(), accountId, tsce.getProductCount()) == 0)
                {
                    log.warn("addLockCount失败,productId:" + tsce.getProductId() + ",accountId:" + accountId);
                    return false;
                }
            }
            
            if (this.tempShoppingDao.deleteLockCountByPIdAndAId(tsce.getProductId(), tempAccountId) == 0)
            {
                log.warn("deleteLockCountByPIdAndAId无匹配,productId:" + tsce.getProductId() + ",accountId:" + tempAccountId);
                return false;
            }
            
            tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.MERGE_CART.getValue());
            if (tempShoppingDao.updateShoppingCart(tsce) == 0)
            {
                log.warn("updateShoppingCart无匹配,productId:" + tsce.getProductId() + ",accountId:" + tempAccountId);
                return false;
            }
        }
        
        for (Integer repeatProductId : sceMap.keySet()) // 购物车中已过期但和临时购物车中不相同的商品　　没有处理过的购物车商品需要重新抢回，设为锁定　
        {
            if (!repeatProductIds.contains(repeatProductId))
            {
                ShoppingCartEntity sce = sceMap.get(repeatProductId);
                ProductCountEntity pce = pceMap.get(repeatProductId);
                if (!loginAgainAddLockCount(accountId, sce, pce))
                {
                    return false;
                }
            }
        }
        
        Date tempValidTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
        if (this.shoppingCartDao.updateLockTime(accountId, tempValidTime.getTime()) == 0)
        {
            log.warn("updateLockTime无匹配,accountId:" + accountId);
            if (this.shoppingCartDao.addLockTime(accountId, System.currentTimeMillis() + cartValidMillis) == 0)
            {
                log.warn("updateLockTime无匹配,accountId:" + accountId);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public String list(String requestParams)
        throws ServiceException
    {
        
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int id = param.get("id").getAsInt();
        String type = param.get("type").getAsString();
        boolean cartIsExpire = true;
        String endSecond = "-1";
        
        List<String> tips = new ArrayList<String>();
        //zhangld 去掉判断 isQqbs
        tips.add("* 心动慈露特卖全场" +   "包邮。");
        result.add("tips", parser.parse(JSONUtils.toJson(tips, false)));
        
        List<OrderProductView> normalOpvs = new ArrayList<OrderProductView>();
        List<OrderProductView> stockLackOpvs = new ArrayList<OrderProductView>();
        DecimalFormat df = new DecimalFormat("0.00");
        
        // 用户已登录
        if (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue()))
        {
            int accountId = id;
            // 用户不存在
            if (adi.findAccountById(accountId) == null)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.CART_LIST_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
                // ConnectionManager.rollbackTransaction();
                // return result.toString();
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", "账号不存在!");
                throw se;
            }
            
            String validTimeStr = this.shoppingCartDao.findValidTimeByAid(accountId);
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 购物车锁定没过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                    cartIsExpire = false;
                }
            }
            
            List<ShoppingCartEntity> normalSces = this.shoppingCartDao.findAllNormalCartByAId(accountId);
            List<ShoppingCartEntity> lackSces = shoppingCartDao.findAllLackCartByAId(accountId);
            if (cartIsExpire) // 已过期
            {
                for (ShoppingCartEntity sce : normalSces) // 购物车正常商品展示
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(sce.getProductId());
                    ProductCommonEntity pcommone = pcommondi.findProductCommonInfoById(sce.getProductId());
                    OrderProductView opv = new OrderProductView();
                    opv.setCount(sce.getProductCount() + "");
                    opv.setImage(pcommone.getSmallImage());
                    opv.setProductId(sce.getProductId() + "");
                    opv.setRestriction(pce.getRestriction() + "");
                    
                    opv.setSalesPrice(df.format(pcommone.getSalesPrice()));
                    opv.setShortName(pcommone.getShortName());
                    if (pce.getStock() - pce.getLock() >= sce.getProductCount()) // 正常展示
                    {
                        opv.setStockCount((pce.getStock() - pce.getLock()) + "");
                        opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                        opv.setUpdateTime(sce.getUpdateTime());
                        normalOpvs.add(opv);
                    }
                    else
                    // 已抢完
                    {
                        sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                         shoppingCartDao.updateShoppingCart(sce);
                        List<ShoppingCartEntity> sces = new ArrayList<ShoppingCartEntity>();
                        sces.add(sce);
                        shoppingCartServiceNew.updateShoppingCart(sces, null);
                        opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.NO.getValue());
                        opv.setUpdateTime(sce.getUpdateTime());
                        stockLackOpvs.add(opv);
                    }
                }
                
            }
            else
            // 未过期
            {
                for (ShoppingCartEntity sce : normalSces) // 购物车正常商品展示
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(sce.getProductId());
                    ProductCommonEntity pcommone = pcommondi.findProductCommonInfoById(sce.getProductId());
                    OrderProductView opv = new OrderProductView();
                    opv.setCount(sce.getProductCount() + "");
                    opv.setImage(pcommone.getSmallImage());
                    opv.setProductId(sce.getProductId() + "");
                    opv.setRestriction(pce.getRestriction() + "");
                    opv.setSalesPrice(df.format(pcommone.getSalesPrice()));
                    opv.setShortName(pcommone.getShortName());
                    opv.setStockCount((pce.getStock() - pce.getLock() + sce.getProductCount()) + "");
                    opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                    opv.setUpdateTime(sce.getUpdateTime());
                    normalOpvs.add(opv);
                }
            }
            
            for (ShoppingCartEntity sce : lackSces) // 购物车没货商品展示
            {
                ProductCountEntity pce = pcdi.findCountInfoById(sce.getProductId());
                ProductCommonEntity pcommone = pcommondi.findProductCommonInfoById(sce.getProductId());
                OrderProductView opv = new OrderProductView();
                opv.setCount(sce.getProductCount() + "");
                opv.setImage(pcommone.getSmallImage());
                opv.setProductId(sce.getProductId() + "");
                opv.setRestriction(pce.getRestriction() + "");
                opv.setSalesPrice(df.format(pcommone.getSalesPrice()));
                opv.setShortName(pcommone.getShortName());
                opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.NO.getValue());
                opv.setUpdateTime(sce.getUpdateTime());
                stockLackOpvs.add(opv);
            }
        }
        // 用户未登录
        else
        {
            int tempAccountId = id;
            // 临时用户不存在
            if (!tadi.idIsExist(tempAccountId))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.CART_LIST_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue());
                /*
                 * ConnectionManager.rollbackTransaction(); return result.toString();
                 */
                
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", "临时账号不存在!");
                throw se;
            }
            
            String tempValidTimeStr = this.tempShoppingDao.findValidTimeByAid(tempAccountId);
            if (tempValidTimeStr != null && !tempValidTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date tempValidTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(tempValidTime)) // 购物车锁定没过期
                {
                    endSecond = (tempValidTime.getTime() - new Date().getTime()) / 1000 + "";
                    cartIsExpire = false;
                }
            }
            
            List<TempShoppingCartEntity> normalTsces = this.tempShoppingDao.findAllNormalCartByAId(tempAccountId);
            List<TempShoppingCartEntity> lackTsces = tempShoppingDao.findAllLackCartByAId(tempAccountId);
            if (cartIsExpire) // 已过期
            {
                for (TempShoppingCartEntity tsce : normalTsces) // 购物车正常商品展示
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(tsce.getProductId());
                    ProductCommonEntity pcommone = pcommondi.findProductCommonInfoById(tsce.getProductId());
                    OrderProductView opv = new OrderProductView();
                    opv.setCount(tsce.getProductCount() + "");
                    opv.setImage(pcommone.getSmallImage());
                    opv.setProductId(tsce.getProductId() + "");
                    opv.setRestriction(pce.getRestriction() + "");
                    opv.setSalesPrice(df.format(pcommone.getSalesPrice()));
                    opv.setShortName(pcommone.getShortName());
                    if (pce.getStock() - pce.getLock() >= tsce.getProductCount()) // 正常展示
                    {
                        opv.setStockCount((pce.getStock() - pce.getLock()) + "");
                        opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                        opv.setUpdateTime(tsce.getUpdateTime());
                        normalOpvs.add(opv);
                    }
                    else
                    // 已抢完
                    {
                        tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                        // tempShoppingDao.updateShoppingCart(tsce);
                        List<TempShoppingCartEntity> tsces = new ArrayList<TempShoppingCartEntity>();
                        tsces.add(tsce);
                        shoppingCartServiceNew.updateShoppingCart(null, tsces);
                        opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.NO.getValue());
                        opv.setUpdateTime(tsce.getUpdateTime());
                        stockLackOpvs.add(opv);
                    }
                }
                
            }
            else
            // 未过期
            {
                for (TempShoppingCartEntity tsce : normalTsces) // 购物车正常商品展示
                {
                    ProductCountEntity pce = pcdi.findCountInfoById(tsce.getProductId());
                    ProductCommonEntity pcommone = pcommondi.findProductCommonInfoById(tsce.getProductId());
                    OrderProductView opv = new OrderProductView();
                    opv.setCount(tsce.getProductCount() + "");
                    opv.setImage(pcommone.getSmallImage());
                    opv.setProductId(tsce.getProductId() + "");
                    opv.setRestriction(pce.getRestriction() + "");
                    opv.setSalesPrice(df.format(pcommone.getSalesPrice()));
                    opv.setShortName(pcommone.getShortName());
                    opv.setStockCount((pce.getStock() - pce.getLock() + tsce.getProductCount()) + "");
                    opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.SALE.getValue());
                    opv.setUpdateTime(tsce.getUpdateTime());
                    normalOpvs.add(opv);
                }
            }
            
            for (TempShoppingCartEntity tsce : lackTsces) // 购物车没货商品展示
            {
                ProductCountEntity pce = pcdi.findCountInfoById(tsce.getProductId());
                ProductCommonEntity pcommone = pcommondi.findProductCommonInfoById(tsce.getProductId());
                OrderProductView opv = new OrderProductView();
                opv.setCount(tsce.getProductCount() + "");
                opv.setImage(pcommone.getSmallImage());
                opv.setProductId(tsce.getProductId() + "");
                opv.setRestriction(pce.getRestriction() + "");
                opv.setSalesPrice(df.format(pcommone.getSalesPrice()));
                opv.setShortName(pcommone.getShortName());
                opv.setStatus(CommonEnum.ORDER_PRODUCT_STOCK_STATUS.NO.getValue());
                opv.setUpdateTime(tsce.getUpdateTime());
                stockLackOpvs.add(opv);
            }
        }
        
        normalOpvs.addAll(stockLackOpvs);
        Collections.sort(normalOpvs);
        result.add("productList", parser.parse(JSONUtils.toJson(normalOpvs, false)));
        result.addProperty("endSecond", endSecond);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    public String submit(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int id = param.get("id").getAsInt();
        String type = param.get("type").getAsString();
        boolean cartIsExpire = true;
        List<ProductCountView> lockPcvs = new ArrayList<ProductCountView>();
        List<ProductCountView> unlockPcvs = new ArrayList<ProductCountView>();
        List<ProductCountView> lackPcvs = new ArrayList<ProductCountView>();
        
        // 用户已登录
        if (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue()))
        {
            int accountId = id;
            // 用户不存在
            if (adi.findAccountById(accountId) == null)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.CART_SUBMIT_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
                // ConnectionManager.rollbackTransaction();
                // return result.toString();
                
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", "账号不存在!");
                throw se;
                
            }
            
            String validTimeStr = this.shoppingCartDao.findValidTimeByAid(accountId);
            if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 购物车锁定没过期
                {
                    cartIsExpire = false;
                }
            }
            
            List<ShoppingCartEntity> normalSces = shoppingCartDao.findAllNormalCartByAId(accountId);
            List<ShoppingCartEntity> lackSces = shoppingCartDao.findAllLackCartByAId(accountId);
            List<Integer> productIds = new ArrayList<Integer>();
            for (ShoppingCartEntity shoppingCartEntity : normalSces)
            {
                productIds.add(shoppingCartEntity.getProductId());
            }
            if (cartIsExpire) // 已过期
            {
                try
                {
                    // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
                    Collections.sort(normalSces);
                    boolean successFlag = true;
                    boolean lockFlag = false;
                    List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds);
                    int index = 0;
                    for (ProductCountEntity pce : pces)
                    {
                        ShoppingCartEntity sce = normalSces.get(index);
                        ProductCountView pcv = new ProductCountView();
                        pcv.setProductId(sce.getProductId() + "");
                        pcv.setCount(sce.getProductCount() + "");
                        if (!isValidProduct(sce.getProductId()) || pce.getStock() - pce.getLock() <= 0)
                        {
                            sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                            if (shoppingCartDao.updateShoppingCart(sce) == 0)
                            {
                                log.warn("设为商品无货失败,productId:" + sce.getProductId() + ",accountId:" + id);
                                successFlag = false;
                            }
                            pcv.setStockCount("0");
                            unlockPcvs.add(pcv);
                        }
                        else
                        // 商品还可以被锁定
                        {
                            int lockCount = sce.getProductCount() > pce.getRestriction() ? pce.getRestriction() : sce.getProductCount();
                            int offset = pce.getStock() - pce.getLock() > lockCount ? lockCount : pce.getStock() - pce.getLock();
                            if (shoppingCartDao.addLockCount(sce.getProductId(), id, offset) == 0)
                            {
                                log.warn("addLockCount失败,productId:" + sce.getProductId() + ",accountId:" + id);
                                successFlag = false;
                            }
                            
                            pce.setLock(pce.getLock() + offset);
                            if (pcdi.updateCountInfo(pce) == 0)
                            {
                                log.warn("updateCountInfo无匹配,productId:" + sce.getProductId() + ",accountId:" + id);
                                successFlag = false;
                            }
                            
                            lockFlag = true;
                            if (sce.getProductCount() > offset) // 修改购物车数量为当前锁定数量
                            {
                                sce.setProductCount((short)offset);
                                if (shoppingCartDao.updateShoppingCart(sce) == 0)
                                {
                                    log.warn("修改商品数量失败,productId:" + sce.getProductId() + ",accountId:" + id);
                                    successFlag = false;
                                }
                                pcv.setStockCount(offset + "");
                                lackPcvs.add(pcv);
                            }
                            else
                            {
                                pcv.setStockCount("");
                                lockPcvs.add(pcv);
                            }
                        }
                        index++;
                    }
                    
                    if (lockFlag && shoppingCartDao.updateLockTime(id, System.currentTimeMillis() + againCartValidMillis) == 0)
                    {
                        log.warn("updateLockTime无匹配,accountId:" + id);
                        successFlag = false;
                    }
                    
                    if (successFlag)
                    {
//                         ConnectionManager.commitTransaction();
                    }
                    else
                    {
//                         ConnectionManager.rollbackTransaction();
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        // return result.toString();
                        
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", "未知错误!");
                        throw se;
                    }
                    
                }
                catch (Exception e)
                {
                    /*
                     * ConnectionManager.rollbackTransaction(); log.error("submit出错！"); throw e;
                     */
                    log.error("submit", e);
                    ServiceException se = new ServiceException();
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", "submit出错!");
                    throw se;
                }
            }
            else
            // 未过期
            {
                for (ShoppingCartEntity sce : normalSces)
                {
                    ProductCountView pcv = new ProductCountView();
                    pcv.setProductId(sce.getProductId() + "");
                    pcv.setCount(sce.getProductCount() + "");
                    pcv.setStockCount("");
                    lockPcvs.add(pcv);
                }
                
            }
            for (ShoppingCartEntity sce : lackSces)
            {
                ProductCountView pcv = new ProductCountView();
                pcv.setProductId(sce.getProductId() + "");
                pcv.setCount(sce.getProductCount() + "");
                pcv.setStockCount("0");
                unlockPcvs.add(pcv);
                
                sce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (shoppingCartDao.updateShoppingCart(sce) == 0)
                {
                    log.warn("设为商品无货失败,productId:" + sce.getProductId() + ",accountId:" + id);
                }
            }
        }
        // 用户未登录
        else
        {
            int tempAccountId = id;
            // 临时用户不存在
            if (!tadi.idIsExist(tempAccountId))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.CART_SUBMIT_ERRORCODE.CARTTOEKN_NOT_EXIST.getValue());
                // ConnectionManager.rollbackTransaction();
                // return result.toString();
                
                ServiceException se = new ServiceException();
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", "临时账号不存在!");
                throw se;
            }
            
            String tempValidTimeStr = this.tempShoppingDao.findValidTimeByAid(tempAccountId);
            if (tempValidTimeStr != null && !tempValidTimeStr.equals("")) // 存在购物车锁定时间 信息
            {
                Date tempValidTime = CommonUtil.string2Date(tempValidTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(tempValidTime)) // 购物车锁定没过期
                {
                    cartIsExpire = false;
                }
            }
            
            List<TempShoppingCartEntity> normalTsces = tempShoppingDao.findAllNormalCartByAId(tempAccountId);
            List<TempShoppingCartEntity> lackTsces = tempShoppingDao.findAllLackCartByAId(tempAccountId);
            List<Integer> productIds = new ArrayList<Integer>();
            for (TempShoppingCartEntity shoppingCartEntity : normalTsces)
            {
                productIds.add(shoppingCartEntity.getProductId());
            }
            if (cartIsExpire) // 已过期
            {
                try
                {
                    // ConnectionManager.beginTransaction(CommonProperties.defaultDbConfig);
                    Collections.sort(normalTsces);
                    boolean successFlag = true;
                    boolean lockFlag = false;
                    List<ProductCountEntity> pces = pcdi.findCountInfosByIdsForUpdate(productIds);
                    int index = 0;
                    for (ProductCountEntity pce : pces)
                    {
                        TempShoppingCartEntity tsce = normalTsces.get(index);
                        ProductCountView pcv = new ProductCountView();
                        pcv.setProductId(tsce.getProductId() + "");
                        pcv.setCount(tsce.getProductCount() + "");
                        if (!isValidProduct(tsce.getProductId()) || pce.getStock() - pce.getLock() <= 0)
                        {
                            tsce.setStatus((byte)CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                            if (tempShoppingDao.updateShoppingCart(tsce) == 0)
                            {
                                log.warn("设为商品无货失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                                successFlag = false;
                            }
                            pcv.setStockCount("0");
                            unlockPcvs.add(pcv);
                        }
                        else
                        // 商品还可以被锁定
                        {
                            int lockCount = tsce.getProductCount() > pce.getRestriction() ? pce.getRestriction() : tsce.getProductCount();
                            int offset = pce.getStock() - pce.getLock() > lockCount ? lockCount : pce.getStock() - pce.getLock();
                            if (this.tempShoppingDao.addLockCount(tsce.getProductId(), id, offset) == 0)
                            {
                                log.warn("addLockCount失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                                successFlag = false;
                            }
                            
                            pce.setLock(pce.getLock() + offset);
                            if (pcdi.updateCountInfo(pce) == 0)
                            {
                                log.warn("updateCountInfo无匹配,productId:" + tsce.getProductId() + ",accountId:" + id);
                                successFlag = false;
                            }
                            
                            lockFlag = true;
                            if (tsce.getProductCount() > offset) // 修改购物车数量为当前锁定数量
                            {
                                tsce.setProductCount((short)offset);
                                if (tempShoppingDao.updateShoppingCart(tsce) == 0)
                                {
                                    log.warn("修改商品数量失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                                    successFlag = false;
                                }
                                pcv.setStockCount(offset + "");
                                lackPcvs.add(pcv);
                            }
                            else
                            {
                                pcv.setStockCount("");
                                lockPcvs.add(pcv);
                            }
                        }
                        index++;
                    }
                    
                    if (lockFlag && tempShoppingDao.updateLockTime(id, System.currentTimeMillis() + againCartValidMillis) == 0)
                    {
                        log.warn("updateLockTime无匹配,accountId:" + id);
                        successFlag = false;
                    }
                    
                    if (successFlag)
                    {
                        // ConnectionManager.commitTransaction();
                    }
                    else
                    {
                        // ConnectionManager.rollbackTransaction();
                        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                        // return result.toString();
                        
                        ServiceException se = new ServiceException();
                        se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                        se.putMap("errorCode", "未知错误!");
                        throw se;
                    }
                    
                }
                catch (Exception e)
                {
                    /*
                     * ConnectionManager.rollbackTransaction(); log.error("submit出错！"); throw e;
                     */
                    log.error("submit", e);
                    ServiceException se = new ServiceException();
                    se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    se.putMap("errorCode", "submit出错!");
                    throw se;
                }
            }
            else
            // 未过期
            {
                for (TempShoppingCartEntity tsce : normalTsces)
                {
                    ProductCountView pcv = new ProductCountView();
                    pcv.setProductId(tsce.getProductId() + "");
                    pcv.setCount(tsce.getProductCount() + "");
                    pcv.setStockCount("");
                    lockPcvs.add(pcv);
                }
                
            }
            for (TempShoppingCartEntity tsce : lackTsces)
            {
                ProductCountView pcv = new ProductCountView();
                pcv.setProductId(tsce.getProductId() + "");
                pcv.setCount(tsce.getProductCount() + "");
                pcv.setStockCount("0");
                unlockPcvs.add(pcv);
                
                tsce.setStatus((byte)CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK_REMOVE.getValue());
                if (tempShoppingDao.updateShoppingCart(tsce) == 0)
                {
                    log.warn("设为商品无货失败,productId:" + tsce.getProductId() + ",accountId:" + id);
                }
            }
        }
        
        String endSecond = "-1";
        String validTimeStr = "";
        if (type.equals(CommonEnum.ACCOUNT_LOGIN_STATUS.YES.getValue()))
        {
            validTimeStr = this.shoppingCartDao.findValidTimeByAid(id);
        }
        else
        {
            validTimeStr = tempShoppingDao.findValidTimeByAid(id);
        }
        if (validTimeStr != null && !validTimeStr.equals("")) // 存在购物车锁定时间 信息
        {
            Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
            if (new Date().before(validTime)) // 购物车锁定没过期
            {
                endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
            }
        }
        
        result.add("lock", parser.parse(JSONUtils.toJson(lockPcvs, false)));
        result.add("unlock", parser.parse(JSONUtils.toJson(unlockPcvs, false)));
        result.add("lack", parser.parse(JSONUtils.toJson(lackPcvs, false)));
        result.addProperty("endSecond", endSecond);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String findValidTimeByAid(int accountId)
        throws Exception
    {
        return this.shoppingCartDao.findValidTimeByAid(accountId);
    }
    
    @Override
    public ShoppingCartView findNormalCartByPIdAndAId(int productId, int accountId)
        throws Exception
    {
        ShoppingCartEntity sce = this.shoppingCartDao.findNormalCartByPIdAndAId(productId, accountId);
        ShoppingCartView scv = new ShoppingCartView();
        if (sce != null)
        {
            scv.setId(sce.getId() + "");
            scv.setAccountId(sce.getAccountId() + "");
            scv.setProductCount(sce.getProductCount());
            scv.setProductId(sce.getProductId() + "");
        }
        return scv;
    }
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws Exception
    {
        return this.shoppingCartDao.findProductCountByAIdAndPId(accountId, productId);
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
}
