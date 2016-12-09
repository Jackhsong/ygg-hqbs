package com.ygg.webapp.service.impl;

import com.ygg.webapp.dao.AccountAvailablePointRecordDao;
import com.ygg.webapp.dao.AccountDao;
import com.ygg.webapp.entity.AccountAvailablePointRecordEntity;
import com.ygg.webapp.entity.AccountEntity;
import com.ygg.webapp.service.IntegralService;
import com.ygg.webapp.util.CommonEnum;
import com.ygg.webapp.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;

@Service("integralService")
public class IntegralServiceImpl implements IntegralService
{
    @Resource
    private AccountDao accountDao;
    
    @Resource
    private AccountAvailablePointRecordDao accountAvailablePointRecordDao;
    
    @Override
    public Map<String, Object> getIntegralInfo(int accountId, boolean getIntegralDetail)
        throws Exception
    {
        Map<String, Object> result = new HashMap<>();
        int totalIntegral = 0;
        AccountEntity ae = accountDao.findAccountById(accountId);
        if (ae != null)
        {
            totalIntegral = ae.getAvailablePoint();
            if (getIntegralDetail)
            {
                // 积分明细
                List<Map<String, Object>> rows = new ArrayList<>();
                List<AccountAvailablePointRecordEntity> records = accountAvailablePointRecordDao.findAvailablePointRecordsByAid(accountId);
                if (records.size() > 0)
                {
                    for (AccountAvailablePointRecordEntity it : records)
                    {
                        Map<String, Object> row = new HashMap<>();
                        row.put("type", CommonEnum.ACCOUNT_OPERATE_POINT_TYPE.getDescriptionByOrdinal(it.getOperateType()));
                        int arithmeticType = it.getArithmeticType();
                        int operatePoint = it.getOperatePoint();
                        if (arithmeticType == 2)
                        {
                            // 减
                            operatePoint = 0 - operatePoint;
                        }
                        row.put("operatePoint", operatePoint);
                        row.put("totalPoint", it.getTotalPoint());
                        Date createTime = CommonUtil.string2Date(it.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                        row.put("operateTime", CommonUtil.date2String(createTime, "yyyy-MM-dd"));
                        rows.add(row);
                    }
                }
                result.put("pointDetails",rows);
            }
        }
        result.put("totalIntegral", totalIntegral);
        return result;
    }
}
