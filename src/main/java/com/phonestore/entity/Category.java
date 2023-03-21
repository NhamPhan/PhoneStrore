package com.phonestore.entity;

import com.phonestore.mapper.annotation.*;
import com.phonestore.validator.annotation.Required;

@DBTable(name = "category")
public class Category {

    @ColumnId
    @DBColumn(name = "id")
    @IgnoreUpdate
    @IgnoreInsert
    private Integer id;

    @Required(message = "Tên loại sản phẩm không thể trống")
    @DBColumn(name = "name")
    private String name;

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
}