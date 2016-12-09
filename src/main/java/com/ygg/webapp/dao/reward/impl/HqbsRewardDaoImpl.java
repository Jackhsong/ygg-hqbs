
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.dao.reward.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.dao.reward.HqbsRewardDao;
import com.ygg.webapp.entity.reward.QqbsRewardEntity;
import com.ygg.webapp.exception.DaoException;

/**
  * 奖励表
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsRewardDaoImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Repository("hqbsRewardDao")
public class HqbsRewardDaoImpl extends BaseDaoImpl implements HqbsRewardDao {
	
	public QqbsRewardEntity getByAccountId(int accountId)
	        throws DaoException{
		QqbsRewardEntity ae = this.getSqlSession().selectOne("HqbsRewardMapper.getByAccountId", accountId);
        return ae;
    }
	
	public QqbsRewardEntity getByAccountIdForUpdate(int accountId){
		QqbsRewardEntity ae = this.getSqlSession().selectOne("HqbsRewardMapper.getByAccountIdForUpdate", accountId);
        return ae;
    }
	public int updateHqbsReward(Map<String, Object> para){
		return this.getSqlSession().update("HqbsRewardMapper.updateHqbsReward", para);
	}
}
