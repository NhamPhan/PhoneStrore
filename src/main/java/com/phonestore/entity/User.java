package com.phonestore.entity;

import com.phonestore.mapper.annotation.*;
import com.phonestore.validator.annotation.Pattern;
import com.phonestore.validator.annotation.Required;

@DBTable(name = "user")
public class User {

    @ColumnId
    @IgnoreInsert
    @IgnoreUpdate
    @DBColumn(name = "id")
    private Integer id;

    @Required(message = "Tên đăng nhập không được trống")
    @DBColumn(name = "user_name")
    private String username;

    @Required(message = "Mật khẩu không được trống")
    @DBColumn(name = "password")
    private String password;

    @Required(message = "Họ và tên không được trống")
    @DBColumn(name = "full_name")
    private String fullName;

    @Required(message = "Số điện thoại không được trống")
    @Pattern(value = "^(0|\\+84)[0-9]{9}", message = "Số điện thoại không đúng định dạng")
    @DBColumn(name = "phone")
    private String phone;

    @Required(message = "Địa chỉ không được trống")
    @DBColumn(name = "address")
    private String address;

    @DBColumn(name = "role")
    private Integer role;

    @DBColumn(name = "status")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}