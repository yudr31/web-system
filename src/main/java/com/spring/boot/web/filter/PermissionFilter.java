package com.spring.boot.web.filter;

import com.spring.boot.web.manager.PermissionManager;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuderen
 * @version 2018/9/18 17:58
 */
public class PermissionFilter implements Filter {

    @Autowired
    private PermissionManager permissionManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取请求链接
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        if (servletPath.startsWith("/")) {
            servletPath = servletPath.substring(1);
        }
        //验证权限
//        if (this.verifyPermission(servletPath)) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            response.setCharacterEncoding("utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.println("{\"statusCode\":\"300\", \"message\":\"您没有相应的权限，请联系管理员。\"}");
//            writer.flush();
//            writer.close();
//        }
    }

    /**
     * 验证用户权限
     *
     * @param servletPath
     * @return
     */
    protected boolean verifyPermission(String servletPath) {
        // 如果是根路径下的子页面，则通过
        if (0 > servletPath.indexOf("/")) {
            return true;
        }
        // 如果是不需要登录的页面，则通过
        for (String url : ignoreLoginUrls) {
            if (servletPath.startsWith(url)) {
                return true;
            }
        }
        //验证链接权限
        return permissionManager.verifyPermissionByHref(servletPath);
    }

    @Override
    public void destroy() {

    }

    protected List<String> ignoreLoginUrls = new ArrayList();

}
