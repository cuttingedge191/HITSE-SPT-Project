package com.hit.spt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Integer oi_id;
    private Integer o_id;
    private Long g_id;
    private Integer quantity;
    private Double cost;
    private Double price;
    private String name;
}
