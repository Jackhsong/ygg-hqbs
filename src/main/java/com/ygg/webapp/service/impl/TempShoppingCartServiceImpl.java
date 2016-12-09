package com.ygg.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.ygg.webapp.dao.TempAccountDao;
import com.ygg.webapp.dao.TempShoppingCartDao;
import com.ygg.webapp.entity.TempShoppingCartEntity;
import com.ygg.webapp.service.TempShoppingCartService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.view.TempShoppingCartView;

@Service("tempShoppingCartService")
public class TempShoppingCartServiceImpl implements TempShoppingCartService
{
    
    @Resource(name = "tempShoppingCartDao")
    private TempShoppingCartDao tempShoppingCartDao;
    
    @Resource(name = "tempAccountDao")
    private TempAccountDao tempAccountDao;
    
    @Override
    public List<TempShoppingCartView> listTempShoppingCartInfo(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String addTempProductToShoppingCart(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String removeTempProductToShoppingCart(String requestParams)
        throws Exception
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public String findTempShoppingCartByAccountIdAndProductId(int accountId, int productId)
        throws Exception
    {
        int productcount = 0;
        JsonObject result = new JsonObject();
        int cartCount = this.tempShoppingCartDao.findTmpShoppingCartCountByAccountId(accountId);
        TempShoppingCartEntity tsce = this.tempShoppingCartDao.findNormalCartByPIdAndAId(productId, accountId);
        if (tsce != null)
            productcount = tsce.getProductCount();
        result.addProperty("cartCount", cartCount);
        result.addProperty("productcount", productcount); // / 这个没login的账号在购物车内此productId的数量
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String findTmpShoppingCartCountByAccountId(int tempAccountId)
        throws Exception
    {
        JsonObject result = new JsonObject();
        int cartCount = this.tempShoppingCartDao.findTmpShoppingCartCountByAccountId(tempAccountId);
        result.addProperty("cartCount", cartCount);
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String findTempShoppingCartByAccountIdAndProductId(String tmpUUID, int productId)
        throws Exception
    {
        int productcount = 0;
        int cartCount = 0;
        int tempAccountId = CommonConstant.ID_NOT_EXIST;
        JsonObject result = new JsonObject();
        tempAccountId = this.tempAccountDao.findIdByImei(tmpUUID);
        String endSecond = "";
        if (tempAccountId != CommonConstant.ID_NOT_EXIST)
        {
            cartCount = this.tempShoppingCartDao.findTmpShoppingCartCountByAccountId(tempAccountId);
            TempShoppingCartEntity tsce = this.tempShoppingCartDao.findNormalCartByPIdAndAId(productId, tempAccountId);
            if (tsce != null)
                productcount = tsce.getProductCount();
            
            String validTimeStr = this.tempShoppingCartDao.findValidTimeByAid(tempAccountId);
            if (validTimeStr != null && !validTimeStr.equals(""))
            {
                Date validTime = CommonUtil.string2Date(validTimeStr, "yyyy-MM-dd HH:mm:ss");
                if (new Date().before(validTime)) // 购物车锁定没过期
                {
                    endSecond = (validTime.getTime() - new Date().getTime()) / 1000 + "";
                }
            }
            
        }
        
        result.addProperty("endSecond", endSecond);
        result.addProperty("cartCount", cartCount);
        result.addProperty("productcount", productcount); // / 这个没login的账号在购物车内此productId的数量
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String findValidTimeByAid(int tempAccountId)
        throws Exception
    {
        
        return this.tempShoppingCartDao.findValidTimeByAid(tempAccountId);
    }
    
    @Override
    public TempShoppingCartView findNormalCartByPIdAndAId(int productId, int accountId)
        throws Exception
    {
        
        TempShoppingCartEntity tsce = this.tempShoppingCartDao.findNormalCartByPIdAndAId(productId, accountId);
        TempShoppingCartView tscv = new TempShoppingCartView();
        if (tsce != null)
        {
            tscv.setId(tsce.getId());
            tscv.setTempAccountId(tsce.getTempAccountId());
            tscv.setProductCount(tsce.getProductCount());
            tscv.setProductId(tsce.getProductId());
        }
        return tscv;
    }
    
    public int findProductCountByAIdAndPId(int accountId, int productId)
        throws Exception
    {
        return this.tempShoppingCartDao.findProductCountByAIdAndPId(accountId, productId);
    }
    
}
