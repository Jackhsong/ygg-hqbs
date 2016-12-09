package com.ygg.test.qqbs.spokesPersonService;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

// import com.ygg.common.services.image.ImgYunServiceIF;
import com.ygg.webapp.cache.CacheServiceIF;
import com.ygg.webapp.dao.OrderDao;
import com.ygg.webapp.dao.QRCodeDao;
import com.ygg.webapp.entity.OrderEntity;
import com.ygg.webapp.entity.OrderProductEntity;
import com.ygg.webapp.entity.OrderSourceChannel;
import com.ygg.webapp.entity.QRCodeEntity;
import com.ygg.webapp.entity.QqbsAccountEntity;
import com.ygg.webapp.exception.DaoException;
import com.ygg.webapp.service.spokesperson.impl.SpokesPersonServiceImpl;

/**
 * @author wuhy
 * @date 创建时间：2016年5月19日 下午7:57:26
 */
public class GetQRCodeTest
{
    private static SpokesPersonServiceImpl spkoesPersonService;
    
    private static final int TRUE_INT = 1;
    
    private static final int ACCOUNT_HAS_PERSISTENT_CODE = 100;
    
    private static final int ACCOUNT_IS_SPKOES_PERSON = 101;
    
    private static final int ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_IS_NOT_SPOKES_PERSON = 102;
    
    private static final int ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_HAS_ONE_AMOUNT_OVER_299 = 103;
    
    private static final int ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_HAS_TOTAL_AMOUNT_OVER_1000 = 104;
    
    private static final String TEST_RETURN_URL = "TEST_RETURN_URL";
    
    private static final String TEST_USER_IMAGE_URL = "http://yangege.b0.upaiyun.com/qqbsAccount/wechatImage/6ACE1C560C0995EC.jpg";
    
    private static final String TEST_QRCODE_IMAGE_URL =
        "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQF37zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzR6clVGdGZtb2l2YmszZUw5eGJMAAIETNg7VwMEAAAAAA==";
    
    private static final String TEST_NICK_NAME = "TEST_NICK";
    
    private static final String TEST_OPEN_ID = "TEST_OPEN_ID";
    
    private static final String TEST_APP_PATH = "D:\\Project\\HuYun\\ygg-hqbs\\target\\ygg-hqbs";
    
    private static final String TEMP_DIR = "E:\\temp";
    
    private static int INDEX = 0;
    
    private QqbsAccountEntity initAccount()
    {
        QqbsAccountEntity account = new QqbsAccountEntity();
        account.setImage(TEST_USER_IMAGE_URL);
        account.setNickName(TEST_NICK_NAME);
        account.setOpenId(TEST_OPEN_ID);
        return account;
        
    }
    
    @BeforeClass
    public static void init()
    {
        spkoesPersonService = new SpokesPersonServiceImpl();
//        spkoesPersonService.setUpyunImg(new ImgYunServiceIF()
//        {
//            return null;
//            @Override
//            public Map<String, String> uploadFile(URL arg0, String arg1)
//                throws IOException
//            {
//                return null;
//            }
//            
//            @Override
//            public Map<String, String> uploadFile(FileInputStream arg0, String arg1)
//                throws IOException
//            {
//                return null;
//            }
//            
//            @Override
//            public Map<String, String> uploadFile(File arg0, String arg1)
//                throws IOException
//            {
//                return null;
//            }
//            
//            @Override
//            public Map<String, String> uploadFile(byte[] imageBuff, String arg1)
//                throws IOException
//            {
//                File apple = new File(TEMP_DIR + File.separator + INDEX++ + ".jpg");// 把字节数组的图片写到另一个地方
//                FileOutputStream fos = new FileOutputStream(apple);
//                fos.write(imageBuff);
//                fos.flush();
//                fos.close();
//                Map<String, String> result = new HashMap<String, String>();
//                result.put("status", "success");
//                result.put("url", TEST_RETURN_URL);
//                return result;
//            }
//        });
        spkoesPersonService.setCacheService(new CacheServiceIF()
        {
            
            @Override
            public <T> void updateCache(String k, T v, int pExpire)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void timerClearCache()
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public int size()
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public boolean isInCache(String k)
            {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public <T> T getCache(String k)
            {
                return null;
            }
            
            @Override
            public void clearCache(String k)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void clear()
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public <T> void addCache(String k, T v, int pExpire)
            {
                // TODO Auto-generated method stub
                
            }
        });
        spkoesPersonService.setQrCodeDao(new QRCodeDao()
        {
            
            @Override
            public QRCodeEntity findQRCodeByAccountId(int accountId)
            {
                if (accountId == ACCOUNT_HAS_PERSISTENT_CODE)
                {
                    QRCodeEntity entity = new QRCodeEntity();
                    entity.setQrCodeUrl(TEST_QRCODE_IMAGE_URL);
                    return entity;
                }
                throw new Error("Error input");
            }
        });
        spkoesPersonService.setOrderDao(new OrderDao()
        {
            
            @Override
            public int updateOrderUserCancel(int orderId, int accountId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderSuccess(int orderId, int accountId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderStatus(int orderId, String payTime)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderPayChannel(int orderId, int accountId, byte payChannel)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderNotDelivery(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderFreezeRecord(int orderFreezeRecordId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderFreeze(int orderId, int lock)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateOrderExpireTime(int orderId, String expireTime)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int updateLockTime(int orderId, long validTime)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int resetOrderCouponAndIntegral(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int insertOrderFreeze(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public Map<String, Object> getOneRealPriceByAid(int accountId)
            {
                if (accountId == ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_HAS_ONE_AMOUNT_OVER_299)
                {
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("realPrice", 300d);
                    return resultMap;
                }
                return null;
                
            }
            
            @Override
            public float getHqbsDraw(int productId)
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public List<Map<String, Object>> findYesDeliveryOrders()
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<Map<String, Object>> findYesDeliveryMoreThan15Orders()
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public String findValidTimeByOid(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public float findSumPriceByOrderIds(List<Integer> orderIds)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public float findProductCostByProductId(int productId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public Map<String, Object> findPayCouponByOrderIds(List<Integer> orderIds)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int findOrderStatusCount(int status, int accountId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public OrderSourceChannel findOrderSourceChannelByMark(String mark)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<OrderEntity> findOrderRefund(int accountId, byte[] status, int page)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<Map<String, Object>> findOrderProductInfosByOId(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Map<String, Object> findOrderProductInfosById(int orderProductId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public Map<String, Object> findOrderInfoById(int id)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int findOrderIdByStatusAndAccountCouponId(int accountCouponId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public Map<String, Object> findOrderFreezeByOrderId(int id)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<OrderEntity> findOrderByStatusAndPage(int accountId, byte[] status, int page)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public OrderEntity findOrderById(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<OrderEntity> findNotPayOrderByOIdAndStatus(List<Integer> orderIds)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<OrderEntity> findNotPayOrderByIds(List<Integer> orderIds)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<OrderEntity> findNotPayOrderByAIdAndStatus(int accountId, int status)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<Integer> findLockProductIdsByOId(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public List<Map<String, Object>> findLockCountInfosByOId(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public int findCountByStatus(int accountId, byte[] status)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public List<Map<String, Object>> findBirdexOrderLogisticsByOrderId(int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public float findAllRealPriceByAidAndPayTime(int accountId, String payTimeBegin, String payTimeEnd)
                throws DaoException
            {
                return 0;
            }
            
            @Override
            public float findAllRealPriceByAidAndBidAndPayTime(int accountId, List<String> brandIds, String payTimeBegin, String payTimeEnd)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public float findAllRealPriceByAidAndBid(int accountId, List<String> brandIds)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public float findAllRealPriceByAid(int accountId)
                throws DaoException
            {
                if (accountId == ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_HAS_TOTAL_AMOUNT_OVER_1000)
                {
                    return 1000;
                }
                return 0;
            }
            
            @Override
            public List<OrderEntity> findAllOrderStatusByIds(List<Integer> orderIds)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return null;
            }
            
            @Override
            public boolean existsProductBlacklist(int orderId, int type)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return false;
            }
            
            @Override
            public int deleteLockCount(int productId, int orderId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int countHistoryOrderByAccountIdAndProductId(int accountId, int productId)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int addOrderSourceChannel(OrderSourceChannel osc)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int addOrderProduct(OrderProductEntity ope)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int addOrder(OrderEntity oe)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int addLockTime(int orderId, long validTime)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
            
            @Override
            public int addLockCount(int productId, int orderId, int count)
                throws DaoException
            {
                // TODO Auto-generated method stub
                return 0;
            }
        });
        
    }
    
    @Test
    public void testGetQRCodeWhenAccountHasPersistentQRCode()
        throws Exception
    {
        QqbsAccountEntity accountHasPersistentQRCode = initAccount();
        accountHasPersistentQRCode.setAccountId(ACCOUNT_HAS_PERSISTENT_CODE);
        accountHasPersistentQRCode.setHasPersistentQRCode(TRUE_INT);
        assertEquals(spkoesPersonService.getQRCode(accountHasPersistentQRCode, TEST_APP_PATH), TEST_RETURN_URL);
    }
    
    @Test
    public void testGetQRCodeWhenAccountHasNoPersistentQRCodeAndIsSpkoesPerson()
        throws Exception
    {
        QqbsAccountEntity account = initAccount();
        account.setAccountId(ACCOUNT_IS_SPKOES_PERSON);
        account.setSpokesPerson(TRUE_INT);
        assertEquals(spkoesPersonService.getQRCode(account, TEST_APP_PATH), TEST_RETURN_URL);
    }
    
    @Test
    public void testGetQRCodeWhenAccountHasNoPersistentQRCodeAndIsSpkoesPersonByOneAmountOver299()
        throws Exception
    {
        QqbsAccountEntity account = initAccount();
        account.setAccountId(ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_HAS_ONE_AMOUNT_OVER_299);
        assertEquals(spkoesPersonService.getQRCode(account, TEST_APP_PATH), TEST_RETURN_URL);
    }
    
    @Test
    public void testGetQRCodeWhenAccountHasNoPersistentQRCodeAndIsSpkoesPersonByTotalAmountOver1000()
        throws Exception
    {
        QqbsAccountEntity account = initAccount();
        account.setAccountId(ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_HAS_TOTAL_AMOUNT_OVER_1000);
        assertEquals(spkoesPersonService.getQRCode(account, TEST_APP_PATH), TEST_RETURN_URL);
    }
    
    @Test
    public void testGetQRCodeWhenAccountHasNoPersistentQRCodeAndIsNotSpkoesPerson()
        throws Exception
    {
        QqbsAccountEntity account = initAccount();
        account.setAccountId(ACCOUNT_HAS_NO_PERSISTENT_QRCODE_AND_IS_NOT_SPOKES_PERSON);
        assertEquals(spkoesPersonService.getQRCode(account, TEST_APP_PATH), null);
    }
    
}
