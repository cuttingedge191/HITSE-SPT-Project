package com.hit.spt.service.impl;

import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.service.OrdersViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersViewServiceImpl implements OrdersViewService {
    @Autowired
    OrdersMapper ordersMapper;

    @Override
    public void deleteOrder(Integer o_id) {
        ordersMapper.deleteOrdersByOid(o_id);
    }

    @Override
    public void updateOrderStatus(Integer o_id, String status) {
        ordersMapper.updateOrderStatus(o_id, status);
    }
}
