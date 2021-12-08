package com.hit.spt.controller;

import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderService;
import com.hit.spt.service.OrdersViewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping
public class OrdersViewController {

    @Autowired
    OrdersMapper ordersMapper;


    @Autowired
    OrderService orderService;

    @Autowired
    OrdersViewService ordersViewService;

    @RequestMapping("updateOrder")
    public String updateOrder(Integer o_id, Model model) {
//        System.out.println(o_id);
        Orders orders = ordersMapper.queryOrdersByOid(o_id);
        model.addAttribute("type", orders.getType());
        model.addAttribute("cname", orders.getName());
        model.addAttribute("o_id", o_id);
        orderService.getGoodsCustomerInfo(model, o_id, orders.getType());
        return "updateOrder";
    }

    @RequestMapping("commitUpdateOrder")
    public String commitUpDateOrder(Integer o_id) {
        if (o_id != null) {
            List<OrderItem> res = orderService.queryOrderItemWithNameListByOid(o_id);
            if (res.size() <= 0)
                ordersMapper.deleteOrdersByOid(o_id);
        }
        return auditOrder(o_id, "unchecked");
    }

    @RequestMapping("deleteOrder")
    public String deleteOrder(Integer o_id) {
        orderService.deleteAllOrderItemByOid(o_id);
        ordersMapper.deleteOrdersByOid(o_id);
        return "redirect:ordersView";
    }


    /**
     * =======================================下面将开启及其恶心的东西！！！！！======================================
     */

    @RequestMapping("orderReview")
    public String orderReview(Integer o_id, Model model) {
        Orders orders = ordersMapper.queryOrdersByOid(o_id);
        orderService.getGoodsCustomerInfo(model, o_id, orders.getType());
        model.addAttribute("theOrder", orders);
        return "orderReview";
    }

    @RequestMapping("auditOrder")
    public String auditOrder(Integer o_id, String status) {
        if (!status.equals("rejectRefund"))
            ordersViewService.updateOrderStatus(o_id, status);
        return "redirect:ordersView";
    }

    /**
     * 这里进行自动货品出库和退货后的入库处理
     *
     * @param o_id    订单编号
     * @param request 请求
     * @return 转发
     */
    @RequestMapping({"refundOrder", "autoDelivery"})
    public String inventoryProcessForOrder(Integer o_id, String status, Model model, HttpServletRequest request) {
        List<OrderItem> orderItemList = orderService.queryOrderItemWithNameListByOid(o_id);
        Orders currentOrder = ordersMapper.queryOrdersByOid(o_id);
        if (request.getRequestURI().equals("/refundOrder")) {
            // 这里进行自动回库
            orderService.autoInventoryRefund(orderItemList);

        } else {
            // 这里进行自动出库
            if (currentOrder.getStatus().equals("checked") || currentOrder.getStatus().equals("paid")) {
                return auditOrder(o_id, status);
            }

            if (!orderService.checkIfCanDelivery(orderItemList)) {
                model.addAttribute("msg", "库存数量不足 无法通过！");
                return orderReview(o_id, model);
            }

            orderService.autoInventoryDelivery(orderItemList);
        }

        return auditOrder(o_id, status);
    }


}
