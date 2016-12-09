package com.ygg.webapp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.PlatformConfigDao;
import com.ygg.webapp.service.PlatformConfigService;

@Service("platformConfigService")
public class PlatformConfigServiceImpl implements PlatformConfigService
{
    
    @Resource(name = "platformConfigDao")
    private PlatformConfigDao platformConfigDao;
    
    @Override
    public List<Map<String, Object>> findAllPlateformConfig()
    {
        return platformConfigDao.findAllPlateformConfig();
    }
    
}
