package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.SpreadChannelEntity;
import com.ygg.webapp.entity.SpreadChannelPrizeEntity;
import com.ygg.webapp.exception.DaoException;

public interface SpreadChannelDao
{
    
    SpreadChannelEntity findSpreadChannelById(int channelId)
        throws DaoException;
    
    List<SpreadChannelPrizeEntity> findSpreadChannelPrizeByChannelId(int channelId)
        throws DaoException;
    
    boolean isReceived(Map<String, Object> para)
        throws DaoException;
    
    int addRelationActivityAndReceivedMobile(Map<String, Object> para)
        throws DaoException;
    
    int updateSpreadChannelReceiveAmount(int channelId)
        throws DaoException;
    
    int findChannelIdByNameFromRegisterCoupon(Map<String, Object> para)
        throws DaoException;
    
    int addRelationActivityAndAccount(Map<String, Object> para)
        throws DaoException;
    
    int updateSpreadChannelNewRegisterAmount(int channelId)
        throws DaoException;
    
}
