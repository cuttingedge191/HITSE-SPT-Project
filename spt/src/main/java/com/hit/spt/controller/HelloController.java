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

    // 不再使用了，转至OrderController
//    @RequestMapping("addOrder")
//    public String addOrder(Model model) {
//        return "addOrder";
//    }

    // ###以下均待处理###

    @RequestMapping("userInfoSearch")
    public String userInfoSearch(Model model) {return "userInfoSearch"; }

    @RequestMapping("pos")
    public String posForRetail(Model model) {
        return "pos";
    }

    @RequestMapping("inventoryTrans")
    public String inventoryTrans(Model model) {
        return "inventoryTrans";
    }

    @RequestMapping("orderReview")
    public String orderReview(Model model) {
        return "orderReview";
    }

}
