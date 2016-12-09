package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CartLockCountEntity;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonEnum;

@Repository("tempShoppingCartDao")
public class TempShoppingCartDaoImpl extends BaseDaoImpl implements TempShoppingCartDao
{
    
    @Override
    public TempShoppingCartEntity findLackCartByPIdAndAId(int productId, int tempAccountId)
        throws DaoException
    {
        String sql =
            "SELECT id,product_count,status FROM temp_shopping_cart WHERE temp_account_id=? AND product_id=? AND status="
                + CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue() + " LIMIT 1";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        TempShoppingCartEntity sce = this.getSqlSession().selectOne("TempShoppingCartMapper.findLackCartByPIdAndAId", params);
        return sce;
        
    }
    
    @Override
    public TempShoppingCartEntity findNormalCartByPIdAndAId(int productId, int tempAccountId)
        throws DaoException
    {
        String sql =
            "SELECT id,product_count,status FROM temp_shopping_cart WHERE temp_account_id=? AND product_id=? AND status="
                + CommonEnum.TEMP_SHOPPING_CART_STATUS.NORMAL.getValue() + " LIMIT 1";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        TempShoppingCartEntity sce = this.getSqlSession().selectOne("TempShoppingCartMapper.findNormalCartByPIdAndAId", params);
        return sce;
        
    }
    
    @Override
    public int updateShoppingCart(TempShoppingCartEntity tsce)
        throws DaoException
    {
        String sql = "UPDATE temp_shopping_cart SET status=?,product_count=? WHERE id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", tsce.getStatus());
        params.put("productCount", tsce.getProductCount());
        params.put("id", tsce.getId());
        return this.getSqlSession().update("TempShoppingCartMapper.updateShoppingCart", params);
        
    }
    
    @Override
    public int findLockCountByPIdAndAId(int productId, int tempAccountId)
        throws DaoException
    {
        // String sql =
        // "SELECT product_count FROM temp_cart_lock_count WHERE temp_account_id=? AND product_id=? LIMIT 1";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        Integer c = this.getSqlSession().selectOne("TempShoppingCartMapper.findLockCountByPIdAndAId", params);
        if (c == null)
            c = -1;
        return c.intValue();
        
    }
    
    @Override
    public int deleteLockCountByPIdAndAId(int productId, int tempAccountId)
        throws DaoException
    {
        // String sql = "DELETE FROM temp_cart_lock_count WHERE temp_account_id=? AND product_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        return this.getSqlSession().delete("TempShoppingCartMapper.deleteLockCountByPIdAndAId", params);
        
    }
    
    @Override
    public int addShoppingCart(TempShoppingCartEntity tsce)
        throws DaoException
    {
        String sql = "INSERT INTO temp_shopping_cart(temp_account_id,product_id,product_count,create_time) values(?,?,?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tsce.getTempAccountId());
        params.put("productId", tsce.getProductId());
        params.put("productCount", tsce.getProductCount());
        params.put("createTime", new Timestamp(System.currentTimeMillis()));
        return this.getSqlSession().insert("TempShoppingCartMapper.addShoppingCart", params);
        
    }
    
    @Override
    public int addLockCount(int productId, int tempAccountId, int count)
        throws DaoException
    {
        String sql = "INSERT INTO temp_cart_lock_count(temp_account_id,product_id,product_count) values(?,?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        params.put("productCount", count);
        return this.getSqlSession().insert("TempShoppingCartMapper.addLockCount", params);
    }
    
    @Override
    public int addLockTime(int tempAccountId, long validTime)
        throws DaoException
    {
        String sql = "INSERT INTO temp_cart_lock_time(temp_account_id,valid_time) values(?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("validTime", new Timestamp(validTime));
        return this.getSqlSession().insert("TempShoppingCartMapper.addLockTime", params);
        
    }
    
    @Override
    public int updateLockTime(int tempAccountId, long validTime)
        throws DaoException
    {
        String sql = "UPDATE temp_cart_lock_time SET valid_time=?,is_valid=1 WHERE temp_account_id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("validTime", new Timestamp(validTime));
        return this.getSqlSession().update("TempShoppingCartMapper.updateLockTime", params);
    }
    
    @Override
    public int updateLockCount(int productId, int tempAccountId, int count)
        throws DaoException
    {
        // String sql = "UPDATE temp_cart_lock_count SET product_count=? WHERE temp_account_id=? AND product_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        params.put("productCount", count);
        return this.getSqlSession().update("TempShoppingCartMapper.updateLockCount", params);
    }
    
    @Override
    public String findValidTimeByAid(int tempAccountId)
        throws DaoException
    {
        String sql = "SELECT valid_time from temp_cart_lock_time WHERE temp_account_id=?";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * tempAccountId));
         * 
         * Map<String, Object> map = queryOneMap(sql, params); if (map == null) { return null; } return
         * ((Timestamp)map.get("valid_time")).toString();
         */
        
        Timestamp tp = this.getSqlSession().selectOne("TempShoppingCartMapper.findValidTimeByAid", tempAccountId);
        if (tp == null)
            return "";
        return tp.toString();
        
    }
    
    @Override
    public List<TempShoppingCartEntity> findAllNormalCartByAId(int tempAccountId)
        throws DaoException
    {
        String sql =
            "SELECT product_count,product_id FROM temp_shopping_cart WHERE temp_account_id=? AND status="
                + CommonEnum.TEMP_SHOPPING_CART_STATUS.NORMAL.getValue();
        
        List<TempShoppingCartEntity> list = this.getSqlSession().selectList("TempShoppingCartMapper.findAllNormalCartByAId", tempAccountId);
        if (list == null)
            list = new ArrayList<>();
        return list;
        
    }
    
    @Override
    public List<TempShoppingCartEntity> findAllNormalCartByAIdExceptPid(int tempAccountId, int productId)
        throws DaoException
    {
        String sql =
            "SELECT product_count,product_id,id,status FROM temp_shopping_cart WHERE temp_account_id=? AND product_id!=? AND status="
                + CommonEnum.TEMP_SHOPPING_CART_STATUS.NORMAL.getValue() + " ORDER BY product_id";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("productId", productId);
        List<TempShoppingCartEntity> list = this.getSqlSession().selectList("TempShoppingCartMapper.findAllNormalCartByAIdExceptPid", params);
        if (list == null)
            list = new ArrayList<TempShoppingCartEntity>();
        return list;
    }
    
    @Override
    public List<Integer> findTempAccountIdsAndNoValid()
        throws DaoException
    {
        // String sql
        // ="select d.temp_account_id as tempAccountId from temp_cart_lock_time as d where (now() - d.valid_time) >0 and d.is_valid=1 "
        // ;
        List<Integer> accountIds = this.getSqlSession().selectList("TempShoppingCartMapper.findTempAccountIdsAndNoValid");
        if (accountIds == null)
            accountIds = new ArrayList<Integer>();
        return accountIds;
    }
    
    @Override
    public List<CartLockCountEntity> findTempLockCountByAccountId(int accountId)
        throws DaoException
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<Integer> findTempLockCountProductIdsByAccountId(int tempaccountId)
        throws DaoException
    {
        // String sql ="select product_id as productId from temp_cart_lock_count where temp_account_id=5 " ;
        List<Integer> prodcutIds = this.getSqlSession().selectList("TempShoppingCartMapper.findTempLockCountProductIdsByAccountId", tempaccountId);
        if (prodcutIds == null)
            prodcutIds = new ArrayList<Integer>();
        return prodcutIds;
    }
    
    @Override
    public int updateIsValidByTempAccountId(int tempAccountId, int isValid)
        throws DaoException
    {
        // String sql = "UPDATE temp_cart_lock_time SET is_valid=? WHERE temp_account_id=?  ";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tempAccountId", tempAccountId);
        params.put("isValid", isValid);
        return this.getSqlSession().update("TempShoppingCartMapper.updateIsValidByTempAccountId", params);
    }
    
    @Override
    public int findTmpShoppingCartCountByAccountId(int tempAccountId)
        throws Exception
    {
        Integer cartCount = this.getSqlSession().selectOne("TempShoppingCartMapper.findTmpShoppingCartCountByAccountId", tempAccountId);
        if (cartCount == null)
            cartCount = 0;
        return cartCount;
    }
    
    @Override
    public List<TempShoppingCartEntity> findAllLackCartByAId(int accountId)
        throws DaoException
    {
        String sql =
            "SELECT product_count,product_id,id,status FROM temp_shopping_cart WHERE temp_account_id=? AND status="
                + CommonEnum.TEMP_SHOPPING_CART_STATUS.STOCK_LACK.getValue() + " ORDER BY update_time DESC";
        
        List<TempShoppingCartEntity> list = this.getSqlSession().selectList("TempShoppingCartMapper.findAllLackCartByAId", accountId);
        if (list == null)
            list = new ArrayList<TempShoppingCartEntity>();
        return list;
    }
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", accountId);
        map.put("productId", productId);
        
        Integer productCount = this.getSqlSession().selectOne("TempShoppingCartMapper.findProductCountByAIdAndPId", map);
        if (productCount == null)
            productCount = 0;
        return productCount;
    }
}
