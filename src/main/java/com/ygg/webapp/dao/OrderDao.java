package com.ygg.webapp.dao;

import java.util.List;
import java.util.Map;

import com.ygg.webapp.entity.OrderEntity;
import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderSourceChannel;
import com.ygg.webapp.exception.DaoException;

public interface OrderDao
{
    
    /**
     * 新增订单
     *
     * @return
     */
    int addOrder(OrderEntity oe)
        throws DaoException;
    
    /**
     * 增加订单来源渠道
     * 
     * @param osc
     * @return
     * @throws DaoException
     */
    int addOrderSourceChannel(OrderSourceChannel osc)
        throws DaoException;
    
    /**
     * 查询订单来源渠道
     * 
     * @param mark
     * @return
     * @throws DaoException
     */
    OrderSourceChannel findOrderSourceChannelByMark(String mark)
        throws DaoException;
    
    /**
     * 新增锁定数量
     *
     * @return
     */
    int addLockCount(int productId, int orderId, int count)
        throws DaoException;
    
    /**
     * 新增订单商品
     * 
     * @param ope
     * @return
     * @throws DaoException
     */
    int addOrderProduct(OrderProductEntity ope)
        throws DaoException;
    
    /**
     * 新增锁定时间
     *
     * @return
     */
    int addLockTime(int orderId, long validTime)
        throws DaoException;
    
    /**
     * 根据id查询对应的订单信息
     *
     * @return
     */
    OrderEntity findOrderById(int orderId)
        throws DaoException;
    
    /**
     * 根据订单id和账号id更新订单状态为用户取消
     *
     * @return
     */
    int updateOrderUserCancel(int orderId, int accountId)
        throws DaoException;
    
    /**
     * 根据订单id和账号id更新订单状态为交易成功
     *
     * @return
     */
    int updateOrderSuccess(int orderId, int accountId)
        throws DaoException;
    
    /**
     * 根据id列表查询对应的订单信息
     *
     * @return
     */
    List<OrderEntity> findNotPayOrderByIds(List<Integer> orderIds)
        throws DaoException;
    
    List<OrderEntity> findAllOrderStatusByIds(List<Integer> orderIds)
        throws DaoException;
    
    /**
     * 根据定单号和定单状态查找所有noPay的定单
     * 
     * @param accountId
     * @param status
     * @return
     * @throws DaoException
     */
    List<OrderEntity> findNotPayOrderByAIdAndStatus(int accountId, int status)
        throws DaoException;
    
    /**
     * 
     * @param orderNumber
     * @param payChannel
     * @return
     * @throws DaoException
     */
    List<OrderEntity> findNotPayOrderByOIdAndStatus(List<Integer> orderIds)
        throws DaoException;
    
    /**
     * 根据订单id查询所有锁定商品id
     *
     * @return
     */
    List<Integer> findLockProductIdsByOId(int orderId)
        throws DaoException;
    
    /**
     * 根据订单id查询所有锁定商品信息
     *
     * @return
     */
    List<Map<String, Object>> findLockCountInfosByOId(int orderId)
        throws DaoException;
    
    /**
     * 跟新锁定时间
     *
     * @return
     */
    int updateLockTime(int orderId, long validTime)
        throws DaoException;
    
    /**
     * 删除锁定数量
     *
     * @return
     */
    int deleteLockCount(int productId, int orderId)
        throws DaoException;
    
    /**
     * 根据订单id和账号id更新订单付款渠道
     *
     * @return
     */
    int updateOrderPayChannel(int orderId, int accountId, byte payChannel)
        throws DaoException;
    
    /**
     * 根据订单id查询有效时间
     *
     * @return
     */
    String findValidTimeByOid(int orderId)
        throws DaoException;
    
    /**
     * 根据订单id查询所有订单商品信息
     *
     * @return
     */
    List<Map<String, Object>> findOrderProductInfosByOId(int orderId)
        throws DaoException;
    
    Map<String, Object> findOrderProductInfosById(int orderProductId)
        throws DaoException;
    
    /**
     * 根据订单状态查询对应的订单数量
     *
     * @return
     */
    int findCountByStatus(int accountId, byte[] status)
        throws DaoException;
    
    /**
     * 根据订单状态查询对应的订单信息
     *
     * @return
     */
    List<OrderEntity> findOrderByStatusAndPage(int accountId, byte[] status, int page)
        throws DaoException;
    
    /**
     * 查询退款状态的定单 查询两个月以内的
     * 
     * @param accountId
     * @param status
     * @param page 暂不用
     * @return
     * @throws DaoException
     */
    List<OrderEntity> findOrderRefund(int accountId, byte[] status, int page)
        throws DaoException;
    
    /**
     * 查询总价根据多个订单id
     *
     * @return
     */
    float findSumPriceByOrderIds(List<Integer> orderIds)
        throws DaoException;
    
    /**
     * 根据订单id更新订单状态为待发货
     *
     * @return
     */
    int updateOrderNotDelivery(int orderId)
        throws DaoException;
    
    /**
     * 修改订单状态
     * 
     * @param orderId
     * @return
     * @throws DaoException
     */
    int updateOrderStatus(int orderId, String payTime)
        throws DaoException;
    
    /**
     * 查询付款优惠信息根据多个订单id
     * 
     * @return
     */
    Map<String, Object> findPayCouponByOrderIds(List<Integer> orderIds)
        throws DaoException;
    
    /**
     * 查询全部付款订单实付金额根据账号id
     * 
     * @return
     */
    float findAllRealPriceByAid(int accountId)
        throws DaoException;
    
    /**
     * 根据状态和账号Id查询定单的总数
     * 
     * @param status
     * @param accountId
     * @return
     * @throws DaoException
     */
    int findOrderStatusCount(int status, int accountId)
        throws DaoException;
    
    /**
     * 查询海外购 所有已发货超过30天的定单　
     * 
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findYesDeliveryOrders()
        throws DaoException;
    
    /**
     * 查询 国内 所有已发货超过 15 天的定单　
     * 
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findYesDeliveryMoreThan15Orders()
        throws DaoException;
    
    /**
     * 检查商品是否保存黑名单商品
     * 
     * @param orderId 订单ID
     * @param type 1，积分商品黑名单 。 2，优惠券商品黑名单
     * @return
     * @throws DaoException
     */
    boolean existsProductBlacklist(int orderId, int type)
        throws DaoException;
    
    /**
     * 根据订单id修改订单锁定状态
     *
     * @return
     */
    int updateOrderFreeze(int orderId, int lock)
        throws DaoException;
    
    /**
     * 插入订单冻结表
     * 
     * @param orderId
     * @return
     * @throws DaoException
     */
    int insertOrderFreeze(int orderId)
        throws DaoException;
    
    /**
     * 查询订单冻结记录
     * 
     * @param orderId
     * @return
     * @throws DaoException
     */
    Map<String, Object> findOrderFreezeByOrderId(int id)
        throws DaoException;
    
    /**
     * 更新订单冻结记录
     * 
     * @param para
     * @return
     * @throws DaoException
     */
    int updateOrderFreezeRecord(int orderFreezeRecordId)
        throws DaoException;
    
    /**
     * 根据商品ID查询商品供货价
     * 
     * @param productId
     * @return
     * @throws DaoException
     */
    float findProductCostByProductId(int productId)
        throws DaoException;
    
    /**
     * 根据订单Id查询笨鸟海淘发货信息
     * @param orderId：订单id
     * @return
     * @throws DaoException
     */
    List<Map<String, Object>> findBirdexOrderLogisticsByOrderId(int orderId)
        throws DaoException;
    
    /**
     * 重置订单优惠券积分使用情况
     * @param orderId
     * @return
     * @throws DaoException
     */
    int resetOrderCouponAndIntegral(int orderId)
        throws DaoException;
    
    /**
     * 根据ID查询订单简略信息
     *
     * @param id
     * @return
     * @throws DaoException
     */
    Map<String, Object> findOrderInfoById(int id)
        throws DaoException;
    
    /**
     * 在待发货、已发货、交易成功的订单订单中查找是否有订单使用了 accountCouponId 优惠券
     *
     * @param accountCouponId
     * @return
     * @throws DaoException
     */
    int findOrderIdByStatusAndAccountCouponId(int accountCouponId)
        throws DaoException;
    
    /**
     * 根据用户ID 和 商品ID查询 统计 历史订单数量
     * @param accountId
     * @param productId
     * @return
     * @throws DaoException
     */
    int countHistoryOrderByAccountIdAndProductId(int accountId, int productId)
        throws DaoException;
    
    /**
     * 更新订单发货超时时间
     * @param orderId
     * @param expireTime
     * @return
     * @throws DaoException
     */
    int updateOrderExpireTime(int orderId, String expireTime)
        throws DaoException;
    
    /**
     * 根据付款时间查询帐号实付金额
     * @param accountId：帐号id
     * @param payTimeBegin
     * @param payTimeEnd
     * @return
     * @throws DaoException
     */
    float findAllRealPriceByAidAndPayTime(int accountId, String payTimeBegin, String payTimeEnd)
        throws DaoException;
    
    /**
     * 根据付款时间查询帐号购买品牌的金额
     * @param accountId：帐号Id
     * @param brandIds：品牌Id
     * @param payTimeBegin
     * @param payTimeEnd
     * @return
     * @throws DaoException
     */
    float findAllRealPriceByAidAndBidAndPayTime(int accountId, List<String> brandIds, String payTimeBegin, String payTimeEnd)
        throws DaoException;
    
    /**
     * 查询帐号购买某品牌的付款金额
     * @param accountId：帐号Id
     * @param brandIds：品牌Id
     * @return
     * @throws DaoException
     */
    float findAllRealPriceByAidAndBid(int accountId, List<String> brandIds)
        throws DaoException;
    
    
    /**
     * 获取用户订单的最大金额订单
     * @param accountId
     * @return
     */
    public Map<String, Object> getOneRealPriceByAid(int accountId);
    
    /**
     * 计算该商品 代言人能够获取多少佣金
     * @param productId 商品Id
     * @param salesPrice 商品销售价
     * @return
     */
    public float getHqbsDraw(int productId);
    
}
