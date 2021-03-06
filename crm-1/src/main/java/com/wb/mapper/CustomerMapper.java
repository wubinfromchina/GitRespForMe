package com.wb.mapper;

import com.wb.pojo.Customer;

import java.util.List;

public interface CustomerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun May 15 21:19:04 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun May 15 21:19:04 CST 2022
     */
    int insert(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun May 15 21:19:04 CST 2022
     */
    int insertSelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun May 15 21:19:04 CST 2022
     */
    Customer selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun May 15 21:19:04 CST 2022
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_customer
     *
     * @mbggenerated Sun May 15 21:19:04 CST 2022
     */
    int updateByPrimaryKey(Customer record);

    /**
     * 保存转换的客户，以及新建客户
     * @param customer
     * @return
     */
    Integer insertCustomer(Customer customer);

    /**
     * 查询所有客户名字
     * @return
     */
    List<String> selectAllCustomer(String customerName);

    /**
     * 根据名字精确查询客户
     * @param name
     * @return
     */
    Customer selectCustomerByName(String name);

    /**
     * 查询所有客户明细
     * @return
     */
    List<Customer> selectAllC();
}