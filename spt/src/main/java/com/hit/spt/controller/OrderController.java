package com.hit.spt.controller;

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

@Controller
@RequestMapping
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    OrdersViewController ordersViewController;

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
    public String addOneOrder(Integer o_id, String item_name, Integer quantity, String cname, String ifUpdate, HttpServletRequest request, Model model) {
        String type = "trade";
        if (request.getRequestURI().equals("/addOneOrderItem")) {
            orderService.genOrderItemForOrder(o_id, item_name, quantity, type, cname, model);
        }
        //update 和 add order不同！！
        if (ifUpdate != null)
            return ordersViewController.updateOrder(o_id, model);

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
        if (request != null && request.getRequestURI().equals("/addOnePosOrderItem")) {
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
     * 提交订单的动作，可能是取消或者保存
     *
     * @param method true为保存， false为取消
     * @param o_id   传递订单id
     * @param cname  客户name
     * @param type   是否批发，trade or retail
     * @return 重定向，防止刷新后产生键值重复的问题
     */

    @RequestMapping("commitOrder")
    public String commitOrder(boolean method, Integer o_id, String cname, String type, Model model, HttpServletRequest request) {
        if (!method) {
            ordersViewController.deleteOrder(o_id);
        } else {
            // 先检查指向这个订单的商品是否非零
            if (orderService.queryOrderItemWithNameListByOid(o_id).size() > 0) {
                // 如果是批发，则是unchecked，反之为closed
                String status = type.equals("trade") ? "unchecked" : "closed";
                // 如果订单号存在，就直接返回，因为这表明是对订单的修改
                if (orderService.checkIfExits(o_id))
                    return "redirect:ordersView";

                orderService.saveOrder(orderService.generateOneOrder(o_id, cname, type, status));
                List<OrderItem> orderItemList = orderService.queryOrderItemWithNameListByOid(o_id);

                if (type.equals("retail") && !orderService.checkIfCanDelivery(orderItemList)) {
                    model.addAttribute("msg", "库存数量不足，无法操作！");

                    return this.addPosOrder(o_id, null, null, null, null, model);
                }else if (type.equals("retail")){
                    ordersViewController.inventoryProcessForOrder(o_id, "closed", model, request);
                }
            } else {
                // 订单商品为0，则删除这个订单
                orderService.deleteAllOrderItemByOid(o_id);
            }
        }
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
        return "ordersView";
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
    public String deleteOneOrderItem(Integer oi_id, Integer o_id, String cname, String type, String ifUpdate, Model model, HttpServletRequest request) {
        String view = "addOrder";
        // 生成暂存物品列表
        orderService.deleteOneOrderItemByOiid(oi_id);

        //update 和 add order不同！！
        if (ifUpdate != null)
            return ordersViewController.updateOrder(o_id, model);

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

}

