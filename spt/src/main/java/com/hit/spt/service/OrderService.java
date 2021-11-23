package com.hit.spt.service;

import com.hit.spt.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    boolean checkIfExits(Integer i_od);

    Integer genOrderId();

    List<OrderItem> queryOrderItemWithNameListByOid(Integer o_id);
}
