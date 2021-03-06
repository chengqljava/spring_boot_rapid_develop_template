package com.chengql.ottService.base.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.chengql.ottService.base.exception.RestException;
import com.chengql.utils.Digests;
import com.chengql.utils.Encodes;

/**
 * 签名工具类
 */
public abstract class SignUtil {

    private SignUtil() {
    }

    /**
     * 根据appkey获取appsecret
     */
    public static String getAppSecret(String appKey) {
        return Config.getInstance().getSECRET_KEY_MAP().get(appKey);
    }

    /**
     * 给请求签名。<br>
     * 算法： 根据参数名称将你的所有请求参数按照字母先后顺序排序:key + value .... key + value
     * 对除签名和图片外的所有请求参数按key做的升序排列, value无需编码。 例如：将foo=1,bar=2,baz=3
     * 排序为bar=2,baz=3,foo=1 参数名和参数值链接后，得到拼装字符串bar2baz3foo1 将app secret 加在字符串后面
     * 加密 encodeHex(sha1(key1value1key2value2...secret))
     * 
     * @param request
     * @param secret
     * @return
     * @throws IOException
     */
    public static String signRequest(HttpServletRequest request,
            String secret) {
        // 第一步：把字典按Key的字母顺序排序
        Map<String, String> sortedParams = new TreeMap<String, String>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (!name.equals(ProtocolParams.SIGN)) {
                if (StringUtils.isNotBlank(name)
                        && StringUtils.isNotBlank(request.getParameter(name))) {
                    sortedParams.put(name, request.getParameter(name));
                }
            }
        }

        Set<Entry<String, String>> paramSet = sortedParams.entrySet();

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        query.append(secret);
        for (Entry<String, String> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue())) {
                query.append(param.getKey()).append(param.getValue());
            }
        }

        // 第三步：使用sha1加密
        query.append(secret);
        return Encodes.encodeHex(Digests.sha1(query.toString().getBytes()))
                .toUpperCase();
    }

    /**
     * 给请求签名。<br>
     * 算法： 根据参数名称将你的所有请求参数按照字母先后顺序排序:key + value .... key + value
     * 对除签名和图片外的所有请求参数按key做的升序排列, value无需编码。 例如：将foo=1,bar=2,baz=3
     * 排序为bar=2,baz=3,foo=1 参数名和参数值链接后，得到拼装字符串bar2baz3foo1 将app secret 加在字符串后面
     * 加密 encodeHex(sha1(key1value1key2value2...secret))
     * 
     * @param request
     * @param secret
     * @return
     * @throws IOException
     */
    public static String signRequestTest(Map<String, String> mapParams,
            String secret) {
        // 第一步：把字典按Key的字母顺序排序
        Map<String, String> sortedParams = new TreeMap<String, String>(
                mapParams);

        Set<Entry<String, String>> paramSet = sortedParams.entrySet();

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        StringBuilder queryURL = new StringBuilder();
        query.append(secret);
        for (Entry<String, String> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue())) {
                query.append(param.getKey()).append(param.getValue());
                queryURL.append(param.getKey() + "=" + param.getValue() + "&");
            }
        }

        // 第三步：使用sha1加密
        query.append(secret);
        System.out.println(queryURL.toString() + "sign="
                + Encodes.encodeHex(Digests.sha1(query.toString().getBytes()))
                        .toUpperCase());
        return Encodes.encodeHex(Digests.sha1(query.toString().getBytes()))
                .toUpperCase();
    }

    /**
     * 验证签名是否正确
     */
    public static void validateSign(HttpServletRequest request) {
        // 验证签名
        String sign = request.getParameter(ProtocolParams.SIGN);
        if (StringUtils.isBlank(sign))
            throw new RestException("缺少签名参数");

        // 验证app_key
        String appKey = request.getParameter(ProtocolParams.APP_KEY);
        if (StringUtils.isBlank(appKey))
            throw new RestException("缺少AppKey参数");

        String appSecret = getAppSecret(appKey);
        if (StringUtils.isBlank(appSecret))
            throw new RestException("无效的AppKey参数");

        // 验证时间戳
        String timestamp = request.getParameter(ProtocolParams.TIMESTAMP);
        if (StringUtils.isBlank(timestamp))
            throw new RestException("缺少timestamp参数");

        String topSign = SignUtil.signRequest(request, appSecret);

        if (!sign.equals(topSign))
            throw new RestException("无效签名");
    }

    /**
     * 验证签名是否正确
     */
    public static void validateSign(HttpServletRequest request,
            String appSecret) {
        // 验证签名
        String sign = request.getParameter(ProtocolParams.SIGN);
        if (StringUtils.isBlank(sign))
            throw new RestException("缺少签名参数");

        // 验证app_key
        String appKey = request.getParameter(ProtocolParams.APP_KEY);
        if (StringUtils.isBlank(appKey))
            throw new RestException("缺少AppKey参数");

        if (StringUtils.isBlank(appSecret))
            throw new RestException("无效的AppKey参数");

        // 验证时间戳
        String timestamp = request.getParameter(ProtocolParams.TIMESTAMP);
        if (StringUtils.isBlank(timestamp))
            throw new RestException("缺少timestamp参数");

        String topSign = SignUtil.signRequest(request, appSecret);

        if (!sign.trim().equals(topSign))
            throw new RestException("无效签名");
    }

    public static void main(String[] args) {
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("name", "hello");
        mapParams.put("password", "world");
        mapParams.put("appKey", "appKey");
        mapParams.put("timestamp", System.currentTimeMillis() + "");
        System.out.println(signRequestTest(mapParams, "appSecret"));
    }
}
