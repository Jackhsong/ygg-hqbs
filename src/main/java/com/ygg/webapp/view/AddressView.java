package com.ygg.webapp.view;

public class AddressView extends BaseView
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private String accountId;
    
    private String addressId;
    
    private String fullName;
    
    private String mobileNumber;
    
    private String province;
    
    private String city;
    
    private String district;
    
    private String detailAddress;
    
    private String idCard;
    
    /**
     * 暂一个一个的判断，以后可以返回一个json
     */
    public String validate(String jsonparams)
    {
        String result = "SUCCESS";
        if (this.fullName == null || this.fullName.equals(""))
            result = "收货人不能为空";
        else if (this.fullName != null && this.fullName.length() > 20)
            result = "收货人长度不能超过20";
        else if (this.mobileNumber == null || this.mobileNumber.equals(""))
            result = "手机号码不能为空";
        else if (this.mobileNumber != null && this.mobileNumber.length() > 30)
            result = "手机号码长度不能超过30";
        else if (this.province != null && this.province.equals(""))
            result = "请选择省份";
        else if (this.city != null && this.city.equals(""))
            result = "请选择城市";
        else if (this.district != null && this.district.equals(""))
            result = "请选择地区";
        else if (this.detailAddress == null || this.detailAddress.equals(""))
            result = "详细地址不能为空";
        else if (this.detailAddress != null && this.detailAddress.length() > 200)
            result = "详细地址长度不能超过200";
        return result;
    };
    
    public String getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
    
    public String getAddressId()
    {
        return addressId;
    }
    
    public void setAddressId(String addressId)
    {
        this.addressId = addressId;
    }
    
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getDistrict()
    {
        return district;
    }
    
    public void setDistrict(String district)
    {
        this.district = district;
    }
    
    public String getDetailAddress()
    {
        return detailAddress;
    }
    
    public void setDetailAddress(String detailAddress)
    {
        this.detailAddress = detailAddress;
    }
    
    public String getIdCard()
    {
        return idCard;
    }
    
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }
    
}
