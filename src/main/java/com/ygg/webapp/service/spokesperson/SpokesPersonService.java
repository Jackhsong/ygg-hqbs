/**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.spokesperson;

import java.util.List;

import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.fans.QqbsFansEntity;
import com.ygg.webapp.entity.fans.QqbsLiShiShuJu;
import com.ygg.webapp.view.fans.QqbsFansOrderView;

/**
 * TODO 请在此处添加注释
 * 
 * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
 * @version $Id: SpokesPersonService.java 12586 2016-05-23 07:16:23Z qiuyibo $
 * @since 2.0
 */
public interface SpokesPersonService
{
    
    /**
     * 代言人信息
     * 
     * @param account
     * @return
     */
    public String getListInfo(QqbsAccountEntity account);
    
    /**
     * 获取我的奖励相关数据
     * 
     * @param account
     * @return
     */
    public String getRewardInfo(QqbsAccountEntity account);
    
    /**
     * 获取二维码
     * 
     * @param account
     * @param appPath
     * @return
     * @throws Exception
     */
    public String getQRCode(QqbsAccountEntity account, String appPath)
        throws Exception;
    
    /**
     * 获取粉丝订单信息
     * 
     * @param account
     * @return
     */
    public String getFansOrderList(QqbsAccountEntity account);
    
    /**
     * 获取粉丝订单信息(分页)
     * 
     * @param account
     * @return
     */
    public List<QqbsFansOrderView> getFansOrderByPage(QqbsAccountEntity account, int page);
    
    /**
     * 获取粉丝临时使用
     * 
     * @param account
     * @return
     */
    public void updateFans();
    
    /**
     * 粉丝订单列表临时使用
     * 
     * @param account
     * @return
     */
    public void updateFansListlinshi();
    
    public List<QqbsFansEntity> getFansList(QqbsAccountEntity account, String nameOrId, int level, int page);
    
    /**
     * 粉丝列表
     * 
     * @param account
     * @param nameOrId
     * @param level "1,2,3"
     * @param page
     * @return
     */
    public List<QqbsFansEntity> getFansLists(QqbsAccountEntity account, String nameOrId, String level, int page);
    
    /**
     * 根据条件查询粉丝订单列表(粉丝订单编号和粉丝ID)
     * 
     * @param account
     * @return
     */
    public List<QqbsFansOrderView> getFansOrderByNumOrId(QqbsAccountEntity account, String num);
    
    /**
     * 获取粉丝订单信息
     * 
     * @param account
     * @return
     */
    public QqbsFansOrderView getFansOrder(QqbsAccountEntity account, int id);
    
    /**
     * 获取历史数据
     * 
     * @param account
     * @return
     */
    public List<QqbsLiShiShuJu> getlishishuju();
    
    public void updatelishi(QqbsLiShiShuJu ls);
    
    /**
     * 获取粉丝数
     * 
     * @param account
     * @return
     */
    public String getFansCountByLevel(QqbsAccountEntity account);
    
    /**
     * 获取粉丝数
     * 
     * @param account
     * @return
     */
    public String getFansCountByLevels(QqbsAccountEntity account);
    
}
