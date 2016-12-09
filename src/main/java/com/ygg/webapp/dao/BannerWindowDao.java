package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.BannerWindowEntity;
import com.ygg.webapp.exception.DaoException;

public interface BannerWindowDao
{
    
    /**
     * 查询当前展示的BannerId
     *
     * @return
     */
    List<Integer> findCurrDisplayBannerId()
        throws DaoException;
    
    /**
     * 根据id列表查询对应的Banner详细信息
     *
     * @return
     */
    List<BannerWindowEntity> findDisplayBannerInfoByIds(List<Integer> bannerIds)
        throws DaoException;
    
    BannerWindowEntity findDisplayBannerInfoById(int id)
        throws DaoException;

    /**
     * 查出所有可显示的banner信息
     * 
     * @param bannerIds
     * @return
     * @throws DaoException
     */
    List<BannerWindowEntity> findDisplayBannersInfo()
        throws DaoException;
    
    /**
     * 根据id查询对应的Banner详细信息
     *
     * @return
     */
    BannerWindowEntity findBannerInfoById(int id)
        throws DaoException;
}