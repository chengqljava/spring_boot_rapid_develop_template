package com.hwsafe.template.controller.redis_demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwsafe.template.base.utils.RedisUtil;

@Controller
public class RedisController {

    private RedisUtil redisUtil;

    @RequestMapping("/redis/write")
    @ResponseBody
    public String write(String value) {
        redisUtil.set("testKey", value);
        return value;
    }

    @RequestMapping("/redis/read")
    @ResponseBody
    public String read(String key) {
        return redisUtil.get("testKey");
    }

}
