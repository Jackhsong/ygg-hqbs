package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.LogisticsDetailDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.LogisticsDetailEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("logisticsDetailDao")
public class LogisticsDetailDaoImpl extends BaseDaoImpl implements LogisticsDetailDao
{
    
    @Override
    public List<LogisticsDetailEntity> findDetailByChannelAndNumber(String channel, String number)
        throws DaoException
    {
        String sql = "SELECT operate_time,`content` FROM logistics_detail WHERE logistics_channel=? AND logistics_number=? ORDER BY operate_time DESC";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.STRING,
         * channel)); params.add(new PstmtParam(FILL_PSTMT_TYPE.STRING, number)); return queryAllInfo2T(sql, params);
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("channel", channel);
        map.put("number", number);
        List<LogisticsDetailEntity> list = this.getSqlSession().selectList("LogisticsMapper.findDetailByChannelAndNumber", map);
        if (list == null || list.size() == 0)
            list = new ArrayList<LogisticsDetailEntity>();
        return list;
    }
    
}
