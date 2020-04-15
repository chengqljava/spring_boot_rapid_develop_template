package com.hwsafe.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.demo.base.utils.AppResponse;

@RestController
public class TestController {

    @RequestMapping(value = "/v1/test")
    public AppResponse test() {
        return AppResponse.ok();
    }

    @RequestMapping(value = "/v1/hello", method = RequestMethod.POST)
    public AppResponse hello() {
        System.out.println();
        return AppResponse.ok("hello");
    }
}
