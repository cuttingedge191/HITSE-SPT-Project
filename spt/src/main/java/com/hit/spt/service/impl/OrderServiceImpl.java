package com.hit.spt.service.impl;

import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.mapper.OrderItemMapper;
import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.GoodsInfo;
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

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

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


    @Override
    public OrderItem generateOrderItem(Integer o_id, String name, Integer quantity, Boolean trade) {
        GoodsInfo goodsInfo = goodsInfoMapper.queryGoodsInfoByName(name);
        OrderItem orderItem = new OrderItem();
        orderItem.setName(name);
        orderItem.setO_id(o_id);
        orderItem.setQuantity(quantity);
        orderItem.setCost(goodsInfo.getCost() * quantity);
        orderItem.setG_id(goodsInfo.getG_id());
        if (trade)
            orderItem.setPrice(goodsInfo.getTrade_price() * quantity);
        else
            orderItem.setPrice(goodsInfo.getRetail_price() * quantity);
        return orderItem;
    }

    @Override
    public void insertOrderItem(OrderItem orderItem) {
        orderItemMapper.insertOrderItem(orderItem);
    }

    @Override
    public void deleteAllOrderItemByOid(Integer o_id) {
        orderItemMapper.deleteOrderItemByOid(o_id);
    }

    @Override
    public void deleteOneOrderItemByOiid(Integer oi_id) {
        orderItemMapper.deleteOrderItemByOiid(oi_id);
    }
}
