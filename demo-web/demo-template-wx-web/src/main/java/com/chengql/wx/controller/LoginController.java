package com.chengql.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengql.common.SpringContextHolder;
import com.chengql.utils.CookieUtils;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
public class LoginController {
    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(Model model) {
        WxMpUser user = JSONObject.toJavaObject(
                JSON.parseObject(CookieUtils.getCookie(
                        SpringContextHolder.getHttpServletRequest(), "name")),
                WxMpUser.class);
        model.addAttribute("user", user);
        return "login";
    }
}
