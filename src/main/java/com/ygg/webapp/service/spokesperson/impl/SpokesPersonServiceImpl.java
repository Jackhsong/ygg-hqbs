/**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.spokesperson.impl;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
 import com.ygg.common.services.image.ImgYunManager;
 import com.ygg.common.services.image.ImgYunServiceIF;
import com.ygg.common.utils.CommonConst;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.QRCodeDao;
import com.ygg.webapp.dao.account.HqbsAccountDao;
import com.ygg.webapp.dao.fans.HqbsFansDao;
import com.ygg.webapp.dao.fans.HqbsFansOrderDao;
import com.ygg.webapp.dao.reward.HqbsRewardDao;
import com.ygg.webapp.dao.reward.HqbsWithdrawalsLogDao;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.fans.QqbsFansEntity;
import com.ygg.webapp.entity.fans.QqbsFansOrderEntity;
import com.ygg.webapp.entity.fans.QqbsLiShiShuJu;
import com.ygg.webapp.entity.reward.QqbsRewardEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.sdk.weixin.util.QRCodeUtil;
import com.ygg.webapp.service.spokesperson.SpokesPersonService;
import com.ygg.webapp.util.CacheConstant;
import com.ygg.webapp.util.MathUtil;
import com.ygg.webapp.view.fans.QqbsFansOrderView;

/**
 * TODO 请在此处添加注释
 * 
 * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
 * @version $Id: SpokesPersonServiceImpl.java 12596 2016-05-23 07:51:39Z wuhuyun $
 * @since 2.0
 */
@Service("spokesPersonService")
public class SpokesPersonServiceImpl implements SpokesPersonService
{
    
    /**    */
    @Resource(name = "hqbsAccountDao")
    private HqbsAccountDao hqbsAccountDao;
    
    /**    */
    @Resource(name = "orderDao")
    private OrderDao orderDao;
    
    /**    */
    @Resource(name = "hqbsFansDao")
    private HqbsFansDao hqbsFansDao;
    
    /**    */
    @Resource(name = "hqbsRewardDao")
    private HqbsRewardDao hqbsRewardDao;
    
    /**    */
    @Resource(name = "hqbsWithdrawalsLogDao")
    private HqbsWithdrawalsLogDao hqbsWithdrawalsLogDao;
    
    /**    */
    @Resource(name = "hqbsFansOrderDao")
    private HqbsFansOrderDao hqbsFansOrderDao;
    
    @Resource
    private QRCodeDao qrCodeDao;
    
    @Resource(name = "memService")
    private CacheServiceIF cacheService;
    
    /** 照片上次方式 */
    private ImgYunServiceIF upyunImg = ImgYunManager.getClient(CommonConst.IMG_UPYUN);
    
    public CacheServiceIF getCacheService()
    {
        return cacheService;
    }
    
    public void setCacheService(CacheServiceIF cacheService)
    {
        this.cacheService = cacheService;
    }
    
    public ImgYunServiceIF getUpyunImg()
    {
        return upyunImg;
    }
    
    public void setUpyunImg(ImgYunServiceIF upyunImg)
    {
        this.upyunImg = upyunImg;
    }
    
    private static final String BACKGROUND_IMAGE_LOCATION = "scripts/images/qrcode.jpg";
    
    private static final String EXRIRED_DESCRIPTION_PREFIX = "此二维码有效期至";
    
    private static Logger log = Logger.getLogger(SpokesPersonServiceImpl.class);
    
    public QRCodeDao getQrCodeDao()
    {
        return qrCodeDao;
    }
    
    public void setQrCodeDao(QRCodeDao qrCodeDao)
    {
        this.qrCodeDao = qrCodeDao;
    }
    
    public OrderDao getOrderDao()
    {
        return orderDao;
    }
    
    public void setOrderDao(OrderDao orderDao)
    {
        this.orderDao = orderDao;
    }
    
    /**
     * TODO 请在此处添加注释
     * 
     * @return
     */
    public String getListInfo(QqbsAccountEntity account)
    {
        JsonObject result = new JsonObject();
        QqbsRewardEntity hr = hqbsRewardDao.getByAccountId(account.getAccountId());
        // 可提现金额
        float withdrawCash = 0;
        // 历史累计奖励
        float cumulativeReward = 0;
        // 已提现
        float alreadyCash = 0;
        if (hr != null)
        {
            withdrawCash = hr.getWithdrawCash();
            cumulativeReward = hr.getTotalReward();
            alreadyCash = hr.getAlreadyCash();
        }
        result.addProperty("withdrawCash", MathUtil.format_2(withdrawCash));
        result.addProperty("cumulativeReward", MathUtil.format_2(cumulativeReward));
        result.addProperty("alreadyCash", MathUtil.format_2(alreadyCash));
        
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        int fans = hqbsFansDao.getFansCount(para);
        result.addProperty("fans", fans);
        // 推荐人
        if (account.getFatherAccountId() == 0)
        {
            result.addProperty("recommendedPerson", "左岸城堡");
        }
        else
        {
            QqbsAccountEntity aa = hqbsAccountDao.findAccountByAccountId(account.getFatherAccountId());
            if (aa != null)
            {
                result.addProperty("recommendedPerson", aa.getNickName());
            }
            else
            {
                result.addProperty("recommendedPerson", "左岸城堡");
            }
        }
        // 是否是代言人 2016年4月15日 10点上线
        if (account.getSpokesPerson() == 1)
        {
            result.addProperty("spokesperson", 1);
        }
        else
        {
            boolean flag = getSpokesperson(account.getAccountId());
            if (flag)
            {
                result.addProperty("spokesperson", 1);
            }
            else
            {
                result.addProperty("spokesperson", 0);
            }
        }
        float fansOrderPrice = hqbsFansOrderDao.getFansOrderPrice(account.getAccountId());
        result.addProperty("fansOrderPrice", MathUtil.format_2(fansOrderPrice));
        
        return result.toString();
    }
    
    /**
     * 获取我的奖励相关数据
     * 
     * @param account
     * @return
     */
    public String getRewardInfo(QqbsAccountEntity account)
    {
        JsonObject result = new JsonObject();
        QqbsRewardEntity hr = hqbsRewardDao.getByAccountId(account.getAccountId());
        // 可提现金额
        float withdrawCash = 0;
        if (hr != null)
        {
            withdrawCash = hr.getWithdrawCash();
        }
        result.addProperty("withdrawCash", MathUtil.format_2(withdrawCash));
        // 是否是代言人 2016年4月15日 10点上线
        if (account.getSpokesPerson() == 1)
        {
            result.addProperty("spokesperson", 1);
        }
        else
        {
            boolean flag = getSpokesperson(account.getAccountId());
            if (flag)
            {
                result.addProperty("spokesperson", 1);
            }
            else
            {
                result.addProperty("spokesperson", 0);
            }
        }
        return result.toString();
    }
    
    public String getQRCode(QqbsAccountEntity account, String appPath)
        throws Exception
    {
        int accountId = account.getAccountId();
        String cacheKey = CacheConstant.HQBS_QRCODE_IMAGE_LINK_CACHE + accountId;
        String qrCodeImageLink = null;//cacheService.getCache(cacheKey);
        if (qrCodeImageLink != null)
        {
            return qrCodeImageLink;
        }
        
        if (account.getHasPersistentQRCode() == 1)
        {
            JsonObject json = initBuildParams(account, cacheKey, appPath);
            json.addProperty("qrCodeImage", qrCodeDao.findQRCodeByAccountId(accountId).getQrCodeUrl());
            return buildImageAndCache(json, cacheKey);
        }
        if (isSpokenPerson(account))
        {
            JsonObject json = initBuildParams(account, cacheKey, appPath);
            json.addProperty("qrCodeImage", QRCodeUtil.buildTemporaryQRCode(accountId));
            json.addProperty("expiredDate", EXRIRED_DESCRIPTION_PREFIX + calculatePlusOneMonthToToday());
            return buildImageAndCache(json, cacheKey);
        }
        return null;
    }
    
    private JsonObject initBuildParams(QqbsAccountEntity account, String cacheKey, String appPath)
        throws UnsupportedEncodingException
    {
        JsonObject json = new JsonObject();
        json.addProperty("backgroundImage", appPath + File.separator + BACKGROUND_IMAGE_LOCATION);
        json.addProperty("headIconImage", account.getImage());
        json.addProperty("upyunImgTarget", buildUpyunImgTarget(account));
        return json;
    }
    
    private String buildImageAndCache(JsonObject json, String cacheKey)
    {
        String imageLink = buildQRCodeImage(json);
        if (cacheKey != null)
        {
            cacheService.addCache(cacheKey, imageLink, CacheConstant.CACHE_DAY_1);
        }
        return imageLink;
    }
    
    private String calculatePlusOneMonthToToday()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1); // 得到前一个月
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }
    
    private String buildUpyunImgTarget(QqbsAccountEntity account)
        throws UnsupportedEncodingException
    {
        return "qqbsAccount/qrcode/" + account.getAccountId() + "/" + UUID.randomUUID() + ".jpg";
        
    }
    
    private boolean isSpokenPerson(QqbsAccountEntity account)
    {
        return true;//(account.getSpokesPerson() == 1) || getSpokesperson(account.getAccountId());
    }
    
    /**
     * 获取粉丝订单信息
     * 
     * @param account
     * @return
     */
    public String getFansOrderList(QqbsAccountEntity account)
    {
        JsonObject result = new JsonObject();
        QqbsRewardEntity hr = hqbsRewardDao.getByAccountId(account.getAccountId());
        // 累计奖励
        float totalReward = 0;
        if (hr != null)
        {
            totalReward = hr.getTotalReward();
        }
        result.addProperty("totalReward", MathUtil.format_2(totalReward));
        // 粉丝销售金额
        float fansOrderPrice = hqbsFansOrderDao.getFansOrderPrice(account.getAccountId());
        result.addProperty("fansOrderPrice", MathUtil.format_2(fansOrderPrice));
        return result.toString();
    }
    
    /**
     * 获取粉丝订单信息(分页)
     * 
     * @param account
     * @return
     */
    public List<QqbsFansOrderView> getFansOrderByPage(QqbsAccountEntity account, int page)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        para.put("start", 10 * (page - 1));
        para.put("size", 10);
        List<QqbsFansOrderView> fansOrderList = hqbsFansOrderDao.getFansOrderList(para);
        return fansOrderList;
    }
    
    /**
     * 根据条件查询粉丝订单列表(粉丝订单编号和粉丝ID)
     * 
     * @param account
     * @return
     */
    public List<QqbsFansOrderView> getFansOrderByNumOrId(QqbsAccountEntity account, String num)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        if (StringUtils.isNotBlank(num))
        {
            para.put("num", num);
        }
        return hqbsFansOrderDao.getFansOrderList(para);
    }
    
    /**
     * 获取粉丝订单信息
     * 
     * @param account
     * @return
     */
    public QqbsFansOrderView getFansOrder(QqbsAccountEntity account, int id)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        para.put("id", id);
        return hqbsFansOrderDao.getFansOrder(para);
    }
    
    /**
     * 获取粉丝临时使用
     * 
     * @param account
     * @return
     */
    public void updateFans()
    {
        List<QqbsAccountEntity> list = hqbsAccountDao.getFans(null);
        log.error("粉丝表处理-----中" + list.size());
        for (QqbsAccountEntity ae : list)
        {
            log.error("粉丝表处理-----中");
            Map<String, Object> fparam = new HashMap<String, Object>();
            fparam.put("fatherAccountId", ae.getAccountId());
            List<QqbsAccountEntity> flist = hqbsAccountDao.getFans(fparam);
            for (QqbsAccountEntity fqe : flist)
            {
                // 一级
                QqbsFansEntity fans = new QqbsFansEntity();
                fans.setAccountId(ae.getAccountId());
                fans.setFansAccountId(fqe.getAccountId());
                fans.setLevel(1);
                fans.setFansImage(fqe.getImage());
                fans.setFansNickname(fqe.getNickName());
                hqbsFansDao.insertFans(fans);
                // 二级
                Map<String, Object> sparam = new HashMap<String, Object>();
                sparam.put("fatherAccountId", fqe.getAccountId());
                List<QqbsAccountEntity> slist = hqbsAccountDao.getFans(sparam);
                for (QqbsAccountEntity sqe : slist)
                {
                    QqbsFansEntity sfans = new QqbsFansEntity();
                    sfans.setAccountId(ae.getAccountId());
                    sfans.setFansAccountId(sqe.getAccountId());
                    sfans.setLevel(2);
                    sfans.setFansImage(sqe.getImage());
                    sfans.setFansNickname(sqe.getNickName());
                    hqbsFansDao.insertFans(sfans);
                    // 三级
                    Map<String, Object> tparam = new HashMap<String, Object>();
                    tparam.put("fatherAccountId", sqe.getAccountId());
                    List<QqbsAccountEntity> tlist = hqbsAccountDao.getFans(tparam);
                    for (QqbsAccountEntity tqe : tlist)
                    {
                        QqbsFansEntity tfans = new QqbsFansEntity();
                        tfans.setAccountId(ae.getAccountId());
                        tfans.setFansAccountId(tqe.getAccountId());
                        tfans.setLevel(3);
                        tfans.setFansImage(tqe.getImage());
                        tfans.setFansNickname(tqe.getNickName());
                        hqbsFansDao.insertFans(tfans);
                    }
                }
            }
        }
    }
    
    /**
     * 粉丝订单列表临时使用
     * 
     * @param account
     * @return
     */
    public void updateFansListlinshi()
    {
        log.error("粉丝订单列表处理-----开始");
        List<QqbsFansOrderEntity> list = hqbsFansOrderDao.getOrderList();
        int i = 0;
        for (QqbsFansOrderEntity oe : list)
        {
            Map<String, Object> sparam = new HashMap<String, Object>();
            sparam.put("fansOrderId", oe.getFansOrderId());
            sparam.put("accountId", oe.getAccountId());
            sparam.put("fansAccountId", oe.getFansAccountId());
            sparam.put("level", oe.getLevel());
            QqbsFansOrderEntity db = hqbsFansOrderDao.getFansOrderByError(sparam);
            if (db == null)
            {
                QqbsFansOrderEntity qfo = new QqbsFansOrderEntity();
                float withdrawCash = 0;
                int level = oe.getLevel();
                float realPrice = oe.getRealPrice();
                if (level == 1)
                {
                    withdrawCash = (realPrice * 0.2f) * 0.5f;
                }
                else if (level == 2)
                {
                    withdrawCash = (realPrice * 0.2f) * 0.3f;
                }
                else
                {
                    withdrawCash = (realPrice * 0.2f) * 0.2f;
                }
                qfo.setWithdrawCash(withdrawCash);
                qfo.setFansOrderId(oe.getFansOrderId());
                qfo.setAccountId(oe.getAccountId());
                qfo.setFansAccountId(oe.getFansAccountId());
                qfo.setFansImage(oe.getFansImage());
                qfo.setFansNickname(oe.getFansNickname());
                qfo.setLevel(oe.getLevel());
                qfo.setNumber(oe.getNumber());
                qfo.setRealPrice(oe.getRealPrice());
                qfo.setStatus(1);
                qfo.setExStatus(0);
                qfo.setCreateTime(oe.getCreateTime());
                hqbsFansOrderDao.insertFans(qfo);
                i++;
            }
            else
            {
                log.error("数据库中已经存在----不处理");
            }
        }
        log.error("粉丝订单列表处理-----完成,共处理条数=" + i);
    }
    
    /**
     * 粉丝列表
     * 
     * @param account
     * @return
     */
    public List<QqbsFansEntity> getFansList(QqbsAccountEntity account, String nameOrId, int level, int page)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        if (level != 0)
        {
            para.put("level", level);
        }
        if (StringUtils.isNotBlank(nameOrId))
        {
            para.put("fansAccountId", nameOrId);
        }
        para.put("start", 10 * (page - 1));
        para.put("size", 10);
        
        return hqbsFansDao.getFansList(para);
    }
    
    /**
     * 粉丝列表
     * 
     * @param account
     * @param nameOrId
     * @param level "1,2,3"
     * @param page
     * @return
     */
    public List<QqbsFansEntity> getFansLists(QqbsAccountEntity account, String nameOrId, String level, int page)
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        if (StringUtils.isNotBlank(level))
        {
            List<Integer> levels = new ArrayList<Integer>();
            String[] sq = level.split(",");
            for (String s : sq)
            {
                levels.add(Integer.valueOf(s));
            }
            para.put("levels", levels);
        }
        if (StringUtils.isNotBlank(nameOrId))
        {
            para.put("fansAccountId", nameOrId);
        }
        para.put("start", 10 * (page - 1));
        para.put("size", 10);
        
        return hqbsFansDao.getFansLists(para);
    }
    
    /**
     * 获取历史数据
     * 
     * @param account
     * @return
     */
    public List<QqbsLiShiShuJu> getlishishuju()
    {
        List<QqbsLiShiShuJu> list = hqbsFansOrderDao.getQqbsLiShiShuJu();
        return list;
    }
    
    public void updatelishi(QqbsLiShiShuJu ls)
    {
        if (ls != null)
        {
            if (ls.getFatherAccountId() == 0)
            {
                log.error("accountId= " + ls.getAccountId() + "父id为0,不处理");
            }
            else
            {
                // 1.当前用户成为推荐人的1级粉丝
                QqbsAccountEntity one = hqbsAccountDao.findAccountByAccountId(ls.getAccountId());
                if (one != null)
                {
                    if (ls.getFatherAccountId() == one.getFatherAccountId())
                    {
                        // log.error("accountId=
                        // "+ls.getAccountId()+"的FatherAccountId="+ls.getFatherAccountId()+"和qqbs_account表中="+one.getFatherAccountId()+"相同,不处理");
                    }
                    else
                    {
                        if (one.getFatherAccountId() == 0)
                        {
                            log.error("accountIdxxxxxxx: " + ls.getAccountId());
                            // 插入QqbsAccountEntity
                            Map<String, Object> para1 = new HashMap<String, Object>();
                            para1.put("id", one.getId());
                            para1.put("fatherAccountId", ls.getFatherAccountId());
                            hqbsAccountDao.updateAccounSpread(para1);
                            
                            // 1.左岸城堡表中FatherAccountId=0，把历史数据插入qqbs_fans 里面处理订单
                            processFansInfo(one, ls.getFatherAccountId());
                        }
                        else
                        {
                            log.error("accountId= " + ls.getAccountId() + "的FatherAccountId=" + ls.getFatherAccountId() + "和qqbs_account表中FatherAccountId="
                                + one.getFatherAccountId() + "不相同,不处理");
                        }
                    }
                }
                else
                {
                    log.error("accountId= " + ls.getAccountId() + "在qqbs_account表中没有,不处理");
                }
            }
            // 更新处理过的数据 状态改成1
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("accountId", ls.getAccountId());
            hqbsFansOrderDao.updateSpread(para);
            
        }
    }
    
    /**
     * 处理粉丝关系
     * 
     * @param accountId 当前用户基本Id
     * @param userInfo 当前用户微信信息
     * @param eventMessage 包含推荐人Id
     * @throws Exception
     */
    private void processFansInfo(QqbsAccountEntity one, int fatherAccountId)
    {
        
        // 1.当前用户成为推荐人的1级粉丝
        QqbsAccountEntity oneFans = hqbsAccountDao.findAccountByAccountId(fatherAccountId);
        if (oneFans != null)
        {
            // a.订单
            Map<String, Object> para = new HashMap<String, Object>();
            para.put("accountId", one.getAccountId());
            List<QqbsFansOrderEntity> list = hqbsFansOrderDao.getOrderListSpread(para);
            // b.
            QqbsFansEntity qfe = new QqbsFansEntity();
            qfe.setAccountId(oneFans.getAccountId());
            qfe.setFansAccountId(one.getAccountId());
            qfe.setLevel(1);
            qfe.setFansImage(one.getImage());
            qfe.setFansNickname(one.getNickName());
            hqbsFansDao.insertFans(qfe);
            // c.处理订单
            processOrderInfo(list, 1, one, fatherAccountId);
            // 2.当前用户成为推荐人上级的2级粉丝
            // 取推荐人的用户信息信息
            if (oneFans.getFatherAccountId() != 0)
            {
                QqbsAccountEntity secondFans = hqbsAccountDao.findAccountByAccountId(oneFans.getFatherAccountId());
                if (secondFans != null)
                {
                    QqbsFansEntity efe = new QqbsFansEntity();
                    efe.setAccountId(secondFans.getAccountId());
                    efe.setFansAccountId(one.getAccountId());
                    efe.setLevel(2);
                    efe.setFansImage(one.getImage());
                    efe.setFansNickname(one.getNickName());
                    hqbsFansDao.insertFans(efe);
                    // 订单
                    processOrderInfo(list, 2, one, secondFans.getAccountId());
                    
                    // 3.当前用户成为推荐人上级 上级的3级粉丝
                    // 取推荐人上级的用户信息信息
                    if (secondFans.getFatherAccountId() != 0)
                    {
                        QqbsAccountEntity thirdFans = hqbsAccountDao.findAccountByAccountId(secondFans.getFatherAccountId());
                        if (thirdFans != null)
                        {
                            QqbsFansEntity tfe = new QqbsFansEntity();
                            tfe.setAccountId(thirdFans.getAccountId());
                            tfe.setFansAccountId(one.getAccountId());
                            tfe.setLevel(3);
                            tfe.setFansImage(one.getImage());
                            tfe.setFansNickname(one.getNickName());
                            hqbsFansDao.insertFans(tfe);
                            // 订单
                            processOrderInfo(list, 3, one, thirdFans.getAccountId());
                        }
                    }
                }
            }
        }
        else
        {
            log.error(" accountId= " + one.getAccountId() + " FatherAccountId=" + one.getFatherAccountId() + "在qqbs_account表中找不到");
        }
    }
    
    /**
     * 处理粉丝订单
     * 
     * @param list 粉丝订单
     * @param level 等级
     * @param one 粉丝对象
     * @param fatherAccountId
     */
    private void processOrderInfo(List<QqbsFansOrderEntity> list, int level, QqbsAccountEntity one, int fatherAccountId)
    {
        // c.处理订单
        if (list != null && list.size() > 0)
        {
            log.error("粉丝订单列表正在处理accountId=" + one.getAccountId() + "数量=" + list.size());
            for (QqbsFansOrderEntity oe : list)
            {
                QqbsFansOrderEntity qfo = new QqbsFansOrderEntity();
                float withdrawCash = 0;
                float realPrice = oe.getRealPrice();
                if (level == 1)
                {
                    withdrawCash = (realPrice * 0.2f) * 0.5f;
                }
                else if (level == 2)
                {
                    withdrawCash = (realPrice * 0.2f) * 0.3f;
                }
                else
                {
                    withdrawCash = (realPrice * 0.2f) * 0.2f;
                }
                qfo.setWithdrawCash(withdrawCash);
                qfo.setFansOrderId(oe.getFansOrderId());
                qfo.setAccountId(fatherAccountId);
                qfo.setFansAccountId(one.getAccountId());
                qfo.setFansImage(one.getImage());
                qfo.setFansNickname(one.getNickName());
                qfo.setLevel(level);
                qfo.setNumber(oe.getNumber());
                qfo.setRealPrice(oe.getRealPrice());
                qfo.setStatus(1);
                qfo.setExStatus(0);
                qfo.setCreateTime(oe.getCreateTime());
                hqbsFansOrderDao.insertFans(qfo);
            }
            log.error("粉丝订单列表处理完成");
        }
        else
        {
            log.error(one.getAccountId() + "没有符合要求订单234");
        }
    }
    
    /**
     * 判断是否是代言人
     * 
     * @param accountId
     * @return boolean
     */
    private boolean getSpokesperson(int accountId)
    {
        return isOneAmountOver50(accountId) || isTotalAmountOver10(accountId);
    }
    
    private boolean isOneAmountOver50(int accountId)
    {
        Map<String, Object> map = orderDao.getOneRealPriceByAid(accountId);
        return !CollectionUtils.isEmpty(map) && (double)map.get("realPrice") >= 50;
    }
    
    private boolean isTotalAmountOver10(int accountId)
    {
        return orderDao.findAllRealPriceByAid(accountId) >= 10;
    }
    
    /**
     * 获取粉丝数
     * 
     * @param account
     * @return
     */
    public String getFansCountByLevel(QqbsAccountEntity account)
    {
        JsonObject result = new JsonObject();
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        int count = hqbsFansDao.getFansCount(para);
        para.put("level", 1);
        int oneCount = hqbsFansDao.getFansCount(para);
        para.put("level", 2);
        int twoCount = hqbsFansDao.getFansCount(para);
        para.put("level", 3);
        int thCount = hqbsFansDao.getFansCount(para);
        result.addProperty("count", count);
        result.addProperty("oneCount", oneCount);
        result.addProperty("twoCount", twoCount);
        result.addProperty("thCount", thCount);
        return result.toString();
    }
    
    /**
     * 点击我的粉丝变更
     * 
     * @param account
     * @return
     */
    public String getFansCountByLevels(QqbsAccountEntity account)
    {
        JsonObject result = new JsonObject();
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("accountId", account.getAccountId());
        int count = hqbsFansDao.getFansCount(para);
        para.put("level", 1);
        int oneCount = hqbsFansDao.getFansCount(para);
        int twoThCount = count - oneCount;
        result.addProperty("count", count);
        result.addProperty("oneCount", oneCount);
        result.addProperty("twoThCount", twoThCount);
        return result.toString();
    }
    
    private String buildQRCodeImage(JsonObject json)
    {
        ByteArrayOutputStream baos = null;
        try
        {
            
            BufferedImage qrCodeImage = loadFromURLOrFile(json.get("qrCodeImage").getAsString());
            BufferedImage headIconImage = loadFromURLOrFile(json.get("headIconImage").getAsString());
            BufferedImage backgroundImage = loadFromURLOrFile(json.get("backgroundImage").getAsString());
            Graphics2D drawer = backgroundImage.createGraphics();
            drawer.drawImage(qrCodeImage, Math.round((backgroundImage.getWidth() - 210) / 2), 450, 210, 210, null);
            drawer.drawImage(headIconImage, Math.round((backgroundImage.getWidth() - 75) / 2), 1050, 75, 75, null);
            JsonElement jsonElement = json.get("expiredDate");
            if (jsonElement != null)
            {
//                drawer.setFont(new Font("微软雅黑", Font.PLAIN, 20));
//                drawer.drawString(jsonElement.getAsString(), 235, 620);
            }
            drawer.dispose();
            backgroundImage.flush();
            headIconImage.flush();
            qrCodeImage.flush();
            baos = new ByteArrayOutputStream();
            ImageIO.write(backgroundImage, "jpg", baos);
            String upyunImgTarget = json.get("upyunImgTarget").getAsString();
            Map<String, String> uploadResult = upyunImg.uploadFile(baos.toByteArray(), upyunImgTarget);
            if ("success".equals(uploadResult.get("status")))
            {
                return uploadResult.get("url");
            }
            throw new ServiceException("上传文件" + upyunImgTarget + "失败");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (baos != null)
                {
                    baos.close();
                }
                
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        throw new ServiceException("生成二维码 失败");
    }
    
    private BufferedImage loadFromURLOrFile(String urlOrFile)
        throws MalformedURLException, IOException
    {
        return isUrl(urlOrFile) ? ImageIO.read(new URL(urlOrFile)) : ImageIO.read(new File(urlOrFile));
    }
    
    private boolean isUrl(String urlOrFile)
    {
        return urlOrFile.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    }
}
