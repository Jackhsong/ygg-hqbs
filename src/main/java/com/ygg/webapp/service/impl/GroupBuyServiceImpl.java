package com.ygg.webapp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ygg.webapp.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.code.AppUrlTypeEnum;
import com.ygg.webapp.code.BusinessResponseMessage;
import com.ygg.webapp.dao.GroupBuyDao;
import com.ygg.webapp.dao.ProductCommonDao;
import com.ygg.webapp.entity.GroupProductCodeEntity;
import com.ygg.webapp.entity.ProductCommonEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.GroupBuyService;

@Service("groupBuyService")
public class GroupBuyServiceImpl implements GroupBuyService
{
    Logger log = Logger.getLogger(GroupBuyServiceImpl.class);
    
    @Resource
    private GroupBuyDao groupBuyDao;
    
    @Resource
    private ProductCommonDao productCommonDao;
    
    @Override
    public Map<String, Object> findGroupProductInfo(int groupProductId)
        throws ServiceException
    {
        List<Integer> tempGroupActivitiesIds = new ArrayList<>();
        tempGroupActivitiesIds.add(6438);
        tempGroupActivitiesIds.add(6439);
        tempGroupActivitiesIds.add(6471);
        tempGroupActivitiesIds.add(6440);
        tempGroupActivitiesIds.add(6470);
        tempGroupActivitiesIds.add(6469);
        tempGroupActivitiesIds.add(6468);
        tempGroupActivitiesIds.add(6467);
        tempGroupActivitiesIds.add(6455);

        GroupProductCodeEntity gpce = groupBuyDao.findGroupProductCodeById(groupProductId);
        if (gpce == null)
        {
            Map<String, Object> result = new HashMap<>();
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.put("msg", BusinessResponseMessage.PARAMETER_ERROR.getMessage());
            return result;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("isAvailable", gpce.getIsAvailable());
        result.put("code", gpce.getCode());
        ProductCommonEntity pce = productCommonDao.findProductCommonInfoById(gpce.getProductId());
        double groupPrice = 0.0;
        if (tempGroupActivitiesIds.contains(gpce.getProductId()))
        {
            groupPrice = 9.9;
        }
        else
        {
            groupPrice = pce.getSalesPrice() * 0.1 >= 10 ? pce.getSalesPrice() - 10.0 : pce.getSalesPrice() * 0.9;
        }
        result.put("groupPrice", StringUtil.removeLastZero(MathUtil.round(groupPrice, 2)));
        result.put("salesPrice", StringUtil.removeLastZero(MathUtil.round(pce.getSalesPrice(), 2)));
        result.put("productImg", pce.getMediumImage());
        result.put("name", pce.getName());
        result.put("shortName", pce.getShortName());
        String[] idArr = new String[] {pce.getProductId() + "", gpce.getCode()};
        String joinUrl =
            pce.getType() == 1 ? StringUtil.replacePlaceholder(AppUrlTypeEnum.SALE_PRODUCT.getDescription(), idArr)
                : StringUtil.replacePlaceholder(AppUrlTypeEnum.MALL_PRODUCT.getDescription(), idArr);
        result.put("joinUrl", joinUrl);
        String url = YggWebProperties.getInstance().getProperties("domain_name");
        if(pce.getType() == Integer.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue())){
            url = url + "/item-"+pce.getProductId()+".htm";
        }else if(pce.getType() == Integer.valueOf(CommonEnum.PRODUCT_TYPE.MALL.ordinal())){
            url = url + "/mitem-"+pce.getProductId()+".htm";
        }
        result.put("url", url);
        List<Map<String, Object>> gaife = groupBuyDao.findGroupAccountInfoByGroupProductCodeId(gpce.getId());
        List<Map<String, Object>> groupPeople = getGroupPeopleInfo(gaife);
        result.put("groupPeople", groupPeople);
        return result;
    }
    
    private List<Map<String, Object>> getGroupPeopleInfo(List<Map<String, Object>> gaife)
    {
        List<Map<String, Object>> groupPeople = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < gaife.size(); i++)
        {
            Map<String, Object> it = gaife.get(i);
            Map<String, Object> people = new HashMap<String, Object>();
            String name = it.get("nickname") == null || "".equals(it.get("nickname")) ? it.get("name") + "" : it.get("nickname") + "";
            people.put("username", SimulationUtils.hidingUsername( name, true));
            people.put("image", it.get("image") + "");
            people.put("isHead", it.get("isHead") + "");
            try
            {
                people.put("groupTime", DateTimeUtil.timestampStringToWebString(it.get("createTime") + ""));
            }
            catch (Exception e)
            {
                people.put("groupTime", "");
            }
            // 从数据库查询出来的时候已经有顺序了
            if ("1".equals(it.get("isHead") + ""))
            {
                people.put("detailMsg", "开团");
            }
            else
            {
                if (i >= 2)
                {
                    people.put("detailMsg", "组团成功");
                }
                else
                {
                    people.put("detailMsg", "入团");
                }
            }
            groupPeople.add(people);
        }
        return groupPeople;
    }
    

}
