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
     * 新建一个订单，并添加商品
     *
     * @param model model，传递信息
     * @param o_id  订单id
     * @return 转发到addOrder
     */
    @RequestMapping({"addOrder", "addOneOrderItem"})
    public String addOneOrder(Integer o_id, String item_name, Integer quantity, String type, String cname, HttpServletRequest request, Model model) {
        if (request.getRequestURI().equals("/addOneOrderItem")) {
            boolean trade = type.equals("trade");

            OrderItem orderItem = orderService.generateOrderItem(o_id, item_name, quantity, trade);
            orderService.addOneOrderItem(orderItem);
            // 生成暂存物品列表
            List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
            model.addAttribute("orderItemWithNameList", orderItemWithNameList);
            model.addAttribute("cname", cname);
            model.addAttribute("type", type);
        }

        if (o_id != null) {
            model.addAttribute("o_id", o_id);
        } else {
            Integer OrderId = orderService.genOrderId();
            model.addAttribute("o_id", OrderId);
        }

        return getGoodsCustomerInfo(model, o_id);
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
    @RequestMapping("deleteOneOrderItem")
    public String deleteOneOrderItem(Integer oi_id, Integer o_id, String cname, Model model) {
        // 生成暂存物品列表
        orderService.deleteOneOrderItemByOiid(oi_id);
        List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("o_id", o_id);
        model.addAttribute("cname", cname);
        return getGoodsCustomerInfo(model, o_id);

    }

    private String getGoodsCustomerInfo(Model model, Integer o_id) {
        List<GoodsInfo> goodsInfoList = orderService.getGoodsInfoList();
        model.addAttribute("goodsInfoList", goodsInfoList);
        List<OrderItem> orderItemWithNameList = orderService.queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        List<Customer> trade_customers = clientInfoService.queryCustomerByType("trade");
        // System.out.println(trade_customers);
        model.addAttribute("trade_customers", trade_customers);
        return "addOrder";
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

