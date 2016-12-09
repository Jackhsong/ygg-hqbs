package com.ygg.webapp.dao.impl.mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.ReceiveAddressDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.ReceiveAddressEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;

@Repository("receiveAddressDao")
public class ReceiveAddressDaoImpl extends BaseDaoImpl implements ReceiveAddressDao
{
    
    @Override
    public int addAddress(ReceiveAddressEntity rae)
        throws DaoException
    {
        // String sql =
        // "INSERT INTO receive_address(account_id,full_name,mobile_number,detail_address,id_card,province,city,district,is_default) VALUES(?,?,?,?,?,?,?,?,?)";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", rae.getAccountId());
        params.put("fullName", rae.getFullName());
        params.put("mobileNumber", rae.getMobileNumber());
        params.put("detailAddress", rae.getDetailAddress());
        params.put("idCard", rae.getIdCard());
        params.put("province", rae.getProvince());
        params.put("city", rae.getCity());
        params.put("district", rae.getDistrict());
        params.put("isDefault", rae.getIsDefault());
        
        return this.getSqlSession().insert("ReceiveAddressMapper.addAddress", rae); // params) ;
    }
    
    @Override
    public int findLastAddressIdByAccountId(int accountId)
        throws DaoException
    {
        // String sql = "SELECT id FROM receive_address WHERE account_id=? ORDER BY update_time DESC LIMIT 1";
        Integer r = this.getSqlSession().selectOne("ReceiveAddressMapper.findLastAddressIdByAccountId", accountId);
        if (r == null)
            r = CommonConstant.ID_NOT_EXIST;
        return r;
    }
    
    @Override
    public List<ReceiveAddressEntity> findAllAddressByAccountId(int accountId)
        throws DaoException
    {
        // String sql = "SELECT * FROM receive_address WHERE account_id=? ORDER BY update_time DESC";
        List<ReceiveAddressEntity> list = this.getSqlSession().selectList("ReceiveAddressMapper.findAllAddressByAccountId", accountId);
        if (list == null || list.isEmpty())
            list = new ArrayList<ReceiveAddressEntity>();
        return list;
    }
    
    @Override
    public int updateAddress(ReceiveAddressEntity rae)
        throws DaoException
    {
        // String sql =
        // "UPDATE receive_address SET full_name=?,mobile_number=?,detail_address=?,id_card=?,province=?,city=?,district=? WHERE id=? AND account_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fullname", rae.getFullName());
        params.put("mobilenumber", rae.getMobileNumber());
        params.put("detailaddress", rae.getDetailAddress());
        params.put("idcard", rae.getIdCard());
        params.put("province", rae.getProvince());
        params.put("city", rae.getCity());
        params.put("district", rae.getDistrict());
        params.put("isdefault", rae.getIsDefault());
        params.put("accountid", rae.getAccountId());
        params.put("id", rae.getId());
        return this.getSqlSession().update("ReceiveAddressMapper.updateAddress", params);
    }
    
    @Override
    public int deleteAddress(int id, int accountId)
        throws DaoException
    {
        // String sql = "DELETE FROM receive_address WHERE id=? AND account_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountid", accountId);
        params.put("id", id);
        return this.getSqlSession().delete("ReceiveAddressMapper.deleteAddress", params);
        
    }
    
    @Override
    public ReceiveAddressEntity findAddressByAccountIdAndId(int id, int accountId)
        throws DaoException
    {
        // String sql = "SELECT is_default FROM receive_address WHERE id=? AND account_id=? LIMIT 1";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountid", accountId);
        params.put("id", id);
        ReceiveAddressEntity r = this.getSqlSession().selectOne("ReceiveAddressMapper.findAddressByAccountIdAndId", params);
        return r;
    }
    
    @Override
    public int updateDefaultAddress(int id, int accountId, int isdefault)
        throws DaoException
    {
        // String sql = "UPDATE receive_address SET is_default=1 WHERE id=? AND account_id=?";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountid", accountId);
        params.put("id", id);
        params.put("isdefault", isdefault);
        return this.getSqlSession().update("ReceiveAddressMapper.updateDefaultAddress", params);
    }
    
    @Override
    public ReceiveAddressEntity findDefaultAddressByAccountId(int accountId)
        throws DaoException
    {
        String sql = "SELECT id,province FROM receive_address WHERE account_id=? AND is_default=1 LIMIT 1";
        
        ReceiveAddressEntity rae = this.getSqlSession().selectOne("ReceiveAddressMapper.findDefaultAddressByAccountId", accountId);
        
        return rae;
    }
    
    @Override
    public ReceiveAddressEntity findReceiveAddressById(int addressId)
        throws DaoException
    {
        // String sql ="select * from receive_address r where r.id= ?" ;
        ReceiveAddressEntity rae = this.getSqlSession().selectOne("ReceiveAddressMapper.findReceiveAddressById", addressId);
        
        return rae;
    }
    
    /* (non-Javadoc)
     * @see com.ygg.webapp.dao.ReceiveAddressDao#updateUnDefaultAddress(int)
     */
    @Override
    public int updateUnDefaultAddress(int accountId)
        throws DaoException
    {
        return getSqlSession().update("ReceiveAddressMapper.updateUnDefaultAddress", accountId);
    }
}
