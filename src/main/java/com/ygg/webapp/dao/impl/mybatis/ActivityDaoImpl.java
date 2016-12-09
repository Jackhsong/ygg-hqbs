package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ActivityDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ActivityCrazyFoodEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodPrizeEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodRecordEntity;
import com.ygg.webapp.entity.ActivitySimplifyEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("activityDao")
public class ActivityDaoImpl extends BaseDaoImpl implements ActivityDao
{
    
    @Override
    public ActivitySimplifyEntity findActivitySimplifyById(int activityId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("ActivityMapper.findActivitySimplifyById", activityId);
    }
    
    @Override
    public List<Map<String, Object>> findActivitySimplifyProduct(int activityId)
        throws DaoException
    {
        List<Map<String, Object>> reList = this.getSqlSession().selectList("ActivityMapper.findActivitySimplifyProduct", activityId);
        return reList == null ? new ArrayList<Map<String, Object>>() : reList;
    }
    
    @Override
    public ActivityCrazyFoodEntity findActivityCrazyFoodById(int activityId)
        throws DaoException
    {
        return this.getSqlSession().selectOne("ActivityMapper.findActivityCrazyFoodById", activityId);
    }
    
    @Override
    public Map<String, Object> findActivityCrazyFoodRecord(int activityId, String mobileNumber)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("activityId", activityId);
        para.put("username", mobileNumber);
        return this.getSqlSession().selectOne("ActivityMapper.findActivityCrazyFoodRecord", para);
    }
    
    @Override
    public Map<String, Object> insertActivityCrazyFoodRecord(String mobileNumber, ActivityCrazyFoodEntity acfe, int isShared)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("username", mobileNumber);
        para.put("activityId", acfe.getId());
        if (isShared == 1)
        {
            para.put("leftTimes", acfe.getLimitTimes() + acfe.getShareIncreaseTimes());
        }
        else
        {
            para.put("leftTimes", acfe.getLimitTimes());
        }
        para.put("usedTimes", 0);
        para.put("isWine", 0);
        para.put("isShared", isShared);
        int result = this.getSqlSession().insert("ActivityMapper.insertActivityCrazyFoodRecord", para);
        return result > 0 ? para : null;
    }
    
    @Override
    public List<ActivityCrazyFoodPrizeEntity> findActivityCrazyFoodPrizeByActivityId(int activityId)
        throws DaoException
    {
        List<ActivityCrazyFoodPrizeEntity> reList = this.getSqlSession().selectList("ActivityMapper.findActivityCrazyFoodPrizeByActivityId", activityId);
        return reList == null ? new ArrayList<ActivityCrazyFoodPrizeEntity>() : reList;
    }
    
    @Override
    public int updateActivityCrazyFoodRecord(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().update("ActivityMapper.updateActivityCrazyFoodRecord", para);
    }
    
    @Override
    public int updateActivityCrazyFoodAmount(ActivityCrazyFoodPrizeEntity prize)
        throws DaoException
    {
        return this.getSqlSession().update("ActivityMapper.updateActivityCrazyFoodAmount", prize);
    }
    
    @Override
    public int insertRedPacketDrawRecord(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("ActivityMapper.insertRedPacketDrawRecord", para);
    }
    
    @Override
    public List<Map<String, Object>> findRedPacketDrawRecord(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().selectList("ActivityMapper.findRedPacketDrawRecord", para);
    }
    
    @Override
    public int countRedPacketDrawRecord(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().selectOne("ActivityMapper.countRedPacketDrawRecord", para);
    }
    
    @Override
    public int insertRedPacketShareRecord(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("ActivityMapper.insertRedPacketShareRecord", para);
    }
    
    @Override
    public Map<String, Object> findRedPacketShareRecordByShareAccountId(int shareAccountId)
        throws DaoException
    {
        return getSqlSession().selectOne("ActivityMapper.findRedPacketShareRecordByShareAccountId", shareAccountId);
    }
    
    @Override
    public int updateRedPacketShareRecordByShareAccountId(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().update("ActivityMapper.updateRedPacketShareRecordByShareAccountId", para);
    }
    
    @Override
    public int finActivityCouponPrize(String phoneNumber, int couponId, int type)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("mobileNumber", phoneNumber);
        para.put("couponId", couponId);
        para.put("sourceType", type);
        Integer id = this.getSqlSession().selectOne("ActivityMapper.finActivityCouponPrize", para);
        return id == null ? CommonConstant.ID_NOT_EXIST : id.intValue();
    }
    
    @Override
    public ActivityCrazyFoodRecordEntity findCrazyFoodRecord(int accountId, int activityId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("activityId", activityId);
        para.put("username", accountId + "");
        return getSqlSession().selectOne("ActivityMapper.findCrazyFoodRecord", para);
    }
    
    @Override
    public int countAccountBuyTimes(int accountId, String payTime)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", accountId);
        para.put("payTime", payTime);
        return getSqlSession().selectOne("ActivityMapper.countAccountBuyTimes", para);
    }
    
    @Override
    public ActivityCrazyFoodRecordEntity insertActivityCrazyFoodRecord(ActivityCrazyFoodRecordEntity record)
        throws DaoException
    {
        if (getSqlSession().insert("ActivityMapper.insertActivityCrazyFoodRecordNew", record) > 0)
        {
            return record;
        }
        return null;
    }
    
    @Override
    public int updateActivityCrazyFoodPrize(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().update("ActivityMapper.updateActivityCrazyFoodPrize", para);
    }
}
