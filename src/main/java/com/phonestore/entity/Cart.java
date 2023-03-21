package com.phonestore.entity;

import com.phonestore.mapper.annotation.*;
import com.phonestore.validator.annotation.Min;
import com.phonestore.validator.annotation.Required;

@DBTable(name = "cart")
public class Cart {

    @ColumnId
    @IgnoreInsert
    @IgnoreUpdate
    @DBColumn(name = "id")
    private Integer id;

    @DBColumn(name = "product_id")
    @IgnoreUpdate
    @Required(message = "Mã sản phầm không được trống")
    private Integer productId;


    @Required(message = "Số lượng không được trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 1")
    @DBColumn(name = "quantity")
    private Integer quantity;

    @Required(message = "Username không thể trống")
    @DBColumn(name = "user_name")
    @IgnoreUpdate
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}