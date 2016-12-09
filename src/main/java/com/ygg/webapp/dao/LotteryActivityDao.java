package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.GiftActivityEntity;
import com.ygg.webapp.entity.GiftPrizeEntity;
import com.ygg.webapp.entity.LotteryActivityEntity;
import com.ygg.webapp.entity.LotteryPrizeEntity;
import com.ygg.webapp.exception.DaoException;

public interface LotteryActivityDao
{
    LotteryActivityEntity findLotteryActivityById(int id)
        throws DaoException;
    
    List<LotteryPrizeEntity> findLotteryPrizeByLotteryActivityId(int id)
        throws DaoException;
    
    int addLotteryRecord(String username, int lotteryActivityId, int lotteryPrizeId)
        throws DaoException;
    
    int addRelationLotteryActivityAndAccount(Map<String, Object> para)
        throws DaoException;
    
    Map<String, Object> findAccountActInfoByUsernameAndLAIdAndDay(String username, int lotteryId, int day)
        throws DaoException;
    
    Map<String, Object> findAccountActInfoByAccountIdAndLAIdAndDay(int accountId, int lotteryId, int day)
        throws DaoException;
    
    int updateAccountActInfoByUsernameAndLAIdAndDay(Map<String, Object> para)
        throws DaoException;
    
    GiftActivityEntity findGiftActivityById(int id)
        throws DaoException;
    
    int countGiftRecordByPara(Map<String, Object> para)
        throws Exception;
    
    int addGiftRecord(String username, int giftActivityId, int type)
        throws DaoException;
    
    List<GiftPrizeEntity> findGiftPrizeByGiftActivityIdAndDrawWay(int giftActivityId, int drawWay)
        throws Exception;

    int reducePrizeNum(int id, int oldNum)
        throws DaoException;
}
