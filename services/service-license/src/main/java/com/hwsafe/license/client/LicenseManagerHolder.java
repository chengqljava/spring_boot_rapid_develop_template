package com.hwsafe.license.client;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hwsafe.license.base.CustomLicenseManager;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * de.schlichtherle.license.LicenseManager的单例
 *
 * @author zifangsky
 * @date 2018/4/19
 * @since 1.0.0
 */
public class LicenseManagerHolder {
    private final static Logger logger = LoggerFactory
            .getLogger(LicenseManagerHolder.class);

    private static volatile LicenseManager LICENSE_MANAGER;

    public static LicenseManager getInstance(LicenseParam param) {
        if (LICENSE_MANAGER == null) {
            synchronized (LicenseManagerHolder.class) {
                if (LICENSE_MANAGER == null) {
                    LICENSE_MANAGER = new CustomLicenseManager(param);
                }
            }
        }

        return LICENSE_MANAGER;
    }

    /**
     * @param param
     * @return 重启服务验证是否已存在 证书安装
     */
    public static void defaultInitInstance(String subject, String publicAlias,
            String storePass, String licensePath, String publicKeysStorePath) {

        LicenseVerifyParam param = new LicenseVerifyParam();
        param.setSubject(subject);
        param.setPublicAlias(publicAlias);
        param.setStorePass(storePass);
        param.setLicensePath(licensePath);
        param.setPublicKeysStorePath(publicKeysStorePath);

        LicenseVerify licenseVerify = new LicenseVerify();
        // 安装证书
        try {
            logger.info("++++++++ 开始初始化证书 ++++++++");
            licenseVerify.install(param);
            logger.info("++++++++ 初始化证书结束 ++++++++");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
