package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;

public interface ActivityService
{
    /**
     * 根据id查找精品特卖活动
     * @param activityId：活动Id
     * @param type：访问类型，1：网页；2：app
     * @return
     * @throws ServiceException
     */
    Map<String, Object> findActivitySimplifyDetailById(int activityId, int type)
        throws ServiceException;
    
    /**
     * 查找疯狂抽美食活动是否存在
     * @param activityId
     * @return
     * @throws ServiceException
     */
    boolean existsActivityCrazyFood(int activityId)
        throws ServiceException;
    
    /**
     * 疯狂美食抽奖
     * @param accountId
     * @param mobileNumber
     * @param activityId
     * @return
     * @throws ServiceException
     */
    Map<String, Object> draw(int accountId, String mobileNumber, int activityId)
        throws ServiceException;
    
    /**
     * 分享活动增加抽奖次数
     * @param accountId
     * @param activityId
     * @return
     * @throws ServiceException
     */
    Map<String, Object> shareActivity(int accountId, int activityId)
        throws ServiceException;
    
    /**
     * 获取疯狂抽美食抽奖次数
     * @param accountId
     * @param activityId
     * @return
     * @throws ServiceException
     */
    int getCrazyFoodActivityLeftTimes(int accountId, int activityId)
        throws ServiceException;
    
    /**
     * 获取微信登陆用户信息
     *
     * @return
     * @throws Exception
     */
    Map<String, String> getUserWinxinInfo(String code)
        throws ServiceException;
    
    /**
     * 获取 该openid对应用户的 红包领取 信息
     * @param openid 微信用户唯一标志
     * @return
     * @throws Exception
     */
    Map<String, Object> getDrawRedPacketInfo(String openid)
        throws ServiceException;
    
    /**
     * 查询 accountId用户分享的红包对应的领取记录
     * @param accountId
     * @param from 请求来源  0: app  ，1 ： wap
     * @return
     * @throws ServiceException
     */
    Map<String, Object> findRedPacketDrawInfo(int accountId, int from)
        throws ServiceException;
    
    /**
     * 红包领取 执行
     * 
     * @param accountId
     * @param mobileNumber
     * @return
     * @throws ServiceException
     */
    Map<String, Object> drawPacket(String nickname, String headimgurl, String openid, int accountId, String mobileNumber)
        throws ServiceException;
    
    /**
     * 红包分享
     * @param accountId
     * @return
     * @throws ServiceException
     */
    Map<String, Object> packetShare(int accountId)
        throws ServiceException;
    
    /**
     * 美食迎新会领取奖励
     * @param accountId
     * @param phoneNumber
     * @param requestFrom
     * @return
     * @throws ServiceException
     */
    Map<String, Object> receiveFoodPartyPrize(int accountId, String phoneNumber, String requestFrom)
        throws ServiceException;
    
    /**
     * 疯狂美食抽奖 线下推广临时抽奖
     * @param mobileNumber
     * @param activityId
     * @return
     * @throws ServiceException
     */
    Map<String, Object> drawTemp(String mobileNumber, int activityId)
        throws ServiceException;
    
    /**
     * 红色星期五领取奖励
     * @param accountId
     * @param phoneNumber
     * @param requestFrom
     * @return
     */
    Map<String, Object> receiveRedFridayPrize(int accountId, String phoneNumber, String requestFrom)
        throws ServiceException;
    
    /**
     * 查找女神节领取奖励记录
     * @param accountId
     * @return
     * @throws ServiceException
     */
    boolean findGoddessPrizeRecordByAccountId(int accountId)
        throws ServiceException;
    
    /**
     * 女神节领取奖励
     * @param accountId
     * @return
     * @throws ServiceException
     */
    String receiveGoddessPrize(int accountId, String phoneNumber, int requestFrom)
        throws ServiceException;
    
    int updateCrazyFoodActivityLeftTimes(int accountId, int activityId)
        throws ServiceException;
    
    Map<String, Object> drawCrazyFood(int accountId, int activityId)
        throws ServiceException;
    public List<Integer> findProductIdsById(int id)
            throws DaoException;
}
