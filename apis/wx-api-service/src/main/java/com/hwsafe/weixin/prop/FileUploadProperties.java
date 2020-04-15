package com.hwsafe.weixin.prop;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.hwsafe.utils.StringUtil;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = FileUploadProperties.FILECONF_PREIX)
@Data
public class FileUploadProperties {
    public static final String FILECONF_PREIX = "file";
    private String winUploadPath;
    private String linuxUploadPath;
    private Boolean haveCreatePath = false;

    public String getFileUploadPath() {
        // 如果没有写文件上传路径,保存到临时目录
        String os = System.getProperty("os.name");
        String fileUploadPath = linuxUploadPath;
        if (os.toLowerCase().startsWith("win")) {
            fileUploadPath = winUploadPath;
        }
        if (StringUtil.isBlank(fileUploadPath)) {
            return System.getProperty("java.io.tmpdir");
        } else {
            // 判断有没有结尾符,没有得加上
            if (!fileUploadPath.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator;
            }
            // 判断目录存不存在,不存在得加上
            if (!haveCreatePath) {
                File file = new File(fileUploadPath);
                file.mkdirs();
                haveCreatePath = true;
            }
            return fileUploadPath;
        }
    }

    public void setFileUploadPath(String fileUploadPath) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            winUploadPath = fileUploadPath;
        } else {
            linuxUploadPath = fileUploadPath;
        }
    }
}