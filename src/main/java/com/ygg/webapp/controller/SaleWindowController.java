package com.ygg.webapp.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.service.ResourceService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.JSONUtils;

/**
 *
 * 特卖
 * 
 * @author lihc
 *
 */
@Controller("saleWindowController")
@RequestMapping("/salewindow")
public class SaleWindowController
{
    
    @Resource(name = "resourceService")
    private ResourceService resourceService;
    
    /**
     * 得到首页所有的请求倒计时
     * @param swid 特卖ids
     * @param nowOrLater 0 表示now 1 表示later
     * @return
     * @throws Exception
     */
    @RequestMapping("/getendsecond")
    @ResponseBody
    public String getEndSecond(@RequestParam(value = "swid", required = false, defaultValue = "0") String swid,
        @RequestParam(value = "noworlater", required = false, defaultValue = "0") String nowOrLater)
        throws Exception
    {
        JsonObject result = new JsonObject();
        if (swid == null || swid.equals("") || swid.equals("0"))
        {
            result.addProperty("success", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            return result.toString();
        }
        JsonParser parser = new JsonParser();
        List<Integer> swIds = new ArrayList<Integer>();
        String[] swIdArrays = swid.split(",");
        for (String sid : swIdArrays)
            swIds.add(new Integer(sid));
        List<Map<String, Object>> map = this.resourceService.findStartEndTimeById(swIds);
        if (map != null && !map.isEmpty())
        {
            Calendar cur = Calendar.getInstance();
            for (Map<String, Object> record : map)
            {
                int saleTimeType = Integer.valueOf(record.get("saleTimeType") + "");
                String endSecond = "0";
                if (nowOrLater != null && nowOrLater.equals("0"))
                {
                    // 正在热卖
                    Calendar end = Calendar.getInstance();
                    Date endDate = CommonUtil.string2Date(record.get("endTime") + "", "yyyyMMdd");
                    end.setTime(endDate);
                    end.add(Calendar.DAY_OF_YEAR, 1);
                    if (saleTimeType == Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_10.getValue()))
                    {
                        end.set(Calendar.HOUR_OF_DAY, 10);
                    }
                    else
                    {
                        end.set(Calendar.HOUR_OF_DAY, 20);
                    }
                    endSecond = ((end.getTimeInMillis() - cur.getTimeInMillis()) / 1000) + "";
                    // record.put("endSecond", endSecond) ;
                }
                else if (nowOrLater != null && nowOrLater.equals("1"))
                {
                    // 即将开始
                    Calendar start = Calendar.getInstance();
                    Date startDate = CommonUtil.string2Date(record.get("startTime") + "", "yyyyMMdd");
                    start.setTime(startDate);
                    if (saleTimeType == Integer.valueOf(CommonEnum.SALE_TIME_TYPE.SALE_10.getValue()))
                    {
                        start.set(Calendar.HOUR_OF_DAY, 10);
                    }
                    else
                    {
                        start.set(Calendar.HOUR_OF_DAY, 20);
                    }
                    endSecond = ((start.getTimeInMillis() - cur.getTimeInMillis()) / 1000) + "";
                }
                record.put("endSecond", endSecond);
            }
        }
        
        result.add("endseconds", parser.parse(JSONUtils.toJson(map, false)));
        ;
        result.addProperty("success", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        // System.out.println("----------getEndSecond----------- "+result.toString());
        
        return result.toString();
    }
    
}
