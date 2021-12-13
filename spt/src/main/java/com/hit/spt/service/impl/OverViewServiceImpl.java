package com.hit.spt.service.impl;

import com.alibaba.fastjson.JSON;
import com.hit.spt.mapper.InventoryMapper;
import com.hit.spt.pojo.Inventory;
import com.hit.spt.service.OverViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OverViewServiceImpl implements OverViewService {

    @Autowired
    InventoryMapper inventoryMapper;

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
}