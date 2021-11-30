package com.hit.spt.service;

public interface OrdersViewService {
    void deleteOrder(Integer o_id);

    void updateOrderStatus(Integer o_id, String status);
}
