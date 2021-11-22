package com.hit.spt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController {
    @RequestMapping("login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("aaaa");
        return null;
    }


}
