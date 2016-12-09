package com.ygg.webapp.util;

import org.joda.time.DateTime;

public class CacheConstant
{
    
    /**
     * 左岸城堡WEB APP缓存标识
     */
    public static final String HQBSWEB_CACHE_KEY = "hqbs_web_";
    
    public static final String INDEX_BANNER_LIST = "bannerList";
    
    public static final String INDEX_NOW_LIST = "nowList";
    
    public static final String INDEX_LATER_LIST = "laterList";
    
    public static final String INDEX_BANNER_LIST_CACHE = HQBSWEB_CACHE_KEY + INDEX_BANNER_LIST;
    
    public static final String INDEX_NOW_LIST_CACHE = HQBSWEB_CACHE_KEY + INDEX_NOW_LIST;
    
    public static final String INDEX_LATER_LIST_CACHE = HQBSWEB_CACHE_KEY + INDEX_LATER_LIST;
    
    /**
     * 缓存首页中的html内容保存在内存中　　页面缓存
     */
    public static final String YGG_PAGE_INDEX_CACHE = HQBSWEB_CACHE_KEY + "yggPageIndexCache";
    
    public static final String YGG_WX_SHARE_JAVASCRIPT_CACHE = HQBSWEB_CACHE_KEY + "yggWxShareJavaScriptCache";
    
    /**
     * 单个商品的Key　+商品的ID 的数据缓存 整个单页面的Key用此常量表示，结构为:Map<String,Map<String,Object>>
     */
    public static final String SINGLE_PRODUCT_CACHE = HQBSWEB_CACHE_KEY + "singleProductCache_";
    
    /**
     * 特卖商品 单个商品html页面内容的缓存key +商品的ID　　页面缓存 整个单页面的Key用此常量表示，结构为:Map<String,Map<String,Object>>
     */
    public static final String PAGE_SINGLE_PRODUCT_CACHE = HQBSWEB_CACHE_KEY + "pageSingleProductCache_";
    
    public static final String PAGE_WXSHARE_SINGLE_PRODUCT_CACHE = HQBSWEB_CACHE_KEY + "pageWxShareSingleProductCache_";
    
    /**
     * 商城商品 单个商品html页面内容的缓存key +商品的ID　　页面缓存 整个单页面的Key用此常量表示，结构为:Map<String,Map<String,Object>>
     */
    public static final String PAGE_WXSHARE_SINGLE_PRODUCT_CACHE_MALL = HQBSWEB_CACHE_KEY + "pageWxShareSingleProductCacheMall_";
    
    public static final String PAGE_SINGLE_PRODUCT_CACHE_MALL = HQBSWEB_CACHE_KEY + "pageSingleProductCacheMall_";
    
    /**
     * 　 专场的Key+专场的ID　　的数据缓存
     */
    public static final String COMMON_ACTIVITY_PRODUCT_CACHE = HQBSWEB_CACHE_KEY + "commonActivityProductcache_";
    
    /**
     * 专场html页面的内容缓存key + 专场的ID　　页面缓存
     */
    public static final String PAGE_COMMON_ACTIVITY_PRODUCT_CACHE = HQBSWEB_CACHE_KEY + "pageCommonActivityProductcache_";
    
    public static final String PAGE_WXSHARE_COMMON_ACTIVITY_PRODUCT_CACHE = HQBSWEB_CACHE_KEY + "pageWxShareCommonActivityProductcache_";
    
    /**
     * 所有省的cache key　list<provinces>
     */
    public static final String PROVINCE_DATA_CACHE = HQBSWEB_CACHE_KEY + "yggprovince";
    
    // public static final String CITY_ALL_DATA_CACHE="yggallcity" ;
    // public static final String DISTRICT_ALL_DATA_CACHE="yggalldistrict" ;
    
    /**
     * 市的cache key Map<yggcitiy,Map<pid,city>>
     */
    public static final String CITY_DATA_CACHE = HQBSWEB_CACHE_KEY + "yggcitiy";
    
    /**
     * 区的cache key Map<yggdistrict,Map<cid,district>>
     */
    public static final String DISTRICT_DATA_CACHE = HQBSWEB_CACHE_KEY + "yggdistrict";
    
    /**
     * 免运费的cache Key value List<FreightView>
     */
    public static final String FREE_SHIPPING_FREIGHT_CACHE = HQBSWEB_CACHE_KEY + "freeShippingFreight";
    
    /**
     * 订单确认时的cache保存10分钟 第一层只保存number，相当于索引　 key:orderconfirmId,value : List<ordernumber> 第二层再拿出ordernumber去查实际的entity
     * key orderconfirmId+ordernumber , value:responseparams
     */
    public static final String ORDER_COMFIRM_ID_CACHE = HQBSWEB_CACHE_KEY + "orderconfirmId";
    
    /**
     * 微信的access_token有效期为7200秒
     */
    public static final String WEIXIN_ACCESS_TOKEN = HQBSWEB_CACHE_KEY + "access_token";
    
    /**
     * 微信的jsapi_ticket 有效期为7200秒
     */
    public static final String WEIXIN_JSAPI_TICKET = HQBSWEB_CACHE_KEY + "jsapi_ticket";
    
    /**
     * 定单ID ,保存30分钟　
     */
    public static final String ORDER_IDS_CACHE = HQBSWEB_CACHE_KEY + "orderIdsCache";
    
    /**
     * 方法调用的时间Key
     */
    public static final String REQUEST_COST_TIME_CACHE = HQBSWEB_CACHE_KEY + "reqUrl_";
    
    /**
     * 缓存30秒
     */
    public static final int CACHE_SECOND_30 = 30;
    
    /**
     * 缓存一分钟
     */
    public static final int CACHE_MINUTE_1 = 60;
    
    /**
     * 缓存十分钟
     */
    public static final int CACHE_MINUTE_10 = 60 * 10;
    
    /**
     * 缓存一小时
     */
    public static final int CACHE_HOUR_1 = 60 * 60;
    
    /**
     * 缓存一天
     */
    public static final int CACHE_DAY_1 = 24 * 60 * 60;
    
    /**
     * 缓存七天
     */
    public static final int CACHE_DAY_7 = 24 * 60 * 60 * 7;
    
    /**
     * 地址管理缓存标识
     */
    public static final String ADD_CACHE_KEY = HQBSWEB_CACHE_KEY + "add_";
    
    /**
     * 地址管理-getNationwideInfo-缓存标识
     */
    public static final String ADD_GETNATIONWIDEINFO_KEY = ADD_CACHE_KEY + "a_";
    
    /**
     * 资源管理缓存标识
     */
    public static final String RES_CACHE_KEY = HQBSWEB_CACHE_KEY + "res_";
    
    /**
     * 资源管理-homeList-banner-缓存标识
     */
    public static final String RES_HOMELIST_BANNER_KEY = RES_CACHE_KEY + "a1_";
    
    /**
     * 资源管理-homeList-now-缓存标识
     */
    public static final String RES_HOMELIST_NOW_KEY = RES_CACHE_KEY + "a2_";
    
    /**
     * 资源管理-homeList-later-缓存标识
     */
    public static final String RES_HOMELIST_LATER_KEY = RES_CACHE_KEY + "a3_";
    
    /**
     * 资源管理-homeList-theme-缓存标识
     */
    public static final String RES_HOMELIST_THEME_KEY = RES_CACHE_KEY + "a4_";
    
    /**
     * 资源管理-homeList-activity-缓存标识
     */
    public static final String RES_HOMELIST_ACTIVITY_KEY = RES_CACHE_KEY + "a5_";
    
    /**
     * 资源管理-homeList-hot-缓存标识
     */
    public static final String RES_HOMELIST_HOT_KEY = RES_CACHE_KEY + "a6_";
    
    /**
     * 资源管理-homeDetail-banner-缓存标识
     */
    public static final String RES_HOMEDETAIL_BANNER_KEY = RES_CACHE_KEY + "b1_";
    
    /**
     * 资源管理-homeDetail-now-缓存标识
     */
    public static final String RES_HOMEDETAIL_NOW_SWE_KEY = RES_CACHE_KEY + "b2_";
    
    /**
     * 资源管理-homeDetail-now-缓存标识
     */
    public static final String RES_HOMEDETAIL_NOW_NV_KEY = RES_CACHE_KEY + "b3_";
    
    /**
     * 资源管理-homeDetail-later-缓存标识
     */
    public static final String RES_HOMEDETAIL_LATER_SWE_KEY = RES_CACHE_KEY + "b4_";
    
    /**
     * 资源管理-homeDetail-later-缓存标识
     */
    public static final String RES_HOMEDETAIL_LATER_LV_KEY = RES_CACHE_KEY + "b5_";
    
    /**
     * 资源管理-homeDetail-now-缓存标识
     */
    public static final String RES_HOMEDETAIL_NOW_NVSTATUS_KEY = RES_CACHE_KEY + "b6_";
    
    /**
     * 资源管理-productBase-缓存标识
     */
    public static final String RES_PRODUCTBASE_PE_KEY = RES_CACHE_KEY + "c1_";
    
    /**
     * 资源管理-productBase-缓存标识
     */
    public static final String RES_PRODUCTBASE_SE_KEY = RES_CACHE_KEY + "c2_";
    
    /**
     * 资源管理-productBase-缓存标识
     */
    public static final String RES_PRODUCTBASE_BE_KEY = RES_CACHE_KEY + "c3_";
    
    /**
     * 资源管理-productBase-缓存标识
     */
    public static final String RES_PRODUCTBASE_PCES_KEY = RES_CACHE_KEY + "c4_";
    
    /**
     * 资源管理-productBase-缓存标识
     */
    public static final String RES_PRODUCTBASE_GIE_KEY = RES_CACHE_KEY + "c5_";
    
    /**
     * 资源管理-productBase-缓存标识
     */
    public static final String RES_PRODUCTBASE_GIE1_KEY = RES_CACHE_KEY + "c6_";
    
    /** 基本商品缓存标识 */
    public static final String RES_PRODUCTBASE_BASEPE_KEY = RES_CACHE_KEY + "c7_";
    
    /**
     * 资源管理-productDetail-缓存标识
     */
    public static final String RES_PRODUCTDETAIL_KEY = RES_CACHE_KEY + "d1_";
    
    /**
     * 资源管理-commonActivitiesList-缓存标识
     */
    public static final String RES_COMMONACTIVITIESLIST_SWE_KEY = RES_CACHE_KEY + "e1_";
    
    /**
     * 资源管理-commonActivitiesList-缓存标识
     */
    public static final String RES_COMMONACTIVITIESLIST_ACE_KEY = RES_CACHE_KEY + "e2_";
    
    /**
     * 资源管理-commonActivitiesList-缓存标识
     */
    public static final String RES_THEMEMENULIST_MWE_KEY = RES_CACHE_KEY + "f1_";
    
    /**
     * 资源管理-commonActivitiesList-缓存标识
     */
    public static final String RES_THEMEMENULIST_MALLPAGEMAP_KEY = RES_CACHE_KEY + "f2_";
    
    /**
     * 资源管理-commonActivitiesList-缓存标识
     */
    public static final String RES_THEMEMENULIST_MALLPAGEFLOORLIST_KEY = RES_CACHE_KEY + "f3_";
    
    /**
     * 资源管理-commonActivitiesList-缓存标识
     */
    public static final String RES_THEMEMENUPRODUCTLIST_PRODUCTIDS_KEY = RES_CACHE_KEY + "g1_";
    
    /**
     * 资源管理-spreadAd-缓存标识
     */
    public static final String RES_SPREADAD_KEY = RES_CACHE_KEY + "h1_";
    
    /**
     * 资源管理-customActivitiesDetail-缓存标识
     */
    public static final String RES_CUSTOMACTIVITIESDETAIL_KEY = RES_CACHE_KEY + "i1_";
    
    /**
     * 平台缓存标识
     */
    public static final String COMMON_CACHE_KEY = "common_";
    
    public static final String STATIC_CSS_VERSION_CACHE_KEY = HQBSWEB_CACHE_KEY + "staticCssVersion";
    
    public static final String STATIC_JS_VERSION_CACHE_KEY = HQBSWEB_CACHE_KEY + "staticJSVersion";
    
    /** 统计同一手机号同一天内发送短信次数缓存标识 */
    public static String NUMBER_SENDMSG_TIMES_COUNT = HQBSWEB_CACHE_KEY + "number_" + DateTime.now().toString("yyyyMMdd") + "_";
    
    /** 统计同一ip同一天内发送短信次数缓存标识 */
    public static String IP_SENDMSG_TIMES_COUNT = HQBSWEB_CACHE_KEY + "ip_" + DateTime.now().toString("yyyyMMdd") + "_";
    
    public static final String IS_SEND_TIPS = HQBSWEB_CACHE_KEY + "attack_request_" + DateTime.now().toString("yyyyMMdd") + "_";
    
    public static final String COUPON_DETAIL = HQBSWEB_CACHE_KEY + "coupon_detail";
    
    /**
     * 环信TOKEN缓存标识
     */
    public static final String COMMON_HUANXIN_CACHE_KEY = COMMON_CACHE_KEY + "huanxin";
    
    /**
     * 资源管理-banner-缓存标识
     */
    public static final String RES_BANNER_PE_KEY = RES_CACHE_KEY + "banner_a_";
    
    /**
     * 资源管理-salewindow-缓存标识
     */
    public static final String RES_SALE_WINDOW_KEY = RES_CACHE_KEY + "salewidow_a_";
    
    /**
     * 资源管理-salewindow-状态缓存标识
     */
    public static final String RES_SALE_WINDOW_STATUS_KEY = RES_CACHE_KEY + "salewidow_status_a_";
    
    /**
     * 资源管理-国旗缓存-缓存标识
     */
    public static final String RES_SALE_FLAG_PE_KEY = RES_CACHE_KEY + "saleFlag_b_";
    
    /** 左岸城堡token缓存 */
    public static final String HQBS_TOKEN_CACHE = "HQBS_TOKEN_CACHE";
    
    public static final String HQBS_TOKEN_LOCK = "HQBS_TOKEN_LOCK";
    
    /** 左岸城堡TICKET缓存 */
    public static final String HQBS_TICKET_CACHE = "HQBS_TICKET_CACHE";
    
    public static final String HQBS_TICKET_LOCK = "HQBS_TICKET_LOCK";
    
    /**
     * 左岸城堡二维码图片地址缓存
     */
    public static final String HQBS_QRCODE_IMAGE_LINK_CACHE = "HQBS_QRCODE_IMAGE_LINK_CACHE_";
    
}
