package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.SpreadChannelEntity;
import com.ygg.webapp.entity.SpreadChannelPrizeEntity;
import com.ygg.webapp.exception.ServiceException;

public interface SpreadChannelService
{
    
    SpreadChannelEntity findSpreadChannelById(int channelId)
        throws ServiceException;
    
    List<SpreadChannelPrizeEntity> findSpreadChannelPrizeByChannelId(int channelId)
        throws ServiceException;
    
    Map<String, Object> receivePrize(String mobileNumber, int channelId)
        throws ServiceException;
    
}
