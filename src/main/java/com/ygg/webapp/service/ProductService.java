package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.view.ProductView;

public interface ProductService
{
    
    /**
     * 增加一个产品,包括产品的详细信息
     * 
     * @param productView
     * @return
     * @throws Exception
     */
    public String addProduct(ProductView productView)
        throws Exception;
    
    /**
     * 删除一个产品及详细信息
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    public String deleteProduct(String requestParams)
        throws Exception;
    
    /**
     * 查询所有单品的ID 要判断是否与特别相绑定
     * 
     * @return
     * @throws Exception
     */
    public List<Integer> findAllProductIds()
        throws Exception;
    
    public int findProductSellCountById(int productId)
        throws Exception;
    
    public List<Map<String, Object>> findProductSellCountByIds(List<Integer> productId)
        throws Exception;
    
    /**
     * 得到商品的状态
     * 
     * @param productId
     * @return
     * @throws Exception
     */
    public String getProductStatusById(String reqParams)
        throws Exception;
    
    /**
     * 根据ID查找商品信息
     * 
     * @param id
     * @return
     * @throws Exception
     */
    ProductEntity findProductById(int id)
        throws Exception;
    
    public ProductCountEntity findCountInfoById(int productId)
            throws DaoException;
    public ProductEntity findProductPartById(int id)
            throws DaoException;
    public ProductCountEntity findProductCountSumByProductIds(List<Integer> productIds)
            throws DaoException;
    
    
            
}
