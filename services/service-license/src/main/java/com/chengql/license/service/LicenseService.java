package com.chengql.license.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chengql.license.base.AbstractServerInfos;
import com.chengql.license.base.LicenseCheckModel;
import com.chengql.license.base.LinuxServerInfos;
import com.chengql.license.base.WindowsServerInfos;
import com.chengql.license.client.LicenseManagerHolder;
import com.chengql.license.config.LicenseClientConfig;

@Service
public class LicenseService {
    @Autowired
    private LicenseClientConfig licenseClientConfig;

    /**
     * @return 获取操作系统参数
     */
    public LicenseCheckModel systemInfo() {
        // 操作系统类型
        String os = System.getProperty("os.name");
        AbstractServerInfos abstractServerInfos = null;
        // 根据不同操作系统类型选择不同的数据获取方法
        if (os.toLowerCase().startsWith("win")) {
            abstractServerInfos = new WindowsServerInfos();
        } else {// 其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

    /**
     * @param param
     * @return 生成证书
     */
    public boolean generateLicense(LicenseCreatorParam param) {
        LicenseCreator licenseCreator = new LicenseCreator(param);
        return licenseCreator.generateLicense();
    }

    /**
     * @param filePath
     *            默认安装证书
     */
    public void defaultInitInstance(String filePath) {
        try {
            File file = new File(
                    filePath + licenseClientConfig.getLicensePath());
            if (file.exists()) {
                LicenseManagerHolder.defaultInitInstance(
                        licenseClientConfig.getSubject(),
                        licenseClientConfig.getPublicAlias(),
                        licenseClientConfig.getStorePass(),
                        filePath + licenseClientConfig.getLicensePath(),
                        licenseClientConfig.getPublicKeysStorePath());
            }
        } catch (Exception e) {
        }
    }
}
