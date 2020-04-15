package com.hwsafe.license.client;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hwsafe.license.config.LicenseClientConfig;

@Service
public class LicenseClientService {
    @Autowired
    private LicenseClientConfig licenseClientConfig;

    public void install(String urlPrefix) throws Exception {
        if (StringUtils.isNotBlank(licenseClientConfig.getLicensePath())) {

            LicenseVerifyParam param = new LicenseVerifyParam();
            param.setSubject(licenseClientConfig.getSubject());
            param.setPublicAlias(licenseClientConfig.getPublicAlias());
            param.setStorePass(licenseClientConfig.getStorePass());
            param.setLicensePath(
                    urlPrefix + licenseClientConfig.getLicensePath());
            param.setPublicKeysStorePath(
                    licenseClientConfig.getPublicKeysStorePath());

            LicenseVerify licenseVerify = new LicenseVerify();
            // 安装证书
            licenseVerify.install(param);

        }
    }

}
