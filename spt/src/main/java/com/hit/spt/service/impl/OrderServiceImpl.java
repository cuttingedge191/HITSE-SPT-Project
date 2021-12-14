package com.hit.spt.service.impl;

import com.hit.spt.mapper.*;
import com.hit.spt.pojo.*;
import com.hit.spt.service.InventoryService;
import com.hit.spt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
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

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    ClientInfoService clientInfoService;

    @Autowired
    InventoryService inventoryService;


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
    public Double calcTotalPriceByOid(Integer o_id) {
        double result = 0;
        List<OrderItem> itemList = orderItemMapper.queryOrderItemByOid(o_id);
        for (OrderItem item : itemList) {
            result += item.getPrice();
        }
        return result;
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
    /**
     *
     * 同时产出这个订单的商品和订单
     */
    public void deleteAllOrderItemByOid(Integer o_id) {
        orderItemMapper.deleteOrderItemByOid(o_id);
        ordersMapper.deleteOrdersByOid(o_id);
    }

    @Override
    public void deleteOneOrderItemByOiid(Integer oi_id) {
        orderItemMapper.deleteOrderItemByOiid(oi_id);
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersMapper.queryAllOrdersWithCname();
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
    public Orders generateOneOrder(Integer o_id, String cname, String type, String status) {
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
            order.setTotal_cost(Math.round(100 * (order.getTotal_cost() + orderItem.getCost())) / 100.0);
            order.setTotal_turnover(Math.round(100 * (order.getTotal_turnover() + orderItem.getPrice())) / 100.0);
        }
        order.setTotal_profit(Math.round(100 * (order.getTotal_turnover() - order.getTotal_cost())) / 100.0);
        order.setType(type);
        order.setStatus(status);

        return order;
    }

    /**
     * 将于该订单绑定的货品信息进行传送
     *
     * @param model 传参
     * @param o_id  订单id
     */
    @Override
    public void getGoodsCustomerInfo(Model model, Integer o_id, String type) {
        List<GoodsInfo> goodsInfoList = getGoodsInfoList();
        model.addAttribute("goodsInfoList", goodsInfoList);
        List<OrderItem> orderItemWithNameList = queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        if (type != null && type.equals("trade")) {
            List<Customer> trade_customers = clientInfoService.queryCustomerByType("trade");
            // System.out.println(trade_customers);
            model.addAttribute("trade_customers", trade_customers);
        } else {
            List<Customer> retail_customers = clientInfoService.queryCustomerByType("retail");
            // System.out.println(trade_customers);
            model.addAttribute("retail_customers", retail_customers);
        }

    }

    /**
     * 根据相关信息，生成order_item，并与当前订单绑定
     *
     * @param o_id      订单id
     * @param item_name 物品名
     * @param quantity  数量
     * @param type      交易模式
     * @param cname     客户名
     * @param model     传参
     */
    @Override
    public void genOrderItemForOrder(Integer o_id, String item_name, Integer quantity, String type, String cname, Model model) {
        boolean trade = type != null && type.equals("trade");

        OrderItem orderItem = generateOrderItem(o_id, item_name, quantity, trade);
        addOneOrderItem(orderItem);
        // 生成暂存物品列表
        List<OrderItem> orderItemWithNameList = queryOrderItemWithNameListByOid(o_id);
        model.addAttribute("orderItemWithNameList", orderItemWithNameList);
        model.addAttribute("cname", cname);
    }

    @Override
    public boolean checkIfCanDelivery(List<OrderItem> orderItemList) {
        boolean res = true;

        for (OrderItem orderItem : orderItemList) {
            int total = totalQuantity(orderItem.getG_id());
            res = res && total >= orderItem.getQuantity();
        }

        return res;
    }

    private class inventoryComparator implements Comparator<Inventory> {

        @Override
        public int compare(Inventory o1, Inventory o2) {
            return o1.getInventory_prior() - o2.getInventory_prior();
        }
    }

    @Override
    public void autoInventoryDelivery(List<OrderItem> orderItemList) {

        for (OrderItem oi : orderItemList) {
            List<Inventory> inventories = inventoryMapper.selectInventoryByName(oi.getName());
            for (Inventory inventory : inventories) {
                inventory.setInventory_prior(inventoryMapper.queryWarehouseByIlID(inventory.getIl_id()).getInventory_prior());
            }
            inventories.sort(new inventoryComparator());
            for (Inventory inventory : inventories) {
                if (oi.getQuantity() > inventory.getQuantity()) {
                    oi.setQuantity(oi.getQuantity() - inventory.getQuantity());
                    inventoryService.decreaseInventory(inventory.getQuantity(), inventory);
                } else {
                    inventoryService.decreaseInventory(oi.getQuantity(), inventory);
                    break;
                }
            }
        }
    }

    @Override
    public void autoInventoryRefund(List<OrderItem> orderItemList) {

        List<Inventory> inventoryLists = inventoryService.queryWarehouseList();
        Integer il_id = null;
        Integer prior = Integer.MAX_VALUE;
        for (Inventory inventory : inventoryLists) {
            if (inventory.getInventory_prior() < prior) {
                prior = inventory.getInventory_prior();
                il_id = inventory.getIl_id();
            }

        }

        for (OrderItem orderItem : orderItemList) {
            Inventory inventory = new Inventory();
            inventory.setCost(orderItem.getCost() / orderItem.getQuantity());
            inventory.setName(orderItem.getName());
            inventory.setQuantity(orderItem.getQuantity());
            inventory.setIl_id(il_id);
            inventory.setG_id(orderItem.getG_id());
            inventory.setQuality("nice");
            inventoryService.mergeInsertInventory(inventory);
        }

    }


    public int totalQuantity(Long g_id) {
        List<Integer> quantityList = inventoryMapper.queryQuantityByGid(g_id);
        int res = 0;
        for (Integer quantity : quantityList) {
            res += quantity;
        }
        return res;
    }
}
