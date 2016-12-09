package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.GroupAccountInfoEntity;
import com.ygg.webapp.entity.GroupProductCodeEntity;
import com.ygg.webapp.exception.DaoException;

public interface GroupBuyDao
{
    /**
     * 根据ID 查询 团购商品
     * 
     * @param id
     * @return
     * @throws DaoException
     */
    GroupProductCodeEntity findGroupProductCodeById(int id)
        throws DaoException;
    
    /**
     * 根据参数查询团购商品
     * 
     * @param para
     * @return
     * @throws DaoException
     */
    List<GroupProductCodeEntity> findGroupProdutCodeByPara(Map<String, Object> para)
        throws DaoException;
        
    /**
     * 根据参数查询团购商品账号记录表
     * 
     * @param para
     * @return
     * @throws DaoException
     */
    List<GroupAccountInfoEntity> findGroupAccountInfoByPara(Map<String, Object> para)
        throws DaoException;
        
    /**
     * 根据ID 查询 团购商品账号记录
     * 
     * @param groupProductCodeId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findGroupAccountInfoByGroupProductCodeId(int groupProductCodeId)
        throws DaoException;
        
}
