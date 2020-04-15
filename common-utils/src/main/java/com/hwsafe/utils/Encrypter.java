package com.hwsafe.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具类
 * 
 * @author xufeng
 */
public final class Encrypter {
    /**
     * Creates a new instance of Encrypter.
     */
    private Encrypter() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Logger
     */
    private static Logger log = LoggerFactory.getLogger(Encrypter.class);

    /**
     * MD5加密
     */
    public static final String MD5 = "MD5";

    /**
     * SHA1加密
     */
    public static final String SHA1 = "SHA-1";

    /**
     * 加密
     * 
     * @param source
     *            待加密的字符串
     * @param algorithm
     *            算法 MD5或SHA-1
     * @return 加密后的字符串
     */
    public static String encrypt(String source, String algorithm) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest alga = MessageDigest.getInstance(algorithm);
            alga.update(source.getBytes("UTF-8"));
            byte[] digesta = alga.digest();
            for (byte b : digesta) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error("使用了不支持的加密算法", e);
        } catch (UnsupportedEncodingException e) {
            log.error("使用了不支持的字符集", e);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(Encrypter.encrypt("dzzfdzzf_123", Encrypter.MD5));
    }

}
