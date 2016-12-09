package com.ygg.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.CheckService;
import com.ygg.webapp.util.CommonEnum;

@Service("checkService")
public class CheckServiceImpl implements CheckService
{
    
    private static Logger logger = Logger.getLogger(CheckServiceImpl.class);
    
    @Resource(name = "shoppingCartDao")
    private ShoppingCartDao shoppingCartDao;
    
    @Resource(name = "tempShoppingCartDao")
    private TempShoppingCartDao tempShoppingCartDao;
    
    @Resource(name = "productCountDao")
    private ProductCountDao productCountDao;
    
    @Override
    public void checkShoppingCartReferencesInfos(List<Integer> accountIds)
        throws ServiceException
    {
        try
        {
            // List<Integer> accountIds = this.shoppingCartDao.findAccountIdsAndNoValid();
            if (accountIds == null || accountIds.size() == 0)
            {
                logger.warn("cart_lock_time is null ");
                return;
            }
            
            for (Integer accountId : accountIds)
            {
                // / 2 到cart_lock_count 表中查询是否有记录
                List<Integer> productIds = this.shoppingCartDao.findLockCountProductIdsByAccountId(accountId.intValue());
                if (productIds == null || productIds.size() == 0)
                {
                    logger.warn("accountId:" + accountId + ",还没有购物!");
                    continue;
                }
                checkShoppingCartReferencesInfos(accountId, productIds);
                
            }
        }
        catch (Exception de)
        {
            String errorCode = de.getMessage() + "-----ThreadServiceImpl-------操作数据库异常!";
            logger.error(errorCode + " ---- " + de.getMessage());
            throw new ServiceException(errorCode); // / // 回滚
        }
        
    }
    
    /**
     * 带事务处理，product_count表中还加了行锁
     */
    @Override
    public void checkShoppingCartReferencesInfos(Integer accountId, List<Integer> productIds)
        throws ServiceException
    {
        
        List<ProductCountEntity> productCountList = this.productCountDao.findProductCountByProductIds(productIds); // 要用for
                                                                                                                   // update
                                                                                                                   // 行锁　
        if (productCountList == null || productCountList.size() == 0) // 脏数据 删除cart_lock_count的数据
        {
            // 更新cart_lock_time中的isvalid为0
            if (this.shoppingCartDao.updateIsValidByAccountId(accountId, 0) == 0) // 回滚
            {
                String errorCode = "accountId:" + accountId + ",在cart_lock_time 更新 isvalid异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            return;
        }
        for (ProductCountEntity pce : productCountList)
        {
            int productId = pce.getProductId();
            int product_count = this.shoppingCartDao.findLockCountByPIdAndAId(productId, accountId);
            if (this.shoppingCartDao.deleteLockCountByPIdAndAId(productId, accountId) == 0) // 回滚
            {
                String errorCode = "productId:" + productId + ",accountId:" + accountId + ",在cart_lock_count删除异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            // 成功update product_count 库存　
            int newlock = (pce.getLock() - product_count);
            if (newlock <= 0)
                newlock = 0;
            pce.setLock(newlock);
            if (this.productCountDao.updateProductCountByProductId(pce) == 0) // 回滚
            {
                String errorCode = "productId:" + productId + ",accountId:" + accountId + ",在product_count 更新锁定数量异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
        }
        // 更新cart_lock_time中的isvalid为0
        if (this.shoppingCartDao.updateIsValidByAccountId(accountId, 0) == 0) // 回滚
        {
            String errorCode = "accountId:" + accountId + ",在cart_lock_time 更新 isvalid异常!";
            logger.error(errorCode);
            throw new ServiceException(errorCode);
        }
        
    }
    
    @Override
    public void checkOrderCartReferencesInfos(Integer orderId, List<Integer> productIds)
        throws ServiceException
    {
        List<ProductCountEntity> productCountList = this.productCountDao.findProductCountByProductIds(productIds); // product_count
                                                                                                                   // 要用for
                                                                                                                   // update
                                                                                                                   // 行锁　
        if (productCountList == null || productCountList.size() == 0) // 脏数据 删除cart_lock_count的数据
        {
            if (this.productCountDao.updateOrderStatusByOrderid(orderId, CommonEnum.ORDER_STATUS.TIME_OUT_CANCEL.getValue()) == 0) // 回滚
            {
                String errorCode = "orderId:" + orderId + ",在order表 　更新状态异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            if (this.productCountDao.updateIsValidByOrderId(orderId, 0) == 0) // 回滚 //对于错误不同步的数据调用一次
            {
                String errorCode = "orderId:" + orderId + ",在order_lock_time 更新isvalid 异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            return;
        }
        for (ProductCountEntity pce : productCountList)
        {
            int productId = pce.getProductId();
            int product_count = this.productCountDao.findLockCountByPIdAndAId(productId, orderId);
            if (this.productCountDao.deleteLockCountByPIdAndAId(productId, orderId) == 0) // 回滚
            {
                String errorCode = "productId:" + productId + ",orderId:" + orderId + ",在order_product_lock_count删除异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            // 成功update product_count 库存　
            int newlock = (pce.getLock() - product_count);
            if (newlock <= 0)
                newlock = 0;
            pce.setLock(newlock);
            if (this.productCountDao.updateProductCountByProductId(pce) == 0) // 回滚
            {
                String errorCode = "productId:" + productId + ",orderId:" + orderId + ",在product_count表　更新锁定数量异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
        }
        
        // 更新定单的状态为　５　已取消
        if (this.productCountDao.updateOrderStatusByOrderid(orderId, CommonEnum.ORDER_STATUS.TIME_OUT_CANCEL.getValue()) == 0) // 回滚
        {
            String errorCode = "orderId:" + orderId + ",在order表 　更新状态异常!";
            logger.error(errorCode);
            throw new ServiceException(errorCode);
        }
        
        // 更新order_lock_time中的isvalid为0
        if (this.productCountDao.updateIsValidByOrderId(orderId, 0) == 0) // 回滚
        {
            String errorCode = "orderId:" + orderId + ",在order_lock_time 更新isvalid 异常!";
            logger.error(errorCode);
            throw new ServiceException(errorCode);
        }
        
    }
    
    @Override
    public void checkTempShoppingCartReferencesInfos(Integer tempAccountId, List<Integer> productIds)
        throws ServiceException
    {
        
        List<ProductCountEntity> productCountList = this.productCountDao.findProductCountByProductIds(productIds); // 要用
                                                                                                                   // product_count
                                                                                                                   // for
                                                                                                                   // update
                                                                                                                   // 行锁　
        if (productCountList == null || productCountList.size() == 0) // 脏数据 删除cart_lock_count的数据
        {
            // 更新temp_cart_lock_time中的isvalid为0
            if (this.tempShoppingCartDao.updateIsValidByTempAccountId(tempAccountId, 0) == 0) // 回滚
            {
                String errorCode = "tempAccountId:" + tempAccountId + ",在temp_cart_lock_time 更新isvalid 异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            return;
        }
        for (ProductCountEntity pce : productCountList)
        {
            int productId = pce.getProductId();
            int product_count = this.tempShoppingCartDao.findLockCountByPIdAndAId(productId, tempAccountId);
            if (this.tempShoppingCartDao.deleteLockCountByPIdAndAId(productId, tempAccountId) == 0) // 回滚
            {
                String errorCode = "productId:" + productId + ",tempAccountId:" + tempAccountId + ",在temp_cart_lock_count删除异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
            // 成功update product_count 库存　
            int newlock = (pce.getLock() - product_count);
            if (newlock <= 0)
                newlock = 0;
            pce.setLock(newlock);
            if (this.productCountDao.updateProductCountByProductId(pce) == 0) // 回滚
            {
                String errorCode = "productId:" + productId + ",tempAccountId:" + tempAccountId + ",在product_count 更新锁定数量异常!";
                logger.error(errorCode);
                throw new ServiceException(errorCode);
            }
        }
        
        // 更新temp_cart_lock_time中的isvalid为0
        if (this.tempShoppingCartDao.updateIsValidByTempAccountId(tempAccountId, 0) == 0) // 回滚
        {
            String errorCode = "tempAccountId:" + tempAccountId + ",在temp_cart_lock_time 更新isvalid 异常!";
            logger.error(errorCode);
            throw new ServiceException(errorCode);
        }
    }
    
}
