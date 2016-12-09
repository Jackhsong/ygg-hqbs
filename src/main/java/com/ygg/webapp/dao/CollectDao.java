package com.ygg.webapp.dao;

import com.ygg.webapp.exception.DaoException;

public interface CollectDao
{
    /**
     * 新增用户收藏特卖商品
     * 
     * @return
     */
    int addAccountCollectProduct(int accountId, int productId)
        throws DaoException;
    
    /**
     * 新增用户收藏通用专场
     * 
     * @return
     */
    int addAccountCollectActivitiesCommon(int accountId, int activitiesCommonId)
        throws DaoException;
    
    /**
     * 新增用户收藏特卖
     * 
     * @return
     */
    int addAccountCollectSale(int accountId, int saleId)
        throws DaoException;
}
