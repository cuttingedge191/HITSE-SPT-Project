package com.hit.spt.controller;

import com.hit.spt.service.OverViewService;
import com.hit.spt.service.impl.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping
public class HelloController {
    @Autowired
    ClientInfoService clientInfoService;

    @Autowired
    OverViewService overViewService;

    @RequestMapping("index")
    public String hello(Model model) {
        List<String> inventoryData = overViewService.getInventoryOverView(1);
        model.addAttribute("legend_data", inventoryData.get(0));
        model.addAttribute("series_data", inventoryData.get(1));
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

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "login";
    }
}
