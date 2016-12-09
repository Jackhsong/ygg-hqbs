package com.ygg.webapp.service.brand.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.dao.brand.QqbsBrandDao;
import com.ygg.webapp.entity.brand.QqbsBrandEntity;
import com.ygg.webapp.service.brand.QqbsBrandService;
import com.ygg.webapp.util.JSONUtils;
import com.ygg.webapp.util.YggWebProperties;

@Repository
public class QqbsBrandServiceImpl implements QqbsBrandService
{

    @Resource
    private QqbsBrandDao qqbsBrandDao;
    
    @Override
    public List<Map<String,Object>> getQqbsBrands() throws Exception
    {
        String baseDefaultUrl = YggWebProperties.getInstance().getProperties("base_default_url");
        List<QqbsBrandEntity> brandList = qqbsBrandDao.getQqbsBrands();
        //Key为ctgId,Value为QqbsBrandEntity
        Map<String,List<QqbsBrandEntity>> ctgMap = new LinkedHashMap<String,List<QqbsBrandEntity>>();
        
        for(QqbsBrandEntity qbe:brandList){
            String ctgName = String.valueOf(qbe.getCtgName());
            if(ctgMap.containsKey(ctgName)){
                ctgMap.get(ctgName).add(qbe);
                ctgMap.put(ctgName, ctgMap.get(ctgName));
            }else{
                List<QqbsBrandEntity> list = new ArrayList<QqbsBrandEntity>();
                list.add(qbe);
                ctgMap.put(ctgName, list);
            }
        }
        
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        
        for(Map.Entry<String, List<QqbsBrandEntity>> map:ctgMap.entrySet()){
            Map<String,Object> resultMap = new LinkedHashMap<String,Object>();
            String categoryName = map.getKey();
            List<QqbsBrandEntity> qbeList = map.getValue();
            List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
            for(QqbsBrandEntity qbe:qbeList){
                Map<String,Object> detail = new HashMap<String,Object>();
                detail.put("brandName", qbe.getBrandName());
                detail.put("image", qbe.getImage());
                detail.put("brandActivitiesCommonId", qbe.getBrandActivitiesCommonId());
                detail.put("url", baseDefaultUrl+"/cnty/toac/" + qbe.getBrandActivitiesCommonId());
                detailList.add(detail);
            }
            resultMap.put("categoryName",categoryName);
            resultMap.put("brands", detailList);
            resultList.add(resultMap);
        }
//        result.add("brandList", parser.parse(JSONUtils.toJson(resultList, false)));
//        return  parser.parse(JSONUtils.toJson(resultList, false)).toString();
        return resultList;
    }

}
