package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.CartLockCountEntity;
import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.exception.DaoException;

public interface ShoppingCartDao
{
    
    /**
     * 根据账号id和商品id查询缺货购物车
     *
     * @return
     */
    ShoppingCartEntity findLackCartByPIdAndAId(int productId, int accountId)
        throws DaoException;
    
    /**
     * 根据账号id和商品id查询正常购物车
     *
     * @return
     */
    ShoppingCartEntity findNormalCartByPIdAndAId(int productId, int accountId)
        throws DaoException;
    
    /**
     * 根据账号id查询所有正常购物车
     *
     * @return
     */
    List<ShoppingCartEntity> findAllNormalCartByAId(int accountId)
        throws DaoException;
    
    /**
     * 根据账号id查询所有正常购物车除了指定商品id
     *
     * @return
     */
    List<ShoppingCartEntity> findAllNormalCartByAIdExceptPid(int accountId, int productId)
        throws DaoException;
    
    /**
     * 新增购物车
     *
     * @return
     */
    int addShoppingCart(ShoppingCartEntity sce)
        throws DaoException;
    
    /**
     * 更新购物车
     *
     * @return
     */
    int updateShoppingCart(ShoppingCartEntity sce)
        throws DaoException;
    
    /**
     * 根据账号id和商品id查询商品锁定数量
     *
     * @return
     */
    int findLockCountByPIdAndAId(int productId, int accountId)
        throws DaoException;
    
    /**
     * 根据账号id和商品id删除商品锁定信息
     *
     * @return
     */
    int deleteLockCountByPIdAndAId(int productId, int accountId)
        throws DaoException;
    
    /**
     * 新增商品锁定信息
     *
     * @return
     */
    int addLockCount(int productId, int accountId, int count)
        throws DaoException;
    
    /**
     * 修改商品锁定信息
     *
     * @return
     */
    int updateLockCount(int productId, int accountId, int count)
        throws DaoException;
    
    /**
     * 新增购物车锁定信息
     *
     * @return
     */
    int addLockTime(int accountId, long validTime)
        throws DaoException;
    
    /**
     * 更新购物车锁定信息
     *
     * @return
     */
    int updateLockTime(int accountId, long validTime)
        throws DaoException;
    
    /**
     * 根据账号id查询购物车锁定时间
     *
     * @return
     */
    String findValidTimeByAid(int accountId)
        throws DaoException;
    
    int addInvalidLockTime(int accountId)
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
    
    int updateIsValidByAccountId(int accountId, int isValid)
        throws DaoException;
    
    /**
     * 根据accountId查购物车内的总数量
     * 
     * @param accountId
     * @return
     * @throws DaoException
     */
    int findShoppingCartCountByAccountId(int accountId)
        throws DaoException;
    
    /**
     * 根据账号id查询所有没货购物车
     *
     * @return
     */
    List<ShoppingCartEntity> findAllLackCartByAId(int accountId)
        throws DaoException;
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws DaoException;
    
}