/**
 * Project Name:KooLeiDian2
 * File Name:CommonConstant.java
 * Package Name:com.koogame.util
 * Date:2014-4-14下午04:26:24
 * Copyright (c) 2014, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.ygg.webapp.util;

/**
 * ClassName:CommonConstant <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-4-14 下午04:26:24 <br/>
 *
 * @author "Lin"
 * @version
 * @since JDK 1.6
 * @see
 */
public class CommonProperties
{
    
    /**
     * 是否验证签名
     */
    public static boolean isValidateSign = true;
    
    /**
     * 平台数据源配置
     */
    public static String defaultDbConfig = "ygg";
    
    /**
     * 邮费包邮阀值
     */
    public static int postageThreshold = 88;
    
    /**
     * 平台是否注册即送优惠券
     */
    public static boolean isRegisterCoupon = false;
    
    /**
     * 静态文件css版本号
     */
    public static String staticCssVersion = "1";
    
    /**
     * 静态js版本号
     */
    public static String staticJsVersion = "1";
    
    /**
     * 推广微信格格号
     */
    public static String weixinGege = "yangege02";

    /**
     * 待审核订单最低消费金额
     */
    public static float orderLowestCheckMoney = 3;
    /**
     * 平台缓存配置
     */
    public static volatile String defaultCacheConfig = "memcache";
    
}
