package com.phonestore.entity;

import com.phonestore.mapper.annotation.*;
import com.phonestore.util.DataUtil;
import com.phonestore.validator.annotation.Min;
import com.phonestore.validator.annotation.Range;
import com.phonestore.validator.annotation.Required;

@DBTable(name = "product")
public class Product {

    @ColumnId
    @IgnoreUpdate
    @IgnoreInsert
    @DBColumn(name = "id")
    private Integer id;

    @Required(message = "Tên sản phẩm không được trống")
    @DBColumn(name = "name")
    private String name;

    @Required(message = "Giá tiền không được trống")
    @Min(value = 1, message = "Giá tiền không được nhỏ hơn 1")
    @DBColumn(name = "price")
    private Float price;

    @Required(message = "Số lượng không thể để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    @DBColumn(name = "quantity")
    private Integer quantity;

    @Required(message = "Loại sách không được trống")
    @DBColumn(name = "category_id")
    private Integer categoryId;

    @Required(message = "Hình ảnh không được để trống")
    @DBColumn(name = "image")
    private String image;

    @Required(message = "Trạng thái sản phẩm không được trống")
    @Range(min = 0, max = 1, message = "Trạng thái sách phải là 0: active hoặc 1: inactive")
    @DBColumn(name = "status")
    private Integer status;

    @DBColumn(name = "description")
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceVnd() {
        return DataUtil.formatCurrency(this.price);
    }
}