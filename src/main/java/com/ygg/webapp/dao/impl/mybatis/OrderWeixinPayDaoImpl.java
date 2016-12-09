package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderWeixinPayDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderWeixinPayEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderWeixinPayDao")
public class OrderWeixinPayDaoImpl extends BaseDaoImpl implements OrderWeixinPayDao
{
    
    @Override
    public int replaceIntoOrderWeixinPay(OrderWeixinPayEntity owpe)
        throws DaoException
    {
        String sql = "INSERT INTO order_weixin_pay(order_id,pay_mark,create_time) VALUES(?,?,?)";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * owpe.getOrderId())); params.add(new PstmtParam(FILL_PSTMT_TYPE.STRING, owpe.getPayMark())); params.add(new
         * PstmtParam(FILL_PSTMT_TYPE.TIMESTAMP, new Timestamp(System.currentTimeMillis()))); return insertReturnId(sql,
         * params);
         */
        return this.getSqlSession().insert("OrderWeixinPayMapper.replaceIntoOrderWeixinPay", owpe);
    }
    
    @Override
    public int updateOrderWeixinPay(OrderWeixinPayEntity owpe)
        throws DaoException
    {
        String sql = "UPDATE order_weixin_pay SET is_pay=?,pay_tid=? WHERE order_id=?";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.BYTE,
         * owpe.getIsPay())); params.add(new PstmtParam(FILL_PSTMT_TYPE.STRING, owpe.getPayTid())); params.add(new
         * PstmtParam(FILL_PSTMT_TYPE.INT, owpe.getOrderId())); return execute(sql, params);
         */
        return this.getSqlSession().update("OrderWeixinPayMapper.updateOrderWeixinPay", owpe);
    }
    
    @Override
    public List<OrderWeixinPayEntity> findOrderWeixinPayByMark(String payMark)
        throws DaoException
    {
        String sql = "SELECT id,order_id,is_pay FROM order_weixin_pay WHERE pay_mark=?";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.STRING,
         * payMark)); return queryAllInfo2T(sql, params);
         */
        Map<String, String> map = new HashMap<String, String>();
        map.put("payMark", payMark);
        List<OrderWeixinPayEntity> list = this.getSqlSession().selectList("OrderWeixinPayMapper.findOrderWeixinPayByMark", map);
        if (list == null)
            list = new ArrayList<OrderWeixinPayEntity>();
        return list;
    }
    
    @Override
    public List<OrderWeixinPayEntity> findOrderWeixinPayByOrderId(int orderId)
        throws DaoException
    {
        List<OrderWeixinPayEntity> owpe = this.getSqlSession().selectList("OrderWeixinPayMapper.findOrderWeixinPayByOrderId", orderId);
        if (owpe == null)
            owpe = new ArrayList<OrderWeixinPayEntity>();
        return owpe;
    }
    
}
