package com.chengql.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.chengql")
@MapperScan("com.chengql")
@EnableSwagger2 // swagger 配置
/* @EnableAsync */// 开启异步任务支持
/* @EnableScheduling */// 开启对计划任务的支持
public class VguardMobileApplication {

    public static void main(String[] args) {
        SpringApplication.run(VguardMobileApplication.class, args);
    }

}
