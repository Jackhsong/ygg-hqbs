package com.ygg.webapp.dao;

import java.util.Map;

import com.ygg.webapp.entity.GameActivityEntity;
import com.ygg.webapp.entity.GamePrizeEntity;
import com.ygg.webapp.exception.DaoException;

public interface GameActivityDao
{
    /**
     * 根据游戏Id查找游戏
     * @param gameId
     * @return
     * @throws DaoException
     */
    GameActivityEntity findGameActivityById(int gameId)
        throws DaoException;
    
    /**
     * 根据游戏Id查找游戏奖励
     * @param gameId
     * @return
     * @throws DaoException
     */
    GamePrizeEntity findGamePrizeByGameId(int gameId)
        throws DaoException;
    
    /**
     * 检查是否已经领取过该游戏奖励
     * @param para
     * @return
     * @throws DaoException
     */
    boolean isReceived(Map<String, Object> para)
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
     * 更新游戏的领取人数
     * @param gameId
     * @return
     * @throws DaoException
     */
    int updateGameReceiveAmount(int gameId)
        throws DaoException;
    
    /**
     * 更新游戏带来新注册用户人数
     * @param gameId
     * @return
     * @throws DaoException
     */
    int updateGameNewRegisterAmount(int gameId)
        throws DaoException;
    
    /**
     * 根据手机号从玩游戏预注册表中查找游戏Id
     * @param para
     * @return
     * @throws DaoException
     */
    int findGameIdByNameFromRegisterCoupon(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 插入relation_activity_and_account表
     * @param para
     * @return
     * @throws DaoException
     */
    
    int addRelationActivityAndAccount(Map<String, Object> para)
        throws DaoException;
    
}
