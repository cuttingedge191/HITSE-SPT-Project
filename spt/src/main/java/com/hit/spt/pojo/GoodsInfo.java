package com.hit.spt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    private Long g_id;
    private String name;
    private Double cost;
    private Double retail_price;
    private Double trade_price;
    private String description;
}
