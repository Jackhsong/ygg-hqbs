package com.ygg.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.SaleWindowDao;
import com.ygg.webapp.service.SaleWindowService;

@Service("saleWindowService")
public class SaleWindowServiceImpl implements SaleWindowService
{
    
    @Resource(name = "saleWindowDao")
    private SaleWindowDao saleWindowDao;
    
    @Override
    public String addSaleTag(String requestParams)
        throws Exception
    {
        return null;
    }
    
    @Override
    public String addSaleWindow(String sw)
        throws Exception
    {
        return null;
    }
    
    @Override
    public List<Integer> findAllSaleWindowIds()
        throws Exception
    {
        
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> nowList = this.saleWindowDao.findCurrDisplayNowId();
        List<Integer> laterList = this.saleWindowDao.findCurrDisplayLaterId();
        
        if (nowList != null && !nowList.isEmpty())
            list.addAll(nowList);
        if (laterList != null && !laterList.isEmpty())
            list.addAll(laterList);
        
        return list;
    }
    
}
