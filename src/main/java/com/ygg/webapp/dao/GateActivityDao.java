package com.ygg.webapp.dao;

import java.util.Map;

import com.ygg.webapp.entity.GateActivityEntity;
import com.ygg.webapp.entity.GatePrizeEntity;
import com.ygg.webapp.exception.DaoException;

public interface GateActivityDao
{
    /**
     * 查找任意门
     * @param para
     * @return
     * @throws DaoException
     */
    GateActivityEntity findGateActivity(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 查找任意门奖励
     * @param id
     * @return
     * @throws DaoException
     */
    GatePrizeEntity findGatePrizeByGateId(int id)
        throws DaoException;
    
    /**
     * 根据Id查找任意门
     * @param gateId
     * @return
     * @throws DaoException
     */
    GateActivityEntity findGateActivityById(int gateId)
        throws DaoException;
    
    /**
     * 是否领取奖励
     * @param para
     * @return
     * @throws DaoException
     */
    boolean isReceived(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 更新任意门领券人数
     * @param gateId
     * @return
     * @throws DaoException
     */
    int updateGateReceiveAmount(int gateId)
        throws DaoException;
    
    /**
     * 更新任意门带来新注册用户人数
     * @param gateId
     * @return
     * @throws DaoException
     */
    int updateGateRewRegister(int gateId)
        throws DaoException;
    
    /**
     * 插入活动领取记录表
     * @param para
     * @return
     * @throws DaoException
     */
    int addRelationActivityAndReceivedMobile(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 查找下次开门
     * @param para
     * @return
     * @throws DaoException
     */
    GateActivityEntity findNextOpenGateActivity(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 查找预注册用户是否是通过任意门带来
     * @param para
     * @return
     * @throws DaoException
     */
    int findGateIdByNameFromRegisterCoupon(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 插入relation_activity_and_account表
     * @param map
     * @return
     * @throws DaoException
     */
    int addRelationActivityAndAccount(Map<String, Object> map)
        throws DaoException;
    
    /**
     * 更新任意门带来注册用户
     * @param gateId
     * @return
     * @throws DaoException
     */
    int updateGateNewRegisterAmount(int gateId)
        throws DaoException;
    
}
