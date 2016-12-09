package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderLogisticsDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderLogisticsEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderLogisticsDao")
public class OrderLogisticsDaoImpl extends BaseDaoImpl implements OrderLogisticsDao
{
    
    @Override
    public int addOrderLogistics(OrderLogisticsEntity ole)
        throws DaoException
    {
        String sql = "INSERT INTO order_logistics(order_id,channel,number,money) VALUES(?,?,?,?)";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * ole.getOrderId())); params.add(new PstmtParam(FILL_PSTMT_TYPE.STRING, ole.getChannel())); params.add(new
         * PstmtParam(FILL_PSTMT_TYPE.STRING, ole.getNumber())); params.add(new PstmtParam(FILL_PSTMT_TYPE.SHORT,
         * ole.getMoney())); return insertReturnId(sql, params);
         */
        return this.getSqlSession().insert("LogisticsMapper.addOrderLogistics", ole);
        
    }
    
    @Override
    public OrderLogisticsEntity findOrderLogisticsByOId(int orderId)
        throws DaoException
    {
        String sql = "SELECT channel,number,money FROM order_logistics WHERE order_id=?";
        OrderLogisticsEntity ole = this.getSqlSession().selectOne("LogisticsMapper.findOrderLogisticsByOId", orderId);
        return ole;
    }
    
    public Map<String, Object> findOrderLogisticsByOrderProductRefundId(int orderProfuctRefundId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("LogisticsMapper.findOrderLogisticsByOrderProductRefundId", orderProfuctRefundId);
    }
    
}
