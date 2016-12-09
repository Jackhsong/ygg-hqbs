package com.ygg.webapp.service;

import java.util.List;

import com.ygg.webapp.view.SaleWindowView;

/**
 * 特卖商品服务 和特卖相关的服务操作都在此类中
 * 
 * @author Administrator
 *
 */
public interface SaleWindowService
{
    
    /**
     * 增加一个特卖标签
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String addSaleTag(String requestParams)
        throws Exception;
    
    /**
     * 增加一个特卖商品
     * 
     * @param sw
     * @return
     * @throws Exception
     */
    public String addSaleWindow(String sw)
        throws Exception;
    
    /**
     * 查询所有特卖品的ID 判断其下的所有商品是否与特卖相绑定
     * 
     * @return
     * @throws Exception
     */
    public List<Integer> findAllSaleWindowIds()
        throws Exception;
    
}
