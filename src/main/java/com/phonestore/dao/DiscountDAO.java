package com.phonestore.dao;

import com.phonestore.constant.AppConstant;
import com.phonestore.entity.Discount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDAO extends BaseDAO<Discount, Integer> {

    public DiscountDAO() {
        super(Discount.class);
    }

    public List<Discount> findStatusActive() {
        try {
            return this.jdbcTemplate
                    .select(this.generator.selectBy("status"), AppConstant.STATUS_DISCOUNT_ACTIVE)
                    .executeQueryList();
        } catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public int insert(Discount discount) throws SQLException {
        return this.jdbcTemplate
                .insert(this.generator.insert(), discount.getCode(), discount.getPercent(), discount.getExpireDate(), discount.getStatus())
                .executeSave();
    }

    public Discount findByCode(String code) {
        try {
            return this.jdbcTemplate
                    .select(this.generator.selectBy("code"), code)
                    .executeQuery();
        } catch(Exception ex) {
            return null;
        }
    }

    public int update(Discount discount) throws SQLException {
        return this.jdbcTemplate
                .update("UPDATE discount SET status = ? WHERE code = ?", discount.getStatus(), discount.getCode())
                .executeSave();
    }
}