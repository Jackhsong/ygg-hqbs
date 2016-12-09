package com.ygg.webapp.dao.impl.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CouponCodeEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("couponCodeDao")
public class CouponCodeDaoImpl extends BaseDaoImpl implements CouponCodeDao
{
    
    @Override
    public Map<String, Object> findCouponById(int id)
        throws DaoException
    {
        // String sql = "SELECT coupon_detail_id,start_time,end_time,remark FROM coupon WHERE id=?";
        return this.getSqlSession().selectOne("CouponCodeMapper.findCouponById", id);
    }
    
    @Override
    public Map<String, Object> findCouponCodeDetailByCode(String code)
        throws DaoException
    {
        String sql = "SELECT id,account_id,coupon_code_id,is_used FROM coupon_code_detail WHERE code=?";
        return this.getSqlSession().selectOne("CouponCodeMapper.findCouponCodeDetailByCode", code);
    }
    
    @Override
    public int updateCouponCodeDetail2Used(int accountId, int couponAccountId, int couponCodeDetailId)
        throws DaoException
    {
        String sql = "UPDATE coupon_code_detail SET is_used=1,account_id=?,coupon_account_id=? WHERE id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("couponAccountId", couponAccountId);
        params.put("couponCodeDetailId", couponCodeDetailId);
        
        return this.getSqlSession().update("CouponCodeMapper.updateCouponCodeDetail2Used", params);
    }
    
    @Override
    public CouponCodeEntity findInfoById(int couponCodeId)
        throws DaoException
    {
        String sql = "SELECT id,coupon_detail_id,type,same_max_count,start_time,end_time,remark,is_available FROM coupon_code WHERE id=?";
        
        return this.getSqlSession().selectOne("CouponCodeMapper.findInfoById", couponCodeId);
    }
    
    @Override
    public CouponCodeEntity findInfoByCommonCode(String commonCode)
        throws DaoException
    {
        String sql = "SELECT id,coupon_detail_id,type,same_max_count,start_time,end_time,remark,is_available FROM coupon_code WHERE code=? and type=2";
        
        return this.getSqlSession().selectOne("CouponCodeMapper.findInfoByCommonCode", commonCode);
    }
    
    @Override
    public int findCodeCountByAidAndId(int accountId, int couponCodeId)
        throws DaoException
    {
        String sql = "SELECT count(*) as count FROM coupon_code_detail WHERE account_id=? and coupon_code_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("couponCodeId", couponCodeId);
        
        Map<String, Object> map = this.getSqlSession().selectOne("CouponCodeMapper.findCodeCountByAidAndId", params);
        if (map == null || map.isEmpty())
            return 0;
        
        return Integer.parseInt(map.get("count").toString());
    }
    
    @Override
    public int findCommonCodeCountByAidAndId(int accountId, int couponCodeId)
        throws DaoException
    {
        String sql = "SELECT count(*) as count FROM coupon_code_common WHERE account_id=? and coupon_code_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("couponCodeId", couponCodeId);
        
        Map<String, Object> map = this.getSqlSession().selectOne("CouponCodeMapper.findCommonCodeCountByAidAndId", params);
        if (map == null || map.isEmpty())
            return 0;
        
        return Integer.parseInt(map.get("count").toString());
    }
    
    @Override
    public int addCouponCodeCommon(int accountId, int couponCodeId, int couponAccountId)
        throws DaoException
    {
        String sql = "INSERT INTO coupon_code_common(account_id,coupon_code_id,coupon_account_id) VALUES(?,?,?)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("couponCodeId", couponCodeId);
        params.put("couponAccountId", couponAccountId);
        
        return this.getSqlSession().insert("CouponCodeMapper.addCouponCodeCommon", params);
    }

    @Override
    public List<Map<String, Object>> findCouponCodeGiftBag(int couponCodeId)
        throws DaoException
    {
        return this.getSqlSession().selectList("CouponCodeMapper.findCouponCodeGiftBag", couponCodeId);
    }
}
