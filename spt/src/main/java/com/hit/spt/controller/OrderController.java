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

import javax.servlet.http.HttpServletRequest;
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
     * @param o_id      当前订单的o_id，如果没有就生成
     * @param item_name 货品名称
     * @param quantity  数量
     * @param cname     客户名，用于绑定c_id
     * @param request   请求
     * @param model     传参数
     * @return 转发
     */
    @RequestMapping({"addOrder", "addOneOrderItem"})
    public String addOneOrder(Integer o_id, String item_name, Integer quantity, String cname, HttpServletRequest request, Model model) {
        String type = "trade";
        if (request.getRequestURI().equals("/addOneOrderItem")) {
            orderService.genOrderItemForOrder(o_id, item_name, quantity, type, cname, model);
        }
        if (o_id != null) {
            model.addAttribute("o_id", o_id);
        } else {
            Integer OrderId = orderService.genOrderId();
            model.addAttribute("o_id", OrderId);
        }
        orderService.getGoodsCustomerInfo(model, o_id, type);
        return "addOrder";
    }

    @RequestMapping({"pos", "addOnePosOrderItem"})
    public String addPosOrder(Integer o_id, String item_name, Integer quantity, String cname, HttpServletRequest request, Model model) {
        String type = "retail";
        if (request.getRequestURI().equals("/addOnePosOrderItem")) {
            orderService.genOrderItemForOrder(o_id, item_name, quantity, type, cname, model);
            model.addAttribute("totalPrice", orderService.calcTotalPriceByOid(o_id));
        }
        if (o_id != null) {
            model.addAttribute("o_id", o_id);
        } else {
            Integer OrderId = orderService.genOrderId();
            model.addAttribute("o_id", OrderId);
        }
        orderService.getGoodsCustomerInfo(model, o_id, type);
        return "pos";
    }

    /**
     * 删除订单中的一个商品
     *
     * @param oi_id 订单商品编号
     * @param o_id  指向的订单编号
     * @param cname 传递客户cname
     * @param model model传递信息
     * @return 转发到addOrder
     */
    @RequestMapping({"deleteOneOrderItem", "deleteOnePosOrderItem"})
    public String deleteOneOrderItem(Integer oi_id, Integer o_id, String cname, String type, Model model, HttpServletRequest request) {
        String view = "addOrder";
        // 生成暂存物品列表
        orderService.deleteOneOrderItemByOiid(oi_id);

        if (request.getRequestURI().equals("/deleteOnePosOrderItem")) {
            view = "pos";
            model.addAttribute("totalPrice", orderService.calcTotalPriceByOid(o_id));
        }

        List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("o_id", o_id);
        model.addAttribute("cname", cname);
        orderService.getGoodsCustomerInfo(model, o_id, type);
        return view;
    }


    /**
     * 提交订单的动作，可能是取消或者保存
     *
     * @param method true为保存， false为取消
     * @param o_id   传递订单id
     * @param cname  客户name
     * @param type   是否批发，trade or retail
     * @param model  传递信息
     * @return 重定向，防止刷新后产生键值重复的问题
     */

    @RequestMapping("commitOrder")
    public String commitOrder(boolean method, Integer o_id, String cname, String type, Model model) {
        if (!method) {
            orderService.deleteAllOrderItemByOid(o_id);
        } else {
            if (orderService.queryOrderItemWithNameListByOid(o_id).size() > 0)
                orderService.saveOrder(orderService.generateOneOrder(o_id, cname, type));
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

