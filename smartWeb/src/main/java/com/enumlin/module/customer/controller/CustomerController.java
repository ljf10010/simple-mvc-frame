/*
 * Copyright (c) 2015. Enum.Lin all rights reserved.
 */

package com.enumlin.module.customer.controller;

import com.enumlin.core.annotation.Action;
import com.enumlin.core.annotation.Controller;
import com.enumlin.core.annotation.Inject;
import com.enumlin.core.bean.Data;
import com.enumlin.core.bean.Param;
import com.enumlin.core.bean.View;
import com.enumlin.module.customer.model.Customer;
import com.enumlin.module.customer.service.CustomerService;

import java.util.List;
import java.util.Map;

/*
 * 客户控制器
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-12-01
 * 
 */
@Controller
public class CustomerController {
    @Inject
    private CustomerService customerService;

    /**
     * 进入客户界面
     *
     * @return
     */
    @Action( "get:/customer" )
    public View index() {
        List<Customer> customerList = customerService.getCustomerList();
        View view = new View("customer.jsp").addModel("customerList", customerList);
        return view;
    }

    /**
     * 显示客户基本信息
     *
     * @param param
     * @return
     */
    @Action( "get:/customer_show" )
    public View show(Param param) {
        Long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        View view = new View("customer_show.jsp").addModel("customer", customer);
        return view;
    }

    /**
     * 进入 创建客户 界面
     *
     * @return
     */
    @Action( "get:/customer_create" )
    public View create() {
        return new View("customer_create.jsp");
    }

    /**
     * 处理 客户创建 请求
     *
     * @param param
     * @return
     */
    @Action( "post:/customer_create" )
    public Data createSubmit(Param param) {
        Map<String, Object> map = param.getMap();
        boolean result = customerService.createCustomer(map);
        return new Data(result);
    }

    /**
     * 进入 编辑客户 页面
     *
     * @param param
     * @return
     */
    @Action( "get:/customer_edit" )
    public View edit(Param param) {
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_edit.jsp").addModel("customer", customer);
    }

    /**
     * 处理 编辑客户 请求
     *
     * @param param
     * @return
     */
    @Action( "post:/customer_edit" )
    public Data editSubmit(Param param) {
        long id = param.getLong("id");
        Map<String, Object> map = param.getMap();
        map.remove("id");
        boolean result = customerService.updateCustomer(map, id);
        return new Data(result);
    }

    /**
     * 处理 删除客户 请求
     *
     * @param param
     * @return
     */
    @Action( "delete:/customer_edit" )
    public Data delete(Param param) {
        long id = param.getLong("id");
        boolean result = customerService.deleteCustomer(id);
        return new Data(result);
    }
}
