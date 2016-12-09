package com.ygg.webapp.service;

import java.util.List;

import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.exception.ServiceException;

/**
 * 主要是对 shoppingcart中 edit 的方法来做的，起一个新事务来处理
 * 
 * @author Administrator
 *
 */
public interface ShoppingCartServiceNew
{
    
    /**
     * 编辑购物车 发起一个REQUIRE_NEW
     * 
     * @param request
     * @return
     */
    String editCrudsc(String requestParams)
        throws Exception;
    
    /**
     * 更新购物车和临时购物车的状态， 发起一个REQUIRE_NEW
     * 
     * @param sces
     * @param tsces
     * @return
     * @throws ServiceException
     */
    boolean updateShoppingCart(List<ShoppingCartEntity> sces, List<TempShoppingCartEntity> tsces)
        throws ServiceException;
    
}
