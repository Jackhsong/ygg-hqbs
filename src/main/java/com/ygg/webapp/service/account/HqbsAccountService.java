
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.account;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsAccountService.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public interface HqbsAccountService {

	public QqbsAccountEntity getAccounByOpenId(String opendId);
	
	public QqbsAccountEntity getAccounByUnionId(String opendId);
    
	public AccountEntity addAccoun(QqbsAccountEntity account);
    
	public int updateQqbsAccount(int id,String image,String nickName,String unionId);
	
	public List<QqbsAccountEntity> getfans(Map<String, Object> para);
	public QqbsAccountEntity getAccountByAccountId(int accountId);
	/**
     * 查找基本用户信息
     * @param id
     * @return
     */
    public AccountEntity findAccountById(int id);
}
