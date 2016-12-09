
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.dao.account;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.exception.DaoException;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsAccountDao.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public interface HqbsAccountDao {
	/**
	 * TODO 请在此处添加注释
	 * @param openId
	 * @return
	 * @throws DaoException
	 */
	public QqbsAccountEntity findAccountByOpenId(String openId)
	        throws DaoException;
	
	/**
	 * TODO 请在此处添加注释
	 * @param openId
	 * @return
	 * @throws DaoException
	 */
	public QqbsAccountEntity getAccounByUnionId(String openId)
	        throws DaoException;
	
	/**
	 * TODO 请在此处添加注释
	 * @param account
	 * @return
	 * @throws DaoException
	 */
	public int updateAccoun(QqbsAccountEntity account)throws DaoException;
	
	/**
	 * 历史
	 * @param account
	 * @return
	 * @throws DaoException
	 */
	public int updateAccounSpread(Map<String, Object> para);
	/**
	 * TODO 请在此处添加注释
	 * @param account
	 * @return
	 * @throws DaoException
	 */
	public int insertQqbsAccount(QqbsAccountEntity account)throws DaoException;
	
	/**
	 * TODO 请在此处添加注释
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsAccountEntity> getFans(Map<String, Object> para)
	        throws DaoException;
	
	/**
	 * TODO 请在此处添加注释
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public QqbsAccountEntity findAccountByAccountId(int accountId)
	        throws DaoException;
	
}
