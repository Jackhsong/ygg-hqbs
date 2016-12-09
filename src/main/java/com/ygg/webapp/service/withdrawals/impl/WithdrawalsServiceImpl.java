
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.withdrawals.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.reward.HqbsRewardDao;
import com.ygg.webapp.dao.reward.HqbsWithdrawalsLogDao;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.entity.reward.QqbsRewardEntity;
import com.ygg.webapp.entity.reward.QqbsWithdrawalsLogEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.service.withdrawals.WithdrawalsService;
import com.ygg.webapp.view.fans.QqbsWithdrawalsLogView;

/**
  * 提现服务
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: WithdrawalsServiceImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Service("withdrawalsService")
public class WithdrawalsServiceImpl implements WithdrawalsService {
	
	
	 /**提现日志Dao */
	@Resource(name="hqbsWithdrawalsLogDao")
	private HqbsWithdrawalsLogDao hqbsWithdrawalsLogDao;
	
	/**    */
	@Resource(name="hqbsRewardDao")
	private HqbsRewardDao hqbsRewardDao;
	private static Logger logger = Logger.getLogger(WithdrawalsServiceImpl.class);
	
	/**
	 * 获取提现记录
	 * @return
	 * @throws DaoException
	 * @see com.ygg.webapp.service.withdrawals.WithdrawalsService#getLogList(com.ygg.webapp.entity.QqbsAccountEntity)
	 */
	public List<QqbsWithdrawalsLogView> getLogList(QqbsAccountEntity qqbsAccountEntity,int page){
		Map<String, Object> para = new HashMap<String, Object>();
		para.put("accountId", qqbsAccountEntity.getAccountId());
		para.put("start", 10 * (page - 1));
		para.put("size", 10);
		List<QqbsWithdrawalsLogView> withdrawalsLogList = hqbsWithdrawalsLogDao.getLogList(para);
		return withdrawalsLogList;
	}
	
	/**
	 * 更新提现金额
	 * @return
	 * @throws DaoException
	 * @see com.ygg.webapp.service.withdrawals.WithdrawalsService#getLogList(com.ygg.webapp.entity.QqbsAccountEntity)
	 */
	public boolean updateDraw(QqbsAccountEntity qqbsAccountEntity,float cashNum){
		boolean flag = true;
		QqbsRewardEntity qqbsReward = hqbsRewardDao.getByAccountIdForUpdate(qqbsAccountEntity.getAccountId());
		if(qqbsReward != null){
			float withdrawCash = qqbsReward.getWithdrawCash();
			if(withdrawCash<cashNum){
				flag = false;
			}else{
				//更新奖励表
				float yuer = withdrawCash - cashNum;
				Map<String, Object> para = new HashMap<String, Object>();
				para.put("id", qqbsReward.getId());
				para.put("withdrawCash", yuer);
				hqbsRewardDao.updateHqbsReward(para);
				//记录提现日志
				QqbsWithdrawalsLogEntity qqbsWithdrawalsLog = new QqbsWithdrawalsLogEntity();
				qqbsWithdrawalsLog.setAccountId(qqbsAccountEntity.getAccountId());
				qqbsWithdrawalsLog.setWithdrawals(cashNum);
				qqbsWithdrawalsLog.setStatus(1);
				hqbsWithdrawalsLogDao.insertWithdrawalsLog(qqbsWithdrawalsLog);
				flag = true;
			}
		}else{
			flag = false;
		}
		return flag;
	}
}
