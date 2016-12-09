package com.ygg.webapp.service;

import java.util.Map;

import com.ygg.webapp.entity.GameActivityEntity;
import com.ygg.webapp.entity.GamePrizeEntity;
import com.ygg.webapp.exception.ServiceException;

public interface GameActivityService
{
    /**
     * 检查游戏是否存在
     * @param gameId
     * @return
     * @throws Exception
     */
    boolean existsGameActivity(int gameId)
        throws ServiceException;
    
    /**
     * 查找游戏
     * @param gameId
     * @return
     * @throws Exception
     */
    GameActivityEntity findGameActivityById(int gameId)
        throws ServiceException;
    
    /**
     * 查找游戏奖励
     * @param gameId
     * @return
     * @throws Exception
     */
    GamePrizeEntity findGamePrizeByGameId(int gameId)
        throws ServiceException;
    
    /**
     * 玩游戏领取奖励
     * @param mobileNumber
     * @param gameId
     * @return
     */
    Map<String, Object> receivePrize(String mobileNumber, int gameId)
        throws ServiceException;
    
}
