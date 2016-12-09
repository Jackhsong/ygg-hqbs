package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SpreadChannelDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.SpreadChannelEntity;
import com.ygg.webapp.entity.SpreadChannelPrizeEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("spreadChannelDao")
public class SpreadChannelDaoImpl extends BaseDaoImpl implements SpreadChannelDao
{
    
    @Override
    public SpreadChannelEntity findSpreadChannelById(int channelId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("SpreadChannelMapper.findSpreadChannelById", channelId);
    }
    
    @Override
    public List<SpreadChannelPrizeEntity> findSpreadChannelPrizeByChannelId(int channelId)
        throws DaoException
    {
        List<SpreadChannelPrizeEntity> result = this.getSqlSession().selectList("SpreadChannelMapper.findSpreadChannelPrizeByChannelId", channelId);
        return result == null ? new ArrayList<SpreadChannelPrizeEntity>() : result;
    }
    
    @Override
    public boolean isReceived(Map<String, Object> para)
        throws DaoException
    {
        int count = getSqlSession().selectOne("SpreadChannelMapper.isReceived", para);
        return count > 0;
    }
    
    @Override
    public int addRelationActivityAndReceivedMobile(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("SpreadChannelMapper.addRelationActivityAndReceivedMobile", para);
    }
    
    @Override
    public int updateSpreadChannelReceiveAmount(int channelId)
        throws DaoException
    {
        return getSqlSession().update("SpreadChannelMapper.updateSpreadChannelReceiveAmount", channelId);
    }
    
    @Override
    public int findChannelIdByNameFromRegisterCoupon(Map<String, Object> para)
        throws DaoException
    {
        Integer channelId = this.getSqlSession().selectOne("SpreadChannelMapper.findChannelIdByNameFromRegisterCoupon", para);
        return channelId == null ? CommonConstant.ID_NOT_EXIST : channelId.intValue();
    }
    
    @Override
    public int addRelationActivityAndAccount(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().insert("SpreadChannelMapper.addRelationActivityAndAccount", para);
    }
    
    @Override
    public int updateSpreadChannelNewRegisterAmount(int channelId)
        throws DaoException
    {
        return getSqlSession().update("SpreadChannelMapper.updateSpreadChannelNewRegisterAmount", channelId);
    }
}
