package com.hwsafe.template.base.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.hwsafe.validate.LinuxCondition;



@Configuration
@Conditional(LinuxCondition.class)
public class WebMvcLinuxConfg extends WebMvcConfigurationSupport   {
	@Value("${linux.image.filePath}")
	private String filePath;
	@Value("${linux.image.dataBasePath}")
	private String dataBasePath;
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(31536000);
	        registry.addResourceHandler(dataBasePath+"**").addResourceLocations("file:"+filePath);
	    }
	    
	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/ajaxtest").setViewName("index");
	        super.addViewControllers(registry);
	    }
	    @Override
	    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
	    configurer.favorPathExtension(true).
	            ignoreAcceptHeader(true).
	            mediaType("json", MediaType.APPLICATION_JSON);
	    }
}
