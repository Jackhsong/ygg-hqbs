package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.ygg.webapp.dao.OrderAliPayDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderAliPayEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderAliPayDao")
public class OrderAliPayDaoImpl extends BaseDaoImpl implements OrderAliPayDao
{
    
    @Override
    public int replaceIntoOrderAliPay(OrderAliPayEntity oape)
        throws DaoException
    {
        String sql = "INSERT INTO order_ali_pay(order_id,pay_mark,create_time) VALUES(?,?,?)";
        return this.getSqlSession().insert("OrderAliPayMapper.replaceIntoOrderAliPay", oape);
    }
    
    @Override
    public int updateOrderAliPay(OrderAliPayEntity oape)
        throws DaoException
    {
        String sql = "UPDATE order_ali_pay SET is_pay=?,pay_tid=? WHERE order_id=?";
        return this.getSqlSession().update("OrderAliPayMapper.updateOrderAliPay", oape);
    }
    
    @Override
    public List<OrderAliPayEntity> findOrderAliPayByMark(String payMark)
        throws DaoException
    {
        String sql = "SELECT id,order_id,is_pay FROM order_ali_pay WHERE pay_mark=?";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("payMark", payMark);
        List<OrderAliPayEntity> list = this.getSqlSession().selectList("OrderAliPayMapper.findOrderAliPayByMark", map);
        if (list == null)
            list = new ArrayList<OrderAliPayEntity>();
        return list;
    }
    
    @Override
    public List<OrderAliPayEntity> findOrderAliPayByOrderId(int orderId)
        throws DaoException
    {
        List<OrderAliPayEntity> oape = this.getSqlSession().selectList("OrderAliPayMapper.findOrderAliPayByOrderId", orderId);
        if (oape == null)
            oape = new ArrayList<OrderAliPayEntity>();
        return oape;
    }
    
}
