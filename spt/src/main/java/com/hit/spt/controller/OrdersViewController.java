package com.hit.spt.controller;

import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrdersViewController {

    @Autowired
    OrdersMapper ordersMapper;


    @Autowired
    OrderService orderService;

    @RequestMapping("updateOrder")
    public String updateOrder(Integer o_id, Model model) {
        System.out.println(o_id);
        Orders orders = ordersMapper.queryOrdersByOid(o_id);
        model.addAttribute("type", orders.getType());
        model.addAttribute("cname", orders.getName());
        model.addAttribute("o_id", o_id);
        orderService.getGoodsCustomerInfo(model, o_id, orders.getType());
        return "updateOrder";
    }

    @RequestMapping("commitUpdateOrder")
    public String commitUpDateOrder() {
        return "redirect:ordersView";
    }

    @RequestMapping("deleteOrder")
    public String deleteOrder(Integer o_id) {
        orderService.deleteAllOrderItemByOid(o_id);
        return null;
    }


}
