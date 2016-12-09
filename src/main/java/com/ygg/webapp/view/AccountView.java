package com.ygg.webapp.view;

import com.ygg.webapp.util.CommonUtil;

public class AccountView extends BaseView
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    
    private String name;
    
    private String pwd;
    
    private byte type;
    
    private String nickname;
    
    private String mobileNumber;
    
    /**
     * 返回是否login 成功 取　CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED
     */
    private String status;
    
    /**
     * errorCode CommonEnum.ACCOUNT_LOGIN_ERRORCODE.MOBILENUMBER_NOT_EXIST
     */
    private String errorCode;
    
    private String image;
    
    public AccountView()
    {
        
    }
    
    public AccountView(String name, String pwd, String mobileNumber)
    {
        super();
        this.name = name;
        this.pwd = pwd;
        this.mobileNumber = mobileNumber;
    }
    
    public AccountView(String name, String pwd)
    {
        super();
        this.name = name;
        this.pwd = pwd;
    }
    
    public String validate(String jsonparams)
    {
        String result = "SUCCESS";
        if (this.name == null || this.name.equals(""))
            result = "手机号不能为空";
        else if (this.name != null && this.name.length() > 20)
            result = "手机号长度不对";
        else if (this.name != null && !CommonUtil.isMobile(this.name))
            result = "手机号格式不对";
        else if (this.pwd == null || this.pwd.equals(""))
            result = "密码不能为空";
        else if (this.pwd != null && this.pwd.length() > 16)
            result = "密码长度太长";
        return result;
    };
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPwd()
    {
        return pwd;
    }
    
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public byte getType()
    {
        return type;
    }
    
    public void setType(byte type)
    {
        this.type = type;
    }
    
    public String getNickname()
    {
        return nickname;
    }
    
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
    
    public String getMobileNumber()
    {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }
    
    public String getImage()
    {
        return image;
    }
    
    public void setImage(String image)
    {
        this.image = image;
    }
    
    @Override
    public String toString()
    {
        return this.id + "_" + this.name + "_" + this.mobileNumber;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
}
