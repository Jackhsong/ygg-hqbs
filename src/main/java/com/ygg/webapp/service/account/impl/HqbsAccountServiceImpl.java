
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.service.account.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.account.HqbsAccountDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.service.account.HqbsAccountService;
import com.ygg.webapp.util.CommonConstant;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsAccountServiceImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Service("hqbsAccountService")
public class HqbsAccountServiceImpl implements HqbsAccountService {
	
	 /**    */
    @Resource(name="hqbsAccountDao")
    private HqbsAccountDao hqbsAccountDao;
    
     /**    */
    @Resource(name="accountDao")
    private AccountDao accountDao;
    
	@Override
    public QqbsAccountEntity getAccounByOpenId(String opendId){
        return hqbsAccountDao.findAccountByOpenId(opendId);
    }
	
	/**
	 * @param opendId
	 * @return
	 * @see com.ygg.webapp.service.account.HqbsAccountService#getAccounByUnionId(java.lang.String)
	 */
	public QqbsAccountEntity getAccounByUnionId(String openId){
		return hqbsAccountDao.getAccounByUnionId(openId);
	}
    @Override
    public AccountEntity addAccoun(QqbsAccountEntity qqbsAccount)
    {
        // 添加基本表
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName(qqbsAccount.getOpenId());
        accountEntity.setPwd("");
        accountEntity.setType(CommonConstant.ACCOUNTTYPE);
        accountEntity.setNickname(qqbsAccount.getNickName());
        accountEntity.setUnionId(qqbsAccount.getUnionId());
        
        accountEntity.setImage(qqbsAccount.getImage());
        accountEntity.setMobileNumber("");
        accountEntity.setRecommendedCode("");
        accountEntity.setIsRecommended((byte)0);
        //保存基本信息
        accountDao.addAccount(accountEntity);
        qqbsAccount.setAccountId(accountEntity.getId());
        //保存左岸城堡信息
        qqbsAccount.setExStatus(1);
        hqbsAccountDao.insertQqbsAccount(qqbsAccount);
        return accountEntity;
    }
    public int updateQqbsAccount(int id,String image,String nickName,String unionId){
    	QqbsAccountEntity ae = new QqbsAccountEntity();
    	ae.setId(id);
    	ae.setImage(image);
    	ae.setNickName(nickName);
    	if(StringUtils.isNotBlank(unionId)){
    	    ae.setUnionId(unionId);
    	}
    	return this.hqbsAccountDao.updateAccoun(ae);
    }
    
    
    public List<QqbsAccountEntity> getfans(Map<String, Object> para){
		return hqbsAccountDao.getFans(para);
	}
    
    public QqbsAccountEntity getAccountByAccountId(int accountId){
		return hqbsAccountDao.findAccountByAccountId(accountId);
	}
    
    /**
     * 查找基本用户信息
     * @param id
     * @return
     */
    public AccountEntity findAccountById(int id){
            return accountDao.findAccountById(id);
    }
}
