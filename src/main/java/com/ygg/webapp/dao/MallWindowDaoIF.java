package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.MallWindowEntity;
import com.ygg.webapp.exception.DaoException;

public interface MallWindowDaoIF
{
    /**
     * 查询当前展示的商城位信息
     * 
     * @return
     */
    List<MallWindowEntity> findDisplayMallWindow()
        throws DaoException;
    
    /**
     * 根据id查询对应的商城位信息
     * 
     * @return
     */
    MallWindowEntity findMallWindowById(int id)
        throws DaoException;
    
    /**
     * 根据id查询对应的商城页面信息
     * 
     * @return
     */
    Map<String, Object> findMallPageById(int mallPageId)
        throws DaoException;
    
    /**
     * 根据id查询全部的商城页面楼层信息
     * 
     * @return
     */
    List<Map<String, Object>> findAllMallPageFloorById(int mallPageId)
        throws DaoException;
    
    /**
     * 根据商城页面楼层id查询关联的商品
     * 
     * @return
     */
    List<Integer> findProductIdsByMallPageFloorId(int mallPageFloorId)
        throws DaoException;
}
