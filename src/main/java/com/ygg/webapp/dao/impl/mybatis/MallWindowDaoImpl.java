package com.ygg.webapp.dao.impl.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.MallWindowDaoIF;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.MallWindowEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("mallWindowDaoIF")
public class MallWindowDaoImpl extends BaseDaoImpl implements MallWindowDaoIF
{
    
    @Override
    public List<MallWindowEntity> findDisplayMallWindow()
        throws DaoException
    {
        /*
         * String sql = "SELECT id,name,image FROM mall_window WHERE is_display=1 ORDER BY sequence DESC LIMIT 5";
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); return queryAllInfo2T(sql, params);
         */
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("limitNum", 5);
        return getSqlSession().selectList("MallWindowMapper.findDisplayMallWindow",para);
    }
    
    @Override
    public MallWindowEntity findMallWindowById(int id)
        throws DaoException
    {
        /*
         * String sql = "SELECT mall_page_id,name,image FROM mall_window WHERE is_display=1 AND id=? LIMIT 1";
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, id));
         * return queryOneT(sql, params);
         */
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("id", id);
        return getSqlSession().selectOne("MallWindowMapper.findDisplayMallWindow", para);
    }
    
    @Override
    public Map<String, Object> findMallPageById(int mallPageId)
        throws DaoException
    {
        /*
         * String sql = "SELECT name FROM mall_page WHERE id=? LIMIT 1"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, mallPageId)); return queryOneMap(sql,
         * params);
         */
        return getSqlSession().selectOne("MallWindowMapper.findMallPageById", mallPageId);
    }
    
    @Override
    public List<Map<String, Object>> findAllMallPageFloorById(int mallPageId)
        throws DaoException
    {
        /*
         * String sql =
         * "SELECT id,name FROM mall_page_floor WHERE mall_page_id=? AND is_display=1 ORDER BY sequence DESC";
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * mallPageId)); return queryAllInfo2Map(sql, params);
         */
        return getSqlSession().selectList("MallWindowMapper.findAllMallPageFloorById", mallPageId);
    }
    
    @Override
    public List<Integer> findProductIdsByMallPageFloorId(int mallPageFloorId)
        throws DaoException
    {
        /*
         * List<Integer> result = new ArrayList<Integer>(); String sql =
         * "SELECT product_id FROM relation_mall_page_floor_and_product WHERE mall_page_floor_id=? ORDER BY `sequence` DESC"
         * ; List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * mallPageFloorId)); List<Map<String, Object>> infos = queryAllInfo2Map(sql, params); for (Map<String, Object>
         * info : infos) { result.add(((Long)info.get("product_id")).intValue()); } return result;
         */
        return getSqlSession().selectList("MallWindowMapper.findProductIdsByMallPageFloorId", mallPageFloorId);
    }
    
}
