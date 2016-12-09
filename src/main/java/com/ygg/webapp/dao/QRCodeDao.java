package com.ygg.webapp.dao;

import com.ygg.webapp.entity.QRCodeEntity;

/**
 * @author wuhy
 * @date 创建时间：2016年5月18日 上午11:39:03
 */
public interface QRCodeDao
{
    
    QRCodeEntity findQRCodeByAccountId(int accountId);
    
}
