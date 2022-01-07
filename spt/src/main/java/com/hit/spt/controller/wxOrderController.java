package com.hit.spt.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.Customer;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.GoodsService;
import com.hit.spt.service.OrderService;
import com.hit.spt.service.impl.ClientInfoService;
import com.hit.spt.service.OrdersViewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.IntegerSyntax;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class wxOrderController {

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ClientInfoService clientInfoService;

    @Autowired
    OrdersViewService ordersViewService;

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

    @RequestMapping("/mall/queryOrderStatusByOid")
    public int getOrderStatusByOid(String o_id) {
        int io_id = Integer.parseInt(o_id);
        String status = orderService.queryOrderStatusByOid(io_id);
        int res = -1;
        switch (status) {
            case "failed":
                res = 0;
                break;
            case "unchecked":
                res = 1;
                break;
            case "checked":
                res = 2;
                break;
            case "paid":
                res = 3;
                break;
            case "received":
                res = 4;
                break;
            case "refund":
                res = 5;
                break;
            case "returned":
                res = 6;
                break;
            case "closed":
                res = 7;
                break;
        }
        return res;
    }

    @RequestMapping("/mall/queryOrderItemsByOid")
    public List<OrderItem> getOrderItemsByOid(String o_id) {
        int io_id = Integer.parseInt(o_id);
        return orderService.queryOrderItemWithNameListByOid(io_id);
    }

    @RequestMapping("/mall/commitOrderFromCart")
    public String commitOrderFromCart(@RequestBody JSONObject jsonObject) {
        // 解析JSON对象
        JSONArray idArr = jsonObject.getJSONArray("ids");
        JSONArray numArr = jsonObject.getJSONArray("nums");
        int c_id = Integer.parseInt(jsonObject.getString("c_id"));
        String c_type = jsonObject.getString("c_type");
        List<String> idList = idArr.toJavaList(String.class);
        List<Integer> numList = numArr.toJavaList(Integer.class);
        int o_id = orderService.genOrderId();
        // 添加所有销售单项(OrderItem)
        double total_turnover = 0.0;
        double total_cost = 0.0;
        double total_profit;
        for (int i = 0; i < idList.size(); ++i) {
            Long g_id = Long.parseLong(idList.get(i));
            int quantity = numList.get(i);
            GoodsInfo goodsInfo = goodsService.queryGoodsInfoByGid(g_id);
            double cost = Math.round(100 * numList.get(i) * goodsInfo.getCost()) / 100.0;
            double price;
            if (c_type.equals("retail")) {
                price = Math.round(100 * goodsInfo.getRetail_price() * quantity) / 100.0;
            } else {
                price = Math.round(100 * goodsInfo.getTrade_price() * quantity) / 100.0;
            }
            total_cost += cost;
            total_turnover += price;
            OrderItem orderItem = new OrderItem(null, o_id, g_id, quantity, cost, price, null);
            orderService.addOneOrderItem(orderItem);
        }
        total_profit = total_turnover - total_cost;
        // 生成销售单
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        Orders order = new Orders(o_id, c_id, c_type, "unchecked", total_turnover, total_cost, total_profit, date, null);
        orderService.saveOrder(order);
        return "ok";
    }

    @RequestMapping("/mall/editOrder")
    public String editOrderStatus(String o_id, String status) {
        int io_id = Integer.parseInt(o_id);
        System.out.println(status);
        if (status.equals("failed")) {
            ordersViewService.updateOrderStatus(io_id, status);
            return "failed";
        } else if (status.equals("paid")) {
            ordersViewService.updateOrderStatus(io_id, status);
            return "paid";
        } else if (status.equals("received")) {
            ordersViewService.updateOrderStatus(io_id, status);
            return "received";
        } else if (status.equals("delete")) {
            orderService.deleteAllOrderItemByOid(io_id);
            ordersMapper.deleteOrdersByOid(io_id);
            return "delete";
        } else if (status.equals("refund")) {
            ordersViewService.updateOrderStatus(io_id, status);
            return "refund";
        }
        return "error";
    }

}
