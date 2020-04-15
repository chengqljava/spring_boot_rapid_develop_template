package com.hwsafe.template.controller.ibeetl_demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hwsafe.demo.domain.Person;

@Controller
@RequestMapping("/beetl")
public class BeetlSimpleController {

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("test", "测试");
        Person person = new Person();
        person.setAge(18);
        person.setName("小明");
        person.setId("89387");
        model.addAttribute("person", person);
        return "hello";
    }

    @RequestMapping("/redirect/hello")
    public String redirectHello(Model model) {
        return "redirect:/hello";
    }
}
