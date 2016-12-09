package com.ygg.webapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.CityDao;
import com.ygg.webapp.dao.DistrictDao;
import com.ygg.webapp.dao.ProvinceDao;
import com.ygg.webapp.dao.ReceiveAddressDao;
import com.ygg.webapp.entity.CityEntity;
import com.ygg.webapp.entity.DistrictEntity;
import com.ygg.webapp.entity.ProvinceEntity;
import com.ygg.webapp.entity.ReceiveAddressEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.AddressService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.IdCardUtil;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.view.AddressView;
import com.ygg.webapp.view.CityView;
import com.ygg.webapp.view.DistrictView;
import com.ygg.webapp.view.ProvinceView;

// @Service("addressService") init方法
public class AddressServiceImpl implements AddressService
{
    
    Logger logger = Logger.getLogger(AddressServiceImpl.class);
    
    private Map<String, Long> lastRequestMillisMap = new HashMap<String, Long>();
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource(name = "provinceDao")
    private ProvinceDao pdi = null;
    
    @Resource(name = "cityDao")
    private CityDao cdi = null;
    
    @Resource(name = "districtDao")
    private DistrictDao ddi = null;
    
    @Resource(name = "receiveAddressDao")
    private ReceiveAddressDao radi = null;
    
    @Resource(name = "accountDao")
    private AccountDao adi = null;
    
    public void init()
        throws ServiceException
    {
        try
        {
            getNationwideInfo();
        }
        catch (Exception e)
        {
            logger.error("加载省市区信息出错", e);
        }
    }
    
    /**
     * 查询数据放入缓存中, 上层调用此方法时， 先从缓存中判断有没有数据， 没有数据再调用此方法即可
     *
     */
    public String getNationwideInfo()
        throws Exception
    {
        JsonObject result = new JsonObject();
        
        List<ProvinceEntity> pes = pdi.findAllProvinceInfo();
        List<ProvinceView> pvs = new ArrayList<ProvinceView>();
        for (ProvinceEntity pe : pes)
        {
            ProvinceView pv = new ProvinceView();
            pv.setName(pe.getName());
            pv.setProvinceId(pe.getProvinceId() + "");
            pvs.add(pv);
        }
        this.cacheService.addCache(CacheConstant.PROVINCE_DATA_CACHE, pvs, CacheConstant.CACHE_DAY_1);
        // /再根据PID查city Map<citys,Map<pid,citys> >
        // Map<String,Object> citiesMap =new HashMap<String,Object>();
        Map<String, Object> cityMap = new HashMap<String, Object>();
        for (ProvinceEntity pe : pes)
        {
            List<CityView> cvs = new ArrayList<CityView>();
            List<CityEntity> ces = this.cdi.findAllCityByPID(pe.getProvinceId() + "");
            for (CityEntity ce : ces)
            {
                CityView cv = new CityView();
                cv.setCityId(ce.getCityId() + "");
                cv.setName(ce.getName());
                cv.setProvinceId(ce.getProvincId() + "");
                cvs.add(cv);
            }
            if (ces != null && !ces.isEmpty())
                cityMap.put(pe.getProvinceId() + "", cvs);
        }
        // citiesMap.put(CacheConstant.CITY_DATA_CACHE, cityMap) ;
        this.cacheService.addCache(CacheConstant.CITY_DATA_CACHE, cityMap, CacheConstant.CACHE_DAY_1);
        
        List<CityEntity> ces = cdi.findAllCityInfo();
        // List<CityView> cvs = new ArrayList<CityView>();
        
        // /根据所有city去分别查区域　Map<districts,Map<cid,district>>
        // Map<String,Object> districtsMap =new HashMap<String,Object>();
        Map<String, Object> districtMap = new HashMap<String, Object>();
        for (CityEntity ce : ces)
        {
            /*
             * CityView cv = new CityView(); cv.setCityId(ce.getCityId() + ""); cv.setName(ce.getName());
             * cv.setProvinceId(ce.getProvincId() + ""); cvs.add(cv) ;
             */
            
            List<DistrictView> dvs = new ArrayList<DistrictView>();
            List<DistrictEntity> des = this.ddi.findAllDistrictByCId(ce.getCityId() + "");
            for (DistrictEntity de : des)
            {
                DistrictView dv = new DistrictView();
                dv.setCityId(de.getCityId() + "");
                dv.setDistrictId(de.getDistrictId() + "");
                dv.setName(de.getName());
                dvs.add(dv);
            }
            if (des != null && !des.isEmpty())
                districtMap.put(ce.getCityId() + "", dvs);
            
        }
        
        // this.cacheService.addCache(CacheConstant.CITY_ALL_DATA_CACHE, cvs, 0);
        
        // districtsMap.put(CacheConstant.DISTRICT_DATA_CACHE, districtMap) ;
        this.cacheService.addCache(CacheConstant.DISTRICT_DATA_CACHE, districtMap, CacheConstant.CACHE_DAY_1);
        
        /*
         * List<DistrictEntity> des = ddi.findAllDistrictInfo() ; List<DistrictView> dvs = new
         * ArrayList<DistrictView>(); for (DistrictEntity de : des) { DistrictView dv = new DistrictView();
         * dv.setName(de.getName()); dv.setDistrictId(de.getDistrictId()+""); dv.setCityId(de.getCityId()+"");
         * dvs.add(dv); } this.cacheService.addCache(CacheConstant.DISTRICT_ALL_DATA_CACHE, dvs,0);
         */
        
        result.addProperty("success", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    /**
     * 暂时不调用
     */
    @Override
    @Deprecated
    public String getNationwideInfo(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String imei = param.get("imei").getAsString();
        if (lastRequestMillisMap.containsKey(imei)) // // imei 保存请求后的一个时间点值,如在6小时内再次请求，从缓存中去查
        {
            long lastRequestMillis = lastRequestMillisMap.get(imei);
            if ((System.currentTimeMillis() - lastRequestMillis) / 1000 < 6 * 60 * 60)
            {
                if (cacheService.isInCache("provinceList"))
                {
                    // /在缓存中
                    result.add("provinceList", (JsonObject)cacheService.getCache("provinceList"));
                    result.add("cityList", (JsonObject)cacheService.getCache("cityList"));
                    result.add("districtList", (JsonObject)cacheService.getCache("districtList"));
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
                    return result.toString();
                }
                else
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.ADDRESS_NATIONWIDE_ERRORCODE.REQUEST_REPEAT.getValue());
                    return result.toString();
                }
                
            }
        }
        
        List<ProvinceEntity> pes = pdi.findAllProvinceInfo();
        List<CityEntity> ces = cdi.findAllCityInfo();
        List<DistrictEntity> des = ddi.findAllDistrictInfo();
        List<ProvinceView> pvs = new ArrayList<ProvinceView>();
        List<CityView> cvs = new ArrayList<CityView>();
        List<DistrictView> dvs = new ArrayList<DistrictView>();
        
        for (ProvinceEntity pe : pes)
        {
            ProvinceView pv = new ProvinceView();
            pv.setName(pe.getName());
            pv.setProvinceId(pe.getProvinceId() + "");
            pvs.add(pv);
        }
        
        for (CityEntity ce : ces)
        {
            CityView cv = new CityView();
            cv.setCityId(ce.getCityId() + "");
            cv.setName(ce.getName());
            cv.setProvinceId(ce.getProvincId() + "");
            cvs.add(cv);
        }
        
        for (DistrictEntity de : des)
        {
            DistrictView dv = new DistrictView();
            dv.setCityId(de.getCityId() + "");
            dv.setDistrictId(de.getDistrictId() + "");
            dv.setName(de.getName());
            dvs.add(dv);
        }
        
        JsonObject provinceList = (JsonObject)parser.parse(JSONUtils.toJson(pvs, false));
        JsonObject cityList = (JsonObject)parser.parse(JSONUtils.toJson(cvs, false));
        JsonObject districtList = (JsonObject)parser.parse(JSONUtils.toJson(dvs, false));
        
        result.add("provinceList", provinceList);
        result.add("cityList", cityList);
        result.add("districtList", districtList);
        lastRequestMillisMap.put(imei, System.currentTimeMillis());
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        
        this.cacheService.addCache("provinceList", provinceList, 0);
        this.cacheService.addCache("cityList", provinceList, 0);
        this.cacheService.addCache("districtList", provinceList, 0);
        
        return result.toString();
    }
    
    @Override
    public String addAddress(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        String fullName = param.get("fullName").getAsString();
        String mobileNumber = param.get("mobileNumber").getAsString();
        String province = param.get("province").getAsString();
        String city = param.get("city").getAsString();
        String district = param.get("district").getAsString();
        String detailAddress = param.get("detailAddress").getAsString();
        String idCard = param.get("idCard").getAsString();
        
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_ADD_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }/*
          * else if(mobileNumber!=null && !CommonUtil.isMobile(mobileNumber)) { result.addProperty("status",
          * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); result.addProperty("errorCode",
          * CommonEnum.ADDRESS_ADD_ERRORCODE.ID_CARD_INVILD_MODBILE.getValue()); return result.toString(); }
          */
        else if (!idCard.equals("") && !IdCardUtil.verify(idCard))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_ADD_ERRORCODE.ID_CARD_INVALID.getValue());
            return result.toString();
        }
        
        ReceiveAddressEntity rae = new ReceiveAddressEntity();
        rae.setAccountId(accountId);
        rae.setCity(city);
        rae.setDetailAddress(detailAddress);
        rae.setDistrict(district);
        rae.setFullName(fullName);
        rae.setIdCard(idCard);
        rae.setMobileNumber(mobileNumber);
        rae.setProvince(province);
        /*
         * int addressId = radi.findLastAddressIdByAccountId(accountId); if (addressId == CommonConstant.ID_NOT_EXIST)
         * // 如果是第一次增加收货地址　就设为默认的收货地址 { rae.setIsDefault(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue())); } else {
         * rae.setIsDefault(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue())); }
         */
        
        ReceiveAddressEntity defaultrae = radi.findDefaultAddressByAccountId(accountId);
        if (defaultrae == null || defaultrae.getId() == 0)
        {
            rae.setIsDefault(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
        }
        else
        {
            rae.setIsDefault(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()));
        }
        
        if (radi.addAddress(rae) == 0) // 如果增加失败
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        
        result.addProperty("addressId", rae.getId());
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    @Override
    public String modifyAddress(String requestParams)
        throws Exception
    {
        // JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        AddressView av = JSONUtils.fromJson(param.get("address").toString(), AddressView.class);
        av.setAccountId(accountId + "");
        // ReceiveAddressEntity rae = new ReceiveAddressEntity();
        return modifyAddress(av);
        /*
         * rae.setAccountId(accountId); rae.setCity(av.getCity()); rae.setDetailAddress(av.getDetailAddress());
         * rae.setDistrict(av.getDistrict()); rae.setFullName(av.getFullName());
         * rae.setId(Integer.parseInt(av.getAddressId())); rae.setIdCard(av.getIdCard()); // 不能修改身份证号
         * rae.setMobileNumber(av.getMobileNumber()); rae.setProvince(av.getProvince());
         * 
         * if (adi.findAccountById(accountId) == null) { result.addProperty("status",
         * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); result.addProperty("errorCode",
         * CommonEnum.ADDRESS_MODIFY_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()); return result.toString(); } else if
         * (!rae.getIdCard().equals("") && !IdCardUtil.verify(rae.getIdCard())) { result.addProperty("status",
         * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); result.addProperty("errorCode",
         * CommonEnum.ADDRESS_MODIFY_ERRORCODE.ID_CARD_INVALID.getValue()); return result.toString(); } else if
         * (radi.updateAddress(rae) == 0) { result.addProperty("status",
         * CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()); result.addProperty("errorCode",
         * CommonEnum.ADDRESS_MODIFY_ERRORCODE.ADDRESSID_NOT_EXIST.getValue()); return result.toString(); }
         * 
         * result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); return
         * result.toString();
         */
    }
    
    public String modifyAddress(AddressView av)
        throws Exception
    {
        JsonObject result = new JsonObject();
        int accountId = Integer.parseInt(av.getAccountId());
        ReceiveAddressEntity rae = new ReceiveAddressEntity();
        rae.setAccountId(Integer.parseInt(av.getAccountId()));
        rae.setCity(av.getCity());
        rae.setDetailAddress(av.getDetailAddress());
        rae.setDistrict(av.getDistrict());
        rae.setFullName(av.getFullName());
        rae.setId(Integer.parseInt(av.getAddressId()));
        rae.setIdCard(av.getIdCard()); // 不能修改身份证号
        rae.setMobileNumber(av.getMobileNumber());
        rae.setProvince(av.getProvince());
        
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_MODIFY_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        else if (!rae.getIdCard().equals("") && !IdCardUtil.verify(rae.getIdCard()))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_MODIFY_ERRORCODE.ID_CARD_INVALID.getValue());
            return result.toString();
        }
        else if (radi.updateAddress(rae) == 0)
        {
            
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_MODIFY_ERRORCODE.ADDRESSID_NOT_EXIST.getValue());
            
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String removeAddress(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int addressId = param.get("addressId").getAsInt();
        
        ReceiveAddressEntity rae = radi.findAddressByAccountIdAndId(addressId, accountId);
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_DELETE_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        else if (radi.deleteAddress(addressId, accountId) == 0)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_DELETE_ERRORCODE.ADDRESSID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        if (rae.getIsDefault() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue())) // / 删除的为　默认的收货地址 　
        {
            int lastAddressId = radi.findLastAddressIdByAccountId(accountId); // 找一个addressId 设为　　默认的收货地址
            if (lastAddressId != CommonConstant.ID_NOT_EXIST)
            {
                radi.updateDefaultAddress(lastAddressId, accountId, Integer.parseInt(CommonEnum.COMMON_IS.YES.getValue()));
            }
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String setDefaultAddress(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int addressId = param.get("addressId").getAsInt();
        
        List<ReceiveAddressEntity> list = this.radi.findAllAddressByAccountId(accountId);
        if (list == null || list.isEmpty())
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_LIST_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }/*
          * else if(list!=null && list.size()==1 ) // 如果只有一个收货地址而已是　　默认的收货地址　返回成功 ,默认的收货地址 在页面上控制， {
          * ReceiveAddressEntity re = list.get(0) ; if(re.getIsDefault() ==
          * Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue())){ // 不再修改 result.addProperty("status",
          * CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue()); return result.toString(); } }
          */
        else
        // 有多个收货地址，而把另一个地址设为　默认的收货地址
        {
            for (ReceiveAddressEntity re : list)
            {
                if (re.getIsDefault() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()) && re.getId() != addressId)
                {
                    // 把之前的更新为　　非默认的收货地址　　再把现在的地址更新为默认
                    this.radi.updateDefaultAddress(re.getId(), re.getAccountId(), Integer.parseInt(CommonEnum.COMMON_IS.NO.getValue()));
                    this.radi.updateDefaultAddress(addressId, accountId, Integer.parseInt(CommonEnum.COMMON_IS.YES.getValue()));
                    break;
                }
            }
        }
        // / 要把它之前的　默认的收货地址　去掉,再设这个为　默认的收货地址
        // if( this.radi.updateDefaultAddress(addressId,accountId) == 0 ){
        // }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    @Override
    public String listAllAddress(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        
        if (adi.findAccountById(accountId) == null)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ADDRESS_LIST_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue());
            return result.toString();
        }
        
        List<ReceiveAddressEntity> raes = radi.findAllAddressByAccountId(accountId);
        List<AddressView> avs = new ArrayList<AddressView>();
        if (raes != null && !raes.isEmpty())
        {
            for (ReceiveAddressEntity rae : raes)
            {
                AddressView av = new AddressView();
                av.setAddressId(rae.getId() + "");
                av.setCity(rae.getCity());
                
                av.setDistrict(rae.getDistrict());
                av.setFullName(rae.getFullName());
                av.setIdCard(rae.getIdCard());
                av.setMobileNumber(rae.getMobileNumber());
                av.setProvince(rae.getProvince());
                String provinceName = CommonUtil.getProvinceNameByProvinceId(rae.getProvince()); // this.pdi.findProvinceNameById(rae.getProvince())
                                                                                                 // ;
                String cityName = CommonUtil.getCityNameByCityId(rae.getProvince(), rae.getCity()); // this.cdi.findCityNameById(rae.getCity())
                                                                                                    // ;
                String districtName = CommonUtil.getDistrictNameByDistrictId(rae.getCity(), rae.getDistrict()); // this.ddi.findDistinctNameById(rae.getDistrict())
                                                                                                                // ;
                String detailAddress = provinceName + cityName + districtName + rae.getDetailAddress();
                av.setDetailAddress(detailAddress);
                avs.add(av);
            }
            result.add("addressList", parser.parse(JSONUtils.toJson(avs, false)));
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
        
    }
    
    @Override
    public AddressView findAddressViewByAcIdAndAdsId(String requestParams)
        throws Exception
    {
        JsonParser parser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(requestParams);
        int accountId = param.get("accountId").getAsInt();
        int addressId = param.get("addressId").getAsInt();
        ReceiveAddressEntity rae = radi.findAddressByAccountIdAndId(addressId, accountId);
        if (rae == null)
            return null;
        AddressView av = new AddressView();
        
        av.setAccountId(rae.getAccountId() + "");
        av.setAddressId(rae.getId() + "");
        av.setCity(rae.getCity());
        av.setDetailAddress(rae.getDetailAddress());
        av.setDistrict(rae.getDistrict());
        av.setFullName(rae.getFullName());
        // av.setId(Integer.parseInt(av.getAddressId()));
        av.setIdCard(rae.getIdCard()); // 不能修改身份证号
        av.setMobileNumber(rae.getMobileNumber());
        av.setProvince(rae.getProvince());
        
        return av;
    }
    
}
