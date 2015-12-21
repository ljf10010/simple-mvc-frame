/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.module.customer.service;

import com.enumlin.core.annotation.Service;
import com.enumlin.core.bean.FileParam;
import com.enumlin.core.helper.DatabaseHelper;
import com.enumlin.core.helper.EntityHelper;
import com.enumlin.core.helper.UploadHelper;
import com.enumlin.module.customer.model.Customer;
import com.enumlin.transaction.annotation.Transaction;

import java.util.List;
import java.util.Map;

/*
 * 客户服务类
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-11-29
 * 
 */
@Service
public class CustomerService {
    /**
     * 获取客户列表
     *
     * @return
     */
    public List<Customer> getCustomerList() {
        String sql = "select * from customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    /**
     * 获取客户
     *
     * @param id
     * @return
     */
    public Customer getCustomer(long id) {
        String sql = "select * from customer where id=?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * 创建客户
     *
     * @param customer
     * @return
     */
    public boolean createCustomer(Map<String, Object> customer, FileParam fileParam) {
        boolean result = DatabaseHelper.insertEntity(Customer.class, customer);
        if (result)
            UploadHelper.uploadFile("/tmp/upload/", fileParam);
        return result;
    }

    /**
     * 创建客户
     *
     * @param customer
     * @return
     */
    @Transaction
    public boolean createCustomer(Customer customer) {
        Map<String, Object> fieldMap = EntityHelper.entity2Map(customer);
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);

    }

    /**
     * 更新客戶
     *
     * @param customer
     * @return
     */
    public boolean updateCustomer(Customer customer) {
        Map<String, Object> fieldMap = EntityHelper.entity2Map(customer);
        return DatabaseHelper.updateEntity(customer.getClass(), customer.getId(), fieldMap);
    }

    /**
     * 更新客戶
     *
     * @param customer
     * @return
     */
    public boolean updateCustomer(Map<String, Object> customer, Long id) {
        return DatabaseHelper.updateEntity(Customer.class, id, customer);
    }

    /**
     * 刪除客戶
     *
     * @param id
     * @return
     */
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
