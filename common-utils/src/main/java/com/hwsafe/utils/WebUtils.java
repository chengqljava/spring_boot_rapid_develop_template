package com.hwsafe.utils;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public class WebUtils {
    /**
     * 获取web应用的根路径（url访问地址，如http://localhost:8090/mywebapp）
     * 
     * @return
     */
    public static String getWebappRootUrl(MultipartHttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        // root根路径
        String webappRootUrl = scheme + "://" + serverName + ":" + serverPort
                + "/";
        return webappRootUrl;
    }
}
