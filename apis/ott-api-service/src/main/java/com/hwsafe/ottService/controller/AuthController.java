package com.hwsafe.ottService.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hwsafe.ottService.base.utils.AppResponse;

/**
 * @author chengql 验证
 *
 */
@RestController
public class AuthController {
    @RequestMapping("/validate")
    public AppResponse validate() {
        return AppResponse.ok("验证成功");
    }
}
