package com.hit.spt.service.impl;

import com.alibaba.fastjson.JSON;
import com.hit.spt.mapper.GoodsInfoMapper;
import com.hit.spt.mapper.InventoryMapper;
import com.hit.spt.mapper.OrderItemMapper;
import com.hit.spt.mapper.OrdersMapper;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.pojo.OrderItem;
import com.hit.spt.pojo.Orders;
import com.hit.spt.pojo.Stock;
import com.hit.spt.service.OverViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OverViewServiceImpl implements OverViewService {

    @Autowired
    InventoryMapper inventoryMapper;

    @Autowired
    OrdersMapper ordersMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    /**
     * 获取指定仓库的库存概览饼状图所需数据
     *
     * @param il_id 仓库ID
     * @return 图例中项信息、数据信息(index.html)
     */
    @Override
    public List<String> getInventoryOverView(Integer il_id) {
        ArrayList<String> item_list = new ArrayList<>();
        ArrayList<Map<String, String>> data = new ArrayList<>();
        List<Inventory> inventoryInfo = inventoryMapper.queryInventoryWithIlid(il_id);
        List<String> result = new ArrayList<>();
        if (inventoryInfo == null || inventoryInfo.size() == 0) {
            result.add("[\"无数据\"]");
            result.add("[{\"name\":\"无数据\", \"value\":\"0\"}]");
            return result;
        }
        for (Inventory inventory : inventoryInfo) {
            double total_cost = inventory.getCost() * inventory.getQuantity();
            inventory.setCost(total_cost);
        }
        // 根据单个货品在仓库中的积压资金额进行降序排序
        inventoryInfo.sort((o1, o2) -> o2.getCost().compareTo(o1.getCost()));
        if (inventoryInfo.size() > 5) {
            // 取前5个货品单独展示
            for (int i = 0; i < 5; ++i) {
                item_list.add(inventoryInfo.get(i).getName());
                Map<String, String> map = new HashMap<>();
                map.put("value", inventoryInfo.get(i).getCost().toString());
                map.put("name", inventoryInfo.get(i).getName());
                data.add(map);
            }
            // 其余货品纳入其他
            Double cost_other = (double) 0;
            for (int i = 5; i < inventoryInfo.size(); ++i)
                cost_other += inventoryInfo.get(i).getCost();
            item_list.add("其他");
            Map<String, String> map = new HashMap<>();
            map.put("value", cost_other.toString());
            map.put("name", "其他");
            data.add(map);
        } else {
            for (Inventory inventory : inventoryInfo) {
                item_list.add(inventory.getName());
                Map<String, String> map = new HashMap<>();
                map.put("value", inventory.getCost().toString());
                map.put("name", inventory.getName());
                data.add(map);
            }
        }
        String legend_data_str = JSON.toJSONString(item_list);
        String series_data_str = JSON.toJSONString(data);
        result.add(legend_data_str);
        result.add(series_data_str);
        return result;
    }

    /**
     * 获取销售概览柱状图所需数据
     *
     * @param g_id 货品ID（条码）
     * @param days 计算天数
     * @return 图例中项信息、数据信息(index.html)
     */
    @Override
    public List<String> getSalesOverView(String g_id, Integer days) {
        ArrayList<String> item_list = new ArrayList<>();
        ArrayList<String> data_list = new ArrayList<>();
        Map<String, Double> data = new HashMap<>();
        // 保存指定的货品名称
        String selectedName = goodsInfoMapper.queryGoodsInfoByGid(Long.parseLong(g_id)).getName();
        // 获取所有状态为已付款、已签收和已关闭的订单
        List<Orders> orders = new ArrayList<>(ordersMapper.queryOrdersByStatus("paid"));
        orders.addAll(ordersMapper.queryOrdersByStatus("received"));
        orders.addAll(ordersMapper.queryOrdersByStatus("closed"));
        // 按照时间戳从新至旧排序
        orders.sort((o1, o2) -> o2.getTime_stamp().compareTo(o1.getTime_stamp()));
        // 计算时间范围
        Date now = new Date();
        Date prev = new Date(now.getTime() - days * 24L * 60L * 60L * 1000L);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取指定时间范围内的各货品的销售额
        try {
            for (Orders order : orders) {
                Date date = df.parse(order.getTime_stamp());
                if (date.after(prev)) {
                    List<OrderItem> orderItems = orderItemMapper.queryOrderItemByOid(order.getO_id());
                    for (OrderItem orderItem : orderItems) {
                        String gid = orderItem.getG_id().toString();
                        String name = goodsInfoMapper.queryGoodsInfoByGid(Long.parseLong(gid)).getName();
                        if (data.containsKey(name)) // 使用名称-条码后四位标识，保证唯一性
                            data.put(name, data.get(name) + orderItem.getPrice());
                        else
                            data.put(name, orderItem.getPrice());
                    }
                } else
                    break;
            }
        } catch (Exception e) { // 不会出现时间转换异常
        }
        // 将货品按照销售额进行降序排序
        List<Map.Entry<String, Double>> list = new ArrayList<>(data.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        // 获取最多5种最大销售额的货品
        for (int i = 0; i < 5; ++i) {
            if (list.size() <= i)
                break;
            else {
                item_list.add(list.get(i).getKey());
                data_list.add(list.get(i).getValue().toString());
            }
        }
        // 如果指定货品在时间范围内出现且销售额不在前10中，则将其加入到结果中
        // 如果指定货品在时间范围内无销售，向结果中填入0
        if (!item_list.contains(selectedName) && data.containsKey(selectedName)) {
            item_list.add(selectedName);
            data_list.add(data.get(selectedName).toString());
        } else if (!data.containsKey(selectedName)) {
            item_list.add(selectedName);
            data_list.add("0");
        }
        // 将结果转换为JSON字符串
        String axis_data_str = JSON.toJSONString(item_list);
        String series_data_str = JSON.toJSONString(data_list).replace("\"", "");
        List<String> result_list = new ArrayList<>();
        result_list.add(axis_data_str);
        result_list.add(series_data_str);
        return result_list;
    }

    @Override
    public List<String> getOperationOverView() {
        // 初始化变量
        List<String> date_list = new ArrayList<>();
        Collection<Double> init = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        List<Double> sale_data = new ArrayList<>(init);
        List<Double> profit_data = new ArrayList<>(init);
        List<Double> cost_data = new ArrayList<>(init);
        List<Double> inventory_data = new ArrayList<>(init);

        // 获取日期列表
        Long dayT = 24 * 60 * 60 * 1000L;
        Date start = new Date(new Date().getTime() / dayT * dayT - 6 * dayT);
        SimpleDateFormat df_show = new SimpleDateFormat("MM-dd");
        for (int i = 0; i < 7; ++i)
            date_list.add(df_show.format(new Date(start.getTime() + i * 24 * 60 * 60 * 1000L)));

        // 获取每日销售金额和盈利金额
        List<Orders> orders = ordersMapper.queryOrdersByStatus("paid");
        orders.addAll(ordersMapper.queryOrdersByStatus("received"));
        orders.addAll(ordersMapper.queryOrdersByStatus("closed"));
        orders.sort((o1, o2) -> o2.getTime_stamp().compareTo(o1.getTime_stamp()));
        try {
            SimpleDateFormat df_raw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Orders order : orders) {
                Date date = df_raw.parse(order.getTime_stamp());
                if (date.after(start)) {
                    String date_str = df_show.format(date);
                    sale_data.set(date_list.indexOf(date_str), sale_data.get(date_list.indexOf(date_str)) + order.getTotal_turnover());
                    profit_data.set(date_list.indexOf(date_str), profit_data.get(date_list.indexOf(date_str)) + order.getTotal_profit());
                } else
                    break;
            }
        } catch (Exception e) { // 不会出现时间转换异常
        }

        // 获取每日进货金额
        List<Stock> stocks = inventoryMapper.queryAllStocks();
        stocks.sort((o1, o2) -> o2.getTime_stamp().compareTo(o1.getTime_stamp()));
        try {
            SimpleDateFormat df_raw = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (Stock stock : stocks) {
                Date date = df_raw.parse(stock.getTime_stamp());
                if (date.after(start)) {
                    String date_str = df_show.format(date);
                    cost_data.set(date_list.indexOf(date_str), cost_data.get(date_list.indexOf(date_str)) + stock.getCost());
                }
                else
                    break;
            }
        } catch (Exception e) { // 不会出现时间转换异常
        }

        // 获取当前库存积压金额，随后回推之前6天的库存积压金额
        List<Inventory> inventoryInfo = inventoryMapper.queryInventoryWithGnameList();
        double sum = 0.0;
        if (inventoryInfo != null) {
            for (Inventory inventory : inventoryInfo)
                sum += inventory.getCost() * inventory.getQuantity();
        }
        sum = Math.round(100 * sum) / 100.0;
        inventory_data.set(6, sum);
        for (int i = 5; i >= 0; --i) {
            double tmp = inventory_data.get(i + 1) - cost_data.get(i + 1) + sale_data.get(i + 1) - profit_data.get(i + 1);
            inventory_data.set(i, Math.round(100 * tmp) / 100.0);
        }

        // 将结果转换为JSON字符串
        String xAxis_data_str = JSON.toJSONString(date_list);
        String sales_data_str = JSON.toJSONString(sale_data).replace("\"", "");
        String profit_data_str = JSON.toJSONString(profit_data).replace("\"", "");
        String cost_data_str = JSON.toJSONString(cost_data).replace("\"", "");
        String inventory_data_str = JSON.toJSONString(inventory_data).replace("\"", "");
        List<String> result = new ArrayList<>();
        result.add(xAxis_data_str);
        result.add(sales_data_str);
        result.add(profit_data_str);
        result.add(cost_data_str);
        result.add(inventory_data_str);
        return result;
    }
}
