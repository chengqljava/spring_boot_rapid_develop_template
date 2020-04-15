/**  
 * Project Name:platform_utils
 * File Name:ResourceUtil.java  
 * Package Name:com.zwsafety.platform.utils  
 * Date:2015年7月27日
 * Copyright (c) 2015,zwsafety All Rights Reserved.   
 */
package com.hwsafe.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName:ResourceUtil
 * @Description:TODO(资源工具类)
 * @date:2014年8月21日
 * @author xufeng
 * @version 1.0
 * @since JDK 1.7
 */
public final class ResourceUtil {
    /**
     * Creates a new instance of ResourceUtil.
     */
    private ResourceUtil() {
        // TODO Auto-generated constructor stub
    }

    // 属性文件的路径
    static String profilepath = LicenseUtil.class.getResource("/").getPath()
            + "sysconfig.properties";

    /**
     * ResourceBundle
     */
    private static final ResourceBundle BUNDLE = java.util.ResourceBundle
            .getBundle("sysconfig");

    /**
     * ResourceBundle
     */
    private static final ResourceBundle JDBCBUNDLE = ("0" == readValue(
            "isDBEncrypted") ? java.util.ResourceBundle.getBundle("jdbc")
                    : java.util.ResourceBundle.getBundle("jdbc_en"));

    /**
     * 采用静态方法
     */
    private static Properties props = new Properties();
    static {
        try {
            props.load(new FileInputStream(profilepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            System.exit(-1);
        }
    }

    /**
     * 获取配置文件参数
     * 
     * @param name
     *            String
     * @return String
     */
    public static final String getConfigByName(String name) {
        return BUNDLE.getString(name);
    }

    /**
     * 获取jdbc配置文件参数
     * 
     * @param name
     *            String
     * @return String
     */
    public static final String getJdbcConfigByName(String name) {
        return JDBCBUNDLE.getString(name);
    }

    /**
     * 获得文件上传的根目录
     * 
     * @param request
     *            HttpServletRequest
     * @author xufeng
     * @date 2015.6.6
     * @return String
     */
    public static String getUploadRootPath(HttpServletRequest request) {
        String targetDirectory = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/"); // 指定上传文件的保存地址
        String[] rootDict = {};
        if (targetDirectory.indexOf("/") != -1) {
            rootDict = targetDirectory.split("/");
        }
        if (targetDirectory.indexOf("\\") != -1) {
            rootDict = targetDirectory.split("\\\\");
        }
        StringBuffer root = new StringBuffer("");
        String osname = System.getProperties().getProperty("os.name");
        // 部署系统是linux系统
        if (osname.indexOf("linux") != -1) {
            root.append("/");
        }
        if (rootDict != null && rootDict.length > 2) {
            for (int i = 0, len = rootDict.length - 2; i < len; i++) {
                root.append(rootDict[i]).append("/");
            }
        }
        root.append("zwsafe_uploadFiles");
        return root.toString();
    }

    /**
     * 获得文件模板的根目录
     * 
     * @param request
     *            HttpServletRequest
     * @author xufeng
     * @date 2015.6.6
     * @return String
     */
    public static String getTemplateRootPath(HttpServletRequest request) {
        String targetDirectory = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/"); // 指定上传文件的保存地址
        String[] rootDict = {};
        if (targetDirectory.indexOf("/") != -1) {
            rootDict = targetDirectory.split("/");
        }
        if (targetDirectory.indexOf("\\") != -1) {
            rootDict = targetDirectory.split("\\\\");
        }
        StringBuffer root = new StringBuffer("");
        String osname = System.getProperties().getProperty("os.name");
        // 部署系统是linux系统
        if (osname.indexOf("linux") != -1) {
            root.append("/");
        }
        if (rootDict != null && rootDict.length > 2) {
            for (int i = 0, len = rootDict.length - 2; i < len; i++) {
                root.append(rootDict[i]).append("/");
            }
        }
        root.append(request.getContextPath() + "/template");
        return root.toString();
    }

    /**
     * 获得临时文件目录
     * 
     * @param request
     * @author xufeng
     * @date 2015.6.6
     */
    public static String getTempfileRootPath(HttpServletRequest request) {
        String targetDirectory = request.getSession().getServletContext()
                .getRealPath("/WEB-INF/"); // 指定上传文件的保存地址
        String[] rootDict = {};
        if (targetDirectory.indexOf("/") != -1) {
            rootDict = targetDirectory.split("/");
        }
        if (targetDirectory.indexOf("\\") != -1) {
            rootDict = targetDirectory.split("\\\\");
        }
        StringBuffer root = new StringBuffer("");
        String osname = System.getProperties().getProperty("os.name");
        if (osname.indexOf("linux") != -1) {// 部署系统是linux系统
            root.append("/");
        }
        if (rootDict != null && rootDict.length > 2) {
            for (int i = 0, len = rootDict.length - 2; i < len; i++) {
                root.append(rootDict[i]).append("/");
            }
        }
        root.append("zwsafe/tempfile");
        return root.toString();
    }

    /**
     * 根据主键key读取主键的值value
     * 
     * @param filePath
     *            属性文件路径
     * @param key
     *            键名
     */
    public static String readValue(String filePath, String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(
                    new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty(key);
            System.out.println(key + "键的值是：" + value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readValue(String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(
                    new FileInputStream(profilepath));
            props.load(in);
            String value = props.getProperty(key);
            System.out.println(key + "键的值是：" + value);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断是否是mirs系统 true是的 false不是的
     * 
     * @return
     */
    public static boolean isMirsSystem() {
        return Boolean.valueOf(readValue("isMirsSystem"));
    }

    /**
     * 更新properties文件的键值对 如果该主键已经存在，更新该主键的值； 如果该主键不存在，则插件一对键值。
     * 
     * @param keyname
     *            键名
     * @param keyvalue
     *            键值
     */
    public static void updateProperties(String keyname, String keyvalue) {
        try {
            props.load(new FileInputStream(profilepath));
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            OutputStream fos = new FileOutputStream(profilepath);
            props.setProperty(keyname, keyvalue);
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }
}
