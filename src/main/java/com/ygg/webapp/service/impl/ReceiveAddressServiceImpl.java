package com.ygg.webapp.service.impl;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.CityDao;
import com.ygg.webapp.dao.DistrictDao;
import com.ygg.webapp.dao.ProvinceDao;
import com.ygg.webapp.dao.ReceiveAddressDao;
import com.ygg.webapp.entity.ReceiveAddressEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.ReceiveAddressService;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.view.ReceiveAddressView;

@Service("receiveAddressService")
public class ReceiveAddressServiceImpl implements ReceiveAddressService
{
    
    Logger logger = Logger.getLogger(ReceiveAddressServiceImpl.class);
    
    @Resource(name = "receiveAddressDao")
    private ReceiveAddressDao receiveAddressDao;
    
    @Resource(name = "provinceDao")
    private ProvinceDao provincedi = null;
    
    @Resource(name = "cityDao")
    private CityDao citydao = null;
    
    @Resource(name = "districtDao")
    private DistrictDao districtDao = null;
    
    @Override
    public ReceiveAddressView findReceiveAddressViewById(int addressId)
        throws ServiceException
    {
        if (addressId <= 0)
            return null;
        ReceiveAddressEntity rae = receiveAddressDao.findReceiveAddressById(addressId);
        ReceiveAddressView rav = new ReceiveAddressView();
        if (rae != null)
            try
            {
                BeanUtils.copyProperties(rav, rae);
                
                // String provinceName = this.provincedi.findProvinceNameById(rav.getProvince()) ;
                // String cityName = this.citydao.findCityNameById(rav.getCity()) ;
                // String districtName=this.districtDao.findDistinctNameById(rav.getDistrict()) ;
                
                String provinceName = CommonUtil.getProvinceNameByProvinceId(rae.getProvince());
                String cityName = CommonUtil.getCityNameByCityId(rae.getProvince(), rae.getCity());
                String districtName = CommonUtil.getDistrictNameByDistrictId(rae.getCity(), rae.getDistrict());
                String detailAddress = provinceName + cityName + districtName + rae.getDetailAddress();
                
                rav.setDetailAddress(detailAddress); // provinceName+cityName+districtName+rav.getDetailAddress());
                
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                logger.error("------findReceiveAddressViewById error------", e);
            }
        return rav;
    }
    
    @Override
    public ReceiveAddressView findDefaultAddressByAccountId(int accountId)
        throws ServiceException
    {
        if (accountId <= 0)
            return null;
        ReceiveAddressEntity rae = receiveAddressDao.findDefaultAddressByAccountId(accountId);
        ReceiveAddressView rav = new ReceiveAddressView();
        if (rae != null)
            try
            {
                BeanUtils.copyProperties(rav, rae);
                
                String provinceName = CommonUtil.getProvinceNameByProvinceId(rae.getProvince());
                String cityName = CommonUtil.getCityNameByCityId(rae.getProvince(), rae.getCity());
                String districtName = CommonUtil.getDistrictNameByDistrictId(rae.getCity(), rae.getDistrict());
                String detailAddress = provinceName + cityName + districtName + rae.getDetailAddress();
                
                rav.setDetailAddress(detailAddress); // provinceName+cityName+districtName+rav.getDetailAddress());
                
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                logger.error("------findDefaultAddressByAccountId error------", e);
            }
        return rav;
    }
    
}
