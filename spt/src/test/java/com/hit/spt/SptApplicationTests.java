package com.hit.spt;

import com.hit.spt.controller.CustomerController;
import com.hit.spt.mapper.CustomerMapper;
import com.hit.spt.pojo.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
class SptApplicationTests {
    @Autowired
    CustomerMapper customerMapper;

    @Test
    public void testCustomerMapper() {
        System.out.println(customerMapper.queryCustomerList());
        System.out.println(customerMapper.insertCustomer(new Customer()));
    }

}
