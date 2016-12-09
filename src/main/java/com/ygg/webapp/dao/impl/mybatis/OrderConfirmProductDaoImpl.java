package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderConfirmProductDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderConfirmProductEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderConfirmProductDao")
public class OrderConfirmProductDaoImpl extends BaseDaoImpl implements OrderConfirmProductDao
{
    
    @Override
    public int addOrderConfirmProduct(int orderConfirmId, int productId, short productCount)
        throws DaoException
    {
        
        // String sql = "INSERT INTO order_confirm_product(order_confirm_id,product_id,product_count) VALUES(?,?,?)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderConfirmId", orderConfirmId);
        map.put("productId", productId);
        map.put("productCount", productCount);
        
        return this.getSqlSession().insert("OrderConfimProductMapper.addOrderConfirmProduct", map);
    }
    
    @Override
    public List<OrderConfirmProductEntity> findConfirmProductByConfirmId(int confirmId)
        throws DaoException
    {
        String sql = "SELECT product_id,product_count FROM order_confirm_product WHERE order_confirm_id=?";
        List<OrderConfirmProductEntity> ocpes = this.getSqlSession().selectList("OrderConfimProductMapper.findConfirmProductByConfirmId", confirmId);
        if (ocpes == null || ocpes.isEmpty())
            ocpes = new ArrayList<OrderConfirmProductEntity>();
        return ocpes;
    }

    @Override
    public int findProductSumByConfirmIdsAndProductId(List<Integer> confirmIds, int productId)
        throws DaoException
    {
        if (confirmIds.size() == 0)
        {
            return 0;
        }
        String sql = "SELECT SUM(product_count) as productCount FROM order_confirm_product WHERE order_confirm_id IN () AND product_id=?";
        
        Map<String, Object> para = new HashMap<>();
        para.put("confirmIdList", confirmIds);
        para.put("productId", productId);
        Integer productCount = this.getSqlSession().selectOne("OrderConfimProductMapper.findProductSumByConfirmIdsAndProductId", para);
        return productCount == null ? 0 : productCount ;
    }
}
