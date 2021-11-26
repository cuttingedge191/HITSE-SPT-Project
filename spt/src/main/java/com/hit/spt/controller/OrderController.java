package com.hit.spt.controller;

import com.hit.spt.pojo.Customer;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderService;
import com.hit.spt.service.impl.ClientInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ClientInfoService clientInfoService;

    /**
     * 新建一个订单，并添加商品
     *
     * @param model model，传递信息
     * @param o_id  订单id
     * @return 转发到addOrder
     */
    @RequestMapping("addOneOrder")
    public String addOneOrder(Model model, Integer o_id) {
        if (o_id != null) {
            List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
            model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        }
        Integer OrderId = orderService.genOrderId();
        model.addAttribute("o_id", OrderId);
//        List<Customer> customers = clientInfoService.showAllCustoms();
        // 按照批发类型选择客户
        List<Customer> wholesale_customers = clientInfoService.queryCustomerByType("bulk");
        model.addAttribute("wholesale_customers", wholesale_customers);
        // 货物列表
//        List<GoodsInfo> goodsInfos =
        return "addOrder";
    }

    /**
     * 在订单中添加一个商品
     *
     * @param o_id      订单的id，通过表单传递，下面同理
     * @param item_name 货品名称
     * @param quantity  质量
     * @param type      类型
     * @param c_id      客户id
     * @param model     model传递信息
     * @return 转发到addOrder
     */
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
        model.addAttribute("type", type);
        return "addOrder";

    }

    /**
     * 删除订单中的一个商品
     *
     * @param oi_id 订单商品编号
     * @param o_id  指向的订单编号
     * @param c_id  传递客户c_id
     * @param model model传递信息
     * @return 转发到addOrder
     */
    @RequestMapping("deleteOneOrderItem")
    public String deleteOneOrderItem(Integer oi_id, Integer o_id, Integer c_id, Model model) {
        // 生成暂存物品列表
        orderService.deleteOneOrderItemByOiid(oi_id);
        List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("o_id", o_id);
        model.addAttribute("c_id", c_id);
        return "addOrder";

    }

    /**
     * 提交订单的动作，可能是取消或者保存
     *
     * @param method true为保存， false为取消
     * @param o_id   传递订单id
     * @param c_id   客户id
     * @param type   是否批发，trade or retail
     * @param model  传递信息
     * @return 重定向，防止刷新后产生键值重复的问题
     */
    @RequestMapping("commitOrder")
    public String commitOrder(boolean method, Integer o_id, Integer c_id, String type, Model model) {
        if (!method) {
            orderService.deleteAllOrderItemByOid(o_id);
        } else {
            orderService.saveOrder(orderService.generateOneOrder(o_id, c_id, type));
        }

        List<Orders> ordersList = orderService.getAllOrders();
        model.addAttribute("orders", ordersList);
        return "redirect:ordersView";
    }

    /**
     * 查看订单
     *
     * @param model 用于传递信息
     * @return 转发到该页面
     */
    @RequestMapping("ordersView")
    public String ordersView(Model model) {
        List<Orders> ordersList = orderService.getAllOrders();
        model.addAttribute("orders", ordersList);
        // yyyy.mm.dd.hh.mm
        return "ordersView";
    }
}

