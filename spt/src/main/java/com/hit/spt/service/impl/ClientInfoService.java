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

    public void insertCustomer(Customer customer){
        customerMapper.insertCustomer(customer);
    }

    public void deleteCustomer(Integer c_id){
        customerMapper.deleteCustomerById(c_id);
    }

    public void updateCustomer(Customer customer){
        customerMapper.updateCustomerById(customer);
    }
    public Customer queryCustomerById(Integer c_id){
        return customerMapper.queryCustomerById(c_id);
    }
    public List<Customer> queryCustomerByType(String type) {
        return customerMapper.queryCustomerByType(type);
    }
}
