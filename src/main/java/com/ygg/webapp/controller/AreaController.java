package com.ygg.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 省，市，区域的选择
 * 
 * @author lihc
 *
 */
@Controller("areaController")
@RequestMapping("/area")
public class AreaController
{
    
    @RequestMapping("/allprovince")
    public void getAllProvince()
    {
        
    }
}
