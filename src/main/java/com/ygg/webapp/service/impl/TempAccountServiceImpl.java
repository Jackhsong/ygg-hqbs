package com.ygg.webapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.TempAccountDao;
import com.ygg.webapp.service.TempAccountService;
import com.ygg.webapp.util.CommonConstant;

@Service("tempAccountService")
public class TempAccountServiceImpl implements TempAccountService
{
    
    @Resource(name = "tempAccountDao")
    private TempAccountDao tempAccountDao;
    
    @Override
    public int findTempAccountIdByUUID(String imei)
        throws Exception
    {
        return this.tempAccountDao.findIdByImei(imei);
    }
    
    @Override
    public int addTempAccount(String imei)
        throws Exception
    {
        
        int tempAccountId = this.tempAccountDao.findIdByImei(imei);
        if (tempAccountId == CommonConstant.ID_NOT_EXIST)
            return this.tempAccountDao.addTempAccount(imei);
        return 0;
    }
    
}
