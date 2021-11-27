package com.hit.spt.controller;

import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrdersViewController {

    @Autowired
    OrderService orderService;

    @RequestMapping("updateOrder")
    public String updateOrder(Integer o_id, Model model) {
        return null;
    }

    @RequestMapping("deleteOrder")
    public String deleteOrder(Integer o_id) {
        orderService.deleteAllOrderItemByOid(o_id);
        orderService.de
    }


}
