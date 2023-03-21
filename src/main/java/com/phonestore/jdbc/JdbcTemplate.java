package com.phonestore.jdbc;

import com.phonestore.mapper.Mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class JdbcTemplate<T> {

    private Mapper<T> mapper;

    public JdbcTemplate(Class<T> clazz) {
        mapper = new Mapper<>(clazz);
    }

    public ExecutorSelect select(String sql, Object...params) {
        this.notNull(sql);
        return new ExecutorSelect(JdbcHelper.query(sql, params));
    }

    public ExecutorSave insert(String sql, Object... params) {
        this.notNull(sql);
        return new ExecutorSave(JdbcHelper.save(sql, params));
    }

    public ExecutorSave update(String sql, Object... params) {
        return this.insert(sql, params);
    }

    public ExecutorSave delete(String sql, Object... params) {
        return this.insert(sql, params);
    }

    public class ExecutorSelect {

        private ResultSet resultSet;

        public ExecutorSelect(ResultSet resultSet) {
            this.resultSet = resultSet;
        }

        private void validateQuery(ResultSet resultSet) {
            if (Objects.isNull(resultSet)) {
                throw new IllegalArgumentException("ResultSet is null");
            }
        }

        public T executeQuery() throws Exception {
            this.validateQuery(this.resultSet);
            this.resultSet.next();
            T t = mapper.mapAsObject(resultSet);
            JdbcConfig.closeConnection();
            return t;
        }

        public List<T> executeQueryList() throws Exception {
            validateQuery(this.resultSet);
            List<T> ts = mapper.mapAsList(this.resultSet);
            JdbcConfig.closeConnection();
            return ts;
        }

    }

    public class ExecutorSave {

        private PreparedStatement preparedStatement;

        public ExecutorSave(PreparedStatement preparedStatement) {
            this.preparedStatement = preparedStatement;
        }

        private void validateSave(PreparedStatement preparedStatement) {
            if (Objects.isNull(preparedStatement)) {
                throw new IllegalArgumentException("Prepared Statement is null");
            }
        }

        public int executeSave() throws SQLException {
            this.validateSave(this.preparedStatement);
            int i = this.preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Integer key = null;
            if (generatedKeys.next()) {
                key = generatedKeys.getInt(1);
            }
            JdbcConfig.closeConnection();
            return Objects.nonNull(key) ? key : i;
        }
    }

    private void notNull(String sql) {
        if (Objects.isNull(sql)) {
            throw new IllegalArgumentException("sql statement cannot be null");
        }
    }
}