package com.hit.spt.service;

import com.hit.spt.pojo.Orders;
import org.springframework.core.annotation.Order;

import java.util.List;

public interface OrderItemService {
    boolean checkIfExits(Integer i_od);

    Integer genOrderId();

    List<Orders> getAllOrders();
}
