package com.hwsafe.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 项目名称：加密 类名称：DesUtil 类描述：TODO
 *
 * @创建人： Xucy 创建时间：2018年11月02日 8:38 @修改人： Xucy 修改时间：2018年11月02日 8:38 修改备注：
 */
public class DesUtil {

    // 测试
    public static void main(String args[]) {
//        //待加密内容
        String str = "内蒙古荣信化工有限公司" + "-"
                + String.valueOf(System.currentTimeMillis());
        // 密码，长度要是8的倍数
        String password = "123456aaaaa~！@#￥%……&*（）{}：_=+_()/*^%$#@!~,./<>?;   ':\"[]{}|“《》？ascascascasc&asalskaAJHBNBKJN81723188aaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String pinEncode = String.format("%-40s", password).replaceAll(" ",
                "a");
        System.out.println(pinEncode);
        byte[] result = DesUtil.encrypt(str.getBytes(), pinEncode);
//        System.out.println("加密后：" + new String(result));
        String encStr = ParseSystemUtil.parseByte2HexStr(result);
        System.out.println("加密后：" + encStr);

        // 直接将如上内容解密
        try {
            byte[] decryResult = DesUtil.decrypt(
                    ParseSystemUtil.parseHexStr2Byte(encStr), pinEncode);
            System.out.println("解密后：" + new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

//        System.out.println(String.format("%-40s", "123456aaaaa~！@#￥%……&*（）{}：“《》？ascascascasc&asalskaAJHBNBKJN81723188aaaaaaaaaaaaaaaaaaaaaaaaaaaaa").replaceAll(" ", "a"));

        // 待加密内容
//        String str = String.valueOf(1541665834112L);
//        //密码，长度要是8的倍数
//        String password = "2018110816";
//        String pinEncode=String.format("%-40s", password).replaceAll(" ", "a");
//        System.out.println(pinEncode);
//        byte[] result = DesUtil.encrypt(str.getBytes(), pinEncode);
//        System.out.println("加密后：" + new String(result));
//
//        final byte[] bytes = Base64.encodeBase64(result);
//        String encStr = ParseSystemUtil.parseByte2HexStr(result);
//        System.out.println("加密后：" + new String(bytes));
//        System.out.println("fYZMaUvgOhKxT/GBVDR48A==".equals(new String(bytes)));
//
//        //直接将如上内容解密
//        try {
//            byte[] decryResult = DesUtil.decrypt(ParseSystemUtil.parseHexStr2Byte(encStr), pinEncode);
//            System.out.println("解密后：" + new String(decryResult));
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }

    }

    /**
     * 加密
     *
     * @param datasource
     *            byte[]
     * @param password
     *            String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src
     *            byte[]
     * @param password
     *            String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return cipher.doFinal(src);
    }
}
