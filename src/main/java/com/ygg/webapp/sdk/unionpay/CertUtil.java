package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.io.File;
/*     */
import java.io.FileInputStream;
/*     */
import java.io.FileNotFoundException;
/*     */
import java.io.FilenameFilter;
/*     */
import java.io.IOException;
/*     */
import java.security.KeyStore;
/*     */
import java.security.KeyStoreException;
/*     */
import java.security.PrivateKey;
/*     */
import java.security.PublicKey;
/*     */
import java.security.Security;
/*     */
import java.security.cert.Certificate;
/*     */
import java.security.cert.CertificateException;
/*     */
import java.security.cert.CertificateFactory;
/*     */
import java.security.cert.X509Certificate;
/*     */
import java.util.Enumeration;
/*     */
import java.util.HashMap;
/*     */
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*     */
/*     */
/*     */
/*     */
/*     */public class CertUtil
/*     */
{
    /* 38 */private static KeyStore keyStore = null;
    
    /*     */
    /* 40 */private static X509Certificate encryptCert = null;
    
    /*     */
    /* 42 */private static X509Certificate validateCert = null;
    
    /*     */
    /* 44 */private static Map<String, X509Certificate> certMap = new HashMap<String, X509Certificate>();
    
    /*     */
    /* 46 */private static KeyStore certKeyStore = null;
    /*     */
    /*     */static
    /*     */
    {
        /* 52 */init();
        /*     */
    }
    
    /*     */
    /*     */public static void init()
    /*     */
    {
        /* 59 */initSignCert();
        /* 60 */initEncryptCert();
        /*     */
        /* 62 */initValidateCertFromDir();
        /*     */
    }
    
    /*     */
    /*     */public static void initSignCert()
    /*     */
    {
        /* 69 */LogUtil.writeLog("加载签名证书开始");
        /* 70 */if (keyStore != null)
        {
            /* 71 */keyStore = null;
            /*     */
        }
        /* 73 */keyStore = getKeyInfo(SDKConfig.getConfig().getSignCertPath(),
        /* 74 */SDKConfig.getConfig().getSignCertPwd(), SDKConfig.getConfig()
        /* 75 */.getSignCertType());
        /*     */
        /* 77 */LogUtil.writeLog("[" + SDKConfig.getConfig().getSignCertPath() +
        /* 78 */"][serialNumber=" + getSignCertId() + "]");
        /* 79 */LogUtil.writeLog("加载签名证书结束");
        /*     */
    }
    
    /*     */
    /*     */public static void initSignCert(String certFilePath, String certPwd)
    /*     */
    {
        /* 87 */LogUtil.writeLog("加载证书文件[" + certFilePath + "]和证书密码[" + certPwd +
        /* 88 */"]的签名证书开始.");
        /* 89 */File files = new File(certFilePath);
        /* 90 */if (!files.exists())
        {
            /* 91 */LogUtil.writeLog("证书文件不存在,初始化签名证书失败.");
            /* 92 */return;
            /*     */
        }
        /* 94 */if (certKeyStore != null)
        {
            /* 95 */certKeyStore = null;
            /*     */
        }
        /* 97 */certKeyStore = getKeyInfo(certFilePath, certPwd, "PKCS12");
        /* 98 */LogUtil.writeLog("加载证书文件[" + certFilePath + "]和证书密码[" + certPwd +
        /* 99 */"]的签名证书结束.");
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("unused")
    public static void initEncryptCert()
    /*     */
    {
        /* 106 */LogUtil.writeLog("加载密码加密证书开始");
        /* 107 */String path = SDKConfig.getConfig().getEncryptCertPath();
        /* 108 */if ((path == null) || ("".equals(path)))
        {
            /* 109 */LogUtil.writeLog("加载密码加密证书路径是空");
            /* 110 */return;
            /*     */
        }
        /* 112 */CertificateFactory cf = null;
        /* 113 */FileInputStream in = null;
        /*     */try
        {
            /* 115 */cf = CertificateFactory.getInstance("X.509");
            /* 116 */in = new FileInputStream(SDKConfig.getConfig().getEncryptCertPath());
            /* 117 */encryptCert = (X509Certificate)cf.generateCertificate(in);
            /*     */
            /* 119 */LogUtil.writeLog("[" + SDKConfig.getConfig().getEncryptCertPath() +
            /* 120 */"][serialNumber=" + getEncryptCertId() + "]");
            /*     */
        }
        catch (CertificateException e)
        {
            /* 122 */LogUtil.writeErrorLog("加密证书加载失败", e);
            /*     */
            /* 126 */if (in != null)
                /*     */try
                {
                    /* 128 */in.close();
                    /*     */
                }
                catch (IOException e1)
                {
                    /* 130 */LogUtil.writeErrorLog(e1.toString());
                    /*     */
                }
            /*     */
        }
        /*     */catch (FileNotFoundException e)
        /*     */
        {
            /* 124 */LogUtil.writeErrorLog("加密证书加载失败,文件不存在", e);
            /*     */
            /* 126 */if (in != null)
                /*     */try
                {
                    /* 128 */in.close();
                    /*     */
                }
                catch (IOException e2)
                {
                    /* 130 */LogUtil.writeErrorLog(e2.toString());
                    /*     */
                }
            /*     */
        }
        /*     */finally
        /*     */
        {
            /* 126 */if (in != null)
            {
                /*     */try
                {
                    /* 128 */in.close();
                    /*     */
                }
                catch (IOException e)
                {
                    /* 130 */LogUtil.writeErrorLog(e.toString());
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /* 134 */LogUtil.writeLog("加载密码加密证书结束");
        /*     */
    }
    
    /*     */
    /*     *//** @deprecated */
    /*     */@SuppressWarnings("unused")
    public static void initValidateCert()
    /*     */
    {
        /* 143 */LogUtil.writeLog("加载验证签名证书");
        /* 144 */String path = SDKConfig.getConfig().getValidateCertPath();
        /* 145 */if ((path == null) || ("".equals(path)))
        {
            /* 146 */LogUtil.writeLog("验证签名证书路径为空");
            /* 147 */return;
            /*     */
        }
        /* 149 */CertificateFactory cf = null;
        /* 150 */FileInputStream in = null;
        /*     */try
        {
            /* 152 */cf = CertificateFactory.getInstance("X.509");
            /* 153 */in = new FileInputStream(SDKConfig.getConfig()
            /* 154 */.getValidateCertPath());
            /* 155 */validateCert = (X509Certificate)cf.generateCertificate(in);
            /*     */
        }
        catch (CertificateException e)
        {
            /* 157 */LogUtil.writeErrorLog("验证签名证书加载失败", e);
            /*     */
            /* 161 */if (in != null)
                /*     */try
                {
                    /* 163 */in.close();
                    /*     */
                }
                catch (IOException e3)
                {
                    /* 165 */LogUtil.writeErrorLog(e3.toString());
                    /*     */
                }
            /*     */
        }
        /*     */catch (FileNotFoundException e)
        /*     */
        {
            /* 159 */LogUtil.writeErrorLog("验证签名证书加载失败,证书文件不存在", e);
            /*     */
            /* 161 */if (in != null)
                /*     */try
                {
                    /* 163 */in.close();
                    /*     */
                }
                catch (IOException e4)
                {
                    /* 165 */LogUtil.writeErrorLog(e4.toString());
                    /*     */
                }
            /*     */
        }
        /*     */finally
        /*     */
        {
            /* 161 */if (in != null)
            {
                /*     */try
                {
                    /* 163 */in.close();
                    /*     */
                }
                catch (IOException e)
                {
                    /* 165 */LogUtil.writeErrorLog(e.toString());
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /* 169 */LogUtil.writeLog("加载验证签名证书结束 ");
        /*     */
    }
    
    /*     */
    /*     */public static void initValidateCertFromDir()
    /*     */
    {
        /* 177 */LogUtil.writeLog("从目录中加载验证签名证书开始.");
        /* 178 */certMap.clear();
        /* 179 */String filePath = SDKConfig.getConfig().getValidateCertDir();
        /* 180 */if ((filePath == null) || ("".equals(filePath)))
        {
            /* 181 */LogUtil.writeLog("验证签名证书路径配置为空.");
            /* 182 */return;
            /*     */
        }
        /*     */
        /* 185 */CertificateFactory cf = null;
        /* 186 */FileInputStream in = null;
        /*     */try
        /*     */
        {
            /* 189 */cf = CertificateFactory.getInstance("X.509");
            /* 190 */File fileDir = new File(filePath);
            /* 191 */File[] files = fileDir.listFiles(new CerFilter());
            /* 192 */for (int i = 0; i < files.length; i++)
            {
                /* 193 */File file = files[i];
                /* 194 */in = new FileInputStream(file.getAbsolutePath());
                /* 195 */validateCert = (X509Certificate)cf.generateCertificate(in);
                /* 196 */certMap.put(validateCert.getSerialNumber().toString(),
                /* 197 */validateCert);
                /*     */
                /* 199 */LogUtil.writeLog("[" + file.getAbsolutePath() +
                /* 200 */"][serialNumber=" +
                /* 201 */validateCert.getSerialNumber().toString() + "]");
                /*     */
            }
            /*     */
        }
        catch (CertificateException e)
        {
            /* 204 */LogUtil.writeErrorLog("验证签名证书加载失败", e);
            /*     */
            /* 208 */if (in != null)
                /*     */try
                {
                    /* 210 */in.close();
                    /*     */
                }
                catch (IOException e5)
                {
                    /* 212 */LogUtil.writeErrorLog(e5.toString());
                    /*     */
                }
            /*     */
        }
        /*     */catch (FileNotFoundException e)
        /*     */
        {
            /* 206 */LogUtil.writeErrorLog("验证签名证书加载失败,证书文件不存在", e);
            /*     */
            /* 208 */if (in != null)
                /*     */try
                {
                    /* 210 */in.close();
                    /*     */
                }
                catch (IOException e6)
                {
                    /* 212 */LogUtil.writeErrorLog(e6.toString());
                    /*     */
                }
            /*     */
        }
        /*     */finally
        /*     */
        {
            /* 208 */if (in != null)
            {
                /*     */try
                {
                    /* 210 */in.close();
                    /*     */
                }
                catch (IOException e)
                {
                    /* 212 */LogUtil.writeErrorLog(e.toString());
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /* 216 */LogUtil.writeLog("从目录中加载验证签名证书结束.");
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static PrivateKey getSignCertPrivateKey()
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 226 */Enumeration aliasenum = keyStore.aliases();
            /* 227 */String keyAlias = null;
            /* 228 */if (aliasenum.hasMoreElements())
            {
                /* 229 */keyAlias = (String)aliasenum.nextElement();
                /*     */
            }
            /* 231 */return (PrivateKey)keyStore.getKey(keyAlias,
            /* 232 */SDKConfig.getConfig().getSignCertPwd().toCharArray());
            /*     */
        }
        /*     */catch (Exception e)
        {
            /* 235 */LogUtil.writeErrorLog("获取签名证书的私钥失败", e);
            /* 236 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static PrivateKey getSignCertPrivateKey(String certPath, String certPwd)
    /*     */
    {
        /* 252 */initSignCert(certPath, certPwd);
        /*     */try
        {
            /* 254 */Enumeration aliasenum = certKeyStore.aliases();
            /* 255 */String keyAlias = null;
            /* 256 */if (aliasenum.hasMoreElements())
            {
                /* 257 */keyAlias = (String)aliasenum.nextElement();
                /*     */
            }
            /* 259 */return (PrivateKey)certKeyStore.getKey(keyAlias,
            /* 260 */certPwd.toCharArray());
            /*     */
        }
        /*     */catch (Exception e)
        {
            /* 263 */LogUtil.writeErrorLog("获取[" + certPath + "]的签名证书的私钥失败", e);
            /* 264 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static PublicKey getEncryptCertPublicKey()
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 275 */if (encryptCert == null)
            {
                /* 276 */initEncryptCert();
                /*     */
            }
            /* 278 */return encryptCert.getPublicKey();
            /*     */
        }
        catch (Exception e)
        {
            /* 280 */LogUtil.writeErrorLog("获取加密证书失败", e);
            /* 281 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static PublicKey getValidateKey()
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 299 */if (validateCert == null)
            {
                /* 300 */return null;
                /*     */
            }
            /* 302 */return validateCert.getPublicKey();
            /*     */
        }
        catch (Exception e)
        {
            /* 304 */LogUtil.writeErrorLog("获取验证签名证书失败", e);
            /* 305 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static PublicKey getValidateKey(String certId)
    /*     */
    {
        /* 318 */X509Certificate cf = null;
        /* 319 */if (certMap.containsKey(certId))
        /*     */
        {
            /* 321 */cf = (X509Certificate)certMap.get(certId);
            /* 322 */return cf.getPublicKey();
            /*     */
        }
        /*     */
        /* 325 */initValidateCertFromDir();
        /* 326 */if (certMap.containsKey(certId))
        /*     */
        {
            /* 328 */cf = (X509Certificate)certMap.get(certId);
            /* 329 */return cf.getPublicKey();
            /*     */
        }
        /* 331 */LogUtil.writeErrorLog("没有certId=[" + certId +
        /* 332 */"]对应的验签证书文件,返回NULL.");
        /* 333 */return null;
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static String getSignCertId()
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 345 */Enumeration aliasenum = keyStore.aliases();
            /* 346 */String keyAlias = null;
            /* 347 */if (aliasenum.hasMoreElements())
            {
                /* 348 */keyAlias = (String)aliasenum.nextElement();
                /*     */
            }
            /* 350 */X509Certificate cert = (X509Certificate)keyStore
            /* 351 */.getCertificate(keyAlias);
            /* 352 */return cert.getSerialNumber().toString();
            /*     */
        }
        catch (Exception e)
        {
            /* 354 */LogUtil.writeErrorLog("获取签名证书的序列号失败", e);
            /* 355 */if (keyStore == null)
                /* 356 */LogUtil.writeErrorLog("keyStore实例化失败,当前为NULL");
            /*     */
        }
        /* 358 */return "";
        /*     */
    }
    
    /*     */
    /*     */public static String getEncryptCertId()
    /*     */
    {
        /* 368 */if (encryptCert == null)
        {
            /* 369 */initEncryptCert();
            /*     */
        }
        /* 371 */return encryptCert.getSerialNumber().toString();
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static PublicKey getSignPublicKey()
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 381 */Enumeration aliasenum = keyStore.aliases();
            /* 382 */String keyAlias = null;
            /* 383 */if (aliasenum.hasMoreElements())
            /*     */
            {
                /* 386 */keyAlias = (String)aliasenum.nextElement();
                /*     */
            }
            /*     */
            /* 389 */Certificate cert = keyStore.getCertificate(keyAlias);
            /* 390 */return cert.getPublicKey();
            /*     */
        }
        /*     */catch (Exception e)
        {
            /* 393 */LogUtil.writeErrorLog(e.toString());
            /* 394 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static KeyStore getKeyInfo(String pfxkeyfile, String keypwd, String type)
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 412 */LogUtil.writeLog("KeyStore Loading Start...");
            /* 413 */KeyStore ks = null;
            /* 414 */if ("JKS".equals(type))
            {
                /* 415 */ks = KeyStore.getInstance(type);
                /* 416 */
            }
            else if ("PKCS12".equals(type))
            /*     */
            {
                /* 418 */Security.addProvider(new BouncyCastleProvider());
                /*     */
                /* 420 */ks = KeyStore.getInstance(type);
                /*     */
            }
            /* 422 */LogUtil.writeLog("传入的私钥证书路径为=>[" + pfxkeyfile + "],密码=[" + keypwd +
            /* 423 */"]");
            /* 424 */FileInputStream fis = new FileInputStream(pfxkeyfile);
            /* 425 */char[] nPassword = (char[])null;
            /* 426 */nPassword = (keypwd == null) || ("".equals(keypwd.trim())) ? null :
            /* 427 */keypwd.toCharArray();
            /* 428 */if (ks != null)
            {
                /* 429 */ks.load(fis, nPassword);
                /*     */
            }
            /* 431 */fis.close();
            /* 432 */LogUtil.writeLog("KeyStore Loading End...");
            /* 433 */return ks;
            /*     */
        }
        catch (Exception e)
        {
            /* 435 */if (Security.getProvider("BC") == null)
            {
                /* 436 */LogUtil.writeLog("BC Provider not installed.");
                /*     */
            }
            /* 438 */LogUtil.writeErrorLog("读取私钥证书失败", e);
            /* 439 */if (((e instanceof KeyStoreException)) && ("PKCS12".equals(type)))
                /* 440 */Security.removeProvider("BC");
            /*     */
        }
        /* 442 */return null;
        /*     */
    }
    
    /*     */
    /*     */public static void printSysInfo()
    /*     */
    {
        /* 448 */System.out
        /* 449 */.println("======================= SYS INFO begin===========================");
        // /* 450 */ System.out.println("java_vendor:" + System.getProperty("java.vendor"));
        // /* 451 */ System.out.println("java_vendor_url:" +
        // /* 452 */ System.getProperty("java.vendor.url"));
        // /* 453 */ System.out.println("java_home:" + System.getProperty("java.home"));
        // /* 454 */ System.out.println("java_class_version:" +
        // /* 455 */ System.getProperty("java.class.version"));
        // /* 456 */ System.out.println("java_class_path:" +
        // /* 457 */ System.getProperty("java.class.path"));
        // /* 458 */ System.out.println("os_name:" + System.getProperty("os.name"));
        // /* 459 */ System.out.println("os_arch:" + System.getProperty("os.arch"));
        // /* 460 */ System.out.println("os_version:" + System.getProperty("os.version"));
        // /* 461 */ System.out.println("user_name:" + System.getProperty("user.name"));
        // /* 462 */ System.out.println("user_home:" + System.getProperty("user.home"));
        // /* 463 */ System.out.println("user_dir:" + System.getProperty("user.dir"));
        // /* 464 */ System.out.println("java_vm_specification_version:" +
        // /* 465 */ System.getProperty("java.vm.specification.version"));
        // /* 466 */ System.out.println("java_vm_specification_vendor:" +
        // /* 467 */ System.getProperty("java.vm.specification.vendor"));
        // /* 468 */ System.out.println("java_vm_specification_name:" +
        // /* 469 */ System.getProperty("java.vm.specification.name"));
        // /* 470 */ System.out.println("java_vm_version:" +
        // /* 471 */ System.getProperty("java.vm.version"));
        // /* 472 */ System.out.println("java_vm_vendor:" +
        // /* 473 */ System.getProperty("java.vm.vendor"));
        /* 474 */System.out
        /* 475 */.println("java_vm_name:" + System.getProperty("java.vm.name"));
        // /* 476 */ System.out.println("java_ext_dirs:" +
        // /* 477 */ System.getProperty("java.ext.dirs"));
        // /* 478 */ System.out.println("file_separator:" +
        // /* 479 */ System.getProperty("file.separator"));
        // /* 480 */ System.out.println("path_separator:" +
        // /* 481 */ System.getProperty("path.separator"));
        // /* 482 */ System.out.println("line_separator:" +
        // /* 483 */ System.getProperty("line.separator"));
        /* 484 */System.out
        /* 485 */.println("======================= SYS INFO end===========================");
        /*     */
    }
    
    /*     */
    /*     */@SuppressWarnings("rawtypes")
    public static String getCertIdByCertPath(String path, String pwd, String certTp)
    /*     */
    {
        /* 519 */KeyStore ks = getKeyInfo(path, pwd, certTp);
        /* 520 */if (ks == null)
            /* 521 */return "";
        /*     */try
        /*     */
        {
            /* 524 */Enumeration aliasenum = ks.aliases();
            /* 525 */String keyAlias = null;
            /* 526 */if (aliasenum.hasMoreElements())
            {
                /* 527 */keyAlias = (String)aliasenum.nextElement();
                /*     */
            }
            /* 529 */X509Certificate cert = (X509Certificate)ks
            /* 530 */.getCertificate(keyAlias);
            /* 531 */return cert.getSerialNumber().toString();
            /*     */
        }
        catch (Exception e)
        {
            /* 533 */LogUtil.writeErrorLog("获取签名证书的序列号失败", e);
            /* 534 */
        }
        return "";
        /*     */
    }
    
    /*     */
    /*     */public static Map<String, X509Certificate> getCertMap()
    /*     */
    {
        /* 544 */return certMap;
        /*     */
    }
    
    /*     */
    /*     */public static void setCertMap(Map<String, X509Certificate> certMap)
    /*     */
    {
        /* 553 */CertUtil.certMap = certMap;
        /*     */
    }
    
    /*     */
    /*     */static class CerFilter
    /*     */implements FilenameFilter
    /*     */
    {
        /*     */public boolean isCer(String name)
        /*     */
        {
            /* 496 */if (name.toLowerCase().endsWith(".cer"))
            {
                /* 497 */return true;
                /*     */
            }
            /* 499 */return false;
            /*     */
        }
        
        /*     */
        /*     */public boolean accept(File dir, String name)
        /*     */
        {
            /* 504 */return isCer(name);
            /*     */
        }
        /*     */
    }
    /*     */
}
