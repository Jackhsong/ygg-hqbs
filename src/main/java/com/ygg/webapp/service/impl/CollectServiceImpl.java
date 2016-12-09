package com.ygg.webapp.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.CollectDao;
import com.ygg.webapp.dao.SaleWindowDao;
import com.ygg.webapp.entity.SaleWindowEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.CollectService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.view.AccountView;

@Service("collectService")
public class CollectServiceImpl implements CollectService
{
    Logger log = Logger.getLogger(CollectServiceImpl.class);

    @Resource
    private SaleWindowDao swdi;
    
    @Resource
    private CollectDao collectDao;

    @Override
    public Map<String, Object> add(Map<String, Object> para)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int id = Integer.parseInt(para.get("id") == null ? "0" : para.get("id") + "");
        int type = Integer.parseInt(para.get("type") == null ? "1" : para.get("type") + "");
        AccountView av = (AccountView)para.get("av");
        
        if (type == Byte.parseByte(CommonEnum.COLLECT_TYPE.SALE.getValue()))
        {
            SaleWindowEntity swe = swdi.findSaleInfoById(id);
            if (swe == null)
            {
                result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.put("msg", "特卖不存在~");
                return result;
            }
            
            if (swe.getType() == CommonEnum.SALE_TYPE.PRODUCT.getValue()) // 收藏特卖如果指向一个商品，也需收藏该商品
            {
                try
                {
                    int rs = collectDao.addAccountCollectProduct(av.getId(), swe.getDisplayId());
                    if (rs == 0)
                    {
                        throw new ServiceException("addAccountCollectProduct出错！");
                    }
                }
                catch (Exception e)
                {
                    if (!e.getMessage().contains("Duplicate entry"))
                    {
                        throw new ServiceException("addAccountCollectProduct出错！", e);
                    }
                }
            }
            else if (swe.getType() == CommonEnum.SALE_TYPE.ACTIVITIES_COMMON.getValue()) // 收藏特卖如果指向一个通用专场，也需收藏该通用专场
            {
                try
                {
                    int rs = collectDao.addAccountCollectActivitiesCommon(av.getId(), swe.getDisplayId());
                    if (rs == 0)
                    {
                        throw new ServiceException("addAccountCollectActivitiesCommon出错！");
                    }
                }
                catch (Exception e)
                {
                    if (!e.getMessage().contains("Duplicate entry"))
                    {
                        throw new ServiceException("addAccountCollectActivitiesCommon出错！", e);
                    }
                }
            }
            
            try
            {
                int rs = collectDao.addAccountCollectSale(av.getId(), id);
                if (rs == 0)
                {
                    throw new ServiceException("addAccountCollectSale出错！");
                }
            }
            catch (Exception e)
            {
                if (!e.getMessage().contains("Duplicate entry"))
                {
                    throw new ServiceException("addAccountCollectSale出错！", e);
                }
            }
        }
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result;
    }
    
    
}
