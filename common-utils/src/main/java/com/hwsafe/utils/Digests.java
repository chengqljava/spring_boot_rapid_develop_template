package com.hwsafe.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 * 
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 * 
 */
public class Digests {
    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * 对输入字符串进行sha1散列.
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }

    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }

    public static byte[] md5(byte[] input) {
        return digest(input, MD5, null, 1);
    }

    public static byte[] md5(byte[] input, byte[] salt) {
        return digest(input, MD5, salt, 1);
    }

    public static byte[] md5(byte[] input, byte[] salt, int iterations) {
        return digest(input, MD5, salt, iterations);
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt,
            int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机的Byte[]作为salt.
     * 
     * @param numBytes
     *            byte数组的大小
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0,
                "numBytes argument must be a positive integer (1 or larger)",
                numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对文件进行md5散列.
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * 对文件进行sha1散列.
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    private static byte[] digest(InputStream input, String algorithm)
            throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     * 
     * @param source
     *            待加密的字符串 MD5
     * @return 加密后的字符串
     */
    public static String encryptMD5(String source) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest alga = MessageDigest.getInstance(MD5);
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
            // 使用了不支持的加密算法
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // 使用了不支持的字符集
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 加密
     * 
     * @param source
     *            待加密的字符串 SHA-1
     * @return 加密后的字符串
     */
    public static String encryptSHA1(String source) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest alga = MessageDigest.getInstance(SHA1);
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
            // 使用了不支持的加密算法
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // 使用了不支持的字符集
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static final String md5(String s) {
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            try {
                mdTemp.update(s.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                mdTemp.update(s.getBytes());
            }
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
                str[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(str).toUpperCase();
        } catch (Exception e) {
        }
        return null;
    }

    public static final String buildToken(String url, String paramJson,
            String secret) {
        String tempUrl = null;
        if (url.contains("https://"))
            tempUrl = url.substring("https://".length());
        else {
            tempUrl = url.substring("http://".length());
        }
        int index = tempUrl.indexOf("/");
        String URI = tempUrl.substring(index);
        String[] ss = URI.split("\\?");
        if (ss.length > 1) {
            return md5(ss[0] + ss[1] + secret);
        }
        return md5(ss[0] + paramJson + secret);
    }
}
