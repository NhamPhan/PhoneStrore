package com.phonestore.entity;

import com.phonestore.mapper.annotation.ColumnId;
import com.phonestore.mapper.annotation.DBColumn;
import com.phonestore.mapper.annotation.DBTable;
import com.phonestore.mapper.annotation.IgnoreInsert;
import com.phonestore.util.DateUtil;

import java.sql.Date;

@DBTable(name = "bill")
public class Bill {

    @ColumnId
    @IgnoreInsert
    @DBColumn(name = "id")
    private Integer id;

    @DBColumn(name = "user_name")
    private String username;

    @DBColumn(name = "created_date")
    private Date createdDate;

    @DBColumn(name = "total")
    private Float total;

    @DBColumn(name = "discount_code")
    private String discountCode;

    @DBColumn(name = "payment_method")
    private String paymentMethod;

    @DBColumn(name = "address")
    private String address;

    @DBColumn(name = "phone")
    private String phone;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFormattedDate() {
        return DateUtil.parse(this.createdDate);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}