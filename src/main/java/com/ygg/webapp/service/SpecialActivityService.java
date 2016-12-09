package com.ygg.webapp.service;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.exception.ServiceException;

public interface SpecialActivityService
{
    /**
     * 访问特卖活动页面
     * @param activityId：特卖活动Id
     * @param type：访问类型，1：wap,2:app
     * @return
     * @throws ServiceException
     */
    Map<String, Object> findSpecialActivityDetailById(int activityId, int type)
        throws ServiceException;
    
    /**
     * 查询新情景特卖信息
     * @param specialActivityNewId
     * @param type
     * @param client 1 : wap ; 2: app
     * @return
     * @throws ServiceException
     */
    Map<String, Object> findSpecialActivityNewById(int specialActivityNewId, int type, int client)
        throws ServiceException;
    
    /**
     * 查询新情景特卖更多商品信息
     * @param specialActivityNewId
     * @param type
     * @param client 1 : wap ; 2: app
     * @return
     * @throws ServiceException
     */
    List<Map<String, Object>> findSpecialActivityNewMoreProductById(int specialActivityNewId, int type, int client)
        throws ServiceException;
    
    /**
     * 查询组合情景特卖信息
     * @param id：特卖活动Id
     * @param clientType：客户端类型，1：wap；2：app
     * @return
     * @throws ServiceException
     */
    Map<String, Object> findSpecialActivityGroupById(int id, int clientType)
        throws ServiceException;
    
    /**
     * 查询情景模版信息
     * @param id：id
     * @return
     * @throws ServiceException
     */
    Map<String, Object> findSpecialActivityModelById(int id)
        throws ServiceException;
    
    String findSpecialActivityModelLayoutProductByIdAndType(int activityId, int type)
        throws ServiceException;
}
