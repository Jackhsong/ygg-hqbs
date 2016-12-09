package com.ygg.webapp.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装 业务层到 controller　throws 出的异常
 * 
 * @author lihc
 *
 */
public class BusException extends RuntimeException
{
    
    private String viewName = "";
    
    /**
     * 错误码　 １表示　数据库操作失败如保存报错，删除报错，更新报错等 2表示 3表示 4表示
     */
    private String errorCode = "1";
    
    /**
     * 保存在ModelAndView中的对象
     */
    private Map<String, Object> modelObject = new HashMap<String, Object>();
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public BusException()
    {
        super();
    }
    
    public BusException(String message)
    {
        super(message);
    }
    
    public BusException(String message, Throwable cause, String viewName)
    {
        super(message, cause);
        this.viewName = viewName;
    }
    
    public BusException(String message, Throwable cause, String viewName, String errorCode)
    {
        super(message, cause);
        this.viewName = viewName;
        this.errorCode = errorCode;
    }
    
    public BusException(String message, String viewName)
    {
        super(message);
        this.viewName = viewName;
    }
    
    public BusException(String message, String viewName, String errorCode)
    {
        super(message);
        this.viewName = viewName;
        this.errorCode = errorCode;
    }
    
    public BusException(Throwable cause, String viewName)
    {
        super(cause);
        this.viewName = viewName;
    }
    
    public BusException(Throwable cause, String viewName, String errorCode)
    {
        super(cause);
        this.viewName = viewName;
        this.errorCode = errorCode;
    }
    
    public BusException(String viewName, String errorCode, Map<String, Object> modelObject)
    {
        super();
        this.viewName = viewName;
        this.errorCode = errorCode;
        this.modelObject = modelObject;
    }
    
    public String getViewName()
    {
        return viewName;
    }
    
    public void setViewName(String viewName)
    {
        this.viewName = viewName;
    }
    
    public Map<String, Object> getModelObject()
    {
        return modelObject;
    }
    
    public void setModelObject(Map<String, Object> modelObject)
    {
        this.modelObject = modelObject;
    }
    
    public void putModelObject(String key, Object value)
    {
        if (this.modelObject != null)
            this.modelObject.put(key, value);
    }
    
    public Object getModelObject(String key)
    {
        if (this.modelObject != null && this.modelObject.containsKey(key))
            return this.modelObject.get(key);
        return null;
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
