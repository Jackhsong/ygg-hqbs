package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.LogisticsDetailEntity;
import com.ygg.webapp.exception.DaoException;

public interface LogisticsDetailDao
{
    
    /**
     * 根据物流渠道和单号查询详情信息
     *
     * @return
     */
    List<LogisticsDetailEntity> findDetailByChannelAndNumber(String channel, String number)
        throws DaoException;
    
}