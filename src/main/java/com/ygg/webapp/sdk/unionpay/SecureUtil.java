/*     */package com.ygg.webapp.sdk.unionpay;

/*     */
/*     */import java.io.ByteArrayOutputStream;
/*     */
import java.io.IOException;
/*     */
import java.io.UnsupportedEncodingException;
/*     */
import java.math.BigInteger;
/*     */
import java.security.MessageDigest;
/*     */
import java.security.PrivateKey;
/*     */
import java.security.PublicKey;
/*     */
import java.security.SecureRandom;
/*     */
import java.security.Signature;
/*     */
import java.security.interfaces.RSAPublicKey;
/*     */
import java.util.zip.Deflater;
/*     */
import java.util.zip.Inflater;

/*     */
import javax.crypto.Cipher;
/*     */
import javax.crypto.Mac;
/*     */
import javax.crypto.SecretKey;
/*     */
import javax.crypto.spec.SecretKeySpec;

/*     */
import org.apache.commons.codec.binary.Base64;
/*     */
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*     */
/*     */
/*     */public class SecureUtil
/*     */
{
    /*     */@SuppressWarnings("unused")
    private static final String ALGORITHM_MD5 = "MD5";
    
    @SuppressWarnings("unused")
    /*     */private static final String ALGORITHM_SHA1 = "SHA-1";
    
    @SuppressWarnings("unused")
    /*     */private static final String BC_PROV_ALGORITHM_SHA1RSA = "SHA1withRSA";
    
    /*     */
    /*     */public static byte[] md5(byte[] datas)
    /*     */
    {
        /* 61 */MessageDigest md = null;
        /*     */try
        {
            /* 63 */md = MessageDigest.getInstance("MD5");
            /* 64 */md.reset();
            /* 65 */md.update(datas);
            /* 66 */return md.digest();
            /*     */
        }
        catch (Exception e)
        {
            /* 68 */e.printStackTrace();
            /* 69 */LogUtil.writeErrorLog("MD5计算失败", e);
            /* 70 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] sha1(byte[] data)
    /*     */
    {
        /* 82 */MessageDigest md = null;
        /*     */try
        {
            /* 84 */md = MessageDigest.getInstance("SHA-1");
            /* 85 */md.reset();
            /* 86 */md.update(data);
            /* 87 */return md.digest();
            /*     */
        }
        catch (Exception e)
        {
            /* 89 */e.printStackTrace();
            /* 90 */LogUtil.writeErrorLog("SHA1计算失败", e);
            /* 91 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] md5X16(String datas, String encoding)
    /*     */
    {
        /* 105 */byte[] bytes = md5(datas, encoding);
        /* 106 */StringBuilder md5StrBuff = new StringBuilder();
        /* 107 */for (int i = 0; i < bytes.length; i++)
        {
            /* 108 */if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                /* 109 */md5StrBuff.append("0").append(
                /* 110 */Integer.toHexString(0xFF & bytes[i]));
            /*     */else
                /* 112 */md5StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
            /*     */
        }
        /*     */try
        /*     */
        {
            /* 116 */return md5StrBuff.toString().getBytes(encoding);
            /*     */
        }
        catch (UnsupportedEncodingException e)
        {
            /* 118 */e.printStackTrace();
            /* 119 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] sha1X16(String data, String encoding)
    /*     */
    {
        /* 133 */byte[] bytes = sha1(data, encoding);
        /* 134 */StringBuilder sha1StrBuff = new StringBuilder();
        /* 135 */for (int i = 0; i < bytes.length; i++)
        {
            /* 136 */if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                /* 137 */sha1StrBuff.append("0").append(
                /* 138 */Integer.toHexString(0xFF & bytes[i]));
            /*     */else
                /* 140 */sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
            /*     */
        }
        /*     */try
        /*     */
        {
            /* 144 */return sha1StrBuff.toString().getBytes(encoding);
            /*     */
        }
        catch (UnsupportedEncodingException e)
        {
            /* 146 */e.printStackTrace();
            /* 147 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] md5(String datas, String encoding)
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 162 */return md5(datas.getBytes(encoding));
            /*     */
        }
        catch (UnsupportedEncodingException e)
        {
            /* 164 */LogUtil.writeErrorLog("MD5计算失败", e);
            /* 165 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] sha1(String datas, String encoding)
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 180 */return sha1(datas.getBytes(encoding));
            /*     */
        }
        catch (UnsupportedEncodingException e)
        {
            /* 182 */LogUtil.writeErrorLog("SHA1计算失败", e);
            /* 183 */
        }
        return null;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] signBySoft(PrivateKey privateKey, byte[] data)
        /*     */throws Exception
    /*     */
    {
        /* 201 */byte[] result = (byte[])null;
        /* 202 */Signature st = Signature.getInstance("SHA1withRSA");
        /* 203 */st.initSign(privateKey);
        /* 204 */st.update(data);
        /* 205 */result = st.sign();
        /* 206 */return result;
        /*     */
    }
    
    /*     */
    /*     */public static boolean validateSignBySoft(PublicKey publicKey, byte[] signData, byte[] srcData)
        /*     */throws Exception
    /*     */
    {
        /* 225 */Signature st = Signature.getInstance("SHA1withRSA");
        /* 226 */st.initVerify(publicKey);
        /* 227 */st.update(srcData);
        /* 228 */return st.verify(signData);
        /*     */
    }
    
    /*     */
    /*     */public static byte[] inflater(byte[] inputByte)
        /*     */throws IOException
    /*     */
    {
        /* 240 */int compressedDataLength = 0;
        /* 241 */Inflater compresser = new Inflater(false);
        /* 242 */compresser.setInput(inputByte, 0, inputByte.length);
        /* 243 */ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        /* 244 */byte[] result = new byte[1024];
        /*     */try
        {
            /* 246 */while (!compresser.finished())
            {
                /* 247 */compressedDataLength = compresser.inflate(result);
                /* 248 */if (compressedDataLength == 0)
                {
                    /*     */break;
                    /*     */
                }
                /* 251 */o.write(result, 0, compressedDataLength);
                /*     */
            }
            /*     */
        }
        catch (Exception ex)
        {
            /* 254 */System.err.println("Data format error!\n");
            /* 255 */ex.printStackTrace();
            /*     */
        }
        finally
        {
            /* 257 */o.close();
            /*     */
        }
        /* 259 */compresser.end();
        /* 260 */return o.toByteArray();
        /*     */
    }
    
    /*     */
    /*     */public static byte[] deflater(byte[] inputByte)
        /*     */throws IOException
    /*     */
    {
        /* 272 */int compressedDataLength = 0;
        /* 273 */Deflater compresser = new Deflater();
        /* 274 */compresser.setInput(inputByte);
        /* 275 */compresser.finish();
        /* 276 */ByteArrayOutputStream o = new ByteArrayOutputStream(inputByte.length);
        /* 277 */byte[] result = new byte[1024];
        /*     */try
        {
            /* 279 */while (!compresser.finished())
            {
                /* 280 */compressedDataLength = compresser.deflate(result);
                /* 281 */o.write(result, 0, compressedDataLength);
                /*     */
            }
            /*     */
        }
        finally
        {
            /* 284 */o.close();
            /*     */
        }
        /* 286 */compresser.end();
        /* 287 */return o.toByteArray();
        /*     */
    }
    
    /*     */
    /*     */public static String EncryptPin(String pin, String card, String encoding, PublicKey key)
    /*     */
    {
        /* 306 */byte[] pinBlock = pin2PinBlockWithCardNO(pin, card);
        /*     */
        /* 308 */byte[] data = (byte[])null;
        /*     */try
        {
            /* 310 */data = encryptedPin(key, pinBlock);
            /* 311 */return new String(base64Encode(data), encoding);
            /*     */
        }
        catch (Exception e)
        {
            /* 313 */e.printStackTrace();
            /* 314 */
        }
        return "";
        /*     */
    }
    
    /*     */
    /*     */public static String EncryptData(String dataString, String encoding, PublicKey key)
    /*     */
    {
        /* 332 */byte[] data = (byte[])null;
        /*     */try
        {
            /* 334 */data = encryptedPin(key, dataString.getBytes(encoding));
            /* 335 */return new String(base64Encode(data), encoding);
            /*     */
        }
        catch (Exception e)
        {
            /* 337 */e.printStackTrace();
            /* 338 */
        }
        return "";
        /*     */
    }
    
    /*     */
    /*     */public static String DecryptedData(String dataString, String encoding, PrivateKey key)
    /*     */
    {
        /* 355 */byte[] data = (byte[])null;
        /*     */try
        {
            /* 357 */data = decryptedPin(key, dataString.getBytes(encoding));
            /* 358 */return new String(data, encoding);
            /*     */
        }
        catch (Exception e)
        {
            /* 360 */e.printStackTrace();
            /* 361 */
        }
        return "";
        /*     */
    }
    
    /*     */
    /*     */public static byte[] base64Decode(byte[] inputByte)
        /*     */throws IOException
    /*     */
    {
        /* 374 */return Base64.decodeBase64(inputByte);
        /*     */
    }
    
    /*     */
    /*     */public static byte[] base64Encode(byte[] inputByte)
        /*     */throws IOException
    /*     */
    {
        /* 386 */return Base64.encodeBase64(inputByte);
        /*     */
    }
    
    /*     */
    /*     */public byte[] Str2Hex(String str)
    /*     */
    {
        /* 397 */char[] ch = str.toCharArray();
        /* 398 */byte[] b = new byte[ch.length / 2];
        /* 399 */for (int i = 0; i < ch.length; i++)
        {
            /* 400 */if (ch[i] == 0)
            {
                /*     */break;
                /*     */
            }
            /* 403 */if ((ch[i] >= '0') && (ch[i] <= '9'))
                /* 404 */ch[i] = ((char)(ch[i] - '0'));
            /* 405 */else if ((ch[i] >= 'A') && (ch[i] <= 'F'))
            {
                /* 406 */ch[i] = ((char)(ch[i] - 'A' + 10));
                /*     */
            }
            /*     */
        }
        /* 409 */for (int i = 0; i < b.length; i++)
        {
            /* 410 */b[i] = ((byte)((ch[(2 * i)] << '\004' & 0xF0) + (ch[(2 * i + 1)] & 0xF)));
            /*     */
        }
        /* 412 */return b;
        /*     */
    }
    
    /*     */
    /*     */public static String Hex2Str(byte[] b)
    /*     */
    {
        /* 423 */StringBuffer d = new StringBuffer(b.length * 2);
        /* 424 */for (int i = 0; i < b.length; i++)
        {
            /* 425 */char hi = Character.forDigit(b[i] >> 4 & 0xF, 16);
            /* 426 */char lo = Character.forDigit(b[i] & 0xF, 16);
            /* 427 */d.append(Character.toUpperCase(hi));
            /* 428 */d.append(Character.toUpperCase(lo));
            /*     */
        }
        /* 430 */return d.toString();
        /*     */
    }
    
    /*     */
    /*     */public static String ByteToHex(byte[] bytes)
    {
        /* 434 */StringBuffer sha1StrBuff = new StringBuffer();
        /* 435 */for (int i = 0; i < bytes.length; i++)
        {
            /* 436 */if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                /* 437 */sha1StrBuff.append("0").append(
                /* 438 */Integer.toHexString(0xFF & bytes[i]));
            /*     */else
            {
                /* 440 */sha1StrBuff.append(Integer.toHexString(0xFF & bytes[i]));
                /*     */
            }
            /*     */
        }
        /* 443 */return sha1StrBuff.toString();
        /*     */
    }
    
    /*     */
    /*     */public static String Hex2Str(byte[] b, int len)
    /*     */
    {
        /* 456 */String str = "";
        /* 457 */char[] ch = new char[len * 2];
        /*     */
        /* 459 */for (int i = 0; i < len; i++)
        {
            /* 460 */if (((b[i] >> 4 & 0xF) < 10) && ((b[i] >> 4 & 0xF) >= 0))
                /* 461 */ch[(i * 2)] = ((char)((b[i] >> 4 & 0xF) + 48));
            /*     */else
            {
                /* 463 */ch[(i * 2)] = ((char)((b[i] >> 4 & 0xF) + 65 - 10));
                /*     */
            }
            /*     */
            /* 466 */if (((b[i] & 0xF) < 10) && ((b[i] & 0xF) >= 0))
                /* 467 */ch[(i * 2 + 1)] = ((char)((b[i] & 0xF) + 48));
            /*     */else
            {
                /* 469 */ch[(i * 2 + 1)] = ((char)((b[i] & 0xF) + 65 - 10));
                /*     */
            }
            /*     */
        }
        /*     */
        /* 473 */str = new String(ch);
        /* 474 */return str;
        /*     */
    }
    
    /*     */
    /*     */public String byte2hex(byte[] b)
    /*     */
    {
        /* 485 */String hs = "";
        /* 486 */String stmp = "";
        /* 487 */for (int n = 0; n < b.length; n++)
        {
            /* 488 */stmp = Integer.toHexString(b[n] & 0xFF);
            /* 489 */if (stmp.length() == 1)
                /* 490 */hs = hs + "0" + stmp;
            /*     */else
            {
                /* 492 */hs = hs + stmp;
                /*     */
            }
            /* 494 */if (n < b.length - 1)
            {
                /* 495 */hs = hs + ":";
                /*     */
            }
            /*     */
        }
        /* 498 */return hs.toUpperCase();
        /*     */
    }
    
    /*     */
    /*     */public String genmac(byte[] inputByte, byte[] inputkey)
        /*     */throws Exception
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 513 */Mac mac = Mac.getInstance("HmacMD5");
            /* 514 */SecretKey key = new SecretKeySpec(inputkey, "DES");
            /* 515 */mac.init(key);
            /*     */
            /* 517 */byte[] macCode = mac.doFinal(inputByte);
            /* 518 */return byte2hex(macCode);
            /*     */
        }
        /*     */catch (Exception ex)
        {
            /* 521 */ex.printStackTrace();
            /* 522 */throw ex;
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public boolean checkmac(byte[] inputByte, byte[] inputkey, String inputmac)
        /*     */throws Exception
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 541 */Mac mac = Mac.getInstance("HmacMD5");
            /* 542 */SecretKey key = new SecretKeySpec(inputkey, "DES");
            /* 543 */mac.init(key);
            /*     */
            /* 545 */byte[] macCode = mac.doFinal(inputByte);
            /* 546 */String strMacCode = byte2hex(macCode);
            /*     */
            /* 548 */if (strMacCode.equals(inputmac))
            {
                /* 549 */return true;
                /*     */
            }
            /* 551 */return false;
            /*     */
        }
        /*     */catch (Exception ex)
        {
            /* 554 */throw ex;
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public static String fillString(String string, char filler, int totalLength, boolean atEnd)
    /*     */
    {
        /* 573 */byte[] tempbyte = string.getBytes();
        /* 574 */int currentLength = tempbyte.length;
        /* 575 */int delta = totalLength - currentLength;
        /*     */
        /* 577 */for (int i = 0; i < delta; i++)
        {
            /* 578 */if (atEnd)
                /* 579 */string = string + filler;
            /*     */else
            {
                /* 581 */string = filler + string;
                /*     */
            }
            /*     */
        }
        /* 584 */return string;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] encryptedPin(PublicKey publicKey, byte[] plainPin)
        /*     */throws Exception
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 604 */Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
            /* 605 */new BouncyCastleProvider());
            /* 606 */cipher.init(1, publicKey);
            /* 607 */int blockSize = cipher.getBlockSize();
            /* 608 */int outputSize = cipher.getOutputSize(plainPin.length);
            /* 609 */int leavedSize = plainPin.length % blockSize;
            /* 610 */int blocksSize = leavedSize != 0 ? plainPin.length / blockSize + 1 :
            /* 611 */plainPin.length / blockSize;
            /* 612 */byte[] raw = new byte[outputSize * blocksSize];
            /* 613 */int i = 0;
            /* 614 */while (plainPin.length - i * blockSize > 0)
            {
                /* 615 */if (plainPin.length - i * blockSize > blockSize)
                    /* 616 */cipher.doFinal(plainPin, i * blockSize, blockSize, raw, i *
                    /* 617 */outputSize);
                /*     */else
                {
                    /* 619 */cipher.doFinal(plainPin, i * blockSize, plainPin.length - i *
                    /* 620 */blockSize, raw, i * outputSize);
                    /*     */
                }
                /* 622 */i++;
                /*     */
            }
            /* 624 */return raw;
            /*     */
        }
        catch (Exception e)
        {
            /* 626 */throw new Exception(e.getMessage());
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public byte[] encryptedData(PublicKey publicKey, byte[] plainData)
        /*     */throws Exception
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 640 */Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
            /* 641 */new BouncyCastleProvider());
            /* 642 */cipher.init(1, publicKey);
            /* 643 */int blockSize = cipher.getBlockSize();
            /* 644 */int outputSize = cipher.getOutputSize(plainData.length);
            /* 645 */int leavedSize = plainData.length % blockSize;
            /* 646 */int blocksSize = leavedSize != 0 ? plainData.length / blockSize + 1 :
            /* 647 */plainData.length / blockSize;
            /* 648 */byte[] raw = new byte[outputSize * blocksSize];
            /* 649 */int i = 0;
            /* 650 */while (plainData.length - i * blockSize > 0)
            {
                /* 651 */if (plainData.length - i * blockSize > blockSize)
                    /* 652 */cipher.doFinal(plainData, i * blockSize, blockSize, raw, i *
                    /* 653 */outputSize);
                /*     */else
                {
                    /* 655 */cipher.doFinal(plainData, i * blockSize, plainData.length -
                    /* 656 */i * blockSize, raw, i * outputSize);
                    /*     */
                }
                /* 658 */i++;
                /*     */
            }
            /* 660 */return raw;
            /*     */
        }
        catch (Exception e)
        {
            /* 662 */throw new Exception(e.getMessage());
            /*     */
        }
        /*     */
    }
    
    /*     */
    /*     */public static byte[] decryptedPin(PrivateKey privateKey, byte[] cryptPin)
        /*     */throws Exception
    /*     */
    {
        /*     */try
        /*     */
        {
            /* 678 */byte[] pinBlock = base64Decode(cryptPin);
            /*     */
            /* 680 */Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding",
            /* 681 */new BouncyCastleProvider());
            /* 682 */cipher.init(2, privateKey);
            /* 683 */int blockSize = cipher.getBlockSize();
            /* 684 */int outputSize = cipher.getOutputSize(pinBlock.length);
            /* 685 */int leavedSize = pinBlock.length % blockSize;
            /* 686 */int blocksSize = leavedSize != 0 ? pinBlock.length / blockSize + 1 :
            /* 687 */pinBlock.length / blockSize;
            /* 688 */byte[] pinData = new byte[outputSize * blocksSize];
            /* 689 */int i = 0;
            /* 690 */while (pinBlock.length - i * blockSize > 0)
            {
                /* 691 */if (pinBlock.length - i * blockSize > blockSize)
                    /* 692 */cipher.doFinal(pinBlock, i * blockSize, blockSize, pinData,
                    /* 693 */i * outputSize);
                /*     */else
                {
                    /* 695 */cipher.doFinal(pinBlock, i * blockSize, pinBlock.length - i *
                    /* 696 */blockSize, pinData, i * outputSize);
                    /*     */
                }
                /* 698 */i++;
                /*     */
            }
            /* 700 */return pinData;
            /*     */
        }
        catch (Exception e)
        {
            /* 702 */LogUtil.writeErrorLog("解密失败", e);
            /*     */
        }
        /* 704 */return null;
        /*     */
    }
    
    /*     */
    /*     */private static byte[] pin2PinBlock(String aPin)
    /*     */
    {
        /* 713 */int tTemp = 1;
        /* 714 */int tPinLen = aPin.length();
        /*     */
        /* 716 */byte[] tByte = new byte[8];
        /*     */try
        /*     */
        {
            /* 725 */tByte[0] = ((byte)Integer.parseInt(Integer.toString(tPinLen), 10));
            /* 726 */if (tPinLen % 2 == 0)
                /* 727 */for (int i = 0; i < tPinLen;)
                {
                    /* 728 */String a = aPin.substring(i, i + 2);
                    /* 729 */tByte[tTemp] = ((byte)Integer.parseInt(a, 16));
                    /* 730 */if ((i == tPinLen - 2) &&
                    /* 731 */(tTemp < 7))
                    {
                        /* 732 */for (int x = tTemp + 1; x < 8; x++)
                        {
                            /* 733 */tByte[x] = -1;
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                    /* 737 */tTemp++;
                    /* 738 */i += 2;
                    /*     */
                }
            /*     */else
                /* 741 */for (int i = 0; i < tPinLen - 1;)
                /*     */
                {
                    /* 743 */String a = aPin.substring(i, i + 2);
                    /* 744 */tByte[tTemp] = ((byte)Integer.parseInt(a, 16));
                    /* 745 */if (i == tPinLen - 3)
                    {
                        /* 746 */String b = aPin.substring(tPinLen - 1) + "F";
                        /* 747 */tByte[(tTemp + 1)] = ((byte)Integer.parseInt(b, 16));
                        /* 748 */if (tTemp + 1 < 7)
                        {
                            /* 749 */for (int x = tTemp + 2; x < 8; x++)
                            {
                                /* 750 */tByte[x] = -1;
                                /*     */
                            }
                            /*     */
                        }
                        /*     */
                    }
                    /* 754 */tTemp++;
                    /* 755 */i += 2;
                    /*     */
                }
            /*     */
        }
        /*     */catch (Exception localException)
        /*     */
        {
            /*     */
        }
        /* 761 */return tByte;
        /*     */
    }
    
    /*     */
    /*     */private static byte[] formatPan(String aPan)
    /*     */
    {
        /* 770 */int tPanLen = aPan.length();
        /* 771 */byte[] tByte = new byte[8];
        /*     */
        /* 773 */int temp = tPanLen - 13;
        /*     */try
        {
            /* 775 */tByte[0] = 0;
            /* 776 */tByte[1] = 0;
            /* 777 */for (int i = 2; i < 8; i++)
            {
                /* 778 */String a = aPan.substring(temp, temp + 2);
                /* 779 */tByte[i] = ((byte)Integer.parseInt(a, 16));
                /* 780 */temp += 2;
                /*     */
            }
            /*     */
        }
        catch (Exception localException)
        {
            /*     */
        }
        /* 784 */return tByte;
        /*     */
    }
    
    /*     */
    /*     */public static byte[] pin2PinBlockWithCardNO(String aPin, String aCardNO)
    /*     */
    {
        /* 794 */byte[] tPinByte = pin2PinBlock(aPin);
        /* 795 */if (aCardNO.length() == 11)
            /* 796 */aCardNO = "00" + aCardNO;
        /* 797 */else if (aCardNO.length() == 12)
        {
            /* 798 */aCardNO = "0" + aCardNO;
            /*     */
        }
        /* 800 */byte[] tPanByte = formatPan(aCardNO);
        /* 801 */byte[] tByte = new byte[8];
        /* 802 */for (int i = 0; i < 8; i++)
        {
            /* 803 */tByte[i] = ((byte)(tPinByte[i] ^ tPanByte[i]));
            /*     */
        }
        /* 805 */return tByte;
        /*     */
    }
    
    /*     */
    /*     */private static byte[] addPKCS1Padding(byte[] aBytesText, int aBlockSize)
    /*     */
    {
        /* 815 */if (aBytesText.length > aBlockSize - 3)
        {
            /* 816 */return null;
            /*     */
        }
        /* 818 */SecureRandom tRandom = new SecureRandom();
        /* 819 */byte[] tAfterPaddingBytes = new byte[aBlockSize];
        /* 820 */tRandom.nextBytes(tAfterPaddingBytes);
        /* 821 */tAfterPaddingBytes[0] = 0;
        /* 822 */tAfterPaddingBytes[1] = 2;
        /* 823 */for (int i = 2;
        /* 824 */i < aBlockSize - 1 - aBytesText.length; i++)
        {
            /* 825 */if (tAfterPaddingBytes[i] == 0)
            {
                /* 826 */tAfterPaddingBytes[i] = ((byte)tRandom.nextInt());
                /*     */
            }
            /* 829 */tAfterPaddingBytes[i] = 0;
            /* 830 */System.arraycopy(aBytesText, 0, tAfterPaddingBytes, i + 1,
            /* 831 */aBytesText.length);
            /*     */
        }
        
        /*     */
        /* 833 */return tAfterPaddingBytes;
        /*     */
    }
    
    /*     */
    /*     */public String assymEncrypt(String tPIN, String iPan, RSAPublicKey publicKey)
    /*     */
    {
        // /* 845 */ System.out.println("SampleHashMap::assymEncrypt([" + tPIN + "])");
        // /* 846 */ System.out.println("SampleHashMap::assymEncrypt(PIN =[" + tPIN + "])");
        /*     */try
        /*     */
        {
            /* 849 */int tKeyLength = 1024;
            /* 850 */int tBlockSize = tKeyLength / 8;
            /*     */
            /* 852 */byte[] tTemp = (byte[])null;
            /*     */
            /* 854 */tTemp = pin2PinBlockWithCardNO(tPIN, iPan);
            /* 855 */tTemp = addPKCS1Padding(tTemp, tBlockSize);
            /*     */
            /* 857 */BigInteger tPlainText = new BigInteger(tTemp);
            /* 858 */BigInteger tCipherText = tPlainText.modPow(publicKey
            /* 859 */.getPublicExponent(), publicKey.getModulus());
            /*     */
            /* 861 */byte[] tCipherBytes = tCipherText.toByteArray();
            /* 862 */int tCipherLength = tCipherBytes.length;
            /* 863 */if (tCipherLength > tBlockSize)
            {
                /* 864 */byte[] tTempBytes = new byte[tBlockSize];
                /* 865 */System.arraycopy(tCipherBytes, tCipherLength - tBlockSize,
                /* 866 */tTempBytes, 0, tBlockSize);
                /* 867 */tCipherBytes = tTempBytes;
                /* 868 */
            }
            else if (tCipherLength < tBlockSize)
            {
                /* 869 */byte[] tTempBytes = new byte[tBlockSize];
                /* 870 */for (int i = 0; i < tBlockSize - tCipherLength; i++)
                {
                    /* 871 */tTempBytes[i] = 0;
                    /*     */
                }
                /* 873 */System.arraycopy(tCipherBytes, 0, tTempBytes, tBlockSize -
                /* 874 */tCipherLength, tCipherLength);
                /* 875 */tCipherBytes = tTempBytes;
                /*     */
            }
            /* 877 */String tEncryptPIN = new String(
            /* 878 */base64Encode(tCipherBytes));
            /*     */
            // /* 880 */ System.out.println("SampleHashMap::assymEncrypt(EncryptCardNo =[" +
            // /* 881 */ tEncryptPIN + "])");
            /*     */
            /* 883 */return tEncryptPIN;
            /*     */
        }
        catch (Exception e)
        {
            /* 885 */e.printStackTrace(System.out);
            /* 886 */return tPIN;
            /*     */
        }
        catch (Error e)
        {
            /* 888 */e.printStackTrace(System.out);
            /* 889 */
        }
        return tPIN;
        /*     */
    }
    
    /*     */
    /*     */public static String trace(byte[] inBytes)
    /*     */
    {
        /* 900 */int j = 0;
        /* 901 */byte[] temp = new byte[76];
        /* 902 */bytesSet(temp, ' ');
        /* 903 */StringBuffer strc = new StringBuffer("");
        /* 904 */strc
        /* 905 */.append("----------------------------------------------------------------------------\n");
        /*     */
        /* 907 */for (int i = 0; i < inBytes.length; i++)
        {
            /* 908 */if (j == 0)
            {
                /* 909 */System.arraycopy(String.format("%03d: ", new Object[] {Integer.valueOf(i)}).getBytes(), 0,
                /* 910 */temp, 0, 5);
                /* 911 */System.arraycopy(String.format(":%03d", new Object[] {Integer.valueOf(i + 15)}).getBytes(), 0,
                /* 912 */temp, 72, 4);
                /*     */
            }
            /* 914 */System.arraycopy(String.format("%02X ", new Object[] {Byte.valueOf(inBytes[i])}).getBytes(), 0,
            /* 915 */temp, j * 3 + 5 + (j > 7 ? 1 : 0), 3);
            /* 916 */if (inBytes[i] == 0)
                /* 917 */temp[(j + 55 + (j > 7 ? 1 : 0))] = 46;
            /*     */else
            {
                /* 919 */temp[(j + 55 + (j > 7 ? 1 : 0))] = inBytes[i];
                /*     */
            }
            /* 921 */j++;
            /* 922 */if (j == 16)
            {
                /* 923 */strc.append(new String(temp)).append("\n");
                /* 924 */bytesSet(temp, ' ');
                /* 925 */j = 0;
                /*     */
            }
            /*     */
        }
        /* 928 */if (j != 0)
        {
            /* 929 */strc.append(new String(temp)).append("\n");
            /* 930 */bytesSet(temp, ' ');
            /*     */
        }
        /* 932 */strc
        /* 933 */.append("----------------------------------------------------------------------------\n");
        /*     */
        /* 935 */return strc.toString();
        /*     */
    }
    
    /*     */
    /*     */private static void bytesSet(byte[] inBytes, char fill)
    /*     */
    {
        /* 944 */if (inBytes.length == 0)
        {
            /* 945 */return;
            /*     */
        }
        /* 947 */for (int i = 0; i < inBytes.length; i++)
            /* 948 */inBytes[i] = ((byte)fill);
        /*     */
    }
    /*     */
}
