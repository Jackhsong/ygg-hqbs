package com.ygg.webapp.dao;

import java.util.List;

import com.ygg.webapp.entity.ReceiveAddressEntity;
import com.ygg.webapp.exception.DaoException;

public interface ReceiveAddressDao
{
    
    /**
     * 新增地址
     *
     * @return
     */
    int addAddress(ReceiveAddressEntity rae)
        throws DaoException;
    
    /**
     * 更新地址
     *
     * @return
     */
    int updateAddress(ReceiveAddressEntity rae)
        throws DaoException;
    
    /**
     * 根据用户id和地址id更新为默认地址
     *
     * @return
     */
    int updateDefaultAddress(int id, int accountId, int isdefault)
        throws DaoException;
    
    /**
     * 删除地址
     *
     * @return
     */
    int deleteAddress(int id, int accountId)
        throws DaoException;
    
    /**
     * 根据用户id和地址id获取地址信息
     *
     * @return
     */
    ReceiveAddressEntity findAddressByAccountIdAndId(int id, int accountId)
        throws DaoException;
    
    /**
     * 根据用户id获取最后修改的地址id
     *
     * @return
     */
    int findLastAddressIdByAccountId(int accountId)
        throws DaoException;
    
    /**
     * 根据用户id获取地址列表
     *
     * @return
     */
    List<ReceiveAddressEntity> findAllAddressByAccountId(int accountId)
        throws DaoException;
    
    /**
     * 根据用户id获取默认地址信息
     *
     * @return
     */
    ReceiveAddressEntity findDefaultAddressByAccountId(int accountId)
        throws DaoException;
    
    /**
     * 根据地址ID查出接收地址实体
     * 
     * @param addressId
     * @return
     * @throws DaoException
     */
    ReceiveAddressEntity findReceiveAddressById(int addressId)
        throws DaoException;
    
    /** 
    * @Description: 将用户的默认收货地址取消
    * @param accountId
    * @return void
    * @throws 
    */
    int updateUnDefaultAddress(int accountId)
        throws DaoException;
    
}