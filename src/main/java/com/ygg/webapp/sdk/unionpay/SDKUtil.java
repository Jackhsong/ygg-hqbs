/*     */package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.io.IOException;
/*     */
import java.io.UnsupportedEncodingException;
/*     */
import java.text.SimpleDateFormat;
/*     */
import java.util.Date;
/*     */
import java.util.HashMap;
/*     */
import java.util.Iterator;
/*     */
import java.util.Map;
/*     */
import java.util.Random;
/*     */
import java.util.Set;
/*     */
import java.util.TreeMap;

/*     */
/*     */
/*     */public class SDKUtil
/*     */
{
    /* 39 */protected static char[] letter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
    /* 40 */'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
    /* 41 */'v', 'w', 'x', 'y', 'z'};
    
    /*     */
    /* 43 */protected static final Random random = new Random();
    
    /*     */
    /*     */public static String send(String url, Map<String, String> data, String encoding, int connectionTimeout, int readTimeout)
    /*     */
    {
        /* 60 */HttpClient hc = new HttpClient(url, connectionTimeout, readTimeout);
        /* 61 */String res = "";
        /*     */try
        {
            /* 63 */int status = hc.send(data, encoding);
            /* 64 */if (200 == status)
                /* 65 */res = hc.getResult();
            /*     */
        }
        /*     */catch (Exception e)
        {
            /* 68 */LogUtil.writeErrorLog("通信异常", e);
            /*     */
        }
        /* 70 */return res;
        /*     */
    }
    
    /*     */
    /*     */public static String signByMd5(Map<String, String> data, String encoding)
    /*     */
    {
        /* 86 */String dataString = coverMap2String(data);
        /* 87 */if ((encoding == null) || ("".equals(encoding)))
        {
            /* 88 */encoding = "UTF-8";
            /*     */
        }
        /* 90 */LogUtil.writeLog("key=value字符串=[" + dataString + "]");
        /*     */
        /* 94 */byte[] signbyte = (byte[])null;
        /*     */try
        {
            /* 96 */byte[] signD = SecureUtil.md5X16(dataString, encoding);
            /* 97 */LogUtil.writeLog("md5->16进制转换后的摘要=[" + new String(signD) + "]");
            /* 98 */signbyte = SecureUtil.base64Encode(SecureUtil.signBySoft(
            /* 99 */CertUtil.getSignCertPrivateKey(), signD));
            /* 100 */return new String(signbyte);
            /*     */
        }
        catch (Exception e)
        {
            /* 102 */LogUtil.writeErrorLog("签名异常", e);
            /* 103 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static boolean sign(Map<String, String> data, String encoding)
    /*     */
    {
        /* 117 */LogUtil.writeLog("签名处理开始.");
        /* 118 */if (isEmpty(encoding))
        {
            /* 119 */encoding = "UTF-8";
            /*     */
        }
        /*     */
        /* 122 */data.put("certId", CertUtil.getSignCertId());
        /*     */
        /* 125 */String stringData = coverMap2String(data);
        /*     */
        /* 127 */LogUtil.writeLog("报文签名之前的字符串(不含signature域)=[" + stringData + "]");
        /*     */
        /* 131 */byte[] byteSign = (byte[])null;
        /* 132 */String stringSign = null;
        /*     */try
        /*     */
        {
            /* 135 */byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
            /* 136 */LogUtil.writeLog("SHA1->16进制转换后的摘要=[" + new String(signDigest) +
            /* 137 */"]");
            /* 138 */byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
            /* 139 */CertUtil.getSignCertPrivateKey(), signDigest));
            /* 140 */stringSign = new String(byteSign);
            /* 141 */LogUtil.writeLog("报文签名之后的字符串=[" + stringSign + "]");
            /*     */
            /* 143 */data.put("signature", stringSign);
            /* 144 */LogUtil.writeLog("签名处理结束.");
            /* 145 */return true;
            /*     */
        }
        catch (Exception e)
        {
            /* 147 */LogUtil.writeErrorLog("签名异常", e);
            /* 148 */
        }
        return false;
        /*     */
    }
    
    /*     */
    /*     */public static boolean signByCertInfo(Map<String, String> data, String encoding, String certPath, String certPwd)
    /*     */
    {
        /* 168 */LogUtil.writeLog("签名处理开始.");
        /* 169 */if (isEmpty(encoding))
        {
            /* 170 */encoding = "UTF-8";
            /*     */
        }
        /* 172 */if ((isEmpty(certPath)) || (isEmpty(certPwd)))
        {
            /* 173 */LogUtil.writeLog("传入参数不合法,签名失败");
            /* 174 */return false;
            /*     */
        }
        /*     */
        /* 177 */data.put("certId", CertUtil.getCertIdByCertPath(
        /* 178 */certPath, certPwd, "PKCS12"));
        /*     */
        /* 180 */String stringData = coverMap2String(data);
        /* 181 */LogUtil.writeLog("报文签名之前的字符串(不含signature域)=[" + stringData + "]");
        /*     */
        /* 185 */byte[] byteSign = (byte[])null;
        /* 186 */String stringSign = null;
        /*     */try
        {
            /* 188 */byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
            /* 189 */LogUtil.writeLog("SHA1->16进制转换后的摘要=[" + new String(signDigest) +
            /* 190 */"]");
            /* 191 */byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(
            /* 192 */CertUtil.getSignCertPrivateKey(certPath, certPwd), signDigest));
            /* 193 */stringSign = new String(byteSign);
            /* 194 */LogUtil.writeLog("报文签名之后的字符串=[" + stringSign + "]");
            /*     */
            /* 196 */data.put("signature", stringSign);
            /* 197 */LogUtil.writeLog("签名处理结束.");
            /* 198 */return true;
            /*     */
        }
        catch (Exception e)
        {
            /* 200 */LogUtil.writeErrorLog("签名异常", e);
            /* 201 */
        }
        return false;
        /*     */
    }
    
    /*     */
    /*     */public static boolean validate(Map<String, String> resData, String encoding)
    /*     */
    {
        /* 261 */LogUtil.writeLog("验签处理开始.");
        /* 262 */if (isEmpty(encoding))
        {
            /* 263 */encoding = "UTF-8";
            /*     */
        }
        /* 265 */String stringSign = (String)resData.get("signature");
        /* 266 */LogUtil.writeLog("返回报文中signature=[" + stringSign + "]");
        /*     */
        /* 269 */String certId = (String)resData.get("certId");
        /* 270 */LogUtil.writeLog("返回报文中certId=[" + certId + "]");
        /*     */
        /* 273 */String stringData = coverMap2String(resData);
        /* 274 */LogUtil.writeLog("返回报文中(不含signature域)的stringData=[" + stringData + "]");
        /*     */try
        /*     */
        {
            /* 278 */return SecureUtil.validateSignBySoft(
            /* 279 */CertUtil.getValidateKey(certId), SecureUtil.base64Decode(stringSign
            /* 280 */.getBytes(encoding)), SecureUtil.sha1X16(stringData,
            /* 281 */encoding));
            /*     */
        }
        catch (UnsupportedEncodingException e)
        {
            /* 283 */LogUtil.writeErrorLog(e.getMessage(), e);
            /*     */
        }
        catch (Exception e)
        {
            /* 285 */LogUtil.writeErrorLog(e.getMessage(), e);
            /*     */
        }
        /* 287 */return false;
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings({"rawtypes", "unchecked"})
    public static String coverMap2String(Map<String, String> data)
    /*     */
    {
        /* 298 */TreeMap tree = new TreeMap();
        /* 299 */Iterator it = data.entrySet().iterator();
        /* 300 */while (it.hasNext())
        {
            /* 301 */Map.Entry en = (Map.Entry)it.next();
            /* 302 */if (!"signature".equals(((String)en.getKey()).trim()))
            /*     */
            {
                /* 305 */tree.put((String)en.getKey(), (String)en.getValue());
                /*     */
            }
            /*     */
        }
        /* 307 */it = tree.entrySet().iterator();
        /* 308 */StringBuffer sf = new StringBuffer();
        /* 309 */while (it.hasNext())
        {
            /* 310 */Map.Entry en = (Map.Entry)it.next();
            /* 311 */sf.append((String)en.getKey() + "=" + (String)en.getValue() +
            /* 312 */"&");
            /*     */
        }
        /* 314 */return sf.substring(0, sf.length() - 1);
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings({"rawtypes", "unchecked"})
    private static Map<String, String> convertResultString2Map(String res)
    /*     */
    {
        /* 325 */Map map = null;
        /* 326 */if ((res != null) && (!"".equals(res.trim())))
        {
            /* 327 */String[] resArray = res.split("&");
            /* 328 */if (resArray.length != 0)
            {
                /* 329 */map = new HashMap(resArray.length);
                /* 330 */for (String arrayStr : resArray)
                    /* 331 */if ((arrayStr != null) && (!"".equals(arrayStr.trim())))
                    /*     */
                    {
                        /* 334 */int index = arrayStr.indexOf("=");
                        /* 335 */if (-1 != index)
                        /*     */
                        {
                            /* 338 */map.put(arrayStr.substring(0, index), arrayStr
                            /* 339 */.substring(index + 1));
                            /*     */
                        }
                        /*     */
                    }
                /*     */
            }
            /*     */
        }
        /* 343 */return map;
        /*     */
    }
    
    /*     */
    /*     */private static void convertResultStringJoinMap(String res, Map<String, String> map)
    /*     */
    {
        /* 354 */if ((res != null) && (!"".equals(res.trim())))
        {
            /* 355 */String[] resArray = res.split("&");
            /* 356 */if (resArray.length != 0)
                /* 357 */for (String arrayStr : resArray)
                    /* 358 */if ((arrayStr != null) && (!"".equals(arrayStr.trim())))
                    /*     */
                    {
                        /* 361 */int index = arrayStr.indexOf("=");
                        /* 362 */if (-1 != index)
                        /*     */
                        {
                            /* 365 */map.put(arrayStr.substring(0, index), arrayStr
                            /* 366 */.substring(index + 1));
                            /*     */
                        }
                        /*     */
                    }
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public static Map<String, String> coverResultString2Map(String result)
    /*     */
    {
        /* 379 */return convertResultStringToMap(result);
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings({"rawtypes", "unchecked"})
    public static Map<String, String> convertResultStringToMap(String result)
    /*     */
    {
        /* 390 */if (result.contains("{"))
        /*     */
        {
            /* 394 */String separator = "\\{";
            /* 395 */String[] res = result.split(separator);
            /*     */
            /* 397 */Map map = new HashMap();
            /*     */
            /* 399 */convertResultStringJoinMap(res[0], map);
            /*     */
            /* 402 */for (int i = 1; i < res.length; i++)
            /*     */
            {
                /* 405 */int index = res[i].indexOf("}");
                /*     */
                /* 407 */String specialValue = "{" +
                /* 408 */res[i].substring(0, index) + "}";
                /*     */
                /* 410 */int indexKey = res[(i - 1)].lastIndexOf("&");
                /* 411 */String specialKey = res[(i - 1)].substring(indexKey + 1,
                /* 412 */res[(i - 1)].length() - 1);
                /*     */
                /* 414 */map.put(specialKey, specialValue);
                /*     */
                /* 417 */String normalResult = res[i].substring(index + 2, res[i]
                /* 418 */.length());
                /*     */
                /* 420 */convertResultStringJoinMap(normalResult, map);
                /*     */
            }
            /*     */
            /* 424 */return map;
            /*     */
        }
        /*     */
        /* 428 */return convertResultString2Map(result);
        /*     */
    }
    
    /*     */
    /*     */public static String encryptPin(String card, String pwd, String encoding)
    /*     */
    {
        /* 445 */return SecureUtil.EncryptPin(pwd, card, encoding,
        /* 446 */CertUtil.getEncryptCertPublicKey());
        /*     */
    }
    
    /*     */
    /*     */public static String encryptCvn2(String cvn2, String encoding)
    /*     */
    {
        /* 459 */return SecureUtil.EncryptData(cvn2, encoding,
        /* 460 */CertUtil.getEncryptCertPublicKey());
        /*     */
    }
    
    /*     */
    /*     */public static String decryptCvn2(String base64cvn2, String encoding)
    /*     */
    {
        /* 473 */return SecureUtil.DecryptedData(base64cvn2, encoding,
        /* 474 */CertUtil.getSignCertPrivateKey());
        /*     */
    }
    
    /*     */
    /*     */public static String encryptAvailable(String date, String encoding)
    /*     */
    {
        /* 487 */return SecureUtil.EncryptData(date, encoding,
        /* 488 */CertUtil.getEncryptCertPublicKey());
        /*     */
    }
    
    /*     */
    /*     */public static String decryptAvailable(String base64Date, String encoding)
    /*     */
    {
        /* 501 */return SecureUtil.DecryptedData(base64Date, encoding,
        /* 502 */CertUtil.getSignCertPrivateKey());
        /*     */
    }
    
    /*     */
    /*     */public static String encryptPan(String pan, String encoding)
    /*     */
    {
        /* 515 */return SecureUtil.EncryptData(pan, encoding,
        /* 516 */CertUtil.getEncryptCertPublicKey());
        /*     */
    }
    
    /*     */
    /*     */public static String decryptPan(String base64Pan, String encoding)
    /*     */
    {
        /* 528 */return SecureUtil.DecryptedData(base64Pan, encoding,
        /* 529 */CertUtil.getSignCertPrivateKey());
        /*     */
    }
    
    /*     */
    /*     */public static String generateCustomerInfo(String customerInfo01, String customerInfo02, String customerInfo03, String customerInfo04,
        String customerInfo05, String customerInfo06, String customerInfo07, String customerInfo08, String pan, String encoding, boolean isEncrypt)
    /*     */
    {
        /* 587 */if (isEmpty(encoding))
        {
            /* 588 */encoding = "UTF-8";
            /*     */
        }
        /*     */
        /* 591 */StringBuffer sf = new StringBuffer("{");
        /*     */
        /* 593 */sf.append((isEmpty(customerInfo01) ? "" : customerInfo01) +
        /* 594 */"|");
        /* 595 */sf.append((isEmpty(customerInfo02) ? "" : customerInfo02) +
        /* 596 */"|");
        /* 597 */sf.append((isEmpty(customerInfo03) ? "" : customerInfo03) +
        /* 598 */"|");
        /* 599 */sf.append((isEmpty(customerInfo04) ? "" : customerInfo04) +
        /* 600 */"|");
        /* 601 */sf.append((isEmpty(customerInfo05) ? "" : customerInfo05) +
        /* 602 */"|");
        /*     */
        /* 604 */if (!isEmpty(customerInfo06))
        {
            /* 605 */if (!isEmpty(pan))
                /* 606 */sf.append(encryptPin(pan.trim(), customerInfo06,
                /* 607 */encoding) +
                /* 608 */"|");
            /*     */else
                /* 610 */sf.append(customerInfo06 + "|");
            /*     */
        }
        /*     */else
        {
            /* 613 */sf.append((isEmpty(customerInfo06) ? "" : customerInfo06) +
            /* 614 */"|");
            /*     */
        }
        /*     */
        /* 617 */if (!isEmpty(customerInfo07))
        {
            /* 618 */if (isEncrypt)
                /* 619 */sf.append(encryptCvn2(customerInfo07, encoding) +
                /* 620 */"|");
            /*     */else
                /* 622 */sf.append(customerInfo07 + "|");
            /*     */
        }
        /*     */else
        {
            /* 625 */sf.append((isEmpty(customerInfo07) ? "" : customerInfo07) +
            /* 626 */"|");
            /*     */
        }
        /*     */
        /* 629 */if (!isEmpty(customerInfo08))
        {
            /* 630 */if (isEncrypt)
                /* 631 */sf.append(encryptAvailable(customerInfo08, encoding));
            /*     */else
            {
                /* 633 */sf.append(customerInfo08);
                /*     */
            }
            /*     */
        }
        /* 636 */sf.append("}");
        /*     */try
        {
            /* 638 */return new String(SecureUtil.base64Encode(sf.toString().getBytes(
            /* 639 */encoding)));
            /*     */
        }
        catch (UnsupportedEncodingException e)
        {
            /* 641 */LogUtil.writeErrorLog(e.getMessage(), e);
            /* 642 */return "";
            /*     */
        }
        catch (IOException e)
        {
            /* 644 */LogUtil.writeErrorLog(e.getMessage(), e);
            /* 645 */
        }
        return "";
        /*     */
    }
    
    /*     */
    /*     */public static boolean isEmpty(String s)
    /*     */
    {
        /* 657 */return (s == null) || ("".equals(s.trim()));
        /*     */
    }
    
    /*     */
    /*     */public static String generateTxnTime()
    /*     */
    {
        /* 666 */return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        /*     */
    }
    
    /*     */
    /*     */public static String generateOrderId()
    /*     */
    {
        /* 675 */StringBuilder sb = new StringBuilder();
        /* 676 */int len = random.nextInt(18);
        /* 677 */for (int i = 0; i < len; i++)
        {
            /* 678 */sb.append(letter[i]);
            /*     */
        }
        /* 680 */return generateTxnTime() + sb.toString();
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static String createAutoSubmitForm(String url, Map<String, String> data)
    /*     */
    {
        /* 692 */StringBuffer sf = new StringBuffer();
        /* 693 */sf.append("<form id = \"sform\" action=\"" + url +
        /* 694 */"\" method=\"post\">");
        /* 695 */if ((data != null) && (data.size() != 0))
        {
            /* 696 */Set set = data.entrySet();
            /* 697 */Iterator it = set.iterator();
            /* 698 */while (it.hasNext())
            {
                /* 699 */Map.Entry ey = (Map.Entry)it.next();
                /* 700 */String key = (String)ey.getKey();
                /* 701 */String value = (String)ey.getValue();
                /* 702 */sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" +
                /* 703 */key + "\" value=\"" + value + "\"/>");
                /*     */
            }
            /*     */
        }
        /* 706 */sf.append("</form>");
        /* 707 */sf.append("</body>");
        /* 708 */sf.append("<script type=\"text/javascript\">");
        /* 709 */sf.append("document.getElementById(\"sform\").submit();\n");
        /* 710 */sf.append("</script>");
        /* 711 */return sf.toString();
        /*     */
    }
    
    /*     */
    /*     */public static String createCombDomain(Map<String, String> data)
    {
        /* 715 */return "";
        /*     */
    }
    /*     */
}
