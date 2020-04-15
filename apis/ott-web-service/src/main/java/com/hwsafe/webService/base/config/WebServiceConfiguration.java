package com.hwsafe.webService.base.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hwsafe.webService.interceptor.CommonParamInterceptor;
import com.hwsafe.webService.interceptor.IpAddressInInterceptor;
import com.hwsafe.webService.service.PointRealTimeService;

/**
 * WS服务配置。
 * 
 */
@Configuration
public class WebServiceConfiguration {
    @Autowired
    private PointRealTimeService pointRealTimeService;
    @Autowired
    private Bus bus;

    @Bean(name = "cxfServlet")
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean
    public IpAddressInInterceptor ipAddressInInterceptor() {
        return new IpAddressInInterceptor();
    }

    @Bean
    public CommonParamInterceptor commonParamInterceptor() {
        return new CommonParamInterceptor();
    }

    /* 注册服务示例 */
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, pointRealTimeService);
        endpoint.publish("/RTDWebService");
        // 增加拦截器
        // IP 白名单
        // endpoint.getInInterceptors().add(ipAddressInInterceptor());
        // 公共参数
        // endpoint.getInInterceptors().add(commonParamInterceptor());
        return endpoint;
    }

}
