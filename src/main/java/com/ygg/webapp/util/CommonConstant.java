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
public class CommonConstant
{
    
    /**
     * 服务器响应业务错误码-未知错误
     */
    public static final String SERVER_RESPONSE_ERROR_UNKNOWN = "0";
    
    /**
     * 服务器响应业务错误码-玩家信息-数值超上限
     */
    public static final String SERVER_RESPONSE_ERROR_PLAYERS_01 = "1";
    
    /**
     * 服务器响应业务错误码-玩家推广-已是推广玩家
     */
    public static final String SERVER_RESPONSE_ERROR_PLAYERS_02 = "4";
    
    /**
     * 服务器响应业务错误码-无尽模式-分数超上限
     */
    public static final String SERVER_RESPONSE_ERROR_ENDLESS_01 = "1";
    
    /**
     * 服务器响应业务错误码-玩家好友-输入玩家id不合法
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_01 = "1";
    
    /**
     * 服务器响应业务错误码-玩家好友-输入玩家id为自己
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_02 = "2";
    
    /**
     * 服务器响应业务错误码-玩家好友-输入玩家id不存在
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_03 = "3";
    
    /**
     * 服务器响应业务错误码-好友申请-重复申请
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_04 = "1";
    
    /**
     * 服务器响应业务错误码-好友申请-已是好友
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_05 = "2";
    
    /**
     * 服务器响应业务错误码-好友申请-自身好友已达上限
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_06 = "3";
    
    /**
     * 服务器响应业务错误码-好友同意-自身好友已达上限
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_07 = "1";
    
    /**
     * 服务器响应业务错误码-好友同意-对方好友已达上限
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_08 = "2";
    
    /**
     * 服务器响应业务错误码-好友同意-过期请求
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_09 = "3";
    
    /**
     * 服务器响应业务错误码-好友助战-同一好友一天只能助战一次
     */
    public static final String SERVER_RESPONSE_ERROR_FRIENDS_10 = "1";
    
    /**
     * 客户端批处理请求上限
     */
    public static final int CLIENT_REQUEST_BATCH_MAX = 50;
    
    /**
     * 客户端邮件接收上限
     */
    public static final int CLIENT_MAIL_RECEIVE_MAX = 20;
    
    /**
     * 战力总排行榜上榜数量
     */
    public static final int POWER_TOTAL_TOP_COUNT = 50;
    
    /**
     * 无尽周排行榜上榜数量
     */
    public static final int ENDLESS_WEEK_TOP_COUNT = 50;
    
    /**
     * 无尽分数上限
     */
    public static final int ENDLESS_PLAYERS_FRACTION_MAX = 999999;
    
    /**
     * 邮件类型-不带奖励的全体发送公告
     */
    public static final byte MAIL_TYPE_ALL_NOTICE = 0;
    
    /**
     * 邮件类型-带奖励的全体发送邮件
     */
    public static final byte MAIL_TYPE_ALL_REWARD_MAIL = 1;
    
    /**
     * 邮件类型-带奖励的指定发送邮件
     */
    public static final byte MAIL_TYPE_ASSIGN_REWARD_MAIL = 2;
    
    /**
     * 玩家申请状态-未下发到被申请id
     */
    public static final byte PLAYERS_APPLY_STATUS_NOTISSUED = 0;
    
    /**
     * 玩家申请状态-已下发到申请id，但还未处理
     */
    public static final byte PLAYERS_APPLY_STATUS_UNTREATED = 1;
    
    /**
     * 玩家信息状态-已解锁
     */
    public static final byte PLAYERS_INFO_STATUS_UNLOCK = 1;
    
    /**
     * 玩家信息状态-未解锁
     */
    public static final byte PLAYERS_INFO_STATUS_LOCK = 0;
    
    /**
     * 玩家信息状态-非vip玩家
     */
    public static final byte PLAYERS_INFO_VIP_NOT = 0;
    
    /**
     * 玩家信息状态-vip玩家
     */
    public static final byte PLAYERS_INFO_VIP_YES = 1;
    
    /**
     * 玩家信息等级上限
     */
    public static final int PLAYERS_INFO_LEVEL_MAX = 40;
    
    /**
     * 玩家信息战力上限
     */
    public static final int PLAYERS_INFO_POWER_MAX = 17000;
    
    /**
     * 玩家信息装备等级上限
     */
    public static final int PLAYERS_INFO_EQUIPMENT_LEVEL_MAX = 20;
    
    /**
     * 玩家信息装备珠子属性上限
     */
    public static final int PLAYERS_INFO_EQUIPMENT_BEAD_MAX = 100;
    
    /**
     * 玩家信息技能等级上限
     */
    public static final int PLAYERS_INFO_SKILL_LEVEL_MAX = 10;
    
    /**
     * 管理员id
     */
    public static final int PLAYERS_INFO_GM_ID = 10000000;
    
    /**
     * 玩家好友数量上限
     */
    public static final int PLAYERS_FRIENDS_COUNT_MAX = 50;
    
    /**
     * 玩家好友推荐上限
     */
    public static final int PLAYERS_FRIENDS_RECOMMEND_MAX = 20;
    
    /**
     * 平台签名密钥
     */
    public static final String SIGN_KEY = "yangegegegeyan";
    
    /**
     * 平台使用字符编码
     */
    public static final String CHARACTER_ENCODING = "UTF-8";
    
    /**
     * 每日特卖上新事件，单位：二十四计时制
     */
    public static final int SALE_REFRESH_HOUR = 10;

    public static final int SALE_REFRESH_HOUR_NIGHT = 20;
    
    /**
     * id不存在
     */
    public static final int ID_NOT_EXIST = -1;
    
    /**
     * 平台短信提示语
     */
    public static final String SMS_CONTENT = "【左岸城堡】短信验证码:";
    
    public static String getSendRegisterSMSContent(String sendRandomCode)
    {
        return "【左岸代言人】验证码" + sendRandomCode + "，您正在注册左岸代言人账号，验证码10分钟内有效。千万不要说出去哦！";
    }
    
    public static String getSendResetSMSContent(String sendRandomCode)
    {
        return "【左岸代言人】验证码" + sendRandomCode + "，您正在找回密码，验证码10分钟内有效。千万不要说出去哦！";
    }
    
    public static final String ERROR_CODE = "errorCode";
    
    /**
     * 定单未付款
     */
    public static final String ORDER_STATUS_1 = "orderstatus1";
    
    /**
     * 待发货
     */
    public static final String ORDER_STATUS_2 = "orderstatus2";
    
    /**
     * 已发货
     */
    public static final String ORDER_STATUS_3 = "orderstatus3";
    
    /**
     * 交易成功
     */
    public static final String ORDER_STATUS_4 = "orderstatus4";
    
    /**
     * 交易取消
     */
    public static final String ORDER_STATUS_5 = "orderstatus5";
    
    /**
     * 操作 DB失败
     */
    public static final String DB_ERROR_ERROR_CODE = "1";
    
    /**
     * 操作DB失败的 Msg
     */
    public static final String DB_ERROR_MSG = "操作失败!";
    
    /**
     * 保税区金额拆分订单阀值
     */
    public static final int BONDED_MONEY_SPLIT_THRESHOLD = 500;
    
    /**
     * 保税区隔天订单阀值
     */
    public static final int BONDED_NEXT_DAY_THRESHOLD = 1000;
    
    /**
     * 默认收货地址省份id
     */
    public static final int DEFAULT_RECEIVE_PROVINCE_ID = 330000;
    
    /**
     * 平台机器唯一标识
     */
    public static final String PLATFORM_IDENTITY_CODE = "2";
    
    /**
     * 商品详情保税区提示语
     */
    public static final String PRODUCT_BONDED_IS_TIP = "* 下单时需提供真实姓名和身份证号。";
    
    /**
     * 商品详情香港提示语
     */
    public static final String PRODUCT_HONGKONG_IS_TIP = "* 下单时需提供真实姓名和身份证号。";
    
    /**
     * 保税区提示语
     */
    // public static final String BONDED_IS_TIP = "含保税区发货商品，根据海关规定，需提供收货人身份证号。";
    public static final String BONDED_IS_TIP = "请提供收货人真实姓名和身份证号。";
    
    /**
     * 保税区分割提示语
     */
    // public static final String BONDED_SPLIT_TIP = "保税区发货商品超过" + BONDED_MONEY_SPLIT_THRESHOLD +
    // "元，为避免通关产生税金，我们已为您拆分订单。";
    public static final String BONDED_SPLIT_TIP = "海外购超过" + BONDED_MONEY_SPLIT_THRESHOLD + "元，为避免产生行邮税，已为您拆分订单并隔天发货。"; // 为避免产生关税，已为您拆分订单。
    
    /**
     * 保税区隔天提示语
     */
    // public static final String BONDED_NEXT_DAY_TIP = "保税区发货商品超过" + BONDED_NEXT_DAY_THRESHOLD +
    // "元，为保证正常清关，我们已自动为您拆分订单并隔天发货。";
    // public static final String BONDED_NEXT_DAY_TIP = "海外购商品超过" + BONDED_NEXT_DAY_THRESHOLD +
    // "元，为保证正常清关，已为您拆分订单并隔天发货。";
    /**
     * 订单每页展示数量
     */
    public static final int ORDER_PAGE_COUNT = 60;
    
    /**
     * 物流快递100授权key
     */
    public static final String LOGISTICS_KUAIDI100_KEY = "key";
    
    /**
     * 物流快递100URL
     */
    public static final String LOGISTICS_KUAIDI100_URL = "http://api.kuaidi100.com/api?id=" + LOGISTICS_KUAIDI100_KEY;
    
    public static final String OrderSoucrChannelKey = "s";
    
    public static final int OrderSoucrChannelKeyLength = 32;
    
    public static final String STATIC_CSS_VERSION = "static_css_version";
    
    public static final String STATIC_JS_VERSION = "static_js_version";
    
    public static final String FILEUPLOAD_URL = "fileupload_url";
    
    /**
     * 用户默认头像
     */
    public static final String ACCOUNT_DEFAULT_HEADIMAGE = "http://img.gegejia.com/platform/appImage.png";
    
    /**
     * V13推荐好友首次下单返积分数量
     */
    public static final int V13_SUB_FIRST_ORDER_RETURN_POINT = 2000;
    
    /**
     * V14推荐好友首次下单返积分数量
     */
    public static final int V14_SUB_FIRST_ORDER_RETURN_POINT = 1000;
    
    /** 同一手机号同一天内发送短信最大次数：20 */
    public static final int SAME_NUMBER_SENDMSG_MAX_TIMES_PER_DAY = 20;
    
    /** 同一IP同一天内发送短信最大次数500 */
    public static final int SAME_IP_SENDMSG_MAX_TIMES_PER_DAY = 500;
    
    /**默认分享文案标题*/
    public static final String DEFAULT_SHARETITLE = "左岸城堡：分享法式高端品质童装，分享精致优雅的生活方式！";
    /** 默认分享文案内容*/
    public static final String DEFAULT_SHARECONTENT = "分享法式高端品质童装，分享精致优雅的生活方式！";
    
    public static final String APPID = YggWebProperties.getInstance().getProperties("appid");
    
    public static final String APPSECRET = YggWebProperties.getInstance().getProperties("appsecret");
    
    /**
     * 用户类型8 左岸城堡
     */
    public static final Byte ACCOUNTTYPE = 8;
    
}
