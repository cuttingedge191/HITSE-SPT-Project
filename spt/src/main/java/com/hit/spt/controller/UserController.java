package com.hit.spt.controller;

import com.hit.spt.service.LogInUpService;
import com.hit.spt.service.impl.LogInUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class UserController {
    @Autowired
    LogInUpService service;

    @RequestMapping("user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (service.checkPassword(username, password))
            return "index";
        model.addAttribute("msg", "用户名或者密码错误!");
        return "login";
    }

    @RequestMapping("user/logup")
    public String logup(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (service.checkPassword(username, password))
            return "index";
        model.addAttribute("msg", "用户名或者密码错误!");
        return "login";
    }

}
