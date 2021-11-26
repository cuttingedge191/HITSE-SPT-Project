package com.hit.spt.service.impl;

import com.hit.spt.mapper.*;
import com.hit.spt.pojo.Customer;
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

    @Autowired
    CustomerMapper customerMapper;

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

    /**
     * 为当前订单生成一个商品对象
     *
     * @param o_id     当前订单的o_id
     * @param name     添加的商品名称
     * @param quantity 添加商品的数量
     * @param trade    批发还是零售
     * @return 一个带详细信息的商品项
     */
    @Override
    public OrderItem generateOrderItem(Integer o_id, String name, Integer quantity, Boolean trade) {
        // 生成一个带有详细信息的goodsInfo
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

    /**
     * 这里将一个订购的商品项放入当前的订单中，但是要注意的是，
     * 如果当前的商品是第一次添加商品是直接将该商品项插入数据库
     * 反之则更新在数据库中的商品数量，即合并。
     *
     * @param orderItem 待添加的商品
     */
    @Override
    public void insertOrderItem(OrderItem orderItem) {

        orderItemMapper.insertOrderItem(orderItem);
    }

    @Override
    public void addOneOrderItem(OrderItem orderItem) {
        OrderItem prevOrderItem = orderItemMapper.queryOrderItemByOidGid(orderItem.getO_id(), orderItem.getG_id());
        if (prevOrderItem == null) {
            orderItemMapper.insertOrderItem(orderItem);
        } else {
            prevOrderItem.setQuantity(prevOrderItem.getQuantity() + orderItem.getQuantity());
            prevOrderItem.setCost(prevOrderItem.getCost() + orderItem.getCost());
            prevOrderItem.setPrice(prevOrderItem.getPrice() + orderItem.getPrice());
            orderItemMapper.updateOrderItem(prevOrderItem);
        }
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
    public List<GoodsInfo> getGoodsInfoList() {
        return goodsInfoMapper.queryGoodsInfoList();
    }

    @Override
    public Orders generateOneOrder(Integer o_id, String cname, String type) {
        Orders order = new Orders();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(new Date());
        List<Customer> customers = customerMapper.queryCustomerByName(cname);
        order.setO_id(o_id);
        order.setC_id(customers.get(0).getC_id());
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
