package com.ygg.webapp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class CommonHttpClient
{
    private static final Logger logger = LoggerFactory.getLogger(CommonHttpClient.class);
    
    public static Map<String, Object> commonHTTP(String method, String url, Map<String, Object> parameters)
    {
        HttpClient httpClient = HttpClients.createDefault();
        try
        {
            
            if ("get".equals(method.toLowerCase()))
            {
                HttpGet httpGet = new HttpGet();
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                
                for (Iterator<Map.Entry<String, Object>> it = parameters.entrySet().iterator(); it.hasNext();)
                {
                    Map.Entry<String, Object> o = (Map.Entry<String, Object>)it.next();
                    BasicNameValuePair pair = new BasicNameValuePair(o.getKey(), o.getValue().toString()); //
                    nvps.add(pair);
                }
                
                httpGet.setURI(URI.create(url + "?" + URLEncodedUtils.format(nvps, HTTP.UTF_8)));
                HttpResponse httpResponse = httpClient.execute(httpGet);
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status == 200)
                {
                    HttpEntity entity = httpResponse.getEntity();
                    
                    JSONReader jsonReader = new JSONReader(new InputStreamReader(entity.getContent(), "utf-8"));
                    JSONObject j = JSON.parseObject(jsonReader.readString());
                    
                    // System.out.println(j.toJSONString());
                    // String response = parserResponse(entity);
                    // System.out.println(response);
                    return j;
                }
            }
            else if ("post".equals(method.toLowerCase()))
            {
                HttpPost httpPost = new HttpPost(url);
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Iterator<Map.Entry<String, Object>> it = parameters.entrySet().iterator(); it.hasNext();)
                {
                    Map.Entry<String, Object> o = (Map.Entry<String, Object>)it.next();
                    BasicNameValuePair pair = new BasicNameValuePair(o.getKey(), o.getValue().toString());
                    nvps.add(pair);
                }
                
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
                
                HttpResponse httpResponse = httpClient.execute(httpPost);
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status == 200)
                {
                    HttpEntity entity = httpResponse.getEntity();
                    // String response = parserResponse(entity);
                    // System.out.println(response);
                    
                    JSONReader jsonReader = new JSONReader(new InputStreamReader(entity.getContent()));
                    JSONObject j = JSON.parseObject(jsonReader.readString());
                    System.out.println(j.toJSONString());
                    return j;
                }
                
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            httpClient.getConnectionManager().shutdown();
        }
        return null;
        
    }
    
    public static Map<String, Object> commonHTTP2(String url, JSONObject parameters)
    {
        HttpClient httpClient = HttpClients.createDefault();
        try
        {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(parameters.toJSONString(), "UTF-8"));
            
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status == 200)
            {
                HttpEntity entity = httpResponse.getEntity();
                // String response = parserResponse(entity);
                // System.out.println(response);
                JSONReader jsonReader = new JSONReader(new InputStreamReader(entity.getContent()));
                JSONObject j = JSON.parseObject(jsonReader.readString());
                // System.out.println(j.toJSONString());
                jsonReader.close();
                return j;
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            httpClient.getConnectionManager().shutdown();
        }
        return null;
        
    }
    
    public static JSONObject sendXmlHTTP(String url, String xmlData)
    {
        HttpClient httpClient = HttpClients.createDefault();
        try
        {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(xmlData, "UTF-8"));
            httpPost.addHeader("Content-Type", "text/xml");
            HttpResponse httpResponse = httpClient.execute(httpPost);
            int status = httpResponse.getStatusLine().getStatusCode();
            if (status == 200)
            {
                HttpEntity entity = httpResponse.getEntity();
                
                return parseXml(entity.getContent());
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接 ,释放资源
            httpClient.getConnectionManager().shutdown();
        }
        return null;
    }
    
    @SuppressWarnings("unused")
    private static String parserResponse(HttpEntity entity)
        throws IOException
    {
        StringBuffer contentBuffer = new StringBuffer();
        InputStream in = entity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String inputLine = null;
        while ((inputLine = reader.readLine()) != null)
        {
            contentBuffer.append(inputLine);
            contentBuffer.append("/n");
        }
        // 去掉结尾的换行符
        contentBuffer.delete(contentBuffer.length() - 2, contentBuffer.length());
        in.close();
        return contentBuffer.toString();
    }
    
    public static JSONObject parseXml(InputStream inputStream)
        throws Exception
    {
        JSONObject jsonObject = new JSONObject();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        @SuppressWarnings("unchecked")
        List<Element> elementList = root.elements();
        
        // 遍历所有子节点
        for (Element e : elementList)
        {
            jsonObject.put(e.getName(), e.getText());
        }
        
        // 释放资源
        inputStream.close();
        inputStream = null;
        
        return jsonObject;
    }
    
    
    public static JSONObject getUserInfo(String access_token, String openid)
    {
        
        JSONObject parameters = new JSONObject();
        parameters.put("access_token", access_token);
        parameters.put("openid", openid);
        parameters.put("lang", "zh_CN");
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP("get", "https://api.weixin.qq.com/cgi-bin/user/info", parameters);
        if (j.getString("errcode") != null)
        {
            logger.info("获取用户基本信息失败" + j.toJSONString());
            return null;
            
        }
        else
        {
            logger.info("获取用户基本信息成功" + j.toJSONString());
            return j;
            
        }
        
    }
    
    // 发送客服消息
    public static JSONObject messageCustomSend(String token, String openId, String content)
    {
        JSONObject parameters = new JSONObject();
        JSONObject jcontent = new JSONObject();
        jcontent.put("content", content);
        parameters.put("touser", openId);
        parameters.put("msgtype", "text");
        parameters.put("text", jcontent);
        JSONObject j = (JSONObject)CommonHttpClient.commonHTTP2("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + token, parameters);
        return j;
        
    }
    
    public static void main(String[] args)
    {
        // System.out.println(CommonHttpClient.messageCustomSend(CommonHttpClient.getToken(),
        // "oaB9JxLPZtmkux5lnXB1fESZr0Jk", "<a href=\"http://m.gegejia.com\">123</a>"));
        
        String index_url =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx50718ec381121bd5&redirect_uri=http%3a%2f%2fwww.waibao58.com%2fhunting%2f&response_type=code&scope=snsapi_userinfo&state=0#wechat_redirect";
        String index_url2 =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx50718ec381121bd5&redirect_uri=http%3a%2f%2fwww.waibao58.com%2fhunting%2fcelebrity.html&response_type=code&scope=snsapi_userinfo&state=0#wechat_redirect";
        StringBuffer sb = new StringBuffer();
        sb.append("Hi！ 欢迎加入左岸城堡，同时恭喜您成为左岸城堡的预备代言人，您只需点击");
        sb.append("<a href=\"" + index_url + "\">环球美食</a>");
        sb.append("购买任意美食，即可成为代言人，分享专属二维码，积累粉丝，赢丰厚奖金。详细模式");
        sb.append("<a href=\"" + index_url2 + "\">点击查看</a>");
        sb.append("。");
    }
    
}
