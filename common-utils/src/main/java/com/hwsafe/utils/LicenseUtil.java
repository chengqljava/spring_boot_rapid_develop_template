/**  
 * Project Name:platform_utils  
 * File Name:LicenseUtil.java  
 * Package Name:com.zwsafety.platform.utils  
 * Date:2016年9月28日
 * Copyright (c) 2016,zwsafety All Rights Reserved.   
 */

package com.hwsafe.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Calendar;

/**
 * @ClassName:LicenseUtil
 * @Description:TODO(用一句话描述该文件做什么)
 * @date:2016年9月28日
 * @author xufeng
 * @version 1.0
 * @since: JDK 1.7
 */
public class LicenseUtil {

    private static final Logger LOG = Logger.getLogger(LicenseUtil.class);

    /**
     * 设置激活码
     * 
     * @Title:setLincese
     * @Description TODO(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2016年9月28日
     * @author xufeng
     * @param lincese
     * @param request
     */
    public static void setLincese(String lincese, HttpServletRequest request) {
        String path = LicenseUtil.class.getResource("/").getPath(); // 指定上传文件的保存地址
        File file = new File(path + "zwsafe.license");
        BufferedWriter bw = null;
        try {
            if (file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(lincese);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    /**
     * 获得license
     * 
     * @Title:getLincese
     * @Description TODO(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2016年9月28日
     * @author xufeng
     * @return
     */
    public static String getLincese(HttpServletRequest request) {
        String path = LicenseUtil.class.getResource("/").getPath(); // 指定上传文件的保存地址
        File file = new File(path + "zwsafe.license");
        if (file.exists()) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                return br.readLine();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获得本机的MAC地址
     * 
     * @Title:getWindowMac
     * @Description TODO(这里用一句话描述这个方法的作用). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2016年9月28日
     * @author xufeng
     * @return
     */
    private static String getWindowMac() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia)
                    .getHardwareAddress();
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // 字节转换为整数
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Linux网卡的mac地址.
     * 
     * @author
     * 
     * @create date 2012.6.27
     * @return mac地址
     */
    private static String getLinuxMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // linux下的命令一般取eth0 作为本地主网卡 显示信息中包含有MAC地址信息 用process流
            process = Runtime.getRuntime().exec("ifconfig ");
            bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串 物理网卡地址
                index = line.toLowerCase().indexOf("hwaddr");
                // 找到了地址
                if (index != -1) {
                    // 取出mac地址并去除两边空格
                    mac = line.substring(index + 4).trim();
                    break;
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                LOG.error(e1.getMessage(), e1);
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
     * 获取unix网卡的mac地址.
     * 
     * @return mac地址
     */
    private static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            // unix下的命令一般取eth0 作为本地主网卡 显示信息中包含有MAC地址信息
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                // 寻找标示字符串【hwaddr】物理网卡地址
                index = line.toLowerCase().indexOf("hwaddr");
                // 找到了地址
                if (index >= 0) {
                    // 取出mac地址并去除两边空格
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                LOG.error(e1.getMessage(), e1);
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    public static String createLicense(String macEncrypt) {
        String encrypt = Encrypter.encrypt("/zwsafety/wtqsafe/" + macEncrypt,
                Encrypter.MD5);
        return encrypt;
    }

    public static String createDate(Integer month) {
        String result = null;
        if (month != null) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, month);
            result = String.valueOf(c.getTimeInMillis());
        } else {
            result = "999";
        }
        return result;
    }

    /**
     * 获取当前操作系统的物理地址
     * 
     * @return
     */
    /*
     * public static String getLocalMac(){ if(OSinfoUtils.isWindows()){ return
     * getWindowMac(); }else if(OSinfoUtils.isLinux()){ return
     * getLinuxMACAddress(); }else{ return getUnixMACAddress(); } }
     */

    /*
     * public static boolean validateLicense(String license) throws Exception {
     * if (null == license) { return false; } String mac = getLocalMac();
     * license = new String(Base64.decodeBase64(license.getBytes())); String
     * arr[] = license.split("##"); if (arr.length == 2) { if
     * (!"999".equals(arr[1])) { Calendar registCalendar =
     * Calendar.getInstance();
     * registCalendar.setTimeInMillis(Long.parseLong(arr[1])); Calendar
     * currCalendar = Calendar.getInstance(); int i =
     * registCalendar.compareTo(currCalendar); if (i < 0) { return false; } }
     * 
     * String encrypt = createLicense(Encrypter .encrypt(mac, Encrypter.MD5));
     * if (encrypt.equals(arr[0])) { return true; } else { return false; } }
     * else { return false; } }
     */

    public static void main(String[] args) {
        String str = createLicense("d34970f81a91c27d93a9d6e6b7f76c28") + "##"
                + createDate(12);
        str = new String(Base64.encodeBase64(str.getBytes()));
        System.out.println(str);
    }
}
