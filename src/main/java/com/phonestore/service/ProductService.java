package com.phonestore.service;

import com.phonestore.constant.StatusBookEnum;
import com.phonestore.dao.ProductDAO;
import com.phonestore.dto.ProductDTO;
import com.phonestore.dto.ResultDTO;
import com.phonestore.dto.StatisticSearchDTO;
import com.phonestore.entity.Product;
import com.phonestore.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductDAO productDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    public List<Product> search(ProductDTO product) {
        try {
            logger.info("search product: {}", product);
            return this.productDAO.searchBook(product);
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ArrayList<>();
        }
    }

    public Product findById(Integer id) {
        return this.productDAO.findById(id);
    }

    public List<Product> findByIdElse(Integer id) {
        return this.productDAO.findByIdElse(id);
    }

    public ResultDTO<Product> save(Product product) {
        try {
            logger.info("save book: {}", product);
            product.setStatus(StatusBookEnum.ACTIVE.getStatus());

            String messageValidate = Validator.validate(product);
            if (Objects.nonNull(messageValidate)) {
                return new ResultDTO(null, true, messageValidate);
            }
            int id = this.productDAO.insert(product);
            product.setId(id);
            return new ResultDTO<>(product, false, "Tạo mới sách thành công");
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Tạo mới sách thất bại");
        }
    }

    public ResultDTO<Product> update(Product product) {
        try {
            logger.info("update book: {}", product);

            Product foundProduct = this.findById(product.getId());
            if (Objects.isNull(foundProduct)) {
                return new ResultDTO<>(null, true, "Không tìm thấy book");
            }
            if (Objects.isNull(product.getStatus())) {
                product.setStatus(StatusBookEnum.ACTIVE.getStatus());
            }
            String messageValidate = Validator.validate(product);
            if (Objects.nonNull(messageValidate)) {
                return new ResultDTO<>(null, true, messageValidate);
            }
            this.productDAO.updateById(product);
            return new ResultDTO<>(product, false, "Cập nhật thành công");
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Cập nhật sách thất bại");
        }
    }

    public ResultDTO<Product> delete(Integer id) {
        try {
            Product product = this.productDAO.findById(id);
            if (Objects.isNull(product)) {
                return new ResultDTO<>(null, true, "Không tìm thấy sách");
            }
            product.setStatus(StatusBookEnum.INACTIVE.getStatus());
            return this.update(product);
        } catch(Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Xóa sách thất bại");
        }
    }
}