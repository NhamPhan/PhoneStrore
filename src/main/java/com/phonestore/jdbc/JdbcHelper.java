package com.phonestore.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Objects;

public class JdbcHelper {

    private static final Logger logger = LoggerFactory.getLogger(JdbcHelper.class);

    private static Connection connection;

    private JdbcHelper() {}

    public static ResultSet query(String sql, Object... param) {
        try {
            logger.info(sql);
            PreparedStatement preparedStatement = getPreparedStatement(sql, param);
            return preparedStatement.executeQuery();
        } catch(Exception ex) {
            JdbcConfig.closeConnection();
            return null;
        }
    }

    public static PreparedStatement save(String sql, Object... param) {
        try {
            logger.info(sql);
            return getPreparedStatement(sql, param);
        } catch(Exception ex) {
            ex.printStackTrace();
            JdbcConfig.closeConnection();
            return null;
        }
    }

    private static PreparedStatement getPreparedStatement(String sql, Object...params) throws SQLException {
        if (Objects.isNull(connection) || connection.isClosed()) {
            connection = JdbcConfig.openConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        if (Objects.isNull(params)) {
            return preparedStatement;
        }
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement;
    }
}