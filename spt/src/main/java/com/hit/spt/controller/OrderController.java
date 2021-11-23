package com.hit.spt.controller;

import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class OrderController {
    @Autowired
    OrderService orderItemService;

    @RequestMapping("addOneOrder")
    public String addOneOrder(Model model) {
        Integer OrderId = orderItemService.genOrderId();
        model.addAttribute("o_id", OrderId);
        return "addOrder";
    }

    @RequestMapping("addOneOrderItem")
    public String orderItemInfo(@RequestParam("o_id") Integer o_id, @RequestParam("name") String name, @RequestParam("quantity") Integer quantity, Model model) {
        Integer OrderId = orderItemService.genOrderId();
        model.addAttribute("o_id", OrderId);

        return "addOrder";
    }
}
