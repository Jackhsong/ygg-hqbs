package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.AccountRecommendedReturnPointEntity;
import com.ygg.webapp.entity.RegisterMobileCouponEntity;
import com.ygg.webapp.exception.DaoException;

public interface AccountDao
{
    
    /**
     * 根据账号名和账号类型查询对应的用户id
     *
     * @return
     */
    int findIdByNameAndType(String name, byte type)
        throws DaoException;
    
    AccountEntity findByNameAndType(String name, byte type)
        throws DaoException;
    
    /**
     * 根据用户id查询对应的用户信息
     *
     * @return
     */
    AccountEntity findAccountById(int id)
        throws DaoException;
    
    /**
     * 新增用户
     *
     * @return
     */
    int addAccount(AccountEntity ae);
    
    /**
     * 更新用户
     *
     * @return
     */
    int updateAccountById(AccountEntity ae)
        throws DaoException;
    
    /**
     * 更新用户部分信息
     * @param para
     * @return
     * @throws DaoException
     */
    int updateAccountInfoById(Map<String, Object> para)
        throws DaoException;
    
    /**
     * 根据密码查询用户信息
     *
     * @return
     */
    AccountEntity findAccountByPwd(String pwd)
        throws DaoException;
    
    /**
     * 根据账号名查找对应的注册优惠券id列表
     * 
     * @return
     */
    List<Map<String, Object>> findRegisterCouponIdByName(String name)
        throws DaoException;
    
    /**
     * 根据账号名删除注册优惠券
     * 
     * @return
     */
    int deleteRegisterCouponByName(String name)
        throws DaoException;
    
    /**
     * 根据账号id查询上级合伙人id
     * 
     * @return
     */
    int findFatherPartnerAccountIdById(int id)
        throws DaoException;
    
    /**
     * 根据推荐码查询对应的用户信息
     * 
     * @return
     */
    AccountEntity findAccountByRecommendedCode(String recommendedCode)
        throws DaoException;
    
    /**
     * 新增账号推荐关系
     * 
     * @return
     */
    int addAccountRecommendRelation(int currAccountId, int fatherAccountId)
        throws DaoException;
    
    /**
     * 新增账号合伙人关系
     * 
     * @return
     */
    int addAccountPartnerRelation(int currAccountId, int fatherAccountId)
        throws DaoException;
    
    /**
     * 根据账号id查询上级推荐人id
     * 
     * @return
     */
    int findFatherRecommendedAccountIdById(int id)
        throws DaoException;
    
    /**
     * 新增用户邀请返积分
     * 
     * @return
     */
    int addAccountRecommendedReturnPoint(AccountRecommendedReturnPointEntity arrpe)
        throws DaoException;
    
    /**
     * 根据新注册手机号查询账号预推荐关系
     * 
     * @param mobileNumber
     * @return
     * @throws DaoException
     */
    int findFatherAccountIdByMobileNumber(String mobileNumber)
        throws DaoException;
    
    int insertAccountPrepRecommendRelation(Map<String, Object> para)
        throws DaoException;
    
    int deleteAccountPrepRecommendRelation(String mobileNumber)
        throws DaoException;
    
    int countPrepRecommended(Map<String, Object> para)
        throws DaoException;
    
    int addRegisterMobileCoupon(String mobileNumber, int couponId, int validityDaysType, int days, int sourceType, int reducePrice)
        throws DaoException;
    
    String findAccountNameById(int id)
        throws DaoException;
    
    /**
     * 根据用户id查询对应的收藏特卖id
     * 
     * @return
     */
    List<Integer> findCollectSaleIdsByAid(int accountId)
        throws DaoException;
    
    /**
     * 统计用户总消费金额
     * 
     * @param accountId
     * @return
     * @throws DaoException
     */
    Map<String, Object> countAccountTotalOrderRealPrice(int accountId)
        throws DaoException;
    
    /**
     * 查询当前合伙人的上级合伙人 培养合伙人 信息
     *
     * @param currAccountId 当前合伙人
     * @return
     * @throws DaoException
     */
    Map<String, Object> findPartnerTrainQuantityInfo(int currAccountId)
        throws DaoException;
    
    /**
     * 是否 黑名单用户
     * @param accountId
     * @return
     * @throws DaoException
     */
    boolean isAccountBlack(int accountId)
        throws DaoException;
    
    /**
     * 查找环信帐号ID
     * @param username
     * @return
     * @throws DaoException
     */
    int findHuanXinInfoByName(String username)
        throws DaoException;
    
    /**
     * 新增环信帐号
     * @param username
     * @return
     * @throws DaoException
     */
    int addHuanXinInfo(String username)
        throws DaoException;
    
    /**
     * 查找用户预注册优惠券
     * @param para
     * @return
     * @throws DaoException
     */
    List<RegisterMobileCouponEntity> findRegisterMobileCoupon(Map<String, Object> para)
        throws DaoException;
    
    int updateIntegral(Map<String, Object> para)
        throws DaoException;
    
    int insertIntegralRecord(Map<String, Object> para)
        throws DaoException;
}