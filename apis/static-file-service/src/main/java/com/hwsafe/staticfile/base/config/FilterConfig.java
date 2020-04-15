package com.hwsafe.staticfile.base.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hwsafe.staticfile.filter.CorsFilter;

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

    /**
     * @return 签名验证
     */
    /*
     * @Bean public FilterRegistrationBean signFilterRegistration() {
     * FilterRegistrationBean registration=new FilterRegistrationBean<>();
     * registration.setFilter(new ProtocolFilter()); //拦截规则
     * registration.addUrlPatterns("/sign/*"); //过滤器名称
     * registration.setName("signFilter"); //设置参数
     * //registration.addInitParameter("paramName", "paramValue"); //过滤器排序
     * //registration.setOrder(4); return registration; }
     * 
     *//**
        * @return 验证Token
        *//*
           * @Bean public FilterRegistrationBean tokenFilterRegistration() {
           * FilterRegistrationBean registration=new FilterRegistrationBean<>();
           * registration.setFilter(new AuthcFilter()); //拦截规则
           * registration.addUrlPatterns("/mobile/*"); //过滤器名称
           * registration.setName("authcFilter"); //设置参数
           * //registration.addInitParameter("paramName", "paramValue"); //过滤器排序
           * //registration.setOrder(4); return registration; }
           */

}
