package com.ygg.webapp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.service.ProductService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.view.ProductView;

@Service("productService")
public class ProductServiceImpl implements ProductService
{
    
    @Resource(name = "productDao")
    private ProductDao productDao;
    
    @Resource(name = "productCommonDao")
    private ProductCommonDao productCommonDao;
    
    @Resource(name = "productCountDao")
    private ProductCountDao productCountDao;
    
    @Override
    public String addProduct(ProductView productView)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String deleteProduct(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Integer> findAllProductIds()
        throws Exception
    {
        
        return productDao.findAllProductIds();
    }
    
    @Override
    public int findProductSellCountById(int productId)
        throws Exception
    {
        return this.productCommonDao.findProductSellCountById(productId);
    }
    
    @Override
    public String getProductStatusById(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int productId = param.get("productId").getAsInt();
        
        ProductEntity pe = productDao.findProductInfoById(productId);
        if (pe.getIsOffShelves() == Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())) // 查询到商品绑定的特卖信息
        {
            Date startTime = CommonUtil.string2Date(pe.getStartTime() + "", "yyyy-MM-dd HH:mm:ss");
            Date endTime = CommonUtil.string2Date(pe.getEndTime() + "", "yyyy-MM-dd HH:mm:ss");
            Date currTime = new Date();
            
            if (currTime.before(startTime)) // 未开始
            {
                result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue());
                result.addProperty("second", ((startTime.getTime() - currTime.getTime()) / 1000) + "");
            }
            else if (currTime.after(endTime)) // 已结束
            {
                result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                result.addProperty("second", "");
            }
            else
            {
                ProductCountEntity pce = productCountDao.findCountInfoById(productId);
                int stock = pce.getStock();
                if (stock == 0)
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
                    result.addProperty("second", "");
                }
                else if (pce.getLock() >= stock)
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.CHANCE.getValue());
                    result.addProperty("second", ((endTime.getTime() - currTime.getTime()) / 1000) + "");
                }
                else
                {
                    result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue());
                    result.addProperty("second", ((endTime.getTime() - currTime.getTime()) / 1000) + "");
                }
            }
        }
        else
        // 已下架
        {
            result.addProperty("productStatus", CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue());
            result.addProperty("second", "");
        }
        return result.toString();
    }
    
    @Override
    public List<Map<String, Object>> findProductSellCountByIds(List<Integer> productId)
        throws Exception
    {
        return this.productCommonDao.findProductSellCountByIds(productId);
    }
    @Override
    public ProductEntity findProductById(int id)
        throws Exception
    {
        return productDao.findProductInfoById(id);
    }
    public ProductCountEntity findCountInfoById(int productId)
            throws DaoException
        {
            return productCountDao.findCountInfoById(productId);
    }
    
    public ProductEntity findProductPartById(int id)
            throws DaoException
        {
            return productDao.findProductPartById(id);
    }
    public ProductCountEntity findProductCountSumByProductIds(List<Integer> productIds)
            throws DaoException{
        if (productIds == null || productIds.isEmpty())
            return new ProductCountEntity();
        
        return productCountDao.findProductCountSumByProductIds(productIds);
    }
    
}
