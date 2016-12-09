package com.ygg.webapp.service;

import java.util.Map;

import com.ygg.webapp.entity.GateActivityEntity;
import com.ygg.webapp.entity.GatePrizeEntity;
import com.ygg.webapp.exception.ServiceException;

public interface GateActivityService
{
    
    public GateActivityEntity findGateActivity(Map<String, Object> para)
        throws ServiceException;
    
    public GatePrizeEntity findGatePrizeByGateId(int id)
        throws ServiceException;
    
    /**
     * web领取奖励页面
     * @param mobileNumber
     * @param gateId
     * @return 0：服务器发生异常；1：领取成功；2：已经领取；3：手机号不存在；4：奖品不存在；
     * @throws ServiceException
     */
    public Map<String, Object> receivePrize(String mobileNumber, int gateId)
        throws ServiceException;
    
    /**
     * app开门、领取奖励页面
     * 
     * @param para
     * @return 0：服务器发生异常；1：领取成功；2：已经领取；3：用户不存在；4：奖品不存在；5：口令错误；
     * @throws ServiceException
     */
    public Map<String, Object> appOpenDoor(Map<String, Object> para)
        throws ServiceException;
    
    /**
     * 查找下一次开门
     * @param para
     * @return
     * @throws ServiceException
     */
    public GateActivityEntity findNextOpenGateActivity(Map<String, Object> para)
        throws ServiceException;
    
    /**
     * web页面开门
     * @param para
     * @return
     * @throws ServiceException
     */
    public Map<String, Object> webOpenDoor(Map<String, Object> para)
        throws ServiceException;
    
}
