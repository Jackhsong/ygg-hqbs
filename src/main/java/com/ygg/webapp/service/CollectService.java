package com.ygg.webapp.service;

import java.util.Map;

public interface CollectService
{
    /**
     * 添加收藏
     * 
     * @param para
     * @return
     * @throws Exception
     */
    Map<String, Object> add(Map<String, Object> para)
        throws Exception;
}
