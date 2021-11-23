package com.hit.spt.mapper;

import com.hit.spt.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderItemMapper {
    int insertOrderItem(OrderItem orderItem);

    int deleteOrderItemByOid(Integer o_id);

    int deleteOrderItemByGid(Integer g_id);

    List<OrderItem> queryOrderItemByOid(Integer o_id);

    List<OrderItem> queryOrderItemByGid(Integer g_id);

    List<OrderItem> queryOrderItemList();

    List<OrderItem> queryOrderItemWithNameList();
}
