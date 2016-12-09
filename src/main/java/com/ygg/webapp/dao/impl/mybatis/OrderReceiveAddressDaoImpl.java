package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderReceiveAddressDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderReceiveAddressEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("orderReceiveAddressDao")
public class OrderReceiveAddressDaoImpl extends BaseDaoImpl implements OrderReceiveAddressDao
{
    
    @Override
    public int addAddress(OrderReceiveAddressEntity orae)
        throws DaoException
    {
        String sql = "INSERT INTO order_receive_address(full_name,mobile_number,detail_address,province,city,district) VALUES(?,?,?,?,?,?)";
        return this.getSqlSession().insert("OrderReceiveAddressMapper.addAddress", orae);
    }
    
    @Override
    public OrderReceiveAddressEntity findAddressById(int id)
        throws DaoException
    {
        String sql = "SELECT * FROM order_receive_address WHERE id=? LIMIT 1";
        OrderReceiveAddressEntity orae = this.getSqlSession().selectOne("OrderReceiveAddressMapper.findAddressById", id);
        
        return orae;
    }
    
}
