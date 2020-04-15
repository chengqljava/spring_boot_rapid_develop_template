package com.hwsafe.wx.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wx")
@Slf4j
public class WxOauthController {
    @Value("${domain.name:}")
    private String domainName;

    /**
     * 鉴权入口
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/oauth")
    public void oauth(HttpServletRequest request, HttpServletResponse response,
            String appid) {
        log.info("进入授权接口oauth");
        try {// 或者换成tenantId
             // 回调地址（必须在公网进行访问）回调可以配置
            String backUrl = domainName + "/wx/redirect/" + appid + "/login";
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                    + appid + "&redirect_uri=" + URLEncoder.encode(backUrl)
                    + "&response_type=code" + "&scope=snsapi_userinfo"
                    + "&state=STATE#wechat_redirect";
            // 重定向
            log.info("url是:" + url);
            response.sendRedirect(url.toString());
        } catch (Exception e) {
            log.error("==微信授权出错", e);
        }
    }

}
