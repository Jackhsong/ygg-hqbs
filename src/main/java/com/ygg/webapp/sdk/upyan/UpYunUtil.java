package com.ygg.webapp.sdk.upyan;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ygg.webapp.util.CommonUtil;

/**
 * 图片类空间的demo，一般性操作参考文件空间的demo（FileBucketDemo.java）
 * <p>
 * 注意：直接使用部分图片处理功能后，将会丢弃原图保存处理后的图片
 */
public class UpYunUtil
{
    
    // 运行前先设置好以下三个参数
    private static final String BUCKET_NAME = "yangege";
    
    private static final String USER_NAME = "mohh";
    
    private static final String USER_PWD = "yangege0.0";
    
    /** 绑定的域名 */
    private static final String URL = "http://" + BUCKET_NAME + ".b0.upaiyun.com";
    
    /** 根目录 */
    private static final String DIR_ROOT = "/";
    
    private static UpYun upyun;
    
    public static Map<String, String> uploadFile(File file, String fileNewName)
        throws IOException
    {
        
        if (upyun == null)
        {
            upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
        }
        
        // ****** 可选设置 begin ******
        
        // 切换 API 接口的域名接入点，默认为自动识别接入点
        // upyun.setApiDomain(UpYun.ED_AUTO);
        
        // 设置连接超时时间，默认为30秒
        // upyun.setTimeout(60);
        
        // 设置是否开启debug模式，默认不开启
        // upyun.setDebug(true);
        
        // ****** 可选设置 end ******
        
        /*
         * 一般性操作参考文件空间的demo（FileBucketDemo.java）
         * 
         * 注：图片的所有参数均可以自由搭配使用
         */
        
        // 1.上传文件（文件内容）
        String filePath = DIR_ROOT + fileNewName;
        // Map<String,String> rel= writeFile(file,filePath);
        
        // 2.图片做缩略图；若使用了该功能，则会丢弃原图
        // testGmkerl();
        
        // 3.图片旋转；若使用了该功能，则会丢弃原图
        // testRotate();
        
        // 4.图片裁剪；若使用了该功能，则会丢弃原图
        // testCrop();
        return writeFile(file, filePath);
    }
    
    public static Map<String, String> uploadFile(MultipartFile file, String fileNewName)
        throws IOException
    {
        
        if (upyun == null)
        {
            upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
        }
        
        // ****** 可选设置 begin ******
        
        // 切换 API 接口的域名接入点，默认为自动识别接入点
        // upyun.setApiDomain(UpYun.ED_AUTO);
        
        // 设置连接超时时间，默认为30秒
        // upyun.setTimeout(60);
        
        // 设置是否开启debug模式，默认不开启
        // upyun.setDebug(true);
        
        // ****** 可选设置 end ******
        
        /*
         * 一般性操作参考文件空间的demo（FileBucketDemo.java）
         * 
         * 注：图片的所有参数均可以自由搭配使用
         */
        
        // 1.上传文件（文件内容）
        String filePath = DIR_ROOT + fileNewName;
        // Map<String,String> rel= writeFile(file,filePath);
        
        // 2.图片做缩略图；若使用了该功能，则会丢弃原图
        // testGmkerl();
        
        // 3.图片旋转；若使用了该功能，则会丢弃原图
        // testRotate();
        
        // 4.图片裁剪；若使用了该功能，则会丢弃原图
        // testCrop();
        return writeFile(file, filePath);
    }
    
    public static Map<String, String> uploadFile(byte[] file, String fileNewName)
        throws IOException
    {
        
        if (upyun == null)
        {
            upyun = new UpYun(BUCKET_NAME, USER_NAME, USER_PWD);
        }
        
        // ****** 可选设置 begin ******
        
        // 切换 API 接口的域名接入点，默认为自动识别接入点
        // upyun.setApiDomain(UpYun.ED_AUTO);
        
        // 设置连接超时时间，默认为30秒
        // upyun.setTimeout(60);
        
        // 设置是否开启debug模式，默认不开启
        // upyun.setDebug(true);
        
        // ****** 可选设置 end ******
        
        /*
         * 一般性操作参考文件空间的demo（FileBucketDemo.java）
         * 
         * 注：图片的所有参数均可以自由搭配使用
         */
        
        // 1.上传文件（文件内容）
        String filePath = DIR_ROOT + fileNewName;
        // Map<String,String> rel= writeFile(file,filePath);
        
        // 2.图片做缩略图；若使用了该功能，则会丢弃原图
        // testGmkerl();
        
        // 3.图片旋转；若使用了该功能，则会丢弃原图
        // testRotate();
        
        // 4.图片裁剪；若使用了该功能，则会丢弃原图
        // testCrop();
        return writeFile(file, filePath);
    }
    
    /**
     * 上传文件
     * 
     * @throws java.io.IOException
     */
    public static Map<String, String> writeFile(byte[] file, String filePath)
        throws IOException
    {
        
        // 要传到upyun后的文件路径
        // String filePath = DIR_ROOT + PIC_NAME;
        // String f="/home/cl/IdeaProjects/nala/web-app/"+SAMPLE_PIC_FILE ;
        // 本地待上传的图片文件
        // File file = new File(SAMPLE_PIC_FILE);
        
        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        upyun.setContentMD5(UpYun.md5(file));
        
        // 设置待上传文件的"访问密钥"
        // 注意：
        // 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
        // 举例：
        // 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
        // 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
        // upyun.setFileSecret("bac");
        
        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(filePath, file);
        
        // System.out.println(filePath + " 上传" + isSuccess(result));
        
        // 获取上传文件后的信息（仅图片空间有返回数据）
        // System.out.println("\r\n****** " + file.getName() + " 的图片信息 *******");
        // 图片宽度
        // System.out.println("图片宽度:" + upyun.getPicWidth());
        // 图片高度
        // System.out.println("图片高度:" + upyun.getPicHeight());
        // 图片帧数
        // System.out.println("图片帧数:" + upyun.getPicFrames());
        // 图片类型
        // System.out.println("图片类型:" + upyun.getPicType());
        
        // System.out.println("****************************************\r\n");
        
        // System.out.println("若设置过访问密钥(bac)，且缩略图间隔标志符为'!'，则可以通过以下路径来访问图片：");
        // System.out.println(URL + filePath + "!bac");
        // System.out.println();
        Map<String, String> map = new HashMap<String, String>();
        if (result)
        {
            map.put("status", "success");
            map.put("url", URL + filePath);
        }
        else
        {
            map.put("status", "failure");
        }
        // String finalpath= URL + filePath;
        return map;
    }
    
    /**
     * 上传文件
     * 
     * @throws java.io.IOException
     */
    public static Map<String, String> writeFile(File file, String filePath)
        throws IOException
    {
        
        // 要传到upyun后的文件路径
        // String filePath = DIR_ROOT + PIC_NAME;
        // String f="/home/cl/IdeaProjects/nala/web-app/"+SAMPLE_PIC_FILE ;
        // 本地待上传的图片文件
        // File file = new File(SAMPLE_PIC_FILE);
        
        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        upyun.setContentMD5(UpYun.md5(file));
        
        // 设置待上传文件的"访问密钥"
        // 注意：
        // 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
        // 举例：
        // 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
        // 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
        // upyun.setFileSecret("bac");
        
        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(filePath, new FileInputStream(file), true);
        
        // System.out.println(filePath + " 上传" + isSuccess(result));
        
        // 获取上传文件后的信息（仅图片空间有返回数据）
        // System.out.println("\r\n****** " + file.getName() + " 的图片信息 *******");
        // 图片宽度
        // System.out.println("图片宽度:" + upyun.getPicWidth());
        // 图片高度
        // System.out.println("图片高度:" + upyun.getPicHeight());
        // 图片帧数
        // System.out.println("图片帧数:" + upyun.getPicFrames());
        // 图片类型
        // System.out.println("图片类型:" + upyun.getPicType());
        
        // System.out.println("****************************************\r\n");
        
        // System.out.println("若设置过访问密钥(bac)，且缩略图间隔标志符为'!'，则可以通过以下路径来访问图片：");
        // System.out.println(URL + filePath + "!bac");
        // System.out.println();
        Map<String, String> map = new HashMap<String, String>();
        if (result)
        {
            map.put("status", "success");
            map.put("url", URL + filePath);
        }
        else
        {
            map.put("status", "failure");
        }
        // String finalpath= URL + filePath;
        return map;
    }
    
    /**
     * 图片做缩略图
     * <p>
     * 注意：若使用了缩略图功能，则会丢弃原图
     * 
     * @throws java.io.IOException
     */
    /*
     * public static void testGmkerl(FileInputStream ls) throws IOException {
     * 
     * // 要传到upyun后的文件路径 String filePath = DIR_ROOT + "gmkerl.jpg";
     * 
     * // 本地待上传的图片文件 // File file = new File(SAMPLE_PIC_FILE);
     * 
     * // 设置缩略图的参数 Map<String, String> params = new HashMap<String, String>();
     * 
     * // 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效 params.put(UpYun.PARAMS.KEY_X_GMKERL_TYPE.getValue(),
     * UpYun.PARAMS.VALUE_FIX_BOTH.getValue());
     * 
     * // 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效 params.put(UpYun.PARAMS.KEY_X_GMKERL_VALUE.getValue(), "150x150");
     * 
     * // 设置缩略图的质量，默认 95 params.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");
     * 
     * // 设置缩略图的锐化，默认锐化（true） params.put(UpYun.PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");
     * 
     * // 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称 // 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
     * params.put(UpYun.PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "small");
     * 
     * // 上传文件，并自动创建父级目录（最多10级） boolean result = upyun.writeFile(filePath,ls, true, params);
     * 
     * System.out.println(filePath + " 制作缩略图" + isSuccess(result)); System.out.println("可以通过该路径来访问图片：" + URL +
     * filePath); System.out.println(); }
     */
    /**
     * 上传文件
     * 
     * @throws java.io.IOException
     */
    public static Map<String, String> writeFile(MultipartFile file, String filePath)
        throws IOException
    {
        
        // 要传到upyun后的文件路径
        // String filePath = DIR_ROOT + PIC_NAME;
        // String f="/home/cl/IdeaProjects/nala/web-app/"+SAMPLE_PIC_FILE ;
        // 本地待上传的图片文件
        // File file = new File(SAMPLE_PIC_FILE);
        
        // 设置待上传文件的 Content-MD5 值
        // 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
        upyun.setContentMD5(UpYun.md5(file));
        
        // 设置待上传文件的"访问密钥"
        // 注意：
        // 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
        // 举例：
        // 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
        // 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
        // upyun.setFileSecret("bac");
        
        // 上传文件，并自动创建父级目录（最多10级）
        boolean result = upyun.writeFile(filePath, file.getInputStream(), true);
        
        // System.out.println(filePath + " 上传" + isSuccess(result));
        
        // 获取上传文件后的信息（仅图片空间有返回数据）
        // System.out.println("\r\n****** " + file.getName() + " 的图片信息 *******");
        // 图片宽度
        // System.out.println("图片宽度:" + upyun.getPicWidth());
        // 图片高度
        // System.out.println("图片高度:" + upyun.getPicHeight());
        // 图片帧数
        // System.out.println("图片帧数:" + upyun.getPicFrames());
        // 图片类型
        // System.out.println("图片类型:" + upyun.getPicType());
        
        // System.out.println("****************************************\r\n");
        
        // System.out.println("若设置过访问密钥(bac)，且缩略图间隔标志符为'!'，则可以通过以下路径来访问图片：");
        // System.out.println(URL + filePath + "!bac");
        // System.out.println();
        Map<String, String> map = new HashMap<String, String>();
        if (result)
        {
            map.put("status", "success");
            map.put("url", URL + filePath);
        }
        else
        {
            map.put("status", "failure");
        }
        // String finalpath= URL + filePath;
        return map;
    }
    
    /**
     * 图片做缩略图
     * <p>
     * 注意：若使用了缩略图功能，则会丢弃原图
     * 
     * @throws java.io.IOException
     */
    /*
     * public static void testGmkerl(FileInputStream ls) throws IOException {
     * 
     * // 要传到upyun后的文件路径 String filePath = DIR_ROOT + "gmkerl.jpg";
     * 
     * // 本地待上传的图片文件 // File file = new File(SAMPLE_PIC_FILE);
     * 
     * // 设置缩略图的参数 Map<String, String> params = new HashMap<String, String>();
     * 
     * // 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效 params.put(UpYun.PARAMS.KEY_X_GMKERL_TYPE.getValue(),
     * UpYun.PARAMS.VALUE_FIX_BOTH.getValue());
     * 
     * // 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效 params.put(UpYun.PARAMS.KEY_X_GMKERL_VALUE.getValue(), "150x150");
     * 
     * // 设置缩略图的质量，默认 95 params.put(UpYun.PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");
     * 
     * // 设置缩略图的锐化，默认锐化（true） params.put(UpYun.PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");
     * 
     * // 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称 // 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
     * params.put(UpYun.PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "small");
     * 
     * // 上传文件，并自动创建父级目录（最多10级） boolean result = upyun.writeFile(filePath,ls, true, params);
     * 
     * System.out.println(filePath + " 制作缩略图" + isSuccess(result)); System.out.println("可以通过该路径来访问图片：" + URL +
     * filePath); System.out.println(); }
     */
    
    /**
     * 图片旋转
     * 
     * @throws java.io.IOException
     */
    // public static void testRotate(FileInputStream ls) throws IOException {
    //
    // // 要传到upyun后的文件路径
    // String filePath = DIR_ROOT + "rotate.jpg";
    //
    // // 本地待上传的图片文件
    // File file = new File(SAMPLE_PIC_FILE);
    //
    // // 图片旋转功能具体可参考：http://wiki.upyun.com/index.php?title=图片旋转
    // Map<String, String> params = new HashMap<String, String>();
    //
    // // 设置图片旋转：只接受"auto"，"90"，"180"，"270"四种参数
    // params.put(UpYun.PARAMS.KEY_X_GMKERL_ROTATE.getValue(),
    // UpYun.PARAMS.VALUE_ROTATE_90.getValue());
    //
    // // 上传文件，并自动创建父级目录（最多10级）
    // boolean result = upyun.writeFile(filePath, ls, true, params);
    //
    // System.out.println(filePath + " 图片旋转" + isSuccess(result));
    // System.out.println("可以通过该路径来访问图片：" + URL + filePath);
    // System.out.println();
    // }
    
    /**
     * 图片裁剪
     * 
     * @throws java.io.IOException
     */
    // public static void testCrop(FileInputStream ls) throws IOException {
    //
    // // 要传到upyun后的文件路径
    // String filePath = DIR_ROOT + "crop.jpg";
    //
    // // 本地待上传的图片文件
    // File file = new File(SAMPLE_PIC_FILE);
    //
    // // 图片裁剪功能具体可参考：http://wiki.upyun.com/index.php?title=图片裁剪
    // Map<String, String> params = new HashMap<String, String>();
    //
    // // 设置图片裁剪，参数格式：x,y,width,height
    // params.put(UpYun.PARAMS.KEY_X_GMKERL_CROP.getValue(), "50,50,300,300");
    //
    // // 上传文件，并自动创建父级目录（最多10级）
    // boolean result = upyun.writeFile(filePath, ls, true, params);
    //
    // System.out.println(filePath + " 图片裁剪" + isSuccess(result));
    // System.out.println("可以通过该路径来访问图片：" + URL + filePath);
    // System.out.println();
    // }
    
    public static void main(String[] args)
        throws IOException
    {
        /*
         * System.out.println(uploadFile( CommonUtil.convertInputStream2Str(new FileInputStream("F:\\mycat.jpg"))
         * .getBytes(), "orderrefund/applyImage/"+1234 + ".jpg"));
         */
        /*
         * System.out.println(uploadFile(CommonUtil.convertInputStream2Bytes(new FileInputStream("F:\\mycat.jpg")),
         * "orderrefund/applyImage/" + 1234 + ".jpg"));
         */
        
        String base64 =
            "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AJ6J/wB9CgCSio/Oi/56J/30KXzov+eif99CgB9FM86L/non/fQo86L/AJ6J/wB9CgB9FM86L/non/fQo86L/non/fQoAfRTPOi/56J/30KPOi/56J/30KAH0Uzzov8Anon/AH0KTzov+eif99CgCSio/Oi/56J/30KPOi/56J/30KAJKKZ50X/PRP8AvoUedF/z0T/voUAPopnnRf8APRP++hSedF/z0T/voUASUVH50X/PRP8AvoUvnRf89E/76FAD6KZ50X/PRP8AvoUedF/z0T/voUAPopnnRf8APRP++hR50X/PRP8AvoUAPopnnRf89E/76FHnRf8APRP++hQA+imedF/z0T/voUedF/z0T/voUAPopnnRf89E/wC+hR50X/PRP++hQA+imedF/wA9E/76FHnRf89E/wC+hQA+imedF/z0T/voUedF/wA9E/76FAD6KZ50X/PRP++hR50X/PRP++hQA+imedF/z0T/AL6FHnRf89E/76FAD6KZ50X/AD0T/voUedF/z0T/AL6FAD6KZ50X/PRP++hR50X/AD0T/voUAPopnnRf89E/76FHnRf89E/76FAD6KZ50X/PRP8AvoUedF/z0T/voUAPopnnRf8APRP++hSedF/z0T/voUASUVH50X/PRP8AvoU8c0ALRRSEgDJIA96AFoqPzov+eif99Cl86L/non/fQoAfRTPOi/56J/30KPOi/wCeif8AfQoAfRTPOi/56J/30KPOi/56J/30KAH0VH50X/PRP++hR50X/PRP++hQBJRTVdXHysD9DTqACs7Sfv3f/XxJ/wChVoVn6T9+7/6+JP8A0KgDSopMj1ooAj2L5v3R930qrpXEE/8A18y/+hmrn/Lb/gP9ap6V/qbj/r5m/wDQzQBdooooAoauiyRWySKGVrhAVYZBGal/svT/APnxtf8Avyv+FR6p0tP+vmP+dNknkj1WZV+ZVtVYIWwM7moAm/svTv8Anxtf+/K/4Uf2Xp3/AD42v/flf8KzxrpAuN0CsYFV22PkFScVabUyZLlIY1dYAuXZ8AkjOPyI/OgCVtN01FLNZWoA6kwr/hR/Z2m/8+Vpz/0yX/Cq9tqP2+2u1MSBoCUYZ3Kw2g/1qDzZdtqkcdoVaJSQ6E+WmPrzQBfGm6aRkWVqR6+Uv+FNWy0p1DLaWZU9D5S/4UwNt0x2cxuh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";
        Map<String, String> writeResult = uploadFile(CommonUtil.getBytesFromBASE64(base64), "orderrefund/applyImages/" + System.currentTimeMillis() + ".jpg");
        System.out.println(writeResult);
        
        /*
         * String b64 = CommonUtil.getBASE64FromBytes(CommonUtil.convertInputStream2Bytes(new
         * FileInputStream("F:\\mycat.jpg")) ) ; System.out.println(b64); Map<String,String> writeResult =
         * uploadFile(CommonUtil.getBytesFromBASE64(b64), "orderrefund/applyImages/"+ System.currentTimeMillis() +
         * ".jpg"); System.out.println(writeResult);
         */
        
        // System.out.println(uploadFile(CommonUtil.convertInputStream2Bytes(new
        // FileInputStream("E:\\燕格格平台需求\\测试图片\\标签图\\保税区特卖.png")), "account/headImage/" + 1234 + ".jpg"));
        
    }
}
