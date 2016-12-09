package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.ActivityCrazyFoodEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodPrizeEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodRecordEntity;
import com.ygg.webapp.entity.ActivitySimplifyEntity;
import com.ygg.webapp.exception.DaoException;

public interface ActivityDao
{
    /**
     * 根据Id查找精品活动
     * @param activityId
     * @return
     * @throws DaoException
     */
    ActivitySimplifyEntity findActivitySimplifyById(int activityId)
        throws DaoException;
    
    /**
     * 查找精品活动商品列表
     * @param activityId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findActivitySimplifyProduct(int activityId)
        throws DaoException;
    
    /**
     * 根据Id查找疯狂美食活动
     * @param activityId
     * @return
     * @throws DaoException
     */
    ActivityCrazyFoodEntity findActivityCrazyFoodById(int activityId)
        throws DaoException;
    
    /**
     * 查找用户抽奖记录
     * @param activityId
     * @param mobileNumber
     * @return
     * @throws DaoException
     */
    Map<String, Object> findActivityCrazyFoodRecord(int activityId, String mobileNumber)
        throws DaoException;
    
    /**
     * 插入用户抽奖记录
     * @param mobileNumber：用户名
     * @param acfe：ActivityCrazyFoodEntity对象
     * @param isShared：是否分享，1是，0否
     * @return
     * @throws DaoException
     */
    Map<String, Object> insertActivityCrazyFoodRecord(String mobileNumber, ActivityCrazyFoodEntity acfe, int isShared)
        throws DaoException;
    
    /**
     * 根据活动Id查找奖品
     * @param activityId
     * @return
     * @throws DaoException
     */
    List<ActivityCrazyFoodPrizeEntity> findActivityCrazyFoodPrizeByActivityId(int activityId)
        throws DaoException;
    
    /**
     * 更新用户抽奖记录
     * @param para
     * @return
     * @throws DaoException
     */
    int updateActivityCrazyFoodRecord(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 更新奖品数量
     * @param prize
     * @return
     * @throws DaoException
     */
    int updateActivityCrazyFoodAmount(ActivityCrazyFoodPrizeEntity prize)
        throws DaoException;
    
    /**
     * 插入红包领取记录
     * @param para
     * @return
     * @throws Exception
     */
    int insertRedPacketDrawRecord(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 查询红包领取记录
     * @param para
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> findRedPacketDrawRecord(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 统计红包领取记录
     * @param para
     * @return
     * @throws Exception
     */
    int countRedPacketDrawRecord(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 插入红包分享记录
     * @param para
     * @return
     * @throws Exception
     */
    int insertRedPacketShareRecord(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 查询用户红包分享记录
     * @param shareAccountId
     * @return
     * @throws Exception
     */
    Map<String, Object> findRedPacketShareRecordByShareAccountId(int shareAccountId)
        throws DaoException;
    
    /**
     * 更新用户红包分享记录
     * @param para
     * @return
     * @throws Exception
     */
    int updateRedPacketShareRecordByShareAccountId(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 
     * @param phoneNumber :手机号
     * @param couponId：优惠券Id
     * @param type：类型
     * @return
     * @throws DaoException
     */
    int finActivityCouponPrize(String phoneNumber, int couponId, int type)
        throws DaoException;
    
    ActivityCrazyFoodRecordEntity findCrazyFoodRecord(int accountId, int activityId)
        throws DaoException;
    
    int countAccountBuyTimes(int accountId, String payTime)
        throws DaoException;
    
    ActivityCrazyFoodRecordEntity insertActivityCrazyFoodRecord(ActivityCrazyFoodRecordEntity record)
        throws DaoException;
    
    int updateActivityCrazyFoodPrize(Map<String, Object> para)
        throws DaoException;
    
}
