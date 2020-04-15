//package com.hwsafe.vguard.base.config;
//
//import org.beetl.core.resource.ClasspathResourceLoader;
//import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
//import org.beetl.ext.spring.BeetlSpringViewResolver;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.DefaultResourceLoader;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternUtils;
//
//@Configuration
//public class BeetlConfig {
//
//
//
//		//MVC视图模板配置路径
//		@Value("${beetl.templatesViewPath}")
//		private String templatesViewPath;
//
//		//MVC视图模板后缀路径
//		@Value("${beetl.templatesViewSuffix}")
//		private String templatesViewSuffix;
//
//
//
//		 /**
//		    * 视图模板配置
//		    * @return
//		    */
//		    @Bean(name = "beetlViewConfig")
//		    public BeetlGroupUtilConfiguration getBeetlGroupViewConfiguration() {
//		            BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
//		            ResourcePatternResolver patternResolver = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader());
//
//		            ClassLoader loader = Thread.currentThread().getContextClassLoader();
//		            if(loader==null){
//		                loader = BeetlConfig.class.getClassLoader();
//		            }
//		            //beetlGroupUtilConfiguration.setConfigFileResource(patternResolver.getResource(propertiesPath));
//		            ClasspathResourceLoader cploder = new ClasspathResourceLoader(loader,templatesViewPath);
//		            beetlGroupUtilConfiguration.setResourceLoader(cploder);
//
//		            beetlGroupUtilConfiguration.init();
//
//		            return beetlGroupUtilConfiguration;
//		    }
//
//		    /**
//		     * 视图模板配置
//		     * @return
//		     */
//		    @Bean(name = "beetlViewResolver")
//		    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlViewConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
//		            BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
//		            beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
//		            beetlSpringViewResolver.setOrder(0);
//		            beetlSpringViewResolver.setSuffix(templatesViewSuffix);
//		            beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
//		            return beetlSpringViewResolver;
//		    }
//
//
//
//}
