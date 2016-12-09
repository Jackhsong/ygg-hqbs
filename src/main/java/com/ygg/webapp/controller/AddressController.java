package com.ygg.webapp.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.exception.BusException;
import com.ygg.webapp.service.AddressService;
import com.ygg.webapp.service.ShoppingCartService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.SessionUtil;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.AddressView;
import com.ygg.webapp.view.CityView;
import com.ygg.webapp.view.DistrictView;
import com.ygg.webapp.view.ProvinceView;

/**
 * 维护送货地址 包括　省，市，区域的选择
 * 
 * @author lihc
 *
 */
@Controller("receiveAddressController")
@RequestMapping("/ads")
public class AddressController
{
    
    Logger logger = Logger.getLogger(AddressController.class);
    
    @Resource(name = "addressService")
    private AddressService addressService;
    
    @Resource(name = "shoppingCartService")
    private ShoppingCartService shoppingCartService;
    
    // @Resource(name = "cacheService")
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    /**
     * 收货地址管理
     * 
     * @param address
     */
    @RequestMapping("/listads")
    public ModelAndView listReceiveAddress(HttpServletRequest request)
        throws Exception
    {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("address/consignee");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        mv.addObject("islogin", CommonEnum.COMMON_IS.YES.getValue());
        String requestParams = "{'accountId':'" + av.getId() + "'}";
        String list = addressService.listAllAddress(requestParams);
        if (null != list && !list.equals(""))
        {
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(list);
            if (param.get("addressList") != null)
            {
                String addressListJson = param.get("addressList").toString(); // .getAsJsonObject() ;
                List<AddressView> addressList = JSONUtils.fromJson(addressListJson, new TypeToken<List<AddressView>>()
                {
                });
                mv.addObject("addressList", addressList);
            }
        }
        return mv;
    }
    
    /**
     * Order的收货管理
     * 
     * @param address
     */
    @RequestMapping("/listadsmgr")
    public ModelAndView listOrderMgrReceiveAddress(HttpServletRequest request,
        @RequestParam(value = "source", required = false, defaultValue = "0") String source,
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype,
        @RequestParam(value = "isBonded", required = false, defaultValue = "0") String isBonded)
        throws Exception
    {
        
        ModelAndView mv = new ModelAndView();
        mv.setViewName("address/consigneecart");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        mv.addObject("islogin", CommonEnum.COMMON_IS.YES.getValue());
        String requestParams = "{'accountId':'" + av.getId() + "'}";
        String list = addressService.listAllAddress(requestParams);
        if (null != list && !list.equals(""))
        {
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(list);
            if (param.get("addressList") != null)
            {
                String addressListJson = param.get("addressList").toString(); // .getAsJsonObject() ;
                List<AddressView> addressList = JSONUtils.fromJson(addressListJson, new TypeToken<List<AddressView>>()
                {
                });
                mv.addObject("addressList", addressList);
            }
        }
        if (source != null && source.equals("consignc"))
        {
            mv.addObject("ordersource", source); // 从order中选地址页面来
            mv.addObject("ordertype", ordertype); // 从order中选地址页面来
            mv.addObject("isBonded", isBonded); // 从order中选地址页面来
        }
        return mv;
    }
    
    /**
     * 从提交定单界面中来
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/listorderads/{ordertype}/{isBonded}"})
    public ModelAndView listOrderReceiveAddress(HttpServletRequest request, @PathVariable("ordertype") String ordertype,
        @PathVariable("isBonded") String isBonded)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("address/consignc");
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        mv.addObject("islogin", CommonEnum.COMMON_IS.YES.getValue());
        String requestParams = "{'accountId':'" + av.getId() + "'}";
        String list = addressService.listAllAddress(requestParams);
        if (null != list && !list.equals(""))
        {
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(list);
            if (param.get("addressList") != null)
            {
                String addressListJson = param.get("addressList").toString(); // .getAsJsonObject() ;
                List<AddressView> addressList = JSONUtils.fromJson(addressListJson, new TypeToken<List<AddressView>>()
                {
                });
                mv.addObject("addressList", addressList);
            }
        }
        String selectedAddressId = SessionUtil.getCurrentSelectedAddressId(request.getSession());
        mv.addObject("selectedAddressId", selectedAddressId);
        return mv;
    }
    
    /**
     * 进入新增页面 要查出所有的省
     * 
     * @return
     */
    @RequestMapping("/toaddads")
    public ModelAndView toAdd(HttpServletRequest reqeust)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        if (SessionUtil.getCurrentUser(reqeust.getSession()) == null)
        {
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        mv.setViewName("address/consignadd");
        List<ProvinceView> pves = this.cacheService.getCache(CacheConstant.PROVINCE_DATA_CACHE);
        Map<String, Object> citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
        Map<String, Object> districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        if (pves == null || pves.isEmpty())
        {
            putpcdToCache(pves, citiesMap, districtsMap);
            pves = this.cacheService.getCache(CacheConstant.PROVINCE_DATA_CACHE);
            citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
            districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        }
        mv.addObject("provinceList", pves);
        return mv;
    }
    
    /**
     * 从定单中进来新增
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/toorderaddads/{ordertype}/{isBonded}")
    public ModelAndView toOrderAdd(HttpServletRequest reqeust, @PathVariable("ordertype") String ordertype, @PathVariable("isBonded") String isBonded,
        @RequestParam(value = "source", required = false, defaultValue = "0") String source)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        if (SessionUtil.getCurrentUser(reqeust.getSession()) == null)
        {
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        mv.setViewName("address/consigncartadd");
        List<ProvinceView> pves = this.cacheService.getCache(CacheConstant.PROVINCE_DATA_CACHE);
        Map<String, Object> citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
        Map<String, Object> districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        if (pves == null || pves.isEmpty())
        {
            putpcdToCache(pves, citiesMap, districtsMap);
            pves = this.cacheService.getCache(CacheConstant.PROVINCE_DATA_CACHE);
            citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
            districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        }
        
        mv.addObject("provinceList", pves);
        
        mv.addObject("ordertype", ordertype);
        mv.addObject("isBonded", isBonded);
        
        if (source != null && source.equals("consignc"))
        {
            mv.addObject("source", source);
        }
        return mv;
    }
    
    /**
     * 根据省ID去查省下面所有的市ID 先假定都在缓存中，后面再加个方法，如果缓存中没有，再去查库 ajax 请求 在配置文件中不起作用，在此配
     * produces="application/json;charset=UTF-8"才能起作用，以后再看
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getcitiesbypid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getCitiesByPID(@RequestParam(value = "pid", required = true, defaultValue = "0") String pid)
        throws Exception
    {
        String result = "";
        Map<String, Object> citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
        if (citiesMap != null && !citiesMap.isEmpty())
        {
            List<CityView> cvs = (List<CityView>)citiesMap.get(pid);
            // 转成json格式
            JsonObject object = new JsonObject();
            object.addProperty("cities", JSONUtils.toJson(cvs, false));
            result = object.toString();
        }
        return result;
    }
    
    /**
     * 根据市ID查出所有的区ID
     * 
     * @param cid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getdistrictsbycid", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getDistrictsByCID(@RequestParam(value = "cid", required = true, defaultValue = "0") String cid)
        throws Exception
    {
        String result = "";
        Map<String, Object> districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        if (districtsMap != null && !districtsMap.isEmpty())
        {
            List<DistrictView> dvs = (List<DistrictView>)districtsMap.get(cid);
            // 转成json格式
            JsonObject object = new JsonObject();
            object.addProperty("districts", JSONUtils.toJson(dvs, false));
            result = object.toString();
        }
        return result;
    }
    
    /**
     * 增加一个收货地址　页面的保存
     * 
     * @param address
     */
    @RequestMapping("/addads")
    public ModelAndView addReceiveAddress(@ModelAttribute AddressView address)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forward:/ads/toaddads"); // / 出错留在此页面
        
        String validateResult = address.validate("");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.addObject("errorCode", validateResult);
            mv.addObject("address", address);
            putPCDToModelAndView(mv, address);
            return mv;
        }
        try
        {
            String requestParams = JSONUtils.toJson(address, false); // {'accountId':'8','fullName':'屈林','mobileNumber':'mobileNumber','province':'province','city':'city','district':'district','detailAddress':'detailAddress','idCard':'idCard'}"
            String result = this.addressService.addAddress(requestParams);
            if (null != result && !result.equals(""))
            {
                
                JsonParser parser = new JsonParser();
                JsonObject param = (JsonObject)parser.parse(result);
                String status = param.get("status").getAsString();
                if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
                {
                    mv.addObject("address", address);
                    String errorCode = param.get("errorCode").getAsString();
                    if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_ADD_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
                        mv.addObject("errorCode", "请先登录!");
                    else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_ADD_ERRORCODE.ID_CARD_INVALID.getValue()))
                        mv.addObject("errorCode", "请输入18位身份证号码!");
                    else if (errorCode != null && errorCode.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
                        mv.addObject("errorCode", "保存失败!");
                    putPCDToModelAndView(mv, address);
                    return mv;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("AddressController ----addReceiveAddress 保存失败!", e);
            BusException be = new BusException(CommonConstant.DB_ERROR_MSG, "forward:/ads/toaddads", CommonConstant.DB_ERROR_ERROR_CODE);
            be.putModelObject("address", address);
            be.putModelObject("errorCode", "保存失败,请重新刷新页面!");
            throw be;
        }
        mv.setViewName("redirect:/ads/listads"); // / 成功跳到地址管理中心
        return mv;
    }
    
    /**
     * 从定单中确认中新增地址时的保存
     * 
     * @param address
     * @return
     */
    @RequestMapping("/addorderads")
    public ModelAndView addOrderReceiveAddress(HttpServletRequest request, @ModelAttribute AddressView address,
        @RequestParam(value = "ordertype", required = false, defaultValue = "1") String ordertype,
        @RequestParam(value = "isBonded", required = false, defaultValue = "0") String isBonded,
        @RequestParam(value = "source", required = false, defaultValue = "0") String source)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        String errorViewName = "forward:/ads/toorderaddads/" + ordertype + "/" + isBonded;
        mv.setViewName(errorViewName); // / 出错留在此页面
        
        String validateResult = address.validate("");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.addObject("errorCode", validateResult);
            mv.addObject("address", address);
            putPCDToModelAndView(mv, address);
            return mv;
        }
        String addressId = "-1";
        try
        {
            String requestParams = JSONUtils.toJson(address, false); //
            String result = this.addressService.addAddress(requestParams);
            if (null != result && !result.equals(""))
            {
                
                JsonParser parser = new JsonParser();
                JsonObject param = (JsonObject)parser.parse(result);
                String status = param.get("status").getAsString();
                addressId = (param.get("addressId") == null ? "-1" : param.get("addressId").getAsString());
                if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
                {
                    mv.addObject("address", address);
                    String errorCode = param.get("errorCode").getAsString();
                    if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_ADD_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
                        mv.addObject("errorCode", "请先登录!");
                    else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_ADD_ERRORCODE.ID_CARD_INVALID.getValue()))
                        mv.addObject("errorCode", "请输入正确的身份证号码!");
                    else if (errorCode != null && errorCode.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
                        mv.addObject("errorCode", "保存失败,请重新刷新页面!");
                    putPCDToModelAndView(mv, address);
                    return mv;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("AddressController ----addOrderReceiveAddress 保存失败!", e);
            BusException be = new BusException(CommonConstant.DB_ERROR_MSG, errorViewName, CommonConstant.DB_ERROR_ERROR_CODE);
            be.putModelObject("address", address);
            be.putModelObject("errorCode", "保存失败,请重新刷新页面!");
            throw be;
        }
        SessionUtil.addSelectedAddressId(request.getSession(), addressId); // 返回到订单时取到，在生成定单时取消
        mv.setViewName("redirect:/order/confirm/" + ordertype); // 成功
        if (source != null && source.equals("consignc"))
        {
            mv.setViewName("forward:/ads/listadsmgr"); // 成功
            mv.addObject("ordertype", ordertype);
            mv.addObject("isBonded", isBonded);
            mv.addObject("source", "consignc");
        }
        return mv;
    }
    
    /**
     * 删除管理
     * 
     * @param address
     */
    @RequestMapping("/deleteads/{addressId}/{accountId}")
    public ModelAndView deleteReceiveAddress(@PathVariable("addressId") String addressId, @PathVariable("accountId") String accountId,
        @RequestParam(value = "isBonded", required = false, defaultValue = "0") String isBonded,
        @RequestParam(value = "ordertype", required = false, defaultValue = "") String ordertype)
        throws Exception
    {
        
        ModelAndView mv = new ModelAndView();
        if (ordertype != null && !ordertype.equals(""))
        {
            mv.setViewName("redirect:/ads/listadsmgr"); // 是从定单中来的
            mv.addObject("ordertype", ordertype);
            mv.addObject("isBonded", isBonded);
            mv.addObject("source", "consignc");
        }
        else
            mv.setViewName("redirect:/ads/listads"); // / 成功跳到地址管理中心　
            
        String requestParams = "{'accountId':'" + accountId + "','addressId':'" + addressId + "'}";
        String result = this.addressService.removeAddress(requestParams);
        
        if (null != result && !result.equals(""))
        {
            JsonParser parser = new JsonParser();
            JsonObject param = (JsonObject)parser.parse(result);
            String status = param.get("status").getAsString();
            if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
            {
                String errorCode = param.get("errorCode").getAsString();
                if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_DELETE_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
                {
                    mv.addObject("errorCode", "请先登录!");
                }
                else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_DELETE_ERRORCODE.ADDRESSID_NOT_EXIST.getValue()))
                {
                    mv.addObject("errorCode", "此记录已被删除,请返回!");
                }
                String viewName = "forward:/ads/tomdfads/" + addressId + "/" + accountId; // 出错留在此页面
                mv.setViewName(viewName);
            }
        }
        
        return mv;
    }
    
    /**
     * 从定单个修改中跳过来
     * 
     * @param addressId
     * @param source 来源==orderidcardinvalid 表示从定单界面来，并身份证为空的情况
     * @return
     * @throws Exception
     */
    @RequestMapping("/toordermdfads/{addressId}/{ordertype}/{isBonded}")
    public ModelAndView toOrderModify(HttpServletRequest reqeust, @PathVariable String addressId, @PathVariable String ordertype,
        @PathVariable String isBonded, @RequestParam(value = "source", required = false, defaultValue = "") String source)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("address/consigncartmdf");
        AccountView acv = SessionUtil.getCurrentUser(reqeust.getSession());
        if (acv == null)
        {
            mv.setViewName("redirect:/user/tologin");
            return mv;
        }
        String requestParams = "{'addressId':'" + addressId + "','accountId':'" + acv.getId() + "'}";
        AddressView av = this.addressService.findAddressViewByAcIdAndAdsId(requestParams);
        if (av == null)
        {
            logger.error("Address Id[ " + addressId + " ],此记录已被删除,请返回!");
            mv.setViewName("redirect:/order/confirm/" + ordertype);
            return mv;
        }
        mv.addObject("address", av);
        putPCDToModelAndView(mv, av);
        mv.addObject("ordertype", ordertype);
        mv.addObject("isBonded", isBonded);
        mv.addObject("source", source);
        return mv;
    }
    
    /**
     * 跳到修改页面
     * 
     * @param addressId
     * @return
     * @throws Exception
     */
    @RequestMapping("/tomdfads/{addressId}/{accountId}")
    public ModelAndView toModify(@PathVariable/* @RequestParam("addressId") */String addressId, @PathVariable String accountId)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("address/consignmodify");
        String requestParams = "{'addressId':'" + addressId + "','accountId':'" + accountId + "'}";
        AddressView av = this.addressService.findAddressViewByAcIdAndAdsId(requestParams);
        if (av != null)
            mv.addObject("address", av);
        else
            mv.addObject("errorCode", "此记录已被删除,请返回!");
        
        putPCDToModelAndView(mv, av);
        return mv;
    }
    
    private void putpcdToCache(List<ProvinceView> pves, Map<String, Object> citiesMap, Map<String, Object> districtsMap)
        throws Exception
    {
        if (pves == null || pves.isEmpty() || citiesMap == null || citiesMap.isEmpty() || districtsMap == null || districtsMap.isEmpty())
        {
            this.addressService.getNationwideInfo(); // 要放入缓存中组织起来
        }
    }
    
    @SuppressWarnings("unchecked")
    private void putPCDToModelAndView(ModelAndView mv, AddressView av)
        throws Exception
    {
        List<ProvinceView> pves = this.cacheService.getCache(CacheConstant.PROVINCE_DATA_CACHE);
        Map<String, Object> citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
        Map<String, Object> districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        if (CollectionUtils.isEmpty(pves))
        {
            putpcdToCache(pves, citiesMap, districtsMap);
            pves = this.cacheService.getCache(CacheConstant.PROVINCE_DATA_CACHE);
            citiesMap = this.cacheService.getCache(CacheConstant.CITY_DATA_CACHE);
            districtsMap = this.cacheService.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        }
        List<CityView> cvs = null;
        List<DistrictView> dvs = null;
        if (av != null)
        {
            cvs = (citiesMap.get(av.getProvince()) == null ? null : (List<CityView>)citiesMap.get(av.getProvince()));
            dvs = (districtsMap.get(av.getCity()) == null ? null : (List<DistrictView>)districtsMap.get(av.getCity()));
            
        }
        mv.addObject("provinceList", pves);
        mv.addObject("cityList", cvs);
        mv.addObject("districtList", dvs);
    }
    
    /**
     * 从提交定单页面中来
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/ordermodifyads")
    public ModelAndView modifyOrderReceiveAddress(HttpServletRequest request, @RequestParam("ordertype") String ordertype,
        @RequestParam("isBonded") String isBonded, @RequestParam(value = "source", required = false, defaultValue = "") String source,
        @ModelAttribute("address") AddressView address)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        String viewName = "forward:/ads/toordermdfads/" + address.getAddressId() + "/" + ordertype + "/" + isBonded;
        mv.setViewName(viewName); // 出错　出错留在此页面
        String validateResult = address.validate("");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.addObject("errorCode", validateResult);
            mv.addObject("address", address);
            putPCDToModelAndView(mv, address);
            return mv;
        }
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("ordertype", ordertype);
            mv.setViewName("redirect:/user/tologin"); // / 出错留在此页面
            return mv;
        }
        try
        {
            String result = this.addressService.modifyAddress(address);
            if (null != result && !result.equals(""))
            {
                JsonParser parser = new JsonParser();
                JsonObject param = (JsonObject)parser.parse(result);
                String status = param.get("status").getAsString();
                if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
                {
                    String errorCode = param.get("errorCode").getAsString();
                    if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_MODIFY_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
                    {
                        mv.addObject("errorCode", "请先登录!");
                    }
                    else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_MODIFY_ERRORCODE.ID_CARD_INVALID.getValue()))
                    {
                        mv.addObject("errorCode", "请输入18位身份证号码!");
                    }
                    else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_MODIFY_ERRORCODE.ADDRESSID_NOT_EXIST.getValue()))
                    {
                        mv.addObject("errorCode", "记录被删除!");
                    }
                    mv.addObject("address", address);
                    putPCDToModelAndView(mv, address);
                    return mv;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("AddressController ----orderModifyReceiveAddress 保存失败!", e);
            BusException be = new BusException(CommonConstant.DB_ERROR_MSG, viewName, CommonConstant.DB_ERROR_ERROR_CODE);
            be.putModelObject("address", address);
            throw be;
        }
        if (source != null && source.equals("orderidcardinvalid")) // 从定单界面中来，并身份证号为空
        {
            mv.setViewName("redirect:/order/confirm/" + ordertype);
        }
        else
        {
            mv.setViewName("forward:/ads/listadsmgr"); // 成功
            mv.addObject("ordertype", ordertype);
            mv.addObject("isBonded", isBonded);
            mv.addObject("source", "consignc");
        }
        return mv;
    }
    
    /**
     * 修改管理页面的保存
     * 
     * @param address
     */
    @RequestMapping("/modifyads")
    public ModelAndView modifyReceiveAddress(@ModelAttribute("address") AddressView address, HttpServletRequest request)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        String viewName = "forward:/ads/tomdfads/" + address.getAddressId() + "/" + address.getAccountId(); // 出错返回的视图
        String validateResult = address.validate("");
        if (!validateResult.equals("SUCCESS"))
        {
            mv.setViewName(viewName); // / 出错留在此页面
            mv.addObject("errorCode", validateResult);
            mv.addObject("address", address);
            putPCDToModelAndView(mv, address);
            return mv;
        }
        
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            mv.addObject("islogin", CommonEnum.COMMON_IS.NO.getValue());
            mv.addObject("address", address);
            mv.setViewName("redirect:/user/tologin"); // / 出错留在此页面
            return mv;
        }
        try
        {
            String result = this.addressService.modifyAddress(address);
            if (null != result && !result.equals(""))
            {
                JsonParser parser = new JsonParser();
                JsonObject param = (JsonObject)parser.parse(result);
                String status = param.get("status").getAsString();
                if (status.equals(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue()))
                {
                    String errorCode = param.get("errorCode").getAsString();
                    if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_MODIFY_ERRORCODE.ACCOUNTID_NOT_EXIST.getValue()))
                    {
                        mv.addObject("errorCode", "请先登录!");
                    }
                    else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_MODIFY_ERRORCODE.ID_CARD_INVALID.getValue()))
                    {
                        mv.addObject("errorCode", "请输入18位身份证号码!");
                    }
                    else if (errorCode != null && errorCode.equals(CommonEnum.ADDRESS_MODIFY_ERRORCODE.ADDRESSID_NOT_EXIST.getValue()))
                    {
                        mv.addObject("errorCode", "记录被删除!");
                    }
                    mv.addObject("address", address);
                    mv.setViewName(viewName); // 出错留在此页面
                    putPCDToModelAndView(mv, address);
                    return mv;
                }
            }
        }
        catch (Exception e)
        {
            logger.error("AddressController ----modifyReceiveAddress 保存失败!", e);
            BusException be = new BusException(CommonConstant.DB_ERROR_MSG, viewName, CommonConstant.DB_ERROR_ERROR_CODE);
            be.putModelObject("address", address);
            throw be;
        }
        mv.setViewName("redirect:/ads/listads"); // / 成功跳到地址管理中心
        return mv;
    }
    
    /**
     * 设默认的收货地址 异步请求不刷新页面
     * 
     * @param address
     */
    @RequestMapping("/defaultads")
    @ResponseBody
    public String setDefaultReceiveAddress(@RequestParam("addressId") String addressId, HttpServletRequest request)
        throws Exception
    {
        AccountView av = SessionUtil.getCurrentUser(request.getSession());
        if (av == null)
        {
            JsonObject result = new JsonObject();
            result.addProperty("islogin", CommonEnum.COMMON_IS.NO.getValue()); // 没有login 页面给出提示
            return result.toString();
        }
        String requestParams = "{'accountId':'" + av.getId() + "','addressId':'" + addressId + "'}";
        String result = this.addressService.setDefaultAddress(requestParams);
        return result;
    }
    
    /**
     * 在Order管理中　选择收货地址
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping("/selectedads/{addressId}/{ordertype}")
    public ModelAndView selectedReceiveAddress(HttpServletRequest request, @PathVariable String addressId, @PathVariable String ordertype)
        throws Exception
    {
        ModelAndView mv = new ModelAndView();
        
        SessionUtil.removeCurrentSelectedAddres(request.getSession());
        SessionUtil.addSelectedAddressId(request.getSession(), addressId);
        
        mv.setViewName("redirect:/order/confirm/" + ordertype);
        return mv;
    }
    
}
