package com.hit.spt;

import com.hit.spt.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CustomerMapper {
    int insertCustomer(Customer customer);

    int deleteCustomerById(Integer c_id);

    List<Customer> queryCustomerList();

}
