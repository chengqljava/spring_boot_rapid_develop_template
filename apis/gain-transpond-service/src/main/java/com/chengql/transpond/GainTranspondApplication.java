package com.hwsafe.transpond;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author chengql 获取数据并转发到指定IP
 *
 */
@SpringBootApplication
@ComponentScan("com.hwsafe")
@MapperScan("com.hwsafe")
@EnableSwagger2 // swagger 配置
/* @EnableAsync */// 开启异步任务支持
@EnableScheduling // 开启对计划任务的支持
public class GainTranspondApplication {

    public static void main(String[] args) {
        SpringApplication.run(GainTranspondApplication.class, args);
    }

}
