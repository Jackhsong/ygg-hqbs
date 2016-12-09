package com.ygg.test.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.ygg.webapp.sdk.weixin.util.QRCodeUtil;

/**
 * @author wuhy
 * @date 创建时间：2016年5月17日 下午2:14:36
 */
public class QRCodeUtilTest
{
    private static final int TEST_ACCOUNT_ID = 100000;
    
    private static final String APP_ID = "wxff7d85e6ce5725cc";
    
    private static final String APP_SECRET = "09f20ca7c2a12d3fee1a92a4fa598f67";
    
    @Test
    public void testBuildQRCode()
    {
        assertEquals(QRCodeUtil.buildTemporaryQRCode(TEST_ACCOUNT_ID, APP_ID, APP_SECRET) != null, true);
    }
}
