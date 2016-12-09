package com.ygg.webapp.util;

public class CommonEnum
{
    
    public static enum ORDER_REFUND_REQ_SOURCE
    {
        REQ_SOURCE_WEB("1"), // web
        REQ_SOURCE_ANDROID("2"), // android
        REQ_SOURCE_IOS("3"); // ios
        
        private String value;
        
        private ORDER_REFUND_REQ_SOURCE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-支付详情-错误码
     */
    public static enum ORDER_PAYDETAIL_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ORDERID_NOT_EXIST("2"); // orderId不存在
        
        private String value;
        
        private ORDER_PAYDETAIL_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单支付渠道
     */
    public static enum ORDER_PAY_CHANNEL
    {
        UNIONPAY(1), // 银联
        ALIPAY(2), // 支付宝
        WEIXIN(3); // 微信
        
        private int value;
        
        private ORDER_PAY_CHANNEL(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-付款-错误码
     */
    public static enum ORDER_PAY_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ORDERID_NOT_EXIST("2"), // orderId不存在
        CHANNEL_NOT_EXIST("3"), // channel不存在
        ORDER_STATUS_INVALID("4"); // 订单状态错误
        
        private String value;
        
        private ORDER_PAY_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-新增-错误码
     */
    public static enum ORDER_ADD_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        CARTTOEKN_NOT_EXIST("2"), // cartToekn不存在
        ADDRESSID_NOT_EXIST("3"), // addressId不存在
        CART_IS_EXPIRED("4"), // 购物车已过期
        ID_CARD_INVALID("5"), // 身份证不合法
        TOTAL_PRICE_INVALID("6"), // 订单总价不正确
        CONFIRMID_NOT_EXIST("7"); // confirmId不存在
        
        private String value;
        
        private ORDER_ADD_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-详情-错误码
     */
    public static enum ORDER_DETAIL_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ORDERID_NOT_EXIST("2"); // orderId不存在
        
        private String value;
        
        private ORDER_DETAIL_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-确认-错误码
     */
    public static enum ORDER_CONFIRM_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        CARTTOEKN_NOT_EXIST("2"), // cartToekn不存在
        CART_IS_EXPIRED("3"); // 购物车已过期
        
        private String value;
        
        private ORDER_CONFIRM_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-修改-错误码
     */
    public static enum ORDER_MODIFY_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ORDERID_NOT_EXIST("2"), // orderId不存在
        ORDERID_STATUS_INVALID("3"); // 订单状态不正确
        
        private String value;
        
        private ORDER_MODIFY_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单确认类型
     */
    public static enum ORDER_CONFIRM_TYPE
    {
        NORMAL("1"), // 正常确认订单
        TEMP_MERGER("2"); // 结算时登录确认订单
        
        private String value;
        
        private ORDER_CONFIRM_TYPE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 购物车-列表-错误码
     */
    public static enum CART_SUBMIT_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        CARTTOEKN_NOT_EXIST("2"); // cartToekn不存在
        
        private String value;
        
        private CART_SUBMIT_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 购物车-列表-错误码
     */
    public static enum CART_LIST_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        CARTTOEKN_NOT_EXIST("2"); // cartToekn不存在
        
        private String value;
        
        private CART_LIST_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 购物车-合并-错误码
     */
    public static enum CART_MERGER_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        CARTTOEKN_NOT_EXIST("2"); // cartToekn不存在
        
        private String value;
        
        private CART_MERGER_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 购物车-编辑-错误码
     */
    public static enum CART_EDIT_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        PRODUCTID_NOT_EXIST("2"), // productId不存在
        CARTTOEKN_NOT_EXIST("3"), // cartToekn不存在
        STOCK_LACK("4"), // 库存不足
        RESTRICTION_EXCEED("5"), // 超过商品限购数量
        NO_COMPETENCE_PRODUCT_GEGE_WELFARE("6");// 无购买格格福利商品资格
        
        private String value;
        
        private CART_EDIT_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 收货地址-删除-错误码
     */
    public static enum ADDRESS_DELETE_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ADDRESSID_NOT_EXIST("2"); // addressId不存在
        
        private String value;
        
        private ADDRESS_DELETE_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 收货地址-修改-错误码
     */
    public static enum ADDRESS_MODIFY_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ADDRESSID_NOT_EXIST("2"), // addressId不存在
        ID_CARD_INVALID("3"); // 身份证不合法
        
        private String value;
        
        private ADDRESS_MODIFY_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 收货地址-列表-错误码
     */
    public static enum ADDRESS_LIST_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"); // accountId不存在
        
        private String value;
        
        private ADDRESS_LIST_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 收货地址-新增-错误码
     */
    public static enum ADDRESS_ADD_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        ID_CARD_INVALID("2"), // 身份证不合法
        ID_CARD_INVILD_MODBILE("5"); // 手机号不合法
        
        private String value;
        
        private ADDRESS_ADD_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 收货地址-获取全国省市区信息-错误码
     */
    public static enum ADDRESS_NATIONWIDE_ERRORCODE
    {
        REQUEST_REPEAT("1"); // 请求过于频繁
        
        private String value;
        
        private ADDRESS_NATIONWIDE_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单商品库存状态
     */
    public static enum ORDER_PRODUCT_STOCK_STATUS
    {
        SALE("1"), // 有货
        NO("2"); // 已抢完
        
        private String value;
        
        private ORDER_PRODUCT_STOCK_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 用户登录状态
     */
    public static enum ACCOUNT_LOGIN_STATUS
    {
        YES("1"), // 用户已登录
        NO("2"); // 用户未登录
        
        private String value;
        
        private ACCOUNT_LOGIN_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 账号-修改密码-错误码
     */
    public static enum ACCOUNT_MODIFYPWD_ERRORCODE
    {
        MOBILENUMBER_NOT_EXIST("1"), // 手机号不存在
        OLD_PASSWORD_INVALID("2"); // 旧密码无效
        
        private String value;
        
        private ACCOUNT_MODIFYPWD_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 账号-登录-错误码
     */
    public static enum ACCOUNT_RESET_ERRORCODE
    {
        MOBILENUMBER_NOT_EXIST("1"), // 手机号不存在
        VERIFICATION_INVALID("2"); // 验证码无效
        
        private String value;
        
        private ACCOUNT_RESET_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 账号-登录-错误码
     */
    public static enum ACCOUNT_LOGIN_ERRORCODE
    {
        MOBILENUMBER_NOT_EXIST("1"), // 手机号不存在
        PASSWORD_INVALID("2"); // 密码错误
        
        private String value;
        
        private ACCOUNT_LOGIN_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 账号-注册-错误码
     */
    public static enum ACCOUNT_REGISTER_ERRORCODE
    {
        VERIFICATION_INVALID(new String[] {"1", "短信验证码错误"}), // 验证码无效
        MOBILENUMBER_REPEAT(new String[] {"2", "手机号已注册"}), // 重复手机号
        MOBILENUMBER_INVALID(new String[] {"3", "请输入11位手机号码"}), // 手机号不合法
        RECOMMENDEDCODE_INVALID(new String[] {"4", "请输入正确的邀请码"}); // 邀请码不存在
        
        private String errorCode;
        
        private String errorMessage;
        
        private ACCOUNT_REGISTER_ERRORCODE(String iValue[])
        {
            this.errorCode = iValue[0];
            this.errorMessage = iValue[1];
        }
        
        public String getErrorCode()
        {
            return this.errorCode;
        }
        
        public String getErrorMessage()
        {
            return this.errorMessage;
        }
    }
    
    /**
     * 账号-注册码-错误码
     */
    public static enum ACCOUNT_VERIFICATION_ERRORCODE
    {
        MOBILENUMBER_INVALID("1"), // 手机号不合法
        MOBILENUMBER_REPEAT("2"), // 重复手机号
        MOBILENUMBER_NOT_EXIST("3"), // 手机号不存在
        REQUEST_REPEAT("4"), // 请求过于频繁，请稍后再试
        IMAGE_VERIFYCODE_ERROR("5"), // 图形验证码错误
        OUTOF_SENDMSG_TIMES("6");// 发送短信次数超过限制
        
        private String value;
        
        private ACCOUNT_VERIFICATION_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 通用是否
     */
    public static enum COMMON_IS
    {
        NO("0"), // 否
        YES("1"); // 是
        
        private String value;
        
        private COMMON_IS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 卖家发货方式
     */
    public static enum SELLER_DELIVERY_TYPE
    {
        DOMESTIC(1), // 国内发货
        BONDED(2), // 保税区发货
        HONGKONG(3); // 香港发货
        
        private int value;
        
        private SELLER_DELIVERY_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 短信验证码类型
     */
    public static enum SMS_VERIFY_TYPE
    {
        REGISTER(1), // 用于注册
        RESET(2); // 用于找回密码
        
        private int value;
        
        private SMS_VERIFY_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 用户登录方式
     */
    public static enum ACCOUNT_LOGIN_TYPE
    {
        MOBILE("1"), // 手机用户
        QQ("2"), // QQ用户
        WEIXIN("3"), // 微信用户
        SINAWEIBO("4"), // 新浪微博用户
        ALIPAY("5"); // 支付宝用户
        
        private String value;
        
        private ACCOUNT_LOGIN_TYPE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 临时购物车商品状态
     */
    public static enum TEMP_SHOPPING_CART_STATUS
    {
        NORMAL(1), // 正常
        USER_DELETE(2), // 用户删除
        STOCK_LACK_REMOVE(3), // 没货移除
        MERGE_CART(4), // 已合并
        STOCK_LACK(5); // 没货
        
        private int value;
        
        private TEMP_SHOPPING_CART_STATUS(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 购物车商品状态
     */
    public static enum SHOPPING_CART_STATUS
    {
        NORMAL(1), // 正常
        USER_DELETE(2), // 用户删除
        STOCK_LACK_REMOVE(3), // 没货移除
        GENERATE_ORDER(4), // 已生成订单
        STOCK_LACK(5); // 没货
        
        private int value;
        
        private SHOPPING_CART_STATUS(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 商品基本信息库存状态
     */
    public static enum PRODUCT_BASE_STOCK_STATUS
    {
        SALE("1"), // 有货
        CHANCE("2"), // 还有机会
        LATER("3"), // 即将开始
        NO("4"); // 已抢完
        
        private String value;
        
        private PRODUCT_BASE_STOCK_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 商品今日特卖库存状态
     */
    public static enum PRODUCT_NOW_STOCK_STATUS
    {
        HAVE("1"), // 有货
        CHANCE("2"), // 还有机会
        NO("3"), // 已抢完
        BRFORE("4"); // 即将开抢
        
        private String value;
        
        private PRODUCT_NOW_STOCK_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 商品绑定类型
     */
    public static enum PRODUCT_BIND_TYPE
    {
        BANNER(1), // banner
        SALE(2); // 特卖
        
        private int value;
        
        private PRODUCT_BIND_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 资源类型
     */
    public static enum RESOURCE_TYPE
    {
        BANNER("1"), // banner
        NOW("2"), // 今日特卖
        LATER("3"), // 即将开始
        THEME("4"), // 主题馆
        CUSTOM("5"), // 自定义活动
        HOT("6"); // 今日最热
        
        private String value;
        
        private RESOURCE_TYPE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 特卖类型
     */
    public static enum SALE_TYPE
    {
        PRODUCT(1), // 商品
        ACTIVITIES_COMMON(2), // 组合
        ACTIVITIES_CUSTOM(3); // 自定义专场
        
        private int value;
        
        private SALE_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 操作PreparedStatemen添加参数的类型
     */
    public static enum FILL_PSTMT_TYPE
    {
        BYTE(1), SHORT(2), INT(3), STRING(4), DATE(5), TIMESTAMP(6);
        
        private int value;
        
        private FILL_PSTMT_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 协议返回状态
     */
    public static enum PROTOCOL_RESPONSE_STATUS
    {
        FAILED("0"), // 失败
        SUCCEED("1"), // 成功
        INVALID("2"); // 无效密钥
        
        private String value;
        
        private PROTOCOL_RESPONSE_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单-列表-错误码
     */
    public static enum ORDER_LIST_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1"), // accountId不存在
        PAGE_INVALID("2"); // page不合法
        
        private String value;
        
        private ORDER_LIST_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 业务返回状态
     */
    public static enum BUSINESS_RESPONSE_STATUS
    {
        FAILED("0"), // 失败
        SUCCEED("1"); // 成功
        
        private String value;
        
        private BUSINESS_RESPONSE_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 确认退货 错误
     */
    public static enum ORDER_PRODUNT_REFUND_STATUS
    {
        MISSING_PARAMS("1"), // 缺少参数错误
        PARAMS_FORMAT_ERROR("2"), // 参数错误或格式不对
        ORDER_PRODUCT_REFUND_ID_NOT_EXISTS("3"), //
        ORDER_PRODUCT_REFUND_ID_IS_EXISTS("4");
        
        private String value;
        
        private ORDER_PRODUNT_REFUND_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    public static enum REFUND_INFO_STATUS
    {
        MISSING_PARAMS("1"), // 缺少参数错误
        ORDER_PRODUCT_REFUND_ID_NOT_EXIST("2"); //
        
        private String value;
        
        private REFUND_INFO_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    public static enum ORDER_RRODUCT_REFUND_STATUS
    {
        APPLICATIONING("1"), // 缺少参数错误
        TO_RETURN("2"), // 2：待退货，
        FOR_RETURN("3"), // 3：待退款，
        SUCCESS_REFUND("4"), // 4：退款成功，
        BACK_OFF("5"), // 5：退款关闭，
        RETURN_CANCEL("6"); // 6：退款取消
        
        private String value;
        
        private ORDER_RRODUCT_REFUND_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    public static enum ACCOUNT_CARD_TYPE
    {
        TYPE_BANK_TYPE(1), // 银行　
        TYPE_ALI_TYPE(2); // 支付宝
        
        private int value;
        
        private ACCOUNT_CARD_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 提交退款申请 错误码
     */
    public static enum ORDER_SUBMIT_PRODUNT_STATUS
    {
        MISSING_PARAMS("1"), // 缺少参数错误
        ACCOUNT_CARD_ERROR("2"), // 关联银行账号不存在
        ACCOUNTCARDID_ERROR("3"), // accountCardId 要为数字
        ORDERID_ERROR("4"), // orderId 要为数字
        PRODUCTID_ERROR("5"), // 订单产品记录不存在 order_product表
        REFUND_COUNT_ERROR("6"), //
        REFUND_MONEY_IS_NOT_NUMERIC("7"), //
        REFUND_MONEY_ERROR("8"), // 退款的金额大于 productCount * salesPrice
        REFUND_RECORD_EXISTS("9"), //
        ORDER_STATUS_ERROR("10"); // 待发货订单只能申请仅退款
        
        private String value;
        
        private ORDER_SUBMIT_PRODUNT_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    public static enum SUBMIT_APPLICATION_INFO_STATUS
    {
        MISSING_PARAMS("1"), // 缺少参数错误
        PRODUCT_ID_ERROR("2"), // 商品Id要为数字或不存在
        ORDER_PRODUCT_ID_ERROR("3"), // 订单商品Id要为数字或不存在
        ORDER_ID_ERROR("4"), // 订单Id要为数字或不存在
        PRODUCT_ORDER_NOT_MATCH("5"); // productId orderId 和orderProductId 不匹配
        
        private String value;
        
        private SUBMIT_APPLICATION_INFO_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 业务返回错误码
     */
    public static enum BUSINESS_RESPONSE_ERRORCODE
    {
        UNKNOWN("0"); // 未知错误
        
        private String value;
        
        private BUSINESS_RESPONSE_ERRORCODE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 定单状态1：未付款，2：待发货，3：已发货，4：交易成功，5：已取消',
     * 
     * @author 　
     *
     */
    public static enum ORDER_STATUS
    {
        /*
         * NOPAYMENT(1), // 1 TOBE_SHIPPED(2), // 2 SHIPPED(3), // 3 TRANS_SUCCESS(4), // 4 TRANS_CANCEL(5) , // 5
         * TIMEOUT_CANCEL(6); // 6 超时取消
         */NOT_PAY(1), // 未付款
        NOT_DELIVERY(2), // 待发货
        YES_DELIVERY(3), // 已发货
        SUCCESS(4), // 交易成功
        USER_CANCEL(5), // 用户取消
        TIME_OUT_CANCEL(6); // 超时取消
        
        private int value;
        
        private ORDER_STATUS(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 定单渠道来源
     *
     */
    public static enum ORDER_CHANNEL_STATUS
    {
        WEBAPP(11), // 网页来源
        QQBS(28), // 左岸城堡来源
        YW(29); // 燕网
        
        private int value;
        
        private ORDER_CHANNEL_STATUS(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    public static enum ACCOUNT_CARD_SHOW_STATUS
    {
        ACCOUNT_ID_ERROR("1");
        
        private String value;
        
        private ACCOUNT_CARD_SHOW_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    public static enum ACCOUNT_CARD_EDIT_STATUS
    {
        PARAMS_ERROR("1"), // 少参数　
        ACCOUNTID_TYPE_ERROR("2"); // accountId & type 要唯一
        
        private String value;
        
        private ACCOUNT_CARD_EDIT_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    public static enum WEIXIN_ORDER_PAY_STATUS
    {
        USER_NOT_LOGIN("1"), // 账号过期或没有login
        IS_NOT_WEIXIN_5("2"), // 请使用微信5.0版本以上
        INVALID_TOKKEN_KEY("3"); // 请使用微信5.0版本以上
        
        private String value;
        
        private WEIXIN_ORDER_PAY_STATUS(String value)
        {
            this.value = value;
        }
        
        public String getValue()
        {
            return this.value;
        }
        
    }
    
    public static enum ORDER_REFUND_INDEX_STATUS
    {
        MISSING_PARAMS("1"), // 缺少参数错误
        ID_NOT_EXISTS("2"), SING_KEY_ERROR("3");
        
        private String value;
        
        private ORDER_REFUND_INDEX_STATUS(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    // 定单物流的来源
    public static enum ORDER_LOGISTIC_SOURCE
    {
        APP_SOURCE("1"), WEB_APP("2"), ORDER_REFUND_PRODUCT("10");
        
        private String value;
        
        private ORDER_LOGISTIC_SOURCE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 用户积分操作类型
     */
    public static enum ACCOUNT_OPERATE_POINT_TYPE
    {
        SHOPPING_BACK_POINT(1, "购物返积分"), // 购物返积分
        SYSTEM_MODIFY(2, "系统调整"), // 系统调整
        SHOPPING_USED(3, "购物抵现"), // 购物抵现
        ORDER_REFUND_BACK(4, "订单退款返还"), // 订单退款返还
        EXCHANGE_COUPON(5, "兑换优惠券"), // 兑换优惠券
        POINT_WITHDRAWALS(6, "积分提现"), // 积分提现
        BUDDY_FIRST_ORDER(7, "小伙伴首次下单"), // 小伙伴首次下单
        BUDDY_AGAIN_ORDER(8, "小伙伴重复下单"), // 小伙伴重复下单
        SIGNIN_REWARD(9, "签到奖励"), // 签到奖励
        COMMENT_REWARD(10, "评价送积分"), // 评价送积分
        BUDDY_PERPETUAL_ORDER(11, "合伙人永久收益"), // 合伙人永久收益
        SHOPPING_USED_CANCEL(12, "购物抵现取消"), //购物抵现取消
        EXCHANGE_PRODUCT_USED(13, "积分换购商品"), // 积分换购
        ACTIVITIES_REWARD(14, "活动送积分");
        
        private int value;
        
        private String description;
        
        private ACCOUNT_OPERATE_POINT_TYPE(int iValue, String description)
        {
            this.value = iValue;
            this.description = description;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (ACCOUNT_OPERATE_POINT_TYPE e : ACCOUNT_OPERATE_POINT_TYPE.values())
            {
                if (ordinal == e.value)
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    public static enum RefundStatusEnum
    {
        /** 0 - 没有用 ，只做占位符 */
        NORMAL("占位符"),
        /** 1 */
        APPLY("申请中"),
        /** 2 */
        WAIT_RETURN_OF_GOODS("待退货"),
        /** 3 */
        WAIT_RETURN_OF_MONEY("待退款"),
        /** 4 */
        SUCCESS("退款成功"),
        /** 5 */
        CLOSE("退款关闭"),
        /** 6 */
        CANCEL("退款取消");
        
        final String description;
        
        private RefundStatusEnum(String description)
        {
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        /**
         * 根据排序值查询描述
         * 
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (RefundStatusEnum e : RefundStatusEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    /**
     * 优惠券范围类型
     */
    public static enum COUPON_SCOPE_TYPE
    {
        COMMON(1), // 全场通用
        ACTIVITIES_COMMON(2), // 指定通用专场
        PRODUCT(3), // 指定商品
        CATEGORY_SECOND(4), // 指定二级类目
        SELLER(5); // 指定卖家
        
        private int value;
        
        private COUPON_SCOPE_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 优惠券来源类型
     */
    public static enum COUPON_SOURCE_TYPE
    {
        COUPON_SEND(1), // 优惠券发放
        COUPON_CODE_EXCHANGE(2), // 优惠码兑换
        LOTTERY_EXCHANGE(3), // 抽奖获取
        SIGNIN_REWARD(4), // 签到奖励
        SUB_RECOMMENDED_REWARD(5), // 小伙伴推荐奖励
        SHARE_GIFT(6), // 分享礼包
        PLAY_GAME(7), // 玩游戏领取
        SPREAD_CHANNEL(8), // 推广渠道领取
        ANY_DOOR(9);// 任意门领取
        
        private int value;
        
        private COUPON_SOURCE_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 合伙人状态
     */
    public static enum PARTNER_STATUS
    {
        IS_NOT_PARTNER(1), // 不是合伙人
        IS_PARTNER(2), // 是合伙人
        DISABLED_PARTNER(3); // 合伙人被禁用
        
        private int value;
        
        private PARTNER_STATUS(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 商品类型
     */
    public static enum PRODUCT_TYPE
    {
        SALE("1"), // 特卖
        MALL("2"); // 商城
        
        private String value;
        
        private PRODUCT_TYPE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 收藏类型
     */
    public static enum COLLECT_TYPE
    {
        SALE("1"), // 特卖
        SALE_PRODUCT("2"), // 特卖商品
        MALL_PRODUCT("3"); // 商城商品
        
        private String value;
        
        private COLLECT_TYPE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 用户优惠券类型
     */
    public static enum ACCOUNT_COUPON_TYPE
    {
        UNUSED(1), // 未使用
        USED(2), // 已使用
        EXPIRED(3); // 已过期
        
        private int value;
        
        private ACCOUNT_COUPON_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    public static enum COUPON_CODE_CHANGE_TPYE
    {
        SINGLE("1"), // 单张优惠券
        LIBAO("2"); // 优惠券礼包
        
        private String value;
        
        private COUPON_CODE_CHANGE_TPYE(String iValue)
        {
            this.value = iValue;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 订单付款来源类型
     */
    public static enum ORDER_PAY_SOURCE_TYPE
    {
        CONFIRM(1), // 确认订单
        DETAIL(2), // 订单详情
        MERGER(3); // 合并订单
        
        private int value;
        
        private ORDER_PAY_SOURCE_TYPE(int iValue)
        {
            this.value = iValue;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 特卖场次
     *
     */
    public static enum SALE_TIME_TYPE
    {
        SALE_10("1"), // 早10点场
        
        SALE_20("2"); // 晚20点场
        
        private String value;
        
        private SALE_TIME_TYPE(String value)
        {
            this.value = value;
        }
        
        public String getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 会员等级
     */
    public static enum LEVEL
    {
        V0(0, 0, "V0会员"),
        
        V1(1, 500, "V1会员"),
        
        V2(2, 2000, "V2会员"),
        
        V3(3, 10000, "V3会员");
        
        private final int code;
        
        /** 最低消费 元 */
        private final int limitMoney;
        
        private final String description;
        
        LEVEL(int code, int limitMoney, String description)
        {
            this.code = code;
            this.limitMoney = limitMoney;
            this.description = description;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public int getLimitMoney()
        {
            return limitMoney;
        }
        
        public String getDescription()
        {
            return description;
        }
    }
    
    /**
     * 付款渠道枚举
     *
     * @author Administrator
     *
     */
    public static enum PayChannelEnum
    {
        /** 0 - 没有用 ，只做占位符 */
        NORMAL("占位符"),
        
        BANK("银联"),
        
        ALIPAY("支付宝"),
        
        WEIXIN("微信");
        
        final String description;
        
        private PayChannelEnum(String description)
        {
            this.description = description;
        }
        
        public String getDescription()
        {
            return description;
        }
        
        /**
         * 根据排序值查询描述
         *
         * @param ordinal
         * @return
         */
        public static String getDescriptionByOrdinal(int ordinal)
        {
            for (PayChannelEnum e : PayChannelEnum.values())
            {
                if (ordinal == e.ordinal())
                {
                    return e.description;
                }
            }
            return "";
        }
    }
    
    /**
     * 商家周末发货类型
     * 
     */
    public static enum SELLER_WEEKENDSENDTYPE
    {
        
        NOT_SEND_WEEKEND(1), // 周末不发货
        SEND_ON_SATURDAY(2), // 星期六发货
        SEND_ON_SUNDAY(3), // 星期天发货
        SEND_ON_WEEKEND(4);// 周末发货
        private int value;
        
        private SELLER_WEEKENDSENDTYPE(int value)
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
    /**
     * 发货时效类型
     *
     */
    public static enum SELLER_SENDTIMETYPE
    {
        
        IN_15_HOUR(1), // 当天15点前订单当天打包并提供物流单号，24小时内有物流信息
        IN_24_HOUR(2), // 单笔订单24小时发货
        IN_48_HOUR(3), // 单笔订单48小时发货
        IN_72_HOUR(4); // 单笔订单72小时发货
        private int value;
        
        private SELLER_SENDTIMETYPE(int value)
        {
            this.value = value;
        }
        
        public int getValue()
        {
            return this.value;
        }
    }
    
}
