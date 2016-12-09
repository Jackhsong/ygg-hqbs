
 /**************************************************************************
 * Copyright (c) 2014-2016 浙江格家网络技术有限公司.
 * All rights reserved.
 * 
 * 项目名称：左岸城堡APP
 * 版权说明：本软件属浙江格家网络技术有限公司所有，在未获得浙江格家网络技术有限公司正式授权
 *           情况下，任何企业和个人，不能获取、阅读、安装、传播本软件涉及的任何受知
 *           识产权保护的内容。                            
 ***************************************************************************/
package com.ygg.webapp.dao.fans.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.fans.HqbsFansOrderDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.fans.QqbsFansOrderEntity;
import com.ygg.webapp.entity.fans.QqbsLiShiShuJu;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.view.fans.QqbsFansOrderView;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsFansOrderDaoImpl.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
@Repository("hqbsFansOrderDao")
public class HqbsFansOrderDaoImpl extends BaseDaoImpl implements HqbsFansOrderDao {
	
	
	/**
	 * 获取粉丝订单
	 * @param para
	 * @return
	 * @throws DaoException
	 * @see com.ygg.webapp.dao.fans.HqbsFansOrderDao#getFansOrderList(java.util.Map)
	 */
	public List<QqbsFansOrderView> getFansOrderList(Map<String, Object> para){
		return this.getSqlSession().selectList("HqbsFansOrderMapper.getFansOrderList", para);
	}
	/**
	 * 获取粉丝订单总金额
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public float getFansOrderPrice(int accountId)throws DaoException{
		return this.getSqlSession().selectOne("HqbsFansOrderMapper.getFansOrderPrice", accountId);
	}
	/**
	 * 获取粉丝订单数
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public int getFansOrderCount(int accountId)throws DaoException{
		return this.getSqlSession().selectOne("HqbsFansOrderMapper.getFansOrderCount", accountId);
	}
	/**
	 * 插入粉丝订单
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public int insertFans(QqbsFansOrderEntity qro)throws DaoException{
		return this.getSqlSession().insert("HqbsFansOrderMapper.insertFans", qro);
	}
	
	/**
	 * 获取历史数据(订单) 临时使用
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsFansOrderEntity> getOrderList()throws DaoException{
		return this.getSqlSession().selectList("HqbsFansOrderMapper.getOrderList");
	}
	
	/**
	 * 根据Id获取粉丝订单
	 * @param para
	 * @return
	 * @throws DaoException
	 * @see com.ygg.webapp.dao.fans.HqbsFansOrderDao#getFansOrderList(java.util.Map)
	 */
	public QqbsFansOrderView getFansOrder(Map<String, Object> para){
		return this.getSqlSession().selectOne("HqbsFansOrderMapper.getFansOrder", para);
	}
	
	
	/**
	 * 获取历史数据(订单) 临时使用(spread)
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsLiShiShuJu> getQqbsLiShiShuJu()throws DaoException{
		return this.getSqlSession().selectList("HqbsFansOrderMapper.getSpreadList");
	}
	/**
	 * 获取历史数据(订单) 临时使用(order)
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsFansOrderEntity> getOrderListSpread(Map<String, Object> para){
		return this.getSqlSession().selectList("HqbsFansOrderMapper.getOrderListSpread",para);
	}
	
	
	
	/**
	 * 更新(spread)
	 * @param para
	 * @return
	 */
	public int updateSpread(Map<String, Object> para){
		return this.getSqlSession().update("HqbsFansOrderMapper.updateSpread", para);
	}
	
	/**
     * 根据粉丝订单ID和用户ID和粉丝ID和等级  查找粉丝订单是否存在
     * @param para
     * @return
     * @throws DaoException
     * @see com.ygg.webapp.dao.fans.HqbsFansOrderDao#getFansOrderList(java.util.Map)
     */
    public QqbsFansOrderEntity getFansOrderByError(Map<String, Object> para){
        return this.getSqlSession().selectOne("HqbsFansOrderMapper.getFansOrderByError", para);
    }
	
}
