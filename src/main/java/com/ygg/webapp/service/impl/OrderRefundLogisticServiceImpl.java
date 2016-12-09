package com.ygg.webapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.dao.OrderProductRefundLogisticDao;
import com.ygg.webapp.entity.OrderProductRefundLogisticsEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.OrderRefundLogisticService;
import com.ygg.webapp.util.CommonEnum;

@Service("orderRefundLogisticService")
public class OrderRefundLogisticServiceImpl implements OrderRefundLogisticService
{
    
    @Resource(name = "orderProductRefundLogisticDao")
    private OrderProductRefundLogisticDao orderProductRefundLogisticDao;
    
    @Override
    public String addLogisticInfo(String resquestParams)
        throws ServiceException
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        JsonObject params = (JsonObject)parser.parse(resquestParams);
        
        String channel = params.get("channel").getAsString();
        String number = params.get("number").getAsString();
        
        OrderProductRefundLogisticsEntity oprfe = new OrderProductRefundLogisticsEntity();
        oprfe.setChannel(channel);
        oprfe.setNumber(number);
        if (this.orderProductRefundLogisticDao.insertOrderProductRefundLogistic(oprfe) == 0)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
            return result.toString();
        }
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
}
