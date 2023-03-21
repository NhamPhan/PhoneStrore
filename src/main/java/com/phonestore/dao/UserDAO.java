package com.phonestore.dao;

import com.phonestore.constant.UserStatusEnum;
import com.phonestore.entity.User;
import com.phonestore.util.DataUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDAO extends BaseDAO<User, Integer> {

    public UserDAO() {
        super(User.class);
    }

    public User findByUsername(String username) {
        try {
            return this.jdbcTemplate
                    .select(this.generator.selectBy("user_name"), username)
                    .executeQuery();
        } catch(Exception ex) {
            return null;
        }
    }

    public int insert(User user) throws SQLException {
        return this.jdbcTemplate
                .insert(this.generator.insert(), user.getUsername(), user.getPassword(), user.getFullName(), user.getPhone(), user.getAddress(), user.getRole())
                .executeSave();
    }

    public int update(User user) throws SQLException {
        return this.jdbcTemplate
                .update("UPDATE user SET password = ?, full_name = ?, phone = ?, address = ? WHERE user_name = ?",
                    user.getPassword(), user.getFullName(), user.getPassword(), user.getAddress(), user.getUsername()
                ).executeSave();
    }

    public List<User> searchUser(User user) {
        try {

            StringBuilder sqlSearch = new StringBuilder("SELECT * FROM user WHERE 1 = 1");
            List<Object> params = new ArrayList<>();

            if (Objects.nonNull(user.getUsername())) {
                sqlSearch.append(" AND user_name LIKE ?");
                params.add("%"+user.getUsername()+"%");
            }

            if (Objects.nonNull(user.getAddress())) {
                sqlSearch.append(" AND address LIKE ?");
                params.add("%"+user.getAddress()+"%");
            }

            if (Objects.nonNull(user.getFullName())) {
                sqlSearch.append(" AND full_name LIKE ?");
                params.add("%"+user.getFullName()+"%");
            }

            if (Objects.nonNull(user.getStatus())) {
                sqlSearch.append(" AND status = ?");
                params.add(user.getStatus());
            }
            sqlSearch.append(" AND role <> 0");
            return this.jdbcTemplate
                    .select(sqlSearch.toString(), params.toArray())
                    .executeQueryList();
        } catch(Exception ex) {
            return new ArrayList<>();
        }
    }

    public void updateStatusUser(String username, UserStatusEnum status) throws SQLException {
        this.jdbcTemplate
                .update("UPDATE user SET status = ? WHERE user_name = ?", status.getValue(), username)
                .executeSave();
    }
}