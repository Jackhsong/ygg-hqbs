package com.ygg.webapp.exception;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;

/**
 * Spring MVC 统一异常处理 在Controller层中 throws 出的所有异常都会在此得到处理
 *
 *
 * @author lihc
 *
 */
public class ExceptionHandler extends HandlerExceptionResolverComposite
{
    
    Logger logger = Logger.getLogger(ExceptionHandler.class);
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    {
        logger.error("--------ExceptionHandler-------------Exception:", ex);
        ModelAndView mv = new ModelAndView();
        if (ex instanceof BusException)
        {
            BusException be = (BusException)ex;
            mv.setViewName(be.getViewName());
            Map<String, Object> map = be.getModelObject();
            if (map != null && !map.isEmpty())
            {
                for (Map.Entry<String, Object> entry : map.entrySet())
                {
                    mv.addObject(entry.getKey(), entry.getValue());
                }
            }
            String errorCode = be.getErrorCode();
            if (null != errorCode && errorCode.equals("1"))
                mv.addObject("errorCode", be.getMessage());
        }
        else
        {
            String uri = request.getRequestURI();
            if (uri.indexOf("/orderrefund") > -1)
            {
                mv.setViewName("error/500");
                Cookie[] cookies = request.getCookies();
                if (cookies != null)
                {
                    for (Cookie cookie : cookies)
                    {
                        if (cookie.getName() != null && cookie.getName().equals("appinfo"))
                        {
                            mv.setViewName("error/app500");
                            break;
                        }
                    }
                }
            }
            else
            {
                mv.setViewName("error/500"); // 如 nullpointerexception 服务器内部运行时异常
            }
        }
        return mv;
    }
    
}
