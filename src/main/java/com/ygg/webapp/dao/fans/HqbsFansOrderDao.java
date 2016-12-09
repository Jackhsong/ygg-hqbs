
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

import com.ygg.webapp.entity.fans.QqbsFansOrderEntity;
import com.ygg.webapp.entity.fans.QqbsLiShiShuJu;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.view.fans.QqbsFansOrderView;

/**
  * TODO 请在此处添加注释
  * @author <a href="mailto:zhangld@yangege.com">zhangld</a>
  * @version $Id: HqbsFansOrderDao.java 12586 2016-05-23 07:16:23Z qiuyibo $   
  * @since 2.0
  */
public interface HqbsFansOrderDao {
	
	/**
	 * 获取粉丝订单列表
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsFansOrderView> getFansOrderList(Map<String, Object> para);
	/**
	 * 获取粉丝订单总金额
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public float getFansOrderPrice(int accountId)throws DaoException;
	/**
	 * 获取粉丝订单数
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public int getFansOrderCount(int accountId)throws DaoException;
	/**
	 * 插入粉丝订单
	 * @param accountId
	 * @return
	 * @throws DaoException
	 */
	public int insertFans(QqbsFansOrderEntity qro)throws DaoException;
	
	/**
	 * 获取历史数据(订单) 临时使用
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsFansOrderEntity> getOrderList()throws DaoException;
	/**
	 * 根据Id获取粉丝订单
	 * @param para
	 * @return
	 * @throws DaoException
	 * @see com.ygg.webapp.dao.fans.HqbsFansOrderDao#getFansOrderList(java.util.Map)
	 */
	public QqbsFansOrderView getFansOrder(Map<String, Object> para);
	
	
	/**
	 * 获取历史数据(订单) 临时使用
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsLiShiShuJu> getQqbsLiShiShuJu()throws DaoException;
	
	/**
	 * 获取历史数据(订单) 临时使用
	 * @param para
	 * @return
	 * @throws DaoException
	 */
	public List<QqbsFansOrderEntity> getOrderListSpread(Map<String, Object> para);
	
	
	/**
	 * 更新(spread)
	 * @param para
	 * @return
	 */
	public int updateSpread(Map<String, Object> para);
	/**
     * 根据粉丝订单ID和用户ID和粉丝ID和等级  查找粉丝订单是否存在
     * @return
     */
    public QqbsFansOrderEntity getFansOrderByError(Map<String, Object> para);
}
