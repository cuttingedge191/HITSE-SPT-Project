package com.hit.spt.controller;

import com.hit.spt.pojo.Customer;
import com.hit.spt.service.impl.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class HelloController {
    @Autowired
    ClientInfoService clientInfoService;

    @RequestMapping("index")
    public String hello(Model model) {
        return "index";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @RequestMapping("logup")
    public String logup(Model model) {
        return "logup";
    }

    // ###以下均待处理###

}
