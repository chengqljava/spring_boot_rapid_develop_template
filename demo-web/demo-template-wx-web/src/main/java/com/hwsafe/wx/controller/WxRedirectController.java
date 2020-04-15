package com.hwsafe.wx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;
import com.hwsafe.common.SpringContextHolder;
import com.hwsafe.utils.CookieUtils;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author Edward
 */
@Controller
@RequestMapping("/wx/redirect/{appid}")
public class WxRedirectController {
    @Autowired
    private WxMpService wxService;

    @RequestMapping("/login")
    public String greetUser(@PathVariable String appid,
            @RequestParam String code, Model model) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(
                    String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        try {
            WxMpOAuth2AccessToken accessToken = wxService
                    .oauth2getAccessToken(code);
            WxMpUser user = wxService.oauth2getUserInfo(accessToken, null);
            model.addAttribute("user", user);
            CookieUtils.setCookie(SpringContextHolder.getHttpServletResponse(),
                    "name", JSONObject.toJSONString(user), 600);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        // 跳转登录注册
        return "redirect:/login";
    }

    @RequestMapping("/register")
    public String register(@PathVariable String appid,
            @RequestParam String code, Model model) {
        if (!this.wxService.switchover(appid)) {
            throw new IllegalArgumentException(
                    String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        try {
            WxMpOAuth2AccessToken accessToken = wxService
                    .oauth2getAccessToken(code);
            WxMpUser user = wxService.oauth2getUserInfo(accessToken, null);
            model.addAttribute("user", user);
            CookieUtils.setCookie(SpringContextHolder.getHttpServletResponse(),
                    "name", JSONObject.toJSONString(user), 600);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        // 跳转登录注册
        return "redirect:/register";
    }
}