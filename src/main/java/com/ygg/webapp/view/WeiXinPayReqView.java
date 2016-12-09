package com.ygg.webapp.view;

public class WeiXinPayReqView extends BaseView
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String appid;
    
    private String mch_id;
    
    private String nonce_str;
    
    private String sign;
    
    private String trade_type;
    
    private String prepay_id;
    
    private String code_url;
    
    private String packageValue;
    
    private String signType;
    
    private String timestamp;
    
    private String orderIds;
    
    public String getAppid()
    {
        return appid;
    }
    
    public void setAppid(String appid)
    {
        this.appid = appid;
    }
    
    public String getMch_id()
    {
        return mch_id;
    }
    
    public void setMch_id(String mch_id)
    {
        this.mch_id = mch_id;
    }
    
    public String getNonce_str()
    {
        return nonce_str;
    }
    
    public void setNonce_str(String nonce_str)
    {
        this.nonce_str = nonce_str;
    }
    
    public String getSign()
    {
        return sign;
    }
    
    public void setSign(String sign)
    {
        this.sign = sign;
    }
    
    public String getTrade_type()
    {
        return trade_type;
    }
    
    public void setTrade_type(String trade_type)
    {
        this.trade_type = trade_type;
    }
    
    public String getPrepay_id()
    {
        return prepay_id;
    }
    
    public void setPrepay_id(String prepay_id)
    {
        this.prepay_id = prepay_id;
    }
    
    public String getCode_url()
    {
        return code_url;
    }
    
    public void setCode_url(String code_url)
    {
        this.code_url = code_url;
    }
    
    public String getPackageValue()
    {
        return packageValue;
    }
    
    public void setPackageValue(String packageValue)
    {
        this.packageValue = packageValue;
    }
    
    public String getSignType()
    {
        return signType;
    }
    
    public void setSignType(String signType)
    {
        this.signType = signType;
    }
    
    public String getTimestamp()
    {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }
    
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
    
}
