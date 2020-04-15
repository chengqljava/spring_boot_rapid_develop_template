package com.hwsafe.accept.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/accept")
    public String accept(@RequestBody String json) {
        System.out.println("TEST" + json);
        return "success";
    }

}
