package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderConfirmDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderConfirmEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderConfirmDao")
public class OrderConfirmDaoImpl extends BaseDaoImpl implements OrderConfirmDao
{
    
    @Override
    public int addOrderConfirm(OrderConfirmEntity oce)
        throws DaoException
    {
        String sql = "INSERT INTO order_confirm(number,account_id,temp_account_id,freight_template_id,total_price,seller_id) VALUES(?,?,?,?,?)";
        return this.getSqlSession().insert("OrderConfimMapper.addOrderConfirm", oce);
    }
    
    @Override
    public List<OrderConfirmEntity> findConfirmByNumberAndAId(long number, int accountId)
        throws DaoException
    {
        String sql =
            "SELECT id,account_id,temp_account_id,freight_template_id,total_price,is_valid FROM order_confirm WHERE number=? AND is_valid=1 AND account_id=?";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("number", number);
        map.put("accountId", accountId);
        
        List<OrderConfirmEntity> oces = this.getSqlSession().selectList("OrderConfimMapper.findConfirmByNumberAndAId", map);
        if (oces == null)
            oces = new ArrayList<OrderConfirmEntity>();
        return oces;
    }
    
    @Override
    public List<OrderConfirmEntity> findConfirmByNumberAndTId(long number, int tempAccountId)
        throws DaoException
    {
        String sql =
            "SELECT id,account_id,temp_account_id,freight_template_id,total_price,is_valid FROM order_confirm WHERE number=? AND is_valid=1 AND temp_account_id=?";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("number", number);
        map.put("tempAccountId", tempAccountId);
        List<OrderConfirmEntity> oces = this.getSqlSession().selectList("OrderConfimMapper.findConfirmByNumberAndTId", map);
        if (oces == null)
            oces = new ArrayList<OrderConfirmEntity>();
        return oces;
    }
    
    @Override
    public int updateOrderConfirm(OrderConfirmEntity oce)
        throws DaoException
    {
        String sql = "UPDATE order_confirm SET is_valid=?,freight_template_id=? WHERE id=?";
        
        return this.getSqlSession().update("OrderConfimMapper.updateOrderConfirm", oce);
    }

    @Override
    public List<OrderConfirmEntity> findConfirmByNumber(long number)
        throws DaoException
    {
        return this.getSqlSession().selectList("OrderConfimMapper.findConfirmByNumber", number);
    }
}
