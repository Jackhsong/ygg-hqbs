package com.ygg.webapp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.code.ActivityTypeEnum;
import com.ygg.webapp.code.CouponAccountSourceTypeEnum;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.CouponAccountDao;
import com.ygg.webapp.dao.CouponCodeDao;
import com.ygg.webapp.dao.CouponDetailDao;
import com.ygg.webapp.dao.GameActivityDao;
import com.ygg.webapp.dao.GateActivityDao;
import com.ygg.webapp.dao.ShoppingCartDao;
import com.ygg.webapp.dao.SmsVerifyDao;
import com.ygg.webapp.dao.SpreadChannelDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.CouponAccountEntity;
import com.ygg.webapp.entity.SmsVerifyEntity;
import com.ygg.webapp.service.AccountService;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonProperties;
import com.ygg.webapp.util.CommonUtil;
import com.ygg.webapp.util.DateTimeUtil;
import com.ygg.webapp.util.StringUtil;
import com.ygg.webapp.view.AccountView;

@Service("accountService")
public class AccountServiceImpl implements AccountService
{
    
    Logger logger = Logger.getLogger(AccountServiceImpl.class);
    
    private int smsValidMillis = 10 * 60 * 1000;
    
    @Resource(name = "shoppingCartDao")
    private ShoppingCartDao shoppingCartDao;
    
    @Resource(name = "accountDao")
    private AccountDao adi = null;
    
    @Resource(name = "smsVerifyDao")
    private SmsVerifyDao svdi = null;
    
    @Resource(name = "couponCodeDao")
    private CouponCodeDao ccdi;
    
    @Resource(name = "couponAccountDao")
    private CouponAccountDao cadi;
    
    @Resource
    private GameActivityDao gameActivityDao;
    
    @Resource
    private SpreadChannelDao spreadChannelDao;
    
    @Resource
    private GateActivityDao gateActivityDao;
    
    @Resource
    private CouponDetailDao couponDetailDao;
    
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    @Override
    public String verificationCode(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String mobileNumber = param.get("mobileNumber").getAsString();
        int type = param.get("type").getAsInt();
        
        if (!CommonUtil.isMobile(mobileNumber))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.MOBILENUMBER_INVALID.getValue());
            return result.toString();
        }
        
        int id = adi.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (type == CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue())
        {
            if (id != CommonConstant.ID_NOT_EXIST)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.MOBILENUMBER_REPEAT.getValue());
                return result.toString();
            }
            SmsVerifyEntity sve = svdi.findSmsVerifyByMobile(mobileNumber, (byte)CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue());
            if (sve == null)
            {
                sve = new SmsVerifyEntity();
                sve.setMobileNumber(mobileNumber);
                String randomCode = CommonUtil.GenerateRandomCode(4);
                CommonUtil.sendSMS(new String[] {mobileNumber}, CommonConstant.getSendRegisterSMSContent(randomCode) + randomCode, 5);
                sve.setCode(randomCode);
                sve.setType((byte)CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue());
                sve.setValidTime((System.currentTimeMillis() + smsValidMillis) + "");
                sve.setCreateTime(System.currentTimeMillis() + "");
                svdi.addSmsVerify(sve);
            }
            else
            {
                Date lastRequestTime = CommonUtil.string2Date(sve.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
                Calendar curr = Calendar.getInstance();
                curr.add(Calendar.MINUTE, -1);
                if (lastRequestTime.after(curr.getTime())) // 1分钟内再次请求
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.REQUEST_REPEAT.getValue());
                    return result.toString();
                }
                updateSms(sve, mobileNumber, CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue());
            }
        }
        else if (type == CommonEnum.SMS_VERIFY_TYPE.RESET.getValue())
        {
            if (id == CommonConstant.ID_NOT_EXIST)
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue());
                return result.toString();
            }
            SmsVerifyEntity sve = svdi.findSmsVerifyByMobile(mobileNumber, (byte)CommonEnum.SMS_VERIFY_TYPE.RESET.getValue());
            if (sve == null)
            {
                sve = new SmsVerifyEntity();
                sve.setMobileNumber(mobileNumber);
                String randomCode = CommonUtil.GenerateRandomCode(4);
                CommonUtil.sendSMS(new String[] {mobileNumber}, CommonConstant.getSendResetSMSContent(randomCode) + randomCode, 5);
                sve.setCode(randomCode);
                sve.setType((byte)CommonEnum.SMS_VERIFY_TYPE.RESET.getValue());
                sve.setValidTime((System.currentTimeMillis() + smsValidMillis) + "");
                sve.setCreateTime(System.currentTimeMillis() + "");
                svdi.addSmsVerify(sve);
            }
            else
            {
                Date lastRequestTime = CommonUtil.string2Date(sve.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
                Calendar curr = Calendar.getInstance();
                curr.add(Calendar.MINUTE, -1);
                if (lastRequestTime.after(curr.getTime()))
                {
                    result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                    result.addProperty("errorCode", CommonEnum.ACCOUNT_VERIFICATION_ERRORCODE.REQUEST_REPEAT.getValue());
                    return result.toString();
                }
                updateSms(sve, mobileNumber, CommonEnum.SMS_VERIFY_TYPE.RESET.getValue());
            }
        }
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    private void updateSms(SmsVerifyEntity sve, String mobileNumber, int type)
        throws Exception
    {
        Date validTime = CommonUtil.string2Date(sve.getValidTime(), "yyyy-MM-dd HH:mm:ss");
        if (new Date().after(validTime) || sve.getIsUsed() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
        {
            String randomCode = CommonUtil.GenerateRandomCode(4);
            if (type == CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue())
            {
                CommonUtil.sendSMS(new String[] {mobileNumber}, CommonConstant.getSendRegisterSMSContent(randomCode), 5);
            }
            else
            {
                CommonUtil.sendSMS(new String[] {mobileNumber}, CommonConstant.getSendResetSMSContent(randomCode), 5);
            }
            sve.setCode(randomCode);
            sve.setIsUsed(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()));
            sve.setValidTime((System.currentTimeMillis() + smsValidMillis) + "");
            svdi.updateSmsVerifyById(sve);
        }
        else
        {
            if (type == CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue())
            {
                CommonUtil.sendSMS(new String[] {mobileNumber}, CommonConstant.getSendRegisterSMSContent(sve.getCode()), 5);
            }
            else
            {
                CommonUtil.sendSMS(new String[] {mobileNumber}, CommonConstant.getSendResetSMSContent(sve.getCode()), 5);
            }
            sve.setIsUsed(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()));
            sve.setValidTime((System.currentTimeMillis() + smsValidMillis) + "");
            svdi.updateSmsVerifyById(sve);
        }
    }
    
    @Override
    public AccountView register(String requestParams)
        throws Exception
    {
        // JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String mobileNumber = param.get("mobileNumber").getAsString();
        String verificationCode = param.get("verificationCode").getAsString();
        String password = param.get("password").getAsString();
        String recommendedCode = param.get("recommendedCode") == null ? null : param.get("recommendedCode").getAsString().toUpperCase();
        
        AccountView av = new AccountView();
        av.setStatus(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        if (!CommonUtil.isMobile(mobileNumber))
        {
            // result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            // result.addProperty("errorCode", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_INVALID.getValue());
            
            // av.setStatus(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            av.setErrorCode(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_INVALID.getErrorCode());
            
            return av;
        }
        
        int id = adi.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (id != CommonConstant.ID_NOT_EXIST)
        {
            // result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            // result.addProperty("errorCode", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_REPEAT.getValue());
            av.setErrorCode(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_REPEAT.getErrorCode());
            return av;
        }
        
        AccountEntity recommendedAe = null;
        if (recommendedCode != null && !recommendedCode.equals(""))
        {
            recommendedAe = adi.findAccountByRecommendedCode(recommendedCode);
            if (recommendedAe == null)
            {
                av.setErrorCode(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.RECOMMENDEDCODE_INVALID.getErrorCode());
                return av;
            }
        }
        else
        {
            // 从账号预推荐关系表中查询 是否存在该手机号
            int fatherAccountId = adi.findFatherAccountIdByMobileNumber(mobileNumber);
            if (fatherAccountId != -1)
            {
                recommendedAe = adi.findAccountById(fatherAccountId);
                if (recommendedAe == null)
                {
                    logger.warn("从账号预推荐关系表中查询到的accountId，没有查找到对应用户信息。");
                }
            }
        }
        
        SmsVerifyEntity sve = svdi.findSmsVerifyByMobile(mobileNumber, (byte)CommonEnum.SMS_VERIFY_TYPE.REGISTER.getValue());
        if (sve == null || sve.getIsUsed() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
        {
            // result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            // result.addProperty("errorCode", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getValue());
            av.setErrorCode(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode());
            return av;
        }
        else
        {
            Date validTime = CommonUtil.string2Date(sve.getValidTime(), "yyyy-MM-dd HH:mm:ss");
            if (new Date().after(validTime))
            {
                // av.setStatus(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                // av.setErrorCode( CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getValue() );
                
                av.setErrorCode(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode());
                
                return av;
            }
            if (!verificationCode.equals(sve.getCode()))
            {
                // result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                // result.addProperty("errorCode",
                // CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getValue());
                
                av.setErrorCode(CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode());
                return av;
            }
        }
        // 新增用户
        AccountEntity ae = new AccountEntity();
        ae.setMobileNumber(mobileNumber);
        ae.setName(mobileNumber);
        ae.setNickname(mobileNumber);
        ae.setPwd(password);
        ae.setType(Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        ae.setImage(CommonConstant.ACCOUNT_DEFAULT_HEADIMAGE);
        if (recommendedAe != null)
        {
            ae.setIsRecommended(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
        }
        else
        {
            ae.setIsRecommended(Byte.parseByte(CommonEnum.COMMON_IS.NO.getValue()));
        }
        
        while (true)
        {
            try
            {
                String currRecommendedCode = CommonUtil.getGenerateLetterWithNum(6);
                ae.setRecommendedCode(currRecommendedCode);
                id = adi.addAccount(ae);
                id = ae.getId();
                if (id == 0) // 注册失败
                {
                    this.logger.error("addAccount出错");
                    
                    av.setErrorCode(CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    return av;
                }
                else
                {
                    break;
                }
            }
            catch (Exception e)
            {
                String message = e.getMessage();
                if (!(message.contains("Duplicate entry") && message.contains("uniq_recommended_code")))
                {
                    logger.error("新增用户失败。", e);
                    av.setErrorCode(CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                    return av;
                }
            }
            
        }
        
        sve.setIsUsed(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
        sve.setValidTime(CommonUtil.string2Date(sve.getValidTime(), "yyyy-MM-dd HH:mm:ss").getTime() + "");
        svdi.updateSmsVerifyById(sve);
        
        av.setStatus(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        av.setMobileNumber(mobileNumber);
        av.setName(mobileNumber);
        av.setNickname(mobileNumber);
        av.setPwd(password);
        av.setType(Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        
        List<Map<String, Object>> couponInfos = adi.findRegisterCouponIdByName(mobileNumber);
        for (Map<String, Object> info : couponInfos)
        {
            int couponId = Integer.parseInt(info.get("couponId") == null ? "0" : info.get("couponId") + "");
            int validityDaysType = Integer.parseInt(info.get("validityDaysType") == null ? "0" : info.get("validityDaysType") + "");
            int days = Integer.parseInt(info.get("days") == null ? "0" : info.get("days") + "");
            int sourceType = Integer.parseInt(info.get("sourceType") == null ? "0" : info.get("sourceType") + "");
            int reducePrice = Integer.parseInt(info.get("reducePrice") == null ? "0" : info.get("reducePrice") + "");
            
            Map<String, Object> cMap = ccdi.findCouponById(couponId);
            if (cMap == null)
                break;
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(ae.getId());
            cae.setCouponDetailId(((Long)cMap.get("coupon_detail_id")).intValue());
            cae.setCouponId(couponId);
            cae.setRemark(cMap.get("remark").toString());
            if (validityDaysType == 1)
            {
                cae.setStartTime(cMap.get("start_time").toString());
                cae.setEndTime(cMap.get("end_time").toString());
            }
            else
            {
                cae.setStartTime(DateTimeUtil.now());
                cae.setEndTime(DateTime.now().plusDays(days).toString("yyyy-MM-dd HH:mm:ss"));
            }
            cae.setSourceType((byte)sourceType);
            cae.setCouponCodeId(0);
            
            // int couponDetailId = ((Long)cMap.get("coupon_detail_id")).intValue();
            // Object cdeCache = cacheService.getCache(CacheConstant.COUPON_DETAIL + couponDetailId);
            // CouponDetailEntity cde = null;
            // if (cdeCache != null)
            // {
            // cde = (CouponDetailEntity)cdeCache;
            // }
            // else
            // {
            // cde = couponDetailDao.findCouponDetailById(couponDetailId);
            // cacheService.addCache(CacheConstant.COUPON_DETAIL + couponDetailId, cde, CacheConstant.CACHE_DAY_1);
            // }
            //
            // if ((cde.getIsRandomReduce() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue())) && (reducePrice ==
            // 0))
            // {
            // // 是随机优惠码
            // int lowestReduce = cde.getLowestReduce();
            // int maximalReduce = cde.getMaximalReduce();
            // reducePrice = new Random().nextInt(maximalReduce - lowestReduce + 1) + lowestReduce;
            // }
            cae.setReducePrice(reducePrice);// 随机优惠码该值才有效，普通优惠券该值为0
            
            if (cadi.addCouponAccount(cae) == 0)
            {
                logger.warn("addCouponAccount失败,cae:" + cae);
            }
        }
        
        // 注册成功，检查是否是玩游戏带来的注册用户
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("mobileNumber", mobileNumber);
        para.put("sourceType", CouponAccountSourceTypeEnum.GAME.getCode());
        para.put("acitvityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_GAME.getCode());
        int gameId = gameActivityDao.findGameIdByNameFromRegisterCoupon(para);
        if (gameId != CommonConstant.ID_NOT_EXIST)
        {
            // 插入relation_activity_and_account表
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("activityId", gameId);
            map.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_GAME.getCode());
            map.put("accountId", ae.getId());
            gameActivityDao.addRelationActivityAndAccount(map);
            // 更新游戏带来新用户数量
            gameActivityDao.updateGameNewRegisterAmount(gameId);
        }
        
        // 注册成功，检查是否是通过渠道带来的新用户
        para.put("sourceType", CouponAccountSourceTypeEnum.SPREAD_CHANNEL.getCode());
        para.put("acitvityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_SPREADCHANNEL.getCode());
        int channelId = spreadChannelDao.findChannelIdByNameFromRegisterCoupon(para);
        if (channelId != CommonConstant.ID_NOT_EXIST)
        {
            // 插入relation_activity_and_account表
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("activityId", channelId);
            map.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_SPREADCHANNEL.getCode());
            map.put("accountId", ae.getId());
            spreadChannelDao.addRelationActivityAndAccount(map);
            // 更新渠道带来新用户数量
            spreadChannelDao.updateSpreadChannelNewRegisterAmount(channelId);
        }
        
        // 注册成功，检查是否是通过任意门带来的新用户
        para.put("sourceType", CouponAccountSourceTypeEnum.ANY_DOOR.getCode());
        para.put("acitvityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_ANYDOOR.getCode());
        int gateId = gateActivityDao.findGateIdByNameFromRegisterCoupon(para);
        if (gateId != CommonConstant.ID_NOT_EXIST)
        {
            // 插入relation_activity_and_account表
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("activityId", gateId);
            map.put("activityType", ActivityTypeEnum.ACTIVITY_TYPE_OF_ANYDOOR.getCode());
            map.put("accountId", ae.getId());
            gateActivityDao.addRelationActivityAndAccount(map);
            // 更新渠道带来新用户数量
            gateActivityDao.updateGateNewRegisterAmount(gateId);
        }
        if (adi.deleteRegisterCouponByName(mobileNumber) == 0)
        {
            logger.warn("deleteRegisterCouponByName失败,mobileNumber:" + mobileNumber);
        }
        
        // 邀请码逻辑
        if (recommendedAe != null)
        {
            recommendedAe.setRecommendedCount(recommendedAe.getRecommendedCount() + 1);
            if (adi.updateAccountById(recommendedAe) == 0)
            {
                logger.warn("updateAccountById没有匹配,mobileNumber:" + mobileNumber);
            }
            
            int fatherPartnerAccountId = adi.findFatherPartnerAccountIdById(recommendedAe.getId());
            if (fatherPartnerAccountId != CommonConstant.ID_NOT_EXIST)
            {
                AccountEntity fatherAe = adi.findAccountById(fatherPartnerAccountId);
                fatherAe.setSubRecommendedCount(fatherAe.getSubRecommendedCount() + 1);
                if (adi.updateAccountById(fatherAe) == 0)
                {
                    logger.warn("updateAccountById没有匹配,mobileNumber:" + mobileNumber);
                }
            }
            
            if (adi.addAccountRecommendRelation(id, recommendedAe.getId()) == 0)
            {
                logger.warn("addAccountRecommendRelation失败,mobileNumber:" + mobileNumber);
            }
            
            if (recommendedAe.getPartnerStatus() == CommonEnum.PARTNER_STATUS.IS_PARTNER.getValue())
            {
                if (adi.addAccountPartnerRelation(id, recommendedAe.getId()) == 0)
                {
                    logger.warn("addAccountPartnerRelation失败,mobileNumber:" + mobileNumber);
                }
            }
            else if (fatherPartnerAccountId != CommonConstant.ID_NOT_EXIST)
            {
                if (adi.addAccountPartnerRelation(id, fatherPartnerAccountId) == 0)
                {
                    logger.warn("addAccountPartnerRelation失败,mobileNumber:" + mobileNumber);
                }
            }
            
            Map<String, Object> cMap = ccdi.findCouponById(114);
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(id);
            cae.setCouponDetailId(((Long)cMap.get("coupon_detail_id")).intValue());
            cae.setCouponId(114);
            cae.setRemark(cMap.get("remark").toString());
            cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_SEND.getValue());
            cae.setCouponCodeId(0);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            cae.setStartTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
            c.add(Calendar.DAY_OF_YEAR, 6);
            cae.setEndTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
            if (cadi.addCouponAccount(cae) == 0)
            {
                logger.warn("addCouponAccount失败,cae:" + cae);
            }
        }
        //新人注册送88
        if (CommonProperties.isRegisterCoupon){
            Map<String, Object> cMap = ccdi.findCouponById(455);
            CouponAccountEntity cae = new CouponAccountEntity();
            cae.setAccountId(id);
            cae.setCouponDetailId(((Long)cMap.get("coupon_detail_id")).intValue());
            cae.setCouponId(455);
            cae.setRemark(cMap.get("remark").toString());
            cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_SEND.getValue());
            cae.setCouponCodeId(0);
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            cae.setStartTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
            c.add(Calendar.DAY_OF_YEAR, 1);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            cae.setEndTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
            cadi.addCouponAccount(cae);
            for (int i = 0; i < 2; i++){
                cMap = ccdi.findCouponById(456);
                cae = new CouponAccountEntity();
                cae.setAccountId(id);
                cae.setCouponDetailId(((Long)cMap.get("coupon_detail_id")).intValue());
                cae.setCouponId(456);
                cae.setRemark(cMap.get("remark").toString());
                cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_SEND.getValue());
                cae.setCouponCodeId(0);
                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                cae.setStartTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
                c.add(Calendar.DAY_OF_YEAR, 6);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                cae.setEndTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
                cadi.addCouponAccount(cae);
            }
            for (int i = 0; i < 3; i++){
                cMap = ccdi.findCouponById(457);
                cae = new CouponAccountEntity();
                cae.setAccountId(id);
                cae.setCouponDetailId(((Long)cMap.get("coupon_detail_id")).intValue());
                cae.setCouponId(457);
                cae.setRemark(cMap.get("remark").toString());
                cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_SEND.getValue());
                cae.setCouponCodeId(0);
                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                cae.setStartTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
                c.add(Calendar.DAY_OF_YEAR, 6);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                cae.setEndTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
                cadi.addCouponAccount(cae);
            }
            for (int i = 0; i < 2; i++){
                cMap = ccdi.findCouponById(458);
                cae = new CouponAccountEntity();
                cae.setAccountId(id);
                cae.setCouponDetailId(((Long)cMap.get("coupon_detail_id")).intValue());
                cae.setCouponId(458);
                cae.setRemark(cMap.get("remark").toString());
                cae.setSourceType((byte)CommonEnum.COUPON_SOURCE_TYPE.COUPON_SEND.getValue());
                cae.setCouponCodeId(0);
                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                cae.setStartTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
                c.add(Calendar.DAY_OF_YEAR, 13);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, 59);
                c.set(Calendar.SECOND, 59);
                cae.setEndTime(CommonUtil.date2String(c.getTime(), "yyyy-MM-dd HH:mm:ss"));
                cadi.addCouponAccount(cae);
            }
        }
        
        return av;
    }
    
    @Override
    public AccountView login(String requestParams)
        throws Exception
    {
        // JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String name = param.get("name").getAsString();
        String type = param.get("type").getAsString();
        String password = param.get("password").getAsString();
        String nickname = (param.get("nickname") == null ? "" : param.get("nickname").getAsString());
        String userImageUrl = param.get("userImageUrl") == null ? "" : param.get("userImageUrl").getAsString();
        AccountEntity ae = null;
        AccountView av = new AccountView();
        av.setStatus(CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
        
        int id = adi.findIdByNameAndType(name, Byte.parseByte(type));
        if (type.equals(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()))
        {
            if (id == CommonConstant.ID_NOT_EXIST)
            {
                av.setErrorCode(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue());
                return av;
            }
            ae = adi.findAccountById(id);
            if (!password.equals(ae.getPwd()))
            {
                av.setErrorCode(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.PASSWORD_INVALID.getValue());
                return av;
            }
        }
        else if (type.equals(CommonEnum.ACCOUNT_LOGIN_TYPE.QQ.getValue()) || type.equals(CommonEnum.ACCOUNT_LOGIN_TYPE.SINAWEIBO.getValue())
            || type.equals(CommonEnum.ACCOUNT_LOGIN_TYPE.ALIPAY.getValue()))
        {
            if (id == CommonConstant.ID_NOT_EXIST)
            {
                AccountEntity aet = new AccountEntity();
                aet.setName(name);
                aet.setPwd(CommonUtil.strToMD5(name + type));
                aet.setType(Byte.parseByte(type));
                aet.setNickname(CommonUtil.removeIllegalEmoji(nickname));
                aet.setMobileNumber("");
                if ("".equals(userImageUrl))
                {
                    aet.setImage(CommonConstant.ACCOUNT_DEFAULT_HEADIMAGE);
                }
                else
                {
                    aet.setImage(userImageUrl);
                }
                // 新增一条记录
                int accountResult = 0;
                while (true)
                {
                    try
                    {
                        String recommendedCode = StringUtil.getGenerateLetterWithNum(6);
                        aet.setRecommendedCode(recommendedCode);
                        accountResult = adi.addAccount(aet);
                        if (accountResult == 1) // 注册成功
                        {
                            break;
                        }
                        else
                        {
                            // TODO
                            av.setErrorCode(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue());
                            return av;
                        }
                    }
                    catch (Exception e)
                    {
                        logger.error(e.getMessage(), e);
                        String message = e.getMessage();
                        if (!(message.contains("Duplicate entry") && message.contains("uniq_recommended_code")))
                        {
                            // 修改之前这里也没有处理，故也直接抛出不做处理。
                            throw e;
                        }
                    }
                }
                id = aet.getId();
            }
            ae = adi.findAccountById(id);
        }
        else if (id == CommonConstant.ID_NOT_EXIST) // / 给一个提示出来
        {
            av.setErrorCode(CommonEnum.ACCOUNT_LOGIN_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue());
            return av;
        }
        
        if (id != CommonConstant.ID_NOT_EXIST && shoppingCartDao.findValidTimeByAid(id).isEmpty())
        {
            if (shoppingCartDao.addInvalidLockTime(id) == 0)
            {
                this.logger.warn("addInvalidLockTime失败,accountId:" + id);
            }
        }
        
        av.setStatus(CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        
        av.setId(ae.getId());
        av.setName(ae.getName());
        av.setMobileNumber(ae.getMobileNumber());
        av.setNickname(ae.getNickname());
        av.setPwd(ae.getPwd());
        av.setImage(ae.getImage());
        av.setType(Byte.parseByte(type));
        return av;
    }
    
    @Override
    public String resetPwd(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String mobileNumber = param.get("mobileNumber").getAsString();
        String verificationCode = param.get("verificationCode").getAsString();
        String password = param.get("password").getAsString();
        
        int id = adi.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (id == CommonConstant.ID_NOT_EXIST)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_RESET_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue());
            return result.toString();
        }
        
        SmsVerifyEntity sve = svdi.findSmsVerifyByMobile(mobileNumber, (byte)CommonEnum.SMS_VERIFY_TYPE.RESET.getValue());
        
        if (sve == null || sve.getIsUsed() == Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_RESET_ERRORCODE.VERIFICATION_INVALID.getValue());
            return result.toString();
        }
        else
        {
            Date validTime = CommonUtil.string2Date(sve.getValidTime(), "yyyy-MM-dd HH:mm:ss");
            if (new Date().after(validTime))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode());
                return result.toString();
            }
            if (!verificationCode.equals(sve.getCode()))
            {
                result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                result.addProperty("errorCode", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.VERIFICATION_INVALID.getErrorCode());
                return result.toString();
            }
        }
        sve.setIsUsed(Byte.parseByte(CommonEnum.COMMON_IS.YES.getValue()));
        sve.setValidTime(CommonUtil.string2Date(sve.getValidTime(), "yyyy-MM-dd HH:mm:ss").getTime() + "");
        svdi.updateSmsVerifyById(sve);
        
        AccountEntity ae = adi.findAccountById(id);
        ae.setPwd(password);
        adi.updateAccountById(ae);
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String modifyPwd(String requestParams)
        throws Exception
    {
        JsonObject result = new JsonObject();
        JsonParser parser = new JsonParser();
        
        JsonObject param = (JsonObject)parser.parse(requestParams);
        String mobileNumber = param.get("mobileNumber").getAsString();
        String oldPassword = param.get("oldPassword").getAsString();
        String newPassword = param.get("newPassword").getAsString();
        
        int id = adi.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (id == CommonConstant.ID_NOT_EXIST)
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_MODIFYPWD_ERRORCODE.MOBILENUMBER_NOT_EXIST.getValue());
            return result.toString();
        }
        AccountEntity ae = adi.findAccountById(id);
        if (!ae.getPwd().equals(oldPassword))
        {
            result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.addProperty("errorCode", CommonEnum.ACCOUNT_MODIFYPWD_ERRORCODE.OLD_PASSWORD_INVALID.getValue());
            return result.toString();
        }
        ae.setPwd(newPassword);
        adi.updateAccountById(ae);
        
        result.addProperty("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        return result.toString();
    }
    
    @Override
    public String getUserInfo(Integer id)
        throws Exception
    {
        JsonObject result = new JsonObject();
        AccountEntity account = adi.findAccountById(id);
        result.addProperty("来源", "手机网页");
        result.addProperty("用户ID", account.getId());
        return result.toString();
    }
    
    @Override
    public Map<String, Object> recommend(String mobileNumber, String code)
        throws Exception
    {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!CommonUtil.isMobile(mobileNumber))
        {
            result.put("status", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_INVALID.getErrorCode());
            result.put("msg", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_INVALID.getErrorMessage());
            return result;
        }
        
        int id = adi.findIdByNameAndType(mobileNumber, Byte.parseByte(CommonEnum.ACCOUNT_LOGIN_TYPE.MOBILE.getValue()));
        if (id != CommonConstant.ID_NOT_EXIST)
        {
            result.put("status", CommonEnum.ACCOUNT_REGISTER_ERRORCODE.MOBILENUMBER_REPEAT.getErrorCode());
            result.put("msg", "该手机号已注册，仅限新用户领取哦");
            return result;
        }
        
        int fatherAccountId = adi.findFatherAccountIdByMobileNumber(mobileNumber);
        if (fatherAccountId != -1)
        {
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
            result.put("msg", "领取成功~");
            return result;
        }
        // 插入关系
        AccountEntity ace = adi.findAccountByRecommendedCode(code);
        if (ace == null)
        {
            result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
            result.put("msg", "领取失败啦~ 请稍后再试");
            return result;
        }
        Map<String, Object> insertPara = new HashMap<String, Object>();
        insertPara.put("currMobileNumber", mobileNumber);
        insertPara.put("fatherAccountId", ace.getId());
        if (adi.insertAccountPrepRecommendRelation(insertPara) < 1)
        {
            result.put("status", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
            result.put("msg", "领取失败啦~ 请稍后再试");
            return result;
        }
        result.put("status", CommonEnum.BUSINESS_RESPONSE_STATUS.SUCCEED.getValue());
        result.put("msg", "领取成功~");
        return result;
    }
    
    @Override
    public boolean checkIfExistsRecommendedCode(String recommendedCode)
        throws Exception
    {
        AccountEntity ace = adi.findAccountByRecommendedCode(recommendedCode);
        if (ace == null)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public int countPrepRecommended(String recommendedCode, String begin, String end)
        throws Exception
    {
        AccountEntity ace = adi.findAccountByRecommendedCode(recommendedCode);
        if (ace == null)
        {
            return 0;
        }
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", ace.getId());
        para.put("begin", begin);
        para.put("end", end);
        int count = adi.countPrepRecommended(para);
        return count;
    }
    
    @Override
    public boolean existsAccount(int id)
        throws Exception
    {
        return adi.findAccountNameById(id) != null;
    }
    
    @Override
    public AccountEntity findAccountById(int id)
        throws Exception
    {
        return adi.findAccountById(id);
    }
    
    @Override
    public int findHuanXinInfoByName(String username)
        throws Exception
    {
        return adi.findHuanXinInfoByName(username);
    }
    
    @Override
    public int addHuanXinInfo(String username)
        throws Exception
    {
        return adi.addHuanXinInfo(username);
    }
}
