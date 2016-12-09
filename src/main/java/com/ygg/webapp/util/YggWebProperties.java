package com.ygg.webapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class YggWebProperties
{
    
    private Logger logger = Logger.getLogger(YggWebProperties.class);
    
    private static YggWebProperties instance = new YggWebProperties();
    
    private Properties props = null;
    
    private YggWebProperties()
    {
        props = new Properties();
        
    }
    
    public void init()
    {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("yggwebapp.properties");
        try
        {
            if (props == null)
                props = new Properties();
            
            props.load(in);
        }
        catch (IOException e)
        {
            logger.error("IOException", e);
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                logger.error("IOException", e);
            }
        }
    }
    
    public static YggWebProperties getInstance()
    {
        return instance;
    }
    
    public String getProperties(String key)
    {
        Object value = props.get(key);
        if (value == null)
            return null;
        return value.toString();
    }
}
