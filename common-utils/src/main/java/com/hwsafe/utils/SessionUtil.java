/**  
 * Project Name:platform_framework  
 * File Name:SessionUtil.java  
 * Package Name:com.zwsafety.module.system.utils  
 * Date:2017年9月20日
 * Copyright (c) 2017,zwsafety All Rights Reserved.   
 */
package com.hwsafe.utils;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * session工具类，用于从session中存取值
 * 
 * @author 刘晓斌
 * @createtime 2017年9月20日 下午2:02:23
 */
public final class SessionUtil {

    /**
     * 从spring mvc中获取session
     * 
     * @return
     * @return HttpSession
     * @auhtor 刘晓斌
     * @createtime 2017年9月20日 下午2:02:23
     */
    private static HttpSession getSession() {
        ServletRequestAttributes sras = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return sras.getRequest().getSession(true);
    }

    /**
     * 
     * @Title:put
     * @Description TODO(向当前session中存字符串信息). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年9月20日
     * @author 刘晓斌
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 
     * @Title:put
     * @Description TODO(向当前session中存对象信息). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年9月20日
     * @author 刘晓斌
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 
     * @Title:get
     * @Description TODO(获取当前session中存对象信息). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年9月20日
     * @author 刘晓斌
     * @param key
     * @return
     */
    public static Object get(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 
     * @Title:remove
     * @Description TODO(移除当前session中存对象信息). TODO(这里描述这个方法适用条件 – 可选).
     *              TODO(这里描述这个方法的执行流程 – 可选). TODO(这里描述这个方法的使用方法 – 可选).
     *              TODO(这里描述这个方法的注意事项 – 可选).
     * @date 2017年9月20日
     * @author 刘晓斌
     * @param key
     */
    public static void remove(String key) {
        getSession().removeAttribute(key);
    }

}
