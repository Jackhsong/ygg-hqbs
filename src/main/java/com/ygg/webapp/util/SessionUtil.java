package com.ygg.webapp.util;

import javax.servlet.http.HttpSession;

import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.view.AccountView;
import com.ygg.webapp.view.OrderProductRefundView;
import com.ygg.webapp.view.ShoppingCartView;

public class SessionUtil
{
    
    public static final String CURRENT_USER_SESSION_KEY = "yggwebapp_current_user_key";
    
    
     /**左岸城堡用户session key    */
    public static final String CURRENT_USER_QQBS_SESSION_KEY = "current_user_qqbs_session_key";
    
    public static final String CURRENT_USER_SHOPPINGCART_KEY = "yggwebapp_current_user_shoppingcart_key";
    
    public static final String ORDER_REFUND_ACCOUNT_CARD_KEY = "orderRefundAccountCardKey";
    
    /**
     * 保存定单的编号
     */
    public static final String ORDER_CONFIRM_ID_NUM_KEY = "confirmId";
    
    // public static final String ORDER_ID_KEY="orderKeyIds" ;
    
    public static final String WEIXIN_CODE_KEY = "weixincode";
    
    public static final String WEIXIN_OPENID_KEY = "weixinopenid";
    
    public static final String WEIXIN_CODE_KEY_QQBS = "weixincodeqqbs";
    
    public static final String WEIXIN_OPENID_KEY_QQBS = "weixinopenidqqbs";
    
    public static final String CANCEL_ORDER_ID_KEY = "cancelOrderId";
    
    public static final String APP_MARKER_VALUE = "appMarkerValue";
    
    /**
     * 提交　Order 页面时,用户选地址是，先保存在session中
     */
    public static final String ORDER_SELECTED_ADDRESS_KEY = "orderSelectedAddressId";
    
    public static final String ORDER_SELECTED_COUPON_KEY = "orderSelectedCouponId";
    
    public static final String CURRENT_REQUEST_RESOURCE = "request_from_app_";
    
    
     /**左岸城堡购物车数量    */
    public static final String QQBS_CART_COUNT_SESSION_KEY = "cartCount";
    
    
    public static void addUserToSession(HttpSession session, AccountView av)
    {
        if (null == av || av.getId() <= 0)
            return;
        // WebUtils.setSessionAttribute(request, CURRENT_USER_SESSION_KEY, av); // 也可以用Spring带的类去判断　
        session.setAttribute(CURRENT_USER_SESSION_KEY, av);
    }
    
    public static void addOrderConfirmId(HttpSession session, String confirmId)
    {
        if (confirmId == null || confirmId.equals(""))
            return;
        session.setAttribute(ORDER_CONFIRM_ID_NUM_KEY, confirmId);
    }
    
    public static void addOrderRefundProduct(HttpSession session, OrderProductRefundView oprv)
    {
        if (oprv == null)
            return;
        session.setAttribute(ORDER_REFUND_ACCOUNT_CARD_KEY, oprv);
    }
    
    public static void addSelectedAddressId(HttpSession session, String orderSelectedAddressId)
    {
        if (orderSelectedAddressId == null || orderSelectedAddressId.equals(""))
            return;
        session.setAttribute(ORDER_SELECTED_ADDRESS_KEY, orderSelectedAddressId);
    }
    
    public static String getCurrentOrderConfirmId(HttpSession session)
    {
        String confirmId = session.getAttribute(ORDER_CONFIRM_ID_NUM_KEY) == null ? "0" : (String)session.getAttribute(ORDER_CONFIRM_ID_NUM_KEY);
        return confirmId;
    }
    
    public static OrderProductRefundView getOrderRefundProduct(HttpSession session)
    {
        if (session.getAttribute(ORDER_REFUND_ACCOUNT_CARD_KEY) == null)
            return null;
        return (OrderProductRefundView)session.getAttribute(ORDER_REFUND_ACCOUNT_CARD_KEY);
    }
    
    public static String getCurrentSelectedAddressId(HttpSession session)
    {
        String orderSelectedAddressId = session.getAttribute(ORDER_SELECTED_ADDRESS_KEY) == null ? "-1" : (String)session.getAttribute(ORDER_SELECTED_ADDRESS_KEY);
        return orderSelectedAddressId;
    }
    
    public static void addAppMarkValue(HttpSession session, String isAppMarkValue)
    {
        session.setAttribute(APP_MARKER_VALUE, isAppMarkValue);
    }
    
    public static String getAppMarkValue(HttpSession session)
    {
        return (session.getAttribute(APP_MARKER_VALUE) == null ? null : (String)session.getAttribute(APP_MARKER_VALUE));
    }
    
    public static void removeAppMarkValue(HttpSession session)
    {
        session.removeAttribute(APP_MARKER_VALUE);
    }
    
    public static AccountView getCurrentUser(HttpSession session)
    {
        AccountView av = session.getAttribute(CURRENT_USER_SESSION_KEY) == null ? null : (AccountView)session.getAttribute(CURRENT_USER_SESSION_KEY);
        return av;
    }
    
    public static void addUserShoppingCartToSession(HttpSession session, ShoppingCartView sc)
    {
        if (null == sc || Integer.parseInt(sc.getId()) <= 0)
            return;
        session.setAttribute(CURRENT_USER_SHOPPINGCART_KEY, sc);
    }
    
    public static ShoppingCartView getCurrentUserShoppingCart(HttpSession session)
    {
        ShoppingCartView scv = (ShoppingCartView)session.getAttribute(CURRENT_USER_SHOPPINGCART_KEY);
        return scv;
    }
    
    public static void removeUserToSession(HttpSession session)
    {
        session.removeAttribute(CURRENT_USER_SESSION_KEY);
        session.removeAttribute(CURRENT_USER_SHOPPINGCART_KEY);
    }
    
    public static void removeCurrentOrderConfirmId(HttpSession session)
    {
        session.removeAttribute(ORDER_CONFIRM_ID_NUM_KEY);
    }
    
    public static void removeCurrentSelectedAddres(HttpSession session)
    {
        session.removeAttribute(ORDER_SELECTED_ADDRESS_KEY);
    }
    
    public static void removeOrderRefundProduct(HttpSession session)
    {
        session.removeAttribute(ORDER_REFUND_ACCOUNT_CARD_KEY);
    }
    
    public static void addWeiXinCode(HttpSession session, String weixincode)
    {
        if (weixincode != null && !weixincode.equals(""))
        {
            session.setAttribute(WEIXIN_CODE_KEY, weixincode);
        }
        
    }
    
    public static void removeWeiXinCode(HttpSession session)
    {
        session.removeAttribute(WEIXIN_CODE_KEY);
    }
    
    public static String getCurrentWeiXinCode(HttpSession session)
    {
        return (String)session.getAttribute(WEIXIN_CODE_KEY);
    }
    
    public static void addWeiXinCodeQQBS(HttpSession session, String weixincode)
    {
        if (weixincode != null && !weixincode.equals(""))
        {
            session.setAttribute(WEIXIN_CODE_KEY_QQBS, weixincode);
        }
        
    }
    
    public static void removeWeiXinCodeQQBS(HttpSession session)
    {
        session.removeAttribute(WEIXIN_CODE_KEY_QQBS);
    }
    
    public static String getCurrentWeiXinCodeQQBS(HttpSession session)
    {
        return (String)session.getAttribute(WEIXIN_CODE_KEY_QQBS);
    }
    
    public static void addWeiXinOpenId(HttpSession session, String openId)
    {
        if (openId != null && !openId.equals(""))
            session.setAttribute(WEIXIN_OPENID_KEY, openId);
    }
    
    public static void removeWeiXinOpenId(HttpSession session)
    {
        session.removeAttribute(WEIXIN_OPENID_KEY);
    }
    
    public static String getCurrentWeiXinOpenId(HttpSession session)
    {
        return (String)session.getAttribute(WEIXIN_OPENID_KEY);
    }
    
    public static void addWeiXinOpenIdQQBS(HttpSession session, String openId)
    {
        if (openId != null && !openId.equals(""))
            session.setAttribute(WEIXIN_OPENID_KEY_QQBS, openId);
    }
    
    public static void removeWeiXinOpenIdQQBS(HttpSession session)
    {
        session.removeAttribute(WEIXIN_OPENID_KEY_QQBS);
    }
    
    public static String getCurrentWeiXinOpenIdQQBS(HttpSession session)
    {
        return (String)session.getAttribute(WEIXIN_OPENID_KEY_QQBS);
    }
    
    public static void addCurrentOrderId(HttpSession session, String orderIds)
    {
        session.setAttribute(CANCEL_ORDER_ID_KEY, orderIds);
    }
    
    public static String getCurrentOrderId(HttpSession session)
    {
        return (session.getAttribute(CANCEL_ORDER_ID_KEY) == null ? "" : session.getAttribute(CANCEL_ORDER_ID_KEY) + "");
    }
    
    public static void removeCurrentOrderId(HttpSession session)
    {
        session.removeAttribute(CANCEL_ORDER_ID_KEY);
    }
    
    /*
     * public static void addCurOrderIds(HttpSession session,String orderIdKey) { if(orderIdKey !=null &&
     * !orderIdKey.equals("") ) session.setAttribute(ORDER_ID_KEY, orderIdKey); }
     * 
     * public static String getCurOrderIds(HttpSession session) { String orderIds =
     * session.getAttribute(ORDER_ID_KEY)==null?"": session.getAttribute(ORDER_ID_KEY)+""; return orderIds ; }
     * 
     * public static void removeCurOrderIds(HttpSession session) { session.removeAttribute(ORDER_ID_KEY); }
     */
    
    public static void removeCurrentSelectedCouponId(HttpSession session, String confirmId)
    {
        session.removeAttribute(ORDER_SELECTED_COUPON_KEY + "_" + confirmId);
    }
    
    public static void addSelectedCouponId(HttpSession session, String orderSelectedCouponId, String confirmId)
    {
        if (orderSelectedCouponId == null || orderSelectedCouponId.equals(""))
            return;
        session.setAttribute(ORDER_SELECTED_COUPON_KEY + "_" + confirmId, orderSelectedCouponId);
    }
    
    public static String getCurrentSelectedCouponId(HttpSession session, String confirmId)
    {
        String orderSelectedCouponId =
            session.getAttribute(ORDER_SELECTED_COUPON_KEY + "_" + confirmId) == null ? "-1" : (String)session.getAttribute(ORDER_SELECTED_COUPON_KEY + "_" + confirmId);
        return orderSelectedCouponId;
    }
    
    public static void addCurrentRequestResource(HttpSession session, String accountId, String isApp)
    {
        session.setAttribute(CURRENT_REQUEST_RESOURCE + accountId, isApp);
    }
    
    public static String getCurrentRequestResource(HttpSession session, String accountId)
    {
        String isApp = session.getAttribute(CURRENT_REQUEST_RESOURCE + accountId) == null ? null : (String)session.getAttribute(CURRENT_REQUEST_RESOURCE + accountId);
        return isApp;
    }
    
    public static QqbsAccountEntity getQqbsAccountFromSession(HttpSession session)
    {
    	QqbsAccountEntity av = session.getAttribute(CURRENT_USER_QQBS_SESSION_KEY) == null ? null : (QqbsAccountEntity)session.getAttribute(CURRENT_USER_QQBS_SESSION_KEY);
        return av;
    }
    public static void addQqbsAccountToSession(HttpSession session, QqbsAccountEntity av)
    {
        if (null == av || av.getId() <= 0)
            return;
        session.setAttribute(CURRENT_USER_QQBS_SESSION_KEY, av);
    }
    
    /**
     * 购物车数量添加到session中
     * @param session
     * @param cartCount
     */
    public static void addCartCount(HttpSession session,String cartCount){
        session.setAttribute(QQBS_CART_COUNT_SESSION_KEY, cartCount);
    }
    /**
     * 购物车数量添加到session中
     * @param session
     * @param cartCount
     */
    public static String getCartCount(HttpSession session){
       String cartc =  (String) session.getAttribute(QQBS_CART_COUNT_SESSION_KEY);
       return cartc;
    }
    
}
