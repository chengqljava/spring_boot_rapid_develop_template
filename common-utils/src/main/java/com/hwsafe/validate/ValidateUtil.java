package com.hwsafe.validate;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author chengql
 *
 */
public class ValidateUtil {

    /**
     * 邮箱验证
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 座机验证
     */
    public static final String REGEX_TEL = "^\\d{3,4}-?\\d{7,9}$";
    /**
     * 身份证
     */
    public static final String REGEX_IDENTITY = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    /**
     * 邮政编码
     */
    public static final String REGEX_ZIP = "^[0-9]{6}$";

    /** 中国手机号匹配正则 **/
    public static final String REGEX_MOBILE_CN = "^([+]?86)?(13|14|15|17|18)\\d{9}$";
    /** 马来西亚手机号匹配正则 **/
    public static final String REGEX_MOBILE_ML = "^([+]?60)?(1)\\d{8,9}$";
    /** 新加波手机号匹配正则 **/
    // public static final String REGEX_MOBILE_SG =
    // "^([+]?65)?(3|6|8|9)\\d{7}$";
    public static final String REGEX_MOBILE_SG = "^([+]?65)?\\d{8,10}$";
    /** 泰国手机号及固话匹配正则 **/
    // public static final String REGEX_MOBILE_TH =
    // "^([+]?66)?(0)?(2|43|6|8|9)\\d{8}$";
    public static final String REGEX_MOBILE_TH = "^([+]?66)?\\d{9,10}$";
    /** 印尼手机号及固话匹配正则 **/
    // public static final String REGEX_MOBILE_ID = "^([+]?62)?(0|2)\\d{8}$";
    public static final String REGEX_MOBILE_ID = "^([+]?62)?\\d{9,10}$";
    /** 香港手机号及固话匹配正则 **/
    // public static final String REGEX_MOBILE_HK = "^([+]?852)?(2|6)\\d{7}$";
    public static final String REGEX_MOBILE_HK = "^([+]?852)?\\d{8,10}$";
    /** 中国身份证 */
    public static final String REGEX_IDENTITY_CN = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
    /** 马来身份证 */
    public static final String REGEX_IDENTITY_ML = "^([a-z]|[A-Z]|[0-9]){" + 8
            + "," + 12 + "}$";
    /** URL正则表达式 */
    public static final String URL_MATCHER = "^(http://|https://){0,1}([\\w-]+\\.)+(wuling|wulingd|wujar)\\.(com|me)[^\u4e00-\u9fa5\\s]*$";
    /** 数字和字母 */
    public static final String NUMBER_CHAR = "^([a-zA-Z0-9]+)*$";

    /**
     * 匹配正则表达式
     * 
     * @param regex
     *            regex
     * @param value
     *            value
     * @return boolean
     */
    public static boolean match(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).find();
    }

    /**
     * 区分大小写
     * 
     * @param regex
     * @param flags
     * @param value
     * @return boolean
     */
    public static boolean match(String regex, int flags, String value) {
        Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(value).find();
    }

    /**
     * 输入内容限制为英文字母 、数字和下划线
     * 
     * @param value
     * @return boolean
     */
    public static boolean isGeneral(String value) {
        String check = "^\\w+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 输入内容限制为英文字母 、数字和下划线
     * 
     * @param value
     * @param min
     * @param max
     * @return boolean
     */
    public static boolean isGeneral(String value, int min, int max) {
        String check = "^\\w{" + min + "," + max + "}$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 中文
     * 
     * @param value
     * @return boolean
     */
    public static boolean isChinese(String value) {
        String check = "^[\\u2E80-\\u9FFF]+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 中文
     * 
     * @param value
     * @param min
     * @param max
     * @return boolean
     */
    public static boolean isChinese(String value, int min, int max) {
        String check = "^[\\u2E80-\\u9FFF]{" + min + "," + max + "}$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 中文字、英文字母、数字和下划线
     * 
     * @param value
     * @return boolean
     */
    public static boolean isString(String value) {
        String check = "^[\\u0391-\\uFFE5\\w]+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 中文字、英文字母、数字和下划线包含空格
     * 
     * @param value
     * @return boolean
     */
    public static boolean isStringSpace(String value) {
        String check = "^[\\u0391-\\uFFE5\\w\\s]+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 中文字、英文字母、数字和下划线
     * 
     * @param value
     * @param min
     * @param max
     * @return boolean
     */
    public static boolean isString(String value, int min, int max) {
        String check = "^[\\u0391-\\uFFE5\\w]{" + min + "," + max + "}$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 输入内容限制中文、英文字母
     * 
     * @param value
     * @return
     */
    public static boolean isChineseLetter(String value) {
        String check = "^[\\u0391-\\uFFE5A-Za-z]+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 输入内容限制中文、英文字母、空格
     * 
     * @param value
     * @return
     */
    public static boolean isChineseLetterSpace(String value) {
        String check = "^[\\u0391-\\uFFE5A-Za-z\\s*]+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 输入内容限制中文、英文字母
     * 
     * @param value
     * @param min
     * @param max
     * @return
     */
    public static boolean isChineseLetter(String value, int min, int max) {
        String check = "^[\\u0391-\\uFFE5A-Za-z]{" + min + "," + max + "}$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 邮箱验证工具
     * 
     * @param value
     * @return boolean
     */
    public static boolean isEmail(String value) {
        if (StringUtils.isBlank(value))
            return false;

        return match(REGEX_EMAIL, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 手机号码验证
     * 
     * @param value
     * @return boolean
     */
    public static boolean isMobile(String value) {
        if (ValidateUtil.isNullOrEmpty(value)) {
            return false;
        }

        value = value.replaceAll(" ", "");

        if (value.contains("-")) {
            value = value.replaceAll("-", "");
        }

        return match(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE, value);

    }

    /**
     * 手机号校验
     * 
     * @param phone
     *            手机号
     * @param countryCode
     *            手机号国别码
     * @return
     */
    public static boolean isMobile(String phone, String countryCode) {
        if (ValidateUtil.isNullOrEmpty(phone)) {
            return false;
        }

        phone = phone.replaceAll(" ", "");

        if (phone.contains("-")) {
            phone = phone.replaceAll("-", "");
        }

        if (!ValidateUtil.isNullOrEmpty(countryCode)) {
            if ("86".equals(countryCode) || "+86".equals(countryCode)) {
                return match(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, phone);
            } else if ("+60".equals(countryCode) || "60".equals(countryCode)) {
                return match(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE, phone);
            } else if ("65".equals(countryCode) || "+65".equals(countryCode)) {
                return match(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE, phone);
            } else if ("66".equals(countryCode) || "+66".equals(countryCode)) {
                return match(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE, phone);
            } else if ("62".equals(countryCode) || "+62".equals(countryCode)) {
                return match(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE, phone);
            } else if ("852".equals(countryCode)
                    || "+852".equals(countryCode)) {
                return match(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE, phone);
            } else {
                return false;
            }

        }
        return match(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, phone)
                || match(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE, phone)
                || match(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE, phone)
                || match(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE, phone)
                || match(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE, phone)
                || match(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE, phone);
    }

    /**
     * 座机验证
     * 
     * @param value
     * @return boolean
     */
    public static boolean isTel(String value) {
        if (StringUtils.isBlank(value))
            return false;

        return match(REGEX_TEL, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 电话号码 包括移动电话和座机
     * 
     * @param value
     *            value
     * @return boolean
     */
    public static boolean isPhone(String value) {
        if (StringUtils.isBlank(value))
            return false;

        return match(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_TEL, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 身份证
     * 
     * @param value
     * @return boolean
     */
    public static boolean isIdentityCard(String value) {
        if (StringUtils.isBlank(value))
            return false;

        // if (value.length() > 30) {
        // return false;
        // }

        return match(REGEX_IDENTITY_CN, Pattern.CASE_INSENSITIVE, value)
                || match(REGEX_IDENTITY_ML, Pattern.CASE_INSENSITIVE, value);
        // return true;
    }

    /**
     * 邮政编码
     * 
     * @param value
     * @return boolean
     */
    public static boolean isZipCode(String value) {
        if (StringUtils.isBlank(value))
            return false;

        return match(REGEX_ZIP, Pattern.CASE_INSENSITIVE, value);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object value) {
        if (value instanceof Collection) {
            if (value == null || ((Collection) value).isEmpty()) {
                return true;
            }
        } else if (value instanceof String) {
            if (value == null || "".equals(value.toString().trim())) {
                return true;
            }
        } else {
            if (value == null)
                return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(Object... values) {
        for (Object value : values) {
            if (isNullOrEmpty(value))
                return true;
        }
        return false;
    }

    public static boolean isLength(String value, int min, int max) {
        int length = isNullOrEmpty(value) ? 0 : value.length();
        return length >= min && length <= max;
    }

    public static boolean isNumber(String value) {
        String check = "^(\\+|\\-)?\\d+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    public static boolean isNumber(String value, int min, int max) {
        String check = "^(\\+|\\-)?\\d{" + min + "," + max + "}$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    /**
     * 正整数
     * 
     * @param value
     * @return boolean
     */
    public static boolean isPositiveNumber(String value) {
        if (StringUtils.isBlank(value))
            return false;

        String check = "^\\d+$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    public static boolean isPositiveNumber(String value, int min, int max) {
        if (StringUtils.isBlank(value))
            return false;

        String check = "^\\d{" + min + "," + max + "}$";
        return match(check, Pattern.CASE_INSENSITIVE, value);
    }

    public static boolean isInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInt(String value, int min, int max) {
        try {
            int temp = Integer.parseInt(value);
            if (temp < min || temp > max)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isLong(String value, long min, long max) {
        try {
            long temp = Long.parseLong(value);
            if (temp < min || temp > max)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String value, double min, double max) {
        try {
            double temp = Double.parseDouble(value);
            if (temp < min || temp > max)
                return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDate(String dateStr) {
        if (isNullOrEmpty(dateStr)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(dateStr);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isDateTime(String dateStr) {
        if (isNullOrEmpty(dateStr)) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            sdf.parse(dateStr);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * 验证是否URL地址
     * 
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        Pattern p = Pattern.compile(URL_MATCHER, Pattern.CASE_INSENSITIVE);
        return p.matcher(url).find();
    }

    public static boolean isNumberOrChar(String value) {
        if (StringUtils.isBlank(value))
            return false;

        return match(NUMBER_CHAR, Pattern.CASE_INSENSITIVE, value);
    }
//public static void main(String[] args) {
//	System.out.println(isMobile("1234567891","852"));
//}
}
