package com.hit.spt.service;

import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import org.springframework.ui.Model;


import java.util.List;

public interface OrderService {
    boolean checkIfExits(Integer o_id);

    Integer genOrderId();

    List<OrderItem> queryOrderItemWithNameListByOid(Integer o_id);

    Double calcTotalPriceByOid(Integer o_id);

    OrderItem generateOrderItem(Integer o_id, String name, Integer quantity, Boolean trade);

    int saveOrder(Orders orders);

    void addOneOrderItem(OrderItem orderItem);

    void insertOrderItem(OrderItem orderItem);

    void deleteAllOrderItemByOid(Integer o_id);

    void deleteOneOrderItemByOiid(Integer oi_id);

    Orders generateOneOrder(Integer o_id, String cname, String type, String status);

    List<Orders> getAllOrders();

    List<GoodsInfo> getGoodsInfoList();

    void getGoodsCustomerInfo(Model model, Integer o_id, String type);

    void genOrderItemForOrder(Integer o_id, String item_name, Integer quantity, String type, String cname, Model model);

    boolean checkIfCanDelivery(List<OrderItem> orderItemList);

    void autoInventoryDelivery(List<OrderItem> orderItemList);

    void autoInventoryRefund(List<OrderItem> orderItemList);
}
