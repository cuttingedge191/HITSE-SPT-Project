package com.hit.spt.service.impl;

import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrdersMapper ordersMapper;

    @Override
    public boolean checkIfExits(Integer o_id) {
        Orders orders = ordersMapper.queryOrdersByOid(o_id);
        return orders != null && orders.getO_id() != null;
    }
}
