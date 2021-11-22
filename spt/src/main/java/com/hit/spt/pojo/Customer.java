package com.hit.spt.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer c_id;
    private String type;
    private String name;
    private String gender;
    private String phone;
    private String address;

}
