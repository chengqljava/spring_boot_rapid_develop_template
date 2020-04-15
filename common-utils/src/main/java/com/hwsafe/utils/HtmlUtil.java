/**
 * Project Name:platform_framework
 * File Name:BaseServiceImpl.java
 * Package Name:com.zwsafety.platform.base
 * Date:2015年7月6日上午14:28:04
 * Copyright (c) 2015,zwsafety All Rights Reserved.
 */
package com.hwsafe.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName:HtmlUtil
 * @Description:TODO(html工具类)
 * @date:2015年7月6日 下午2:28:55
 * @author peijun
 * @version 1.0
 * @since JDK 1.6
 */
public final class HtmlUtil {

    /**
     * Creates a new instance of HtmlUtil.
     */
    private HtmlUtil() {

        // TODO Auto-generated constructor stub

    }

    /**
     * @Title:writerJson
     * @Description TODO(response写入对象).
     * @date 2015年7月6日 下午2:29:08
     * @author peijun
     * @param response
     *            响应对象
     * @param object
     *            object对象
     */
    public static void writerJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json");
        writer(response, JSON.toJSONString(object));
    }

    /**
     * @Title:writerText
     * @Description TODO(response写入对象).
     * @date 2015年7月6日 下午2:29:08
     * @author peijun
     * @param response
     *            响应对象
     * @param object
     *            object对象
     */
    public static void writerText(HttpServletResponse response, Object object) {
        response.setContentType("text/html");
        writer(response, JSON.toJSONString(object));
    }

    /**
     * @Title:writer
     * @Description TODO(response写入字符串).
     * @date 2015年7月6日 下午2:30:40
     * @author peijun
     * @param response
     *            响应对象
     * @param result
     *            结果
     */
    public static void writer(HttpServletResponse response, String result) {
        try {
            // 设置页面不缓存
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            out = response.getWriter();
            out.print(result);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
