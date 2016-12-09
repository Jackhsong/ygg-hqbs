package com.ygg.webapp.dao.impl.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.LotteryActivityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.GiftActivityEntity;
import com.ygg.webapp.entity.GiftPrizeEntity;
import com.ygg.webapp.entity.LotteryActivityEntity;
import com.ygg.webapp.entity.LotteryPrizeEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("lotteryActivityDao")
public class LotteryActivityDaoImpl extends BaseDaoImpl implements LotteryActivityDao
{
    
    @Override
    public LotteryActivityEntity findLotteryActivityById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("LotteryActivityMapper.findLotteryActivityById", id);
    }
    
    @Override
    public List<LotteryPrizeEntity> findLotteryPrizeByLotteryActivityId(int id)
        throws DaoException
    {
        List<LotteryPrizeEntity> reList = getSqlSession().selectList("LotteryActivityMapper.findLotteryPrizeByLotteryActivityId", id);
        return reList;
    }
    
    @Override
    public int addLotteryRecord(String username, int lotteryActivityId, int lotteryPrizeId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("username", username);
        para.put("lotteryActivityId", lotteryActivityId);
        para.put("lotteryPrizeId", lotteryPrizeId);
        return getSqlSession().insert("LotteryActivityMapper.addLotteryRecord", para);
    }
    
    @Override
    public int addRelationLotteryActivityAndAccount(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("LotteryActivityMapper.addRelationLotteryActivityAndAccount", para);
    }
    
    @Override
    public Map<String, Object> findAccountActInfoByUsernameAndLAIdAndDay(String username, int lotteryId, int day)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("lotteryActivityId", lotteryId);
        para.put("username", username);
        para.put("recordDay", day);
        return getSqlSession().selectOne("LotteryActivityMapper.findAccountActInfoByUsernameAndLAId", para);
    }
    
    @Override
    public int updateAccountActInfoByUsernameAndLAIdAndDay(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().update("LotteryActivityMapper.updateAccountActInfoByUsernameAndLAId", para);
    }
    
    @Override
    public Map<String, Object> findAccountActInfoByAccountIdAndLAIdAndDay(int accountId, int lotteryId, int day)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("lotteryActivityId", lotteryId);
        para.put("accountId", accountId);
        para.put("recordDay", day);
        return getSqlSession().selectOne("LotteryActivityMapper.findAccountActInfoByAccountIdAndLAId", para);
    }
    
    @Override
    public GiftActivityEntity findGiftActivityById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("LotteryActivityMapper.findGiftActivityById", id);
    }
    
    @Override
    public int countGiftRecordByPara(Map<String, Object> para)
        throws Exception
    {
        return getSqlSession().selectOne("LotteryActivityMapper.countGiftRecordByPara", para);
    }
    
    @Override
    public int addGiftRecord(String username, int giftActivityId, int type)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("username", username);
        para.put("giftActivityId", giftActivityId);
        para.put("type", type);
        return getSqlSession().insert("LotteryActivityMapper.addGiftRecord", para);
    }
    
    @Override
    public List<GiftPrizeEntity> findGiftPrizeByGiftActivityIdAndDrawWay(int giftActivityId, int drawWay)
        throws Exception
    {
        Map<String, Object> recordPara = new HashMap<String, Object>();
        recordPara.put("giftActivityId", giftActivityId);
        recordPara.put("drawWay", drawWay);
        return getSqlSession().selectList("LotteryActivityMapper.findGiftPrizeByGiftActivityIdAndDrawWay", recordPara);
    }

    @Override
    public int reducePrizeNum(int id, int oldNum)
        throws DaoException
    {
        Map<String,Object> para = new HashMap<>();
        para.put("id", id);
        para.put("oldNum", oldNum);
        return getSqlSession().update("LotteryActivityMapper.reducePrizeNum", para);
    }
}
