package com.phonestore.service;

import com.phonestore.dao.CategoryDAO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Category;
import com.phonestore.validator.Validator;

import java.util.List;
import java.util.Objects;

public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public List<Category> findAll() {
        return this.categoryDAO.findAll();
    }

    public Category findById(Integer id) {
        return this.categoryDAO.findById(id);
    }

    public ResultDTO<Category> save(Category category) {
        try {

            String messageValidate = Validator.validate(category);
            if (Objects.nonNull(messageValidate)) {
                return new ResultDTO<>(null, true, messageValidate);
            }

            int id = this.categoryDAO.insert(category);
            category.setId(id);
            return new ResultDTO<>(category, false, "Thêm mới thành công");
        } catch (Exception ex) {
            return new ResultDTO<>(null, true, "Thêm mới thất bại");
        }
    }

    public ResultDTO<Category> update(Category category) {
        try {
            String messageValidate = Validator.validate(category);
            if (Objects.nonNull(messageValidate)) {
                return new ResultDTO<>(null, true, messageValidate);
            }
            this.categoryDAO.update(category);
            return new ResultDTO<>(category, false, "Cập nhật thành công");
        } catch(Exception ex) {
            return new ResultDTO<>(null, true, "Cập nhật thất bại");
        }
    }
}