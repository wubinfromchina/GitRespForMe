package com.wb.service;

import com.wb.pojo.Customer;

import java.util.List;

public interface CustomerService {

    List<String> queryAllCustomer(String customerName);

    List<Customer> queryAllC();
}
