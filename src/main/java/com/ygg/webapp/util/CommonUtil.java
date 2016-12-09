package com.ygg.webapp.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
//import com.ygg.appnative.sdk.yimei.SingletonClient;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.entity.AccountCartEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.sdk.weixin.util.Sha1Util;
import com.ygg.webapp.service.AddressService;
import com.ygg.webapp.service.OrderPayService;
import com.ygg.webapp.service.PlatformConfigService;
import com.ygg.webapp.view.CityView;
import com.ygg.webapp.view.DistrictView;
import com.ygg.webapp.view.ProvinceView;

public class CommonUtil
{
    public static Logger log = Logger.getLogger(CommonUtil.class);
    
    /**
     * 平台签名密钥
     */
    public static final String SIGN_KEY = "yangegegegeyan";
    
    private static char[] letterNoIO = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    
    private static Random random = new Random();
    
    private static Map<String, String> bankType = new HashMap<String, String>();
    
    static
    {
        bankType.put("1", "工商银行");
        bankType.put("2", "农业银行");
        bankType.put("3", "中国银行");
        bankType.put("4", "建设银行");
        bankType.put("5", "邮政储蓄");
        bankType.put("6", "交通银行");
        bankType.put("7", "招商银行");
        bankType.put("8", "光大银行");
        bankType.put("9", "中信银行");
    }
    
    /**
     * 将BASE64字符串恢复为二进制数据
     * 
     * @param base64String
     * @return
     */
    public static byte[] getBytesFromBASE64(String base64String)
    {
        try
        {
            return Base64.decodeBase64(base64String.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            return null;
        }
    }
    
    public static String getBASE64FromBytes(byte[] base64String)
    {
        try
        {
            return new String(Base64.encodeBase64(base64String));
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    /**
     * 将输入流转为字节数组格式的对象
     * 
     * @param in 输入流对象
     * @return
     */
    public static byte[] convertInputStream2Bytes(InputStream in)
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int length = 0;
        byte[] buffer = new byte[1024];
        
        try
        {
            while ((length = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, length);
            }
            
            return out.toByteArray();
        }
        catch (IOException e)
        {
            log.error("read from inputstream error.", e);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                log.error("close the outputstream error.", e);
            }
        }
        
        return null;
    }
    
    /**
     * 将输入流转为字符串格式的对象
     * 
     * @param in 输入流对象
     * @return
     */
    public static String convertInputStream2Str(InputStream in)
    {
        String result = null;
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int length = 0;
        byte[] buffer = new byte[1024];
        
        try
        {
            while ((length = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, length);
            }
            
            result = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            log.error("read from inputstream error.", e);
        }
        finally
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
                log.error("close the outputstream error.", e);
            }
        }
        
        return result;
    }
    
    /**
     * 发送短信
     *
     * @param str
     * @return 验证通过返回true
     */
    public static void sendSMS(String[] mobiles, String smsContent, int smsPriority)
        throws Exception
    {
//        int i;
//        try
//        {
//            i = SingletonClient.getClient().sendSMS(mobiles, smsContent, "", smsPriority);
//            if (i != 0)
//            {
//                throw new Exception("发送短信失败！mobiles：" + mobiles + ",smsContent:" + smsContent + ",i:" + i);
//            }
//        }
//        catch (RemoteException e)
//        {
//            log.warn("发送短信失败！mobiles：" + mobiles + ",smsContent:" + smsContent);
//            throw new Exception(e);
//        }
    }
    
    /**
     * 生成指定长度的随机数字
     *
     * @param 随机数字长度
     * @return 验证通过返回true
     */
    public static String GenerateRandomCode(int length)
    {
        String result = "";
        for (int i = 0; i < length; i++)
        {
            result += nextInt(0, 9);
        }
        return result;
    }
    
    /**
     * 生成一个唯一的UUID
     *
     * @return
     */
    public static String generateUUID()
    {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 30);
    }
    
    private static int nextInt(final int min, final int max)
    {
        Random rand = new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;
    }
    
    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str)
    {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    /**
     * 电话号码验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(String str)
    {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
        if (str.length() > 9)
        {
            m = p1.matcher(str);
            b = m.matches();
        }
        else
        {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }
    
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str)
    {
        if (str == null || str.equals(""))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 把字符串转换成md5
     *
     * @param str
     * @return
     */
    public static String strToMD5(String str)
        throws UnsupportedEncodingException
    {
        
        try
        {
            byte[] input;
            input = str.getBytes(CommonConstant.CHARACTER_ENCODING);
            return bytesToHex(bytesToMD5(input));
        }
        catch (UnsupportedEncodingException e)
        {
            log.error("strToMD5编码不支持!", e);
            throw e;
        }
    }
    
    /**
     * 把字节数组转成16进位制数
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes)
    {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 4; i < bytes.length - 4; i++)
        {
            digital = bytes[i];
            if (digital < 0)
            {
                digital += 256;
            }
            if (digital < 16)
            {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }
    
    /**
     * 把字节数组转换成md5
     *
     * @param input
     * @return
     */
    public static byte[] bytesToMD5(byte[] input)
    {
        // String md5str = null;
        byte[] buff = null;
        try
        {
            // 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算后获得字节数组
            buff = md.digest(input);
            // 把数组每一字节换成16进制连成md5字符串
            // md5str = bytesToHex(buff);
        }
        catch (Exception e)
        {
            log.error(e);
        }
        return buff;
    }
    
    /**
     * 将字符串格式装换成DATE类型
     *
     * @param date
     * @param type 被转换的字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static Date string2Date(String date, String type)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        Date time = null;
        try
        {
            time = sdf.parse(date);
        }
        catch (ParseException e)
        {
            log.error("字符串格式装换成DATE类型出错", e);
        }
        return time;
    }
    
    /**
     * 将DATE类型装换成字符串格式
     *
     * @param date
     * @param type 要转换成的字符串格式，如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2String(Date date, String type)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String time = sdf.format(date);
        return time;
    }
    
    /**
     * 根据长度转换成SQL设值串，如：?,?,?,?
     *
     * @param length
     * @return
     */
    public static String changeSqlValueByLen(int length)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append("?,");
        }
        if (sb.length() > 0)
        {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    /**
     * 验证字符串是否全是数字
     *
     * @param str
     * @return
     */
    public static boolean validateStrIsNum(String str)
    {
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isDigit(str.charAt(i)))
            {
                continue;
            }
            else
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 获取当前年和周
     *
     * @return 年份+周数，201414
     */
    public static String getYearAndWeek()
    {
        Calendar cl = Calendar.getInstance();
        int week = cl.get(Calendar.WEEK_OF_YEAR);
        cl.add(Calendar.DAY_OF_MONTH, -7);
        int year = cl.get(Calendar.YEAR);
        if (week < cl.get(Calendar.WEEK_OF_YEAR))
        {
            year += 1;
        }
        return (year + "") + (week + "");
    }
    
    /**
     * 获取今日特卖日期
     *
     * @return yyyyMMdd
     */
    public static int getNowSaleDate()
    {
        Calendar cl = Calendar.getInstance();
        int currentHour = cl.get(Calendar.HOUR_OF_DAY);
        if (currentHour < CommonConstant.SALE_REFRESH_HOUR)
        {
            cl.add(Calendar.DAY_OF_YEAR, -1);
        }
        return Integer.parseInt(date2String(cl.getTime(), "yyyyMMdd"));
    }
    
    /**
     * 获取今日晚场特卖日期
     *
     * @return yyyyMMdd
     */
    public static int getNowSaleDateNight()
    {
        Calendar cl = Calendar.getInstance();
        int currentHour = cl.get(Calendar.HOUR_OF_DAY);
        if (currentHour < CommonConstant.SALE_REFRESH_HOUR_NIGHT)
        {
            cl.add(Calendar.DAY_OF_YEAR, -1);
        }
        return Integer.parseInt(date2String(cl.getTime(), "yyyyMMdd"));
    }
    
    /**
     * 获取即将开始日期
     *
     * @return yyyyMMdd
     */
    public static int getLaterSaleDate()
    {
        Calendar cl = Calendar.getInstance();
        int currentHour = cl.get(Calendar.HOUR_OF_DAY);
        if (currentHour < CommonConstant.SALE_REFRESH_HOUR)
        {
            cl.add(Calendar.DAY_OF_YEAR, -1);
        }
        cl.add(Calendar.DAY_OF_YEAR, 1);
        return Integer.parseInt(date2String(cl.getTime(), "yyyyMMdd"));
    }
    
    /**
     * 获取今日特卖到明日特卖剩余的秒数
     *
     * @return
     */
    public static int getNowEndSecond()
    {
        Calendar now = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        int currentHour = now.get(Calendar.HOUR_OF_DAY);
        if (currentHour < CommonConstant.SALE_REFRESH_HOUR)
        {
            tomorrow.set(Calendar.HOUR_OF_DAY, 10);
        }
        else
        {
            tomorrow.add(Calendar.DAY_OF_YEAR, 1);
            tomorrow.set(Calendar.HOUR_OF_DAY, 10);
        }
        return (int)((tomorrow.getTimeInMillis() - now.getTimeInMillis()) / 1000);
    }
    
    /**
     * 生成一个唯一的事务ID
     *
     * @return
     */
    public synchronized static Long generateTransactionId()
    {
        try
        {
            Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
            log.error("getTransactionId出错！", e);
        }
        return Long.parseLong(System.currentTimeMillis() + CommonConstant.PLATFORM_IDENTITY_CODE);
    }
    
    /**
     * 生成一个唯一的订单id
     *
     * @return
     */
    public synchronized static String generateOrderNumber()
    {
        try
        {
            Thread.sleep(1);
        }
        catch (InterruptedException e)
        {
            log.error(e);
        }
        Calendar curr = Calendar.getInstance();
        
        StringBuffer suffix =
            new StringBuffer(
                ((curr.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000) + (curr.get(Calendar.MINUTE) * 60 * 1000) + (curr.get(Calendar.SECOND) * 1000) + curr.get(Calendar.MILLISECOND))
                    + "");
        int len = suffix.toString().length();
        if (len >= 0 && len < 8)
        {
            int size = 8 - len;
            for (int i = 0; i < size; i++)
                suffix.insert(0, "0");
        }
        return CommonUtil.date2String(curr.getTime(), "yyyyMMdd").substring(2) + suffix + CommonConstant.PLATFORM_IDENTITY_CODE;
    }
    
    public static String getRemoteIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.length() > 15)
        {
            if (ip.indexOf(",") > 0)
            {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
    
    /**
     * 是否微信brower
     * 
     * @param userAgent
     * @return
     */
    public static boolean isWeiXinBrower(String userAgent)
    {
        
        // log.info("userAgent is: " + userAgent);
        boolean flag = false;
        if (userAgent == null || userAgent.equals(""))
            return flag;
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("micromessenger"))
        {
            flag = true;
        }
        return flag;
    }
    
    public static boolean isWeiXinBrower(HttpServletRequest request)
    {
        String userAgent = request.getHeader("User-Agent");
        // log.info("userAgent is: " + userAgent);
        boolean flag = false;
        if (userAgent == null || userAgent.equals(""))
            return flag;
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("micromessenger"))
        {
            flag = true;
        }
        return flag;
    }
    
    /**
     * 判断微信的版本是否是>5.0版本以上
     * 
     * @param userAgent
     * @return
     */
    public static boolean isWeiXinVersionMoreThan5(String userAgent)
    {
        // String str =
        // "Mozilla/5.0(iphone;CPU iphone OS 5_1_1 like Mac OS X) AppleWebKit/534.46(KHTML,likeGeocko) Mobile/9B206
        // MicroMessenger/5.0"
        // ;
        // boolean bf1 = str.contains("MicroMessenger/") ;
        // log.info("userAgent is: " + userAgent);
        boolean flag = false;
        if (userAgent == null || userAgent.equals(""))
            return flag;
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("micromessenger"))
        {
            int len1 = userAgent.indexOf("micromessenger");
            String userAgent2 = userAgent.substring(len1, userAgent.length() - 1);
            String[] mm3 = userAgent2.split("/");
            if (mm3.length > 1)
            {
                String mm = mm3[1];
                mm = mm.substring(0, 3);
                float wxversion = Float.parseFloat(mm);
                if (wxversion >= 5.0f)
                    flag = true;
            }
            /*
             * String[] userAgentArray = userAgent.split("/"); if(userAgentArray ==null || userAgentArray.length == 0)
             * return flag ; float wxversion = Float.parseFloat(userAgentArray[userAgentArray.length-1]) ; if(wxversion
             * >= 5.0f) flag = true ;
             */
        }
        return flag;
    }
    
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request)
    {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<String> temp = request.getParameterNames();
        if (null != temp)
        {
            while (temp.hasMoreElements())
            {
                String key = temp.nextElement();
                String value = request.getParameter(key);
                res.put(key, value);
            }
        }
        return res;
    }
    
    public static String getProvinceNameByProvinceId(String provinceId)
    {
        String provinceName = "";
        
        if (provinceId == null || provinceId.equals(""))
            return provinceName;
        
        CacheServiceIF cs = SpringBeanUtil.getBean("memService", CacheServiceIF.class);
        
        List<ProvinceView> pvs = cs.getCache(CacheConstant.PROVINCE_DATA_CACHE);
        if (pvs == null || pvs.isEmpty())
        {
            AddressService ads = SpringBeanUtil.getBean("addressService", AddressService.class);
            try
            {
                ads.getNationwideInfo();
            }
            catch (Exception e)
            {
                log.error(e);
            }
            pvs = cs.getCache(CacheConstant.PROVINCE_DATA_CACHE);
        }
        for (ProvinceView pv : pvs)
        {
            String provinceIdTmp = pv.getProvinceId();
            if (provinceIdTmp != null && provinceIdTmp.equals(provinceId))
            {
                provinceName = pv.getName();
                break;
            }
        }
        
        return provinceName;
    }
    
    public static String getCityNameByCityId(String provinceId, String cityId)
    {
        String cityName = "";
        
        if (provinceId == null || provinceId.equals("") || cityId == null || cityId.equals(""))
            return cityName;
        
        CacheServiceIF cs = SpringBeanUtil.getBean("memService", CacheServiceIF.class);
        
        Map<String, Object> cvsmap = cs.getCache(CacheConstant.CITY_DATA_CACHE);
        if (cvsmap == null || cvsmap.isEmpty())
        {
            AddressService ads = SpringBeanUtil.getBean("addressService", AddressService.class);
            try
            {
                ads.getNationwideInfo();
            }
            catch (Exception e)
            {
                log.error(e);
            }
            cvsmap = cs.getCache(CacheConstant.CITY_DATA_CACHE);
        }
        @SuppressWarnings("unchecked")
        List<CityView> cvs = (cvsmap.get(provinceId) == null ? new ArrayList<CityView>() : (List<CityView>)cvsmap.get(provinceId));
        
        for (CityView cv : cvs)
        {
            String cityIdTmp = cv.getCityId();
            if (cityIdTmp != null && cityIdTmp.equals(cityId))
            {
                cityName = cv.getName();
                break;
            }
        }
        return cityName;
    }
    
    public static String getDistrictNameByDistrictId(String cityId, String districtId)
    {
        String districtName = "";
        
        if (districtId == null || districtId.equals(""))
            return districtName;
        
        CacheServiceIF cs = SpringBeanUtil.getBean("memService", CacheServiceIF.class);
        
        Map<String, Object> dvsmap = cs.getCache(CacheConstant.DISTRICT_DATA_CACHE); // .addCache(CacheConstant.PROVINCE_DATA_CACHE,
                                                                                     // pvs,0);
        if (dvsmap == null || dvsmap.isEmpty())
        {
            AddressService ads = SpringBeanUtil.getBean("addressService", AddressService.class);
            try
            {
                ads.getNationwideInfo();
            }
            catch (Exception e)
            {
                log.error(e);
            }
            dvsmap = cs.getCache(CacheConstant.DISTRICT_DATA_CACHE);
        }
        @SuppressWarnings("unchecked")
        List<DistrictView> dvs = (dvsmap.get(cityId) == null ? new ArrayList<DistrictView>() : (List<DistrictView>)dvsmap.get(cityId));
        
        for (DistrictView dv : dvs)
        {
            String districtIdTmp = dv.getDistrictId();
            if (districtIdTmp != null && districtIdTmp.equals(districtId))
            {
                districtName = dv.getName();
                break;
            }
        }
        return districtName;
    }
    
    public static void getWeiXinAccessToken(HttpServletRequest request, HttpServletResponse response, String resquestParams, Map<String, Object> mv)
        throws Exception
    {
        OrderPayService orderPayService = SpringBeanUtil.getBean("orderPayService", OrderPayService.class);
        String responseParams = orderPayService.configPay(request, response, resquestParams);
        // log.info("---CommonUtil------getWeiXinAccessToken-----responseParams---is: " + responseParams);
        JsonParser parser = new JsonParser();
        // JsonParser requestparser = new JsonParser();
        JsonObject param = (JsonObject)parser.parse(responseParams);
        String timestamp = "";
        String nonceStr = "";
        String signature = "";
        String accessToken = "";
        String jsApiTicket = "";
        // float totalPrice = 0f ;
        timestamp = param.get("timestamp") == null ? Sha1Util.getTimeStamp() : param.get("timestamp").getAsString();
        nonceStr = param.get("nonceStr") == null ? "" : param.get("nonceStr").getAsString();
        signature = param.get("signature") == null ? "" : param.get("signature").getAsString();
        accessToken = param.get(CacheConstant.WEIXIN_ACCESS_TOKEN) == null ? "" : param.get(CacheConstant.WEIXIN_ACCESS_TOKEN).getAsString();
        jsApiTicket = param.get(CacheConstant.WEIXIN_JSAPI_TICKET) == null ? "" : param.get(CacheConstant.WEIXIN_JSAPI_TICKET).getAsString();
        
        mv.put(CacheConstant.WEIXIN_ACCESS_TOKEN, accessToken);
        mv.put(CacheConstant.WEIXIN_JSAPI_TICKET, jsApiTicket);
        mv.put("appid", CommonConstant.APPID);
        mv.put("timestamp", timestamp); // 必填，生成签名的时间戳
        mv.put("nonceStr", nonceStr); // 必填，生成签名的随机串
        mv.put("signature", signature); // 必填，签名
        // mv.addObject("jsApiList", "[]"); // 必填，需要使用的JS接口列表，
        
        getWeiXinVersion(request, mv);
        
    }
    
    public static void getWeiXinVersion(HttpServletRequest request, Map<String, Object> mv)
    {
        boolean flag = CommonUtil.isWeiXinVersionMoreThan5(request.getHeader("User-Agent"));
        mv.put("iswx5version", (flag ? "1" : "0")); // 1表示 >=5.0以上的版本
    }
    
    public static String readTxtFile(String filePath)
    {
        StringBuffer sb = new StringBuffer();
        File file = null;
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try
        {
            String encoding = "UTF-8";
            file = new File(filePath);
            if (file.isFile() && file.exists()) // 判断文件是否存在
            {
                read = new InputStreamReader(new FileInputStream(file), encoding); // 考虑到编码格式
                bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    sb.append(lineTxt + "\n");
                }
                
            }
            else
            {
                log.error("----readTxtFile---找不到指定的文件");
                
            }
        }
        catch (Exception e)
        {
            log.error("---readTxtFile----读取文件内容出错", e);
        }
        finally
        {
            try
            {
                bufferedReader.close();
                read.close();
            }
            catch (IOException e)
            {
                log.error("IOException", e);
            }
        }
        return sb.toString();
    }
    
    public static String getWxShareUrl(Map<String, String> reqParams)
    {
        String wxshareurlparams = "";
        if (reqParams == null || reqParams.isEmpty())
            return wxshareurlparams;
        String code = reqParams.get("code");
        String state = reqParams.get("state");
        String from = reqParams.get("from");
        String isappinstalled = reqParams.get("isappinstalled");
        
        if (code != null && !code.equals(""))
            wxshareurlparams += "code=" + code + "&";
        if (state != null && !state.equals(""))
            wxshareurlparams += "state=" + state + "&";
        if (from != null && !from.equals(""))
            wxshareurlparams += "from=" + from + "&";
        if (isappinstalled != null && !isappinstalled.equals(""))
            wxshareurlparams += "isappinstalled=" + isappinstalled + "&";
        
        if (wxshareurlparams != "")
            wxshareurlparams = wxshareurlparams.substring(0, wxshareurlparams.length() - 1);
        return wxshareurlparams;
    }
    
    /**
     * 只能在微信中调用，调用前先判断是否是微信环境
     * 
     * @param requestUrl
     * @return
     */
    public static String getRedirectUrl(String requestUrl)
    {
        String redirectUrl = "";
        log.debug("UserLoginFilter---getRedirectUrl--is:" + requestUrl);
        if (requestUrl != null)
        {
            String domainName = YggWebProperties.getInstance().getProperties("domain_name");
            if (requestUrl.indexOf("ygg/product/single/") > 0)
            {
                int len = requestUrl.lastIndexOf("/");
                int len1 = requestUrl.lastIndexOf(".");
                if (len > 0 && len1 > 0 && len1 > len)
                {
                    String singleProductId = requestUrl.substring(len + 1, len1);
                    redirectUrl = YggWebProperties.getInstance().getProperties("sharesingleproducturl");
                    redirectUrl = redirectUrl.replaceFirst("#1", singleProductId);
                }
            }
            else if (requestUrl.indexOf("ygg/cnty/toac/") > 0)
            {
                int len = requestUrl.lastIndexOf("/");
                int len1 = requestUrl.lastIndexOf(".");
                if (len > 0 && len1 > 0 && len1 > len)
                {
                    String commonActivityId = requestUrl.substring(len + 1, len1);
                    redirectUrl = YggWebProperties.getInstance().getProperties("sharebrandurl");
                    redirectUrl = redirectUrl.replaceFirst("#1", commonActivityId);
                }
            }
            else if (requestUrl.endsWith(domainName) || requestUrl.endsWith(domainName + "/"))
            {
                redirectUrl = YggWebProperties.getInstance().getProperties("sharehomeurl");
            }
        }
        if (redirectUrl.equals(""))
            redirectUrl = requestUrl;
        
        return redirectUrl;
    }
    
    public static boolean isNumeric(String str)
    {
        if (str == null || str.equals(""))
            return false;
        /*
         * for(int i=str.length()-1;i>=0;i--) { if (!Character.isDigit(str.charAt(i))) { return false; } }
         */
        
        boolean flag = true;
        char[] array = str.toCharArray();
        for (char c : array)
        {
            
            if (c == '.' || (c >= '0' && c <= '9')) // c =='-' ||
                continue;
            else
            {
                flag = false;
                break;
            }
            
        }
        
        return flag;
    }
    
    public static Cookie addCookie(String key, String value, int expiry)
    {
        // 写入用户cookie
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(expiry);
        cookie.setPath("/");
        return cookie;
    }
    
    public static String getCookieValue(HttpServletRequest request, String key)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0 || key == null || key.equals(""))
            return "";
        for (Cookie c : cookies)
        {
            if (c.getName().equals(key))
            {
                String oscValue = c.getValue();
                if (!oscValue.equals(""))
                {
                    if (oscValue.length() > CommonConstant.OrderSoucrChannelKeyLength)
                        oscValue = oscValue.substring(0, 32);
                }
                return oscValue;
            }
            
        }
        return "";
    }
    
    public static String getAccountCardValue(String cardNumber, int subNum, String type)
    {
        if (cardNumber == null || cardNumber.equals(""))
            return "";
        
        if ("bank".equals(type) && cardNumber.length() > subNum)
        {
            cardNumber = cardNumber.substring(cardNumber.length() - subNum);
        }
        else if ("ali".equals(type) && (cardNumber.length() > subNum))
        {
            cardNumber = cardNumber.substring(0, subNum - 1);
        }
        return cardNumber;
    }
    
    public static String getAccountCardValue(AccountCartEntity ace)
        throws ServiceException
    {
        if (ace == null)
            return "";
        String accountCardValue = "";
        if (ace.getType() == new Byte(CommonEnum.ACCOUNT_CARD_TYPE.TYPE_BANK_TYPE.getValue() + "").byteValue())
        {
            String cardNumber = getAccountCardValue(ace.getCardNumber(), 4, "bank");
            accountCardValue = bankType.get((ace.getBankType() <= 0 ? "1" : ace.getBankType()) + "") + " 尾号" + cardNumber + " " + ace.getCardName();
        }
        else if (ace.getType() == new Byte(CommonEnum.ACCOUNT_CARD_TYPE.TYPE_ALI_TYPE.getValue() + "").byteValue())
        {
            String cardNumber = ace.getCardNumber();
            if (ace.getCardNumber().length() > 15)
            {
                cardNumber = CommonUtil.getAccountCardValue(ace.getCardNumber(), 13, "ali");
                cardNumber += "...";
            }
            accountCardValue = "支付宝 " + cardNumber + " " + ace.getCardName();
        }
        
        return accountCardValue;
    }
    
    private static void addStaticFileVersionToCache()
    {
        PlatformConfigService platformConfigService = SpringBeanUtil.getBean("platformConfigService", PlatformConfigService.class);
        CacheServiceIF cs = SpringBeanUtil.getBean("memService", CacheServiceIF.class);
        List<Map<String, Object>> list = platformConfigService.findAllPlateformConfig();
        if (list != null)
        {
            boolean cssFlag = false, jsFlag = false;
            for (Map<String, Object> map : list)
            {
                String key = map.get("key") == null ? "" : (String)map.get("key");
                String value = map.get("value") == null ? "" : (String)map.get("value");
                if (key.equals(CommonConstant.STATIC_CSS_VERSION))
                {
                    cs.addCache(CacheConstant.STATIC_CSS_VERSION_CACHE_KEY, value, CacheConstant.CACHE_MINUTE_10);
                    cssFlag = true;
                }
                else if (key.equals(CommonConstant.STATIC_JS_VERSION))
                {
                    cs.addCache(CacheConstant.STATIC_JS_VERSION_CACHE_KEY, value, CacheConstant.CACHE_MINUTE_10);
                    jsFlag = true;
                }
                
                if (cssFlag && jsFlag)
                    break;
            }
            if (!cssFlag)
                cs.addCache(CacheConstant.STATIC_CSS_VERSION_CACHE_KEY, "1", CacheConstant.CACHE_MINUTE_10);
            if (!jsFlag)
                cs.addCache(CacheConstant.STATIC_JS_VERSION_CACHE_KEY, "1", CacheConstant.CACHE_MINUTE_10);
        }
    }
    
    public static String getStaticCssVersion()
        throws ServiceException
    {
        // String cssVersion = "1";
        //
        // CacheServiceIF cs = SpringBeanUtil.getBean("memService", CacheServiceIF.class);
        // cssVersion = cs.getCache(CacheConstant.STATIC_CSS_VERSION_CACHE_KEY);
        // if (cssVersion == null || cssVersion.equals(""))
        // {
        // addStaticFileVersionToCache();
        // cssVersion = cs.getCache(CacheConstant.STATIC_CSS_VERSION_CACHE_KEY);
        // }
        
        return CommonProperties.staticCssVersion;
    }
    
    public static String getStaticJsVersion()
        throws ServiceException
    {
        // String jsVersion = "1";
        //
        // CacheServiceIF cs = SpringBeanUtil.getBean("memService", CacheServiceIF.class);
        // jsVersion = cs.getCache(CacheConstant.STATIC_JS_VERSION_CACHE_KEY);
        // if (jsVersion == null || jsVersion.equals(""))
        // {
        // addStaticFileVersionToCache();
        // jsVersion = cs.getCache(CacheConstant.STATIC_JS_VERSION_CACHE_KEY);
        // }
        
        return CommonProperties.staticJsVersion;
    }
    
    /**
     * 生成num长度的大写字母字符串 4 如AAAA
     * 
     * @param num
     * @return
     */
    public static String getGenerateLetterWithNum(int num)
    {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < num; i++)
        {
            str.append(letterNoIO[random.nextInt(24)]);
        }
        return str.toString();
    }
    
    /**
     * 转换float为平台支持的小数位
     *
     * @return
     */
    public static float transformFloat(float f)
    {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return Float.parseFloat(decimalFormat.format(f));
    }
    
    public static Map<String, Object> parseXml(HttpServletRequest request)
        throws Exception
    {
        // 将解析结果存储在HashMap中
        Map<String, Object> map = new HashMap();
        
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        
        // 遍历所有子节点
        for (Element e : elementList)
        {
            map.put(e.getName(), e.getText());
        }
        
        // 释放资源
        inputStream.close();
        inputStream = null;
        
        return map;
    }
    
    /**
     * 发起http-get请求获取返回结果
     * 
     * @param req_url 请求地址
     * @return
     */
    public static String sendHttpGet(String req_url)
    {
        StringBuffer buffer = new StringBuffer();
        try
        {
            URL url = new URL(req_url);
            HttpURLConnection httpUrlConn = (HttpURLConnection)url.openConnection();
            
            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            String str = null;
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            
        }
        catch (Exception e)
        {
            log.error("sendHttpGet", e);
        }
        return buffer.toString();
    }
    
    public static String removeIllegalEmoji(String content)
    {
        Pattern emoji = Pattern.compile("[\\x{10000}-\\x{10FFFF}]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher mat = emoji.matcher(content);
        return mat.replaceAll("");
    }
    
    public static String replaceIllegalEmoji(String content)
    {
        Pattern emoji = Pattern.compile("[\\x{10000}-\\x{10FFFF}]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher mat = emoji.matcher(content);
        return mat.replaceAll("?");
    }
    
    /**
     * 发起RESTFul-POST请求获取返回结果
     * 
     * @param req_url 请求地址
     * @return
     */
    public static String sendRESTFulPost(String req_url, String body, Map<String, String> headers)
    {
        StringBuffer buffer = new StringBuffer();
        try
        {
            // 需要请求的restful地址
            URL url = new URL(req_url);
            
            // 忽略ssl
            SslUtils.ignoreSsl();
            
            // 打开restful链接
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            
            // 提交模式
            conn.setRequestMethod("POST");// POST GET PUT DELETE
            
            // 设置访问提交模式，表单提交
            conn.setRequestProperty("Content-Type", "application/json");
            for (String key : headers.keySet())
            {
                conn.setRequestProperty(key, headers.get(key));
            }
            
            conn.setConnectTimeout(10000);// 连接超时 单位毫秒
            conn.setReadTimeout(2000);// 读取超时 单位毫秒
            
            conn.setDoOutput(true);// 是否输入参数
            conn.connect();
            
            byte[] bypes = body.getBytes();
            conn.getOutputStream().write(bypes);// 输入参数
            
            // 将返回的输入流转换成字符串
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            String str = null;
            while ((str = bufferedReader.readLine()) != null)
            {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            conn.disconnect();
        }
        catch (Exception e)
        {
            log.error("sendHttpGet", e);
        }
        return buffer.toString();
    }
    
    public static void main(String[] args)
    {
        // String result = CommonUtil.sendHttpGet("http://www.waibao58.com/hunting/index.php/celebrity/getRefee/" +
        // 581056);
        // JsonParser parser = new JsonParser();
        // JsonObject param = (JsonObject)parser.parse(result);
        // System.out.println(param.get("body"));
        // param = (JsonObject)parser.parse(param.get("body").toString());
        // System.out.println(param.get("father_account_id"));
        // System.out.println(param.get("grand_account_id"));
        // System.out.println(param.get("great_account_id"));
        // System.out.println(CommonHttpClient.getTokenYanwang());
        // String refeeResult =
        // CommonUtil.sendHttpGet("http://www.waibao58.com/hunting/index.php/spread/getUserInfo/586532");
        // JsonParser parser = new JsonParser();
        // JsonObject param = (JsonObject)parser.parse(refeeResult);
        // param = (JsonObject)parser.parse(param.get("body").toString());
        // System.out.println("param:" + param);
        // String nickname = param.get("nickname").toString().substring(1, param.get("nickname").toString().length() -
        // 1);
        // String headimgurl = param.get("headimgurl").toString().substring(1,
        // param.get("headimgurl").toString().length() - 1);
        // System.out.println(nickname);
        // System.out.println(headimgurl);
        
        Long beginTimer = System.currentTimeMillis();
        System.out.println();
        String accountIds =
            "[662767, 662626, 662566, 662563, 660403, 658870, 657023, 656383, 656368, 656195, 655887, 655882, 655673, 655646, 655404, 647067, 647011, 646976, 646748, 646469, 646464, 646321, 646315, 644767, 644736, 644546, 644317, 644281, 644219, 644192, 644129, 644023, 644006, 643977, 643967, 643944, 638160, 637426, 637367, 637196, 637183, 637170, 636988, 636954, 636946, 636914, 636909, 636882, 636877, 636847, 636825, 636817, 636809, 636134, 636000, 635954, 635698, 635685, 635667, 635618, 635617, 635611, 635602, 635600, 635598, 630567, 629038, 627725, 626756, 626661, 626599, 626512, 626488, 626456, 626389, 626371, 626221, 626207, 626168, 626165, 623248, 622793, 622600, 622504, 622503, 622494, 621268, 620544, 618108, 618067, 618059, 618057, 615723, 615711, 615706, 615690, 615269, 615062, 614413, 613971, 613841, 613826, 613792, 613776, 613652, 612988, 612986, 612549, 612531, 611922, 611367, 611184, 611168, 611078, 611037, 611031, 610990, 610960, 610950, 610938, 610918, 610905, 610902, 608222, 607972, 607846, 607788, 607768, 607495, 607241, 607227, 607217, 607185, 607184, 606313, 606246, 606023, 606002, 605023, 604697, 604680, 604669, 604661, 604608, 604596, 604577, 604572, 604566, 604555, 604485, 604474, 604460, 604452, 604448, 604444, 604427, 604426, 604419, 604391, 604371, 604363, 604343, 604342, 604317, 604276, 604259, 604223, 604211, 604184, 604181, 604176, 604171, 604161, 604149, 604139, 603953, 603875, 603869, 602942, 602857, 601755, 601750, 601744, 601718, 600856, 599415, 599081, 599041, 598996, 598755, 598754, 598666, 598537, 598467, 598278, 598066, 598036, 597967, 597939, 597910, 597848, 597803, 597769, 597759, 597687, 597679, 597677, 597662, 597648, 597636, 597629, 597626, 597624, 597619, 597615, 597613, 597612, 597611, 597607, 597204, 595850, 595598, 595306, 595303, 595289, 595251, 595250, 593119, 591590, 591439, 591392, 591312, 591103, 589902, 589347, 588114, 587821, 587639, 587567, 587514, 587337, 587241, 587239, 587184, 587173, 587164, 587161, 587137, 587132, 587119, 587109, 587106, 587105, 587096, 587095, 587093, 587077, 587068, 587050, 587047, 587044, 587036, 587033, 587007, 587001, 586989, 586959, 586952, 586940, 586922, 586918, 586904, 586894, 586886, 586880, 586869, 586865, 586864, 586863, 586861, 586860, 586859, 586857, 586856, 586853, 586852, 586798, 586749, 586744, 586704, 586696, 586691, 586689, 586683, 586682, 586668, 586664, 586661, 586660, 584780, 583337, 583274, 583265, 583183, 583104, 583082, 583080, 583072, 583069, 583064, 583026, 583024, 583012, 583007, 583003, 582998, 582913, 582872, 582770, 582767, 582756, 582755, 582721, 582503, 582353, 582329, 582308, 580585, 580313, 580279, 580272, 580267, 580195, 580157, 580150, 580143, 580122, 580112, 580111, 580108, 580106, 580105, 580103, 580098, 580090, 578244, 578029, 577889, 577703, 577690, 577683, 576588, 576491, 575946, 575674, 575320, 575310, 573629, 573393, 571935, 571750, 571666, 571643, 571636, 571460, 571147, 571032, 570974, 570672, 570668, 570639, 570634, 570468, 570219, 570117, 570090, 569985, 569977, 569975, 569862, 569847, 569710, 569685, 569494, 569284, 569194, 569130, 569014, 568492, 568402, 568354, 568350, 568335, 568301, 568126, 567969, 567741, 567526, 567514, 567335, 567267, 567248, 567113, 567101, 567085, 566892, 566696, 566693, 566645, 566632, 566589, 566452, 566350, 566329, 566222, 566197, 566187, 566178, 566146, 566139, 566078, 566062, 566061, 566020, 566012, 565978, 565967, 565950, 565914, 565910, 565902, 565891, 565886, 565842, 565833, 565824, 565781, 565740, 565733, 565709, 565577, 565554, 565546, 565420, 565405, 565400, 565376, 565374, 565292, 565279, 565272, 565270, 565269, 565246, 565243, 565237, 565226, 565220, 565204, 565202, 565200, 565191, 565160, 565155, 565148, 565146, 565141, 565121, 565110, 565075, 565059, 565007, 565000, 564991, 564943, 564941, 564909, 564904, 564856, 564838, 564823, 564814, 564789, 564781, 564756, 564733, 564728, 564711, 564697, 564695, 564685, 564661, 564653, 564635, 564630, 564628, 564613, 564588, 564576, 564567, 564563, 564561, 564548, 564528, 564527, 564524, 564516, 564503, 564495, 564493, 564483, 564469, 564458, 564443, 564433, 564421, 564398, 564393, 564356, 564351, 564338, 564330, 564301, 564280, 564278, 564274, 564267, 564259, 564252, 564245, 564243, 564225, 564222, 564213, 564208, 564204, 564189, 564188, 564120, 564111, 564049, 564041, 564040, 564007, 564006, 564004, 563988, 563971, 563917, 563911, 563907, 563897, 563893, 563888, 563884, 563880, 563878, 563875, 563870, 563867, 563863, 563862, 563859, 563829, 563816, 563804, 563789, 563768, 563760, 563754, 563748, 563746, 563742, 563732, 563730, 563712, 563711, 563702, 563698, 563633, 563618, 563610, 563606, 563598, 563491, 563401, 563360, 563349, 563305, 563225, 563219, 563160, 563138, 563097, 563090, 563077, 563065, 563053, 563047, 563027, 563026, 563025, 563008, 562997, 562954, 562944, 562937, 562927, 562920, 562887, 562877, 562876, 562872, 562802, 562795, 562791, 562761, 562753, 562750, 562696, 562695, 562672, 562671, 562666, 562662, 562657, 562655, 562652, 562651, 562648, 562647, 562644, 562642, 562635, 562631, 562614, 562613, 562544, 562534, 562515, 562505, 562489, 562488, 562470, 562418, 562407, 562401, 562393, 562390, 562387, 562381, 562367, 562366, 562364, 562363, 562355, 562350, 562334, 562333, 562331, 562328, 562327, 562319, 562316, 562312, 562275, 562252, 562242, 562239, 562227, 562225, 562224, 562222, 562205, 562197, 562196, 562189, 562186, 562185, 562179, 562178, 562173, 562172, 562152, 562146, 562144, 562134, 562132, 562130, 562129, 562127, 562126, 562123, 562118, 562115, 562109, 562108, 562102, 562096, 562096, 562064, 562061, 562035, 562034, 562010, 561989, 561975, 561948, 561944, 561930, 561903, 561873, 561867, 561856, 561854, 561850, 561847, 561829, 561826, 561816, 561815, 561812, 561810, 561802, 561784, 561762, 561729, 561724, 561718, 561712, 561708, 561707, 561704, 561698, 561694, 561650, 561624, 561621, 561611, 561608, 561601, 561586, 561583, 561574, 561564, 561554, 561543, 561537, 561526, 561522, 561509, 561503, 561500, 561493, 561485, 561484, 561464, 561461, 561457, 561453, 561436, 561434, 561430, 561416, 561407, 561395, 561393, 561388, 561355, 561353, 561352, 561349, 561325, 561323, 561307, 561306, 561305, 561289, 561267, 561262, 561259, 561247, 561244, 561233, 561228, 561219, 561196, 561182, 561178, 561170, 561168, 561165, 561163, 561157, 561152, 561150, 561148, 561146, 561144, 561141, 561140, 561135, 561124, 561121, 561118, 561113, 561109, 561106, 561088, 561079, 561077, 561076, 561075, 561074, 561067, 561050, 561048, 561044, 561037, 561035, 561034, 561033, 561026, 561021, 561018, 561016, 561013, 561010, 561004, 560988, 560985, 560983, 560979, 560978, 560977, 560975, 560966, 560938, 560937, 560936, 560933, 560930, 560926, 560925, 560922, 560919, 560917, 560912, 560899, 560895, 560881, 560880, 560875, 560873, 560872, 560871, 560866, 560864, 560845, 560834, 560833, 560828, 560827, 560820, 560818, 560817, 560811, 560808, 560806, 560801, 560781, 560779, 560777, 560774, 560769, 560752, 560748, 560733, 560727, 560725, 560719, 560716, 560713, 560711, 560709, 560707, 560701, 560699, 560693, 560689, 560688, 560686, 560683, 560679, 560678, 560672, 560671, 560670, 560669, 560666, 560662, 560661, 560659, 560658, 560657, 560655, 560654, 560653, 560650, 560649, 560646, 560645, 560636, 560631, 560622, 560621, 560615, 560613, 560608, 560607, 560604, 560603, 560591, 560589, 560583, 560582, 560574, 560570, 560567, 560566, 560562, 560560, 560556, 560554, 560553, 560552, 560548, 560547, 560540, 560534, 560532, 560530, 560527, 560516, 560514, 560512, 560510, 560503, 560497, 560495, 560492, 560489, 560488, 560487, 560483, 560479, 560476, 560475, 560471, 560468, 560469, 560467, 560461, 560455, 560452, 560450, 560447, 560446, 560444, 560442, 560437, 560434, 560433, 560428, 560427, 560426, 560424, 560421, 560420, 560419, 560418, 560417, 560416, 560415, 560412, 560409, 560407, 560406, 560405, 560404, 560403, 560400, 560397, 560396, 560389, 560387, 560385, 560384, 560381, 560379, 560374, 560373, 560371, 560367, 560366, 560363, 560362, 560361, 560358, 560357, 560354, 560352, 560351, 560350, 560349, 560348, 560347, 560344, 560341, 560339, 560338, 560335, 560334, 560333, 560332, 560329, 560328, 560326, 560325, 560324, 560323, 560321, 560319, 560318, 560316, 560314, 560313, 560312, 560308, 560306, 560304, 560303, 560301, 560299, 560298, 560296, 560295, 560294, 560293, 560292, 560291, 560290, 560288, 560286, 560285, 560284, 560283, 560281, 560276, 560272, 560271, 560270, 560268, 560264, 560263, 560262, 560261, 560260, 560258, 560256, 560253, 560251, 560250, 560248, 560244, 560242, 560240, 560239, 560237, 560225, 559856]";
        JSONObject parameters = new JSONObject();
        parameters.put("accountIds", accountIds);
        parameters.put("startDate", "20160201");
        parameters.put("endDate", "20160307");
        JSONObject result = (JSONObject)CommonHttpClient.commonHTTP("post", "http://115.29.211.17:10110/quanqiubushou/order/getOrderBatchMoneyInfo", parameters);
        Long endTimer = System.currentTimeMillis();
        System.out.println(result);
        System.out.println(beginTimer - endTimer);
        
    }
    
    
    /**
     * 读取指定的信息
     * @param ResourceBundleStr
     * @param name
     * @return
     */
    public static String readString(String ResourceBundleStr, String name)
    {
        String result = "";
        try
        {
            if (StringUtils.isEmpty(ResourceBundleStr))
            {
                ResourceBundleStr = "conf/config";
            }
            ResourceBundle rb = ResourceBundle.getBundle(ResourceBundleStr);
            result = rb.getString(name);
        }
        catch (Exception e)
        {
            System.out.println("读取指定配置信息" + name + "失败!");
        }
        return result;
    }
    
    /**
     * (V2版的jsapi签名，15年的新公众号都已用V3)
     * @param map
     * @return
     */
    public static String getJsApiTicketSign_V2(Map<String, Object> map)
    {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            if (entry.getValue() != "")
            {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
        {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result = result.substring(0, result.length() - 1);
        
        result = DecripUtil.SHA1(result);
        return result;
    }
    /**
     * 获得一个UUID
     * 
     * @return String UUID
     */
    public synchronized static String getUUID(){
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }
    
    /**
     * 将http文件转换为二进制
     * 
     * @param httpFile url
     * @return
     * @throws IOException
     */
    public static byte[] convertHttpFile2Bytes(String httpFile)
        throws IOException
    {
        URL url = new URL(httpFile);
        URLConnection urlConn = url.openConnection();
        return convertInputStream2Bytes(urlConn.getInputStream());
    }
}
