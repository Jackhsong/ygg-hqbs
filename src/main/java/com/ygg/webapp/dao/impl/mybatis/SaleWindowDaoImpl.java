package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SaleWindowDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.SaleWindowEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonUtil;

@Repository("saleWindowDao")
public class SaleWindowDaoImpl extends BaseDaoImpl implements SaleWindowDao
{
    
    @Override
    public List<Integer> findCurrDisplayNowId()
        throws DaoException
    {
        /*
         * String sql =
         * "SELECT id FROM sale_window WHERE start_time<=? AND end_time>=? AND is_display=1 ORDER BY now_order DESC";
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); int nowSaleDate = CommonUtil.getNowSaleDate();
         * params.add(new PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, nowSaleDate)); params.add(new
         * PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, nowSaleDate)); return queryAllId(sql, params);
         */
        int nowSaleDate = CommonUtil.getNowSaleDate();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", nowSaleDate);
        params.put("endTime", nowSaleDate);
        
        List<Integer> result = this.getSqlSession().selectList("SellerMapper.findCurrDisplayNowId", params);
        return result;
    }
    
    @Override
    public List<Integer> findCurrDisplayLaterId()
        throws DaoException
    {
        /*
         * String sql =
         * "SELECT id FROM sale_window WHERE start_time=? AND end_time>=? AND is_display=1 ORDER BY later_order DESC";
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); int laterSaleDate = CommonUtil.getLaterSaleDate();
         * params.add(new PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, laterSaleDate)); params.add(new
         * PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, laterSaleDate)); return queryAllId(sql, params);
         */
        
        int laterSaleDate = CommonUtil.getLaterSaleDate();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", laterSaleDate);
        params.put("endTime", laterSaleDate);
        
        List<Integer> result = this.getSqlSession().selectList("SellerMapper.findCurrDisplayLaterId", params);
        return result;
        
    }
    
    /**
     * 查询所有的今日特买商品
     * 
     * @param nowIds
     * @return
     * @throws DaoException
     */
    public List<SaleWindowEntity> findDisplayNowInfos()
        throws DaoException
    {
        List<SaleWindowEntity> result = null;
        int nowSaleDate = CommonUtil.getNowSaleDate();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", nowSaleDate);
        params.put("endTime", nowSaleDate);
        result = this.getSqlSession().selectList("SellerMapper.findDisplayNowInfos", params);
        return result;
    }
    
    /**
     * 查询所有的即将特买商品
     * 
     * @return
     * @throws DaoException
     */
    public List<SaleWindowEntity> findDisplayLaterInfos(int saleTimeType)
        throws DaoException
    {
        List<SaleWindowEntity> result = null;
        int laterSaleDate = CommonUtil.getLaterSaleDate();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", laterSaleDate);
        params.put("endTime", laterSaleDate);
        params.put("saleTimeType", saleTimeType);
        result = this.getSqlSession().selectList("SellerMapper.findDisplayLaterInfos", params);
        return result;
    }
    
    @Override
    public List<SaleWindowEntity> findDisplayNowInfoByIds(List<Integer> nowIds)
        throws DaoException
    {
        if (nowIds.size() == 0)
        {
            return new ArrayList<SaleWindowEntity>();
        }
        /*
         * String sql = "SELECT type,display_id,image,`name`,`desc`,end_time FROM sale_window WHERE id IN (" +
         * CommonUtil.changeSqlValueByLen(nowIds.size()) + ")"; List<PstmtParam> params = new ArrayList<PstmtParam>();
         * for (Integer bannerId : nowIds) { params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, bannerId)); } return
         * queryAllInfo2T(sql, params);
         */
        List<SaleWindowEntity> result = this.getSqlSession().selectList("SellerMapper.findDisplayNowInfoByIds", nowIds);
        return result;
    }
    
    @Override
    public SaleWindowEntity findDisplayNowInfoById(int id)
        throws DaoException
    {
        List<Integer> nowIds = new ArrayList<Integer>();
        nowIds.add(id);
        List<SaleWindowEntity> reList = findDisplayNowInfoByIds(nowIds);
        return reList.isEmpty() ? null : reList.get(0);
    }

    @Override
    public List<SaleWindowEntity> findDisplayLaterInfoByIds(List<Integer> nowIds)
        throws DaoException
    {
        if (nowIds.size() == 0)
        {
            return new ArrayList<SaleWindowEntity>();
        }
        /*
         * String sql = "SELECT type,display_id,image,`name`,`desc`,start_time FROM sale_window WHERE id IN (" +
         * CommonUtil.changeSqlValueByLen(nowIds.size()) + ")"; List<PstmtParam> params = new ArrayList<PstmtParam>();
         * for (Integer bannerId : nowIds) { params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, bannerId)); } return
         * queryAllInfo2T(sql, params);
         */
        List<SaleWindowEntity> result = this.getSqlSession().selectList("SellerMapper.findDisplayLaterInfoByIds", nowIds);
        return result;
        
    }
    
    @Override
    public SaleWindowEntity findDisplayLaterInfoById(int id)
        throws DaoException
    {
        List<Integer> laterIds = new ArrayList<Integer>();
        laterIds.add(id);
        List<SaleWindowEntity> reList = findDisplayLaterInfoByIds(laterIds);
        return reList.isEmpty() ? null : reList.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<List<Integer>> findTagIdByIds(List<Integer> ids)
        throws DaoException
    {
        /*
         * List<List<Integer>> result = new ArrayList<List<Integer>>(); String[] sqlArray = new String[ids.size()];
         * List<PstmtParam>[] paramArray = new ArrayList[ids.size()]; for (int i = 0; i < ids.size(); i++) { sqlArray[i]
         * = "SELECT sale_tag_id FROM relation_sale_window_and_tag WHERE sale_window_id=?"; List<PstmtParam> params =
         * new ArrayList<PstmtParam>(); params.add(new PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, ids.get(i)));
         * paramArray[i] = params; } List<Map<String, Object>>[] queryBatchResult = queryBatch2Map(sqlArray,
         * paramArray);
         * 
         * for (List<Map<String, Object>> list : queryBatchResult) { List<Integer> idList = new ArrayList<>(); for
         * (Map<String, Object> map : list) { idList.add(((Long)map.get("sale_tag_id")).intValue()); }
         * result.add(idList); } return result;
         */
        return null;
    }
    
    @Override
    public SaleWindowEntity findSaleInfoById(int id)
        throws DaoException
    {
        // String sql = "SELECT start_time,end_time FROM sale_window WHERE id=? LIMIT 1";
        SaleWindowEntity se = this.getSqlSession().selectOne("SellerMapper.findSaleInfoById", id);
        return se;
    }
    
    @Override
    public SaleWindowEntity findSaleInfoByDisplayId(int displayId, byte type)
        throws DaoException
    {
        /*
         * String sql = "SELECT start_time,end_time FROM sale_window WHERE display_id=? AND type=? LIMIT 1";
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * displayId)); params.add(new PstmtParam(FILL_PSTMT_TYPE.BYTE, type)); return queryOneInfo(sql, params);
         */
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("displayId", displayId);
        params.put("type", type);
        SaleWindowEntity se = this.getSqlSession().selectOne("SellerMapper.findSaleInfoByDisplayId", params);
        
        return se;
        
    }
    
    @Override
    public List<Map<String, Object>> findStartEndTimeById(List<Integer> saleWindowId)
        throws DaoException
    {
        List<Map<String, Object>> map = this.getSqlSession().selectList("SellerMapper.findStartEndTimeById", saleWindowId);
        return map;
    }

    @Override
    public List<Integer> findTodayDisplayId(int saleTimeType)
        throws DaoException
    {
        Map<String,Object> para = new HashMap<>();
        para.put("saleTimeType", saleTimeType);
        para.put("startTime", Integer.valueOf(DateTime.now().toString("yyyyMMdd")));
        return getSqlSession().selectList("SellerMapper.findTodayDisplayId", para);
    }

    @Override
    public List<SaleWindowEntity> findTodayDisplayInfos(int saleTimeType)
        throws DaoException
    {
        Map<String,Object> para = new HashMap<>();
        para.put("saleTimeType", saleTimeType);
        para.put("startTime", Integer.valueOf(DateTime.now().toString("yyyyMMdd")));
        return getSqlSession().selectList("SellerMapper.findTodayDisplayInfos", para);
    }

    @Override
    public List<SaleWindowEntity> findCurrDisplayNowIdWhereIdNotIn(List<Integer> idList)
        throws DaoException
    {
        if (idList.size() == 0)
        {
            idList.add(0);
        }
        int nowSaleDate = CommonUtil.getNowSaleDate();
        Map<String,Object> para = new HashMap<>();
        para.put("idList", idList);
        para.put("nowSaleDate", nowSaleDate);
        return getSqlSession().selectList("SellerMapper.findCurrDisplayNowIdWhereIdNotIn", para);
    }

}
