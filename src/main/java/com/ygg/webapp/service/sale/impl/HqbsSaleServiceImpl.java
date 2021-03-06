
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.sale.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.sale.QqbsSaleDao;
import com.ygg.webapp.entity.QqbsSaleWindowEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.service.sale.HqbsSaleService;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsSaleServiceImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Service("hqbsSaleService")
public class HqbsSaleServiceImpl implements HqbsSaleService {
	 /**    */
	@Resource
	private QqbsSaleDao qqbsSaleDao;
	
	public List<QqbsSaleWindowEntity> findSaleList(Map<String, Object> para)
	        throws DaoException
	    {
	        return qqbsSaleDao.findSaleList(para);
	    }
}
