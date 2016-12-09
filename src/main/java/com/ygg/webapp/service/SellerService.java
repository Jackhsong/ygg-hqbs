package com.ygg.webapp.service;

import com.ygg.webapp.view.SellerView;

/**
 * 商家服务
 * 
 * @author Administrator
 *
 */
public interface SellerService
{
    
    /**
     * 增加一个商家
     * 
     * @param sv
     * @return
     * @throws Exception
     */
    public String addSeller(SellerView sv)
        throws Exception;
    
    /**
     * 修改一个商家详情
     * 
     * @param sv
     * @return
     * @throws Exception
     */
    public String modifySeller(SellerView sv)
        throws Exception;
    
    /**
     * 移除一个商家
     * 
     * @param sv
     * @return
     * @throws Exception
     */
    public String removeSeller(SellerView sv)
        throws Exception;
    
    /**
     * 列出商家
     * 
     * @param sv
     * @return
     * @throws Exception
     */
    public String listSeller(SellerView sv)
        throws Exception;
    
}
