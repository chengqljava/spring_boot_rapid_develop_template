package com.hwsafe.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @ClassName:JsonUtil
 * @Description:TODO(处理json的工具类，负责json数据转换成java对象和java对象转换成json)
 * @date:2015年8月3日
 * @author xufeng
 * @version 1.0
 * @since JDK 1.7
 */
public final class JSONUtil {

    /**
     * Logger
     */
    private static Logger logger = Logger.getLogger(JSONUtil.class);

    /**
     * Creates a new instance of JsonUtil.
     */
    private JSONUtil() {
        // TODO Auto-generated constructor stub
    }

    /**
     * ObjectMapper
     */
    private static final ObjectMapper objectMapper;
    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * json string convert to javaBean
     */
    public static <T> T json2obj(String jsonStr, Class<T> clazz) {
        T t = null;
        try {
            t = objectMapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();// 为了保证调用者的事务同步
        }
        return t;
    }

    /**
     * json string convert to map with javaBean
     */
    public static <T> Map<String, T> json2map(String jsonStr,
            TypeReference<Map<String, T>> typeReference) {
        Map<String, T> result = null;
        try {
            result = objectMapper.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * json array string convert to list with javaBean
     */
    public static <T> List<T> json2list(String jsonArrayStr,
            TypeReference<List<T>> typeReference) {
        List<T> result = null;
        try {
            result = objectMapper.readValue(jsonArrayStr, typeReference);
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();// 为了保证调用者的事务同步
        }
        return result;
    }

}
