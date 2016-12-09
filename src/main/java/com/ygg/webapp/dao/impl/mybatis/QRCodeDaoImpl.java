package com.ygg.webapp.dao.impl.mybatis;

import org.springframework.stereotype.Repository;

import com.ygg.webapp.dao.QRCodeDao;
import com.ygg.webapp.dao.impl.mybatis.base.BaseDaoImpl;
import com.ygg.webapp.entity.QRCodeEntity;

/**
 * @author wuhy
 * @date 创建时间：2016年5月18日 上午11:40:10
 */
@Repository
public class QRCodeDaoImpl extends BaseDaoImpl implements QRCodeDao
{
    
    @Override
    public QRCodeEntity findQRCodeByAccountId(int accountId)
    {
        return getSqlSession().selectOne("HqbsQRCodeMapper.findQRCodeByAccountId", accountId);
    }
    
}
