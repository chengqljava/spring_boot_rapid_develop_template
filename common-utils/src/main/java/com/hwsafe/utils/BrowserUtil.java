package com.hwsafe.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chengql 浏览器判断
 *
 */
public class BrowserUtil {

    /**
     * @param request
     * @return 判断是否IE
     */
    public static boolean isMSBrowser(HttpServletRequest request) {
        String[] IEBrowserSignals = { "MSIE", "Trident", "Edge" };
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)) {
                return true;
            }
        }
        return false;
    }

}
