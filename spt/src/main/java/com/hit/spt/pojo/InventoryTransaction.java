package com.hit.spt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryTransaction {
    private Integer u_id;
    private Integer iti_id;
    private Integer i_id_s;
    private Integer il_id_d;
    private Integer quantity;
    private Integer s_quantity;
    private Integer d_quantity;
    private Integer i_id_d;
    private Integer il_id_s;
    private Long g_id;
    private String goods_name;
    private String s_inventory_name;
    private String d_inventory_name;
}
