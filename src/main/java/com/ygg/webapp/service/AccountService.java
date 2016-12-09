package com.ygg.webapp.service;

import java.util.Map;

import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.view.AccountView;

public interface AccountService
{
    
    /**
     * 获取短信验证码
     *
     * @param request
     * @return
     */
    String verificationCode(String requestParams)
        throws Exception;
    
    /**
     * 注册账号
     *
     * @param request
     * @return
     */
    AccountView register(String requestParams)
        throws Exception;
    
    /**
     * 登录
     *
     * @param request
     * @return
     */
    AccountView login(String requestParams)
        throws Exception;
    
    /**
     * 重置密码
     *
     * @param request
     * @return
     */
    String resetPwd(String requestParams)
        throws Exception;
    
    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    String modifyPwd(String requestParams)
        throws Exception;
    
    /**
     * 获取用户信息
     * 
     * @param id
     * @return
     */
    String getUserInfo(Integer id)
        throws Exception;
    
    /**
     * 用户推荐
     * 
     * @return
     * @throws Exception
     */
    public Map<String, Object> recommend(String mobileNumber, String code)
        throws Exception;
    
    /**
     * 根据邀请码检查用户是否存在
     * 
     * @param para
     * @return
     * @throws Exception
     */
    boolean checkIfExistsRecommendedCode(String recommendedCode)
        throws Exception;
    
    /**
     * 统计小伙伴邀请次数
     * @param recommendedCode
     * @return
     * @throws Exception
     */
    int countPrepRecommended(String recommendedCode, String begin, String end)
        throws Exception;
    
    boolean existsAccount(int id)
        throws Exception;
    
    /**
     * 查询账户信息
     * @param id
     * @return
     * @throws Exception
     */
    AccountEntity findAccountById(int id)
        throws Exception;
    
    /**
     * 查找环信帐号信息
     * @param username
     * @return
     * @throws Exception
     */
    int findHuanXinInfoByName(String username)
        throws Exception;
    
    /**
     * 添加环信帐号
     * @param username
     * @return
     * @throws Exception
     */
    int addHuanXinInfo(String username)
        throws Exception;
}
