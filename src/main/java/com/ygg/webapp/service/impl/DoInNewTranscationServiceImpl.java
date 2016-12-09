package com.ygg.webapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.dao.AccountSuccessOrderRecordDao;
import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.OrderProductRefundDao;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.entity.AccountSuccessOrderRecordEntity;
import com.ygg.webapp.entity.OrderEntity;
import com.ygg.webapp.exception.ServiceException;
import com.ygg.webapp.service.DoInNewTranscationService;
import com.ygg.webapp.util.CommonEnum;

@Service("doInNewTranscationService")
public class DoInNewTranscationServiceImpl implements DoInNewTranscationService
{
    private static Logger log = Logger.getLogger(DoInNewTranscationServiceImpl.class);
    
    @Resource(name = "orderDao")
    private OrderDao odi = null;
    
    @Resource(name = "accountDao")
    private AccountDao adi = null;
    
    @Resource(name = "orderProductRefundDao")
    private OrderProductRefundDao orderProductRefundDao;
    
    @Resource(name = "accountSuccessOrderRecordDao")
    private AccountSuccessOrderRecordDao accountSuccessOrderRecordDao;
    
    /**
     * 
     * 
     * @param map
     */
    public void autoConfirmReceiptInNewTranscation(Map<String, Object> map)
    {
        
        int orderId = Integer.parseInt(map.get("Id") + "");
        int accountId = Integer.parseInt(map.get("accountId") + "");
        
        int updateResult = this.odi.updateOrderSuccess(orderId, accountId);
        if (updateResult == 0)
        {
            log.error("autoConfirmReceipt没有匹配,orderId：" + orderId + ",accountId：" + accountId + ",status：" + map.get("status") + ",sendTime:" + map.get("sendTime"));
            return;
        }
        
        // ----------------计算等级 begin---------------------
        
        OrderEntity oe = odi.findOrderById(orderId);
        AccountEntity ae = adi.findAccountById(accountId);
        
        // 计算订单下满足条件的金额 主要针对退款
        float availableRealPrice = oe.getRealPrice();
        List<Map<String, Object>> refunds = orderProductRefundDao.findRefundsByOrderId(oe.getId());
        if (refunds.size() > 0)
        {
            Map<Integer, Integer> refundMap = new HashMap<>();
            for (Map<String, Object> refund : refunds)
            {
                int orderProductId = Integer.valueOf(refund.get("orderProductId") + "");
                int count = Integer.valueOf(refund.get("count") + "");
                refundMap.put(orderProductId, count);
            }
            
            float totalPrice = 0; // 订单商品总价
            float refundTotalPrice = 0; // 发生退款商品总价
            List<Map<String, Object>> ops = odi.findOrderProductInfosByOId(oe.getId());
            for (Map<String, Object> op : ops)
            {
                int id = Integer.valueOf(op.get("id") + "");
                int productCount = Integer.valueOf(op.get("product_count") + "");
                float salePrice = Float.valueOf(op.get("salesPrice") + "");
                if (refundMap.keySet().contains(id))
                {
                    // 存在退款
                    int refundCount = refundMap.get(id);
                    refundTotalPrice += salePrice * refundCount;
                }
                totalPrice += productCount * salePrice;
            }
            availableRealPrice = (availableRealPrice * ((totalPrice - refundTotalPrice) / totalPrice));
        }
        
        // 计算会员等级信息
        if (availableRealPrice > 0)
        {
            // 计算等级并更新用户总交易成功金额 插入成功交易金额记录
            float totalSuccessPriceAndNowOrderPrice = ae.getTotalSuccessPrice() + availableRealPrice;
            int level = 0;
            if (totalSuccessPriceAndNowOrderPrice >= CommonEnum.LEVEL.V3.getLimitMoney())
            {
                level = CommonEnum.LEVEL.V3.getCode();
            }
            else if (totalSuccessPriceAndNowOrderPrice >= CommonEnum.LEVEL.V2.getLimitMoney())
            {
                level = CommonEnum.LEVEL.V2.getCode();
            }
            else if (totalSuccessPriceAndNowOrderPrice >= CommonEnum.LEVEL.V1.getLimitMoney())
            {
                level = CommonEnum.LEVEL.V1.getCode();
            }
            else
            {
                level = CommonEnum.LEVEL.V0.getCode();
            }
            
            Map<String, Object> para = new HashMap<>();
            para.put("id", ae.getId());
            para.put("level", level);
            para.put("totalSuccessPrice", totalSuccessPriceAndNowOrderPrice);
            if (adi.updateAccountInfoById(para) == 0)
            {
                log.error("updateAccountInfoById失败");
                ServiceException se = new ServiceException("更新用户会员等级信息异常");
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                throw se;
            }
            
            AccountSuccessOrderRecordEntity record = new AccountSuccessOrderRecordEntity();
            record.setAccountId(ae.getId());
            record.setOperateType(1); // 用户确认
            record.setOrderId(oe.getId());
            record.setRealPrice(availableRealPrice);
            record.setTotalRealPrice(totalSuccessPriceAndNowOrderPrice);
            if (accountSuccessOrderRecordDao.addAccountSuccessOrderRecord(record) == 0)
            {
                log.error("addAccountSuccessOrderRecord失败");
                ServiceException se = new ServiceException("交易成功订单记录插入异常");
                se.putMap("status", CommonEnum.BUSINESS_RESPONSE_STATUS.FAILED.getValue());
                se.putMap("errorCode", CommonEnum.BUSINESS_RESPONSE_ERRORCODE.UNKNOWN.getValue());
                throw se;
            }
        }
        
        // --------------计算等级 end -----------------------
    }
}
