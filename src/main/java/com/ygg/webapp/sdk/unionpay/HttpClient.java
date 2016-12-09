/*     */package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.io.BufferedReader;
/*     */
import java.io.IOException;
/*     */
import java.io.InputStream;
/*     */
import java.io.InputStreamReader;
/*     */
import java.io.PrintStream;
/*     */
import java.io.UnsupportedEncodingException;
/*     */
import java.net.HttpURLConnection;
/*     */
import java.net.MalformedURLException;
/*     */
import java.net.ProtocolException;
/*     */
import java.net.URISyntaxException;
/*     */
import java.net.URL;
/*     */
import java.net.URLConnection;
/*     */
import java.net.URLEncoder;
/*     */
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/*     */
/*     */
/*     */public class HttpClient
/*     */
{
    /*     */private URL url;
    
    /*     */private int connectionTimeout;
    
    /*     */private int readTimeOut;
    
    /*     */private String result;
    
    /*     */
    /*     */public String getResult()
    /*     */
    {
        /* 64 */return this.result;
        /*     */
    }
    
    /*     */
    /*     */public void setResult(String result)
    /*     */
    {
        /* 72 */this.result = result;
        /*     */
    }
    
    /*     */
    /*     */public HttpClient(String url, int connectionTimeout, int readTimeOut)
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 83 */this.url = new URL(url);
            /* 84 */this.connectionTimeout = connectionTimeout;
            /* 85 */this.readTimeOut = readTimeOut;
            /*     */
        }
        catch (MalformedURLException e)
        {
            /* 87 */e.printStackTrace();
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public int send(Map<String, String> data, String encoding)
        /*     */throws Exception
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 100 */HttpURLConnection httpURLConnection = createConnection(encoding);
            /* 101 */if (httpURLConnection == null)
            {
                /* 102 */throw new Exception("创建联接失败");
                /*     */
            }
            /* 104 */requestServer(httpURLConnection, getRequestParamString(data, encoding),
            /* 105 */encoding);
            /* 106 */this.result = response(httpURLConnection, encoding);
            /* 107 */return httpURLConnection.getResponseCode();
            /*     */
        }
        catch (Exception e)
        {
            /* 109 */throw e;
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */private void requestServer(URLConnection connection, String message, String encoder)
        /*     */throws Exception
    /*     */
    {
        /* 122 */PrintStream out = null;
        /*     */try
        {
            /* 124 */connection.connect();
            /* 125 */out = new PrintStream(connection.getOutputStream(), false, encoder);
            /* 126 */out.print(message);
            /* 127 */out.flush();
            /*     */
        }
        catch (Exception e)
        {
            /* 129 */throw e;
            /*     */
        }
        finally
        {
            /* 131 */if (out != null)
                /* 132 */out.close();
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */private String response(HttpURLConnection connection, String encoding)
        /*     */throws URISyntaxException, IOException, Exception
    /*     */
    {
        /* 148 */InputStream in = null;
        /* 149 */StringBuilder sb = new StringBuilder(1024);
        /* 150 */BufferedReader br = null;
        /* 151 */String temp = null;
        /*     */try
        {
            /* 153 */if (200 == connection.getResponseCode())
            {
                /* 154 */in = connection.getInputStream();
                /* 155 */br = new BufferedReader(new InputStreamReader(in, encoding));
                /* 156 */while ((temp = br.readLine()) != null)
                    /* 157 */sb.append(temp);
                /*     */
            }
            /*     */else
            {
                /* 160 */in = connection.getErrorStream();
                /* 161 */br = new BufferedReader(new InputStreamReader(in, encoding));
                /* 162 */while ((temp = br.readLine()) != null)
                {
                    /* 163 */sb.append(temp);
                    /*     */
                }
                /*     */
            }
            /* 166 */return sb.toString();
            /*     */
        }
        catch (Exception e)
        {
            /* 168 */throw e;
            /*     */
        }
        finally
        {
            /* 170 */if (br != null)
            {
                /* 171 */br.close();
                /*     */
            }
            /* 173 */if (in != null)
            {
                /* 174 */in.close();
                /*     */
            }
            /* 176 */if (connection != null)
                /* 177 */connection.disconnect();
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */private HttpURLConnection createConnection(String encoding)
        /*     */throws ProtocolException
    /*     */
    {
        /* 189 */HttpURLConnection httpURLConnection = null;
        /*     */try
        {
            /* 191 */httpURLConnection = (HttpURLConnection)this.url.openConnection();
            /*     */
        }
        catch (IOException e)
        {
            /* 193 */e.printStackTrace();
            /* 194 */return null;
            /*     */
        }
        /* 196 */httpURLConnection.setConnectTimeout(this.connectionTimeout);
        /* 197 */httpURLConnection.setReadTimeout(this.readTimeOut);
        /* 198 */httpURLConnection.setDoInput(true);
        /* 199 */httpURLConnection.setDoOutput(true);
        /* 200 */httpURLConnection.setUseCaches(false);
        /* 201 */httpURLConnection.setRequestProperty("Content-type",
        /* 202 */"application/x-www-form-urlencoded;charset=" + encoding);
        /* 203 */httpURLConnection.setRequestMethod("POST");
        /* 204 */if ("https".equalsIgnoreCase(this.url.getProtocol()))
        {
            /* 205 */HttpsURLConnection husn = (HttpsURLConnection)httpURLConnection;
            /* 206 */husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
            /* 207 */husn.setHostnameVerifier(new BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier());
            /* 208 */return husn;
            /*     */
        }
        /* 210 */return httpURLConnection;
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    private String getRequestParamString(Map<String, String> requestParam, String coder)
    /*     */
    {
        /* 221 */if ((coder == null) || ("".equals(coder)))
        {
            /* 222 */coder = "UTF-8";
            /*     */
        }
        /* 224 */StringBuffer sf = new StringBuffer("");
        /* 225 */String reqstr = "";
        /* 226 */if ((requestParam != null) && (requestParam.size() != 0))
        {
            /* 227 */for (Map.Entry en : requestParam.entrySet())
            {
                /*     */try
                {
                    /* 229 */sf.append((String)en.getKey() +
                    /* 230 */"=" + (
                    /* 231 */(en.getValue() == null) || ("".equals(en.getValue())) ? "" :
                    /* 232 */URLEncoder.encode((String)en.getValue(), coder)) + "&");
                    /*     */
                }
                catch (UnsupportedEncodingException e)
                {
                    /* 234 */e.printStackTrace();
                    /* 235 */return "";
                    /*     */
                }
                /*     */
            }
            /* 238 */reqstr = sf.substring(0, sf.length() - 1);
            /*     */
        }
        /* 240 */return reqstr;
        /*     */
    }
    /*     */
}
