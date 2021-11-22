package com.example.hit_se_spt.mapper;

import com.example.hit_se_spt.pojo.Customer;

import java.util.List;

public interface CustomerMapper {
    Integer insertCustomer(Customer customer);

    List<Customer> queryCustomerList();

}
