package com.ygg.webapp.service;

import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.view.AddressView;

public interface AddressService
{
    
    /**
     * 加载所有的省市区信息到cache中
     * 
     * @throws ServiceException
     */
    public void init()
        throws ServiceException;
    
    /**
     * 获取全国省市区信息
     *
     * @param request
     * @return
     */
    String getNationwideInfo(String requestParams)
        throws Exception;
    
    /**
     * 获取全国省市区信息 并放入缓存中,之后都从缓存中去取数据
     * 
     * @return
     * @throws Exception
     */
    String getNationwideInfo()
        throws Exception;
    
    /**
     * 增加一个收货地址
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String addAddress(String requestParams)
        throws Exception;
    
    /**
     * 修改收货地址
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String modifyAddress(String requestParams)
        throws Exception;
    
    String modifyAddress(AddressView av)
        throws Exception;
    
    /**
     * 删除收货地址
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String removeAddress(String requestParams)
        throws Exception;
    
    /**
     * 设置默认的收货地址
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String setDefaultAddress(String requestParams)
        throws Exception;
    
    /**
     * 列出所有的地址 自行再转成json以json的形式返回
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    String listAllAddress(String requestParams)
        throws Exception;
    
    /**
     * 根据accoundId addressId 查出一条记录
     * 
     * @param requestParams
     * @return
     * @throws Exception
     */
    AddressView findAddressViewByAcIdAndAdsId(String requestParams)
        throws Exception;
    
}
