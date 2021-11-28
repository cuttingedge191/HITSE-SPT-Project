package com.hit.spt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Integer o_id;
    private Integer c_id;
    private String type;
    private String status;
    private Double total_turnover;
    private Double total_cost;
    private Double total_profit;
    private String time_stamp;
    private String name; // 客户名
}
