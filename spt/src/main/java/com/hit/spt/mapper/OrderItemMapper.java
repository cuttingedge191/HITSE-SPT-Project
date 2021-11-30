package com.hit.spt.mapper;

import com.hit.spt.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderItemMapper {
    int insertOrderItem(OrderItem orderItem);

    int updateOrderItem(OrderItem orderItem);

    int deleteOrderItemByOid(Integer o_id);

    int deleteOrderItemByGid(Long g_id);

    int deleteOrderItemByOiid(Integer oi_id);

    List<OrderItem> queryOrderItemByOid(Integer o_id);

    List<OrderItem> queryOrderItemByGid(Long g_id);

    List<OrderItem> queryOrderItemList();

    List<OrderItem> queryOrderItemWithNameList();

    List<OrderItem> queryOrderItemWithNameListByOid(Integer o_id);

    OrderItem queryOrderItemByOidGid(Integer o_id, Long g_id);

}
