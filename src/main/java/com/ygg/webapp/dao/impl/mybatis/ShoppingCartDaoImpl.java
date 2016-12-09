package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CartLockCountEntity;
import com.ygg.webapp.entity.ShoppingCartEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonEnum;

@Repository("shoppingCartDao")
public class ShoppingCartDaoImpl extends BaseDaoImpl implements ShoppingCartDao
{
    
    @Override
    public ShoppingCartEntity findLackCartByPIdAndAId(int productId, int accountId)
        throws DaoException
    {
        // String sql =
        // "SELECT id,product_count,status FROM shopping_cart WHERE account_id=? AND product_id=? AND status="
        // + CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue() + " LIMIT 1";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        ShoppingCartEntity sce = this.getSqlSession().selectOne("ShoppingCartMapper.findLackCartByPIdAndAId", params);
        return sce;
        
    }
    
    @Override
    public ShoppingCartEntity findNormalCartByPIdAndAId(int productId, int accountId)
        throws DaoException
    {
        String sql =
            "SELECT id,product_count,status FROM shopping_cart WHERE account_id=? AND product_id=? AND status="
                + CommonEnum.SHOPPING_CART_STATUS.NORMAL.getValue() + " LIMIT 1";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        ShoppingCartEntity sce = this.getSqlSession().selectOne("ShoppingCartMapper.findNormalCartByPIdAndAId", params);
        return sce;
    }
    
    @Override
    public int updateShoppingCart(ShoppingCartEntity sce)
        throws DaoException
    {
        String sql = "UPDATE shopping_cart SET status=?,product_count=? WHERE id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", sce.getStatus());
        params.put("productCount", sce.getProductCount());
        params.put("id", sce.getId());
        return this.getSqlSession().update("ShoppingCartMapper.updateShoppingCart", params);
        
    }
    
    @Override
    public int findLockCountByPIdAndAId(int productId, int accountId)
        throws DaoException
    {
        String sql = "SELECT product_count FROM cart_lock_count WHERE account_id=? AND product_id=? LIMIT 1";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * accountId)); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, productId)); Map<String, Object> map =
         * queryOneMap(sql, params); if (map == null) { return -1; } return (Integer)map.get("product_count");
         */
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        Integer c = this.getSqlSession().selectOne("ShoppingCartMapper.findLockCountByPIdAndAId", params);
        if (c == null)
            c = -1;
        return c.intValue();
    }
    
    @Override
    public int deleteLockCountByPIdAndAId(int productId, int accountId)
        throws DaoException
    {
        String sql = "DELETE FROM cart_lock_count WHERE account_id=? AND product_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        return 1;//this.getSqlSession().delete("ShoppingCartMapper.deleteLockCountByPIdAndAId", params);
    }
    
    @Override
    public int addShoppingCart(ShoppingCartEntity sce)
        throws DaoException
    {
        String sql = "INSERT INTO shopping_cart(account_id,product_id,product_count,create_time) values(?,?,?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", sce.getAccountId());
        params.put("productId", sce.getProductId());
        params.put("productCount", sce.getProductCount());
        params.put("createTime", new Timestamp(System.currentTimeMillis()));
        return this.getSqlSession().insert("ShoppingCartMapper.addShoppingCart", params);
    }
    
    @Override
    public int addLockCount(int productId, int accountId, int count)
        throws DaoException
    {
        String sql = "INSERT INTO cart_lock_count(account_id,product_id,product_count) values(?,?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        params.put("productCount", count);
        return 1;//this.getSqlSession().insert("ShoppingCartMapper.addLockCount", params);
    }
    
    @Override
    public int addLockTime(int accountId, long validTime)
        throws DaoException
    {
        String sql = "INSERT INTO cart_lock_time(account_id,valid_time) values(?,?)";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("validTime", new Timestamp(validTime));
        return this.getSqlSession().insert("ShoppingCartMapper.addLockTime", params);
        
    }
    
    @Override
    public int updateLockTime(int accountId, long validTime)
        throws DaoException
    {
        String sql = "UPDATE cart_lock_time SET valid_time=?,is_valid=1 WHERE account_id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("validTime", new Timestamp(validTime));
        return this.getSqlSession().update("ShoppingCartMapper.updateLockTime", params);
    }
    
    @Override
    public int updateLockCount(int productId, int accountId, int count)
        throws DaoException
    {
        String sql = "UPDATE cart_lock_count SET product_count=? WHERE account_id=? AND product_id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        params.put("productCount", count);
        return this.getSqlSession().update("ShoppingCartMapper.updateLockCount", params);
    }
    
    @Override
    public String findValidTimeByAid(int accountId)
        throws DaoException
    {
        // String sql = "SELECT valid_time from cart_lock_time WHERE account_id=?";
        Timestamp tp = this.getSqlSession().selectOne("ShoppingCartMapper.findValidTimeByAid", accountId);
        if (tp == null)
            return "";
        return tp.toString();
    }
    
    public int addInvalidLockTime(int accountId)
        throws DaoException
    {
        int r = this.getSqlSession().insert("ShoppingCartMapper.addInvalidLockTime", accountId);
        return r;
    }
    
    @Override
    public List<ShoppingCartEntity> findAllNormalCartByAId(int accountId)
        throws DaoException
    {
        String sql = "SELECT product_count,product_id FROM shopping_cart WHERE account_id=? AND status=" + CommonEnum.SHOPPING_CART_STATUS.NORMAL.getValue();
        
        List<ShoppingCartEntity> list = this.getSqlSession().selectList("ShoppingCartMapper.findAllNormalCartByAId", accountId);
        if (list == null)
            list = new ArrayList<ShoppingCartEntity>();
        return list;
    }
    
    @Override
    public List<ShoppingCartEntity> findAllNormalCartByAIdExceptPid(int accountId, int productId)
        throws DaoException
    {
        String sql =
            "SELECT product_count,product_id,id,status FROM shopping_cart WHERE account_id=? AND product_id!=? AND status="
                + CommonEnum.SHOPPING_CART_STATUS.NORMAL.getValue() + " ORDER BY product_id";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("productId", productId);
        List<ShoppingCartEntity> list = this.getSqlSession().selectList("ShoppingCartMapper.findAllNormalCartByAIdExceptPid", params);
        if (list == null)
            list = new ArrayList<ShoppingCartEntity>();
        return list;
    }
    
    @Override
    public List<Integer> findAccountIdsAndNoValid()
        throws DaoException
    {
        // String sql ="select d.account_id from cart_lock_time as d where (now() - d.valid_time) >0 and d.is_valid=1 "
        // ;
        List<Integer> accountIds = this.getSqlSession().selectList("ShoppingCartMapper.findAccountIdsAndNoValid");
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
    public List<Integer> findLockCountProductIdsByAccountId(int accountId)
        throws DaoException
    {
        // String sql ="select product_id as productId from cart_lock_count where account_id=5 " ;
        List<Integer> prodcutIds = this.getSqlSession().selectList("ShoppingCartMapper.findLockCountProductIdsByAccountId", accountId);
        if (prodcutIds == null)
            prodcutIds = new ArrayList<Integer>();
        return prodcutIds;
    }
    
    @Override
    public int updateIsValidByAccountId(int accountId, int isValid)
        throws DaoException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("isValid", isValid);
        return this.getSqlSession().update("ShoppingCartMapper.updateIsValidByAccountId", params);
    }
    
    @Override
    public int findShoppingCartCountByAccountId(int accountId)
        throws DaoException
    {
        
        Integer cartCount = this.getSqlSession().selectOne("ShoppingCartMapper.findShoppingCartCountByAccountId", accountId);
        if (cartCount == null)
            cartCount = 0;
        return cartCount;
    }
    
    @Override
    public List<ShoppingCartEntity> findAllLackCartByAId(int accountId)
        throws DaoException
    {
        String sql =
            "SELECT product_count,product_id,id,status FROM shopping_cart WHERE account_id=? AND status="
                + CommonEnum.SHOPPING_CART_STATUS.STOCK_LACK.getValue() + " ORDER BY update_time DESC";
        
        List<ShoppingCartEntity> list = this.getSqlSession().selectList("ShoppingCartMapper.findAllLackCartByAId", accountId);
        if (list == null)
            list = new ArrayList<ShoppingCartEntity>();
        return list;
    }
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", accountId);
        map.put("productId", productId);
        
        Integer productCount = this.getSqlSession().selectOne("ShoppingCartMapper.findProductCountByAIdAndPId", map);
        if (productCount == null)
            productCount = 0;
        return productCount;
    }
    
}
