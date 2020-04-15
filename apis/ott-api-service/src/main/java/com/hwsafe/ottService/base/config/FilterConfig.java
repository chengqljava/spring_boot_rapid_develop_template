package com.hwsafe.ottService.base.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hwsafe.ottService.filter.AuthcFilter;
import com.hwsafe.ottService.filter.CorsFilter;
import com.hwsafe.ottService.filter.ProtocolFilter;

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
        registration.setOrder(1);
        return registration;
    }

    /**
     * @return 配置信息 验证
     */
    @Bean
    public FilterRegistrationBean authcFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthcFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("authcFilter");
        // 设置参数
        // registration.addInitParameter("paramName", "paramValue");
        // 过滤器排序
        registration.setOrder(2);
        return registration;
    }

//API限流 拦截器 未实现
    /**
     * @return 签名验证
     */
    @Bean
    public FilterRegistrationBean signFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean<>();
        registration.setFilter(new ProtocolFilter());
        // 拦截规则
        registration.addUrlPatterns("/*");
        // 过滤器名称
        registration.setName("signFilter");
        // 设置参数
        // registration.addInitParameter("paramName", "paramValue");
        // 过滤器排序
        registration.setOrder(3);
        return registration;
    }

}
