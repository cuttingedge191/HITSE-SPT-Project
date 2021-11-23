package com.hit.spt.controller;

import com.hit.spt.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrderController {
    @Autowired
    OrderItemService orderItemService;

    @RequestMapping("addOneOrder")
    public String addOneOrder(Model model) {
        Integer OrderId = orderItemService.genOrderId();
        model.addAttribute("o_id", OrderId);
        return "addOrder";
    }
}
