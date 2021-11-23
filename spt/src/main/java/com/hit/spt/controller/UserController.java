package com.hit.spt.controller;

import com.hit.spt.pojo.User;
import com.hit.spt.service.LogInUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class UserController {
    @Autowired
    LogInUpService logInUpService;

    @RequestMapping("user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (logInUpService.checkPassword(username, password))
            return "index";
        model.addAttribute("msg", "用户名或者密码错误!");
        return "login";
    }

    @RequestMapping("user/logup")
    public String logup(User user, Model model) {
        if (logInUpService.registerUser(user)) {
            return "login";
        }
        return null;
    }

}
