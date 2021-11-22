package com.example.hit_se_spt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("hello")
    public String hello(Model model){
        model.addAttribute("name", "李四");
        return "index";
    }
    @RequestMapping("login")
    public String login(Model model){
        return "login";
    }
}
