package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderProductRefundLogisticDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderProductRefundLogisticsEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderProductRefundLogisticDao")
public class OrderProductRefundLogisticDaoImpl extends BaseDaoImpl implements OrderProductRefundLogisticDao
{
    
    @Override
    public int insertOrderProductRefundLogistic(OrderProductRefundLogisticsEntity oprfe)
        throws DaoException
    {
        
        return this.getSqlSession().insert("OrderProductRefundLogisticsMapper.insertOrderProductRefundLogistic", oprfe);
    }
    
    @Override
    public OrderProductRefundLogisticsEntity queryOrderProductRefundLogisticByOId(int orderProductFrefundId)
        throws DaoException
    {
        
        OrderProductRefundLogisticsEntity opres =
            this.getSqlSession().selectOne("OrderProductRefundLogisticsMapper.queryOrderProductRefundLogisticByOId", orderProductFrefundId);
        
        return opres;
    }
    
}
