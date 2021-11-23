package com.hit.spt.controller;

import com.hit.spt.pojo.OrderItem;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("addOneOrder")
    public String addOneOrder(Model model, Integer o_id) {
        if (o_id != null) {
            List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
            model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        }
        Integer OrderId = orderService.genOrderId();
        model.addAttribute("o_id", OrderId);
        return "addOrder";
    }

    @RequestMapping("addOneOrderItem")
    public String orderItemInfo(Integer o_id, String item_name, Integer quantity, String type, String c_id, Model model) {

        boolean trade = type.equals("trade");

        OrderItem orderItem = orderService.generateOrderItem(o_id, item_name, quantity, trade);
        orderService.insertOrderItem(orderItem);
        // 生成暂存物品列表
        List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("o_id", o_id);
        model.addAttribute("c_id", c_id);
        return "addOrder";

    }

    @RequestMapping("deleteOneOrderItem")
    public String deleteOneOrderItem(Integer oi_id, Integer o_id,Integer c_id, Model model) {
        // 生成暂存物品列表
        orderService.deleteOneOrderItemByOiid(oi_id);
        List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("o_id", o_id);
        model.addAttribute("c_id", c_id);
        return "addOrder";

    }

    @RequestMapping("commitOrder")
    public String cancelOrder(boolean method, Integer o_id, Model model) {
        if (!method)
            orderService.deleteAllOrderItemByOid(o_id);
        System.out.println(o_id);
        return "ordersView";
    }
}