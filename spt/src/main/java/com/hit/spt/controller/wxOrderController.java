package com.hit.spt.controller;

import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class wxOrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/mall/getOrderCntByCid")
    public List<Integer> getOrderCntByCid(String c_id) {
        int ic_id = Integer.parseInt(c_id);
        return orderService.getOrderCntByCid(ic_id);
    }

    @RequestMapping("/mall/queryOrdersByCidAndStatus")
    public List<Orders> getOrdersByCidAndStatus(String c_id, String status) {
        int ic_id = Integer.parseInt(c_id);
        return orderService.queryOrdersByCidAndStatus(ic_id, status);
    }
}
