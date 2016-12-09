package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderUnionPayDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderUnionPayEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderUnionPayDao")
public class OrderUnionPayDaoImpl extends BaseDaoImpl implements OrderUnionPayDao
{
    
    public int replaceIntoOrderUnionPay(OrderUnionPayEntity oape)
        throws DaoException
    {
        // String sql = "INSERT INTO order_union_pay(order_id,pay_mark,create_time) VALUES(?,?,?)";
        return this.getSqlSession().insert("OrderUnionPayMapper.replaceIntoOrderUnionPay", oape);
    }
    
    public int updateOrderUnionPay(OrderUnionPayEntity oape)
        throws DaoException
    {
        // String sql = "UPDATE order_union_pay SET is_pay=?,pay_tid=? WHERE order_id=?";
        return this.getSqlSession().update("OrderUnionPayMapper.updateOrderUnionPay", oape);
    }
    
    public List<OrderUnionPayEntity> findOrderUnionPayByMark(String payMark)
        throws DaoException
    {
        // String sql = "SELECT id,order_id,is_pay FROM order_union_pay WHERE pay_mark=?";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("payMark", payMark);
        List<OrderUnionPayEntity> list = this.getSqlSession().selectList("OrderUnionPayMapper.findOrderUnionPayByMark", map);
        if (list == null || list.isEmpty())
            list = new ArrayList<OrderUnionPayEntity>();
        return list;
    }
    
    @Override
    public List<OrderUnionPayEntity> findOrderUnionPayByOrderId(int orderId)
        throws DaoException
    {
        List<OrderUnionPayEntity> oupe = this.getSqlSession().selectList("OrderUnionPayMapper.findOrderUnionPayByOrderId", orderId);
        if (oupe == null)
            oupe = new ArrayList<OrderUnionPayEntity>();
        return oupe;
    }
    
}
