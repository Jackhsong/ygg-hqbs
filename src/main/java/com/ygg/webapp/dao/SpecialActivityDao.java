package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.SpecialActivityEntity;
import com.ygg.webapp.entity.SpecialActivityGroupEntity;
import com.ygg.webapp.entity.SpecialActivityModelEntity;
import com.ygg.webapp.entity.SpecialActivityModelLayoutEntity;
import com.ygg.webapp.entity.SpecialActivityModelLayoutProductEntity;
import com.ygg.webapp.entity.SpecialActivityNewEntity;
import com.ygg.webapp.exception.DaoException;

public interface SpecialActivityDao
{
    /**
     * 根据Id查询自定义活动
     * @param activityId
     * @return
     * @throws DaoException
     */
    SpecialActivityEntity findSpecialActivityById(int activityId)
        throws DaoException;
    
    /**
     * 根据特卖活动Id查询特卖活动板块
     * @param activityId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findSpecialActivityLayout(int activityId)
        throws DaoException;
    
    /**
     * 根据板块Id查询商品布局
     * @param layouId
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findSpecialActivityLayouProduct(int layouId)
        throws DaoException;
    
    /**
     * 根据ID查询新情景专场活动
     * @param id
     * @return
     * @throws DaoException
     */
    SpecialActivityNewEntity findSpecialActivityNewById(int id)
        throws DaoException;
    
    /**
     * 根据新情景活动ID 和 类型 查询新情景专场活动商品信息
     * @param actId
     * @param type
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findSpecialActivityNewProductByActIdAndType(int actId, int type)
        throws DaoException;
    
    /**
     *
     * @param actId
     * @param type
     * @return
     * @throws DaoException
     */
    int countSpecialActivityNewProductByActIdAndType(int actId, int type)
        throws DaoException;
    
    SpecialActivityGroupEntity findSpecialActivityGroupById(int id)
        throws DaoException;
    
    /**
     * 根据活动Id和类型查找商品
     * @param id
     * @param type：1：楼层，2：更多商品
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findSpecialActivityGroupProductByActIdAndType(int id, int type)
        throws DaoException;
    
    SpecialActivityModelEntity findSpecialActivityModelById(int id)
        throws DaoException;
    
    List<SpecialActivityModelLayoutEntity> findSpecialActivityModelLayoutsBysamId(int id)
        throws DaoException;
    
    List<SpecialActivityModelLayoutProductEntity> findSpecialActivityModelLayoutProductsByLayoutId(int id)
        throws DaoException;
}
