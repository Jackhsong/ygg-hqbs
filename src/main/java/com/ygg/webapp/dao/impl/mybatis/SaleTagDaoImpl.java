package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.SaleTagDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.exception.DaoException;

@Repository("saleTagDao")
public class SaleTagDaoImpl extends BaseDaoImpl implements SaleTagDao
{
    
    @Override
    public List<String> findImagesByIds(List<Integer> ids)
        throws DaoException
    {
        List<String> result = new ArrayList<String>();
        if (ids.size() == 0)
        {
            return result;
        }
        // String sql = "SELECT image FROM sale_tag WHERE id IN (" + CommonUtil.changeSqlValueByLen(ids.size()) + ")";
        result = this.getSqlSession().selectList("SellerMapper.findImagesByIds", ids);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<List<String>> findImagesBySaleIds(List<Integer> saleIds)
        throws DaoException
    {
        List<List<String>> result = new ArrayList<List<String>>();
        String[] sqlArray = new String[saleIds.size()];
        /*
         * List<PstmtParam>[] paramArray = new ArrayList[saleIds.size()]; for (int i = 0; i < saleIds.size(); i++) {
         * sqlArray[i] =
         * "SELECT st.image FROM sale_tag AS st INNER JOIN (SELECT sale_tag_id FROM relation_sale_window_and_tag WHERE sale_window_id=?) AS r ON st.id=r.sale_tag_id"
         * ; List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new
         * PstmtParam(CommonEnum.FILL_PSTMT_TYPE.INT, saleIds.get(i))); paramArray[i] = params; } List<SaleTagEntity>[]
         * stess = queryBatch2T(sqlArray, paramArray);
         * 
         * for (List<SaleTagEntity> stes : stess) { List<String> images = new ArrayList<String>(); for (SaleTagEntity
         * ste : stes) { images.add(ste.getImage()); } result.add(images); }
         */
        return result;
    }
    
}
