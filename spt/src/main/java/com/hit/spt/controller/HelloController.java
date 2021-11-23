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

    @RequestMapping("addInventory")
    public String addInventory(Model model) {
        return "addInventory";
    }

    @RequestMapping("addOrder")
    public String addOrder(Model model) {
        return "addOrder";
    }

    // ###以下均待处理###

    @RequestMapping("goodsView")
    public String goodsView(Model model) {
        return "goodsView";
    }

}
