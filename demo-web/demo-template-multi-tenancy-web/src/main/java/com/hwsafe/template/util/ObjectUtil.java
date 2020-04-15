/**
 * 文件名：ObjectUtil.java
 *
 * 版本信息：
 * 日期：2020年3月31日
 * Copyright 河南汉威电子股份有限公司软件部 Corporation 2020 
 * 版权所有
 *
 */
package com.hwsafe.template.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hwsafe.ems.domain.EmsSucIgrEvacuateroute;

/**
 
 * 
 */
public class ObjectUtil {

    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性名数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    public static List getFiledsInfo(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        List list = new ArrayList();
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 属性名(name)，获取属性类型(type)
     */
    public static Map<String, String> getFiledsInfoMap(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        Map<String, String> infoMap = new HashMap<>();
        String baseType = null;
        for (int i = 0; i < fields.length; i++) {
            baseType = baseType(fields[i].getGenericType().getTypeName());
            infoMap.put(fields[i].getName().replace("_", "").toUpperCase(), StringUtils.isBlank(baseType) ? fields[i].getGenericType().getTypeName() : baseType);
        }
        return infoMap;
    }

    /**
     * 获取对象的所有属性值，返回一个对象数组
     */
    public Object[] getFiledValues(Object o) {
        String[] fieldNames = this.getFiledName(o);
        Object[] value = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            value[i] = this.getFieldValueByName(fieldNames[i], o);
        }
        return value;
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        EmsSucIgrEvacuateroute emsResMaterialMin = new EmsSucIgrEvacuateroute();
        getFiledsInfoMap(Class.forName("com.hwsafe.ems.domain.EmsSucIgrEvacuateroute").newInstance());
    }

    private static String baseType(String typeString) {
        if (typeString.equals("java.lang.String")) {
            return "String";
        }
        if (typeString.equals("java.util.Date")) {
            return "Date";
        }
        if (typeString.equals("java.lang.Boolean")) {
            return "Boolean";
        }
        if (typeString.equals("java.lang.Double")) {
            return "Double";
        }
        if (typeString.equals("java.lang.Float")) {
            return "Float";
        }
        if (typeString.equals("java.lang.Integer")) {
            return "Integer";
        }
        if (typeString.equals("java.lang.Byte")) {
            return "Byte";
        }
        if (typeString.equals("java.lang.Short")) {
            return "Short";
        }
        if (typeString.equals("java.lang.Long")) {
            return "Long";
        }
        if (typeString.equals("java.lang.Char")) {
            return "Char";
        }
        if (typeString.equals("java.math.BigDecimal")) {
            return "BigDecimal";
        }
        return null;
    }
}
