package com.ygg.webapp.code;

public enum ErrorCodeEnum
{
    ;
    /**
     * 账号-获取个人优惠券-错误码
     */
    public static enum ACCOUNT_GETCOUPON_ERRORCODE
    {
        ACCOUNTID_NOT_LOGIN("1", "请先登录哦~");
        
        private String value;
        
        private String description;
        
        private ACCOUNT_GETCOUPON_ERRORCODE(String iValue, String description)
        {
            this.value = iValue;
            this.description = description;
        }
        
        public String getValue()
        {
            return this.value;
        }
        
        public String getDescription()
        {
            return this.description;
        }
    }
    
    /**
     * 账号-优惠券码兑换优惠券-错误码
     */
    public static enum ACCOUNT_CODECHANGECOUPON_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST(new String[] {"1", "出错了~稍后再试试吧"}), // accountId不存在
        COUPONCODE_NOT_EXIST(new String[] {"2", "优惠券码不存在"}), // 优惠券码不存在
        COUPONCODE_IS_USED(new String[] {"3", "优惠券码已被使用"}), // 优惠券码已被使用
        COUPONCODE_IS_EXPIRED(new String[] {"4", "优惠券码已过期"}), // 优惠券码已过期
        EXCEED_USED_COUNT(new String[] {"5", "您已兑换过该优惠券码，无法重复兑换"}); // 超过同一批次优惠券码使用次数
        
        private String errorCode;
        
        private String errorMessage;
        
        private ACCOUNT_CODECHANGECOUPON_ERRORCODE(String iValue[])
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
     * 订单-获取订单优惠券-错误码
     */
    public static enum ORDER_GETCOUPON_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST("1", "未登录哦"), // accountId不存在
        CONFIRMID_NOT_EXIST("2", "订单确认ID不存在"), // confirmId不存在
        ORDERID_NOT_EXIST("3", "订单ID不存在"); // orderId不存在
        
        private String value;
        
        private String description;
        
        private ORDER_GETCOUPON_ERRORCODE(String iValue, String description)
        {
            this.value = iValue;
            this.description = description;
        }
        
        public String getValue()
        {
            return this.value;
        }
        
        public String getDescription()
        {
            return description;
        }
    }
    
    /**
     * 订单-新增-错误码
     */
    public static enum ORDER_ADD_ERRORCODE
    {
        ACCOUNTID_NOT_EXIST(new String[] {"1", "出错了~稍后再试试吧"}), // accountId不存在
        CARTTOEKN_NOT_EXIST(new String[] {"2", "出错了~稍后再试试吧"}), // cartToekn不存在
        ADDRESSID_NOT_EXIST(new String[] {"3", "亲~请先选择收货地址"}), // addressId不存在
        CART_IS_EXPIRED(new String[] {"4", "又逛超时了~"}), // 购物车已过期
        ID_CARD_INVALID(new String[] {"5", "请输入正确的18位身份证号码"}), // 身份证不合法
        TOTAL_PRICE_INVALID(new String[] {"6", "系统繁忙，请重新提交"}), // 订单总价不正确
        CONFIRMID_NOT_EXIST(new String[] {"7", "系统繁忙，请重新提交"}), // confirmId不存在
        REAL_PRICE_INVALID(new String[] {"8", "系统繁忙，请重新提交"}), // 优惠后订单总价异常
        USED_POINT_INVALID(new String[] {"9", "系统繁忙，请重新提交"}), // 积分使用异常
        USED_COUPON_INVALID(new String[] {"10", "系统繁忙，请重新提交"}), // 优惠券状态异常
        UNSUPPORT_DELIVER_AREA(new String[] {"11", "不支持的配送地区"});
        
        private String errorCode;
        
        private String errorMessage;
        
        private ORDER_ADD_ERRORCODE(String iValue[])
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
}
