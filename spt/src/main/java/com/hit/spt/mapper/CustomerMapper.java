package com.hit.spt.mapper;

import com.hit.spt.pojo.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CustomerMapper {
    //已测试
    int insertCustomer(Customer customer);

    //已测试
    int deleteCustomerById(Integer c_id);

    //已测试
    List<Customer> queryCustomerList();

    //已测试
    int updateCustomerById(Customer customer);

    Customer queryCustomerById(Integer c_id);

    // 根据type（批发零售）返回customer
    List<Customer> queryCustomerByType(String type);

    List<Customer> queryCustomerByName(String name);
}
