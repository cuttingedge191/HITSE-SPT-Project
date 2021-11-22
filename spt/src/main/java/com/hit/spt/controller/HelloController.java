package com.hit.spt.controller;

import com.hit.spt.pojo.Customer;
import com.hit.spt.service.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HelloController {
    @Autowired
    ClientInfoService clientInfoService;

    @RequestMapping("hello")
    public String hello(Model model) {
        model.addAttribute("name", "李四");
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

    @RequestMapping("addCustomer")
    public String addCustomer() {
        return "addCustomer";
    }


    @RequestMapping("clientInfoSearch")
    public String clientInfoSearch(Model model) {
        List<Customer> customers = clientInfoService.showAllCustoms();
        model.addAttribute("customers", customers);
        return "clientInfoSearch";
    }

    @RequestMapping("addCustomerNow")
    public String addCustomerNow(Model model){
        return "clientInfoSearch";
    }
}
