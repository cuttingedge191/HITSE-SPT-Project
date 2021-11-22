package com.hit.spt.service.impl;

import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientInfoService {
    @Autowired
    CustomerMapper customerMapper;
    public List<Customer> showAllCustoms(){
        List<Customer> customers = customerMapper.queryCustomerList();
        return customers;
    }

    public void insertCustomer(){

    }
}
