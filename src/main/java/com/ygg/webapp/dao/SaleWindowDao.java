package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.SaleWindowEntity;
import com.ygg.webapp.exception.DaoException;

public interface SaleWindowDao
{
    
    /**
     * 查询当前展示的今日特卖Id
     *
     * @return
     */
    List<Integer> findCurrDisplayNowId()
        throws DaoException;
    
    /**
     * 
     *
     * @return
     */
    List<Integer> findCurrDisplayLaterId()
        throws DaoException;
    
    
    /**
     * 根据id列表查询对应的今日特卖详细信息
     *
     * @return
     */
    List<SaleWindowEntity> findDisplayNowInfoByIds(List<Integer> nowIds)
        throws DaoException;
    
    /**
     * 
     */
    SaleWindowEntity findDisplayNowInfoById(int id)
        throws DaoException;
    
    /**
     * 查询所有的今日特买商品
     * 
     * @param nowIds
     * @return
     * @throws DaoException
     */
    List<SaleWindowEntity> findDisplayNowInfos()
        throws DaoException;

    /**
     * 查询今日开始的特卖ID
     *
     * @param saleTimeType 1:早场;2:晚场
     * @return
     * @throws DaoException
     */
    List<Integer> findTodayDisplayId(int saleTimeType)
        throws DaoException;

    /**
     * 查询今日开始的特卖信息
     *
     * @param saleTimeType 1:早场;2:晚场
     * @return
     * @throws DaoException
     */
    List<SaleWindowEntity> findTodayDisplayInfos(int saleTimeType)
        throws DaoException;

    /**
     * 查询当前晚场展示的今日特卖Id
     *
     * @return
     */
    List<SaleWindowEntity> findCurrDisplayNowIdWhereIdNotIn(List<Integer> idList)
        throws DaoException;
    
    /**
     * 查询所有的即将特买商品
     * 
     * @return
     * @throws DaoException
     */
    List<SaleWindowEntity> findDisplayLaterInfos(int saleTimeType)
        throws DaoException;
    
    SaleWindowEntity findDisplayLaterInfoById(int id)
        throws DaoException;

    /**
     * 根据id列表查询对应的即将开始详细信息
     *
     * @return
     */
    List<SaleWindowEntity> findDisplayLaterInfoByIds(List<Integer> nowIds)
        throws DaoException;
    
    /**
     * 根据id列表查询对应的标签id
     *
     * @return
     */
    List<List<Integer>> findTagIdByIds(List<Integer> ids)
        throws DaoException;
    
    /**
     * 根据id查询特卖信息
     *
     * @return
     */
    SaleWindowEntity findSaleInfoById(int id)
        throws DaoException;
    
    /**
     * 根据展示id和类型查询特卖信息
     *
     * @return
     */
    SaleWindowEntity findSaleInfoByDisplayId(int displayId, byte type)
        throws DaoException;
    
    /**
     * 查询今日和即将特买的时间
     * 
     * @param saleWindowId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findStartEndTimeById(List<Integer> saleWindowId)
        throws DaoException;
}