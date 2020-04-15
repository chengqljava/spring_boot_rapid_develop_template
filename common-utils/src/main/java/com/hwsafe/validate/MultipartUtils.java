package com.hwsafe.validate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author chengql
 *
 */
public class MultipartUtils {
    /**
     * 判断是否有文件上传
     * 
     * @param request
     * @return
     */
    public static boolean isMultiFiles(HttpServletRequest request) {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getServletContext());
        return commonsMultipartResolver.isMultipart(request);
    }
}
