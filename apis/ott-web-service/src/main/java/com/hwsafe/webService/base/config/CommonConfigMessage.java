package com.hwsafe.webService.base.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "common")
public class CommonConfigMessage {

    /**
     * IP 白名单列表
     */
    private String ipWhiteList;
    /**
     * 公共参数常量键值对
     */
    private Map<String, String> parameterConstant = new HashMap<String, String>();

}
