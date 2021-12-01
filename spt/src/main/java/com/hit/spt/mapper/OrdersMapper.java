package com.hit.spt.mapper;

import com.hit.spt.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersMapper {
    List<Orders> queryOrdersByCid(Integer c_id);

    int insertOrder(Orders orders);

    void updateOrderStatus(Integer o_id, String status);

    Orders queryOrdersByOid(Integer o_id);

    List<Orders> queryOrdersByStatus(String status);

    List<Orders> queryOrdersByType(String type);

    void deleteOrdersByOid(Integer o_id);

    List<Orders> queryAllOrders();

    List<Orders> queryAllOrdersWithCname();

}
