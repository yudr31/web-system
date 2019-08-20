package com.spring.boot.web.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuderen
 * @version 2018/9/20 16:00
 */
//@Aspect
//@Component
public class AddParamInterceptor {

    @AfterReturning(returning = "object", pointcut = "execution(public * com.spring.boot.web.controller.*Controller.*(..))")
    public Object addParamToModelAndView(Object object){
        if (object instanceof ModelAndView){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            ModelAndView mav = (ModelAndView) object;
            mav.addObject("callbackType",request.getParameter("callbackType"));
            String forwardUrl = request.getParameter("forwardUrl");
            Object newId = mav.getModelMap().get("newId");
            if (StringUtils.isNotBlank(forwardUrl) && null != newId){
                forwardUrl += newId;
            }
            mav.addObject("forwardUrl",forwardUrl);
            mav.addObject("navTabTitle",request.getParameter("navTabTitle"));
            return mav;
        }
        return object;
    }

}
