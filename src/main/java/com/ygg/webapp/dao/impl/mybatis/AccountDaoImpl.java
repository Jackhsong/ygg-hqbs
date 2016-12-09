package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.AccountRecommendedReturnPointEntity;
import com.ygg.webapp.entity.RegisterMobileCouponEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("accountDao")
public class AccountDaoImpl extends BaseDaoImpl implements AccountDao
{
    
    @Override
    public int addAccount(AccountEntity ae){
        return this.getSqlSession().insert("AccountMapper.addAccount", ae);
    }
    
    @Override
    public AccountEntity findAccountById(int id)
        throws DaoException
    {
        // String sql = "SELECT id,name,pwd,type,nickname,mobile_number FROM account WHERE id=? LIMIT 1";
        AccountEntity ae = this.getSqlSession().selectOne("AccountMapper.findAccountById", id);
        return ae;
    }
    
    @Override
    public int findIdByNameAndType(String name, byte type)
        throws DaoException
    {
        // String sql = "SELECT id FROM account WHERE name=? AND type=? LIMIT 1";
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("type", type);
        Integer r = this.getSqlSession().selectOne("AccountMapper.findIdByNameAndType", params);
        if (r == null)
            r = CommonConstant.ID_NOT_EXIST;
        return r;
    }
    
    @Override
    public int updateAccountById(AccountEntity ae)
        throws DaoException
    {
        // String sql = "UPDATE account SET pwd=? where id=?";
        
        // Map<String, Object> params = new HashMap<String, Object>();
        // params.put("pwd", ae.getPwd());
        // params.put("id", ae.getId());
        // params.put("availablePoint", ae.getAvailablePoint());
        
        return this.getSqlSession().update("AccountMapper.updateAccountById", ae);
    }
    
    @Override
    public int updateAccountInfoById(Map<String, Object> para)
        throws DaoException
    {
        return this.getSqlSession().update("AccountMapper.updateAccountInfoById", para);
    }
    
    @Override
    public AccountEntity findAccountByPwd(String pwd)
        throws DaoException
    {
        // String sql = "SELECT id,name,type,nickname,mobile_number FROM account WHERE pwd=? LIMIT 1";
        AccountEntity ae = this.getSqlSession().selectOne("AccountMapper.findAccountByPwd", pwd);
        return ae;
    }
    
    @Override
    public List<Map<String, Object>> findRegisterCouponIdByName(String name)
        throws DaoException
    {
        List<Map<String, Object>> maps = this.getSqlSession().selectList("AccountMapper.findRegisterCouponIdByName", name);
        return maps;
    }
    
    @Override
    public int deleteRegisterCouponByName(String name)
        throws DaoException
    {
        // String sql = "DELETE FROM register_mobile_coupon WHERE mobile_number=?";
        return this.getSqlSession().delete("AccountMapper.deleteRegisterCouponByName", name);
    }
    
    @Override
    public int findFatherPartnerAccountIdById(int id)
        throws DaoException
    {
        // ///////////////String sql = "SELECT father_account_id FROM account_partner_relation WHERE curr_account_id=?";
        Integer result = this.getSqlSession().selectOne("AccountMapper.findFatherPartnerAccountIdById", id);
        if (result == null)
            result = -1;
        return result;
    }
    
    @Override
    public AccountEntity findAccountByRecommendedCode(String recommendedCode)
        throws DaoException
    {
        // String sql =
        // "SELECT
        // id,name,pwd,type,nickname,mobile_number,image,available_point,partner_status,recommended_code,sub_recommended_count,total_recommended_point,apply_partner_status,recommended_count,recommended_order_count
        // FROM account WHERE recommended_code=? LIMIT 1";
        AccountEntity ae = this.getSqlSession().selectOne("AccountMapper.findAccountByRecommendedCode", recommendedCode);
        
        return ae;
    }
    
    @Override
    public int addAccountRecommendRelation(int currAccountId, int fatherAccountId)
        throws DaoException
    {
        // String sql = "INSERT INTO account_recommend_relation(curr_account_id,father_account_id) VALUES(?,?)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currAccountId", currAccountId);
        params.put("fatherAccountId", fatherAccountId);
        return this.getSqlSession().insert("AccountMapper.addAccountRecommendRelation", params);
    }
    
    @Override
    public int addAccountPartnerRelation(int currAccountId, int fatherAccountId)
        throws DaoException
    {
        // String sql = "INSERT INTO account_partner_relation(curr_account_id,father_account_id) VALUES(?,?)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("currAccountId", currAccountId);
        params.put("fatherAccountId", fatherAccountId);
        return this.getSqlSession().insert("AccountMapper.addAccountPartnerRelation", params);
    }
    
    @Override
    public int findFatherRecommendedAccountIdById(int id)
        throws DaoException
    {
        // String sql = "SELECT father_account_id FROM account_recommend_relation WHERE curr_account_id=?";
        Integer result = this.getSqlSession().selectOne("AccountMapper.findFatherRecommendedAccountIdById", id);
        if (result == null)
            result = -1;
        return result;
    }
    
    @Override
    public int addAccountRecommendedReturnPoint(AccountRecommendedReturnPointEntity arrpe)
        throws DaoException
    {
        // String sql =
        // "INSERT INTO
        // account_recommended_return_point(account_id,recommended_account_id,order_id,point,type,is_first_recommended)
        // VALUES(?,?,?,?,?,?)";
        return this.getSqlSession().insert("AccountMapper.addAccountRecommendedReturnPoint", arrpe);
    }
    
    @Override
    public int findFatherAccountIdByMobileNumber(String mobileNumber)
        throws DaoException
    {
        // SELECT father_account_id FROM account_prep_recommend_relation WHERE curr_mobile_number=?
        Integer fatherAccountId = getSqlSession().selectOne("AccountMapper.findFatherAccountIdByMobileNumber", mobileNumber);
        return fatherAccountId != null ? fatherAccountId : -1;
    }
    
    @Override
    public int insertAccountPrepRecommendRelation(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("AccountMapper.insertAccountPrepRecommendRelation", para);
    }
    
    @Override
    public int deleteAccountPrepRecommendRelation(String mobileNumber)
        throws DaoException
    {
        return getSqlSession().delete("AccountMapper.deleteAccountPrepRecommendRelation", mobileNumber);
    }
    
    @Override
    public int countPrepRecommended(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().selectOne("AccountMapper.countPrepRecommended", para);
    }
    
    @Override
    public int addRegisterMobileCoupon(String mobileNumber, int couponId, int validityDaysType, int days, int sourceType, int reducePrice)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("mobileNumber", mobileNumber);
        para.put("couponId", couponId);
        para.put("validityDaysType", validityDaysType);
        para.put("days", days);
        para.put("sourceType", sourceType);
        para.put("reducePrice", reducePrice);
        return getSqlSession().insert("AccountMapper.addRegisterMobileCoupon", para);
    }
    
    @Override
    public String findAccountNameById(int id)
        throws DaoException
    {
        String name = getSqlSession().selectOne("AccountMapper.findAccountNameById", id);
        return name;
    }
    
    @Override
    public List<Integer> findCollectSaleIdsByAid(int accountId)
        throws DaoException
    {
        return getSqlSession().selectList("AccountMapper.findCollectSaleIdsByAid", accountId);
    }
    
    @Override
    public Map<String, Object> countAccountTotalOrderRealPrice(int accountId)
        throws DaoException
    {
        return getSqlSession().selectOne("AccountMapper.countAccountTotalOrderRealPrice", accountId);
    }
    
    @Override
    public Map<String, Object> findPartnerTrainQuantityInfo(int currAccountId)
        throws DaoException
    {
        return getSqlSession().selectOne("AccountMapper.findPartnerTrainQuantityInfo", currAccountId);
    }
    
    @Override
    public AccountEntity findByNameAndType(String name, byte type)
        throws DaoException
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", name);
        params.put("type", type);
        
        return this.getSqlSession().selectOne("AccountMapper.findByNameAndType", params);
    }
    
    @Override
    public boolean isAccountBlack(int accountId)
        throws DaoException
    {
        return getSqlSession().selectOne("AccountMapper.findAccountBlack", accountId) != null;
    }
    
    @Override
    public int findHuanXinInfoByName(String username)
        throws DaoException
    {
        Integer id = getSqlSession().selectOne("AccountMapper.findHuanXinInfoByName", username);
        return id == null ? CommonConstant.ID_NOT_EXIST : id.intValue();
    }
    
    @Override
    public int addHuanXinInfo(String username)
        throws DaoException
    {
        return getSqlSession().insert("AccountMapper.addHuanXinInfo", username);
    }
    
    @Override
    public List<RegisterMobileCouponEntity> findRegisterMobileCoupon(Map<String, Object> para)
        throws DaoException
    {
        List<RegisterMobileCouponEntity> reList = getSqlSession().selectList("AccountMapper.findRegisterMobileCoupon", para);
        return reList == null ? new ArrayList<RegisterMobileCouponEntity>() : reList;
    }
    
    @Override
    public int updateIntegral(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().update("AccountMapper.updateIntegral", para);
    }
    
    @Override
    public int insertIntegralRecord(Map<String, Object> para)
        throws DaoException
    {
        return getSqlSession().insert("AccountMapper.insertIntegralRecord", para);
    }
}
