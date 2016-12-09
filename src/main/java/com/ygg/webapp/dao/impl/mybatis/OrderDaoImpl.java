package com.ygg.webapp.dao.impl.mybatis;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.OrderEntity;
import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderSourceChannel;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.util.CommonConstant;
import com.ygg.webapp.util.CommonEnum;

@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao
{
    
    @Override
    public int addOrder(OrderEntity oe)
        throws DaoException
    {
        int result = this.getSqlSession().insert("OrderMapper.addOrder", oe);
        if (result > 0)
            return oe.getId();
        return 0;
    }
    
    @Override
    public int addLockCount(int productId, int orderId, int count)
        throws DaoException
    {
        String sql = "INSERT INTO order_lock_count(order_id,product_id,product_count) values(?,?,?)";
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("productId", productId);
        map.put("productCount", count);
        return this.getSqlSession().insert("OrderMapper.addLockCount", map);
    }
    
    @Override
    public int addLockTime(int orderId, long validTime)
        throws DaoException
    {
        String sql = "INSERT INTO order_lock_time(order_id,valid_time) values(?,?)";
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("validTime", new Timestamp(validTime));
        return this.getSqlSession().insert("OrderMapper.addLockTime", map);
    }
    
    @Override
    public OrderEntity findOrderById(int orderId)
        throws DaoException
    {
        /*
         * String sql =
         * "SELECT id,number,seller_id,receive_address_id,total_price,freight_money,pay_channel,status,create_time,pay_time FROM `order` WHERE id =?"
         * ; List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * orderId)); return queryOneT(sql, params);
         */
        OrderEntity oe = this.getSqlSession().selectOne("OrderMapper.findOrderById", orderId);
        return oe;
    }
    
    @Override
    public int updateOrderUserCancel(int orderId, int accountId)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<>();
        map.put("status", (byte)CommonEnum.ORDER_STATUS.USER_CANCEL.getValue());
        map.put("orderId", orderId);
        map.put("accountId", accountId);
        return this.getSqlSession().update("OrderMapper.updateOrderUserCancel", map);
    }
    
    @Override
    public int updateOrderSuccess(int orderId, int accountId)
        throws DaoException
    {
        /*
         * String sql = "UPDATE `order` SET receive_time=?,status=? WHERE id=? AND account_id=?"; List<PstmtParam>
         * params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.TIMESTAMP, new
         * Timestamp(System.currentTimeMillis()))); params.add(new PstmtParam(FILL_PSTMT_TYPE.BYTE,
         * (byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue())); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, orderId));
         * params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, accountId)); return execute(sql, params);
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        map.put("accountId", accountId);
        map.put("receiveTime", new Timestamp(System.currentTimeMillis()));
        map.put("status", (byte)CommonEnum.ORDER_STATUS.SUCCESS.getValue());
        return this.getSqlSession().update("OrderMapper.updateOrderSuccess", map);
    }
    
    @Override
    public List<OrderEntity> findNotPayOrderByIds(List<Integer> orderIds)
        throws DaoException
    {
        if (orderIds.size() == 0)
        {
            return new ArrayList<OrderEntity>();
        }
        /*
         * String sql = "SELECT total_price FROM `order` WHERE id IN (" +
         * CommonUtil.changeSqlValueByLen(orderIds.size()) + ") AND status=" +
         * CommonEnum.ORDER_STATUS.NOT_PAY.getValue(); List<PstmtParam> params = new ArrayList<PstmtParam>(); for
         * (Integer orderId : orderIds) { params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, orderId)); } return
         * queryAllInfo2T(sql, params);
         */
        
        List<OrderEntity> list = this.getSqlSession().selectList("OrderMapper.findNotPayOrderByIds", orderIds);
        if (list == null || list.isEmpty())
            return new ArrayList<OrderEntity>();
        return list;
    }
    
    public List<OrderEntity> findAllOrderStatusByIds(List<Integer> orderIds)
        throws DaoException
    {
        if (orderIds.size() == 0)
        {
            return new ArrayList<OrderEntity>();
        }
        
        List<OrderEntity> list = this.getSqlSession().selectList("OrderMapper.findAllOrderStatusByIds", orderIds);
        if (list == null || list.isEmpty())
            return new ArrayList<OrderEntity>();
        return list;
    }
    
    @Override
    public List<Integer> findLockProductIdsByOId(int orderId)
        throws DaoException
    {
        List<Integer> result = new ArrayList<Integer>();
        /*
         * String sql = "SELECT product_id FROM order_lock_count WHERE order_id=?"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, orderId)); List<Map<String, Object>>
         * mapList = queryAllInfo2Map(sql, params); for (Map<String, Object> map : mapList) {
         * result.add(((Long)map.get("product_id")).intValue()); } return result;
         */
        result = this.getSqlSession().selectList("OrderMapper.findLockProductIdsByOId", orderId);
        if (result == null || result.size() == 0)
            result = new ArrayList<Integer>();
        return result;
    }
    
    @Override
    public List<Map<String, Object>> findLockCountInfosByOId(int orderId)
        throws DaoException
    {
        List<Map<String, Object>> list = this.getSqlSession().selectList("OrderMapper.findLockCountInfosByOId", orderId);
        return list;
    }
    
    @Override
    public int updateLockTime(int orderId, long validTime)
        throws DaoException
    {
        /*
         * String sql = "UPDATE `order_lock_time` SET valid_time=? WHERE order_id=?"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.TIMESTAMP, new Timestamp(validTime)));
         * params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, orderId)); return execute(sql, params);
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("validTime", new Timestamp(validTime));
        map.put("isValid", 0);
        map.put("orderId", orderId);
        return this.getSqlSession().update("OrderMapper.updateLockTime", map);
    }
    
    @Override
    public int deleteLockCount(int productId, int orderId)
        throws DaoException
    {
        /*
         * String sql = "DELETE FROM order_lock_count where product_id=? AND order_id=?"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, productId)); params.add(new
         * PstmtParam(FILL_PSTMT_TYPE.INT, orderId)); return execute(sql, params);
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productId", productId);
        map.put("orderId", orderId);
        return this.getSqlSession().delete("OrderMapper.deleteLockCount", map);
        
    }
    
    @Override
    public int updateOrderPayChannel(int orderId, int accountId, byte payChannel)
        throws DaoException
    {
        String sql = "UPDATE `order` SET pay_channel=? WHERE id=? AND account_id=?";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.BYTE,
         * payChannel)); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, orderId)); params.add(new
         * PstmtParam(FILL_PSTMT_TYPE.INT, accountId)); return execute(sql, params);
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("payChannel", payChannel);
        map.put("orderId", orderId);
        map.put("accountId", accountId);
        return this.getSqlSession().update("OrderMapper.updateOrderPayChannel", map);
        
    }
    
    @Override
    public String findValidTimeByOid(int orderId)
        throws DaoException
    {
        String sql = "SELECT valid_time from order_lock_time WHERE order_id=?";
        /*
         * List<PstmtParam> params = new ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * orderId));
         * 
         * Map<String, Object> map = queryOneMap(sql, params); if (map == null) { return null; } return
         * ((Timestamp)map.get("valid_time")).toString();
         */
        
        java.sql.Timestamp validTime = this.getSqlSession().selectOne("OrderMapper.findValidTimeByOid", orderId);
        if (validTime != null)
            return validTime.toString();
        return "";
    }
    
    @Override
    public List<Map<String, Object>> findOrderProductInfosByOId(int orderId)
        throws DaoException
    {
        List<Map<String, Object>> list = this.getSqlSession().selectList("OrderMapper.findOrderProductInfosByOId", orderId);
        if (list == null)
            list = new ArrayList<Map<String, Object>>();
        return list;
        
    }
    
    public List<Map<String, Object>> findYesDeliveryOrders()
        throws DaoException
    {
        List<Map<String, Object>> list = this.getSqlSession().selectList("OrderMapper.findYesDeliveryOrders");
        if (list == null)
            list = new ArrayList<Map<String, Object>>();
        return list;
        
    }
    
    public Map<String, Object> findOrderProductInfosById(int orderProductId)
        throws DaoException
    {
        Map<String, Object> map = this.getSqlSession().selectOne("OrderMapper.findOrderProductInfosById", orderProductId);
        if (map == null)
            map = new HashMap<String, Object>();
        return map;
    }
    
    @Override
    public int findCountByStatus(int accountId, byte[] status)
        throws DaoException
    {
        /*
         * String sql = "SELECT count(*) as count FROM `order` WHERE status IN (" +
         * CommonUtil.changeSqlValueByLen(status.length) + ") AND account_id=? AND is_delete=0"; List<PstmtParam> params
         * = new ArrayList<PstmtParam>(); for (byte statu : status) { params.add(new PstmtParam(FILL_PSTMT_TYPE.BYTE,
         * statu)); } params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, accountId)); return ((Long)(queryOneMap(sql,
         * params).get("count"))).intValue();
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", accountId);
        map.put("status", status);
        Integer it = this.getSqlSession().selectOne("OrderMapper.findCountByStatus", map);
        if (it == null)
            it = 0;
        return it;
    }
    
    @Override
    public List<OrderEntity> findOrderByStatusAndPage(int accountId, byte[] status, int page)
        throws DaoException
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", accountId);
        map.put("status", status);
        map.put("start", (page - 1) * CommonConstant.ORDER_PAGE_COUNT);
        map.put("page", CommonConstant.ORDER_PAGE_COUNT);
        List<OrderEntity> list = this.getSqlSession().selectList("OrderMapper.findOrderByStatusAndPage", map);
        if (list == null)
            list = new ArrayList<OrderEntity>();
        return list;
        
    }
    
    public List<OrderEntity> findOrderRefund(int accountId, byte[] status, int page)
        throws DaoException
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accountId", accountId);
        // map.put("status", status);
        // map.put("start", (page - 1) * CommonConstant.ORDER_PAGE_COUNT) ;
        // map.put("page", CommonConstant.ORDER_PAGE_COUNT) ;
        List<OrderEntity> list = this.getSqlSession().selectList("OrderMapper.findOrderRefund", map); // 先查交易成功在
                                                                                                      // 一个月以内的定单
        if (list == null)
            list = new ArrayList<OrderEntity>();
        
        // 再查　待发货　和　已发货　的定单
        byte[] status2 = new byte[] {(byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue(), (byte)CommonEnum.ORDER_STATUS.YES_DELIVERY.getValue()};
        
        map.put("status", status2);
        List<OrderEntity> list2 = this.getSqlSession().selectList("OrderMapper.findOrderRefund2", map);
        if (list2 != null && !list2.isEmpty())
            list.addAll(list2);
        
        return list;
    }
    
    @Override
    public float findSumPriceByOrderIds(List<Integer> orderIds)
        throws DaoException
    {
        if (orderIds.size() == 0)
        {
            return 0;
        }
        /*
         * String sql = "SELECT SUM(total_price) as total_price FROM `order` WHERE id IN (" +
         * CommonUtil.changeSqlValueByLen(orderIds.size()) + ")"; List<PstmtParam> params = new ArrayList<PstmtParam>();
         * for (Integer orderId : orderIds) { params.add(new PstmtParam(FILL_PSTMT_TYPE.INT, orderId)); } return
         * queryOneT(sql, params).getTotalPrice();
         */
        
        float totalPrice = this.getSqlSession().selectOne("OrderMapper.findSumPriceByOrderIds", orderIds);
        return totalPrice;
    }
    
    @Override
    public int updateOrderNotDelivery(int orderId)
        throws DaoException
    {
        /*
         * String sql = "UPDATE `order` SET pay_time=?,status=? WHERE id=?"; List<PstmtParam> params = new
         * ArrayList<PstmtParam>(); params.add(new PstmtParam(FILL_PSTMT_TYPE.TIMESTAMP, new
         * Timestamp(System.currentTimeMillis()))); params.add(new PstmtParam(FILL_PSTMT_TYPE.BYTE,
         * (byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue())); params.add(new PstmtParam(FILL_PSTMT_TYPE.INT,
         * orderId)); return execute(sql, params);
         */
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("payTime", new Timestamp(System.currentTimeMillis()));
        map.put("status", (byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue());
        map.put("orderId", orderId);
        return this.getSqlSession().update("OrderMapper.updateOrderNotDelivery", map);
    }
    
    @Override
    public int updateOrderStatus(int orderId, String payTime)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("payTime", payTime);
        map.put("status", (byte)CommonEnum.ORDER_STATUS.NOT_DELIVERY.getValue());
        map.put("orderId", orderId);
        return this.getSqlSession().update("OrderMapper.updateOrderNotDelivery", map);
    }
    
    @Override
    public int findOrderStatusCount(int status, int accountId)
        throws DaoException
    {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("accountId", accountId);
        int count = this.getSqlSession().selectOne("OrderMapper.findOrderStatusCount", map);
        return count;
    }
    
    @Override
    public List<OrderEntity> findNotPayOrderByAIdAndStatus(int accountId, int status)
        throws DaoException
    {
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("accountId", accountId);
        params.put("status", status);
        List<OrderEntity> orders = this.getSqlSession().selectList("OrderMapper.findNotPayOrderByAIdAndStatus", params);
        if (orders == null)
            orders = new ArrayList<OrderEntity>();
        return orders;
    }
    
    @Override
    public int addOrderProduct(OrderProductEntity ope)
        throws DaoException
    {
        if (ope == null)
            return 0;
        return this.getSqlSession().insert("OrderMapper.addOrderProductByEntity", ope);
    }
    
    @Override
    public float findProductCostByProductId(int productId)
        throws DaoException
    {
        Map<String, Object> costInfo = getSqlSession().selectOne("OrderMapper.findProductCostByProductId", productId);
        double cost = 0;
        if (costInfo != null)
        {
            double hqbsCost = Double.parseDouble(costInfo.get("hqbs_wholesale_price") == null ? "0" : costInfo.get("hqbs_wholesale_price") + "");
            if(hqbsCost>0){
                return Float.valueOf(hqbsCost + "").floatValue();
            }else{
                int submitType = Integer.parseInt(costInfo.get("submit_type") == null ? "1" : costInfo.get("submit_type") + "");
                if (submitType == 1)// 正常结算
                {
                    cost = Double.parseDouble(costInfo.get("wholesale_price") == null ? "0" : costInfo.get("wholesale_price") + "");
                }
                else if (submitType == 2)// 扣点结算
                {
                    double proposalPrice = Double.parseDouble(costInfo.get("proposal_price") == null ? "0" : costInfo.get("proposal_price") + "");
                    double deduction = Double.parseDouble(costInfo.get("deduction") == null ? "0" : costInfo.get("deduction") + "");
                    cost = (100 - deduction) * proposalPrice / 100;
                }
                else if (submitType == 3)// 自营采购价
                {
                    cost = Double.parseDouble(costInfo.get("self_purchase_price") == null ? "0" : costInfo.get("self_purchase_price") + "");
                }
            }
        }
        return Float.valueOf(cost + "").floatValue();
    }
    
    @Override
    public List<OrderEntity> findNotPayOrderByOIdAndStatus(List<Integer> orderIds)
        throws DaoException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderIds", orderIds);
        // map.put("payChannel", payChannel) ;
        List<OrderEntity> orderEntitys = this.getSqlSession().selectList("OrderMapper.findNotPayOrderByOIdAndStatus", orderIds);
        if (orderEntitys == null)
            orderEntitys = new ArrayList<OrderEntity>();
        return orderEntitys;
    }
    
    @Override
    public int addOrderSourceChannel(OrderSourceChannel osc)
        throws DaoException
    {
        return this.getSqlSession().insert("OrderMapper.addOrderSourceChannel", osc);
    }
    
    public OrderSourceChannel findOrderSourceChannelByMark(String mark)
        throws DaoException
    {
        if (mark == null || mark.equals(""))
            return null;
        return this.getSqlSession().selectOne("OrderMapper.findOrderSourceChannelByMark", mark);
    }
    
    @Override
    public Map<String, Object> findPayCouponByOrderIds(List<Integer> orderIds)
        throws DaoException
    {
        if (orderIds.size() == 0)
        {
            return new HashMap<String, Object>();
        }
        // String sql =
        // "SELECT SUM(real_price) as real_price,SUM(account_point) as account_point,account_coupon_id,account_id FROM `order` WHERE id IN ("
        // + CommonUtil.changeSqlValueByLen(orderIds.size()) + ")";
        return this.getSqlSession().selectOne("OrderMapper.findPayCouponByOrderIds", orderIds);
    }
    
    @Override
    public List<Map<String, Object>> findYesDeliveryMoreThan15Orders()
        throws DaoException
    {
        List<Map<String, Object>> list = this.getSqlSession().selectList("OrderMapper.findYesDeliveryMoreThan15Orders");
        if (list == null)
            list = new ArrayList<Map<String, Object>>();
        return list;
    }
    
    @Override
    public boolean existsProductBlacklist(int orderId, int type)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("orderId", orderId);
        para.put("type", type);
        Integer id = getSqlSession().selectOne("OrderMapper.existsroductBlacklist", para);
        return id != null;
    }
    
    @Override
    public int updateOrderFreeze(int orderId, int isFreeze)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("orderId", orderId);
        para.put("isFreeze", isFreeze);
        return getSqlSession().update("OrderMapper.updateOrderFreeze", para);
    }
    
    @Override
    public int insertOrderFreeze(int orderId)
        throws DaoException
    {
        return getSqlSession().insert("OrderMapper.insertOrderFreeze", orderId);
    }
    
    @Override
    public Map<String, Object> findOrderFreezeByOrderId(int id)
        throws DaoException
    {
        return getSqlSession().selectOne("OrderMapper.findOrderFreezeByOrderId", id);
    }
    
    @Override
    public int updateOrderFreezeRecord(int orderFreezeRecordId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<String, Object>();
        para.put("id", orderFreezeRecordId);
        para.put("unfreezeTime", "0000-00-00 00:00:00");
        para.put("status", 1);
        return getSqlSession().update("OrderMapper.updateOrderFreezeRecord", para);
    }
    
    @Override
    public float findAllRealPriceByAid(int accountId)
        throws DaoException
    {
        Float totalPrice = this.getSqlSession().selectOne("OrderMapper.findAllRealPriceByAid", accountId);
        return totalPrice == null ? 0.0f : totalPrice.floatValue();
    }
    
    @Override
    public List<Map<String, Object>> findBirdexOrderLogisticsByOrderId(int orderId)
        throws DaoException
    {
        List<Map<String, Object>> list = this.getSqlSession().selectList("OrderMapper.findBirdexOrderLogisticsByOrderId", orderId);
        return list == null ? new ArrayList<Map<String, Object>>() : list;
    }
    
    @Override
    public int resetOrderCouponAndIntegral(int orderId)
        throws DaoException
    {
        return getSqlSession().update("OrderMapper.resetOrderCouponAndIntegral", orderId);
    }
    
    @Override
    public Map<String, Object> findOrderInfoById(int id)
        throws DaoException
    {
        return this.getSqlSession().selectOne("OrderMapper.findOrderInfoById", id);
    }
    
    @Override
    public int findOrderIdByStatusAndAccountCouponId(int accountCouponId)
        throws DaoException
    {
        Integer result = this.getSqlSession().selectOne("OrderMapper.findOrderIdByStatusAndAccountCouponId", accountCouponId);
        return result == null ? -1 : result;
    }
    
    @Override
    public int countHistoryOrderByAccountIdAndProductId(int accountId, int productId)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", accountId);
        para.put("productId", productId);
        Integer num = getSqlSession().selectOne("OrderMapper.countHistoryOrderByAccountIdAndProductId", para);
        return num == null ? 0 : num;
    }
    
    @Override
    public int updateOrderExpireTime(int orderId, String expireTime)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("id", orderId);
        para.put("expireTime", expireTime);
        return getSqlSession().update("OrderMapper.updateOrderExpireTime", para);
    }
    
    @Override
    public float findAllRealPriceByAidAndBid(int accountId, List<String> brandIds)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", accountId);
        para.put("brandIdList", brandIds);
        Float totalPrice = getSqlSession().selectOne("OrderMapper.findAllRealPriceByAidAndBid", para);
        return totalPrice == null ? 0.0f : totalPrice.floatValue();
    }
    
    @Override
    public float findAllRealPriceByAidAndBidAndPayTime(int accountId, List<String> brandIds, String payTimeBegin, String payTimeEnd)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", accountId);
        para.put("brandIdList", brandIds);
        para.put("payTimeBegin", payTimeBegin);
        para.put("payTimeEnd", payTimeEnd);
        Float totalPrice = getSqlSession().selectOne("OrderMapper.findAllRealPriceByAidAndBidAndPayTime", para);
        return totalPrice == null ? 0.0f : totalPrice.floatValue();
    }
    
    @Override
    public float findAllRealPriceByAidAndPayTime(int accountId, String payTimeBegin, String payTimeEnd)
        throws DaoException
    {
        Map<String, Object> para = new HashMap<>();
        para.put("accountId", accountId);
        para.put("payTimeBegin", payTimeBegin);
        para.put("payTimeEnd", payTimeEnd);
        Float totalPice = getSqlSession().selectOne("OrderMapper.findAllRealPriceByAidAndPayTime", para);
        return totalPice == null ? 0.0f : totalPice.floatValue();
    }
    
    
    /**
     * 判断是否是代言人条件一
     * @return
     * @see com.ygg.webapp.dao.OrderDao#getOneRealPriceByAid(int)
     */
    @Override
    public Map<String, Object> getOneRealPriceByAid(int accountId)
    {
    	return this.getSqlSession().selectOne("OrderMapper.getOneRealPriceByAid", accountId);
    }
    
    
    /**
     * 计算该商品 代言人能够获取多少佣金
     * @param productId
     * @return double
     */
    public float getHqbsDraw(int productId)
    {
        Map<String, Object> costInfo = getSqlSession().selectOne("OrderMapper.getHqbsDrawByProductId", productId);
        double bsCommision = 0;
        if (costInfo != null)
        {
            bsCommision = Double.parseDouble(costInfo.get("bsCommision") == null ? "0" : costInfo.get("bsCommision") + "");
            if(bsCommision>0){
                return (float)bsCommision;
            }
        }
        return 0;
    }
}
