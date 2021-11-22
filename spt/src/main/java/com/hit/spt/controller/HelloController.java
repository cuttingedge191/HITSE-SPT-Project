package com.hit.spt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class HelloController {

    @RequestMapping("hello")
    public String hello(Model model) {
        model.addAttribute("name", "李四");
        return "index";
    }

    @RequestMapping("login")
    public String login(String email, String password, Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(email + " " + password);
        return "login";

    }

    @RequestMapping("logup")
    public String logup(Model model) {
        return "logup";
    }

    @RequestMapping("addCustomer")
    public String addCustomer(Model model) {
        return "addCustomer";
    }


    @RequestMapping("clientInfoSearch")
    public String clientInfoSearch(Model model) {
        return "clientInfoSearch";
    }
}
