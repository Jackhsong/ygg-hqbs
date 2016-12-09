package com.ygg.webapp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.code.LotteryActivityDrawMessageEnum;
import com.ygg.webapp.code.LotteryPrizeTypeEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.LotteryActivityDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.CouponDetailEntity;
import com.ygg.webapp.entity.GiftActivityEntity;
import com.ygg.webapp.entity.GiftPrizeEntity;
import com.ygg.webapp.entity.LotteryActivityEntity;
import com.ygg.webapp.entity.LotteryPrizeEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.LotteryActivityService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.DateTimeUtil;

@Service("lotteryActivityService")
public class LotteryActivityServiceImpl implements LotteryActivityService
{
    Logger log = Logger.getLogger(LotteryActivityServiceImpl.class);
    
    @Resource
    private LotteryActivityDao lotteryActivityDao;
    
    @Resource
    private AccountDao accountDao;
    
    @Resource
    private CouponCodeDao couponCodeDao;
    
    @Resource
    private CouponAccountDao couponAccountDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Override
    public boolean existsLotteryActivity(int lotteryId)
        throws Exception
    {
        LotteryActivityEntity lae = lotteryActivityDao.findLotteryActivityById(lotteryId);
        if (lae == null)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public Map<String, Object> draw(int aId, String mobileNumber, int lotteryId)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        String msg = "";
        String text = "";// 客户显示文案
        int man = 0;
        int jian = 0;
        
        if ("".equals(mobileNumber))
        {
            mobileNumber = accountDao.findAccountNameById(aId);
            if (mobileNumber == null || "".equals(mobileNumber))
            {
                msg = LotteryActivityDrawMessageEnum.ERROR.getDescription();
                status = LotteryActivityDrawMessageEnum.ERROR.ordinal();
                result.put("msg", msg);
                result.put("status", status);
                return result;
            }
        }
        
        LotteryActivityEntity lottery = lotteryActivityDao.findLotteryActivityById(lotteryId);
        Date start = DateTimeUtil.string2Date(lottery.getStartTime());
        Date end = DateTimeUtil.string2Date(lottery.getEndTime());
        Date now = new Date();
        if (start.after(now))
        {
            // 活动未开始
            msg = LotteryActivityDrawMessageEnum.NOT_STARTED.getDescription();
            status = LotteryActivityDrawMessageEnum.NOT_STARTED.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        if (end.before(now))
        {
            // 活动已结束
            msg = LotteryActivityDrawMessageEnum.COMPLETE.getDescription();
            status = LotteryActivityDrawMessageEnum.COMPLETE.ordinal();
            result.put("msg", msg);
            result.put("status", status);
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
                msg = LotteryActivityDrawMessageEnum.ERROR.getDescription();
                status = LotteryActivityDrawMessageEnum.ERROR.ordinal();
                result.put("msg", msg);
                result.put("status", status);
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
            if (prizes.size() > 0)
            {
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
                if (prize.getType() == LotteryPrizeTypeEnum.COUPON.ordinal())
                {
                    // 向手机号对应账户发放优惠券
                    int couponId = prize.getCouponId();
                    Map<String, Object> cMap = couponCodeDao.findCouponById(couponId);
                    if (cMap == null)
                    {
                        log.warn("找不到优惠券信息，优惠券ID：" + couponId);
                        msg = LotteryActivityDrawMessageEnum.ERROR.getDescription();
                        status = LotteryActivityDrawMessageEnum.ERROR.ordinal();
                        result.put("msg", msg);
                        result.put("status", status);
                        return result;
                    }
                    int couponDetailId = ((Long)cMap.get("coupon_detail_id")).intValue();
                    
                    int accountId = -1;
                    if (aId > 0)
                    {
                        accountId = aId;
                    }
                    else
                    {
                        accountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
                    }
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
                    if (status != 0)
                    {
                        // 更新用户抽奖次数
                        Map<String, Object> para = new HashMap<String, Object>();
                        para.put("leftCount", leftCount - 1);
                        para.put("usedCount", usedCount + 1);
                        para.put("username", mobileNumber);
                        para.put("lotteryActivityId", lotteryId);
                        para.put("recordDay", day);
                        if (lotteryActivityDao.updateAccountActInfoByUsernameAndLAIdAndDay(para) == 0)
                        {
                            throw new ServiceException("更新用户抽奖次数失败！手机号：" + mobileNumber);
                        }
                        
                        // 插入抽奖记录
                        if (lotteryActivityDao.addLotteryRecord(mobileNumber, lotteryId, prize.getId()) == 0)
                        {
                            log.warn("插入抽奖记录失败！手机号: " + mobileNumber + ";活动ID: " + lotteryId + ";奖品: " + prize.getId());
                        }
                        // 获取网页交互数据
                        Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + couponDetailId);
                        CouponDetailEntity cde = null;
                        if (cdeCache != null)
                        {
                            cde = (CouponDetailEntity)cdeCache;
                        }
                        else
                        {
                            cde = couponDetailDao.findCouponDetailById(couponDetailId);
                            cacheService.addCache(CacheConstant.COUPON_DETAIL + couponDetailId, cde, CacheConstant.CACHE_DAY_1);
                        }
                        man = cde.getThreshold();
                        jian = cde.getReduce();
                        // 生成提示语句，非逻辑性代码
                        text = getLotteryResultText(cde);
                        result.put("text", text);
                        result.put("man", man);
                        result.put("jian", jian);
                    }
                }
                else if (prize.getType() == LotteryPrizeTypeEnum.INTEGRAL.ordinal())
                {
                    // TODO
                }
                else if (prize.getType() == LotteryPrizeTypeEnum.THANK.ordinal())
                {
                    // TODO
                }
                else
                {
                    // TODO
                }
                result.put("type", prize.getType());
                result.put("nums", leftCount - 1);
            }
            status = status > 1 ? 1 : status;
            msg = LotteryActivityDrawMessageEnum.getEnumByOrdinal(status).getDescription();
        }
        else
        {
            msg = "哎呀！机会用光啦。进入APP额外增加两次机会哟~";
            status = LotteryActivityDrawMessageEnum.NO_CHANGES.ordinal();
        }
        result.put("msg", msg);
        result.put("status", status);
        return result;
    }
    
    private String getLotteryResultText(CouponDetailEntity cde)
    {
        String text = "";
        int threshold = cde.getThreshold();
        int reduce = cde.getReduce();
        if (threshold == reduce)
        {
            text = "哈哈哈！终于可以白吃白喝一回了！";
        }
        else if (reduce == 1)
        {
            text = "我去，史上最大的优惠券产生了！";
        }
        else if (threshold >= 10000)
        {
            text = "哎呀！1秒变土豪了！";
        }
        else
        {
            text = "抢到一张不错的优惠券，买吃的去！";
        }
        return text;
    }
    
    @Override
    public int chance(int lotteryId, int accountId)
        throws Exception
    {
        int nums = 0;
        LotteryActivityEntity lae = lotteryActivityDao.findLotteryActivityById(lotteryId);
        Integer day = lae.getLimitType() == 1 ? 0 : Integer.parseInt(DateTime.now().toString("yyyyMMdd"));
        Map<String, Object> result = lotteryActivityDao.findAccountActInfoByAccountIdAndLAIdAndDay(accountId, lotteryId, day);
        if (result != null)
        {
            nums = Integer.parseInt(result.get("leftCount") == null ? "0" : result.get("leftCount") + "");
        }
        else
        {
            AccountEntity ae = accountDao.findAccountById(accountId);
            if (ae != null && lae != null)
            {
                Map<String, Object> info = addRelationLotteryActivityAndAccount(ae.getName(), lae, 0, 0);
                if (info != null)
                {
                    nums = lae.getLimitNum();
                }
            }
        }
        
        return nums;
    }
    
    private Map<String, Object> addRelationLotteryActivityAndAccount(String mobileNumber, LotteryActivityEntity lottery, int usedCount, int increaseNum)
        throws Exception
    {
        // 插入用户抽奖信息记录
        Map<String, Object> addPara = new HashMap<String, Object>();
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
            log.warn("插入用户抽奖信息记录失败!");
            return null;
        }
        return addPara;
    }
    
    @Override
    public Map<String, Object> shareActivity(int accountId, int lotteryId)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        String msg = "";
        LotteryActivityEntity lae = lotteryActivityDao.findLotteryActivityById(lotteryId);
        Integer day = lae.getLimitType() == 1 ? 0 : Integer.parseInt(DateTime.now().toString("yyyyMMdd"));
        AccountEntity ae = accountDao.findAccountById(accountId);
        if (lae == null || ae == null)
        {
            // 失败
            msg = "活动或用户不存在";
        }
        else
        {
            Date start = DateTimeUtil.string2Date(lae.getStartTime());
            Date end = DateTimeUtil.string2Date(lae.getEndTime());
            Date now = new Date();
            
            if (start.after(now))
            {
                // 活动未开始
                msg = "活动未开始哦~";
            }
            else if (end.before(now))
            {
                // 活动未开始
                msg = "活动已经结束啦~";
            }
            else
            {
                Map<String, Object> lotteryinfo = lotteryActivityDao.findAccountActInfoByAccountIdAndLAIdAndDay(accountId, lotteryId, day);
                if (lotteryinfo == null)
                {
                    // 该用户还没有抽奖信息 - 插入
                    Map<String, Object> info = addRelationLotteryActivityAndAccount(ae.getName(), lae, 0, 1);
                    if (info != null)
                    {
                        // 成功
                        status = 1;
                        msg = "成功";
                    }
                }
                else
                {
                    int increaseNum = Integer.parseInt(lotteryinfo.get("increaseNum") + "");
                    int leftCount = Integer.parseInt(lotteryinfo.get("leftCount") + "");
                    if (increaseNum < lae.getLimitShareNum())
                    {
                        leftCount += lae.getShareNum();
                        increaseNum += 1;
                        Map<String, Object> para = new HashMap<String, Object>();
                        para.put("leftCount", leftCount);
                        para.put("username", ae.getName());
                        para.put("increaseNum", increaseNum);
                        para.put("lotteryActivityId", lotteryId);
                        para.put("recordDay", day);
                        if (lotteryActivityDao.updateAccountActInfoByUsernameAndLAIdAndDay(para) > 0)
                        {
                            // 成功
                            status = 1;
                            msg = "成功";
                        }
                    }
                    else
                    {
                        // 分享次数已用完
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
    public Map<String, Object> giftAppShare(int accountId, int giftActivityId)
        throws Exception
    {
        // 分享获取礼物
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        String msg = "";
        AccountEntity ae = null;
        if (accountId != 0)
        {
            ae = accountDao.findAccountById(accountId);
        }
        if (ae == null)
        {
            // 未登录没有礼物
            msg = "用户不存在";
            result.put("status", status);
            result.put("msg", msg);
            return result;
        }
        GiftActivityEntity gift = lotteryActivityDao.findGiftActivityById(giftActivityId);
        if (gift == null)
        {
            msg = "活动不存在";
            result.put("status", status);
            result.put("msg", msg);
            return result;
        }
        Date start = DateTimeUtil.string2Date(gift.getStartTime());
        Date end = DateTimeUtil.string2Date(gift.getEndTime());
        Date now = new Date();
        if (start.after(now))
        {
            // 活动未开始
            msg = "活动未开始哦~";
        }
        else if (end.before(now))
        {
            // 活动未开始
            msg = "活动已经结束啦~";
        }
        else
        {
            // 查询已分享记录
            Map<String, Object> recordPara = new HashMap<String, Object>();
            recordPara.put("username", ae.getName());
            recordPara.put("giftActivityId", gift.getId());
            recordPara.put("type", 1);
            int recordCount = lotteryActivityDao.countGiftRecordByPara(recordPara);
            if (recordCount >= gift.getLimitNum())
            {
                msg = "没有机会啦";
                result.put("status", status);
                result.put("msg", msg);
                return result;
            }
            // 查找奖品并发放
            List<GiftPrizeEntity> prizes = lotteryActivityDao.findGiftPrizeByGiftActivityIdAndDrawWay(gift.getId(), 1);
            for (GiftPrizeEntity p : prizes)
            {
                int couponId = p.getCouponId();
                Map<String, Object> cMap = couponCodeDao.findCouponById(couponId);
                if (cMap != null)
                {
                    int num = p.getNum();
                    for (int i = 1; i <= num; i++)
                    {
                        int couponDetailId = ((Long)cMap.get("coupon_detail_id")).intValue();
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(couponDetailId);
                        cae.setCouponId(couponId);
                        cae.setEndTime(cMap.get("end_time").toString());
                        cae.setRemark(cMap.get("remark").toString());
                        cae.setStartTime(cMap.get("start_time").toString());
                        cae.setSourceType((byte)CouponAccountSourceTypeEnum.GIFT.getCode());
                        cae.setCouponCodeId(0);
                        if (couponAccountDao.addCouponAccount(cae) != 1)
                        {
                            log.warn("奖品发放，插入用户优惠券失败！");
                        }
                    }
                }
            }
            // 插入记录
            status = lotteryActivityDao.addGiftRecord(ae.getName(), giftActivityId, 1);
        }
        result.put("status", status);
        result.put("msg", msg);
        return result;
    }
    
    @Override
    public Map<String, Object> giftDrew(String mobileNumber, int giftActivityId)
        throws Exception
    {
        // 发券
        Map<String, Object> result = new HashMap<String, Object>();
        int status = 0;
        String msg = "";
        
        GiftActivityEntity gift = lotteryActivityDao.findGiftActivityById(giftActivityId);
        Date start = DateTimeUtil.string2Date(gift.getStartTime());
        Date end = DateTimeUtil.string2Date(gift.getEndTime());
        Date now = new Date();
        if (start.after(now))
        {
            // 活动未开始
            msg = LotteryActivityDrawMessageEnum.NOT_STARTED.getDescription();
            status = LotteryActivityDrawMessageEnum.NOT_STARTED.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        if (end.before(now))
        {
            // 活动已结束
            msg = LotteryActivityDrawMessageEnum.COMPLETE.getDescription();
            status = LotteryActivityDrawMessageEnum.COMPLETE.ordinal();
            result.put("msg", msg);
            result.put("status", status);
            return result;
        }
        // 查询已领取记录
        Map<String, Object> recordPara = new HashMap<String, Object>();
        recordPara.put("username", mobileNumber);
        recordPara.put("giftActivityId", gift.getId());
        recordPara.put("type", 2);
        int recordCount = lotteryActivityDao.countGiftRecordByPara(recordPara);
        if (recordCount >= gift.getLimitNum())
        {
            msg = "咦~你好像已经领过了呢，快登陆左岸城堡APP看看";
            status = LotteryActivityDrawMessageEnum.NO_CHANGES.ordinal();
            result.put("status", status);
            result.put("msg", msg);
            return result;
        }
        // 查找奖品并发放
        List<GiftPrizeEntity> prizes = lotteryActivityDao.findGiftPrizeByGiftActivityIdAndDrawWay(gift.getId(), 2);
        int accountId = accountDao.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (accountId == CommonConstant.ID_NOT_EXIST)
        {
            // 插入注册就送预优惠券表
            for (GiftPrizeEntity p : prizes)
            {
                int couponId = p.getCouponId();
                Map<String, Object> cMap = couponCodeDao.findCouponById(couponId);
                if (cMap != null)
                {
                    int num = p.getNum();
                    for (int i = 1; i <= num; i++)
                    {
                        // 插入注册就送预优惠券表
                        status = accountDao.addRegisterMobileCoupon(mobileNumber, couponId, 1, 0, CouponAccountSourceTypeEnum.GIFT.getCode(), 0);
                    }
                }
            }
        }
        else
        {
            for (GiftPrizeEntity p : prizes)
            {
                int couponId = p.getCouponId();
                Map<String, Object> cMap = couponCodeDao.findCouponById(couponId);
                if (cMap != null)
                {
                    int num = p.getNum();
                    for (int i = 1; i <= num; i++)
                    {
                        int couponDetailId = ((Long)cMap.get("coupon_detail_id")).intValue();
                        CouponAccountEntity cae = new CouponAccountEntity();
                        cae.setAccountId(accountId);
                        cae.setCouponDetailId(couponDetailId);
                        cae.setCouponId(couponId);
                        cae.setEndTime(cMap.get("end_time").toString());
                        cae.setRemark(cMap.get("remark").toString());
                        cae.setStartTime(cMap.get("start_time").toString());
                        cae.setSourceType((byte)CouponAccountSourceTypeEnum.GIFT.getCode());
                        cae.setCouponCodeId(0);
                        if (couponAccountDao.addCouponAccount(cae) != 1)
                        {
                            log.warn("奖品发放，插入用户优惠券失败！");
                        }
                    }
                }
            }
        }
        // 插入记录
        status = lotteryActivityDao.addGiftRecord(mobileNumber, giftActivityId, 2);
        
        result.put("msg", msg);
        result.put("status", status);
        return result;
    }
    
    @Override
    public boolean existsGiftActivity(int giftId)
        throws Exception
    {
        GiftActivityEntity gift = lotteryActivityDao.findGiftActivityById(giftId);
        if (gift == null)
        {
            return false;
        }
        return true;
    }
    
}
