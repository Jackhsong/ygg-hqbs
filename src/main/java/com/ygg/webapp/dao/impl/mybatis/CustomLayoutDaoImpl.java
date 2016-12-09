package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CustomLayoutDaoIF;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CustomLayoutEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("customLayoutDaoIF")
public class CustomLayoutDaoImpl extends BaseDaoImpl implements CustomLayoutDaoIF
{
    
    @Override
    public List<CustomLayoutEntity> findCustomLayoutsByLayoutIds(List<Integer> customLayoutIds)
        throws DaoException
    {
        // String sql =
        // "SELECT display_type,one_image,one_type,one_display_id,one_remark,one_width,one_height,two_image,two_type,two_display_id,two_remark,two_width,two_height FROM custom_layout WHERE is_display=1 AND id IN("
        // + CommonUtil.changeSqlValueByLen(customLayoutIds.size())
        // + ")"
        // + "ORDER BY FIELD(id,"
        // + CommonUtil.changeSqlValueByLen(customLayoutIds.size())
        // + ")";
        if (customLayoutIds.size() == 0)
        {
            return new ArrayList<CustomLayoutEntity>();
        }
        List<CustomLayoutEntity> infos = getSqlSession().selectList("CustomLayoutMapper.findCustomLayoutsByLayoutIds", customLayoutIds);
        if (infos != null && !infos.isEmpty())
        {
            List<CustomLayoutEntity> curInfos = infos;
            infos = new ArrayList<CustomLayoutEntity>();
            for (int i : customLayoutIds)
            {
                for (CustomLayoutEntity it : curInfos)
                {
                    if (i == it.getId())
                    {
                        infos.add(it);
                        break;
                    }
                }
            }
        }
        return infos;
    }
    
    @Override
    public List<Map<String, Object>> findDisplayCustomRegion()
        throws DaoException
    {
        // String sql = "SELECT title,id FROM custom_region WHERE is_display=1 ORDER BY sequence DESC";
        // List<PstmtParam> params = new ArrayList<PstmtParam>();
        // return queryAllInfo2Map(sql, params);
        return getSqlSession().selectList("CustomLayoutMapper.findDisplayCustomRegion");
    }
    
    @Override
    public List<Integer> findLayoutIdByRegionId(int customRegionId)
        throws DaoException
    {
        // String sql =
        // "SELECT custom_layout_id FROM relation_custom_region_layout WHERE custom_region_id=? ORDER BY `order` DESC";
        return getSqlSession().selectList("CustomLayoutMapper.findLayoutIdByRegionId", customRegionId);
    }
    
}
