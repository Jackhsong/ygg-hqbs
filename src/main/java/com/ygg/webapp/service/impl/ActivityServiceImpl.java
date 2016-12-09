package com.ygg.webapp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.ygg.common.utils.CommonUtil;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.AccessTypeEnum;
import com.ygg.webapp.code.ActivityCrazyFoodPrizeTypeEnum;
import com.ygg.webapp.code.ActivityEnum;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.code.LotteryActivityDrawMessageEnum;
import com.ygg.webapp.code.LotteryPrizeTypeEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.ActivitiesCommonDao;
import com.ygg.webapp.dao.ActivityDao;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.dao.CouponDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.LotteryActivityDao;
import com.ygg.webapp.dao.ProductCountDao;
import com.ygg.webapp.dao.ProductDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodPrizeEntity;
import com.ygg.webapp.entity.ActivityCrazyFoodRecordEntity;
import com.ygg.webapp.entity.ActivitySimplifyEntity;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.CouponEntity;
import com.ygg.webapp.entity.LotteryActivityEntity;
import com.ygg.webapp.entity.LotteryPrizeEntity;
import com.ygg.webapp.entity.ProductCountEntity;
import com.ygg.webapp.entity.ProductEntity;
import com.ygg.webapp.entity.RegisterMobileCouponEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.ActivityService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.DateTimeUtil;
import com.ygg.webapp.util.YggWebProperties;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService
{
    private static Logger logger = Logger.getLogger(ActivityServiceImpl.class);
    
    @Resource
    private ActivityDao activityDao;
    
    @Resource
    private ProductDao productDao;
    
    @Resource(name = "productCountDao")
    private ProductCountDao productCountDao;
    
    @Resource
    private AccountDao accountDao;
    
    @Resource
    private CouponDao couponDao;
    
    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource
    private RestTemplate restTemplate;
    
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Resource
    private CouponCodeDao couponCodeDao;
    
    @Resource
    private LotteryActivityDao lotteryActivityDao;
    /**    */
	@Resource
	private ActivitiesCommonDao activitiesCommonDao;
    @Override
    public Map<String, Object> findActivitySimplifyDetailById(int activityId, int type)
        throws ServiceException
    {
        Map<String, Object> resultMap = null;
        ActivitySimplifyEntity ase = activityDao.findActivitySimplifyById(activityId);
        if (ase == null)
        {
            return resultMap;
        }
        resultMap = new HashMap<String, Object>();
        resultMap.put("title", ase.getTitle());
        resultMap.put("image", ase.getImage());
        List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> reList = activityDao.findActivitySimplifyProduct(activityId);
        for (Map<String, Object> it : reList)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            
            int productId = Integer.valueOf(it.get("productId") + "").intValue();
            ProductEntity pe = productDao.findProductInfoById(productId);
            if (pe == null)
            {
                continue;
            }
            map.put("title", it.get("title"));
            map.put("desc", it.get("desc"));
            map.put("image", it.get("image"));
            map.put("marketPrice", pe.getMarketPrice());
            map.put("salesPrice", pe.getSalesPrice());
            StringBuilder url = new StringBuilder();
            String status = "1";
            if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
            {
                if (type == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    url.append("ggj://redirect/resource/saleProduct/").append(pe.getId());
                }
                else if (type == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    url.append("/item-").append(pe.getId());
                }
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    Date nowTime = new Date();
                    Date startTime = CommonUtil.string2Date(pe.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                    Date endTime = CommonUtil.string2Date(pe.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                    if (nowTime.before(startTime))
                    {
                        status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.LATER.getValue();
                    }
                    else if (nowTime.after(endTime))
                    {
                        status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue();
                    }
                    else
                    {
                        ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                        if (pce.getStock() == 0)
                        {
                            status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue();
                        }
                        else
                        {
                            status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue();
                        }
                    }
                }
                else
                {
                    status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue();
                }
            }
            else if (pe.getType() == Byte.valueOf(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
            {
                if (type == AccessTypeEnum.TYPE_OF_APP.getValue())
                {
                    url.append("ggj://redirect/resource/mallProduct/").append(pe.getId());
                }
                else if (type == AccessTypeEnum.TYPE_OF_WAP.getValue())
                {
                    url.append("/mitem-").append(pe.getId());
                }
                
                if (pe.getIsOffShelves() == Byte.valueOf(CommonEnum.COMMON_IS.NO.getValue()))
                {
                    ProductCountEntity pce = productCountDao.findCountInfoById(pe.getId());
                    if (pce.getStock() == 0)
                    {
                        status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue();
                    }
                    else
                    {
                        status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.SALE.getValue();
                    }
                }
                else
                {
                    status = CommonEnum.PRODUCT_BASE_STOCK_STATUS.NO.getValue();
                }
            }
            map.put("url", url.toString());
            map.put("status", status);
            productList.add(map);
            
        }
        resultMap.put("productList", productList);
        return resultMap;
    }
    
    @Override
    public boolean existsActivityCrazyFood(int activityId)
        throws ServiceException
    {
        ActivityCrazyFoodEntity acfe = activityDao.findActivityCrazyFoodById(activityId);
        return acfe != null;
    }
    
    @Override
    public Map<String, Object> draw(int accountId, String mobileNumber, int activityId)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        
        //app用户
        if ("".equals(mobileNumber))
        {
            mobileNumber = accountDao.findAccountNameById(accountId);
            if (StringUtils.isEmpty(mobileNumber))
            {
                status = LotteryActivityDrawMessageEnum.ACCOUNT_NOT_EXIST.ordinal();
                result.put("status", status);
                logger.info("疯狂抽美食--用户Id:" + accountId + "不存在！！！");
                return result;
            }
        }
        
        ActivityCrazyFoodEntity acfe = activityDao.findActivityCrazyFoodById(activityId);
        Date start = DateTimeUtil.string2Date(acfe.getStartTime());
        Date end = DateTimeUtil.string2Date(acfe.getEndTime());
        Date now = new Date();
        if (start.after(now))
        {
            // 活动未开始
            status = LotteryActivityDrawMessageEnum.NOT_STARTED.ordinal();
            result.put("status", status);
            return result;
        }
        if (end.before(now))
        {
            // 活动已结束
            status = LotteryActivityDrawMessageEnum.COMPLETE.ordinal();
            result.put("status", status);
            return result;
        }
        
        Map<String, Object> record = activityDao.findActivityCrazyFoodRecord(activityId, mobileNumber);
        if (record == null)
        {
            // 插入用户抽奖信息记录
            Map<String, Object> info = activityDao.insertActivityCrazyFoodRecord(mobileNumber, acfe, 0);
            if (info == null)
            {
                status = LotteryActivityDrawMessageEnum.PRIZE_NOT_EXIST.ordinal();
                result.put("status", status);
                logger.info("疯狂抽美食--插入用户抽奖记录失败！！！");
                return result;
            }
            record = info;
        }
        
        // 剩余抽奖次数
        int leftTimes = Integer.parseInt(record.get("leftTimes") == null ? "0" : record.get("leftTimes") + "");
        // 已使用抽奖次数
        int usedTimes = Integer.parseInt(record.get("usedTimes") == null ? "0" : record.get("usedTimes") + "");
        // 是否中奖
        int isWine = Integer.parseInt(record.get("isWine") == null ? "0" : record.get("isWine") + "");
        // 是否分享
        int isShared = Integer.parseInt(record.get("isShared") == null ? "0" : record.get("isShared") + "");
        if (leftTimes > 0)
        {
            List<ActivityCrazyFoodPrizeEntity> prizes = activityDao.findActivityCrazyFoodPrizeByActivityId(activityId);
            if (prizes.size() > 0)
            {
                if (isWine == 1)
                {
                    Map<String, Object> para = new HashMap<String, Object>();
                    para.put("leftTimes", leftTimes - 1);
                    para.put("usedTimes", usedTimes + 1);
                    para.put("username", mobileNumber);
                    para.put("activityId", activityId);
                    activityDao.updateActivityCrazyFoodRecord(para);
                    status = LotteryActivityDrawMessageEnum.SUCCESS.ordinal();
                    result.put("status", status);
                    result.put("leftTimes", leftTimes - 1);
                    result.put("usedTimes", usedTimes + 1);
                    result.put("prize", ActivityCrazyFoodPrizeTypeEnum.PRIZE_1.getValue());
                    return result;
                }
                else
                {
                    // 中奖逻辑
                    ActivityCrazyFoodPrizeEntity prize = null;
                    while (true)
                    {
                        double upperLimit = 0; // 当前商品的中奖几率的上线限（含）
                        double floorLimit = 0; // 当前商品的中奖几率的下限（不含）
                        double previousFloorLimit = 0; // 前一个商品的中奖几率的下限
                        double cur = new Random().nextDouble();
                        int sum = 0;
                        for (ActivityCrazyFoodPrizeEntity it : prizes)
                        {
                            sum += it.getProbability();
                        }
                        
                        for (ActivityCrazyFoodPrizeEntity it : prizes)
                        {
                            floorLimit = previousFloorLimit;
                            upperLimit = 1.0 * it.getProbability() / sum + floorLimit;
                            previousFloorLimit = upperLimit;
                            if (floorLimit <= cur && cur < upperLimit)
                            {
                                prize = it;
                                break;
                            }
                        }
                        if (prize.getType() == LotteryPrizeTypeEnum.COUPON.ordinal())
                        {
                            //中奖：检查奖品数量是否大于0，大于则先将奖品数量减1；若更新失败则重新抽奖，直到更新成功
                            if (prize.getPrizeAmount() > 0)
                            {
                                if (activityDao.updateActivityCrazyFoodAmount(prize) > 0)
                                {
                                    break;
                                }
                            }
                            else
                            {
                                //奖品已抽完，直接按未中奖处理
                                Map<String, Object> para = new HashMap<String, Object>();
                                para.put("leftTimes", leftTimes - 1);
                                para.put("usedTimes", usedTimes + 1);
                                para.put("username", mobileNumber);
                                para.put("activityId", activityId);
                                para.put("isWine", 0);
                                activityDao.updateActivityCrazyFoodRecord(para);
                                status = LotteryActivityDrawMessageEnum.SUCCESS.ordinal();
                                result.put("status", status);
                                result.put("leftTimes", leftTimes - 1);
                                result.put("usedTimes", usedTimes + 1);
                                result.put("prize", ActivityCrazyFoodPrizeTypeEnum.PRIZE_1.getValue());
                                return result;
                            }
                        }
                        else
                        {
                            //未中奖
                            Map<String, Object> para = new HashMap<String, Object>();
                            para.put("leftTimes", leftTimes - 1);
                            para.put("usedTimes", usedTimes + 1);
                            para.put("username", mobileNumber);
                            para.put("activityId", activityId);
                            para.put("isWine", 0);
                            activityDao.updateActivityCrazyFoodRecord(para);
                            status = LotteryActivityDrawMessageEnum.SUCCESS.ordinal();
                            result.put("status", status);
                            result.put("leftTimes", leftTimes - 1);
                            result.put("usedTimes", usedTimes + 1);
                            result.put("prize", ActivityCrazyFoodPrizeTypeEnum.PRIZE_1.getValue());
                            return result;
                        }
                    }
                    
                    // 抽中奖品逻辑
                    // 向手机号对应账户发放优惠券
                    int couponId = prize.getCouponId();
                    CouponEntity ce = couponDao.findCouponById(couponId);
                    if (ce == null)
                    {
                        status = LotteryActivityDrawMessageEnum.PRIZE_NOT_EXIST.ordinal();
                        throw new ServiceException("疯狂抽美食--优惠券信息不存在！！");
                    }
                    
                    int aId = -1;
                    if (accountId > 0)
                    {
                        aId = accountId;
                    }
                    else
                    {
                        aId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
                    }
                    if (aId == CommonConstant.ID_NOT_EXIST)
                    {
                        // 插入注册就送预优惠券表
                        status =
                            accountDao.addRegisterMobileCoupon(mobileNumber, couponId, prize.getValidDaysType(), prize.getDays(), CouponAccountSourceTypeEnum.LOTTERY.getCode(), 0);
                    }
                    else
                    {
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(aId);
                        cae.setCouponDetailId(ce.getCouponDetailId());
                        cae.setCouponId(couponId);
                        cae.setRemark(ce.getRemark());
                        if (prize.getValidDaysType() == 1)
                        {
                            cae.setStartTime(ce.getStartTime());
                            cae.setEndTime(ce.getEndTime());
                        }
                        else
                        {
                            cae.setStartTime(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toString("yyyy-MM-dd HH:mm:ss"));
                            cae.setEndTime(DateTime.now()
                                .plusDays(prize.getDays() - 1)
                                .withHourOfDay(23)
                                .withMinuteOfHour(59)
                                .withSecondOfMinute(59)
                                .toString("yyyy-MM-dd HH:mm:ss"));
                        }
                        cae.setSourceType(Byte.valueOf(CouponAccountSourceTypeEnum.LOTTERY.getCode() + ""));
                        cae.setCouponCodeId(0);
                        status = couponAccountDao.addCouponAccount(cae);
                    }
                    if (status != 0)
                    {
                        status = LotteryActivityDrawMessageEnum.SUCCESS.ordinal();
                        Map<String, Object> para = new HashMap<String, Object>();
                        para.put("leftTimes", leftTimes - 1);
                        para.put("usedTimes", usedTimes + 1);
                        para.put("username", mobileNumber);
                        para.put("activityId", activityId);
                        para.put("isWine", 1);
                        //更新用户抽奖记录，更新失败则事物回滚
                        if (activityDao.updateActivityCrazyFoodRecord(para) == 0)
                        {
                            throw new ServiceException("疯狂抽美食--更新用户抽奖次数失败！手机号：" + mobileNumber);
                        }
                        result.put("status", status);
                        result.put("leftTimes", leftTimes - 1);
                        result.put("usedTimes", usedTimes + 1);
                        result.put("prize", ActivityCrazyFoodPrizeTypeEnum.PRIZE_5.getValue());
                        return result;
                    }
                    else
                    {
                        throw new ServiceException("疯狂抽美食--插入用户优惠券信息失败！手机号：" + mobileNumber + "  优惠券Id：" + couponId);
                    }
                }
            }
            else
            {
                status = LotteryActivityDrawMessageEnum.PRIZE_NOT_EXIST.ordinal();
                logger.info("疯狂抽美食--奖品信息不存在！！！");
            }
        }
        else
        {
            status = LotteryActivityDrawMessageEnum.NO_CHANGES.ordinal();
            result.put("leftTimes", 0);
            if (isShared == 1)
            {
                result.put("usedTimes", -1);
            }
            else
            {
                result.put("usedTimes", usedTimes);
            }
        }
        result.put("status", status);
        return result;
    }
    
    @Override
    public Map<String, Object> shareActivity(int accountId, int activityId)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        String msg = "";
        ActivityCrazyFoodEntity acfe = activityDao.findActivityCrazyFoodById(activityId);
        AccountEntity ae = accountDao.findAccountById(accountId);
        if (acfe == null || ae == null)
        {
            msg = "活动或用户不存在";
        }
        else
        {
            Date start = DateTimeUtil.string2Date(acfe.getStartTime());
            Date end = DateTimeUtil.string2Date(acfe.getEndTime());
            Date now = new Date();
            
            if (start.after(now))
            {
                msg = "活动未开始哦~";
            }
            else if (end.before(now))
            {
                msg = "活动已经结束啦~";
            }
            else
            {
                Map<String, Object> record = activityDao.findActivityCrazyFoodRecord(activityId, ae.getName());
                if (record == null)
                {
                    // 该用户还没有抽奖信息 - 插入
                    Map<String, Object> info = activityDao.insertActivityCrazyFoodRecord(ae.getName(), acfe, 1);
                    if (info != null)
                    {
                        // 成功
                        status = 1;
                        msg = "成功";
                    }
                }
                else
                {
                    int isShared = Integer.valueOf(record.get("isShared") + "");
                    int leftTimes = Integer.valueOf(record.get("leftTimes") + "");
                    if (isShared == 0)
                    {
                        Map<String, Object> para = new HashMap<String, Object>();
                        para.put("leftTimes", leftTimes + acfe.getShareIncreaseTimes());
                        para.put("username", ae.getName());
                        para.put("activityId", activityId);
                        para.put("isShared", 1);
                        if (activityDao.updateActivityCrazyFoodRecord(para) > 0)
                        {
                            status = 1;
                            msg = "成功";
                        }
                    }
                    else
                    {
                        // 分享次数已用完
                        status = 2;
                        msg = "分享次数已用完";
                    }
                }
            }
        }
        
        result.put("status", status);
        result.put("msg", "".equals(msg) ? "分享失败" : msg);
        return result;
    }
    
    @Override
    public int getCrazyFoodActivityLeftTimes(int accountId, int activityId)
        throws ServiceException
    {
        AccountEntity ae = accountDao.findAccountById(accountId);
        if (ae == null)
        {
            return 2;
        }
        Map<String, Object> record = activityDao.findActivityCrazyFoodRecord(activityId, ae.getName());
        if (record == null)
        {
            return 2;
        }
        return Integer.parseInt(record.get("leftTimes") == null ? "0" : record.get("leftTimes") + "");
    }
    
    @Override
    public Map<String, String> getUserWinxinInfo(String code)
        throws ServiceException
    {
        String appid = CommonConstant.APPID;
        String appsecret = CommonConstant.APPSECRET;
        
        String nickname = "";
        String headimgurl = "";
        
        // 通过code换取网页授权access_token
        String access_token = "";
        String openid = "";
        String refresh_token = "";
        String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        String accessTokenResult = restTemplate.getForObject(getAccessTokenUrl, String.class);
        Map<String, Object> accessTokenResultMap = (Map<String, Object>)JSON.parse(accessTokenResult);
        logger.info("accessTokenResultMap: " + accessTokenResultMap);
        if (accessTokenResultMap.get("errcode") == null)
        {
            access_token = accessTokenResultMap.get("access_token") + "";
            refresh_token = accessTokenResultMap.get("refresh_token") + "";
            openid = accessTokenResultMap.get("openid") + "";
            
            // 拉取用户信息(需scope为 snsapi_userinfo)
            String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
            String userInfoResult = restTemplate.getForObject(getUserInfoUrl, String.class);
            Map<String, Object> userInfoResultMap = (Map<String, Object>)JSON.parse(userInfoResult);
            logger.info("userInfoResultMap: " + userInfoResultMap);
            if (userInfoResultMap.get("errcode") == null)
            {
                nickname = userInfoResultMap.get("nickname") + "";
                headimgurl = userInfoResultMap.get("headimgurl") + "";
            }
            else
            {
                // 失败，刷新token ，重新获取
                String getRefreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appid + "&grant_type=refresh_token&refresh_token=" + refresh_token;
                String refreshTokenResult = restTemplate.getForObject(getRefreshTokenUrl, String.class);
                Map<String, Object> refreshTokenResultMap = (Map<String, Object>)JSON.parse(refreshTokenResult);
                logger.info("refreshTokenResultMap: " + refreshTokenResultMap);
                if (refreshTokenResultMap.get("errcode") == null)
                {
                    access_token = refreshTokenResultMap.get("access_token") + "";
                    getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
                    userInfoResult = restTemplate.getForObject(getUserInfoUrl, String.class);
                    userInfoResultMap = (Map<String, Object>)JSON.parse(userInfoResult);
                    logger.info("userInfoResultMap: " + userInfoResultMap);
                    if (userInfoResultMap.get("errcode") == null)
                    {
                        nickname = userInfoResultMap.get("nickname") + "";
                        headimgurl = userInfoResultMap.get("headimgurl") + "";
                    }
                }
            }
        }
        
        Map<String, String> result = new HashMap<>();
        result.put("nickname", nickname);
        result.put("headimgurl", headimgurl);
        result.put("openid", openid);
        return result;
    }
    
    @Override
    public Map<String, Object> findRedPacketDrawInfo(int accountId, int from)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<>();
        if (from == 1)
        {
            Map<String, Object> para = new HashMap<>();
            para.put("shareAccountId", accountId);
            para.put("start", 0);
            para.put("max", 60);
            List<Map<String, Object>> data = activityDao.findRedPacketDrawRecord(para);
            if (!data.isEmpty())
            {
                for (Map<String, Object> it : data)
                {
                    String createTime = DateTimeUtil.dateToString(DateTimeUtil.string2Date(it.get("createTime") + ""));
                    it.put("createTime", createTime.substring(0, createTime.length() - 3));
                    it.put("reduce", it.get("reduce") + "元");
                }
            }
            result.put("drawRecord", data);
        }
        else
        {
            int isShare = 0;
            int isSuccess = 0;
            int maxReduce = 0;
            int drawNums = 0;
            List<Map<String, Object>> drawRecord = new ArrayList<>();
            Map<String, Object> shareRecord = activityDao.findRedPacketShareRecordByShareAccountId(accountId);
            if (shareRecord != null)
            {
                // 已分享
                isShare = 1;
                Map<String, Object> para = new HashMap<>();
                para.put("shareAccountId", accountId);
                para.put("start", 0);
                para.put("max", 10);
                drawRecord = activityDao.findRedPacketDrawRecord(para);
                if (!drawRecord.isEmpty())
                {
                    drawNums = drawRecord.size();
                    if (drawRecord.size() == 10)
                    {
                        isSuccess = 1;
                    }
                    Map<Integer, Map<String, Object>> drawRecordTreeMap = new TreeMap<>();
                    for (Map<String, Object> it : drawRecord)
                    {
                        String createTime = DateTimeUtil.dateToString(DateTimeUtil.string2Date(it.get("createTime") + ""));
                        int itReduce = Integer.valueOf(it.get("reduce") + "");
                        if (itReduce > maxReduce)
                        {
                            maxReduce = itReduce;
                        }
                        it.put("createTime", createTime.substring(0, createTime.length() - 3));
                        it.put("reduce", it.get("reduce") + "元");
                        int reduceKey = itReduce * 10000;
                        while (true)
                        {
                            if (drawRecordTreeMap.get(reduceKey) == null)
                            {
                                drawRecordTreeMap.put(reduceKey, it);
                                break;
                            }
                            else
                            {
                                reduceKey += 1;
                            }
                        }
                    }
                    
                    drawRecord = new ArrayList<>();
                    for (Map.Entry<Integer, Map<String, Object>> entry : drawRecordTreeMap.entrySet())
                    {
                        drawRecord.add(entry.getValue());
                    }
                    
                    List<Map<String, Object>> newDrawRecord = new ArrayList<>();
                    for (int i = drawRecord.size() - 1; i >= 0; i--)
                    {
                        newDrawRecord.add(drawRecord.get(i));
                    }
                    drawRecord = newDrawRecord;
                }
            }
            result.put("isShare", isShare);
            result.put("isSuccess", isSuccess);
            result.put("maxReduce", maxReduce);
            result.put("drawNums", drawNums);
            result.put("drawRecord", drawRecord);
        }
        
        return result;
    }
    
    @Override
    public Map<String, Object> getDrawRedPacketInfo(String openid)
        throws ServiceException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("weixinOpenid", openid);
        List<Map<String, Object>> recordList = activityDao.findRedPacketDrawRecord(para);
        String reduce = "";
        String isDraw = "0";
        if (recordList.size() > 0)
        {
            isDraw = "1";
            reduce = recordList.get(0).get("reduce") + "";
        }
        Map<String, Object> result = new HashMap<>();
        result.put("reduce", reduce);
        result.put("isDraw", isDraw);
        return result;
    }
    
    @Override
    public Map<String, Object> drawPacket(String nickname, String headimgurl, String openid, int accountId, String mobileNumber)
        throws ServiceException
    {
        // 5-15 元优惠券ID
        int couponId = 465;
        int status = 1;
        String msg = "抽奖失败啦，请稍后再试~";
        int reducePrice = 0;
        
        // 判断手机号 是否已经领取过
        Map<String, Object> para = new HashMap<>();
        para.put("weixinOpenid", openid);
        List<Map<String, Object>> recordList = activityDao.findRedPacketDrawRecord(para);
        if (recordList.size() > 0)
        {
            status = 2;
            msg = "亲，每个微信号只有一次机会哦~";
        }
        if (recordList.size() == 0)
        {
            para.remove("weixinOpenid");
            para.put("mobileNumber", mobileNumber);
            recordList = activityDao.findRedPacketDrawRecord(para);
            if (recordList.size() > 0)
            {
                status = 2;
                msg = "亲，每个手机号只有一次机会哦~";
            }
        }
        if (recordList.size() == 0)
        {
            CouponEntity coupon = couponDao.findCouponById(couponId);
            Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + coupon.getCouponDetailId());
            CouponDetailEntity cde = null;
            if (cdeCache != null)
            {
                cde = (CouponDetailEntity)cdeCache;
                if (cde.getDesc() == null)
                {
                    cde = couponDetailDao.findCouponDetailById(coupon.getCouponDetailId());
                    cacheService.addCache(CacheConstant.COUPON_DETAIL + coupon.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
                }
            }
            else
            {
                cde = couponDetailDao.findCouponDetailById(coupon.getCouponDetailId());
                cacheService.addCache(CacheConstant.COUPON_DETAIL + coupon.getCouponDetailId(), cde, CacheConstant.CACHE_DAY_1);
            }
            int mobileNumberAccountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
            if (mobileNumberAccountId == CommonConstant.ID_NOT_EXIST)
            {
                // 插入注册就送预优惠券表 100%
                if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                {
                    // 是随机优惠码
                    int lowestReduce = cde.getLowestReduce();
                    int maximalReduce = cde.getMaximalReduce();
                    reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
                }
                status = accountDao.addRegisterMobileCoupon(mobileNumber, couponId, 1, 0, CouponAccountSourceTypeEnum.RED_PACKET.getCode(), reducePrice);
            }
            else
            {
                CouponAccountEntity cae = new CouponAccountEntity();
                cae.setAccountId(mobileNumberAccountId);
                cae.setCouponDetailId(coupon.getCouponDetailId());
                cae.setCouponId(couponId);
                cae.setRemark(cde.getDesc());
                cae.setStartTime(coupon.getStartTime());
                cae.setEndTime(coupon.getEndTime());
                cae.setSourceType((byte)CouponAccountSourceTypeEnum.RED_PACKET.getCode());
                cae.setCouponCodeId(0);
                
                if (cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
                {
                    // 是随机优惠码
                    int lowestReduce = cde.getLowestReduce();
                    int maximalReduce = cde.getMaximalReduce();
                    reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
                }
                cae.setReducePrice(reducePrice);// 随机优惠码该值才有效，普通优惠券该值为0
                status = couponAccountDao.addCouponAccount(cae);
            }
            if (status == 1)
            {
                // 如果没有分享记录，插入accountId对应的分享记录
                Map<String, Object> shareMap = packetShare(accountId);
                if (Integer.valueOf(shareMap.get("status") + "") != 1)
                {
                    throw new ServiceException("插入红包分享记录失败！accountId：" + accountId + "，手机号" + mobileNumber);
                }
                
                try
                {
                    // 插入领取记录
                    Map<String, Object> record = new HashMap<>();
                    record.put("shareAccountId", accountId);
                    record.put("mobileNumber", mobileNumber);
                    record.put("username", com.ygg.webapp.util.CommonUtil.removeIllegalEmoji(nickname));
                    record.put("userImage", headimgurl);
                    record.put("weixinOpenid", openid);
                    record.put("couponId", couponId);
                    record.put("reduce", reducePrice);
                    if (activityDao.insertRedPacketDrawRecord(record) == 0)
                    {
                        throw new ServiceException("插入红包领取记录失败！手机号：" + mobileNumber);
                    }
                }
                catch (Exception e)
                {
                    logger.info("插入领取记录失败！,重新插入。。", e);
                    // 插入领取记录
                    Map<String, Object> record = new HashMap<>();
                    record.put("shareAccountId", accountId);
                    record.put("mobileNumber", mobileNumber);
                    record.put("username", mobileNumber);
                    record.put("userImage", headimgurl);
                    record.put("weixinOpenid", openid);
                    record.put("couponId", couponId);
                    record.put("reduce", reducePrice);
                    if (activityDao.insertRedPacketDrawRecord(record) == 0)
                    {
                        throw new ServiceException("插入红包领取记录失败！手机号：" + mobileNumber);
                    }
                }
                
                // 如果抽奖人数大于10 发送最大优惠券金额给分享人
                Map<String, Object> searchPara = new HashMap<>();
                searchPara.put("shareAccountId", accountId);
                int totalRecord = activityDao.countRedPacketDrawRecord(searchPara);
                if (totalRecord >= 10)
                {
                    Map<String, Object> shareRecord = activityDao.findRedPacketShareRecordByShareAccountId(accountId);
                    int isReceiveCoupon = Integer.valueOf(shareRecord.get("isReceiveCoupon") + "");
                    if (isReceiveCoupon == 0)
                    {
                        searchPara.put("start", 0);
                        searchPara.put("max", 10);
                        List<Map<String, Object>> first10 = activityDao.findRedPacketDrawRecord(searchPara);
                        int maxReducePrice = 0;
                        for (Map<String, Object> it : first10)
                        {
                            int itReducePrice = Integer.valueOf(it.get("reduce") + "");
                            if (itReducePrice > maxReducePrice)
                            {
                                maxReducePrice = itReducePrice;
                            }
                        }
                        
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(coupon.getCouponDetailId());
                        cae.setCouponId(couponId);
                        cae.setRemark(cde.getDesc());
                        cae.setStartTime(coupon.getStartTime());
                        cae.setEndTime(coupon.getEndTime());
                        cae.setSourceType((byte)CouponAccountSourceTypeEnum.RED_PACKET.getCode());
                        cae.setCouponCodeId(0);
                        cae.setReducePrice(maxReducePrice);// 随机优惠码该值才有效，普通优惠券该值为0
                        if (couponAccountDao.addCouponAccount(cae) == 0)
                        {
                            throw new ServiceException("红包领取人数大于10人，给分享者发送现金券失败！shareAccountId：" + accountId);
                        }
                        logger.info("红包领取人数大于10人，给分享者发送红包成功！");
                        Map<String, Object> updatePara = new HashMap<>();
                        updatePara.put("isReceiveCoupon", 1);
                        updatePara.put("shareAccountId", accountId);
                        if (activityDao.updateRedPacketShareRecordByShareAccountId(updatePara) == 0)
                        {
                            throw new ServiceException("红包领取，更新分享人的红包领取状态失败！shareAccountId：" + accountId);
                        }
                    }
                    
                }
            }
            
        }
        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("msg", status != 1 ? msg : "");
        result.put("reduce", reducePrice);
        return result;
    }
    
    @Override
    public Map<String, Object> packetShare(int accountId)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> searchPara = new HashMap<>();
        searchPara.put("shareAccountId", accountId);
        if (activityDao.findRedPacketShareRecordByShareAccountId(accountId) != null)
        {
            result.put("status", 1);
            result.put("msg", "重复分享");
            return result;
        }
        Map<String, Object> para = new HashMap<>();
        para.put("shareAccountId", accountId);
        para.put("isReceiveCoupon", 0);
        int status = activityDao.insertRedPacketShareRecord(para);
        result.put("status", status);
        result.put("msg", status == 1 ? "分享成功" : "分享失败");
        return result;
    }
    
    @Override
    public Map<String, Object> receiveFoodPartyPrize(int accountId, String phoneNumber, String requestFrom)
        throws ServiceException
    {
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if ("1".equals(requestFrom))
        {
            if (StringUtils.isEmpty(phoneNumber))
            {
                resultMap.put("status", "0");
                resultMap.put("msg", "手机号码不正确");
                return resultMap;
            }
            accountId = accountDao.findIdByNameAndType(phoneNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
            int couponId = 872;
            while (couponId < 876)
            {
                CouponEntity coupon = couponDao.findCouponById(couponId);
                if (accountId == CommonConstant.ID_NOT_EXIST)
                {
                    //插入注册就送预优惠券表
                    int id = activityDao.finActivityCouponPrize(phoneNumber, couponId, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode());
                    if (id == CommonConstant.ID_NOT_EXIST)
                    {
                        accountDao.addRegisterMobileCoupon(phoneNumber, couponId, 1, 0, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode(), 0);
                    }
                    else
                    {
                        resultMap.put("status", "2");
                        resultMap.put("msg", "已经领取过了");
                        return resultMap;
                    }
                }
                else
                {
                    CouponAccountEntity caInfo = couponAccountDao.findCouponInfoBycidAndType(accountId, couponId, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode());
                    if (caInfo == null)
                    {
                        //插入账户优惠券表
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(coupon.getCouponDetailId());
                        cae.setCouponId(coupon.getId());
                        cae.setRemark(coupon.getRemark());
                        cae.setStartTime(coupon.getStartTime());
                        cae.setEndTime(coupon.getEndTime());
                        cae.setSourceType((byte)(CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode()));
                        cae.setCouponCodeId(0);
                        cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
                        couponAccountDao.addCouponAccount(cae);
                    }
                    else
                    {
                        resultMap.put("status", "2");
                        resultMap.put("msg", "已经领取过了");
                        return resultMap;
                    }
                }
                couponId++;
            }
            
        }
        else if ("2".equals(requestFrom))
        {
            AccountEntity ae = accountDao.findAccountById(accountId);
            if (ae == null)
            {
                resultMap.put("status", "0");
                resultMap.put("msg", "用户不存在");
                return resultMap;
            }
            //插入账户优惠券表
            int couponId = 872;
            while (couponId < 876)
            {
                CouponAccountEntity caInfo = couponAccountDao.findCouponInfoBycidAndType(accountId, couponId, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode());
                if (caInfo == null)
                {
                    CouponEntity coupon = couponDao.findCouponById(couponId);
                    CouponAccountEntity cae = new CouponAccountEntity();
                    cae.setAccountId(accountId);
                    cae.setCouponDetailId(coupon.getCouponDetailId());
                    cae.setCouponId(coupon.getId());
                    cae.setRemark(coupon.getRemark());
                    cae.setStartTime(coupon.getStartTime());
                    cae.setEndTime(coupon.getEndTime());
                    cae.setSourceType((byte)(CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode()));
                    cae.setCouponCodeId(0);
                    cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
                    couponAccountDao.addCouponAccount(cae);
                }
                else
                {
                    resultMap.put("status", "2");
                    resultMap.put("msg", "已经领取过了");
                    return resultMap;
                }
                couponId++;
            }
            
        }
        resultMap.put("status", "1");
        return resultMap;
    }
    
    @Override
    public Map<String, Object> drawTemp(String mobileNumber, int lotteryId)
        throws ServiceException
    {
        Map<String, Object> result = new HashMap<>();
        int status = 1;
        int prizeCode = 5;
        String msg = "";
        
        LotteryActivityEntity lottery = lotteryActivityDao.findLotteryActivityById(lotteryId);
        Date start = DateTimeUtil.string2Date(lottery.getStartTime());
        Date end = DateTimeUtil.string2Date(lottery.getEndTime());
        Date now = new Date();
        if (start.after(now))
        {
            // 活动未开始
            result.put("msg", "活动未开始");
            result.put("status", 0);
            result.put("prize", prizeCode);
            return result;
        }
        if (end.before(now))
        {
            // 活动未开始
            result.put("msg", "活动已经结束");
            result.put("status", 0);
            result.put("prize", prizeCode);
            return result;
        }
        
        // 判断是否有机会抽奖
        Integer day = lottery.getLimitType() == 1 ? 0 : Integer.parseInt(DateTime.now().toString("yyyyMMdd"));
        Map<String, Object> actInfo = lotteryActivityDao.findAccountActInfoByUsernameAndLAIdAndDay(mobileNumber, lotteryId, day);
        if (actInfo == null)
        {
            // 插入用户抽奖信息记录
            Map<String, Object> info = addRelationLotteryActivityAndAccount(mobileNumber, lottery, 0, 0);
            if (info == null)
            {
                // 抽奖失败
                result.put("msg", "抽奖失败");
                result.put("status", 0);
                result.put("prize", prizeCode);
                return result;
            }
            actInfo = info;
        }
        // 剩余抽奖次数
        int leftCount = Integer.parseInt(actInfo.get("leftCount") == null ? "0" : actInfo.get("leftCount") + "");
        // 已使用抽奖次数
        int usedCount = Integer.parseInt(actInfo.get("usedCount") == null ? "0" : actInfo.get("usedCount") + "");
        if (leftCount > 0)
        {
            List<LotteryPrizeEntity> prizes = lotteryActivityDao.findLotteryPrizeByLotteryActivityId(lotteryId);
            if (!prizes.isEmpty())
            {
                List<LotteryPrizeEntity> _prizes = new ArrayList<>();
                for (LotteryPrizeEntity e : prizes)
                {
                    if (e.getType() == 1)
                    {
                        _prizes.add(e);
                    }
                    else if (e.getType() == 2 && e.getCouponId() == 812)
                    {
                        _prizes.add(e);
                    }
                    else if (e.getType() == 2 && e.getCouponId() == 811)
                    {
                        _prizes.add(e);
                    }
                }
                // 中奖逻辑
                double upperLimit = 0; // 当前商品的中奖几率的上线限（含）
                double floorLimit = 0; // 当前商品的中奖几率的下限（不含）
                double previousFloorLimit = 0; // 前一个商品的中奖几率的下限
                double cur = new Random().nextDouble();
                LotteryPrizeEntity prize = null;
                int sum = 0;
                for (LotteryPrizeEntity it : prizes)
                {
                    sum += it.getProbability();
                }
                
                for (LotteryPrizeEntity it : prizes)
                {
                    upperLimit = previousFloorLimit;
                    floorLimit = 1.0 * it.getProbability() / sum + upperLimit;
                    previousFloorLimit = floorLimit;
                    if (upperLimit <= cur && cur < floorLimit)
                    {
                        prize = it;
                        break;
                    }
                }
                // 抽中奖品啦~
                if (prize.getType() == 4)
                {
                    if (prize.getNum() <= 0)
                    {
                        prize = _prizes.get(new Random().nextInt(3));
                        logger.info("无商品库存，替换奖品为： " + prize.getId());
                    }
                }
                if (prize.getType() == LotteryPrizeTypeEnum.COUPON.ordinal())
                {
                    // 向手机号对应账户发放优惠券
                    int couponId = prize.getCouponId();
                    Map<String, Object> cMap = couponCodeDao.findCouponById(couponId);
                    if (cMap == null)
                    {
                        logger.warn("找不到优惠券信息，优惠券ID：" + couponId);
                        msg = "抽奖失败";
                        result.put("msg", msg);
                        result.put("status", 0);
                        result.put("prize", 5);
                        return result;
                    }
                    int couponDetailId = ((Long)cMap.get("coupon_detail_id")).intValue();
                    
                    int accountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
                    if (accountId == CommonConstant.ID_NOT_EXIST)
                    {
                        // 插入注册就送预优惠券表
                        if (prize.getValidityDaysType() == 1)
                        {
                            status = accountDao.addRegisterMobileCoupon(mobileNumber, couponId, 1, 0, CouponAccountSourceTypeEnum.LOTTERY.getCode(), 0);
                        }
                        else
                        {
                            status = accountDao.addRegisterMobileCoupon(mobileNumber, couponId, 2, prize.getDays(), CouponAccountSourceTypeEnum.LOTTERY.getCode(), 0);
                        }
                    }
                    else
                    {
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(couponDetailId);
                        cae.setCouponId(couponId);
                        cae.setRemark(cMap.get("remark").toString());
                        if (prize.getValidityDaysType() == 1)
                        {
                            cae.setStartTime(cMap.get("start_time").toString());
                            cae.setEndTime(cMap.get("end_time").toString());
                        }
                        else
                        {
                            cae.setStartTime(DateTime.now().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).toString("yyyy-MM-dd HH:mm:ss"));
                            cae.setEndTime(DateTime.now()
                                .plusDays(prize.getDays() - 1)
                                .withHourOfDay(23)
                                .withMinuteOfHour(59)
                                .withSecondOfMinute(59)
                                .toString("yyyy-MM-dd HH:mm:ss"));
                        }
                        cae.setSourceType((byte)3);
                        cae.setCouponCodeId(0);
                        status = couponAccountDao.addCouponAccount(cae);
                    }
                    status = 1;
                    if (prize.getCouponId() == 812)
                    {
                        prizeCode = 3;
                    }
                    else
                    {
                        prizeCode = 6;
                    }
                }
                else if (prize.getType() == LotteryPrizeTypeEnum.THANK.ordinal())
                {
                    status = 1;
                    prizeCode = 5;
                }
                else if (prize.getType() == LotteryPrizeTypeEnum.ITEM.ordinal())
                {
                    status = 1;
                    prizeCode = prize.getCouponId();//暂当Code用
                }
                
                if (status != 0)
                {
                    // 更新用户抽奖次数
                    Map<String, Object> para = new HashMap<>();
                    para.put("leftCount", leftCount - 1);
                    para.put("usedCount", usedCount + 1);
                    para.put("username", mobileNumber);
                    para.put("lotteryActivityId", lotteryId);
                    para.put("recordDay", day);
                    if (lotteryActivityDao.updateAccountActInfoByUsernameAndLAIdAndDay(para) == 0)
                    {
                        throw new ServiceException("更新用户抽奖次数失败！手机号：" + mobileNumber);
                    }
                    
                    // 减少商品件数
                    if (prize.getType() == 4)
                    {
                        if (lotteryActivityDao.reducePrizeNum(prize.getId(), prize.getNum()) == 0)
                        {
                            logger.info("更新奖品件数失败！！");
                        }
                        else
                        {
                            logger.info("更新奖品件数成功！prize: " + prize.getId());
                        }
                    }
                    
                    // 插入抽奖记录
                    if (lotteryActivityDao.addLotteryRecord(mobileNumber, lotteryId, prize.getId()) == 0)
                    {
                        logger.warn("插入抽奖记录失败！手机号: " + mobileNumber + ";活动ID: " + lotteryId + ";奖品: " + prize.getId());
                    }
                    else
                    {
                        logger.warn("插入抽奖记录成功！手机号: " + mobileNumber + ";活动ID: " + lotteryId + ";奖品: " + prize.getId());
                    }
                }
            }
        }
        else
        {
            prizeCode = 5;
            status = 0;
            msg = "哎呀！机会用光啦。";
        }
        result.put("status", status);
        result.put("prize", prizeCode);
        result.put("msg", msg);
        return result;
    }
    
    private Map<String, Object> addRelationLotteryActivityAndAccount(String mobileNumber, LotteryActivityEntity lottery, int usedCount, int increaseNum)
        throws ServiceException
    {
        // 插入用户抽奖信息记录
        Map<String, Object> addPara = new HashMap<>();
        addPara.put("username", mobileNumber);
        addPara.put("lotteryActivityId", lottery.getId());
        int leftCount = lottery.getLimitNum();
        if (increaseNum > 0)
        {
            leftCount = lottery.getLimitNum() + lottery.getShareNum();
        }
        addPara.put("leftCount", leftCount);
        addPara.put("usedCount", usedCount);
        addPara.put("increaseNum", increaseNum);
        Integer day = lottery.getLimitType() == 1 ? 0 : Integer.parseInt(DateTime.now().toString("yyyyMMdd"));
        addPara.put("recordDay", day);
        if (lotteryActivityDao.addRelationLotteryActivityAndAccount(addPara) == 0)
        {
            logger.warn("插入用户抽奖信息记录失败!");
            return null;
        }
        return addPara;
    }
    
    @Override
    public Map<String, Object> receiveRedFridayPrize(int accountId, String phoneNumber, String requestFrom)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if ("1".equals(requestFrom))
        {
            if (StringUtils.isEmpty(phoneNumber))
            {
                resultMap.put("status", "0");
                resultMap.put("msg", "手机号码不正确");
                return resultMap;
            }
            accountId = accountDao.findIdByNameAndType(phoneNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
            int couponId = 993;
            while (couponId < 996)
            {
                CouponEntity coupon = couponDao.findCouponById(couponId);
                if (accountId == CommonConstant.ID_NOT_EXIST)
                {
                    //插入注册就送预优惠券表
                    int id = activityDao.finActivityCouponPrize(phoneNumber, couponId, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode());
                    if (id == CommonConstant.ID_NOT_EXIST)
                    {
                        accountDao.addRegisterMobileCoupon(phoneNumber, couponId, 1, 0, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode(), 0);
                    }
                    else
                    {
                        resultMap.put("status", "2");
                        resultMap.put("msg", "已经领取过了");
                        return resultMap;
                    }
                }
                else
                {
                    CouponAccountEntity caInfo = couponAccountDao.findCouponInfoBycidAndType(accountId, couponId, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode());
                    if (caInfo == null)
                    {
                        //插入账户优惠券表
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(coupon.getCouponDetailId());
                        cae.setCouponId(coupon.getId());
                        cae.setRemark(coupon.getRemark());
                        cae.setStartTime(coupon.getStartTime());
                        cae.setEndTime(coupon.getEndTime());
                        cae.setSourceType((byte)(CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode()));
                        cae.setCouponCodeId(0);
                        cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
                        couponAccountDao.addCouponAccount(cae);
                    }
                    else
                    {
                        resultMap.put("status", "2");
                        resultMap.put("msg", "已经领取过了");
                        return resultMap;
                    }
                }
                couponId++;
            }
            
        }
        else if ("2".equals(requestFrom))
        {
            AccountEntity ae = accountDao.findAccountById(accountId);
            if (ae == null)
            {
                resultMap.put("status", "0");
                resultMap.put("msg", "用户不存在");
                return resultMap;
            }
            //插入账户优惠券表
            int couponId = 993;
            while (couponId < 996)
            {
                CouponAccountEntity caInfo = couponAccountDao.findCouponInfoBycidAndType(accountId, couponId, CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode());
                if (caInfo == null)
                {
                    CouponEntity coupon = couponDao.findCouponById(couponId);
                    CouponAccountEntity cae = new CouponAccountEntity();
                    cae.setAccountId(accountId);
                    cae.setCouponDetailId(coupon.getCouponDetailId());
                    cae.setCouponId(coupon.getId());
                    cae.setRemark(coupon.getRemark());
                    cae.setStartTime(coupon.getStartTime());
                    cae.setEndTime(coupon.getEndTime());
                    cae.setSourceType((byte)(CouponAccountSourceTypeEnum.DELICIOUS_FOOD_PARTY.getCode()));
                    cae.setCouponCodeId(0);
                    cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
                    couponAccountDao.addCouponAccount(cae);
                }
                else
                {
                    resultMap.put("status", "2");
                    resultMap.put("msg", "已经领取过了");
                    return resultMap;
                }
                couponId++;
            }
            
        }
        resultMap.put("status", "1");
        return resultMap;
    }
    
    @Override
    public boolean findGoddessPrizeRecordByAccountId(int accountId)
        throws ServiceException
    {
        
        List<CouponAccountEntity> caInfoList1 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_268_20, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        List<CouponAccountEntity> caInfoList2 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_399_30, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        List<CouponAccountEntity> caInfoList3 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_599_50, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        List<CouponAccountEntity> caInfoList4 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_3299_320, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        return (caInfoList1.size() + caInfoList2.size() + caInfoList3.size() + caInfoList4.size()) == 7;
    }
    
    @Override
    public String receiveGoddessPrize(int accountId, String phoneNumber, int requestFrom)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (requestFrom == 1)
        {
            if (!CommonUtil.isMobile(phoneNumber))
            {
                resultMap.put("status", "0");
                resultMap.put("msg", "手机号码不正确");
                return JSON.toJSONString(resultMap);
            }
            accountId = accountDao.findIdByNameAndType(phoneNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
            
            if (accountId == CommonConstant.ID_NOT_EXIST)
            {
                int total = 0;
                
                //满268减20优惠券:2张
                Map<String, Object> para = new HashMap<String, Object>();
                para.put("mobileNumber", phoneNumber);
                para.put("couponId", GoddessCouponId.COUPONID_268_20);
                para.put("sourceType", CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
                List<RegisterMobileCouponEntity> registerCouponList1 = accountDao.findRegisterMobileCoupon(para);
                for (int i = 0; i < 2 - registerCouponList1.size(); i++)
                {
                    accountDao.addRegisterMobileCoupon(phoneNumber, GoddessCouponId.COUPONID_268_20, 1, 0, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode(), 0);
                }
                total += registerCouponList1.size();
                
                //满399减30优惠券：2张
                para.put("couponId", GoddessCouponId.COUPONID_399_30);
                List<RegisterMobileCouponEntity> registerCouponList2 = accountDao.findRegisterMobileCoupon(para);
                for (int i = 0; i < 2 - registerCouponList2.size(); i++)
                {
                    accountDao.addRegisterMobileCoupon(phoneNumber, GoddessCouponId.COUPONID_399_30, 1, 0, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode(), 0);
                }
                total += registerCouponList2.size();
                
                //满599减50：2张
                para.put("couponId", GoddessCouponId.COUPONID_599_50);
                List<RegisterMobileCouponEntity> registerCouponList3 = accountDao.findRegisterMobileCoupon(para);
                for (int i = 0; i < 2 - registerCouponList3.size(); i++)
                {
                    accountDao.addRegisterMobileCoupon(phoneNumber, GoddessCouponId.COUPONID_599_50, 1, 0, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode(), 0);
                }
                total += registerCouponList3.size();
                
                //满3299减320优惠券：1张
                para.put("couponId", GoddessCouponId.COUPONID_599_50);
                List<RegisterMobileCouponEntity> registerCouponList4 = accountDao.findRegisterMobileCoupon(para);
                for (int i = 0; i < 1 - registerCouponList4.size(); i++)
                {
                    accountDao.addRegisterMobileCoupon(phoneNumber, GoddessCouponId.COUPONID_3299_320, 1, 0, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode(), 0);
                }
                total += registerCouponList4.size();
                
                if (total < 7)
                {
                    resultMap.put("status", "1");
                    resultMap.put("msg", "领取成功");
                    return JSON.toJSONString(resultMap);
                }
                else
                {
                    resultMap.put("status", "2");
                    resultMap.put("msg", "该手机号已经领取了");
                    return JSON.toJSONString(resultMap);
                }
            }
        }
        
        if (requestFrom == 2)
        {
            AccountEntity ae = accountDao.findAccountById(accountId);
            if (ae == null)
            {
                resultMap.put("status", "0");
                resultMap.put("msg", "用户不存在");
                return JSON.toJSONString(resultMap);
            }
        }
        
        int total = 0;
        
        //满286减20：2张
        CouponEntity coupon268_20 = couponDao.findCouponById(GoddessCouponId.COUPONID_268_20);
        List<CouponAccountEntity> caInfoList1 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_268_20, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        for (int i = 0; i < 2 - caInfoList1.size(); i++)
        {
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(accountId);
            cae.setCouponDetailId(coupon268_20.getCouponDetailId());
            cae.setCouponId(coupon268_20.getId());
            cae.setRemark(coupon268_20.getRemark());
            cae.setStartTime(coupon268_20.getStartTime());
            cae.setEndTime(coupon268_20.getEndTime());
            cae.setSourceType((byte)(CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode()));
            cae.setCouponCodeId(0);
            cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
            couponAccountDao.addCouponAccount(cae);
        }
        total += caInfoList1.size();
        
        //满399减30优惠券：2张
        CouponEntity coupon399_30 = couponDao.findCouponById(GoddessCouponId.COUPONID_399_30);
        List<CouponAccountEntity> caInfoList2 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_399_30, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        for (int i = 0; i < 2 - caInfoList2.size(); i++)
        {
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(accountId);
            cae.setCouponDetailId(coupon399_30.getCouponDetailId());
            cae.setCouponId(coupon399_30.getId());
            cae.setRemark(coupon399_30.getRemark());
            cae.setStartTime(coupon399_30.getStartTime());
            cae.setEndTime(coupon399_30.getEndTime());
            cae.setSourceType((byte)(CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode()));
            cae.setCouponCodeId(0);
            cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
            couponAccountDao.addCouponAccount(cae);
        }
        total += caInfoList2.size();
        
        //满599减50：2张
        CouponEntity coupon599_50 = couponDao.findCouponById(GoddessCouponId.COUPONID_599_50);
        List<CouponAccountEntity> caInfoList3 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_599_50, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        for (int i = 0; i < 2 - caInfoList3.size(); i++)
        {
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(accountId);
            cae.setCouponDetailId(coupon599_50.getCouponDetailId());
            cae.setCouponId(coupon599_50.getId());
            cae.setRemark(coupon599_50.getRemark());
            cae.setStartTime(coupon599_50.getStartTime());
            cae.setEndTime(coupon599_50.getEndTime());
            cae.setSourceType((byte)(CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode()));
            cae.setCouponCodeId(0);
            cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
            couponAccountDao.addCouponAccount(cae);
        }
        total += caInfoList3.size();
        
        //满3299减320：1张
        CouponEntity coupon3299_320 = couponDao.findCouponById(GoddessCouponId.COUPONID_3299_320);
        List<CouponAccountEntity> caInfoList4 =
            couponAccountDao.findAllCouponInfoBycidAndType(accountId, GoddessCouponId.COUPONID_3299_320, CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode());
        for (int i = 0; i < 1 - caInfoList4.size(); i++)
        {
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(accountId);
            cae.setCouponDetailId(coupon3299_320.getCouponDetailId());
            cae.setCouponId(coupon3299_320.getId());
            cae.setRemark(coupon3299_320.getRemark());
            cae.setStartTime(coupon3299_320.getStartTime());
            cae.setEndTime(coupon3299_320.getEndTime());
            cae.setSourceType((byte)(CouponAccountSourceTypeEnum.GODDESS_FESTIVAL.getCode()));
            cae.setCouponCodeId(0);
            cae.setReducePrice(0);// 随机优惠券该值才有效，普通优惠券该值为0
            couponAccountDao.addCouponAccount(cae);
        }
        total += caInfoList4.size();
        if (total < 7)
        {
            resultMap.put("status", "1");
            resultMap.put("msg", "领取成功");
            return JSON.toJSONString(resultMap);
        }
        else
        {
            resultMap.put("status", "2");
            resultMap.put("msg", "该帐号已经领取了");
            return JSON.toJSONString(resultMap);
        }
    }
    
    static class GoddessCouponId
    {
        /**满268减20优惠券2张*/
        static int COUPONID_268_20 = 1651;
        
        /**满399减30优惠券2张*/
        static int COUPONID_399_30 = 1652;
        
        /**满599减50优惠券2张*/
        static int COUPONID_599_50 = 1653;
        
        /**满3299减320优惠券1张*/
        static int COUPONID_3299_320 = 1654;
    }
    
    @Override
    public int updateCrazyFoodActivityLeftTimes(int accountId, int activityId)
        throws ServiceException
    {
        int leftTimes = 1;
        if (accountId == 0)
        {
            return leftTimes;
        }
        ActivityCrazyFoodRecordEntity record = activityDao.findCrazyFoodRecord(accountId, activityId);
        ActivityCrazyFoodEntity afe = activityDao.findActivityCrazyFoodById(activityId);
        if (record == null)
        {
            leftTimes += activityDao.countAccountBuyTimes(accountId, afe.getStartTime());
            record = new ActivityCrazyFoodRecordEntity();
            record.setActivityId(activityId);
            record.setUsername(accountId + "");
            record.setUsedTimes(0);
            record.setLeftTimes(leftTimes);
            activityDao.insertActivityCrazyFoodRecord(record);
        }
        else
        {
            if (record.getUsedTimes() >= afe.getLimitTimes())
            {
                //超过限制次数，则可用0次
                leftTimes = 0;
            }
            else
            {
                leftTimes = record.getLeftTimes() + activityDao.countAccountBuyTimes(accountId, record.getUpdateTime());
            }
            
            Map<String, Object> para = new HashMap<>();
            para.put("activityId", activityId);
            para.put("username", accountId + "");
            para.put("leftTimes", leftTimes);
            activityDao.updateActivityCrazyFoodRecord(para);
        }
        return leftTimes;
    }
    
    @Override
    public Map<String, Object> drawCrazyFood(int accountId, int activityId)
        throws ServiceException
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        AccountEntity account = accountDao.findAccountById(accountId);
        if (account == null)
        {
            resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.NOT_LOGIN.getValue());
            resultMap.put("msg", ActivityEnum.ActivityCrazyFoodStatusEnum.NOT_LOGIN.getMessage());
            return resultMap;
        }
        ActivityCrazyFoodEntity acfe = activityDao.findActivityCrazyFoodById(activityId);
        Date start = DateTimeUtil.string2Date(acfe.getStartTime());
        Date end = DateTimeUtil.string2Date(acfe.getEndTime());
        Date now = new Date();
        if (start.after(now))
        {
            resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.NOT_START.getValue());
            resultMap.put("msg", ActivityEnum.ActivityCrazyFoodStatusEnum.NOT_START.getMessage());
            return resultMap;
        }
        if (end.before(now))
        {
            resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.FINISHED.getValue());
            resultMap.put("msg", ActivityEnum.ActivityCrazyFoodStatusEnum.FINISHED.getMessage());
            return resultMap;
        }
        
        ActivityCrazyFoodEntity afe = activityDao.findActivityCrazyFoodById(activityId);
        ActivityCrazyFoodRecordEntity record = activityDao.findCrazyFoodRecord(accountId, activityId);
        if (record == null)
        {
            int leftTimes = activityDao.countAccountBuyTimes(accountId, afe.getStartTime()) + 1;
            record = new ActivityCrazyFoodRecordEntity();
            record.setActivityId(activityId);
            record.setUsername(accountId + "");
            record.setUsedTimes(0);
            record.setLeftTimes(leftTimes);
            
            ActivityCrazyFoodRecordEntity addRecord = activityDao.insertActivityCrazyFoodRecord(record);
            if (addRecord == null)
            {
                resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.SERVER_ERROR.getValue());
                resultMap.put("msg", ActivityEnum.ActivityCrazyFoodStatusEnum.SERVER_ERROR.getMessage());
                logger.info("幸运大转盘--插入用户抽奖记录失败！！！" + record.toString());
                return resultMap;
            }
            record = addRecord;
        }
        int leftTimes = record.getLeftTimes();
        int usedTimes = record.getUsedTimes();
        if (usedTimes == afe.getLimitTimes())
        {
            resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.OUT_OF_LIMIT.getValue());
            resultMap.put("msg", ActivityEnum.ActivityCrazyFoodStatusEnum.OUT_OF_LIMIT.getMessage());
            resultMap.put("leftTimes", leftTimes);
        }
        else
        {
            synchronized (account)
            {
                if (leftTimes > 0)
                {
                    ActivityCrazyFoodPrizeEntity prize = null;
                    int status = 0;
                    while (true)
                    {
                        List<ActivityCrazyFoodPrizeEntity> prizes = activityDao.findActivityCrazyFoodPrizeByActivityId(activityId);
                        // 抽奖逻辑
                        double floorLimit = 0; // 当前商品的中奖几率的下限（含）
                        double upperLimit = 0; // 当前商品的中奖几率的上限（不含）
                        double previousFloorLimit = 0; // 前一个商品的中奖几率的下限
                        double cur = new Random().nextDouble();
                        int sum = 0;
                        for (ActivityCrazyFoodPrizeEntity it : prizes)
                        {
                            sum += it.getProbability();
                        }
                        
                        for (ActivityCrazyFoodPrizeEntity it : prizes)
                        {
                            floorLimit = previousFloorLimit;
                            upperLimit = 1.0 * it.getProbability() / sum + floorLimit;
                            previousFloorLimit = upperLimit;
                            if (floorLimit <= cur && cur < upperLimit)
                            {
                                prize = it;
                                break;
                            }
                        }
                        
                        if (prize.getType() == ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_1.getValue())
                        {
                            //10元现金券
                            CouponEntity ce = couponDao.findCouponById(prize.getCouponId());
                            CouponAccountEntity cae = new CouponAccountEntity();
                            cae.setAccountId(accountId);
                            cae.setCouponDetailId(ce.getCouponDetailId());
                            cae.setCouponId(prize.getCouponId());
                            cae.setRemark(ce.getRemark());
                            cae.setStartTime(ce.getStartTime());
                            cae.setEndTime(ce.getEndTime());
                            cae.setSourceType((byte)CouponAccountSourceTypeEnum.LUCKY_DRAW.getCode());
                            cae.setCouponCodeId(0);
                            cae.setReducePrice(0);
                            status = couponAccountDao.addCouponAccount(cae);
                        }
                        else if (prize.getType() == ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_2.getValue())
                        {
                            //321积分
                            Map<String, Object> para = new HashMap<String, Object>();
                            para.put("accountId", accountId);
                            para.put("point", prize.getCouponId());
                            if (accountDao.updateIntegral(para) > 0)
                            {
                                para.clear();
                                para.put("accountId", accountId);
                                para.put("operatePoint", prize.getCouponId());
                                para.put("totalPoint", account.getAvailablePoint() + prize.getCouponId());
                                para.put("operateType", CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.ACTIVITIES_REWARD.getValue());
                                para.put("arithmeticType", prize.getCouponId() < 0 ? 2 : 1);
                                status = accountDao.insertIntegralRecord(para);
                            }
                        }
                        else if (prize.getType() == ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_3.getValue())
                        {
                            //1-50元随机现金券
                            CouponEntity coupon = couponDao.findCouponById(prize.getCouponId());
                            CouponDetailEntity cde = couponDetailDao.findCouponDetailById(coupon.getCouponDetailId());
                            
                            CouponAccountEntity cae = new CouponAccountEntity();
                            cae.setAccountId(accountId);
                            cae.setCouponDetailId(coupon.getCouponDetailId());
                            cae.setCouponId(coupon.getId());
                            cae.setRemark(coupon.getRemark());
                            cae.setStartTime(coupon.getStartTime());
                            cae.setEndTime(coupon.getEndTime());
                            cae.setSourceType((byte)CouponAccountSourceTypeEnum.LUCKY_DRAW.getCode());
                            cae.setCouponCodeId(0);
                            
                            int reduce = 0;
                            if (cde.getIsRandomReduce() == Byte.parseByte("1"))
                            {
                                int lowestReduce = cde.getLowestReduce();
                                int maximalReduce = cde.getMaximalReduce();
                                reduce = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
                            }
                            else
                            {
                                reduce = cde.getReduce();
                            }
                            cae.setReducePrice(reduce);
                            status = couponAccountDao.addCouponAccount(cae);
                            resultMap.put("reduce", reduce);
                        }
                        else if (prize.getType() == ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_4.getValue())
                        {
                            //左岸城堡购物袋(单品现金券)
                            if (prize.getPrizeAmount() > 0)
                            {
                                if (activityDao.updateActivityCrazyFoodAmount(prize) > 0)
                                {
                                    CouponEntity ce = couponDao.findCouponById(prize.getCouponId());
                                    CouponDetailEntity cde = couponDetailDao.findCouponDetailById(ce.getCouponDetailId());
                                    
                                    CouponAccountEntity cae = new CouponAccountEntity();
                                    cae.setAccountId(accountId);
                                    cae.setCouponDetailId(ce.getCouponDetailId());
                                    cae.setCouponId(prize.getCouponId());
                                    cae.setRemark(ce.getRemark());
                                    cae.setStartTime(ce.getStartTime());
                                    cae.setEndTime(ce.getEndTime());
                                    cae.setSourceType((byte)CouponAccountSourceTypeEnum.LUCKY_DRAW.getCode());
                                    cae.setCouponCodeId(0);
                                    cae.setReducePrice(0);
                                    status = couponAccountDao.addCouponAccount(cae);
                                    
                                    StringBuffer url = new StringBuffer("ggj://redirect/resource/");
                                    ProductEntity pe = productDao.findProductInfoById(cde.getScopeId());
                                    if (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.SALE.getValue()))
                                    {
                                        url.append("saleProduct/").append(cde.getScopeId());
                                    }
                                    else if (pe.getType() == Byte.parseByte(CommonEnum.PRODUCT_TYPE.MALL.getValue()))
                                    {
                                        url.append("mallProduct/").append(cde.getScopeId());
                                    }
                                    resultMap.put("url", url.toString());
                                }
                                else
                                {
                                    continue;
                                }
                            }
                            else
                            {
                                //数量抽完剔除奖品
                                Map<String, Object> para = new HashMap<>();
                                para.put("activityId", activityId);
                                para.put("type", ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_4.getValue());
                                para.put("isAvailable", 0);
                                para.put("probability", -prize.getProbability());
                                activityDao.updateActivityCrazyFoodPrize(para);
                                
                                //调整概率
                                para.clear();
                                para.put("activityId", activityId);
                                para.put("type", ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_5.getValue());
                                para.put("probability", prize.getProbability());
                                activityDao.updateActivityCrazyFoodPrize(para);
                                continue;
                            }
                        }
                        else if (prize.getType() == ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_5.getValue())
                        {
                            //满299-20优惠券
                            CouponEntity ce = couponDao.findCouponById(prize.getCouponId());
                            CouponAccountEntity cae = new CouponAccountEntity();
                            cae.setAccountId(accountId);
                            cae.setCouponDetailId(ce.getCouponDetailId());
                            cae.setCouponId(prize.getCouponId());
                            cae.setRemark(ce.getRemark());
                            cae.setStartTime(ce.getStartTime());
                            cae.setEndTime(ce.getEndTime());
                            cae.setSourceType((byte)CouponAccountSourceTypeEnum.LUCKY_DRAW.getCode());
                            cae.setCouponCodeId(0);
                            cae.setReducePrice(0);
                            status = couponAccountDao.addCouponAccount(cae);
                        }
                        else if (prize.getType() == ActivityEnum.ActivityCrazyFoodPrizeEnum.PRIZE_6.getValue())
                        {
                            //海蓝之谜传奇面霜(概率为0)
                        }
                        break;
                    }
                    
                    resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.WINE.getValue());
                    resultMap.put("prize", prize.getType());
                    resultMap.put("leftTimes", leftTimes - 1);
                    if (status == 0)
                    {
                        throw new ServiceException(String.format("幸运大转盘--用户%d抽奖出错", accountId));
                    }
                    Map<String, Object> para = new HashMap<String, Object>();
                    para.put("leftTimes", leftTimes - 1);
                    para.put("usedTimes", usedTimes + 1);
                    para.put("username", accountId);
                    para.put("activityId", activityId);
                    activityDao.updateActivityCrazyFoodRecord(para);
                }
                else
                {
                    resultMap.put("status", ActivityEnum.ActivityCrazyFoodStatusEnum.OUT_OF_TIMES.getValue());
                    resultMap.put("msg", ActivityEnum.ActivityCrazyFoodStatusEnum.OUT_OF_TIMES.getMessage());
                    resultMap.put("leftTimes", leftTimes);
                }
            }
        }
        return resultMap;
    }
    public List<Integer> findProductIdsById(int id)
            throws DaoException{
    	return activitiesCommonDao.findProductIdsById(id);
    }
}
