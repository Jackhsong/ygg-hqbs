package com.ygg.webapp.service;

import java.util.Map;

public interface LotteryActivityService
{
    /**
     * 检查是否存在该可用抽奖活动
     * @param lotteryId
     * @return
     * @throws Exception
     */
    boolean existsLotteryActivity(int lotteryId)
        throws Exception;
    
    /**
     * 执行抽奖活动 doAction
     * 
     * @param mobileNumber
     * @param lotteryId
     * @return
     * @throws Exception
     */
    Map<String, Object> draw(int accountId, String mobileNumber, int lotteryId)
        throws Exception;
    
    /**
     * 获取用户剩余抽奖机会
     * @param lotteryId
     * @param accountId
     * @return
     * @throws Exception
     */
    int chance(int lotteryId, int accountId)
        throws Exception;
    
    /**
     * 分享活动 触发事件
     * @param accountId
     * @param lotteryId
     * @return
     * @throws Exception
     */
    Map<String, Object> shareActivity(int accountId, int lotteryId)
        throws Exception;
    
    /**
     * 礼包分享 触发事件
     * @param accountId
     * @param giftActivityId
     * @return
     * @throws Exception
     */
    Map<String, Object> giftAppShare(int accountId, int giftActivityId)
        throws Exception;
    
    /**
     * 按手机号领取礼包
     * @param mobileNubmer
     * @param giftActivityId
     * @return
     * @throws Exception
     */
    Map<String, Object> giftDrew(String mobileNumber, int giftActivityId)
        throws Exception;
    
    /**
     * 是否存在该礼包活动
     * @param giftId
     * @return
     * @throws Exception
     */
    boolean existsGiftActivity(int giftId)
        throws Exception;
    
}
