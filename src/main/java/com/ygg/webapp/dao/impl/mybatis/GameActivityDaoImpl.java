package com.ygg.webapp.dao.impl.mybatis;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.GameActivityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.GameActivityEntity;
import com.ygg.webapp.entity.GamePrizeEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("gameActivityDao")
public class GameActivityDaoImpl extends BaseDaoImpl implements GameActivityDao
{
    
    @Override
    public GameActivityEntity findGameActivityById(int gameId)
        throws DaoException
    {
        return getSqlSession().selectOne("GameActivityMapper.findGameActivityById", gameId);
    }
    
    @Override
    public GamePrizeEntity findGamePrizeByGameId(int gameId)
        throws DaoException
    {
        return getSqlSession().selectOne("GameActivityMapper.findGamePrizeByGameId", gameId);
    }
    
    @Override
    public boolean isReceived(Map<String, Object> para)
        throws DaoException
    {
        int count = getSqlSession().selectOne("GameActivityMapper.isReceived", para);
        return count > 0;
    }
    
    @Override
    public int updateGameReceiveAmount(int gameId)
        throws DaoException
    {
        
        return getSqlSession().update("GameActivityMapper.updateGameReceiveAmount", gameId);
    }
    
    @Override
    public int updateGameNewRegisterAmount(int gameId)
        throws DaoException
    {
        return getSqlSession().update("GameActivityMapper.updateGameNewRegisterAmount", gameId);
    }
    
    @Override
    public int findGameIdByNameFromRegisterCoupon(Map<String, Object> para)
        throws DaoException
    {
        Integer gameId = getSqlSession().selectOne("GameActivityMapper.findGameIdByNameFromRegisterCoupon", para);
        return gameId == null ? CommonConstant.ID_NOT_EXIST : gameId.intValue();
    }
    
    @Override
    public int addRelationActivityAndAccount(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("GameActivityMapper.addRelationActivityAndAccount", para);
    }
    
    @Override
    public int addRelationActivityAndReceivedMobile(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().insert("GameActivityMapper.addRelationActivityAndReceivedMobile", para);
    }
}
