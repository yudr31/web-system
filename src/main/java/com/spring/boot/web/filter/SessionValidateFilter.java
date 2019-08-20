package com.spring.boot.web.filter;

import com.spring.boot.web.manager.UserManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/11 17:00
 */
public class SessionValidateFilter implements Filter {

    protected List<String> ignoreLoginUrls = new ArrayList();
    @Autowired
    private UserManager userManager;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/")) {
            servletPath = servletPath.substring(1);
        }
        boolean isPassed = false;

        // 如果是根路径下的子页面，则通过
        if(0 > servletPath.indexOf("/")) {
            isPassed = true;
        }
        if (!isPassed){
            // 如果是不需要登录的页面，则通过
            for (String url : ignoreLoginUrls) {
                if(servletPath.startsWith(url)) {
                    isPassed = true;
                }
            }
        }
        if (!isPassed){
            if(userManager.authLoginResponse()) {
                isPassed = true;
            }
        }
        filterChain.doFilter(request, response);
//        if(isPassed) {
//            filterChain.doFilter(request, response);
//        } else {
//            if(isAjaxRequest(request)) {
//                response.setCharacterEncoding("utf-8");
//                PrintWriter writer = response.getWriter();
//                writer.println("{\"statusCode\":\"301\", \"message\":\"Session超时，请重新登录\"}");
//                writer.flush();
//                writer.close();
//            } else {
//                String contextPath = request.getContextPath();
//                response.sendRedirect(contextPath + "/login");
//            }
//        }
    }

    public static Boolean isAjaxRequest(HttpServletRequest request){
        String accept = request.getHeader("accept");
        return StringUtils.isNotEmpty(accept) && accept.indexOf("application/json") == -1;
    }

    @Override
    public void destroy() {
    }

}
