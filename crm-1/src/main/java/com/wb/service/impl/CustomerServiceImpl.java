package com.wb.service.impl;

import com.wb.mapper.CustomerMapper;
import com.wb.pojo.Customer;
import com.wb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<String> queryAllCustomer(String customerName) {
        return customerMapper.selectAllCustomer(customerName);
    }

    @Override
    public List<Customer> queryAllC() {
        return customerMapper.selectAllC();
    }
}
