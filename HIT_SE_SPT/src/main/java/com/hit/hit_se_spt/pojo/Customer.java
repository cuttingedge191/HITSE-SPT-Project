package com.hit.hit_se_spt.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer c_id;
    private Integer type;
    private String name;
    private String gender;
    private String phone;
    private String address;
}
