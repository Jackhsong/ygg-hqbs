package com.ygg.webapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ygg.webapp.service.ReserveDownloadService;
import com.ygg.webapp.util.FileOperateUtil;
import com.ygg.webapp.util.YggWebProperties;

/**
 * 对应PC上线前，用户预定下载app的链接
 * 
 * @author lihc
 *
 */
@Controller("downloadController")
public class DownloadController
{
    
    @Resource(name = "reserveDownloadService")
    private ReserveDownloadService reserveDownloadService;
    
    @RequestMapping("/download")
    public String todownload(@RequestParam(value = "phoneno", required = true, defaultValue = "0") String phonenum)
        throws Exception
    {
        
        return "download/index";
    }
    
    @RequestMapping("/download/reserveygg")
    @ResponseBody
    public String reserveRemind(@RequestParam(value = "phoneno", required = true, defaultValue = "0") String phonenum)
        throws Exception
    {
        if (phonenum != null && !phonenum.equals("") && !phonenum.equals("0"))// 放在前端去判断
        {
            reserveDownloadService.reserveDownload("{'phonenum':'" + phonenum + "'}");
        }
        return "{\"success\":\"1\"}"; // "redirect:/download";
    }
    
    /**
     * 下载app.apk
     * 
     * @throws Exception
     */
    @RequestMapping("/download1")
    public ModelAndView doloadYggApp(HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        
        String storeName = YggWebProperties.getInstance().getProperties("downandroidname"); // reserveDownloadService.getStoreName()
                                                                                            // ; // "YggAndroid.apk";
        String realName = YggWebProperties.getInstance().getProperties("downandroidname"); // reserveDownloadService.getRealName()
                                                                                           // ; // "YggAndroid.apk";
        String contentType = YggWebProperties.getInstance().getProperties("downloadcontenttype"); // reserveDownloadService.getDownLoadContentType()
                                                                                                  // ; //
                                                                                                  // "application/octet-stream";
        FileOperateUtil.download(request, response, storeName, contentType, realName);
        return null;
    }
}
