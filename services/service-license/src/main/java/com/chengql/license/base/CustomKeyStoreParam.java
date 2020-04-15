package com.hwsafe.license.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import de.schlichtherle.license.AbstractKeyStoreParam;

/**
 * 自定义KeyStoreParam，用于将公私钥存储文件存放到其他磁盘位置而不是项目中
 *
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {
    private static final Logger LOG = LoggerFactory
            .getLogger(CustomKeyStoreParam.class);

    /**
     * 公钥/私钥在磁盘上的存储路径
     */
    private String storePath;
    private String alias;
    private String storePwd;
    private String keyPwd;

    public CustomKeyStoreParam(Class clazz, String resource, String alias,
            String storePwd, String keyPwd) {
        super(clazz, resource);
        this.storePath = resource;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * 复写de.schlichtherle.license.AbstractKeyStoreParam的getStream()方法<br/>
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     * 
     * @date 2018/4/26 18:28
     * @since 1.0.0
     * @param
     * @return java.io.InputStream
     */
    @Override
    public InputStream getStream() throws IOException {
        InputStream in = resourceUtils();
        if (in == null) {
            /* 此方法，传入参数为String，不能带/ */
            in = classLoaderResourceAsStream();
            if (in == null) {
                /* 此方法，传入参数为String，不能带/ */
                in = classResourceAsStream();
                if (in == null) {
                    in = classResourceFile();
                    if (in == null) {
                        throw new FileNotFoundException(storePath);
                    } else {
                        return in;
                    }
                } else {
                    return in;
                }
            } else {
                return in;
            }

        } else {
            return in;
        }
    }

    private InputStream resourceUtils() {
        try {
            File file = ResourceUtils.getFile("classpath:" + storePath);
            final InputStream in = new FileInputStream(file);
            LOG.info("this.ResourceUtils.getFile()" + (in != null));
            return in;
        } catch (Exception e) {
            LOG.info("this.ResourceUtils.getFile() false");
            LOG.error(e.getMessage());
        }
        return null;
    }

    private InputStream classLoaderResourceAsStream() {
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader()
                    .getResourceAsStream(storePath);
            LOG.info("this.getClass().getClassLoader().getResourceAsStream"
                    + (resourceAsStream != null));
            return resourceAsStream;
        } catch (Exception e) {
            LOG.info(
                    "this.getClass().getClassLoader().getResourceAsStream false");
            LOG.error(e.getMessage());
        }
        return null;
    }

    private InputStream classResourceAsStream() {
        try {
            InputStream resourceStream = this.getClass()
                    .getResourceAsStream(storePath);
            LOG.info("this.getClass().getResourceAsStream()"
                    + (resourceStream != null));
            return resourceStream;
        } catch (Exception e) {
            LOG.info("this.getClass().getResourceAsStream() false");
            LOG.error(e.getMessage());
        }
        return null;
    }

    private InputStream classResourceFile() {
        try {
            String localFileResource = this.getClass().getResource(storePath)
                    .getPath();
            File localFile = new File(localFileResource);
            InputStream localFileIn = new FileInputStream(localFile);
            LOG.info("this.getClass().getResource()" + (localFileIn != null));
            return localFileIn;
        } catch (Exception e) {
            LOG.info("this.getClass().getResource() false");
            LOG.error(e.getMessage());
        }
        return null;
    }
}
