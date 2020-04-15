package com.hwsafe.weixin.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public class JsonUtils {

    /**
     * @Description:
     * @field ObjectMapper objectMapper
     * @created 2016 2016年2月5日 下午8:10:52
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(obj);
    }

    public static String convertObject2Json(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
