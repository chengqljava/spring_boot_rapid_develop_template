package com.hwsafe.demo.base.utils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

import com.hwsafe.demo.base.exception.RestException;
import com.hwsafe.utils.Digests;
import com.hwsafe.utils.Encodes;

/**
 * 签名工具类
 * 
 * @author eric
 * @version $Id: SignUtil.java, v 0.1 2015年3月23日 下午8:03:39 eric Exp $
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
        for (Entry<String, String> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue())) {
                query.append(param.getKey()).append(param.getValue());
            }
        }

        // 第三步：使用sha1加密
        query.append(secret);
        return Encodes.encodeHex(Digests.sha1(query.toString().getBytes()));
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
        /*
         * if (StringUtils.isBlank(timestamp)) throw new
         * RestException("缺少timestamp参数"); if (!NumberUtils.isNumber(timestamp))
         * throw new RestException("无效的timestamp参数");
         */
        /*
         * if (Config.TIMESTAMP_OUT_SECONDS > 0) { if
         * (Math.abs(Seconds.secondsBetween(new DateTime(), new
         * DateTime(Long.valueOf(timestamp))).getSeconds()) >
         * Config.TIMESTAMP_OUT_SECONDS) throw new
         * RestException("无效的timestamp参数"); }
         */

        String topSign = SignUtil.signRequest(request, appSecret);

        if (!sign.equals(topSign))
            throw new RestException("无效签名");
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
        for (Entry<String, String> param : paramSet) {
            if (StringUtils.isNotBlank(param.getKey())
                    && StringUtils.isNotBlank(param.getValue())) {
                query.append(param.getKey()).append(param.getValue());
            }
        }

        // 第三步：使用sha1加密
        query.append(secret);
        return Encodes.encodeHex(Digests.sha1(query.toString().getBytes()));
    }

    public static void main(String[] args) {
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("params1", "hello");
        mapParams.put("params2", "world");
        mapParams.put("app_key", "app_secret");
        System.out.println(signRequestTest(mapParams, "appSecret"));
    }
}
