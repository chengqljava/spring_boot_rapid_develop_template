package com.hwsafe.weixin.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@ConditionalOnProperty(prefix = "hwsafe", name = "swagger-open", havingValue = "true")
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                // 当前包路径
                .apis(RequestHandlerSelectors
                        .basePackage("com.hwsafe.vguard.controller"))
                .paths(PathSelectors.any()).build();

    }

    // 构建api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("springBoot-Swagger2-vguard-mobile")
                // 版本号
                .version("1.0")
                // 描述
                .description("API 描述").build();
    }
}