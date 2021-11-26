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

    /**
     * 进行登录
     *
     * @param username 用户名
     * @param password 密码
     * @param model    传递信息
     * @return 转发到主页（暂时没有）
     */
    @RequestMapping("user/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        if (logInUpService.checkPassword(username, password))
            return "index";
        model.addAttribute("msg", "用户名或者密码错误!");
        return "login";
    }

    /**
     * 进行注册
     *
     * @param user  囊括了所有的用户信息，打包到user中
     * @param model
     * @return
     */
    @RequestMapping("user/logup")
    public String logup(User user, Model model) {
        if (logInUpService.registerUser(user)) {
            return "login";
        }
        model.addAttribute("msg", "用户名已存在");
        return "logup";
    }

}
