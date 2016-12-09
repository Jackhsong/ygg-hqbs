package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderProductRefundDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderProductRefundEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("orderProductRefundDao")
public class OrderProductRefundDaoImpl extends BaseDaoImpl implements OrderProductRefundDao
{
    
    public int queryOrderRefundCountByAccountId(int accountId)
        throws DaoException
    {
        Integer count = this.getSqlSession().selectOne("OrderProductRefundMapper.queryOrderRefundCountByAccountId", accountId);
        if (count == null)
            count = 0;
        return count;
    }
    
    @Override
    public int insertOrderProductRefund(OrderProductRefundEntity ope)
        throws DaoException
    {
        ope.setRealMoney(ope.getRealMoney());
        return this.getSqlSession().insert("OrderProductRefundMapper.insertOrderProductRefund", ope);
    }
    
    @Override
    public int updateOrderProductRefundInfo(OrderProductRefundEntity ope)
        throws DaoException
    {
        return this.getSqlSession().insert("OrderProductRefundMapper.updateOrderProductRefundInfo", ope);
    }
    
    @Override
    public OrderProductRefundEntity queryOrderProductRefundById(int orderProductRefundId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("OrderProductRefundMapper.queryOrderProductRefundById", orderProductRefundId);
    }
    
    @Override
    public Map<String, Object> queryOPRInfosByOrderProductId(Map<String, Object> mapParams)
        throws DaoException
    {
        
        /*
         * Map<String,Object> map = new HashMap<String,Object>(); map.put("orderProductId", orderProductId) ;
         * map.put("productId", productId) ;
         */
        Map<String, Object> map = this.getSqlSession().selectOne("OrderProductRefundMapper.queryOPRInfosByOrderProductId", mapParams);
        if (map == null)
            map = new HashMap<String, Object>();
        return map;
    }
    
    @Override
    public OrderProductEntity queryOrderProductById(int orderProductId)
        throws DaoException
    {
        
        OrderProductEntity ope = this.getSqlSession().selectOne("OrderProductRefundMapper.queryOrderProductById", orderProductId);
        
        return ope;
    }
    
    public List<OrderProductRefundEntity> queryAllOrderProductRefund(int accountId, int page)
        throws DaoException
    {
        List<OrderProductRefundEntity> opes = null;
        Map<String, Object> map = new HashMap<String, Object>();
        
        // map.put("start", (page - 1) * CommonConstant.ORDER_PAGE_COUNT) ;
        map.put("page", CommonConstant.ORDER_PAGE_COUNT);
        map.put("accountId", accountId);
        opes = this.getSqlSession().selectList("OrderProductRefundMapper.queryAllOrderProductRefund", map);
        if (opes == null)
            opes = new ArrayList<OrderProductRefundEntity>();
        return opes;
        
    }
    
    public boolean isOrderProductRefundExist(int id)
        throws DaoException
    {
        Integer c = this.getSqlSession().selectOne("OrderProductRefundMapper.isOrderProductRefundExist", id);
        if (c == null || c.intValue() <= 0)
            return false;
        
        return true;
    }
    
    @Override
    public int updateOrderProductRefund(Map<String, Object> para)
        throws Exception
    {
        return getSqlSession().update("OrderProductRefundMapper.updateOrderProductRefund", para);
    }
    
    @Override
    public boolean queryOrderProductExists(OrderProductRefundEntity oprfe)
        throws DaoException
    {
        
        Integer c = this.getSqlSession().selectOne("OrderProductRefundMapper.queryOrderProductExists", oprfe);
        if (c != null && c.intValue() > 0)
            return true;
        return false;
    }
    
    @Override
    public String findOrderProductRefundIdByPidAndOidAndAid(String productId, String orderId, String accountId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("productId", productId);
        para.put("orderId", orderId);
        para.put("accountId", accountId);
        Integer orderProductRefundId = this.getSqlSession().selectOne("OrderProductRefundMapper.findOrderProductRefundIdByPidAndOidAndAid", para);
        return orderProductRefundId == null ? "-1" : orderProductRefundId.intValue() + "";
    }
    
    @Override
    public String findOrderProductIdByPidAndOid(String productId, String orderId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("productId", productId);
        para.put("orderId", orderId);
        Integer orderProductId = this.getSqlSession().selectOne("OrderProductRefundMapper.findOrderProductIdByPidAndOid", para);
        return orderProductId == null ? "-1" : orderProductId.intValue() + "";
    }

    @Override
    public List<Map<String, Object>> findRefundsByOrderId(int orderId)
        throws DaoException
    {
        return this.getSqlSession().selectList("OrderProductRefundMapper.findRefundsByOrderId", orderId);
    }
    

    @Override
    public String findOrderIdByOrderProductRefundId(String orderProductRefundId) throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("orderProductRefundId", orderProductRefundId);
        Integer orderId = this.getSqlSession().selectOne("OrderProductRefundMapper.findOrderIdByOrderProductRefundId", para);
        return orderId == null ? "-1" : orderId.intValue() + "";
    }

}
