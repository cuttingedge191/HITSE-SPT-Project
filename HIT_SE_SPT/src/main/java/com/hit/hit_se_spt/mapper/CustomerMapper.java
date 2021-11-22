package com.hit.hit_se_spt.mapper;

import com.hit.hit_se_spt.pojo.Customer;

import java.util.List;

public interface CustomerMapper {
    int insertCustomer(Customer customer);

    int deleteCustomerById(Integer c_id);

    List<Customer> queryCustomerList();

}
