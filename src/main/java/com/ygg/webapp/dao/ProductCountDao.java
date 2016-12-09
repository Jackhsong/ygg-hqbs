package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.CartLockCountEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.exception.DaoException;

public interface ProductCountDao
{
    /**
     * 根据商品id查询对应的商品数量信息
     *
     * @return
     */
    ProductCountEntity findProductCountInfoById(int id)
        throws DaoException;
    
    /**
     * 根据商品id查询对应的商品数量信息
     *
     * @return
     */
    ProductCountEntity findCountInfoById(int productId)
        throws DaoException;
    
    /**
     * 根据通用专场id查询对应的多个商品数量信息汇总
     *
     * @return
     */
    ProductCountEntity findProductCountSumByCommonId(int id)
        throws DaoException;
    
    /**
     * 根据自定义专场id查询对应的多个商品数量信息汇总
     *
     * @return
     */
    ProductCountEntity findProductCountSumByCustomId(int id)
        throws DaoException;
    
    /**
     * 根据通用专场id列表查询对应的多个商品数量信息汇总
     *
     * @return
     */
    List<ProductCountEntity>[] findProductCountSumByCommonIds(List<Integer> activitiesCommonIds)
        throws DaoException;
    
    /**
     * 根据产品ID找到所有的productCount 此时要用到行锁定此条记录　
     *
     * @param productId
     * @return
     * @throws DaoException
     */
    ProductCountEntity findProductCountByProductId(int productId)
        throws DaoException;
    
    List<ProductCountEntity> findProductCountByProductIds(List<Integer> productIds)
        throws DaoException;
    
    /**
     * 根据商品id列表查询对应的多个商品数量信息汇总
     * 
     * @return
     */
    ProductCountEntity findProductCountSumByProductIds(List<Integer> productIds)
        throws DaoException;
    
    /**
     * 根据productId更新库存
     * 
     * @param productId
     * @return
     * @throws DaoException
     */
    int updateProductCountByProductId(ProductCountEntity pce)
        throws DaoException;
    
    /**
     * 查找所有 isValid为无效的账号ID
     * 
     * @return
     * @throws DaoException
     */
    List<Integer> findAccountIdsAndNoValid()
        throws DaoException;
    
    /**
     * 通过accountId找到此账号所有的数量　
     *
     * @param accountId
     * @return
     * @throws DaoException
     */
    List<CartLockCountEntity> findLockCountByAccountId(int accountId)
        throws DaoException;
    
    List<Integer> findLockCountProductIdsByAccountId(int accountId)
        throws DaoException;
    
    /**
     * 根据账号id和商品id查询 商品定单 的锁定数量
     *
     * @return
     */
    int findLockCountByPIdAndAId(int productId, int accountId)
        throws DaoException;
    
    /**
     * 根据账号id和商品id删除 商品定单 锁定信息
     *
     * @return
     */
    int deleteLockCountByPIdAndAId(int productId, int accountId)
        throws DaoException;
    
    /**
     * 根据定单ID更新定单的状态　
     *
     * @param orderId
     * @param status
     * @return
     * @throws DaoException
     */
    int updateOrderStatusByOrderid(int orderId, int status)
        throws DaoException;
    
    int updateIsValidByOrderId(int orderId, int isValid)
        throws DaoException;
    
    /**
     * 根据商品id查询对应的商品数量信息并锁定
     *
     * @return
     */
    ProductCountEntity findCountInfoByIdForUpdate(int productId)
        throws DaoException;
    
    /**
     * 根据productId查出已卖出的商品数量　
     * 
     * @param productId
     * @return
     * @throws DaoException
     */
    int findSellCountByProductId(int productId)
        throws DaoException;
    
    /**
     * 更新商品数量
     *
     * @return
     */
    int updateCountInfo(ProductCountEntity pce)
        throws DaoException;
    
    /**
     * 根据商品id列表查询对应的商品数量信息列表并锁定
     *
     * @return
     */
    List<ProductCountEntity> findCountInfosByIdsForUpdate(List<Integer> productIds)
        throws DaoException;
    
}