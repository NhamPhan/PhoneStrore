package com.phonestore.dao;

import com.phonestore.entity.Cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO extends BaseDAO<Cart, Integer>{

    public CartDAO() {
        super(Cart.class);
    }

    public List<Cart> findByUsername(String username) {
        try {
            return this.jdbcTemplate
                    .select(this.generator.selectBy("user_name"), username)
                    .executeQueryList();
        } catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<Cart> findByUsernameAndIds(String username, List<String> ids) throws Exception {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();
        sql.append("SELECT cart.id, cart.user_name, cart.book_id, cart.quantity FROM cart ")
                .append(" WHERE user_name = ?");
        params.add(username);

        if (!ids.isEmpty()) {
            sql.append(" AND ( id = ? ");
            params.add(ids.get(0));
        }


        for (String id : ids) {
            if (id.equals(ids.get(0))) continue;
            sql.append(" OR id = ?");
            params.add(id);
        }

        if (!ids.isEmpty()) {
            sql.append(" )");
        }

        return this.jdbcTemplate
                .select(sql.toString(), params.toArray())
                .executeQueryList();
    }

    public Cart findByUsernameAndBookId(String username, Integer bookId) {
        try {
            return this.jdbcTemplate
                    .select(this.generator.selectBy("user_name", "book_id"), username, bookId)
                    .executeQuery();
        } catch(Exception ex) {
            return null;
        }
    }

    public int insert(Cart cart) throws SQLException {
        return this.jdbcTemplate
                .insert(this.generator.insert(), cart.getProductId(), cart.getQuantity(), cart.getUsername())
                .executeSave();
    }

    public int update(Cart cart) throws SQLException {
        return this.jdbcTemplate
                .update(this.generator.updateById(), cart.getQuantity(), cart.getId())
                .executeSave();
    }

}