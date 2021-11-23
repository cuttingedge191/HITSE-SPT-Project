package com.hit.spt.service.impl;

import com.hit.spt.mapper.OrderItemMapper;
import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public boolean checkIfExits(Integer o_id) {
        Orders orders = ordersMapper.queryOrdersByOid(o_id);
        return orders != null && orders.getO_id() != null;
    }

    @Override
    public Integer genOrderId() {
        int OrderId = 1;
        while (checkIfExits(OrderId))
            OrderId += 1;
        return OrderId;
    }

    @Override
    public List<OrderItem> queryOrderItemWithNameListByOid(Integer o_id) {
        return orderItemMapper.queryOrderItemWithNameListByOid(o_id);
    }
}
