package com.ygg.webapp.dao.impl.mybatis;

import com.ygg.webapp.dao.OrderGiftShareDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ActivitiesOrderGiftEntity;
import com.ygg.webapp.exception.DaoException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单红包
 */
@Repository("orderGiftShareDao")
public class OrderGiftShareDaoImpl extends BaseDaoImpl implements OrderGiftShareDao
{
    @Override
    public int addMobileByWeixinOpenId(String wxOpenId, String mobileNumber)
        throws DaoException
    {
        Map<String,Object> para = new HashMap<>();
        para.put("mobileNumber", mobileNumber);
        para.put("wxOpenId", wxOpenId);
        return getSqlSession().insert("OrderGiftShareMapper.addMobileByWeixinOpenId", para);
    }

    @Override
    public int deleteMobile(String mobileNumber)
        throws DaoException
    {
        return getSqlSession().delete("OrderGiftShareMapper.deleteMobile", mobileNumber);
    }

    @Override
    public String findMobileByWeixinOpenId(String weixinOpenid)
        throws DaoException
    {
        return getSqlSession().selectOne("OrderGiftShareMapper.findMobileByWeixinOpenId", weixinOpenid);
    }
    
    @Override
    public Map<String, Object> findRecordByWXIdAndGiftId(String wxId, int giftId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("weixinOpenid", wxId);
        para.put("activitiesOrderGiftId", giftId);
        return getSqlSession().selectOne("OrderGiftShareMapper.findRecordByWXIdAndGiftId", para);
    }

    @Override
    public Map<String, Object> findRecordByMobileAndGiftId(String mobileNumber, int giftId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("mobileNumber", mobileNumber);
        para.put("activitiesOrderGiftId", giftId);
        return getSqlSession().selectOne("OrderGiftShareMapper.findRecordByMobileAndGiftId", para);
    }

    @Override
    public List<Map<String, Object>> findRecordByGiftId(int giftId)
        throws DaoException
    {
        return getSqlSession().selectList("OrderGiftShareMapper.findRecordByGiftId", giftId);
    }

    @Override
    public ActivitiesOrderGiftEntity findActivitiesOrderGiftById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("OrderGiftShareMapper.findActivitiesOrderGiftById", id);
    }

    @Override
    public int insertActivitiesOrderGiftRecord(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("OrderGiftShareMapper.insertActivitiesOrderGiftRecord", para);
    }

    @Override
    public int updateActivitiesOrderGiftById(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().update("OrderGiftShareMapper.updateActivitiesOrderGiftById", para);
    }

    @Override
    public int updateMobilePhone(String wxOpenId, String mobileNumber)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("mobileNumber", mobileNumber);
        para.put("weixinOpenid", wxOpenId);
        return getSqlSession().update("OrderGiftShareMapper.updateMobilePhone", para);
    }
}
