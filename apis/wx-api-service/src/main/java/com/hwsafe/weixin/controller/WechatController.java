package com.hwsafe.weixin.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {

    /**
     * 鉴权入口
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/oauth")
    public void OAuth(HttpServletRequest request, HttpServletResponse response,
            String params) {
        log.info("进入授权接口oauth");
        try {
            // 回调地址（必须在公网进行访问）
            String backUrl = "http://9a49499c.ngrok.io/wechat/getCode";
            String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
                    + "wx07308887f42cf969" + "&redirect_uri="
                    + URLEncoder.encode(backUrl) + "&response_type=code"
                    + "&scope=snsapi_userinfo" + "&state=STATE#wechat_redirect";

            // 重定向
            log.info("url是:" + url);
            response.sendRedirect(url.toString());
        } catch (Exception e) {
            log.error("五一分类赢话费mayDay==微信授权出错", e);
        }
    }

    /**
     * 获取鉴权码
     * 
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getCode")
    public void getCode(Model model, HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");// 授权码
        log.info("getCode方法==code:" + code);
        String oauth = "http://9a49499c.ngrok.io" + "/wechat/oauth";
        String getUser = "http://9a49499c.ngrok.io/wx/redirect/wx07308887f42cf969/greet?code="
                + code + "&state=STATE";

        if (StringUtils.isBlank(code)) {// 这个必须有，没有就继续鉴权
            response.sendRedirect(oauth.toString());
        } else {
            response.sendRedirect(getUser.toString());
        }
    }

}
