package com.ygg.webapp.dao;

import com.ygg.webapp.entity.ActivitiesOrderGiftEntity;
import com.ygg.webapp.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface OrderGiftShareDao
{

    /**
     * 插入 订单红包领取  -  微信和手机号对应关系
     * @param mobileNumber
     * @param wxOpenId
     * @return
     * @throws DaoException
     */
    int addMobileByWeixinOpenId(String wxOpenId, String mobileNumber)
        throws DaoException;

    int deleteMobile(String mobileNumber)
        throws DaoException;

    /**
     * 查询订单红包领取  -  微信和手机号对应关系
     * @param weixinOpenid 微信openId
     * @return
     * @throws DaoException
     */
    String findMobileByWeixinOpenId(String weixinOpenid)
        throws DaoException;

    /**
     * 查询订单红包领取 领取记录
     * @param wxId
     * @param giftId
     * @return
     * @throws DaoException
     */
    Map<String, Object> findRecordByWXIdAndGiftId(String wxId, int giftId)
        throws DaoException;

    /**
     * 查询订单红包领取 领取记录
     * @param mobileNumber
     * @param giftId
     * @return
     * @throws DaoException
     */
    Map<String, Object> findRecordByMobileAndGiftId(String mobileNumber, int giftId)
        throws DaoException;

    /**
     * 查询订单所有红包领取 领取记录
     * @param giftId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findRecordByGiftId(int giftId)
        throws DaoException;

    /**
     * 根据ID查询订单红包信息
     * @param id
     * @return
     * @throws DaoException
     */
    ActivitiesOrderGiftEntity findActivitiesOrderGiftById(int id)
        throws DaoException;

    /**
     * 插入订单红包领取记录
     * @param para
     * @return
     * @throws DaoException
     */
    int insertActivitiesOrderGiftRecord(Map<String,Object> para)
        throws DaoException;

    /**
     * 更新订单所有红包信息
     * @param para
     * @return
     * @throws DaoException
     */
    int updateActivitiesOrderGiftById(Map<String,Object> para)
        throws DaoException;

    /**
     * 修改订单领取手机号
     * @param wxOpenId
     * @param mobileNumber
     * @return
     * @throws DaoException
     */
    int updateMobilePhone(String wxOpenId, String mobileNumber)
        throws DaoException;
}
