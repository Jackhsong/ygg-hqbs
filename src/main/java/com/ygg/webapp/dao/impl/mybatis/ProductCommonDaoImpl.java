package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("productCommonDao")
public class ProductCommonDaoImpl extends BaseDaoImpl implements ProductCommonDao
{
    
    @Override
    public ProductCommonEntity findProductCommonInfoById(int productId)
        throws DaoException
    {
        // String sql = "SELECT * FROM product_common WHERE product_id=? LIMIT 1";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * productId)); return queryOneInfo(sql, params);
         */
        ProductCommonEntity pe = this.getSqlSession().selectOne("ProductCommonMapper.findProductCommonInfoById", productId);
        return pe;
        
    }
    
    @Override
    public List<ProductCommonEntity> findProductCommonInfoById(List<Integer> productIds)
        throws DaoException
    {
        if (productIds.size() == 0)
        {
            return new ArrayList<ProductCommonEntity>();
        }
        /*
         * String sql = "SELECT * FROM product_common WHERE product_id IN (" +
         * CommonUtil.changeSqlValueByLen(productIds.size()) + ")"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); for (Integer productId : productIds) { params.add(new
         * PstmtParam(FILL_PSTMT_TYPE.INT, productId)); } return queryAllInfo2T(sql, params);
         */
        // String orderIds = "id," ; // "order by field(id,1,2,5,3 )"
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", productIds);
        // List<String> orderIds = new ArrayList<String>();
        // orderIds.add("id") ;
        /*
         * for(Integer pid : productIds) orderIds+=pid +","; orderIds = orderIds.substring(0, orderIds.length()-1 ) ;
         * //orderIds = "order by field(id,"+orderIds+" )" ; map.put("orderIds", orderIds) ;
         */
        
        List<ProductCommonEntity> list = this.getSqlSession().selectList("ProductCommonMapper.findProductCommonsInfoById", map);
        if (list.size() > 0)
        {
            List<ProductCommonEntity> pcs = list;
            list = new ArrayList<ProductCommonEntity>();
            for (Integer it : productIds)
            {
                for (ProductCommonEntity pc : pcs)
                {
                    if (it == pc.getProductId())
                    {
                        list.add(pc);
                        break;
                    }
                }
            }
        }
        return list;
        
    }
    
    @Override
    public int updateSellCountById(int productId, int sellCount)
        throws DaoException
    {
        String sql = "UPDATE product_common SET sell_count=? WHERE product_id=?";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sellCount", sellCount);
        map.put("productId", productId);
        return this.getSqlSession().update("ProductCommonMapper.updateSellCountById", map);
    }
    
    @Override
    public int findProductSellCountById(int productId)
        throws DaoException
    {
        Integer count = this.getSqlSession().selectOne("ProductCommonMapper.findProductSellCountById", productId);
        if (count == null)
            count = 0;
        return count;
    }
    
    @Override
    public List<Map<String, Object>> findProductSellCountByIds(List<Integer> productId)
        throws Exception
    {
        List<Map<String, Object>> products = this.getSqlSession().selectList("ProductCommonMapper.findProductSellCountByIds", productId);
        if (products == null)
            products = new ArrayList<Map<String, Object>>();
        return products;
    }
    
}
