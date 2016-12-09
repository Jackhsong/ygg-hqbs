package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.BannerWindowDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.BannerWindowEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("bannerWindowDao")
public class BannerWindowDaoImpl extends BaseDaoImpl implements BannerWindowDao
{
    @Override
    public List<Integer> findCurrDisplayBannerId()
        throws DaoException
    {
        // String sql =
        // "SELECT id FROM banner_window WHERE start_time<? AND end_time>? AND is_display=1 ORDER BY `order` DESC";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.TIMESTAMP,
         * new Timestamp(System.currentTimeMillis()))); params.add(new PstmtParam(FILL_PSTMT_TYPE.TIMESTAMP, new
         * Timestamp(System.currentTimeMillis()))); return queryAllId(sql, params);
         */
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", new Timestamp(System.currentTimeMillis()));
        params.put("endTime", new Timestamp(System.currentTimeMillis()));
        List<Integer> currdisplayBannerIds = this.getSqlSession().selectList("BannerWindowMapper.findCurrDisplayBannerId", params);
        return currdisplayBannerIds;
    }
    
    @Override
    public List<BannerWindowEntity> findDisplayBannerInfoByIds(List<Integer> bannerIds)
        throws DaoException
    {
        if (bannerIds.size() == 0)
        {
            return new ArrayList<BannerWindowEntity>();
        }
        List<BannerWindowEntity> list = this.getSqlSession().selectList("BannerWindowMapper.findBannerInfoById", bannerIds);
        return list;
        
    }
    
    @Override
    public BannerWindowEntity findDisplayBannerInfoById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("BannerWindowMapper.findDisplayBannerInfoById", id);
    }

    @Override
    public List<BannerWindowEntity> findDisplayBannersInfo()
        throws DaoException
    {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", new Timestamp(System.currentTimeMillis()));
        params.put("endTime", new Timestamp(System.currentTimeMillis()));
        
        List<BannerWindowEntity> list = this.getSqlSession().selectList("BannerWindowMapper.findDisplayBannersInfo", params);
        if (list == null || list.isEmpty())
            return new ArrayList<BannerWindowEntity>();
        
        return list;
    }
    
    @Override
    public BannerWindowEntity findBannerInfoById(int id)
        throws DaoException
    {
        return (BannerWindowEntity)this.getSqlSession().selectOne("BannerWindowMapper.findBannerInfoById", id);
    }
    
}
