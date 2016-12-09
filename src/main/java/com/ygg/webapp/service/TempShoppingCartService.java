package com.ygg.webapp.service;

import java.util.List;

import com.ygg.webapp.view.TempShoppingCartView;

public interface TempShoppingCartService
{
    
    /**
     * 列出购物车待传款的产品详情
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public List<TempShoppingCartView> listTempShoppingCartInfo(String requestParams)
        throws Exception;
    
    /**
     * 加一个商品到购物车中， 注意当前用户是否已登陆的情况
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String addTempProductToShoppingCart(String requestParams)
        throws Exception;
    
    /**
     * 从购物车中移除一个商品
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String removeTempProductToShoppingCart(String requestParams)
        throws Exception;
    
    /**
     * 用户没有　login后，根据账号ID和商品ID查出　正常　临时购物车 对象
     * 
     * @param accountId
     * @param productId
     * @return
     * @throws Exception
     */
    public String findTempShoppingCartByAccountIdAndProductId(int accountId, int productId)
        throws Exception;
    
    /**
     * 用户没有　login后，根据账号ID和商品ID查出　正常　临时购物车 对象
     * 
     * @param accountId
     * @param productId
     * @return
     * @throws Exception
     */
    public String findTempShoppingCartByAccountIdAndProductId(String tmpUUID, int productId)
        throws Exception;
    
    /**
     * 根据临时购物车查询购物车中的数量总和
     * 
     * @param tempAccountId
     * @return
     * @throws Exception
     */
    String findTmpShoppingCartCountByAccountId(int tempAccountId)
        throws Exception;
    
    public String findValidTimeByAid(int tempAccountId)
        throws Exception;
    
    TempShoppingCartView findNormalCartByPIdAndAId(int productId, int accountId)
        throws Exception;
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws Exception;
}
