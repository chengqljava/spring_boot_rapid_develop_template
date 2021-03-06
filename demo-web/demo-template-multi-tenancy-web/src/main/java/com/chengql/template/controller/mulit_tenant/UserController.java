package com.chengql.template.controller.mulit_tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chengql.demo.domain.User;
import com.chengql.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public String hello() {
        userService.count();
        userService.getAddrAndUser();
        userService.getUserAndAddr();
        userService.getAddrAndUserMap();
        userService.userSchool();
        return "test";
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save() {
        User user = new User();
        user.setId(3L);
        user.setName("hel");
        userService.save(user);
        return "save";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String update() {
        User user = new User();
        user.setId(1L);
        user.setName("hello");
        userService.updateById(user);
        return "update";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete() {

        userService.removeById(1L);
        return "delete";
    }
}
