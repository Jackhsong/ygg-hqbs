package com.ygg.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.util.JSONUtils;

/**
 * @author wuhy
 * @date 创建时间：2016年5月14日 下午1:50:40
 */
public class GSonNullTest
{
    private static final String NOT_CONTAINS_NULL_PARAMS = "{\"propertyA\":\"valueA\"}";
    
    private static final String CONTAINS_NULL_VALUE_PARAMS = "{\"propertyA\":null}";
    
    private static final String CONTAINS_NULL_KEY_PARAMS = "{\"propertyB\":null}";
    
    private JsonParser parser = new JsonParser();
    
    @Test
    public void rseNotContainsNullParams()
    {
        
        JsonObject params = (JsonObject)parser.parse(NOT_CONTAINS_NULL_PARAMS);
        assertEquals("parse not contains null params successfully", params.get("propertyA").getAsString(), "valueA");
        
    }
    
    @Test
    public void parseContainsNullValueParams()
    {
        
        JsonObject params = (JsonObject)parser.parse(CONTAINS_NULL_VALUE_PARAMS);
        assertEquals("parse not contains null params successfully", params.get("propertyA") == null, false);
        assertEquals("parse not contains null params successfully", params.get("propertyA").isJsonNull(), true);
        
    }
    
    @Test
    public void parseContainsNullKeyParams()
    {
        
        JsonObject params = (JsonObject)parser.parse(CONTAINS_NULL_KEY_PARAMS);
        assertEquals("parse not contains null params successfully", params.get("propertyA") == null, true);
        
    }
    
    @Test
    public void getDefaultValueWhenKeyIsNull()
    {
        
        JsonObject params = (JsonObject)parser.parse(CONTAINS_NULL_KEY_PARAMS);
        String value = JSONUtils.getValue(params, "propertyA", "defaultValue");
        assertEquals("parse not contains null params successfully", value, "defaultValue");
        
    }
    
    @Test
    public void getDefaultValueWhenValueIsNull()
    {
        
        JsonObject params = (JsonObject)parser.parse(CONTAINS_NULL_VALUE_PARAMS);
        String value = JSONUtils.getValue(params, "propertyA", "defaultValue");
        assertEquals("parse not contains null params successfully", value, "defaultValue");
        
    }
}
