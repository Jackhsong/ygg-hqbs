package com.ygg.webapp.dao.impl.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.GateActivityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.GateActivityEntity;
import com.ygg.webapp.entity.GatePrizeEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("gateActivityDao")
public class GateActivityDaoImpl extends BaseDaoImpl implements GateActivityDao
{
    
    @Override
    public GateActivityEntity findGateActivity(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().selectOne("GateActivityMapper.findGateActivity", para);
    }
    
    @Override
    public GatePrizeEntity findGatePrizeByGateId(int gateId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("GateActivityMapper.findGatePrizeByGateId", gateId);
    }
    
    @Override
    public GateActivityEntity findGateActivityById(int gateId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("id", gateId);
        return findGateActivity(para);
    }
    
    @Override
    public boolean isReceived(Map<String, Object> para)
        throws DaoException
    {
        int count = this.getSqlSession().selectOne("GateActivityMapper.isReceived", para);
        return count > 0;
    }
    
    @Override
    public int updateGateReceiveAmount(int gateId)
        throws DaoException
    {
        return this.getSqlSession().update("GateActivityMapper.updateGateReceiveAmount", gateId);
    }
    
    @Override
    public int updateGateRewRegister(int gateId)
        throws DaoException
    {
        return this.getSqlSession().update("GateActivityMapper.updateGateRewRegister", gateId);
    }
    
    @Override
    public int addRelationActivityAndReceivedMobile(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().insert("GateActivityMapper.addRelationActivityAndReceivedMobile", para);
    }
    
    @Override
    public GateActivityEntity findNextOpenGateActivity(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().selectOne("GateActivityMapper.findNextOpenGateActivity", para);
    }
    
    @Override
    public int addRelationActivityAndAccount(Map<String, Object> map)
        throws DaoException
    {
        return this.getSqlSession().insert("GateActivityMapper.addRelationActivityAndAccount", map);
    }
    
    @Override
    public int findGateIdByNameFromRegisterCoupon(Map<String, Object> para)
        throws DaoException
    {
        Integer gateId = this.getSqlSession().selectOne("GateActivityMapper.findGateIdByNameFromRegisterCoupon", para);
        return gateId == null ? CommonConstant.ID_NOT_EXIST : gateId.intValue();
    }
    
    @Override
    public int updateGateNewRegisterAmount(int gateId)
        throws DaoException
    {
        return this.getSqlSession().update("GateActivityMapper.updateGateNewRegisterAmount", gateId);
    }
    
}
