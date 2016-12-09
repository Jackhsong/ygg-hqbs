/*     */package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.io.File;
/*     */
import java.io.FileInputStream;
/*     */
import java.io.FileNotFoundException;
/*     */
import java.io.IOException;
/*     */
import java.io.InputStream;
/*     */
import java.util.Properties;

/*     */
/*     */
/*     */public class SDKConfig
/*     */
{
    /*     */public static final String FILE_NAME = "unionpay_sdk.properties";
    
    /*     */private String frontRequestUrl;
    
    /*     */private String backRequestUrl;
    
    /*     */private String singleQueryUrl;
    
    /*     */private String batchQueryUrl;
    
    /*     */private String batchTransUrl;
    
    /*     */private String fileTransUrl;
    
    /*     */private String signCertPath;
    
    /*     */private String signCertPwd;
    
    /*     */private String signCertType;
    
    /*     */private String encryptCertPath;
    
    /*     */private String validateCertPath;
    
    /*     */private String validateCertDir;
    
    /*     */private String signCertDir;
    
    /*     */private String cbFrontRequestUrl;
    
    /*     */private String cbBackRequestUrl;
    
    /*     */private String cardRequestUrl;
    
    /*     */private String appRequestUrl;
    
    /*     */public static final String SDK_FRONT_URL = "acpsdk.frontTransUrl";
    
    /*     */public static final String SDK_BACK_URL = "acpsdk.backTransUrl";
    
    /*     */public static final String SDK_SIGNQ_URL = "acpsdk.singleQueryUrl";
    
    /*     */public static final String SDK_BATQ_URL = "acpsdk.batchQueryUrl";
    
    /*     */public static final String SDK_BATTRANS_URL = "acpsdk.batchTransUrl";
    
    /*     */public static final String SDK_FILETRANS_URL = "acpsdk.fileTransUrl";
    
    /*     */public static final String SDK_CB_FRONT_URL = "acpsdk.cbFrontTransUrl";
    
    /*     */public static final String SDK_CB_BACK_URL = "acpsdk.cbBackTransUrl";
    
    /*     */public static final String SDK_CARD_URL = "acpsdk.cardTransUrl";
    
    /*     */public static final String SDK_APP_URL = "acpsdk.appTransUrl";
    
    /*     */public static final String SDK_SIGNCERT_PATH = "acpsdk.signCert.path";
    
    /*     */public static final String SDK_SIGNCERT_PWD = "acpsdk.signCert.pwd";
    
    /*     */public static final String SDK_SIGNCERT_TYPE = "acpsdk.signCert.type";
    
    /*     */public static final String SDK_ENCRYPTCERT_PATH = "acpsdk.encryptCert.path";
    
    /*     */public static final String SDK_VALIDATECERT_PATH = "acpsdk.validateCert.path";
    
    /*     */public static final String SDK_VALIDATECERT_DIR = "acpsdk.validateCert.dir";
    
    /*     */public static final String SDK_CVN_ENC = "acpsdk.cvn2.enc";
    
    /*     */public static final String SDK_DATE_ENC = "acpsdk.date.enc";
    
    /*     */public static final String SDK_PAN_ENC = "acpsdk.pan.enc";
    
    /*     */public static final String SDK_SIGNCERT_DIR = "acpsdk.signCert.dir";
    
    /*     */private static SDKConfig config;
    
    /*     */private Properties properties;
    
    /*     */
    /*     */public static SDKConfig getConfig()
    /*     */
    {
        /* 136 */if (config == null)
        {
            /* 137 */config = new SDKConfig();
            /*     */
        }
        /* 139 */return config;
        /*     */
    }
    
    /*     */
    /*     */public void loadPropertiesFromPath(String rootPath)
    /*     */
    {
        /* 149 */File file = new File(rootPath + File.separator + FILE_NAME);
        /* 150 */InputStream in = null;
        /* 151 */if (file.exists())
        {
            /*     */try
            {
                /* 153 */in = new FileInputStream(file);
                /* 154 */this.properties = new Properties();
                /* 155 */this.properties.load(in);
                /* 156 */loadProperties(this.properties);
                /*     */
            }
            catch (FileNotFoundException e)
            {
                /* 158 */e.printStackTrace();
                /*     */
                /* 162 */if (in == null)
                    return;
                /*     */try
                {
                    /* 164 */in.close();
                    /*     */
                }
                catch (IOException e1)
                {
                    /* 166 */e1.printStackTrace();
                    /*     */
                }
                /*     */
            }
            /*     */catch (IOException e)
            /*     */
            {
                /* 160 */e.printStackTrace();
                /*     */
                /* 162 */if (in == null)
                    return;
                /*     */try
                {
                    /* 164 */in.close();
                    /*     */
                }
                catch (IOException e2)
                {
                    /* 166 */e2.printStackTrace();
                    /*     */
                }
                /*     */
            }
            /*     */finally
            /*     */
            {
                /* 162 */if (in != null)
                    /*     */try
                    {
                        /* 164 */in.close();
                        /*     */
                    }
                    catch (IOException e)
                    {
                        /* 166 */e.printStackTrace();
                        /*     */
                    }
                /*     */
            }
            /*     */try
            /*     */
            {
                /* 164 */in.close();
                /*     */
            }
            catch (IOException e)
            {
                /* 166 */e.printStackTrace();
                /*     */
            }
            /*     */
            /*     */
        }
        /*     */else
        /*     */
        {
            // /* 172 */ System.out.println(rootPath + "unionpay.properties" + "不存在,加载参数失败");
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public void loadPropertiesFromSrc()
    /*     */
    {
        /* 180 */InputStream in = null;
        /*     */try
        /*     */
        {
            /* 183 */in = SDKConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
            /* 184 */
            /* 185 */if (in != null)
            {
                /* 186 */this.properties = new Properties();
                /*     */try
                {
                    /* 188 */this.properties.load(in);
                    /*     */
                }
                catch (IOException e)
                {
                    /* 190 */throw e;
                    /*     */
                }
                /*     */
            }
            /* 193 */loadProperties(this.properties);
            /*     */
        }
        catch (IOException e)
        {
            /* 195 */e.printStackTrace();
            /*     */
            /* 197 */if (in != null)
                /*     */try
                {
                    /* 199 */in.close();
                    /*     */
                }
                catch (IOException e3)
                {
                    /* 201 */e3.printStackTrace();
                    /*     */
                }
            /*     */
        }
        /*     */finally
        /*     */
        {
            /* 197 */if (in != null)
                /*     */try
                {
                    /* 199 */in.close();
                    /*     */
                }
                catch (IOException e)
                {
                    /* 201 */e.printStackTrace();
                    /*     */
                }
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public void loadProperties(Properties pro)
    /*     */
    {
        /* 213 */String value = pro.getProperty("acpsdk.frontTransUrl");
        /* 214 */if (!SDKUtil.isEmpty(value))
        {
            /* 215 */this.frontRequestUrl = value.trim();
            /*     */
        }
        /* 217 */value = pro.getProperty("acpsdk.backTransUrl");
        /* 218 */if (!SDKUtil.isEmpty(value))
        {
            /* 219 */this.backRequestUrl = value.trim();
            /*     */
        }
        /* 221 */value = pro.getProperty("acpsdk.signCert.path");
        /* 222 */if (!SDKUtil.isEmpty(value))
        {
            /* 223 */this.signCertPath = value.trim();
            /*     */
        }
        /* 225 */value = pro.getProperty("acpsdk.signCert.pwd");
        /* 226 */if (!SDKUtil.isEmpty(value))
        {
            /* 227 */this.signCertPwd = value.trim();
            /*     */
        }
        /* 229 */value = pro.getProperty("acpsdk.signCert.type");
        /* 230 */if (!SDKUtil.isEmpty(value))
        {
            /* 231 */this.signCertType = value.trim();
            /*     */
        }
        /* 233 */value = pro.getProperty("acpsdk.encryptCert.path");
        /* 234 */if (!SDKUtil.isEmpty(value))
        {
            /* 235 */this.encryptCertPath = value.trim();
            /*     */
        }
        /* 237 */value = pro.getProperty("acpsdk.validateCert.path");
        /* 238 */if (!SDKUtil.isEmpty(value))
        {
            /* 239 */this.validateCertPath = value.trim();
            /*     */
        }
        /* 241 */value = pro.getProperty("acpsdk.validateCert.dir");
        /* 242 */if (!SDKUtil.isEmpty(value))
        {
            /* 243 */this.validateCertDir = value.trim();
            /*     */
        }
        /* 245 */value = pro.getProperty("acpsdk.batchQueryUrl");
        /* 246 */if (!SDKUtil.isEmpty(value))
        {
            /* 247 */this.batchQueryUrl = value.trim();
            /*     */
        }
        /* 249 */value = pro.getProperty("acpsdk.batchTransUrl");
        /* 250 */if (!SDKUtil.isEmpty(value))
        {
            /* 251 */this.batchTransUrl = value.trim();
            /*     */
        }
        /* 253 */value = pro.getProperty("acpsdk.fileTransUrl");
        /* 254 */if (!SDKUtil.isEmpty(value))
        {
            /* 255 */this.fileTransUrl = value.trim();
            /*     */
        }
        /* 257 */value = pro.getProperty("acpsdk.singleQueryUrl");
        /* 258 */if (!SDKUtil.isEmpty(value))
        {
            /* 259 */this.singleQueryUrl = value.trim();
            /*     */
        }
        /* 261 */value = pro.getProperty("acpsdk.signCert.dir");
        /* 262 */if (!SDKUtil.isEmpty(value))
        {
            /* 263 */this.signCertDir = value.trim();
            /*     */
        }
        /*     */
        /* 266 */value = pro.getProperty("acpsdk.cbFrontTransUrl");
        /* 267 */if (!SDKUtil.isEmpty(value))
        {
            /* 268 */this.cbFrontRequestUrl = value.trim();
            /*     */
        }
        /* 270 */value = pro.getProperty("acpsdk.cbBackTransUrl");
        /* 271 */if (!SDKUtil.isEmpty(value))
        {
            /* 272 */this.cbBackRequestUrl = value.trim();
            /*     */
        }
        /*     */
        /* 275 */value = pro.getProperty("acpsdk.cardTransUrl");
        /* 276 */if (!SDKUtil.isEmpty(value))
        {
            /* 277 */this.cardRequestUrl = value.trim();
            /*     */
        }
        /*     */
        /* 280 */value = pro.getProperty("acpsdk.appTransUrl");
        /* 281 */if (!SDKUtil.isEmpty(value))
            /* 282 */this.appRequestUrl = value.trim();
        /*     */
    }
    
    /*     */
    /*     */public String getFrontRequestUrl()
    /*     */
    {
        /* 290 */return this.frontRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setFrontRequestUrl(String frontRequestUrl)
    {
        /* 294 */this.frontRequestUrl = frontRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getBackRequestUrl()
    {
        /* 298 */return this.backRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setBackRequestUrl(String backRequestUrl)
    {
        /* 302 */this.backRequestUrl = backRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getSignCertPath()
    {
        /* 306 */return this.signCertPath;
        /*     */
    }
    
    /*     */
    /*     */public void setSignCertPath(String signCertPath)
    {
        /* 310 */this.signCertPath = signCertPath;
        /*     */
    }
    
    /*     */
    /*     */public String getSignCertPwd()
    {
        /* 314 */return this.signCertPwd;
        /*     */
    }
    
    /*     */
    /*     */public void setSignCertPwd(String signCertPwd)
    {
        /* 318 */this.signCertPwd = signCertPwd;
        /*     */
    }
    
    /*     */
    /*     */public String getSignCertType()
    {
        /* 322 */return this.signCertType;
        /*     */
    }
    
    /*     */
    /*     */public void setSignCertType(String signCertType)
    {
        /* 326 */this.signCertType = signCertType;
        /*     */
    }
    
    /*     */
    /*     */public String getEncryptCertPath()
    {
        /* 330 */return this.encryptCertPath;
        /*     */
    }
    
    /*     */
    /*     */public void setEncryptCertPath(String encryptCertPath)
    {
        /* 334 */this.encryptCertPath = encryptCertPath;
        /*     */
    }
    
    /*     */
    /*     */public String getValidateCertPath()
    {
        /* 338 */return this.validateCertPath;
        /*     */
    }
    
    /*     */
    /*     */public void setValidateCertPath(String validateCertPath)
    {
        /* 342 */this.validateCertPath = validateCertPath;
        /*     */
    }
    
    /*     */
    /*     */public String getValidateCertDir()
    {
        /* 346 */return this.validateCertDir;
        /*     */
    }
    
    /*     */
    /*     */public void setValidateCertDir(String validateCertDir)
    {
        /* 350 */this.validateCertDir = validateCertDir;
        /*     */
    }
    
    /*     */
    /*     */public String getSingleQueryUrl()
    {
        /* 354 */return this.singleQueryUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setSingleQueryUrl(String singleQueryUrl)
    {
        /* 358 */this.singleQueryUrl = singleQueryUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getBatchQueryUrl()
    {
        /* 362 */return this.batchQueryUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setBatchQueryUrl(String batchQueryUrl)
    {
        /* 366 */this.batchQueryUrl = batchQueryUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getBatchTransUrl()
    {
        /* 370 */return this.batchTransUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setBatchTransUrl(String batchTransUrl)
    {
        /* 374 */this.batchTransUrl = batchTransUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getFileTransUrl()
    {
        /* 378 */return this.fileTransUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setFileTransUrl(String fileTransUrl)
    {
        /* 382 */this.fileTransUrl = fileTransUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getSignCertDir()
    {
        /* 386 */return this.signCertDir;
        /*     */
    }
    
    /*     */
    /*     */public void setSignCertDir(String signCertDir)
    {
        /* 390 */this.signCertDir = signCertDir;
        /*     */
    }
    
    /*     */
    /*     */public Properties getProperties()
    {
        /* 394 */return this.properties;
        /*     */
    }
    
    /*     */
    /*     */public void setProperties(Properties properties)
    {
        /* 398 */this.properties = properties;
        /*     */
    }
    
    /*     */
    /*     */public String getCbFrontRequestUrl()
    {
        /* 402 */return this.cbFrontRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setCbFrontRequestUrl(String cbFrontRequestUrl)
    {
        /* 406 */this.cbFrontRequestUrl = cbFrontRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getCbBackRequestUrl()
    {
        /* 410 */return this.cbBackRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setCbBackRequestUrl(String cbBackRequestUrl)
    {
        /* 414 */this.cbBackRequestUrl = cbBackRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getCardRequestUrl()
    {
        /* 418 */return this.cardRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setCardRequestUrl(String cardRequestUrl)
    {
        /* 422 */this.cardRequestUrl = cardRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public String getAppRequestUrl()
    {
        /* 426 */return this.appRequestUrl;
        /*     */
    }
    
    /*     */
    /*     */public void setAppRequestUrl(String appRequestUrl)
    {
        /* 430 */this.appRequestUrl = appRequestUrl;
        /*     */
    }
    /*     */
}
