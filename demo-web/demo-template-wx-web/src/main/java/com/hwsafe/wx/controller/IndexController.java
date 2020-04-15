package com.hwsafe.wx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hwsafe.common.SpringContextHolder;
import com.hwsafe.utils.CookieUtils;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
public class IndexController {
    @RequestMapping(value = "/index")
    public String index(Model model) {
        WxMpUser user = JSONObject.toJavaObject(
                JSON.parseObject(CookieUtils.getCookie(
                        SpringContextHolder.getHttpServletRequest(), "name")),
                WxMpUser.class);
        model.addAttribute("user", user);
        return "index";
    }
}
