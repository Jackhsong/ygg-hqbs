package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CartLockCountEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("productCountDao")
public class ProductCountDaoImpl extends BaseDaoImpl implements ProductCountDao
{
    
    @Override
    public ProductCountEntity findProductCountInfoById(int productId)
        throws DaoException
    {
        // String sql =
        // "SELECT sell,stock,`lock`,restriction,stock_algorithm FROM product_count WHERE product_id=? LIMIT 1";
        ProductCountEntity pe = this.getSqlSession().selectOne("ProductCountMapper.findProductCountInfoById", productId);
        return pe;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ProductCountEntity>[] findProductCountSumByCommonIds(List<Integer> activitiesCommonIds)
        throws DaoException
    {
        /*
         * String[] sqlArray = new String[activitiesCommonIds.size()]; List<PstmtParam>[] paramArray = new
         * ArrayList[activitiesCommonIds.size()]; for (int i = 0; i < activitiesCommonIds.size(); i++) { sqlArray[i] =
         * "SELECT SUM(p.stock) as stock,SUM(p.lock) as lock FROM product_count AS p INNER JOIN (SELECT product_id FROM relation_activities_common_and_product WHERE activities_common_id=?) AS r ON p.product_id=r.product_id"
         * ; List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new
         * PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, activitiesCommonIds.get(i))); paramArray[i] = params; } return
         * queryBatch2T(sqlArray, paramArray);
         */
        return null;
    }
    
    @Override
    public ProductCountEntity findProductCountSumByCommonId(int id)
        throws DaoException
    {
        // String sql =
        // "SELECT SUM(p.stock) as stock,SUM(p.lock) as `lock` FROM product_count AS p INNER JOIN (SELECT product_id FROM relation_activities_common_and_product WHERE activities_common_id=?) AS r ON p.product_id=r.product_id";
        ProductCountEntity pe = this.getSqlSession().selectOne("ProductCountMapper.findProductCountSumByCommonId", id);
        return pe;
        
    }
    
    @Override
    public ProductCountEntity findProductCountSumByCustomId(int id)
        throws DaoException
    {
        // String sql =
        // "SELECT SUM(p.stock) as stock,SUM(p.lock) as `lock` FROM product_count AS p INNER JOIN (SELECT product_id FROM relation_activities_custom_and_product WHERE activities_custom_id=?) AS r ON p.product_id=r.product_id";
        ProductCountEntity pe = this.getSqlSession().selectOne("ProductCountMapper.findProductCountSumByCustomId", id);
        return pe;
        
    }
    
    @Override
    public int updateProductCountByProductId(ProductCountEntity pce)
        throws DaoException
    {
        // String sql =" update product_count set `lock`=2 where product_id =1 " ;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("newLock", pce.getLock());
        params.put("productId", pce.getProductId());
        return this.getSqlSession().update("ProductCountMapper.updateProductCountByProductId", params);
    }
    
    @Override
    public ProductCountEntity findProductCountByProductId(int productId)
        throws DaoException
    {
        return null;
    }
    
    @Override
    public List<ProductCountEntity> findProductCountByProductIds(List<Integer> productIds)
        throws DaoException
    {
        // String
        // sql="select  id as id ,  product_id as productId , sell  as sell ,  stock as stock , `lock`  as `lock`  ,  stock_algorithm as stockAlgorithm "+
        // " from product_count   where product_id in (1,3) for update " ;
        List<ProductCountEntity> list = this.getSqlSession().selectList("ProductCountMapper.findProductCountByProductIds", productIds);
        if (list == null)
            list = new ArrayList<ProductCountEntity>();
        return list;
    }
    
    public ProductCountEntity findProductCountSumByProductIds(List<Integer> productIds)
        throws DaoException
    {
        if (productIds == null || productIds.isEmpty())
            return new ProductCountEntity();
        
        ProductCountEntity pe = this.getSqlSession().selectOne("ProductCountMapper.findProductCountSumByProductIds", productIds);
        return pe;
    }
    
    @Override
    public List<Integer> findAccountIdsAndNoValid()
        throws DaoException
    {
        List<Integer> accountIds = this.getSqlSession().selectList("ProductCountMapper.findAccountIdsAndNoValid");
        if (accountIds == null)
            accountIds = new ArrayList<Integer>();
        return accountIds;
    }
    
    @Override
    public List<CartLockCountEntity> findLockCountByAccountId(int accountId)
        throws DaoException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Integer> findLockCountProductIdsByAccountId(int orderId)
        throws DaoException
    {
        // String sql ="select product_id as productId from order_lock_count where account_id=5 " ;
        List<Integer> prodcutIds = this.getSqlSession().selectList("ProductCountMapper.findLockCountProductIdsByAccountId", orderId);
        if (prodcutIds == null)
            prodcutIds = new ArrayList<Integer>();
        return prodcutIds;
    }
    
    @Override
    public int findLockCountByPIdAndAId(int productId, int orderId)
        throws DaoException
    {
        // String sql = "SELECT product_count FROM order_product_lock_count WHERE order_id=? AND product_id=? LIMIT 1";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("productId", productId);
        Integer c = this.getSqlSession().selectOne("ProductCountMapper.findLockCountByPIdAndAId", params);
        if (c == null)
            c = 0;
        return c.intValue();
    }
    
    @Override
    public int deleteLockCountByPIdAndAId(int productId, int orderId)
        throws DaoException
    {
        // String sql = "DELETE FROM order_product_lock_count WHERE account_id=? AND product_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("productId", productId);
        return this.getSqlSession().delete("ProductCountMapper.deleteLockCountByPIdAndAId", params);
    }
    
    @Override
    public int updateOrderStatusByOrderid(int orderId, int status)
        throws DaoException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("status", status);
        return this.getSqlSession().update("ProductCountMapper.updateOrderStatusByOrderid", params);
    }
    
    @Override
    public int updateIsValidByOrderId(int orderId, int isValid)
        throws DaoException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);
        params.put("isValid", isValid);
        return this.getSqlSession().update("ProductCountMapper.updateIsValidByOrderId", params);
    }
    
    @Override
    public ProductCountEntity findCountInfoByIdForUpdate(int productId)
        throws DaoException
    {
        // String sql =
        // "SELECT id,sell,stock,`lock`,restriction,stock_algorithm FROM product_count WHERE product_id=? LIMIT 1 FOR UPDATE";
        ProductCountEntity pce = this.getSqlSession().selectOne("ProductCountMapper.findCountInfoByIdForUpdate", productId);
        return pce;
    }
    
    @Override
    public int updateCountInfo(ProductCountEntity pce)
        throws DaoException
    {
        // String sql = "UPDATE product_count SET `lock`=? WHERE id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("newlock", pce.getLock());
        params.put("id", pce.getId());
        params.put("stock", pce.getStock());
        params.put("sell", pce.getSell());
        return this.getSqlSession().update("ProductCountMapper.updateCountInfo", params);
    }
    
    @Override
    public List<ProductCountEntity> findCountInfosByIdsForUpdate(List<Integer> productIds)
        throws DaoException
    {
        if (productIds.size() == 0)
        {
            return new ArrayList<>();
        }
        List<ProductCountEntity> list = this.getSqlSession().selectList("ProductCountMapper.findCountInfosByIdsForUpdate", productIds);
        return list;
    }
    
    @Override
    public ProductCountEntity findCountInfoById(int productId)
        throws DaoException
    {
        String sql = "SELECT id,sell,stock,`lock`,restriction,stock_algorithm FROM product_count WHERE product_id=? LIMIT 1";
        ProductCountEntity pe = this.getSqlSession().selectOne("ProductCountMapper.findCountInfoById", productId);
        return pe;
    }
    
    @Override
    public int findSellCountByProductId(int productId)
        throws DaoException
    {
        Integer sellcount = this.getSqlSession().selectOne("ProductCountMapper.findSellCountByProductId", productId);
        if (sellcount == null)
            sellcount = 0;
        return sellcount;
    }
    
}
