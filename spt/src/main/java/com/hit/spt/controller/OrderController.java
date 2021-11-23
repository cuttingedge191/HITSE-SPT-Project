package com.hit.spt.controller;

import com.hit.spt.pojo.OrderItem;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    public String orderItemInfo(@RequestParam("o_id") Integer o_id, @RequestParam("name") String name, @RequestParam("quantity") Integer quantity, @RequestParam("type") String type, Model model) {

        boolean trade = type.equals("trade");

        OrderItem orderItem = orderItemService.generateOrderItem(o_id, name, quantity, trade);
        orderItemService.insertOrderItem(orderItem);
        // 生成暂存物品列表
        List<OrderItem> orderItemWithNameList = orderItemService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("o_id", o_id);
        return "addOrder";

    }

}