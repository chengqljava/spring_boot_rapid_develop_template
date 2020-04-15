package com.hwsafe.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hwsafe.demo.base.utils.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Api("登录相关接口")
@Controller
@RequestMapping("/mobile/login")
public class LoginController {

    @RequestMapping(value = "/v1/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "登录接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true, dataType = "String") })
    public AppResponse login(HttpServletResponse response,
            @RequestParam String username, @RequestParam String password) {
        return AppResponse.ok("成功");
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "退出登录")
    public AppResponse logout() {

        return AppResponse.ok("退出成功");
    }
}
