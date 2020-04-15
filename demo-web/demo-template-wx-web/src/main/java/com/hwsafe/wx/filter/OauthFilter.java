package com.hwsafe.wx.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.hwsafe.utils.CookieUtils;

public class OauthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String url = httpServletRequest.getRequestURI();
        String cookie = CookieUtils.getCookie(httpServletRequest, "name");
        // 获取tenantid 或者 appid
        // 判断是登录还是注册 跳转
        // 判断是否有cookie
        // 有 如果是 登录 或注册 跳转 index
        // 没有 如果是 登录 或注册 进行 openid获取 跳转
        if (StringUtils.isNoneBlank(cookie)) {
            if (url.startsWith("/login") || url.startsWith("/register")) {
                httpServletResponse.sendRedirect("/index");
            }
        } else {
            // response.sendRedirect("new.jsp");//重定向到
            if (url.startsWith("/login") || url.startsWith("/register")) {
                httpServletResponse
                        .sendRedirect("/wx/oauth?appid=wx4f9a6db2e0db25fc");
            }
        }
        chain.doFilter(httpServletRequest, httpServletResponse);

    }

    @Override
    public void destroy() {
    }

}
