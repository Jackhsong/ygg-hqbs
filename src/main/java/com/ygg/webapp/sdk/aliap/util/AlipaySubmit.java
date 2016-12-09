package com.ygg.webapp.sdk.aliap.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ygg.webapp.util.YggWebProperties;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.ygg.webapp.sdk.aliap.config.AlipayConfig;
import com.ygg.webapp.sdk.aliap.sign.MD5;
import com.ygg.webapp.sdk.aliap.sign.RSA;
import com.ygg.webapp.sdk.aliap.util.httpClient.HttpProtocolHandler;
import com.ygg.webapp.sdk.aliap.util.httpClient.HttpRequest;
import com.ygg.webapp.sdk.aliap.util.httpClient.HttpResponse;
import com.ygg.webapp.sdk.aliap.util.httpClient.HttpResultType;

/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipaySubmit
{
    
    static Logger logger = Logger.getLogger(AlipaySubmit.class);

    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GLOBAL_GATEWAY_NEW = YggWebProperties.getInstance().getProperties("ali_global_gate_url");
    
    /**
     * 生成签名结果
     * 
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String buildRequestMysign(Map<String, String> sPara)
    {
        String prestr = AlipayCore.createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if (AlipayConfig.sign_type.equals("MD5"))
        {
            mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        if (AlipayConfig.sign_type.equals("0001"))
        {
            mysign = RSA.sign(prestr, AlipayConfig.private_key, AlipayConfig.input_charset);
        }
        return mysign;
    }
    
    /**
     * 生成要请求给支付宝的参数数组
     * 
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp)
    {
        // 除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        // 生成签名结果
        String mysign = buildRequestMysign(sPara);
        
        // 签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        if (!sPara.get("service").equals("alipay.wap.trade.create.direct") && !sPara.get("service").equals("alipay.wap.auth.authAndExecute"))
        {
            sPara.put("sign_type", AlipayConfig.sign_type);
        }
        
        return sPara;
    }
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * 
     * @paramALIPAY_GATEWAY_NEW 支付宝网关地址
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(String ALIPAY_GATEWAY_NEW, Map<String, String> sParaTemp, String strMethod, String strButtonName)
    {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        
        StringBuffer sbHtml = new StringBuffer();
        
        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset
            + "\" method=\"" + strMethod + "\">");
        
        for (int i = 0; i < keys.size(); i++)
        {
            String name = (String)keys.get(i);
            String value = (String)sPara.get(name);
            
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        // sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        
        return sbHtml.toString();
    }
    
    /**
     * 建立请求，以表单HTML形式构造，带文件上传功能
     * 
     * @paramALIPAY_GATEWAY_NEW 支付宝网关地址
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @param strParaFileName 文件上传的参数名
     * @return 提交表单HTML文本
     */
    public static String buildRequest(String ALIPAY_GATEWAY_NEW, Map<String, String> sParaTemp, String strMethod, String strButtonName, String strParaFileName)
    {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        
        StringBuffer sbHtml = new StringBuffer();
        
        sbHtml.append(
            "<form id=\"alipaysubmit\" name=\"alipaysubmit\"  enctype=\"multipart/form-data\" action=\"" + ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset
                + "\" method=\"" + strMethod + "\">");
        
        for (int i = 0; i < keys.size(); i++)
        {
            String name = (String)keys.get(i);
            String value = (String)sPara.get(name);
            
            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }
        
        sbHtml.append("<input type=\"file\" name=\"" + strParaFileName + "\" />");
        
        // submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        
        return sbHtml.toString();
    }
    
    /**
     * 建立请求，以模拟远程HTTP的POST请求方式构造并获取支付宝的处理结果 如果接口中没有上传文件参数，那么strParaFileName与strFilePath设置为空值 如：buildRequest("",
     * "",sParaTemp)
     * 
     * @paramALIPAY_GATEWAY_NEW 支付宝网关地址
     * @param strParaFileName 文件类型的参数名
     * @param strFilePath 文件路径
     * @param sParaTemp 请求参数数组
     * @return 支付宝处理结果
     * @throws Exception
     */
    public static String buildRequest(String ALIPAY_GATEWAY_NEW, String strParaFileName, String strFilePath, Map<String, String> sParaTemp)
        throws Exception
    {
        // 待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp);
        
        HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
        
        HttpRequest request = new HttpRequest(HttpResultType.BYTES);
        // 设置编码集
        request.setCharset(AlipayConfig.input_charset);
        
        request.setParameters(generatNameValuePair(sPara));
        String url = ALIPAY_GATEWAY_NEW + "_input_charset=" + AlipayConfig.input_charset;
        request.setUrl(url); // ALIPAY_GATEWAY_NEW+"_input_charset="+AlipayConfig.input_charset);
        System.out.println("----sendAliUrl----is:" + url);
        System.out.println("----sendAliparam----is:" + generatNameValuePair(sPara).toString());
        HttpResponse response = httpProtocolHandler.execute(request, strParaFileName, strFilePath);
        if (response == null)
        {
            return null;
        }
        
        String strResult = response.getStringResult();
        
        return strResult;
    }
    
    /* *//**
     * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
     */
    /*
     * public static String post(String ALIPAY_GATEWAY_NEW,Map<String, String> sParaTemp) {
     * 
     * //待请求参数数组 Map<String, String> sPara = buildRequestPara(sParaTemp);
     * 
     * // 创建默认的httpClient实例. CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httppost HttpPost
     * httppost = new HttpPost(ALIPAY_GATEWAY_NEW); // 创建参数队列 List<org.apache.http.NameValuePair> formparams = new
     * ArrayList<org.apache.http.NameValuePair>(); for (Map.Entry<String, String> entry : sPara.entrySet()) {
     * formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); } UrlEncodedFormEntity uefEntity; try {
     * uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8"); httppost.setEntity(uefEntity);
     * System.out.println("executing request " + httppost.getURI()); CloseableHttpResponse response =
     * httpclient.execute(httppost); try { HttpEntity entity = response.getEntity(); String resStr =
     * EntityUtils.toString(entity, "UTF-8") ; if (entity != null) {
     * System.out.println("--------------------------------------"); System.out.println("Response content: " + resStr )
     * ; System.out.println("--------------------------------------"); } return resStr ; } finally { response.close(); }
     * } catch (ClientProtocolException e) { e.printStackTrace(); } catch (UnsupportedEncodingException e1) {
     * e1.printStackTrace(); } catch (IOException e) { e.printStackTrace(); } finally { // 关闭连接,释放资源 try {
     * httpclient.close(); } catch (IOException e) { e.printStackTrace(); } } return null ; }
     */
    
    public static String buildRequestParams(Map<String, String> sParams)
    {
        String requestParams = "";
        if (sParams == null || sParams.isEmpty())
            return requestParams;
        sParams = buildRequestPara(sParams); // 签名参数
        /*
         * for (Map.Entry<String, String> entry : sParams.entrySet()) { requestParams
         * +=entry.getKey()+"="+entry.getValue()+"&" ; }
         */
        if (sParams.get("req_data") != null)
            requestParams += "req_data=" + sParams.get("req_data") + "&";
        if (sParams.get("service") != null)
            requestParams += "service=" + sParams.get("service") + "&";
        if (sParams.get("sec_id") != null)
            requestParams += "sec_id=" + sParams.get("sec_id") + "&";
        if (sParams.get("partner") != null)
            requestParams += "partner=" + sParams.get("partner") + "&";
        if (sParams.get("req_id") != null)
            requestParams += "req_id=" + sParams.get("req_id") + "&";
        if (sParams.get("sign") != null)
            requestParams += "sign=" + sParams.get("sign") + "&";
        if (sParams.get("format") != null)
            requestParams += "format=" + sParams.get("format") + "&";
        if (sParams.get("_input_charset") != null)
            requestParams += "_input_charset=" + sParams.get("_input_charset") + "&";
        if (sParams.get("v") != null)
            requestParams += "v=" + sParams.get("v"); // +"&" ;
        /*
         * if(sParams.get("sign") !=null) requestParams +="sign="+sParams.get("sign") ;
         */
        
        return requestParams;
    }
    
    /**
     * MAP类型数组转换成NameValuePair类型
     * 
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties)
    {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet())
        {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }
        
        return nameValuePair;
    }
    
    /**
     * 解析远程模拟提交后返回的信息，获得token
     * 
     * @param text 要解析的字符串
     * @return 解析结果
     * @throws Exception
     */
    public static String getRequestToken(String text)
        throws Exception
    {
        String request_token = "";
        // 以“&”字符切割字符串
        String[] strSplitText = text.split("&");
        // 把切割后的字符串数组变成变量与数值组合的字典数组
        Map<String, String> paraText = new HashMap<String, String>();
        for (int i = 0; i < strSplitText.length; i++)
        {
            
            // 获得第一个=字符的位置
            int nPos = strSplitText[i].indexOf("=");
            // 获得字符串长度
            int nLen = strSplitText[i].length();
            // 获得变量名
            String strKey = strSplitText[i].substring(0, nPos);
            // 获得数值
            String strValue = strSplitText[i].substring(nPos + 1, nLen);
            // 放入MAP类中
            paraText.put(strKey, strValue);
        }
        
        if (paraText.get("res_data") != null)
        {
            String res_data = paraText.get("res_data");
            // 解析加密部分字符串（RSA与MD5区别仅此一句）
            if (AlipayConfig.sign_type.equals("0001"))
            {
                res_data = RSA.decrypt(res_data, AlipayConfig.private_key, AlipayConfig.input_charset);
            }
            // System.out.println("-----------AliPaySubmit---res-data---is:"+res_data);
            logger.info("-----------AliPaySubmit---res-data---is:" + res_data);
            // token从res_data中解析出来（也就是说res_data中已经包含token的内容）
            Document document = DocumentHelper.parseText(res_data);
            request_token = document.selectSingleNode("//direct_trade_create_res/request_token").getText();
        }
        return request_token;
    }
    
    /**
     * 用于防钓鱼，调用接口query_timestamp来获取时间戳的处理函数 注意：远程解析XML出错，与服务器是否支持SSL等配置有关
     * 
     * @return 时间戳字符串
     * @throws IOException
     * @throws DocumentException
     * @throws MalformedURLException
     */
    public static String query_timestamp()
        throws MalformedURLException, DocumentException, IOException
    {
        
        // 构造访问query_timestamp接口的URL串
        String strUrl = "https://mapi.alipay.com/gateway.do?service=query_timestamp&partner=" + AlipayConfig.partner;
        StringBuffer result = new StringBuffer();
        
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new URL(strUrl).openStream());
        
        List<Node> nodeList = doc.selectNodes("//alipay/*");
        
        for (Node node : nodeList)
        {
            // 截取部分不需要解析的信息
            if (node.getName().equals("is_success") && node.getText().equals("T"))
            {
                // 判断是否有成功标示
                List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
                for (Node node1 : nodeList1)
                {
                    result.append(node1.getText());
                }
            }
        }
        
        return result.toString();
    }


    /******************************************下为国际支付宝*******************************************************************/
    /**
     * 建立请求，以表单HTML形式构造（默认）
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName) {
        //待请求参数数组
        Map<String, String> sPara = globalBuildRequestPara(sParaTemp);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"alipaysubmit\" name=\"alipaysubmit\" action=\"" + ALIPAY_GLOBAL_GATEWAY_NEW
            + "_input_charset=" + AlipayConfig.input_charset + "\" method=\"" + strMethod
            + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
//        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");

        return sbHtml.toString();
    }

    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> globalBuildRequestPara(Map<String, String> sParaTemp) {
        //除去数组中的空值和签名参数
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        //生成签名结果
        String mysign = globalBuildRequestMysign(sPara);

        //签名结果与签名方式加入请求提交参数组中
        sPara.put("sign", mysign);
        sPara.put("sign_type", AlipayConfig.global_sign_type);

        return sPara;
    }

    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
    public static String globalBuildRequestMysign(Map<String, String> sPara) {
        String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.global_sign_type.equals("MD5") ) {
            mysign = MD5.sign(prestr, AlipayConfig.global_key, AlipayConfig.input_charset);
        }
        return mysign;
    }

}
