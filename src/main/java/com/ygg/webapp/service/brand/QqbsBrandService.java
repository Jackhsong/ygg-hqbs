package com.ygg.webapp.service.brand;

import java.util.List;
import java.util.Map;

public interface QqbsBrandService
{
    /**
     * 获取品牌栏目和品牌馆信息
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getQqbsBrands() 
            throws Exception;
}
