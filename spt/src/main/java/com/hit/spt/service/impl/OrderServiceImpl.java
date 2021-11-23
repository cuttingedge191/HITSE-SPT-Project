package com.hit.spt.service.impl;

import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.mapper.OrderItemMapper;
import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.GoodsInfo;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Override
    public List<Orders> getAllOrders() {
        return ordersMapper.queryAllOrders();
    }

    @Override
    public int saveOrder(Orders orders) {
        return ordersMapper.insertOrder(orders);
    }

    @Override
    public Orders generateOneOrder(Integer o_id, Integer c_id, String type) {
        Orders order = new Orders();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());

        order.setO_id(o_id);
        order.setC_id(c_id);
        order.setTime_stamp(date);
        order.setTotal_cost(0.0);
        order.setTotal_turnover(0.0);
        List<OrderItem> orderItemList = orderItemMapper.queryOrderItemByOid(o_id);
        for (OrderItem orderItem : orderItemList) {
            order.setTotal_cost(order.getTotal_cost() + orderItem.getCost());
            order.setTotal_turnover(order.getTotal_turnover() + orderItem.getPrice());
        }
        order.setTotal_profit(order.getTotal_turnover() - order.getTotal_cost());
        order.setType(type);
        order.setStatus("unchecked");

        return order;
    }
}
