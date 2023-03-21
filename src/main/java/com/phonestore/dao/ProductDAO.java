package com.phonestore.dao;

import com.phonestore.constant.StatusBookEnum;
import com.phonestore.dto.ProductDTO;
import com.phonestore.dto.StatisticSearchDTO;
import com.phonestore.entity.Product;
import com.phonestore.util.DateUtil;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductDAO extends BaseDAO<Product, Integer> {

    public ProductDAO() {
        super(Product.class);
    }

    public List<Product> searchBook(ProductDTO product) throws Exception {
        StringBuilder searchSql = new StringBuilder("SELECT * FROM product WHERE status = ?");
        List<Object> params = new ArrayList<>();
        params.add(StatusBookEnum.ACTIVE.getStatus());

        if (Objects.nonNull(product.getName())) {
            searchSql.append(" AND name LIKE ?");
            params.add("%"+product.getName()+"%");
        }

        if (Objects.nonNull(product.getIdCategory())) {
            searchSql.append(" AND category_id = ?");
            params.add(product.getIdCategory());
        }

        if (Objects.nonNull(product.getFromPrice())) {
            searchSql.append(" AND price >= ?");
            params.add(product.getFromPrice());
        }

        if (Objects.nonNull(product.getToPrice())) {
            searchSql.append(" AND price <= ?");
            params.add(product.getToPrice());
        }

        return this.jdbcTemplate
                .select(searchSql.toString(), params.toArray())
                .executeQueryList();
    }

    public int insert(Product product) throws SQLException {
        return this.jdbcTemplate
                .insert(this.generator.insert(), product.getName(),  product.getPrice(),  product.getQuantity(), product.getCategoryId(), product.getImage(), product.getStatus(), product.getDescription())
                .executeSave();
    }

    public int updateById(Product product) throws SQLException {
        return this.jdbcTemplate
                .update(this.generator.updateById(), product.getName(),  product.getPrice(), product.getQuantity(), product.getCategoryId(), product.getImage(), product.getStatus(), product.getDescription(), product.getId())
                .executeSave();
    }

    public List<Product> findByIdElse(Integer id) {
        try {
            return this.jdbcTemplate
                    .select("SELECT * FROM product WHERE id <> ?", id)
                    .executeQueryList();
        } catch(Exception ex) {
            return new ArrayList<>();
        }
    }
}