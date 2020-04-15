package com.hwsafe.license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author chengql
 * 
 *
 */
@Data
@Component
@PropertySource(value = "classpath:license-config.properties", ignoreResourceNotFound = false, encoding = "UTF-8")
public class LicenseClientConfig {
    /**
     * 证书subject
     */
    @Value("${license.subject:}")
    private String subject;

    /**
     * 公钥别称
     */
    @Value("${license.publicAlias:}")
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    @Value("${license.storePass:}")
    private String storePass;

    /**
     * 证书生成路径
     */
    @Value("${license.licensePath:}")
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    @Value("${license.publicKeysStorePath:}")
    private String publicKeysStorePath;

}
