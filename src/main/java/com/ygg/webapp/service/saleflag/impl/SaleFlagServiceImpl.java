
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.saleflag.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.SaleFlagDaoIF;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.service.saleflag.SaleFlagService;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: SaleFlagServiceImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Service("saleFlagService")
public class SaleFlagServiceImpl implements SaleFlagService {
	
	@Resource
	private SaleFlagDaoIF saleFlagDao;
	
	public String findImageById(int id)
	        throws DaoException
	    {
	        return saleFlagDao.findImageById(id);
    }
}
