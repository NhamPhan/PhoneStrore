package com.phonestore.dao;

import com.phonestore.entity.Category;

import java.sql.SQLException;

public class CategoryDAO extends BaseDAO<Category, Integer> {

    public CategoryDAO() {
        super(Category.class);
    }

    public int insert(Category category) throws SQLException {
        return this.jdbcTemplate
                .insert(this.generator.insert(), category.getName())
                .executeSave();
    }

    public int update(Category category) throws SQLException {
        return this.jdbcTemplate
                .update(this.generator.updateById(), category.getName(), category.getId())
                .executeSave();
    }
}