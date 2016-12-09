
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.dao.fans;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.fans.QqbsFansEntity;
import com.ygg.webapp.exception.DaoException;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsFansDao.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public interface HqbsFansDao {
	
	/**
	 * TODO 请在此处添加注释
	 * @param fans
	 * @return
	 * @throws DaoException
	 */
	public int insertFans(QqbsFansEntity fans)throws DaoException;
	
	/**
	 * TODO 请在此处添加注释
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public int getFansCount(Map<String, Object> para)throws DaoException;
	
	/**
	 * TODO 请在此处添加注释
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsFansEntity> getFansList(Map<String, Object> para)throws DaoException;
	
	/**
	 * level "1,2,3"
	 * @param para
	 * @return
	 */
	public List<QqbsFansEntity> getFansLists(Map<String, Object> para);
	/**
	 * TODO 请在此处添加注释
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public int getFansOrderCount(int accountId)throws DaoException;
	/**
	 * 获取粉丝订单总金额
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public float getFansOrderPrice(int accountId)throws DaoException;
}
