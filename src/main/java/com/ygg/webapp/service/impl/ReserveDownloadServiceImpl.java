package com.ygg.webapp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.dao.ReserveDownloadDao;
import com.ygg.webapp.entity.ReserveDownloadEntity;
import com.ygg.webapp.service.ReserveDownloadService;
import com.ygg.webapp.util.CommonEnum;

@Service("reserveDownloadService")
// 放到spring.xml中去配置
public class ReserveDownloadServiceImpl implements ReserveDownloadService
{
    
    private String storeName; // 上传存储的文件名
    
    private String realName; // 本地文件的真实文件名
    
    private String downLoadContentType; // 下载的contextType
    
    @Resource(name = "reserveDownloadDao")
    private ReserveDownloadDao reserveDownloadDao;
    
    @Override
    public ReserveDownloadEntity findReserveDownLoad(String phonenum)
        throws Exception
    {
        
        return this.reserveDownloadDao.findReserveDownLoad(phonenum);
    }
    
    @Override
    public int insertReserveDownload(ReserveDownloadEntity rde)
        throws Exception
    {
        return this.reserveDownloadDao.insertReserveDownload(rde);
    }
    
    @Override
    public boolean isPhoneNumExists(String phonenum)
        throws Exception
    {
        return this.reserveDownloadDao.isPhoneNumExists(phonenum);
    }
    
    @Override
    public String reserveDownload(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String phonenum = param.get("phonenum").getAsString();
        
        boolean flag = this.reserveDownloadDao.isPhoneNumExists(phonenum);
        if (!flag)
        {
            ReserveDownloadEntity rde = new ReserveDownloadEntity();
            rde.setPhonenum(phonenum);
            this.reserveDownloadDao.insertReserveDownload(rde);
        }
        
        result.addProperty("success", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    public String getStoreName()
    {
        return storeName;
    }
    
    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }
    
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public String getDownLoadContentType()
    {
        return downLoadContentType;
    }
    
    public void setDownLoadContentType(String downLoadContentType)
    {
        this.downLoadContentType = downLoadContentType;
    }
    
}
