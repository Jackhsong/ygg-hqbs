package com.ygg.webapp.service;

import com.ygg.webapp.entity.ActivitiesOrderGiftEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface OrderGiftShareService
{

    /**
     * 获取 该openid对应用户的 订单红包领取 信息
     * @param openid 微信用户唯一标志
     * @return
     * @throws Exception
     */
    Map<String, Object> findOrderGiftReceiveInfo(int giftId, String openid)
        throws ServiceException;

    /**
     * 获取订单红包领取记录
     * @param giftId
     * @return
     * @throws ServiceException
     */
    List<Map<String, Object>> findRecordByGiftId(int giftId)
        throws ServiceException;

    /**
     * 红包领取 执行
     *
     * @param nickname  微信昵称
     * @param headimgurl 微信头像
     * @param openid 微信openId
     * @param giftId 订单红包ID
     * @param mobileNumber 领取手机号
     * @return
     * @throws ServiceException
     */
    Map<String, Object> drawOrderGift(String nickname, String headimgurl, String openid, int giftId, String mobileNumber)
        throws ServiceException;

    /**
     * 根据ID查询订单红包信息
     * @param id
     * @return
     * @throws DaoException
     */
    ActivitiesOrderGiftEntity findActivitiesOrderGiftById(int id)
        throws DaoException;

    /**
     * 修改订单红包领取手机号
     * @param wxOpenId
     * @param mobileNumber
     * @return
     * @throws ServiceException
     */
    Map<String, Object> updateMobilePhone(String wxOpenId, String mobileNumber)
        throws ServiceException;
}
