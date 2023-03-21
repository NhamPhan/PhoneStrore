package com.phonestore.dao;

import com.phonestore.entity.BillDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillDetailDAO extends BaseDAO<BillDetail, Integer>{

    public BillDetailDAO() {
        super(BillDetail.class);
    }

    public int insert(BillDetail billDetail) throws SQLException {
        return this.jdbcTemplate
                .insert(this.generator.insert(), billDetail.getBillId(), billDetail.getProductId(), billDetail.getPrice(), billDetail.getSubtotal(), billDetail.getQuantity())
                .executeSave();
    }

    public List<BillDetail> findByBillId(Integer billId) {
        try {
            return this.jdbcTemplate
                    .select(this.generator.selectBy("bill_id"), billId)
                    .executeQueryList();
        } catch(Exception ex) {
            return new ArrayList<>();
        }
    }
}