/**  
 * Project Name:platform_utils
 * File Name:StringUtils.java  
 * Package Name:com.zwsafety.platform.utils  
 * Date:2015年7月27日
 * Copyright (c) 2015,zwsafety All Rights Reserved.   
 */
package com.hwsafe.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Clob;

/**
 * @ClassName:StringUtils
 * @Description:TODO(用一句话描述该文件做什么)
 * @date:2015年7月27日
 * @author luyao
 * @version 1.0
 * @since: JDK 1.7
 */
public final class StringUtil {
    /**
     * 日志
     */
    private static final Logger LOG = Logger.getLogger(StringUtil.class);

    /**
     * Creates a new instance of StringUtil.
     */
    private StringUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @Title:toChinese
     * @Description TODO(字符串GBK编码).
     * @date 2015年7月28日
     * @author luyao
     * @param str
     *            String 字符串
     * @return String
     */
    public static String toUTF8(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        try {
            return new String(str.getBytes(getEncoding(str)), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            return str;
        }
    }

    /**
     * @Title:isBlank
     * @Description TODO(判断字符串是否null或者"").
     * @date 2015年7月28日
     * @author luyao
     * @param src
     *            String
     * @return boolean
     */
    public static boolean isBlank(String src) {
        if (null == src || "".equals(src.trim())) {
            return true;
        }
        return false;
    }

    /**
     * @Title:convertNull
     * @Description TODO(把没有实例化的串转化为空串).
     * @date 2015年7月28日
     * @author luyao
     * @param pin
     *            String
     * @return String
     */
    public static String convertNull(String pin) {
        if (pin == null) {
            return "";
        } else {
            return pin;
        }
    }

    /**
     * @Title:controlLength
     * @Description TODO(控制页面字符串显示长度). 字符串的字节长度小于显示长度，则原样输出，否则以XXX...的形式输出
     * @date 2015年7月28日
     * @author luyao
     * @param dataStr
     *            控制长度的字符串
     * @param length
     *            字节的长度
     * @return String
     */
    public static String controlLength(String dataStr, int length) {
        if (dataStr == null) {
            return "";
        }
        // 转化成格式化的Html
        dataStr = StringUtil.escape(dataStr);
        StringBuffer sb = new StringBuffer();
        char datach;
        int lengthi = 0; // 字符串的字节长度
        int strj = 0; // 字符串的实际长度
        int lengthi3 = 0; // 字节长度-4
        // 得到字符串的字节长度
        while (strj < dataStr.length()) {
            datach = dataStr.charAt(strj++);
            if (((datach <= 'z') && (datach >= 'a'))
                    || ((datach <= 'Z') && (datach >= 'A'))
                    || ((datach <= '9') && (datach >= '0'))) {
                lengthi++;
            } else {
                lengthi = lengthi + 2;
            }
        }
        strj = 0;
        // 得到字符串的字节长度-4
        while ((lengthi3 < length - 4) && (strj < dataStr.length())) {
            datach = dataStr.charAt(strj++);
            sb.append(datach);
            if (((datach <= 'z') && (datach >= 'a'))
                    || ((datach <= 'Z') && (datach >= 'A'))
                    || ((datach <= '9') && (datach >= '0'))) {
                lengthi3++;
            } else {
                lengthi3 = lengthi3 + 2;
            }
        }
        if (lengthi <= length) {
            return dataStr;
        } else {
            sb.append("...");
        }
        return sb.toString();
    }

    /**
     * @Title:escape
     * @Description TODO(把字符串转化成HTML字符串).
     * @date 2015年7月28日
     * @author luyao
     * @param str
     *            需要转换的字符串
     * @return String
     */
    public static String escape(String str) {
        if (str == null) {
            return "";
        }

        byte[] data = str.getBytes();
        int len = data.length;
        StringBuffer result = new StringBuffer(len * 2);

        int begin = 0, count = 0;
        for (int i = 0; i < len; i++) {
            switch ((char) data[i]) {
            case '&':
                result.append(new String(data, begin, count));
                result.append("&");
                begin = i + 1;
                count = 0;
                break;
            case '\"':
                result.append(new String(data, begin, count));
                result.append("");
                begin = i + 1;
                count = 0;
                break;
            case '<':
                result.append(new String(data, begin, count));
                result.append("<");
                begin = i + 1;
                count = 0;
                break;
            case '>':
                result.append(new String(data, begin, count));
                result.append(">");
                begin = i + 1;
                count = 0;
                break;
            case '\n':
                result.append(new String(data, begin, count));
                result.append("<br>");
                begin = i + 1;
                count = 0;
                break;
            case '$':
                result.append(new String(data, begin, count));
                result.append("$$");
                begin = i + 1;
                count = 0;
                break;
            case ' ':
                result.append(new String(data, begin, count));
                result.append(" ");
                begin = i + 1;
                count = 0;
                break;
            default:
                count++;
                break;
            }
        }
        if (count > 0) {
            result.append(new String(data, begin, count));
        }
        return result.toString();
    }

    /**
     * @Title:isNumberString
     * @Description TODO(全数字判断,参照字符串strRef可以是:"0123456789","23546"或"0123"等等).
     * @date 2015年7月28日
     * @author luyao
     * @param strIn
     *            String
     * @return boolean
     */
    public static boolean isNumberString(String strIn) {
        return isNumberString(strIn, "0123456789");
    }

    /**
     * @Description TODO(全数字判断).
     * @date 2015年7月28日
     * @author luyao
     * @param strIn
     *            判断数字
     * @param strRef
     *            数字过滤条件
     * @return boolean
     */
    public static boolean isNumberString(String strIn, String strRef) {
        if (strIn == null || strIn.length() == 0) {
            return (false);
        }
        for (int i = 0; i < strIn.length(); i++) {
            String strTmp = strIn.substring(i, i + 1);
            if (strRef.indexOf(strTmp, 0) == -1) {
                return (false);
            }
        }
        return (true);
    }

    /**
     * @Title:replace
     * @Description TODO(字符串替换,
     *              调用了org.apache.commons.lang.StringUtils.replace()).
     * @date 2015年7月28日
     * @author luyao
     * @param strObj
     *            目标字符串
     * @param str1
     *            被替换的字符串
     * @param str2
     *            替换成的字符串
     * @return String
     */
    public static String replace(String strObj, String str1, String str2) {
        /*
         * if(strObj == null || str1 == null || str2 == null){ return null; }
         */
        if ("".equals(str1)) {
            return strObj;
        }
        return StringUtil.replace(strObj, str1, str2);
    }

    /**
     * 字符串切割,比如"#sd#kf##dkf##Ej#"按照"#"切割之后length=8,即"#"出现的次数加一
     * 
     * @param strObj
     *            目标字符串
     * @param str1
     *            用于切割的字符串
     * @return String[]
     */
    public static String[] split(String strObj, String str1) {
        if (strObj == null) {
            return null;
        }
        if ("".equals(strObj) || "".equals(str1)) {
            return new String[] { strObj };
        }

        int count = org.apache.commons.lang3.StringUtils.countMatches(strObj,
                str1) + 1; // 调用org.apache.commons
        int length = str1.length();
        String[] strs = new String[count];
        int posPre = 0 - length;
        int pos = 0;
        for (int i = 0; i < count - 1; i++) {
            pos = strObj.indexOf(str1, posPre + length);
            strs[i] = strObj.substring(posPre + length, pos);
            posPre = pos;
        }
        strs[count - 1] = strObj.substring(posPre + length);

        return strs;
    }

    /**
     * @Title:appendStr
     * @Description TODO(用于转义单斜杠特殊字符).
     * @date 2015年7月28日
     * @author luyao
     * @param str
     *            String
     * @return String
     */
    public static String appendStr(String str) {
        char c = ' ';
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '\\') {
                sb.append("\\\\");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();

    }

    /**
     * 将reader流中的数据变成String
     * 
     * @param is
     *            reader流
     * @return String
     * @throws IOException
     *             io异常
     */
    public static String readerToString(Reader is) throws IOException {
        StringBuffer sb = new StringBuffer();
        char[] b = new char[8192];
        int n;

        while ((n = is.read(b)) > 0) {
            sb.append(b, 0, n);
        }

        return sb.toString();
    }

    /**
     * @Title:inputStreamToString
     * @Description TODO(Input流中的数据变成String).
     * @date 2015年7月28日
     * @author luyao
     * @param is
     *            InputStream
     * @return String
     * @throws IOException
     *             io异常
     */
    public static String inputStreamToString(InputStream is)
            throws IOException {
        return readerToString(new InputStreamReader(is));
    }

    /***
     * @Title:getDoubleByScale
     * @Description TODO(double数据类型取小数点位数).
     * @date 2015年7月28日
     * @author luyao
     * @param doubleData
     *            double
     * @param scale
     *            int
     * @return String
     */
    public static String getDoubleByScale(double doubleData, int scale) {
        String strFormator = "#."
                + org.apache.commons.lang3.StringUtils.repeat("#", scale);
        java.text.DecimalFormat formater = new java.text.DecimalFormat(
                strFormator);
        String newData = formater.format(doubleData);
        return newData;
    }

    /**
     * @Title:sortByLenDesc
     * @Description TODO(对传入的字符串，按长度降序排列).
     * @date 2015年7月28日
     * @author luyao
     * @param source
     *            String[]
     * @return String[]
     */
    public static String[] sortByLenDesc(String[] source) {
        int i, j;
        int length = source.length;
        String tmpStr;
        for (i = 0; i < length - 1; i++) {
            for (j = i + 1; j < length; j++) {
                if (source[i].length() < source[j].length()) {
                    tmpStr = source[i];
                    source[i] = source[j];
                    source[j] = tmpStr;
                }
            }
        }
        return source;
    }

    /**
     * 将java.sql.Clob转化成String
     * 
     * @param clob
     *            java.sql.Clob
     * @return String
     * @throws Exception
     *             异常
     */
    public static String clob2String(Clob clob) throws Exception {
        StringWriter writer = new StringWriter();
        Reader reader = null;
        try {
            reader = clob.getCharacterStream();
            char[] cRead = new char[1024];
            int iRead;
            while ((iRead = reader.read(cRead, 0, 1024)) != -1) {
                writer.write(cRead, 0, iRead);
            }
            return writer.getBuffer().toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("转换java.sql.Clob到String异常!");
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * 
     * @param d
     *            需要四舍五入的数字
     * @param scale
     *            小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static float round(double d, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(d));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * @Title:getEncoding
     * @Description TODO(获取字符编码格式).
     * @date 2016年9月5日
     * @author lzqiangPC
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
            LOG.error("GB2312字符转换错误");
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
            LOG.error("ISO-8859-1字符转换错误");
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
            LOG.error("UTF-8字符转换错误");
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
            LOG.error("GBK字符转换错误");
        }
        return "";
    }

    /**
     * json中unicode转为utf-8
     * 
     * @param theString
     * @return
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}
