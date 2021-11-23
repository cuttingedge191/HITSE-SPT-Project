package com.hit.spt.service;

import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;


import java.util.List;

public interface OrderService {
    boolean checkIfExits(Integer i_od);

    Integer genOrderId();

    List<OrderItem> queryOrderItemWithNameListByOid(Integer o_id);

    OrderItem generateOrderItem(Integer o_id, String name, Integer quantity, Boolean trade);

    void insertOrderItem(OrderItem orderItem);

    void deleteAllOrderItemByOid(Integer o_id);

    void deleteOneOrderItemByOiid(Integer oi_id);

    Orders generateOneOrder(Integer o_id, Integer c_id, String type);

    List<Orders> getAllOrders();

    int saveOrder(Orders orders);
}
