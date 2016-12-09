package com.ygg.webapp.service;

import java.util.List;

import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.view.ShoppingCartView;

public interface ShoppingCartService
{
    
    /**
     * 列出购物车待付款的产品详情
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public List<ShoppingCartView> listShoppingCartInfo(String requestParams)
        throws Exception;
    
    /**
     * 加一个商品到购物车中， 注意当前用户是否已登陆的情况
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String addProductToShoppingCart(String requestParams)
        throws Exception;
    
    /**
     * 从购物车中移除一个商品
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String removeProductToShoppingCart(String requestParams)
        throws Exception;
    
    /**
     * 用户已login后，根据账号ID和商品ID查出购物车对象
     * 
     * @param accountId
     * @param productId
     * @return
     * @throws Exception
     */
    public String findShoppingCartByAccountIdAndProductId(int accountId, int productId)
        throws Exception;
    
    /**
     * 根据 购物车查询购物车中的数量总和
     * 
     * @param accountId
     * @return
     * @throws Exception
     */
    String findShoppingCartCountByAccountId(int accountId)
        throws Exception;
    
    /**
     * 获取购物车临时Token
     *
     * @param request
     * @return
     */
    String getCartToken(String requestParams)
        throws ServiceException;
    
    /**
     * 编辑购物车 发起一个REQUIRE_NEW
     * 
     * @param request
     * @return
     */
    @Deprecated
    String editCrudsc(String requestParams)
        throws ServiceException;
    
    /**
     * 编辑购物车 REQUIRE
     * 
     * @param requestParams
     * @return
     * @throws ServiceException
     */
    String editShoppingCart(String requestParams)
        throws ServiceException;
    
    /**
     * 合并购物车
     *
     * @param request
     * @return
     */
    String merger(String requestParams)
        throws ServiceException;
    
    /**
     * 购物车列表
     *
     * @param request
     * @return
     */
    String list(String requestParams)
        throws ServiceException;
    
    /**
     * 购物车提交
     *
     * @param request
     * @return
     */
    String submit(String requestParams)
        throws Exception;
    
    /**
     * 更新购物车和临时购物车的状态， 发起一个REQUIRE_NEW
     * 
     * @param sces
     * @param tsces
     * @return
     * @throws ServiceException
     */
    // boolean updateShoppingCart(List<ShoppingCartEntity> sces , List<TempShoppingCartEntity> tsces) throws
    // ServiceException ;
    
    public String findValidTimeByAid(int accountId)
        throws Exception;
    
    ShoppingCartView findNormalCartByPIdAndAId(int productId, int accountId)
        throws Exception;
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws Exception;
}
