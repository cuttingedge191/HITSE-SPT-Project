package com.example.hit_se_spt.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer cid;
    private Integer type;
    private String name;
    private String gender;
    private String phone;
}
