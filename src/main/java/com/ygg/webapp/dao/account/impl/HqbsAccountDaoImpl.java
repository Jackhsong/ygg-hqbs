
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.dao.account.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.account.HqbsAccountDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.exception.DaoException;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsAccountDaoImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Repository("hqbsAccountDao")
public class HqbsAccountDaoImpl extends BaseDaoImpl implements HqbsAccountDao {
	
	/**
	 * TODO 请在此处添加注释
	 * @param openId
	 * @return
	 * @throws DaoException
	 */
	public QqbsAccountEntity findAccountByOpenId(String openId)
	        throws DaoException{
		QqbsAccountEntity ae = this.getSqlSession().selectOne("HqbsAccountMapper.findAccountByOpenId", openId);
        return ae;
    }
	/**
	 * TODO 请在此处添加注释
	 * @param openId
	 * @return
	 * @throws DaoException
	 */
	public QqbsAccountEntity getAccounByUnionId(String openId)
	        throws DaoException{
		QqbsAccountEntity ae = this.getSqlSession().selectOne("HqbsAccountMapper.getAccounByUnionId", openId);
        return ae;
    }
	
	
	public int updateAccoun(QqbsAccountEntity account)throws DaoException{
		return this.getSqlSession().update("HqbsAccountMapper.updateAccoun", account);
	}
	
	/**
	 * 历史
	 * @param account
	 * @return
	 * @throws DaoException
	 */
	public int updateAccounSpread(Map<String, Object> para){
		return this.getSqlSession().update("HqbsAccountMapper.updateAccounSpread", para);
	}
	
	
	public int insertQqbsAccount(QqbsAccountEntity account)throws DaoException{
		return this.getSqlSession().insert("HqbsAccountMapper.insertQqbsAccount", account);
	}
	
	public List<QqbsAccountEntity> getFans(Map<String, Object> para)
	        throws DaoException{
		
	    List<QqbsAccountEntity> resultList = getSqlSession().selectList("HqbsAccountMapper.getfans", para);
	    if (resultList == null)
	    {
	        return new ArrayList<QqbsAccountEntity>();
	    }
	    return resultList;
	}
	/**
	 * TODO 请在此处添加注释
	 * @param openId
	 * @return
	 * @throws DaoException
	 */
	public QqbsAccountEntity findAccountByAccountId(int accountId)
	        throws DaoException{
		QqbsAccountEntity ae = this.getSqlSession().selectOne("HqbsAccountMapper.findAccountByAccountId", accountId);
        return ae;
    }
	
}
