package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.exception.DaoException;

@Repository("couponAccountDao")
public class CouponAccountDaoImpl extends BaseDaoImpl implements CouponAccountDao
{
    
    @Override
    public int addCouponAccount(CouponAccountEntity cae)
        throws DaoException
    {
        // String sql =
        // "INSERT INTO coupon_account(account_id,coupon_id,coupon_detail_id,start_time,end_time,remark,create_time,source_type,coupon_code_id) VALUES(?,?,?,?,?,?,?,?,?)";
        cae.setCreateTime(new Timestamp(System.currentTimeMillis()).toString());
        return this.getSqlSession().insert("CouponAccountMapper.addCouponAccount", cae);
    }
    
    @Override
    public CouponAccountEntity findCouponsByAidAndId(int id, int accountId)
        throws DaoException
    {
        String sql = "SELECT coupon_detail_id,start_time,end_time,remark,id,is_used FROM coupon_account WHERE id=? and account_id=?";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("accountId", accountId);
        
        return this.getSqlSession().selectOne("CouponAccountMapper.findCouponsByAidAndId", params);
    }
    
    @Override
    public List<CouponAccountEntity> findUnusedCouponsByAid(int accountId)
        throws DaoException
    {
        String sql = "SELECT coupon_detail_id,start_time,end_time,remark,id FROM coupon_account WHERE account_id=? and is_used=0 and end_time>=CURDATE() ORDER BY end_time ASC";
        
        List<CouponAccountEntity> list = this.getSqlSession().selectList("CouponAccountMapper.findUnusedCouponsByAid", accountId);
        if (list == null)
            list = new ArrayList<CouponAccountEntity>();
        
        return list;
    }
    
    @Override
    public List<CouponAccountEntity> findUsedCouponsByAid(int accountId)
        throws DaoException
    {
        String sql = "SELECT coupon_detail_id,start_time,end_time,remark,id FROM coupon_account WHERE account_id=? and is_used=1 ORDER BY update_time DESC LIMIT 30";
        
        List<CouponAccountEntity> list = this.getSqlSession().selectList("CouponAccountMapper.findUsedCouponsByAid", accountId);
        if (list == null)
            list = new ArrayList<CouponAccountEntity>();
        
        return list;
    }
    
    @Override
    public List<CouponAccountEntity> findExpiredCouponsByAid(int accountId)
        throws DaoException
    {
        String sql =
            "SELECT coupon_detail_id,start_time,end_time,remark,id FROM coupon_account WHERE account_id=? and is_used=0 and end_time<CURDATE() ORDER BY end_time DESC LIMIT 30";
        
        List<CouponAccountEntity> list = this.getSqlSession().selectList("CouponAccountMapper.findExpiredCouponsByAid", accountId);
        if (list == null)
            list = new ArrayList<CouponAccountEntity>();
        
        return list;
    }
    
    @Override
    public int updateCouponAccount2Used(int id)
        throws DaoException
    {
        String sql = "UPDATE coupon_account SET is_used=1 WHERE id=?";
        return this.getSqlSession().update("CouponAccountMapper.updateCouponAccount2Used", id);
    }
    
    @Override
    public CouponAccountEntity findCouponInfoBycidAndType(int accountId, int couponId, int type)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", accountId);
        para.put("couponId", couponId);
        para.put("type", type);
        CouponAccountEntity cae = this.getSqlSession().selectOne("CouponAccountMapper.findCouponInfoBycidAndType", para);
        return cae;
    }
    
    @Override
    public int updateCouponAccount2UnUsed(int id)
        throws DaoException
    {
        return this.getSqlSession().update("CouponAccountMapper.updateCouponAccount2UnUsed", id);
    }
    
    @Override
    public List<CouponAccountEntity> findAllCouponInfoBycidAndType(int accountId, int couponId, int sourceType)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", accountId);
        para.put("couponId", couponId);
        para.put("type", sourceType);
        return getSqlSession().selectList("CouponAccountMapper.findAllCouponInfoBycidAndType", para);
    }
    
    @Override
    public CouponAccountEntity findCouponAccountById(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("CouponAccountMapper.findCouponAccountById", id);
    }
}
