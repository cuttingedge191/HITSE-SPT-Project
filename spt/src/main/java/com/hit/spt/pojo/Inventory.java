package com.hit.spt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private Integer i_id;
    private Long g_id;
    private Integer quantity;
    private String quality;
    private String name;
    private Double retail_price;
    private Double cost;
}
