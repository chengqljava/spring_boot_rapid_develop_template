package com.hwsafe.template.base.config;

import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

@Configuration
public class BeetlConfig {

		
		//MVC视图模板配置路径
		@Value("${beetl.templatesViewPath}") 
		private String templatesViewPath;
		
		//MVC视图模板后缀路径
		@Value("${beetl.templatesViewSuffix}") 
		private String templatesViewSuffix;
		
		
		 
		 /**
		    * 视图模板配置
		    * @return
		    */
		    @Bean(name = "beetlViewConfig")
		    public BeetlGroupUtilConfiguration getBeetlGroupViewConfiguration() {
		            BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		            ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
		            
		            ClassLoader loader = Thread.currentThread().getContextClassLoader();
		            if(loader==null){
		                loader = BeetlConfig.class.getClassLoader();
		            }
		           // beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource(propertiesPath));
		            ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,templatesViewPath);
		            beetlGroupUtilConfiguration.setResourceLoader(cploder);
		            
		            beetlGroupUtilConfiguration.init();
		            
		            return beetlGroupUtilConfiguration;
		    }
		    
		    /**
		     * 视图模板配置
		     * @return
		     */
		    @Bean(name = "beetlViewResolver")
		    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlViewConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		            BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		            beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		            beetlSpringViewResolver.setOrder(0);
		            beetlSpringViewResolver.setSuffix(templatesViewSuffix);
		            beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		            return beetlSpringViewResolver;
		    }
		
		
	/*	
	@Bean(initMethod = "init", name = "beetlConfig")
	public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
		BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
		try {
			//ClasspathResourceLoader cploder = new ClasspathResourceLoader(BeetlConfig.class.getClassLoader(), "/views");
			 ClasspathResourceLoader classpathResourceLoader = new ClasspathResourceLoader();
			beetlGroupUtilConfiguration.setResourceLoader(classpathResourceLoader);
			ResourcePatternResolver patternResolver = ResourcePatternUtils
					.getResourcePatternResolver(new DefaultResourceLoader());
			beetlGroupUtilConfiguration
					.setConfigFileResource(patternResolver.getResource("classpath:beetl.properties"));
			
					
			
			 * Map<String, Object> functionPackages = new HashMap<String,
			 * Object>(); functionPackages.put("user", new GlobalInfo()); 全局参数
			 * beetlGroupUtilConfiguration.setFunctionPackages(functionPackages)
			 * ;
			 

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		// 读取配置文件信息
		return beetlGroupUtilConfiguration;
	}

	@Bean(name = "beetlViewResolver")
	public BeetlSpringViewResolver getBeetlSpringViewResolver(
			@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
		BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
		beetlSpringViewResolver.setPrefix("/view/");
		beetlSpringViewResolver.setSuffix(".html");
		beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
		beetlSpringViewResolver.setOrder(0);
		beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
		beetlSpringViewResolver.setCache(false);
		return beetlSpringViewResolver;
	}*/
}
