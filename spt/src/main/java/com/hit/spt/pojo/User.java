package com.hit.spt.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer u_id;
    private String name;
    private String gender;
    private String position;
    private Integer level ;
    private String username;
    private String password;
}
