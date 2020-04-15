package com.hwsafe.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

/**
 * 签名工具类
 * 
 * @author chengql
 * @version $Id: SignUtil.java, v 0.1 2015年3月23日 下午8:03:39 $
 */
public abstract class SignUtil {

    private SignUtil() {
    }

    /**
     * 给请求签名。<br>
     * 算法： 根据参数名称将你的所有请求参数按照字母先后顺序排序:key + value .... key + value
     * 对除签名和图片外的所有请求参数按key做的升序排列, value无需编码。 例如：将foo=1,bar=2,baz=3
     * 排序为bar=2,baz=3,foo=1 参数名和参数值链接后，得到拼装字符串bar2baz3foo1 将app secret 加在字符串后面
     * 加密 encodeHex(sha1(secretkey1value1key2value2...secret))
     * 
     * @param request
     * @param secret
     * @return
     * @throws IOException
     */
    public static String signRequest(Map<String, Object> sortedParams,
            String secret) {

        Set<Entry<String, Object>> paramSet = sortedParams.entrySet();
        StringBuilder query = new StringBuilder();
        // 第一步
        query.append(secret);
        // 第二步：把所有参数名和参数值串在一起

        for (Entry<String, Object> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue().toString())) {
                query.append(param.getKey()).append(param.getValue());
            }
        }
        // 第三步：使用sha1加密
        query.append(secret);
        String sign = Encodes
                .encodeHex(Digests.md5(query.toString().getBytes()));

        return sign.toUpperCase();
    }

    /**
     * 给请求签名。<br>
     * 算法： 根据参数名称将你的所有请求参数按照字母先后顺序排序:key + value .... key + value
     * 对除签名和图片外的所有请求参数按key做的升序排列, value无需编码。 例如：将foo=1,bar=2,baz=3
     * 排序为bar=2,baz=3,foo=1 参数名和参数值链接后，得到拼装字符串bar2baz3foo1
     * encodeHex(sha1(key1value1key2value2...))
     * 
     * @param request
     * @return 无secret返回参数加密
     * @throws IOException
     */
    public static String signRequest(Map<String, Object> sortedParams) {

        Set<Entry<String, Object>> paramSet = sortedParams.entrySet();
        StringBuilder query = new StringBuilder();
        for (Entry<String, Object> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue().toString())) {
                query.append(param.getKey()).append(param.getValue());
            }
        }
        String sign = Encodes
                .encodeHex(Digests.md5(query.toString().getBytes()));

        return sign.toUpperCase();
    }

    /**
     * 给请求签名。<br>
     * 算法： 根据参数名称将你的所有请求参数按照字母先后顺序排序:key + value .... key + value
     * 对除签名和图片外的所有请求参数按key做的升序排列, value无需编码。 例如：将foo=1,bar=2,baz=3
     * 排序为bar=2,baz=3,foo=1 参数名和参数值链接后，得到拼装字符串bar2baz3foo1 将app secret 加在字符串后面
     * 加密 encodeHex(sha1(secretkey1value1key2value2...secret))
     * 
     * @param request
     * @param secret
     * @return 返回的URL拼接
     * @throws IOException
     */
    public static String signRequestUrlParam(Map<String, Object> sortedParams,
            String secret) {

        Set<Entry<String, Object>> paramSet = sortedParams.entrySet();
        StringBuilder query = new StringBuilder();
        query.append(secret);
        StringBuilder queryParam = new StringBuilder();
        boolean falg = false;
        for (Entry<String, Object> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue().toString())) {
                query.append(param.getKey()).append(param.getValue());
                queryParam.append((falg ? "&" : "") + param.getKey() + "="
                        + param.getValue());
                if (!falg) {
                    falg = true;
                }

            }
        }
        query.append(secret);
        String sign = Encodes
                .encodeHex(Digests.md5(query.toString().getBytes()));
        queryParam.append((falg ? "&" : "") + "sign=" + sign.toUpperCase());
        return queryParam.toString();
    }

    public static String parmsStr(Map<String, Object> params) {
        Set<Entry<String, Object>> paramSet = params.entrySet();
        StringBuilder query = new StringBuilder();
        // 第一步
        // 第二步：把所有参数名和参数值串在一起
        int index = 0;
        for (Entry<String, Object> param : paramSet) {
            index++;
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue().toString())) {
                query.append(param.getKey() + "=" + param.getValue()
                        + (index < params.size() ? "&" : ""));
            }
        }
        return query.toString();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("appKey ", "12345678");
        map.put("timestamp", "2017-01-01 12:00:00");
        map.put("format", "json");
        map.put("v", "1.0");
        map.put("current", 1);
        map.put("size", 2);
        SignUtil.signRequest(map, "secret");
        System.out.println(
                Encodes.encodeHex("helloworld".getBytes()).toUpperCase());
    }

}
