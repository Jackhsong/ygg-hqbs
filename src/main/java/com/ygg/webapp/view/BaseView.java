package com.ygg.webapp.view;

import java.io.Serializable;

public class BaseView implements Serializable
{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 留给子类判断长度，一些校验 成功返回SUCCESS 不成功返回json对象如:{'errorCode':'名称长度不能超过5'}或{'errorCode':['name':'长度太长','code':'编码类型不对']}
     * 也可以自定义格式
     * 
     * @return
     */
    public String validate(String jsonparams)
    {
        return "SUCCESS";
    };
    
    public String url;
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
}
