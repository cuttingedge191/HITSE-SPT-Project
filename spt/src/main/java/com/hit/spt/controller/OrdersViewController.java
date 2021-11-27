package com.hit.spt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrdersViewController {

    @RequestMapping("updateOrder")
    public String updateOrder() {
        return null;
    }

}
