package com.hwsafe.validate;

import java.util.Collection;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author chengql
 *
 */
public class Check {

    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    // public static final String REGEX_MOBILE =
    // "^((((86)?)|([+]86)?)((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8})|((((60)?)|([+]60)?)(01)\\d{8,9})$";
    public static final String REGEX_TEL = "^\\d{3,4}-?\\d{7,9}$";
    public static final String REGEX_IDENTITY = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
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
    public static final String REGEX_MOBILE_TH = "^([+]?66)?(0)?\\d{9,10}$";
    /** 印尼手机号及固话匹配正则 **/
    // public static final String REGEX_MOBILE_ID = "^([+]?62)?(0|2)\\d{8}$";
    public static final String REGEX_MOBILE_ID = "^([+]?62)?\\d{9,10}$";
    /** 香港手机号及固话匹配正则 **/
    // public static final String REGEX_MOBILE_HK = "^([+]?852)?(2|6)\\d{7}$";
    public static final String REGEX_MOBILE_HK = "^([+]?852)?\\d{8,10}$";

    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new CheckException("error.illegalArgument");
        }
    }

    public static void checkArgument(boolean expression,
            String errorMessageTemplate) {
        if (!expression) {
            throw new CheckException(errorMessageTemplate);
        }
    }

    public static void checkArgument(boolean expression,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (!expression) {
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        }
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new CheckException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, String errorMessageTemplate) {
        if (reference == null) {
            throw new CheckException(errorMessageTemplate);
        }
        return reference;
    }

    public static <T> T checkNotBlank(T reference,
            String errorMessageTemplate) {
        if (reference == null || StringUtils.isBlank(reference.toString())) {
            throw new CheckException(errorMessageTemplate);
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, String errorMessageTemplate,
            Object... errorMessageArgs) {
        if (reference == null) {
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        }
        return reference;
    }

    public static <T> T checkNotNullAndNotEmpty(T value) {
        if (value instanceof Collection) {
            if (value == null || ((Collection) value).isEmpty()) {
                throw new CheckException();
            }
        } else if (value instanceof String) {
            if (value == null || "".equals(value.toString().trim())) {
                throw new CheckException();
            }
        } else {
            if (value == null)
                throw new CheckException();
        }
        return value;
    }

    public static <T> T checkNotNullAndNotEmpty(T value,
            String errorMessageTemplate) {
        if (value instanceof Collection) {
            if (value == null || ((Collection) value).isEmpty()) {
                throw new CheckException(errorMessageTemplate);
            }
        } else if (value instanceof String) {
            if (value == null || "".equals(value.toString().trim())) {
                throw new CheckException(errorMessageTemplate);
            }
        } else {
            if (value == null)
                throw new CheckException(errorMessageTemplate);
        }
        return value;
    }

    public static <T> T checkNotNullAndNotEmpty(T value,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (value instanceof Collection) {
            if (value == null || ((Collection) value).isEmpty()) {
                throw new CheckException(errorMessageTemplate,
                        errorMessageArgs);
            }
        } else if (value instanceof String) {
            if (value == null || "".equals(value.toString().trim())) {
                throw new CheckException(errorMessageTemplate,
                        errorMessageArgs);
            }
        } else {
            if (value == null)
                throw new CheckException(errorMessageTemplate,
                        errorMessageArgs);
        }
        return value;
    }

    /**
     * 区分大小写
     * 
     * @param regex
     * @param flags
     * @param value
     * @return boolean
     */
    public static boolean matchMobile(String regex, int flags, String value) {
        Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(value).find();
    }

    /**
     * 检查是否匹配正则
     * 
     * @param regex
     *            正则
     * @param value
     *            需要匹配的字符
     */
    public static void checkMatch(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(value).find()) {
            throw new CheckException();
        }
    }

    public static void checkMatch(String regex, String value,
            String errorMessageTemplate) {
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(value).find()) {
            throw new CheckException(errorMessageTemplate);
        }
    }

    public static void checkMatch(String regex, String value,
            String errorMessageTemplate, Object... errorMessageArgs) {
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(value).find()) {
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        }
    }

    /**
     * 检查是否匹配正则
     * 
     * @param regex
     *            正则
     * @param flags
     *            Match flags, a bit mask that may include
     *            {@link #CASE_INSENSITIVE}, {@link #MULTILINE},
     *            {@link #DOTALL}, {@link #UNICODE_CASE}, {@link #CANON_EQ},
     *            {@link #UNIX_LINES}, {@link #LITERAL},
     *            {@link #UNICODE_CHARACTER_CLASS} and {@link #COMMENTS}
     * @param value
     *            需要匹配的字符
     */
    public static void checkMatch(String regex, int flags, String value) {
        Pattern pattern = Pattern.compile(regex, flags);
        if (!pattern.matcher(value).find()) {
            throw new CheckException();
        }
    }

    public static void checkMatch(String regex, int flags, String value,
            String errorMessageTemplate) {
        Pattern pattern = Pattern.compile(regex, flags);
        if (!pattern.matcher(value).find()) {
            throw new CheckException(errorMessageTemplate);
        }
    }

    public static void checkMatch(String regex, int flags, String value,
            String errorMessageTemplate, Object... errorMessageArgs) {
        Pattern pattern = Pattern.compile(regex, flags);
        if (!pattern.matcher(value).find()) {
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        }
    }

    /**
     * 手机号码验证
     * 
     * @param value
     */
    public static void checkMatchMobile(String value) {
        if (StringUtils.isBlank(value)) {
            throw new CheckException();
        }
        value = value.replaceAll(" ", "");
        if (value.contains("-")) {
            value = value.replaceAll("-", "");
        }

        if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, value)
                && !matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        value)) {
            throw new CheckException();
        }

    }

    /**
     * 通过手机号和国别码校验
     * 
     * @param phone
     *            手机号
     * @param countryCode
     *            国别码
     */
    public static void checkMatchMobileByCode(String phone,
            String countryCode) {
        if (StringUtils.isBlank(phone)) {
            throw new CheckException();
        }
        phone = phone.replaceAll(" ", "");
        if (phone.contains("-")) {
            phone = phone.replaceAll("-", "");
        }

        if (StringUtils.isNoneBlank(countryCode)) {
            if ("86".equals(countryCode) || "+86".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("60".equals(countryCode) || "+60".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("65".equals(countryCode) || "+65".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("66".equals(countryCode) || "+66".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("62".equals(countryCode) || "+62".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("852".equals(countryCode)
                    || "+852".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else {
                throw new CheckException();
            }
        }
        if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, phone)
                && !matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        phone)) {
            throw new CheckException();
        }
    }

    /**
     * 通过手机号和国别码校验
     * 
     * @param phone
     *            手机号
     * @param countryCode
     *            国别码
     * @param errorMessageTemplate
     */
    public static void checkMatchMobileByCode(String phone, String countryCode,
            String errorMessageTemplate) {
        if (StringUtils.isBlank(phone)) {
            throw new CheckException();
        }
        phone = phone.replaceAll(" ", "");
        if (phone.contains("-")) {
            phone = phone.replaceAll("-", "");
        }

        if (StringUtils.isNoneBlank(countryCode)) {
            if ("86".equals(countryCode) || "+86".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException(errorMessageTemplate);
                }
            } else if ("60".equals(countryCode) || "+60".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException(errorMessageTemplate);
                }
            } else if ("65".equals(countryCode) || "+65".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("66".equals(countryCode) || "+66".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("62".equals(countryCode) || "+62".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("852".equals(countryCode)
                    || "+852".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else {
                throw new CheckException(errorMessageTemplate);
            }
        }
        if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, phone)
                && !matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        phone)) {
            throw new CheckException(errorMessageTemplate);
        }
    }

    /**
     * 通过手机号和国别码校验
     * 
     * @param phone
     *            手机号
     * @param countryCode
     *            国别码
     * @param errorMessageTemplate
     */
    public static void checkMatchMobileByCode(String phone, String countryCode,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (StringUtils.isBlank(phone)) {
            throw new CheckException();
        }
        phone = phone.replaceAll(" ", "");
        if (phone.contains("-")) {
            phone = phone.replaceAll("-", "");
        }

        if (StringUtils.isNoneBlank(countryCode)) {
            if ("86".equals(countryCode) || "+86".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException(errorMessageTemplate);
                }
            } else if ("60".equals(countryCode) || "+60".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException(errorMessageTemplate);
                }
            } else if ("65".equals(countryCode) || "+65".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("66".equals(countryCode) || "+66".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("62".equals(countryCode) || "+62".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else if ("852".equals(countryCode)
                    || "+852".equals(countryCode)) {
                if (!matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        phone)) {
                    throw new CheckException();
                }
            } else {
                throw new CheckException(errorMessageTemplate);
            }
        }
        if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, phone)
                && !matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        phone)
                && !matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        phone)) {
            throw new CheckException(errorMessageTemplate);
        }
    }

    /**
     * 手机号码验证
     * 
     * @param value
     * @param errorMessageTemplate
     */
    public static void checkMatchMobile(String value,
            String errorMessageTemplate) {
        if (StringUtils.isBlank(value)) {
            throw new CheckException(errorMessageTemplate);
        }
        value = value.replaceAll(" ", "");
        if (value.contains("-")) {
            value = value.replaceAll("-", "");
        }

        if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, value)
                && !matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        value)) {
            throw new CheckException(errorMessageTemplate);
        }
    }

    /**
     * 手机号码验证
     * 
     * @param value
     * @param errorMessageTemplate
     * @param errorMessageArgs
     */
    public static void checkMatchMobile(String value,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (StringUtils.isBlank(value)) {
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        }
        value = value.replaceAll(" ", "");
        if (value.contains("-")) {
            value = value.replaceAll("-", "");
        }

        if (!matchMobile(REGEX_MOBILE_CN, Pattern.CASE_INSENSITIVE, value)
                && !matchMobile(REGEX_MOBILE_ML, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_SG, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_TH, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_ID, Pattern.CASE_INSENSITIVE,
                        value)
                && !matchMobile(REGEX_MOBILE_HK, Pattern.CASE_INSENSITIVE,
                        value)) {
            throw new CheckException();
        }
    }

    public static void checkMatchIdentityCard(String value) {
        if (StringUtils.isBlank(value))
            throw new CheckException();
        if (value.length() > 30) {
            throw new CheckException();
        }
        // checkMatch(REGEX_IDENTITY, Pattern.CASE_INSENSITIVE, value);
    }

    public static void checkMatchIdentityCard(String value,
            String errorMessageTemplate) {
        if (StringUtils.isBlank(value))
            throw new CheckException(errorMessageTemplate);
        if (value.length() > 30) {
            throw new CheckException(errorMessageTemplate);
        }
        // checkMatch(REGEX_IDENTITY, Pattern.CASE_INSENSITIVE, value);
    }

    public static void checkMatchIdentityCard(String value,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (StringUtils.isBlank(value))
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        if (value.length() > 30) {
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        }
        // checkMatch(REGEX_IDENTITY, Pattern.CASE_INSENSITIVE, value);
    }

    public static void checkMatchEmail(String value,
            String errorMessageTemplate) {
        if (StringUtils.isBlank(value))
            throw new CheckException(errorMessageTemplate);
        checkMatch(REGEX_EMAIL, Pattern.CASE_INSENSITIVE, value);
    }

    public static void checkMatchEmail(String value) {
        if (StringUtils.isBlank(value))
            throw new CheckException();
        checkMatch(REGEX_EMAIL, Pattern.CASE_INSENSITIVE, value);
    }

    public static void checkMatchEmail(String value,
            String errorMessageTemplate, Object... errorMessageArgs) {
        if (StringUtils.isBlank(value))
            throw new CheckException(errorMessageTemplate, errorMessageArgs);
        checkMatch(REGEX_EMAIL, Pattern.CASE_INSENSITIVE, value);
    }
}
