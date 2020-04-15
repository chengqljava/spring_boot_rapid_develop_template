package com.hwsafe.wx.base.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hwsafe.wx.filter.CorsFilter;
import com.hwsafe.wx.filter.OauthFilter;

@Configuration
public class FilterConfig {

    /**
     * @return 跨域
     */
    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("corsFilter");
        // 设置参数
        // registration.addInitParameter("paramName", "paramValue");
        // 过滤器排序
        // registration.setOrder(4);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<OauthFilter> userFilterRegistration() {
        FilterRegistrationBean<OauthFilter> registration = new FilterRegistrationBean<OauthFilter>();
        // 注入过滤器
        registration.setFilter(new OauthFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("OauthFilter");
        // 过滤器排序
        // registration.setOrder(4);
        return registration;
    }
}
