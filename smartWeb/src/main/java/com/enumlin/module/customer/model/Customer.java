package com.enumlin.module.customer.model;

import com.enumlin.core.annotation.ExcludeField;

/*
 * 客户实体
 *
 * @author   Enum.Lin
 * @version  1.0
 * @since    2015-11-29
 * 
 */
public class Customer {
    @ExcludeField
    private long id;
    private String name;
    private String contact;
    private String telephone;
    private String email;
    private String remark;

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "contact='" + contact + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
