package com.spring.boot.web.interceptor;

import com.spring.boot.common.annotation.permission.PermissionLimit;
import com.spring.boot.web.filter.SessionValidateFilter;
import com.spring.boot.web.manager.PermissionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author yuderen
 * @version 2018/9/19 14:21
 */
@Aspect
@Component
public class PermissionLimitInterceptor {

    @Autowired
    private PermissionManager permissionManager;

    @Pointcut("@annotation(com.spring.boot.common.annotation.permission.PermissionLimit)")
    private void permissionLimit(){}

    @Around("permissionLimit()")
    public Object permissionLimitCheck(ProceedingJoinPoint joinPoint){
        HttpServletRequest request = getHttpServletRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object target = joinPoint.getTarget();
        try {
            Boolean verificationPassed = false;
            Method method = target.getClass().getMethod(signature.getName(), signature.getParameterTypes());
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations){
                if (annotation instanceof PermissionLimit){
                    PermissionLimit permissionLimit = (PermissionLimit) annotation;
                    verificationPassed = permissionManager.verifyPermissionByHref(permissionLimit);
                    break;
                }
            }
            if (verificationPassed){
                return joinPoint.proceed();
            } else {
                if(SessionValidateFilter.isAjaxRequest(request)) {
                    ModelAndView mav = new ModelAndView("/template/ajaxDone");
                    mav.addObject("statusCode", 300);
                    mav.addObject("message", "抱歉！您无权限访问。");
                    mav.addObject("resultDetail", "");
                    return mav;
                } else {
                    return "index/permission";
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private HttpServletRequest getHttpServletRequest(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();
        return request;
    }

}
