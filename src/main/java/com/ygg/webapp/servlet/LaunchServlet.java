package com.ygg.webapp.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.ygg.webapp.dao.PlatformConfigDao;
import com.ygg.webapp.sdk.unionpay.SDKConfig;
import com.ygg.webapp.util.CommonProperties;
//import com.unionpay.acp.sdk.SDKConfig;
import com.ygg.webapp.util.ContextPathUtil;
import com.ygg.webapp.util.FileOperateUtil;
import com.ygg.webapp.util.FreeMarkerUtil;
import com.ygg.webapp.util.SpringBeanUtil;
import com.ygg.webapp.util.YggWebProperties;

/**
 * 初始化的Servlet
 * 
 * @author lihc
 *         
 */
public class LaunchServlet extends HttpServlet
{
    
    Logger log = Logger.getLogger(LaunchServlet.class);
    
    private static final long serialVersionUID = 1L;
    
    private PlatformConfigDao platformConfigDao = null;
    
    /**
     * 配置freemarker模版的位置 在init里加载也主要是为保证Configuration实例唯一
     */
    @Override
    public void init(ServletConfig config)
        throws ServletException
    {
        log.info("--------LaunchServlet--------init-------------");
        SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
        super.init(config);
        String templateDir = config.getInitParameter("templateDir");
        String yggcontextPath = config.getInitParameter("yggcontextPath");
        String downLoadDir = config.getInitParameter("downLoadDir");
        ContextPathUtil.yggcontextPath = yggcontextPath;
        FileOperateUtil.downloadDir = downLoadDir;
        FreeMarkerUtil.initConfig(config.getServletContext(), templateDir);
        
        SpringBeanUtil.initServletContext(config.getServletContext());
        YggWebProperties.getInstance().init();
        
        platformConfigDao = (PlatformConfigDao)SpringBeanUtil.getBean("platformConfigDao");
        
        new Thread()
        {
            public void run()
            {
                log.info("更新平台配置线程启动");
                while (true)
                {
                    try
                    {
                        List<Map<String, Object>> configMaps = platformConfigDao.findAllPlateformConfig();
                        for (Map<String, Object> configMap : configMaps)
                        {
                            String key = configMap.get("key").toString();
                            String value = configMap.get("value").toString();
                            if (key.equals("is_register_coupon"))
                            {
                                CommonProperties.isRegisterCoupon = value.equals("1") ? true : false;
                            }
                            else if (key.equals("static_css_version"))
                            {
                                CommonProperties.staticCssVersion = value;
                            }
                            else if (key.equals("static_js_version"))
                            {
                                CommonProperties.staticJsVersion = value;
                            }
                            else if (key.equals("weixin_gege"))
                            {
                                CommonProperties.weixinGege = value;
                            }
                            else if (key.equals("order_lowest_check_money"))
                            {
                                CommonProperties.orderLowestCheckMoney = Float.valueOf(value);
                            }
                            
                        }
                        Thread.sleep(1000 * 60 * 5);
                    }
                    catch (Exception e)
                    {
                        log.error("更新平台配置线程出错！", e);
                    }
                }
            }
        }.start();
        
    }
}
